package tutorial.ch07.exercise08;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

/**
 * Write a program that reads all words in a file and prints out on which line(s)
 * each of them occurred. Use a map from strings to sets.
 */
public class OccurancyPrinter {

    private static final String SEPARATOR = " ";

    public static void main(String[] args) {
        TreeMap<String, Set<Integer>> map = new TreeMap<>(String::compareTo);
        try (Scanner scanner = new Scanner(new File("src/main/java/tutorial/ch07/exercise07/example"))) {
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String[] wordsOfTheLine = scanner.nextLine().split(SEPARATOR);
                for (String word : wordsOfTheLine) {
                    if (map.containsKey(word)) {
                        map.get(word).add(lineNumber);
                    } else {
                        map.put(word, new HashSet<>(Set.of(lineNumber)));
                    }
                }
                lineNumber++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }

}
