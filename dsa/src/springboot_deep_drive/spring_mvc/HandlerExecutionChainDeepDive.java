package springboot_deep_drive.spring_mvc;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                  📖 CHAPTER 08 : HANDLER EXECUTION CHAIN                                  ║
║                                                                                            ║
║                          ⏱ Reading Time : 15 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                   LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter you will understand

✔ What is HandlerExecutionChain?

✔ Why did Spring create HandlerExecutionChain?

✔ What does it contain?

✔ How does it work internally?

✔ Why are Interceptors stored inside it?

✔ Why do postHandle() and afterCompletion() execute in reverse order?

✔ Important interview questions.


==============================================================================================
                               THE BIG QUESTION
==============================================================================================

In the previous chapter we learned

HandlerMapping

↓

returns

↓

HandlerExecutionChain

Question

Why doesn't HandlerMapping simply return

EmployeeController#getEmployees()

Why another object?

Answer

Because Spring doesn't only need the Controller.

It also needs all Interceptors
associated with that request.

Therefore Spring bundles everything
inside one object.

That object is called

                HandlerExecutionChain.


==============================================================================================
                        WHAT IS HANDLEREXECUTIONCHAIN?
==============================================================================================

HandlerExecutionChain is a Spring MVC class.

It represents

"The complete execution information
required to process one request."

Think of it as

                📦 Request Package

Everything needed to process the request
is packed together.


==============================================================================================
                      WHAT DOES IT CONTAIN?
==============================================================================================

A HandlerExecutionChain mainly contains

                ┌──────────────────────────────┐
                │           Handler            │
                │ (Controller Method)          │
                └──────────────┬───────────────┘
                               │
                               ▼
                ┌──────────────────────────────┐
                │     Interceptor List         │
                └──────────────────────────────┘


Simplified representation

class HandlerExecutionChain {

    Object handler;

    HandlerInterceptor[] interceptors;

}


Meaning

Handler

↓

Which Controller should execute?

Interceptors

↓

Which Interceptors should execute
before and after the Controller?


==============================================================================================
                     COMPLETE REQUEST FLOW
==============================================================================================

                         HTTP REQUEST


Client

   │

   ▼

DispatcherServlet

   │

   ▼

HandlerMapping

   │

   ▼

Returns

HandlerExecutionChain

   │

   ▼

DispatcherServlet

   │

   ▼

Execute

preHandle()

   │

   ▼

Execute

Controller

   │

   ▼

Execute

postHandle()

   │

   ▼

Execute

afterCompletion()

   │

   ▼

Client


Notice

DispatcherServlet now has

✔ Controller

✔ All Interceptors

Everything required
to complete the request.


==============================================================================================
                 INTERNAL WORKING OF SPRING
==============================================================================================

Suppose we have

LoggingInterceptor

SecurityInterceptor

AuditInterceptor

Controller

EmployeeController


HandlerExecutionChain becomes


        HandlerExecutionChain

        ┌────────────────────────────┐
        │ Handler                    │
        │ EmployeeController         │
        ├────────────────────────────┤
        │ LoggingInterceptor         │
        │ SecurityInterceptor        │
        │ AuditInterceptor           │
        └────────────────────────────┘


DispatcherServlet receives this object.

Now it doesn't need to search
for anything else.


==============================================================================================
                HOW preHandle() EXECUTES
==============================================================================================

Suppose three Interceptors exist.

Execution


Logging

      │

      ▼

Security

      │

      ▼

Audit

      │

      ▼

Controller


Spring internally loops
through the Interceptor list
from FIRST to LAST.

Conceptually

for(each interceptor){

    preHandle();

}


Execution Order

Logging

↓

Security

↓

Audit

↓

Controller


==============================================================================================
               HOW postHandle() EXECUTES
==============================================================================================

Now Controller has finished.

Question

Should Spring again execute

Logging

↓

Security

↓

Audit

?

No.

It executes them in

REVERSE ORDER.


Execution

Controller

↓

Audit

↓

Security

↓

Logging


Conceptually

for(reverse order){

    postHandle();

}


==============================================================================================
           WHY REVERSE ORDER?
==============================================================================================

Imagine nested method calls.

Logging

    opens timer

        ↓

Security

    validates user

        ↓

Audit

    starts audit

        ↓

Controller

        ↓

Audit finishes

        ↓

Security finishes

        ↓

Logging finishes


Exactly like a Stack (LIFO).

The last interceptor entered

is the first one to exit.


==============================================================================================
            afterCompletion() EXECUTION
==============================================================================================

afterCompletion()

also executes

in REVERSE ORDER.


Controller

      │

      ▼

Audit

      │

      ▼

Security

      │

      ▼

Logging


Reason

Cleanup should happen
in the reverse order
of initialization.


==============================================================================================
                  VISUAL EXECUTION DIAGRAM
==============================================================================================


                    HTTP REQUEST


        Logging Interceptor

                │

                ▼

        Security Interceptor

                │

                ▼

          Audit Interceptor

                │

                ▼

            Controller

================ RESPONSE =================

            Controller

                │

                ▼

          Audit Interceptor

                │

                ▼

        Security Interceptor

                │

                ▼

        Logging Interceptor


Think of it as

                PUSH

↓

Controller

↓

POP


==============================================================================================
                     DESIGN THINKING
==============================================================================================

Question

Why not store Interceptors
inside DispatcherServlet?

Answer

Because every request
can have a different Controller
and a different set of Interceptors.

HandlerExecutionChain represents

ONE REQUEST.

DispatcherServlet is shared
by ALL REQUESTS.

Therefore

request-specific information
should stay inside

HandlerExecutionChain.


==============================================================================================
                    REAL WORLD ANALOGY
==============================================================================================

Imagine ordering food online.

Your delivery packet contains

✔ Food

✔ Bill

✔ Spoon

✔ Sauce

Instead of carrying them separately,

everything comes together.

Similarly

HandlerExecutionChain contains

✔ Controller

✔ Interceptors

as one package.


==============================================================================================
                     INTERVIEW QUESTIONS
==============================================================================================

Q.

What is HandlerExecutionChain?

Answer

It is an object returned by HandlerMapping
that contains the Handler
and all applicable Interceptors.


------------------------------------------------------------

Q.

Why does Spring use it?

Answer

To keep all request-specific execution
information together.


------------------------------------------------------------

Q.

Why does postHandle()
execute in reverse order?

Answer

Because Interceptors behave like
a stack (LIFO).


------------------------------------------------------------

Q.

Does DispatcherServlet search
Interceptors separately?

Answer

No.

They already come inside
HandlerExecutionChain.


==============================================================================================
                      COMMON MISTAKES
==============================================================================================

❌ Thinking

HandlerExecutionChain executes Controllers.

Wrong.

DispatcherServlet executes the chain.

HandlerAdapter executes the Controller.


------------------------------------------------------------

❌ Thinking

HandlerExecutionChain contains
only the Controller.

Wrong.

It contains

✔ Handler

✔ Interceptors


------------------------------------------------------------

❌ Thinking

postHandle() executes
in the same order as preHandle().

Wrong.

It executes in reverse order.


==============================================================================================
                           MEMORY TRICK
==============================================================================================

HandlerExecutionChain

↓

Handler

+

Interceptors


Request

↓

Forward Order


Response

↓

Reverse Order


Simple Formula

Bundle

↓

Execute

↓

Reverse


==============================================================================================
                           FINAL SUMMARY
==============================================================================================

✔ HandlerExecutionChain is created by HandlerMapping.

✔ It bundles the Handler and all matching Interceptors.

✔ preHandle() executes from first to last.

✔ postHandle() executes from last to first.

✔ afterCompletion() also executes in reverse order.

✔ This design keeps DispatcherServlet simple and
   request-specific data organized.

═══════════════════════════════════════════════════════════════════════════════════════════════

🎉 SPRING MVC CORE PIPELINE COMPLETED

Servlet
   │
Filter
   │
DispatcherServlet
   │
HandlerMapping
   │
HandlerExecutionChain
   │
HandlerAdapter
   │
Controller
   │
Service
   │
Repository

═══════════════════════════════════════════════════════════════════════════════════════════════
*/

public class HandlerExecutionChainDeepDive {

}