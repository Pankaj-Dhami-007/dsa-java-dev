package springboot_deep_drive.exception_handling.in_spring;

public class SpringExceptionPropagation {

    /*
    ================================================================================
                    SPRING EXCEPTION PROPAGATION - INTERNAL WORKING
    ================================================================================

    In the previous file we learned:

    ✔ What is Exception
    ✔ Why do we handle Exceptions
    ✔ What is Exception Propagation

    In this file we will understand

    • How Spring knows an Exception occurred?
    • Who catches the Exception?
    • Why Controller doesn't need try-catch?
    • Why HTTP 500 comes?
    • What is DispatcherServlet's role?
    • What is HandlerExceptionResolver?
    • How does Spring convert Exception into HTTP Response?

    ================================================================================
    1. COMPLETE REQUEST LIFECYCLE
    ================================================================================

    Suppose client sends

            GET /users/10

    Request Flow

            Client

               │

               ▼

        DispatcherServlet

               │

               ▼

         UserController

               │

               ▼

          UserService

               │

               ▼

        UserRepository

               │

               ▼

            Database

    Until now everything is normal.


    ================================================================================
    2. USER DOES NOT EXIST
    ================================================================================

    Repository executes

            findById(10)

    Database returns

            No Record Found

    Repository returns

            Optional.empty()

    Repository DOES NOT throw

            UserNotFoundException

    Service decides

            This user should exist.

    So Service throws

            UserNotFoundException


    ================================================================================
    3. WHAT HAPPENS IMMEDIATELY AFTER THROW?
    ================================================================================

    Suppose Service executes

            throw new UserNotFoundException();

    VERY IMPORTANT

    The moment JVM sees "throw"
    It immediately stops executing the remaining code
    inside that method.

    Example

            throw new UserNotFoundException();
            System.out.println("Hello");

    "Hello" will NEVER execute.

    Why?
    Because execution has already stopped.


    ================================================================================
    4. JVM STARTS SEARCHING
    ================================================================================

    Now JVM asks
            "Who can handle this Exception?"

    First it checks
            Current Method

    Is there a catch block?

            YES → Handle it
            NO  → Move upward

    This moving upward is called
            Exception Propagation


    ================================================================================
    5. PROPAGATION STEP BY STEP
    ================================================================================

    Request Flow

        Controller
             │
             ▼
        Service
             │
             ▼
        Repository

    Repository returns Optional.empty()

             │

             ▼

    Service throws UserNotFoundException

             │

             ▼

    JVM checks Service

             │

             ▼

    No catch found

             │

             ▼

    JVM goes to Controller

             │

             ▼

    No catch found

             │

             ▼

    JVM goes to DispatcherServlet


    ================================================================================
    6. WHAT IS DISPATCHERSERVLET?
    ================================================================================

    One of the most important classes in Spring MVC.

    Think of DispatcherServlet as
            The Front Controller
    It receives EVERY incoming HTTP request.

    Client

        │

        ▼

    DispatcherServlet

        │

        ▼

    Controller

    And when the controller finishes,

    response also comes back through DispatcherServlet.
    So every request and every response passes through it.


    ================================================================================
    7. WHY DOES EXCEPTION REACH DISPATCHERSERVLET?
    ================================================================================

    Because

    Controller never handled it.
    Service never handled it.

    JVM keeps propagating the exception.

    Finally it reaches

            DispatcherServlet

    DispatcherServlet now says
            "Controller execution failed."

    It now has to decide
            Can someone handle this Exception?


    ================================================================================
    8. HANDLEREXCEPTIONRESOLVER
    ================================================================================

    DispatcherServlet itself does NOT know
    how to handle every Exception.

    So it asks another Spring component
            HandlerExceptionResolver

    Think of it like
            Exception Manager

    DispatcherServlet

            │

            ▼

    HandlerExceptionResolver

            │

            ▼

    "Can anybody handle this Exception?"


    ================================================================================
    9. WHAT DOES HANDLEREXCEPTIONRESOLVER DO?
    ================================================================================

    It starts searching.

    Example
            UserNotFoundException

    It searches

    Do we have any method
    capable of handling
            UserNotFoundException ?
    If YES
            Execute that method.

    If NO
            Continue searching.

    (How it searches will be discussed
    in the next file.)


    ================================================================================
    10. IF NOBODY HANDLES THE EXCEPTION?
    ================================================================================

    Suppose

    Service throws
            RuntimeException

    There is

    No try-catch
    No Global Handler
    No Exception Handler

    Then
    DispatcherServlet cannot resolve it.

    Result
            Request Failed

    Server returns
            HTTP 500


    ================================================================================
    11. WHY DOES SERVER RETURN HTTP 500?
    ================================================================================

    Very common interview question.

    Think carefully.

    Client has already sent an HTTP Request.
    Server MUST send exactly one HTTP Response.
    There are only two possibilities.

    Request processed successfully
            OR
    Request processing failed.

    Since Exception was never handled,
    Spring considers
            Request Processing Failed

    Therefore
            HTTP 500 Internal Server Error
    is returned.

    500 does NOT mean
            Database Error

    500 does NOT mean
            Bug always exists

    500 simply means
            Server failed while processing
            this request.


    ================================================================================
    12. WHO CREATES HTTP 500?
    ================================================================================

    Another interview question.

    Many developers think
            Service returns 500

    Wrong.

    Service knows NOTHING
    about HTTP.

    Service only throws Exception.
    Controller also didn't return 500.
    DispatcherServlet also didn't directly create it.

    The Servlet container (like Tomcat) recognizes that
    request processing ended with an unhandled exception.

    Spring Boot then provides a default error response
    (through its default error handling) so the client
    receives a structured HTTP 500 response instead of
    a raw server error page.


    ================================================================================
    13. COMPLETE INTERNAL FLOW
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

    Repository

         │

         ▼

    Database

         │

         ▼

    Optional.empty()

         │

         ▼

    Service throws Exception

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

    Can someone handle it?

         │

     ┌───┴──────────────┐

     │                  │

    YES                NO

     │                  │

     ▼                  ▼

    Execute         HTTP 500
    Handler


    ================================================================================
    14. REAL LIFE EXAMPLE
    ================================================================================

    Think of a company hierarchy.

    Employee

        │

        ▲

    Team Lead

        │

        ▲

    Manager

        │

        ▲

    Director

    Employee cannot solve a problem.
    It goes to Team Lead.
    Team Lead cannot solve it.
    Goes to Manager.
    Manager cannot solve it.
    Goes to Director.
    This upward movement

    is exactly
            Exception Propagation.


    ================================================================================
    15. INTERVIEW QUESTIONS
    ================================================================================

    Q1.

    Why doesn't Service return HTTP 500?

    Answer:

    Because Service belongs to the business layer.
    It knows nothing about HTTP.
    It only throws exceptions.


    --------------------------------------------------


    Q2.

    Who receives the exception after Controller?

    Answer:

    DispatcherServlet.


    --------------------------------------------------


    Q3.

    What does DispatcherServlet do?

    Answer:

    It is the Front Controller of Spring MVC.
    It receives every request and coordinates request
    processing. If an exception reaches it, it delegates
    exception handling to the HandlerExceptionResolver.


    --------------------------------------------------


    Q4.

    What is Exception Propagation?

    Answer:

    The automatic movement of an exception
    from the current method to its caller
    until some code handles it.


    --------------------------------------------------


    Q5.

    Who converts Exception into HTTP Response?

    Short Answer:

    Spring's exception handling mechanism
    (through DispatcherServlet and
    HandlerExceptionResolver).


    ================================================================================
    NEXT FILE
    ================================================================================

    SpringExceptionHandlerExample.java

    We will now learn

    ✔ @ExceptionHandler

    ✔ @ControllerAdvice

    ✔ @RestControllerAdvice

    ✔ How HandlerExceptionResolver finds our method

    ✔ How ResponseEntity is created

    ✔ Practical HRMS examples

    ================================================================================
    */

}