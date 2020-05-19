package tutorial.ch08.exercise07;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tutorial.ch08.exercise06.WordTester;

/**
 * 07
 * Turning a file into a stream of tokens, list the first 100 tokens that are words
 * in the sense of the preceding exercise. Read the file again and list the 10 most
 * frequent words, ignoring letter case.
 */
public class FrequentWordFinder {

    private final String fileName;
    private final WordTester wordTester;

    public FrequentWordFinder(final String fileName) {
        this.fileName = fileName;
        this.wordTester = new WordTester();
    }

    public List<String> getFirstTokens(final int number) throws IOException {
        List<String> words;
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            words = processLines(lines)
                .limit(number)
                .collect(Collectors.toList());
        }
        return words;
    }

    public List<String> getFrequentWords(final int number) throws IOException {
        List<String> words;
        try (Stream<String> lines = Files.lines(Path.of(fileName))) {
            words = processLines(lines)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .limit(number)
                .collect(Collectors.toList());
        }
        return words;
    }

    private Stream<String> processLines(final Stream<String> lines) throws IOException {
        return lines.map(line -> Arrays.asList(line.split("\\PL+")))
            .flatMap(Collection::stream)
            .map(String::trim)
            .filter(wordTester::test);
    }

    public static void main(String[] args) throws IOException {
        FrequentWordFinder frequentWordFinder = new FrequentWordFinder("src/main/java/tutorial/ch08/war_and_peace");
        List<String> firstTokens = frequentWordFinder.getFirstTokens(100);
        List<String> frequentWords = frequentWordFinder.getFrequentWords(10);
        System.out.println(firstTokens);
        System.out.println(frequentWords);
    }

}
