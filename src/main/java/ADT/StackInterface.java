package ADT;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

public interface StackInterface<T> {
    void push(T element);
    T pop();
    T peek();
    boolean isEmpty();
    int size();
}
