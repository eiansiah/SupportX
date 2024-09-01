package Control;

/*
 *  author: Siah E-Ian
 *  ID: 2307610
 * */

import Boundary.DoneeUI;

import Entity.Donee;

import Utilities.GeneralFunction;

import ADT.ListInterface;
import Utilities.Message;

import java.util.Iterator;
import java.util.Objects;

public class DoneeFilter {
    public static void filterByType(ListInterface<Donee> donees) {
        int filterType;

        do {
            while (true) { //Input Validation Loop
                GeneralFunction.clearScreen();
                String filterTypeInput = DoneeUI.filterDoneeTypeUI(); // Read the input as a string and trim any surrounding spaces

                if (filterTypeInput.isEmpty()) {
                    Message.displayEmptyInputMessage("Please enter a number between 1 and 4.");
                } else {
                    try {
                        filterType = Integer.parseInt(filterTypeInput);
                        if (filterType >= 1 && filterType <= 4) {
                            break;  // Exit loop for valid choice input
                        } else {
                            Message.displayInvalidChoiceMessage("Please enter a number between 1 and 4.");
                        }
                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                        Message.displayInvalidInputMessage("Please enter a number.");
                    }
                }
            }

            switch (filterType) {
                case 1:
                    individualFilter(donees);
                    break;
                case 2:
                    organizationFilter(donees);
                    break;
                case 3:
                    familyFilter(donees);
                    break;
                default:
                    GeneralFunction.enterToContinue();
            }

            if (filterType != 4) {
                DoneeUI.printEmptyLine();
                GeneralFunction.enterToContinue();
            }
        } while (filterType != 4);
    }

    public static void individualFilter(ListInterface<Donee> donees){
        boolean found = false;

        GeneralFunction.clearScreen();
        DoneeUI.individualFilterUI();
        Iterator<Donee> iterator = donees.iterator();

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (Objects.equals(donee.getDoneeType(), "Individual")) {
                DoneeUI.displayFilterDetailsUI(donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getRegisteredDate());
                found = true;
            }
        }

        if (!found) {
            Message.displayGeneralMessage("No donees found within the specified donee type.\n");
        }
        GeneralFunction.repeatPrint("-", 85);
    }

    public static void organizationFilter(ListInterface<Donee> donees){
        boolean found = false;

        GeneralFunction.clearScreen();
        DoneeUI.organizationFilterUI();
        Iterator<Donee> iterator = donees.iterator();

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (Objects.equals(donee.getDoneeType(), "Organization")) {
                DoneeUI.displayFilterDetailsUI(donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getRegisteredDate());
                found = true;
            }
        }
        if (!found) {
            Message.displayGeneralMessage("No donees found within the specified donee type.\n");
        }
        GeneralFunction.repeatPrint("-", 85);
    }

    public static void familyFilter(ListInterface<Donee> donees){
        boolean found = false;

        GeneralFunction.clearScreen();
        DoneeUI.familyFilterUI();
        Iterator<Donee> iterator = donees.iterator();

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (Objects.equals(donee.getDoneeType(), "Family")) {
                DoneeUI.displayFilterDetailsUI(donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeType(), donee.getRegisteredDate());
                found = true;
            }
        }
        if (!found) {
            Message.displayGeneralMessage("No donees found within the specified donee type.\n");
        }
        GeneralFunction.repeatPrint("-", 85);
    }

    public static void filterByUrgency(ListInterface<Donee> donees){
        int filterUrgency;

        do {
            while (true) { //Input Validation Loop
                GeneralFunction.clearScreen();
                String filterUrgencyInput = DoneeUI.filterUrgencyTypeUI();  // Read the input as a string and trim any surrounding spaces

                if (filterUrgencyInput.isEmpty()) {
                    Message.displayEmptyInputMessage("Please enter a number between 1 and 4.");
                } else {
                    try {
                        filterUrgency = Integer.parseInt(filterUrgencyInput);
                        if (filterUrgency >= 1 && filterUrgency <= 4) {
                            break;  // Exit loop for valid choice input
                        } else {
                            Message.displayInvalidChoiceMessage("Please enter a number between 1 and 4.");
                        }
                    } catch (NumberFormatException e) {  // Catch any non-integer inputs
                        Message.displayInvalidInputMessage("Please enter a number.");
                    }
                }
            }

            switch (filterUrgency) {
                case 1:
                    filterLowUrgency(donees);
                    break;
                case 2:
                    filterModerateUrgency(donees);
                    break;
                case 3:
                    filterHighUrgency(donees);
                    break;
                default:
                    GeneralFunction.enterToContinue();
            }

            if (filterUrgency != 4) {
                DoneeUI.printEmptyLine();
                GeneralFunction.enterToContinue();
            }
        } while (filterUrgency != 4);
    }

    public static void filterLowUrgency(ListInterface<Donee> donees){
        boolean found = false;

        GeneralFunction.clearScreen();
        DoneeUI.filterLowUrgencyUI();
        Iterator<Donee> iterator = donees.iterator();

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (Objects.equals(donee.getDoneeUrgency(), "Low")) {
                DoneeUI.displayFilterDetailsUI(donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency(), donee.getRegisteredDate());
                found = true;
            }
        }
        if (!found) {
            Message.displayGeneralMessage("No donees found within the specified urgency category.\n");
        }
        GeneralFunction.repeatPrint("-", 85);
    }

    public static void filterModerateUrgency(ListInterface<Donee> donees){
        boolean found = false;

        GeneralFunction.clearScreen();
        DoneeUI.filterModerateUrgencyUI();
        Iterator<Donee> iterator = donees.iterator();

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (Objects.equals(donee.getDoneeUrgency(), "Moderate")) {
                DoneeUI.displayFilterDetailsUI(donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency(), donee.getRegisteredDate());
                found = true;
            }
        }
        if (!found) {
            Message.displayGeneralMessage("No donees found within the specified urgency category.\n");
        }
        GeneralFunction.repeatPrint("-", 85);
    }

    public static void filterHighUrgency(ListInterface<Donee> donees){
        boolean found = false;

        GeneralFunction.clearScreen();
        DoneeUI.filterHighUrgencyUI();
        Iterator<Donee> iterator = donees.iterator();

        while (iterator.hasNext()) {
            Donee donee = iterator.next();
            if (Objects.equals(donee.getDoneeUrgency(), "High")) {
                DoneeUI.displayFilterDetailsUI(donee.getDoneeID(), donee.getName(), donee.getPhone(), donee.getDoneeUrgency(), donee.getRegisteredDate());
                found = true;
            }
        }
        if (!found) {
            Message.displayGeneralMessage("No donees found within the specified urgency category.\n");
        }
        GeneralFunction.repeatPrint("-", 85);
    }
}
