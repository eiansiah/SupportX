/*
 *  Author: Sim Hor Kang
 *  ID: 2307611
 *
 *  Co-author: Siah E-ian
 *  ID: 2307610
 *
 * */

package Control;

//Entity
import Entity.DonationItem;
import Entity.DonationRecord;
import Entity.Donor;

//Data Handling
import DAO.DonorFileHandler;

//ADT
import ADT.*;

//Boundaries
import Boundary.DonorUI;

//Utilities
import Utilities.GeneralFunction;
import Utilities.NewValidation;
import Utilities.Message;

//Misc
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Scanner;

public class DonorFunctions {

    private static ListInterface<Donor> donorArrayList;

    private static final DonorFileHandler donorFileHandler = new DonorFileHandler();

    public DonorFunctions (){
        donorArrayList = readDonors();
    }

    public static ListInterface<Donor> readDonors(){
        return donorFileHandler.readData("donor.txt");
    }

    public static void runDonorSystem() {
        int choice = 0;

        donorFileHandler.checkAndCreateFile("donor.txt");

        GeneralFunction.printTitle("Welcome to Donor Subsystem!", 60, "--", "|");

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
                    displayDonorDonationConnection(DonorUI.inputCheckDonorIDUI());
                    break;
                case 6: //View reports
                    viewDonorReport(readDonors());
                    break;
                default: //Exit
                    Message.displayExitMessage();
                    break;            }

        } while (choice >= 1 && choice <= 6);
    }

    public static Donor checkIfDonorExist(String donorID) {
        ListInterface<Donor> checkDonors = readDonors(); // Assuming readDonors() retrieves the list of donors

        // Check for the donor by ID
//        for (int i = 0; i < checkDonors.size(); i++) {
//            Donor donor = checkDonors.get(i);
//
//            if (donor.getId().equals(donorID)) {
//                return donor; // Return the entire Donor object if found
//            }
//        }
        Iterator<Donor> donorIterator = checkDonors.iterator();
        while (donorIterator.hasNext()) {
            Donor donor = donorIterator.next();

            if (donor.getId().equals(donorID)) {
                return donor; // Return the entire Donor object if found
            }
        }

        // If the donor is not found, display a message and return null
//        Message.displayDataNotFoundMessage("Donor with ID " + donorID + " was not found.");
        return null;
    }

    public static void addDonor() {
        // Get the last ID from the file
        String lastId = donorFileHandler.getLastDonorId("donor.txt");
        // Increment to get the new ID
        String newDonorId = donorFileHandler.incrementDonorId(lastId);
        Donor donor = inputDonorDetails(newDonorId);
        // Save the donor with the new ID
        donorFileHandler.saveData("donor.txt", donor);
    }

    public static Donor inputDonorDetails(String donorId){
        boolean isValid;
        DonorUI.addDonorUI();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
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
                    Message.displayInvalidChoiceMessage("Please select a valid category (1-3).");
                    isValid = false;
                }
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                isValid = false;
            }

            if (isValid)
                category = NewValidation.validateCategory(choice);
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
                    Message.displayInvalidChoiceMessage("Please select a valid type (1-2).");
                    isValid = false;
                }
            } catch (NumberFormatException e) {  // Catch any non-integer inputs
                Message.displayInvalidInputMessage("Please enter a valid number and ensure input is not empty.");
                isValid = false;
            }

            if (isValid)
                type = NewValidation.validateDonorType(choice);

        } while (!isValid);

        LocalDate registeredDate = LocalDate.now();

        return new Donor(donorId, name, email, phone, category, type, registeredDate);
    }

    public static void deleteDonor(ListInterface<Donor> donors) {
        Donor selectedDonor = null;

        String donorIDToDelete = DonorUI.inputRemoveDonorIDUI();

//        // Search for the donor by ID
//        for (int i = 0; i < donors.size(); i++) {
//            Donor donor = donors.get(i);
//
//            if (donor.getId().equals(donorIDToDelete)) {
//                selectedDonor = donor;
//                break;
//            }
//        }

        // Use iterator to search for the donor by ID
        Iterator<Donor> donorIterator = donors.iterator();
        while (donorIterator.hasNext()) {
            Donor donor = donorIterator.next();
            if (donor.getId().equals(donorIDToDelete)) {
                selectedDonor = donor;
                break; // Exit the loop once the donor is found
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
                    selectedDonor.getType(),
                    selectedDonor.getRegisteredDate()
            );

            if (confirmation.equals("Y")) {
                donors.remove(selectedDonor);
                donorFileHandler.updateMultipleData("donor.txt", donors);
                DonorUI.displayDeleteDonorMsg(donorIDToDelete);
            } else {
                Message.displayRemoveCancelMessage();
            }
        } else {
            Message.displayDataNotFoundMessage("Donor with ID " + donorIDToDelete + " was not found.");
        }
    }

    public static void modifyDonor(ListInterface<Donor> donors) {
        Donor selectedDonor = null;
        String donorIDToModify = DonorUI.inputUpdateDonorIDUI();

        // Use iterator to search for the donor by ID
        Iterator<Donor> donorIterator = donors.iterator();
        while (donorIterator.hasNext()) {
            Donor donor = donorIterator.next();
            if (donor.getId().equals(donorIDToModify)) {
                selectedDonor = donor;
                break; // Exit the loop once the donor is found
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
                    selectedDonor.getType(),
                    selectedDonor.getRegisteredDate()
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

                        case "X", "x":
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
                donorFileHandler.updateMultipleData("donor.txt", donors);

            } else {
                Message.displayUpdateCancelMessage();
            }
        } else {
            Message.displayDataNotFoundMessage("Donor with ID " + donorIDToModify + " was not found.");
        }
    }

    public static void displayDonors(ListInterface<Donor> donors) {

        int pageSize = 10;  // Number of donors to display per page
        int currentPage = 0;
        int totalDonors = donors.size();
        boolean done = false;
        boolean showCategory = false;
        boolean showType = false;

        Stack<ListInterface<Donor>> stateStack = new Stack<>(); // Stack to store donor list states

        // Save the initial state (original list) into the stack
        stateStack.push(donors);

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
//                        for (int i = 0; i < donors.size(); i++) {
//                            Donor donor = donors.get(i);
//
//                            if (donor.getId().equals(donorID)) {
//                                selectedDonor = donor;
//                                break;
//                            }
//                        }

                        // Use an iterator to search for the donor by ID
                        Iterator<Donor> donorIterator = donors.iterator();
                        while (donorIterator.hasNext()) {
                            Donor donor = donorIterator.next();

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
                                    selectedDonor.getType(),
                                    selectedDonor.getRegisteredDate()
                            );



                        } else {
                            DonorUI.donorNotFoundMsg(donorID);
                        }

                        DonorUI.pressKeyToGoBack();
                        break;

                case "F":
                    String filterChoice = DonorUI.filterMenu();

                    try {
                        if (filterChoice.equals("1")) {
                            String input = DonorUI.filterChoiceName();
                            if (!input.isEmpty()) {
                                char letter = input.charAt(0);
                                stateStack.push(donors);
                                donors = DonorFilter.filterByName(donors, letter);

                                if (donors.isEmpty()) {
                                    Message.displayDataNotFoundMessage("No donors found with names starting with " + letter + ".");
                                }
                            }
                        } else if (filterChoice.equals("2")) {
                            String categoryChoiceStr = DonorUI.filterChoiceCategory();
                            int categoryChoice = Integer.parseInt(categoryChoiceStr); // Parse the string to an integer
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
                                stateStack.push(donors);  // Save the current state before filtering
                                donors = DonorFilter.filterByCategory(donors, category);

                                if (donors.isEmpty()) {
                                    Message.displayDataNotFoundMessage("No donors found in the '" + category + "' category.");
                                }
                            }
                        } else if (filterChoice.equals("3")) {
                            String typeChoiceStr = DonorUI.filterChoiceType();
                            int typeChoice = Integer.parseInt(typeChoiceStr); // Parse the string to an integer
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
                                stateStack.push(donors);  // Save the current state before filtering
                                donors = DonorFilter.filterByType(donors, type);

                                if (donors.isEmpty()) {
                                    Message.displayDataNotFoundMessage("No donors found of type '" + type + "'.");
                                }
                            }
                        } else if (filterChoice.equals("4")) {
                            if (!stateStack.isEmpty()) {
                                donors = stateStack.pop();  // Restore the previous state

                                // Initialize flags to determine if any category or type filters are active
                                boolean hasCategoryFilter = false;
                                boolean hasTypeFilter = false;

                                // Peek into the previous state to determine showCategory and showType
                                if (!stateStack.isEmpty()) {
                                    ListInterface<Donor> previousState = stateStack.peek();

                                    // Determine if showCategory should be true
                                    if (previousState != null && !previousState.isEmpty()) {
                                        hasCategoryFilter = !DonorFilter.filterByCategory(previousState, "Government").isEmpty()
                                                || !DonorFilter.filterByCategory(previousState, "Private").isEmpty()
                                                || !DonorFilter.filterByCategory(previousState, "Public").isEmpty();

                                        // Determine if showType should be true
                                        hasTypeFilter = !DonorFilter.filterByType(previousState, "Individual").isEmpty()
                                                || !DonorFilter.filterByType(previousState, "Organization").isEmpty();
                                    }
                                    showCategory = hasCategoryFilter;
                                    showType = hasTypeFilter;

                                } else {
                                    // If stack is empty, reset both
                                    showCategory = false;
                                    showType = false;
                                }
                            }

                        } else if (filterChoice.equals("5")) {
                            Message.displayFilterCancelMessage();
                        } else {
                            Message.displayInvalidChoiceMessage("Please select a valid option.");
                        }
                    } catch (NumberFormatException e) {
                        // Handle invalid integer conversion, such as when parsing a non-integer string
                        Message.displayInvalidChoiceMessage("Invalid input! Please enter a valid number.");
                    } catch (Exception e) {
                        // Handle any other unexpected exceptions
                        Message.displayGeneralErrorMsg("An unexpected error occurred: " + e.getMessage());
                    }

                    totalDonors = donors.size();
                    currentPage = 0;  // Reset to the first page after applying the filter
                    break;

                case "S":
                    String sortOptionInput  = DonorUI.SortMenu();

                    try {
                        int sortOption = Integer.parseInt(sortOptionInput);  // Attempt to parse the input to an integer

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
                                Message.displayInvalidChoiceMessage("Invalid input! Please select a valid sorting option from 1 to 3.");
                                break;
                        }
                    } catch (NumberFormatException e) {
                        // Handle the case where input is not a valid integer
                        Message.displayInvalidChoiceMessage("Invalid input! Please enter a number from 1 to 3.");
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

    public static void viewDonorReport(ListInterface<Donor> donors) {
        // Initialize the queue and enqueue filter criteria
        Queue<String> filterQueue = new Queue<>();
        filterQueue.enqueue("Public");
        filterQueue.enqueue("Private");
        filterQueue.enqueue("Government");
        filterQueue.enqueue("Individual");
        filterQueue.enqueue("Organization");

        // Store the counts in an ArrayList
        ArrayList<Integer> filterCounts = new ArrayList<>();

        // Process each filter in the queue
        while (!filterQueue.isEmpty()) {
            // Dequeue the next filter
            String filter = filterQueue.dequeue();

            // Apply the appropriate filter
            if (filter.equals("Individual") || filter.equals("Organization")) {
                donors = DonorFilter.filterByType(donors, filter);
            } else {
                donors = DonorFilter.filterByCategory(donors, filter);
            }

            // Save the count to the ArrayList
            filterCounts.add(donors.size());

            // Reset the filter
            donors = DonorFilter.resetFilter(donors);
        }

        GeneralFunction.printTitle("Donor Subsystem Summary Report", 87, "--", "|");
        GeneralFunction.printTitle("Total Number of Donors", 87, "--", "|");
        DonorUI.totalNumberOfDonors(donors.size());
        GeneralFunction.printTitle("Summary Number of Donors by Different Types and Category", 87, "--", "|");
        DonorUI.viewSummaryDonorData(filterCounts);
        GeneralFunction.printTitle("Donor with the Most Donations Record", 87, "--", "|");
        displayDonationData();
        GeneralFunction.printTitle("Number fo Donor That Joined This Month", 87, "--", "|");
        displayDonorJoinedThisMonth();
        GeneralFunction.printTitle("Report generated on: " + LocalDate.now(), 87, "--", "|");
        DonorUI.pressKeyToContinue();
    }

    public static void displayDonorDonationConnection(String donorId){
        Donation donation = new Donation();
        ListInterface<DonationRecord> donationRecord = new ArrayList<>();
        donationRecord = donation.findRecordsForADonor(donorId);
        ListInterface<Donor> donorDetails = readDonors();

        // Check if the donor exists in donorDetails
        boolean donorExists = false;
//        for (int i = 0; i < donorDetails.size(); i++) {
//            if (donorDetails.get(i).getId().equals(donorId)) {
//                donorExists = true;
//                break;
//            }
//        }

        Iterator<Donor> donorIterator = donorDetails.iterator();
        while (donorIterator.hasNext()) {
            Donor donor = donorIterator.next();
            if (donor.getId().equals(donorId)) {
                donorExists = true;
                break;
            }
        }

        // If donor does not exist, display a message and exit
        if (!donorExists) {
            Message.displayDataNotFoundMessage("Donor does not exist found.");
            return;
        }


        if (donationRecord == null || donationRecord.isEmpty()) {
            Message.displayDataNotFoundMessage("Donor has not made any donations.");
            return;  // Exit the method if no records are found
        }

//        for (int i = 0; i < donationRecord.size(); i++){
//            DonationRecord record = donationRecord.get(i);
        Iterator<DonationRecord> recordIterator = donationRecord.iterator();
        while (recordIterator.hasNext()) {
            DonationRecord record = recordIterator.next();
//            System.out.println("Record ID: " + record.getRecordID());
//            System.out.println("Donor: " + record.getDonor().getId());
            DonorUI.showRecordID(record.getRecordID());
//            for (int itemIterator = 0; itemIterator < record.getItem().size(); itemIterator++){
//                DonorUI.showItemData(record.getItem().get(itemIterator));
//            }
            Iterator<DonationItem> itemIterator = record.getItem().iterator();
            while (itemIterator.hasNext()) {
                DonorUI.showItemData(itemIterator.next());
            }
//            System.out.println("Donation Date/Time: " + record.getDonationDateTime());
            DonorUI.showItemDate(record.getDonationDateTime());
            System.out.println();
        }
    }

    public static void displayDonationData() {

        // Initialize Donor ArrayList
        ListInterface<Donor> donorDetails = readDonors();

        // Initialize Donation ArrayList
        Donation donation = new Donation();
        ListInterface<DonationRecord> donationDetails = new ArrayList<>();

        // Variables to track the donor with the most records
        Donor topDonor = null;
        int maxRecords = 0;

//        for (int i = 0; i < donorDetails.size(); i++) {
//            // Read through all donors and get their ID
//            String donorID = donorDetails.get(i).getId();
//
//            // Pass donorID to get all donation data for that one ID
//            ListInterface<DonationRecord> currentDonorDonations = donation.findRecordsForADonor(donorID);
//
//            // Add all records from the current donor to the main donationDetails list
//            for (int j = 0; j < currentDonorDonations.size(); j++) {
//                donationDetails.add(currentDonorDonations.get(j));
//            }
//
//            // Check if the current donor has the most records
//            if (currentDonorDonations.size() > maxRecords) {
//                maxRecords = currentDonorDonations.size();
//                topDonor = donorDetails.get(i);
//            }
//        }

        // Use an iterator to loop through donorDetails
        Iterator<Donor> donorIterator = donorDetails.iterator();
        while (donorIterator.hasNext()) {
            // Read through all donors and get their ID
            Donor currentDonor = donorIterator.next();
            String donorID = currentDonor.getId();

            // Pass donorID to get all donation data for that one ID
            ListInterface<DonationRecord> currentDonorDonations = donation.findRecordsForADonor(donorID);

            // Add all records from the current donor to the main donationDetails list
            Iterator<DonationRecord> recordIterator = currentDonorDonations.iterator();
            while (recordIterator.hasNext()) {
                donationDetails.add(recordIterator.next());
            }

            // Check if the current donor has the most records
            if (currentDonorDonations.size() > maxRecords) {
                maxRecords = currentDonorDonations.size();
                topDonor = currentDonor;
            }
        }

        // Output the donor with the most records
        if (topDonor != null) {
            DonorUI.donorWithMostRecord(topDonor.getId(),topDonor.getName(),maxRecords);
        } else {
            Message.displayDataNotFoundMessage("No donors found.");
        }
    }

    public static void displayDonorJoinedThisMonth(){
        // Get the current month and year
        LocalDate currentDate = LocalDate.now();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Retrieve donor data
        ListInterface<Donor> donorData = readDonors();

        // Counter for the number of donors who joined this month
        int donorsJoinedThisMonth = 0;

//        // Get number of donors who joined this month
//        for (int i = 0; i < donorData.size(); i++) {
//            Donor donor = donorData.get(i);
//            LocalDate registeredDate = donor.getRegisteredDate();
//
//            // Check if the donor joined in the current month and year
//            if (registeredDate.getMonthValue() == currentMonth && registeredDate.getYear() == currentYear) {
//                donorsJoinedThisMonth++;
//            }
//        }

        // Use an iterator to loop through donorData
        Iterator<Donor> donorIterator = donorData.iterator();
        while (donorIterator.hasNext()) {
            Donor donor = donorIterator.next();
            LocalDate registeredDate = donor.getRegisteredDate();

            // Check if the donor joined in the current month and year
            if (registeredDate.getMonthValue() == currentMonth && registeredDate.getYear() == currentYear) {
                donorsJoinedThisMonth++;
            }
        }

        DonorUI.donorAddedThisMonth(donorsJoinedThisMonth);

    }

}
