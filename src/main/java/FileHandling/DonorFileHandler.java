/*
 *  Author: Sim Hor Kang
 *  ID: 2307611
 *
 * */

package FileHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Libraries.Color;
import Entity.Donor;
import Libraries.ArrayList;
import Libraries.ListInterface;

public class DonorFileHandler implements FileHandlingInterface<Donor>{
    
        @Override
        public void checkAndCreateFile(String filename) {
            File file = new File(filename);
            try {
                if (!file.exists()) {
                    file.createNewFile();
                    System.out.println("File created: " + filename);
                } else {
                    //System.out.println("File exists: " + filename);
                    System.out.println(Color.BRIGHT_CYAN + "System Ready" + Color.RESET);

                }
            } catch (IOException e) {
                System.err.println("Error creating the file: " + e.getMessage());
            }
        }

        // Write data to Donor.txt file
        @Override
        public void saveData(String filename, Donor donor) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
                writer.write(donor.toString() + "\n");
                System.out.println(Color.GREEN + "Donor details saved successfully." + Color.RESET);
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }

        // Read Donor record in the Donor.txt file
        @Override
        public ListInterface<Donor> readData(String filename) {
            ListInterface<Donor> donors = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    Donor donor = new Donor(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5]);
    //                donor.setId(parts[0]);
    //                donor.setName(parts[1]);
    //                donor.setEmail(parts[2]);
    //                donor.setPhone(parts[3]);
    //                donor.setCategory(parts[4]);
    //                donor.setType(parts[5]);
                    donors.add(donor);
                }
            } catch (IOException e) {
                System.err.println("Error reading file: " + e.getMessage());
            }
            return donors;
        }

        // Update DonorSystem.Donor record in the DonorSystem.Donor.txt file
        @Override
        public void updateData(String filename, Donor selectedDonor) {
            ListInterface<Donor> donors = readData(filename);

            for (int i = 0; i < donors.size(); i++) {
                Donor donor = donors.get(i);

                if (donor.getId().equals(selectedDonor.getId())) {
                    donor.setName(selectedDonor.getName());
                    donor.setEmail(selectedDonor.getEmail());
                    donor.setPhone(selectedDonor.getPhone());
                    donor.setCategory(selectedDonor.getCategory());
                    donor.setType(selectedDonor.getType());
                    break;
                }
            }
            updateMultipleData(filename, donors);
        }

    // Rewrite all Contents into Donor.txt file after modifying Donor array list
        @Override
        public void updateMultipleData(String filename, ListInterface<Donor> donors) {
           try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
               for (int i = 0; i < donors.size(); i++) {
                   Donor donor = donors.get(i);

                   writer.write(donor.toString() + "\n");
               }
            } catch (IOException e) {
                System.err.println("Error writing to file: " + e.getMessage());
            }
        }

        // Update all Contents from Donor.txt file after deleting selected Donor from array list
        @Override
        public void deleteData(String filename, String donorID){
            ListInterface<Donor> donors = readData(filename);

            // Find the donor with the given ID and remove it
            for (int i = 0; i < donors.size(); i++) {
                 if (donors.get(i).getId().equals(donorID)) {
                    donors.remove(i);
                    System.out.println(Color.YELLOW + "Donor with ID " + donorID + " has been removed." + Color.RESET);
                    break;  // Exit the loop after removing the donor
                }
            }
             // Rewrite the updated list back to the file
            updateMultipleData(filename, donors);
        }

        public String getLastDonorId(String fileName) {
            String defaultId = "DNR00000";  // Default value if the file is empty or doesn't exist
            boolean isFileEmpty = true;  // Flag to check if the file is empty

            try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    isFileEmpty = false;  // If we read at least one line, the file is not empty
                    String[] donorData = line.split(","); //make a list where the item are split based on the ","
                    if (donorData.length > 0) {
                        defaultId = donorData[0];
                    }
                }
            } catch (IOException e) {
                System.err.println("Error reading the file: " + e.getMessage());
            }

            // If the file was empty, return a starting ID instead of the default ID
            if (isFileEmpty) {
                return "DNR00000";  // Start the ID sequence from DN00000
            }

            return defaultId;
        }

        public String incrementDonorId(String lastId) {
            int number = Integer.parseInt(lastId.substring(3)); // Extract the numeric part of the ID
            number++;  // Increment the number
            return String.format("DNR%05d", number); // Format the new ID with leading zeros
        }

}
