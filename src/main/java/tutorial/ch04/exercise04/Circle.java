package tutorial.ch04.exercise04;

import tutorial.ch04.exercise01.Point;

public class Circle extends Shape{

    private double radius;

    public Circle(final Point center, final double radius) {
        super(center);
        this.radius = radius;
    }

    @Override
    public Point getCenter() {
        return point;
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() -1)
            + ", radius=" + radius
            + '}';
    }

    @Override
    public Circle clone() {
        return new Circle(new Point(point.getX(), point.getY()), radius);
    }
}
