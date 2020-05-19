package tutorial.ch01.exercises;

/**
 * Section 1.5.3, “String Comparison” (page 25) has an example of two strings s
 * and t so that s.equals(t) but s != t. Come up with a different
 * example that doesn't use substring).
 */
public class StringComparisonExercise {

    public static void main(String[] args) {
        String first = "example";
        String second = new String("example");
        System.out.printf("%s.equals(%s): %s\n", first, second, first.equals(second));
        System.out.printf("%s != %s: %s\n", first, second, first != second);
    }

}
