package tutorial.ch01.exercises;

import java.math.BigInteger;

/**
 * Write a program that computes the factorial n! = 1 × 2 × ... × n, using
 * BigInteger. Compute the factorial of 1000
 */
public class FactorialExercise {

    private static final int NUMBER = 5;

    public static void main(String[] args) {
        System.out.printf("Factorial of %d is: %d", NUMBER, calculateFactorial(NUMBER));
    }

    private static BigInteger calculateFactorial(final int number) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= number; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

}
