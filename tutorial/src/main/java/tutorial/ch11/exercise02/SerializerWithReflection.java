package tutorial.ch11.exercise02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SerializerWithReflection {

    private static final int SPACES_OF_INDENTATION = 2;
    private static final String SERIAL_VERSION_UID = "\"serialVersionUID\"";

    private final long serialVersionUID;

    public SerializerWithReflection(final long serialVersionUID) {
        this.serialVersionUID = serialVersionUID;
    }

    public void serialize(final Object object) throws IOException {
        Class<?> objectClass = object.getClass();
        StringBuilder sb = new StringBuilder();
        writeContent(objectClass, createFileContent(object, objectClass, sb));
    }

    private String createFileContent(final Object object, final Class<?> objectClass, final StringBuilder sb) {
        sb.append("{\n");
        appendIndentation(sb, 1);
        sb.append(SERIAL_VERSION_UID + ": " + serialVersionUID + ",\n");
        serializeObject(object, objectClass, sb, 1);
        sb.delete(sb.length() - 2, sb.length() - 1);
        sb.append("}\n");
        return sb.toString();
    }

    private void serializeObject(final Object object, final Class<?> objectClass, final StringBuilder sb, final int numberOfIndentation) {
        List<Field> fieldList = getFieldList(objectClass);
        for (Field field : fieldList) {
            try {
                field.setAccessible(true);
                handleField(field, field.get(object), sb, numberOfIndentation);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        appendIndentation(sb, numberOfIndentation - 1);
    }

    private List<Field> getFieldList(final Class<?> objectClass) {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(objectClass.getDeclaredFields()));
        Class parentClass = objectClass.getSuperclass();
        while (parentClass != Object.class) {
            fieldList.addAll(Arrays.asList(parentClass.getDeclaredFields()));
            parentClass = parentClass.getSuperclass();
        }
        return fieldList;
    }

    private void handleField(final Field field, final Object value, final StringBuilder sb, final int numberOfIndentation) {
        appendIndentation(sb, numberOfIndentation);
        sb.append("\"");
        sb.append(field.getName());
        sb.append("\"");
        sb.append(": ");
        if (!field.getType().isPrimitive()) {
            if (field.getType().equals(String.class)) {
                sb.append("\"" + value + "\"");
            } else if (value instanceof Number) {
                sb.append(value);
            } else {
                sb.append("{\n");
                serializeObject(value, field.getType(), sb, numberOfIndentation + 1);
                sb.delete(sb.length() - 4, sb.length() - 1);
                sb.append("\n");
                appendIndentation(sb, numberOfIndentation);
                sb.append("}");
            }
        } else {
            sb.append(value);
        }
        sb.append(",\n");
    }

    private void appendIndentation(final StringBuilder sb, final int numberOfIndentation) {
        sb.append(IntStream.range(0, SPACES_OF_INDENTATION * numberOfIndentation).mapToObj(i -> " ").collect(Collectors.joining("")));
    }

    private void writeContent(final Class<?> objectClass, final String content) throws FileNotFoundException {
        Path path = Path.of("tmp/objects/" + objectClass.getName() + UUID.randomUUID() + ".json");
        File file = path.toFile();
        PrintWriter printWriter = new PrintWriter(file);
        Stream.of(content.split("\n")).forEach(printWriter::println);
        printWriter.flush();
    }

}
