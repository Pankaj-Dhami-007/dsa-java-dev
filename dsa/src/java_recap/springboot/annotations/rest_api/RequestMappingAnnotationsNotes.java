package java_recap.springboot.annotations.rest_api;

/*

=========================================================
        REQUEST MAPPING ANNOTATIONS
=========================================================

Annotations:
-------------
@RequestMapping
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
@PatchMapping

Core Concepts:
---------------
1. REST APIs
2. HTTP Methods
3. Request Routing
4. DispatcherServlet
5. Handler Mapping

=========================================================
                WHAT IS REST API?
=========================================================

REST =
-------
Representational State Transfer

API communicates using:
------------------------

HTTP Protocol

=========================================================
                HTTP METHODS
=========================================================

GET
-----
Read Data

POST
------
Create Data

PUT
-----
Update Complete Resource

PATCH
-------
Partial Update

DELETE
--------
Delete Resource

=========================================================
                @RequestMapping
=========================================================

Base mapping annotation.

Used to map:
-------------
1. URL
2. HTTP Method
3. Headers
4. Content Type

=========================================================
                EXAMPLE
=========================================================

@RestController
@RequestMapping("/users")
public class UserController {

}

=========================================================
                MEANING
=========================================================

All APIs start with:
---------------------

/users

=========================================================
                METHOD LEVEL EXAMPLE
=========================================================

@RequestMapping(
    value = "/all",
    method = RequestMethod.GET
)

=========================================================
                PROBLEM
=========================================================

Verbose syntax.

=========================================================
                SOLUTION
=========================================================

Shortcut annotations.

=========================================================
                @GetMapping
=========================================================

Shortcut for:
--------------

@RequestMapping(method = GET)

=========================================================
                EXAMPLE
=========================================================

@GetMapping("/users")
public List<User> getUsers() {

}

=========================================================
                USE CASE
=========================================================

Fetch data.

=========================================================
                @PostMapping
=========================================================

Used for:
-----------
Create operations.

=========================================================
                EXAMPLE
=========================================================

@PostMapping("/users")
public User createUser(
        @RequestBody User user
) {

}

=========================================================
                @PutMapping
=========================================================

Used for:
-----------
Full update.

=========================================================
                EXAMPLE
=========================================================

@PutMapping("/users/{id}")

=========================================================
                IMPORTANT
=========================================================

PUT replaces complete resource.

=========================================================
                @PatchMapping
=========================================================

Used for:
-----------
Partial updates.

=========================================================
                EXAMPLE
=========================================================

@PatchMapping("/users/{id}")

=========================================================
                DIFFERENCE
=========================================================

PUT
-----
Full update

PATCH
-------
Partial update

=========================================================
                @DeleteMapping
=========================================================

Used for:
-----------
Delete resource.

=========================================================
                EXAMPLE
=========================================================

@DeleteMapping("/users/{id}")

=========================================================
                INTERNAL WORKING
=========================================================

Client Request
        ↓
DispatcherServlet
        ↓
HandlerMapping
        ↓
Controller Method Found
        ↓
Method Executes
        ↓
Response Returned

=========================================================
                DISPATCHERSERVLET
=========================================================

Front Controller of Spring MVC.

Every request first reaches:
-----------------------------

DispatcherServlet

=========================================================
                HANDLER MAPPING
=========================================================

Responsible for:
-----------------

Finding correct controller method.

=========================================================
                EXAMPLE FLOW
=========================================================

GET /users/1
        ↓
DispatcherServlet
        ↓
Find matching @GetMapping
        ↓
Execute controller method
        ↓
Return response

=========================================================
                CLASS LEVEL + METHOD LEVEL
=========================================================

@RestController
@RequestMapping("/api/users")

public class UserController {

    @GetMapping("/{id}")

}

=========================================================
                FINAL URL
=========================================================

/api/users/{id}

=========================================================
                PRODUCES
=========================================================

Specify response content type.

=========================================================
                EXAMPLE
=========================================================

@GetMapping(
    value = "/users",
    produces = "application/json"
)

=========================================================
                CONSUMES
=========================================================

Specify accepted request type.

=========================================================
                EXAMPLE
=========================================================

@PostMapping(
    consumes = "application/json"
)

=========================================================
                PATH VARIABLES
=========================================================

@GetMapping("/users/{id}")

=========================================================
                QUERY PARAMS
=========================================================

@GetMapping("/users?id=1")

=========================================================
                REST API BEST PRACTICES
=========================================================

GET
-----
Should NOT modify data.

POST
------
Used for creation.

PUT
-----
Idempotent full update.

PATCH
-------
Partial update.

DELETE
--------
Delete operation.

=========================================================
                IDEMPOTENT
=========================================================

Multiple same requests produce same result.

=========================================================
                EXAMPLE
=========================================================

PUT request repeated:
----------------------

Same final resource state.

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q:
---
Difference between PUT and PATCH?

=========================================================
                ANSWER
=========================================================

PUT:
-----
Replaces complete resource.

PATCH:
-------
Updates partial fields only.

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
Difference between @RequestMapping
and @GetMapping?

=========================================================
                ANSWER
=========================================================

@RequestMapping:
-----------------
Generic mapping annotation.

@GetMapping:
--------------
Shortcut specialized for GET.

=========================================================
                COMMON INTERVIEW QUESTIONS
=========================================================

Q1. How request reaches controller?

Q2. What is DispatcherServlet?

Q3. What is HandlerMapping?

Q4. Difference between PUT and PATCH?

Q5. Difference between POST and PUT?

Q6. What is idempotency?

Q7. Why use specialized mapping annotations?

Q8. Can multiple mappings exist?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Can two controller methods have
same URL mapping?

YES.

If:
----
HTTP methods differ.

Example:
---------

@GetMapping("/users")

@PostMapping("/users")

Valid.

=========================================================
                ANOTHER TRICKY QUESTION
=========================================================

Q:
---
What happens if two methods have
same URL + same HTTP method?

Answer:
--------
Application startup fails due to
ambiguous mapping.

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Explain request flow in Spring MVC.

Answer:
--------
1. Client sends HTTP request
2. DispatcherServlet receives request
3. HandlerMapping finds controller
4. Controller method executes
5. Response returned to client

=========================================================
                COMMON MISTAKES
=========================================================

1. Using GET for data modification

2. Wrong HTTP method selection

3. Duplicate mappings

4. Huge controllers

=========================================================
                BEST PRACTICE
=========================================================

1. Use REST naming conventions
2. Keep controllers thin
3. Business logic inside service layer
4. Use proper HTTP methods
5. Design idempotent APIs carefully

=========================================================
                SUMMARY
=========================================================

@RequestMapping
----------------
Generic request mapping.

@GetMapping
-------------
GET APIs

@PostMapping
--------------
Create APIs

@PutMapping
-------------
Full update

@PatchMapping
---------------
Partial update

@DeleteMapping
----------------
Delete APIs

DispatcherServlet handles all requests
in Spring MVC.

=========================================================

*/

public class RequestMappingAnnotationsNotes {
}