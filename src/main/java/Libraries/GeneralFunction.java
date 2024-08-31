package Libraries;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

import java.util.Scanner;

public class GeneralFunction {
    public static void enterToContinue(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter to continue");
        sc.nextLine();
    }

    public static void repeatPrint(String string, int times){
        for (int i = 0; i < times; i++) {
            System.out.print(string);
        }
    }

    public static void printTitle(String title, int width, String topBottomBorder, String sideBorder){
        System.out.print("|");
        GeneralFunction.repeatPrint("-", width - 2);
        System.out.println("|");

        int sideTextPadding = (width - 2 - title.length()) / 2;

        System.out.print("|");
        repeatPrint(" ", sideTextPadding);
        System.out.print(Color.BLUE + title + Color.RESET);
        repeatPrint(" ", (width - 2 - title.length()) % 2 == 0 ? sideTextPadding : sideTextPadding + 1);
        System.out.println("|");

        System.out.print("|");
        GeneralFunction.repeatPrint("-", width - 2);
        System.out.println("|");
    }

    public static void clearScreen(){
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
