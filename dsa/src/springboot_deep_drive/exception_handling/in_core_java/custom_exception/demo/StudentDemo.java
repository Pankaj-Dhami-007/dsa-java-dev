package springboot_deep_drive.exception_handling.in_core_java.custom_exception.demo;

import springboot_deep_drive.exception_handling.in_core_java.custom_exception.common.ConsolePrinter;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.common.ErrorResponse;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.common.ErrorUtil;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.manager.StudentManager;
import springboot_deep_drive.exception_handling.in_core_java.custom_exception.model.Student;

public class StudentDemo {
    public void run() {
        StudentManager manager = new StudentManager();
        try {
            Student student = manager.findStudentById(999);
            System.out.println(student);
        } catch (Exception exception) {
            ErrorResponse response = ErrorUtil.build(exception);
            ConsolePrinter.print(response);
        }

    }

}
