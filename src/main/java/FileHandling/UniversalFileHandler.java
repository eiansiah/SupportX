package FileHandling;

import Libraries.ArrayList;

import java.io.*;

public class UniversalFileHandler {

    //source: https://www.baeldung.com/java-append-to-file
    /**
     * Appends a single line of data to the specified file.
     * @param _filePath the path to the file
     * @param dataObjectString the string data to append
     */
    public static void appendData(String _filePath, String dataObjectString) {
        try{
            FileWriter fw = new FileWriter(_filePath, true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(dataObjectString);

            bw.newLine();
            bw.close();
        }catch(IOException e){
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    /**
     * Appends multiple lines of data to the specified file.
     * @param _filePath the path to the file
     * @param dataObjectStrings a list of strings to append
     */
    public static void appendData(String _filePath, ArrayList<String> dataObjectStrings) {
        for(String dataString: dataObjectStrings){
            appendData(_filePath, dataString);
        }
    }

    //source: https://www.digitalocean.com/community/tutorials/java-read-file-line-by-line
    /**
     * Removes all occurrences of a specified line of data from the file.
     * @param _filePath the path to the file
     * @param dataObjectString the string data to remove
     */
    public static void removeData(String _filePath, String dataObjectString) {
        BufferedReader reader;
        String tempPath = _filePath + "_temp";

        try{
            reader = new BufferedReader(new FileReader(_filePath));
            String line = reader.readLine();

            FileWriter fw = new FileWriter(tempPath, true);
            BufferedWriter bw = new BufferedWriter(fw);

            while (line != null) {
                if(!line.equals(dataObjectString)){
                    bw.write(line);
                    bw.newLine();
                }

                // read next line
                line = reader.readLine();
            }

            bw.close();
            reader.close();

            File file = new File(tempPath);
            File rename = new File(_filePath);

            rename.delete();

            boolean flag = file.renameTo(rename);

            System.out.println(flag ? "File Successfully Rename" : "Operation Failed");

            file.delete();
        }catch(IOException e){
            System.err.println("Error remove data from file: " + e.getMessage());
        }
    }

    /**
     * Removes all occurrences of multiple lines of data from the file.
     * @param _filePath the path to the file
     * @param dataObjectStrings a list of strings to remove
     */
    public static void removeData(String _filePath, ArrayList<String> dataObjectStrings) {
        BufferedReader reader;
        String tempPath = _filePath + "_temp";

        try{
            reader = new BufferedReader(new FileReader(_filePath));
            String line = reader.readLine();

            FileWriter fw = new FileWriter(tempPath, true);
            BufferedWriter bw = new BufferedWriter(fw);

            while (line != null) {
                if(!dataObjectStrings.contains(line)){
                    bw.write(line);
                    bw.newLine();
                }

                // read next line
                line = reader.readLine();
            }

            bw.close();
            reader.close();

            File file = new File(tempPath);
            File rename = new File(_filePath);

            rename.delete();

            boolean flag = file.renameTo(rename);

            System.out.println(flag ? "File Successfully Rename" : "Operation Failed");

            file.delete();
        }catch(IOException e){
            System.err.println("Error remove data from file: " + e.getMessage());
        }
    }

    /**
     * Modifies a specific line of data in the file by first removing the old line and then appending the new line.
     * @param _filePath the path to the file
     * @param oldDataObjectStrings the string data to be replaced
     * @param newDataObjectStrings the new string data to append
     */
    public static void modifyData(String _filePath, String oldDataObjectStrings, String newDataObjectStrings) {
        removeData(_filePath, oldDataObjectStrings);
        appendData(_filePath, newDataObjectStrings);
    }

    /**
     * Reads all lines of data from the specified file.
     * @param _filePath the path to the file
     * @return a list of strings representing the lines of data in the file
     */
    public static ArrayList<String> readData(String _filePath) {
        BufferedReader reader;
        ArrayList<String> tempData = new ArrayList<>();

        try{
            reader = new BufferedReader(new FileReader(_filePath));
            String line = reader.readLine();

            while (line != null) {
                tempData.add(line);

                // read next line
                line = reader.readLine();
            }

            reader.close();

            return tempData;
        }catch(IOException e){
            System.err.println("Error remove data from file: " + e.getMessage());
        }

        return null;
    }
}