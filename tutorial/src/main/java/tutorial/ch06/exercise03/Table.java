package tutorial.ch06.exercise03;

import java.util.ArrayList;
import java.util.Map;

/**
 * 3. Implement a class Table<K, V> that manages an array list of Entry<K,
 * V> elements. Supply methods to get the value associated with a key, to put a
 * value for a key, and to remove a key.
 */
public class Table<K, V> {

    private ArrayList<Map.Entry<K, V>> store = new ArrayList<>();

    public Map.Entry<K, V> get(final K key) {

        // return store.stream()
        //     .filter(entry -> entry.getKey().equals(key))
        //     .findAny()
        //     .orElse(null);

        int index = getIndex(key);
        return index > -1 ? store.get(index) : null;
    }

    private int getIndex(final K key) {
        int index = -1;
        for (int i = 0; index < 0 && i < store.size(); i++) {
            if (store.get(i).getKey().equals(key)) {
                index = i;
            }
        }
        return index;
    }

    public void put(final K key, final V value) {
        if (getIndex(key) < 0) {
            store.add(Map.entry(key, value));
        }
    }

    public void remove(final K key) {
        int index = getIndex(key);
        if (index > -1) {
            store.remove(index);
        }
    }

    public static void main(String[] args) {
        Table<Integer, String> table = new Table<>();
        table.put(1, "First");
        table.put(2, "Second");
        table.put(3, "Third");
        System.out.println(table.get(1));
        System.out.println(table.get(0));
        table.put(1, "NewFirst");
        System.out.println(table.get(1));
        table.remove(1);
        System.out.println(table.get(1));
    }

}
