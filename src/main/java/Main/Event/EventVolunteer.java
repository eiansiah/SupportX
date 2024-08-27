package Main.Event;

enum VolunteerStatus{
    REGISTERED,
    REMOVED
}

public class EventVolunteer{
    private final String eventID;
    private final String volunteerID;
    private VolunteerStatus volunteerStatus;

    public static final String separator = "!,!";

    public EventVolunteer(String eventID, String volunteerID, VolunteerStatus volunteerStatus) {
        this.eventID = eventID;
        this.volunteerID = volunteerID;
        this.volunteerStatus = volunteerStatus;
    }

    public EventVolunteer(String eventVolunteer) {
        String[] data = eventVolunteer.split(separator);

        eventID = data[0];
        volunteerID = data[1];
        volunteerStatus = VolunteerStatus.valueOf(data[2]);
    }

    public String eventID() {
        return eventID;
    }

    public String VolunteerID() {
        return volunteerID;
    }

    public VolunteerStatus volunteerStatus() {
        return volunteerStatus;
    }

    public void setVolunteerStatus(VolunteerStatus volunteerStatus) {
        this.volunteerStatus = volunteerStatus;
    }

    public String toString(){
        return eventID + separator + volunteerID + separator + volunteerStatus;
    }
}
