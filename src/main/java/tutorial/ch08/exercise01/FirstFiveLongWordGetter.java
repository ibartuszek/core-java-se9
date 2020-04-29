package tutorial.ch08.exercise01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorial.utils.measure.Measurer;

/**
 * 01
 * Verify that asking for the first five long words does not call the filter
 * method once the fifth long word has been found. Simply log each method call.
 * 02
 * Measure the difference when counting long words with a
 * parallelStream instead of a stream. Call
 * System.currentTimeMillis before and after the call and print the
 * difference. Switch to a larger document (such as War and Peace) if you have a
 * fast computer.
 */
public class FirstFiveLongWordGetter {

    public List<String> get(final List<String> source, final int lengthInclusive) {
        return source.stream()
//            .peek(System.out::println)
            .sorted(Comparator.comparingInt(String::length))
            .dropWhile(s -> s.length() < lengthInclusive)
            .limit(5)
            .collect(Collectors.toList());
    }

    public List<String> getParallel(final List<String> source, final int lengthInclusive) {
        return source.parallelStream()
            .sorted(Comparator.comparingInt(String::length))
            .dropWhile(s -> s.length() < lengthInclusive)
            .limit(5)
            .collect(Collectors.toList());
    }

    public static void main(String[] args) throws IOException {
        Measurer measurer = new Measurer();
        FirstFiveLongWordGetter getter = new FirstFiveLongWordGetter();
        List<String> example = readExample("src/main/java/tutorial/ch08/example");
        List<String> warAndPiece = readExample("src/main/java/tutorial/ch08/war_and_peace");;
        System.out.println("Simple text with sequential execution: " + measurer.measure(getter::get, example, 5));
        System.out.println("Simple text with parallel execution: " + measurer.measure(getter::getParallel, example, 5));
        System.out.println("Long text with sequential execution: " + measurer.measure(getter::get, warAndPiece, 5));
        System.out.println("Long text with parallel execution: " + measurer.measure(getter::getParallel, warAndPiece, 5));
    }

    private static List<String> readExample(final String path) {
        List<String> example;
        try (Stream<String> lines = Files.lines(Path.of(path))) {
            example = lines.map(line -> Arrays.asList(line.split("\\PL+")))
                .flatMap(Collection::stream)
                .map(String::trim)
                .collect(Collectors.toList());

        } catch (IOException e) {
            e.printStackTrace();
            example = Collections.emptyList();
        }
        return example;
    }



}
