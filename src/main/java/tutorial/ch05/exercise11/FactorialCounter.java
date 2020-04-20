package tutorial.ch05.exercise11;

import java.text.MessageFormat;

/**
 * Write a recursive factorial method in which you print all stack frames
 * before you return the value. Construct (but don't throw) an exception object of
 * any kind and get its stack trace, as described in Section 5.1.8, “Uncaught
 * Exceptions and the Stack Trace” (page 192).
 */
public class FactorialCounter {

    public long factorial(final long n) {
        long result = 1;
        if (n == 1 || n == 0) {
            result = 1;
        } else if (n < 0) {
            throw new IllegalArgumentException("The argument cannot be negative!");
        } else {
            result = factorialRecursiveCall(n, result);
        }
        return result;
    }

    private long factorialRecursiveCall(final long n, final long factorial) {
        long result = factorial * n;
        StackWalker walker = StackWalker.getInstance();
        StringBuilder builder = new StringBuilder();
        walker.forEach(frame -> builder.append(MessageFormat.format("{0} (line: {1}) -> ", frame.getMethodName(), frame.getLineNumber())));
        builder.setLength(builder.length() - 4);
        System.out.println(builder.toString());
        if (n > 1) {
            result = factorialRecursiveCall(n - 1, result);
        }
        return result;
    }

    public static void main(String[] args) {
        System.out.println(new FactorialCounter().factorial(5));
    }

}
