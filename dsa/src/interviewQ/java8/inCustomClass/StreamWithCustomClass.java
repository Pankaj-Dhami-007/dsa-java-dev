package interviewQ.java8.inCustomClass;


import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Employee{
    private int id;
    private String name;
    private String department;
    private double salary;
    private int age;
    private List<String> skills;

    // Constructor, getters, setters, toString
    public Employee(int id, String name, String department, double salary, int age, List<String> skills) {
        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.age = age;
        this.skills = skills;
    }

    // Getters and toString
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getSalary() { return salary; }
    public int getAge() { return age; }
    public List<String> getSkills() { return skills; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", name='" + name + "', department='" + department + "', salary=" + salary + "}";
    }
}

public class StreamWithCustomClass {

    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
                new Employee(1, "John", "IT", 75000, 28, Arrays.asList("Java", "Spring")),
                new Employee(2, "Alice", "HR", 55000, 32, Arrays.asList("Recruitment", "Communication")),
                new Employee(3, "Bob", "IT", 80000, 35, Arrays.asList("Java", "AWS", "Docker")),
                new Employee(4, "Carol", "Finance", 65000, 29, Arrays.asList("Accounting", "Excel")),
                new Employee(5, "David", "IT", 90000, 40, Arrays.asList("Java", "Microservices", "Kubernetes"))
        );

        //. Common Interview Questions
        // Q1: Find IT employees with salary > 70000
        List<Employee> highPaidIT = employees.stream()
                .filter(emp -> "IT".equals(emp.getDepartment()))
                .filter(emp -> emp.getSalary() > 70000)
                .collect(Collectors.toList());
        System.out.println("High paid IT employees: " + highPaidIT);


        //2. Group employees by department
        Map<String, List<Employee>> employeesByDept = employees.stream()
                .collect(Collectors.groupingBy(Employee::getDepartment));
        //Default toList()
        //groupingBy() returns a Map<K, List<T>> by default

        System.out.println(employeesByDept);

        //3. Group employees by age range
        // a custom classification function!
        Map<String, List<Employee>> employeesByAgeRange = employees.stream()
                .collect(Collectors.groupingBy(emp -> {
                    if (emp.getAge() < 30) return "Young";
                    else if (emp.getAge() < 40) return "Middle";
                    else return "Senior";
                }));
        System.out.println(employeesByAgeRange);

        //4. Group employees by department with counting
        Map<String, Long> employeeCountByDept = employees.stream()           // 1. Create stream
                .collect(Collectors.groupingBy(                                 // 2. Terminal: Group by department
                        Employee::getDepartment,                                    // 3. Classification function
                        Collectors.counting()                                       // 4. Downstream collector - count elements
                ));

        System.out.println("Employee count by department: " + employeeCountByDept);


//Q5: Find average salary by department
        Map<String, Double> avgSalaryByDept = employees.stream()            // 1. Create stream
                .collect(Collectors.groupingBy(                                // 2. Terminal: Group by department
                        Employee::getDepartment,                                   // 3. Classification function
                        Collectors.averagingDouble(Employee::getSalary)           // 4. Calculate average salary
                ));

        System.out.println("Average salary by department: " + avgSalaryByDept);


        //Collectors.joining()
        // Purpose: Concatenate strings with delimiter
        //When to use: When you need comma-separated or combined strings
        //Return Type: String

        // Example: Get comma-separated employee names by department
        Map<String, String> employeeNamesByDept = employees.stream()
                .collect(Collectors.groupingBy(
                        Employee::getDepartment,
                        Collectors.mapping(
                                Employee::getName,
                                Collectors.joining(", ")     // Join with delimiter
                        )
                ));

        // Variations:
       // Collectors.joining()                    // No delimiter: "JohnBobDavid"
       // Collectors.joining(", ")               // With delimiter: "John, Bob, David"
       // Collectors.joining(", ", "[", "]")     // With prefix/suffix: "[John, Bob, David]"



        Map<Boolean, List<Employee>> partitionedBySalary = employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.getSalary() > 70000));

        System.out.println("High salary (true): " + partitionedBySalary.get(true));
        System.out.println("Low salary (false): " + partitionedBySalary.get(false));

        Map<Boolean, List<Employee>> partitionedByAge = employees.stream()
                .collect(Collectors.partitioningBy(emp -> emp.getAge() >= 30));

        System.out.println("30 or older: " + partitionedByAge.get(true));
        System.out.println("Under 30: " + partitionedByAge.get(false));

    }
}

/*

What is a Collector?
A Collector is an interface that implements various reduction operations, such as
accumulating elements into collections, summarizing elements, or reducing elements in various ways.

What is Collectors?
Collectors is a utility class that provides implementations of the Collector interface.
It contains static methods for common reduction operations.

What is a Downstream Collector?
Definition: Operations applied to elements after they are grouped by classification function.AND
// Basic structure
.collect(Collectors.groupingBy(
    classificationFunction,  // HOW to group
    downstreamCollector      // WHAT to do with each group
))
A downstream collector is a collector that's passed as a parameter to
another collector to perform additional operations on the grouped or partitioned data.

Java 8 GroupingBy:
Java 8's Collectors.groupingBy() is a powerful collector that groups elements of a stream based
on a classification function.
GroupingBy is one of the most powerful features in Java 8 Streams,
enabling you to perform complex data aggregation and analysis with minimal code.

// Simple grouping
Collectors.groupingBy(Function classifier)

// Grouping with downstream collector
Collectors.groupingBy(Function classifier, Collector downstream)

// Grouping with map factory and downstream collector
Collectors.groupingBy(Function classifier, Supplier mapFactory, Collector downstream)




partitioningBy---------------->
partitioningBy is a specialized grouping that always creates two groups based on a boolean condition.

// Basic signature
Collectors.partitioningBy(Predicate<T> predicate)

// With downstream collector
Collectors.partitioningBy(Predicate<T> predicate, Collector<T, A, D> downstream)

Return Type: Always Map<Boolean, ...>






 */
