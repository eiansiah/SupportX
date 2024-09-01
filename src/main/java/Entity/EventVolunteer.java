package Entity;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

public class EventVolunteer{
    private final String eventID;
    private final String volunteerID;

    public static final String separator = "!,!";

    public EventVolunteer(String eventID, String volunteerID) {
        this.eventID = eventID;
        this.volunteerID = volunteerID;
    }

    public EventVolunteer(String eventVolunteer) {
        String[] data = eventVolunteer.split(separator);

        eventID = data[0];
        volunteerID = data[1];
    }

    public String eventID() {
        return eventID;
    }

    public String VolunteerID() {
        return volunteerID;
    }

    public String toString(){
        return eventID + separator + volunteerID;
    }
}
