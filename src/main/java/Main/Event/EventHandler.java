package Main.Event;

import FileHandling.UniversalFileHandler;
import Libraries.ArrayList;
import Utilities.Search;

import java.time.LocalDateTime;
import java.util.Random;

public class EventHandler {

    private final static String eventFilePath = "event.txt";
    private final static String eventVolunteerFilePath = "eventVolunteer.txt";

    public static Event addNewEvent(LocalDateTime _startDateTime, LocalDateTime _endDateTime, String _venue, /*int _minVolunteerPax, int _maxVolunteerPax,*/ String _description){

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
    }

    public static Event searchEventByEventID(String eventID){
        String eventString = Search.searchFirstMatchesStringFromFile(eventFilePath, eventID, Event.separator, 0);

        if(eventString == null) {
            return null;
        }

        return new Event(eventString);
    }

    public static ArrayList<Event> searchAllEventByEventStatus(EventStatus eventStatus){
        ArrayList<String> eventListString = Search.searchAllMatchesString(eventFilePath, eventStatus.toString(), Event.separator, 5);
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

    public static void modifyEvent(String eventID, LocalDateTime _startDateTime, LocalDateTime _endDateTime, String _venue, /*int _minVolunteerPax, int _maxVolunteerPax,*/ String _description, EventStatus eventStatus){
        Event eventOld = searchEventByEventID(eventID);
        Event eventNew = new Event(eventID, _startDateTime, _endDateTime, _venue, /*_minVolunteerPax, _maxVolunteerPax,*/ _description, eventStatus);

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
