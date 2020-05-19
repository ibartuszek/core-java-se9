package tutorial.ch10.exercise18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

import tutorial.ch10.exercise17.AbstractWordCounterWithSharedCounter;

/**
 * 17
 * Write a program that walks a directory tree and generates a thread for each
 * file. In the threads, count the number of words in the files and, without using
 * locks, update a shared counter that is declared as
 * public static long count = 0;
 * Run the program multiple times. What happens? Why?
 * 18
 * Fix the program of the preceding exercise with using a lock.
 */
public class WordCounterWithSharedCounter extends AbstractWordCounterWithSharedCounter {

    public static long count = 0;
    public static Lock lock = new ReentrantLock();

    public long provide(final Path directory) throws InterruptedException {
        invoke(directory, this::createRunnableWithLongCounterWithLock);
        return count;
    }

    private Callable<Void> createRunnableWithLongCounterWithLock(final Path file) {
        return () -> {
            try {
                Files.lines(file).flatMap(line -> Stream.of(line.split("\\PL+")))
                    .forEach(word -> {
                        increment();
                    });
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private void increment() {
        lock.lock();
        try {
            count++;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Count with shared static long counter with lock:");
        System.out.println(new WordCounterWithSharedCounter().provide(Path.of("src/main/java/tutorial")));
    }

}
