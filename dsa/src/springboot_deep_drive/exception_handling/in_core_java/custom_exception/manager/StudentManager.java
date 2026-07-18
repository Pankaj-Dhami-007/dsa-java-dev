package springboot_deep_drive.exception_handling.in_core_java.custom_exception.manager;

import springboot_deep_drive.exception_handling.in_core_java.custom_exception.common.ErrorCode;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.data.DataStore;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.exception.StudentNotFoundException;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.model.Student;

public class StudentManager {

    public Student findStudentById(int studentId) {
        Student student = DataStore.STUDENTS.get(studentId);
        if (student == null) {
            throw new StudentNotFoundException(
                    ErrorCode.STUDENT_NOT_FOUND
            );
        }
        return student;
    }

}
