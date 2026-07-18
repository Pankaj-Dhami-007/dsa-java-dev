package java_recap.springboot.annotations.exception_handling;

/*

=================================================================
        EXCEPTION HANDLING ANNOTATIONS
=================================================================

Annotations:
-------------
@ExceptionHandler
@ControllerAdvice
@RestControllerAdvice
@ResponseStatus

Core Concepts:
---------------
1. Global Exception Handling
2. Centralized Error Management
3. API Error Responses
4. Exception Propagation
5. REST Error Design
6. Spring MVC Exception Flow

=================================================================
                WHY EXCEPTION HANDLING EXISTS?
=================================================================

Imagine API throws exception:

-----------------------------------------------------------------

java.lang.NullPointerException

-----------------------------------------------------------------

Without handling:
------------------

Client receives ugly stack trace.

=================================================================
                PROBLEMS
=================================================================

1. Bad API experience
2. Security risk
3. Internal details exposed
4. Inconsistent responses
5. Difficult debugging

=================================================================
                REAL PRODUCTION REQUIREMENT
=================================================================

Frontend expects:

{
   "message": "User not found",
   "status": 404,
   "timestamp": "2026-05-18"
}

NOT Java stack traces.

=================================================================
                SOLUTION
=================================================================

Centralized Exception Handling.

=================================================================
                WHAT IS EXCEPTION PROPAGATION?
=================================================================

Exception moves upward through call stack.

=================================================================
                EXAMPLE FLOW
=================================================================

Controller
    ↓
Service
    ↓
Repository
    ↓
Database Exception

Exception bubbles upward.

=================================================================
                DEFAULT SPRING BEHAVIOR
=================================================================

Spring Boot automatically returns:
-----------------------------------

500 Internal Server Error

=================================================================
                PROBLEM
=================================================================

Generic/unfriendly responses.

=================================================================
                1. @ExceptionHandler
=================================================================

Most basic exception handling annotation.

=================================================================
                DEFINITION
=================================================================

@ExceptionHandler tells Spring:

"When this exception occurs,
execute this method."

=================================================================
                EXAMPLE
=================================================================

@ExceptionHandler(UserNotFoundException.class)

public String handleException(
        UserNotFoundException ex
) {

    return ex.getMessage();

}

=================================================================
                INTERNAL WORKING
=================================================================

Exception Thrown
        ↓
DispatcherServlet catches exception
        ↓
Spring searches matching
@ExceptionHandler method
        ↓
Handler method executes
        ↓
Response returned

=================================================================
                IMPORTANT
=================================================================

@ExceptionHandler can handle:
------------------------------

1. Custom exceptions
2. Runtime exceptions
3. Validation exceptions
4. Database exceptions

=================================================================
                LOCAL EXCEPTION HANDLING
=================================================================

@Controller
public class UserController {

    @ExceptionHandler(UserNotFoundException.class)

    public String handle() {

    }

}

=================================================================
                PROBLEM
=================================================================

Works only inside same controller.

=================================================================
                ENTERPRISE PROBLEM
=================================================================

100 controllers
    ↓
Duplicate exception logic everywhere

=================================================================
                SOLUTION
=================================================================

@ControllerAdvice

=================================================================
                2. @ControllerAdvice
=================================================================

Most important enterprise annotation.

=================================================================
                DEFINITION
=================================================================

@ControllerAdvice provides:
----------------------------

Global exception handling
for all controllers.

=================================================================
                REAL MEANING
=================================================================

Central place to handle exceptions.

=================================================================
                EXAMPLE
=================================================================

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
        UserNotFoundException.class
    )

    public ResponseEntity<String> handle(
        UserNotFoundException ex
    ) {

        return ResponseEntity
                .status(404)
                .body(ex.getMessage());

    }

}

=================================================================
                INTERNAL WORKING
=================================================================

Controller Exception
        ↓
DispatcherServlet catches exception
        ↓
Spring checks @ControllerAdvice
        ↓
Matching @ExceptionHandler found
        ↓
Global handler executes
        ↓
Response returned

=================================================================
                VERY IMPORTANT
=================================================================

@ControllerAdvice works globally
across application.

=================================================================
                REAL PROJECT USE CASE
=================================================================

Central handling for:
----------------------

1. Validation exceptions
2. JWT exceptions
3. Database exceptions
4. Business exceptions
5. Generic exceptions

=================================================================
                3. @RestControllerAdvice
=================================================================

Most used in REST APIs.

=================================================================
                DEFINITION
=================================================================

@RestControllerAdvice =
------------------------

@ControllerAdvice
+
@ResponseBody

=================================================================
                WHAT DOES IT MEAN?
=================================================================

Returns JSON response automatically.

=================================================================
                EXAMPLE
=================================================================

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(
        UserNotFoundException.class
    )

    public ErrorResponse handle(
        UserNotFoundException ex
    ) {

        return new ErrorResponse(
            ex.getMessage(),
            404
        );

    }

}

=================================================================
                RESPONSE
=================================================================

{
   "message": "User not found",
   "status": 404
}

=================================================================
                WHY IMPORTANT?
=================================================================

Modern frontend applications expect:
-------------------------------------

JSON responses.

=================================================================
                DIFFERENCE
=================================================================

@ControllerAdvice
------------------
Mainly MVC/View applications.

@RestControllerAdvice
----------------------
REST APIs/JSON responses.

=================================================================
                4. @ResponseStatus
=================================================================

Used to define HTTP status.

=================================================================
                EXAMPLE
=================================================================

@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserNotFoundException
        extends RuntimeException {

}

=================================================================
                WHAT HAPPENS?
=================================================================

Whenever exception thrown:
---------------------------

Spring automatically returns:
------------------------------

404 NOT FOUND

=================================================================
                INTERNAL WORKING
=================================================================

Exception thrown
        ↓
Spring checks @ResponseStatus
        ↓
HTTP status updated
        ↓
Response returned

=================================================================
                REAL PROJECT EXAMPLE
=================================================================

@ResponseStatus(HttpStatus.BAD_REQUEST)

public class InvalidCouponException
        extends RuntimeException {

}

=================================================================
                CUSTOM ERROR RESPONSE
=================================================================

Best practice in enterprise apps.

=================================================================
                EXAMPLE
=================================================================

public class ErrorResponse {

    private String message;
    private int status;
    private LocalDateTime timestamp;

}

=================================================================
                WHY IMPORTANT?
=================================================================

Standardized API responses.

=================================================================
                VALIDATION EXCEPTION HANDLING
=================================================================

Very important real-world topic.

=================================================================
                EXAMPLE
=================================================================

@ExceptionHandler(
    MethodArgumentNotValidException.class
)

public ResponseEntity<?> handleValidation(
    MethodArgumentNotValidException ex
) {

}

=================================================================
                USED FOR
=================================================================

Returning proper validation errors.

=================================================================
                EXAMPLE RESPONSE
=================================================================

{
   "name": "must not be blank",
   "email": "invalid email"
}

=================================================================
                GENERIC EXCEPTION HANDLER
=================================================================

@ExceptionHandler(Exception.class)

=================================================================
                WHY IMPORTANT?
=================================================================

Catch unexpected errors globally.

=================================================================
                BUT IMPORTANT
=================================================================

Do NOT expose:
---------------

1. Stack traces
2. Internal DB errors
3. Sensitive details

=================================================================
                EXCEPTION HIERARCHY
=================================================================

Throwable
   ↓
Exception
   ↓
RuntimeException
   ↓
Custom Exceptions

=================================================================
                CUSTOM EXCEPTION
=================================================================

public class UserNotFoundException
        extends RuntimeException {

    public UserNotFoundException(
            String message
    ) {

        super(message);

    }

}

=================================================================
                WHY CUSTOM EXCEPTIONS?
=================================================================

1. Better readability
2. Business-specific meaning
3. Cleaner error handling

=================================================================
                REAL PROJECT SCENARIO
=================================================================

E-commerce APIs:
-----------------

1. ProductNotFoundException
2. PaymentFailedException
3. CouponExpiredException
4. UnauthorizedException

=================================================================
                EXCEPTION FLOW IN SPRING
=================================================================

Request
   ↓
Controller
   ↓
Service
   ↓
Exception occurs
   ↓
DispatcherServlet catches
   ↓
HandlerExceptionResolver executes
   ↓
@ControllerAdvice checks
   ↓
Matching handler executes
   ↓
JSON error response returned

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
Difference between
@ControllerAdvice and
@RestControllerAdvice?

=================================================================
                ANSWER
=================================================================

@ControllerAdvice:
-------------------
Used for MVC applications.

@RestControllerAdvice:
-----------------------
Used for REST APIs.
Automatically adds @ResponseBody.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
How does Spring handle exceptions internally?

=================================================================
                ANSWER
=================================================================

1. Exception thrown
2. DispatcherServlet catches exception
3. HandlerExceptionResolver invoked
4. Matching @ExceptionHandler found
5. Response generated

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why global exception handling important?

=================================================================
                ANSWER
=================================================================

1. Centralized logic
2. Consistent API responses
3. Better maintainability
4. Security
5. Cleaner controllers

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Can multiple @ControllerAdvice exist?

YES.

=================================================================
                HOW ORDER DECIDED?
=================================================================

Using:
-------

@Order annotation

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
What happens if no exception handler found?

Answer:
--------
Spring Boot default error handling executes.

Usually returns:
-----------------

500 Internal Server Error

=================================================================
                COMMON MISTAKES
=================================================================

1. Returning stack traces to clients

2. Catching Exception everywhere

3. Writing duplicate try-catch blocks

4. Not using custom exceptions

=================================================================
                BEST PRACTICE
=================================================================

1. Use global exception handling
2. Create custom business exceptions
3. Return standardized JSON responses
4. Hide internal details
5. Handle validation exceptions separately

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain complete exception flow in Spring Boot.

Answer:
--------
1. Exception occurs in application
2. DispatcherServlet catches exception
3. Spring searches matching handler
4. @ControllerAdvice executes
5. Error response generated
6. JSON response returned to client

=================================================================
                SUMMARY
=================================================================

@ExceptionHandler
------------------
Handles specific exceptions.

@ControllerAdvice
-------------------
Global exception handling.

@RestControllerAdvice
-----------------------
Global JSON exception handling.

@ResponseStatus
----------------
Defines HTTP status code.

Centralized exception handling
is mandatory in enterprise APIs.

=================================================================

*/

public class ExceptionHandlingAnnotationsNotes {
}