package Libraries;

public class Stack<T> extends ArrayList<T> implements StackInterface<T>{

    public Stack(){
        super();
    }

    /**
     * Pushes the specified element onto the top of this Stack.
     * @param element the element to be pushed onto this Stack
     */
    @Override
    public void push(T element) {
        add(element);
    }

    /**
     * Removes and returns the element at the top of this Stack,
     * or returns null if this Stack is empty.
     * @return the element at the top of this Stack, or null if this Stack is empty
     */
    @Override
    public T pop() {
        if(!isEmpty()){
            T element = get(size() - 1);
            remove(size() - 1);

            return element;
        }

        return null;
    }

    /**
     * Retrieves, but does not remove, the element at the top of this Stack,
     * or returns null if this Stack is empty.
     * @return the element at the top of this Stack, or null if this Stack is empty
     */
    @Override
    public T peek() {
        if(!isEmpty()){
            return get(size() - 1);
        }

        return null;
    }
}
