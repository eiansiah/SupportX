package FileHandling;

/*
 *  author: Sim Hor Kang
 *  ID: 2307611
 * */

import Libraries.ListInterface;

public interface FileHandlingInterface<T> {
    
    public void checkAndCreateFile(String filename);
    void saveData(String filename, T entity);
    ListInterface<T> readData(String filename);
    void updateData(String filename, T entity);
    void updateMultipleData(String filename, ListInterface<T> entity);
    void deleteData(String filename, String entity);

}
