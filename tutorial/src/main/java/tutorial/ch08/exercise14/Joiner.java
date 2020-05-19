package tutorial.ch08.exercise14;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * 14
 * Join all elements in a Stream<ArrayList<T>> to one ArrayList<T>.
 * Show how to do this with each of the three forms of reduce.
 */
public class Joiner {

    public <T> ArrayList<T> joinWithBinaryOperator(final Stream<ArrayList<T>> source) {
        return source.reduce(this::merge)
            .orElseGet(ArrayList::new);
    }

    private <T> ArrayList<T> merge(final ArrayList<T> first, final ArrayList<T> second) {
        ArrayList<T> arrayList = new ArrayList<>(first);
        arrayList.addAll(second);
        return arrayList;
    }

    public <T> ArrayList<T> joinWithDefaultValueAndBinaryOperator(final Stream<ArrayList<T>> source) {
        return source.reduce(new ArrayList<T>(), this::merge);
    }

    public <T> ArrayList<T> joinWithBiFunction(final Stream<ArrayList<T>> source) {
        return source.parallel().reduce(new ArrayList<>(), this::merge, this::merge);
    }

    public static void main(String[] args) {
        Joiner joiner = new Joiner();
        ArrayList<String> first = new ArrayList<>(List.of("First", "Second"));
        ArrayList<String> second = new ArrayList<>(List.of("Third", "Forth"));
        System.out.println(joiner.joinWithBinaryOperator(Stream.of(first, second)));
        System.out.println(joiner.joinWithDefaultValueAndBinaryOperator(Stream.of(first, second)));
        System.out.println(joiner.joinWithBiFunction(Stream.of(first, second)));
    }

}
