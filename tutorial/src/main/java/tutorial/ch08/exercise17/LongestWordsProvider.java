package tutorial.ch08.exercise17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

import tutorial.utils.measure.Measurer;

/**
 * 17
 * Find the 500 longest strings in War and Peace with a parallel stream. Is it any
 * faster than using a serial stream?
 */
public class LongestWordsProvider {

    public Set<String> provide(final String source, final long limit) {
        return Arrays.stream(source.split("\\PL+"))
            .map(String::trim)
            .collect(Collectors.groupingBy(String::length, Collectors.toSet()))
            .entrySet().stream()
            .sorted((first, second) -> second.getKey().compareTo(first.getKey()))
            .flatMap(entry -> entry.getValue().stream())
            .limit(limit)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public Set<String> provideWithParallel(final String source, final long limit) {
        return Arrays.stream(source.split("\\PL+"))
            .parallel()
            .map(String::trim)
            .collect(Collectors.groupingBy(String::length, Collectors.toSet()))
            .entrySet().stream()
            .sorted((first, second) -> second.getKey().compareTo(first.getKey()))
            .flatMap(entry -> entry.getValue().stream())
            .limit(limit)
            .collect(Collectors.toCollection(TreeSet::new));
    }

    public static void main(String[] args) throws IOException {
        String source = Files.lines(Path.of("src/main/java/tutorial/ch08/war_and_peace"))
            .parallel()
            .collect(Collectors.joining());
        long limit = 500;
        LongestWordsProvider provider = new LongestWordsProvider();
        Measurer measurer = new Measurer();
        System.out.println("Poviding with sequential execution: " + measurer.measure(provider::provide, source, limit));
        System.out.println("Providing with parallel execution: " + measurer.measure(provider::provideWithParallel, source, limit));
    }

}
