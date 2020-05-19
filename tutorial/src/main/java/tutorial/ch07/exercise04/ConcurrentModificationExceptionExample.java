package tutorial.ch07.exercise04;

import java.text.MessageFormat;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

/**
 * 04
 * Produce a situation that yields a
 * ConcurrentModificationException. What can you do to avoid it?
 */
public class ConcurrentModificationExceptionExample {

    public static void main(String[] args) throws InterruptedException {
        Set<Integer> numbers = new HashSet<>();
        init(numbers);
        modifyConcurrently(numbers, "HashSet");
        Set<Integer> concurrentNumbers = new ConcurrentSkipListSet<>();
        init(concurrentNumbers);
        modifyConcurrently(concurrentNumbers, "ConcurrentSkipListSet");
    }

    private static void init(final Set<Integer> numbers) {
        for (int index = 1; index <= 1000; index++) {
            numbers.add(index);
        }
    }

    private static void modifyConcurrently(final Set<Integer> numbers, final String message) throws InterruptedException {
        Thread t1 = new Thread(() -> removeDividableElements(numbers, 2));
        Thread t2 = new Thread(() -> removeDividableElements(numbers, 3));
        try {
            t1.start();
            t2.start();
        } catch (ConcurrentModificationException e) {
            e.printStackTrace();
        }
        t1.join();
        t2.join();
        System.out.println(MessageFormat.format("The result with {0}: {1}", message, numbers));
    }

    private static void removeDividableElements(final Set<Integer> numbers, final int divider) {
        numbers.removeIf(integer -> integer % divider == 0);
    }

}
