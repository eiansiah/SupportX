package Control;

import Boundary.DoneeUI;

import Entity.Donee;

import Libraries.ArrayList;
import Libraries.GeneralFunction;

import Utilities.Message;

import java.util.Objects;
import java.util.Scanner;

public class DoneeFilter {
    public static void filterByType(ArrayList<Donee> donees) {
        int filterType;

        do {
            while (true) { //Input Validation Loop
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
                GeneralFunction.enterToContinue();
            }
        } while (filterType != 4);
    }

    public static void individualFilter(ArrayList<Donee> donees){
        boolean found = false;

        DoneeUI.individualFilterUI();
        for (Donee donee : donees) {
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

    public static void organizationFilter(ArrayList<Donee> donees){
        boolean found = false;

        DoneeUI.organizationFilterUI();
        for (Donee donee : donees) {
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

    public static void familyFilter(ArrayList<Donee> donees){
        boolean found = false;

        DoneeUI.familyFilterUI();
        for (Donee donee : donees) {
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

    public static void filterByUrgency(ArrayList<Donee> donees){
        int filterUrgency;
        Scanner scanner = new Scanner(System.in);

        do {
            while (true) { //Input Validation Loop
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
                GeneralFunction.enterToContinue();
            }
        } while (filterUrgency != 4);
    }

    public static void filterLowUrgency(ArrayList<Donee> donees){
        boolean found = false;

        DoneeUI.filterLowUrgencyUI();
        for (Donee donee : donees) {
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

    public static void filterModerateUrgency(ArrayList<Donee> donees){
        boolean found = false;

        DoneeUI.filterModerateUrgencyUI();
        for (Donee donee : donees) {
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

    public static void filterHighUrgency(ArrayList<Donee> donees){
        boolean found = false;

        DoneeUI.filterHighUrgencyUI();
        for (Donee donee : donees) {
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
