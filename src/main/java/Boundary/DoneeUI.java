package Boundary;

import java.util.Scanner;

public class DoneeUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void mainWelcomeMessage() {
        System.out.println("\nWelcome to Donee Management Subsystem!");
    }

    public static String getMainMenuChoice(){
        System.out.println("\nDonee Management Subsystem Main Menu\n");
        System.out.println("1. Add Donee");
        System.out.println("2. Delete Donee");
        System.out.println("3. Modify Donee");
        System.out.println("4. View Donee Details");
        System.out.println("5. View donations received by donee");
        System.out.println("6. Filter Donee");
        System.out.println("7. View summary reports");
        System.out.println("8. Exit");
        System.out.print("\nSelect an option to proceed : ");

        return scanner.nextLine().trim();
    }

    public static void addDoneeUI() {
        System.out.println("\nNew Donee Registration");
        System.out.println("**Please enter the details accordingly.**");
    }

    public static String inputDoneeNameUI() {
        System.out.print("\nEnter new donee name: ");
        return scanner.nextLine().trim();
    }

    public static String inputDoneeEmailUI(){
        System.out.print("\nEnter new donee email: ");
        return scanner.nextLine().trim();
    }

    public static String inputDoneePhoneUI(){
        System.out.print("\nEnter new donee phone: ");
        return scanner.nextLine().trim();
    }

    public static String inputDoneeAddressUI(){
        System.out.print("\nEnter new donee address: ");
        return scanner.nextLine().trim();
    }

    public static String inputDoneeTypeUI(){
        System.out.println("\nDonee Types");
        System.out.println(String.format("%0" + 15 + "d", 0).replace("0", "-"));
        System.out.println("1. Individual");
        System.out.println("2. Organization");
        System.out.println("3. Family");
        System.out.print("\nPlease enter new donee type: ");

        return scanner.nextLine().trim();
    }

    public static String inputItemCategoryUI(){
        System.out.println("\nItem Categories");
        System.out.println(String.format("%0" + 17 + "d", 0).replace("0", "-"));
        System.out.println("1. Food");
        System.out.println("2. Beverage");
        System.out.println("3. Clothing");
        System.out.println("4. Personal Care");
        System.out.println("5. Medical Device");
        System.out.println("6. Medicine");
        System.out.println("7. Monetary Aid");
        System.out.print("\nPlease select a new item category required: ");

        return scanner.nextLine().trim();
    }

    public static String inputDoneeUrgencyUI(){
        System.out.println("\nDonee Urgency Categories");
        System.out.println(String.format("%0" + 30 + "d", 0).replace("0", "-"));
        System.out.println("1. Low");
        System.out.println("2. Moderate");
        System.out.println("3. High");
        System.out.print("\nPlease select a new donee urgency: ");

        return scanner.nextLine().trim();
    }

}
