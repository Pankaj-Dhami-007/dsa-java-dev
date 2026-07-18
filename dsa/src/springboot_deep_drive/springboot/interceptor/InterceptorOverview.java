package springboot_deep_drive.springboot.interceptor;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                        📖 CHAPTER 01 : INTERCEPTOR OVERVIEW                               ║
║                                                                                            ║
║                          ⏱ Reading Time : 10 Minutes                                      ║
║                          📈 Difficulty : 🟢 Beginner                                      ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is an Interceptor?

✔ Why do we need an Interceptor?

✔ Where does an Interceptor execute?

✔ How is it different from a Filter (basic idea)?

✔ Common real-world use cases.


==============================================================================================
                                 📖 WHAT IS AN INTERCEPTOR?
==============================================================================================

An Interceptor is a Spring MVC component that intercepts an HTTP request
before it reaches the Controller and also intercepts the response before
it is returned to the client.

Unlike Filters, Interceptors are part of the Spring MVC framework.
They work only after the request enters Spring MVC.


==============================================================================================
                         🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine you visit a company.

                    Visitor
                       │
                       ▼
             Security Guard (Filter)
                       │
                       ▼
              Reception Desk (Interceptor)
                       │
                       ▼
                  Manager (Controller)

The Security Guard checks whether you can enter the building.

The Receptionist checks where you should go,
whether you have an appointment,
and informs the Manager.

Similarly,

Filter

↓

Allows the request into the application.

Interceptor

↓

Performs Spring MVC-related processing
before the Controller executes.


==============================================================================================
                           ⚙ WHERE DOES AN INTERCEPTOR EXECUTE?
==============================================================================================

                         HTTP REQUEST

                ┌───────────────┐
                │    Client     │
                └───────┬───────┘
                        │
                        ▼
            ┌──────────────────────┐
            │       Filter         │
            └──────────┬───────────┘
                       │
                       ▼
            ┌──────────────────────┐
            │ DispatcherServlet    │
            └──────────┬───────────┘
                       │
                       ▼
            ┌──────────────────────┐
            │     Interceptor      │
            └──────────┬───────────┘
                       │
                       ▼
            ┌──────────────────────┐
            │     Controller       │
            └──────────┬───────────┘
                       │
                       ▼
            ┌──────────────────────┐
            │       Service        │
            └──────────┬───────────┘
                       │
                       ▼
            ┌──────────────────────┐
            │     Repository       │
            └──────────────────────┘


Notice carefully.

✔ Filter executes BEFORE DispatcherServlet.
✔ Interceptor executes AFTER DispatcherServlet.

This is the biggest difference between them.


==============================================================================================
                              ❓ WHY DO WE NEED INTERCEPTORS?
==============================================================================================

Suppose every Controller needs to perform

✔ User Validation
✔ Logging
✔ Execution Time Measurement
✔ Permission Checking
✔ Locale Selection

Writing the same code inside every Controller
would create duplicate code.

Instead,

the common logic can be written once
inside an Interceptor.
Controllers remain focused only on business logic.


==============================================================================================
                               💼 COMMON USE CASES
==============================================================================================

Interceptors are commonly used for

✔ Logging
✔ Authorization
✔ Performance Monitoring
✔ Locale Change
✔ Audit Logs
✔ User Activity Tracking
✔ Request Validation

Notice

These tasks are related to the MVC layer,
not the Servlet layer.


==============================================================================================
                              🎯 IMPORTANT OBSERVATIONS
==============================================================================================

✔ Interceptors belong to Spring MVC.
✔ Filters belong to the Servlet API.
✔ Interceptors execute after DispatcherServlet.
✔ Interceptors can access Controller information.
✔ Filters cannot directly access Controller details.


==============================================================================================
                              💎 INTERVIEW POINT
==============================================================================================

Question

Which executes first?

Filter or Interceptor?

Answer

Filter

↓

DispatcherServlet

↓

Interceptor

↓

Controller


==============================================================================================
                               ⚠ COMMON MISTAKES
==============================================================================================

❌ Thinking Interceptor replaces Filter.

Wrong.

Both solve different problems.

------------------------------------------------------------

❌ Using an Interceptor for CORS or Compression.

These belong to the Servlet layer,
so Filters are more appropriate.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

✔ Interceptor is a Spring MVC component.

✔ It executes after DispatcherServlet.

✔ It executes before the Controller.

✔ It helps remove duplicate MVC-related logic.

✔ It is mainly used for logging,
authorization,
auditing,
and request validation.


==============================================================================================
                                  📚 QUICK REVISION
==============================================================================================

                    Client
                       │
                       ▼
                    Filter
                       │
                       ▼
              DispatcherServlet
                       │
                       ▼
                 Interceptor
                       │
                       ▼
                  Controller


🧠 MEMORY TRICK

Filter

↓

DispatcherServlet

↓

Interceptor

↓

Controller


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : HandlerInterceptor Lifecycle
(preHandle(), postHandle(), afterCompletion())

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class InterceptorOverview {

}