package Main;

import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {

    public static void main(String[] args) {
        DonorFileHandler fileHandler = new DonorFileHandler();

        // Get the last ID from the file
        String lastId = fileHandler.getLastDonorId("donor.txt");

        // Increment to get the new ID
        String newDonorId = fileHandler.incrementDonorId(lastId);

        Donor donor = AddDonor(newDonorId);

        // Save the donor with the new ID
        fileHandler.saveData("donor.txt", donor);

    }

    public static Donor AddDonor(String donorId) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("\nEnter Donor details:");

        String name;
        String email;
        String phone;
        String category;
        String type;

        Pattern nameFormat = Pattern.compile("^[A-Za-z]{2}[A-Za-z/ ]*$");
        do {
            System.out.print("Name: ");
            name = scanner.nextLine();
            Matcher matcher = nameFormat.matcher(name);
            if (!matcher.matches() || name.isEmpty()) {
                System.err.println("Invalid name. Only alphabets and spaces are allowed. Please enter a valid name.");
            }
        } while (!nameFormat.matcher(name).matches() || name.isEmpty());

        Pattern emailFormat = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        do {
            System.out.print("Email: ");
            email = scanner.nextLine();
            Matcher matcher = emailFormat.matcher(email);
            if (!matcher.matches()) {
                System.err.println("Invalid email format. Please enter a valid email.");
            }
        } while (!emailFormat.matcher(email).matches());

        Pattern phoneFormat = Pattern.compile("^(\\d{10})$");
        do {
            System.out.print("Phone: ");
            phone = scanner.nextLine();
            Matcher matcher = phoneFormat.matcher(phone);
            if (!matcher.matches()) {
                System.err.println("Invalid phone number format. Please enter a valid phone number (e.g.1234567890).");
            }
        } while (!phoneFormat.matcher(phone).matches());

        do {
            System.out.print("Please enter a valid category (e.g.: Government, Private, Public): ");
            category = scanner.nextLine();

            if (!category.equals("Government") && !category.equals("Private") && !category.equals("Public")) {
                System.err.println("Invalid category selected. Please enter a valid category (e.g.: Government, Private, Public).");
            }
        } while (!category.equals("Government") && !category.equals("Private") && !category.equals("Public"));


        do {
            System.out.print("Please enter a valid type (e.g.: Individual, Organization): ");
            type = scanner.nextLine();

            if (!type.equals("Individual") && !type.equals("Organization")) {
                System.err.println("Invalid type selected. Please enter a valid type (e.g.: Individual, Organization).");
            }
        } while (!type.equals("Individual") && !type.equals("Organization"));

        return new Donor(donorId, name, email, phone, category, type);
    }

}

