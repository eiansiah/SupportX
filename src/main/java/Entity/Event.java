package Entity;

/*
*  author: Saw Khoo Zi Chong
*  ID: 2307609
* */

import java.time.LocalDateTime;

public class Event{
    private final String eventID;
    private String eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String venue;
    private String description;
    private EventStatus eventStatus;
    
    public static final String separator = "!,!";

    public Event(String eventID, String eventName, LocalDateTime startDateTime, LocalDateTime endDateTime, String venue, String description, EventStatus eventStatus) {
        this.eventID = eventID;
        this.eventName = eventName;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.venue = venue;
        this.description = description;
        this.eventStatus = eventStatus;
    }

    public Event(String event){
        String[] data = event.split(separator);

        eventID = data[0];
        eventName = data[1];
        startDateTime = LocalDateTime.parse(data[2]);
        endDateTime = LocalDateTime.parse(data[3]);
        venue = data[4];
        description = data[5];
        eventStatus = EventStatus.valueOf(data[6]);
    }

    public String eventID() {
        return eventID;
    }

    public String eventName() { return eventName; }

    public void setEventName(String eventName) { this.eventName = eventName; }

    public LocalDateTime startDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime endDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public String venue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String description() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public EventStatus eventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public String toString() {
        return eventID + separator + eventName + separator + startDateTime + separator + endDateTime + separator + venue + separator + description + separator + eventStatus;
    }
}

