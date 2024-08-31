/*
 *  Author: Sim Hor Kang
 *  ID: 2307611
 *
 *  Co-author: Siah E-ian
 *  ID: 2307610
 *
 *  Co-author: Saw Khoo Zi Chong
 *  ID: 2307611
 *
 *  Co-author: Tan Qian Yiin
 *  ID: 2307616
 *
 *  Co-author: Ko Jie Qi
 *  ID: 230
 *
 * */


package Control;

import Boundary.DonorUI;
import Boundary.MainUI;
import Libraries.GeneralFunction;
import Main.Event.EventHandler;
import Control.DonorFunctions;
import static Utilities.Message.*;

public class Main {
    public static void main(String[] args) {

        //TODO: ADT hashmap with multiple key
        //TODO: Multi algorithm to store data. example: <List> list of donee --> filter with condition --> <Queue> Delivery flow, near to far to near (circle)
        GeneralFunction.clearScreen();
        //MainUI.randomPicture();
        MainUI.printLogo();

        EventHandler.updateEventStatus();

        while (true){
            MainUI.printMainMenu();

            int action = 0;

            while (true){
                try{
                    action = Integer.parseInt(MainUI.getInput("Enter action: "));
                } catch (NumberFormatException e) {
                    displayGeneralErrorMsg("Invalid input. Please try again.");
                    continue;
                }

                if (action < 1 || action > 6){
                    displayGeneralErrorMsg("Invalid input. Please try again.");
                }else{
                    break;
                }
            }

            switch (action) {
                case 1:
                    DonorFunctions.runDonorSystem();
                    break;
                case 2:
                    DoneeFunctions.doneeHandler();
                    break;
                case 3:

                    break;
                case 4:

                    break;
                case 5:
                    EventHandler.subsystemMenu();
                    break;
                case 6:
                    GeneralFunction.clearScreen();

                    MainUI.randomPicture();

                    return;
            }

            GeneralFunction.clearScreen();
        }
    }
}