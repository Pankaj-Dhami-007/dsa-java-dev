package springboot_deep_drive.exception_handling.in_spring.global_exception_handler;

public class GlobalExceptionHandlerInternals_5 {

    /*
    ================================================================================
                HOW RESPONSEENTITY BECOMES HTTP RESPONSE
    ================================================================================

    Previous Flow

    ExceptionHandlerExceptionResolver

            │

            ▼

    @ExceptionHandler Method

            │

            ▼

    ResponseEntity<ErrorResponse>

    -------------------------------------------------------------------------------

    Now Question

    ResponseEntity is just a Java Object.

    Client understands

            HTTP Response

    not

            Java Object.

    So

    who converts it?

    ================================================================================



    ================================================================================
    1. RESPONSEENTITY IS NOT THE HTTP RESPONSE
    ================================================================================

    Very Important

    Many developers think

            ResponseEntity

    itself is sent to client.

    Wrong.

    ResponseEntity

    is only a Java object.

    Think

            Parcel

    It contains

    • Status Code

    • Headers

    • Body

    Example

    ResponseEntity

            Status

                404

            Headers

                Content-Type

            Body

                ErrorResponse Object

    Client still cannot understand it.



    ================================================================================
    2. DISPATCHERSERVLET RECEIVES RESPONSEENTITY
    ================================================================================

    Your Global Exception Handler returns

            ResponseEntity<ErrorResponse>

    Control goes back to

            DispatcherServlet

    DispatcherServlet now asks

            "How should I convert
             this Java Object
             into an HTTP Response?"



    ================================================================================
    3. HTTPMESSAGECONVERTER
    ================================================================================

    New Component

            HttpMessageConverter

    Think of it as

            Translator

    Java Object

            →

    HTTP Message

    ----------------------------------------------------------------------------

    Responsibility

    Convert Java Objects

    into formats like

    • JSON

    • XML

    • String

    • Byte Array

    etc.

    It works for

    normal controller responses

    as well as

    exception responses.



    ================================================================================
    4. WHO CONVERTS OBJECT INTO JSON?
    ================================================================================

    If Jackson library

    is available,

    Spring uses

            MappingJackson2HttpMessageConverter

    Internally

    it uses

            Jackson ObjectMapper

    Flow

    ErrorResponse Object

            ↓

    ObjectMapper

            ↓

    JSON String



    ================================================================================
    5. WHAT HAPPENS INTERNALLY?
    ================================================================================

    Suppose

    ErrorResponse

    contains

    errorCode = 1001

    message = User Not Found

    Jackson converts

    Java Object

    into

    {

        "errorCode":"1001",

        "message":"User Not Found"

    }

    automatically.

    You never write

    JSON manually.



    ================================================================================
    6. BUILDING HTTP RESPONSE
    ================================================================================

    DispatcherServlet now has

    Status Code

            404

    Headers

            Content-Type

    Body

            JSON

    It writes all of them

    into

            HttpServletResponse

    This is the actual

    HTTP Response object

    managed by the Servlet API.



    ================================================================================
    7. WHO SENDS RESPONSE TO CLIENT?
    ================================================================================

    DispatcherServlet finishes.

    Control returns to

            Tomcat

    Tomcat writes

            HttpServletResponse

    to the network socket.

    Response travels

    over HTTP

    to the client.



    ================================================================================
    8. COMPLETE REQUEST-RESPONSE FLOW
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

    Repository

        │

        ▼

    Database

        │

        ▼

    Exception

        ▲

        │

    DispatcherServlet

        │

        ▼

    HandlerExceptionResolverComposite

        │

        ▼

    ExceptionHandlerExceptionResolver

        │

        ▼

    @ControllerAdvice

        │

        ▼

    @ExceptionHandler

        │

        ▼

    ResponseEntity

        │

        ▼

    HttpMessageConverter

        │

        ▼

    JSON

        │

        ▼

    HttpServletResponse

        │

        ▼

    Tomcat

        │

        ▼

    Client



    ================================================================================
    9. WHY DON'T WE CREATE JSON OURSELVES?
    ================================================================================

    Because Spring provides

            HttpMessageConverter

    which automatically converts

    Java Objects

    into

            JSON

    using Jackson.

    This keeps code clean

    and avoids manual serialization.



    ================================================================================
    10. COMPLETE STORY
    ================================================================================

    Client sends Request

            ↓

    Tomcat receives Request

            ↓

    DispatcherServlet receives Request

            ↓

    HandlerMapping finds Controller

            ↓

    HandlerAdapter executes Controller

            ↓

    Service executes

            ↓

    Service throws Exception

            ↓

    Exception propagates

            ↓

    DispatcherServlet catches Exception

            ↓

    processHandlerException()

            ↓

    HandlerExceptionResolverComposite

            ↓

    ExceptionHandlerExceptionResolver

            ↓

    Finds matching

    @ExceptionHandler

            ↓

    Executes Global Exception Handler

            ↓

    ResponseEntity returned

            ↓

    HttpMessageConverter

            ↓

    JSON generated

            ↓

    HttpServletResponse prepared

            ↓

    Tomcat sends Response

            ↓

    Client receives Response



    ================================================================================
    11. INTERVIEW QUESTIONS
    ================================================================================

    Q.

    Does ResponseEntity itself go to the client?

    Answer

    No.

    It is first processed by Spring,
    converted into an HTTP response,
    and then sent by Tomcat.


    --------------------------------------------------


    Q.

    Who converts Java Objects into JSON?

    Answer

    HttpMessageConverter.

    When Jackson is available,
    Spring uses MappingJackson2HttpMessageConverter,
    which internally uses Jackson's ObjectMapper.


    --------------------------------------------------


    Q.

    Who sends the final HTTP response?

    Answer

    Tomcat sends the HTTP response
    after DispatcherServlet has finished
    preparing it.


    --------------------------------------------------


    Q.

    Does DispatcherServlet create JSON?

    Answer

    No.

    DispatcherServlet coordinates the flow.
    JSON conversion is delegated to
    HttpMessageConverters.


    ================================================================================
    EXCEPTION HANDLING SERIES COMPLETED
    ================================================================================

    We now understand

    ✔ Exception Basics

    ✔ Exception Propagation

    ✔ DispatcherServlet

    ✔ HandlerMapping

    ✔ HandlerAdapter

    ✔ HandlerExceptionResolver

    ✔ ExceptionHandlerExceptionResolver

    ✔ @ControllerAdvice

    ✔ @ExceptionHandler

    ✔ ResponseEntity

    ✔ HttpMessageConverter

    ✔ Tomcat

    ✔ Complete Request-Response Flow

    ================================================================================
    */

}