package tutorial.ch07.exercise05;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.function.Supplier;

/**
 * 05
 * Implement a method public static void swap(List<?> list,
 * int i, int j) that swaps elements in the usual way when the type of
 * list implements the RandomAccess interface, and that minimizes the cost
 * of visiting the positions at index i and j if it is not.
 */
public class Swapper {

    public static void swap(final List<?> list, final int i, final int j) {
        if (list instanceof RandomAccess) {
            swapHelperWithRandomAccess(list, i, j);
        } else {
            swapHelperWithSequence(list, i, j);
        }
    }

    private static <T> void swapHelperWithRandomAccess(final List<T> list, final int i, final int j) {
        T temp = list.get(i);
        list.set(i, list.get(j));
        list.set(j, temp);
    }

    private static <T> void swapHelperWithSequence(final List<T> list, final int i, final int j) {
        SwapPairs<T> pairs = getSwapPairs(list, i, j);
        int limit = Integer.max(i, j);
        ListIterator<T> listIterator = list.listIterator();
        for (int index = 0; index < list.size() || index <= limit; index++) {
            listIterator.next();
            if (index == i) {
                listIterator.set(pairs.getSecond());
            } else if (index == j) {
                listIterator.set(pairs.getFirst());
            }
        }
    }

    private static <T> SwapPairs<T> getSwapPairs(final List<T> list, final int i, final int j) {
        T first = null;
        T second = null;
        ListIterator<T> listIterator = list.listIterator();
        for (int index = 0; (first == null || second == null) && i < list.size(); index++) {
            if (index == i) {
                first = listIterator.next();
            } else if (index == j) {
                second = listIterator.next();
            } else {
                listIterator.next();
            }
        }
        return new SwapPairs<T>(first, second);
    }

    public static void main(String[] args) {
        testSwap(ArrayList::new, 20, "ArrayList with random access");
        testSwap(LinkedList::new, 20, "ArrayList with random access");
    }

    public static void testSwap(final Supplier<List<Integer>> constructor, final int size, final String message) {
        List<Integer> list = constructor.get();
        init(list, size);
        swap(list, 5, 10);
        System.out.println(MessageFormat.format("Test swap with: {0}", message));
        System.out.println(list + "\n");
    }

    private static void init(final List<Integer> list, final int size) {
        for(int index = 0; index < size; index++) {
            list.add(index);
        }
    }

}

class SwapPairs<T> {
    private T first;
    private T second;

    public SwapPairs(final T first, final T second) {
        this.first = first;
        this.second = second;
    }

    public T getFirst() {
        return first;
    }

    public T getSecond() {
        return second;
    }

}
