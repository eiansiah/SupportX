package Boundary;

import Entity.Donor;
import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import Libraries.Color;
import Utilities.DonorFilter;
import Utilities.DonorSorter;
import Utilities.Validation;
import Utilities.Message;

import java.util.Scanner;

public class DonorFunctions {

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

    public static void deleteDonor(String donorIDToDelete, ArrayList<Donor> donors, DonorFileHandler fileHandler) {
        Scanner scanner = new Scanner(System.in);
        Donor selectedDonor = null;

        // Search for the donor by ID
        for (Donor donor : donors) {
            if (donor.getId().equals(donorIDToDelete)) {
                selectedDonor = donor;
                break;
            }
        }

        if (selectedDonor != null) {
            // Display the donor's information before deletion
            System.out.println("\nDonor Details:");
            System.out.printf("%-15s: %s%n", "ID", selectedDonor.getId());
            System.out.printf("%-15s: %s%n", "Name", selectedDonor.getName());
            System.out.printf("%-15s: %s%n", "Email", selectedDonor.getEmail());
            System.out.printf("%-15s: %s%n", "Phone", selectedDonor.getPhone());
            System.out.printf("%-15s: %s%n", "Category", selectedDonor.getCategory());
            System.out.printf("%-15s: %s%n", "Type", selectedDonor.getType());

            System.out.print("\nAre you sure you want to delete this donor? (Y/N): ");
            String confirmation = scanner.nextLine().trim().toUpperCase();

            if (confirmation.equals("Y")) {
                donors.remove(selectedDonor);
                fileHandler.updateMultipleData("donor.txt", donors);
                System.out.println(Color.RED + "Donor with ID " + donorIDToDelete + " has been deleted." + Color.RESET);
            } else {
                System.out.println(Color.YELLOW + "Deletion cancelled." + Color.RESET);
            }
        } else {
            Message.displayDataNotFoundMessage("Donor with ID " + donorIDToDelete + " was not found.");
        }
    }

    public static void modifyDonor(String donorIDToModify, ArrayList<Donor> donors, DonorFileHandler fileHandler) {
        Scanner scanner = new Scanner(System.in);
        Donor selectedDonor = null;

        // Search for the donor by ID
        for (Donor donor : donors) {
            if (donor.getId().equals(donorIDToModify)) {
                selectedDonor = donor;
                break;
            }
        }

        if (selectedDonor != null) {
            // Display the donor's information before modification
            System.out.println("\nDonor Details:");
            System.out.printf("%-15s: %s%n", "ID", selectedDonor.getId());
            System.out.printf("%-15s: %s%n", "Name", selectedDonor.getName());
            System.out.printf("%-15s: %s%n", "Email", selectedDonor.getEmail());
            System.out.printf("%-15s: %s%n", "Phone", selectedDonor.getPhone());
            System.out.printf("%-15s: %s%n", "Category", selectedDonor.getCategory());
            System.out.printf("%-15s: %s%n", "Type", selectedDonor.getType());

            System.out.print("\nDo you want to proceed with modifying this donor? (Y/N): ");
            String confirmation = scanner.nextLine().trim().toUpperCase();

            if (confirmation.equals("Y")) {
                String choice;

                do {
                    // Prompt user for which part to modify
                    System.out.println("\nWhich part do you want to update?");
                    System.out.println("1 - Name");
                    System.out.println("2 - Email");
                    System.out.println("3 - Phone");
                    System.out.println("4 - Category");
                    System.out.println("5 - Type");
                    System.out.println("X - Stop updating");
                    System.out.print("Please select an option (1-5 or X): ");
                    choice = scanner.nextLine().trim();

                    switch (choice) {
                        case "1":
                            selectedDonor.setName(Validation.validateName(scanner));
                            break;
                        case "2":
                            selectedDonor.setEmail(Validation.validateEmail(scanner));
                            break;
                        case "3":
                            selectedDonor.setPhone(Validation.validatePhone(scanner));
                            break;
                        case "4":
                            selectedDonor.setCategory(Validation.validateCategory(scanner));
                            break;
                        case "5":
                            selectedDonor.setType(Validation.validateType(scanner));
                            break;

                        case "X":
                            Message.displayEndUpdateMessage();
                            break;
                        default:
                            Message.displayInvalidChoiceMessage("Please select a valid option.");
                            break;
                    }

                    if (!choice.equalsIgnoreCase("X")) {
                        System.out.println(Color.GREEN + "Donor with ID " + donorIDToModify + " has been updated." + Color.RESET);
                    }

                } while (!choice.equalsIgnoreCase("X"));

                // Update the donor in the file after all modifications
                fileHandler.updateMultipleData("donor.txt", donors);

            } else {
                System.out.println(Color.YELLOW + "Modification cancelled." + Color.RESET);
            }
        } else {
            Message.displayDataNotFoundMessage("Donor with ID " + donorIDToModify + " was not found.");
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
            System.out.println("F - Filter Donor");
            System.out.println("S - Sort Donor");
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
                        System.out.println(Color.BRIGHT_YELLOW + "Donor with ID " + donorID + " not found." + Color.RESET);
                    }
                    done = true;
                    break;

                case "F":
                    System.out.println("\nFilter Options:");
                    System.out.println("1 - Filter by Name Starting Letter");
                    System.out.println("2 - Filter by Category");
                    System.out.println("3 - Filter by Type");
                    System.out.print("Select a filter option: ");
                    int filterChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (filterChoice == 1) {
                        System.out.print("Enter starting letter: ");
                        String input = scanner.nextLine().toUpperCase().trim();

                        // Error handling for empty input
                        if (input.isEmpty()) {
                            Message.displayInvalidInputMessage("Starting letter cannot be empty.");
                        } else {
                            char letter = input.toUpperCase().charAt(0);
                            donors = DonorFilter.filterByName(donors, letter);

                            // Error handling for no donors found after filtering
                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found with names starting with " + letter + ".");
                            }
                        }
                    } else if (filterChoice == 2) {
                        System.out.println("\nSelect category:");
                        System.out.println("1. Government");
                        System.out.println("2. Private");
                        System.out.println("3. Public");
                        System.out.print("Enter category number: ");
                        String category = null;
                        int categoryChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (categoryChoice) {
                            case 1:
                                category = "Government";
                                break;
                            case 2:
                                category = "Private";
                                break;
                            case 3:
                                category = "Public";
                                break;
                            default:
                                Message.displayInvalidChoiceMessage("Please select a valid option.");
                                break;
                        }

                        if (category != null) {
                            donors = DonorFilter.filterByCategory(donors, category);

                            // Error handling for no donors found after filtering
                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found in the '" + category + "' category.");System.out.println(Color.BRIGHT_YELLOW + "No donors found in the '" + category + "' category." + Color.RESET);
                            }
                        }
                    } else if (filterChoice == 3) {
                        System.out.println("\nSelect type:");
                        System.out.println("1. Individual");
                        System.out.println("2. Organization");
                        System.out.print("Enter type number: ");
                        String type = null;
                        int typeChoice = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        switch (typeChoice) {
                            case 1:
                                type = "Individual";
                                break;
                            case 2:
                                type = "Organization";
                                break;
                            default:
                                Message.displayInvalidChoiceMessage("Please select a valid option.");
                                break;
                        }

                        if (type != null) {
                            donors = DonorFilter.filterByType(donors, type);

                            // Error handling for no donors found after filtering
                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found of type '" + type + "'.");
                            }
                        }
                    } else {
                        // Error handling for invalid filter choice
                        Message.displayInvalidChoiceMessage("Please select a valid option.");
                    }


                    // Update totalDonors after filtering
                    totalDonors = donors.size();
                    currentPage = 0;  // Reset to first page after applying filter
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
                            Message.displayInvalidChoiceMessage("Please select a valid sorting option.");
                            break;
                    }
                    break;

                case "X":
                    done = true;
                    break;

                default:
                    Message.displayInvalidChoiceMessage("Please try again.");
                    break;
            }
        }
    }
}
