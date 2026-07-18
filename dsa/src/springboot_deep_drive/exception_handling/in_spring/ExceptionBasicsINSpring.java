package springboot_deep_drive.exception_handling.in_spring;

public class ExceptionBasicsINSpring {

    /*
    ===============================================================================
                        EXCEPTION HANDLING - COMPLETE FOUNDATION
    ===============================================================================

    ===============================================================================
    1. WHAT IS AN EXCEPTION?
    ===============================================================================

    Exception means:

    "An unexpected situation that occurs while the application is running and
    interrupts the normal flow of program execution."

    Simple Meaning:

    Everything is going fine...

            User sends request
                    ↓
             Controller executes
                    ↓
              Service executes
                    ↓
            Database is queried
                    ↓
                Response returned

    Suddenly something unexpected happens.

    Examples:

    • User does not exist
    • Password is incorrect
    • Database connection lost
    • Required field is missing
    • Null object accessed
    • File not found

    These unexpected situations are called Exceptions.


    ===============================================================================
    2. REAL LIFE EXAMPLE
    ===============================================================================

    Imagine an ATM Machine.

    You insert your ATM card.

            Card Inserted
                  ↓
            Enter PIN
                  ↓
          Enter Amount
                  ↓
        Machine checks balance

    If balance is sufficient

            Money Dispensed

    But suppose balance is only ₹1000
    and you request ₹5000.

    Something unexpected happened.

    ATM does NOT crash.

    Instead it shows:

            "Insufficient Balance"

    This is Exception Handling.

    The system handled the problem gracefully.


    ===============================================================================
    3. WHAT HAPPENS INSIDE A PROGRAM?
    ===============================================================================

    Suppose

            GET /users/101

    Flow

            Client

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


    Database says

            User 101 doesn't exist.


    Service decides

            "This is an invalid business scenario."

    So Service throws

            UserNotFoundException


    IMPORTANT

    In real Spring Boot projects,
    Repository generally DOES NOT throw
    UserNotFoundException.

    Repository returns

            Optional.empty()

    Service checks

            Is user present?

                No

                ↓

    Service throws

            UserNotFoundException


    This is why we usually say

            Business Exceptions
            are thrown from Service Layer.


    ===============================================================================
    4. WHAT IS NORMAL FLOW?
    ===============================================================================

    Client

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

    Repository

      │
      ▼

    Service

      │
      ▼

    Controller

      │
      ▼

    Client


    This is called

            Normal Flow


    ===============================================================================
    5. WHAT HAPPENS WHEN EXCEPTION OCCURS?
    ===============================================================================

    Client

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

            User Not Found

      │
      ▼

    Repository returns Optional.empty()

      │
      ▼

    Service throws

            UserNotFoundException

      │
      ▼

    Normal execution STOPS immediately.

    Remaining code is NOT executed.

    JVM now starts searching

            "Who can handle this exception?"


    ===============================================================================
    6. WHAT IS EXCEPTION PROPAGATION?
    ===============================================================================

    Suppose

    Controller calls Service.

    Service throws Exception.

    Service does NOT catch it.

    Then Exception automatically moves upward.

    This movement is called

            Exception Propagation

    Flow

            Service

              │
              ▼

          Controller

              │
              ▼

      Spring Framework

              │
              ▼

          HTTP Response


    Think of it like a football.

    If Service doesn't catch it,
    it throws the ball upward.

    Whoever catches it,
    handles it.


    ===============================================================================
    7. WHY DO WE HANDLE EXCEPTIONS?
    ===============================================================================

    Imagine we don't handle anything.

    Service

            throw new UserNotFoundException();


    Nobody catches it.

    Then JVM says

            Exception is Unhandled.


    Spring cannot generate
    your custom response.


    Finally

            Client receives

            HTTP 500


    This is not good API design.


    ===============================================================================
    8. WHAT IS HTTP 500 INTERNAL SERVER ERROR?
    ===============================================================================

    One of the most common interview questions.


    Question

            Why client gets HTTP 500?


    Answer

    Because an exception occurred inside
    the server and nobody handled it.


    Example

            throw new RuntimeException();


    No catch block

            ↓

    Exception reaches Spring

            ↓

    Spring cannot resolve it

            ↓

    Request is treated as failed

            ↓

    HTTP 500 is returned


    500 means

            "Something went wrong
             on the server."


    It DOES NOT mean

    • Database always failed
    • API not found
    • Client sent wrong data

    It simply means

            Server failed
            while processing the request.


    ===============================================================================
    9. WHY DOES CLIENT RECEIVE 500?
    ===============================================================================

    Think carefully.

    Client never talks to Service directly.

    Client sends HTTP Request.

    Server must ALWAYS send
    one HTTP Response.

    Suppose

            Client

                │
                ▼

            Controller

                │
                ▼

            Service

                │
                ▼

            throw Exception


    Now what?

    The server cannot leave the connection open forever.

    It MUST send some response.

    Since nobody handled the exception,

    Server sends

            HTTP 500

    This tells the client

            "Your request reached me,
             but I failed while processing it."


    ===============================================================================
    10. BEFORE SPRING - HOW PEOPLE HANDLED EXCEPTIONS
    ===============================================================================

    Earlier developers wrote

            try
            {

            }
            catch(...)
            {

            }


    everywhere.


    Example

    Controller A

            try
            {

            }
            catch(...)
            {

            }


    Controller B

            try
            {

            }
            catch(...)
            {

            }


    Controller C

            try
            {

            }
            catch(...)
            {

            }


    Same code repeated
    hundreds of times.


    Problems

    • Duplicate code
    • Large controllers
    • Difficult maintenance
    • Every developer writes differently
    • Inconsistent API responses


    ===============================================================================
    11. NEXT IMPROVEMENT
    ===============================================================================

    Developers started writing

            Exception Handler

    inside every Controller.


    Better than try-catch.

    But still

    Controller A

        Exception Handler

    Controller B

        Exception Handler

    Controller C

        Exception Handler


    Same duplicate methods.


    Still not ideal.


    ===============================================================================
    12. FINAL SOLUTION
    ===============================================================================

    Spring introduced

            Global Exception Handling


    Instead of handling exceptions
    in every controller,

    one central class
    handles exceptions
    for the entire application.


    Benefits

    • No duplicate code
    • Clean controllers
    • Consistent responses
    • Easy maintenance
    • Centralized error handling


    (How Spring finds this class and routes exceptions to it
    will be covered in the next files.)


    ===============================================================================
    13. WHAT ARE CUSTOM EXCEPTIONS?
    ===============================================================================

    Java provides

    RuntimeException
    IllegalArgumentException
    NullPointerException
    IOException


    But business applications create

    UserNotFoundException
    InvalidCredentialException
    LeaveNotFoundException

    AttendanceNotFoundException

    EmployeeAlreadyExistsException


    Why?

    Because these names explain

            WHAT actually went wrong.


    ===============================================================================
    14. ERROR vs EXCEPTION
    ===============================================================================

    ERROR

    • JVM problem
    • Serious
    • Normally cannot recover

    Examples

    OutOfMemoryError

    StackOverflowError


    ----------------------------------------

    EXCEPTION

    • Application problem

    • Can be handled

    Examples

    UserNotFoundException

    InvalidPasswordException

    SQLException


    ===============================================================================
    15. INTERVIEW QUESTIONS
    ===============================================================================

    Q1. What is an Exception?

    An unexpected event that interrupts the normal execution
    of a program.


    ------------------------------------------------

    Q2. Why do we handle exceptions?

    To prevent application failure,
    return meaningful error messages,
    send proper HTTP status codes,
    and keep application code clean.


    ------------------------------------------------

    Q3. Why do clients receive HTTP 500?

    Because an exception occurred on the server
    and no one handled it properly.


    ------------------------------------------------

    Q4. Where are business exceptions usually thrown?

    Mostly in the Service Layer,
    because business rules are implemented there.


    ------------------------------------------------

    Q5. What is Exception Propagation?

    The process in which an exception moves upward
    through the call stack until someone handles it.

    call stack

    ---------------------
Repository
---------------------
Service
---------------------
Controller
---------------------



    ===============================================================================
    NEXT FILE
    ===============================================================================

    SpringExceptionPropagation.java

    In the next file we will answer:

    • How does Spring know an exception occurred?
    • Who catches uncaught exceptions?
    • What is DispatcherServlet's role?
    • What is HandlerExceptionResolver?
    • How does Spring choose the correct handler?
    • How does ResponseEntity reach the client?
    • Complete internal working with diagrams.

    ===============================================================================
    */

}