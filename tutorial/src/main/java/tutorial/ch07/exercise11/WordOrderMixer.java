package tutorial.ch07.exercise11;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * 11
 * Write a program that reads a sentence into an array list. Then, using
 * Collections.shuffle, shuffle all but the first and last word, without
 * copying the words into another collection
 * 12
 * Using Collections.shuffle, write a program that reads a sentence,
 * shuffles the words, and prints the result. Fix the capitalization of the initial
 * word and the punctuation of the last word (before and after the shuffle). Hint:
 * Don't shuffle the words.
 */
public class WordOrderMixer {

    public ArrayList<String> shuffleWithOutFirstAndLastWord(final String line) {
        ArrayList<String> result = Arrays.stream(line.split(" "))
            .collect(Collectors.toCollection(ArrayList::new));
        Collections.shuffle(result.subList(1, result.size() - 2));
        return result;
    }

    private ArrayList<String> shuffleWithCapitalizationAndPunctuation(final String line) {
        String lineWithoutPunctuation = line.substring(0, line.length() - 2);
        ArrayList<String> result = Arrays.stream(lineWithoutPunctuation.split(" "))
            .map(String::toLowerCase)
            .collect(Collectors.toCollection(ArrayList::new));
        capitalizeFirstWord(result);
        result.set(result.size() - 1, result.get(result.size() - 1) + ".");
        return result;
    }

    private void capitalizeFirstWord(final ArrayList<String> result) {
        String word = result.get(0);
        word = (word.charAt(0) + "").toUpperCase() + word.substring(1, word.length() -1);
        result.set(0, word);
    }

    public static void main(String[] args) {
//        String line = "Test line of my task blah bla blah.";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Write a sentence:");
        String line = scanner.nextLine();
        WordOrderMixer wordOrderMixer = new WordOrderMixer();
        System.out.println(wordOrderMixer.shuffleWithOutFirstAndLastWord(line));
        System.out.println(wordOrderMixer.shuffleWithCapitalizationAndPunctuation(line));
    }

}
