/*
 *  Author: Sim Hor Kang
 *  ID: 2307611
 *
 * */

package Control;

import FileHandling.DonorFileHandler;
import Libraries.ArrayList;
import Entity.Donor;
import Libraries.ListInterface;

public class DonorFilter {


    // Filter by starting alphabet of the name
    public static ListInterface<Donor> filterByName(ListInterface<Donor> objects, char startingLetter) {
        ArrayList<Donor> filteredList = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Donor obj = filteredList.get(i);

            if (obj.getName().toUpperCase().charAt(0) == Character.toUpperCase(startingLetter)) {
                filteredList.add(obj);
            }
        }

        return filteredList;
    }

    // Filter by category
    public static ListInterface<Donor> filterByCategory(ListInterface<Donor> objects, String category) {
        ArrayList<Donor> filteredList = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            Donor obj = filteredList.get(i);

            if (obj.getCategory().equalsIgnoreCase(category)) {
                filteredList.add(obj);
            }
        }

        return filteredList;
    }

    // Filter by type
    public static ListInterface<Donor> filterByType(ListInterface<Donor> objects, String type) {
        ArrayList<Donor> filteredList = new ArrayList<>();
         for (int i = 0; i < objects.size(); i++) {
            Donor obj = filteredList.get(i);

            if (obj.getType().equalsIgnoreCase(type)) {
                filteredList.add(obj);
            }
        }

        return filteredList;
    }

    // Reset Filter
    public static ListInterface<Donor> resetFilter(ListInterface<Donor> objects) {
        DonorFileHandler fileHandler = new DonorFileHandler();

        return fileHandler.readData("donor.txt");
    }

}
