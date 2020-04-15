package tutorial.ch03.exercise01;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 1.
 * Provide an interface Measurable with a method double
 * getMeasure() that measures an object in some way. Make Employee
 * implement Measurable. Provide a method double
 * average(Measurable[] objects) that computes the average
 * measure. Use it to compute the average salary of an array of employees.
 */
/**
 * 2.
 * Continue with the preceding exercise and provide a method Measurable
 * largest(Measurable[] objects). Use it to find the name of the
 * employee with the largest salary. Why do you need a cast?
 */
public class Employee implements Measurable {

    private final Double salary;
    private final String name;

    public Employee(final String name, final double salary) {
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Employee{salary=" + salary + ", name=" + name + "}";
    }

    @Override
    public double getMeasure() {
        return salary;
    }

    private static double average(final Measurable[] objects) {
        return Arrays.stream(objects)
            .mapToDouble(Measurable::getMeasure)
            .average()
            .orElse(0.0d);
    }

    private static Measurable largest(Measurable[] objects) {
        return Arrays.stream(objects)
            .max(Comparator.comparingDouble(Measurable::getMeasure))
            .orElse(null);
    }

    public static void main(String[] args) {
        Measurable[] employees = {
            new Employee("John1", 20.0d),
            new Employee("John2", 15.0d),
            new Employee("John3", 25.0d)
        };
        Measurable[] emptyEmployees = {};
        testEmployee(employees);
        testEmployee(emptyEmployees);
    }

    private static void testEmployee(final Measurable[] employees) {
        System.out.println(Arrays.toString(employees));
        System.out.println("Average: " + average(employees));
        Employee largest = (Employee) largest(employees);
        if (largest != null) {
            System.out.println(largest.getName() + "'s salary is the highest.");
        }
    }

}
