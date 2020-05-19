package tutorial.ch05.exercise01;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 01
 * Write a method public ArrayList<Double>
 * readValues(String filename) throws ... that reads a file
 * containing floating-point numbers. Throw appropriate exceptions if the file
 * could not be opened or if some of the inputs are not floating-point numbers.
 */
/**
 * 02
 * Write a method public double sumOfValues(String
 * filename) throws ... that calls the preceding method and returns the
 * sum of the values in the file. Propagate any exceptions to the caller.
 */
/**
 * 03
 * Write a program that calls the preceding method and prints the result. Catch
 * the exceptions and provide feedback to the user about any error conditions.
 */
public class CustomFileReader {

    private static final String SEPARATOR = ",";

    public ArrayList<Double> readValues(final String filename) throws IOException, NumberFormatException {
        ArrayList<Double> result = new ArrayList<>();
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                Arrays.stream(scanner.nextLine().trim().split(SEPARATOR))
                    .map(Double::parseDouble)
                    .forEach(result::add);
            }
        }
        return result;
    }

    public double sumOfValues(final String filename) throws IOException, NumberFormatException {
        ArrayList<Double> doubleList = readValues(filename);
        return doubleList.stream().reduce(Double::sum).orElse(0.0D);
    }

    public static void main(String[] args) {
        CustomFileReader fileReader = new CustomFileReader();
        List<Double> result = getDoubles(fileReader, "exampl.csv");
        System.err.println(result);
        result = getDoubles(fileReader, "src/main/java/tutorial/ch05/exercise01/example_with_wrong_value.csv");
        System.err.println(result);
        result = getDoubles(fileReader, "src/main/java/tutorial/ch05/exercise01/example.csv");
        System.out.println(result);
        System.out.println(getSum(fileReader, "src/main/java/tutorial/ch05/exercise01/example.csv"));
    }

    private static List<Double> getDoubles(final CustomFileReader fileReader, final String fileName) {
        ArrayList<Double> result = null;
        try {
            result = fileReader.readValues(fileName);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return result == null ? Collections.emptyList() : result;
    }

    private static double getSum(final CustomFileReader fileReader, final String fileName) {
        double sum = 0.0D;
        try {
            sum = fileReader.sumOfValues(fileName);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
        return sum;
    }

}
