package tutorial.ch01.sec01.exercises;

import utils.IntegerReader;

/**
 * Write a program that reads an integer and prints it in binary, octal, and
 * hexadecimal. Print the reciprocal as a hexadecimal floating-point number.
 */
public class IntegerPrinterExercise {

    public static void main(String[] args) {
        int number = new IntegerReader().readInteger();
        double reciprocal = 1.0d / number;
        System.out.printf("%d in binary form: %s\n", number, Integer.toString(number, 2));
        System.out.printf("%d in octal form: %o\n", number, number);
        System.out.printf("%d in hexadecimal form: %x\n", number, number);
        System.out.printf("%d reciprocal as a hexadecimal: %a\n", number, reciprocal);
    }

}
