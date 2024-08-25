package Main;

import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import Utilities.Validation;
import Utilities.DonorSorter;

import java.util.Scanner;

public class Test {

    public static void main(String[] args) {
        int option;
        DonorFileHandler fileHandler = new DonorFileHandler();
        fileHandler.checkAndCreateFile("donor.txt");

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("\n1. Add Donor");
            System.out.println("2. Remove Donor");
            System.out.println("3. Update Donors");
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
                    System.out.print("\nWhich Donor Would you like to remove? Please enter their ID: ");
                    String donorIdToDelete = scanner.next().trim();
                    // Delete the donor
                    fileHandler.deleteData("donor.txt", donorIdToDelete);
                    break;

                // Modify Donor
                case 3:
                    displayDonors();
                    // Get the ID to be modified
                    System.out.print("\nWhich Donor Would you like to update? Please enter their ID: ");
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
                    System.out.println("\nWhich part do you want to update?");
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

        Scanner scanner = new Scanner(System.in);
        int pageSize = 10;  // Number of donors to display per page
        int currentPage = 0;
        int totalDonors = donors.size();
        boolean done = false;

        while (!done) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, totalDonors);

            System.out.println("\nLIST OF DONORS (Page " + (currentPage + 1) + ")\n");
            System.out.println(String.format("%0" + 45 + "d", 0).replace("0", "-"));
            System.out.printf("%-10s%-35s%n", "Donor ID", "Donor Name");
            System.out.println(String.format("%0" + 45 + "d", 0).replace("0", "-"));

            for (int i = start; i < end; i++) {
                Donor donor = donors.get(i);
                System.out.printf("%-10s%-35s%n", donor.getId(), donor.getName());
            }
            System.out.println(String.format("%0" + 45 + "d", 0).replace("0", "-"));

            System.out.println("\nOptions:");
            if (currentPage > 0) {
                System.out.println("P - Previous Page");
            }
            if (end < totalDonors) {
                System.out.println("N - Next Page");
            }
            System.out.println("D - Details (Enter donor ID to view details)");
            System.out.println("S - Sort Data");
            System.out.println("X - Exit");

            System.out.print("Select an option: ");
            String option = scanner.nextLine().trim().toUpperCase();

            switch (option) {
                case "P":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        System.out.println("You are already on the first page.");
                    }
                    break;
                case "N":
                    if (end < totalDonors) {
                        currentPage++;
                    } else {
                        System.out.println("You are already on the last page.");
                    }
                    break;
                case "D":
                    System.out.print("Enter Donor ID to view details: ");
                    String donorID = scanner.nextLine().trim();

                    Donor selectedDonor = null;
                    for (Donor donor : donors) {
                        if (donor.getId().equals(donorID)) {
                            selectedDonor = donor;
                            break;
                        }
                    }

                    if (selectedDonor != null) {
                        System.out.println("\nDonor Details:");
                        System.out.printf("%-15s: %s%n", "ID", selectedDonor.getId());
                        System.out.printf("%-15s: %s%n", "Name", selectedDonor.getName());
                        System.out.printf("%-15s: %s%n", "Email", selectedDonor.getEmail());
                        System.out.printf("%-15s: %s%n", "Phone", selectedDonor.getPhone());
                        System.out.printf("%-15s: %s%n", "Category", selectedDonor.getCategory());
                        System.out.printf("%-15s: %s%n", "Type", selectedDonor.getType());
                    } else {
                        System.out.println("Donor with ID " + donorID + " not found.");
                    }
                    done = true;
                    break;

                case "S":
                    System.out.println("\n1 - Sort by Name Ascending");
                    System.out.println("2 - Sort by Name Descending");
                    System.out.println("3 - Sort by ID Descending");
                    System.out.print("Choose a sorting criterion: ");

                    int sortOption = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    switch (sortOption) {
                        case 1:
                            DonorSorter.sortName(donors);
                            break;
                        case 2:
                            DonorSorter.sortNameDescending(donors);
                            break;
                        case 3:
                            DonorSorter.reverseID(donors);
                            break;
                        default:
                            System.out.println("Invalid sorting option.");
                            break;
                    }
                    break;

                case "X":
                    done = true;
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }


}
