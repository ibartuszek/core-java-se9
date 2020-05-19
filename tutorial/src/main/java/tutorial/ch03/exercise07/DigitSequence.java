package tutorial.ch03.exercise07;

import java.util.Iterator;

/**
 * In this exercise, you will try out what happens when a method is added to an
 * interface. In Java 7, implement a class DigitSequence that implements
 * Iterator<Integer>, not IntSequence. Provide methods hasNext,
 * next, and a do-nothing remove. Write a program that prints the elements of
 * an instance.
 * In Java 8, the Iterator class gained another method,
 * forEachRemaining. Does your code still compile when you switch to
 * Java 8? If you put your Java 7 class in a JAR file and don't recompile, does it
 * work in Java 8? What if you call the forEachRemaining method? Also,
 * the remove method has become a default method in Java 8, throwing an
 * UnsupportedOperationException. What happens when remove is
 * called on an instance of your class?
 */
public class DigitSequence implements Iterator<Integer> {

    private int number;

    private DigitSequence(final int number) {
        this.number = number;
    }

    public static DigitSequence of(final int number) {
        return new DigitSequence(number);
    }

    @Override
    public boolean hasNext() {
        return number % 10 != 0;
    }

    @Override
    public Integer next() {
        int result = number % 10;
        number /= 10;
        return result;
    }

    public static void main(String[] args) {
        DigitSequence digitSequence = DigitSequence.of(359);
        while(digitSequence.hasNext()) {
            System.out.printf("%2d", digitSequence.next());
        }
        System.out.println();
        DigitSequence.of(678).forEachRemaining(System.out::println);
    }
}
