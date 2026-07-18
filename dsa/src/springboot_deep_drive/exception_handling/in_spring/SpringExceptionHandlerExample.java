package springboot_deep_drive.exception_handling.in_spring;

public class SpringExceptionHandlerExample {

    /*
    ================================================================================
                    SPRING EXCEPTION HANDLER - PRACTICAL EXAMPLE
    ================================================================================

    Previous File Summary

    Client
        │
        ▼
    DispatcherServlet
        │
        ▼
    Controller
        │
        ▼
    Service
        │
        ▼
    Exception Thrown
        │
        ▲
    Controller
        │
        ▲
    DispatcherServlet

    Now the question is...
            DispatcherServlet has received an Exception.
    What happens next?

    ================================================================================
    1. HOW DOES SPRING HANDLE THE EXCEPTION?
    ================================================================================

    DispatcherServlet receives
            UserNotFoundException
    It DOES NOT immediately return HTTP 500.

    Instead it asks
            "Is there any Exception Handler available?"

    It starts searching.
    Search Order (Simplified)

            Controller

                    ↓

            Global Exception Handler

                    ↓

            Spring Default Handler

                    ↓

            HTTP 500

    This means

    Spring first gives YOUR code
    a chance to handle the Exception.

    Only when nobody handles it,
    Spring returns HTTP 500.


    ================================================================================
    2. BEFORE GLOBAL EXCEPTION HANDLER
    ================================================================================

    Earlier developers used
            try-catch

    Example

            public User getUser(Long id){
                try{

                    ...

                }catch(Exception e){

                    ...

                }

            }

    Every API looked like this.

    Problems

    • Duplicate code
    • Difficult to maintain
    • Huge controllers
    • Different response formats


    ================================================================================
    3. SECOND APPROACH
    ================================================================================

    Developers started writing

            @ExceptionHandler

    inside every Controller.

    Example

    UserController

        @ExceptionHandler(...)

    EmployeeController

        @ExceptionHandler(...)

    LeaveController

        @ExceptionHandler(...)

    AttendanceController

        @ExceptionHandler(...)

    Same method
    copied again and again.

    Still duplication.


    ================================================================================
    4. SPRING'S SOLUTION
    ================================================================================

    Spring introduced

            @ControllerAdvice

    Think of it as

            Central Exception Office

    Instead of every controller
    handling exceptions,

    one class handles exceptions
    for the entire application.


    ================================================================================
    5. REAL PROJECT FLOW
    ================================================================================

    HRMS Example

    Client

            GET /employees/50

                    │

                    ▼

    EmployeeController

                    │

                    ▼

    EmployeeService

                    │

                    ▼

    employeeRepository.findById()

                    │

                    ▼

    Optional.empty()

                    │

                    ▼

    throw EmployeeNotFoundException

                    │

                    ▲

    Controller

                    │

                    ▲

    DispatcherServlet

                    │

                    ▼

    HandlerExceptionResolver

                    │

                    ▼

    Global Exception Handler

                    │

                    ▼

    ResponseEntity<ErrorResponse>

                    │

                    ▼

    JSON Response

                    │

                    ▼

    Client


    ================================================================================
    6. HOW DOES SPRING FIND OUR HANDLER?
    ================================================================================

    Interview Question
            Spring has thousands of classes.

    How does it know
    where our Exception Handler is?

    Answer

    During application startup,
    Spring scans all classes.

    When it finds
            @ControllerAdvice

    it registers that class
    as a Global Exception Handler.

    Later,

    whenever an Exception occurs,
    HandlerExceptionResolver checks
            "Do I have any registered handler
             capable of handling this Exception?"

    If YES
            Call that method.


    ================================================================================
    7. HOW IS THE CORRECT METHOD CHOSEN?
    ================================================================================

    Suppose

    GlobalExceptionHandler

    contains

            handleUserNotFound()
            handleDuplicateResource()
            handleRuntimeException()
            handleException()

    Service throws
            UserNotFoundException

    Spring searches

    Can this method handle it?

    handleUserNotFound()

            YES

    Stop searching.

    Execute that method.

    Spring always tries to find
    the MOST SPECIFIC matching handler.

    Example

    UserNotFoundException

            is preferred over

    RuntimeException

            which is preferred over

    Exception


    ================================================================================
    8. WHAT DOES OUR HANDLER RETURN?
    ================================================================================

    Usually

            ResponseEntity

    Why?

    Because ResponseEntity allows us to control

    • HTTP Status

    • Headers

    • Response Body

    Example Response

    HTTP 404

    {

        "errorCode":"1001",

        "message":"User not found"

    }

    Instead of

            HTTP 500


    ================================================================================
    9. WHY RESPONSEENTITY?
    ================================================================================

    Without ResponseEntity

    Spring usually returns

            HTTP 200

    But if the user doesn't exist,

    returning

            HTTP 200

    is incorrect.

    We want

            HTTP 404

    Therefore

    Exception Handler returns

            ResponseEntity

    with the correct status code.


    ================================================================================
    10. COMPLETE INTERNAL FLOW
    ================================================================================

    Client

        │

        ▼

    DispatcherServlet

        │

        ▼

    Controller

        │

        ▼

    Service

        │

        ▼

    throw Exception

        │

        ▲

    DispatcherServlet

        │

        ▼

    HandlerExceptionResolver

        │

        ▼

    Find Matching Handler

        │

    ┌───┴────────────┐

    │                │

    ▼                ▼

    Found          Not Found

    │                │

    ▼                ▼

    Execute      Default Handling

    Method           │

    │                ▼

    ▼            HTTP 500

    ResponseEntity

    │

    ▼

    HTTP Response

    │

    ▼

    Client


    ================================================================================
    11. WHAT IF MULTIPLE HANDLERS EXIST?
    ================================================================================

    Example

    Handle RuntimeException

    Handle Exception

    Handle UserNotFoundException

    Exception thrown

            UserNotFoundException

    Spring chooses

            UserNotFoundException Handler

    because it is
    the most specific match.

    This is called

            Best Match Resolution.


    ================================================================================
    12. INTERVIEW QUESTIONS
    ================================================================================

    Q1.

    Why do we use Global Exception Handler?

    Answer

    To centralize exception handling,
    remove duplicate code,
    keep controllers clean,
    and return consistent API responses.


    --------------------------------------------------


    Q2.

    How does Spring know which method to execute?

    Answer

    HandlerExceptionResolver checks all
    registered Exception Handler methods
    and invokes the most specific matching one.


    --------------------------------------------------


    Q3.

    Why is ResponseEntity used?

    Answer

    Because it allows us to control

    • HTTP Status
    • Response Body
    • Headers

    while returning an error response.


    --------------------------------------------------


    Q4.

    Does Service know about HTTP Status?

    Answer

    No.

    Service only throws business exceptions.

    HTTP concepts belong to the web layer.


    --------------------------------------------------


    Q5.

    Who finally sends the JSON response?

    Answer

    After the Exception Handler returns
    ResponseEntity,
    DispatcherServlet writes that response
    back to the client.

    ================================================================================
    NEXT FILE

    SpringExceptionAnnotations.java

    We will study

    ✔ @ExceptionHandler

    ✔ @ControllerAdvice

    ✔ @RestControllerAdvice

    ✔ @ResponseStatus

    ✔ Which annotation should be used when

    ✔ Internal differences

    ================================================================================
    */

}