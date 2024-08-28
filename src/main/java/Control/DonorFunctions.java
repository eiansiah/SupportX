package Control;

import Entity.Donor;

import FileHandling.DonorFileHandler;

import Libraries.ArrayList;

import Boundary.DonorUI;

import Utilities.NewValidation;
import Utilities.Message;

import java.util.Scanner;

public class DonorFunctions {

    private ArrayList<Donor> donorArrayList;
    private static final DonorFileHandler fileHandler = new DonorFileHandler();

    public DonorFunctions (){
        donorArrayList = readDonors();
    }

    public static ArrayList<Donor> readDonors(){
        return fileHandler.readData("donor.txt");
    }

    public void runDonorSystem() {
        int choice = 0;

        fileHandler.checkAndCreateFile("donor.txt");

        DonorUI.DonorWelcomeMessage();

        do {

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
                    //displayDonorDonation();
                    break;
                case 6: //View reports
                    break;
                default: //Exit
                    Message.displayExitMessage();
                    break;            }

        } while (choice >= 1 && choice <= 6);
    }

    public static Donor checkIfDonorExist(String donorID) {
        ArrayList<Donor> checkDonors = readDonors(); // Assuming readDonors() retrieves the list of donors

        // Check for the donor by ID
        for (Donor donor : checkDonors) {
            if (donor.getId().equals(donorID)) {
                return donor; // Return the entire Donor object if found
            }
        }

        // If the donor is not found, display a message and return null
        Message.displayDataNotFoundMessage("Donor with ID " + donorID + " was not found.");
        return null;
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
        boolean showCategory = false;
        boolean showType = false;

        while (!done) {
            int start = currentPage * pageSize;
            int end = Math.min(start + pageSize, totalDonors);

            String option = DonorUI.displayDonorTable(pageSize, currentPage, totalDonors, donors, start, end, showCategory, showType);

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

                    DonorUI.pressKeyToGoBack();
                    scanner.nextLine();
                    break;

                case "F":
                    int filterChoice = DonorUI.filterMenu();

                    ArrayList<Donor> donorCopy = new ArrayList<>();
                    for (Donor donor : donors) {
                        donorCopy.add(donor);
                    }

                    if (filterChoice == 1) {
                        String input = DonorUI.filterChoiceName();
                        if (!input.isEmpty()) {
                            char letter = input.charAt(0);
                            donors = DonorFilter.filterByName(donors, letter);

                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found with names starting with " + letter + ".");
                            }
                        }
                    } else if (filterChoice == 2) {
                        int categoryChoice = DonorUI.filterChoiceCategory();
                        String category = null;
                        showCategory = true;

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
                            donorCopy = new ArrayList<>();
                            for (Donor donor : donors) {
                                donorCopy.add(donor);
                            }

                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found in the '" + category + "' category.");
                            }
                        }
                    } else if (filterChoice == 3) {
                        int typeChoice = DonorUI.filterChoiceType();
                        String type = null;
                        showType = true;

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
                            donorCopy = new ArrayList<>();
                            for (Donor donor : donors) {
                                donorCopy.add(donor);
                            }

                            if (donors.isEmpty()) {
                                Message.displayDataNotFoundMessage("No donors found of type '" + type + "'.");
                            }
                        }
                    } else if (filterChoice == 4) {
                            donors = DonorFilter.resetFilter(donors);
                            showCategory = false;
                            showType = false;
                    } else {
                        Message.displayInvalidChoiceMessage("Please select a valid option.");
                    }

                    totalDonors = donors.size();
                    currentPage = 0;  // Reset to the first page after applying the filter
                    break;

                case "S":
                    int sortOption = DonorUI.SortMenu();

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
