package Libraries;

/*
 *  author: Saw Khoo Zi Chong
 *  ID: 2307609
 * */

public interface HashmapInterface<K, V> {
    void put(K key, V value);
    V get(K key);
    boolean remove(K key);
    void clear();
    boolean containsKey(K key);
    int size();
    void display();
}
