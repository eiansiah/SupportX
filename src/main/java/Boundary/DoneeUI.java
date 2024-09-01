package Boundary;

/*
 *  author: Siah E-Ian
 *  ID: 2307610
 * */

import Entity.Donee;

import Utilities.Message;
import Utilities.Color;
import Utilities.GeneralFunction;

import ADT.ListInterface;

import java.time.LocalDate;
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

    public static String obtainDoneeId(){
        Scanner scanner = new Scanner(System.in);
        String doneeId;

        doneeId = scanner.nextLine().trim();

        if (doneeId.isEmpty()) {
            Message.displayEmptyInputMessage("Please enter a valid Donee ID.");
        }

        return doneeId;
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

    public static void deleteDoneeUI(){
        System.out.println("\nWhich donee would you like to delete?");
        System.out.print("Please enter the Donee ID (Or X to return) : ");
    }

    public static void displayIndividualDoneeDetailsUI(String doneeID, String name, String email, String phone, String address, String doneeType, String itemCategory, String doneeUrgency, LocalDate registeredDate){
        System.out.println("\n**Donee Details**");
        GeneralFunction.repeatPrint("-",50);
        DoneeUI.printEmptyLine();
        System.out.printf("%-25s: %s%n", "Donee ID", doneeID);
        System.out.printf("%-25s: %s%n", "Name", name);
        System.out.printf("%-25s: %s%n", "Email", email);
        System.out.printf("%-25s: %s%n", "Phone", phone);
        System.out.printf("%-25s: %s%n", "Address", address);
        System.out.printf("%-25s: %s%n", "Donee Type", doneeType);
        System.out.printf("%-25s: %s%n", "Item Category Required", itemCategory);
        System.out.printf("%-25s: %s%n", "Donee Urgency", doneeUrgency);
        System.out.printf("%-25s: %s%n", "Registered Date", registeredDate);
        GeneralFunction.repeatPrint("-",50);
        DoneeUI.printEmptyLine();
    }

    public static String deleteDoneeConfirmationUI(){
        System.out.print("Are you sure you want to delete this donee? (Y/N): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public static void deleteDoneeSuccessfulMessage(String doneeId){
        System.out.println(Color.BRIGHT_GREEN + "\nDonee with ID " + doneeId + " has been deleted successfully!" + Color.RESET);
    }

    public static void deleteDoneeOperationAbortMessage(){
        System.out.println(Color.BRIGHT_YELLOW + "\nDelete Operation cancelled." + Color.RESET);
    }

    public static void modifyDoneeUI(){
        System.out.println("**Modify Donee Details**");
        System.out.print("\nWhich donee would you like to modify?");
        System.out.print("Please enter the Donee ID: ");
    }

    public static String modifyTypeUI(){
        System.out.println("\nWhich part do you want to modify?");
        System.out.println("1. Name");
        System.out.println("2. Email");
        System.out.println("3. Phone");
        System.out.println("4. Address");
        System.out.println("5. Donee Type");
        System.out.println("6. Item Category Required");
        System.out.println("7. Donee Urgency");
        System.out.println("X. Return to main menu\n");
        System.out.print("Please select an option (1-7 or X): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public static void updateDoneeSuccessfulMessage(String doneeId){
        System.out.println(Color.BRIGHT_GREEN + "\nDonee with ID " + doneeId + " has been updated successfully." + Color.RESET);
    }

    public static String displayDoneeTableUI(int start, int currentPage, int end, ListInterface<Donee> donees, int totalDonees){
        System.out.println("\n**LIST OF DONEES (Page " + (currentPage + 1) + ")**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency");
        System.out.println(String.format("%0" + 65 + "d", 0).replace("0", "-"));

        for (int i = start; i < end; i++) {
            Donee donee = donees.get(i);
            System.out.printf("%-10s %-25s %-15s %-15s %n", donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency());
        }
        System.out.println(String.format("%0" + 65 + "d", 0).replace("0", "-"));

        System.out.println("\nOptions:");

        if (currentPage > 0) {
            System.out.println("P - Previous Page");
        }
        if (end < totalDonees) {
            System.out.println("N - Next Page");
        }

        System.out.println("D - Individual Details (Enter donee ID to view details)");
        System.out.println("S - Sort Donee");
        System.out.println("X - Exit");
        System.out.print("\nSelect an option: ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public static void displayFirstPageMessage(){
        System.out.println("You are already on the first page.");
    }

    public static void displayLastPageMessage(){
        System.out.println("You are already on the last page.");
    }

    public static void displayDoneeUI(){
        System.out.print("Enter Donee ID to view details: ");
    }

    public static String sortDoneeUI(){
        System.out.println("\nSorting Criterion: ");
        System.out.println("1 - Sort by Donee Urgency (Priority)");
        System.out.println("2 - Sort by Ascending/Descending Order");
        System.out.print("\nSelect a sorting criterion: ");
        return scanner.nextLine().trim();
    }

    public static void displayDonationDetailsUI(String doneeID){
        System.out.println("\nDonations received by Donee ID: " + doneeID);
        GeneralFunction.repeatPrint("-", 100);
        System.out.printf("\n%-20s %-20s %-20s %-20s %-20s%n", "Distribution ID", "Item Code", "Item Name", "Quantity", "Received Date");
    }

    public static String filterDoneeUI(){
        System.out.println("\nFilter Donees by Category:");
        System.out.println("1. Donee Type");
        System.out.println("2. Donee Urgency");
        System.out.println("3. Return to main menu");
        System.out.print("\nSelect a category: ");
        return scanner.nextLine().trim();
    }

    public static String filterDoneeTypeUI(){
        System.out.println("\nFilter by Donee Types:");
        System.out.println("1. Individual");
        System.out.println("2. Organization");
        System.out.println("3. Family");
        System.out.println("4. Back");
        System.out.print("\nSelect a donee type: ");
        return scanner.nextLine().trim();
    }

    public static void individualFilterUI(){
        System.out.println("\n**Individual Type Donee List**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Registered Date");
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
    }

    public static void displayFilterDetailsUI(String doneeID, String name, String phone, String doneeTypeOrUrgency, LocalDate registeredDate){
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", doneeID, name, phone, doneeTypeOrUrgency, registeredDate);
    }

    public static void organizationFilterUI(){
        System.out.println("\n**Organization Type Donee List**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Registered Date");
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
    }

    public static void familyFilterUI(){
        System.out.println("\n**Family Type Donee List**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Type", "Registered Date");
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
    }

    public static String filterUrgencyTypeUI(){
        System.out.println("\nFilter by Donee Urgency:");
        System.out.println("1. Low");
        System.out.println("2. Moderate");
        System.out.println("3. High");
        System.out.println("4. Back");
        System.out.print("\nSelect a donee urgency category: ");
        return scanner.nextLine().trim();
    }

    public static void filterLowUrgencyUI(){
        System.out.println("\n**Low Urgency Donee List**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency", "Registered Date");
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
    }

    public static void filterModerateUrgencyUI(){
        System.out.println("\n**Moderate Urgency Donee List**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency", "Registered Date");
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
    }

    public static void filterHighUrgencyUI(){
        System.out.println("\n**High Urgency Donee List**\n");
        System.out.printf("%-10s %-25s %-15s %-15s %-20s %n", "Donee ID", "Donee Name", "Donee Phone", "Donee Urgency", "Registered Date");
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));
    }

    public static void reportDoneeUrgencyUI(int highUrgencyCount, int mediumUrgencyCount, int lowUrgencyCount){
        System.out.println("\n**Summary of Number of Donees by Donee Urgency**");
        GeneralFunction.repeatPrint("-", 48);

        System.out.printf("\n%-16s %-16s %-16s%n", centerString("High", 16), centerString("Moderate", 16), centerString("Low", 16));
        System.out.printf("%-16s %-16s %-16s%n", centerString(String.valueOf(highUrgencyCount), 16), centerString(String.valueOf(mediumUrgencyCount), 16), centerString(String.valueOf(lowUrgencyCount), 16));

        GeneralFunction.repeatPrint("-", 48);
        System.out.print("\n");
    }

    public static void reportDoneeTypeUI(int individualCount, int organizationCount, int familyCount){
        System.out.println("\n**Summary of Number of Donees by Donee Type**");
        GeneralFunction.repeatPrint("-", 48);

        System.out.printf("\n%-16s %-16s %-16s%n", centerString("Individual", 16), centerString("Organization", 16), centerString("Family", 16));
        System.out.printf("%-16s %-16s %-16s%n", centerString(String.valueOf(individualCount), 16), centerString(String.valueOf(organizationCount), 16), centerString(String.valueOf(familyCount), 16));

        GeneralFunction.repeatPrint("-", 48);
        System.out.print("\n");
    }

    public static void reportItemCategoryUI(int foodCount, int beverageCount, int clothingCount, int personalCount, int deviceCount, int medicineCount, int moneyCount){
        System.out.println("\n**Summary of Number of Donees by Item Category Required**");
        GeneralFunction.repeatPrint("-", 87);

        System.out.printf("\n%-8s %-10s %-10s %-15s %-16s %-10s %-13s %n",
                "Food", "Beverage", "Clothing", "Personal Care", "Medical Device", "Medicine", "Monetary Aid");

        // Print the counts centered under each label
        System.out.printf("%-8s %-10s %-10s %-15s %-16s %-10s %-13s %n", centerString(String.valueOf(foodCount), 8), centerString(String.valueOf(beverageCount), 10), centerString(String.valueOf(clothingCount), 10), centerString(String.valueOf(personalCount), 15), centerString(String.valueOf(deviceCount), 16), centerString(String.valueOf(medicineCount), 10), centerString(String.valueOf(moneyCount), 13));

        GeneralFunction.repeatPrint("-", 87);
        System.out.print("\n");
    }

    private static String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        String format = "%" + padding + "s%s%" + padding + "s";
        return String.format(format, "", text, "");
    }

    public static void viewDistributionUI(){
        System.out.println("**Donation Distribution Details for each Donee**");
        System.out.print("\nEnter Donee ID to view details: ");
    }

    public static void printEmptyLine(){
        System.out.print("\n");
    }
}
