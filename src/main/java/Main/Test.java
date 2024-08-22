package Main;

import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        int option;
        DonorFileHandler fileHandler = new DonorFileHandler();

        do {
            Scanner scanner = new Scanner(System.in);

            System.out.println("1. Add Donor");
            System.out.println("2. Delete Donor");
            System.out.println("3. Modify Donor");
            System.out.println("4. See All Donor");
            System.out.println("5. Terminate Session");
            System.out.print("What would you like to do : ");
            option = scanner.nextInt();

            switch (option) {
                //Add Donor
                case 1 -> {
                    // Get the last ID from the file
                    String lastId = fileHandler.getLastDonorId("donor.txt");
                    // Increment to get the new ID
                    String newDonorId = fileHandler.incrementDonorId(lastId);
                    Donor donor = AddDonor(newDonorId);
                    // Save the donor with the new ID
                    fileHandler.saveData("donor.txt", donor);
                }
                //Delete Donor
                case 2 -> {

                }
                //Delete Donor
                case 3 -> {

                }
                //Delete Donor
                case 4 -> {
                    displayDonors();
                }
                //Terminate
                default -> {
                    System.out.println("\nTerminating Session...");
                }
            }
        }while (option >= 1 && option <= 4);




    }

    public static Donor AddDonor(String donorId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter Donor details:");

        String name;
        String email;
        String phone;
        String category;
        String type;

        Pattern nameFormat = Pattern.compile("^[A-Za-z]{2}[A-Za-z‘\\-/.]{1,30}$");
        do {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            Matcher matcher = nameFormat.matcher(name);
            if (!matcher.matches() || name.isEmpty()) {
                System.err.println("Invalid name. Only alphabets and spaces are allowed. Please enter a valid name.");
            }
        } while (!nameFormat.matcher(name).matches() || name.isEmpty());

        Pattern emailFormat = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            Matcher matcher = emailFormat.matcher(email);
            if (!matcher.matches()) {
                System.err.println("Invalid email format. Please enter a valid email.");
            }
        } while (!emailFormat.matcher(email).matches());

        Pattern phoneFormat = Pattern.compile("^(\\d{10})$");
        do {
            System.out.print("Phone: ");
            phone = scanner.nextLine().trim();
            Matcher matcher = phoneFormat.matcher(phone);
            if (!matcher.matches()) {
                System.err.println("Invalid phone number format. Please enter a valid phone number (e.g.1234567890).");
            }
        } while (!phoneFormat.matcher(phone).matches());

        do {
            System.out.print("Please enter a valid category (e.g.: Government, Private, Public): ");
            category = scanner.nextLine().trim();

            if (!category.equals("Government") && !category.equals("Private") && !category.equals("Public")) {
                System.err.println("Invalid category selected. Please enter a valid category (e.g.: Government, Private, Public).");
            }
        } while (!category.equals("Government") && !category.equals("Private") && !category.equals("Public"));

        do {
            System.out.print("Please enter a valid type (e.g.: Individual, Organization): ");
            type = scanner.nextLine().trim();

            if (!type.equals("Individual") && !type.equals("Organization")) {
                System.err.println("Invalid type selected. Please enter a valid type (e.g.: Individual, Organization).");
            }
        } while (!type.equals("Individual") && !type.equals("Organization"));

        return new Donor(donorId, name, email, phone, category, type);
    }

    public static void displayDonors() {
        DonorFileHandler fileHandler = new DonorFileHandler();
        ArrayList<Donor> donors = fileHandler.readData("Donor.txt");

        System.out.println("\nLIST OF DONORS\n");
        System.out.printf("%-10s%-35s%-20s%-15s%-15s%-15s%n", "Donor ID", "Donor Name" ,"Donor Email", "Donor Phone", "Donor Category", "Donor Type");
        System.out.println(String.format("%0" + 120 + "d", 0).replace("0", "-"));
        for (Donor donor : donors) {
            System.out.printf("%-10s%-35s%-20s%-15s%-15s%-15s%n", donor.getId(), donor.getName(), donor.getEmail(), donor.getPhone(), donor.getCategory(),donor.getType());
        }
        System.out.println(String.format("%0" + 120 + "d", 0).replace("0", "-"));
    }

}


