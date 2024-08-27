package Boundary;

import Libraries.Color;

import java.util.Scanner;

import Main.Event.EventHandler;
import Utilities.Message;

import static Utilities.Message.displayGeneralErrorMsg;

public class EventUI {
    public static void main(String[] args) {
        subsystemMenu();
    }

    private static void subsystemMenu(){
        Scanner sc = new Scanner(System.in);

        while(true){
            subsystemMenuDisplay();

            int action = 0;
            while (true){
                System.out.print("Enter action: ");

                String _action = sc.nextLine().trim();

                if(_action.isEmpty()){
                    displayGeneralErrorMsg("Input can't be empty");
                    continue;
                }

                try{
                    action = Integer.parseInt(_action);
                }catch (NumberFormatException e){
                    displayGeneralErrorMsg("Input is not a number");
                    continue;
                }
                
                if(action < 1 || action > 8){
                    displayGeneralErrorMsg("Invalid action");
                    continue;
                }

                break;
            }

            switch (action) {
                case 1:
                    addNewEventUI();
                    break;
                case 2:
                    removeEventUI();
                    break;
                case 3:
                    searchEventUI();
                    break;
                case 4:
                    amendEventDetailsUI();
                    break;
                case 5:
                    listAllEventUI();
                    break;
                case 6:
                    removeEventFromVolunteer();
                    break;
                case 7:
                    listAllEventForVolunteerUI();
                    break;
                case 8:
                    generateReportsUI();
                    break;
            }
        }
    }

    private static void addNewEventUI(){

    }

    private static void removeEventUI(){

    }

    private static void searchEventUI(){

    }

    private static void amendEventDetailsUI(){

    }

    private static void listAllEventUI(){

    }

    private static void removeEventFromVolunteer(){

    }

    private static void listAllEventForVolunteerUI(){

    }

    private static void generateReportsUI(){

    }

    private static void subsystemMenuDisplay(){
        System.out.println(Color.BLUE + "Event Menu" + Color.RESET);
        System.out.println("----------------------------");
        System.out.println("1. Add a new event");
        System.out.println("2. Remove an event");
        System.out.println("3. Search an event");
        System.out.println("4. Amend an event details");
        System.out.println("5. List all events");
        System.out.println("6. Remove an event from a volunteer");
        System.out.println("7. List all events for a volunteer");
        System.out.println("8. Generate summary reports");
    }
}
