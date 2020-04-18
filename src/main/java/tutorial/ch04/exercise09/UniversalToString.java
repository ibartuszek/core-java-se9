package tutorial.ch04.exercise09;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tutorial.ch04.exercise01.LabeledPoint;
import tutorial.ch04.exercise01.Point;
import tutorial.ch04.exercise04.Circle;

/**
 * Write a “universal” toString method that uses reflection to yield a string
 * with all instance variables of an object. Extra credit if you can handle cyclic
 * references.
 */
public class UniversalToString {

    public String toString(final Object object) {
        StringBuilder stringBuilder = new StringBuilder(object.getClass().getSimpleName());
        stringBuilder.append("{");
        getFieldList(object.getClass()).forEach(field -> toString(object, field, stringBuilder));
        stringBuilder.setLength(stringBuilder.length() - 2);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    private List<Field> getFieldList(final Class objectClass) {
        List<Field> fieldList = new ArrayList<>(Arrays.asList(objectClass.getDeclaredFields()));
        Class parentClass = objectClass.getSuperclass();
        while (parentClass != Object.class) {
            fieldList.addAll(Arrays.asList(parentClass.getDeclaredFields()));
            parentClass = parentClass.getSuperclass();
        }
        return fieldList;
    }

    private void toString(final Object parentObject, final Field field, final StringBuilder stringBuilder) {
        field.setAccessible(true);
        stringBuilder.append(field.getName());
        if (!field.getType().isPrimitive() && field.getType() != String.class) {
            handleFieldAsObject(parentObject, field, stringBuilder);
        } else {
            handleAsPrimitive(parentObject, field, stringBuilder);
        }
        stringBuilder.append(", ");
    }

    private void handleFieldAsObject(final Object parentObject, final Field field, final StringBuilder stringBuilder) {
        try {
            stringBuilder.append(" (" + field.getType().getSimpleName() + ")");
            stringBuilder.append(": {");
            Object object = field.get(parentObject);
            getFieldList(object.getClass()).forEach(field1 -> toString(object, field1, stringBuilder));
            stringBuilder.setLength(stringBuilder.length() - 2);
            stringBuilder.append("}");
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleAsPrimitive(final Object object, final Field field, final StringBuilder stringBuilder) {
        stringBuilder.append(": ");
        Class type = field.getType();
        try {
            if (boolean.class.equals(type)) {
                stringBuilder.append(field.getBoolean(object));
            } else if (byte.class.equals(type)) {
                stringBuilder.append(field.getByte(object));
            } else if (char.class.equals(type)) {
                stringBuilder.append(field.getChar(object));
            } else if (double.class.equals(type)) {
                stringBuilder.append(field.getDouble(object));
            } else if (float.class.equals(type)) {
                stringBuilder.append(field.getFloat(object));
            } else if (int.class.equals(type)) {
                stringBuilder.append(field.getInt(object));
            } else if (long.class.equals(type)) {
                stringBuilder.append(field.getLong(object));
            } else if (short.class.equals(type)) {
                stringBuilder.append(field.getShort(object));
            } else if (String.class.equals(type)) {
                stringBuilder.append(field.get(object));
            } else {
                throw new IllegalStateException("Unexpected value: " + field.getType());
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        LabeledPoint center = new LabeledPoint(20.0D, 25.0D, "center");
        Point point = new Point(22.0D, 28.0D);
        Circle circle = new Circle(center, 10.0D);
        List<Point> pointList = List.of(point, center);
        CustomTestClass testObject = new CustomTestClass(5, circle, pointList);
        System.out.println(new UniversalToString().toString(testObject));
    }

}

class CustomTestClass {
    int number;
    Circle circle;
    List<Point> pointList;

    public CustomTestClass(final int number, final Circle circle, final List<Point> pointList) {
        this.number = number;
        this.circle = circle;
        this.pointList = pointList;
    }

}
