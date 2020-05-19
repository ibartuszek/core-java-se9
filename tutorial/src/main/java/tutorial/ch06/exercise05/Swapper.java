package tutorial.ch06.exercise05;

/**
 * Consider this variant of the swap method where the array can be supplied
 * with varargs: ...
 */
public class Swapper {

    public static <T> T[] swap(int i, int j, T... values) {
        T temp = values[i];
        values[i] = values[j];
        values[j] = temp;
        return values;
    }

    public static void main(String[] args) {
        // Double[] result = Swapper.swap(0, 1, 1.5, 2, 3);
        // Double[] result = Swapper.<Double>swap(0, 1, 1.5, 2, 3);
        Double[] result = Swapper.swap(0, 1, 1.5, 2.0, 3.0);
    }
}
