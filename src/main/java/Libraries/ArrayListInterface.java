package Libraries;

public interface ArrayListInterface<T>{
    void add(T element);
    int size();
    T get(int index);
    T set(int index, T element);
    boolean remove(T object);
    boolean remove(int index);
    boolean contains(T element);
    void clear();
    //void ensureCapacity();
    void display();
    int indexOf(T element);
    int lastIndexOf(T element);
    boolean isEmpty();


    //TODO: sorting, removeall?
}
