package java_recap.springboot.annotations.rest_api;

/*

=========================================================
    RESPONSE + HEADER RELATED ANNOTATIONS
=========================================================

Annotations:
-------------
@ResponseBody
@ResponseStatus
@RequestHeader
@CrossOrigin

Core Concepts:
---------------
1. HTTP Response Handling
2. JSON Serialization
3. HTTP Headers
4. CORS
5. Browser Security Policy
6. REST Response Flow

=========================================================
                1. @ResponseBody
=========================================================

Very important Spring MVC annotation.

=========================================================
                DEFINITION
=========================================================

@ResponseBody tells Spring:

"Return method result directly as HTTP response body."

=========================================================
                WITHOUT @ResponseBody
=========================================================

@Controller
public class UserController {

    @GetMapping("/hello")

    public String hello() {

        return "hello";

    }

}

=========================================================
                WHAT SPRING THINKS?
=========================================================

Spring treats:
---------------

"hello"

as:
----

View Name

=========================================================
                RESULT
=========================================================

Spring tries to find:

hello.jsp
hello.html

=========================================================
                WITH @ResponseBody
=========================================================

@Controller
public class UserController {

    @ResponseBody
    @GetMapping("/hello")

    public String hello() {

        return "hello";

    }

}

=========================================================
                NOW WHAT HAPPENS?
=========================================================

Spring returns:
----------------

HTTP Response Body

=========================================================
                RESPONSE
=========================================================

hello

=========================================================
                INTERNAL WORKING
=========================================================

Controller Method
        ↓
@ResponseBody detected
        ↓
HttpMessageConverter selected
        ↓
Object serialized
        ↓
HTTP Response returned

=========================================================
                OBJECT RESPONSE
=========================================================

@GetMapping("/user")

@ResponseBody
public User getUser() {

    return new User(1, "Pankaj");

}

=========================================================
                INTERNAL FLOW
=========================================================

Java Object
        ↓
Jackson Serializer
        ↓
JSON Conversion
        ↓
HTTP Response

=========================================================
                IMPORTANT
=========================================================

@ResponseBody mainly uses:
---------------------------

HttpMessageConverter

=========================================================
                JSON CONVERTER
=========================================================

MappingJackson2HttpMessageConverter

=========================================================
                MOST IMPORTANT
=========================================================

@RestController internally contains:
-------------------------------------

@Controller
+
@ResponseBody

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q:
---
Difference between @Controller
and @RestController?

=========================================================
                ANSWER
=========================================================

@Controller:
--------------
Returns Views

@RestController:
-----------------
Returns JSON/XML response body.

=========================================================
                2. @ResponseStatus
=========================================================

Used to set custom HTTP status code.

=========================================================
                EXAMPLE
=========================================================

@PostMapping("/users")

@ResponseStatus(HttpStatus.CREATED)

public User createUser() {

}

=========================================================
                RESPONSE STATUS
=========================================================

201 CREATED

=========================================================
                WHY IMPORTANT?
=========================================================

REST APIs should return proper
HTTP status codes.

=========================================================
                COMMON STATUS CODES
=========================================================

200 → Success
201 → Created
204 → No Content
400 → Bad Request
401 → Unauthorized
403 → Forbidden
404 → Not Found
500 → Internal Server Error

=========================================================
                EXCEPTION HANDLING
=========================================================

@ResponseStatus(HttpStatus.NOT_FOUND)

public class UserNotFoundException
        extends RuntimeException {

}

=========================================================
                WHAT HAPPENS?
=========================================================

Whenever exception thrown:
---------------------------

Spring automatically returns:

404 NOT FOUND

=========================================================
                INTERNAL WORKING
=========================================================

Controller executes
        ↓
@ResponseStatus detected
        ↓
Spring modifies HTTP response status

=========================================================
                BEST PRACTICE
=========================================================

Use meaningful status codes.

=========================================================
                BAD PRACTICE
=========================================================

Always returning:
------------------

200 OK

even for errors.

=========================================================
                3. @RequestHeader
=========================================================

Used to read HTTP request headers.

=========================================================
                EXAMPLE
=========================================================

@GetMapping("/profile")

public String profile(

    @RequestHeader("Authorization")
    String token

) {

}

=========================================================
                HEADER VALUE
=========================================================

Authorization: Bearer xyz

=========================================================
                TOKEN EXTRACTED
=========================================================

token = "Bearer xyz"

=========================================================
                INTERNAL WORKING
=========================================================

HTTP Request
        ↓
Spring extracts matching header
        ↓
Injects value into parameter

=========================================================
                OPTIONAL HEADER
=========================================================

@RequestHeader(
    required = false
)

=========================================================
                DEFAULT VALUE
=========================================================

@RequestHeader(
    defaultValue = "unknown"
)

=========================================================
                COMMON HEADERS
=========================================================

Authorization
Content-Type
Accept
User-Agent
Cookie

=========================================================
                REAL PROJECT USAGE
=========================================================

1. JWT Authentication
2. API Versioning
3. Tenant ID
4. Correlation ID
5. Tracing

=========================================================
                4. @CrossOrigin
=========================================================

Most important frontend-backend topic.

=========================================================
                WHY THIS EXISTS?
=========================================================

Browser security policy called:
--------------------------------

Same Origin Policy (SOP)

=========================================================
                SAME ORIGIN MEANS
=========================================================

Protocol + Domain + Port

must match.

=========================================================
                EXAMPLE
=========================================================

Frontend:
-----------
http://localhost:3000

Backend:
----------
http://localhost:8080

=========================================================
                PROBLEM
=========================================================

Browser blocks request.

=========================================================
                ERROR
=========================================================

CORS Error

=========================================================
                WHY BROWSER BLOCKS?
=========================================================

Security protection.

Prevent malicious websites
from accessing other domains.

=========================================================
                SOLUTION
=========================================================

@CrossOrigin

=========================================================
                EXAMPLE
=========================================================

@CrossOrigin(
    origins = "http://localhost:3000"
)

@RestController
public class UserController {

}

=========================================================
                WHAT HAPPENS?
=========================================================

Spring sends CORS headers:

Access-Control-Allow-Origin

=========================================================
                INTERNAL FLOW
=========================================================

Browser Request
        ↓
Spring checks @CrossOrigin
        ↓
Adds CORS headers
        ↓
Browser allows request

=========================================================
                PREFLIGHT REQUEST
=========================================================

Browser may send:

OPTIONS request first.

=========================================================
                WHY?
=========================================================

Checks whether actual request allowed.

=========================================================
                CORS HEADERS
=========================================================

Access-Control-Allow-Origin
Access-Control-Allow-Methods
Access-Control-Allow-Headers

=========================================================
                GLOBAL CORS CONFIG
=========================================================

Instead of annotation everywhere:

Use WebMvcConfigurer

=========================================================
                SECURITY IMPORTANT
=========================================================

NEVER use:
------------

@CrossOrigin("*")

in production.

=========================================================
                WHY?
=========================================================

Allows every domain.

Security risk.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is @ResponseBody?

Q2. Difference between @Controller
and @RestController?

Q3. How JSON response generated?

Q4. What is HttpMessageConverter?

Q5. Why use @ResponseStatus?

Q6. What is CORS?

Q7. Why browser blocks cross-origin requests?

Q8. What is Same Origin Policy?

Q9. What is preflight request?

Q10. How @CrossOrigin works internally?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Does CORS restriction come from backend?

Answer:
--------
NO.

CORS enforced by browser.

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
Can Postman bypass CORS?

YES.

Because Postman is NOT browser.

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Explain complete flow of
@ResponseBody.

Answer:
--------
1. Controller method returns object
2. Spring detects @ResponseBody
3. HttpMessageConverter selected
4. Jackson serializes object into JSON
5. HTTP response returned

=========================================================
                COMMON MISTAKES
=========================================================

1. Using @Controller instead of
@RestController for APIs

2. Returning wrong status codes

3. Using wildcard CORS in production

4. Confusing browser security with backend issue

=========================================================
                BEST PRACTICE
=========================================================

1. Use @RestController for APIs
2. Return proper HTTP status codes
3. Configure secure CORS
4. Use headers for authentication/tracing

=========================================================
                SUMMARY
=========================================================

@ResponseBody
---------------
Returns object directly in response body.

@ResponseStatus
----------------
Sets custom HTTP status.

@RequestHeader
----------------
Reads request headers.

@CrossOrigin
--------------
Enables controlled cross-origin access.

=========================================================

*/

public class ResponseAndHeaderAnnotationsNotes {
}