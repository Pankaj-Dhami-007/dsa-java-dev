package springboot_deep_drive.exception_handling.in_core_java.custom_exception.data;

import springboot_deep_drive.exception_handling.in_core_java.custom_exception.model.Student;

import java.util.HashMap;
import java.util.Map;

public final class DataStore {

    public static final Map<Integer, Student> STUDENTS = new HashMap<>();

    static {
        STUDENTS.put(101,
                new Student(101, "Rahul", 20));
        STUDENTS.put(102,
                new Student(102, "Amit", 22));
        STUDENTS.put(103,
                new Student(103, "Neha", 19));
    }

    private DataStore() {

    }

}
