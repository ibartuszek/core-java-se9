package tutorial.ch10.exercise10;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.LongAccumulator;

import tutorial.utils.measure.Measurer;

/**
 * 10
 * Use a LongAccumulator to compute the maximum or minimum of the
 * accumulated elements.
 */
public class MixMaxProvider {

    public long min(final List<Long> longList) {
        return get(longList, new LongAccumulator(Long::min, 0));
    }

    public long max(final List<Long> longList) {
        return get(longList, new LongAccumulator(Long::max, 0));
    }

    private long get(final List<Long> longList, final LongAccumulator longAccumulator) {
        longList.parallelStream()
            .forEach(longAccumulator::accumulate);
        return longAccumulator.get();
    }

    public long minWithoutAccumulator(final List<Long> longList) {
        return longList.parallelStream().min(Long::compareTo).get();
    }

    public long maxWithoutAccumulator(final List<Long> longList) {
        return longList.parallelStream().max(Long::compareTo).get();
    }

    public static void main(String[] args) {
        MixMaxProvider provider = new MixMaxProvider();
        Measurer measurer = new Measurer();
        List<Long> longList = createLongList();
        System.out.println("Min with parallel stream: " + measurer.measure(provider::minWithoutAccumulator, longList));
        System.out.println("Min with longAccumulator: " + measurer.measure(provider::min, longList));
        System.out.println("Max with parallel stream: " + measurer.measure(provider::maxWithoutAccumulator, longList));
        System.out.println("Max with longAccumulator: " + measurer.measure(provider::max, longList));
    }

    private static List<Long> createLongList() {
        List<Long> longList = new ArrayList<>();
        Random random = new Random();
        for (int index = 0; index < 50000000; index++) {
            longList.add(random.nextLong());
        }
        return longList;
    }

}
