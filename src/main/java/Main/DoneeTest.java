package Main;

import java.time.LocalDate;
import java.util.Scanner;

import FileHandling.DoneeFileHandler;
import Libraries.ArrayList;
import Utilities.Validation;

public class DoneeTest {

    static DoneeFileHandler fileHandler = new DoneeFileHandler();

    public static void main(String[] args) {
        int choice;

        fileHandler.checkAndCreateFile("donee.txt");

        System.out.println("\nWelcome to Donee Management Subsystem!");
        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\nDonee Management Subsystem Main Menu\n");
            System.out.println("1. Add Donee");
            System.out.println("2. Delete Donee");
            System.out.println("3. Modify Donee");
            System.out.println("4. View Donee Details");
            System.out.println("5. Exit");

            while (true) { //Input Validation Loop
                System.out.print("\nWhat would you like to do? : ");
                String inputChoice = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces

                if (inputChoice.isEmpty()) {
                    System.out.println("Empty input detected. Please enter a number between 1 and 5.");
                } else {
                    try {
                        choice = Integer.parseInt(inputChoice);
                        if (choice >= 1 && choice <= 5) {
                            break;  // Exit loop for valid choice input
                        } else {
                            System.out.println("Invalid input. Please enter a number between 1 and 5.");
                        }
                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                        System.out.println("Invalid input. Please enter a number.");
                    }
                }
            }

            switch (choice) {
                //Add Donee
                case 1:
                    // Get the last ID from the file
                    String lastId = fileHandler.getLastDoneeId("donee.txt");
                    // Increment to get the new ID
                    String newDoneeId = fileHandler.incrementDoneeId(lastId);
                    Donee donee = addDonee(newDoneeId);
                    // Save the donor with the new ID
                    fileHandler.saveData("donee.txt", donee);
                    System.out.print("\nPress any key to return to main menu.....");
                    scanner.nextLine();
                    break;
                //Delete Donee
                case 2:
                    displayDonees();
                    deleteDonee();
                    System.out.print("\nPress any key to return to main menu.....");
                    scanner.nextLine();
                    break;
                //Modify Donee
                case 3:
                    displayDonees();
                    modifyDonee(readDonees());
                    System.out.print("\nPress any key to return to main menu.....");
                    scanner.nextLine();
                    break;
                //View Donee Details
                case 4:
                    displayDonees();
                    System.out.print("\nPress any key to return to main menu.....");
                    scanner.nextLine();
                    break;
                //Exit
                default:
                    System.out.println("\nExiting Program...");
            }
        }while (choice !=5);
    }

    public static String obtainDoneeId(){
        Scanner scanner = new Scanner(System.in);
        String doneeId;

        doneeId = scanner.nextLine().trim();

        if (doneeId.isEmpty()) {
            System.out.println("Empty input detected. Please enter a valid donee ID.");
        }

        return doneeId;
    }

    public static ArrayList<Donee> readDonees(){
        return fileHandler.readData("donee.txt");
    }

    public static Donee addDonee(String doneeId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nNew Donee Registration");
        System.out.println("**Please enter the details accordingly.**");

        String name = Validation.validateName(scanner);
        String email = Validation.validateEmail(scanner);
        String phone = Validation.validatePhone(scanner);
        String address = Validation.validateAddress(scanner);
        String doneeType = Validation.validateDoneeType(scanner);
        String itemCategory = Validation.validateItemCategory(scanner);
        String doneeUrgency = Validation.validateDoneeUrgency(scanner);
        LocalDate registeredDate = LocalDate.now();

        return new Donee(doneeId, name, email, phone, address, doneeType, itemCategory, doneeUrgency, registeredDate);
    }

    public static void deleteDonee() {
        String doneeId;
        do {
            System.out.print("\nWhich donee would you like to delete? Please enter the Donee ID: ");
            doneeId = obtainDoneeId();
        } while (doneeId.isEmpty());
        // Delete donee
        fileHandler.deleteData("donee.txt", doneeId);
    }

    public static void modifyDonee(ArrayList<Donee> donees) {
        Scanner scanner = new Scanner(System.in);
        boolean doneeFound = false;
        String doneeId;

        do {
            System.out.print("\nWhich donee would you like to modify? Please enter the Donee ID: ");
            doneeId = obtainDoneeId();
        } while (doneeId.isEmpty());

        for (Donee doneeSelected : donees) {
            if (doneeSelected.getDoneeID().equals(doneeId)) {
                doneeFound = true;
                char choice = ' ';

                do {
                    // Prompt user for which part to modify
                    System.out.println("\nWhich part do you want to modify?");
                    System.out.println("1. Name");
                    System.out.println("2. Email");
                    System.out.println("3. Phone");
                    System.out.println("4. Address");
                    System.out.println("5. Donee Type");
                    System.out.println("6. Item Category Required");
                    System.out.println("7. Donee Urgency");
                    System.out.println("X. Return to main menu\n");
                    System.out.print("Please select an option (1-5 or X): ");
                    String input = scanner.nextLine().trim();

                    if (input.isEmpty()) {
                        System.out.println("Empty input detected. Please enter an option between 1-7 and X.");
                        continue;
                    }

                    choice = input.toUpperCase().charAt(0);

                    if (Character.isDigit(choice) && choice >= '1' && choice <= '7' || choice == 'X') {
                        switch (choice) {
                            case '1':
                                doneeSelected.setName(Validation.validateName(scanner));
                                break;
                            case '2':
                                doneeSelected.setEmail(Validation.validateEmail(scanner));
                                break;
                            case '3':
                                doneeSelected.setPhone(Validation.validatePhone(scanner));
                                break;
                            case '4':
                                doneeSelected.setAddress(Validation.validateAddress(scanner));
                                break;
                            case '5':
                                doneeSelected.setDoneeType(Validation.validateDoneeType(scanner));
                                break;
                            case '6':
                                doneeSelected.setItemCategoryRequired(Validation.validateItemCategory(scanner));
                                break;
                            case '7':
                                doneeSelected.setDoneeUrgency(Validation.validateDoneeUrgency(scanner));
                                break;
                            default:
                                System.out.println("Returning to main menu.....");
                        }

                        if (choice != 'X'){
                            System.out.println("\nDonee with ID " + doneeId + " has been updated successfully.");
                        }
                    } else {
                        System.out.println("Invalid input. Please enter a valid option between 1-7 and X.");
                    }
                } while (choice != 'X');

                // Update the donor in the file after all modifications
                fileHandler.updateMultipleData("donee.txt", donees);
                break;
            }
        }

        if (!doneeFound) {
            System.out.println("\n**Donee with ID " + doneeId + " was not found**");
            System.out.println("Returning to main menu.....");
        }
    }

    public static void displayDonees() {
        ArrayList<Donee> donees = readDonees();

        System.out.println("\nLIST OF DONEES\n");
        System.out.printf("%-10s %-40s %-30s %-15s %-50s %-15s %-30s %-20s %-15s %n", "Donee ID", "Donee Name", "Donee Email", "Donee Phone", "Donee Address", "Donee Type", "Item Category Required", "Donee Urgency", "Registered Date");
        System.out.println(String.format("%0" + 200 + "d", 0).replace("0", "-"));
        for (Donee donee : donees) {
            System.out.printf("%-10s %-40s %-30s %-15s %-50s %-15s %-30s %-20s %-15s %n", donee.getDoneeID(), donee.getName(), donee.getEmail(), donee.getPhone(), donee.getAddress(), donee.getDoneeType(), donee.getItemCategoryRequired(), donee.getDoneeUrgency(), donee.getRegisteredDate());
        }
        System.out.println(String.format("%0" + 200 + "d", 0).replace("0", "-"));
    }
}
