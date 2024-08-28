package Boundary;

import Entity.Donor;
import Libraries.ArrayList;
import Libraries.Color;
import Utilities.Message;

import java.util.Scanner;

public class DonorUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void DonorWelcomeMessage() {
        System.out.println("\nEntering Donor System!");
    }

    public static String getMainMenuChoice(){
        System.out.println("\nDonor System Main Menu\n");
        System.out.println("1. Add Donor");
        System.out.println("2. Delete Donor");
        System.out.println("3. Update Donor");
        System.out.println("4. View All Donor Details");
        System.out.println("5. View Donor Donations");
        System.out.println("6. View summary reports");
        System.out.println("7. Exit");
        System.out.print("\nSelect an option to proceed : ");

        return scanner.next().trim();
    }

    public static void addDonorUI() {
        System.out.println("\nEnter Donor details:");
    }

    public static String inputDonorNameUI() {
        System.out.print("Enter new donor name: ");
        return scanner.nextLine().trim();
    }

    public static String inputDonorEmailUI(){
        System.out.print("Enter new donor email: ");
        return scanner.nextLine().trim();
    }

    public static String inputDonorPhoneUI(){
        System.out.print("Enter new donor phone: ");
        return scanner.nextLine().trim();
    }

    public static String inputDonorCategoryUI(){
        System.out.println("\nSelect category:");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.println("3. Public");
        System.out.print("Enter new category number: ");

        return scanner.next().trim();
    }

    public static String inputDonorTypeUI(){
        System.out.println("\nSelect types:");
        System.out.println("1. Individual");
        System.out.println("2. Organization");
        System.out.print("Please enter new donor type: ");

        return scanner.next().trim();
    }

    public static String inputRemoveDonorIDUI(){
        // Get the ID to be deleted
        System.out.print("\nWhich Donor Would you like to remove? Please enter their ID: ");
        return scanner.next().trim();
    }

    public static String inputUpdateDonorIDUI(){
        // Get the ID to be deleted
        System.out.print("\nWhich Donor Would you like to update? Please enter their ID: ");
        return scanner.next().trim();
    }

    public static String deleteConfirmation(String id, String name, String email, String phone , String category, String type){

        displaySelectedDonorDetail(id, name, email, phone, category, type);

        System.out.print("\nAre you sure you want to delete this donor? (Y/N): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public static void displaySelectedDonorDetail(String id, String name, String email, String phone , String category, String type){
        // Display the donor's information before deletion
        System.out.println("\nDonor Details:");
        System.out.printf("%-15s: %s%n", "ID", id);
        System.out.printf("%-15s: %s%n", "Name", name);
        System.out.printf("%-15s: %s%n", "Email", email);
        System.out.printf("%-15s: %s%n", "Phone", phone);
        System.out.printf("%-15s: %s%n", "Category", category);
        System.out.printf("%-15s: %s%n", "Type", type);
    }

    public static String displayDonorToBeUpdated(String id, String name, String email, String phone , String category, String type){
        // Display the donor's information before deletion
        System.out.println("\nDonor Details:");
        System.out.printf("%-15s: %s%n", "ID", id);
        System.out.printf("%-15s: %s%n", "Name", name);
        System.out.printf("%-15s: %s%n", "Email", email);
        System.out.printf("%-15s: %s%n", "Phone", phone);
        System.out.printf("%-15s: %s%n", "Category", category);
        System.out.printf("%-15s: %s%n", "Type", type);

        System.out.print("\nDo you want to proceed with modifying this donor? (Y/N): ");
        return scanner.nextLine().trim().toUpperCase();
    }

    public static String promptUpdatePart(){
        // Prompt user for which part to modify
        System.out.println("\nWhich part do you want to update?");
        System.out.println("1 - Name");
        System.out.println("2 - Email");
        System.out.println("3 - Phone");
        System.out.println("4 - Category");
        System.out.println("5 - Type");
        System.out.println("X - Stop updating");
        System.out.print("Please select an option (1-5 or X): ");
        return scanner.nextLine().trim();
    }

    public static void displayDeleteDonorMsg(String donorIDToDelete){
        System.out.println(Color.RED + "Donor with ID " + donorIDToDelete + " has been deleted." + Color.RESET);
    }

    public static void displayUpdatedDonorMsg(String donorIDToModify){
        System.out.println(Color.RED + "Donor with ID " + donorIDToModify + " has been deleted." + Color.RESET);
    }

    public static String displayDonorTable(int pageSize, int currentPage, int totalDonors, ArrayList<Donor> donors, int start, int end,boolean showCategory, boolean showType){
        System.out.println("\nLIST OF DONORS (Page " + (currentPage + 1) + ")\n");
        // Adjust the header based on what needs to be displayed

        if (showType && showCategory) {
            System.out.printf("%-10s%-20s%-20s%-15s%n", "Donor ID", "Donor Name", "Category", "Type");
        } else if (showCategory) {
            System.out.printf("%-10s%-20s%-20s%n", "Donor ID", "Donor Name", "Category");
        }else if (showType) {
            System.out.printf("%-10s%-20s%-20s%n", "Donor ID", "Donor Name", "Type");
        } else {
            System.out.printf("%-10s%-35s%n", "Donor ID", "Donor Name");
        }

        System.out.println(String.format("%0" + 70 + "d", 0).replace("0", "-"));

        for (int i = start; i < end; i++) {
            Donor donor = donors.get(i);
            if (showType && showCategory) {
                System.out.printf("%-10s%-20s%-20s%-15s%n", donor.getId(), donor.getName(), donor.getCategory(), donor.getType());
            } else if (showCategory) {
                System.out.printf("%-10s%-20s%-20s%n", donor.getId(), donor.getName(), donor.getCategory());
            }else if (showType) {
                System.out.printf("%-10s%-20s%-20s%n", donor.getId(), donor.getName(), donor.getType());
            } else {
                System.out.printf("%-10s%-35s%n", donor.getId(), donor.getName());
            }
        }
        System.out.println(String.format("%0" + 70 + "d", 0).replace("0", "-"));


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

        return scanner.next().trim().toUpperCase();
    }

    public static void atFirstPage(){
        System.out.println("You are already on the first page.");
    }

    public static void atLastPage(){
        System.out.println("You are already on the last page.");
    }

    public static String inputCheckDonorIDUI(){
        // Get the ID to be checked
        System.out.print("Enter Donor ID to view details: ");
        return scanner.nextLine().trim();
    }

    public static void donorNotFoundMsg(String donorID){
        System.out.println(Color.RED + "Donor with ID " + donorID + " not found." + Color.RESET);
    }

    public static void pressKeyToGoBack(){
        System.out.println("Press any key to return to previous page.....");
    }

    public static int filterMenu(){
        System.out.println("\nFilter Options:");
        System.out.println("1 - Filter by Name Starting Letter");
        System.out.println("2 - Filter by Category");
        System.out.println("3 - Filter by Type");
        System.out.println("4 - Reset Filter");
        System.out.print("Select a filter option: ");
        return scanner.nextInt();
    }

    public static String filterChoiceName() {
        scanner.nextLine();
        System.out.print("Enter starting letter: ");
        String input = scanner.next();

        // Error handling for empty input within this method itself
        if (input.isEmpty()) {
            Message.displayInvalidInputMessage("Starting letter cannot be empty.");
            return filterChoiceName(); // Recursively ask for valid input
        }

        return input.toUpperCase();
    }

    public static int filterChoiceCategory(){
        System.out.println("\nSelect category:");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.println("3. Public");
        System.out.print("Enter category number: ");
        scanner.nextLine();
        return scanner.nextInt();
    }

    public static int filterChoiceType(){
        System.out.println("\nSelect type:");
        System.out.println("1. Individual");
        System.out.println("2. Organization");
        System.out.print("Enter type number: ");
        scanner.nextLine();
        return scanner.nextInt();
    }

    public static int SortMenu(){
        System.out.println("\n1 - Sort by Name Ascending");
        System.out.println("2 - Sort by Name Descending");
        System.out.println("3 - Sort by ID Descending");
        System.out.print("Choose a sorting criterion: ");
        return scanner.nextInt();
    }



}
