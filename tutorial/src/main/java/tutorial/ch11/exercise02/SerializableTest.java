package tutorial.ch11.exercise02;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 02
 * If annotations had existed in early versions of Java, then the Serializable interface
 * would surely have been an annotation.
 * Implement a @Serializable annotation. Choose a text or binary format for persistence.
 * Provide classes for streams or readers/writers that persist the state of objects by
 * saving and restoring all fields that are primitive values or themselves serializable.
 * Don't worry about cyclic references for now.
 */
public class SerializableTest {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Path tempObjects = Path.of("tmp\\objects");
        Files.createDirectories(tempObjects);
        serialize(new DataWithInterface("Data with serialization by interface"), "tmp\\objects\\firstData");
        System.out.println(deserialize("tmp\\objects\\firstData"));
        DataWithAnnotationSerializer.serialize(createData());
        System.out.println(DataWithAnnotationSerializer.deserialize(getFirstDataWithAnnotation(tempObjects)));
    }

    private static void serialize(final Object objectToWrite, final String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(objectToWrite);
        }
    }

    private static Object deserialize(final String fileName) throws IOException, ClassNotFoundException {
        Object result;
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            result = in.readObject();
        }
        return result;
    }

    private static DataWithAnnotation createData() {
        DataWithAnnotation dataWithAnnotation = new DataWithAnnotation();
        dataWithAnnotation.setData("Data with serialization by annotation");
        dataWithAnnotation.setNumber(1);
        dataWithAnnotation.setTest(2);
        return dataWithAnnotation;
    }

    private static Path getFirstDataWithAnnotation(final Path tempObjects) throws IOException {
        return Files.list(tempObjects)
            .filter(path -> path.toString().contains("DataWithAnnotation"))
            .findAny()
            .orElse(null);
    }

}


