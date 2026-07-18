package java_recap.springboot.annotations.core_spring;

/*

=========================================================
            STEREOTYPE ANNOTATIONS
=========================================================

Annotations:
-------------
@Component
@Service
@Repository
@Controller
@RestController

=========================================================
                WHAT ARE THEY?
=========================================================

These annotations tell Spring:

"Create object of this class
 and manage it as Bean."

All are special types of:
-------------------------

@Component

=========================================================
                INTERNAL HIERARCHY
=========================================================

                @Component
                     ↑
    --------------------------------
    ↑              ↑              ↑
@Service     @Repository     @Controller
                                     ↑
                            @RestController

=========================================================
                1. @Component
=========================================================

Generic annotation.

Used for:
----------
Utility classes
Helper classes
Common services

Example:
---------

@Component
public class EmailUtil {

}

=========================================================
                2. @Service
=========================================================

Used for:
----------
Business Logic Layer

Example:
---------

@Service
public class UserService {

    public void registerUser() {

    }

}

=========================================================
                WHY @Service?
=========================================================

Technically same as @Component.

But gives semantic meaning:
---------------------------

"This class contains business logic."

Useful for:
------------
1. Readability
2. Clean architecture
3. AOP
4. Maintenance

=========================================================
                REAL FLOW
=========================================================

Controller
    ↓
Service
    ↓
Repository
    ↓
Database

=========================================================
                3. @Repository
=========================================================

Used for:
----------
Database Layer / DAO Layer

Example:
---------

@Repository
public class UserRepository {

}

=========================================================
                SPECIAL FEATURE
=========================================================

@Repository provides:

Exception Translation

Meaning:
--------
Database exceptions converted into:

DataAccessException

Spring handles DB exceptions better.

=========================================================
                4. @Controller
=========================================================

Used in:
---------
Spring MVC applications.

Purpose:
---------
Handle HTTP requests.

Example:
---------

@Controller
public class HomeController {

}

=========================================================
                IMPORTANT
=========================================================

@Controller returns:
---------------------

View/Page

Example:
---------
index.jsp
home.html

Mostly used in:
---------------
Traditional MVC applications

=========================================================
                5. @RestController
=========================================================

Most used in Spring Boot APIs.

Purpose:
---------
Create REST APIs.

Example:
---------

@RestController
public class UserController {

    @GetMapping("/users")
    public List<String> users() {

        return List.of("A", "B");

    }

}

=========================================================
                INTERNAL WORKING
=========================================================

@RestController internally contains:
------------------------------------

@Controller
@ResponseBody

=========================================================
                @ResponseBody
=========================================================

Converts Java object into JSON.

Example:
---------

User object
    ↓
Jackson Library
    ↓
JSON Response

=========================================================
                DIFFERENCE
=========================================================

@Controller
-------------
Returns View/Page

@RestController
----------------
Returns JSON/XML response

=========================================================
                REAL PROJECT STRUCTURE
=========================================================

controller/
------------
UserController

service/
---------
UserService

repository/
------------
UserRepository

=========================================================
                COMPLETE FLOW
=========================================================

Client Request
      ↓
@RestController
      ↓
@Service
      ↓
@Repository
      ↓
Database
      ↓
Response Returned

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Difference between @Component and @Service?

Q2. Difference between @Controller and
@RestController?

Q3. Why use @Repository?

Q4. What is stereotype annotation?

Q5. Does @Service create bean?

Q6. Is @RestController a combination
of annotations?

=========================================================
                COMMON MISTAKES
=========================================================

1. Using @Controller for REST API

Problem:
---------
Returns View instead of JSON.

2. Using @Component everywhere

Problem:
---------
Bad readability.

3. Business logic inside Controller

Wrong architecture.

=========================================================
                BEST PRACTICE
=========================================================

Use:
-----

@Controller
→ MVC Pages

@RestController
→ REST APIs

@Service
→ Business Logic

@Repository
→ Database Layer

@Component
→ Generic Helper Classes

=========================================================
                SUMMARY
=========================================================

@Component
-------------
Generic Bean

@Service
-----------
Business Logic Bean

@Repository
--------------
Database Bean

@Controller
-------------
MVC Controller

@RestController
-----------------
REST API Controller

=========================================================

*/

public class StereotypeAnnotationsNotes {
}