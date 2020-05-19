package tutorial.ch06.exercise18;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.IntFunction;

/**
 * 18
 * Consider the method
 * public static <T> T[] repeat(int n, T obj, IntFunction<T[]> constr)
 * in Section 6.6.3, “You Cannot Instantiate Type Variables” (page 221). The call
 * Arrays.repeat(10, 42, int[]::new) will fail. Why? How can
 * you fix that? What do you need to do for the other primitive types?
 * 19
 * Consider the method
 * public static <T> ArrayList<T> repeat(int n, T obj)
 * in Section 6.6.3, “You Cannot Instantiate Type Variables” (page 221). This
 * method had no trouble constructing an ArrayList<T> which contains an
 * array of T values. Can you produce a T[] array from that array list without
 * using a Class value or a constructor reference? If not, why not?
 * 20
 * Implement the method
 * @SafeVarargs public static final <T> T[] repeat(int n, T... objs)
 * Return an array with n copies of the given objects. Note that no Class value
 * or constructor reference is required since you can reflectively increase objs.
 */
public class Repeater {

    public static <T> T[] repeat(int n, T obj, IntFunction<T[]> constr) {
        T[] result = constr.apply(n);
        for (int index = 0; index < n; index++) {
            result[index] = obj;
        }
        return result;
    }

    public static <T> ArrayList<T> repeat(int n, T obj) {
        ArrayList<T> result = new ArrayList<>();
        for (int index = 0; index < n; index++) {
            result.add(obj);
        }
        return result;
    }

    public static <T> T[] repeatToArray(int n, T obj, IntFunction<T[]> constr) {
        return repeat(n, obj).toArray(constr);
    }

    @SafeVarargs
    public static final <T> T[] repeatWithVarArgs(int n, T... objs) {
        Class<?> componentType = objs.getClass().getComponentType();
        @SuppressWarnings("unchecked")
        T[] result = (T[]) Array.newInstance(componentType, n * objs.length);
        int index = 0;
        for (T obj : objs) {
            for (int i = 0; i < n; i++) {
                result[index++] = obj;
            }
        }
        return result;
    }

    public static void main(String[] args) {
        // int[] integers = repeat(10, 42, int[]::new);
        Integer[] integers = repeat(10, 42, Integer[]::new);
        System.out.println(Arrays.toString(integers));
        ArrayList<Double> doubles = repeat(5, 5.0D);
        System.out.println(doubles);
        Long[] longs = repeatWithVarArgs(3, 5L, 6L, 7L);
        System.out.println(Arrays.toString(longs));
    }

}
