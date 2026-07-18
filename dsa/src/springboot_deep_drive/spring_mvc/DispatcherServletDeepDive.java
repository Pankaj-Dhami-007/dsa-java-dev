package springboot_deep_drive.spring_mvc;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                    📖 CHAPTER 06 : DISPATCHER SERVLET (PART 1/3)                          ║
║                                                                                            ║
║                          ⏱ Reading Time : 10 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝

==============================================================================================
                                   🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand

✔ What is DispatcherServlet?

✔ Why do we need DispatcherServlet?

✔ Why is it called the Front Controller?

✔ Where does it execute?

✔ Complete Request Architecture

✔ Servlet Inheritance Hierarchy

✔ Internal Processing Flow


==============================================================================================
                                  WHAT IS DISPATCHERSERVLET?
==============================================================================================

DispatcherServlet is the heart of Spring MVC.

It is a special Servlet provided by the Spring Framework.

Every HTTP Request entering Spring MVC first reaches
DispatcherServlet.

DispatcherServlet does NOT contain business logic.

Its job is to coordinate different Spring MVC components.

Think of it as a Traffic Police Officer.

It never drives the vehicles.

It only decides

✔ Where should the request go?
✔ Which Controller should execute?
✔ Which View should be returned?
✔ Which Exception Handler should run?

Because of this responsibility,

DispatcherServlet is known as the

                    ★ FRONT CONTROLLER ★


==============================================================================================
                           WHY IS IT CALLED FRONT CONTROLLER?
==============================================================================================

Without DispatcherServlet

Client
   │
   ├────────────► EmployeeController
   │
   ├────────────► UserController
   │
   ├────────────► ProductController
   │
   └────────────► OrderController

Each Controller would have to

✔ Authenticate

✔ Validate

✔ Handle Errors

✔ Select Views

✔ Process Requests

This creates duplicate infrastructure logic.


With DispatcherServlet


                    Client
                       │
                       ▼
          ┌─────────────────────────┐
          │   DispatcherServlet     │
          └────────────┬────────────┘
                       │
      ┌────────────────┼────────────────┐
      ▼                ▼                ▼
EmployeeController  UserController  ProductController


Now every request enters from

ONE SINGLE ENTRY POINT.

Hence

DispatcherServlet = Front Controller.


==============================================================================================
                           COMPLETE SPRING MVC ARCHITECTURE
==============================================================================================


                           HTTP REQUEST


                   ┌──────────────┐
                   │    Client    │
                   └──────┬───────┘
                          │
                          ▼
             ┌─────────────────────────┐
             │ Embedded Tomcat         │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ Servlet Filter(s)       │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ DispatcherServlet       │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ HandlerMapping          │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ Interceptor             │
             │ preHandle()             │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ HandlerAdapter          │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ Controller              │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ Service                 │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ Repository              │
             └───────────┬─────────────┘
                         │
                         ▼
             ┌─────────────────────────┐
             │ Database                │
             └─────────────────────────┘


==============================================================================================
                        RESPONSIBILITIES OF DISPATCHERSERVLET
==============================================================================================

DispatcherServlet is NOT responsible for

❌ Business Logic

❌ Database Operations

❌ Writing SQL

❌ Authentication Logic

Its responsibilities are

✔ Receive Request

✔ Find Correct Controller

✔ Execute Interceptors

✔ Execute Controller

✔ Handle Exceptions

✔ Resolve View

✔ Return Response


Think of DispatcherServlet as

             "THE MANAGER"

Manager does not perform every task.

Instead,

Manager delegates work to specialists.


==============================================================================================
                          INTERNAL COMPONENTS USED
==============================================================================================

DispatcherServlet communicates with

                ┌────────────────────────┐
                │ HandlerMapping         │
                └────────────────────────┘

                ┌────────────────────────┐
                │ HandlerAdapter         │
                └────────────────────────┘

                ┌────────────────────────┐
                │ ViewResolver           │
                └────────────────────────┘

                ┌────────────────────────┐
                │ ExceptionResolver      │
                └────────────────────────┘

Notice

DispatcherServlet itself does almost nothing.

It coordinates these components.


==============================================================================================
                     SERVLET INHERITANCE HIERARCHY
==============================================================================================

                      java.lang.Object
                              │
                              ▼
                     GenericServlet
                              │
                              ▼
                        HttpServlet
                              │
                              ▼
                  FrameworkServlet
                              │
                              ▼
                    DispatcherServlet


Explanation

GenericServlet

↓

Provides generic servlet functionality.

--------------------------------------------

HttpServlet

↓

Adds HTTP-specific support like

GET

POST

PUT

DELETE

--------------------------------------------

FrameworkServlet

↓

Spring's base servlet.

Adds Spring Context support.

--------------------------------------------

DispatcherServlet

↓

Heart of Spring MVC.

Routes every HTTP request.


==============================================================================================
                       REQUEST REACHES DISPATCHERSERVLET
==============================================================================================

Suppose

GET /employees

Client

      │

      ▼

Embedded Tomcat

      │

      ▼

Servlet Filters

      │

      ▼

DispatcherServlet

Now DispatcherServlet starts processing.

Question

How?

Answer

By executing its internal methods.

Those methods are

✔ service()

✔ doService()

✔ doDispatch()

These three methods form the complete request
processing pipeline.


==============================================================================================
                               INTERVIEW QUESTIONS
==============================================================================================

Q1.

What is DispatcherServlet?

Answer

DispatcherServlet is the Front Controller
of Spring MVC.

--------------------------------------------------

Q2.

Why is it called Front Controller?

Answer

Because every request enters Spring MVC
through DispatcherServlet.

--------------------------------------------------

Q3.

Does DispatcherServlet contain business logic?

Answer

No.

It only coordinates Spring MVC components.

--------------------------------------------------

Q4.

Who creates DispatcherServlet?

Answer

Spring Boot automatically creates and registers
DispatcherServlet during application startup.

==============================================================================================
                                PART 1 SUMMARY
==============================================================================================

✔ DispatcherServlet is the Front Controller.

✔ Every Spring MVC request reaches DispatcherServlet.

✔ It delegates work to HandlerMapping,
HandlerAdapter,
ViewResolver,
ExceptionResolver.

✔ It does NOT execute business logic.

✔ Internal processing starts with

service()

↓

doService()

↓

doDispatch()


═══════════════════════════════════════════════════════════════════════════════════════════════
➡ CONTINUE TO PART 2
(Internal Methods : service(), doService(), doDispatch())
═══════════════════════════════════════════════════════════════════════════════════════════════

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                    📖 CHAPTER 06 : DISPATCHER SERVLET (PART 2/3)                          ║
║                                                                                            ║
║                      🔥 INTERNAL WORKING OF DISPATCHERSERVLET 🔥                           ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                           INTERNAL REQUEST PROCESSING
==============================================================================================

In Part 1 we learned that every request reaches DispatcherServlet.

Now the question is...

What happens AFTER DispatcherServlet receives the request?

The internal flow is

                 service()

                      │

                      ▼

                 doService()

                      │

                      ▼

                 doDispatch()

                      │

                      ▼

          Spring MVC Components


This is the core execution pipeline of Spring MVC.


==============================================================================================
                          STEP 1 : service()
==============================================================================================

DispatcherServlet is ultimately a Servlet.

Every Servlet has a

service()

method.

When Tomcat receives an HTTP request,

it calls

                        service()

NOT

doDispatch()

Tomcat has no idea that doDispatch() even exists.


Flow


             Client

                │

                ▼

      Embedded Tomcat

                │

                ▼

 DispatcherServlet.service()


Think of

service()

as the ENTRY GATE of DispatcherServlet.


==============================================================================================
                      STEP 2 : doService()
==============================================================================================

After service(),

DispatcherServlet internally calls

doService()

Purpose

Prepare everything required before processing
the request.

Typical work

✔ Store framework attributes

✔ Prepare request context

✔ Initialize MVC infrastructure

✔ Forward request for actual processing

Important

Business Logic still has NOT started.


Flow


service()

      │

      ▼

doService()

      │

      ▼

Preparing Request...


==============================================================================================
                       STEP 3 : doDispatch()
==============================================================================================

This is the MOST IMPORTANT method
inside DispatcherServlet.

Almost the entire Spring MVC framework
works inside this method.

Everything we usually study like

✔ HandlerMapping

✔ Interceptor

✔ Controller

✔ ViewResolver

starts from here.


Think of it as

                ★ THE BRAIN OF SPRING MVC ★


==============================================================================================
                    HIGH LEVEL doDispatch() FLOW
==============================================================================================


                  doDispatch()

                       │

                       ▼

         Find HandlerMapping

                       │

                       ▼

     Get HandlerExecutionChain

                       │

                       ▼

      Execute preHandle()

                       │

                       ▼

     Call HandlerAdapter

                       │

                       ▼

      Execute Controller

                       │

                       ▼

      Execute postHandle()

                       │

                       ▼

        Render Response

                       │

                       ▼

 Execute afterCompletion()


Every Spring MVC request follows
this sequence.


==============================================================================================
                  COMPLETE INTERNAL EXECUTION DIAGRAM
==============================================================================================


                     DispatcherServlet

                             │
                             ▼

                     service()

                             │
                             ▼

                    doService()

                             │
                             ▼

                   doDispatch()

                             │
                             ▼

              ┌────────────────────────┐
              │ HandlerMapping         │
              └──────────┬─────────────┘
                         │
                         ▼
          HandlerExecutionChain Found
                         │
                         ▼
              ┌────────────────────────┐
              │ preHandle()            │
              └──────────┬─────────────┘
                         │
                         ▼
              ┌────────────────────────┐
              │ HandlerAdapter         │
              └──────────┬─────────────┘
                         │
                         ▼
              ┌────────────────────────┐
              │ Controller             │
              └──────────┬─────────────┘
                         │
                         ▼
              ┌────────────────────────┐
              │ postHandle()           │
              └──────────┬─────────────┘
                         │
                         ▼
              ┌────────────────────────┐
              │ ViewResolver           │
              └──────────┬─────────────┘
                         │
                         ▼
              ┌────────────────────────┐
              │ afterCompletion()      │
              └──────────┬─────────────┘
                         │
                         ▼
                      Response


This is the REAL Spring MVC pipeline.


==============================================================================================
                    WHY DOES DISPATCHERSERVLET USE
                        HANDLERMAPPING?
==============================================================================================

Suppose we have

GET /employees

Spring has hundreds of Controllers.

Question

How does DispatcherServlet know

which Controller should execute?

Answer

It asks

                    HandlerMapping


DispatcherServlet itself never searches Controllers.

It delegates this work.


==============================================================================================
                  WHY DOES IT USE HANDLERADAPTER?
==============================================================================================

Question

If DispatcherServlet already found
the Controller,

why doesn't it execute it directly?

Answer

Because Spring supports different
types of handlers.

DispatcherServlet doesn't know
how each handler should be invoked.

HandlerAdapter solves this problem.


DispatcherServlet

          │

          ▼

HandlerAdapter

          │

          ▼

Controller


Think of HandlerAdapter as

            ★ Universal Remote Control ★


==============================================================================================
                       INTERNAL THINKING OF SPRING
==============================================================================================

DispatcherServlet never says

"I'll do everything."

Instead it says

"I'll ask the correct component."

Need Controller?

↓

Ask HandlerMapping.

--------------------------------------------

Need Controller Execution?

↓

Ask HandlerAdapter.

--------------------------------------------

Need View?

↓

Ask ViewResolver.

--------------------------------------------

Need Exception Handling?

↓

Ask ExceptionResolver.


This is called

Delegation.

It is one of the biggest design principles
used inside Spring Framework.


==============================================================================================
                      REAL LIFE ANALOGY
==============================================================================================

Imagine a Hospital.

Patient

      │

      ▼

Reception

      │

      ▼

Find Doctor

      │

      ▼

Send Patient

      │

      ▼

Doctor Treats

      │

      ▼

Billing

Reception never treats the patient.

It only coordinates.

DispatcherServlet works exactly
the same way.


==============================================================================================
                        COMMON INTERVIEW QUESTIONS
==============================================================================================

Q.

Which method contains the actual
Spring MVC processing logic?

Answer

doDispatch()


--------------------------------------------------

Q.

Does Tomcat directly call doDispatch()?

Answer

No.

Tomcat calls

service()

DispatcherServlet internally calls

doService()

↓

doDispatch()


--------------------------------------------------

Q.

Who finds the Controller?

Answer

HandlerMapping.


--------------------------------------------------

Q.

Who executes the Controller?

Answer

HandlerAdapter.


==============================================================================================
                                PART 2 SUMMARY
==============================================================================================

Tomcat

↓

service()

↓

doService()

↓

doDispatch()

↓

HandlerMapping

↓

HandlerExecutionChain

↓

HandlerAdapter

↓

Controller

↓

ViewResolver

↓

Response


Remember

DispatcherServlet

does NOT perform all work itself.

It delegates work to specialized
Spring MVC components.


═══════════════════════════════════════════════════════════════════════════════════════════════

➡ CONTINUE TO PART 3

Topics

✔ Exception Handling

✔ Response Rendering

✔ Complete End-to-End Flow

✔ Memory Tricks

✔ Interview Summary

═══════════════════════════════════════════════════════════════════════════════════════════════


 /*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                    📖 CHAPTER 06 : DISPATCHER SERVLET (PART 3/3)                          ║
║                                                                                            ║
║                          🔥 COMPLETE INTERNAL WORKING 🔥                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                        STEP 4 : HANDLER EXECUTION
==============================================================================================

In Part-2 we learned

DispatcherServlet

↓

HandlerMapping

↓

HandlerExecutionChain

↓

HandlerAdapter

Question

What happens after HandlerAdapter gets the Controller?

Answer

HandlerAdapter executes the Controller method.

Example

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    @GetMapping
    public List<Employee> getEmployees(){

        return service.getEmployees();

    }

}

Execution

DispatcherServlet

↓

HandlerAdapter

↓

EmployeeController.getEmployees()

↓

Service

↓

Repository

↓

Database

↓

Return Response


Notice

DispatcherServlet NEVER directly calls

employeeController.getEmployees()

Instead

HandlerAdapter executes it.


==============================================================================================
                        WHY DOES SPRING USE HANDLERADAPTER?
==============================================================================================

This is one of the most important interview questions.

Question

DispatcherServlet already knows the Controller.

Then why doesn't it call it directly?

Suppose today Spring supports

@RestController

Tomorrow Spring introduces

Functional Endpoints

Next year

GraphQL Handler

If DispatcherServlet directly invokes every handler,

every new handler type would require modifying
DispatcherServlet.

That violates the

Open Closed Principle.

Instead

DispatcherServlet delegates execution
to HandlerAdapter.

Now

New Handler

↓

Create New Adapter

↓

DispatcherServlet remains unchanged.

This is a brilliant framework design.


==============================================================================================
                            STEP 5 : RESPONSE RETURNS
==============================================================================================

Controller finishes execution.

Example

return employeeList;

Now the response starts travelling back.

Database

↓

Repository

↓

Service

↓

Controller

↓

HandlerAdapter

↓

DispatcherServlet


If required,

DispatcherServlet now performs

✔ View Resolution

✔ Exception Handling

✔ Response Rendering


==============================================================================================
                             VIEW RESOLUTION
==============================================================================================

Question

Controller returned

"home"

How does Spring know

which HTML/JSP/Thymeleaf page
should be shown?

Answer

DispatcherServlet asks

ViewResolver.


Example

Controller

↓

return "home";

↓

DispatcherServlet

↓

ViewResolver

↓

home.jsp

↓

Client


For REST APIs

@RestController

usually returns JSON,

therefore

ViewResolver is generally skipped.


==============================================================================================
                           EXCEPTION HANDLING
==============================================================================================

Suppose

Repository throws

NullPointerException

Flow

Database

↓

Repository

↓

Exception

↓

DispatcherServlet

↓

HandlerExceptionResolver

↓

@ControllerAdvice

↓

Error Response

Instead of crashing the application,

Spring delegates exception handling.


==============================================================================================
                     COMPLETE END TO END SPRING MVC FLOW
==============================================================================================


                           HTTP REQUEST


        Client

           │

           ▼

 Embedded Tomcat

           │

           ▼

 Servlet Filters

           │

           ▼

 DispatcherServlet

           │

           ▼

 HandlerMapping

           │

           ▼

 HandlerExecutionChain

           │

           ▼

 preHandle()

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


======================== RESPONSE ========================


 Database

    │

    ▼

 Repository

    │

    ▼

 Service

    │

    ▼

 Controller

    │

    ▼

 postHandle()

    │

    ▼

 ViewResolver

    │

    ▼

 afterCompletion()

    │

    ▼

 DispatcherServlet

    │

    ▼

 Servlet Filters

    │

    ▼

 Client


This entire journey happens
for every HTTP Request.


==============================================================================================
                    DESIGN PATTERN USED
==============================================================================================

DispatcherServlet is one of the best examples
of the

                    Front Controller Pattern


Meaning

Every request enters

ONE SINGLE CONTROLLER

which coordinates the entire framework.

Benefits

✔ Centralized Request Processing

✔ Easy Logging

✔ Security

✔ Exception Handling

✔ Flexible Architecture

Without Front Controller,

every Controller would need to repeat
framework-level logic.


==============================================================================================
                        SOLID PRINCIPLES FOLLOWED
==============================================================================================

Open Closed Principle

↓

New Handler

↓

New HandlerAdapter

↓

DispatcherServlet remains unchanged.


------------------------------------------------------------

Single Responsibility Principle

DispatcherServlet

↓

Coordinates Request

Only.

It does NOT

❌ Execute SQL

❌ Perform Business Logic

❌ Validate DTOs

❌ Calculate Salary

Each component has one responsibility.


------------------------------------------------------------

Dependency Inversion Principle

DispatcherServlet depends upon

interfaces

such as

HandlerMapping

HandlerAdapter

ViewResolver

instead of concrete implementations.


==============================================================================================
                      REAL WORLD ANALOGY
==============================================================================================

Imagine an Airport.

Passenger

↓

Reception Desk

↓

Find Correct Counter

↓

Security

↓

Boarding Gate

↓

Flight

Reception Desk never

✔ Flies the Plane

✔ Checks Passport

✔ Loads Luggage

It only coordinates.

DispatcherServlet behaves exactly
like the Reception Desk.


==============================================================================================
                         COMMON INTERVIEW QUESTIONS
==============================================================================================

Q1

What is DispatcherServlet?

Answer

DispatcherServlet is the Front Controller
of Spring MVC.

It receives every request
and coordinates all Spring MVC components.


------------------------------------------------------------

Q2

Who calls DispatcherServlet?

Answer

Embedded Tomcat.


------------------------------------------------------------

Q3

Who calls HandlerMapping?

Answer

DispatcherServlet.


------------------------------------------------------------

Q4

Who executes the Controller?

Answer

HandlerAdapter.


------------------------------------------------------------

Q5

Who resolves the View?

Answer

ViewResolver.


------------------------------------------------------------

Q6

Who handles Exceptions?

Answer

HandlerExceptionResolver.


------------------------------------------------------------

Q7

Does DispatcherServlet execute SQL?

Answer

No.

It only coordinates.


==============================================================================================
                         COMMON MISTAKES
==============================================================================================

❌ Thinking

DispatcherServlet executes Controllers.

Actually

HandlerAdapter executes Controllers.


------------------------------------------------------------

❌ Thinking

DispatcherServlet contains business logic.

Wrong.

Business Logic belongs to

Service Layer.


------------------------------------------------------------

❌ Thinking

DispatcherServlet finds Controllers itself.

Wrong.

It delegates that work to

HandlerMapping.


==============================================================================================
                            MEMORY TRICK
==============================================================================================

Remember

DispatcherServlet

↓

Find

HandlerMapping

↓

Execute

HandlerAdapter

↓

Business Logic

Controller

↓

Resolve

ViewResolver

↓

Return

Client


Simple Formula

Receive

↓

Find

↓

Execute

↓

Resolve

↓

Respond


==============================================================================================
                          FINAL SUMMARY
==============================================================================================

DispatcherServlet is the heart of Spring MVC.

It receives every request from Tomcat.

It delegates work to

✔ HandlerMapping

✔ HandlerExecutionChain

✔ HandlerAdapter

✔ ViewResolver

✔ HandlerExceptionResolver

Instead of performing every task itself,
DispatcherServlet coordinates different
Spring MVC components.

This delegation-based architecture makes
Spring MVC highly extensible,
maintainable,
and loosely coupled.


═══════════════════════════════════════════════════════════════════════════════════════════════

🎉 DISPATCHERSERVLET DEEP DIVE COMPLETED

Next Chapter

📖 HandlerMapping Deep Dive

═══════════════════════════════════════════════════════════════════════════════════════════════

*/

public class DispatcherServletDeepDive {

}

