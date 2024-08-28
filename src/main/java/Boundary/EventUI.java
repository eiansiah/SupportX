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
                        GeneralFunction.enterToContinue();
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
        System.out.println("Add new event");

        String eventName = getEventName();
        LocalDateTime eventStartDateTime = getStartTimeInput();
        LocalDateTime eventEndDateTime = getEndTimeInput(eventStartDateTime);
        String venue = getVenueInput();
        String description = getDescription();

        EventHandler.addNewEvent(eventName, eventStartDateTime, eventEndDateTime, venue, description);

        System.out.println(Color.GREEN + "New event added" + Color.RESET);
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
        System.out.println("Event list");

        for(Event event: EventHandler.getAllEvent()){
            System.out.println(event.eventID() + " " + event.startDateTime() + " " + event.endDateTime() + " " + event.venue() + " " + event.eventStatus());
        }

        GeneralFunction.repeatPrint("-", 10);
        System.out.println();

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
    }

    private static void searchEventUI(){
        Scanner sc = new Scanner(System.in);

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

            for(Event event: events){
                System.out.println(event.eventID() + " " + event.eventName() + " " + event.startDateTime() + " " + event.endDateTime() + " " + event.eventStatus());
            }

            GeneralFunction.enterToContinue();
        }
    }

    private static void amendEventDetailsUI(){
        System.out.println(Color.YELLOW + "Disclaimer" + Color.RESET);
        System.out.println(Color.YELLOW + "1. Only upcoming event allow to modify" + Color.RESET);
        System.out.println(Color.YELLOW + "2. Time of the event are not allow to modify to avoid crashed schedule" + Color.RESET);

        ArrayList<Event> events = EventHandler.searchAllEventByEventStatus(EventStatus.UPCOMING);

        for(Event event: events){
            System.out.println(event.eventID() + " " + event.eventName() + " " + event.startDateTime() + " " + event.endDateTime() + " " + event.venue() + " " + event.description());
        }

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
    }

    private static void listAllEventUI(){
        ArrayList<Event> allEvents = EventHandler.getAllEvent();

        System.out.println("All events");
        GeneralFunction.repeatPrint("-", 10);

        for(Event event: allEvents){
            System.out.println(event.eventID() + event.eventName() + " " + event.startDateTime() + " " + event.endDateTime() + " " + event.venue() + event.description() + " " + event.eventStatus());
        }

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
                    System.out.println(Color.GREEN + "Remove successfully");
                    return;
                }
            }

            displayGeneralErrorMsg("Event ID does not exist. Please try again.");
        }
    }

    private static ArrayList <EventVolunteer> listAllEventForVolunteerUI(){
        ArrayList<String> eventVolunteers = EventHandler.getAllEventVolunteerID();

        if(eventVolunteers.isEmpty()){
            displayGeneralErrorMsg("Don't have any volunteer joining any event. ");
            return null;
        }

        for(String eventVolunteerID : eventVolunteers){
            System.out.println(eventVolunteerID);
        }

        Scanner sc = new Scanner(System.in);
        String volunteerID;
        while (true){
            System.out.print("Enter volunteerID: ");
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

        System.out.println("Volunteer joins:");
        for(EventVolunteer eventVolunteer: eventsJoined){
            Event event = EventHandler.searchEventByEventID(eventVolunteer.eventID());

            System.out.println(event.eventID()+ " " + event.eventName() + " " + event.eventStatus());
        }

        return eventsJoined;
    }

    private static void generateReportsUI(){

    }

    private static void subsystemMenuDisplay(){
        System.out.println(Color.BLUE + "Event Menu" + Color.RESET);
        System.out.println("----------------------------");
        System.out.println("1. Add a new event");
        System.out.println("2. Remove an event");
        System.out.println("3. Search an event");
        System.out.println("4. Amend an event details");
        System.out.println("5. List all events");
        System.out.println("6. Remove an event from a volunteer");
        System.out.println("7. List all events for a volunteer");
        System.out.println("8. Generate summary reports");
    }
}
