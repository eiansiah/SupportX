package Main;

import java.util.Scanner;

public class DoneeTest {

    public static void main(String[] args) {
        int choice;
        
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
//                    String lastId = fileHandler.getLastDonorId("donor.txt");
//                    // Increment to get the new ID
//                    String newDonorId = fileHandler.incrementDonorId(lastId);
//                    Donor donor = AddDonor(newDonorId);
//                    // Save the donor with the new ID
//                    fileHandler.saveData("donee.txt", donee);
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
}
