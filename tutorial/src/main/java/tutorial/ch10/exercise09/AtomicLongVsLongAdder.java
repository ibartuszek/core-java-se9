package tutorial.ch10.exercise09;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorial.utils.measure.Measurer;

/**
 * 09
 * Generate 1,000 threads, each of which increments a counter 100,000 times.
 * Compare the performance of using AtomicLong versus LongAdder.
 */
public class AtomicLongVsLongAdder {

    public AtomicLong counterWithAtomicLong() {
        AtomicLong result = new AtomicLong();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        List<Future<Void>> futures = Stream.generate(() -> (Callable<Void>) () -> {
            result.incrementAndGet();
            return null;
        }).limit(100000)
            .map(executorService::submit)
            .collect(Collectors.toList());
        waitForResult(futures);
        executorService.shutdown();
        return result;
    }

    public LongAdder counterWithLongAdder() {
        LongAdder result = new LongAdder();
        ExecutorService executorService = Executors.newFixedThreadPool(1000);
        List<Future<Void>> futures = Stream.generate(() -> (Callable<Void>) () -> {
            result.add(1L);
            return null;
        }).limit(100000)
            .map(executorService::submit)
            .collect(Collectors.toList());
        waitForResult(futures);
        executorService.shutdown();
        return result;
    }

    private void waitForResult(final List<Future<Void>> futures) {
        try {
            boolean done = false;
            while (!done) {
                done = futures.stream()
                    .filter(future -> !future.isDone())
                    .findAny()
                    .isEmpty();
                Thread.sleep(100L);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Measurer measurer = new Measurer();
        AtomicLongVsLongAdder counter = new AtomicLongVsLongAdder();
        for (int index = 0; index < 5; index++) {
            System.out.println("Simple test with atomicLong: " + measurer.measure(counter::counterWithAtomicLong));
            Thread.sleep(2000L);
            System.out.println("Simple test with longAdder: " + measurer.measure(counter::counterWithLongAdder));
            Thread.sleep(2000L);
        }
    }

}
