package tutorial.ch08.exercise10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

/**
 * 10
 * Given a finite stream of strings, find the average string length.
 */
public class AverageLengthProvider {

    public OptionalDouble provide(final String source) {
        return Arrays.stream(source.split("\\PL+"))
            .parallel()
            .map(String::trim)
            .map(String::length)
            .mapToInt(Integer::intValue)
            .average();
    }

    public static void main(String[] args) throws IOException {
        OptionalDouble result = new AverageLengthProvider().provide(
            Files.lines(Path.of("src/main/java/tutorial/ch08/war_and_peace"))
            .parallel()
            .collect(Collectors.joining())
        );
        result.ifPresent(System.out::println);
    }

}
