package Main.Event;

import FileHandling.UniversalFileHandler;
import Libraries.ArrayList;
import Utilities.Search;

import java.time.LocalDateTime;
import java.util.Random;

public class EventHandler {

    private final static String eventFilePath = "event.txt";

    public static Event addNewEvent(LocalDateTime _startDateTime, LocalDateTime _endDateTime, String _venue, int _minVolunteerPax, int _maxVolunteerPax, String _description){

        if(_endDateTime.isBefore(_startDateTime)) {
            return null;
        }

        if (_minVolunteerPax < 0 || _maxVolunteerPax < 0 ) {
            return null;
        }

        if(_venue.isEmpty()) {
            return null;
        }

        Event newEvent = new Event(
                generateEventID(),
                _startDateTime,
                _endDateTime,
                _venue,
                _minVolunteerPax,
                _maxVolunteerPax,
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
        ArrayList<String> eventListString = Search.searchAllMatchesString(eventFilePath, eventStatus.name(), Event.separator, 7);
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

    private static String generateEventID(){
        return new Random().nextInt(1000) + "";
    }

    //TODO: Amend an event details
    //TODO: Remove an event from a volunteer
    //TODO: List all events for a volunteer
    //TODO: Generate summary reports
}
