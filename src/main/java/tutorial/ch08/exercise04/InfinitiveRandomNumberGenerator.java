package tutorial.ch08.exercise04;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 04
 * Using Stream.iterate, make an infinite stream of random numbers—not
 * by calling Math.random but by directly implementing a linear congruential
 * generator. In such a generator, you start with x0 = seed and then produce xn + 1
 * = (a xn + c) % m, for appropriate values of a, c, and m. You should implement
 * a method with parameters a, c, m, and seed that yields a Stream<Long>.
 * Try out a = 25214903917, c = 11, and m = 2^48
 */
public class InfinitiveRandomNumberGenerator {

    private final long a;
    private final long c;
    private final long m;

    public InfinitiveRandomNumberGenerator(final long a, final long c, final long m) {
        this.a = a;
        this.c = c;
        this.m = m;
    }

    public Stream<Long> generateStream() {
        return Stream.iterate(1L, number -> number + 1)
            .map(this::next);
    }

    private long next(final long x) {
        return (a * x + c) % m;
    }

    public static void main(String[] args) {
        InfinitiveRandomNumberGenerator generator = new InfinitiveRandomNumberGenerator(25214903917L, 11, (long) Math.pow(2, 48));
        System.out.println(
            generator.generateStream().limit(5)
            .collect(Collectors.toList())
        );
    }
}
