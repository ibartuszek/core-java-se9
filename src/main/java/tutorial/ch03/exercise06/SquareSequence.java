package tutorial.ch03.exercise06;

import java.math.BigInteger;

/**
 * The SquareSequence class doesn't actually deliver an infinite sequence of
 * squares due to integer overflow. Specifically, how does it behave? Fix the
 * problem by defining a Sequence<T> interface and a SquareSequence
 * class that implements Sequence<BigInteger>.
 */
public class SquareSequence implements Sequence<BigInteger> {

    private long root = 1L;

    private SquareSequence() {
    }

    public static SquareSequence of() {
        return new SquareSequence();
    }

    @Override
    public boolean hasNext() {
        return root < Long.MAX_VALUE;
    }

    @Override
    public BigInteger next() {
        return BigInteger.valueOf(root).multiply(BigInteger.valueOf(root++));
    }

    public static void main(String[] args) {
        SquareSequence squareSequence = SquareSequence.of();
        BigInteger result = squareSequence.next();
        while(result.compareTo(BigInteger.valueOf(100)) < 0) {
            System.out.printf("%3d\n", result);
            result = squareSequence.next();
        }
    }
}
