package tutorial.ch09.exercise04;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

import tutorial.utils.measure.Measurer;

/**
 * 04
 * Using a Scanner is convenient, but it is a bit slower than using a
 * BufferedReader. Read in a long file a line at a time, counting the number
 * of input lines, with (a) a Scanner and hasNextLine/nextLine, (b) a
 * BufferedReader and readLine, (c) a BufferedReader and lines.
 * Which is the fastest? The most convenient?
 */
public class FileLineCounter {

    public int countWithScanner(final Path path) {
        int counter = 0;
        try (Scanner scanner = new Scanner(path)) {
            while (scanner.hasNextLine()) {
                counter++;
                scanner.nextLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public int countWithBufferedReaderReadLine(final Path path) {
        int counter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            while (bufferedReader.readLine() != null) {
                counter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public int countWithBufferedReaderLines(final Path path) {
        int counter = 0;
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            counter = (int) bufferedReader.lines().count();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return counter;
    }

    public static void main(String[] args) {
        Measurer measurer = new Measurer();
        FileLineCounter counter = new FileLineCounter();
        Path path = Path.of("src/main/java/tutorial/ch09/war_and_peace");
        System.out.println("Long text with scanner readLine method: " + measurer.measure(counter::countWithScanner, path));
        System.out.println("Long text with BufferedReader readLine method: " + measurer.measure(counter::countWithBufferedReaderReadLine, path));
        System.out.println("Long text with BufferedReader lines method: " + measurer.measure(counter::countWithBufferedReaderLines, path));
    }

}
