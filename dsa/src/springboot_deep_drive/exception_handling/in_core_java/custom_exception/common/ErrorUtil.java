package springboot_deep_drive.exception_handling.in_core_java.custom_exception.common;

import springboot_deep_drive.exception_handling.in_core_java.custom_exception.exception.ApplicationException;

public final class ErrorUtil {

    private ErrorUtil() {

    }

    public static ErrorResponse build(Exception exception) {
        if (exception instanceof ApplicationException applicationException) {
            ErrorCode errorCode = applicationException.getErrorCode();
            return new ErrorResponse(
                    errorCode.getCode(),
                    errorCode.getMessage(),
                    exception.getMessage()
            );
        }
        return new ErrorResponse(
                ErrorCode.UNKNOWN_ERROR.getCode(),
                ErrorCode.UNKNOWN_ERROR.getMessage(),
                exception.getMessage()
        );

    }

}
