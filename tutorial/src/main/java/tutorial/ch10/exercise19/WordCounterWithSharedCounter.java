package tutorial.ch10.exercise19;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.Stream;

import tutorial.ch10.exercise17.AbstractWordCounterWithSharedCounter;

/**
 * 17
 * Write a program that walks a directory tree and generates a thread for each
 * file. In the threads, count the number of words in the files and, without using
 * locks, update a shared counter that is declared as
 * public static long count = 0;
 * Run the program multiple times. What happens? Why?
 * 19
 * Fix the program of the preceding exercise with using a LongAdder
 */
public class WordCounterWithSharedCounter extends AbstractWordCounterWithSharedCounter {


    public static LongAdder longAdder = new LongAdder();

    public long provide(final Path directory) throws InterruptedException {
        invoke(directory, this::createRunnableWithLongAdder);
        return longAdder.longValue();
    }

    private Callable<Void> createRunnableWithLongAdder(final Path file) {
        return () -> {
            try {
                Files.lines(file).flatMap(line -> Stream.of(line.split("\\PL+")))
                    .forEach(word -> longAdder.add(1));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Count with shared static longadder to count:");
        System.out.println(new WordCounterWithSharedCounter().provide(Path.of("src/main/java/tutorial")));
    }

}
