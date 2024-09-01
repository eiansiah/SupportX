/*
 *  Author: Tan Qian Yiin
 *  ID: 2307616
 *
 * */
package Boundary;

import Control.VolunteerFunctions;
import Entity.Volunteer;
import Entity.Event;
import Entity.EventVolunteer;

import ADT.ArrayList;
import Utilities.Color;
import ADT.ListInterface;

import java.util.Scanner;
import java.time.format.DateTimeFormatter;

public class VolunteerUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static String mainMenu() {
        System.out.println("\n--------------------------------");
        System.out.println(Color.BLUE + "Volunteer Subsystem's Main Menu" + Color.RESET);
        System.out.println("--------------------------------");
        System.out.println("1. Add Volunteer");
        System.out.println("2. Remove Volunteer");
        System.out.println("3. Modify Volunteer");
        System.out.println("4. Search Volunteer");
        System.out.println("5. Assign Volunteer to Events");
        System.out.println("6. List all Volunteers");
        System.out.println("7. List of Volunteers and Their Events");
        System.out.println("8. Filter Volunteers based on criteria");
        System.out.println("9. Search Events under a Volunteer");
        System.out.println("10. View Summary Report");
        System.out.println("11. Exit");
        System.out.println(" ");
        System.out.print("Enter choice: ");

        return scanner.nextLine().trim();
    }

    public static void upcomingEventsUI(ListInterface<Event> upcomingEvents) {
        for (int i = 0; i < upcomingEvents.size(); i++) {
            Event event = upcomingEvents.get(i);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println(event.eventID() + " " + event.startDateTime().format(df) + " " + event.endDateTime().format(df) + " " + event.venue());

        }
    }

    public static String inputEventID_UI() {
        System.out.println("");
        System.out.print("Enter event ID: ");
        return scanner.nextLine().trim();
    }

    public static void volunteersUI(ArrayList<Volunteer> volunteers) {
        for (Volunteer volunteer : volunteers) {
            System.out.println(volunteer.toString());
        }
    }

    public static String inputVolunteerID_UI() {
        System.out.println("");
        System.out.print("Enter volunteer ID: ");
        return scanner.nextLine().trim();
    }

    public static String volunteerIDtoSearchUI() {
        System.out.println("");
        System.out.print("Enter Volunteer ID to search: ");
        return scanner.nextLine().trim();
    }

    public static void volunteerDetailsUI(Volunteer volunteerToSearch) {
        System.out.println("\nVolunteer Details");
        System.out.println("-------------------");
        System.out.println("ID: " + volunteerToSearch.getId());
        System.out.println("Name: " + volunteerToSearch.getName());
        System.out.println("Availability: " + volunteerToSearch.getAvailability());
    }

    public static void eventsJoinedUI(ArrayList<Event> events) {
        System.out.println("\nEvents Joined by Volunteer");
        System.out.println("---------------------------");
        for (int i = 0; i < events.size(); i++) {
            Event event = events.get(i);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println("Event ID: " + event.eventID());
            System.out.println("Event Name: " + event.eventName());
            System.out.println("Date Joined: " + event.startDateTime().format(df));
            System.out.println("---------------------------");
        }
    }

    public static void addVolunteerUI() {
        System.out.println("\nEnter New Volunteer details");
        System.out.println("----------------------------");
    }

    public static String inputVolunteerNameUI() {
        System.out.print("Enter name: ");
        return scanner.nextLine().trim();
    }

    public static String inputVolunteerAgeUI() {
        System.out.print("Enter age: ");
        return scanner.nextLine().trim();
    }

    public static String inputVolunteerGenderUI() {
        System.out.print("Enter gender (Male, Female): ");
        return scanner.nextLine().trim();
    }

    public static String inputVolunteerPhoneUI() {
        System.out.print("Enter contact number: ");
        return scanner.nextLine().trim();
    }

    public static String inputVolunteerEmailUI() {
        System.out.print("Enter email: ");
        return scanner.nextLine().trim();
    }

    public static String inputVolunteerAvailabilityUI() {
        System.out.print("Enter availability (Weekdays, Weekends): ");
        return scanner.nextLine().trim();
    }

    public static void removeVolunteerUI() {
        System.out.println("");
        System.out.println("Removing Volunteer...");
    }

    public static void listOfVolunteersUI(ListInterface<Volunteer> volunteers) {
        System.out.println("");
        System.out.println("List of Volunteers");
        System.out.println("------------------");
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);
            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }
    }

    public static String inputVolunteerUItoDeleteUI() {
        System.out.println("");
        System.out.print("Enter Volunteer ID to delete: ");
        return scanner.nextLine().trim();
    }

    public static void modifyVolunteerUI() {
        System.out.println("");
        System.out.println("Modify Volunteer:");
    }

    public static String inputVolunteerIDtoModifyUI() {
        System.out.println("");
        System.out.print("Enter Volunteer ID to modify: ");
        return scanner.nextLine().trim();
    }

    public static int inputDataToModifyUI() {
        System.out.println("");
        System.out.println("Select data to modify:");
        System.out.println("----------------------");
        System.out.println("1. Name");
        System.out.println("2. Age");
        System.out.println("3. Gender");
        System.out.println("4. Contact Number");
        System.out.println("5. Email");
        System.out.println("6. Availability");
        System.out.println("7. Back");
        System.out.println(" ");
        System.out.print("Enter choice: ");

        return Integer.parseInt(scanner.nextLine().trim());
    }

    public static String inputModifyNameUI() {
        System.out.print("Enter new name (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    public static String inputModifyAgeUI() {
        System.out.print("Enter new age (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    public static String inputModifyGenderUI() {
        System.out.print("Enter new gender (Male, Female) (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    public static String inputModifyPhoneUI() {
        System.out.print("Enter new contact number (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    public static String inputModifyEmailUI() {
        System.out.print("Enter new email (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    public static String inputModifyAvailabilityUI() {
        System.out.print("Enter new availability (Weekdays, Weekends) (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    public static void searchVolunteerDetailsUI(Volunteer volunteerToSearch) {
        System.out.println("\nVolunteer Details");
        System.out.println("-------------------");
        System.out.println("ID: " + volunteerToSearch.getId());
        System.out.println("Name: " + volunteerToSearch.getName());
        System.out.println("Age: " + volunteerToSearch.getAge());
        System.out.println("Gender: " + volunteerToSearch.getGender());
        System.out.println("Phone: " + volunteerToSearch.getPhone());
        System.out.println("Email: " + volunteerToSearch.getEmail());
        System.out.println("Availability: " + volunteerToSearch.getAvailability());
    }

    public static void listVolunteerEventsUI() {
        System.out.println(" ");
        System.out.println("Volunteers and Their Events");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n", "Volunteer ID", "Volunteer Name", "Event ID", "Event Name", "Event Venue", "Event Start Date & Time", "Event End Date & Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void listParticipatedVolunteerEventsUI(Volunteer volunteer, ListInterface<Event> events) {
        for (int j = 0; j < events.size(); j++) {
            Event event = events.get(j);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                    volunteer.getId(),
                    volunteer.getName(),
                    event.eventID(),
                    event.eventName(),
                    event.venue(),
                    event.startDateTime().format(df),
                    event.endDateTime().format(df));
        }
    }

    public static void listVolunteerNoEventsUI(Volunteer volunteer) {
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                volunteer.getId(),
                volunteer.getName(),
                "N/A",
                "No events participated",
                "N/A",
                "N/A",
                "N/A");
    }

    public static void displayListVolunteerEventsEndline() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void volunteersEventHistoryUI() {
        System.out.println("");
        System.out.println("Volunteers Event Registration History");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n", "Volunteer ID", "Volunteer Name", "Event ID", "Event Name", "Event Venue", "Event Start Date & Time", "Event End Date & Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void volunteerEventsHistoryUI(Volunteer volunteer, ListInterface<Event> events) {
        for (int j = 0; j < events.size(); j++) {
            Event event = events.get(j);
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                    volunteer.getId(),
                    volunteer.getName(),
                    event.eventID(),
                    event.eventName(),
                    event.venue(),
                    event.startDateTime().format(df),
                    event.endDateTime().format(df));
        }
    }

    public static void displayReportHeader() {
        System.out.printf("""
                         ----------------------------------------------------------------------------
                         |                                                                          |
                         |                   SUMMARY REPORT OF VOLUNTEER SUBSYSTEM                  |
                         |                                                                          |
                         ----------------------------------------------------------------------------""");
        System.out.println(" ");
    }

    public static void displayVolunteersEndline() {
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
    }

    public static void viewSummaryReportUI() {
        System.out.println("Volunteers and Their Events Participation");
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-10s %-25s %-15s%n", "Volunteer ID", "Volunteer Name", "Gender", "Events Participated", "Total Events");
        System.out.println("---------------------------------------------------------------------------------------");
    }

    public static void viewSummaryReportEventCountUI(Volunteer volunteer, ListInterface<EventVolunteer> eventVolunteers) {
        System.out.printf("%-15s %-20s %-10s %-25s %-15d%n",
                volunteer.getId(),
                volunteer.getName(),
                volunteer.getGender(),
                VolunteerFunctions.listEventIDs(eventVolunteers), // Helper method to list event IDs
                eventVolunteers.size()); // Total number of events participated
    }

    public static void viewSummaryReportNoEventsUI(Volunteer volunteer) {
        System.out.printf("%-15s %-20s %-10s %-25s %-15s%n",
                volunteer.getId(),
                volunteer.getName(),
                volunteer.getGender(),
                "No events participated",
                "0");
    }

    public static void viewSummaryReportGendersUI(int maleCount, int femaleCount, int totalVolunteer) {
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println("Total Male Volunteers: " + maleCount);
        System.out.println("Total Female Volunteers: " + femaleCount);
        System.out.println("Total Volunteers: " + totalVolunteer);
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.println(" ");
    }

    public static void viewTopVolunteerUI(Volunteer topVolunteer, int maxEventCount) {
        System.out.println(" ");
        System.out.println("---------------------------------------------------");
        System.out.println("Top Volunteer that participated in the most events");
        System.out.println("---------------------------------------------------");
        System.out.println("Volunteer ID: " + topVolunteer.getId());
        System.out.println("Name: " + topVolunteer.getName());
        System.out.println("Total Events Participated: " + maxEventCount);
        System.out.println("---------------------------------------------------");
    }

    public static void noTopVolunteerUI() {
        System.out.println("No volunteer participated in any events.");
    }

    public static void listVolunteersUI(ListInterface<Volunteer> volunteers) {
        System.out.println(" ");
        System.out.println("List of Volunteers");
        System.out.println("-------------------");
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }
    }

    public static int filterVolunteersUI() {
        System.out.println("");
        System.out.println("Filter Volunteers by:");
        System.out.println("---------------------");
        System.out.println("1. Age");
        System.out.println("2. Gender");
        System.out.println("3. Availability");
        System.out.println("4. Back");
        System.out.println(" ");
        System.out.print("Enter choice: ");

        return Integer.parseInt(scanner.nextLine().trim());
    }

    public static void clearInvalidInput() {
        scanner.nextLine();
    }

    public static void upcomingEventsHeader() {
        System.out.println("");
        System.out.println("Upcoming Events");
        System.out.println("------------------");
    }

    public static int inputMinimumAgeUI() {
        System.out.print("Enter minimum age: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public static int inputMaximumAgeUI() {
        System.out.print("Enter maximum age: ");
        return Integer.parseInt(scanner.nextLine().trim());
    }

    public static void filterByAgeUI(int minAge, int maxAge) {
        System.out.println("");
        System.out.println("Volunteers between " + minAge + " and " + maxAge + " years old");
        System.out.println("--------------------------------------");
    }

    public static void filterVolunteerUI(Volunteer volunteer) {
        System.out.println(volunteer.getId() + " " + volunteer.getName());
    }

    public static String inputFilterByGenderUI() {
        System.out.print("Enter gender to filter by (Male, Female):");
        return scanner.nextLine().trim();
    }

    public static void filterByGenderUI(String gender) {
        System.out.println("");
        System.out.println("Volunteers with gender " + gender);
        System.out.println("----------------------------");
    }

    public static String inputFilterByAvailabilityUI() {
        System.out.print("Enter availability to filter by (Weekdays, Weekends): ");
        return scanner.nextLine().trim();
    }

    public static void filterByAvailabilityUI(String availability) {
        System.out.println("");
        System.out.println("Volunteers available on " + availability);
        System.out.println("--------------------------------");
    }

    public static void displayEmptyString() {
        System.out.println(" ");
    }
}
