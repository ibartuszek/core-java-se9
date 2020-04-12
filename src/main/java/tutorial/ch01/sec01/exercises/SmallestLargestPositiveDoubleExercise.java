package tutorial.ch01.sec01.exercises;

/**
 * Write a program that prints the smallest and largest positive double values.
 * Hint: Look up Math.nextUp in the Java API
 */
public class SmallestLargestPositiveDoubleExercise {

    public static void main(String[] args) {
        System.out.println(Math.nextUp(0));
        System.out.println(Math.nextUp(Double.MAX_VALUE));
    }
}
