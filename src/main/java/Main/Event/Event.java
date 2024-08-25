package Main.Event;

import java.time.LocalDateTime;

enum EventStatus{
    UPCOMING,
    ONGOING,
    COMPLETED,
    CANCELLED
}

public class Event{
    private final String eventID;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private String venue;
    private int minVolunteerPax; // equals 0, no down limit
    private int maxVolunteerPax; // equals 0, no up limit
    private String description;
    private EventStatus eventStatus;
    
    private final String separator = "!,!";

    public Event(String eventID, LocalDateTime startDateTime, LocalDateTime endDateTime, String venue, int minVolunteerPax, int maxVolunteerPax, String description, EventStatus eventStatus) {
        this.eventID = eventID;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.venue = venue;
        this.minVolunteerPax = minVolunteerPax;
        this.maxVolunteerPax = maxVolunteerPax;
        this.description = description;
        this.eventStatus = eventStatus;
    }

    public Event(String event){
        String[] data = event.split(separator);

        eventID = data[0];
        startDateTime = LocalDateTime.parse(data[1]);
        endDateTime = LocalDateTime.parse(data[2]);
        venue = data[3];
        minVolunteerPax = Integer.parseInt(data[4]);
        maxVolunteerPax = Integer.parseInt(data[5]);
        description = data[6];
        eventStatus = EventStatus.valueOf(data[7]);
    }

    public String eventID() {
        return eventID;
    }

    public LocalDateTime startDateTime() {
        return startDateTime;
    }

    public Event setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
        return this;
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

    public Event setVenue(String venue) {
        this.venue = venue;
        return this;
    }

    public int minVolunteerPax() {
        return minVolunteerPax;
    }

    public void setMinVolunteerPax(int minVolunteerPax) {
        this.minVolunteerPax = minVolunteerPax;
    }

    public int maxVolunteerPax() {
        return maxVolunteerPax;
    }

    public void setMaxVolunteerPax(int maxVolunteerPax) {
        this.maxVolunteerPax = maxVolunteerPax;
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
        return eventID + separator + startDateTime + separator + endDateTime + separator + venue + separator + minVolunteerPax + separator + maxVolunteerPax + separator + description + separator + eventStatus;
    }
}

