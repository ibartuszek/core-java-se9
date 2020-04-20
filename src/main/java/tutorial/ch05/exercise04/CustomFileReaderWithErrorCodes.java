package tutorial.ch05.exercise04;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * 04
 * Repeat the preceding exercise, but don't use exceptions. Instead, have
 * readValues and sumOfValues return error codes of some kind.
 */
public class CustomFileReaderWithErrorCodes {

    private static final String SEPARATOR = ",";

    public int readValues(final String filename, final ArrayList<Double> arrayList) {
        int result = 0;
        try (FileInputStream inputStream = new FileInputStream(filename)) {
            Scanner scanner = new Scanner(inputStream);
            while (scanner.hasNextLine()) {
                Arrays.stream(scanner.nextLine().trim().split(SEPARATOR))
                    .map(Double::parseDouble)
                    .forEach(arrayList::add);
            }
        } catch (NumberFormatException e) {
            result = 1;
        } catch (IOException e) {
            result = 2;
        }
        return result;
    }

    public int sumOfValues(final String filename, final Holder holder) {
        ArrayList<Double> doubleList = new ArrayList<>();
        int response = readValues(filename, doubleList);
        if (response == 0) {
            holder.setResult(doubleList.stream().reduce(Double::sum).orElse(0.0D));
        }
        return response;
    }

    public static void main(String[] args) {
        CustomFileReaderWithErrorCodes fileReader = new CustomFileReaderWithErrorCodes();
        ArrayList<Double> result = new ArrayList<>();
        int response = getDoubles(fileReader, "exampl.csv", result);
        System.err.println(response);
        response = getDoubles(fileReader, "src/main/java/tutorial/ch05/exercise01/example_with_wrong_value.csv", result);
        System.err.println(response);
        response = getDoubles(fileReader, "src/main/java/tutorial/ch05/exercise01/example.csv", result);
        System.out.println(response);
        Holder sum = new Holder();
        System.out.println(getSum(fileReader, "src/main/java/tutorial/ch05/exercise01/example.csv", sum));
    }

    private static int getDoubles(final CustomFileReaderWithErrorCodes fileReader, final String fileName, final ArrayList<Double> doubleList) {
        int result = fileReader.readValues(fileName, doubleList);
        printError(result);
        return result;
    }

    private static int getSum(final CustomFileReaderWithErrorCodes fileReader, final String fileName, final Holder holder) {
        int response = fileReader.sumOfValues(fileName,holder);
        printError(response);
        return response;
    }

    private static void printError(final int result) {
        if (result == 1) {
            System.err.println("Error: file contains wrong format number(s)!");
        } else if (result == 2) {
            System.err.println("Error: file cannot be read!");
        }
    }

}

class Holder {

    double result;

    public double getResult() {
        return result;
    }

    public void setResult(final double result) {
        this.result = result;
    }
}
