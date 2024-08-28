package Utilities;

import Libraries.Color;

public class Message {

    public static void displayGeneralErrorMsg(String errorMsg) {
        System.out.println(Color.RED + " " + errorMsg + " " + Color.RESET);
    }

    public static void displayGeneralMessage(String msg) {
        System.out.print("\n" + Color.BRIGHT_YELLOW + msg + Color.RESET);
    }

    public static void displayInvalidChoiceMessage(String reason) {
        System.out.println(Color.RED + "\nInvalid choice. " + reason + Color.RESET);
    }

    public static void displayInvalidInputMessage(String reason) {
        System.out.println(Color.RED + "\nInvalid input. " + reason + Color.RESET);
    }

    public static void displayDataNotFoundMessage(String reason) {
        System.out.println(Color.BRIGHT_YELLOW + "\nData not found. " + reason + Color.RESET);
    }

    public static void displayEmptyInputMessage(String reason) {
        System.out.println(Color.BRIGHT_YELLOW + "\nEmpty input detected. " + reason + Color.RESET);
    }

    public static void displayInvalidChoiceMessage() {
        System.out.println(Color.RED + "\nInvalid choice. " + Color.RESET);
    }

    public static void displayInvalidInputMessage() {
        System.out.println(Color.RED + "\nInvalid Input. " + Color.RESET);
    }

    public static void displayDataNotFoundMessage() {
        System.out.println(Color.BRIGHT_YELLOW + "\nData not found. " + Color.RESET);
    }

    public static void displayEndDeletingMessage() {
        System.out.println(Color.BRIGHT_YELLOW + "\nExiting delete. " + Color.RESET);
    }

    public static void displayEndUpdateMessage() {
        System.out.println(Color.BRIGHT_YELLOW + "\nExiting modification. " + Color.RESET);
    }

    public static void displayExitMessage() {
        System.out.println(Color.BRIGHT_YELLOW + "\nExiting system. " + Color.RESET);
    }

    public static void displayExitMessage(String reason) {
        System.out.print(Color.BRIGHT_YELLOW + "\nExiting system. " + reason + Color.RESET);
    }

    public static void displayInvalidFormatMessage(String item){
        System.out.println(Color.RED + "\nInvalid " + item + " format entered. Please try again." + Color.RESET);
    }

    public static void displayRemoveCancelMessage(){
        System.out.println(Color.YELLOW + "Deletion cancelled." + Color.RESET);
    }

    public static void displayUpdateCancelMessage(){
        System.out.println(Color.YELLOW + "Updating cancelled." + Color.RESET);
    }

    public static void displayFilterCancelMessage(){
        System.out.println(Color.YELLOW + "Filtering cancelled." + Color.RESET);
    }

}
