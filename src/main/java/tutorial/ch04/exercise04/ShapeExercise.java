package tutorial.ch04.exercise04;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import tutorial.ch04.exercise01.Point;

/**
 * 04
 * Define an abstract class Shape with an instance variable of class Point, a
 * constructor, a concrete method public void moveBy(double dx,
 * double dy) that moves the point by the given amount, and an abstract
 * method public Point getCenter(). Provide concrete subclasses
 * Circle, Rectangle, Line with constructors public Circle(Point
 * center, double radius), public Rectangle(Point
 * topLeft, double width, double height), and public
 * Line(Point from, Point to)
 * 05
 * Define clone methods for the classes of the preceding exercise
 */
public class ShapeExercise {

    public static void main(String[] args) {
        List<Shape> shapeList = new ArrayList<>(List.of(
            new Circle(new Point(22.0D, 23.0D), 10.0D),
            new Rectangle(new Point(10.0D, 10.0D), 10.0D, 20.0D),
            new Line(new Point(10.0D, 10.0D), new Point(20.0D, 20.0D))
        ));
        List<Shape> cloneList = shapeList.stream()
            .map(Shape::clone)
            .collect(Collectors.toList());

        cloneList.forEach(shape -> shape.moveBy(10.0D, 10.0D));
        System.out.println("Originals:");
        shapeList.forEach(System.out::println);
        System.out.println("Clones:");
        cloneList.forEach(System.out::println);
        System.out.println("Centers of originals:");
        shapeList.stream().map(Shape::getCenter)
            .forEach(System.out::println);
        System.out.println("Centers of clones:");
        cloneList.stream().map(Shape::getCenter)
            .forEach(System.out::println);
    }

}
