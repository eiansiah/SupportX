package Libraries;

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
