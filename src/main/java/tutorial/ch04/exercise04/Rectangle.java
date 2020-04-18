package tutorial.ch04.exercise04;

import tutorial.ch04.exercise01.Point;

public class Rectangle extends Shape {

    private double width;
    private double height;

    public Rectangle(final Point topLeft, final double width, final double height) {
        super(topLeft);
        this.width = width;
        this.height = height;
    }

    @Override
    public Point getCenter() {
        return new Point(point.getX() + this.width / 2, point.getY() + this.height / 2);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() -1)
            + ", width=" + width
            + ", height=" + height
            + '}';
    }

    @Override
    public Rectangle clone() {
        return new Rectangle(new Point(point.getX(), point.getY()), width, height);
    }

}
