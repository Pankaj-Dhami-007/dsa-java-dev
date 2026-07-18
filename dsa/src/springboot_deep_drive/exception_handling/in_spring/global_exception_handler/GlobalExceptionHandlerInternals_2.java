package springboot_deep_drive.exception_handling.in_spring.global_exception_handler;

public class GlobalExceptionHandlerInternals_2 {

    /*
    ================================================================================
                    SPRING MVC INTERNALS - EXCEPTION FLOW
    ================================================================================

    In previous part

    Client

        │

        ▼

    Tomcat

        │

        ▼

    DispatcherServlet

        │

        ▼

    HandlerMapping

        │

        ▼

    HandlerAdapter

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

    Now imagine

    Service throws

            UserNotFoundException

    Question

    Spring ko kaise pata chalega?

    ================================================================================
    1. CONTROLLER DOES NOT CATCH IT
    ================================================================================

    Suppose

    Service

            throw new UserNotFoundException();

    Controller

    does NOT have

            try-catch

    So what happens?

    JVM starts

            Exception Propagation

    Exception moves

    Service

        ▲

    Controller

    Controller also doesn't catch it.

    Exception keeps moving.

    Finally

    DispatcherServlet receives it.

    VERY IMPORTANT

    DispatcherServlet doesn't magically know.

    Exception PROPAGATES
    until it reaches DispatcherServlet.


    ================================================================================
    2. WHERE DOES DISPATCHERSERVLET CATCH IT?
    ================================================================================

    Spring internally has

            doDispatch()

    Simplified version

            try{

                Execute Controller

            }
            catch(Exception ex){

                dispatchException = ex;

            }

    IMPORTANT

    THIS is the catch block

    because of which

    DispatcherServlet knows

    an Exception occurred.

    Interview

    Q.

    How does DispatcherServlet know
    an Exception occurred?

    Answer

    Because controller execution
    happens inside a try-catch block
    in DispatcherServlet's internal
    request processing.


    ================================================================================
    3. WHAT IS dispatchException ?
    ================================================================================

    Think of it as

            A variable

    used to store

    the Exception object.

    Example

            UserNotFoundException

    gets stored inside

            dispatchException

    Spring now has

    the complete Exception object.

    It knows

    • Exception Type

    • Message

    • Stack Trace

    • Cause

    Everything.


    ================================================================================
    4. DOES DISPATCHERSERVLET RETURN 500 NOW?
    ================================================================================

    NO.

    This is a very common misunderstanding.

    DispatcherServlet first asks

            Can someone handle
            this Exception?

    It NEVER immediately returns

            HTTP 500

    Why?

    Because maybe

    YOU have written

            Global Exception Handler.


    ================================================================================
    5. processDispatchResult()
    ================================================================================

    After Controller execution finishes,

    DispatcherServlet calls

            processDispatchResult()

    Think of it as

            Final Decision Maker

    It checks

    Did Controller return normally?

            OR

    Did Controller throw Exception?

    If everything is fine

            Return Response

    If Exception exists

            Process Exception


    ================================================================================
    6. SIMPLIFIED INTERNAL FLOW
    ================================================================================

            doDispatch(){

                try{

                    Execute Controller

                }
                catch(Exception ex){

                    dispatchException = ex;

                }

                processDispatchResult(
                        dispatchException);

            }

    This is NOT exact source code.

    It is simplified
    for understanding.


    ================================================================================
    7. WHAT DOES processDispatchResult() DO?
    ================================================================================

    Suppose

    dispatchException

    is NOT NULL.

    That means

            An Exception occurred.

    It immediately calls

            processHandlerException()

    Think

            "Exception Processing Department"


    ================================================================================
    8. FLOW UNTIL NOW
    ================================================================================

    Client

        │

        ▼

    Tomcat

        │

        ▼

    DispatcherServlet

        │

        ▼

    HandlerMapping

        │

        ▼

    HandlerAdapter

        │

        ▼

    Controller

        │

        ▼

    Service

        │

        ▼

    throw Exception

        ▲

        │

    Controller

        ▲

        │

    DispatcherServlet

        │

        ▼

    catch(Exception ex)

        │

        ▼

    dispatchException

        │

        ▼

    processDispatchResult()

        │

        ▼

    processHandlerException()

    We stop here.

    Next part starts from

            processHandlerException()


    ================================================================================
    9. REAL LIFE EXAMPLE
    ================================================================================

    Think of DispatcherServlet

    as a School Principal.

    Teacher (Controller)

    calls Student (Service)

    Student creates a problem
    (Exception).

    Teacher cannot solve it.

    Teacher sends student
    to Principal.

    Principal DOES NOT punish immediately.

    Principal first asks

            "Is there any class teacher
             responsible for this student?"

    This is exactly what

    processHandlerException()

    will do.


    ================================================================================
    10. INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Does Controller catch
    every Exception?

    Answer

    No.

    Usually Controllers don't contain
    try-catch blocks.

    Exceptions propagate to
    DispatcherServlet.


    ------------------------------------------------


    Q.

    How does DispatcherServlet know
    about an Exception?

    Answer

    Controller execution happens
    inside DispatcherServlet's
    internal try-catch block.


    ------------------------------------------------


    Q.

    Does DispatcherServlet immediately
    return HTTP 500?

    Answer

    No.

    First it tries to resolve
    the Exception by calling

            processHandlerException().

    HTTP 500 is returned only
    if the Exception remains
    unresolved.


    ================================================================================
    NEXT PART
    ================================================================================

    processHandlerException()

            ↓

    HandlerExceptionResolverComposite

            ↓

    ExceptionHandlerExceptionResolver

            ↓

    @ControllerAdvice

            ↓

    @ExceptionHandler

    This is where Spring
    actually starts searching
    for your Global Exception Handler.

    ================================================================================
    */

}