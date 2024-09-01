//package Main;
//
//import Entity.Donor;
//
//import FileHandling.DonorFileHandler;
//
//import Libraries.ArrayList;
//
//import Utilities.Message;
//
//import Control.DonorFunctions;
//
//import java.util.Scanner;
//
//public class Test {
//
//    public static void main(String[] args) {
//        int option;
//        DonorFileHandler fileHandler = new DonorFileHandler();
//        fileHandler.checkAndCreateFile("donor.txt");
//
//        do {
//            Scanner scanner = new Scanner(System.in);
//
//            System.out.println("\n1 - Add Donor");
//            System.out.println("2 - Remove Donor");
//            System.out.println("3 - Update Donors");
//            System.out.println("4 - See All Donors");
//            System.out.println("5 - Terminate Session");
//            System.out.print("What would you like to do : ");
//            option = scanner.nextInt();
//
//            switch (option) {
//                //Add Donor
//                case 1:
//                    DonorFunctions.addDonor();
//                    break;
//                //Delete Donor
//                case 2:
////                    DonorFunctions.deleteDonor();
//                    break;
//
//                // Modify Donor
//                case 3:
//                    System.out.print("\nWhich Donor Would you like to update? Please enter their ID: ");
//                    String donorIDToUpdate = scanner.next().trim();
//                    // Read all donors from the file
//                    ArrayList<Donor> donorsUpdate = fileHandler.readData("donor.txt");
//                    // Modify the donor using the modifyDonor method
//                    DonorFunctions.modifyDonor(donorIDToUpdate, donorsUpdate, fileHandler);
//                    break;
//
//                //See All Donor
//                case 4:
//                    DonorFunctions.displayDonors();
//                    break;
//
//                //Terminate
//                default:
//                    Message.displayExitMessage();
//                    break;
//
//            }
//        } while (option >= 1 && option <= 4);
//
//    }
//}