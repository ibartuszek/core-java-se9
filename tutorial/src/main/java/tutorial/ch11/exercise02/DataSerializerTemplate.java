package tutorial.ch11.exercise02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class DataSerializerTemplate {

    private static final int SPACES_OF_INDENTATION = 2;
    private static final String SERIAL_VERSION_UID_LABEL = "serialVersionUID";
    private static final long SERIAL_VERSION_UID_VALUE = 2L;

    public static void serialize(final Object object) throws FileNotFoundException {
        if (object instanceof DataWithAnnotation) {
            DataWithAnnotation data = (DataWithAnnotation) object;
            writeContent(createContent(data));
        } else {
            throw new IllegalArgumentException("Given object is not instance of DataWithAnnotation class!");
        }
    }

    private static String createContent(final DataWithAnnotation data) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\n");
        appendIndentation(sb, 1);
        sb.append("\"" + SERIAL_VERSION_UID_LABEL + "\": " + SERIAL_VERSION_UID_VALUE + ",\n");
        serializeObject("number", data.getNumber(), sb, 1);
        sb.append(",\n");
        serializeObject("data", data.getData(), sb, 1);
        sb.append(",\n");
        serializeObject("test", data.getTest(), sb, 1);
        sb.append("\n");
        sb.append("}\n");
        return sb.toString();
    }

    private static void appendIndentation(final StringBuilder sb, final int numberOfIndentation) {
        sb.append(IntStream.range(0, SPACES_OF_INDENTATION * numberOfIndentation).mapToObj(i -> " ").collect(Collectors.joining("")));
    }

    private static void serializeObject(final String name, final Object object, final StringBuilder sb, final int numberOfIndentation) {
        appendName(name, sb, numberOfIndentation);
        if (!object.getClass().isPrimitive() && !(object instanceof Number)) {
            sb.append("\"" + object + "\"");
        } else {
            sb.append(object);
        }
    }

    private static void appendName(final String name, final StringBuilder sb, final int numberOfIndentation) {
        appendIndentation(sb, numberOfIndentation);
        sb.append("\"");
        sb.append(name);
        sb.append("\": ");
    }

    private static void writeContent(final String content) throws FileNotFoundException {
        Path path = Path.of("tmp/objects/DataWithAnnotation" + UUID.randomUUID() + ".json");
        File file = path.toFile();
        PrintWriter printWriter = new PrintWriter(file);
        Stream.of(content.split("\n")).forEach(printWriter::println);
        printWriter.flush();
    }

    public static DataWithAnnotation deserialize(final Path path) throws IOException {
        DataWithAnnotation dataWithAnnotation = new DataWithAnnotation();
        Map<String, String> fields = new HashMap<>();
        Files.lines(path).filter(line -> line.length() > 3)
            .map(DataSerializerTemplate::split)
            .forEach(keyValues -> fields.put(keyValues[0].trim(), keyValues[1].trim()));
        dataWithAnnotation.setNumber(Integer.parseInt(fields.get("number")));
        dataWithAnnotation.setData(fields.get("data"));
        dataWithAnnotation.setTest(Integer.parseInt(fields.get("test")));
        return dataWithAnnotation;
    }

    private static String[] split(final String s) {
        String[] words = s.split(":");
        String key = transformWord(words[0].trim());
        String value = transformWord(words[1].trim());
        return new String[]{key, value};
    }

    private static String transformWord(final String word) {
        int beginIndex = 0;
        int endIndex = word.length();
        if (word.contains(",")) {
            endIndex--;
        }
        if (word.charAt(0) == '\"' && word.charAt(endIndex - 1) == '\"') {
            beginIndex++;
            endIndex--;
        }
        return word.substring(beginIndex, endIndex);
    }

}
