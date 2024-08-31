package Main.Event;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

import java.time.LocalDateTime;

public class EventDonor {
    private final String donorID;
    private final String eventID;
    private final LocalDateTime donorJoinTime;

    public EventDonor(String donorID, String eventID, LocalDateTime donorJoinTime) {
        this.donorID = donorID;
        this.eventID = eventID;
        this.donorJoinTime = donorJoinTime;
    }

    public String donorID() {
        return donorID;
    }

    public String eventID() {
        return eventID;
    }

    public LocalDateTime donorJoinTime() {
        return donorJoinTime;
    }
}
