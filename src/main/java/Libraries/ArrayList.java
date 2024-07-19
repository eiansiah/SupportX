// Source: https://www.javamadesoeasy.com/2015/02/arraylist-custom-implementation.html?m=1

package Libraries;

import java.util.Arrays;


public class ArrayList<T> implements ArrayListInterface<T>{

    // Define INITIAL_CAPACITY, size of elements of custom ArrayList
    private static final int INITIAL_CAPACITY = 10;
    private int size = 0;
    private Object[] elementData;

    public ArrayList() {
        elementData = new Object[INITIAL_CAPACITY];
    }

    /**
     * Method adds elements to List.
     */
    public boolean add(T element) {
        if (size == elementData.length) {
            ensureCapacity(); // increase current capacity of list, make it
            // double.
        }
        elementData[size++] = element;

        return true;
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size "
                    + index);
        }
        return (T) elementData[index]; // return value on index.
    }

    /** Replace the element with the respective index.
     * @param index position of element
     * @param element new element to replace the old element
     * @return old element*/
    @Override
    public T set(int index, T element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size "
                    + index);
        }

        T oldValue = (T) elementData[index];
        elementData[index] = element;

        return oldValue;
    }

    /**
     * remove elements from ArrayList method returns
     * @param object remove specific object
     */
    @Override
    public boolean remove(Object object) {
        int i = 0;

        found: {
            if (object == null) {
                for (; i < size; i++)
                    if (elementData[i] == null)
                        break found;
            } else {
                for (; i < size; i++)
                    if (object.equals(elementData[i]))
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
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size "
                    + index);
        }

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
        elementData = Arrays.copyOf(elementData, newIncreasedCapacity);
    }

    /**
     * Display ArrayList.
     */
    @Override
    public void display() {
        System.out.print("Displaying list : ");
        for (int i = 0; i < size; i++) {
            System.out.print(elementData[i] + " ");
        }
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

}
