package tutorial.ch10.exercise04;

import java.text.MessageFormat;
import java.util.Arrays;

/**
 * 04
 * In this exercise, you will use the parallelPrefix method to parallelize
 * the computation of Fibonacci numbers. We use the fact that the nth Fibonacci
 * number is the top left coefficient of Fn, where * . Make an array
 * filled with 2 × 2 matrices. Define a Matrix class with a multiplication
 * method, use parallelSetAll to make an array of matrices, and use
 * parallelPrefix to multiply them.
 */
public class FibonacciNumberProvider {

    public long provide(final int number) {
        return calculateFibonacciMatrix(number).first;
    }

    private Matrix calculateFibonacciMatrix(final int number) {
        Matrix[] array = new Matrix[number];
        Arrays.parallelSetAll(array, this::init);
        Arrays.parallelPrefix(array, Matrix::multiply);
        return array[number - 1];
    }

    private Matrix init(int number) {
        return new Matrix();
    }

    class Matrix {
        /**
         * It represents a 2x2 matrix where:
         * | first, second |
         * | third, forth  |
         * are the values of the matrix.
         */

        private final long first;
        private final long second;
        private final long third;
        private final long forth;

        private Matrix(final long first, final long second, final long third, final long forth) {
            this.first = first;
            this.second = second;
            this.third = third;
            this.forth = forth;
        }

        Matrix() {
            this(1, 1, 1, 0);
        }

        Matrix multiply(final Matrix other) {
            return new Matrix(first * other.first + second * other.third,
                first * other.second + second * other.forth,
                third * other.first + forth * other.third,
                third * other.second + forth * other.forth);
        }

        @Override
        public String toString() {
            return MessageFormat.format("| {0}, {1} |\n| {2}, {3} |", first, second, third, forth);
        }

    }

    public static void main(String[] args) {
        int max = 20;
        FibonacciNumberProvider provider = new FibonacciNumberProvider();
        for (int index = 1; index <= max; index++) {
            System.out.print(provider.provide(index) + ", ");
        }
        System.out.println("...");
    }

}
