package tutorial.ch01.exercises;

/**
 * Improve the average method so that it is called with at least one parameter
 */
public class ImprovedAverageExercise {

    public static void main(String[] args) {
        System.out.println(average());
        System.out.println(averageImproved(1.0d));
    }

    public static double averageImproved(final double firstValue, double... values) {
        double sum = firstValue;
        for (double v : values) sum += v;
        return sum / (values.length + 1);
    }

    private static double average(double... values) {
        double sum = 0;
        for (double v : values) sum += v;
        return values.length == 0 ? 0 : sum / values.length;
    }
}
