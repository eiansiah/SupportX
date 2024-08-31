/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Boundary;

import Entity.Beverage;
import Entity.DonationItem;
import Entity.DonationRecord;
import Entity.Donor;
import Entity.Food;
import Entity.Medicine;
import Entity.Money;
import Entity.PersonalCare;
import Libraries.ListInterface;
import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author vivia
 */
public class DonationUI {

    //--------------------------------------------------------------Input-------------------------------------------------------------------------
    Scanner scanner = new Scanner(System.in);

    //get item code
    public String inputRecordIDRemove() {
        System.out.print("\nEnter the record ID to be removed or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    public String inputRecordIDAmend() {
        System.out.print("\nEnter the record ID to be amended or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    public String inputOption(int n) {
        String line = "\nPlease select an option (1-" + n + "): ";
        System.out.print(line);
        return scanner.nextLine();
    }

    public String inputDonorID() {
        System.out.print("Enter Donor ID: ");
        return scanner.nextLine();
    }

    //Input Name - Boundary
    public String inputName() {
        System.out.print("Enter Item Name: ");
        return scanner.nextLine();
    }

    //Input Description - Boundary
    public String inputDescription() {
        System.out.print("Enter Description: ");
        return scanner.nextLine();
    }

    //Input Food Net Weight - Boundary
    public String inputFoodNetWeight() {
        System.out.print("Enter Food Net Weight (kilogram): ");
        return scanner.nextLine();
    }

    //Input Beverage Net Volume - Boundary
    public String inputBeverageNetVolume() {
        System.out.print("Enter Beverage Net Volume (mililitre): ");
        return scanner.nextLine();
    }

    //Input Net Weight - Boundary
    public String inputNetWeight() {
        System.out.print("Enter Net Weight (gram): ");
        return scanner.nextLine();
    }

    //Input Expiry Date - Boundary
    public String inputExpiryDate() {
        System.out.print("Enter Expiry Date (yyyy-mm-dd): ");
        return scanner.nextLine();
    }

    //Input Food Type - Boundary
    public String inputFoodType() {
        System.out.print("Enter Food Type (1-8): ");
        return scanner.nextLine();
    }

    //Input Clothing Category - Boundary
    public String inputClothingCategory() {
        System.out.print("Enter Clothing Category (1-9): ");
        return scanner.nextLine();
    }

    //Input Gender - Boundary
    public String inputGender() {
        System.out.print("Enter Gender (1-3): ");
        return scanner.nextLine();
    }
    //Input Age - Boundary

    public String inputAge() {
        System.out.print("Enter Age Group(1-6): ");
        return scanner.nextLine();
    }

    //Input search item code
    public String inputSearchItemCode() {
        System.out.print("Enter Item Code to be searched (or X to return to Donation Page): ");
        return scanner.nextLine();
    }

    public String inputNumA() {
        System.out.println("Enter a: ");
        return scanner.nextLine();
    }

    public String inputNumB() {
        System.out.println("Enter b: ");
        return scanner.nextLine();
    }

    //Input Personal Care Category - Boundary
    public String inputPCCat() {
        System.out.print("Enter Personal Care Category (1-5): ");
        return scanner.nextLine();
    }
    //Input

    public String inputDosageForm() {
        System.out.print("Enter Dosage Form (1-5): ");
        return scanner.nextLine();
    }
    //Input Quantity - Boundary

    public String inputQty() {
        System.out.print("Enter Quantity: ");
        return scanner.nextLine();
    }
    //Input

    public String inputAmt() {
        System.out.print("Enter Amount : RM ");
        return scanner.nextLine();
    }

    //Input Venue Code - Boundary
    public String inputVenueCode() {
        System.out.print("Enter Venue Code: ");
        return scanner.nextLine();
    }

    //Input Source - Boundary
    public String inputSource() {
        System.out.print("Enter Source: ");
        return scanner.nextLine();
    }

    public String inputActionToDescription() {
        System.out.print("Do you wanna \n1. Append Description \n2. Keep original version\n3. Keep the current version");
        System.out.print("\nX. Stop this Addition of Donation\nPick an option(1-3, X): ");
        return scanner.nextLine();
    }

    //Input Size - Boundary
    public String inputSize() {
        System.out.print("Enter Clothing Size: ");
        return scanner.nextLine();
    }

    //get item code
    public String inputItemCodeRemove() {
        System.out.print("\nEnter the item code to be removed or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    //get item code
    public String inputItemCodeEdit() {
        System.out.print("\nEnter the item code to be amended or 'X' to back to previous page: ");
        return scanner.nextLine();
    }

    public String inputAskConAmend() {
        System.out.print("\nDo you wish to amend other donation? (Y/N): ");
        return scanner.nextLine();
    }

    public String inputAskConRemove() {
        System.out.print("\nDo you wish to remove other donation? (Y/N): ");
        return scanner.nextLine();
    }

    public String inputAskConAdd() {
        System.out.print("\nDo you wish to add other donation? (Y/N): ");
        return scanner.nextLine();
    }

    public String inputChoice() {
        System.out.print("Your option (1-2, X): ");
        return scanner.nextLine();
    }

    public String inputDetectedSame() {
        System.out.print("Detected this item existed before. Do you wish to add the quantity? (Y/N): ");
        return scanner.nextLine();
    }

    public String inputDetectedSameAmt() {
        System.out.print("Detected this item existed before. Do you wish to add the amount? (Y/N): ");
        return scanner.nextLine();
    }
    //--------------------------------------------------------------Output-------------------------------------------------------------------------

    //Display Donation Action Menu - Boundary
    public void displayMenuDonationHome() {
        System.out.println("\nDonation");
        System.out.println("1. Add Donation");
        System.out.println("2. Remove Donation");
        System.out.println("3. Search Donation");
        System.out.println("4. Amend Donation Details");
        System.out.println("5. Track Donation");
        System.out.println("6. Donation-Donor List");
        System.out.println("7. Full Donation List");
        System.out.println("8. Summary Report");
        System.out.println("9. Back to Support X Home");
    }

    //Display Donation Category - Boundary
    public void displayDonationCategory() {
        System.out.println("Donation Category");
        System.out.println("1. Food");
        System.out.println("2. Beverage");
        System.out.println("3. Clothing");
        System.out.println("4. Personal Care");
        System.out.println("5. Medical Device");
        System.out.println("6. Medicine");
        System.out.println("7. Money");
        System.out.println("8. Back to Donation");
    }

    public void displayAddPageHeader() {
        System.out.println("\nAdd Donation");
    }

    //Display Instruction for addDonation
    public void displayAddDonationInstruction() {
        System.out.println("\nDonor For This Donation");
        System.out.println("**Note that a donation can have one or many items.");
        System.out.println("**Leaving this page to go back Donation Page will end this session for this donation for this donor.\n");
    }

    public void displayRemoveDonationHeader() {
        System.out.println("\n\nRemove Donation");
    }
    //Display Add Beverage Instruction - Boundary

    public void displayInstructionAddBeverage() {
        System.out.println("\nAdd Beverage");
        displayVenueCode();
        System.out.println("\nInstruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Display Add Clothing Instruction - Boundary
    public void displayInstructionAddClothing() {
        System.out.println("\nAdd Clothing\n");
        displayClothingCategory();
        displayGender();
        displayAge();
        displaySize();
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }
    //Display Clothing Category - Boundary

    public void displayClothingCategory() {
        System.out.println("Clothing Category:\n1. Tops\n2. Bottoms\n3. Outerwear\n4. Dress\n5. Innerwear");
        System.out.println("6. Sportswear\n7. Footwear\n8. Accessories\n9. Sleepwear\n");
    }

    //Display Gender - Boundary
    public void displayGender() {
        System.out.println("Gender:\n1. Men\n2. Women\n3. Neutral\n");
    }

    //Display Age- Boundary
    public void displayAge() {
        System.out.println("Age Group:\n1. Infants\n2. Toddlers\n3. Kids\n4. Teens\n5. Adults\n6. Seniors\n");
    }

    //Display Add PersonalCare Instruction - Boundary
    public void displayInstructionAddPersonalCare() {
        System.out.println("\nAdd Personal Care\n");
        displayPCCat();
        displayGender();
        displayAge();
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter '-' for expiry date if the product does not have an expiry date.");
        System.out.println("Enter '-999' for netweight if the product does not have a net weight.\nEnter 'X' to back to the previous page.\n");
    }

    //Display Personal care category - Boundary
    public void displayPCCat() {
        System.out.println("Personal Care Category:\n1. Skin care (Moisturizers, Cleansers, Toners)\n2. Hair Care (Shampoo, Conditioners, Hair Masks)");
        System.out.println("3. Body Care (Body Wash, Soap, Body Scrubs)\n4. Oral Care (Toothpaste, Toothbrush, Mouthwash)");
        System.out.println("5. Personal Hygiene (Sanitary Pads, Nail Clippers, Cotton Swabs)\n");
    }
    //Display Add Medical Device Instruction - Boundary

    public void displayInstructionAddMedicine() {
        System.out.println("\nAdd Medicine\n");
        displayDSForm();
        displayGender();
        displayAge();
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Display Dosage Form - Boundary
    public void displayDSForm() {
        System.out.println("Dosage Form:\n1. Solid\n2. Liquid\n3. Semi-Solid(Cream, Gel, Paste, ...)\n4. Inhalation\n5. Injectable\n");
    }
    //Display Source - Boundary

    public void displaySource() {
        System.out.println("Source: \nEnter 'E001' if the source is from e-banking.\nEnter venue code if the source is from a collection location. ");
        System.out.println("Enter event code if the source if from an event.\n");
    }

    //Display Add Money Instruction - Boundary
    public void displayInstructionAddMoney() {
        System.out.println("\nAdd Money\n");
        displayVenueCode();
        displaySource();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    public void displayVenueCode() {
        System.out.println("\nVenue Code");
        try {
            File myObj = new File("Venue.txt");
            Scanner readerFile = new Scanner(myObj);
            while (readerFile.hasNextLine()) {
                String[] venueList = readerFile.nextLine().split("#");
                System.out.printf("%-10s %-20s\n", venueList[0], venueList[1]);
            }
            readerFile.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    //Display Add Medical Device Instruction - Boundary

    public void displayInstructionAddMedicalDevice() {
        System.out.println("\nAdd Medical Device\n");
        displayVenueCode();
        System.out.println("Instruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    public void displayAmendDetailsHeader() {
        System.out.println("\n\nAmend Donation");
    }

    public void displayAmendQtyRecord() {
        System.out.println("Action:\n1. Remove from a record\n2. Amend a record quantity\nX. Stop this modification.");
    }

    public void displayAmendOptionAmt() {
        System.out.println("Action:\n1. Remove from a record\n2. Amend a record amount\nX. Stop this modification.");
    }
//Display Add Food Instruction - Boundary

    public void displayInstructionAddFood() {
        System.out.println("\nAdd Food\n");
        displayFoodType();
        displayVenueCode();
        System.out.println("\nInstruction: Please enter the field accordingly. \nEnter 'X' to back to the previous page.\n");
    }

    //Display Food Type Category - Boundary
    public void displayFoodType() {
        System.out.println("Food Category:\n1. Fruit\n2. Vegetable\n3. Protein\n4. Whole Grain\n5. Baby Food\n6. Dairy\n7. Fat\n8. Seasoning");
    }

    //Amend Food Option Menu - Boundary
    public void displayAmendFoodOption() {
        System.out.println("\nAmend Food Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Weight");
        System.out.println("4. Expiry Date");
        System.out.println("5. Food Type");
        System.out.println("6. Venue Code");
        System.out.println("7. Back to Previous Page");
    }

    //Amend Beverage Option Menu - Boundary
    public void displayAmendBeverageOption() {
        System.out.println("\nAmend Beverage Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Volume");
        System.out.println("4. Expiry Date");
        System.out.println("5. Venue Code");
        System.out.println("6. Back to Previous Page");
    }

    //Amend Clothing Option Menu - Boundary
    public void displayAmendClothingOption() {
        System.out.println("\nAmend Clothing Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Clothing Category");
        System.out.println("4. Gender");
        System.out.println("5. Age");
        System.out.println("6. Size");
        System.out.println("7. Venue Code");
        System.out.println("8. Back to Previous Page");
    }

    //Amend Personal Care Option Menu - Boundary
    public void displayAmendPersonalCareOption() {
        System.out.println("\nAmend Personal Care Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Weight");
        System.out.println("4. Expiry Date");
        System.out.println("5. Gender");
        System.out.println("6. Age");
        System.out.println("7. Personal Care Category");
        System.out.println("8. Venue Code");
        System.out.println("9. Back to Previous Page");
    }

    //Amend Medical Device Option Menu - Boundary
    public void displayAmendMedicalDeviceOption() {
        System.out.println("\nAmend Medical Device Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Venue Code");
        System.out.println("4. Description");
        System.out.println("5. Back to Previous Page");
    }

    //Amend Medicine Option Menu - Boundary
    public void displayAmendMedicineOption() {
        System.out.println("\nAmend Medicine Option");
        System.out.println("1. Item Name");
        System.out.println("2. Quantity");
        System.out.println("3. Net Weight");
        System.out.println("4. Expiry Date");
        System.out.println("5. Gender");
        System.out.println("6. Age");
        System.out.println("7. Venue Code");
        System.out.println("8. Dosage Form");
        System.out.println("9. Description");
        System.out.println("10. Back to Previous Page");
    }

    //Amend Money Option Menu - Boundary
    public void displayAmendMoneyOption() {
        System.out.println("\nAmend Money Option");
        System.out.println("1. Amount");
        System.out.println("2. Source");
        System.out.println("3. Back to Previous Page");
    }

    public void displaySearchResultsHeaderIfFound() {
        System.out.println("Results Found: ");
    }

    //Display Single Item
    public void displaySingleItem(String lineToDisplay) {
        System.out.println(lineToDisplay);
    }

    public void displayDonationRecordHeader() {
        System.out.println("Donation Record Associated");
    }

    public void displaySingleRecordWithoutDonorIDMoney(DonationRecord record, int i, int amtIndex) {
        System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", "", record.getItem().get(i).getItemCode(),
                record.getItem().get(i).getItemName(), record.getAmount().get(amtIndex), "");
    }

    public void displaySingleRecordWithoutDonorIDMoney(DonationRecord record, int amtIndex) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getItem().get(0).getItemCode(),
                record.getItem().get(0).getItemName(), record.getAmount().get(amtIndex), record.getDonationDateTime().format(dateFormat));
    }

    public void displaySingleRecordWithoutDonorIDNotMoney(DonationRecord record, int i, int qtyIndex) {
        System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", "", record.getItem().get(i).getItemCode(),
                record.getItem().get(i).getItemName(), record.getQty().get(qtyIndex), "");
    }

    public void displaySingleRecordWithoutDonorIDNotMoney(DonationRecord record, int qtyIndex) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getItem().get(0).getItemCode(),
                record.getItem().get(0).getItemName(), record.getQty().get(qtyIndex), record.getDonationDateTime().format(dateFormat));
    }

    public void displaySingleDonorRecordsHeader(Donor donor) {
        System.out.println("\nDonor ID: " + donor.getId());
        System.out.println("Donor Name: " + donor.getName());
        System.out.printf("%-25s %-10s %-20s %-10s %-20s\n", "Donation Record ID", "Item Code", "Item Name", "Quantity", "Date Time");
    }

    public void displaySingleRecordWithDonorIDMoney(DonationRecord record, int i, int amtIndex) {
        System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getDonor().getId(),
                record.getDonor().getName(), record.getItem().get(0).getItemCode(), record.getItem().get(i).getItemName(),
                record.getAmount().get(amtIndex), "");
    }

    public void displaySingleRecordWithDonorIDMoney(DonationRecord record, int amtIndex) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getDonor().getId(),
                record.getDonor().getName(), record.getItem().get(0).getItemCode(), record.getItem().get(0).getItemName(),
                record.getAmount().get(amtIndex), record.getDonationDateTime().format(dateFormat));
    }

    public void displaySingleRecordWithDonorIDNotMoney(DonationRecord record, int i, int qtyIndex) {
        System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", "", "", "", record.getItem().get(i).getItemCode(),
                record.getItem().get(i).getItemName(), record.getQty().get(qtyIndex), "");
    }

    public void displaySingleRecordWithDonorIDNotMoney(DonationRecord record, int qtyIndex) {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", record.getRecordID(), record.getDonor().getId(),
                record.getDonor().getName(), record.getItem().get(0).getItemCode(), record.getItem().get(0).getItemName(),
                record.getQty().get(qtyIndex), record.getDonationDateTime().format(dateFormat));
    }

    public void displayDonationRecordTableHeader() {
        System.out.printf("%-25s %-10s %-20s %-10s %-20s %-10s %-20s\n", "Donation Record ID", "Donor ID", "Donor Name", "Item Code",
                "Item Name", "Quantity", "Date Time");
    }

    //Display Empty File
    public void displayEmptyList() {
        System.out.println("There is nothing in the list yet!");
    }

    public void displayMoneyListHeader() {
        System.out.println("\nMoney List");
    }

    public void displayMedicineListHeader() {
        System.out.println("\nMedicine List");
    }

    public void displayMedicalDeviceListHeader() {
        System.out.println("\nMedical Devcie List");
    }

    public void displayPersonalCareListHeader() {
        System.out.println("\nPersonal Care List");
    }

    public void displayClothingListHeader() {
        System.out.println("\nClothingList");
    }

    public void displayBeverageListHeader() {
        System.out.println("\nBeverage List");
    }

    public void displayFoodListHeader() {
        System.out.println("\nFood List");
    }

    //Display Size
    public void displaySize() {
        System.out.print("Size: XS / S / M / L / XL / XXL / XXXL\n**This size is only available for Tops,");
        System.out.println("Bottoms, Outerwear, Dress, Innerwear, Sportswear, Sleepwear\n**This sizes are only applicable for Kids, Teens, Adults and Seniors");
        System.out.println("\nFootwear Size:\n1. Kids 10-13.5 1-6\n2. Teens 6-8\n3. Adults & Seniors - Men 6-14\n4. Adults & Seniors - Women 2-10");
        System.out.println("5. Adults & Seniors - Neutral 2-14\n");
    }

    public void displayFullDonationActionMenuAftSort() {
        System.out.println("\nAction:");
        System.out.println("1. Filter by Category");
        System.out.println("2. Filter by Quantity");
        System.out.println("3. Cancel Sort");
        System.out.println("4. Back to Support X Home");
    }

    public void displayFullDonationActionMenu() {
        System.out.println("\nAction:");
        System.out.println("1. Filter by Category");
        System.out.println("2. Filter by Quantity");
        System.out.println("3. Sort by Item Code in Ascending Order");
        System.out.println("4. Sort by Item Code in Descending Order");
        System.out.println("5. Sort by Quantity in Ascending Order");
        System.out.println("6. Sort by Quantity in Descending Order");
        System.out.println("7. Back to Support X Home");
    }

    public void displayFullDonationActionMenuAftFilter() {
        System.out.println("\nAction:");
        System.out.println("1. Cancel Filter");
        System.out.println("2. Sort by Item Code in Ascending Order");
        System.out.println("3. Sort by Item Code in Descending Order");
        System.out.println("4. Sort by Quantity in Ascending Order");
        System.out.println("5. Sort by Quantity in Descending Order");
        System.out.println("6. Back to Support X Home");
    }

    public void displayFullDonationActionMenuAftFilterAftSort() {
        System.out.println("\nAction:");
        System.out.println("1. Cancel Filter");
        System.out.println("2. Cancel Sort");
        System.out.println("3. Cancel Sort and Filter");
        System.out.println("4. Back to Support X Home");
    }

    public void displayFilterQtyOp() {
        System.out.println("\nFilter Action:");
        System.out.println("1. Larger Than a");
        System.out.println("2. Between Range (Inclusive) a<=Quantity<=b");
        System.out.println("3. Smaller Than a");
        System.out.println("4. Back to Support X Home");
    }

    //Report
    //Display Report Choice - Boundary
    public void displaySummaryReportChoices() {
        System.out.println("\nView Summary Report");
        System.out.println("1. Report of Expired Items by Category as of " + LocalDate.now());
        System.out.println("2. Top 3 Most Donated Non-Money Item Category Report as of " + LocalDate.now());
        System.out.println("3. Back to Donation Page");
    }

    public void displayReport1Ntg() {
        System.out.println("No item expired as of " + LocalDate.now());
        System.out.print("\n");
    }

    public void displayReport1Food(ListInterface<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Food");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        Iterator<DonationItem> iterator = expiredItemList.iterator();
        while (iterator.hasNext()) {
            DonationItem food = iterator.next();
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", food.getItemCode(), food.getItemName(), ((Food) food).getExpiryDate(), food.getQuantity());
            total += food.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Bg(ListInterface<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Beverage");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        Iterator<DonationItem> iterator = expiredItemList.iterator();
        while (iterator.hasNext()) {
            DonationItem bg = iterator.next();
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", bg.getItemCode(), bg.getItemName(), ((Beverage) bg).getExpiryDate(), bg.getQuantity());
            total += bg.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Pc(ListInterface<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Personal Care");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        Iterator<DonationItem> iterator = expiredItemList.iterator();
        while (iterator.hasNext()) {
            DonationItem pc = iterator.next();
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", pc.getItemCode(), pc.getItemName(), ((PersonalCare) pc).getExpiryDate(), pc.getQuantity());
            total += pc.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Med(ListInterface<DonationItem> expiredItemList) {
        int total = 0;
        System.out.println("Medicine");
        for (int i = 0; i < 68; i++) {
            System.out.print("_");
        }
        System.out.print("\n");
        System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", "Item Code", "Item Name", "Expiry Date", "Quantity");
        System.out.print("|");
        for (int i = 0; i < 11; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("-");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("-");
        }
        System.out.print("|\n");
        Iterator<DonationItem> iterator = expiredItemList.iterator();
        while (iterator.hasNext()) {
            DonationItem med = iterator.next();
            System.out.printf("|%-10s |%-20s |%-20s |%-10s|\n", med.getItemCode(), med.getItemName(), ((Medicine) med).getExpiryDate(), med.getQuantity());
            total += med.getQuantity();
        }
        System.out.printf("|%-10s_|%-20s_|%-20s_|%-10s|\n", "__________", "____________________", "____________________", "__________");
        System.out.printf(" %-10s  %-20s |%-20s |%-10s|\n", "", "", "Total Quantity: ", total);
        for (int i = 0; i < 34; i++) {
            System.out.print(" ");
        }
        System.out.print("|");
        for (int i = 0; i < 21; i++) {
            System.out.print("_");
        }
        System.out.print("|");
        for (int i = 0; i < 10; i++) {
            System.out.print("_");
        }
        System.out.print("|\n");
        System.out.print("\n");
    }

    public void displayReport1Header() {
        System.out.print("\n");
        for (int i = 0; i < 8; i++) {
            System.out.print(" ");
        }
        System.out.println("Report of Expired Items by Category as of " + LocalDate.now());
        System.out.print("\n");
    }

    public void displayReport2Header() {
        System.out.print("\n");
        for (int i = 0; i < 11; i++) {
            System.out.print(" ");
        }
        System.out.println("Top 3 Most Donated Non-Money Item Category Report");
        for (int i = 0; i < 27; i++) {
            System.out.print(" ");
        }
        System.out.println("as of " + LocalDate.now());
    }
}
