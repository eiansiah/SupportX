package Boundary;

import Utilities.Color;
import Utilities.GeneralFunction;
import ADT.ListInterface;
import Entity.Event;
import Control.EventHandler;
import Entity.EventVolunteer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Scanner;

public class EventUI {

    public static String getSubSystemMenuAction() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter action: ");

        return sc.nextLine().trim();
    }

    public static String getEventInputUI(String msg){
        Scanner sc = new Scanner(System.in);

        System.out.print(msg);
        return sc.nextLine().trim();
    }

    public static void addNewEventUI(){
        GeneralFunction.clearScreen();

        GeneralFunction.printTitle("Add New Event", 40, "-", "|");
    }

    public static void removeEventUI(){
        ListInterface<Event> events = EventHandler.getAllEvent();
        events.sort(Comparator.comparing(Event::eventStatus));

        EventDisplay(events, "All events", 102);
    }

    public static void searchEventUI(){
        GeneralFunction.clearScreen();

        GeneralFunction.printTitle("Search Event with Name", 50, "-", "|");
    }

    public static void amendEventDetailsUI(){
        GeneralFunction.clearScreen();

        System.out.println(Color.YELLOW + "Disclaimer" + Color.RESET);
        System.out.println(Color.YELLOW + "1. Only upcoming event allow to modify" + Color.RESET);
        System.out.println(Color.YELLOW + "2. Time of the event are not allow to modify to avoid crashed schedule" + Color.RESET);
    }

    public static void listAllEvent(ListInterface<String> eventVolunteers){
        System.out.print("|");
        GeneralFunction.repeatPrint("-", 9);
        System.out.println("|");
        System.out.printf("| %5s   |\n", "ID");

        for (int i = 0; i < eventVolunteers.size(); i++) {
            String eventVolunteerID = eventVolunteers.get(i);

            System.out.println("| " + eventVolunteerID + " |");
        }

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 9);
        System.out.println("|");
    }

    public static void listVolunteerJoinedEvent(ListInterface<EventVolunteer> eventsJoined){
        GeneralFunction.printTitle("Event Joined", 42, "-", "|");

        for (int i = 0; i < eventsJoined.size(); i++) {
            EventVolunteer eventVolunteer = eventsJoined.get(i);

            Event event = EventHandler.searchEventByEventID(eventVolunteer.eventID());

            System.out.printf("| %6s %20s %10s |\n", event.eventID(), event.eventName(), event.eventStatus());
        }

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 40);
        System.out.println("|");
    }

    public static void generateReportsUI(int ongoingEvent, int upcomingEvent, int completeEvent, int allEvent){
        GeneralFunction.clearScreen();

        GeneralFunction.printTitle("Event Summary Report " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM-yyyy")), 50, "-", "|");

        System.out.printf("| %-46s |\n", "Number of ongoing events: " + ongoingEvent);
        System.out.printf("| %-46s |\n", "Number of upcoming events: " + upcomingEvent);
        System.out.printf("| %-46s |\n", "Total number of events has ended: " + completeEvent);
        System.out.printf("| %-46s |\n", "Total number of events had registered: " + allEvent);

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 48);
        System.out.println("|");


        GeneralFunction.enterToContinue();
    }

    public static void subsystemMenuDisplay(){
        GeneralFunction.clearScreen();

        GeneralFunction.printTitle("Event menu", 41, "-", "|");
        System.out.println("| 1. Add a new event                    |");
        System.out.println("| 2. Remove an event                    |");
        System.out.println("| 3. Search an event                    |");
        System.out.println("| 4. Amend an event details             |");
        System.out.println("| 5. List all events                    |");
        System.out.println("| 6. Remove an event from a volunteer   |");
        System.out.println("| 7. List all events for a volunteer    |");
        System.out.println("| 8. Generate summary reports           |");
        System.out.println("| 9. Exit subsystem                     |");
        System.out.print("|");
        GeneralFunction.repeatPrint("-", 39);
        System.out.println("|");
    }

    public static void EventDisplay(ListInterface<Event> events, String title, int width){
        GeneralFunction.printTitle(title, width, "-", "|");
        System.out.printf("| %6s %20s %16s %16s %25s %10s |\n", "ID", "Event Name", "Start Date Time", "End Date Time", "Venue","Status");

        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);

            System.out.print("|");
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.printf(" %6s %20s %16s %16s %25s %10s ", event.eventID(), event.eventName(), event.startDateTime().format(df), event.endDateTime().format(df), event.venue(), event.eventStatus());
            System.out.println("|");
        }

        System.out.print("|");
        GeneralFunction.repeatPrint("-", width - 2);
        System.out.println("|");
    }
}
