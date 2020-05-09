package tutorial.ch09.exercise07;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FileEncoder {

    private final MessageDigest messageDigest;

    public FileEncoder(final String encoding) throws NoSuchAlgorithmException {
        this.messageDigest = MessageDigest.getInstance(encoding);
    }

    public void encode(final Path source, final Path target) throws IOException {
        String content = Files.readString(source, StandardCharsets.UTF_8);
        System.out.println(new String(messageDigest.digest(content.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8));
        System.out.println("1930e5d76b919b456aeb715b9d5709db6ab05b8c3c3a632dafd9f1e79ee5fd750a7cbb067d61c4fbe1bd15af9ba6576c4bb114470b515b8f9bbb0ec634691244");
    }

    public String encode(final String source) {
        return getEncodedString(messageDigest.digest(source.getBytes(StandardCharsets.UTF_8))).toString();
    }

    private StringBuilder getEncodedString(final byte[] digest) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte element : digest) {
            stringBuilder.append(getCharacters(element));
        }
        return stringBuilder;
    }

    private String getCharacters(final byte element) {
        return Integer.toString((element & 0xff) + 0x100, 16).substring(1);
    }

    public static void main(String[] args) throws NoSuchAlgorithmException, IOException {
        FileEncoder encoder = new FileEncoder("SHA-512");
        Path source = Path.of("src/main/java/tutorial/ch09/example");
        Path target = Path.of("encoded");
        try {
            Files.delete(target);
        } catch (NoSuchFileException e) {
            System.out.println("File does not exists yet...");
        }
        encoder.encode(source, target);
        System.out.println(encoder.encode("Test π"));
        System.out.println("cd13c2ba280b30dd9a329e8d5bc7ae24c784536103e0fa8e83af6947a49bcae2f97239ab6ce71fed0444e12f6c9554b063b110d00c1752581ac28fd58b8b555a");
    }
}
