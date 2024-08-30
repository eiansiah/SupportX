package Main.Event;

import Boundary.EventUI;
import FileHandling.UniversalFileHandler;
import Libraries.ArrayList;
import Libraries.Color;
import Libraries.GeneralFunction;
import Utilities.Search;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Random;
import java.util.Scanner;

import static Boundary.EventUI.*;
import static Utilities.Message.*;

public class EventHandler {

    public static void main(String[] args) {
        updateEventStatus();
        subsystemMenu();
    }

    //region UI Handler
    private static void subsystemMenu(){
        root: {
            while(true) {
                subsystemMenuDisplay();

                int action = 0;
                while (true) {
                    String _action = getSubSystemMenuAction();

                    if (_action.isEmpty()) {
                        displayGeneralErrorMsg("Input can't be empty");
                        continue;
                    }

                    try {
                        action = Integer.parseInt(_action);
                    } catch (NumberFormatException e) {
                        displayGeneralErrorMsg("Input is not a number");
                        continue;
                    }

                    if (action < 1 || action > 9) {
                        displayGeneralErrorMsg("Invalid action");
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

        displaySuccessMessage("New event added");

        GeneralFunction.enterToContinue();
    }

    private static void removeEvent(){
        removeEventUI();

        Event eventChosen;

        while(true){
            String eventID = getEventInputUI("Enter event ID: ");;

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
        displaySuccessMessage("Removed event");
        GeneralFunction.enterToContinue();
    }

    private static void searchEvent(){
        searchEventUI();

        while(true){
            String eventName = getEventInputUI("Enter event name (x to stop): ");

            if(eventName.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            if(eventName.equals("x")){
                break;
            }

            ArrayList<Event> events = EventHandler.searchAllEventByEventName(eventName);

            if(events == null || events.isEmpty()){
                displayGeneralErrorMsg("Event name does not exist. Please try again.");
                continue;
            }

            EventDisplay(events, "All events", 102);
        }
    }

    private static void amendEventDetails(){
        amendEventDetailsUI();

        ArrayList<Event> events = EventHandler.searchAllEventByEventStatus(EventStatus.UPCOMING);

        if(events == null){
            displayGeneralErrorMsg("No upcoming event to modify.");
            GeneralFunction.enterToContinue();
            return;
        }

        EventDisplay(events, "All Upcoming Events", 102);

        String eventID;

        root: {
            while(true) {
                eventID = getEventInputUI("Enter event ID: ");

                for (Event event : events) {
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

        displaySuccessMessage("Event modified");
        GeneralFunction.enterToContinue();
    }

    private static void listAllEvent(){
        GeneralFunction.clearScreen();

        ArrayList<Event> allEvents = EventHandler.getAllEvent();

        EventDisplay(allEvents, "All events", 102);

        GeneralFunction.enterToContinue();
    }

    private static ArrayList <EventVolunteer> listAllEventForVolunteer(){
        GeneralFunction.clearScreen();

        ArrayList<String> eventVolunteers = EventHandler.getAllEventVolunteerID();

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
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            if(!eventVolunteers.contains(volunteerID)){
                displayGeneralErrorMsg("Volunteer does not exist. Please try again.");
                continue;
            }

            break;
        }

        ArrayList <EventVolunteer> eventsJoined = EventHandler.getEventVolunteerJoined(volunteerID);

        if(eventsJoined == null){
            displayGeneralErrorMsg("Volunteer doesn't join any event");
            GeneralFunction.enterToContinue();

            return null;
        }

        listVolunteerJoinedEvent(eventsJoined);

        return eventsJoined;
    }

    private static void removeEventFromVolunteerUI(){
        ArrayList <EventVolunteer> eventsJoined = listAllEventForVolunteer();

        if(eventsJoined == null){
            GeneralFunction.enterToContinue();
            return;
        }

        while(true){
            String eventID = getEventInputUI("Enter event ID to opt out event: ");

            if(eventID.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            for (EventVolunteer eventVolunteer: eventsJoined){
                if(eventVolunteer.eventID().equals(eventID)){
                    EventHandler.removeVolunteerFromVolunteerEvent(eventID, eventVolunteer.VolunteerID());
                    displaySuccessMessage("Remove successfully");

                    GeneralFunction.enterToContinue();
                    return;
                }
            }

            displayGeneralErrorMsg("Event ID does not exist. Please try again.");
        }
    }

    private static void generateReport(){
        ArrayList<Event> allEvent = EventHandler.getAllEvent();
        ArrayList<Event> ongoingEvent = new ArrayList<>();
        ArrayList<Event> upcomingEvent = new ArrayList<>();
        ArrayList<Event> completeEvent = new ArrayList<>();

        for(Event event : allEvent){
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
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            return eventName;
        }
    }

    private static LocalDateTime getStartTimeInput(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime eventStartDateTime = null;
        Scanner sc = new Scanner(System.in);

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
        Scanner sc = new Scanner(System.in);

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
                displayGeneralErrorMsg("Input can't be empty");
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

        /*if (_minVolunteerPax < 0 || _maxVolunteerPax < 0 ) {
            return null;
        }*/

        if(_venue.isEmpty()) {
            return null;
        }

        Event newEvent = new Event(
                generateEventID(),
                _eventName,
                _startDateTime,
                _endDateTime,
                _venue,
                /*_minVolunteerPax,
                _maxVolunteerPax,*/
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

    public static ArrayList<EventVolunteer> searchEventVolunteerByEventID(String eventID){
        ArrayList<String> eventVolunteerStrings = Search.searchAllMatchesString(eventVolunteerFilePath, eventID, Event.separator, 0);
        ArrayList<EventVolunteer> eventVolunteers = new ArrayList<>();

        if(eventVolunteerStrings == null) {
            return null;
        }

        for (String eventVolunteerString : eventVolunteerStrings) {
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

    public static ArrayList<Event> searchAllEventByEventName(String eventName){
        ArrayList<String> eventListString = Search.searchAllMatchesString(eventFilePath, eventName, Event.separator, 1);
        ArrayList<Event> events = new ArrayList<>();

        if(eventListString == null) {
            return null;
        }

        for(String eventString : eventListString) {
            Event event = new Event(eventString);
            events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> searchAllEventByEventStatus(EventStatus eventStatus){
        ArrayList<String> eventListString = Search.searchAllMatchesString(eventFilePath, eventStatus.toString(), Event.separator, 6);
        ArrayList<Event> events = new ArrayList<>();

        if(eventListString == null) {
            return null;
        }

        for(String eventString : eventListString) {
            Event event = new Event(eventString);
            events.add(event);
        }

        return events;
    }

    public static ArrayList<Event> getAllEvent(){
        ArrayList<String> eventsString = UniversalFileHandler.readData(eventFilePath);
        ArrayList<Event> events = new ArrayList<>();

        for(String eventString : eventsString) {
            Event event = new Event(eventString);
            events.add(event);
        }

        return events;
    }

    public static ArrayList<String> getAllEventVolunteerID(){
        ArrayList<String> eventVolunteersString = UniversalFileHandler.readData(eventVolunteerFilePath);
        ArrayList<String> eventVolunteerIDs = new ArrayList<>();

        for(String eventVolunteerString : eventVolunteersString) {
            String volunteerID = eventVolunteerString.split(Event.separator)[1];

            boolean exists = false;

            for(String _volunteerID : eventVolunteerIDs){
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

    public static ArrayList<EventVolunteer> getEventVolunteerJoined(String volunteerID){
        ArrayList<String> eventVolunteerListString = Search.searchAllMatchesString(eventVolunteerFilePath, volunteerID, Event.separator, 1);
        ArrayList<EventVolunteer> eventVolunteers = new ArrayList<>();

        if(eventVolunteerListString == null) {
            return null;
        }

        for(String eventVolunteerString : eventVolunteerListString) {
            EventVolunteer event = new EventVolunteer(eventVolunteerString);
            eventVolunteers.add(event);
        }

        return eventVolunteers;
    }

    public static void addEventVolunteer(String eventID, String volunteerID){
        UniversalFileHandler.appendData(eventVolunteerFilePath, new EventVolunteer(eventID, volunteerID/*, VolunteerStatus.REGISTERED*/).toString());
    }

    public static void removeVolunteerFromAllVolunteerEvent(String volunteerID){
        ArrayList<EventVolunteer> eventVolunteers = getEventVolunteerJoined(volunteerID);

        if(eventVolunteers != null) {
            for(EventVolunteer eventVolunteer : eventVolunteers) {
                String old = eventVolunteer.toString();
                //eventVolunteer.setVolunteerStatus(VolunteerStatus.REMOVED);
                UniversalFileHandler.modifyData(eventVolunteerFilePath, old, eventVolunteer.toString());
            }
        }
    }

    public static void removeVolunteerFromVolunteerEvent(String eventID, String volunteerID){
        ArrayList<EventVolunteer> eventVolunteers = getEventVolunteerJoined(volunteerID);

        if(eventVolunteers != null) {
            for(EventVolunteer eventVolunteer : eventVolunteers) {
                if(eventVolunteer.eventID().equals(eventID)) {
                    String old = eventVolunteer.toString();
                    //eventVolunteer.setVolunteerStatus(VolunteerStatus.REMOVED);

                    UniversalFileHandler.removeData(eventVolunteerFilePath, old);
                }
            }
        }
    }

    public static void updateEventStatus(){
        ArrayList<Event> allEvents = getAllEvent();

        for(Event event : allEvents) {
            if(event.endDateTime().isBefore(LocalDateTime.now()) && event.eventStatus() != EventStatus.COMPLETED){
                modifyEvent(event.eventID(), event.eventName(), event.venue(), event.description(), EventStatus.COMPLETED);
            } else if (event.endDateTime().isAfter(LocalDateTime.now()) && event.startDateTime().isBefore(LocalDateTime.now()) && event.eventStatus() != EventStatus.ONGOING) {
                modifyEvent(event.eventID(), event.eventName(), event.venue(), event.description(), EventStatus.ONGOING);
            }
        }
    }
    //endregion
}
