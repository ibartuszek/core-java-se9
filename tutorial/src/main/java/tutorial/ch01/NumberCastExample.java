package tutorial.ch01;

public class NumberCastExample {

    public static void main(String[] args) {
        System.out.println((int) 3.75f);
        System.out.println(Math.round(3.75f));
        castNumberWithOverFlowProtection();
    }

    private static void castNumberWithOverFlowProtection() {
        long tooLargeToInteger = Integer.MAX_VALUE + 1L;
        try {
            int number = Math.toIntExact(tooLargeToInteger);
            System.out.println(number + " should not be printed.");
        } catch (ArithmeticException e) {
            System.out.println(tooLargeToInteger + " cannot be transformed to integer!");
            System.out.println(e.getMessage());
        }
    }

}
