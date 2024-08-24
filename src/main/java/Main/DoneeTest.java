package Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import FileHandling.DoneeFileHandler;
import FileHandling.DonorFileHandler;
import Libraries.ArrayList;

public class DoneeTest {

    public static void main(String[] args) {
        int choice;

        DoneeFileHandler fileHandler = new DoneeFileHandler();
        fileHandler.checkAndCreateFile("donee.txt");

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Add Donee");
            System.out.println("2. Delete Donee");
            System.out.println("3. Modify Donee");
            System.out.println("4. View Donee Details");
            System.out.println("5. Exit");

            while (true) { //Input Validation Loop
                System.out.print("What would you like to do? : ");
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
                case 1 -> {
                    // Get the last ID from the file
                    String lastId = fileHandler.getLastDoneeId("donee.txt");
                    // Increment to get the new ID
                    String newDoneeId = fileHandler.incrementDoneeId(lastId);
                    Donee donee = addDonee(newDoneeId);
                    // Save the donor with the new ID
                    fileHandler.saveData("donee.txt", donee);
                }
                //Delete Donee
                case 2 -> {

                }
                //Modify Donee
                case 3 -> {

                }
                //View Donee Details
                case 4 -> {
//                    displayDonors();
                }
                //Exit
                default -> {
                    System.out.println("\nExiting Program...");
                }
            }
        }while (choice !=5);

    }

    public static Donee addDonee(String doneeId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter Donee details:");

        String name;
        String email;
        String phone;
        String doneeType;
        String itemCategoryRequired;
        String doneeUrgency;

        return null;
    }

    public static void displayDonees() {
        DoneeFileHandler fileHandler = new DoneeFileHandler();
        ArrayList<Donee> donees = fileHandler.readData("donee.txt");

        System.out.println("\nLIST OF DONEES\n");
        System.out.printf("%-10s %-40s %-30s %-15s %-50s %-15s %-30s %-20s %-15s %n", "Donee ID", "Donee Name", "Donee Email", "Donee Phone", "Donee Address", "Donee Type", "Item Category Required", "Donee Urgency", "Registered Date");
        System.out.println(String.format("%0" + 120 + "d", 0).replace("0", "-"));
        for (Donee donee : donees) {
            System.out.printf("%-10s %-40s %-30s %-15s %-50s %-15s %-30s %-20s %-15s %n", donee.getDoneeID(), donee.getName(), donee.getEmail(), donee.getPhone(), donee.getAddress(), donee.getDoneeType(), donee.getItemCategoryRequired(), donee.getDoneeUrgency(), donee.getRegisteredDate());
        }
        System.out.println(String.format("%0" + 120 + "d", 0).replace("0", "-"));
    }
}


