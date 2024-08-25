package Main;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validation {

    public static String validateName(Scanner scanner) {
        String name;
        Pattern nameFormat = Pattern.compile("^[A-Za-z]{2}[A-Za-zâ€˜\\-/.]{1,30}$");
        do {
            System.out.print("\nName: ");
            name = scanner.nextLine().trim();
            Matcher matcher = nameFormat.matcher(name);
            if (!matcher.matches() || name.isEmpty()) {
                System.out.println("Invalid name. Only alphabets and spaces are allowed. Please enter a valid name.");
            }
        } while (!nameFormat.matcher(name).matches() || name.isEmpty());
        return name;
    }

    public static String validateEmail(Scanner scanner) {
        String email;
        Pattern emailFormat = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        do {
            System.out.print("\nEmail: ");
            email = scanner.nextLine().trim();
            Matcher matcher = emailFormat.matcher(email);
            if (!matcher.matches()) {
                System.out.println("Invalid email format. Please enter a valid email.");
            }
        } while (!emailFormat.matcher(email).matches());
        return email;
    }

    public static String validatePhone(Scanner scanner) {
        String phone;
        Pattern phoneFormat = Pattern.compile("^(\\d{10,11})$");
        do {
            System.out.print("\nPhone: ");
            phone = scanner.nextLine().trim();
            Matcher matcher = phoneFormat.matcher(phone);
            if (!matcher.matches()) {
                System.out.println("Invalid phone number format. Please enter a valid phone number (e.g.0123456789).");
            }
        } while (!phoneFormat.matcher(phone).matches());
        return phone;
    }

    public static String validateAddress(Scanner scanner) {
        String address;
        do { //Input Validation Loop
            System.out.print("\nAddress: ");
            address = scanner.nextLine().trim();  // Read the input as a string and trim any surrounding spaces

            if (address.isEmpty())
                System.out.println("Empty input detected. Please do not leave address empty.");

        } while (address.isEmpty());
        return address;
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

    public static String validateItemCategory(Scanner scanner) {
        String itemCategory;
        int choice = 0;

        System.out.println("\nItem Categories");
        System.out.println(String.format("%0" + 17 + "d", 0).replace("0", "-"));
        System.out.println("1. Food");
        System.out.println("2. Beverage");
        System.out.println("3. Clothing");
        System.out.println("4. Personal Care");
        System.out.println("5. Medical Device");
        System.out.println("6. Medicine");
        System.out.println("7. Monetary Aid");

        do {
            System.out.print("\nPlease select an item category required: ");

            String inputChoice = scanner.nextLine().trim();
            if (inputChoice.isEmpty()) {
                System.out.println("Empty input detected. Please enter a number between 1 and 7.");
            } else {
                try {
                    choice = Integer.parseInt(inputChoice);
                    if (choice >= 1 && choice <= 7) {
                        break;  // Exit loop for valid choice input
                    } else {
                        System.out.println("Invalid choice. Please select a valid item category (1-7).");
                    }
                } catch (NumberFormatException e) {  // Catch any non-integer inputs
                    System.out.println("Invalid input. Please enter a number between 1 and 7.");
                }
            }
        } while (true);

        switch (choice) {
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

    public static String validateDoneeType(Scanner scanner) {
        String type ;
        int choice = 0;

        System.out.println("\nDonee Types");
        System.out.println(String.format("%0" + 15 + "d", 0).replace("0", "-"));
        System.out.println("1. Individual");
        System.out.println("2. Organization");
        System.out.println("3. Family");

        do {
            System.out.print("\nPlease select a donee type: ");

            String inputChoice = scanner.nextLine().trim();
            if (inputChoice.isEmpty()) {
                System.out.println("Empty input detected. Please enter a number between 1 and 3.");
            } else {
                try {
                    choice = Integer.parseInt(inputChoice);
                    if (choice >= 1 && choice <= 3) {
                        break;  // Exit loop for valid choice input
                    } else {
                        System.out.println("Invalid choice. Please select a valid donee type (1-3).");
                    }
                } catch (NumberFormatException e) {  // Catch any non-integer inputs
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }
        } while (true);

        switch (choice) {
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

    public static String validateDoneeUrgency(Scanner scanner) {
        String doneeUrgency;
        int choice = 0;

        System.out.println("\nDonee Urgency Categories");
        System.out.println(String.format("%0" + 30 + "d", 0).replace("0", "-"));
        System.out.println("1. Low");
        System.out.println("2. Moderate");
        System.out.println("3. High");

        do {
            System.out.print("\nPlease select a donee urgency category: ");

            String inputChoice = scanner.nextLine().trim();
            if (inputChoice.isEmpty()) {
                System.out.println("Empty input detected. Please enter a number between 1 and 3.");
            } else {
                try {
                    choice = Integer.parseInt(inputChoice);
                    if (choice >= 1 && choice <= 3) {
                        break;  // Exit loop for valid choice input
                    } else {
                        System.out.println("Invalid choice. Please select a valid donee urgency category (1-3).");
                    }
                } catch (NumberFormatException e) {  // Catch any non-integer inputs
                    System.out.println("Invalid input. Please enter a number between 1 and 3.");
                }
            }
        } while (true);

        switch (choice) {
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
