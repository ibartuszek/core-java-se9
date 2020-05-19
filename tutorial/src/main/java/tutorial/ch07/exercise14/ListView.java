package tutorial.ch07.exercise14;

import java.util.Iterator;
import java.util.function.BiFunction;

import tutorial.ch07.exercise13.Cache;

/**
 * 14
 * Write a method that produces an immutable list view of the numbers from 0
 * to n, without actually storing the numbers.
 * 15
 * Generalize the preceding exercise to an arbitrary IntFunction. Note that
 * the result is an infinite collection, so certain methods (such as size and
 * toArray) should throw an UnsupportedOperationException.
 * 16
 * Improve the implementation of the preceding exercise by caching the last 100
 * computed function values.
 */
public class ListView implements Iterator<Integer> {

    private int index;
    private int max;

    public ListView(final int max) {
        this.index = 0;
        this.max = max;
    }

    @Override
    public Integer next() {
        return index++;
    }

    @Override
    public boolean hasNext() {
        return index <= max;
    }

    public static void main(String[] args) {
        GeneralListView<Integer> listView = new GeneralListView<>(0,  3, 20, Integer::sum, (i, j) -> Integer.valueOf(i).compareTo(j));
        while (listView.hasNext()) {
            System.out.println(listView.next());
        }
    }

}

class GeneralListView<E> implements Iterator<E> {

    private E index;
    private E increment;
    private E max;
    private BiFunction<E, E, E> add;
    private BiFunction<E, E, Integer> compare;
    private Cache<E, E> cache = new Cache(100);

    public GeneralListView(final E index, final E increment, final E max, final BiFunction<E, E, E> add, final BiFunction<E, E, Integer> compare) {
        this.index = index;
        this.increment = increment;
        this.max = max;
        this.add = add;
        this.compare = compare;
    }

    @Override
    public boolean hasNext() {
        return compare.apply(max, index) > 0;
    }

    @Override
    public E next() {
        E nextIndex = add.apply(index, increment);
        cache.put(index, nextIndex);
        index = nextIndex;
        return index;
    }
}
