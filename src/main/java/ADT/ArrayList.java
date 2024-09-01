// Source: https://www.javamadesoeasy.com/2015/02/arraylist-custom-implementation.html?m=1

package ADT;

/*
 *  author: Saw Khoo Zi Chong + Ko Jie Qi
 *  ID: 2307609 + 2307589
 * */

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;


public class ArrayList<T> implements ListInterface<T>, Iterable<T>{

    // Define INITIAL_CAPACITY, size of elements of custom ArrayList
    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;
    private Object[] elementData;

    public ArrayList() {
        elementData = new Object[INITIAL_CAPACITY];
    }

    /**
     * Method adds elements to ListInterface.
     */
    public void add(T newElement) {
        if (size == elementData.length) {
            ensureCapacity(); // increase current capacity of list, make it double.
        }
        elementData[size] = newElement;
        size++;
    }

    /**
     * Inserts a new element at the specified position in the list.
     * @param index position at which to insert the new element
     * @param newElement the element to insert
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public void add(int index, T newElement) {
        checkAddRange(index);

        if (size == elementData.length) {
            ensureCapacity(); // increase current capacity of list, make it
            // double.
        }

        /*for (int i = size; i > index; i--) {
            elementData[i] = elementData[i - 1];
        }*/

        System.arraycopy(elementData, index,
                elementData, index + 1,
                size - index);

        elementData[index] = newElement;
        size++;
    }

    /** @return array size */
    @Override
    public int size() {
        return size;
    }

    /**
     * method returns element on specific index.
     */
    @SuppressWarnings("unchecked")
    public T get(int index) {
        // if index is negative or greater than size of size, we throw
        // Exception.
        checkAddRange(index);

        return (T) elementData[index]; // return value on index.
    }

    /** Replace the element with the respective index.
     * @param index position of element
     * @param newElement new element to replace the old element
     * @return old element*/
    @Override
    public T set(int index, T newElement) {
        checkAddRange(index);

        T oldValue = (T) elementData[index];
        elementData[index] = newElement;

        return oldValue;
    }

    /**
     * remove elements from ArrayList method returns
     * @param element remove specific object
     */
    @Override
    public boolean remove(T element) {
        int i = 0;

        found: {
            if (element == null) {
                for (; i < size; i++)
                    if (elementData[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (element.equals(elementData[i]))
                        break found;
            }
            return false;
        }

        remove(i);
        return true;
    }

    /**
     * remove elements from ArrayList method returns
     * @param index removedElement on specific index. else it throws IndexOutOfBoundException
     * if index is negative or greater than size of size.
     */
    public boolean remove(int index) {
        checkAddRange(index);

        elementData[index] = null;

        for (int i = index; i < size - 1; i++) {
            elementData[i] = elementData[i + 1];
        }

        size--;

        return true;
    }

    /** Check whether arraylist contains certain element
     * @return boolean  */
    @Override
    public boolean contains(T element) {
        for (int i = 0; i < size; i++) {
            if (element.equals(elementData[i])) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void clear() {
        for(int i = 0; i < size; i++) {
            elementData[i] = null;
        }

        size = 0;
    }

    /**
     * Method increases capacity of list by making it double.
     */
    private void ensureCapacity() {
        int newIncreasedCapacity = elementData.length * 2;

        //Debug.printDebugMsgln("Expand array from " + elementData.length + " to " + newIncreasedCapacity);

        elementData = Arrays.copyOf(elementData, newIncreasedCapacity);
    }

    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    @Override
    public int indexOf(T element) {
        if (contains(element)) {
            for (int i = 0; i < size; i++) {
                if (element.equals(elementData[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     */
    @Override
    public int lastIndexOf(T element) {
        if (contains(element)) {
            for (int i = size - 1; i >= 0; i--) {
                if (element.equals(elementData[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    /** @return boolean whether is the arraylist empty */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkAddRange(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }
    }
    
    public void sort(Comparator<? super T> comparator) {
        int n = size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (comparator.compare(get(j), get(j + 1)) > 0) {
                    T temp = get(j);
                    set(j, get(j + 1));
                    set(j + 1, temp);
                }
            }
        }
    }

    /** Added for foreach function to work*/
    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int currentIndex = 0;

        @Override
        public boolean hasNext() {
            return currentIndex < size;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return (T) elementData[currentIndex++];
        }
    }
}
