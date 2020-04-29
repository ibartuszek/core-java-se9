package tutorial.ch08.exercise11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 11
 * Given a finite stream of strings, find all strings of maximum length
 */
public class MaximumLengthStringsProvider {

    public Set<String> provide(final String source) {
        return Arrays.stream(source.split("\\PL+"))
            .parallel()
            .map(String::trim)
            .collect(Collectors.groupingBy(String::length, Collectors.toSet()))
            .entrySet().stream()
            .max(Comparator.comparingInt(Map.Entry::getKey))
            .map(Map.Entry::getValue)
            .orElseGet(HashSet::new);
    }

    public static void main(String[] args) throws IOException {
        Set<String> result = new MaximumLengthStringsProvider().provide(
            Files.lines(Path.of("src/main/java/tutorial/ch08/example"))
                .parallel()
                .collect(Collectors.joining())
        );
        System.out.println(result);
    }



}
