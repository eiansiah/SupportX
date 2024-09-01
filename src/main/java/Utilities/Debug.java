package Utilities;

public class Debug {

    private static final boolean debug = false;

    private static final String textColor = Color.YELLOW;

    public static void printDebugMsgln(String msg) {
        if(debug){
            System.out.println(textColor + msg + Color.RESET);
        }
    }

    public static void printDebugMsg(String msg) {
        if(debug){
            System.out.print(textColor + msg + Color.RESET);
        }
    }
}
