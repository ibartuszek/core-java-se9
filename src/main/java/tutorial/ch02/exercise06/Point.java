package tutorial.ch02.exercise06;

/**
 * Repeat the preceding exercise, but now make translate and scale into
 * mutators.
 */

/**
 * Point class represents a geometrical point of the plain. It has two coordinates: x and y.
 *
 * @author Isttván Bartuszek
 * @version 1.0
 */
public class Point {

    private double x;
    private double y;

    /**
     * Constructor method of {@link Point} with no arguments. The constructed point is placed to the origin.
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructor method of {@link tutorial.ch02.exercise05.Point}
     *
     * @param x the first coordinate of the point.
     * @param y the second coordinate of the point.
     */
    public Point(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter method of the first coordinate.
     *
     * @return x as a double.
     */
    public double getX() {
        return x;
    }

    /**
     * Getter method of the second coordinate.
     *
     * @return y as a double.
     */
    public double getY() {
        return y;
    }

    /**
     * Translate the point with the given coordinates.
     *
     * @param x the translation into the first direction.
     * @param y the translation into the second direction.
     */
    public void translate(final double x, final double y) {
        this.x += x;
        this.y += y;
    }

    /**
     * Scale the point coordinates with the given factor.
     *
     * @param factor the factor of the scale (both direction).
     */
    public void scale(final double factor) {
        this.x *= factor;
        this.y *= factor;
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + '}';
    }

    public static void main(String[] args) {
        Point p = new Point(3, 4);
        p.translate(1, 3);
        p.scale(0.5);
        System.out.println(p);
    }

}
