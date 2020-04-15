package tutorial.ch03.exercise15;

import java.util.Arrays;
import java.util.Comparator;

import tutorial.ch03.exercise01.Employee;

/**
 * Write a call to Arrays.sort that sorts employees by salary, breaking ties
 * by name. Use Comparator.thenComparing. Then do this in reverse
 * order
 */
public class EmployeeSorter {

    public void sort(final Employee[] employees) {
        Arrays.sort(employees, Comparator.comparingDouble(Employee::getMeasure)
            .thenComparing(Employee::getName));
    }

    public void reverse(final Employee[] employees) {
        Arrays.sort(employees, (e1, e2) -> {
            int result = Double.compare(e2.getMeasure(), e1.getMeasure());
            return result != 0 ? result : CharSequence.compare(e2.getName(), e1.getName());
        });
    }

    public static void main(String[] args) {
        Employee[] employees = {
            new Employee("Johny", 25.0d),
            new Employee("John", 25.0d),
            new Employee("Albert", 15.0d),
            new Employee("Zed", 22.0d),
        };

        EmployeeSorter sorter = new EmployeeSorter();

        System.out.println(Arrays.toString(employees));
        sorter.sort(employees);
        System.out.println(Arrays.toString(employees));
        sorter.reverse(employees);
        System.out.println(Arrays.toString(employees));
    }

}
