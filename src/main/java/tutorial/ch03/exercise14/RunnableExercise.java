package tutorial.ch03.exercise14;

import java.util.Arrays;

import tutorial.ch03.exercise09.Greater;

/**
 * Write a method that takes an array of Runnable instances and returns a
 * Runnable whose run method executes them in order. Return a lambda
 * expression.
 */
public class RunnableExercise {

    public Runnable createSequence(final Runnable[] runnables) {
        return () -> Arrays.stream(runnables)
            .forEach(Runnable::run);
    }

    public static void main(String[] args) {
        Runnable[] runnables = {
            Greater.of("John", 5),
            Greater.of("Johny", 5)
        };
        new Thread(new RunnableExercise().createSequence(runnables)).start();
    }

}
