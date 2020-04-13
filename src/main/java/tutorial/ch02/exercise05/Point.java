package tutorial.ch02.exercise05;

/**
 * Implement an immutable class Point that describes a point in the plane.
 * Provide a constructor to set it to a specific point, a no-arg constructor to set it
 * to the origin, and methods getX, getY, translate, and scale. The
 * translate method moves the point by a given amount in x- and y-direction.
 * The scale method scales both coordinates by a given factor. Implement
 * these methods so that they return new points with the results. For example,
 * Point p = new Point(3, 4).translate(1, 3).scale(0.5);
 * should set p to a point with coordinates (2, 3.5).
 */

/**
 * Point class represents a geometrical point of the plain. It has two coordinates: x and y.
 * This class is immutable.
 *
 * @author Isttván Bartuszek
 * @version 1.0
 */
public class Point {

    private final double x;
    private final double y;

    /**
     * Constructor method of {@link Point} with no arguments. The constructed point is placed to the origin.
     */
    public Point() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Constructor method of {@link Point}
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
     * @return a new {@link Point} with the translated coordinates.
     */
    public Point translate(final double x, final double y) {
        return new Point(this.x + x, this.y + y);
    }

    /**
     * Scale the point coordinates with the given factor.
     *
     * @param factor the factor of the scale (both direction).
     * @return a new {@link Point} with the scaled coordinates.
     */
    public Point scale(final double factor) {
        return new Point(this.x * factor, this.y * factor);
    }

    @Override
    public String toString() {
        return "Point{x=" + x + ", y=" + y + '}';
    }

    public static void main(String[] args) {
        System.out.println(new Point(3, 4).translate(1, 3).scale(0.5));
    }

}
