package tutorial.ch10.exercise01;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

import tutorial.ch10.WordFinderUtility;

/**
 * 01
 * Using parallel streams, find all files in a directory that contain a given word.
 * How do you find just the first one? Are the files actually searched
 * concurrently?
 */
public class WordFinder {

    private WordFinderUtility wordFinderUtility = new WordFinderUtility();

    public List<Path> findWithParallelStream(final String word, final Path directory) {
        return wordFinderUtility.initFileList(directory).parallelStream()
            .filter(path -> contains(word, path))
            .collect(Collectors.toList());
    }

    private boolean contains(final String word, final Path path) {
        boolean result = false;
        try {
            result = Files.lines(path)
                .anyMatch(line -> line.contains(word));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        new WordFinder().findWithParallelStream("required", Path.of("src/main/java/tutorial")).forEach(System.out::println);
    }

}
