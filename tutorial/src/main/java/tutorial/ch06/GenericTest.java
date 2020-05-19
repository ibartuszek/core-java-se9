package tutorial.ch06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import tutorial.ch04.exercise01.LabeledPoint;
import tutorial.ch04.exercise01.Point;

public class GenericTest {

    private static final LabeledPoint[] LABELED_POINTS = {
        new LabeledPoint(5.0D, 5.0D, "point 1"),
        new LabeledPoint(10.0D, 10.0D, "point 2")
    };

    public static void main(String[] args) {
        covarianceExample();
        superTypeWildCardExample();
        captureWildCardExample();
        constructorExample();
    }

    private static void covarianceExample() {
        try {
            Point[] points = LABELED_POINTS;
            points[0] = new Point(5.0D, 5.0D);
        } catch (ArrayStoreException e) {
            System.err.println("Cannot store superclass in a subclass array!");
        }
    }

    private static void superTypeWildCardExample() {
        printAll(LABELED_POINTS, p -> p.getX() > 5.0D);
    }

    // private static void printAll(Point[] points, Predicate<? super Point> filter) {
    //     Arrays.stream(points)
    //         .filter(filter::test)
    //         .forEach(System.out::println);
    // }

    private static <T> void printAll(T[] elements, Predicate<? super T> filter) {
        Arrays.stream(elements)
            .filter(filter::test)
            .forEach(System.out::println);
    }

    private static void captureWildCardExample() {
        List<LabeledPoint> labeledPointList = new ArrayList<>(List.of(LABELED_POINTS[0], LABELED_POINTS[1]));
        swap(labeledPointList, 0, 1);
    }

    public static void swap(final List<?> elements, final int i, final int j) {
        swapHelper(elements, i, j);
        System.out.println(elements);
    }

    private static <T> void swapHelper(final List<T> elements, final int i, final int j) {
        T temp = elements.get(i);
        elements.set(i, elements.get(j));
        elements.set(j, temp);
    }

    private static void constructorExample() {
        LabeledPoint[] points = repeat(3, LABELED_POINTS[0], LabeledPoint[]::new);
        System.out.println(Arrays.toString(points));
    }

    public static <T> T[] repeat(int n, T obj, IntFunction<T[]> constr) {
        T[] result = constr.apply(n);
        for (int i = 0; i < n; i++) {
            result[i] = obj;
        }
        return result;
    }

}
