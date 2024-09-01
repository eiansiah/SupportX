package Utilities;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

import DAO.UniversalFileHandler;
import ADT.ArrayList;

public class Search {

    /**
     * Searches for the first line in a file that contains the specified text.
     * @param _filePath path to the file to be searched
     * @param textToSearch the text to search for in the file
     * @return the first line containing the specified text, or null if no match is found or if the file could not be read
     */
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

    /**
     * Searches for the first line in a file that contains the specified text in a specified field.
     * @param _filePath path to the file to be searched
     * @param textToSearch the text to search for in the file
     * @param separator the delimiter used to split each line into fields
     * @param index the index of the field to search within
     * @return the first line containing the specified text in the specified field, or null if no match is found, the index is out of range, or if the file could not be read
     */
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

    /**
     * Searches for all lines in a file that contain the specified text.
     * @param _filePath path to the file to be searched
     * @param textToSearch the text to search for in the file
     * @return a list of all lines containing the specified text, or an empty list if no matches are found or if the file could not be read
     */
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

    /**
     * Searches for all lines in a file that contain the specified text in a specified field.
     * @param _filePath path to the file to be searched
     * @param textToSearch the text to search for in the file
     * @param separator the delimiter used to split each line into fields
     * @param index the index of the field to search within
     * @return a list of all lines containing the specified text in the specified field, or an empty list if no matches are found, the index is out of range, or if the file could not be read
     */
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