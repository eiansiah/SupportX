package Libraries;

public interface QueueInterface<T> {

    void enqueue(T element);
    T dequeue();
    T peek();
    boolean isEmpty();
    int size();
}
