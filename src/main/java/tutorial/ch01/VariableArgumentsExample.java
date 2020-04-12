package tutorial.ch01;

import java.util.Arrays;

public class VariableArgumentsExample {

    public static void main(String[] args) {
        System.out.println(average(0, 3, 4.5d, -5.0, 0));
    }

    private static double average(final double defaultValue, final double... values) {
        return Arrays.stream(values)
            .average()
            .orElse(defaultValue);
    }

}
