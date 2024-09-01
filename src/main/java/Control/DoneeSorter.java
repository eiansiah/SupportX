package Control;

/*
 *  author: Siah E-Ian
 *  ID: 2307610
 * */

import Entity.Donee;
import ADT.ListInterface;
import ADT.PriorityQueue;

import java.util.Comparator;
import java.util.Iterator;

public class DoneeSorter {
    // Bubble Sort by ID in Descending or Ascending Order
    public static void sortReverseID(ListInterface<Donee> object) {
        // The flag to heck whether the list is currently in descending order.
        boolean isDescending = true;

        // Check if the list is in descending order
        for (int i = 0; i < object.size() - 1; i++) { // Read through the list
            // The compare function compares two strings lexicographically
            // If first object is less than second object, set flag to false
            // e.g. 01(first) is smaller than 02(second) means is ascending so the flag should be false to reverse order
            // e.g. 03(first) is larger than 02(second) means is descending so the flag should be true to reverse order
            if (object.get(i).getDoneeID().compareTo(object.get(i + 1).getDoneeID()) < 0) {
                isDescending = false; //The list is not in descending order
                break;
            }
        }

        // If the list is in descending order, sort it in ascending order
        if (isDescending) {
            for (int i = 0; i < object.size() - 1; i++) {
                for (int j = 0; j < object.size() - i - 1; j++) {
                    if (object.get(j).getDoneeID().compareTo(object.get(j + 1).getDoneeID()) > 0) {
                        // Swap elements to sort in ascending order
                        Donee temp = object.get(j);
                        object.set(j, object.get(j + 1));
                        object.set(j + 1, temp);
                    }
                }
            }
        } else {
            // If the list is not in descending order, sort it in descending order
            for (int i = 0; i < object.size() - 1; i++) {
                for (int j = 0; j < object.size() - i - 1; j++) {
                    if (object.get(j).getDoneeID().compareTo(object.get(j + 1).getDoneeID()) < 0) {
                        // Swap elements to sort in descending order
                        Donee temp = object.get(j);
                        object.set(j, object.get(j + 1));
                        object.set(j + 1, temp);
                    }
                }
            }
        }
    }

    public static void sortByPriority(ListInterface<Donee> object) {
        // Define the comparator based on the donee's string priority
        Comparator<Donee> doneePriorityComparator = new Comparator<Donee>() {
            @Override
            public int compare(Donee d1, Donee d2) {
                // Convert priority strings to numeric values for comparison
                return getPriorityValue(d2.getDoneeUrgency()) - getPriorityValue(d1.getDoneeUrgency());
            }

            private int getPriorityValue(String priority) {
                switch (priority.toLowerCase()) {
                    case "high":
                        return 3;
                    case "moderate":
                        return 2;
                    case "low":
                        return 1;
                    default:
                        return 0; // Unknown priority, treated as lowest
                }
            }
        };

        // Initialize the custom priority queue with the comparator
        PriorityQueue<Donee> priorityQueue = new PriorityQueue<>(doneePriorityComparator);

        // Enqueue all donees into the priority queue
        Iterator<Donee> doneeIterator = object.iterator();

        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();
            priorityQueue.enqueue(donee);
        }

        // Clear the original list and add back donees in sorted order
        object.clear();
        while (!priorityQueue.isEmpty()) {
            object.add(priorityQueue.dequeue());
        }
    }
}
