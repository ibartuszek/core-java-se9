package tutorial.ch01.sec01.exercises;

import utils.IntegerReader;

/**
 * Write a program that reads an integer angle (which may be positive or negative)
 * and normalizes it to a value between 0 and 359 degrees. Try it first with the %
 * operator, then with floorMod
 */
public class AngleReaderExercise {

    private static final int MAX_ANGLE = 360;

    public static void main(String[] args) {
       int angle = new IntegerReader().readInteger();
        System.out.printf("Normalized angle with %% operator: %d\n", normalize(angle));
        System.out.printf("Normalized angle with floorMod method: %d\n", Math.floorMod(angle, MAX_ANGLE));
    }

    private static int normalize(final int angle) {
        int number = angle % MAX_ANGLE;
        return number >= 0 ? number : number + MAX_ANGLE;
    }

}
