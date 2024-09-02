package ADT;

/*
 *  author: Saw Khoo Zi Chong + Ko Jie Qi
 *  ID: 2307609 + 2307589
 * */

import java.util.Comparator;
import java.util.Iterator;

public interface ListInterface<T>{
    void add(T element);
    void add(int index, T element);
    int size();
    T get(int index);
    T set(int index, T element);
    boolean remove(T element);
    boolean remove(int index);
    boolean contains(T element);
    void clear();
    int indexOf(T element);
    int lastIndexOf(T element);
    boolean isEmpty();
    void sort(Comparator<? super T> comparator);
    Iterator<T> iterator();
}
