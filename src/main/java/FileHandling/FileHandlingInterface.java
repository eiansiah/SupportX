package FileHandling;

import Libraries.ArrayList;

public interface FileHandlingInterface<T> {
    
    void saveData(String filename, T entity);
    ArrayList<T> readData(String filename);
    void updateData(String filename, T entity);
    void updateMultipleData(String filename, ArrayList<T> entity);

}
