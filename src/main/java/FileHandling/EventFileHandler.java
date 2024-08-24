package FileHandling;

import Libraries.ArrayList;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//public class EventFileHandler implements FileHandlingInterface<DonationEvent>{
//     @Override
//     public void checkAndCreateFile(String filename) {
//         File file = new File(filename);
//         try {
//             if (!file.exists()) {
//                 file.createNewFile();
//                 System.out.println("File created: " + filename);
//             } else {
//                 //System.out.println("File exists: " + filename);
//                 System.out.println("System Ready");
                
//             }
//         } catch (IOException e) {
//             System.err.println("Error creating the file: " + e.getMessage());
//         }
//     }

//    @Override
//    public void saveData(String filename, DonationEvent entity) {
//
//    }
//
//    @Override
//    public ArrayList<DonationEvent> readData(String filename) {
//        return null;
//    }
//
//    @Override
//    public void updateData(String filename, DonationEvent entity) {
//
//    }
//
//    @Override
//    public void updateMultipleData(String filename, ArrayList<DonationEvent> entity) {
//
//    }

//    @Override
//    public void deleteData(String filename, enitity){
//
//    }
//}
