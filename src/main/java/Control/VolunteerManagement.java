/*
 *  Author: Tan Qian Yiin
 *  ID: 2307616
 *
 * */

package Control;

import Boundary.VolunteerUI;
import Entity.Volunteer;
import DAO.VolunteerFileHandler;
import ADT.ArrayList;
import Utilities.Color;
import ADT.Hashmap;
import ADT.ListInterface;
import Entity.Event;
import Entity.EventStatus;
import Entity.EventVolunteer;

import java.util.Scanner;

public class VolunteerManagement {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "volunteers.txt";
    private static final VolunteerFileHandler fileHandler = new VolunteerFileHandler();
    private static ListInterface<Volunteer> volunteers = new ArrayList<>();

    public static void main(String[] args) {
        volunteers = fileHandler.readData(FILE_NAME);
        VolunteerUI.mainMenu();
    }

    private static void handle() {
        while (true) {
            try {
                System.out.println(Color.BLUE + "\nVolunteer Management System" + Color.RESET);
                System.out.println("----------------------------");
                System.out.println("1. Add Volunteer");
                System.out.println("2. Delete Volunteer");
                System.out.println("3. Modify Volunteer");
                System.out.println("4. Search Volunteer");
                System.out.println("5. Assign Volunteer");
                System.out.println("6. List All Volunteers");
                System.out.println("7. List of Volunteers and Events");
                System.out.println("8. Filter Volunteers");
                System.out.println("9. Search Events by Volunteer");
                System.out.println("10. View Summary Reports");
                System.out.println("11. Back");
                System.out.println(" ");
                System.out.print("Enter choice: ");

                String input = scanner.nextLine().trim();

                if (input.isEmpty()) {
                    System.out.println(Color.RED + "Input cannot be empty. Please try again." + Color.RESET);
                    continue;
                }

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println(Color.RED + "Invalid input. Please enter a number." + Color.RESET);
                    continue;
                }

                switch (choice) {
                    case 1:
                        addVolunteer();
                        break;
                    case 2:
                        removeVolunteer();
                        break;
                    case 3:
                        modifyVolunteer();
                        break;
                    case 4:
                        searchVolunteer();
                        break;
                    case 5:
                        assignVolunteer();
                        break;
                    case 6:
                        listVolunteers();
                        break;
                    case 7:
                        listVolunteerEvents();
                        break;
                    case 8:
                        filterVolunteers();
                        break;
                    case 9:
                        searchEventsByVolunteer();
                        break;
                    case 10:
                        viewSummaryReport();
                        break;
                    case 11:
                        return;
                    default:
                        System.out.println(Color.RED + "Invalid choice. Please try again." + Color.RESET);
                }
            } catch (Exception e) {
                System.out.println(Color.RED + "An error occurred. Please try again!!!" + Color.RESET);
                scanner.nextLine();
            }
        }
    }

    private static void assignVolunteer() {
        ListInterface<Event> upcomingEvents = EventHandler.searchAllEventByEventStatus(EventStatus.UPCOMING);

        //Display
        if (upcomingEvents == null || upcomingEvents.isEmpty()) {
            System.out.println("No upcoming event. ");
            return;
        }

        for (int i = 0; i < upcomingEvents.size(); i++) {
            Event event = upcomingEvents.get(i);

            System.out.println(event.eventID() + " " + event.startDateTime() + " " + event.endDateTime() + " " + event.venue());
        }

        Event eventChosen;
        while (true) {
            System.out.print("Enter event ID: ");
            String eventID = scanner.nextLine().trim();

            Event event = EventHandler.searchEventByEventID(eventID);

            if (event == null) {
                System.out.println(Color.RED + "Invalid event ID. Please try again." + Color.RESET);
            } else {
                eventChosen = event;
                break;
            }
        }

        //Display
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);
            System.out.println(volunteer.toString());
        }

        String volunteerID;
        while (true) {
            System.out.print("Enter volunteer ID: ");
            volunteerID = scanner.nextLine().trim();

            boolean idExist = false;

            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.get(i);

                if (volunteer.getId().equals(volunteerID)) {
                    idExist = true;
                    break;
                }
            }

            if (!idExist) {
                System.out.println(Color.RED + "Volunteer ID not exist. Please try again." + Color.RESET);
                continue;
            }

            ListInterface <EventVolunteer> whatEventVolunteerJoined = EventHandler.getEventVolunteerJoined(volunteerID);
            boolean crashed = false;
            if (whatEventVolunteerJoined != null) {
                for (int i = 0; i < whatEventVolunteerJoined.size(); i++) {
                    EventVolunteer eventVolunteer = whatEventVolunteerJoined.get(i);

                    Event event = EventHandler.searchEventByEventID(eventVolunteer.eventID());

                    if (!(eventChosen.endDateTime().isBefore(event.startDateTime()) || eventChosen.startDateTime().isAfter(event.endDateTime()))) {
                        // Schedule crash
                        System.out.println(Color.RED + "The event selected crashed with volunteer schedule " + event.eventID() + ". Please try again." + Color.RESET);
                        crashed = true;
                        break;
                    }
                }
            }

            if (crashed) {
                continue;
            }

            EventHandler.addEventVolunteer(eventChosen.eventID(), volunteerID);
            System.out.println(volunteerID + " has been assigned to " + eventChosen.eventID());
            break;
        }
    }

    private static void searchEventsByVolunteer() {
        //TODO: refer to EventHandler.java getEventVolunteerJoined?
        System.out.print("Enter Volunteer ID to search: ");
        String volunteerID = scanner.nextLine().trim();

        Volunteer volunteerToSearch = null;

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getId().equals(volunteerID)) {
                volunteerToSearch = volunteer;
                break;
            }
        }

        if (volunteerToSearch != null) {
            System.out.println("\nVolunteer Details");
            System.out.println("-------------------");
            System.out.println("ID: " + volunteerToSearch.getId());
            System.out.println("Name: " + volunteerToSearch.getName());
            System.out.println("Availability: " + volunteerToSearch.getAvailability());

            // Retrieve events associated with this volunteer
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteerID);

            ListInterface<Event> events = new ArrayList<>();

            for (int i = 0; i < eventVolunteers.size(); i++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(i);
                events.add(EventHandler.searchEventByEventID(eventVolunteer.eventID()));
            }

            if (eventVolunteers != null && !eventVolunteers.isEmpty()) {
                System.out.println("\nEvents Joined by Volunteer");
                System.out.println("---------------------------");

                for (int i = 0; i < events.size(); i++) {
                    Event event = events.get(i);

                    System.out.println("Event ID: " + event.eventID());
                    System.out.println("Event Name: " + event.description());
                    System.out.println("Date Joined: " + event.startDateTime());
                    System.out.println("---------------------------");
                }
            } else {
                System.out.println(Color.YELLOW + "No events found for this volunteer." + Color.RESET);
            }
        } else {
            System.out.println(Color.YELLOW + "Volunteer does not exist." + Color.RESET);
        }

    }

    private static void addVolunteer() {
        System.out.println("\nEnter Volunteer details");
        System.out.println("-------------------------");

        String lastId = fileHandler.getLastVolunteerId(FILE_NAME);
        String newVolunteerId = fileHandler.incrementVolunteerId(lastId);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(newVolunteerId);

        String name;
        do {
            System.out.print("Enter your name: ");
            name = scanner.nextLine();
            if (!isValidInput(name)) {
                System.out.println(Color.RED + "Sorry! Empty field! Please enter a valid name." + Color.RESET);
            } else if (!name.matches("[a-zA-Z\\s-]+")) {
                System.out.println(Color.RED + "Sorry! Invalid name. Please enter a valid name containing only letters." + Color.RESET);
            } else {
                volunteer.setName(name);
                break;
            }
        } while (true);

        while (true) {
            System.out.print("Enter age: ");
            String ageInput = scanner.nextLine().trim();

            try {
                int age = Integer.parseInt(ageInput);
                if (age > 0) {
                    volunteer.setAge(ageInput);
                    break;
                } else {
                    System.out.println(Color.RED + "Age must be a positive number. Please try again." + Color.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Invalid input for age. Please enter a valid number." + Color.RESET);
            }
        }

        String gender;
        do {
            System.out.print("Enter gender (Male, Female, Other): ");
            gender = scanner.nextLine().trim();
            if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other"))) {
                System.out.println(Color.RED + "Invalid gender. Please enter 'Male', 'Female', or 'Other'.");
            } else {
                volunteer.setGender(gender);
                break;
            }
        } while (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other")));

        String phone;
        do {
            System.out.print("Enter contact number: ");
            phone = scanner.nextLine().trim();
            if (!isValidContactNumber(phone)) {
                System.out.println(Color.RED + "Invalid contact number. Please try again." + Color.RESET);
            } else {
                volunteer.setPhone(phone);
                break;
            }
        } while (!isValidContactNumber(phone));

        String email;
        do {
            System.out.print("Enter your email: ");
            email = scanner.nextLine().trim();
            if (!isValidInput(email)) {
                System.out.println(Color.RED + "Empty field! Please enter valid data." + Color.RESET);
            } else if (!isValidEmail(email)) {
                System.out.println(Color.RED + "Invalid email format. Please try again." + Color.RESET);
            } else {
                volunteer.setEmail(email);
                break;
            }
        } while (!isValidEmail(email) || !isValidInput(email));

        String availability;
        do {
            System.out.print("Enter availability (Weekdays, Weekends): ");
            availability = scanner.nextLine().trim();
            if (availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends")) {
                volunteer.setAvailability(availability);
                break;
            } else {
                System.out.println(Color.RED + "Invalid availability. Please enter 'Weekdays' or 'Weekends'." + Color.RESET);
            }
        } while (!(availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends")));

        fileHandler.saveData(FILE_NAME, volunteer);
        volunteers.add(volunteer);

        System.out.println(Color.GREEN + "Volunteer added successfully." + Color.RESET);
    }

    private static void removeVolunteer() {
        System.out.println("Removing Volunteer...");

        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers to delete." + Color.RESET);
            return;
        }

        System.out.println("List of Volunteers:");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }

        while (true) {
            System.out.print("Enter Volunteer ID to delete: ");
            String volunteerID = scanner.nextLine().trim();

            Volunteer volunteerToRemove = null;

            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.get(i);

                if (volunteer.getId().equals(volunteerID)) {
                    volunteerToRemove = volunteer;
                    break;
                }
            }

            if (volunteerToRemove != null) {
                volunteers.remove(volunteerToRemove);
                fileHandler.updateMultipleData(FILE_NAME, (ArrayList<Volunteer>) volunteers);

                EventHandler.removeVolunteerFromAllVolunteerEvent(volunteerToRemove.getId());

                System.out.println(Color.GREEN + "Volunteer deleted successfully." + Color.RESET);
                break;
            } else {
                System.out.println(Color.YELLOW + "Volunteer does not exist. Please enter a valid ID." + Color.RESET);
            }
        }
    }

    private static void modifyVolunteer() {
        System.out.println("Modify Volunteer");
        System.out.println("-------------------");

        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers to modify." + Color.RESET);
            return;
        }

        System.out.println("List of Volunteers:");
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }

        while (true) {
            System.out.print("Enter Volunteer ID to modify: ");
            String volunteerID = scanner.nextLine().trim();

            Volunteer volunteerToModify = null;
            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.get(i);

                if (volunteer.getId().equals(volunteerID)) {
                    volunteerToModify = volunteer;
                    break;
                }
            }

            if (volunteerToModify != null) {
                while (true) {
                    System.out.println("Select data to modify:");
                    System.out.println("1. Name");
                    System.out.println("2. Age");
                    System.out.println("3. Gender");
                    System.out.println("4. Phone");
                    System.out.println("5. Email");
                    System.out.println("6. Availability");
                    System.out.println("7. Back");
                    System.out.println(" ");
                    System.out.print("Enter choice: ");

                    int modifyChoice = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (modifyChoice) {
                        case 1:
                            modifyName(volunteerToModify);
                            break;
                        case 2:
                            modifyAge(volunteerToModify);
                            break;
                        case 3:
                            modifyGender(volunteerToModify);
                            break;
                        case 4:
                            modifyPhone(volunteerToModify);
                            break;
                        case 5:
                            modifyEmail(volunteerToModify);
                            break;
                        case 6:
                            modifyAvailability(volunteerToModify);
                            break;
                        case 7:
                            fileHandler.updateData(FILE_NAME, volunteerToModify);
                            System.out.println(Color.GREEN + "Volunteer details updated successfully." + Color.RESET);
                            return;
                        default:
                            System.out.println(Color.RED + "Invalid choice. Please try again." + Color.RESET);
                    }
                }
            } else {
                System.out.println(Color.YELLOW + "Volunteer does not exist. Please enter a valid ID." + Color.RESET);
            }
        }
    }

    private static void modifyName(Volunteer volunteer) {
        System.out.print("Enter new name (leave blank to keep current): ");
        String name = scanner.nextLine().trim();
        System.out.println(" ");
        if (!name.isEmpty() && name.matches("[a-zA-Z\\s-]+")) {
            volunteer.setName(name);
        } else if (!name.isEmpty()) {
            System.out.println(Color.RED + "Invalid name. Please enter a valid name containing only letters." + Color.RESET);
        }
    }

    private static void modifyAge(Volunteer volunteer) {
        while (true) {
            System.out.print("Enter new age (leave blank to keep current): ");
            String ageInput = scanner.nextLine().trim();
            System.out.println(" ");

            if (ageInput.isEmpty()) {
                break;
            }

            try {
                int age = Integer.parseInt(ageInput);
                System.out.println(" ");
                if (age > 0) {
                    volunteer.setAge(ageInput);
                    break;
                } else {
                    System.out.println(Color.RED + "Age must be a positive number. Please try again." + Color.RESET);
                }
            } catch (NumberFormatException e) {
                System.out.println(Color.RED + "Invalid input for age. Please enter a valid number." + Color.RESET);
            }
        }
    }

    private static void modifyGender(Volunteer volunteer) {
        while (true) {
            System.out.print("Enter new gender (Male, Female, Other) (leave blank to keep current): ");
            System.out.println(" ");
            String gender = scanner.nextLine().trim();
            if (gender.isEmpty()) {
                break;
            }
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other")) {
                volunteer.setGender(gender);
                break;
            } else {
                System.out.println(Color.RED + "Invalid gender. Please enter 'Male', 'Female', or 'Other'." + Color.RESET);
            }
        }
    }

    private static void modifyPhone(Volunteer volunteer) {
        while (true) {
            System.out.print("Enter new contact number (leave blank to keep current): ");
            System.out.println(" ");
            String phone = scanner.nextLine().trim();
            if (phone.isEmpty()) {
                break;
            }
            if (isValidContactNumber(phone)) {
                volunteer.setPhone(phone);
                break;
            } else {
                System.out.println(Color.RED + "Invalid contact number. Please try again." + Color.RESET);
            }
        }
    }

    private static void modifyEmail(Volunteer volunteer) {
        while (true) {
            System.out.print("Enter new email (leave blank to keep current): ");
            System.out.println(" ");
            String email = scanner.nextLine().trim();
            if (email.isEmpty()) {
                break;
            }
            if (isValidEmail(email)) {
                volunteer.setEmail(email);
                break;
            } else {
                System.out.println(Color.RED + "Invalid email format. Please try again." + Color.RESET);
            }
        }
    }

    private static void modifyAvailability(Volunteer volunteer) {
        while (true) {
            System.out.print("Enter new availability (Weekdays, Weekends) (leave blank to keep current): ");
            String availability = scanner.nextLine().trim();
            if (availability.isEmpty()) {
                break;
            }
            if (availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends")) {
                volunteer.setAvailability(availability);
                break;
            } else {
                System.out.println(Color.RED + "Invalid availability. Please enter 'Weekdays' or 'Weekends'." + Color.RESET);
            }
        }
    }

    private static void searchVolunteer() {
        System.out.print("Enter Volunteer ID to search: ");
        String volunteerID = scanner.nextLine().trim();

        Volunteer volunteerToSearch = null;

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getId().equals(volunteerID)) {
                volunteerToSearch = volunteer;
                break;
            }
        }

        if (volunteerToSearch != null) {
            System.out.println("\nVolunteer Details");
            System.out.println("-------------------");
            System.out.println("ID: " + volunteerToSearch.getId());
            System.out.println("Name: " + volunteerToSearch.getName());
            System.out.println("Age: " + volunteerToSearch.getAge());
            System.out.println("Gender: " + volunteerToSearch.getGender());
            System.out.println("Phone: " + volunteerToSearch.getPhone());
            System.out.println("Email: " + volunteerToSearch.getEmail());
            System.out.println("Availability: " + volunteerToSearch.getAvailability());
        } else {
            System.out.println(Color.YELLOW + "Volunteer does not exist." + Color.RESET);
        }
    }

    private static void listVolunteerEvents() {
        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers available." + Color.RESET);
            return;
        }

        System.out.println(" ");
        System.out.println("Volunteers and Their Events");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n", "Volunteer ID", "Volunteer Name", "Event ID", "Event Description", "Event Venue", "Event Start Date & Time", "Event End Date & Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");

        for (int j = 0; j < volunteers.size(); j++) {

            Volunteer volunteer = volunteers.get(j);
            // Get the list of events the volunteer has participated in
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteer.getId());
            ListInterface<Event> events = new ArrayList<>();

            for (int i = 0; i < eventVolunteers.size(); i++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(i);

                events.add(EventHandler.searchEventByEventID(eventVolunteer.eventID()));
            }

            // If the volunteer has participated in events, list them
            if (eventVolunteers != null && !eventVolunteers.isEmpty()) {

                for (int i = 0; i < events.size(); i++) {
                    Event event = events.get(i);

                    System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                            volunteer.getId(),
                            volunteer.getName(),
                            event.eventID(),
                            event.description(),
                            event.venue(),
                            event.startDateTime(),
                            event.endDateTime());
                }
            } else {
                // If the volunteer hasn't participated in any events, indicate so
                System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        "N/A",
                        "No events participated",
                        "N/A",
                        "N/A",
                        "N/A");
            }
        }

        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void viewSummaryReport() {

        // Check if there are any volunteers available
        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers available." + Color.RESET);
            return;
        }

        // Create a HashMap to store the volunteerID and their event count
        Hashmap<String, Integer> volunteerEventCountMap = new Hashmap<>();

        //Count the number of events each volunteer has participated in

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteer.getId());
            volunteerEventCountMap.put(volunteer.getId(), eventVolunteers.size());
        }

        // Display the top 3 volunteers with the most events participated
        System.out.println("");
        System.out.println("Top 3 Volunteers with the Most Events");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n", "Volunteer ID", "Volunteer Name", "Event ID", "Event Description", "Event Venue", "Event Start Date & Time", "Event End Date & Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");

        // Display details for the top 3 volunteers
        int count = 0;
        for (int j = 0; j < volunteers.size(); j++) {
            Volunteer volunteer = volunteers.get(j);

            if (count >= 3) {
                break; // Only display the top 3
            }
            // Retrieve the events joined by the volunteer
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteer.getId());
            ListInterface<Event> events = new ArrayList<>();

            // Retrieve the event details for each event the volunteer joined
            for (int i = 0; i < eventVolunteers.size(); i++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(i);

                events.add(EventHandler.searchEventByEventID(eventVolunteer.eventID()));
            }

            // List the events participated by the volunteer
            for (int i = 0; i < events.size(); i++) {
                Event event = events.get(i);

                System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        event.eventID(),
                        event.description(),
                        event.venue(),
                        event.startDateTime(),
                        event.endDateTime());
            }

            count++;
        }


        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("");
        System.out.println("");

        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers available." + Color.RESET);
            return;
        }

        // Counters for male and female volunteers
        int maleCount = 0;
        int femaleCount = 0;

        // Header for the summary report
        System.out.println("Summary Report: Volunteers and Their Events Participation");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-10s %-25s %-15s%n", "Volunteer ID", "Volunteer Name", "Gender", "Events Participated", "Total Events");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");

        // Iterate over each volunteer to generate the report
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            // Increment gender counters based on volunteer's gender
            if (volunteer.getGender().equalsIgnoreCase("Male")) {
                maleCount++;
            } else if (volunteer.getGender().equalsIgnoreCase("Female")) {
                femaleCount++;
            }

            // Get the list of events the volunteer has participated in
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteer.getId());

            // Display the volunteer's participation details
            if (eventVolunteers != null && !eventVolunteers.isEmpty()) {
                // Display the count of events joined by the volunteer
                System.out.printf("%-15s %-20s %-10s %-25s %-15d%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        volunteer.getGender(),
                        listEventIDs((ArrayList<EventVolunteer>) eventVolunteers), // Helper method to list event IDs
                        eventVolunteers.size()); // Total number of events participated
            } else {
                // If no events participated, display '0'
                System.out.printf("%-15s %-20s %-10s %-25s %-15s%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        volunteer.getGender(),
                        "No events participated",
                        "0");
            }
        }


        // Display the total number of male and female volunteers
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Total Male Volunteers: " + maleCount);
        System.out.println("Total Female Volunteers: " + femaleCount);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
    }

    // Helper method to list event IDs participated by a volunteer
    private static String listEventIDs(ListInterface<EventVolunteer> eventVolunteers) {
        StringBuilder eventIDs = new StringBuilder();

        for (int i = 0; i < eventVolunteers.size(); i++) {
            EventVolunteer ev = eventVolunteers.get(i);

            eventIDs.append(ev.eventID()).append(", ");
        }

        // Remove trailing comma and space
        if (eventIDs.length() > 0) {
            eventIDs.setLength(eventIDs.length() - 2);
        }
        return eventIDs.toString();
    }

    private static void listVolunteers() {
        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers available." + Color.RESET);
            return;
        }

        System.out.println("List of Volunteers");
        System.out.println("-------------------");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }
    }

    private static void filterVolunteers() {
        System.out.println("Filter Volunteers by:");
        System.out.println("1. Age");
        System.out.println("2. Gender");
        System.out.println("3. Availability");
        System.out.println(" ");
        System.out.print("Enter choice: ");

        int filterChoice = scanner.nextInt();
        scanner.nextLine();

        switch (filterChoice) {
            case 1:
                filterByAge();
                break;
            case 2:
                filterByGender();
                break;
            case 3:
                filterByAvailability();
                break;
            default:
                System.out.println(Color.RED + "Invalid choice. Please try again." + Color.RESET);
        }
    }

    private static void filterByAge() {
        System.out.print("Enter minimum age: ");
        int minAge = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter maximum age: ");
        int maxAge = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        System.out.println("Volunteers between " + minAge + " and " + maxAge + " years old:");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            int age = Integer.parseInt(volunteer.getAge());
            if (age >= minAge && age <= maxAge) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println(Color.YELLOW + "No volunteers found within the specified age range." + Color.RESET);
        }
    }

    private static void filterByGender() {
        System.out.print("Enter gender to filter by (Male, Female, Other): ");
        String gender = scanner.nextLine().trim();

        if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female") || gender.equalsIgnoreCase("Other"))) {
            System.out.println(Color.RED + "Invalid gender. Please enter 'Male', 'Female', or 'Other'." + Color.RESET);
            return;
        }

        boolean found = false;
        System.out.println("Volunteers with gender " + gender + ":");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getGender().equalsIgnoreCase(gender)) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println(Color.YELLOW + "No volunteers found with the specified gender." + Color.RESET);
        }
    }

    private static void filterByAvailability() {
        System.out.print("Enter availability to filter by (Weekdays, Weekends): ");
        String availability = scanner.nextLine().trim();

        if (!(availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends"))) {
            System.out.println(Color.RED + "Invalid availability. Please enter 'Weekdays' or 'Weekends'." + Color.RESET);
            return;
        }

        boolean found = false;
        System.out.println("Volunteers available on " + availability + ":");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getAvailability().equalsIgnoreCase(availability)) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }

        if (!found) {
            System.out.println(Color.YELLOW + "No volunteers found with the specified availability." + Color.RESET);
        }
    }

    private static boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    private static boolean isValidContactNumber(String phone) {
        return phone.matches("^01\\d{8,9}$");
    }

    private static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }
}
