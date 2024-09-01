package Control;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 *
 * Notes:
 * iterator: getEventVolunteerJoined getAllEvent
 * */

import Boundary.EventUI;
import DAO.UniversalFileHandler;
import ADT.ArrayList;
import Entity.Event;
import Entity.EventStatus;
import Entity.EventVolunteer;
import Utilities.GeneralFunction;
import ADT.ListInterface;
import Utilities.Search;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Random;

import static Boundary.EventUI.*;
import static Utilities.Message.*;

public class EventHandler {

    //region UI Handler
    public static void subsystemMenu(){
        root: {
            while(true) {
                subsystemMenuDisplay();

                int action = 0;
                while (true) {
                    String _action = getSubSystemMenuAction();

                    if (_action.isEmpty()) {
                        displayGeneralErrorMsg("Input can't be empty.");
                        continue;
                    }

                    try {
                        action = Integer.parseInt(_action);
                    } catch (NumberFormatException e) {
                        displayGeneralErrorMsg("Input is not a number.");
                        continue;
                    }

                    if (action < 1 || action > 9) {
                        displayGeneralErrorMsg("Invalid action.");
                        continue;
                    }

                    break;
                }

                switch (action) {
                    case 1:
                        addNewEvent();
                        break;
                    case 2:
                        removeEvent();
                        break;
                    case 3:
                        searchEvent();
                        break;
                    case 4:
                        amendEventDetails();
                        break;
                    case 5:
                        listAllEvent();
                        break;
                    case 6:
                        removeEventFromVolunteerUI();
                        break;
                    case 7:
                        listAllEventForVolunteer();
                        GeneralFunction.enterToContinue();
                        break;
                    case 8:
                        generateReport();
                        break;
                    case 9:
                        break root;
                }
            }
        }
    }

    private static void addNewEvent(){
        addNewEventUI();

        String eventName = getEventName();

        LocalDateTime eventStartDateTime = getStartTimeInput();
        LocalDateTime eventEndDateTime = getEndTimeInput(eventStartDateTime);
        String venue = getVenueInput();
        String description = getDescription();

        EventHandler.addNewEvent(eventName, eventStartDateTime, eventEndDateTime, venue, description);

        displaySuccessMessage("New event added.");

        GeneralFunction.enterToContinue();
    }

    private static void removeEvent(){
        removeEventUI();
        displayGeneralYellowMessage("Event sorted based on event status");

        Event eventChosen;

        while(true){
            String eventID = getEventInputUI("Enter event ID: ");

            if(eventID.length() != 6){
                displayGeneralErrorMsg("EventID invalid. Please try again.");
                continue;
            }

            eventChosen = EventHandler.searchEventByEventID(eventID);

            if(eventChosen == null){
                displayGeneralErrorMsg("EventID does not exist. Please try again.");
                continue;
            }

            break;
        }

        EventHandler.removeEvent(eventChosen);
        displaySuccessMessage("Removed event.");
        GeneralFunction.enterToContinue();
    }

    private static void searchEvent(){
        searchEventUI();

        while(true){
            String eventName = getEventInputUI("Enter event name (x to stop): ");

            if(eventName.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty.");
                continue;
            }

            if(eventName.equals("x")){
                break;
            }

            ListInterface<Event> events = EventHandler.searchAllEventByEventName(eventName);
            events.sort(Comparator.comparing(Event::eventStatus));

            if(events == null || events.isEmpty()){
                displayGeneralErrorMsg("Event name does not exist. Please try again.");
                continue;
            }

            EventDisplay(events, "All events", 102);
            displayGeneralYellowMessage("Event sorted based on event status");
        }
    }

    private static void amendEventDetails(){
        amendEventDetailsUI();

        ListInterface<Event> events = EventHandler.searchAllEventByEventStatus(EventStatus.UPCOMING);

        if(events == null || events.isEmpty()){
            displayGeneralErrorMsg("No upcoming event to modify.");
            GeneralFunction.enterToContinue();
            return;
        }

        EventDisplay(events, "All Upcoming Events", 102);

        String eventID;

        root: {
            while(true) {
                eventID = getEventInputUI("Enter event ID: ");

                for (int i = 0; i < events.size(); i++) {
                    Event event = events.get(i);

                    if (event.eventID().equals(eventID)) {
                        break root;
                    }
                }

                displayGeneralErrorMsg("Event ID does not exist. Please try again.");
            }
        }

        String eventName = getEventName();
        String venue = getVenueInput();
        String description = getDescription();

        EventHandler.modifyEvent(eventID, eventName, venue, description, EventStatus.UPCOMING);

        displaySuccessMessage("Event modified.");
        GeneralFunction.enterToContinue();
    }

    private static void listAllEvent(){
        GeneralFunction.clearScreen();

        ListInterface<Event> allEvents = EventHandler.getAllEvent();
        allEvents.sort(Comparator.comparing(Event::eventStatus));

        EventDisplay(allEvents, "All events", 102);
        displayGeneralYellowMessage("Event sorted based on event status.");

        GeneralFunction.enterToContinue();
    }

    private static ListInterface<EventVolunteer> listAllEventForVolunteer(){
        GeneralFunction.clearScreen();

        ListInterface<String> eventVolunteers = EventHandler.getAllEventVolunteerID();

        if(eventVolunteers.isEmpty()){
            displayGeneralErrorMsg("Don't have any volunteer joining any event. ");
            GeneralFunction.enterToContinue();
            return null;
        }

        EventUI.listAllEvent(eventVolunteers);

        String volunteerID;
        while (true){
            volunteerID = getEventInputUI("Enter volunteerID to view event list: ");

            if(volunteerID.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty.");
                continue;
            }

            if(!eventVolunteers.contains(volunteerID)){
                displayGeneralErrorMsg("Volunteer does not exist. Please try again.");
                continue;
            }

            break;
        }

        ListInterface <EventVolunteer> eventsJoined = EventHandler.getEventVolunteerJoined(volunteerID);

        if(eventsJoined == null){
            displayGeneralErrorMsg("Volunteer doesn't join any event.");
            GeneralFunction.enterToContinue();

            return null;
        }

        eventsJoined.sort(Comparator.comparing(EventVolunteer::eventID));

        listVolunteerJoinedEvent(eventsJoined);
        displayGeneralYellowMessage("Event sorted based on eventID.");

        return eventsJoined;
    }

    private static void removeEventFromVolunteerUI(){
        ListInterface<EventVolunteer> eventsJoined = listAllEventForVolunteer();

        if(eventsJoined == null){
            GeneralFunction.enterToContinue();
            return;
        }

        while(true){
            String eventID = getEventInputUI("Enter event ID to opt out event: ");

            if(eventID.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty.");
                continue;
            }

            for (int i = 0; i < eventsJoined.size(); i++) {
                EventVolunteer eventVolunteer = eventsJoined.get(i);

                if(eventVolunteer.eventID().equals(eventID)){
                    EventHandler.removeVolunteerFromVolunteerEvent(eventID, eventVolunteer.VolunteerID());
                    displaySuccessMessage("Remove successfully.");

                    GeneralFunction.enterToContinue();
                    return;
                }
            }

            displayGeneralErrorMsg("Event ID does not exist. Please try again.");
        }
    }

    private static void generateReport(){
        ListInterface<Event> allEvent = EventHandler.getAllEvent();
        ListInterface<Event> ongoingEvent = new ArrayList<>();
        ListInterface<Event> upcomingEvent = new ArrayList<>();
        ListInterface<Event> completeEvent = new ArrayList<>();

        for (int i = 0; i < allEvent.size(); i++) {
            Event event = allEvent.get(i);

            if(event.eventStatus() == EventStatus.UPCOMING){
                upcomingEvent.add(event);
            } else if (event.eventStatus() == EventStatus.ONGOING){
                ongoingEvent.add(event);
            } else if (event.eventStatus() == EventStatus.COMPLETED){
                completeEvent.add(event);
            }
        }

        generateReportsUI(ongoingEvent.size(), upcomingEvent.size(), completeEvent.size(), allEvent.size());
    }
    //endregion

    //region Util

    private static String getEventName(){
        String eventName;

        while(true){
            eventName = getEventInputUI("Enter event name: ");

            if (eventName.isEmpty()) {
                displayGeneralErrorMsg("Input can't be empty.");
                continue;
            }

            return eventName;
        }
    }

    private static LocalDateTime getStartTimeInput(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime eventStartDateTime = null;

        while(true){
            try{
                eventStartDateTime = LocalDateTime.parse(getEventInputUI("Enter start time (format: dd-MM-yyyy HH:mm): "), dtf);
            }catch(DateTimeParseException e){
                displayGeneralErrorMsg("Invalid format of start time. Please try again.");
                continue;
            }

            if(eventStartDateTime.isBefore(LocalDateTime.now())){
                displayGeneralErrorMsg("Start time is before current time. Please try again.");
                continue;
            }

            return eventStartDateTime;
        }
    }

    private static LocalDateTime getEndTimeInput(LocalDateTime startDateTime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime eventEndDateTime = null;

        while(true){
            try{
                eventEndDateTime = LocalDateTime.parse(getEventInputUI("Enter end time (format: dd-MM-yyyy HH:mm): "), dtf);
            }catch(DateTimeParseException e){
                displayGeneralErrorMsg("Invalid format of end time. Please try again.");
                continue;
            }

            if(eventEndDateTime.isBefore(startDateTime)){
                displayGeneralErrorMsg("End time is before start time. Please try again.");
                continue;
            }

            return eventEndDateTime;
        }
    }

    private static String getVenueInput(){
        String venue;

        while(true){
            venue = getEventInputUI("Enter venue: ");

            if(venue.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty.");
                continue;
            }

            return venue;
        }
    }

    private static String getDescription(){
       return getEventInputUI("Enter description: ");
    }

    //endregion

    //region Logic Handler
    private final static String eventFilePath = "event.txt";
    private final static String eventVolunteerFilePath = "eventVolunteer.txt";

    public static Event addNewEvent(String _eventName, LocalDateTime _startDateTime, LocalDateTime _endDateTime, String _venue, /*int _minVolunteerPax, int _maxVolunteerPax,*/ String _description){

        if(_endDateTime.isBefore(_startDateTime)) {
            return null;
        }

        if(_venue.isEmpty()) {
            return null;
        }

        Event newEvent = new Event(
                generateEventID(),
                _eventName,
                _startDateTime,
                _endDateTime,
                _venue,
                _description,
                EventStatus.UPCOMING
        );

        UniversalFileHandler.appendData(eventFilePath, newEvent.toString());

        return newEvent;
    }

    public static void removeEvent(Event event){
        UniversalFileHandler.removeData(eventFilePath, event.toString());
        UniversalFileHandler.removeData(eventVolunteerFilePath, Search.searchAllMatchesString(eventVolunteerFilePath, event.eventID(), Event.separator, 0));
    }

    public static ListInterface<EventVolunteer> searchEventVolunteerByEventID(String eventID){
        ListInterface<String> eventVolunteerStrings = Search.searchAllMatchesString(eventVolunteerFilePath, eventID, Event.separator, 0);
        ListInterface<EventVolunteer> eventVolunteers = new ArrayList<>();

        if(eventVolunteerStrings == null) {
            return null;
        }

        for (int i = 0; i < eventVolunteerStrings.size(); i++) {
            String eventVolunteerString = eventVolunteerStrings.get(i);

            EventVolunteer eventVolunteer = new EventVolunteer(eventVolunteerString);
            eventVolunteers.add(eventVolunteer);
        }

        return eventVolunteers;
    }

    public static Event searchEventByEventID(String eventID){
        String eventString = Search.searchFirstMatchesStringFromFile(eventFilePath, eventID, Event.separator, 0);

        if(eventString == null) {
            return null;
        }

        return new Event(eventString);
    }

    public static ListInterface<Event> searchAllEventByEventName(String eventName){
        ListInterface<String> eventListString = Search.searchAllMatchesString(eventFilePath, eventName, Event.separator, 1);
        ListInterface<Event> events = new ArrayList<>();

        if(eventListString == null) {
            return null;
        }

        for (int i = 0; i < eventListString.size(); i++) {
            String eventString = eventListString.get(i);

            Event event = new Event(eventString);
            events.add(event);
        }

        return events;
    }

    public static ListInterface<Event> searchAllEventByEventStatus(EventStatus eventStatus){
        ListInterface<String> eventListString = Search.searchAllMatchesString(eventFilePath, eventStatus.toString(), Event.separator, 6);
        ListInterface<Event> events = new ArrayList<>();

        if(eventListString == null) {
            return null;
        }

        for (int i = 0; i < eventListString.size(); i++) {
            String eventString = eventListString.get(i);

            Event event = new Event(eventString);
            events.add(event);
        }

        return events;
    }

    public static ListInterface<Event> getAllEvent(){
        ListInterface<String> eventsString = UniversalFileHandler.readData(eventFilePath);
        ListInterface<Event> events = new ArrayList<>();

        Iterator<String> iterator = eventsString.iterator();

        while(iterator.hasNext()){
            String eventString = iterator.next();

            Event event = new Event(eventString);
            events.add(event);
        }

        return events;
    }

    public static ListInterface<String> getAllEventVolunteerID(){
        ListInterface<String> eventVolunteersString = UniversalFileHandler.readData(eventVolunteerFilePath);
        ListInterface<String> eventVolunteerIDs = new ArrayList<>();

        for (int i = 0; i < eventVolunteersString.size(); i++) {
            String eventVolunteerString = eventVolunteersString.get(i);
            String volunteerID = eventVolunteerString.split(Event.separator)[1];

            boolean exists = false;

            for (int j = 0; j < eventVolunteerIDs.size(); j++) {
                String _volunteerID = eventVolunteerIDs.get(j);

                if(_volunteerID.equals(volunteerID)) {
                    exists = true;
                    break;
                }
            }

            if(!exists) {
                eventVolunteerIDs.add(volunteerID);
            }
        }

        return eventVolunteerIDs;
    }

    public static void modifyEvent(String eventID, String _eventName, String _venue, /*int _minVolunteerPax, int _maxVolunteerPax,*/ String _description, EventStatus eventStatus){
        Event eventOld = searchEventByEventID(eventID);
        Event eventNew = new Event(eventID, _eventName, eventOld.startDateTime(), eventOld.endDateTime(), _venue, /*_minVolunteerPax, _maxVolunteerPax,*/ _description, eventStatus);

        UniversalFileHandler.modifyData(eventFilePath, eventOld.toString(), eventNew.toString());
    }

    private static String generateEventID(){
        while (true){
            int newID = new Random().nextInt(899999) + 100000;

            String exists = Search.searchFirstMatchesStringFromFile(eventFilePath, newID+ "", Event.separator, 0);

            if(exists == null) {
                return newID + "";
            }
        }
    }

    public static ListInterface<EventVolunteer> getEventVolunteerJoined(String volunteerID){
        ListInterface<String> eventVolunteerListString = Search.searchAllMatchesString(eventVolunteerFilePath, volunteerID, Event.separator, 1);
        ListInterface<EventVolunteer> eventVolunteers = new ArrayList<>();

        if(eventVolunteerListString == null) {
            return null;
        }

        Iterator<String> iterator = eventVolunteerListString.iterator();

        while (iterator.hasNext()){
            String eventVolunteerString = iterator.next();

            EventVolunteer event = new EventVolunteer(eventVolunteerString);
            eventVolunteers.add(event);
        }

        return eventVolunteers;
    }

    public static void addEventVolunteer(String eventID, String volunteerID){
        UniversalFileHandler.appendData(eventVolunteerFilePath, new EventVolunteer(eventID, volunteerID/*, VolunteerStatus.REGISTERED*/).toString());
    }

    public static void removeVolunteerFromAllVolunteerEvent(String volunteerID){
        ListInterface<EventVolunteer> eventVolunteers = getEventVolunteerJoined(volunteerID);

        if(eventVolunteers != null) {
            for (int i = 0; i < eventVolunteers.size(); i++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(i);

                String old = eventVolunteer.toString();
                //eventVolunteer.setVolunteerStatus(VolunteerStatus.REMOVED);
                UniversalFileHandler.modifyData(eventVolunteerFilePath, old, eventVolunteer.toString());
            }
        }
    }

    public static void removeVolunteerFromVolunteerEvent(String eventID, String volunteerID){
        ListInterface<EventVolunteer> eventVolunteers = getEventVolunteerJoined(volunteerID);

        if(eventVolunteers != null) {
            for (int i = 0; i < eventVolunteers.size(); i++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(i);

                if(eventVolunteer.eventID().equals(eventID)) {
                    String old = eventVolunteer.toString();

                    UniversalFileHandler.removeData(eventVolunteerFilePath, old);
                }
            }
        }
    }

    public static void updateEventStatus(){
        ListInterface<Event> allEvents = getAllEvent();

        for (int i = 0; i < allEvents.size(); i++) {
            Event event = allEvents.get(i);

            if(event.endDateTime().isBefore(LocalDateTime.now()) && event.eventStatus() != EventStatus.COMPLETED){
                modifyEvent(event.eventID(), event.eventName(), event.venue(), event.description(), EventStatus.COMPLETED);
            } else if (event.endDateTime().isAfter(LocalDateTime.now()) && event.startDateTime().isBefore(LocalDateTime.now()) && event.eventStatus() != EventStatus.ONGOING) {
                modifyEvent(event.eventID(), event.eventName(), event.venue(), event.description(), EventStatus.ONGOING);
            }
        }
    }
    //endregion
}
