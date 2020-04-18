package tutorial.ch04.exercise04;

import tutorial.ch04.exercise01.Point;

public abstract class Shape implements Cloneable{

    protected Point point;

    public Shape(final Point point) {
        this.point = point;
    }

    public void moveBy(double dx, double dy) {
        point = new Point(point.getX() + dx, point.getY() + dy);
    }

    public abstract Point getCenter();

    @Override
    public String toString() {
        return getClass().getSimpleName() + "{"
            + "point=" + point
            + '}';
    }

    @Override
    public abstract Shape clone();

}
