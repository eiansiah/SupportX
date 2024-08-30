package Libraries;

import java.util.Comparator;

public interface ListInterface<T>{
    void add(T element);
    int size();
    T get(int index);
    T set(int index, T element);
    boolean remove(T element);
    boolean remove(int index);
    boolean contains(T element);
    void clear();
    //void ensureCapacity();
    void display();
    int indexOf(T element);
    int lastIndexOf(T element);
    boolean isEmpty();
    void sort(Comparator<? super T> comparator);


    //TODO: sorting, removeall?
}
