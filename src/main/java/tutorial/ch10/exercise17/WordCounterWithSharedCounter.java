package tutorial.ch10.exercise17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.Callable;
import java.util.stream.Stream;

/**
 * 17
 * Write a program that walks a directory tree and generates a thread for each
 * file. In the threads, count the number of words in the files and, without using
 * locks, update a shared counter that is declared as
 * public static long count = 0;
 * Run the program multiple times. What happens? Why?
 */
public class WordCounterWithSharedCounter extends AbstractWordCounterWithSharedCounter {

    public static long count = 0;

    public long provide(final Path directory) throws InterruptedException {
        invoke(directory, this::createRunnableWithLongCounter);
        return count;
    }

    private Callable<Void> createRunnableWithLongCounter(final Path file) {
        return () -> {
            try {
                Files.lines(file).flatMap(line -> Stream.of(line.split("\\PL+")))
                    .forEach(word -> count++);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Count with shared static long counter:");
        System.out.println(new WordCounterWithSharedCounter().provide(Path.of("src/main/java/tutorial")));
    }

}
