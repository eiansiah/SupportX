package Main;

import FileHandling.VolunteerFileHandler;
import Libraries.ArrayList;
import Libraries.Color;

import java.util.Scanner;

public class VolunteerManagement {

    private static final Scanner scanner = new Scanner(System.in);
    private static final String FILE_NAME = "volunteers.txt";
    private static final VolunteerFileHandler fileHandler = new VolunteerFileHandler();
    private static ArrayList<Volunteer> volunteers = new ArrayList<>();

    public static void main(String[] args) {
        volunteers = fileHandler.readData(FILE_NAME);
        handle();
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
                System.out.println("6. List Volunteers");
                System.out.println("7. Filter Volunteers");
                System.out.println("8. Search Events by Volunteer");
                System.out.println("9. View Reports");
                System.out.println("10. Back");
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
//                    assignVolunteer();
                        break;
                    case 6:
                        listVolunteers();
                        break;
                    case 7:
                        filterVolunteers();
                        break;
                    case 8:
//                    searchEventsByVolunteer();
                        break;
                    case 9:
//                    viewReports(); 
                        break;
                    case 10:
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
        for (Volunteer volunteer : volunteers) {
            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }

        while (true) {
            System.out.print("Enter Volunteer ID to delete: ");
            String volunteerID = scanner.nextLine().trim();

            Volunteer volunteerToRemove = null;
            for (Volunteer volunteer : volunteers) {
                if (volunteer.getId().equals(volunteerID)) {
                    volunteerToRemove = volunteer;
                    break;
                }
            }

            if (volunteerToRemove != null) {
                volunteers.remove(volunteerToRemove);
                fileHandler.updateMultipleData(FILE_NAME, volunteers);
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
        for (Volunteer volunteer : volunteers) {
            System.out.println(volunteer.getId() + " " + volunteer.getName());
        }

        while (true) {
            System.out.print("Enter Volunteer ID to modify: ");
            String volunteerID = scanner.nextLine().trim();

            Volunteer volunteerToModify = null;
            for (Volunteer volunteer : volunteers) {
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
        for (Volunteer volunteer : volunteers) {
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

//    private static void assignVolunteer() {
//        System.out.println("Assign a Volunteer");
//        System.out.println("-------------------");
//
//        if (volunteers.isEmpty()) {
//            System.out.println("No volunteers available to assign.");
//            return;
//        }
//
//        if (events.isEmpty()) {
//            System.out.println("No events available for assignment.");
//            return;
//        }
//        
//        System.out.println("Available Events:");
//        for (Event event : events) {
//            System.out.println(event);
//        }
//
//        Event selectedEvent = null;
//        while (true) {
//            System.out.print("Enter Event ID to assign volunteers: ");
//            String eventId = scanner.nextLine().trim();
//
//            for (Event event : events) {
//                if (event.getEventId().equalsIgnoreCase(eventId)) {
//                    selectedEvent = event;
//                    break;
//                }
//            }
//
//            if (selectedEvent != null) {
//                break;
//            } else {
//                System.out.println("Invalid Event ID. Please enter a valid Event ID.");
//            }
//        }
//
//        // List available volunteers
//        System.out.println("Available Volunteers:");
//        for (Volunteer volunteer : volunteers) {
//            System.out.println(volunteer.getId() + " " + volunteer.getName());
//        }
//
//        // Select a volunteer
//        while (true) {
//            System.out.print("Enter Volunteer ID to assign to the event: ");
//            String volunteerId = scanner.nextLine().trim();
//
//            Volunteer selectedVolunteer = null;
//            for (Volunteer volunteer : volunteers) {
//                if (volunteer.getId().equalsIgnoreCase(volunteerId)) {
//                    selectedVolunteer = volunteer;
//                    break;
//                }
//            }
//
//            if (selectedVolunteer != null) {
//                // Assign the volunteer to the selected event
//                selectedEvent.addVolunteer(selectedVolunteer);
//                System.out.println("Volunteer " + selectedVolunteer.getName() + " assigned to event " + selectedEvent.getEventName() + " successfully.");
//                break;
//            } else {
//                System.out.println("Invalid Volunteer ID. Please enter a valid Volunteer ID.");
//            }
//        }
//    }
    private static void listVolunteers() {
        if (volunteers.isEmpty()) {
            System.out.println(Color.YELLOW + "No volunteers available." + Color.RESET);
            return;
        }

        System.out.println("List of Volunteers");
        System.out.println("-------------------");
        for (Volunteer volunteer : volunteers) {
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
        for (Volunteer volunteer : volunteers) {
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
        for (Volunteer volunteer : volunteers) {
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
        for (Volunteer volunteer : volunteers) {
            if (volunteer.getAvailability().equalsIgnoreCase(availability)) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }
        if (!found) {
            System.out.println(Color.YELLOW + "No volunteers found with the specified availability." + Color.RESET);
        }
    }

//    private static void searchEventsByVolunteer() {
//        System.out.println("Search Events by Volunteer");
//
//        // Check if there are volunteers
//        if (volunteers.isEmpty()) {
//            System.out.println("No volunteers available.");
//            return;
//        }
//
//        // List current volunteers
//        System.out.println("List of Volunteers:");
//        for (Volunteer volunteer : volunteers) {
//            System.out.println(volunteer.getId() + " " + volunteer.getName());
//        }
//
//        System.out.print("Enter Volunteer ID to search for events: ");
//        String volunteerID = scanner.nextLine().trim();
//
//        Volunteer volunteer = null;
//        for (Volunteer v : volunteers) {
//            if (v.getId().equals(volunteerID)) {
//                volunteer = v;
//                break;
//            }
//        }
//
//        if (volunteer != null) {
//            System.out.println("Events for Volunteer " + volunteer.getName() + ":");
//            boolean found = false;
//            for (Event event : events) {
//                if (event.getAssignedVolunteers().contains(volunteer)) {
//                    System.out.println(event);
//                    found = true;
//                }
//            }
//
//            if (!found) {
//                System.out.println("No events found for this volunteer.");
//            }
//        } else {
//            System.out.println("Volunteer not found.");
//        }
//    }
//    private static void viewReports() {
//        System.out.println("Summary of Volunteers Report");
//    }
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
