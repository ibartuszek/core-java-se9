package utils;

import java.util.Scanner;

public class IntegerMatrixReader {

    private static final String SEPARATOR = " ";
    private Scanner scanner;

    public IntegerMatrixReader() {
        this(new Scanner(System.in));
    }

    public IntegerMatrixReader(final Scanner scanner) {
        this.scanner = scanner;
    }

    public int[][] readMatrixFromConsole() {
        int[][] testMatrix = initTestMatrix();
        int dimension = testMatrix.length;
        boolean validRow;
        for(int rowIndex = 1; rowIndex < dimension; rowIndex++) {
            int[] row;
            do {
                row = readNextRow();
                validRow = row.length == dimension;
                if (!validRow) {
                    System.out.println("The lines must contain the same number of integers!");
                }
            } while (!validRow);
            testMatrix[rowIndex] = row;
        }
        return testMatrix;
    }

    private int[][] initTestMatrix() {
        int[] row = readNextRow();
        int[][] testMatrix = new int[row.length][row.length];
        testMatrix[0] = row;
        return testMatrix;
    }

    private int[] readNextRow() {
        NumberFormatException exception;
        int[] row = new int[0];
        do {
            exception = null;
            try {
                String[] lineElements = scanner.nextLine().split(SEPARATOR);
                row = new int[lineElements.length];
                for(int i = 0; i < lineElements.length; i++) {
                    row[i] = Integer.parseInt(lineElements[i]);
                }
            } catch (NumberFormatException e) {
                System.out.println("The line contains invalid data!");
                exception = e;
            }
        } while (exception != null);
        return row;
    }

}
