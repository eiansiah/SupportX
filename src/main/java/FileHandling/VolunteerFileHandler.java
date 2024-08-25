package FileHandling;

import Libraries.ArrayList;
import Main.Volunteer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class VolunteerFileHandler implements FileHandlingInterface<Volunteer> {

    @Override
    public void checkAndCreateFile(String filename) {
        File file = new File(filename);
        try {
            if (!file.exists()) {
                file.createNewFile();
                System.out.println("File created: " + filename);
            } else {
                System.out.println("System Ready");
            }
        } catch (IOException e) {
            System.err.println("Error creating the file: " + e.getMessage());
        }
    }

    @Override
    public void saveData(String filename, Volunteer volunteer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(volunteer.toString() + "\n");
            System.out.println("Volunteer details saved successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Volunteer> readData(String filename) {
        ArrayList<Volunteer> volunteers = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    Volunteer volunteer = new Volunteer();
                    volunteer.setId(parts[0]);
                    volunteer.setName(parts[1]);
                    volunteer.setAge(parts[2]);
                    volunteer.setGender(parts[3]);
                    volunteer.setPhone(parts[4]);
                    volunteer.setEmail(parts[5]);
                    volunteer.setAvailability(parts[6]);
                    volunteers.add(volunteer);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
        return volunteers;
    }

    @Override
    public void updateData(String filename, Volunteer updatedVolunteer) {
        ArrayList<Volunteer> volunteers = readData(filename);

        for (Volunteer volunteer : volunteers) {
            if (volunteer.getId().equals(updatedVolunteer.getId())) {
                volunteer.setName(updatedVolunteer.getName());
                volunteer.setAge(updatedVolunteer.getAge());
                volunteer.setGender(updatedVolunteer.getGender());
                volunteer.setPhone(updatedVolunteer.getPhone());
                volunteer.setEmail(updatedVolunteer.getEmail());
                volunteer.setAvailability(updatedVolunteer.getAvailability());
                break;
            }
        }
        updateMultipleData(filename, volunteers);
    }

    @Override
    public void updateMultipleData(String filename, ArrayList<Volunteer> volunteers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Volunteer volunteer : volunteers) {
                writer.write(volunteer.toString() + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    public String getLastVolunteerId(String fileName) {
        String defaultId = "VL00000";  // Default value if the file is empty or doesn't exist
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] volunteerData = line.split(",");
                defaultId = volunteerData[0];
            }
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return defaultId;
    }

    public String incrementVolunteerId(String lastId) {
        int number = Integer.parseInt(lastId.substring(2)); // Extract the numeric part of the ID
        number++;  // Increment the number
        return String.format("VL%05d", number); // Format the new ID with leading zeros
    }

    @Override
    public void deleteData(String filename, String volunteerId) {
        ArrayList<Volunteer> volunteers = readData(filename);
        ArrayList<Volunteer> updatedVolunteers = new ArrayList<>();

        for (Volunteer volunteer : volunteers) {
            if (!volunteer.getId().equals(volunteerId)) {
                updatedVolunteers.add(volunteer);
            }
        }

        updateMultipleData(filename, updatedVolunteers);
    }
}
