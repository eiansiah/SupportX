package ADT;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

public class LinkedStack<T> implements StackInterface<T>{
    private Node head;

    private int size;

    private class Node {
        public T data;
        public Node next;

        public Node(T data) {
            this.data = data;
        }
    }

    /**
     * Pushes the specified element onto the top of this stack.
     * @param element the element to be pushed onto the stack
     */
    @Override
    public void push(T element) {
        if(head == null) {
            head = new Node(element);
        }else{
            Node newNode = new Node(element);
            newNode.next = head;
            head = newNode;
        }

        size++;
    }

    /**
     * Removes and returns the element at the top of this stack.
     * @return the element at the top of this stack, or null if the stack is empty
     */
    @Override
    public T pop() {
        if(!isEmpty()) {
            Node currentNode = head;
            head = head.next;
            size--;

            return currentNode.data;
        }

        return null;
    }

    /**
     * Retrieves, but does not remove, the element at the top of this stack.
     * @return the element at the top of this stack, or null if the stack is empty
     */
    @Override
    public T peek() {
        if(!isEmpty()) {
            return head.data;
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
}
