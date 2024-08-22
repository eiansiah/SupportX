// Source: https://www.devglan.com/datastructure/queue-implementation-java

package Libraries;

import java.util.Arrays;

public class Queue<T> implements QueueInterface<T> {

    // Define INITIAL_CAPACITY, size of elements of custom ArrayList
    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;
    private Object[] elementData;

    private int frontIndex = 0;
    private int backIndex = -1;

    public Queue() {
        elementData = new Object[INITIAL_CAPACITY];
    }

    /**
     * Inserts the specified element into the end of this Queue.
     * @param element the element to be added
     */
    @Override
    public void enqueue(T element) {
        if (size == elementData.length) {
            ensureCapacity(); // increase current capacity of list, make it
            // double.
        }

        backIndex = (backIndex + 1) % elementData.length;
        elementData[backIndex] = element;
        size++;
    }

    /**
     * Retrieves and removes the head of this Queue, or returns null if this Queue is empty.
     * @return the head of this Queue, or null if this Queue is empty
     */
    @Override
    public T dequeue() {
        if(!isEmpty()){
            T element = (T) elementData[frontIndex];

            elementData[frontIndex] = null;

            frontIndex = (frontIndex + 1) % elementData.length;
            size--;

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
            return (T) elementData[frontIndex];
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    private void ensureCapacity() {
        int newIncreasedCapacity = elementData.length * 2;

        Debug.printDebugMsgln("Expand array from " + elementData.length + " to " + newIncreasedCapacity);

        elementData = Arrays.copyOf(elementData, newIncreasedCapacity);
    }
}
