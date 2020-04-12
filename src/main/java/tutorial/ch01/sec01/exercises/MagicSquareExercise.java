package tutorial.ch01.sec01.exercises;

import java.util.Arrays;

import utils.IntegerMatrixReader;

/**
 * Write a program that reads a two-dimensional array of integers and
 * determines whether it is a magic square (that is, whether the sum of all rows,
 * all columns, and the diagonals is the same). Accept lines of input that you
 * break up into individual integers, and stop when the user enters a blank line
 */

// Square -> the dimensions of the matrix are equal => n=m
// I will read the input when the square is ready to use.
public class MagicSquareExercise {

    public static void main(String[] args) {
        int[][] testMatrix;
        if (args.length > 0 && "test".equals(args[0])) {
            testMatrix = new int[][] {
                { 16, 3, 2, 13 },
                { 5, 10, 11, 8 },
                { 9, 6, 7, 12 },
                { 4, 15, 14, 1 }
            };
        } else {
            testMatrix = new IntegerMatrixReader().readMatrixFromConsole();
        }

        if (testMatrix(testMatrix)) {
            System.out.println("The given matrix is a magic square.");
        } else {
            System.out.println("The given matrix is not a magic square.");
        }
    }

    private static boolean testMatrix(final int[][] matrix) {
        int sum = sumRow(matrix[0]);
        return testRows(matrix, sum) && testColumns(matrix, sum) && testDiagonals(matrix, sum);
    }

    private static boolean testRows(final int[][] magicSquare, final int sum) {
        boolean isMagicSquare = true;
        for(int i = 1; i < magicSquare.length && isMagicSquare; i++) {
            isMagicSquare = sum == sumRow(magicSquare[i]);
        }
        return isMagicSquare;
    }

    private static int sumRow(final int[] numbers) {
        return Arrays.stream(numbers).sum();
    }

    private static boolean testColumns(final int[][] matrix, final int sum) {
        boolean isMagicSquare = true;
        for (int j = 0; j < matrix[0].length && isMagicSquare; j++) {
            int tempSum = 0;
            for (int i = 0; i < matrix.length; i++) {
                tempSum += matrix[i][j];
            }
            isMagicSquare = sum == tempSum;
        }
        return isMagicSquare;
    }

    private static boolean testDiagonals(final int[][] matrix, final int sum) {
        return testNormalDiagonal(matrix, sum) && testReverseDiagonal(matrix, sum);
    }

    private static boolean testNormalDiagonal(final int[][] matrix, final int sum) {
        int tempSum = 0;
        for(int i = 0, j = 0; i < matrix.length && j < matrix[i].length; i++, j++) {
            tempSum += matrix[i][j];
        }
        return sum == tempSum;
    }

    private static boolean testReverseDiagonal(final int[][] matrix, final int sum) {
        int tempSum = 0;
        for (int i = 0, j = matrix[i].length - 1; i < matrix.length && j >= 0; i++, j--) {
            tempSum += matrix[i][j];
        }
        return sum == tempSum;
    }

}
