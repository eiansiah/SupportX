// Source: https://www.devglan.com/datastructure/queue-implementation-java

package Libraries;

public class Queue<T> extends ArrayList<T> implements QueueInterface<T> {

    public Queue() {
        super();
    }

    /**
     * Inserts the specified element into the end of this Queue.
     * @param element the element to be added
     */
    @Override
    public void enqueue(T element) {
        add(element);
    }

    /**
     * Retrieves and removes the head of this Queue, or returns null if this Queue is empty.
     * @return the head of this Queue, or null if this Queue is empty
     */
    @Override
    public T dequeue() {
        if(!isEmpty()){
            T element = get(0);
            remove(0);

            return element;
        }

        return null;
    }

    /**
     * Retrieves, but does not remove, the head of this Queue, or returns null if this Queue is empty.
     * @return the head of this Queue, or null if this Queue is empty
     */
    @Override
    public T peek() {
        if(!isEmpty()){
            return get(0);
        }

        return null;
    }
}
