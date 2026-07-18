package springboot_deep_drive.exception_handling.in_spring.global_exception_handler;

public class GlobalExceptionHandlerInternals {

    /*
    ================================================================================
                    SPRING MVC INTERNALS - REQUEST LIFECYCLE
    ================================================================================

    Before understanding Exception Handling,
    we must understand

            "How a Request reaches the Controller?"

    Because

    if we don't know

            how Controller executes,

    we can never understand

            how Exception Handling works.

    ================================================================================
    1. WHO RECEIVES THE HTTP REQUEST FIRST?
    ================================================================================

    Imagine you call

            GET /users/10

    From Postman

                    │

                    ▼

    Internet

                    │

                    ▼

    Your Spring Boot Application

    But wait...

    Does the request directly go to Controller?

            NO

    Does it directly go to Service?

            NO

    There are many components
    before Controller.


    ================================================================================
    2. TOMCAT
    ================================================================================

    First component

            Tomcat

    ----------------------------------------------------------------------------

    Interview Question

    Q.

    What is Tomcat?

    Answer

    Tomcat is a Servlet Container.

    It receives HTTP Requests,
    executes Servlets,
    and sends HTTP Responses.

    ----------------------------------------------------------------------------

    Think of Tomcat as

            Receptionist

    Someone enters the office.

    Receptionist says

            "Let me find the correct person."

    Tomcat never executes
    your business logic.

    Tomcat only manages

    • HTTP Request

    • HTTP Response

    • Servlets

    ----------------------------------------------------------------------------

    Flow

    Client

        │

        ▼

    Tomcat


    ================================================================================
    3. WHAT IS A SERVLET?
    ================================================================================

    Before Spring existed,

    developers wrote Servlets.

    Example

            LoginServlet

            UserServlet

            EmployeeServlet

            LeaveServlet

    Every URL

    had its own Servlet.

    Problems

    • Huge code duplication

    • Difficult maintenance

    • Difficult routing

    • Difficult testing

    Spring solved this problem.

    Instead of

            100 Servlets

    Spring created

            ONE Servlet

    called

            DispatcherServlet


    ================================================================================
    4. WHAT IS DISPATCHERSERVLET?
    ================================================================================

    One of the MOST IMPORTANT classes
    in Spring MVC.

    It is called

            Front Controller

    ----------------------------------------------------------------------------

    Why was it created?

    Because

    we don't want

    LoginServlet

    UserServlet

    LeaveServlet

    EmployeeServlet

    etc.

    Instead

    ONE central servlet

    receives every request.

    ----------------------------------------------------------------------------

    Client

        │

        ▼

    Tomcat

        │

        ▼

    DispatcherServlet

    Every request

    passes through it.

    Every response

    also passes through it.

    DispatcherServlet is the heart
    of Spring MVC.


    ================================================================================
    5. RESPONSIBILITIES OF DISPATCHERSERVLET
    ================================================================================

    DispatcherServlet does NOT
    contain business logic.

    It coordinates everything.

    Responsibilities

    ✔ Receive Request

    ✔ Find Controller

    ✔ Execute Controller

    ✔ Catch Exceptions

    ✔ Generate Response

    ✔ Return Response to Tomcat

    Think of it as

            Project Manager

    It doesn't do every task itself.

    It delegates work
    to different Spring components.


    ================================================================================
    6. INTERNAL FLOW
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

    ???

    Question

    How does DispatcherServlet know

    which Controller

    should execute?

    Suppose application has

    300 Controllers.

    How does it know

    where

            GET /users/10

    should go?

    It doesn't know.

    So it asks another Spring component.


    ================================================================================
    7. HANDLERMAPPING
    ================================================================================

    New Component

            HandlerMapping

    Think of it as

            Google Maps

    ----------------------------------------------------------------------------

    DispatcherServlet asks

            I received

            GET /users/10

            Which Controller
            should execute?

    HandlerMapping searches

    all registered Controllers.

    It finds

            UserController

    and returns it.

    ----------------------------------------------------------------------------

    Flow

    DispatcherServlet

            │

            ▼

    HandlerMapping

            │

            ▼

    UserController Found

    ----------------------------------------------------------------------------

    Interview

    Q.

    Why HandlerMapping?

    Answer

    DispatcherServlet cannot remember
    every Controller and every URL.

    HandlerMapping resolves

            URL

                →

            Controller


    ================================================================================
    8. CAN DISPATCHERSERVLET EXECUTE CONTROLLER?
    ================================================================================

    Interview Question

    DispatcherServlet found

            UserController

    Can it execute it directly?

            NO

    Why?

    Because Spring supports

    • Normal Controllers

    • REST Controllers

    • Different handler types

    DispatcherServlet doesn't know
    how to invoke every possible handler.

    So again

    it delegates.


    ================================================================================
    9. HANDLERADAPTER
    ================================================================================

    New Component

            HandlerAdapter

    Think of it as

            Universal Remote

    ----------------------------------------------------------------------------

    DispatcherServlet says

            Here is the Controller.

            Please execute it.

    HandlerAdapter knows

    how to invoke

            Controller Methods.

    ----------------------------------------------------------------------------

    Flow

    DispatcherServlet

            │

            ▼

    HandlerAdapter

            │

            ▼

    UserController.execute()


    ================================================================================
    10. COMPLETE REQUEST FLOW
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

    Find Controller

        │

        ▼

    HandlerAdapter

        │

        ▼

    Execute Controller

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

    Until here

    everything is normal.

    We have NOT discussed
    Exception Handling yet.


    ================================================================================
    11. SPRING SOURCE CODE (SIMPLIFIED)
    ================================================================================

    DispatcherServlet internally does
    something conceptually similar to:

            doDispatch(request){

                // Find Controller

                getHandler(request);

                // Execute Controller

                adapter.handle();

            }

    NOTE

    This is NOT the exact source code.

    It is only a simplified version
    to understand the flow.


    ================================================================================
    12. INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Why is DispatcherServlet called
    Front Controller?

    Answer

    Because every incoming request
    first reaches DispatcherServlet,
    and every outgoing response
    also passes through it.


    ------------------------------------------------


    Q.

    What is the responsibility
    of HandlerMapping?

    Answer

    It maps an incoming URL
    to the correct Controller.


    ------------------------------------------------


    Q.

    Why do we need HandlerAdapter?

    Answer

    DispatcherServlet knows how to
    coordinate the request, but it
    delegates the actual controller
    invocation to HandlerAdapter.


    ------------------------------------------------


    Q.

    Does DispatcherServlet execute
    business logic?

    Answer

    No.

    Business logic belongs
    to the Service Layer.

    DispatcherServlet only coordinates
    request processing.


    ================================================================================
    NEXT PART
    ================================================================================

    Now the interesting part begins.

    We already know

    Client
        ↓
    Tomcat
        ↓
    DispatcherServlet
        ↓
    HandlerMapping
        ↓
    HandlerAdapter
        ↓
    Controller
        ↓
    Service

    Next we will answer

    Service throws Exception

            ↓

    How does DispatcherServlet know
    an Exception occurred?

    Does Controller catch it?

    Does JVM catch it?

    Where exactly is the catch block?

    We will read Spring's internal
    flow around doDispatch()
    step by step.

    ================================================================================
    */

}