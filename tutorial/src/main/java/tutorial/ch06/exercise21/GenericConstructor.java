package tutorial.ch06.exercise21;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

/**
 * Using the @SafeVarargs annotation, write a method that can construct
 * arrays of generic types. For example,
 * List<String>[] result = Arrays.<List<String>>construct(10);
 * // Sets result to a List<String>[] of size 10
 */
public class GenericConstructor {

    @SafeVarargs
    public static <T> T[] construct(int n, T... objs) {
        Class<?> componentType = objs.getClass().getComponentType();;
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(componentType, n);
        return result;
    }

    public static void main(String[] args) {
        List<String>[] result = construct(10);
        System.out.println(Arrays.toString(result));
    }

}
