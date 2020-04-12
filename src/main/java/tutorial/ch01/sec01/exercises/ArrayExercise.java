package tutorial.ch01.sec01.exercises;

import java.util.Arrays;
import java.util.Random;

/**
 * Write a program that prints a lottery combination, picking six distinct
 * numbers between 1 and 49. To pick six distinct numbers, start with an array
 * list filled with 1...49. Pick a random index and remove the element. Repeat
 * six times. Print the result in sorted order
 */
public class ArrayExercise {

    public static void main(String[] args) {
        int[] numbers = init();
        int[] result = new int[6];
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            result[i] = pullFromNumbers(numbers, random);
        }
        Arrays.sort(result);
        System.out.printf("The six numbers: %s", Arrays.toString(result));
    }

    private static int[] init() {
        int[] numbers = new int[49];
        for (int i = 1; i <= 49; i++) {
            numbers[i - 1] = i;
        }
        return numbers;
    }

    private static int pullFromNumbers(final int[] numbers, final Random random) {
        int result = 0;
        while (result == 0) {
            int index = random.nextInt(48) + 1;
            result = numbers[index];
            numbers[index] = 0;
        }
        return result;
    }

}
