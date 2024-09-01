/*
 *  Author: Tan Qian Yiin
 *  ID: 2307616
 *
 * */
package Control;

import Boundary.VolunteerUI;
import Entity.Volunteer;
import DAO.VolunteerFileHandler;
import ADT.ArrayList;
import ADT.ListInterface;
import Utilities.GeneralFunction;
import Utilities.Message;

public class VolunteerFilter {

    private static final VolunteerFileHandler fileHandler = new VolunteerFileHandler();
    private static ListInterface<Volunteer> volunteers = new ArrayList<>();

    public static void filterVolunteers() {
        GeneralFunction.clearScreen();

        while (true) {
            try {
                volunteers = fileHandler.readData("volunteers.txt");
                int filterChoice = VolunteerUI.filterVolunteersUI();

                switch (filterChoice) {
                    case 1:
                        filterByAge();
                        break;
                    case 2:
                        filterByGender();
                        break;
                    case 3:
                        filterByAvailability();
                        break;
                    case 4:
                        return;
                    default:
                        Message.displayGeneralErrorMsg("Invalid choice. Please try again.");
                        break;
                }
            } catch (Exception ex) {
                Message.displayGeneralErrorMsg("Invalid choice. Please enter a number.");
            }
        }
    }

    private static void filterByAge() {
        int minAge = VolunteerUI.inputMinimumAgeUI();
        int maxAge = VolunteerUI.inputMaximumAgeUI();

        boolean found = false;
        GeneralFunction.clearScreen();
        VolunteerUI.filterByAgeUI(minAge, maxAge);

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            int age = Integer.parseInt(volunteer.getAge());
            if (age >= minAge && age <= maxAge) {
                VolunteerUI.filterVolunteerUI(volunteer);
                found = true;
            }
        }
        if (!found) {
            Message.displayDataNotFoundMessage("No volunteers found within the specified age range.");
        }
        VolunteerUI.displayEmptyString();
        GeneralFunction.enterToContinue();
        GeneralFunction.clearScreen();
    }

    private static void filterByGender() {

        String gender = VolunteerUI.inputFilterByGenderUI();

        if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
            Message.displayGeneralErrorMsg("Invalid gender. Please enter 'Male' or 'Female'.");
            return;
        }

        boolean found = false;
        GeneralFunction.clearScreen();
        VolunteerUI.filterByGenderUI(gender);

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getGender().equalsIgnoreCase(gender)) {
                VolunteerUI.filterVolunteerUI(volunteer);
                found = true;
            }
        }

        if (!found) {
            Message.displayDataNotFoundMessage("No volunteers found with the specified gender.");
        }
        VolunteerUI.displayEmptyString();
        GeneralFunction.enterToContinue();
        GeneralFunction.clearScreen();
    }

    private static void filterByAvailability() {

        String availability = VolunteerUI.inputFilterByAvailabilityUI();

        if (!(availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends"))) {
            Message.displayGeneralErrorMsg("Invalid availability. Please enter 'Weekdays' or 'Weekends'.");
            return;
        }

        boolean found = false;
        GeneralFunction.clearScreen();
        VolunteerUI.filterByAvailabilityUI(availability);

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getAvailability().equalsIgnoreCase(availability)) {
                VolunteerUI.filterVolunteerUI(volunteer);
                found = true;
            }
        }

        if (!found) {
            Message.displayDataNotFoundMessage("No volunteers found with the specified availability.");
        }
        
        VolunteerUI.displayEmptyString();
        GeneralFunction.enterToContinue();
        GeneralFunction.clearScreen();
    }
    

}
