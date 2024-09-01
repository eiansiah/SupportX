/*
 *  Author: Sim Hor Kang
 *  ID: 2307611
 *
 *  Co-author: Siah E-ian
 *  ID: 2307610
 *
 * */

package Boundary;

import Control.Donation;

import Entity.DonationItem;
import Entity.Donor;

import ADT.*;

import Utilities.Color;
import Utilities.GeneralFunction;
import Utilities.Message;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class DonorUI {

    private static final Scanner scanner = new Scanner(System.in);

//    public static void DonorWelcomeMessage() {
//        System.out.println("\nEntering Donor System!");
//    }

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
        Scanner nameScanner = new Scanner(System.in);
        System.out.print("Enter new donor name: ");
        return nameScanner.nextLine().trim();
    }

    public static String inputDonorEmailUI(){
        System.out.print("Enter new donor email: ");
        return scanner.next().trim();
    }

    public static String inputDonorPhoneUI(){
        System.out.print("Enter new donor phone: ");
        return scanner.next().trim();
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
        Scanner removeID = new Scanner(System.in);
        System.out.print("\nWhich Donor Would you like to remove? Please enter their ID: ");
        return removeID.nextLine().trim();
    }

    public static String inputUpdateDonorIDUI(){
        // Get the ID to be deleted
        Scanner updateID = new Scanner(System.in);
        System.out.print("\nWhich Donor Would you like to update? Please enter their ID: ");
        return updateID.nextLine().trim();
    }

    public static String deleteConfirmation(String id, String name, String email, String phone , String category, String type , LocalDate registeredDate){

        displaySelectedDonorDetail(id, name, email, phone, category, type, registeredDate);

        System.out.print("\nAre you sure you want to delete this donor? (Y/N): ");
        return scanner.next().trim().toUpperCase();
    }

    public static void displaySelectedDonorDetail(String id, String name, String email, String phone , String category, String type , LocalDate registeredDate){
        // Display the donor's information before deletion
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("\nDonor Details:");
        System.out.printf("%-15s: %s%n", "ID", id);
        System.out.printf("%-15s: %s%n", "Name", name);
        System.out.printf("%-15s: %s%n", "Email", email);
        System.out.printf("%-15s: %s%n", "Phone", phone);
        System.out.printf("%-15s: %s%n", "Category", category);
        System.out.printf("%-15s: %s%n", "Type", type);
        System.out.printf("%-15s: %s%n", "Registered Date", registeredDate);
    }

    public static String displayDonorToBeUpdated(String id, String name, String email, String phone , String category, String type, LocalDate registeredDate){
        // Display the donor's information before updated
        displaySelectedDonorDetail(id,name, email,phone, category,type,registeredDate);

        System.out.print("\nDo you want to proceed with modifying this donor? (Y/N): ");
        return scanner.next().trim().toUpperCase();
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
        return scanner.next().trim();
    }

    public static void displayDeleteDonorMsg(String donorIDToDelete){
        System.out.println(Color.RED + "Donor with ID " + donorIDToDelete + " has been deleted." + Color.RESET);
    }

    public static void displayUpdatedDonorMsg(String donorIDToModify){
        System.out.println(Color.BRIGHT_GREEN + "Donor with ID " + donorIDToModify + " has been updated." + Color.RESET);
    }

    public static String displayDonorTable(int pageSize, int currentPage, int totalDonors, ListInterface<Donor> donors, int start, int end, boolean showCategory, boolean showType){
        System.out.println("\nLIST OF DONORS (Page " + (currentPage + 1) + ")\n");
        // Adjust the header based on what needs to be displayed

        if (showType && showCategory) {
            System.out.printf("%-10s%-35s%-20s%-15s%n", "Donor ID", "Donor Name", "Category", "Type");
        } else if (showCategory) {
            System.out.printf("%-10s%-35s%-20s%n", "Donor ID", "Donor Name", "Category");
        }else if (showType) {
            System.out.printf("%-10s%-35s%-20s%n", "Donor ID", "Donor Name", "Type");
        } else {
            System.out.printf("%-10s%-35s%n", "Donor ID", "Donor Name");
        }

        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));

        for (int i = start; i < end; i++) {
            Donor donor = donors.get(i);
            if (showType && showCategory) {
                System.out.printf("%-10s%-35s%-20s%-15s%n", donor.getId(), donor.getName(), donor.getCategory(), donor.getType());
            } else if (showCategory) {
                System.out.printf("%-10s%-35s%-20s%n", donor.getId(), donor.getName(), donor.getCategory());
            }else if (showType) {
                System.out.printf("%-10s%-35s%-20s%n", donor.getId(), donor.getName(), donor.getType());
            } else {
                System.out.printf("%-10s%-35s%n", donor.getId(), donor.getName());
            }
        }
        System.out.println(String.format("%0" + 85 + "d", 0).replace("0", "-"));


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
        Scanner checkID = new Scanner(System.in);
        // Get the ID to be checked
        System.out.print("Enter Donor ID to view details: ");
        return checkID.nextLine().trim();
    }


    public static void donorNotFoundMsg(String donorID){
        System.out.println(Color.RED + "Donor with ID " + donorID + " not found." + Color.RESET);
    }

    public static void pressKeyToGoBack(){
        System.out.print("Press any key to return to previous page.....");
        try {
            System.in.read();  // Reads a single character (byte) from the input stream
        } catch (IOException e) {
            e.printStackTrace();  // Handle any potential IO exceptions
        }
    }

    public static void pressKeyToContinue(){
        System.out.print("Press any key to continue after you have finish reading..... ");
        try {
            System.in.read();  // Reads a single character (byte) from the input stream
        } catch (IOException e) {
            e.printStackTrace();  // Handle any potential IO exceptions
        }
    }

    public static String filterMenu(){
        System.out.println("\nFilter Options:");
        System.out.println("1 - Filter by Name Starting Letter");
        System.out.println("2 - Filter by Category");
        System.out.println("3 - Filter by Type");
        System.out.println("4 - Undo Filter");
        System.out.println("5 - Cancel Filter");
        System.out.print("Select a filter option: ");
        Scanner filterScanner = new Scanner(System.in);
        return filterScanner.nextLine();
    }

    public static String filterChoiceName() {
        System.out.print("Enter starting letter: ");
        String input = scanner.next();

        // Error handling for empty input within this method itself
        if (input.isEmpty()) {
            Message.displayInvalidInputMessage("Starting letter cannot be empty.");
            return filterChoiceName(); // Recursively ask for valid input
        }

        return input.toUpperCase();
    }

    public static String filterChoiceCategory(){
        System.out.println("\nSelect category:");
        System.out.println("1. Government");
        System.out.println("2. Private");
        System.out.println("3. Public");
        System.out.print("Enter category number: ");
        return scanner.next();
    }

    public static String filterChoiceType(){
        System.out.println("\nSelect type:");
        System.out.println("1. Individual");
        System.out.println("2. Organization");
        System.out.print("Enter type number: ");
        return scanner.next();
    }

    public static String SortMenu(){
        System.out.println("\n1 - Sort by Name Ascending");
        System.out.println("2 - Sort by Name Descending");
        System.out.println("3 - Sort by ID Descending");
        System.out.print("Choose a sorting criterion: ");
        return scanner.next();
    }

    public static void viewSummaryDonorData(ArrayList <Integer> filterCount){
        int publicCount = filterCount.get(0);
        int privateCount = filterCount.get(1);
        int governmentCount = filterCount.get(2);
        int individualCount = filterCount.get(3);
        int organizationCount = filterCount.get(4);

        // Donor Type Breakdown
        System.out.printf("|%-28s|%-28s|%-27s|%n", centerString("Public", 28), centerString("Private", 28), centerString("Government", 27));
        System.out.printf("|%-28s|%-28s|%-27s|%n", centerString(String.valueOf(publicCount), 28), centerString(String.valueOf(privateCount), 28), centerString(String.valueOf(governmentCount), 27));

        GeneralFunction.repeatPrint("-", 87);
        GeneralFunction.printEmptyLine();
        // Donor Category Breakdown
        System.out.printf("|%-42s|%-42s|%n", centerString("Individual", 42), centerString("Organization", 42));
        System.out.printf("|%-42s|%-42s|%n", centerString(String.valueOf(individualCount), 42), centerString(String.valueOf(organizationCount), 42));
    }

    private static String centerString(String text, int width) {
        int padding = (width - text.length()) / 2;
        String format = "%" + padding + "s%s%" + padding + "s";
        return String.format(format, "", text, "");
    }

    public static void donorWithMostRecord(String id, String name , int maxRecord){
        System.out.printf("|%-28s|%-28s|%-27s|%n", centerString("Donor ID", 28), centerString("Donor Name", 28), centerString("Number of Records", 28));
        System.out.printf("|%-28s|%-28s|%-27s|%n", centerString(id, 28), centerString(name, 28), centerString(String.valueOf(maxRecord), 27));
    }

    public static void donorAddedThisMonth(int number){
        System.out.printf("|%-85s|%n", centerString(String.valueOf(number), 85));

    }

    public static void totalNumberOfDonors(int number){
        System.out.printf("|%-85s|%n", centerString(String.valueOf(number), 85));
    }

    public static void showRecordID(int id){
        System.out.println("Record ID: " + id);
    }

    public static void showItemData(DonationItem item){
        System.out.println("Items: " + item);
    }

    public static void showItemDate(LocalDateTime date){
        System.out.println("Items: " + date);
    }
}
