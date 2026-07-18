package springboot_deep_drive.springboot.interceptor;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                  📖 CHAPTER 02 : HANDLERINTERCEPTOR LIFECYCLE                             ║
║                                                                                            ║
║                          ⏱ Reading Time : 12 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is HandlerInterceptor?
✔ Why does Spring provide HandlerInterceptor?
✔ What are preHandle(), postHandle() and afterCompletion()?
✔ In which order are these methods executed?
✔ What happens when preHandle() returns false?


==============================================================================================
                               📖 WHAT IS HandlerInterceptor?
==============================================================================================

Interceptor is implemented using the HandlerInterceptor interface.
This interface provides three callback methods.

                ┌────────────────────────────┐
                │     HandlerInterceptor     │
                └─────────────┬──────────────┘
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
 preHandle()            postHandle()      afterCompletion()

These methods are automatically called by Spring MVC.


==============================================================================================
                           ⚙ COMPLETE REQUEST LIFECYCLE
==============================================================================================

                           HTTP REQUEST

               ┌───────────────┐
               │    Client     │
               └──────┬────────┘
                      │
                      ▼
           ┌─────────────────────┐
           │      Filter         │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │ DispatcherServlet   │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │    preHandle()      │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │     Controller      │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │    postHandle()     │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │ afterCompletion()   │
           └─────────┬───────────┘
                     │
                     ▼
                  Client


Remember

preHandle()

↓

Controller

↓

postHandle()

↓

afterCompletion()


==============================================================================================
                                1️⃣ preHandle()
==============================================================================================

This method executes BEFORE the Controller.

Purpose

✔ Authentication
✔ Authorization
✔ Logging
✔ Request Validation
✔ Block Unauthorized Requests


Execution

Request

        │

        ▼

preHandle()

        │

        ▼

Controller


Very Important

preHandle() returns

boolean


return true

↓

Continue Request


return false

↓

Stop Request


==============================================================================================
                         🚫 WHAT IF preHandle() RETURNS FALSE?
==============================================================================================

                Client
                   │
                   ▼
            preHandle()
                   │
         returns false ❌
                   │
                   ▼
        Controller is NEVER executed

Spring immediately stops request processing.


==============================================================================================
                                2️⃣ postHandle()
==============================================================================================

postHandle() executes AFTER the Controller
but BEFORE the response is sent to the Client.

Execution

Controller

        │

        ▼

postHandle()

        │

        ▼

Client


Common Use Cases

✔ Add Model Data

✔ Modify Response

✔ Logging


==============================================================================================
                             3️⃣ afterCompletion()
==============================================================================================

This is the LAST interceptor method.

It executes after the complete request is finished.

Execution

Controller

        │

        ▼

Response Sent

        │

        ▼

afterCompletion()


Common Uses

✔ Cleanup Resources

✔ Final Logging

✔ Performance Monitoring

✔ Exception Logging


==============================================================================================
                         ⚖ EXECUTION ORDER
==============================================================================================

                    NORMAL REQUEST

preHandle()

        │

        ▼

Controller

        │

        ▼

postHandle()

        │

        ▼

afterCompletion()


==============================================================================================
                             💎 INTERVIEW POINT
==============================================================================================

Question

Which method can stop a request?

Answer

preHandle()

because it returns

boolean


==============================================================================================
                            ⚠ COMMON MISTAKES
==============================================================================================

❌ Thinking postHandle() executes before Controller.

Wrong.

It executes AFTER Controller.


------------------------------------------------------------

❌ Thinking afterCompletion() executes before Response.

Wrong.

It executes after request processing is completed.


------------------------------------------------------------

❌ Forgetting to return true from preHandle().

Result

The request never reaches the Controller.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

┌────────────────────┬────────────────────────────────────────────┐
│ Method             │ Purpose                                    │
├────────────────────┼────────────────────────────────────────────┤
│ preHandle()        │ Before Controller                          │
│ postHandle()       │ After Controller                           │
│ afterCompletion()  │ After Complete Request                     │
└────────────────────┴────────────────────────────────────────────┘


==============================================================================================
                                  📚 QUICK REVISION
==============================================================================================

Request

        │

        ▼

preHandle()

        │

        ▼

Controller

        │

        ▼

postHandle()

        │

        ▼

afterCompletion()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

PRE

↓

CONTROLLER

↓

POST

↓

COMPLETE

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Registering an Interceptor

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class HandlerInterceptorLifecycle {

}