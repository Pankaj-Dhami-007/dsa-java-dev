package springboot_deep_drive.exception_handling.in_core_java.custom_exception.common;

public enum ErrorCode {

    INVALID_AGE(
            "1001",
            "Age must be greater than or equal to 18."
    ),

    STUDENT_NOT_FOUND(
            "1002",
            "Student not found."
    ),

    VALIDATION_ERROR(
            "1003",
            "Validation failed."
    ),

    INSUFFICIENT_BALANCE(
            "1004",
            "Insufficient account balance."
    ),

    DATABASE_ERROR(
            "1005",
            "Database operation failed."
    ),

    UNKNOWN_ERROR(
            "9999",
            "Unknown error occurred."
    );

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
