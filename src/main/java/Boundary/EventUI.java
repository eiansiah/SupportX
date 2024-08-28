package Boundary;

import Libraries.ArrayList;
import Libraries.Color;
import Libraries.Debug;
import Libraries.GeneralFunction;
import Main.Event.Event;
import Main.Event.EventHandler;
import Main.Event.EventStatus;
import Main.Event.EventVolunteer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import static Utilities.Message.displayGeneralErrorMsg;

public class EventUI {
    public static void main(String[] args) {
        subsystemMenu();
    }

    private static void subsystemMenu(){
        Scanner sc = new Scanner(System.in);

        root: {
            while(true) {
                GeneralFunction.clearScreen();

                subsystemMenuDisplay();

                int action = 0;
                while (true) {
                    System.out.print("Enter action: ");

                    String _action = sc.nextLine().trim();

                    if (_action.isEmpty()) {
                        displayGeneralErrorMsg("Input can't be empty");
                        continue;
                    }

                    try {
                        action = Integer.parseInt(_action);
                    } catch (NumberFormatException e) {
                        displayGeneralErrorMsg("Input is not a number");
                        continue;
                    }

                    if (action < 1 || action > 9) {
                        displayGeneralErrorMsg("Invalid action");
                        continue;
                    }

                    break;
                }

                switch (action) {
                    case 1:
                        addNewEventUI();
                        break;
                    case 2:
                        removeEventUI();
                        break;
                    case 3:
                        searchEventUI();
                        break;
                    case 4:
                        amendEventDetailsUI();
                        break;
                    case 5:
                        listAllEventUI();
                        break;
                    case 6:
                        removeEventFromVolunteerUI();
                        break;
                    case 7:
                        listAllEventForVolunteerUI();
                        break;
                    case 8:
                        generateReportsUI();
                        break;
                    case 9:
                        break root;
                }
            }
        }
    }

    private static void addNewEventUI(){
        GeneralFunction.clearScreen();

        GeneralFunction.printTitle("Add New Event", 40, "-", "|");

        String eventName = getEventName();
        LocalDateTime eventStartDateTime = getStartTimeInput();
        LocalDateTime eventEndDateTime = getEndTimeInput(eventStartDateTime);
        String venue = getVenueInput();
        String description = getDescription();

        EventHandler.addNewEvent(eventName, eventStartDateTime, eventEndDateTime, venue, description);

        System.out.println(Color.GREEN + "New event added" + Color.RESET);

        GeneralFunction.enterToContinue();
    }

    private static String getEventName(){
        Scanner sc = new Scanner(System.in);
        String eventName;

        while(true){
            System.out.print("Enter event name: ");
            eventName = sc.nextLine().trim();

            if (eventName.isEmpty()) {
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            return eventName;
        }
    }

    private static LocalDateTime getStartTimeInput(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime eventStartDateTime = null;
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.print("Enter start time (format: dd-MM-yyyy HH:mm): ");
            try{
                eventStartDateTime = LocalDateTime.parse(sc.nextLine(), dtf);
            }catch(DateTimeParseException e){
                displayGeneralErrorMsg("Invalid format of start time. Please try again.");
                continue;
            }

            if(eventStartDateTime.isBefore(LocalDateTime.now())){
                displayGeneralErrorMsg("Start time is before current time. Please try again.");
                continue;
            }

            return eventStartDateTime;
        }
    }

    private static LocalDateTime getEndTimeInput(LocalDateTime startDateTime){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime eventEndDateTime = null;
        Scanner sc = new Scanner(System.in);

        while(true){
            System.out.print("Enter end time (format: dd-MM-yyyy HH:mm): ");
            try{
                eventEndDateTime = LocalDateTime.parse(sc.nextLine(), dtf);
            }catch(DateTimeParseException e){
                displayGeneralErrorMsg("Invalid format of end time. Please try again.");
                continue;
            }

            if(eventEndDateTime.isBefore(startDateTime)){
                displayGeneralErrorMsg("End time is before start time. Please try again.");
                continue;
            }

            return eventEndDateTime;
        }
    }

    private static String getVenueInput(){
        Scanner sc = new Scanner(System.in);
        String venue;

        while(true){
            System.out.print("Enter venue: ");
            venue = sc.nextLine();

            if(venue.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            return venue;
        }
    }

    private static String getDescription(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter description: ");

        return sc.nextLine();
    }

    private static void removeEventUI(){
        EventDisplay(EventHandler.getAllEvent(), "All events", 102);

        Scanner sc = new Scanner(System.in);
        Event eventChosen;

        while(true){
            System.out.print("Enter event ID: ");
            String eventID = sc.nextLine();
            eventChosen = EventHandler.searchEventByEventID(eventID);

            if(eventChosen == null){
                displayGeneralErrorMsg("EventID does not exist. Please try again.");
                continue;
            }

            break;
        }

        EventHandler.removeEvent(eventChosen);
        System.out.println(Color.GREEN + "Removed event" + Color.RESET);
        GeneralFunction.enterToContinue();
    }

    private static void searchEventUI(){
        GeneralFunction.clearScreen();

        Scanner sc = new Scanner(System.in);

        GeneralFunction.printTitle("Search Event with Name", 50, "-", "|");

        while(true){
            System.out.print("Enter event name (x to stop): ");

            String eventName = sc.nextLine();

            if(eventName.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            if(eventName.equals("x")){
                break;
            }

            ArrayList<Event> events = EventHandler.searchAllEventByEventName(eventName);

            if(events == null || events.isEmpty()){
                displayGeneralErrorMsg("Event name does not exist. Please try again.");
                continue;
            }

            EventDisplay(events, "All events", 102);
        }
    }

    private static void amendEventDetailsUI(){
        GeneralFunction.clearScreen();

        System.out.println(Color.YELLOW + "Disclaimer" + Color.RESET);
        System.out.println(Color.YELLOW + "1. Only upcoming event allow to modify" + Color.RESET);
        System.out.println(Color.YELLOW + "2. Time of the event are not allow to modify to avoid crashed schedule" + Color.RESET);

        ArrayList<Event> events = EventHandler.searchAllEventByEventStatus(EventStatus.UPCOMING);

        if(events == null){
            displayGeneralErrorMsg("No upcoming event to modify.");
            GeneralFunction.enterToContinue();
            return;
        }

        EventDisplay(events, "All Upcoming Events", 102);

        Scanner sc = new Scanner(System.in);
        String eventID;

        root: {
            while(true) {
                System.out.print("Enter event ID: ");
                eventID = sc.nextLine();

                for (Event event : events) {
                    if (event.eventID().equals(eventID)) {
                        break root;
                    }
                }

                displayGeneralErrorMsg("Event ID does not exist. Please try again.");
            }
        }

        String eventName = getEventName();
        String venue = getVenueInput();
        String description = getDescription();

        EventHandler.modifyEvent(eventID, eventName, venue, description, EventStatus.UPCOMING);

        System.out.println(Color.GREEN + "Event modified" + Color.RESET);
        GeneralFunction.enterToContinue();
    }

    private static void listAllEventUI(){
        GeneralFunction.clearScreen();

        ArrayList<Event> allEvents = EventHandler.getAllEvent();

        EventDisplay(allEvents, "All events", 102);

        GeneralFunction.enterToContinue();
    }

    private static void removeEventFromVolunteerUI(){
        ArrayList <EventVolunteer> eventsJoined = listAllEventForVolunteerUI();
        Scanner sc = new Scanner(System.in);

        if(eventsJoined == null){
            GeneralFunction.enterToContinue();
            return;
        }

        while(true){
            System.out.print("Enter event ID to opt out event: ");
            String eventID = sc.nextLine();

            if(eventID.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            for (EventVolunteer eventVolunteer: eventsJoined){
                if(eventVolunteer.eventID().equals(eventID)){
                    EventHandler.removeVolunteerFromVolunteerEvent(eventID, eventVolunteer.VolunteerID());
                    System.out.println(Color.GREEN + "Remove successfully" + Color.RESET);

                    GeneralFunction.enterToContinue();
                    return;
                }
            }

            displayGeneralErrorMsg("Event ID does not exist. Please try again.");
        }
    }

    private static ArrayList <EventVolunteer> listAllEventForVolunteerUI(){
        GeneralFunction.clearScreen();

        ArrayList<String> eventVolunteers = EventHandler.getAllEventVolunteerID();

        if(eventVolunteers.isEmpty()){
            displayGeneralErrorMsg("Don't have any volunteer joining any event. ");
            GeneralFunction.enterToContinue();
            return null;
        }

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 9);
        System.out.println("|");
        System.out.printf("| %5s   |\n", "ID");

        for(String eventVolunteerID : eventVolunteers){
            System.out.println("| " + eventVolunteerID + " |");
        }

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 9);
        System.out.println("|");

        Scanner sc = new Scanner(System.in);
        String volunteerID;
        while (true){
            System.out.print("Enter volunteerID to view event list: ");
            volunteerID = sc.nextLine();

            if(volunteerID.isEmpty()){
                displayGeneralErrorMsg("Input can't be empty");
                continue;
            }

            if(!eventVolunteers.contains(volunteerID)){
                displayGeneralErrorMsg("Volunteer does not exist. Please try again.");
                continue;
            }

            break;
        }

        ArrayList <EventVolunteer> eventsJoined = EventHandler.getEventVolunteerJoined(volunteerID);

        if(eventsJoined == null){
            displayGeneralErrorMsg(Color.RED + "Volunteer doesn't join any event");
            GeneralFunction.enterToContinue();

            return null;
        }

        GeneralFunction.printTitle("Event Joined", 42, "-", "|");
        for(EventVolunteer eventVolunteer: eventsJoined){
            Event event = EventHandler.searchEventByEventID(eventVolunteer.eventID());

            System.out.printf("| %6s %20s %10s |\n", event.eventID(), event.eventName(), event.eventStatus());
        }

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 40);
        System.out.println("|");

        return eventsJoined;
    }

    private static void generateReportsUI(){
        //TODO: how much event ongoing this month
        //TODO: how much event Upcoming this month

        GeneralFunction.clearScreen();

        GeneralFunction.printTitle("Event Summary Report " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM-yyyy")), 50, "-", "|");

        ArrayList<Event> allEvent = EventHandler.getAllEvent();
        ArrayList<Event> ongoingEvent = new ArrayList<>();
        ArrayList<Event> upcomingEvent = new ArrayList<>();
        ArrayList<Event> completeEvent = new ArrayList<>();

        for(Event event : allEvent){
            if(event.eventStatus() == EventStatus.UPCOMING){
                upcomingEvent.add(event);
            } else if (event.eventStatus() == EventStatus.ONGOING){
                ongoingEvent.add(event);
            } else if (event.eventStatus() == EventStatus.COMPLETED){
                completeEvent.add(event);
            }
        }

        System.out.printf("| %-46s |\n", "Number of ongoing events: " + ongoingEvent.size());
        System.out.printf("| %-46s |\n", "Number of upcoming events: " + upcomingEvent.size());
        System.out.printf("| %-46s |\n", "Total number of events has ended: " + completeEvent.size());
        System.out.printf("| %-46s |\n", "Total number of events had registered: " + allEvent.size());

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 48);
        System.out.println("|");


        GeneralFunction.enterToContinue();
    }

    private static void subsystemMenuDisplay(){
        GeneralFunction.printTitle("Event menu", 41, "-", "|");
        System.out.println("| 1. Add a new event                    |");
        System.out.println("| 2. Remove an event                    |");
        System.out.println("| 3. Search an event                    |");
        System.out.println("| 4. Amend an event details             |");
        System.out.println("| 5. List all events                    |");
        System.out.println("| 6. Remove an event from a volunteer   |");
        System.out.println("| 7. List all events for a volunteer    |");
        System.out.println("| 8. Generate summary reports           |");
        System.out.print("|");
        GeneralFunction.repeatPrint("-", 39);
        System.out.println("|");
    }

    private static void EventDisplay(ArrayList<Event> events, String title, int width){
        GeneralFunction.printTitle(title, width, "-", "|");
        System.out.printf("| %6s %20s %16s %16s %25s %10s |\n", "ID", "Event Name", "Start Date Time", "End Date Time", "Venue","Status");

        for(Event event: events){
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
