//package Main;
//
//import java.time.LocalDate;
//import java.util.Objects;
//import java.util.Scanner;
//
//import Entity.Donee;
//
//import Utilities.Validation;
//
//import FileHandling.DoneeFileHandler;
//
//import Libraries.ArrayList;
//import Libraries.Color;
//import Libraries.PriorityQueue;
//
//public class DoneeTest {
//
//    private static final DoneeFileHandler fileHandler = new DoneeFileHandler();
//
//    public static void main(String[] args) {
//        int choice;
//
//        fileHandler.checkAndCreateFile("donee.txt");
//
//        System.out.println("\nWelcome to Donee Management Subsystem!");
//        do {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("\nDonee Management Subsystem Main Menu\n");
//            System.out.println("1. Add Donee");
//            System.out.println("2. Delete Donee");
//            System.out.println("3. Modify Donee");
//            System.out.println("4. View Donee Details");
//            System.out.println("5. View donations received by donee");
//            System.out.println("6. Filter Donee");
//            System.out.println("7. View summary reports");
//            System.out.println("8. Exit");
//
//            while (true) { //Input Validation Loop
//                System.out.print("\nWhat would you like to do? : ");
//                String inputChoice = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces
//
//                if (inputChoice.isEmpty()) {
//                    System.out.println(Color.RED + "Empty input detected. Please enter a number between 1 and 8." + Color.RESET);
//                } else {
//                    try {
//                        choice = Integer.parseInt(inputChoice);
//                        if (choice >= 1 && choice <= 8) {
//                            break;  // Exit loop for valid choice input
//                        } else {
//                            System.out.println(Color.RED + "Invalid input. Please enter a number between 1 and 8." + Color.RESET);
//                        }
//                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
//                        System.out.println(Color.RED + "Invalid input. Please enter a number." + Color.RESET);
//                    }
//                }
//            }
//
//            switch (choice) {
//                case 1: //Add Donee
//                    addDonee();
//                    System.out.print("\nPress any key to return to main menu.....");
//                    scanner.nextLine();
//                    break;
//                case 2: //Delete Donee
//                    displayDonees(readDonees());
//                    deleteDonee();
//                    System.out.print("\nPress any key to return to main menu.....");
//                    scanner.nextLine();
//                    break;
//                case 3: //Modify Donee
//                    displayDonees(readDonees());
//                    modifyDonee(readDonees());
//                    System.out.print("\nPress any key to return to main menu.....");
//                    scanner.nextLine();
//                    break;
//                case 4: //View Donee Details
//                    displayDonees(readDonees());
//                    System.out.print("\nPress any key to return to main menu.....");
//                    scanner.nextLine();
//                    break;
//                case 5: //View donations for each donee
//                    break;
//                case 6: //Filter donees by criteria
//                    filterDonees(readDonees());
//                    System.out.print("\nPress any key to return to main menu.....");
//                    scanner.nextLine();
//                    break;
//                case 7: //View reports
//                    break;
//                default: //Exit
//                    System.out.println("\nExiting Program...");
//            }
//        }while (choice !=8);
//    }
//
//    public static String obtainDoneeId(){
//        Scanner scanner = new Scanner(System.in);
//        String doneeId;
//
//        doneeId = scanner.nextLine().trim();
//
//        if (doneeId.isEmpty()) {
//            System.out.println("Empty input detected. Please enter a valid donee ID.");
//        }
//
//        return doneeId;
//    }
//
//    public static ArrayList<Donee> readDonees(){
//        return fileHandler.readData("donee.txt");
//    }
//
//    public static void addDonee() {
//        Scanner scanner = new Scanner(System.in);
//
//        // Get the last ID from the file
//        String lastId = fileHandler.getLastDoneeId("donee.txt");
//        // Increment to get the new ID
//        String newDoneeId = fileHandler.incrementDoneeId(lastId);
//
//        System.out.println("\nNew Donee Registration");
//        System.out.println("**Please enter the details accordingly.**");
//
//        String name = Validation.validateName(scanner);
//        String email = Validation.validateEmail(scanner);
//        String phone = Validation.validatePhone(scanner);
//        String address = Validation.validateAddress(scanner);
//        String doneeType = Validation.validateDoneeType(scanner);
//        String itemCategory = Validation.validateItemCategory(scanner);
//        String doneeUrgency = Validation.validateDoneeUrgency(scanner);
//        LocalDate registeredDate = LocalDate.now();
//
//        Donee donee = new Donee(newDoneeId, name, email, phone, address, doneeType, itemCategory, doneeUrgency, registeredDate);
//
//        fileHandler.saveData("donee.txt", donee);
//    }
//
//    public static void deleteDonee() {
//        String doneeId;
//        do {
//            System.out.print("\nWhich donee would you like to delete? Please enter the Donee ID: ");
//            doneeId = obtainDoneeId();
//        } while (doneeId.isEmpty());
//        // Delete donee
//        fileHandler.deleteData("donee.txt", doneeId);
//    }
//
//    public static void modifyDonee(ArrayList<Donee> donees) {
//        Scanner scanner = new Scanner(System.in);
//        boolean doneeFound = false;
//        String doneeId;
//
//        do {
//            System.out.print("\nWhich donee would you like to modify? Please enter the Donee ID: ");
//            doneeId = obtainDoneeId();
//        } while (doneeId.isEmpty());
//
//        for (Donee doneeSelected : donees) {
//            if (doneeSelected.getDoneeID().equals(doneeId)) {
//                doneeFound = true;
//                char choice = ' ';
//
//                do {
//                    // Prompt user for which part to modify
//                    System.out.println("\nWhich part do you want to modify?");
//                    System.out.println("1. Name");
//                    System.out.println("2. Email");
//                    System.out.println("3. Phone");
//                    System.out.println("4. Address");
//                    System.out.println("5. Donee Type");
//                    System.out.println("6. Item Category Required");
//                    System.out.println("7. Donee Urgency");
//                    System.out.println("X. Return to main menu\n");
//                    System.out.print("Please select an option (1-5 or X): ");
//                    String input = scanner.nextLine().trim();
//
//                    if (input.isEmpty()) {
//                        System.out.println("Empty input detected. Please enter an option between 1-7 and X.");
//                        continue;
//                    }
//
//                    choice = input.toUpperCase().charAt(0);
//
//                    if (Character.isDigit(choice) && choice >= '1' && choice <= '7' || choice == 'X') {
//                        switch (choice) {
//                            case '1':
//                                doneeSelected.setName(Validation.validateName(scanner));
//                                break;
//                            case '2':
//                                doneeSelected.setEmail(Validation.validateEmail(scanner));
//                                break;
//                            case '3':
//                                doneeSelected.setPhone(Validation.validatePhone(scanner));
//                                break;
//                            case '4':
//                                doneeSelected.setAddress(Validation.validateAddress(scanner));
//                                break;
//                            case '5':
//                                doneeSelected.setDoneeType(Validation.validateDoneeType(scanner));
//                                break;
//                            case '6':
//                                doneeSelected.setItemCategoryRequired(Validation.validateItemCategory(scanner));
//                                break;
//                            case '7':
//                                doneeSelected.setDoneeUrgency(Validation.validateDoneeUrgency(scanner));
//                                break;
//                            default:
//                                System.out.println("Returning to main menu.....");
//                        }
//
//                        if (choice != 'X'){
//                            System.out.println("\nDonee with ID " + doneeId + " has been updated successfully.");
//                        }
//                    } else {
//                        System.out.println("Invalid input. Please enter a valid option between 1-7 and X.");
//                    }
//                } while (choice != 'X');
//
//                // Update the donor in the file after all modifications
//                fileHandler.updateMultipleData("donee.txt", donees);
//                break;
//            }
//        }
//
//        if (!doneeFound) {
//            System.out.println("\n**Donee with ID " + doneeId + " was not found**");
//            System.out.println("Returning to main menu.....");
//        }
//    }
//
//    public static void displayDonees(ArrayList<Donee> donees) {
//        System.out.println("\n**LIST OF DONEES**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-25s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Item Category Required", "Donee Urgency", "Registered Date");
//        System.out.println(String.format("%0" + 130 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            System.out.printf("%-10s %-25s %-15s %-15s %-25s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getItemCategoryRequired(), donee.getDoneeUrgency(), donee.getRegisteredDate());
//        }
//        System.out.println(String.format("%0" + 130 + "d", 0).replace("0", "-"));
//    }
//
//    public static void filterDonees(ArrayList<Donee> donees) {
//        Scanner scanner = new Scanner(System.in);
//        int filterChoice;
//
//        do {
//            System.out.println("\nFilter Donees by Category:");
//            System.out.println("1. Donee Type");
//            System.out.println("2. Donee Urgency");
//            System.out.println("3. Return to main menu");
//
//            while (true) { //Input Validation Loop
//                System.out.print("\nSelect a category: ");
//                String filterInput = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces
//
//                if (filterInput.isEmpty()) {
//                    System.out.println(Color.RED + "Empty input detected. Please enter a number between 1 and 3." + Color.RESET);
//                } else {
//                    try {
//                        filterChoice = Integer.parseInt(filterInput);
//                        if (filterChoice >= 1 && filterChoice <= 3) {
//                            break;  // Exit loop for valid choice input
//                        } else {
//                            System.out.println(Color.RED + "Invalid input. Please enter a number between 1 and 3." + Color.RESET);
//                        }
//                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
//                        System.out.println(Color.RED + "Invalid input. Please enter a number." + Color.RESET);
//                    }
//                }
//            }
//
//            switch (filterChoice) {
//                case 1:
//                    filterByType(donees);
//                    break;
//                case 2:
//                    filterByUrgency(readDonees());
//                    break;
//                case 3:
//                    break;
//            }
//
//        } while (filterChoice != 3);
//    }
//
//    public static void filterByType(ArrayList<Donee> donees) {
//        int filterType;
//        Scanner scanner = new Scanner(System.in);
//
//        do {
//            System.out.println("\nFilter by Donee Types:");
//            System.out.println("1. Individual");
//            System.out.println("2. Organization");
//            System.out.println("3. Family");
//            System.out.println("4. Back");
//
//            while (true) { //Input Validation Loop
//                System.out.print("\nSelect a donee type: ");
//                String filterTypeInput = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces
//
//                if (filterTypeInput.isEmpty()) {
//                    System.out.println(Color.RED + "Empty input detected. Please enter a number between 1 and 4." + Color.RESET);
//                } else {
//                    try {
//                        filterType = Integer.parseInt(filterTypeInput);
//                        if (filterType >= 1 && filterType <= 4) {
//                            break;  // Exit loop for valid choice input
//                        } else {
//                            System.out.println(Color.RED + "Invalid input. Please enter a number between 1 and 4." + Color.RESET);
//                        }
//                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
//                        System.out.println(Color.RED + "Invalid input. Please enter a number." + Color.RESET);
//                    }
//                }
//            }
//
//            switch (filterType) {
//                case 1:
//                    individualFilter(donees);
//                    break;
//                case 2:
//                    organizationFilter(donees);
//                    break;
//                case 3:
//                    familyFilter(donees);
//                    break;
//                default:
//                    System.out.print("\nReturning to filter menu.....\n");
//            }
//
//            if (filterType != 4) {
//                System.out.print("\nPress any key to return to menu.....");
//                scanner.nextLine();
//            }
//        } while (filterType != 4);
//    }
//
//    public static void individualFilter(ArrayList<Donee> donees){
//        boolean found = false;
//
//        System.out.println("\n**Individual Type Donee List**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Registered Date");
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            if (Objects.equals(donee.getDoneeType(), "Individual")) {
//                System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getRegisteredDate());
//
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println(Color.YELLOW + "\nNo donees found within the specified donee type.\n" + Color.RESET);
//        }
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//    }
//
//    public static void organizationFilter(ArrayList<Donee> donees){
//        boolean found = false;
//
//        System.out.println("\n**Organization Type Donee List**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Registered Date");
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            if (Objects.equals(donee.getDoneeType(), "Organization")) {
//                System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getRegisteredDate());
//
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println(Color.YELLOW + "No donees found within the specified donee type." + Color.RESET);
//        }
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//    }
//
//    public static void familyFilter(ArrayList<Donee> donees){
//        boolean found = false;
//
//        System.out.println("\n**Family Type Donee List**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Registered Date");
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            if (Objects.equals(donee.getDoneeType(), "Family")) {
//                System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getRegisteredDate());
//
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println(Color.YELLOW + "No donees found within the specified donee type." + Color.RESET);
//        }
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//    }
//
//    public static void filterByUrgency(ArrayList<Donee> donees){
//        int filterUrgency;
//        Scanner scanner = new Scanner(System.in);
//
//        do {
//            System.out.println("\nFilter by Donee Urgency:");
//            System.out.println("1. Low");
//            System.out.println("2. Moderate");
//            System.out.println("3. High");
//            System.out.println("4. Back");
//
//            while (true) { //Input Validation Loop
//                System.out.print("\nSelect a donee urgency category: ");
//                String filterUrgencyInput = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces
//
//                if (filterUrgencyInput.isEmpty()) {
//                    System.out.println(Color.RED + "Empty input detected. Please enter a number between 1 and 4." + Color.RESET);
//                } else {
//                    try {
//                        filterUrgency = Integer.parseInt(filterUrgencyInput);
//                        if (filterUrgency >= 1 && filterUrgency <= 4) {
//                            break;  // Exit loop for valid choice input
//                        } else {
//                            System.out.println(Color.RED + "Invalid input. Please enter a number between 1 and 4." + Color.RESET);
//                        }
//                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
//                        System.out.println(Color.RED + "Invalid input. Please enter a number." + Color.RESET);
//                    }
//                }
//            }
//
//            switch (filterUrgency) {
//                case 1:
//                    filterLowUrgency(donees);
//                    break;
//                case 2:
//                    filterModerateUrgency(donees);
//                    break;
//                case 3:
//                    filterHighUrgency(donees);
//                    break;
//                default:
//                    System.out.print("\nReturning to filter menu.....\n");
//            }
//
//            if (filterUrgency != 4) {
//                System.out.print("\nPress any key to return to menu.....");
//                scanner.nextLine();
//            }
//        } while (filterUrgency != 4);
//    }
//
//    public static void filterLowUrgency(ArrayList<Donee> donees){
//        boolean found = false;
//
//        System.out.println("\n**Low Urgency Donee List**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency", "Registered Date");
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            if (Objects.equals(donee.getDoneeUrgency(), "Low")) {
//                System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency(), donee.getRegisteredDate());
//
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println(Color.YELLOW + "No donees found within the specified urgency category." + Color.RESET);
//        }
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//    }
//
//    public static void filterModerateUrgency(ArrayList<Donee> donees){
//        boolean found = false;
//
//        System.out.println("\n**Moderate Urgency Donee List**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency", "Registered Date");
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            if (Objects.equals(donee.getDoneeUrgency(), "Moderate")) {
//                System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency(), donee.getRegisteredDate());
//
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println(Color.YELLOW + "No donees found within the specified urgency category." + Color.RESET);
//        }
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//    }
//
//    public static void filterHighUrgency(ArrayList<Donee> donees){
//        boolean found = false;
//
//        System.out.println("\n**High Urgency Donee List**\n");
//        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency", "Registered Date");
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//        for (Donee donee : donees) {
//            if (Objects.equals(donee.getDoneeUrgency(), "High")) {
//                System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency(), donee.getRegisteredDate());
//
//                found = true;
//            }
//        }
//        if (!found) {
//            System.out.println(Color.YELLOW + "No donees found within the specified urgency category." + Color.RESET);
//        }
//        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
//    }
//}
