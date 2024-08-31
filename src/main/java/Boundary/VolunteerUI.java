package Boundary;

import Control.VolunteerFunctions;
import Entity.Volunteer;
import Main.Event.Event;
import Main.Event.EventVolunteer;

import Libraries.ArrayList;
import Libraries.Color;
import Libraries.ListInterface;
import java.util.Scanner;

public class VolunteerUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static String mainMenu() {
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

        return scanner.nextLine().trim();
    }

    //private static void displayNoUpcomingEvents() {
    //    System.out.println("No upcoming events. ");
    //}

    public static void upcomingEventsUI(ListInterface<Event> upcomingEvents) {
        for (int i = 0; i < upcomingEvents.size(); i++) {
            Event event = upcomingEvents.get(i);

            System.out.println(event.eventID() + " " + event.startDateTime() + " " + event.endDateTime() + " " + event.venue());
           
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
    
    //private static void assignedVolunteerUI(Event eventChosen, String volunteerID) {
    //    System.out.println(volunteerID + " has been assigned to " + eventChosen.eventID());
    //}

    public static String volunteerIDtoSearchUI() {
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

    private static void eventsJoinedUI(ArrayList<Event> events) {
        System.out.println("\nEvents Joined by Volunteer");
        System.out.println("---------------------------");
        for (Event event : events) {
            System.out.println("Event ID: " + event.eventID());
            System.out.println("Event Name: " + event.description());
            System.out.println("Date Joined: " + event.startDateTime());
            System.out.println("---------------------------");
        }
    }

    private static void addVolunteerUI() {
        System.out.println("\nEnter Volunteer details");
        System.out.println("-------------------------");
    }

    private static String inputVolunteerNameUI() {
        System.out.print("Enter name: ");
        return scanner.nextLine().trim();
    }

    private static String inputVolunteerAgeUI() {
        System.out.print("Enter age: ");
        return scanner.nextLine().trim();
    }

    private static String inputVolunteerGenderUI() {
        System.out.print("Enter gender (Male, Female, Other): ");
        return scanner.nextLine().trim();
    }

    private static String inputVolunteerPhoneUI() {
        System.out.print("Enter contact number: ");
        return scanner.nextLine().trim();
    }

    private static String inputVolunteerEmailUI() {
        System.out.print("Enter email: ");
        return scanner.nextLine().trim();
    }

    private static String inputVolunteerAvailabilityUI() {
        System.out.print("Enter availability (Weekdays, Weekends): ");
        return scanner.nextLine().trim();
    }

    private static void displayAddVolunteerMsg() {
        System.out.println(Color.GREEN + "Volunteer added successfully." + Color.RESET);
    }

    private static void removeVolunteerUI() {
        System.out.println("Removing Volunteer...");
    }

    private static void listOfVolunteersUI(ArrayList<Volunteer> volunteers) {
        System.out.println("List of Volunteers:");
        for (Volunteer volunteer : volunteers) {
            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }
    }

    private static String inputVolunteerUItoDeleteUI() {
        System.out.print("Enter Volunteer ID to delete: ");
        return scanner.nextLine().trim();
    }

    private static void displayRemoveVolunteerMsg() {
        System.out.println(Color.GREEN + "Volunteer deleted successfully." + Color.RESET);
    }

    private static void modifyVolunteerUI() {
        System.out.println("Modify Volunteer");
        System.out.println("-------------------");
    }

    private static String inputVolunteerIDtoModifyUI() {
        System.out.print("Enter Volunteer ID to modify: ");
        return scanner.nextLine().trim();
    }

    private static int inputDataToModifyUI() {
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

        return scanner.nextInt();
    }

    private static void displayModifyVolunteerMsg() {
        System.out.println();
        System.out.println(Color.GREEN + "Volunteer details updated successfully." + Color.RESET);
    }

    private static String inputModifyNameUI() {
        System.out.print("Enter new name (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    private static String inputModifyAgeUI() {
        System.out.print("Enter new age (leave blank to keep current): ");
        return scanner.nextLine().trim();
    }

    private static String inputModifyGenderUI() {
        System.out.print("Enter new gender (Male, Female, Other) (leave blank to keep current): ");
        System.out.println();
        return scanner.nextLine().trim();
    }

    private static String inputModifyPhoneUI() {
        System.out.print("Enter new contact number (leave blank to keep current): ");
        System.out.println(" ");
        return scanner.nextLine().trim();
    }

    private static String inputModifyEmailUI() {
        System.out.print("Enter new email (leave blank to keep current): ");
        System.out.println(" ");
        return scanner.nextLine().trim();
    }

    private static String inputModifyAvailabilityUI() {
        System.out.print("Enter new availability (Weekdays, Weekends) (leave blank to keep current): ");
        System.out.println(" ");
        return scanner.nextLine().trim();
    }

    private static void moreVolunteerDetailsUI(Volunteer volunteerToSearch) {
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

    private static void listVolunteerEventsUI(){
        System.out.println(" ");
        System.out.println("Volunteers and Their Events");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n", "Volunteer ID", "Volunteer Name", "Event ID", "Event Description", "Event Venue", "Event Start Date & Time", "Event End Date & Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void listParticipatedVolunteerEventsUI(Volunteer volunteer, ArrayList<Event> events) {
        for (Event event : events) {
            System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                    volunteer.getId(),
                    volunteer.getName(),
                    event.eventID(),
                    event.description(),
                    event.venue(),
                    event.startDateTime(),
                    event.endDateTime());
        }
    }

    private static void listVolunteerNoEventsUI(Volunteer volunteer) {
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        "N/A",
                        "No events participated",
                        "N/A",
                        "N/A",
                        "N/A");
    }

    private static void displayListVolunteerEventsEndline(){
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void top3VolunteersUI(){
        System.out.println("");
        System.out.println("Top 3 Volunteers with the Most Events");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n", "Volunteer ID", "Volunteer Name", "Event ID", "Event Description", "Event Venue", "Event Start Date & Time", "Event End Date & Time");
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void top3VolunteerEventsUI(Volunteer volunteer, ArrayList<Event> events){
        for (Event event : events) {
            System.out.printf("%-15s %-20s %-15s %-30s %-20s %-30s %-30s%n",
                    volunteer.getId(),
                    volunteer.getName(),
                    event.eventID(),
                    event.description(),
                    event.venue(),
                    event.startDateTime(),
                    event.endDateTime());
        }
    }

    private static void displayTop3VolunteersEndline(){
        System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("");
        System.out.println("");
    }

    private static void viewSummaryReportUI(){
        System.out.println("Summary Report: Volunteers and Their Events Participation");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-15s %-20s %-10s %-25s %-15s%n", "Volunteer ID", "Volunteer Name", "Gender", "Events Participated", "Total Events");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
    }

    private static void viewSummaryReportEventsUI(Volunteer volunteer, ArrayList<EventVolunteer> eventVolunteers){
        System.out.printf("%-15s %-20s %-10s %-25s %-15d%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        volunteer.getGender(),
                        VolunteerFunctions.listEventIDs(eventVolunteers), // Helper method to list event IDs
                        eventVolunteers.size()); // Total number of events participated
    }

    private static void viewSummaryReportNoEventsUI(Volunteer volunteer){
        System.out.printf("%-15s %-20s %-10s %-25s %-15s%n",
                        volunteer.getId(),
                        volunteer.getName(),
                        volunteer.getGender(),
                        "No events participated",
                        "0");
    }

    private static void viewSummaryReportGendersUI(int maleCount, int femaleCount){
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
        System.out.println("Total Male Volunteers: " + maleCount);
        System.out.println("Total Female Volunteers: " + femaleCount);
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
    }

    public static void listVolunteersUI(ListInterface<Volunteer> volunteers){
        System.out.println(" ");
        System.out.println("List of Volunteers");
        System.out.println("-------------------");
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }
    }

    private static int filterVolunteersUI(){
        System.out.println("Filter Volunteers by:");
        System.out.println("1. Age");
        System.out.println("2. Gender");
        System.out.println("3. Availability");
        System.out.println(" ");
        System.out.print("Enter choice: ");

        return scanner.nextInt();
    }

    private static int inputMinimumAgeUI(){
        System.out.print("Enter minimum age: ");
        return scanner.nextInt();
    }

    private static int inputMaximumAgeUI(){
        System.out.print("Enter maximum age: ");
        return scanner.nextInt();
    }

    private static void filterByAgeUI(int minAge, int maxAge){
        System.out.println("Volunteers between " + minAge + " and " + maxAge + " years old:");
    }

    private static void filterVolunteerUI(Volunteer volunteer){
        System.out.println(volunteer.getId() + " " + volunteer.getName());
    }

    private static String inputFilterByGenderUI(){
        System.out.print("Enter gender to filter by (Male, Female, Other):");
        return scanner.nextLine().trim();
    }

    private static void filterByGenderUI(String gender){
        System.out.println("Volunteers with gender " + gender + ":");
    }

    private static String inputFilterByAvailabilityUI(){
        System.out.print("Enter availability to filter by (Weekdays, Weekends): ");
        return scanner.nextLine().trim();
    }

    private static void filterByAvailabilityUI(String availability){
        System.out.println("Volunteers available on " + availability + ":");
    }
    
    private static void displayEmptyString(){
        System.out.println(" ");
    }
}
