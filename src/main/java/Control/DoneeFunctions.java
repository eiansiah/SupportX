package Control;

/*
 *  author: Siah E-Ian
 *  ID: 2307610
 *
 *  Co-author: Sim Hor Kang
 *  ID: 2307611
 *
 * */

import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;

import Entity.DonationDistribution;
import Entity.Donee;

import DAO.DoneeFileHandler;

import Utilities.GeneralFunction;
import ADT.ArrayList;
import ADT.ListInterface;

import Utilities.Message;
import Utilities.NewValidation;

import Boundary.DoneeUI;

public class DoneeFunctions {

    private static ListInterface<Donee> doneeList = new ArrayList<>();
    private static final DoneeFileHandler fileHandler = new DoneeFileHandler();

    public DoneeFunctions (){
        doneeList = readDonees();
    }

    public static ListInterface<Donee> readDonees(){
        return fileHandler.readData("donee.txt");
    }

    public void runDoneeManagement() {
        int choice = 0;

        fileHandler.checkAndCreateFile("donee.txt");

        do {
            DoneeUI.mainWelcomeMessage();

            String inputChoice = DoneeUI.getMainMenuChoice();

            try {
                choice = Integer.parseInt(inputChoice);
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                continue;
            }

            switch (choice) {
                case 1: //Add Donee
                    addDonee();
                    break;
                case 2: //Delete Donee
                    deleteDoneeHandler(readDonees());
                    break;
                case 3: //Modify Donee
                    modifyDoneeHandler(readDonees());
                    break;
                case 4: //View Donee Details
                    displayDonees(readDonees());
                    break;
                case 5: //View donations for each donee
                    viewDonationDistribution();
                    break;
                case 6: //Filter donees by criteria
                    filterDonees(readDonees());
                    break;
                case 7: //View reports
                    summaryReport(readDonees());
                    break;
                case 8:
                    break;
                default: //Exit
                    Message.displayInvalidChoiceMessage("Please enter a valid choice and ensure input is not empty.");
            }

        } while (choice != 8);
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

    public static void addDonee() {
        // Get the last ID from the file
        String lastId = fileHandler.getLastDoneeId("donee.txt");
        // Increment to get the new ID
        String newDoneeId = fileHandler.incrementDoneeId(lastId);

        Donee newDonee = inputDoneeDetails(newDoneeId);

        fileHandler.saveData("donee.txt", newDonee);

        GeneralFunction.enterToContinue();
    }

    public static Donee inputDoneeDetails(String doneeID){
        boolean isValid;
        DoneeUI.addDoneeUI();
        String name;
        do {
            name = DoneeUI.inputDoneeNameUI(); // input from boundary file
            isValid = NewValidation.validateName(name);
            if (!isValid) {
                Message.displayInvalidFormatMessage("name");
            }
        } while (!isValid);

        String email;
        do {
            email = DoneeUI.inputDoneeEmailUI(); // input from boundary file
            isValid = NewValidation.validateEmail(email);
            if (!isValid) {
                Message.displayInvalidFormatMessage("email");
            }
        } while (!isValid);

        String phone;
        do {
            phone = DoneeUI.inputDoneePhoneUI();
            isValid = NewValidation.validatePhone(phone);
            if (!isValid) {
                Message.displayInvalidFormatMessage("phone");
            }
        } while (!isValid);

        String address;
        do {
            address = DoneeUI.inputDoneeAddressUI();
            isValid = NewValidation.validateAddress(address);
            if (!isValid) {
                Message.displayEmptyInputMessage("Please do not leave address input blank.");
            }
        } while (!isValid);

        String doneeType = "";
        do{
            int choice = 0;
            String doneeTypeChoice = DoneeUI.inputDoneeTypeUI();
            try {
                choice = Integer.parseInt(doneeTypeChoice);
                if (choice >= 1 && choice <= 3) {
                    isValid = true;  // Exit loop for valid choice input
                } else {
                    Message.displayInvalidChoiceMessage("Please select a valid item category (1-3).");
                    isValid = false;
                }
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                isValid = false;
            }

            if (isValid)
                doneeType = NewValidation.validateDoneeType(choice);

        } while (!isValid);

        String itemCategory = "";
        do{
            int choice = 0;
            String itemCategoryChoice = DoneeUI.inputItemCategoryUI();
            try {
                choice = Integer.parseInt(itemCategoryChoice);
                if (choice >= 1 && choice <= 7) {
                    isValid = true;  // Exit loop for valid choice input
                } else {
                    Message.displayInvalidChoiceMessage("Please select a valid item category (1-7).");
                    isValid = false;
                }
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                isValid = false;
            }

            if (isValid)
                itemCategory = NewValidation.validateItemCategory(choice);

        } while (!isValid);

        String doneeUrgency = "";
        do{
            int choice = 0;
            String doneeUrgencyChoice = DoneeUI.inputDoneeUrgencyUI();
            try {
                choice = Integer.parseInt(doneeUrgencyChoice);
                if (choice >= 1 && choice <= 3) {
                    isValid = true;  // Exit loop for valid choice input
                } else {
                    Message.displayInvalidChoiceMessage("Please select a valid item category (1-3).");
                    isValid = false;
                }
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                isValid = false;
            }

            if (isValid)
                doneeUrgency = NewValidation.validateDoneeUrgency(choice);

        } while (!isValid);

        LocalDate registeredDate = LocalDate.now();

        return new Donee(doneeID, name, email, phone, address, doneeType, itemCategory, doneeUrgency, registeredDate);
    }

    public static void deleteDoneeHandler(ListInterface<Donee> donees){
        doneeList = deleteDoneeCore(donees);

        fileHandler.updateMultipleData("donee.txt", doneeList);

        GeneralFunction.enterToContinue();
    }

    public static ListInterface<Donee> deleteDoneeCore(ListInterface<Donee> donees) {
        String doneeId;
        Donee selectedDonee = null;

        do {
            DoneeUI.deleteDoneeUI();
            doneeId = obtainDoneeId().toUpperCase();
        } while (doneeId.isEmpty());

        if (doneeId.equals("X")){
            return donees;
        }

        Iterator<Donee> doneeIterator = donees.iterator();
        // Search for the donor by ID
        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();
            if (donee.getDoneeID().equals(doneeId)) {
                // Update the donee details
                selectedDonee = donee;
                break; // Exit the loop once the donee is found and updated
            }
        }

        if (selectedDonee != null) {
            // Display the donor's information before deletion
            DoneeUI.displayIndividualDoneeDetailsUI(selectedDonee.getDoneeID(), selectedDonee.getName(), selectedDonee.getEmail(), selectedDonee.getPhone(), selectedDonee.getAddress(), selectedDonee.getDoneeType(), selectedDonee.getItemCategoryRequired(), selectedDonee.getDoneeUrgency(), selectedDonee.getRegisteredDate());

            String confirmation;
            boolean deleteValid;
            do {
                confirmation = DoneeUI.deleteDoneeConfirmationUI();

                if (confirmation.equals("Y")) {
                    donees.remove(selectedDonee);
                    DoneeUI.deleteDoneeSuccessfulMessage(doneeId);
                    deleteValid = true;
                } else if (confirmation.equals("N")) {
                    DoneeUI.deleteDoneeOperationAbortMessage();
                    deleteValid = true;
                } else {
                    Message.displayInvalidInputMessage("Please enter only Y for Yes or N for No.");
                    deleteValid = false;
                }
            } while (confirmation.isEmpty() || !deleteValid);

        } else {
            Message.displayDataNotFoundMessage("Donee with ID " + doneeId + " was not found.");
        }

        return donees;
    }

    public static void modifyDoneeHandler(ListInterface<Donee> donees){
        doneeList = modifyDoneeCore(donees);

        fileHandler.updateMultipleData("donee.txt", doneeList);

        GeneralFunction.enterToContinue();
    }

    public static ListInterface<Donee> modifyDoneeCore(ListInterface<Donee> donees) {
        boolean doneeFound = false;
        String doneeId;

        do {
            DoneeUI.modifyDoneeUI();
            doneeId = obtainDoneeId();
        } while (doneeId.isEmpty());

        Iterator<Donee> doneeIterator = donees.iterator();

        while (doneeIterator.hasNext()) {
            Donee doneeSelected  = doneeIterator.next();

            if (doneeSelected.getDoneeID().equals(doneeId)) {
                doneeFound = true;
                char choice = ' ';

                DoneeUI.displayIndividualDoneeDetailsUI(doneeSelected.getDoneeID(), doneeSelected.getName(), doneeSelected.getEmail(), doneeSelected.getPhone(), doneeSelected.getAddress(), doneeSelected.getDoneeType(), doneeSelected.getItemCategoryRequired(), doneeSelected.getDoneeUrgency(), doneeSelected.getRegisteredDate());

                do {
                    // Prompt user for which part to modify
                    String modifyChoice = DoneeUI.modifyTypeUI();

                    if (modifyChoice.isEmpty()) {
                        Message.displayEmptyInputMessage("Please enter an option between 1-7 and X.");
                        continue;
                    }

                    choice = modifyChoice.toUpperCase().charAt(0);

                    if (Character.isDigit(choice) && choice >= '1' && choice <= '7' || choice == 'X') {
                        boolean isValid;
                        switch (choice) {
                            case '1':
                                String name;
                                do {
                                    name = DoneeUI.inputDoneeNameUI();
                                    isValid = NewValidation.validateName(name);
                                    if (!isValid) {
                                        Message.displayInvalidFormatMessage("name");
                                    }
                                } while (!isValid);
                                doneeSelected.setName(name);
                                break;
                            case '2':
                                String email;
                                do {
                                    email = DoneeUI.inputDoneeEmailUI();
                                    isValid = NewValidation.validateEmail(email);
                                    if (!isValid) {
                                        Message.displayInvalidFormatMessage("email");
                                    }
                                } while (!isValid);
                                doneeSelected.setEmail(email);
                                break;
                            case '3':
                                String phone;
                                do {
                                    phone = DoneeUI.inputDoneePhoneUI();
                                    isValid = NewValidation.validatePhone(phone);
                                    if (!isValid) {
                                        Message.displayInvalidFormatMessage("phone");
                                    }
                                } while (!isValid);
                                doneeSelected.setPhone(phone);
                                break;
                            case '4':
                                String address;
                                do {
                                    address = DoneeUI.inputDoneeAddressUI();
                                    isValid = NewValidation.validateAddress(address);
                                    if (!isValid) {
                                        Message.displayEmptyInputMessage("Please do not leave address input blank.");
                                    }
                                } while (!isValid);
                                doneeSelected.setAddress(address);
                                break;
                            case '5':
                                String doneeType = "";
                                do{
                                    int doneeTypeOption = 0;
                                    String doneeTypeChoice = DoneeUI.inputDoneeTypeUI();
                                    try {
                                        doneeTypeOption = Integer.parseInt(doneeTypeChoice);
                                        if (doneeTypeOption >= 1 && doneeTypeOption <= 3) {
                                            isValid = true;  // Exit loop for valid choice input
                                        } else {
                                            Message.displayInvalidChoiceMessage("Please select a valid item category (1-3).");
                                            isValid = false;
                                        }
                                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                                        Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                                        isValid = false;
                                    }

                                    if (isValid)
                                        doneeType = NewValidation.validateDoneeType(doneeTypeOption);

                                } while (!isValid);
                                doneeSelected.setDoneeType(doneeType);
                                break;
                            case '6':
                                String itemCategory = "";
                                do{
                                    int itemCategoryOption = 0;
                                    String itemCategoryChoice = DoneeUI.inputItemCategoryUI();
                                    try {
                                        itemCategoryOption = Integer.parseInt(itemCategoryChoice);
                                        if (itemCategoryOption >= 1 && itemCategoryOption <= 7) {
                                            isValid = true;  // Exit loop for valid choice input
                                        } else {
                                            Message.displayInvalidChoiceMessage("Please select a valid item category (1-7).");
                                            isValid = false;
                                        }
                                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                                        Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                                        isValid = false;
                                    }

                                    if (isValid)
                                        itemCategory = NewValidation.validateItemCategory(choice);

                                } while (!isValid);
                                doneeSelected.setItemCategoryRequired(itemCategory);
                                break;
                            case '7':
                                String doneeUrgency = "";
                                do{
                                    int doneeUrgencyOption = 0;
                                    String doneeUrgencyChoice = DoneeUI.inputDoneeUrgencyUI();
                                    try {
                                        doneeUrgencyOption = Integer.parseInt(doneeUrgencyChoice);
                                        if (doneeUrgencyOption >= 1 && doneeUrgencyOption <= 3) {
                                            isValid = true;  // Exit loop for valid choice input
                                        } else {
                                            Message.displayInvalidChoiceMessage("Please select a valid item category (1-3).");
                                            isValid = false;
                                        }
                                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                                        Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                                        isValid = false;
                                    }

                                    if (isValid)
                                        doneeUrgency = NewValidation.validateDoneeUrgency(choice);

                                } while (!isValid);
                                doneeSelected.setDoneeUrgency(doneeUrgency);
                                break;
                        }

                        if (choice != 'X'){
                            DoneeUI.updateDoneeSuccessfulMessage(doneeId);
                        }
                    } else {
                        Message.displayInvalidInputMessage("Please enter a valid option between 1-7 and X.");
                    }
                } while (choice != 'X');

                break;
            }
        }

        if (!doneeFound) {
            Message.displayDataNotFoundMessage("Donee with ID " + doneeId + " was not found.");
        }

        return donees;
    }

    public static void displayDonees(ListInterface<Donee> donees){
        int pageSize = 10;  // Number of donees to display per page
        int currentPage = 0;
        int totalDonees = donees.size();
        String displayOption;
        boolean done = false;

        do {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, totalDonees);
            boolean displayOptionValid = true;

            do{
                do {
                    displayOption = DoneeUI.displayDoneeTableUI(start, currentPage, end, donees, totalDonees);

                    if (displayOption.isEmpty()) {
                        Message.displayEmptyInputMessage("Empty input detected. Please enter a valid option (D, S, X)");
                    }
                } while (displayOption.isEmpty());

                switch (displayOption) {
                    case "P":
                        if (currentPage > 0) {
                            currentPage--;
                        } else {
                            DoneeUI.displayFirstPageMessage();
                        }
                        displayOptionValid = true;
                        break;
                    case "N":
                        if (end < totalDonees) {
                            currentPage++;
                        } else {
                            DoneeUI.displayLastPageMessage();
                        }
                        done = true;
                        displayOptionValid = true;
                        break;
                    case "D":
                        String doneeID;
                        do {
                            DoneeUI.displayDoneeUI();
                            doneeID = obtainDoneeId().toUpperCase();
                        } while (doneeID.isEmpty());

                        Donee selectedDonee = null;

                        Iterator<Donee> doneeIterator = donees.iterator();

                        while (doneeIterator.hasNext()) {
                            Donee donee = doneeIterator.next();
                            if (donee.getDoneeID().equals(doneeID)) {
                                // Update the donee details
                                selectedDonee = donee;
                                break; // Exit the loop once the donee is found and updated
                            }
                        }

                        if (selectedDonee != null) {
                            DoneeUI.displayIndividualDoneeDetailsUI(selectedDonee.getDoneeID(), selectedDonee.getName(), selectedDonee.getEmail(), selectedDonee.getPhone(), selectedDonee.getAddress(), selectedDonee.getDoneeType(), selectedDonee.getItemCategoryRequired(), selectedDonee.getDoneeUrgency(), selectedDonee.getRegisteredDate());
                        } else {
                            Message.displayDataNotFoundMessage("Donee with ID " + doneeID + " was not found.");
                        }
                        done = true;
                        displayOptionValid = true;
                        GeneralFunction.enterToContinue();
                        break;
                    case "S":
                        int sortOption;

                        while (true) { //Input Validation Loop
                            String inputSortOption = DoneeUI.sortDoneeUI();

                            if (inputSortOption.isEmpty()) {
                                Message.displayEmptyInputMessage("Please select a valid sorting criterion (1 or 2).");
                            } else {
                                try {
                                    sortOption = Integer.parseInt(inputSortOption);
                                    if (sortOption >= 1 && sortOption <= 2) {
                                        break;  // Exit loop for valid choice input
                                    } else {
                                        Message.displayInvalidInputMessage("Please select a valid sorting criteria (1 or 2).");
                                    }
                                } catch (NumberFormatException e) {  // Catch any non-integer inputs
                                    Message.displayInvalidInputMessage("Please input a valid number.");
                                }
                            }
                        }

                        switch (sortOption) {
                            case 1:
                                DoneeSorter.sortByPriority(donees);
                                break;
                            case 2:
                                DoneeSorter.sortReverseID(donees);
                                break;
                        }
                        done = true;
                        displayOptionValid = true;
                        break;
                    case "X":
                        done = false;
                        GeneralFunction.enterToContinue();
                        displayOptionValid = true;
                        break;
                    default:
                        Message.displayInvalidChoiceMessage("Please enter a valid option (D, S, X).");
                        displayOptionValid = false;
                }
            } while (!displayOptionValid);
        } while (done);
    }

    public ListInterface<DonationDistribution> initializeDistribution() {
        ListInterface<DonationDistribution> distributionList = new ArrayList<>();
        distributionList.add(new DonationDistribution("DTR00001", "PC020", "Sunblock", 4, "DNE00001", "2024-09-01")); //Personal Care - Individual
        distributionList.add(new DonationDistribution("DTR00002", "MY100", "Cash", 10000, "DNE00005", "2024-09-05")); //Monetary - Family
        distributionList.add(new DonationDistribution("DTR00003", "FD006", "Cream Crackers", 5, "DNE00007", "2024-09-05")); //Food - Individual
        distributionList.add(new DonationDistribution("DTR00004", "FD020", "Cooking Oil 5L", 5, "DNE00013", "2024-09-10")); //Food -Family
        distributionList.add(new DonationDistribution("DTR00005", "MD042", "Cough Syrup", 100, "DNE00019", "2024-09-11")); //Medicine - Organization
        distributionList.add(new DonationDistribution("DTR00006", "MD049", "Paracetamol", 10, "DNE00021", "2024-09-13")); //Medicine - Family
        distributionList.add(new DonationDistribution("DTR00007", "FD030", "Maggi Noodles", 100, "DNE00024", "2024-09-18")); //Food - Organization
        distributionList.add(new DonationDistribution("DTR00008", "BG002", "Milo Powder 1KG", 2, "DNE00030", "2024-09-20")); //Beverage - Individual
        distributionList.add(new DonationDistribution("DTR00009", "MY121", "Cash", 13000, "DNE00031", "2024-09-26")); //Monetary - Family
        distributionList.add(new DonationDistribution("DTR00010", "PC055", "Johnson Body Shampoo 1.5L", 5, "DNE00039", "2024-09-29")); //Personal Care - Family

        return distributionList;
    }

    public void viewDonationDistribution() {
        ListInterface<DonationDistribution> donationList = initializeDistribution();

        // Get user input for the donee ID
        DoneeUI.displayDoneeUI();
        String doneeID = obtainDoneeId();

        // Display matching donations
        boolean found = false;

        DoneeUI.displayDonationDetailsUI(doneeID);

        Iterator<DonationDistribution> distributionIterator = donationList.iterator();

        while (distributionIterator.hasNext()) {
            DonationDistribution donation = distributionIterator.next();
            if (donation.getDoneeID().equals(doneeID)) {
                // Display the donation information
                System.out.printf("%-20s %-20s %-20s %-20s %-20s%n",
                        donation.getDistributionID(),
                        donation.getItemCode(),
                        donation.getItemName(),
                        donation.getItemQuantity(),
                        donation.getDistributionDate());
                found = true;
            }
        }

        if (!found) {
            Message.displayDataNotFoundMessage("No donations found for Donee ID: " + doneeID);
        }

        GeneralFunction.repeatPrint("-", 100);

        DoneeUI.printEmptyLine();
        GeneralFunction.enterToContinue();
    }

    public static void filterDonees(ListInterface<Donee> donees) {
        int filterChoice;

        do {
            while (true) { //Input Validation Loop
                String filterInput = DoneeUI.filterDoneeUI();  // Read the input as a string and trim any surrounding spaces

                if (filterInput.isEmpty()) {
                    Message.displayEmptyInputMessage("Please enter a number between 1 and 3.");
                } else {
                    try {
                        filterChoice = Integer.parseInt(filterInput);
                        if (filterChoice >= 1 && filterChoice <= 3) {
                            break;  // Exit loop for valid choice input
                        } else {
                            Message.displayInvalidChoiceMessage("Please enter a number between 1 and 3.");
                        }
                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                        Message.displayInvalidInputMessage("Please enter a valid number.");
                    }
                }
            }

            switch (filterChoice) {
                case 1:
                    DoneeFilter.filterByType(donees);
                    break;
                case 2:
                    DoneeFilter.filterByUrgency(readDonees());
                    break;
                case 3:
                    break;
            }

        } while (filterChoice != 3);

        GeneralFunction.enterToContinue();
    }

    public static void summaryReport(ListInterface<Donee> donees){
        int highUrgencyCount = 0;
        int mediumUrgencyCount = 0;
        int lowUrgencyCount = 0;
        int individualCount = 0;
        int organisationCount = 0;
        int familyCount = 0;
        int foodCount = 0;
        int beverageCount = 0;
        int clothingCount = 0;
        int personalCount = 0;
        int deviceCount = 0;
        int medicineCount = 0;
        int moneyCount =0;

        doneeList = donees;

        GeneralFunction.printTitle("Donee Management Subsystem Summary Report", 87, "--", "|");

        //Donee Urgency
        Iterator<Donee> doneeIterator = donees.iterator();
        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();
            String urgency = donee.getDoneeUrgency();
            if ("High".equalsIgnoreCase(urgency)) {
                highUrgencyCount++;
            } else if ("Moderate".equalsIgnoreCase(urgency)) {
                mediumUrgencyCount++;
            } else if ("Low".equalsIgnoreCase(urgency)) {
                lowUrgencyCount++;
            }
        }

        DoneeUI.reportDoneeUrgencyUI(highUrgencyCount,mediumUrgencyCount,lowUrgencyCount);

        DoneeUI.printEmptyLine();
        GeneralFunction.repeatPrint("=", 87);
        DoneeUI.printEmptyLine();

        //Donee Type
        doneeIterator = donees.iterator(); // Reset iterator
        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();
            String doneeType = donee.getDoneeType();
            if ("Individual".equalsIgnoreCase(doneeType)) {
                individualCount++;
            } else if ("Organization".equalsIgnoreCase(doneeType)) {
                organisationCount++;
            } else if ("Family".equalsIgnoreCase(doneeType)) {
                familyCount++;
            }
        }

        DoneeUI.reportDoneeTypeUI(individualCount, organisationCount, familyCount);

        DoneeUI.printEmptyLine();
        GeneralFunction.repeatPrint("=", 87);
        DoneeUI.printEmptyLine();

        //Donee Item Category Required
        doneeIterator = donees.iterator(); // Reset iterator
        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();
            String doneeCategory = donee.getItemCategoryRequired();
            if ("Food".equalsIgnoreCase(doneeCategory)) {
                foodCount++;
            } else if ("Beverage".equalsIgnoreCase(doneeCategory)) {
                beverageCount++;
            } else if ("Clothing".equalsIgnoreCase(doneeCategory)) {
                clothingCount++;
            } else if ("Personal Care".equalsIgnoreCase(doneeCategory)) {
                personalCount++;
            } else if ("Medical Device".equalsIgnoreCase(doneeCategory)) {
                deviceCount++;
            } else if ("Medicine".equalsIgnoreCase(doneeCategory)) {
                medicineCount++;
            } else if ("Monetary Aid".equalsIgnoreCase(doneeCategory)) {
                moneyCount++;
            }
        }

        DoneeUI.reportItemCategoryUI(foodCount, beverageCount, clothingCount, personalCount, deviceCount, medicineCount, moneyCount);

        DoneeUI.printEmptyLine();

        GeneralFunction.printTitle("Report generated on: " + LocalDate.now(), 87, "--", "|");

        DoneeUI.printEmptyLine();
        GeneralFunction.enterToContinue();
    }

    public static void doneeHandler() {
        DoneeFunctions doneeFunctions = new DoneeFunctions();
        doneeFunctions.runDoneeManagement();
    }
}
