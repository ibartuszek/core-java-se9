package tutorial.ch06.exercise22;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * 22
 * Improve the method public static <V, T extends Throwable>
 * V doWork(Callable<V> c, T ex) throws T of Section 6.6.7,
 * “Exceptions and Generics” (page 225) so that one doesn't have to pass an
 * exception object, which may never get used. Instead, accept a constructor
 * reference for the exception class.
 */
public class ImprovedThrowable {

    public static <V, T extends Throwable> V doWork(Callable<V> c, Supplier<T> constr) throws T {
        try {
            return c.call();
        } catch (Throwable realEx) {
            T exception = constr.get();
            exception.initCause(realEx);
            throw exception;
        }
    }

    public static void main(String[] args) {
        try {
            doWork((
                Callable<NumberFormatException>) () -> { throw new NumberFormatException("cause"); },
                IllegalArgumentException::new);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

}
