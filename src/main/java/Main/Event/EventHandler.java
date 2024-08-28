package Main.Event;

import FileHandling.UniversalFileHandler;
import Libraries.ArrayList;
import Utilities.Search;

import java.time.LocalDateTime;
import java.util.Random;

public class EventHandler {

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
        return new Random().nextInt(1000) + "";
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

    //TODO: Generate summary reports
}
