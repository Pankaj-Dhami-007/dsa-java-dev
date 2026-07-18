package java_recap.springboot.annotations.rest_api;

/*

=========================================================
    @PathVariable + @RequestParam + @RequestBody
=========================================================

Core Concepts:
---------------
1. Request Data Binding
2. URL Parameters
3. Query Parameters
4. JSON Deserialization
5. HttpMessageConverters
6. Jackson Internals

=========================================================
                WHY THESE EXIST?
=========================================================

Client sends data to server using:
-----------------------------------

1. URL Path
2. Query Parameters
3. Request Body
4. Headers

Spring provides annotations
to extract this data easily.

=========================================================
                1. @PathVariable
=========================================================

Used to extract values from URL path.

=========================================================
                EXAMPLE
=========================================================

GET /users/10

=========================================================
                CODE
=========================================================

@GetMapping("/users/{id}")

public User getUser(
        @PathVariable Long id
) {

}

=========================================================
                VALUE MAPPING
=========================================================

/users/10
        ↓
id = 10

=========================================================
                INTERNAL WORKING
=========================================================

DispatcherServlet
        ↓
HandlerMapping matches URL
        ↓
Spring extracts path value
        ↓
Converts to required datatype
        ↓
Injects into method parameter

=========================================================
                MULTIPLE PATH VARIABLES
=========================================================

@GetMapping("/users/{userId}/orders/{orderId}")

public String getOrder(
    @PathVariable Long userId,
    @PathVariable Long orderId
) {

}

=========================================================
                CUSTOM NAME
=========================================================

@GetMapping("/users/{id}")

public User getUser(
        @PathVariable("id") Long userId
) {

}

=========================================================
                USE CASE
=========================================================

Best for:
----------
Resource identification.

=========================================================
                REST BEST PRACTICE
=========================================================

Good:
------

/users/10

Bad:
-----

/getUser?id=10

=========================================================
                2. @RequestParam
=========================================================

Used for query parameters.

=========================================================
                EXAMPLE
=========================================================

GET /users?page=1&size=10

=========================================================
                CODE
=========================================================

@GetMapping("/users")

public List<User> getUsers(

    @RequestParam int page,

    @RequestParam int size

) {

}

=========================================================
                VALUE MAPPING
=========================================================

?page=1
        ↓
page = 1

=========================================================
                OPTIONAL PARAMETER
=========================================================

@RequestParam(required = false)

=========================================================
                DEFAULT VALUE
=========================================================

@RequestParam(defaultValue = "0")

=========================================================
                EXAMPLE
=========================================================

@GetMapping("/users")

public List<User> getUsers(

    @RequestParam(defaultValue = "0")
    int page

) {

}

=========================================================
                USE CASE
=========================================================

Best for:
-----------
1. Pagination
2. Filtering
3. Sorting
4. Searching

=========================================================
                PATHVARIABLE VS REQUESTPARAM
=========================================================

@PathVariable
---------------
Mandatory resource identifier.

@RequestParam
---------------
Optional/filter/query data.

=========================================================
                EXAMPLE
=========================================================

Good REST API:
---------------

GET /users/10
GET /users?page=1

=========================================================
                3. @RequestBody
=========================================================

Most important REST annotation.

=========================================================
                DEFINITION
=========================================================

@RequestBody converts incoming JSON
into Java Object.

=========================================================
                EXAMPLE REQUEST
=========================================================

POST /users

{
    "name": "Pankaj",
    "email": "abc@gmail.com"
}

=========================================================
                JAVA DTO
=========================================================

public class UserRequest {

    private String name;
    private String email;

}

=========================================================
                CONTROLLER
=========================================================

@PostMapping("/users")

public User createUser(
        @RequestBody UserRequest request
) {

}

=========================================================
                INTERNAL WORKING
=========================================================

JSON Request
        ↓
DispatcherServlet
        ↓
HttpMessageConverter
        ↓
Jackson Library
        ↓
JSON → Java Object
        ↓
@RequestBody parameter populated

=========================================================
                IMPORTANT INTERNAL
=========================================================

Spring uses:
--------------
HttpMessageConverters

Default JSON converter:
------------------------

MappingJackson2HttpMessageConverter

=========================================================
                JACKSON ROLE
=========================================================

Jackson performs:
------------------

Deserialization

Meaning:
---------
JSON → Java Object

=========================================================
                RESPONSE FLOW
=========================================================

Java Object
        ↓
Jackson Serialization
        ↓
JSON Response

=========================================================
                SERIALIZATION VS DESERIALIZATION
=========================================================

Serialization:
---------------
Java → JSON

Deserialization:
-----------------
JSON → Java

=========================================================
                VALIDATION WITH REQUESTBODY
=========================================================

@PostMapping("/users")

public User createUser(

    @Valid
    @RequestBody UserRequest request

) {

}

=========================================================
                CONTENT TYPE
=========================================================

Client must send:
------------------

Content-Type: application/json

=========================================================
                OTHERWISE
=========================================================

415 Unsupported Media Type

=========================================================
                COMMON ERROR
=========================================================

400 Bad Request

=========================================================
                WHY?
=========================================================

Invalid JSON or field mismatch.

=========================================================
                IMPORTANT
=========================================================

@RequestBody works mainly for:
-------------------------------

POST
PUT
PATCH

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q:
---
How does @RequestBody work internally?

=========================================================
                ANSWER
=========================================================

1. DispatcherServlet receives request
2. HttpMessageConverter selected
3. Jackson deserializes JSON
4. Java object created
5. Object injected into controller method

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
Difference between @PathVariable
and @RequestParam?

=========================================================
                ANSWER
=========================================================

@PathVariable:
---------------
Part of URL path.
Used for resource identification.

@RequestParam:
---------------
Query parameter.
Used for filtering/pagination/searching.

=========================================================
                COMMON INTERVIEW QUESTIONS
=========================================================

Q1. How Spring converts JSON to object?

Q2. What is HttpMessageConverter?

Q3. What is Jackson?

Q4. Difference between serialization
and deserialization?

Q5. When to use @PathVariable?

Q6. When to use @RequestParam?

Q7. Why @RequestBody mainly used
with POST/PUT/PATCH?

Q8. Why Content-Type important?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Can we use @RequestBody with GET request?

YES technically.

BUT:
-----
Not recommended.

Many clients/proxies ignore GET body.

=========================================================
                ANOTHER TRICKY QUESTION
=========================================================

Q:
---
Can multiple @RequestBody exist
in same method?

NO.

HTTP request body can be consumed only once.

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Explain complete flow of @RequestBody.

Answer:
--------
1. Client sends JSON request
2. DispatcherServlet receives request
3. Appropriate HttpMessageConverter selected
4. Jackson converts JSON into Java object
5. Spring injects object into controller method

=========================================================
                COMMON MISTAKES
=========================================================

1. Missing getters/setters

2. Invalid JSON structure

3. Wrong Content-Type

4. Using entity directly as request DTO

=========================================================
                BEST PRACTICE
=========================================================

1. Use DTOs instead of entities
2. Validate request body
3. Keep APIs RESTful
4. Use path variables for IDs
5. Use query params for filtering

=========================================================
                SUMMARY
=========================================================

@PathVariable
---------------
Extracts values from URL path.

@RequestParam
---------------
Extracts query parameters.

@RequestBody
--------------
Converts JSON request into Java object.

Jackson + HttpMessageConverter
handle JSON conversion internally.

=========================================================

*/

public class RequestDataBindingNotes {
}