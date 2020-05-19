package tutorial.ch07.exercise03;

import java.util.HashSet;
import java.util.Set;

/**
 * 03
 * How do you compute the union, intersection, and difference of two sets, using
 * just the methods of the Set interface and without using loops?
 */
public class SetUtils {


    public <E> Set<E> makeUnion(final Set<? extends E> first, final Set<? extends E> second) {
        Set<E> result = new HashSet<>(first);
        result.addAll(second);
        return result;
    }

    public <E> Set<E> intersect(final Set<? extends E> first, final Set<? extends E> second) {
        Set<E> result = new HashSet<>(first);
        result.retainAll(second);
        return result;
    }

    public <E> Set<E> differentiate(final Set<? extends E> from, final Set<? extends E> subtrahend) {
        Set<E> result = new HashSet<>(from);
        result.removeAll(subtrahend);
        return result;
    }

    public static void main(String[] args) {
        SetUtils setUtils = new SetUtils();
        Set<String> first = Set.of("First", "Second", "Third");
        Set<String> second = Set.of("Second", "Third", "Forth");
        System.out.println(setUtils.makeUnion(first, second));
        System.out.println(setUtils.intersect(first, second));
        System.out.println(setUtils.differentiate(first, second));
    }

}
