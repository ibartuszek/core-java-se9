package tutorial.ch04.exercise01;

/**
 * 01
 * Define a class Point with a constructor public Point(double x,
 * double y) and accessor methods getX, getY. Define a subclass
 * LabeledPoint with a constructor public LabeledPoint(String
 * label, double x, double y) and an accessor method getLabel.
 */
/**
 * 02
 * Define toString, equals, and hashCode methods for the classes of the
 * preceding exercise
 */
public class LabeledPointExercise {

    public static void main(String[] args) {
        Point p = new Point(20.0D, 22.0D);
        System.out.println(p);
        LabeledPoint lp = new LabeledPoint(10.0D, 12.0D, "Label");
        System.out.println(lp);
    }

}
