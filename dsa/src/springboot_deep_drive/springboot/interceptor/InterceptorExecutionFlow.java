package springboot_deep_drive.springboot.interceptor;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                  📖 CHAPTER 05 : INTERCEPTOR EXECUTION FLOW                               ║
║                                                                                            ║
║                          ⏱ Reading Time : 12 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ Complete Request Flow

✔ Where preHandle() executes

✔ Where postHandle() executes

✔ Where afterCompletion() executes

✔ Complete Response Flow


==============================================================================================
                             📖 COMPLETE REQUEST FLOW
==============================================================================================


                                 HTTP REQUEST

          ┌──────────────┐
          │    Client    │
          └──────┬───────┘
                 │
                 ▼
      ┌─────────────────────┐
      │ Servlet Filter(s)   │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ DispatcherServlet   │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Handler Mapping     │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ preHandle()         │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Controller          │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Service             │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Repository          │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Database            │
      └─────────────────────┘


==============================================================================================
                            📖 COMPLETE RESPONSE FLOW
==============================================================================================


      ┌─────────────────────┐
      │ Database            │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Repository          │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Service             │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Controller          │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ postHandle()        │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ View Rendering      │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ afterCompletion()   │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ DispatcherServlet   │
      └─────────┬───────────┘
                │
                ▼
      ┌─────────────────────┐
      │ Servlet Filter(s)   │
      └─────────┬───────────┘
                │
                ▼
           ┌────────────┐
           │   Client   │
           └────────────┘


==============================================================================================
                          🧠 WHAT ACTUALLY HAPPENS?
==============================================================================================

Step 1

Client sends HTTP Request.

                │

                ▼

Servlet Filter executes.


------------------------------------------------------------

Step 2

DispatcherServlet receives the request.

                │

                ▼

Handler Mapping finds the Controller.


------------------------------------------------------------

Step 3

Spring executes

preHandle()

before calling the Controller.


------------------------------------------------------------

Step 4

Controller executes.

Business Logic starts.

Controller

↓

Service

↓

Repository

↓

Database


------------------------------------------------------------

Step 5

Controller returns Response.

Now

postHandle()

is executed.


------------------------------------------------------------

Step 6

Response is prepared.

Finally,

afterCompletion()

executes.

This is the last Interceptor callback.


==============================================================================================
                        🚫 WHAT IF preHandle() RETURNS FALSE?
==============================================================================================


          ┌──────────────┐
          │    Client    │
          └──────┬───────┘
                 │
                 ▼
      ┌─────────────────────┐
      │ preHandle()         │
      └─────────┬───────────┘
                │
        returns false
                │
                ▼
         ❌ REQUEST STOPS


Controller

❌ Not Executed


Service

❌ Not Executed


Repository

❌ Not Executed


==============================================================================================
                             💎 INTERVIEW POINT
==============================================================================================

Question

Which method executes first?

Answer

Filter

↓

DispatcherServlet

↓

preHandle()

↓

Controller


------------------------------------------------------------

Question

Which method executes last?

Answer

afterCompletion()


==============================================================================================
                             ⚠ COMMON MISTAKES
==============================================================================================

❌ Thinking

postHandle()

executes after Response reaches Client.

Wrong.

It executes BEFORE the Response is sent.


------------------------------------------------------------

❌ Thinking

afterCompletion()

executes before postHandle().

Wrong.

It is always the LAST callback.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

HTTP Request

↓

Filter

↓

DispatcherServlet

↓

preHandle()

↓

Controller

↓

Service

↓

Repository

↓

Database

↓

postHandle()

↓

afterCompletion()

↓

Client


==============================================================================================
                                  📚 QUICK REVISION
==============================================================================================

Before Controller

↓

preHandle()

-------------------------

After Controller

↓

postHandle()

-------------------------

After Request Completion

↓

afterCompletion()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

PRE

↓

Controller

↓

POST

↓

COMPLETE

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Filter vs Interceptor

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class InterceptorExecutionFlow {

}