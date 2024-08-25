package Main;

import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import java.util.Scanner;
import Main.Validation;

public class Test {

    public static void main(String[] args) {
        int option;
        DonorFileHandler fileHandler = new DonorFileHandler();
        fileHandler.checkAndCreateFile("donor.txt");

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n1. Add Donor");
            System.out.println("2. Delete Donor");
            System.out.println("3. Modify Donors");
            System.out.println("4. See All Donors");
            System.out.println("5. Terminate Session");
            System.out.print("What would you like to do : ");
            option = scanner.nextInt();

            switch (option) {
                //Add Donor
                case 1:
                    // Get the last ID from the file
                    String lastId = fileHandler.getLastDonorId("donor.txt");
                    // Increment to get the new ID
                    String newDonorId = fileHandler.incrementDonorId(lastId);
                    Donor donor = AddDonor(newDonorId);
                    // Save the donor with the new ID
                    fileHandler.saveData("donor.txt", donor);
                    break;
                //Delete Donor
                case 2:
                    //Check IF EMPTY
                    displayDonors();
                    // Get the ID to be deleted
                    System.out.print("\nWhich Donor Would you like to delete? Please enter their ID: ");
                    String donorIdToDelete = scanner.next().trim();
                    // Delete the donor
                    fileHandler.deleteData("donor.txt", donorIdToDelete);
                    break;

                // Modify Donor
                case 3:
                    displayDonors();
                    // Get the ID to be modified
                    System.out.print("\nWhich Donor Would you like to modify? Please enter their ID: ");
                    String donorIDToModify = scanner.next().trim();
                    // Read all donors from the file
                    ArrayList<Donor> donors = fileHandler.readData("donor.txt");
                    // Modify the donor using the modifyDonor method
                    modifyDonor(donorIDToModify, donors, fileHandler);
                    break;

                //See All Donor
                case 4:
                    displayDonors();
                    break;

                //Terminate
                default:
                    System.out.println("\nTerminating Session...");
                    break;

            }
        } while (option >= 1 && option <= 4);

    }

    public static Donor AddDonor(String donorId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter Donor details:");

        String name = Validation.validateName(scanner);
        String email = Validation.validateEmail(scanner);
        String phone = Validation.validatePhone(scanner);
        String category = Validation.validateCategory(scanner);
        String type = Validation.validateType(scanner);

        return new Donor(donorId, name, email, phone, category, type);
    }

    public static void modifyDonor(String donorIDToModify, ArrayList<Donor> donors, DonorFileHandler fileHandler) {
        Scanner scanner = new Scanner(System.in);
        boolean donorFound = false;

        for (Donor donorSelected : donors) {
            if (donorSelected.getId().equals(donorIDToModify)) {
                donorFound = true;
                String choice;

                do {
                    // Prompt user for which part to modify
                    System.out.println("\nWhich part do you want to modify?");
                    System.out.println("1. Name");
                    System.out.println("2. Email");
                    System.out.println("3. Phone");
                    System.out.println("4. Category");
                    System.out.println("5. Type");
                    System.out.println("X. Stop modifying");
                    System.out.print("Please select an option (1-5 or X): ");
                    choice = scanner.nextLine().trim();

                    switch (choice) {
                        case "1":
                            donorSelected.setName(Validation.validateName(scanner));
                            break;
                        case "2":
                            donorSelected.setEmail(Validation.validateEmail(scanner));
                            break;
                        case "3":
                            donorSelected.setPhone(Validation.validatePhone(scanner));
                            break;
                        case "4":
                            donorSelected.setCategory(Validation.validateCategory(scanner));
                            break;
                        case "5":
                            donorSelected.setType(Validation.validateType(scanner));
                            break;

                        case "x":
                            System.out.println("Exiting modification.");
                            break;
                        default:
                            System.out.println("Invalid choice. Please select a valid option.");
                            break;
                    }

                    if (!choice.equalsIgnoreCase("X")) {
                        System.out.println("Donor with ID " + donorIDToModify + " has been updated.");
                    }

                } while (!choice.equalsIgnoreCase("X"));

                // Update the donor in the file after all modifications
                fileHandler.updateMultipleData("donor.txt", donors);
                break;
            }
        }

        if (!donorFound) {
            System.out.println("Donor with ID " + donorIDToModify + " was not found.");
        }
    }

    public static void displayDonors() {
        DonorFileHandler fileHandler = new DonorFileHandler();
        ArrayList<Donor> donors = fileHandler.readData("donor.txt");

        System.out.println("\nLIST OF DONORS\n");
        System.out.printf("%-10s%-35s%-20s%-15s%-15s%-15s%n", "Donor ID", "Donor Name", "Donor Email", "Donor Phone", "Donor Category", "Donor Type");
        System.out.println(String.format("%0" + 120 + "d", 0).replace("0", "-"));
        for (Donor donor : donors) {
            System.out.printf("%-10s%-35s%-20s%-15s%-15s%-15s%n", donor.getId(), donor.getName(), donor.getEmail(), donor.getPhone(), donor.getCategory(), donor.getType());
        }
        System.out.println(String.format("%0" + 120 + "d", 0).replace("0", "-"));
    }
}
