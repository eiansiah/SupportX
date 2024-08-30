/*
package Libraries;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> implements ListInterface<T>, Iterable<T> {
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

    */
/**
     * Adds the specified element to the end of this list.
     * @param element the element to be added
     *//*

    @Override
    public void add(T element) {
        addLast(element);
    }

    */
/**
     * Inserts the specified element at the specified position in this list.
     * @param index the index at which to insert the element
     * @param element the element to be inserted
     *//*

    public void add( int index, T element) {
        checkAddRange(index);

        Node current = head;
        Node prev = null;

        for (int i = 0; i < index; i++) {
            prev = current;
            current = current.next;
        }

        if(prev == null) {
            head = new Node(element);
        }else{
            Node newNode = new Node(element);
            newNode.next = current;
            prev.next = newNode;
        }
        size++;
    }

    */
/**
     * Appends the specified element to the end of this list.
     * @param element the element to be appended
     *//*

    public void addLast(T element) {
        Node newNode = new Node(element);

        if (head == null) {
            head = tail = newNode;
        }else{
            tail.next = newNode;
            tail = newNode;
        }

        size++;
    }

    */
/**
     * Inserts the specified element at the beginning of this list.
     * @param element the element to be inserted
     *//*

    public void addFirst(T element) {
        Node newNode = new Node(element);

        if (head == null) {
            tail = newNode;
        }else{
            newNode.next = head;
        }

        head = newNode;

        size++;
    }

    @Override
    public int size() {
        return size;
    }

    */
/**
     * Returns the element at the specified position in this list.
     * @param index the index of the element to return
     * @return the element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     *//*

    @Override
    public T get(int index) {
        checkAddRange(index);

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        return current.data;
    }

    */
/**
     * Replaces the element at the specified position in this list with the specified element.
     * @param index the index of the element to replace
     * @param element the new element
     * @return the old element at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     *//*

    @Override
    public T set(int index, T element) {
        checkAddRange(index);
        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
        }

        T old = current.data;
        current.data = element;

        return old;
    }

    */
/**
     * Removes the first occurrence of the specified element from this list.
     * @param element the element to be removed
     * @return true if the element was removed, false otherwise
     *//*

    @Override
    public boolean remove(T element) {

        if (head == null) {
            return false;
        }

        Node current = head;
        Node prev = null;

        if(element == null) {
            while (current != null) {
                if (current.data == null) {
                    if (prev == null) {
                        head = current.next;
                    }else{
                        prev.next = current.next;
                    }

                    size--;
                    return true;
                }

                prev = current;
                current = current.next;
            }
        }else{
            while (current != null) {
                if (current.data.equals(element)) {
                    if (prev == null) {
                        head = current.next;
                    }else{
                        prev.next = current.next;
                    }

                    size--;
                    return true;
                }

                prev = current;
                current = current.next;
            }
        }

        return false; //not found any related element
    }

    */
/**
     * Removes the element at the specified position in this list.
     * @param index the index of the element to be removed
     * @return true if the element was removed, false otherwise
     * @throws IndexOutOfBoundsException if the index is out of range
     *//*

    @Override
    public boolean remove(int index) {
        checkAddRange(index);

        Node current = head;
        Node prev = null;

        for (int i = 0; i < index; i++) {
            prev = current;
            current = current.next;
        }

        if(prev == null){
            head = current.next;
        }else{
            prev.next = current.next;
        }

        size--;

        return true;
    }

    */
/**
     * Returns true if this list contains the specified element.
     * @param element the element to check for
     * @return true if the list contains the element, false otherwise
     *//*

    @Override
    public boolean contains(T element) {

        Node current = head;
        while (current != null) {
            if (current.data.equals(element)) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public void display() {
        System.out.print("Displaying list : ");

        Node current = head;

        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
    }

    */
/**
     * Returns the index of the first occurrence of the specified element
     * @param element the element to find
     * @return the index of the element, or -1 if not found
     *//*

    @Override
    public int indexOf(T element) {
        Node current = head;

        if(contains(element)){
            for (int i = 0; i < size; i++) {
                if(current.data.equals(element)){
                    return i;
                }
                current = current.next;
            }
        }

        return -1;
    }

    */
/**
     * Returns the index of the last occurrence of the specified element
     * @param element the element to find
     * @return the index of the last occurrence, or -1 if not found
     *//*

    @Override
    public int lastIndexOf(T element) {
        Node current = head;

        if(contains(element)){
            for (int i = size - 1; i >= 0; i--) {
                if(current.data.equals(element)){
                    return i;
                }
                current = current.next;
            }
        }

        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    private void checkAddRange(int index){
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size " + index);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    private class LinkedListIterator implements Iterator<T> {
        private Node currentNode = head;

        @Override
        public boolean hasNext() {
            return currentNode != null;
        }

        @Override
        @SuppressWarnings("unchecked")
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            T data = (T) currentNode.data;
            currentNode = currentNode.next;
            return data;
        }
    }
}
*/
