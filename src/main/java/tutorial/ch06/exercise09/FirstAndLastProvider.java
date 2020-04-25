package tutorial.ch06.exercise09;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import tutorial.ch06.exercise07.Pair;

/**
 * 09
 * In a utility class Arrays, supply a method
 * public static <E> Pair<E> firstLast(ArrayList<___> a)
 * that returns a pair consisting of the first and last element of a. Supply an
 * appropriate type argument.
 * 10
 * Provide generic methods min and max in an Arrays utility class that yield
 * the smallest and largest element in an array.
 * 11
 * Continue the preceding exercise and provide a method minMax that yields a
 * Pair with the minimum and maximum
 * 12
 * Implement the following method that stores the smallest and largest element
 * in elements in the result list:
 * public static <T> void minmax(List<T> elements,
 * Comparator<? super T> comp, List<? super T> result)
 * Note the wildcard in the last parameter—any supertype of T will do to hold
 * the result.
 * 13
 * Given the method from the preceding exercise, consider this method:
 * public static <T> void maxmin(List<T> elements,
 * Comparator<? super T> comp, List<? super T> result) {
 * minmax(elements, comp, result);
 * Lists.swapHelper(result, 0, 1);
 * }
 * Why would this method not compile without wildcard capture? Hint: Try to
 * supply an explicit type Lists.<___>swapHelper(result, 0, 1)
 */
public class FirstAndLastProvider {

    public static <E extends Comparable<? super E>> Pair<E> firstAndLast(final ArrayList<? extends E> arrayList) {
        return arrayList.size() > 0
            ? new Pair<E>(arrayList.get(0), arrayList.get(arrayList.size() - 1))
            : null;
    }

    public static <E extends Comparable<? super E>> E max(final E[] array) {
        return Arrays.stream(array)
            .max(Comparator.naturalOrder())
            .orElse(null);
    }

    public static <E extends Comparable<? super E>> E min(final E[] array) {
        return Arrays.stream(array)
            .min(Comparator.naturalOrder())
            .orElse(null);
    }

    public static <E extends Comparable<? super E>> Pair<E> minMax(final  E[] array) {
        return new Pair<>(min(array), max(array));
    }

    public static <T> void minMax(final List<T> elements, final Comparator<? super T> comp, final List<? super T> result) {
        T min = null;
        T max = null;
        for (T element : elements) {
            if (comp.compare(element, min) < 0) {
                min = element;
            }
            if (comp.compare(element, max) > 0) {
                max = element;
            }
        }
        result.add(min);
        result.add(max);
    }

    public static <T> void maxMin(final List<T> elements, final Comparator<? super T> comp, final List<? super T> result) {
        minMax(elements, comp, result);
        swapHelper(result, 0, 1);
    }

    private static <T> void swapHelper(final List<T> list, final int first, final int second) {
        T temp = list.get(first);
        list.set(first, list.get(second));
        list.set(second, temp);
    }

    public static void main(String[] args) {
        ArrayList<Integer> integerArrayList = new ArrayList<>();
        integerArrayList.add(10);
        integerArrayList.add(15);
        integerArrayList.add(5);
        System.out.println(firstAndLast(integerArrayList));
        Integer[] array = integerArrayList.toArray(Integer[]::new);
        System.out.println("minMax=" + minMax(array));
        List<Integer> minMaxList = new ArrayList<>();
        minMax(integerArrayList, Comparator.naturalOrder(), minMaxList);
        System.out.println(minMaxList);
    }

}
