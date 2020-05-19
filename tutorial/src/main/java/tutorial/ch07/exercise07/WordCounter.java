package tutorial.ch07.exercise07;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * 07
 * Write a program that reads all words in a file and prints out how often each
 * word occurred. Use a TreeMap<String, Integer>
 */
public class WordCounter {

    private static final String SEPARATOR = " ";

    public static void main(String[] args) {
        TreeMap<String, Integer> map = new TreeMap<>(String::compareTo);
        try (Scanner scanner = new Scanner(new File("src/main/java/tutorial/ch07/exercise07/example"))) {
            while (scanner.hasNextLine()) {
                String[] wordsOfTheLine = scanner.nextLine().split(SEPARATOR);
                for (String word : wordsOfTheLine) {
                    map.merge(word, 1, Integer::sum);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(map);
    }

}
