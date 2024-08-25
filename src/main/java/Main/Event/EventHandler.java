package Main.Event;

import FileHandling.UniversalFileHandler;

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

    private static String generateEventID(){
        return new Random().nextInt(1000) + "";
    }
}
