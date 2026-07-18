package springboot_deep_drive.exception_handling.in_spring;

public class SpringExceptionAnnotations {

    /**
    ================================================================================
                    SPRING EXCEPTION HANDLING ANNOTATIONS
    ================================================================================

    Until now we have understood

    ✔ What is Exception
    ✔ Exception Propagation
    ✔ DispatcherServlet
    ✔ HandlerExceptionResolver
    ✔ Global Exception Handling

    Now we will understand
    the annotations used for Exception Handling.

    ================================================================================
    1. @ExceptionHandler
    ================================================================================

    Purpose

    Handles one or more Exceptions.

    Think of it as

            "If this Exception occurs,
             execute this method."

    Example

            UserNotFoundException

                    ↓

            handleUserNotFound()

    It is NOT executed for every Exception.

    It executes only when
    the matching Exception occurs.

    ----------------------------------------------------------------------------

    Before @ExceptionHandler

    try{

    }

    catch(UserNotFoundException e){

    }

    After Spring

    We simply write

            @ExceptionHandler(UserNotFoundException.class)

    Spring calls the method automatically.

    ----------------------------------------------------------------------------

    Scope

    If @ExceptionHandler is written
    inside a Controller,

    it handles Exceptions
    only for that Controller.

    Example

    UserController

            @ExceptionHandler(...)

    It cannot handle

    LeaveController

    Exceptions.

    ----------------------------------------------------------------------------

    Interview

    Q.

    Why use @ExceptionHandler?

    Answer

    To separate exception handling logic
    from business logic and avoid writing
    try-catch blocks inside controllers.


    ================================================================================
    2. @ControllerAdvice
    ================================================================================

    Purpose

    Makes one class responsible
    for handling Exceptions
    across multiple Controllers.

    Think of it as

            Global Exception Office

    ----------------------------------------------------------------------------

    Before

    UserController

        @ExceptionHandler()

    EmployeeController

        @ExceptionHandler()

    LeaveController

        @ExceptionHandler()

    Duplicate methods everywhere.

    ----------------------------------------------------------------------------

    After

    One class

            GlobalExceptionHandler

    handles Exceptions
    for every Controller.

    ----------------------------------------------------------------------------

    Important

    @ControllerAdvice itself
    does NOT handle Exceptions.

    It simply tells Spring

            "This class contains
             global advice."

    The actual handling
    is still done by

            @ExceptionHandler


    ================================================================================
    3. @RestControllerAdvice
    ================================================================================

    Very common interview question.

    Many developers think

            It is a completely new annotation.

    Wrong.

    It is simply

            @ControllerAdvice

                    +

            @ResponseBody

    ----------------------------------------------------------------------------

    Meaning

    Whatever your Exception Handler returns

            ResponseEntity

                    ↓

    Spring directly converts it

    into JSON.

    No View Resolver is involved.

    ----------------------------------------------------------------------------

    In REST APIs

    We almost always use

            @RestControllerAdvice

    instead of

            @ControllerAdvice

    because REST APIs return JSON.


    ================================================================================
    4. @ResponseStatus
    ================================================================================

    Purpose

    Used to specify
    an HTTP Status.

    Example

            NOT_FOUND

            BAD_REQUEST

            CONFLICT

    etc.

    ----------------------------------------------------------------------------

    Without it

    Spring may return
    a default status.

    ----------------------------------------------------------------------------

    With it

    You can tell Spring

            Return HTTP 404

    or

            Return HTTP 400


    ----------------------------------------------------------------------------

    Important

    If you already return

            ResponseEntity

    then generally

            @ResponseStatus

    is not required,
    because ResponseEntity already contains
    the status code.

    Interview Tip

    ResponseEntity gives more flexibility
    than @ResponseStatus.


    ================================================================================
    5. @Valid
    ================================================================================

    Very important annotation.

    Package

            jakarta.validation.Valid

    It is NOT a Spring annotation.

    ----------------------------------------------------------------------------

    Purpose

    Validate incoming request objects.

    Example

    UserDTO

            @NotBlank

            @Email

            @Size

    Controller

            @Valid UserDTO

    ----------------------------------------------------------------------------

    Flow

    Client sends JSON

            ↓

    Spring converts JSON to DTO

            ↓

    Spring sees @Valid

            ↓

    Bean Validation starts

            ↓

    Validation Passed?

        │

    ┌───┴───────────┐

    │               │

    ▼               ▼

    YES            NO

    │               │

    ▼               ▼

    Controller    Validation Exception


    ----------------------------------------------------------------------------

    Very Important

    @Valid does NOT validate anything.

    It only triggers

            Jakarta Bean Validation.

    Actual validation
    is performed by

            Hibernate Validator.


    ================================================================================
    6. MethodArgumentNotValidException
    ================================================================================

    Suppose

    DTO

            @NotBlank

            name

    Client sends

            ""

    Validation fails.

    Spring throws

            MethodArgumentNotValidException

    automatically.

    We usually handle it
    inside Global Exception Handler.

    This allows us to send

    {
        "message":"Name is required"
    }

    instead of

            HTTP 500


    ================================================================================
    7. ConstraintViolationException
    ================================================================================

    Another Validation Exception.

    Difference

    MethodArgumentNotValidException

            Usually occurs for

            @RequestBody

    --------------------------------------------------

    ConstraintViolationException

            Usually occurs for

            @PathVariable

            @RequestParam

    Example

    GET /users/-1

    Validation fails

            ConstraintViolationException


    ================================================================================
    8. COMMON INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Difference between

    @ControllerAdvice

    and

    @RestControllerAdvice ?

    Answer

    @RestControllerAdvice

            =

    @ControllerAdvice

            +

    @ResponseBody

    REST APIs generally use
    @RestControllerAdvice.


    --------------------------------------------------


    Q.

    Difference between

    @ExceptionHandler

    and

    @ControllerAdvice ?

    Answer

    @ExceptionHandler

    defines

    WHICH Exception to handle.

    @ControllerAdvice

    defines

    WHERE the handler is available
    globally.


    --------------------------------------------------


    Q.

    Does @ControllerAdvice
    handle Exceptions?

    Answer

    No.

    @ControllerAdvice
    only marks the class.

    @ExceptionHandler methods
    perform the actual handling.


    --------------------------------------------------


    Q.

    Is @Valid a Spring Annotation?

    Answer

    No.

    It belongs to

            Jakarta Bean Validation.

    Spring only integrates it.


    --------------------------------------------------


    Q.

    Who performs Validation?

    Answer

    Hibernate Validator.


    --------------------------------------------------


    Q.

    Why do we prefer ResponseEntity?

    Answer

    Because it allows us to control

    • Status Code

    • Response Body

    • Headers

    in one object.


    ================================================================================
    QUICK REVISION
    ================================================================================

    @ExceptionHandler

            Which Exception?

    -----------------------------

    @ControllerAdvice

            Global Scope

    -----------------------------

    @RestControllerAdvice

            Global Scope
            +
            JSON Response

    -----------------------------

    @ResponseStatus

            Fixed HTTP Status

    -----------------------------

    @Valid

            Starts Validation

    -----------------------------

    MethodArgumentNotValidException

            Validation Failed

    -----------------------------

    ConstraintViolationException

            PathVariable /
            RequestParam Validation Failed


    ================================================================================
    NEXT FILE

    GlobalExceptionHandlerInternals.java

    This is the deepest file.

    We will understand

    ✔ DispatcherServlet source level flow

    ✔ ExceptionHandlerExceptionResolver

    ✔ ControllerAdviceBean

    ✔ How Spring registers handlers

    ✔ Resolver Chain

    ✔ BasicErrorController

    ✔ ErrorAttributes

    ✔ Why Spring Boot returns JSON automatically

    ================================================================================
    */

}