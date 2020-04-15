package tutorial.ch03;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.function.IntConsumer;

public class FunctionalInterfaceTest {

    public static void main(String[] args) {
        String[] words = {"longest", "longer", "long"};
        System.out.println(Arrays.toString(words));
        // Comparator<String> comp = (first, second) -> first.length() - second.length();
        // Arrays.sort(words, (first, second) -> first.length() - second.length());
        Arrays.sort(words, Comparator.comparingInt(String::length));
        System.out.println(Arrays.toString(words));
        // List<String> wordList = Arrays.asList(words);
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
        wordList.removeIf(e -> e.equals("longer"));
        wordList.forEach(System.out::println);
        repeat(10, i -> System.out.println("Countdown: " + (9 - i)));
    }

    public static void repeat(int n, IntConsumer action) {
        for (int i = 0; i < n; i++) action.accept(i);
    }

}
