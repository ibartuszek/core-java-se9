package tutorial.ch07.exercise13;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 13
 * The LinkedHashMap calls the method removeEldestEntry whenever
 * a new element is inserted. Implement a subclass Cache that limits the map to
 * a given size provided in the constructor.
 */
public class Cache<K, V> extends LinkedHashMap<K, V> {

    private final int capacity;

    public Cache(final int capacity) {
        super(capacity);
        this.capacity = capacity;
    }

    @Override
    protected boolean removeEldestEntry(final Map.Entry<K, V> eldest) {
        return this.capacity < this.size();
    }

    public static void main(String[] args) {
        Cache<String, String> cache = new Cache<>(2);
        cache.put("First", "1. value");
        cache.put("Second", "2. value");
        System.out.println(cache);
        cache.put("Third", "3. value");
        System.out.println(cache);
    }

}
