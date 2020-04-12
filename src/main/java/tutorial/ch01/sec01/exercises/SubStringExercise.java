package tutorial.ch01.sec01.exercises;

import java.util.Scanner;

/**
 * Write a program that reads a string and prints all of its nonempty substrings
 */
public class SubStringExercise {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        for (int i = 1; i <= line.length(); i++) {
            for (int j = 0; j <= line.length() - i; j++) {
                System.out.println(line.substring(j, j + i));
            }
        }
    }

}
