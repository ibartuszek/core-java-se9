package tutorial.ch10.exercise16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 16
 * Repeat the preceding exercise, using parallel streams. None of the stream
 * operations should have any side effects.
 */
public class WordFrequencyProvider {

    private Map<String, Long> provideTopTen(final Path directory) {
        return getFiles(directory)
            .parallel()
            .flatMap(this::getWordsOfFile)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .parallel()
            .sorted((first, second) -> second.getValue().compareTo(first.getValue()))
            .limit(10)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private Stream<Path> getFiles(Path directory) {
        Stream<Path> result = Stream.of();
        try {
            result = Files.list(directory)
                .parallel()
                .flatMap(path -> path.toFile().isDirectory() ? getFiles(path) : Stream.of(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private Stream<String> getWordsOfFile(final Path path) {
        Stream<String> result = Stream.of();
        try {
            result = Files.lines(path)
                .flatMap(line -> Stream.of(line.split("\\PL+")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new WordFrequencyProvider().provideTopTen(Path.of("src/main/java/tutorial")));
    }

}
