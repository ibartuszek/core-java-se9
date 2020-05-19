package tutorial.ch11.exercise02;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;

@SupportedAnnotationTypes("tutorial.ch11.exercise02.CustomSerializable")
@SupportedSourceVersion(SourceVersion.RELEASE_11)
public class CustomSerializableProcessor extends AbstractProcessor {

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        for (TypeElement annotation : annotations) {
            for (Element element : roundEnv.getElementsAnnotatedWith(annotation)) {
                if (element.getKind() == ElementKind.CLASS) {
                    TypeElement typeElement = (TypeElement) element;
                    String packageName = typeElement.getEnclosingElement().toString();
                    String className = typeElement.getSimpleName().toString();
                    Map<String, TypeMirror> fields = getFields(typeElement);
                    String content = createContent(packageName, className, fields);
                    try {
                        writeContent(packageName, className, content);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    private Map<String, TypeMirror> getFields(final TypeElement typeElement) {
        Map<String, TypeMirror> resultMap = new HashMap<>();
        typeElement.getEnclosedElements().stream()
            .filter(member -> member.getKind() == ElementKind.FIELD)
            .forEach(element -> resultMap.put(element.getSimpleName().toString(), element.asType()));
        return resultMap;
    }

    private String createContent(final String packageName, final String className, final Map<String, TypeMirror> fields) {
        SerializerContentBuilder builder = SerializerContentBuilder.builder(packageName, className)
            .withPackage()
            .withImports()
            .withClassDeclaration()
            .withStaticFields()
            .withSerialize()
            .withCreateContent(fields.keySet().toArray(String[]::new))
            .withAppendIndentation()
            .withSerializeObject()
            .withAppendName()
            .withWriteContent()
            .withDeserialize(fields)
            .withSplit()
            .withTransformWord();
        return builder.build();
    }

    private void writeContent(final String packageName, final String className, final String content) throws IOException {
        Path folder = Path.of("tutorial/generatedSrc/main/java/" + packageName.replaceAll("\\.", "/"));
        Files.createDirectories(folder);
        Path target = Path.of(folder.toString() + "/" + className + "Serializer.java");
        PrintWriter printWriter = new PrintWriter(target.toFile());
        Stream.of(content.split("\n")).forEach(printWriter::println);
        printWriter.flush();
    }

}
