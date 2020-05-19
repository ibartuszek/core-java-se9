package tutorial.ch04.exercise04;

import tutorial.ch04.exercise01.Point;

public class Line extends Shape {

    private Point endPoint;

    public Line(final Point from, final Point to) {
        super(from);
        this.endPoint = to;
    }

    @Override
    public Point getCenter() {
        return new Point((this.point.getX() + this.endPoint.getX())/2,
            (this.point.getY() + this.endPoint.getY())/2);
    }

    @Override
    public void moveBy(final double dx, final double dy) {
        super.moveBy(dx, dy);
        endPoint = new Point(endPoint.getX() + dx, endPoint.getY() + dy);
    }

    @Override
    public String toString() {
        String superString = super.toString();
        return superString.substring(0, superString.length() -1)
            + ", endPoint=" + endPoint
            + '}';
    }

    @Override
    public Line clone() {
        return new Line(new Point(point.getX(), point.getY()), new Point(endPoint.getX(), endPoint.getY()));
    }
}
