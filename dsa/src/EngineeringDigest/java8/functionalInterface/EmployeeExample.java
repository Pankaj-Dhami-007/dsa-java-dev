package EngineeringDigest.java8.functionalInterface;

import java.util.*;
import java.util.stream.*;
import java.util.function.*;

class Employee {
    String name;
    String dept;
    double salary;
    int age;

    Employee(String name, String dept, double salary, int age) {
        this.name = name;
        this.dept = dept;
        this.salary = salary;
        this.age = age;
    }

    // getters
    public String getName() { return name; }
    public String getDept() { return dept; }
    public double getSalary() { return salary; }
    public int getAge() { return age; }
}

public class EmployeeExample {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee("Rahul", "IT", 80000, 25),
                new Employee("Priya", "HR", 60000, 28),
                new Employee("Rohit", "IT", 90000, 30),
                new Employee("Sneha", "Finance", 70000, 26),
                new Employee("Amit", "IT", 55000, 24)
        );

        // 1. Filter IT employees (Predicate)
        Predicate<Employee> isIT = emp -> emp.getDept().equals("IT");

        List<Employee> itEmployees = employees.stream()
                .filter(isIT)
                .collect(Collectors.toList());

        // 2. Get only names (Function)
        Function<Employee, String> getName = Employee::getName;

        List<String> names = employees.stream()
                .map(getName)
                .collect(Collectors.toList());

        System.out.println(names); // [Rahul, Priya, Rohit, Sneha, Amit]

        // 3. Print each employee (Consumer)
        Consumer<Employee> printEmp = emp ->
                System.out.println(emp.getName() + " - " + emp.getSalary());

        employees.forEach(printEmp);

        // 4. Get employees with salary > 65000 in IT dept
        List<String> highPaidIT = employees.stream()
                .filter(emp -> emp.getDept().equals("IT"))   // Predicate inline
                .filter(emp -> emp.getSalary() > 65000)      // Predicate inline
                .map(Employee::getName)                       // Method reference
                .sorted()                                     // Sort alphabetically
                .collect(Collectors.toList());

        System.out.println(highPaidIT); // [Rahul, Rohit]

        // 5. Average salary of IT dept
        OptionalDouble avgSalary = employees.stream()
                .filter(isIT)
                .mapToDouble(Employee::getSalary)
                .average();

        System.out.println(avgSalary.getAsDouble()); // 75000.0

        // 6. Group by department
        Map<String, List<Employee>> byDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDept));

        byDept.forEach((dept, emps) ->
                System.out.println(dept + ": " + emps.size()));
    }
}
