package tutorial.ch06.exercise15;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 15
 * Implement a method map that receives an array list and a Function<T,
 * R> object (see Chapter 3), and that returns an array list consisting of the
 * results of applying the function to the given elements.
 */
public class MethodMapper {

    public <T, R> ArrayList<R> map(final ArrayList<T> list, final Function<T, R> function) {
        return list.stream()
            .map(function::apply)
            .collect(Collectors.toCollection(ArrayList::new));
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>(List.of(5, 6, 7, 8, 9));
        ArrayList<Double> result = new MethodMapper().map(list, integer -> integer / 3.0D );
        System.out.println(result);
    }

}
