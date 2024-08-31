package FileHandling;

//import Libraries.ArrayList;
import Libraries.ListInterface;

public interface FileHandlingInterface<T> {
    
    public void checkAndCreateFile(String filename);
    void saveData(String filename, T entity);
    ListInterface<T> readData(String filename);
    void updateData(String filename, T entity);
    void updateMultipleData(String filename, ListInterface<T> entity);
    void deleteData(String filename, String entity);

}
