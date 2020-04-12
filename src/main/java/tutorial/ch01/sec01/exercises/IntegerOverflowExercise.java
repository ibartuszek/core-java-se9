package tutorial.ch01.sec01.exercises;

/**
 * What happens when you cast a double to an int that is larger than the
 * largest possible int value? Try it out
 */
public class IntegerOverflowExercise {

    public static void main(String[] args) {
       double number = Integer.MAX_VALUE * 2;
       int integer = (int) number;
        System.out.println(integer);
    }

}
