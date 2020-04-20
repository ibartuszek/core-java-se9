package tutorial.ch05.exercise06;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

/**
 * 6. Section 5.1.6, “The finally Clause” (page 189) has an example of a broken
 * try statement with catch and finally clauses. Fix the code with (a)
 * catching the exception in the finally clause, (b) a try/catch statement
 * containing a try/finally statement, and (c) a try-with-resources statement
 * with a catch clause.
 */
public class TryTryFinallyCatchExample {

    public void printFileOut(final Path path) {
        BufferedReader in = null;
        try {
            try {
                in = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                // Read from in
                while (in.ready()) {
                    Optional.of(in.readLine()).stream()
                        .filter(s -> !"".equals(s))
                        .forEach(System.out::println);
                }
            } catch (IOException ex) {
                System.err.println("Caught IOException: " + ex.getMessage());
            } finally {
                if (in != null) {
                    in.close(); // Caution—might throw an exception
                }
            }
        } catch (IOException e) {

        }
    }

    public static void main(String[] args) {
        new TryTryFinallyCatchExample().printFileOut(Paths.get("src/main/java/tutorial/ch05/exercise06/exampleInput"));
    }

}
