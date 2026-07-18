package springboot_deep_drive.springboot.interceptor;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                    📖 CHAPTER 06 : FILTER vs INTERCEPTOR                                  ║
║                                                                                            ║
║                          ⏱ Reading Time : 10 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ Difference between Filter and Interceptor.

✔ Where each executes.

✔ Which one executes first.

✔ When to use Filter.

✔ When to use Interceptor.

✔ Most asked interview questions.


==============================================================================================
                               🤔 WHY DO WE HAVE BOTH?
==============================================================================================

Many beginners ask,

"If Interceptor can intercept requests,
then why do we even need Filters?"

The answer is simple.

Both solve different problems.

Filter belongs to the Servlet Layer.

Interceptor belongs to the Spring MVC Layer.

Neither replaces the other.


==============================================================================================
                           ⚙ COMPLETE REQUEST PIPELINE
==============================================================================================

                                 HTTP REQUEST

          ┌──────────────┐
          │    Client    │
          └──────┬───────┘
                 │
                 ▼
      ┌─────────────────────┐
      │ Servlet Filter      │
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
      │ Interceptor         │
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


Notice

Filter executes BEFORE DispatcherServlet.

Interceptor executes AFTER DispatcherServlet.


==============================================================================================
                              📊 COMPARISON TABLE
==============================================================================================

┌──────────────────────────────┬─────────────────────────────────────────────┐
│ Filter                       │ Interceptor                                │
├──────────────────────────────┼─────────────────────────────────────────────┤
│ Part of Servlet API          │ Part of Spring MVC                         │
│ Managed by Tomcat            │ Managed by Spring                          │
│ Executes before Dispatcher   │ Executes after Dispatcher                  │
│ Works on every request       │ Works on mapped MVC requests               │
│ Can modify Request           │ Can access Controller information          │
│ Cannot access Controller     │ Knows which Controller will execute        │
│ Used for Infrastructure      │ Used for MVC processing                    │
└──────────────────────────────┴─────────────────────────────────────────────┘


==============================================================================================
                        🌍 WHEN SHOULD WE USE A FILTER?
==============================================================================================

Use a Filter when the work belongs to the Servlet layer.

Examples

✔ JWT Authentication

✔ CORS

✔ Compression

✔ Request Logging

✔ Response Headers

✔ Character Encoding

✔ Request Wrapping


==============================================================================================
                     🌍 WHEN SHOULD WE USE AN INTERCEPTOR?
==============================================================================================

Use an Interceptor when the work belongs to Spring MVC.

Examples

✔ Role Checking

✔ Controller Logging

✔ Execution Time

✔ Locale Change

✔ Audit

✔ User Activity Tracking


==============================================================================================
                           💎 INTERVIEW QUESTIONS
==============================================================================================

Question

Which executes first?

Answer

Filter

↓

DispatcherServlet

↓

Interceptor

↓

Controller


------------------------------------------------------------

Question

Which can access Controller information?

Answer

Interceptor.


------------------------------------------------------------

Question

Which belongs to the Servlet API?

Answer

Filter.


------------------------------------------------------------

Question

Which belongs to Spring MVC?

Answer

Interceptor.


==============================================================================================
                            ⚠ COMMON MISTAKES
==============================================================================================

❌ Using an Interceptor for CORS.

Use a Filter instead.


------------------------------------------------------------

❌ Using a Filter when Controller information is required.

Use an Interceptor.


------------------------------------------------------------

❌ Thinking Interceptor replaces Filter.

Wrong.

They work together.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

Filter

✔ Servlet Layer

✔ Executes First

✔ Infrastructure Work


--------------------------------------------


Interceptor

✔ Spring MVC Layer

✔ Executes After DispatcherServlet

✔ MVC Related Work


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


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Filter

↓

Framework Entry

↓

DispatcherServlet

↓

Interceptor

↓

Controller

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🏁 FILTER MODULE + INTERCEPTOR MODULE COMPLETED

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class FilterVsInterceptorDeepDive {

}