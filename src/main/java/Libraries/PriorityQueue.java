package Libraries;

/*
 *  Author: Sim Hor Kang
 *  ID: 2307611
 * */

import java.util.Arrays;
import java.util.Comparator;

public class PriorityQueue<T> implements QueueInterface<T> {

    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;
    private Object[] elementData;
    private Comparator<? super T> comparator;

    public PriorityQueue(Comparator<? super T> comparator) {
        this.elementData = new Object[INITIAL_CAPACITY];
        this.comparator = comparator;
    }

    /**
     * Inserts the specified element into this priority queue.
     * @param element the element to be added
     */
    @Override
    public void enqueue(T element) {
        if (size == elementData.length) {
            ensureCapacity();
        }

        int i;
        for (i = size - 1; i >= 0; i--) {
            if (comparator.compare((T) elementData[i], element) > 0) {
                elementData[i + 1] = elementData[i];
            } else {
                break;
            }
        }

        elementData[i + 1] = element;
        size++;
    }

    /**
     * Retrieves and removes the head of this priority queue, or returns null if this priority queue is empty.
     * @return the element removed, or null if empty
     */
    @Override
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }

        T element = (T) elementData[0];

        for (int i = 1; i < size; i++) {
            elementData[i - 1] = elementData[i];
        }

        elementData[--size] = null;

        return element;
    }

    /**
     * Retrieves the element at the front of the priority queue without removing it.
     * @return the element at the front, or null if empty
     */
    @Override
    public T peek() {
        return isEmpty() ? null : (T) elementData[0];
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
        elementData = Arrays.copyOf(elementData, newIncreasedCapacity);
    }
}
