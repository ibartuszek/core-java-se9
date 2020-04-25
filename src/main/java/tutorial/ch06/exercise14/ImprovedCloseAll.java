package tutorial.ch06.exercise14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 14
 * Implement an improved version of the closeAll method in Section 6.3,
 * “Type Bounds” (page 210). Close all elements even if some of them throw an
 * exception. In that case, throw an exception afterwards. If two or more calls
 * throw an exception, chain them together.
 */
public class ImprovedCloseAll {

    public static <T extends AutoCloseable> void closeAll(final ArrayList<T> list) throws Exception {
        Exception exception = null;
        for (T elem : list) {
            try {
                elem.close();
            } catch (Exception e) {
                if (exception == null) {
                    exception = e;
                } else {
                    exception.addSuppressed(e);
                }
            }
        }
        if (exception != null) {
            throw  exception;
        }
    }

    public static void main(String[] args) {
        ArrayList<ExampleAutoCloseAble> list = new ArrayList((List.of(
            new ExampleAutoCloseAble(), new ExampleAutoCloseAble())));
        try {
            closeAll(list);
        } catch (Exception e) {
            System.out.println(e);
            Arrays.stream(e.getSuppressed()).forEach(System.out::println);
        }
    }


}

class ExampleAutoCloseAble implements AutoCloseable {

    @Override
    public void close() throws Exception {
        System.out.println("Close...");
        throw new Exception("Something happened: " + toString());
    }
}
