package tutorial.ch06.exercise04;

import java.util.ArrayList;
import java.util.Objects;

/**
 * 3.
 * Implement a class Table<K, V> that manages an array list of Entry<K,
 * V> elements. Supply methods to get the value associated with a key, to put a
 * value for a key, and to remove a key.
 * 4.
 * In the previous exercise, make Entry into a nested class. Should that class
 * be generic?
 */
public class TableWithInnerEntry<K, V> {

    final class Entry<K, V> {

        private final K key;
        private final V value;

        public Entry(final K key, final V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o)
                return true;
            if (o == null || getClass() != o.getClass())
                return false;
            Entry<?, ?> entry = (Entry<?, ?>) o;
            return Objects.equals(key, entry.key) &&
                Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return "Entry{"
                + "key=" + key
                + ", value=" + value
                + '}';
        }
    }

    private ArrayList<Entry<K, V>> store = new ArrayList<>();

    public Entry<K, V> get(final K key) {
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
            store.add(new Entry<>(key, value));
        }
    }

    public void remove(final K key) {
        int index = getIndex(key);
        if (index > -1) {
            store.remove(index);
        }
    }

    public static void main(String[] args) {
        TableWithInnerEntry<Integer, String> tableWithInnerEntry = new TableWithInnerEntry<>();
        tableWithInnerEntry.put(1, "First");
        tableWithInnerEntry.put(2, "Second");
        tableWithInnerEntry.put(3, "Third");
        System.out.println(tableWithInnerEntry.get(1));
        System.out.println(tableWithInnerEntry.get(0));
        tableWithInnerEntry.put(1, "NewFirst");
        System.out.println(tableWithInnerEntry.get(1));
        tableWithInnerEntry.remove(1);
        System.out.println(tableWithInnerEntry.get(1));
    }

}
