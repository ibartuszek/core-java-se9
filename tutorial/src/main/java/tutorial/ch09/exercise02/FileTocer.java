package tutorial.ch09.exercise02;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 02
 * Write a program that reads a text file and produces a file with the same name
 * but extension .toc, containing an alphabetized list of all words in the input
 * file together with a list of line numbers in which each word occurs. Assume
 * that the file's encoding is UTF-8.
 */
public class FileTocer {

    public static void createToc(final Path path) throws IOException {
        List<String> newLines = getWordMap(path).entrySet()
            .stream()
            .map(Object::toString)
            .sorted(String::compareTo)
            .collect(Collectors.toList());
        PrintWriter printWriter = new PrintWriter(Path.of(path.getFileName().toString() + ".toc").toFile());
        newLines.forEach(printWriter::println);
        printWriter.flush();
    }

    private static Map<String, List<Long>> getWordMap(final Path path) throws IOException {
        Map<String, List<Long>> map = new HashMap<>();
        String[] lines = Files.readString(path, StandardCharsets.UTF_8).split("\\n");
        for (int lineNumber = 0; lineNumber < lines.length; lineNumber++) {
            for (String word : lines[lineNumber].split("\\s")) {
                if (word.contains(",") || word.contains(".")) {
                    word = word.substring(0, word.length() - 2);
                }
                if (map.containsKey(word)) {
                    map.get(word).add((long) lineNumber);
                } else {
                    map.put(word, new ArrayList<>(List.of((long) lineNumber)));
                }
            }
        }
        return map;
    }

    public static void main(String[] args) throws IOException {
        try {
            Files.delete(Paths.get("example.toc"));
        } catch (NoSuchFileException e) {
            System.out.println("File does not exists yet...");
        }
        FileTocer.createToc(Path.of("src/main/java/tutorial/ch09/example"));
    }

}
