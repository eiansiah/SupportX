/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Entity.Volunteer;
import Utilities.Message;


public class VolunteerFilter {
    
    
    private static void filterVolunteers() {
  

        int filterChoice = scanner.nextInt();
        scanner.nextLine();

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
            default:
                Message.displayGeneralErrorMsg("Invalid choice. Please try again.");
        }
    }

    private static void filterByAge() {
        System.out.print("Enter minimum age: ");
        int minAge = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter maximum age: ");
        int maxAge = scanner.nextInt();
        scanner.nextLine();

        boolean found = false;
        System.out.println("Volunteers between " + minAge + " and " + maxAge + " years old:");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            int age = Integer.parseInt(volunteer.getAge());
            if (age >= minAge && age <= maxAge) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }
        if (!found) {
            Message.displayDataNotFoundMessage("No volunteers found within the specified age range.");
        }
    }

    private static void filterByGender() {
        System.out.print("Enter gender to filter by (Male, Female): ");
        String gender = scanner.nextLine().trim();

        if (!(gender.equalsIgnoreCase("Male") || gender.equalsIgnoreCase("Female"))) {
            Message.displayGeneralErrorMsg("Invalid gender. Please enter 'Male' or 'Female'.");
            return;
        }

        boolean found = false;
        System.out.println("Volunteers with gender " + gender + ":");
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getGender().equalsIgnoreCase(gender)) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }

        if (!found) {
            Message.displayDataNotFoundMessage("No volunteers found with the specified gender.");
        }
    }

    private static void filterByAvailability() {
        System.out.print("Enter availability to filter by (Weekdays, Weekends): ");
        String availability = scanner.nextLine().trim();

        if (!(availability.equalsIgnoreCase("Weekdays") || availability.equalsIgnoreCase("Weekends"))) {
            Message.displayGeneralErrorMsg("Invalid availability. Please enter 'Weekdays' or 'Weekends'.");
            return;
        }

        boolean found = false;
        System.out.println("Volunteers available on " + availability + ":");

        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);

            if (volunteer.getAvailability().equalsIgnoreCase(availability)) {
                System.out.println(volunteer.getId() + " " + volunteer.getName());
                found = true;
            }
        }

        if (!found) {
            Message.displayDataNotFoundMessage("No volunteers found with the specified availability.");
        }
    }

    
        
    
}
