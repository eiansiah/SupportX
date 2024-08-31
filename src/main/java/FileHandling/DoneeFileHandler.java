package FileHandling;

import Libraries.ArrayList;
import Libraries.Color;
import Libraries.ListInterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;

import Entity.Donee;

public class DoneeFileHandler implements FileHandlingInterface<Donee> {
     @Override
     public void checkAndCreateFile(String filename) {
         File file = new File(filename);
         try {
             if (!file.exists()) {
                 file.createNewFile();
                 System.out.println(Color.BRIGHT_GREEN + "File created: " + filename + Color.RESET);
             } else {
                 System.out.println(Color.BRIGHT_GREEN + "System Ready!" + Color.RESET);
             }
         } catch (IOException e) {
             System.err.println(Color.RED + "Error creating the file: " + e.getMessage() + Color.RESET);
         }
     }

    @Override
    public void saveData(String filename, Donee donee) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(donee.toString() + "\n");
            System.out.println(Color.BRIGHT_GREEN + "\nNew donee added successfully!" + Color.RESET);
        } catch (IOException e) {
            System.err.println(Color.RED + "Error writing to file: " + e.getMessage() + Color.RESET);
        }
    }

    @Override
    public ListInterface<Donee> readData(String filename) {
        ListInterface<Donee> donees = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                LocalDate registeredDate = LocalDate.parse(parts[8], formatter);
                Donee donee = new Donee(parts[0],parts[1],parts[2],parts[3],parts[4],parts[5],parts[6],parts[7],registeredDate);
                donees.add(donee);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return donees;
    }

    @Override
    public void updateData(String filename, Donee selectedDonee) {
        ListInterface<Donee> donees = readData(filename);

        Iterator<Donee> doneeIterator = donees.iterator();

        while (doneeIterator.hasNext()) {
            Donee donee = doneeIterator.next();

            if (donee.getDoneeID().equals(selectedDonee.getDoneeID())) {
                // Update the donee details
                donee.setName(selectedDonee.getName());
                donee.setEmail(selectedDonee.getEmail());
                donee.setPhone(selectedDonee.getPhone());
                donee.setAddress(selectedDonee.getAddress());
                donee.setDoneeType(selectedDonee.getDoneeType());
                donee.setItemCategoryRequired(selectedDonee.getItemCategoryRequired());
                donee.setDoneeUrgency(selectedDonee.getDoneeUrgency());
                break; // Exit the loop once the donee is found and updated
            }
        }
        updateMultipleData(filename, donees);
    }

    @Override
    public void updateMultipleData(String filename, ListInterface<Donee> donees) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            Iterator<Donee> doneeIterator = donees.iterator();
            while (doneeIterator.hasNext()) {
                Donee donee = doneeIterator.next();
                writer.write(donee.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public void deleteData(String filename, String doneeID){
        ListInterface<Donee> donees = readData(filename);

        // Find the donor with the given ID and remove it
        for (int i = 0; i < donees.size(); i++) {
            if (donees.get(i).getDoneeID().equals(doneeID)) {
                donees.remove(i);
                System.out.println("**Donee with ID " + doneeID + " has been removed successfully**");
                break;  // Exit the loop after removing the donor
            }
        }
        // Rewrite the updated list back to the file
        updateMultipleData(filename, donees);
    }

    public String getLastDoneeId(String fileName) {
        String defaultId = "DNE00000";  // Default value if the file is empty or doesn't exist
        boolean isFileEmpty = true;  // Flag to check if the file is empty

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                isFileEmpty = false;  // If we read at least one line, the file is not empty
                String[] doneeData = line.split(","); //make a list where the item are split based on the ","
                if (doneeData.length > 0) {
                    defaultId = doneeData[0];
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }

        // If the file was empty, return a starting ID instead of the default ID
        if (isFileEmpty) {
            return "DNE00000";  // Start the ID sequence from DN00000
        }

        return defaultId;
    }

    public String incrementDoneeId(String lastId) {
        int number = Integer.parseInt(lastId.substring(3)); // Extract the numeric part of the ID
        number++;  // Increment the number
        return String.format("DNE%05d", number); // Format the new ID with leading zeros
    }
}
