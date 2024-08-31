package FileHandling;

import Libraries.ArrayList;
import Libraries.ListInterface;
import Libraries.Color;
import Entity.Volunteer;

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
                System.out.println(Color.GREEN + "File created: " + filename + Color.RESET);
            } else {
                System.out.println(Color.GREEN + "System Ready" + Color.RESET);
            }
        } catch (IOException e) {
            System.err.println(Color.RED + "Error creating the file: " + e.getMessage() + Color.RESET);
        }
    }

    @Override
    public void saveData(String filename, Volunteer volunteer) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(volunteer.toString() + "\n");
            System.out.println(Color.GREEN + "Volunteer details saved successfully." + Color.RESET);
        } catch (IOException e) {
            System.err.println(Color.RED + "Error writing to file: " + e.getMessage() + Color.RESET);
        }
    }

    @Override
    public ListInterface<Volunteer> readData(String filename) {
        ListInterface<Volunteer> volunteers = new ArrayList<>();
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
            if (volunteers.size() == 0) {
                System.out.println(Color.YELLOW + "No volunteers found in the file." + Color.RESET);
            }
        } catch (IOException e) {
            System.err.println(Color.RED + "Error reading file: " + e.getMessage() + Color.RESET);
        }
        return volunteers;
    }

    @Override
    public void updateData(String filename, Volunteer updatedVolunteer) {
        ListInterface<Volunteer> volunteers = readData(filename);

        boolean updated = false;
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);
            if (volunteer.getId().equals(updatedVolunteer.getId())) {
                volunteer.setName(updatedVolunteer.getName());
                volunteer.setAge(updatedVolunteer.getAge());
                volunteer.setGender(updatedVolunteer.getGender());
                volunteer.setPhone(updatedVolunteer.getPhone());
                volunteer.setEmail(updatedVolunteer.getEmail());
                volunteer.setAvailability(updatedVolunteer.getAvailability());
                updated = true;
                break;
            }
        }
        
        if (updated) {
            updateMultipleData(filename, volunteers);
            System.out.println(Color.GREEN + "Volunteer details updated successfully." + Color.RESET);
        } else {
            System.out.println(Color.YELLOW + "Volunteer with ID " + updatedVolunteer.getId() + " not found." + Color.RESET);
        }
    }

    @Override
    public void updateMultipleData(String filename, ListInterface<Volunteer> volunteers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int i = 0; i < volunteers.size(); i++) {
                Volunteer volunteer = volunteers.get(i);
                writer.write(volunteer.toString() + "\n");
            }
            System.out.println(Color.GREEN + "All volunteer details updated successfully." + Color.RESET);
        } catch (IOException e) {
            System.err.println(Color.RED + "Error writing to file: " + e.getMessage() + Color.RESET);
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
            System.err.println(Color.RED + "Error reading the file: " + e.getMessage() + Color.RESET);
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
        ListInterface<Volunteer> volunteers = readData(filename);
        ListInterface<Volunteer> updatedVolunteers = new ArrayList<>();

        boolean deleted = false;
        for (int i = 0; i < volunteers.size(); i++) {
            Volunteer volunteer = volunteers.get(i);
            if (!volunteer.getId().equals(volunteerId)) {
                updatedVolunteers.add(volunteer);
            } else {
                deleted = true;
            }
        }

        if (deleted) {
            updateMultipleData(filename, updatedVolunteers);
            System.out.println(Color.GREEN + "Volunteer with ID " + volunteerId + " deleted successfully." + Color.RESET);
        } else {
            System.out.println(Color.YELLOW + "Volunteer with ID " + volunteerId + " not found." + Color.RESET);
        }
    }
}
