package Main.Event;

enum VolunteerStatus{
    REGISTERED,
    REMOVED
}

public class EventVolunteer{
    private final String eventID;
    private final String VolunteerID;
    private VolunteerStatus volunteerStatus;

    public EventVolunteer(String eventID, String volunteerID, VolunteerStatus volunteerStatus) {
        this.eventID = eventID;
        VolunteerID = volunteerID;
        this.volunteerStatus = volunteerStatus;
    }

    public String eventID() {
        return eventID;
    }

    public String VolunteerID() {
        return VolunteerID;
    }

    public VolunteerStatus volunteerStatus() {
        return volunteerStatus;
    }

    public void setVolunteerStatus(VolunteerStatus volunteerStatus) {
        this.volunteerStatus = volunteerStatus;
    }
}
