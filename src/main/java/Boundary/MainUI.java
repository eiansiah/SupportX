package Boundary;

import Utilities.Color;
import Utilities.GeneralFunction;

import java.util.Random;
import java.util.Scanner;

public class MainUI {

    public static void printLogo(){
        System.out.println("" +
                "   _____                              _  __   __\n" +
                "  / ____|                            | | \\ \\ / /\n" +
                " | (___  _   _ _ __  _ __   ___  _ __| |_ \\ V / \n" +
                "  \\___ \\| | | | '_ \\| '_ \\ / _ \\| '__| __| > <  \n" +
                "  ____) | |_| | |_) | |_) | (_) | |  | |_ / . \\ \n" +
                " |_____/ \\__,_| .__/| .__/ \\___/|_|   \\__/_/ \\_\\\n" +
                "              | |   | |                         \n" +
                "              |_|   |_|                         ");
    }


    //Art source: https://www.messletters.com/en/text-art/
    // https://emojicombos.com/cat
    public static void randomPicture() {
        int random = new Random().nextInt(2);

        if (random == 0) {
            System.out.println("" +
                    "   ^,,,^\n" +
                    "(  _' . ' _)\n" +
                    "/    > Thank you");
        } else if (random == 1) {
           System.out.println("" +
                   "  /\\,,/\\\n" +
                   " (' - ' )\n" +
                   "|-U U---------------|\n" +
                   "|     " + Color.BLUE + "Thank You" + Color.RESET + "     |   \n" +
                   "|___________________|");
        }

    }

    public static void printMainMenu(){
        GeneralFunction.printTitle("Main Menu", 51, "-", "|");
        System.out.println("| 1. Donor Management                             |");
        System.out.println("| 2. Donee Management                             |");
        System.out.println("| 3. Donation Management                          |");
        System.out.println("| 4. Volunteer Management                         |");
        System.out.println("| 5. Event Management                             |");
        System.out.println("| 6. Exit Program                                 |");
        System.out.println("|" + Color.YELLOW + " x. Donation Distribution (In construction)      " + Color.RESET + "|");

        System.out.print("|");
        GeneralFunction.repeatPrint("-", 49);
        System.out.println("|");
    }

    public static String getInput(String msg){
        Scanner sc = new Scanner(System.in);

        System.out.print(msg);

        return sc.nextLine().trim();
    }
}
