package tutorial.ch08.exercise15;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 15
 * Write a call to reduce that can be used to compute the average of a
 * Stream<Double>. Why can't you simply compute the sum and divide by
 * count()?
 */
public class AverageProvider {

    public double provide(final Stream<Double> source) {
        return source
            .parallel()
            .reduce(Holder.init(), Holder::accept, Holder::combine)
            .average();
    }

    static class Holder {

        private static int counter2 = 0;
        private final int counter;
        private final Double sum;

        public Holder(final int counter, final Double sum) {
            counter2++;
            this.counter = counter;
            this.sum = sum;
        }

        public static Holder init() {
            return new Holder(0, 0.0D);
        }

        public Holder accept(final Double value) {
            return new Holder(  counter + 1, sum + value);
        }

        public Holder combine(final Holder other) {
            return new Holder(counter + other.counter, sum + other.sum);
        }

        public double average() {
            return sum / (double) counter;
        }

    }

    public static void main(String[] args) {
        List<Double> list = Stream.iterate(1.0D, n -> 1.0)
            .limit(100000)
            .collect(Collectors.toList());
        System.out.println(new AverageProvider().provide(list.stream()));
    }

}
