package tutorial.ch05.exercise13;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;

import tutorial.ch03.exercise16.IntSequence;

/**
 * Write a method int min(int[] values) that, just before returning the
 * smallest value, asserts that it is indeed ≤ all values in the array. Use a private
 * helper method or, if you already peeked into Chapter 8,
 * Stream.allMatch. Call the method repeatedly on a large array and
 * measure the runtime with assertions enabled, disabled, and removed.
 */
public class MinimumFinder {

    public int minWithAssertions(int[] values) {
        int minimum = Arrays.stream(values).min().orElseThrow(IllegalArgumentException::new);
        assert Arrays.stream(values).allMatch(i -> minimum <= i);
        return minimum;
    }

    public int minWithoutAssertions(int[] values) {
        int minimum = Arrays.stream(values).min().orElseThrow(IllegalArgumentException::new);
        return minimum;
    }

    public static void main(String[] args) {
        int[] values = initValues();
        MinimumFinder minimumFinder = new MinimumFinder();
        getElapsedTimeInNanoSeconds(values, minimumFinder::minWithAssertions); // -> ~4095 without -ea and 11379 with -ea
        getElapsedTimeInNanoSeconds(values, minimumFinder::minWithoutAssertions); // -> ~1774 us
    }

    private static int[] initValues() {
        int size = 100000;
        int[] array = new int[size];
        Random random = new Random();
        for (int index = 0; index < size; index++) {
            array[index] = random.nextInt(100);
        }
        return array;
    }

    private static long getElapsedTimeInNanoSeconds(final int[] values, final Function<int[], Integer> function) {
        long start = System.nanoTime();
        int result = function.apply(values);
        long end = System.nanoTime();
        System.out.printf("Result: %d; and the elapsed time is: %d us\n", result, (end - start) / 1000);
        return end - start;
    }

}
