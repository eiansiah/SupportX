package Libraries;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

public interface QueueInterface<T> {

    void enqueue(T element);
    T dequeue();
    T peek();
    boolean isEmpty();
    int size();
}
