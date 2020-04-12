package tutorial.ch01.exercises;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Write a program that reads in two integers between 0 and 4294967295, stores
 * them in int variables, and computes and displays their unsigned sum,
 * difference, product, quotient, and remainder. Do not convert them to long
 * values.
 */
public class LargeIntegersWithOverFlowExercise {

    private static long MAX_INPUT = 4294967295L;

    public static void main(String[] args) {
        int firstNumber = readAsUnsignedInteger();
        int secondNumber = readAsUnsignedInteger();
        System.out.printf("Difference: %d\n", transformToLong(firstNumber) - transformToLong(secondNumber));
        System.out.printf("Product: %d\n",
            BigInteger.valueOf(transformToLong(firstNumber)).multiply(BigInteger.valueOf(transformToLong(secondNumber))));
        System.out.printf("Quotient: %d\n", transformToLong(firstNumber) / transformToLong(secondNumber));
        System.out.printf("Reminder: %d\n", transformToLong(firstNumber) % transformToLong(secondNumber));
    }

    private static int readAsUnsignedInteger() {
        long number = 0;
        Scanner in = new Scanner(System.in);
        NumberFormatException exception;
        do {
            exception = null;
            System.out.printf("Please write a number: (between 0 and %d)\n", MAX_INPUT);
            try {
                number = Long.parseLong(in.nextLine());
            } catch (NumberFormatException e) {
                exception = e;
            }
        } while (exception != null);
        return (int) number;
    }

    private static long transformToLong(int unsignedInteger) {
        // long maxInput = (long) Integer.MAX_VALUE * 2 + 1; // = 4294967295 = -1
        return unsignedInteger >= 0 ? unsignedInteger : + (long) Integer.MAX_VALUE * 2 - unsignedInteger;
    }

}
