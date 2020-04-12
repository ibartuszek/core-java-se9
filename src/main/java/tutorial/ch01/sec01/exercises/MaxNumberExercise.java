package tutorial.ch01.sec01.exercises;

import utils.IntegerReader;

/**
 * Using only the conditional operator, write a program that reads three integers
 * and prints the largest. Repeat with Math.max.
 */
public class MaxNumberExercise {

    // private static final int DEFAULT_MAXIMUM = Integer.MIN_VALUE;

    public static void main(String[] args) {
        IntegerReader integerReader = new IntegerReader();
        int firstNumber = integerReader.readInteger();
        int secondNumber = integerReader.readInteger();
        int thirdNumber = integerReader.readInteger();
        System.out.printf("Maximum of the read numbers (with conditional operators): %d\n",
            getMaximumNumberWithConditions(firstNumber, secondNumber, thirdNumber));
        System.out.printf("Maximum of the read numbers (with Math.max): %d\n",
            getMaximumNumberWithMathMax(firstNumber, secondNumber, thirdNumber));
    }

//    private static int getMaximumNumber(final int defaultValue, final int... numbers) {
//        return Arrays.stream(numbers).max().orElse(defaultValue);
//    }

    private static int getMaximumNumberWithConditions(final int firstNumber, final int secondNumber,
        final int thirdNumber) {
        int result;
        if (firstNumber < secondNumber) {
          if (secondNumber < thirdNumber) {
              result = thirdNumber;
          } else {
              result = secondNumber;
          }
        } else {
            if (firstNumber < thirdNumber) {
                result = thirdNumber;
            } else {
                result = firstNumber;
            }
        }
        return result;
    }

    private static int getMaximumNumberWithMathMax(final int firstNumber, final int secondNumber,
        final int thirdNumber) {
        return Math.max(Math.max(firstNumber, secondNumber), thirdNumber);
    }

}
