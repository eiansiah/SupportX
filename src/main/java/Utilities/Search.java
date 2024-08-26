package Utilities;

import FileHandling.UniversalFileHandler;
import Libraries.ArrayList;
import Libraries.Debug;

public class Search {

    public static String searchFirstMatchesStringFromFile(String _filePath, String textToSearch) {
        ArrayList<String> fileData = UniversalFileHandler.readData(_filePath);

        if(fileData == null) return null;

        for(String data: fileData) {
            if(data.contains(textToSearch)) {
                return data;
            }
        }

        Debug.printDebugMsgln("Search no result");

        return null;
    }

    public static String searchFirstMatchesStringFromFile(String _filePath, String textToSearch, String separator, int index) {
        ArrayList<String> fileData = UniversalFileHandler.readData(_filePath);

        if(fileData == null) return null;

        for(String data: fileData) {
            String[] dataFragment = data.split(separator);

            if(index >= dataFragment.length) {
                Debug.printDebugMsgln("Invalid index: " + index + " searchFirstMatchesStringFromFile");
                return null;
            }

            if(dataFragment[index].contains(textToSearch)) {
                return data;
            }
        }

        Debug.printDebugMsgln("Search no result");

        return null;
    }

    public static ArrayList<String> searchAllMatchesString(String _filePath, String textToSearch) {
        ArrayList<String> fileData = UniversalFileHandler.readData(_filePath);
        ArrayList<String> temp = new ArrayList<>();

        if(fileData == null) return null;

        for(String data: fileData) {
            if(data.contains(textToSearch)) {
               temp.add(data);
            }
        }

        return temp;
    }

    public static ArrayList<String> searchAllMatchesString(String _filePath, String textToSearch, String separator, int index) {
        ArrayList<String> fileData = UniversalFileHandler.readData(_filePath);
        ArrayList<String> temp = new ArrayList<>();

        if(fileData == null) return null;

        for(String data: fileData) {
            String[] dataFragment = data.split(separator);

            if(index >= dataFragment.length) {
                Debug.printDebugMsgln("Invalid index: " + index + " searchAllMatchesString");
                return null;
            }

            if(dataFragment[index].contains(textToSearch)) {
                temp.add(data);
            }
        }

        return temp;
    }

    /*public static ArrayList<String> searchAllMatchesString(String _filePath, String textToSearch, String separator, int index, boolean ascendingOrder, int sortIndex) {
        ArrayList<String> fileData = UniversalFileHandler.readData(_filePath);
        ArrayList<String> temp = new ArrayList<>();
        ArrayList<String> tempSorted = new ArrayList<>();

        if(fileData == null) return null;

        for(String data: fileData) {
            String[] dataFragment = data.split(separator);

            if(index >= dataFragment.length) {
                Debug.printDebugMsgln("Invalid index: " + index + " searchAllMatchesString");
                return null;
            }

            if(dataFragment[index].contains(textToSearch)) {
                temp.add(data);
            }
        }

        int currentIndex = 0;
        while(!temp.isEmpty()){
            String[] dataFragment = temp.get(currentIndex).split(separator);

            if(sortIndex >= dataFragment.length) {
                Debug.printDebugMsgln("Invalid sort index: " + index + " searchAllMatchesString");
                return null;
            }

            if(ascendingOrder){

            }

            currentIndex++;
        }

        return temp;
    } */
}
