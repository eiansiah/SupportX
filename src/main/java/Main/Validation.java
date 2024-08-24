package Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static String validateName(Scanner scanner) {
        String name;
        Pattern nameFormat = Pattern.compile("^[A-Za-z]{2}[A-Za-zâ€˜\\-/.]{1,30}$");
        do {
            System.out.print("Name: ");
            name = scanner.nextLine().trim();
            Matcher matcher = nameFormat.matcher(name);
            if (!matcher.matches() || name.isEmpty()) {
                System.err.print("Invalid name. Only alphabets and spaces are allowed. Please enter a valid name.\n");
            }
        } while (!nameFormat.matcher(name).matches() || name.isEmpty());
        return name;
    }

    public static String validateEmail(Scanner scanner) {
        String email;
        Pattern emailFormat = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        do {
            System.out.print("Email: ");
            email = scanner.nextLine().trim();
            Matcher matcher = emailFormat.matcher(email);
            if (!matcher.matches()) {
                System.err.println("Invalid email format. Please enter a valid email.");
            }
        } while (!emailFormat.matcher(email).matches());
        return email;
    }

    public static String validatePhone(Scanner scanner) {
        String phone;
        Pattern phoneFormat = Pattern.compile("^(\\d{10})$");
        do {
            System.out.print("Phone: ");
            phone = scanner.nextLine().trim();
            Matcher matcher = phoneFormat.matcher(phone);
            if (!matcher.matches()) {
                System.err.println("Invalid phone number format. Please enter a valid phone number (e.g.1234567890).");
            }
        } while (!phoneFormat.matcher(phone).matches());
        return phone;
    }

    public static String validateCategory(Scanner scanner) {
        String category;
        int choice = 0;

        do {
            if (choice != 0) {
                System.err.println("Invalid choice. Please select a valid category (1-3).");
            }

            System.out.println("Please select a category:");
            System.out.println("1. Government");
            System.out.println("2. Private");
            System.out.println("3. Public");

            while (!scanner.hasNextInt()) {
                System.err.println("Invalid input. Please enter a number (1-3).");
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
            case 3:
                category = "Public";
                break;
            default:
                category = ""; // This should never happen due to the do-while loop
                break;
        }

        return category;
    }

    public static String validateType(Scanner scanner) {
        String type;
        int choice = 0;

        do {
            if (choice != 0) {
                System.err.println("Invalid choice. Please select a valid type (1-2).");
            }

            System.out.println("Please select a type:");
            System.out.println("1. Individual");
            System.out.println("2. Organization");

            while (!scanner.hasNextInt()) {
                System.err.println("Invalid input. Please enter a number (1-2).");
                scanner.next(); // Clear the invalid input
            }

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

        } while (choice < 1 || choice > 2);

        switch (choice) {
            case 1:
                type = "Individual";
                break;
            case 2:
                type = "Organization";
                break;
            default:
                System.err.println("Invalid choice. Please select a valid type (1-2).");
                type = null;
                break;
        }

        return type;
    }

}
