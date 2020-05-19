package tutorial.ch05.exercise12;

import java.util.Objects;

/**
 * 12. Compare the use of Objects.requireNonNull(obj) and assert
 * obj != null. Give a compelling use for each.
 */
public class AssertionsExample {

    public static void main(String[] args) {
        Object obj = new Object();
        assert obj != null;
        Objects.requireNonNull(obj);
        obj = null;
        // AssertionError if the vm got -ea argument
        assert obj != null;
        // Throws NullPointerException
        Objects.requireNonNull(obj);
    }

}
