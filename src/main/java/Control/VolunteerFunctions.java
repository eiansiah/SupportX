/*
 *  Author: Tan Qian Yiin
 *  ID: 2307616
 *
 * */

package Control;

import Boundary.VolunteerUI;
import Entity.Volunteer;
import FileHandling.VolunteerFileHandler;
import Libraries.ArrayList;
import Libraries.Hashmap;
import Libraries.ListInterface;
import Main.Event.Event;
import Main.Event.EventHandler;
import Main.Event.EventStatus;
import Main.Event.EventVolunteer;
import Utilities.Message;
import Utilities.NewValidation;

import java.util.Scanner;

public class VolunteerFunctions {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "volunteers.txt";
    private static final VolunteerFileHandler fileHandler = new VolunteerFileHandler();
    private static ListInterface<Volunteer> volunteers = new ArrayList<>();

    public void runVolunteerSystem() {

        fileHandler.checkAndCreateFile("volunteers.txt");

        while (true) {
            try {
                String input = VolunteerUI.mainMenu();

                if (input.isEmpty()) {
                    Message.displayGeneralErrorMsg("Input cannot be empty. Please try again.");
                    continue;
                }

                int choice;
                try {
                    choice = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    Message.displayInvalidChoiceMessage("Invalid input. Please enter a number.");
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
                        VolunteerFilter.filterVolunteers();
                        break;
                    case 9:
                        searchEventsByVolunteer();
                        break;
                    case 10:
                        viewSummaryReport();
                        break;
                    case 11:
                        //test this
                        Message.displayExitMessage();
                        return;
                    default:
                        Message.displayInvalidInputMessage("Invalid choice. Please try again.");
                }
            } catch (Exception e) {
                Message.displayInvalidInputMessage("An error occurred. Please try again!!!");
                scanner.nextLine();
            }
        }
    }

    private static void assignVolunteer() {
        ListInterface<Event> upcomingEvents = EventHandler.searchAllEventByEventStatus(EventStatus.UPCOMING);

        //Display!!
        if (upcomingEvents == null || upcomingEvents.isEmpty()) {
            Message.displayGeneralMessage("No upcoming event. ");
            return;
        }

        VolunteerUI.upcomingEventsUI(upcomingEvents);

        Event eventChosen;
        while (true) {
            String eventID = VolunteerUI.inputEventID_UI();

            Event event = EventHandler.searchEventByEventID(eventID);

            if (event == null) {
                Message.displayInvalidInputMessage("Invalid event ID. Please try again.");
            } else {
                eventChosen = event;
                break;
            }
        }

        //Display Volunteer List
        volunteers = fileHandler.readData("volunteers.txt");
        VolunteerUI.listVolunteersUI(volunteers);

        String volunteerID;
        while (true) {
            volunteerID = VolunteerUI.inputVolunteerID_UI();

            boolean idExist = false;
            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.get(i);

                if (volunteer.getId().equals(volunteerID)) {
                    idExist = true;
                    break;
                }
            }

            if (!idExist) {
                Message.displayInvalidInputMessage("Volunteer ID not exist. Please try again.");
                continue;
            }

            ListInterface<EventVolunteer> whatEventVolunteerJoined = EventHandler.getEventVolunteerJoined(volunteerID);
            boolean crashed = false;
            if (whatEventVolunteerJoined != null) {
                for (int i = 0; i < whatEventVolunteerJoined.size(); i++) {
                    EventVolunteer eventVolunteer = whatEventVolunteerJoined.get(i);

                    Event event = EventHandler.searchEventByEventID(eventVolunteer.eventID());

                    if (!(eventChosen.endDateTime().isBefore(event.startDateTime()) || eventChosen.startDateTime().isAfter(event.endDateTime()))) {
                        // Schedule crash
                        Message.displayInvalidInputMessage("The event selected crashed with volunteer schedule " + event.eventID() + ". Please try again.");
                        crashed = true;
                        break;
                    }
                }
            }

            if (crashed) {
                continue;
            }

            EventHandler.addEventVolunteer(eventChosen.eventID(), volunteerID);
            Message.displaySuccessMessage(volunteerID + " has been assigned to " + eventChosen.eventID());
            break;
        }
    }

    private static void searchEventsByVolunteer() {
        volunteers = fileHandler.readData("volunteers.txt");
        //unable to read data from file
        String volunteerID = VolunteerUI.volunteerIDtoSearchUI();
        Volunteer volunteerToSearch = null;

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getId().equals(volunteerID)) {
                volunteerToSearch = volunteer;
                break;
            }
        }

        if (volunteerToSearch != null) {
            VolunteerUI.volunteerDetailsUI(volunteerToSearch);

            // Retrieve events associated with this volunteer
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteerID);

            ListInterface<Event> events = new ArrayList<>();

            for (int i = 0; i < eventVolunteers.size(); i++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(i);

                events.add(EventHandler.searchEventByEventID(eventVolunteer.eventID()));
            }

            if (eventVolunteers != null && !eventVolunteers.isEmpty()) {
                VolunteerUI.eventsJoinedUI((ArrayList<Event>) events);
            } else {
                Message.displayDataNotFoundMessage("No events found for this volunteer.");
            }
        } else {
            Message.displayInvalidInputMessage("Volunteer does not exist.");
        }

    }

    private static void addVolunteer() {
        VolunteerUI.addVolunteerUI();

        String lastId = fileHandler.getLastVolunteerId(FILE_NAME);
        String newVolunteerId = fileHandler.incrementVolunteerId(lastId);

        Volunteer volunteer = new Volunteer();
        volunteer.setId(newVolunteerId);

        String name;
        do {
            name = VolunteerUI.inputVolunteerNameUI();
            if (!NewValidation.isValidInput(name)) {
                Message.displayInvalidInputMessage("Sorry! Empty field! Please enter a valid name.");
            } else if (!name.matches("[a-zA-Z\\s-]+")) {
                Message.displayInvalidInputMessage("Sorry! Invalid name. Please enter a valid name containing only letters.");
            } else {
                volunteer.setName(name);
                break;
            }
        } while (true);

        while (true) {

            String ageInput = VolunteerUI.inputVolunteerAgeUI();

            try {
                int age = Integer.parseInt(ageInput);
                if (age > 0) {
                    volunteer.setAge(ageInput);
                    break;
                } else {
                    Message.displayInvalidInputMessage("Age must be a positive number. Please try again.");
                }
            } catch (NumberFormatException e) {
                Message.displayInvalidInputMessage("Invalid input for age. Please enter a valid number.");
            }
        }

        String gender;
        do {
            gender = VolunteerUI.inputVolunteerGenderUI();
            if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
                Message.displayInvalidInputMessage("Invalid gender. Please enter 'Male' or 'Female'.");
            } else {
                volunteer.setGender(gender);
                break;
            }
        } while (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")));

        String phone;
        do {
            phone = VolunteerUI.inputVolunteerPhoneUI();
            if (!NewValidation.isValidContactNumber(phone)) {
                Message.displayInvalidInputMessage("Invalid contact number. Please try again.");
            } else {
                volunteer.setPhone(phone);
                break;
            }
        } while (!NewValidation.isValidContactNumber(phone));

        String email;
        do {
            email = VolunteerUI.inputVolunteerEmailUI();
            if (!NewValidation.isValidInput(email)) {
                Message.displayInvalidInputMessage("Empty field! Please enter valid data.");
            } else if (!NewValidation.isValidEmail(email)) {
                Message.displayInvalidInputMessage("Invalid email format. Please try again.");
            } else {
                volunteer.setEmail(email);
                break;
            }
        } while (!NewValidation.isValidEmail(email) || !NewValidation.isValidInput(email));

        String availability;
        do {
            availability = VolunteerUI.inputVolunteerAvailabilityUI();
            if (availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends")) {
                volunteer.setAvailability(availability);
                break;
            } else {
                Message.displayGeneralErrorMsg("Invalid availability. Please enter 'Weekdays' or 'Weekends'.");
            }
        } while (!(availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends")));

        fileHandler.saveData(FILE_NAME, volunteer);
        volunteers.add(volunteer);

        Message.displaySuccessMessage("Volunteer added successfully.");
    }

    private static void removeVolunteer() {
        volunteers = fileHandler.readData("volunteers.txt");
        VolunteerUI.removeVolunteerUI();

        if (volunteers.isEmpty()) {
            Message.displayDataNotFoundMessage("No volunteers to delete.");
            return;
        }

        VolunteerUI.listOfVolunteersUI(volunteers);

        while (true) {
            String volunteerID = VolunteerUI.inputVolunteerUItoDeleteUI();

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

                Message.displaySuccessMessage("Volunteer deleted successfully.");
                break;
            } else {
                Message.displayDataNotFoundMessage("Volunteer does not exist. Please enter a valid ID.");
            }
        }
    }

    private static void modifyVolunteer() {
        volunteers = fileHandler.readData("volunteers.txt");
        VolunteerUI.modifyVolunteerUI();

        if (volunteers.isEmpty()) {
            Message.displayDataNotFoundMessage("No volunteers to modify.");
            return;
        }

        VolunteerUI.listOfVolunteersUI(volunteers);

        while (true) {
            String volunteerID = VolunteerUI.inputVolunteerIDtoModifyUI();

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
                    int modifyChoice = VolunteerUI.inputDataToModifyUI();

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
                            return;
                        default:
                            Message.displayGeneralErrorMsg("Invalid choice. Please try again.");
                    }
                }
            } else {
                Message.displayInvalidInputMessage("Volunteer does not exist. Please enter a valid ID.");
            }
        }
    }

    private static void modifyName(Volunteer volunteer) {

        String name = VolunteerUI.inputModifyNameUI();

        VolunteerUI.displayEmptyString();

        if (!name.isEmpty() && name.matches("[a-zA-Z\\s-]+")) {
            volunteer.setName(name);
            Message.displaySuccessMessage("Name changed succesfully!");
        } else if (!name.isEmpty()) {
            Message.displayGeneralErrorMsg("Invalid name. Please enter a valid name containing only letters.");
        }

    }

    private static void modifyAge(Volunteer volunteer) {
        while (true) {
            String ageInput = VolunteerUI.inputModifyAgeUI();
            VolunteerUI.displayEmptyString();

            if (ageInput.isEmpty()) {
                break;
            }

            try {
                int age = Integer.parseInt(ageInput);
                VolunteerUI.displayEmptyString();
                if (age > 0) {
                    volunteer.setAge(ageInput);
                    Message.displaySuccessMessage("Age changed succesfully!");
                    break;
                } else {
                    Message.displayInvalidInputMessage("Age must be a positive number. Please try again.");
                }
            } catch (NumberFormatException e) {
                Message.displayGeneralErrorMsg("Invalid input for age. Please enter a valid number.");
            }
        }
    }

    private static void modifyGender(Volunteer volunteer) {
        while (true) {
            String gender = VolunteerUI.inputModifyGenderUI();

            VolunteerUI.displayEmptyString();

            if (gender.isEmpty()) {
                break;
            }
            if (gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female")) {
                volunteer.setGender(gender);
                Message.displaySuccessMessage("Gender changed succesfully!");
                break;
            } else {
                Message.displayGeneralErrorMsg("Invalid gender. Please enter 'Male' or 'Female'.");
            }
        }
    }

    private static void modifyPhone(Volunteer volunteer) {
        while (true) {

            String phone = VolunteerUI.inputModifyPhoneUI();

            VolunteerUI.displayEmptyString();

            if (phone.isEmpty()) {
                break;
            }
            if (NewValidation.isValidContactNumber(phone)) {
                volunteer.setPhone(phone);
                Message.displaySuccessMessage("Contact Number changed succesfully!");
                break;
            } else {
                Message.displayGeneralErrorMsg("Invalid contact number. Please try again.");
            }
        }
    }

    private static void modifyEmail(Volunteer volunteer) {
        while (true) {

            String email = VolunteerUI.inputModifyEmailUI();

            VolunteerUI.displayEmptyString();

            if (email.isEmpty()) {
                break;
            }
            if (NewValidation.isValidEmail(email)) {
                volunteer.setEmail(email);
                Message.displaySuccessMessage("Email changed succesfully!");
                break;
            } else {
                Message.displayGeneralErrorMsg("Invalid email format. Please try again.");
            }
        }
    }

    private static void modifyAvailability(Volunteer volunteer) {
        while (true) {

            String availability = VolunteerUI.inputModifyAvailabilityUI();

            VolunteerUI.displayEmptyString();

            if (availability.isEmpty()) {
                break;
            }
            if (availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends")) {
                volunteer.setAvailability(availability);
                Message.displaySuccessMessage("Availability changed succesfully!");
                break;
            } else {
                Message.displayGeneralErrorMsg("Invalid availability. Please enter 'Weekdays' or 'Weekends'.");
            }
        }
    }

    private static void searchVolunteer() {
        volunteers = fileHandler.readData("volunteers.txt");
        String volunteerID = VolunteerUI.volunteerIDtoSearchUI();

        Volunteer volunteerToSearch = null;

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getId().equals(volunteerID)) {
                volunteerToSearch = volunteer;
                break;
            }
        }

        if (volunteerToSearch != null) {
            VolunteerUI.searchVolunteerDetailsUI(volunteerToSearch);
        } else {
            Message.displayDataNotFoundMessage("Volunteer does not exist.");
        }
    }

    private static void listVolunteerEvents() {
        volunteers = fileHandler.readData("volunteers.txt");

        if (volunteers.isEmpty()) {
            Message.displayDataNotFoundMessage("No volunteers available.");
            return;
        }

        VolunteerUI.listVolunteerEventsUI();

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            // Get the list of events the volunteer has participated in
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteer.getId());
            ListInterface<Event> events = new ArrayList<>();

            for (int j = 0; j < eventVolunteers.size(); j++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(j);

                events.add(EventHandler.searchEventByEventID(eventVolunteer.eventID()));
            }

            // If the volunteer has participated in events, list them
            if (eventVolunteers != null && !eventVolunteers.isEmpty()) {

                VolunteerUI.listParticipatedVolunteerEventsUI(volunteer, events);
            } else {
                // If the volunteer hasn't participated in any events, indicate so
                VolunteerUI.listVolunteerNoEventsUI(volunteer);
            }
        }

        VolunteerUI.displayListVolunteerEventsEndline();
    }

    public static void viewSummaryReport() {
        volunteers = fileHandler.readData("volunteers.txt");
        // Check if there are any volunteers available
        if (volunteers.isEmpty()) {
            Message.displayDataNotFoundMessage("No volunteers available.");
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

        // Display the header
        VolunteerUI.volunteersEventHistoryUI();

        // Display event registration details 
        int count = 0;
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            // Retrieve the events joined by the volunteer
            ListInterface<EventVolunteer> eventVolunteers = EventHandler.getEventVolunteerJoined(volunteer.getId());
            ListInterface<Event> events = new ArrayList<>();

            // Retrieve the event details for each event the volunteer joined
            for (int j = 0; j < eventVolunteers.size(); j++) {
                EventVolunteer eventVolunteer = eventVolunteers.get(j);

                events.add(EventHandler.searchEventByEventID(eventVolunteer.eventID()));
            }

            // List the events participated by the volunteer
            VolunteerUI.volunteerEventsHistoryUI(volunteer, events);

            count++;
        }

        VolunteerUI.displayVolunteersEndline();

        if (volunteers.isEmpty()) {
            Message.displayDataNotFoundMessage("No volunteers available.");
            return;
        }

        // Counters for male and female volunteers
        int maleCount = 0;
        int femaleCount = 0;

        // Header for the summary report
        VolunteerUI.viewSummaryReportUI();

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
                VolunteerUI.viewSummaryReportEventCountUI(volunteer, eventVolunteers);

            } else {
                // If no events participated, display '0'
                VolunteerUI.viewSummaryReportNoEventsUI(volunteer);
            }
        }

        int totalVolunteers = volunteers.size();
        // Display the total number of male and female volunteers
        VolunteerUI.viewSummaryReportGendersUI(maleCount, femaleCount, totalVolunteers);

        // Step 4: Identify and display the top volunteer
        Volunteer topVolunteer = null;
        int maxEventCount = 0;

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);
            int eventCount = volunteerEventCountMap.get(volunteer.getId());

            if (eventCount > maxEventCount) {
                maxEventCount = eventCount;
                topVolunteer = volunteer;
            }
        }

        // Display the volunteer who participated in the most events
        if (topVolunteer != null) {
            VolunteerUI.viewTopVolunteerUI(topVolunteer, maxEventCount);
        } else {
            VolunteerUI.noTopVolunteerUI();
        }
    }

    // Helper method to list event IDs participated by a volunteer
    public static String listEventIDs(ListInterface<EventVolunteer> eventVolunteers) {
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
        volunteers = fileHandler.readData("volunteers.txt");
        if (volunteers.isEmpty()) {
            Message.displayDataNotFoundMessage("No volunteers available.");
            return;
        }

        VolunteerUI.listVolunteersUI(volunteers);
    }

    public static void main(String args[]) {
        VolunteerFunctions volunteerFunctions = new VolunteerFunctions();
        volunteerFunctions.runVolunteerSystem();
    }

}
