package tutorial.ch08.exercise16;

import java.math.BigInteger;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorial.utils.measure.Measurer;

/**
 * Find 500 prime numbers with 50 decimal digits, using a parallel stream of
 * BigInteger and the BigInter.isProbablePrime method. Is it any
 * faster than using a serial stream?
 */
public class BigPrimeFinder {

    private int minimumDigits;
    private long numberToFind;
    private Random random;

    public BigPrimeFinder(final int minimumDigits, final long numberToFind) {
        this.minimumDigits = minimumDigits;
        this.numberToFind = numberToFind;
        this.random = new Random();
    }

    public List<BigInteger> find() {
        return Stream.iterate(BigInteger.probablePrime(minimumDigits, random), number -> BigInteger.probablePrime(minimumDigits, random))
            .limit(numberToFind)
            .collect(Collectors.toList());
    }

    public List<BigInteger> findParallel() {
        return Stream.iterate(BigInteger.probablePrime(minimumDigits, random), number -> BigInteger.probablePrime(minimumDigits, random))
            .parallel()
            .limit(numberToFind)
            .collect(Collectors.toCollection(CopyOnWriteArrayList::new));
    }

    public static void main(String[] args) {
        BigPrimeFinder finder = new BigPrimeFinder(50, 500);
        Measurer measurer = new Measurer();
        System.out.println("Simple execution: " + measurer.measure(finder::find));
        System.out.println("Parallel execution: " + measurer.measure(finder::findParallel));
    }
}
