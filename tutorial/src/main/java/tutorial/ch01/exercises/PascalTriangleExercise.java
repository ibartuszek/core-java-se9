package tutorial.ch01.exercises;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorial.utils.IntegerReader;

/**
 * Write a program that stores Pascal's triangle up to a given n in an
 * ArrayList<ArrayList<Integer>>
 */
public class PascalTriangleExercise {

    public static void main(String[] args) {
        int n = readTheDimension();
        ArrayList<ArrayList<Integer>> pascalTriangle = createPascalTriangle(n);
        System.out.printf("The %d dimension pascal triangle:\n\n", n);
        for(ArrayList<Integer> row : pascalTriangle) {
            System.out.println(row);
        }
    }

    private static int readTheDimension() {
        System.out.println("Write the dimension of the triangle: ");
        return new IntegerReader().readInteger();
    }

    private static ArrayList<ArrayList<Integer>> createPascalTriangle(final int n) {
        ArrayList<ArrayList<Integer>> pascalTriangle = new ArrayList<>(n);
        if (n > 0) {
            pascalTriangle.add(new ArrayList<>(List.of(1)));
        }
        if (n > 1) {
            pascalTriangle.add(new ArrayList<>(List.of(1, 1)));
        }
        for (int index = 2; index <= n; index++) {
            pascalTriangle.add(createPascalTriangleLine(index, pascalTriangle.get(index - 1)));
        }
        return pascalTriangle;
    }

    private static ArrayList<Integer> createPascalTriangleLine(final int rowIndex,
        final ArrayList<Integer> previousRow) {
        Integer[] row = new Integer[rowIndex + 1];
        row[0] = 1;
        row[rowIndex] = 1;
        for (int index = 1; index < rowIndex; index++) {
            row[index] = previousRow.get(index - 1) + previousRow.get(index);
        }
        return new ArrayList<>(Arrays.asList(row));
    }

}
