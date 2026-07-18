package EngineeringDigest.collections.sorting;

import java.util.ArrayList;
import java.util.List;

public class ComparableDemo {
    public static void main(String[] args) {
        List<Student> list = new ArrayList<>();

        list.add(new Student("Charlie", 32));
        list.add(new Student("Bob", 25));
        list.add(new Student("Alice", 20));
        list.add(new Student("Akshit", 28));

        list.sort(null); // uses compareTo()
       // list.sort((a, b) -> a.getAge() - b.getAge());

        System.out.println(list);
    }
}

class Student implements Comparable<Student>{
   private String name;
   private Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Student other) {
        return this.age - other.age; // sorting by age
    }

//    @Override
//    public int compareTo(Student other) {
//        return this.name.compareTo(other.name);
//    }

    @Override
    public String toString() {
        return name + " - " + age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
