package springboot_deep_drive.exception_handling.in_springboot;

public class BasicErrorController {

    /*
    ================================================================================
                    SPRING BOOT EXCEPTION HANDLING
                            BASIC ERROR CONTROLLER
    ================================================================================

    ================================================================================
    1. WHAT PROBLEM DOES SPRING BOOT SOLVE?
    ================================================================================

    We already know

            Spring Framework

    can resolve Exceptions.

    Example

    Service

            throw new RuntimeException();

    DispatcherServlet

            ↓

    HandlerExceptionResolver

            ↓

    No matching handler found.

    Question

    Now what?

    Should client receive

            nothing?

            NO

    Should connection remain open forever?

            NO

    Every HTTP Request

    MUST receive

    exactly one HTTP Response.

    Spring Framework knows

    an Exception occurred,

    but it does not automatically create
    a rich JSON error response.

    Spring Boot improves this experience.

    ================================================================================
    2. BEFORE SPRING BOOT
    ================================================================================

    In plain Spring MVC

    developers usually had to create
    their own error pages
    or their own error controllers.

    Every project looked different.

    Error JSON format

    was also different.

    ================================================================================
    3. WHAT DID SPRING BOOT ADD?
    ================================================================================

    Spring Boot introduced

            BasicErrorController

    Think

            Default Error Controller

    It is automatically configured.

    You never create it.

    You never call it.

    Spring Boot registers it for you.

    ================================================================================
    4. WHEN IS BasicErrorController USED?
    ================================================================================

    Scenario 1

    You created

            @RestControllerAdvice

    Exception handled successfully.

    Result

            BasicErrorController

            is NOT used.

    ------------------------------------------------

    Scenario 2

    Exception occurs

    Nobody handles it.

    No matching

            @ExceptionHandler

    exists.

    DispatcherServlet cannot resolve it.

    Spring Boot forwards
    the request to

            /error

    Guess who handles

            /error

    ?

            BasicErrorController

    ================================================================================
    5. IMPORTANT FLOW
    ================================================================================

    Service

        │

        ▼

    Exception

        │

        ▼

    DispatcherServlet

        │

        ▼

    HandlerExceptionResolver

        │

        ▼

    Exception handled?

       │

    ┌──┴─────────────┐

    │                │

    ▼                ▼

    YES              NO

    │                │

    ▼                ▼

    ResponseEntity   Forward to /error

                     │

                     ▼

             BasicErrorController

                     │

                     ▼

             Default JSON Response

    ================================================================================
    6. WHY DON'T WE SEE BasicErrorController?
    ================================================================================

    Because Spring Boot creates it
    automatically.

    This is called

            Auto Configuration.

    You simply add

            spring-boot-starter-web

    and Boot registers

            BasicErrorController

    behind the scenes.

    ================================================================================
    7. DOES EVERY REQUEST GO TO BasicErrorController?
    ================================================================================

    NO.

    This is a common misunderstanding.

    Normal Request

            Controller

            ↓

            Success

    BasicErrorController

            NOT USED.

    Only

    unresolved errors

    reach it.

    ================================================================================
    8. WHAT DOES BasicErrorController RETURN?
    ================================================================================

    By default

    something like

    {

        "timestamp": "...",

        "status": 500,

        "error": "Internal Server Error",

        "path": "/users"

    }

    But

    Question

    Where did these fields come from?

    timestamp?

    status?

    path?

    message?

    BasicErrorController
    doesn't create them itself.

    Another component does.

    That component is

            ErrorAttributes.

    ================================================================================
    9. INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Does BasicErrorController
    handle every Exception?

    Answer

    No.

    It mainly handles requests
    forwarded to /error when no custom
    exception handler has resolved
    the exception.

    ------------------------------------------------

    Q.

    Why do we rarely write
    BasicErrorController ourselves?

    Answer

    Because Spring Boot
    auto-configures a default one.

    ------------------------------------------------

    Q.

    Is BasicErrorController
    part of Spring Framework?

    Answer

    No.

    It is a Spring Boot feature.

    Spring Framework provides
    exception resolution.

    Spring Boot provides
    default error handling.

    ================================================================================
    NEXT FILE
    ================================================================================

    ErrorAttributes

    We will answer

    Who creates

    timestamp?

    status?

    error?

    path?

    message?

    Why does every Boot project
    return almost the same
    JSON format?

    ================================================================================
    */
}


/**
 *
 * Service
 *      │
 *      ▼
 * Exception
 *      │
 *      ▼
 * DispatcherServlet
 *      │
 *      ▼
 * HandlerExceptionResolver
 *
 *      │
 *      ▼
 *
 * Resolved ?
 *
 *  YES            NO
 *
 *  │               │
 *
 *  ▼               ▼
 *
 * ResponseEntity   Spring Boot forwards to /error
 *
 *                      │
 *
 *                      ▼
 *
 *             BasicErrorController
 *
 *                      │
 *
 *                      ▼
 *
 *                ErrorAttributes
 *
 *                      │
 *
 *                      ▼
 *
 *               HTTP 500 Response
 */