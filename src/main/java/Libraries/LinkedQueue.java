package Libraries;

public class LinkedQueue<T> implements QueueInterface<T>{
    private Node head;
    private Node tail;

    private int size;

    private class Node {
        public T data;
        public Node next;

        public Node(T data) {
            this.data = data;
        }
    }

    /**
     * Inserts the specified element at the end of this queue.
     * @param element the element to be added to the queue
     */
    @Override
    public void enqueue(T element) {
        Node newNode = new Node(element);

        if (head == null) {
            head = tail = newNode;
        }else{
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    /**
     * Retrieves and removes the element at the front of this queue.
     * @return the element at the front of this queue, or null if the queue is empty
     */
    @Override
    public T dequeue() {
        if(!isEmpty()) {
            Node currentNode = head;
            head = head.next;
            size--;

            return currentNode.data;
        }
        return null;
    }

    /**
     * Retrieves, but does not remove, the element at the front of this queue.
     * @return the element at the front of this queue, or null if the queue is empty
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
