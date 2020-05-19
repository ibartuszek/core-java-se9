package tutorial.ch03.exercise16;

import java.util.Random;

import com.sun.source.tree.BreakTree;

/**
 * Implement the RandomSequence in Section 3.9.1, “Local Classes” (page
 * 129) as a nested class, outside the randomInts method.
 */

//    private static Random generator = new Random();
//    public static IntSequence randomInts(int low, int high) {
//        class RandomSequence implements IntSequence {
//            public int next() { return low + generator.nextInt(high - low + 1); }
//            public boolean hasNext() { return true; }
//        }
//        return new RandomSequence();
//    }

public class RandomSequence implements IntSequence {

    private final int low;
    private final int high;
    private final Random generator;

    private RandomSequence(final int low, final int high) {
        this.low = low;
        this.high = high;
        this.generator = new Random();
    }

    public static RandomSequence of(final int low, final int high) {
        return new RandomSequence(low, high);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public Integer next() {
        return generator.nextInt(high - low + 1) + low;
    }

    public static void main(String[] args) {
        RandomSequence randomSequence = RandomSequence.of(1, 10);
        for (int i = 0; i < 10; i++) {
            System.out.printf("%4d", randomSequence.next());
        }
    }
}