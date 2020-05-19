package tutorial.ch07.exercise09;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

/**
 * 09
 * You can update the counter in a map of counters as
 * counts.merge(word, 1, Integer::sum);
 * Do the same without the merge method, (a) by using contains, (b) by
 * using get and a null check, (c) by using getOrDefault, (d) by using
 * putIfAbsent.
 */
public class MapCounter<K, V> {

    private V defaultValue;
    private Map<K, V> map;

    public MapCounter(final Map<K, V> map, final V defaultValue) {
        this.map = map;
        this.defaultValue = defaultValue;
    }

    public Map<K, V> getMap() {
        return new HashMap<>(map);
    }

    public V mergeWithContains(final K key, final V value,
        final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V result = null;
        if (map.containsKey(key)) {
            result = map.put(key, remappingFunction.apply(map.get(key), value));
        } else {
            result = map.put(key, value);
        }
        return result;
    }

    public V mergeWithGetAndNullCheck(final K key, final V value,
        final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V oldValue = map.get(key);
        return oldValue != null
            ? map.put(key, remappingFunction.apply(oldValue, value))
            : map.put(key, value);
    }

    public V mergeWithGetOrDefault(final K key, final V value,
        final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V oldValue = map.getOrDefault(key, defaultValue);
        return map.put(key, remappingFunction.apply(oldValue, value));
    }

    public V mergeWithPutIfAbsent(final K key, final V value,
        final BiFunction<? super V, ? super V, ? extends V> remappingFunction) {
        V putResult = map.putIfAbsent(key, value);
        return putResult == null ? value
            : map.put(key, remappingFunction.apply(map.get(key), value));
    }

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>(
            Map.of("First", 1, "Second", 2, "Third", 3, "Forth", 4)
        );
        MapCounter<String, Integer> mapCounter = new MapCounter<>(map, 0);
        mapCounter.mergeWithContains("First", 2, Integer::sum);
        mapCounter.mergeWithGetAndNullCheck("Second", 2, Integer::sum);
        mapCounter.mergeWithGetOrDefault("Third", 2, Integer::sum);
        mapCounter.mergeWithPutIfAbsent("Forth", 2, Integer::sum);
        System.out.println(mapCounter.getMap());
    }

}
