package Libraries;

import java.util.Arrays;

public class Stack<T> implements StackInterface<T>{

    private static final int INITIAL_CAPACITY = 16;
    private int size = 0;
    private Object[] elementData;

    public Stack(){
        elementData = new Object[INITIAL_CAPACITY];
    }

    /**
     * Pushes the specified element onto the top of this Stack.
     * @param element the element to be pushed onto this Stack
     */
    @Override
    public void push(T element) {
        if (size == elementData.length) {
            ensureCapacity(); // increase current capacity of list, make it
            // double.
        }

        elementData[size++] = element;
    }

    /**
     * Removes and returns the element at the top of this Stack,
     * or returns null if this Stack is empty.
     * @return the element at the top of the stack, or null if empty
     */
    @Override
    public T pop() {
        if(!isEmpty()){
            T element = (T) elementData[--size];
            elementData[size] = null;

            return element;
        }

        return null;
    }

    /**
     * Retrieves, but does not remove, the element at the top of this Stack,
     * or returns null if this Stack is empty.
     * @return the element at the top of the stack, or null if empty
     */
    @Override
    public T peek() {
        if(!isEmpty()){
            return (T) elementData[size - 1];
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
