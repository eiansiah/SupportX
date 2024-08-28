package Libraries;

import java.util.Scanner;

public class GeneralFunction {
    public static void enterToContinue(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter to continue");
        sc.nextLine();
    }

    public static void repeatPrint(String string, int times){
        for (int i = 0; i < times; i++) {
            System.out.print(string);
        }
    }
}
