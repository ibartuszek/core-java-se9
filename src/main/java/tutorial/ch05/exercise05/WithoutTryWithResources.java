package tutorial.ch05.exercise05;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Implement a method that contains the code with a Scanner and a
 * PrintWriter in Section 5.1.5, “The Try-with-Resources Statement” (page
 * 187). But don't use the try-with-resources statement. Instead, just use catch
 * clauses. Be sure to close both objects, provided they have been properly
 * constructed. You need to consider the following conditions:
 * • The Scanner constructor throws an exception.
 * • The PrintWriter constructor throws an exception.
 * • hasNext, next, or println throw an exception.
 * • out.close() throws an exception.
 * • in.close() throws an exception.
 */
public class WithoutTryWithResources {

    public void copy(final String inFile, final String outFile) {
        Scanner scanner;
        PrintWriter printWriter;
        try {
            scanner = new Scanner(new File(inFile));
            printWriter = new PrintWriter(new File(outFile));
            try {
                while (scanner.hasNextLine()) {
                    printWriter.println(scanner.nextLine());
                }
            } catch (NoSuchElementException e) {
                e.printStackTrace();
            } finally {
                scanner.close();
                printWriter.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new WithoutTryWithResources().copy("src/main/java/tutorial/ch05/exercise05/exampleInput",
            "src/main/java/tutorial/ch05/exercise05/exampleOutput");
        System.out.println("Copy is ready.");
    }


}
