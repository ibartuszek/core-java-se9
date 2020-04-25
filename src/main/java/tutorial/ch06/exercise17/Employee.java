package tutorial.ch06.exercise17;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 17
 * Define a class Employee that implements Comparable<Employee>.
 * Using the javap utility, demonstrate that a bridge method has been
 * synthesized. What does it do?
 */
public class Employee implements Comparable<Employee> {

    private Double salary;

    public Employee(final Double salary) {
        this.salary = salary;
    }

    @Override
    public int compareTo(final Employee employee) {
        return Double.compare(this.salary, employee.salary);
    }

    @Override
    public String toString() {
        return "Employee{"
            + "salary=" + salary
            + '}';
    }

    public static void main(String[] args) {
        ArrayList<Employee> list = new ArrayList(List.of(
           new Employee(50.0D), new Employee(25.0D)
        ));
        System.out.println(list.stream()
            .sorted(Employee::compareTo)
            .collect(Collectors.toList()));

    }

}
