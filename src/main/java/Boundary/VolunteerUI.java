package Boundary;

import Libraries.Color;
import java.util.Scanner;

public class VolunteerUI {

    private static final Scanner scanner = new Scanner(System.in);

    public static void mainMenu() {
        System.out.println(Color.BLUE + "\nVolunteer Management System" + Color.RESET);
        System.out.println("----------------------------");
        System.out.println("1. Add Volunteer");
        System.out.println("2. Delete Volunteer");
        System.out.println("3. Modify Volunteer");
        System.out.println("4. Search Volunteer");
        System.out.println("5. Assign Volunteer");
        System.out.println("6. List All Volunteers");
        System.out.println("7. List of Volunteers and Events");
        System.out.println("8. Filter Volunteers");
        System.out.println("9. Search Events by Volunteer");
        System.out.println("10. View Summary Reports");
        System.out.println("11. Back");
        System.out.println(" ");
        System.out.print("Enter choice: ");

        String input = scanner.nextLine().trim();

    }

    private static void assignVolunteer() {
        
        System.out.print("Enter event ID: ");
    }
    
    
}
