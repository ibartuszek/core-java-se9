package tutorial.ch04.exercise07;

import java.awt.Color;
import java.lang.reflect.Field;

/**
 * Define an enumeration type for the eight combinations of primary colors
 * BLACK, RED, BLUE, GREEN, CYAN, MAGENTA, YELLOW, WHITE with
 * methods getRed, getGreen, and getBlue
 */
public enum ColorEnum {

    BLACK,
    RED,
    BLUE,
    GREEN,
    CYAN,
    MAGENTA,
    YELLOW,
    WHITE;

    public int getRed() {
        return getColor(this.name()).getRed();
    }

    public int getGreen() {
        return getColor(this.name()).getGreen();
    }

    public int getBlue() {
        return getColor(this.name()).getBlue();
    }

    private Color getColor(final String name) {
        Object value;
        try {
            value = Color.class.getDeclaredField(name).get(new Color(0));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            throw new RuntimeException(e.getMessage());
        }
        return (Color) value;
    }

    public static void main(String[] args) {
        ColorEnum color = ColorEnum.RED;
        System.out.printf("%s[r:%d, g:%d, b%d]\n", color.name(), color.getRed(), color.getGreen(), color.getBlue());
    }

}
