package Utilities;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewValidation {

    public static boolean validateName(String name) {
        Pattern nameFormat = Pattern.compile("^[A-Za-z]{2}[A-Za-zâ€˜\\-/.]{1,30}$");
//        do {
//            System.out.print("\nName: ");
//            name = scanner.nextLine().trim();
            Matcher matcher = nameFormat.matcher(name);
        //                System.out.println("Invalid name. Only alphabets and spaces are allowed. Please enter a valid name.");
        return matcher.matches() && !name.isEmpty();
//        } while (!nameFormat.matcher(name).matches() || name.isEmpty());
    }

    public static boolean validateEmail(String email) {
//        String email;
        Pattern emailFormat = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
//        do {
//            System.out.print("\nEmail: ");
//            email = scanner.nextLine().trim();
            Matcher matcher = emailFormat.matcher(email);
//            if (!matcher.matches()) {
//                System.out.println("Invalid email format. Please enter a valid email.");
//            }
//        } while (!emailFormat.matcher(email).matches());
//        return email;
        return matcher.matches() && !email.isEmpty();
    }

    public static boolean validatePhone(String phone) {
//        String phone;
        Pattern phoneFormat = Pattern.compile("^(\\d{10,11})$");
//        do {
//            System.out.print("\nPhone: ");
//            phone = scanner.nextLine().trim();
            Matcher matcher = phoneFormat.matcher(phone);
//            if (!matcher.matches()) {
//                System.out.println("Invalid phone number format. Please enter a valid phone number (e.g.0123456789).");
//            }
//        } while (!phoneFormat.matcher(phone).matches());
//        return phone;
        return matcher.matches() && !phone.isEmpty();
    }

    public static boolean validateAddress(String address) {
//        String address;
//        do { //Input Validation Loop
//            System.out.print("\nAddress: ");
//            address = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces
//
//            if (address.isEmpty())
//                System.out.println("Empty input detected. Please do not leave address empty.");
//
//        } while (address.isEmpty());
        return !address.isEmpty();
    }

    public static String validateCategory(Scanner scanner) {
        String category;
        int choice = 0;

        do {
            if (choice != 0) {
                System.out.println("Invalid choice. Please select a valid category (1-3).");
            }

            System.out.println("\nPlease select a category:");
            System.out.println("1. Government");
            System.out.println("2. Private");
            System.out.println("3. Public");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-3).");
                scanner.next(); // Clear the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

        } while (choice < 1 || choice > 3);

        switch (choice) {
            case 1:
                category = "Government";
                break;
            case 2:
                category = "Private";
                break;
            default:
                category = "Public";
        }

        return category;
    }

    public static String validateItemCategory(int input) {
        String itemCategory;

        switch (input) {
            case 1:
                itemCategory = "Food";
                break;
            case 2:
                itemCategory = "Beverage";
                break;
            case 3:
                itemCategory = "Clothing";
                break;
            case 4:
                itemCategory = "Personal Care";
                break;
            case 5:
                itemCategory = "Medical Device";
                break;
            case 6:
                itemCategory = "Medicine";
                break;
            default:
                itemCategory = "Monetary Aid";
        }

        return itemCategory;
    }

    public static String validateType(Scanner scanner) {
        String type;
        int choice = 0;

        do {
            if (choice != 0) {
                System.out.println("Invalid choice. Please select a valid type (1-2).");
            }

            System.out.println("\nPlease select a type:");
            System.out.println("1. Individual");
            System.out.println("2. Organization");

            while (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number (1-2).\n");
                scanner.next(); // Clear the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

        } while (choice < 1 || choice > 2);

        if (choice == 1) {
            type = "Individual";
        } else {
            type = "Organization";
        }

        return type;
    }

    public static String validateDoneeType(int input) {
        String type;

        switch (input) {
            case 1:
                type = "Individual";
                break;
            case 2:
                type = "Organization";
                break;
            default:
                type = "Family";
        }

        return type;
    }

    public static String validateDoneeUrgency(int input) {
        String doneeUrgency;

        switch (input) {
            case 1:
                doneeUrgency = "Low";
                break;
            case 2:
                doneeUrgency = "Moderate";
                break;
            default:
                doneeUrgency = "High";
        }

        return doneeUrgency;
    }

}
