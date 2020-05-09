package tutorial.ch09.exercise01;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 01
 * Write a utility method for copying all of an InputStream to an
 * OutputStream, without using any temporary files. Provide another
 * solution, without a loop, using operations from the Files class, using a
 * temporary file.
 */
public class StreamCopier {

    public static OutputStream copy(final InputStream inputStream) throws IOException {
        OutputStream out = new ByteArrayOutputStream();
        for (int next = inputStream.read(); next != -1; next = inputStream.read()) {
            out.write(next);
        }
        return out;
    }

    public static OutputStream copyWithTempFile(final InputStream inputStream) throws IOException {
        OutputStream result = new ByteArrayOutputStream();
        Path temp = Path.of("temp.txt");
        Files.copy(inputStream, temp);
        Files.copy(temp, result);
        Files.delete(temp);
        return result;
    }

    public static void main(String[] args) throws IOException {
        OutputStream outputStream = StreamCopier.copy(Files.newInputStream(Paths.get("src/main/java/tutorial/ch09/example")));
        ((ByteArrayOutputStream) outputStream).writeTo(System.out);
        System.out.println();
        outputStream = StreamCopier.copyWithTempFile(Files.newInputStream(Paths.get("src/main/java/tutorial/ch09/example")));
        ((ByteArrayOutputStream) outputStream).writeTo(System.out);
    }

}
