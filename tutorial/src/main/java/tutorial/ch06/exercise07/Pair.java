package tutorial.ch06.exercise07;

import java.util.Objects;

/**
 * 07
 * Implement a class Pair<E> that stores a pair of elements of type E. Provide
 * accessors to get the first and second element.
 * 08
 * Modify the class of the preceding exercise by adding methods max and min,
 * getting the larger or smaller of the two elements. Supply an appropriate type
 * bound for E.
 *
 */
public class Pair<E extends Comparable<? super E>>{

    private E first;
    private E second;

    public Pair() {
    }

    public Pair(final E first, final E second) {
        this.first = first;
        this.second = second;
    }

    public E getFirst() {
        return first;
    }

    public void setFirst(final E first) {
        this.first = first;
    }

    public E getSecond() {
        return second;
    }

    public void setSecond(final E second) {
        this.second = second;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Pair<?> pair = (Pair<?>) o;
        return Objects.equals(first, pair.first)
            && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public String toString() {
        return "Pair{"
            + "first=" + first
            + ", Second=" + second
            + '}';
    }

    public E getMin() {
        return first.compareTo(second) >= 0 ? second : first;
    }

    public E getMax() {
        return first.compareTo(second) < 0 ? second : first;
    }

    public static void main(String[] args) {
        Pair<Double> pair = new Pair<>(20.0D, 10.0D);
        System.out.println("First: " + pair.getFirst());
        System.out.println("Second: " + pair.getSecond());
        System.out.println("Min: " + pair.getMin());
        System.out.println("Max: " + pair.getMax());
    }

}
