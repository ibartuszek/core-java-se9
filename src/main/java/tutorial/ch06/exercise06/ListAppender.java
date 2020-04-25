package tutorial.ch06.exercise06;

import java.util.ArrayList;

import tutorial.ch04.exercise01.LabeledPoint;
import tutorial.ch04.exercise01.Point;

/**
 * 06
 * Implement a generic method that appends all elements from one array list to
 * another. Use a wildcard for one of the type arguments. Provide two equivalent
 * solutions, one with a ? extends E wildcard and one with ? super E.
 */
public class ListAppender {

    private static <E> void appendAll(final ArrayList<? extends E> from, final ArrayList<E> to) {
        to.addAll(from);
    }

    private static <E> void appendAll2(final ArrayList<E> from, final ArrayList<? super E> to) {
        to.addAll(from);
    }

    public static void main(String[] args) {
        ArrayList<Point> to = new ArrayList<>();
        ArrayList<LabeledPoint> from = new ArrayList<>();
        from.add(new LabeledPoint(20.0D, 20.0D, "first"));
        from.add(new LabeledPoint(30.0D, 30.0D, "second"));
        from.add(new LabeledPoint(40.0D, 40.0D, "third"));
        appendAll(from, to);
        appendAll2(from, to);
        to.forEach(System.out::println);
    }

}
