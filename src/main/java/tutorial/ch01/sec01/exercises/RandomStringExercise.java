package tutorial.ch01.sec01.exercises;

import java.util.Random;

/**
 * Write a program that produces a random string of letters and digits by
 * generating a random long value and printing it in base 36.
 */
public class RandomStringExercise {

    public static void main(String[] args) {
        Random random = new Random();
        long randomValue = random.nextLong();
        System.out.printf("%d: %s\n", randomValue, Long.toString(randomValue, 36));
    }

}
