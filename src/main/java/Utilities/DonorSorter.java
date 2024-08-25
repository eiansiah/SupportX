package Utilities;

import Libraries.ArrayList;
import Main.Donor;

public class DonorSorter {

    // Bubble Sort by Name in Ascending Order
    public static void sortName(ArrayList<Donor> object) {
        for (int i = 0; i < object.size() - 1; i++) {
            for (int j = 0; j < object.size() - i - 1; j++) {
                if (object.get(j).getName().compareTo(object.get(j + 1).getName()) > 0) {
                    // Swap elements
                    Donor temp = object.get(j);
                    object.set(j, object.get(j + 1));
                    object.set(j + 1, temp);
                }
            }
        }
    }

    // Bubble Sort by Name in Descending Order
    public static void sortNameDescending(ArrayList<Donor> object) {
        for (int i = 0; i < object.size() - 1; i++) {
            for (int j = 0; j < object.size() - i - 1; j++) {
                if (object.get(j).getName().compareTo(object.get(j + 1).getName()) < 0) {
                    // Swap elements
                    Donor temp = object.get(j);
                    object.set(j, object.get(j + 1));
                    object.set(j + 1, temp);
                }
            }
        }
    }

    // Bubble Sort by ID in Descending or Ascending Order
    public static void reverseID(ArrayList<Donor> object) {
        // The flag to heck whether the list is currently in descending order.
        boolean isDescending = true;

        // Check if the list is in descending order
        for (int i = 0; i < object.size() - 1; i++) { // Read through the list
            // The compare function compares two strings lexicographically
            // If first object is less than second object, set flag to false
            // e.g. 01(first) is smaller than 02(second) means is ascending so the flag should be false to reverse order
            // e.g. 03(first) is larger than 02(second) means is descending so the flag should be true to reverse order
            if (object.get(i).getId().compareTo(object.get(i + 1).getId()) < 0) {
                isDescending = false; //The list is not in descending order
                break;
            }
        }

        // If the list is in descending order, sort it in ascending order
        if (isDescending) {
            for (int i = 0; i < object.size() - 1; i++) {
                for (int j = 0; j < object.size() - i - 1; j++) {
                    if (object.get(j).getId().compareTo(object.get(j + 1).getId()) > 0) {
                        // Swap elements to sort in ascending order
                        Donor temp = object.get(j);
                        object.set(j, object.get(j + 1));
                        object.set(j + 1, temp);
                    }
                }
            }
        } else {
            // If the list is not in descending order, sort it in descending order
            for (int i = 0; i < object.size() - 1; i++) {
                for (int j = 0; j < object.size() - i - 1; j++) {
                    if (object.get(j).getId().compareTo(object.get(j + 1).getId()) < 0) {
                        // Swap elements to sort in descending order
                        Donor temp = object.get(j);
                        object.set(j, object.get(j + 1));
                        object.set(j + 1, temp);
                    }
                }
            }
        }
    }


}
