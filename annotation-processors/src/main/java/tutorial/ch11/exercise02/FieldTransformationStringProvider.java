package tutorial.ch11.exercise02;

import javax.lang.model.type.TypeMirror;

public class FieldTransformationStringProvider {

    private final String fieldName;
    private final TypeMirror typeMirror;

    public FieldTransformationStringProvider(final String fieldName, final TypeMirror typeMirror) {
        this.fieldName = fieldName;
        this.typeMirror = typeMirror;
    }

    public String provide() {
        String result = null;
        String type = typeMirror.toString();
        switch (type) {
            case "java.lang.String":
                result = getBase();
                break;
            case "int":
            case "java.lang.Integer":
                result = "Integer.parseInt(" + getBase() + ")";
                break;
            default:
                throw new UnsupportedOperationException("Not implemented");
        }
        return result;
    }

    private String getBase() {
        return "fields.get(\"" + fieldName + "\")";
    }
}
