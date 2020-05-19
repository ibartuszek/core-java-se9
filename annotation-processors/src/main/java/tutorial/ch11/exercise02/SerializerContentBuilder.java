package tutorial.ch11.exercise02;

import java.util.Map;

import javax.lang.model.type.TypeMirror;

public class SerializerContentBuilder {

    private final StringBuilder sb;
    private final String packageName;
    private final String originalClassName;
    private final String className;

    private SerializerContentBuilder(final String packageName, final String className) {
        sb = new StringBuilder();
        this.packageName = packageName;
        this.originalClassName = className;
        this.className = originalClassName + "Serializer";
    }

    public static SerializerContentBuilder builder(final String packageName, final String className) {
        return new SerializerContentBuilder(packageName, className);
    }

    private void addIndentation() {
        sb.append("    ");
    }

    private void addNewLine() {
        sb.append("\n");
    }

    private void addWithNewLine(final String line) {
        sb.append(line);
        addNewLine();
    }

    private void addWithIndentAndNewLine(final String line) {
        addIndentation();
        addWithNewLine(line);
    }

    private void addWithDoubleIndentAndNewLine(final String line) {
        addIndentation();
        addWithIndentAndNewLine(line);
    }

    private void addWithTripleIndentAndNewLine(final String line) {
        addIndentation();
        addWithDoubleIndentAndNewLine(line);
    }

    private String capitalizeFirstLetter(final String word) {
        return word.substring(0, 1).toUpperCase() + word.substring(1);
    }

    public String build() {
        sb.append("}");
        addNewLine();
        return sb.toString();
    }

    public SerializerContentBuilder withPackage() {
        addWithNewLine("package " + packageName + ";");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withImports() {
        addWithNewLine("import java.io.File;");
        addWithNewLine("import java.io.FileNotFoundException;");
        addWithNewLine("import java.io.IOException;");
        addWithNewLine("import java.io.PrintWriter;");
        addWithNewLine("import java.nio.file.Files;");
        addWithNewLine("import java.nio.file.Path;");
        addWithNewLine("import java.util.HashMap;");
        addWithNewLine("import java.util.Map;");
        addWithNewLine("import java.util.UUID;");
        addWithNewLine("import java.util.stream.Collectors;");
        addWithNewLine("import java.util.stream.IntStream;");
        addWithNewLine("import java.util.stream.Stream;");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withClassDeclaration() {
        addWithNewLine("public class " + className + " {");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withStaticFields() {
        addWithIndentAndNewLine("private static final int SPACES_OF_INDENTATION = 2;");
        addWithIndentAndNewLine("private static final String SERIAL_VERSION_UID_LABEL = \"serialVersionUID\";");
        addWithIndentAndNewLine("private static final long SERIAL_VERSION_UID_VALUE = 2L;");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withSerialize() {
        addWithIndentAndNewLine("public static void serialize(final Object object) throws FileNotFoundException {");
        addWithDoubleIndentAndNewLine("if (object instanceof " + originalClassName + ") {");
        addWithTripleIndentAndNewLine(originalClassName + " data = (" + originalClassName + ") object;");
        addWithTripleIndentAndNewLine("writeContent(createContent(data));");
        addWithDoubleIndentAndNewLine("} else {");
        addWithTripleIndentAndNewLine("throw new IllegalArgumentException(\"Given object is not instance of " + className + " class!\");");
        addWithDoubleIndentAndNewLine("}");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withCreateContent(final String... fields) {
        addWithIndentAndNewLine("private static String createContent(final " + originalClassName + " data) {" );
        addWithDoubleIndentAndNewLine("StringBuilder sb = new StringBuilder();");
        addWithDoubleIndentAndNewLine("sb.append(\"{\\n\");");
        addWithDoubleIndentAndNewLine("appendIndentation(sb, 1);");
        addWithDoubleIndentAndNewLine("sb.append(\"\\\"\" + SERIAL_VERSION_UID_LABEL + \"\\\": \" + SERIAL_VERSION_UID_VALUE + \",\\n\");");
        appendFieldsForSerialization(fields);
        addWithDoubleIndentAndNewLine("sb.append(\"}\\n\");");
        addWithDoubleIndentAndNewLine("return sb.toString();");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    private void appendFieldsForSerialization(final String[] fields) {
        for (int index = 0; index < fields.length; index++) {
            String field = fields[index];
            String getter = "get" + capitalizeFirstLetter(field) + "()";
            addWithDoubleIndentAndNewLine("serializeObject(\"" + field + "\", data." + getter + ", sb, 1);");
            if (index == fields.length - 1) {
                addWithDoubleIndentAndNewLine("sb.append(\"\\n\");");
            } else {
                addWithDoubleIndentAndNewLine("sb.append(\",\\n\");");
            }
        }
    }

    public SerializerContentBuilder withAppendIndentation() {
        addWithIndentAndNewLine("private static void appendIndentation(final StringBuilder sb, final int numberOfIndentation) {");
        addWithDoubleIndentAndNewLine("sb.append(IntStream.range(0, SPACES_OF_INDENTATION * numberOfIndentation).mapToObj(i -> \" \").collect(Collectors.joining(\"\")));");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withSerializeObject() {
        addWithIndentAndNewLine("private static void serializeObject(final String name, final Object object, final StringBuilder sb, final int numberOfIndentation) {");
        addWithDoubleIndentAndNewLine("appendName(name, sb, numberOfIndentation);");
        addWithDoubleIndentAndNewLine("if (!object.getClass().isPrimitive() && !(object instanceof Number)) {");
        addWithTripleIndentAndNewLine("sb.append(\"\\\"\" + object + \"\\\"\");");
        addWithDoubleIndentAndNewLine("} else {");
        addWithTripleIndentAndNewLine("sb.append(object);");
        addWithDoubleIndentAndNewLine("}");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withAppendName() {
        addWithIndentAndNewLine("private static void appendName(final String name, final StringBuilder sb, final int numberOfIndentation) {");
        addWithDoubleIndentAndNewLine("appendIndentation(sb, numberOfIndentation);");
        addWithDoubleIndentAndNewLine("sb.append(\"\\\"\");");
        addWithDoubleIndentAndNewLine("sb.append(name);");
        addWithDoubleIndentAndNewLine("sb.append(\"\\\": \");");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withWriteContent() {
        addWithIndentAndNewLine("private static void writeContent(final String content) throws FileNotFoundException {");
        addWithDoubleIndentAndNewLine("Path path = Path.of(\"tmp/objects/" + originalClassName + "\" + UUID.randomUUID() + \".json\");");
        addWithDoubleIndentAndNewLine("File file = path.toFile();");
        addWithDoubleIndentAndNewLine("PrintWriter printWriter = new PrintWriter(file);");
        addWithDoubleIndentAndNewLine("Stream.of(content.split(\"\\n\")).forEach(printWriter::println);");
        addWithDoubleIndentAndNewLine("printWriter.flush();");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withDeserialize(final Map<String, TypeMirror> fields) {
        String objectName = originalClassName.substring(0, 1).toLowerCase() + originalClassName.substring(1);
        addWithIndentAndNewLine("public static " + originalClassName + " deserialize(final Path path) throws IOException {");
        addWithDoubleIndentAndNewLine(originalClassName + " " + objectName + " = new " + originalClassName + "();");
        addWithDoubleIndentAndNewLine("Map<String, String> fields = new HashMap<>();");
        addWithDoubleIndentAndNewLine("Files.lines(path).filter(line -> line.length() > 3)");
        addWithTripleIndentAndNewLine(".map(" + className + "::split)");
        addWithTripleIndentAndNewLine(".forEach(keyValues -> fields.put(keyValues[0].trim(), keyValues[1].trim()));");
        appendFieldsForDeserialization(fields, objectName);
        addWithDoubleIndentAndNewLine("return " + objectName + ";");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    private void appendFieldsForDeserialization(final Map<String, TypeMirror> fields, final String objectName) {
        fields.entrySet().forEach(entry -> {
            FieldTransformationStringProvider provider = new FieldTransformationStringProvider(entry.getKey(), entry.getValue());
            String methodName = "set" + capitalizeFirstLetter(entry.getKey());
            addWithDoubleIndentAndNewLine(objectName + "." + methodName + "(" + provider.provide() + ");");
        });
    }

    public SerializerContentBuilder withSplit() {
        addWithIndentAndNewLine("private static String[] split(final String s) {");
        addWithDoubleIndentAndNewLine("String[] words = s.split(\":\");");
        addWithDoubleIndentAndNewLine("String key = transformWord(words[0].trim());");
        addWithDoubleIndentAndNewLine("String value = transformWord(words[1].trim());");
        addWithDoubleIndentAndNewLine("return new String[]{key, value};");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

    public SerializerContentBuilder withTransformWord() {
        addWithIndentAndNewLine("private static String transformWord(final String word) {");
        addWithDoubleIndentAndNewLine("int beginIndex = 0;");
        addWithDoubleIndentAndNewLine("int endIndex = word.length();");
        addWithDoubleIndentAndNewLine("if (word.contains(\",\")) {");
        addWithTripleIndentAndNewLine("endIndex--;");
        addWithDoubleIndentAndNewLine("}");
        addWithDoubleIndentAndNewLine("if (word.charAt(0) == '\\\"' && word.charAt(endIndex - 1) == '\\\"') {");
        addWithTripleIndentAndNewLine("beginIndex++;");
        addWithTripleIndentAndNewLine("endIndex--;");
        addWithDoubleIndentAndNewLine("}");
        addWithDoubleIndentAndNewLine("return word.substring(beginIndex, endIndex);");
        addWithIndentAndNewLine("}");
        addNewLine();
        return this;
    }

}
