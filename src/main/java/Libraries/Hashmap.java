// Source: https://stackoverflow.com/questions/4072127/custom-hashmap-implementation
// Source: https://www.youtube.com/watch?v=PEdFSWJm0TA (Explain what is node)

/*
* IMPORTANT DON'T DELETE
* My understanding of Hashmap:
*
* Basically, data (Nodes) are store in array.
* Data contains key and value. To increase searching performance, this where
* hashing comes in (well is 'Hashmap')
* When storing data, it needs key, value and key in form of hashcode (hashing)
* Its greatly improve searching performance, since is faster to compare number
* (hash key) with object (normal key)
*
* Since it use hashing method, it has the collision possibility (different key but
* same hashcode), old value will be replaced by new value after collision happened
* To solve this, Node implemented in hashmap, to provide chaining method.
* Node will store key, value and next node
*
* Example,
*       key 'one' has value '1' --> hashcode = 1
*       key 'two' has value '2' --> hashcode = 2
*       key 'three' has value '3' --> hashcode = 2 (collision with key 'two')
*
* Visual example (with nodes),
*   hashcode    1       2
*   value       1       2 -> 3 (chain node)
*
* visual example (without nodes) before add key 'three',
*   hashcode    1       2
*   value       1       2
*
* visual example (without nodes) after add key 'three',
*   hashcode    1       2
*   value       1       3 (replaced)
*
* When retrieve data and collision happen, it retrieves the whole chain node with
* the respective index key in array = hashcode. Example: Node wholeNode = hashmapArray[hashcode]
* Then, start moving node by node (inside chain node) searching for correct key (not hashcode key)
* When correct key found, return the value.
*
* Example:
* 1. searching value with key 'three'
* 2. hashcode is 2, go array hashmapArray[2],
* 3. key 'two' not equals to key 'three', move to next node
* 4. found value with key 'three' return 3
*
* */

package Libraries;

import java.util.Objects;
import static Libraries.Debug.printDebugMsgln;

public class Hashmap <K, V> implements HashmapInterface<K, V>{

    private Node<K, V>[] table;
    private int capacity = 16;
    private int size = 0;
    private static final float LOAD_FACTOR = 0.75f;

    private static class Node<K, V> {
        K key;
        V value;
        Node<K, V> nextNode;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    @SuppressWarnings("unchecked")
    public Hashmap() {
        table = new Node[capacity];
    }

    private int hash(K key) {
        // return Math.abs(key.hashCode()) % capacity;

        printDebugMsgln("Key: " + key + " , Hash: "+ (Objects.hashCode(key) & (capacity - 1)) + " , Ori Hash: " + Objects.hashCode(key));

        return key.hashCode() & (capacity - 1); // bitwise operation, more efficient (bit 'AND' bit operation)
    }

    private int hash(K key, int capacity) {
        // return Math.abs(key.hashCode()) % capacity;

        //For debug purpose.
        printDebugMsgln("Key: " + key + " , Hash: "+ (Objects.hashCode(key) & (capacity - 1)) + " , Ori Hash: " + Objects.hashCode(key));

        return key.hashCode() & (capacity - 1); // bitwise operation, more efficient (bit 'AND' bit operation)
    }

    /**
     * Associates the specified value with the specified key in this map. If the map previously contained
     * a mapping for the key, the old value is replaced by the specified value.
     * @param key   the key
     * @param value the value
     */
    public void put(K key, V value) {
        if(size >= capacity * LOAD_FACTOR) {
            resize();
        }

        int hash = hash(key);
        Node<K, V> newNode = new Node<>(key, value);

        if(table[hash] == null) {
            // Case: No nodes at this hash index, insert newNode directly

            table[hash] = newNode;
        }else{
            // Case: Collision, traverse the chain at table[hash]

            Node<K, V> currentNode = table[hash];
            Node<K, V> previousNode = null;

            while(currentNode != null) {
                if(currentNode.key.equals(key)) {
                    // found same key, update the value
                    currentNode.value = value;
                    return;
                }

                // Save current node before moving to next node
                previousNode = currentNode;
                currentNode = currentNode.nextNode;
            }

            // Key not found in the chain, add new node at the end
            previousNode.nextNode = newNode;

            /*
            * purpose of previousNode is to record currentNode before moving to next node, which
            * might be null (doesn't exist) so new node can be added to previousNode.nextNode
            * Without previousNode, new node can't be added
            *
            * */
        }

        size++;
    }

    /**
     * Returns the value to which the specified key is mapped, or {@code null} if this map contains no mapping for the key.
     * @param key the key
     * @return the value
     */
    public V get(K key) {
        int hash = hash(key);
        Node<K, V> currentNode = table[hash];

        while(currentNode != null) {
            if(currentNode.key.equals(key)) {
                return currentNode.value;
            }else{
                currentNode = currentNode.nextNode;
            }
        }

        return null;
    }

    /**
     * Removes the mapping for the specified key from this map if present.
     * @param key the key
     * @return {@code true} if this map contains a mapping for the specified key and has successfully remove it
     */
    public boolean remove(K key) {
        int hash = hash(key);

        Node<K, V> currentNode = table[hash];
        Node<K, V> previousNode = null;

        while (currentNode != null){
            if(currentNode.key.equals(key)) {
                if(previousNode == null) {
                    table[hash] = currentNode.nextNode;
                }else{
                    previousNode.nextNode = currentNode.nextNode;
                }

                size--;
                return true;
            }

            previousNode = currentNode;
            currentNode = currentNode.nextNode;
        }
        return false;
    }

    public void clear(){
        for(int i = 0; i < capacity; i++) {
            table[i] = null;
        }

        size = 0;
    }

    /**
     * Returns {@code true} if this map contains a mapping for the specified key.
     * @param key the key
     * @return {@code true} if this map contains a mapping for the specified key
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * Resizes the table when the load factor threshold is exceeded.
     */
    @SuppressWarnings("unchecked")
    private void resize() {
        printDebugMsgln("Resizing table.....");

        int newCapacity = capacity * 2;
        Node<K, V>[] newTable = new Node[newCapacity];

        for (int i = 0; i < capacity; i++) {
            Node<K, V> currentNode = table[i];
            while (currentNode != null) {
                Node<K, V> nextNode = currentNode.nextNode;

                int newHash = hash(currentNode.key, newCapacity);

                //Move node to new table, new node sequence doesn't matter
                currentNode.nextNode = newTable[newHash];
                newTable[newHash] = currentNode;

                currentNode = nextNode;
            }
        }

        table = newTable;
        capacity = newCapacity;
    }

    /**
     * Returns the number of key-value mappings in this map.
     * @return the number of key-value mappings
     */
    public int size() {
        return size;
    }

    /**
     * Displays the current state of the hashmap, including all key-value pairs and nodes connected.
     */
    public void display(){
        for(int i = 0; i < capacity; i++) {
            if(table[i] != null) {
                System.out.print(i + ": " + table[i].key + " = " + table[i].value);

                Node<K, V> currentNode = table[i];

                while (currentNode.nextNode != null) {
                    currentNode = currentNode.nextNode;
                    System.out.print(" -> " + currentNode.key + " = " + currentNode.value);
                }

                System.out.println();
            }
        }
    }



    //TODO: Add remove function, clear,
}
