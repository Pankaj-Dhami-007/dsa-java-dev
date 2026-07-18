package springboot_deep_drive.exception_handling.in_spring.global_exception_handler;

public class GlobalExceptionHandlerInternals_4 {

    /*
    ================================================================================
                HOW SPRING FINDS YOUR @ControllerAdvice
    ================================================================================

    Previous Flow

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

    -------------------------------------------------------------------------------

    Now Question

            How does Spring know

            where our

            GlobalExceptionHandler

            class is?

    ================================================================================



    ================================================================================
    1. APPLICATION STARTUP
    ================================================================================

    When Spring Boot starts

    it creates

            ApplicationContext

    During startup

    Spring performs

            Component Scanning

    It searches classes having

    @Component

    @Service

    @Repository

    @Controller

    @RestController

    @ControllerAdvice

    etc.

    Every discovered class

    becomes a Spring Bean.

    Therefore

    GlobalExceptionHandler

    is also created as

    a Spring Bean.

    IMPORTANT

    Spring does NOT search
    for this class

    every time an Exception occurs.

    It is already available
    inside ApplicationContext.



    ================================================================================
    2. EXCEPTIONHANDLEREXCEPTIONRESOLVER
       INITIALIZATION
    ================================================================================

    During initialization

    ExceptionHandlerExceptionResolver

    asks ApplicationContext

            Give me all

            @ControllerAdvice Beans.

    Spring returns

            GlobalExceptionHandler

            ValidationExceptionHandler

            SecurityExceptionHandler

            ...

    if they exist.

    These are wrapped as

            ControllerAdviceBean

    objects.



    ================================================================================
    3. WHAT IS ControllerAdviceBean ?
    ================================================================================

    It is NOT

    your exception handler.

    It is a wrapper.

    Think

            Information Card

    Instead of storing

            only the object,

    Spring stores

    information like

    • Bean

    • Bean Type

    • Order

    • Applicable Controllers

    etc.

    Why?

    Because later

    Spring needs quick access

    to these details.



    ================================================================================
    4. NEXT STEP
    ================================================================================

    Spring now has

            ControllerAdviceBean

    But

    it still doesn't know

    which method handles

            UserNotFoundException

    Example

    GlobalExceptionHandler

    may contain

            handleUser()

            handleValidation()

            handleRuntime()

            handleException()

    Spring now analyses

    every method.

    It looks for

            @ExceptionHandler



    ================================================================================
    5. ExceptionHandlerMethodResolver
    ================================================================================

    This is another helper class.

    Responsibility

    Read all methods

    having

            @ExceptionHandler

    and create a mapping.

    Example

    Spring finds

            @ExceptionHandler(
                    UserNotFoundException.class)

    It creates

    Mapping

    UserNotFoundException

            →

    handleUserNotFound()

    Another

    RuntimeException

            →

    handleRuntime()

    Another

    Exception

            →

    handleException()

    It builds this map

    only once

    during initialization.



    ================================================================================
    6. WHY CREATE A MAP?
    ================================================================================

    Imagine

    every request

    Spring searches

    all methods

    using Reflection.

    Terrible Performance.

    Instead

    Spring builds

    a lookup table.

    Example

    ------------------------------------------------

    UserNotFoundException

        →

    Method A

    ------------------------------------------------

    RuntimeException

        →

    Method B

    ------------------------------------------------

    Exception

        →

    Method C

    ------------------------------------------------

    Finding a method

    becomes extremely fast.



    ================================================================================
    7. NOW EXCEPTION OCCURS
    ================================================================================

    Service throws

            UserNotFoundException

    DispatcherServlet

            ↓

    ExceptionHandlerExceptionResolver

            ↓

    Finds matching

    ControllerAdviceBean

            ↓

    Uses

    ExceptionHandlerMethodResolver

            ↓

    Lookup

            UserNotFoundException

            →

    handleUserNotFound()

            ↓

    Invoke Method



    ================================================================================
    8. HOW IS METHOD INVOKED?
    ================================================================================

    Spring uses

            Reflection

    It calls

    your method

    automatically.

    You never write

            handler.handle(...)

    Spring does it

    for you.

    That is why

    your method executes

    even though

    you never call it.



    ================================================================================
    9. WHAT IF THERE IS NO EXACT MATCH?
    ================================================================================

    Example

    You have

            @ExceptionHandler(Exception.class)

    But

    Exception thrown is

            UserNotFoundException

    Spring checks

    inheritance.

    UserNotFoundException

            extends

    RuntimeException

            extends

    Exception

    Therefore

    handle(Exception.class)

    can also handle it.

    But

    if

    handle(UserNotFoundException.class)

    exists,

    Spring chooses

    that one.

    Spring always prefers

    the MOST SPECIFIC handler.



    ================================================================================
    10. COMPLETE FLOW
    ================================================================================

    Application Startup

            │

            ▼

    Component Scan

            │

            ▼

    GlobalExceptionHandler Bean

            │

            ▼

    ControllerAdviceBean

            │

            ▼

    ExceptionHandlerMethodResolver

            │

            ▼

    Cache Created

    ------------------------------------------------

    UserNotFoundException

            →

    handleUser()

    RuntimeException

            →

    handleRuntime()

    Exception

            →

    handleException()

    ------------------------------------------------

    Runtime

            │

            ▼

    Exception Occurs

            │

            ▼

    Direct Lookup

            │

            ▼

    Invoke Method



    ================================================================================
    11. INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Does Spring scan every class
    whenever an Exception occurs?

    Answer

    No.

    Scanning happens during application
    initialization.

    Later Spring uses cached metadata.


    ------------------------------------------------


    Q.

    Why does Spring use
    ExceptionHandlerMethodResolver?

    Answer

    To analyze @ExceptionHandler methods,
    build exception-to-method mappings,
    and perform fast lookups.


    ------------------------------------------------


    Q.

    Why does Spring use Reflection?

    Answer

    Because it needs to invoke
    methods dynamically without
    compile-time knowledge.


    ------------------------------------------------


    Q.

    Which handler is chosen
    if multiple handlers match?

    Answer

    Spring chooses the most
    specific matching exception type.

    ================================================================================
    NEXT PART
    ================================================================================

    Your @ExceptionHandler method
    has executed.

            ↓

    It returned

            ResponseEntity<ErrorResponse>

            ↓

    How does ResponseEntity

    become

            HTTP Response ?

    Where does JSON come from?

    What is HttpMessageConverter?

    How does Tomcat finally
    send the response?

    This is the last part.

    ================================================================================
    */

}