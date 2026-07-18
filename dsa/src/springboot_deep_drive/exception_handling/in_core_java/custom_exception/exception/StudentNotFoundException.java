package springboot_deep_drive.exception_handling.in_core_java.custom_exception.exception;

import springboot_deep_drive.exception_handling.in_core_java.custom_exception.common.ErrorCode;

public class StudentNotFoundException extends ApplicationException {

    public StudentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
