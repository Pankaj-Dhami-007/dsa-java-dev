package springboot_deep_drive.spring_mvc;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                      📖 CHAPTER 07 : HANDLER MAPPING (PART 2/2)                           ║
║                                                                                            ║
║                          🔥 INTERNAL WORKING OF HANDLER MAPPING 🔥                         ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                    WHICH HANDLERMAPPING IMPLEMENTATION IS USED?
==============================================================================================

HandlerMapping is NOT a class.

It is an Interface.

Spring provides multiple implementations.

Examples

        HandlerMapping (Interface)
                  ▲
                  │
        ┌─────────┼───────────────────────────────┐
        │         │                               │
        ▼         ▼                               ▼
BeanNameHandler  SimpleUrlHandlerMapping   RequestMappingHandlerMapping

Question

Which implementation is used in modern Spring Boot applications?

Answer

RequestMappingHandlerMapping

Whenever we use

✔ @Controller

✔ @RestController

✔ @RequestMapping

✔ @GetMapping

✔ @PostMapping

Spring internally uses

RequestMappingHandlerMapping.


==============================================================================================
                 WHAT HAPPENS DURING APPLICATION STARTUP?
==============================================================================================

This is one of the most important phases.

Application Starts

        │
        ▼

Spring Context Starts

        │
        ▼

Component Scanning Begins

        │
        ▼

@RestController Found

        │
        ▼

@RequestMapping Read

        │
        ▼

@GetMapping Read

        │
        ▼

Create URL Mapping

        │
        ▼

Store Mapping In Memory


Important

This process happens ONLY ONCE.

It never scans Controllers again
for every request.


==============================================================================================
                      EXAMPLE OF CONTROLLER SCANNING
==============================================================================================

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping
    public List<Employee> getEmployees(){}

    @PostMapping
    public Employee saveEmployee(){}

}


Spring discovers

Class Mapping

↓

/employees

Method Mapping

↓

GET

↓

/employees

POST

↓

/employees


Now Spring stores these mappings internally.


==============================================================================================
                     INTERNAL LOOKUP TABLE (SIMPLIFIED)
==============================================================================================

┌──────────────┬────────┬───────────────────────────────────────────────┐
│ URL          │ Method │ Controller Method                             │
├──────────────┼────────┼───────────────────────────────────────────────┤
│ /employees   │ GET    │ EmployeeController#getEmployees()             │
│ /employees   │ POST   │ EmployeeController#saveEmployee()             │
│ /users       │ GET    │ UserController#getUsers()                     │
│ /login       │ POST   │ AuthController#login()                        │
└──────────────┴────────┴───────────────────────────────────────────────┘

Notice

Spring does NOT store only the URL.

It also stores

✔ HTTP Method

✔ Controller

✔ Controller Method

✔ Additional Mapping Information


==============================================================================================
                   WHAT HAPPENS WHEN A REQUEST ARRIVES?
==============================================================================================

Suppose

GET /employees

arrives.

Flow

                 DispatcherServlet
                         │
                         ▼
             RequestMappingHandlerMapping
                         │
                         ▼
            Search Internal Mapping Table
                         │
                         ▼
             Match URL + HTTP Method
                         │
                         ▼
              EmployeeController#getEmployees()
                         │
                         ▼
                Return HandlerExecutionChain

Notice

There is NO Controller Scanning here.

Only a very fast lookup.


==============================================================================================
                   HOW DOES @GetMapping ACTUALLY WORK?
==============================================================================================

Many developers think

@GetMapping

is some special keyword.

Actually,

@GetMapping is itself built using
@RequestMapping.

Conceptually,

@GetMapping("/employees")

is equivalent to

@RequestMapping(
        value = "/employees",
        method = RequestMethod.GET
)

This is why Spring treats both
in almost the same way internally.


==============================================================================================
                      WHY IS HANDLERMAPPING SO FAST?
==============================================================================================

Imagine

500 Controllers

Each Controller

20 APIs

Total

10,000 API methods.

If Spring searched all methods
for every request,

performance would be terrible.

Instead

Application Startup

↓

Build Mapping Table

↓

Store In Memory

↓

Runtime

↓

Direct Lookup

This reduces request processing time
significantly.


==============================================================================================
                      WHY RETURN HANDLEREXECUTIONCHAIN?
==============================================================================================

Question

Why doesn't HandlerMapping return
only the Controller?

Answer

Because before the Controller executes,

Spring may also need to execute

✔ Interceptors

✔ preHandle()

✔ postHandle()

✔ afterCompletion()

Therefore,

HandlerMapping returns

HandlerExecutionChain

which contains

✔ Handler (Controller Method)

✔ Interceptor List


We'll study HandlerExecutionChain
in the next chapter.


==============================================================================================
                       INTERNAL DESIGN THINKING
==============================================================================================

Suppose HandlerMapping did not exist.

DispatcherServlet would have to

✔ Scan Controllers

✔ Compare URLs

✔ Compare HTTP Methods

✔ Execute Matching Controller

This would make DispatcherServlet
very large and difficult to maintain.

Spring follows

Single Responsibility Principle.

DispatcherServlet

↓

Coordinate Request

HandlerMapping

↓

Find Handler

HandlerAdapter

↓

Execute Handler

ViewResolver

↓

Resolve View

Each class performs only ONE responsibility.


==============================================================================================
                           REAL WORLD ANALOGY
==============================================================================================

Imagine Google Maps.

You enter

Delhi Airport

↓

Google immediately finds the route.

Why?

Because roads were already indexed.

Google does NOT scan every road
every time you search.

HandlerMapping works exactly the same way.

Controllers are indexed during startup,

then requests are resolved
using fast lookups.


==============================================================================================
                          INTERVIEW QUESTIONS
==============================================================================================

Q1.

Which HandlerMapping implementation is
commonly used in Spring Boot?

Answer

RequestMappingHandlerMapping.


------------------------------------------------------------

Q2.

When are Controllers scanned?

Answer

Only during application startup.


------------------------------------------------------------

Q3.

Does Spring scan Controllers
for every request?

Answer

No.

Only an in-memory lookup is performed.


------------------------------------------------------------

Q4.

Why does HandlerMapping return
HandlerExecutionChain instead of
just the Controller?

Answer

Because Spring also needs to execute
Interceptors before and after
the Controller.


==============================================================================================
                         COMMON MISTAKES
==============================================================================================

❌ Thinking

HandlerMapping executes the Controller.

Wrong.

It only finds the Handler.

Execution is performed by

HandlerAdapter.


------------------------------------------------------------

❌ Thinking

Controllers are scanned
for every request.

Wrong.

Scanning happens only once
during startup.


------------------------------------------------------------

❌ Thinking

@GetMapping and @RequestMapping
are completely different.

Wrong.

@GetMapping is a specialized form
of @RequestMapping.


==============================================================================================
                              MEMORY TRICK
==============================================================================================

Application Starts

↓

Scan Controllers

↓

Create Mapping Table

↓

Store In Memory

↓

Request Arrives

↓

Lookup Mapping

↓

Return HandlerExecutionChain


Simple Formula

Scan Once

↓

Lookup Forever


==============================================================================================
                              FINAL SUMMARY
==============================================================================================

✔ HandlerMapping is responsible for finding the correct Handler.

✔ Modern Spring Boot applications use RequestMappingHandlerMapping.

✔ Controller scanning happens only once during startup.

✔ Spring creates an internal mapping table in memory.

✔ Every request performs a fast lookup instead of scanning Controllers.

✔ HandlerMapping returns a HandlerExecutionChain because Controller execution
  may involve Interceptors as well.

✔ HandlerMapping follows the Single Responsibility Principle by keeping
  Controller lookup separate from request execution.


═══════════════════════════════════════════════════════════════════════════════════════════════

🎉 HANDLERMAPPING DEEP DIVE COMPLETED

Next Chapter

📖 HandlerExecutionChain Deep Dive

═══════════════════════════════════════════════════════════════════════════════════════════════
*/

public class HandlerMappingDeepDive_2 {

}