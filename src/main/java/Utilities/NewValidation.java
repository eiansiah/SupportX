package Utilities;

/*
 *  Author: Siah E-ian
 *  ID: 2307610
 *
 *  Co-Author: Sim Hor Kang
 *  ID: 2307611
 *
 *  Co-author: Tan Qian Yiin
 *  ID: 2307616
 *
 * */

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewValidation {

    public static boolean validateName(String name) {
        Pattern nameFormat = Pattern.compile("^[A-Za-z]{2}[A-Za-zâ€˜\\- /.]{1,30}$");
            Matcher matcher = nameFormat.matcher(name);
        return matcher.matches() && !name.isEmpty();
    }

    public static boolean validateEmail(String email) {
        Pattern emailFormat = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
            Matcher matcher = emailFormat.matcher(email);
        return matcher.matches() && !email.isEmpty();
    }

    public static boolean validatePhone(String phone) {
        Pattern phoneFormat = Pattern.compile("^01([0-9]{8,9})$");
            Matcher matcher = phoneFormat.matcher(phone);
        return matcher.matches() && !phone.isEmpty();
    }

    public static boolean validateAddress(String address) {
        return !address.isEmpty();
    }

    public static String validateCategory(int choice) {
        String category;

        switch (choice) {
            case 1:
                category = "Government";
                break;
            case 2:
                category = "Private";
                break;
            default:
                category = "Public";
        }

        return category;
    }

    public static String validateItemCategory(int input) {
        String itemCategory;

        switch (input) {
            case 1:
                itemCategory = "Food";
                break;
            case 2:
                itemCategory = "Beverage";
                break;
            case 3:
                itemCategory = "Clothing";
                break;
            case 4:
                itemCategory = "Personal Care";
                break;
            case 5:
                itemCategory = "Medical Device";
                break;
            case 6:
                itemCategory = "Medicine";
                break;
            default:
                itemCategory = "Monetary Aid";
        }

        return itemCategory;
    }

    public static String validateDonorType(int typeChoice) {
        String type;

        switch (typeChoice) {
            case 1:
                type = "Individual";
                break;
            default:
                type = "Organization";
        }

        return type;
    }

    public static String validateDoneeType(int input) {
        String type;

        switch (input) {
            case 1:
                type = "Individual";
                break;
            case 2:
                type = "Organization";
                break;
            default:
                type = "Family";
        }

        return type;
    }

    public static String validateDoneeUrgency(int input) {
        String doneeUrgency;

        switch (input) {
            case 1:
                doneeUrgency = "Low";
                break;
            case 2:
                doneeUrgency = "Moderate";
                break;
            default:
                doneeUrgency = "High";
        }

        return doneeUrgency;
    }
    
    //check for empty input
    public static boolean isValidInput(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidContactNumber(String phone) {
        return phone.matches("^01\\d{8,9}$");
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
    }

}
