package Control;

import java.time.LocalDate;
import java.util.Scanner;

import Entity.Donee;

import FileHandling.DoneeFileHandler;

import Libraries.GeneralFunction;
import Libraries.ArrayList;

import Utilities.Message;
import Utilities.NewValidation;

import Boundary.DoneeUI;

public class DoneeFunctions {

    private static ArrayList<Donee> doneeList;
    private static final DoneeFileHandler fileHandler = new DoneeFileHandler();
    private static final Scanner scanner = new Scanner(System.in); //To be shifted

    public DoneeFunctions (){
        doneeList = readDonees();
    }

    public static ArrayList<Donee> readDonees(){
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
                    break;
                case 6: //Filter donees by criteria
                    filterDonees(readDonees());
                    break;
                case 7: //View reports
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

    public static void deleteDoneeHandler(ArrayList<Donee> donees){
        doneeList = deleteDoneeCore(donees);

        fileHandler.updateMultipleData("donee.txt", doneeList);

        GeneralFunction.enterToContinue();
    }

    public static ArrayList<Donee> deleteDoneeCore(ArrayList<Donee> donees) {
        String doneeId;
        Donee selectedDonee = null;

        do {
            DoneeUI.deleteDoneeUI();
            doneeId = obtainDoneeId().toUpperCase();
        } while (doneeId.isEmpty());

        if (doneeId.equals("X")){
            return donees;
        }

        // Search for the donor by ID
        for (Donee donee : donees) {
            if (donee.getDoneeID().equals(doneeId)) {
                selectedDonee = donee;
                break;
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

    public static void modifyDoneeHandler(ArrayList<Donee> donees){
        doneeList = modifyDoneeCore(donees);

        fileHandler.updateMultipleData("donee.txt", doneeList);

        GeneralFunction.enterToContinue();
    }

    public static ArrayList<Donee> modifyDoneeCore(ArrayList<Donee> donees) {
        Scanner scanner = new Scanner(System.in);
        boolean doneeFound = false;
        String doneeId;

        do {
            DoneeUI.modifyDoneeUI();
            doneeId = obtainDoneeId();
        } while (doneeId.isEmpty());

        for (Donee doneeSelected : donees) {
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

    public static void displayDonees(ArrayList<Donee> donees){
        Scanner scanner = new Scanner(System.in);

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
                        displayOptionValid = true;
                        break;
                    case "D":
                        String doneeID;
                        do {
                            DoneeUI.displayDoneeUI();
                            doneeID = obtainDoneeId().toUpperCase();
                        } while (doneeID.isEmpty());

                        Donee selectedDonee = null;
                        for (Donee donee : donees) {
                            if (donee.getDoneeID().equals(doneeID)) {
                                selectedDonee = donee;
                                break;
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

    public static void filterDonees(ArrayList<Donee> donees) {
        Scanner scanner = new Scanner(System.in);
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

    public static void main (String[] args) {
        DoneeFunctions doneeFunctions = new DoneeFunctions();
        doneeFunctions.runDoneeManagement();
    }
}
