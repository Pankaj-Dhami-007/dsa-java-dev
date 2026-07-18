package springboot_deep_drive.exception_handling.in_spring.global_exception_handler;

public class GlobalExceptionHandlerInternals_3 {

    /*
    ================================================================================
                SPRING MVC INTERNALS - EXCEPTION RESOLUTION
    ================================================================================

    Previous Flow

    Client

        │

        ▼

    Tomcat

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

        ▲

        │

    DispatcherServlet catches Exception

        │

        ▼

    processDispatchResult()

        │

        ▼

    processHandlerException()

    -------------------------------------------------------------------------------

    Now the question is

            How does Spring find

            our Global Exception Handler?

    ================================================================================



    ================================================================================
    1. processHandlerException()
    ================================================================================

    This method belongs to

            DispatcherServlet

    Responsibility

    Given an Exception,

    find someone capable of handling it.

    Think of it like

            HR Department

    An employee has a problem.

    HR asks

            "Who is responsible
             for handling this case?"

    Spring does exactly the same.

    It DOES NOT know

            who should handle

            UserNotFoundException.

    It asks another component.

    ================================================================================



    ================================================================================
    2. HANDLEREXCEPTIONRESOLVER
    ================================================================================

    DispatcherServlet delegates the work to

            HandlerExceptionResolver

    IMPORTANT

    HandlerExceptionResolver

    is an INTERFACE.

    It is NOT a class.

    Interview Question

    Why Interface?

    Because Spring allows

    multiple implementations

    of exception handling.

    This follows

            Open/Closed Principle

    Spring can add new resolvers

    without changing DispatcherServlet.

    DispatcherServlet simply says

            "Resolver,

             please resolve this Exception."



    ================================================================================
    3. WHY NOT PUT EVERYTHING
       INSIDE DISPATCHERSERVLET?
    ================================================================================

    Suppose DispatcherServlet itself handled

    every possible Exception.

    Then it would contain

    thousands of lines.

    It would violate

            Single Responsibility Principle.

    Therefore

    DispatcherServlet only coordinates.

    Actual exception resolution

    is delegated.

    This is called

            Delegation Pattern.



    ================================================================================
    4. MULTIPLE RESOLVERS
    ================================================================================

    Spring does NOT have

    only one Resolver.

    It has multiple Resolvers.

    Think

            Team of Specialists.

    Example

    Resolver A

            Handles

            @ExceptionHandler

    Resolver B

            Handles

            @ResponseStatus

    Resolver C

            Handles

            Spring MVC Exceptions

    DispatcherServlet

    asks them

    one by one.

    This group is called

            HandlerExceptionResolverComposite



    ================================================================================
    5. WHAT IS
       HandlerExceptionResolverComposite?
    ================================================================================

    Think of it as

            Team Leader

    Instead of

    DispatcherServlet calling

    every Resolver,

    DispatcherServlet calls only

            HandlerExceptionResolverComposite

    Composite internally contains

    multiple Resolvers.

    Flow

    DispatcherServlet

            │

            ▼

    HandlerExceptionResolverComposite

            │

     ┌──────┼───────────────┐

     ▼      ▼               ▼

    R1      R2              R3

    Each Resolver gets

    one chance

    to resolve the Exception.



    ================================================================================
    6. HOW DOES COMPOSITE WORK?
    ================================================================================

    Imagine

    Exception

            UserNotFoundException

    Composite asks

    Resolver 1

            Can you handle it?

    NO

            ↓

    Resolver 2

            Can you handle it?

    YES

            ↓

    Stop searching.

    Return the result.

    Remaining Resolvers

    are NOT executed.

    This improves performance.



    ================================================================================
    7. WHICH RESOLVER
       HANDLES @ExceptionHandler ?
    ================================================================================

    This is the most important Resolver.

    Name

            ExceptionHandlerExceptionResolver

    Long name

    But responsibility is simple.

    It knows how to execute

            @ExceptionHandler

    methods.

    Example

            @ExceptionHandler(
                UserNotFoundException.class)

    This Resolver knows

    how to find

    this method

    and execute it.



    ================================================================================
    8. WHAT DOES THIS RESOLVER DO?
    ================================================================================

    DispatcherServlet

            │

            ▼

    Composite

            │

            ▼

    ExceptionHandlerExceptionResolver

            │

            ▼

    Search

    Do we have

    any matching

    @ExceptionHandler

    method?

    If YES

            Execute it.

    If NO

            Return NULL

    and allow

    next Resolver

    to try.



    ================================================================================
    9. IMPORTANT POINT
    ================================================================================

    Resolver DOES NOT

    search every class

    on every request.

    That would be very slow.

    Spring already knows

    where

            @ControllerAdvice

    classes are.

    Why?

    Because during application startup,

    Spring scanned them

    and stored their metadata.

    (We will study this in Part 4.)



    ================================================================================
    10. COMPLETE FLOW
    ================================================================================

    Service

        │

        ▼

    throw Exception

        ▲

        │

    DispatcherServlet

        │

        ▼

    processHandlerException()

        │

        ▼

    HandlerExceptionResolverComposite

        │

        ▼

    ExceptionHandlerExceptionResolver

        │

        ▼

    ???

    Now Spring must answer

            Which

            @ExceptionHandler

            method should execute?

    That is exactly

    Part 4.



    ================================================================================
    11. INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Why is HandlerExceptionResolver
    an Interface?

    Answer

    Because Spring supports multiple
    exception resolution strategies.

    Different implementations
    can resolve different kinds
    of exceptions.


    --------------------------------------------------


    Q.

    Why does DispatcherServlet
    delegate exception handling?

    Answer

    To keep DispatcherServlet focused
    on request coordination.

    Exception resolution is delegated
    to specialized components,
    following the Single Responsibility
    Principle.


    --------------------------------------------------


    Q.

    What is
    HandlerExceptionResolverComposite?

    Answer

    It is a composite implementation
    that contains multiple
    HandlerExceptionResolvers and
    asks them one by one until
    one resolves the exception.


    --------------------------------------------------


    Q.

    Which Resolver executes
    @ExceptionHandler methods?

    Answer

    ExceptionHandlerExceptionResolver.


    ================================================================================
    NEXT PART
    ================================================================================

    We will answer

    Spring already found

            ExceptionHandlerExceptionResolver

    But

    How does it find

            GlobalExceptionHandler ?

    How does it know

            which method

            handles

            UserNotFoundException ?

    We will study

    ControllerAdviceBean

    ExceptionHandlerMethodResolver

    Method Matching Algorithm

    Cache

    Startup Scanning

    ================================================================================

    */

}