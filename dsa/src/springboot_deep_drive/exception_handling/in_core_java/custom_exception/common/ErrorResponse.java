package springboot_deep_drive.exception_handling.in_core_java.custom_exception.common;

import java.time.LocalDateTime;

public class ErrorResponse {

    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String details;

    public ErrorResponse(String errorCode,
                         String message,
                         String details) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }

    @Override
    public String toString() {
        return """
          
                ==========================================
                      ERROR RESPONSE
                ==========================================
                Time       : %s
                Error Code : %s
                Message    : %s
                Details    : %s
                ==========================================
                """.formatted(
                timestamp,
                errorCode,
                message,
                details
        );
    }
}
