package FileHandling;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Main.Donor;
import Libraries.ArrayList;

public class DonorFileHandler implements FileHandlingInterface<Donor>{
    
    // Write data to DonorSystem.Donor.txt file
    @Override
    public void saveData(String filename, Donor donor) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(donor.toString() + "\n");
            System.out.println("DonorSystem.Donor details saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
    // Read DonorSystem.Donor record in the DonorSystem.Donor.txt file
    @Override
    public ArrayList<Donor> readData(String filename) {
        ArrayList<Donor> donors = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                Donor donor = new Donor();
                donor.setId(parts[0]);
                donor.setName(parts[1]);
                donor.setEmail(parts[2]);
                donor.setPhone(parts[3]);
                donor.setCategory(parts[4]);
                donor.setType(parts[5]);
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
        ArrayList<Donor> donors = readData(filename);

        for (Donor donor : donors) {
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
    
    // Rewrite all Contents into DonorSystem.Donor.txt file after modifying DonorSystem.Donor array list
    @Override
    public void updateMultipleData(String filename, ArrayList<Donor> donors) {
       try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Donor donor : donors) {
                writer.write(donor.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
    
}
