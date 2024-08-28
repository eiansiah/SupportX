package Control;

import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import Entity.Donor;

public class DonorFilter {


    // Filter by starting alphabet of the name
    public static ArrayList<Donor> filterByName(ArrayList<Donor> objects, char startingLetter) {
        ArrayList<Donor> filteredList = new ArrayList<>();
        for (Donor obj : objects) {
            if (obj.getName().toUpperCase().charAt(0) == Character.toUpperCase(startingLetter)) {
                filteredList.add(obj);
            }
        }
        return filteredList;
    }

    // Filter by category
    public static ArrayList<Donor> filterByCategory(ArrayList<Donor> objects, String category) {
        ArrayList<Donor> filteredList = new ArrayList<>();
        for (Donor obj : objects) {
            if (obj.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(obj);
            }
        }
        return filteredList;
    }

    // Filter by type
    public static ArrayList<Donor> filterByType(ArrayList<Donor> objects, String type) {
        ArrayList<Donor> filteredList = new ArrayList<>();
        for (Donor obj : objects) {
            if (obj.getType().equalsIgnoreCase(type)) {
                filteredList.add(obj);
            }
        }
        return filteredList;
    }

    // Reset Filter
    public static ArrayList<Donor> resetFilter(ArrayList<Donor> objects) {
        DonorFileHandler fileHandler = new DonorFileHandler();

        return fileHandler.readData("donor.txt");
    }

}
