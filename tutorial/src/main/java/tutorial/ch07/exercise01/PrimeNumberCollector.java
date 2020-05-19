package tutorial.ch07.exercise01;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntFunction;

/**
 * 01
 * Implement the “Sieve of Erathostenes” algorithm to determine all prime
 * numbers ≤ n. Add all numbers from 2 to n to a set. Then repeatedly find the
 * smallest element s in the set, and remove s2, s · (s + 1), s · (s + 2), and so on.
 * You are done when s2 > n. Do this with both a HashSet<Integer> and a
 * BitSet.
 */
public class PrimeNumberCollector {

    private static final int ONE = 1;
    private static final int FIRST_PRIME = 2;

    public List<Integer> collect(final int n) {
        return collectPrimes(initNumbersWithHashSet(n), n);
    }

    private Set<Integer> initNumbersWithHashSet(final int n) {
        Set<Integer> numbers = new HashSet<>();
        for (int index = FIRST_PRIME; index <= n; index++) {
            numbers.add(index);
        }
        return numbers;
    }

    private List<Integer> collectPrimes(final Set<Integer> numbers, final int n) {
        List<Integer> result = new ArrayList<>(List.of(ONE));
        for (int number = 2; number <= n; number++) {
            if (numbers.contains(number)) {
                removeMultiplications(numbers, n, number);
                result.add(number);
            }
        }
        return result;
    }

    private void removeMultiplications(final Set<Integer> numbers, final int n, final Integer number) {
        for (int index = 0, multiplied = number * number; multiplied <= n; multiplied = number * (number + index++)) {
            numbers.remove(multiplied);
        }
    }

    public List<Integer> collectWithBitSet(final int n) {
        return collectPrimes(initNumbersWithBitSet(n), n);
    }

    private BitSet initNumbersWithBitSet(final int n) {
        BitSet numbers = new BitSet();
        for (int index = FIRST_PRIME; index <= n; index++) {
            numbers.set(index);
        }
        return numbers;
    }

    private List<Integer> collectPrimes(final BitSet numbers, final int n) {
        List<Integer> result = new ArrayList<>(List.of(ONE));
        for (int number = 2; number <= n; number++) {
            if (numbers.get(number)) {
                removeMultiplications(numbers, n, number);
                result.add(number);
            }
        }
        return result;
    }

    private void removeMultiplications(final BitSet numbers, final int n, final Integer number) {
        for (int index = 0, multiplied = number * number; multiplied <= n; multiplied = number * (number + index++)) {
            numbers.set(multiplied, false);
        }
    }

    public static void main(String[] args) {
        int n = 10000;
        PrimeNumberCollector primeNumberCollector = new PrimeNumberCollector();
        System.out.println(MessageFormat.format("Primes until {0} calculated with hashSet:", n));
        System.out.println(getPrimes(n, primeNumberCollector::collect));
        System.out.println(MessageFormat.format("Primes until {0} calculated with bitSet:", n));
        System.out.println(getPrimes(n, primeNumberCollector::collectWithBitSet));
    }

    private static List<Integer> getPrimes(final int n, final IntFunction<List<Integer>> collector) {
        long start = System.nanoTime();
        List<Integer> result = collector.apply(n);
        long end = System.nanoTime();
        System.out.println(MessageFormat.format("It took: {0} milliseconds.", (end - start) / 1000));
        return result;
    }

}
