package Control;

import Boundary.DoneeUI;
import Entity.Donee;
import Entity.Donor;

import FileHandling.DoneeFileHandler;
import FileHandling.DonorFileHandler;

import Libraries.ArrayList;
import Libraries.Color;

import Boundary.DonorUI;

import Utilities.NewValidation;
import Utilities.Validation;
import Utilities.Message;

import java.util.Scanner;

public class DonorFunctions {

    private ArrayList<Donor> donorArrayList;
    private static final DonorFileHandler fileHandler = new DonorFileHandler();
    private static final Scanner scanner = new Scanner(System.in); //To be shifted

    public DonorFunctions (){
        donorArrayList = readDonors();
    }

    public static ArrayList<Donor> readDonors(){
        return fileHandler.readData("donor.txt");
    }

    public void runDonorSystem() {
        int choice = 0;

        fileHandler.checkAndCreateFile("donor.txt");

        do {
            DonorUI.DonorWelcomeMessage();

            String inputChoice = DonorUI.getMainMenuChoice();

            try {
                choice = Integer.parseInt(inputChoice);
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                continue;
            }

            switch (choice) {
                case 1: //Add Donor
                    addDonor();
                    break;
                case 2: //Delete Donor
                    deleteDonor(readDonors());
                    break;
                case 3: //Update Donor
                    modifyDonor(readDonors());
                    break;
                case 4: //View All Donor Details
                    displayDonors(readDonors());
                    break;
                case 5: //View Donor Donation
                    break;
                case 6: //View reports
                    break;
                default: //Exit
                    Message.displayExitMessage();
                    break;            }

        } while (choice >= 1 && choice <= 6);
    }

    public static String obtainDonorId(){
        Scanner scanner = new Scanner(System.in);
        String donorId;

        donorId = scanner.nextLine().trim();

        if (donorId.isEmpty()) {
            Message.displayEmptyInputMessage("Please enter a valid ID.");
        }

        return donorId;
    }

    public static void addDonor() {
        // Get the last ID from the file
        String lastId = fileHandler.getLastDonorId("donor.txt");
        // Increment to get the new ID
        String newDonorId = fileHandler.incrementDonorId(lastId);
        Donor donor = inputDonorDetails(newDonorId);
        // Save the donor with the new ID
        fileHandler.saveData("donor.txt", donor);
    }

    public static Donor inputDonorDetails(String donorId){
        boolean isValid;
        DonorUI.addDonorUI();
        String name;
        do {
            name = DonorUI.inputDonorNameUI(); // input from boundary file
            isValid = NewValidation.validateName(name);
            if (!isValid) {
                Message.displayInvalidFormatMessage("name");
            }
        } while (!isValid);

        String email;
        do {
            email = DonorUI.inputDonorEmailUI(); // input from boundary file
            isValid = NewValidation.validateEmail(email);
            if (!isValid) {
                Message.displayInvalidFormatMessage("email");
            }
        } while (!isValid);

        String phone;
        do {
            phone = DonorUI.inputDonorPhoneUI();
            isValid = NewValidation.validatePhone(phone);
            if (!isValid) {
                Message.displayInvalidFormatMessage("phone");
            }
        } while (!isValid);

        String category = "";
        do{
            int choice = 0;
            String donorCategoryChoice = DonorUI.inputDonorCategoryUI();
            try {
                choice = Integer.parseInt(donorCategoryChoice);
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
                category = NewValidation.validateDonorType(choice);
        }while (!isValid);

        String type = "";
        do{
            int choice = 0;
            String donorTypeChoice = DonorUI.inputDonorTypeUI();
            try {
                choice = Integer.parseInt(donorTypeChoice);
                if (choice >= 1 && choice <= 2) {
                    isValid = true;  // Exit loop for valid choice input
                } else {
                    Message.displayInvalidChoiceMessage("Please select a valid item category (1-2).");
                    isValid = false;
                }
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                isValid = false;
            }

            if (isValid)
                type = NewValidation.validateDonorType(choice);

        } while (!isValid);


        return new Donor(donorId, name, email, phone, category, type);
    }

    public static void deleteDonor(ArrayList<Donor> donors) {
        Scanner scanner = new Scanner(System.in);
        Donor selectedDonor = null;

        String donorIDToDelete = DonorUI.inputRemoveDonorIDUI();

        // Search for the donor by ID
        for (Donor donor : donors) {
            if (donor.getId().equals(donorIDToDelete)) {
                selectedDonor = donor;
                break;
            }
        }

        if (selectedDonor != null) {
            // Display the donor's information before deletion
            String confirmation = DonorUI.deleteConfirmation(
                    selectedDonor.getId(),
                    selectedDonor.getName(),
                    selectedDonor.getEmail(),
                    selectedDonor.getPhone(),
                    selectedDonor.getCategory(),
                    selectedDonor.getType()
            );

            if (confirmation.equals("Y")) {
                donors.remove(selectedDonor);
                fileHandler.updateMultipleData("donor.txt", donors);
                DonorUI.displayDeleteDonorMsg(donorIDToDelete);
            } else {
                Message.displayRemoveCancelMessage();
            }
        } else {
            Message.displayDataNotFoundMessage("Donor with ID " + donorIDToDelete + " was not found.");
        }
    }

    public static void modifyDonor(ArrayList<Donor> donors) {
        Scanner scanner = new Scanner(System.in);
        Donor selectedDonor = null;
        String donorIDToModify = DonorUI.inputUpdateDonorIDUI();

        // Search for the donor by ID
        for (Donor donor : donors) {
            if (donor.getId().equals(donorIDToModify)) {
                selectedDonor = donor;
                break;
            }
        }

        if (selectedDonor != null) {
            // Display the donor's information before update
            String confirmation = DonorUI.displayDonorToBeUpdated(
                    selectedDonor.getId(),
                    selectedDonor.getName(),
                    selectedDonor.getEmail(),
                    selectedDonor.getPhone(),
                    selectedDonor.getCategory(),
                    selectedDonor.getType()
            );

            if (confirmation.equals("Y")) {
                String choice;

                do {

                    choice = DonorUI.promptUpdatePart();

                    boolean isValid;
                    switch (choice) {
                        case "1":
                            String name;
                            do {
                                name = DonorUI.inputDonorNameUI(); // input from boundary file
                                isValid = NewValidation.validateName(name);
                                if (!isValid) {
                                    Message.displayInvalidFormatMessage("name");
                                }
                            } while (!isValid);
                            break;
                        case "2":
                            String email;
                            do {
                                email = DonorUI.inputDonorEmailUI(); // input from boundary file
                                isValid = NewValidation.validateEmail(email);
                                if (!isValid) {
                                    Message.displayInvalidFormatMessage("email");
                                }
                            } while (!isValid);
                            break;
                        case "3":
                            String phone;
                            do {
                                phone = DonorUI.inputDonorPhoneUI();
                                isValid = NewValidation.validatePhone(phone);
                                if (!isValid) {
                                    Message.displayInvalidFormatMessage("phone");
                                }
                            } while (!isValid);

                            break;
                        case "4":
                            String category = "";
                            do{
                                int categoyrChoice = 0;
                                String donorCategoryChoice = DonorUI.inputDonorCategoryUI();
                                try {
                                    categoyrChoice = Integer.parseInt(donorCategoryChoice);
                                    if (categoyrChoice >= 1 && categoyrChoice <= 3) {
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
                                    category = NewValidation.validateDonorType(categoyrChoice);
                            }while (!isValid);
                            break;
                        case "5":
                            String type = "";
                            do{
                                int typeChoice = 0;
                                String donorTypeChoice = DonorUI.inputDonorTypeUI();
                                try {
                                    typeChoice = Integer.parseInt(donorTypeChoice);
                                    if (typeChoice >= 1 && typeChoice <= 2) {
                                        isValid = true;  // Exit loop for valid choice input
                                    } else {
                                        Message.displayInvalidChoiceMessage("Please select a valid item category (1-2).");
                                        isValid = false;
                                    }
                                } catch (NumberFormatException e) {  // Catch any non-integer inputs
                                    Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                                    isValid = false;
                                }

                                if (isValid)
                                    type = NewValidation.validateDonorType(typeChoice);

                            } while (!isValid);

                            break;

                        case "X":
                            Message.displayEndUpdateMessage();
                            break;
                        default:
                            Message.displayInvalidChoiceMessage("Please select a valid option.");
                            break;
                    }

                    if (!choice.equalsIgnoreCase("X")) {
                        DonorUI.displayUpdatedDonorMsg(donorIDToModify);
                    }

                } while (!choice.equalsIgnoreCase("X"));

                // Update the donor in the file after all modifications
                fileHandler.updateMultipleData("donor.txt", donors);

            } else {
                Message.displayUpdateCancelMessage();
            }
        } else {
            Message.displayDataNotFoundMessage("Donor with ID " + donorIDToModify + " was not found.");
        }
    }

    public static void displayDonors(ArrayList<Donor> donors) {

        Scanner scanner = new Scanner(System.in);
        int pageSize = 10;  // Number of donors to display per page
        int currentPage = 0;
        int totalDonors = donors.size();
        boolean done = false;

        while (!done) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, totalDonors);

            String option = DonorUI.displayDonorTable(pageSize,currentPage,totalDonors,donors, start, end);

            switch (option) {
                case "P":
                    if (currentPage > 0) {
                        currentPage--;
                    } else {
                        DonorUI.atFirstPage();
                    }
                    break;
                case "N":
                    if (end < totalDonors) {
                        currentPage++;
                    } else {
                        DonorUI.atLastPage();
                    }
                    break;
                case "D":

                    String donorID = DonorUI.inputCheckDonorIDUI();

                    Donor selectedDonor = null;
                    for (Donor donor : donors) {
                        if (donor.getId().equals(donorID)) {
                            selectedDonor = donor;
                            break;
                        }
                    }

                    if (selectedDonor != null) {
                        DonorUI.displaySelectedDonorDetail(
                                selectedDonor.getId(),
                                selectedDonor.getName(),
                                selectedDonor.getEmail(),
                                selectedDonor.getPhone(),
                                selectedDonor.getCategory(),
                                selectedDonor.getType()
                        );

                    } else {
                        DonorUI.donorNotFoundMsg(donorID);
                    }

                    DonorUI.pressKeyToContinue();
                    scanner.nextLine();
//                    done = true;
                    break;

                case "F":
                    int filterChoice = DonorUI.filterMenu();
                    scanner.nextLine();

                    if (filterChoice == 1) {
                        String input = DonorUI.filterChoiceName();

                        // Error handling for empty input
                        if (input.isEmpty()) {
                            Message.displayInvalidInputMessage("Starting letter cannot be empty.");
                        } else {
                            char letter = input.toUpperCase().charAt(0);
                            donors = DonorFilter.filterByName(donors, letter);

                            // Error handling for no donors found after filtering
                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found with names starting with " + letter + ".");
                            }
                        }
                    } else if (filterChoice == 2) {

                        String category = null;
                        int categoryChoice = DonorUI.filterChoiceCategory();
                        scanner.nextLine(); // Consume the newline character

                        switch (categoryChoice) {
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
                                Message.displayInvalidChoiceMessage("Please select a valid option.");
                                break;
                        }

                        if (category != null) {
                            donors = DonorFilter.filterByCategory(donors, category);

                            // Error handling for no donors found after filtering
                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found in the '" + category + "' category.");System.out.println(Color.BRIGHT_YELLOW + "No donors found in the '" + category + "' category." + Color.RESET);
                            }
                        }
                    } else if (filterChoice == 3) {
                        String type = null;
                        int typeChoice = DonorUI.filterChoiceType();
                        scanner.nextLine(); // Consume the newline character

                        switch (typeChoice) {
                            case 1:
                                type = "Individual";
                                break;
                            case 2:
                                type = "Organization";
                                break;
                            default:
                                Message.displayInvalidChoiceMessage("Please select a valid option.");
                                break;
                        }

                        if (type != null) {
                            donors = DonorFilter.filterByType(donors, type);

                            // Error handling for no donors found after filtering
                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found of type '" + type + "'.");
                            }
                        }
                    } else {
                        // Error handling for invalid filter choice
                        Message.displayInvalidChoiceMessage("Please select a valid option.");
                    }

                    // Update totalDonors after filtering
                    totalDonors = donors.size();
                    currentPage = 0;  // Reset to first page after applying filter
                    break;


                case "S":
                    int sortOption = DonorUI.SortMenu();
                    scanner.nextLine(); // Consume newline

                    switch (sortOption) {
                        case 1:
                            DonorSorter.sortName(donors);
                            break;
                        case 2:
                            DonorSorter.sortNameDescending(donors);
                            break;
                        case 3:
                            DonorSorter.reverseID(donors);
                            break;
                        default:
                            Message.displayInvalidChoiceMessage("Please select a valid sorting option.");
                            break;
                    }
                    break;

                case "X":
                    done = true;
                    break;

                default:
                    Message.displayInvalidChoiceMessage("Please try again.");
                    break;
            }
        }
    }

    public static void main (String[] args) {
        DonorFunctions donorFunctions = new DonorFunctions(); //Create an arraylist
        donorFunctions.runDonorSystem();
    }
}
