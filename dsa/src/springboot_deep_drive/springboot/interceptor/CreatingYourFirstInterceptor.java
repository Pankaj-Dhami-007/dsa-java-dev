package springboot_deep_drive.springboot.interceptor;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                    📖 CHAPTER 03 : CREATING YOUR FIRST INTERCEPTOR                        ║
║                                                                                            ║
║                          ⏱ Reading Time : 8 Minutes                                       ║
║                          📈 Difficulty : 🟢 Beginner                                      ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ How to create an Interceptor.

✔ Which interface should be implemented.

✔ Which methods should be overridden.

✔ What is the minimum code required.


==============================================================================================
                            📖 CREATING AN INTERCEPTOR
==============================================================================================

Creating an Interceptor is very simple.

We just create a normal Java class and implement

                        HandlerInterceptor

Example

public class LoggingInterceptor implements HandlerInterceptor {

}

That's it.

Now Spring knows that this class can intercept
HTTP Requests.


==============================================================================================
                           📦 HandlerInterceptor INTERFACE
==============================================================================================

public interface HandlerInterceptor {

    boolean preHandle(...);

    void postHandle(...);

    void afterCompletion(...);

}

These are the same lifecycle methods
that we studied in the previous chapter.


==============================================================================================
                         ⚙ STEP 1 : CREATE THE CLASS
==============================================================================================

public class LoggingInterceptor
        implements HandlerInterceptor {

}

Nothing special.

It is just a normal Java class.


==============================================================================================
                       ⚙ STEP 2 : OVERRIDE METHODS
==============================================================================================

@Override
public boolean preHandle(...) {

    return true;
}

@Override
public void postHandle(...) {

}

@Override
public void afterCompletion(...) {

}

You can override

✔ One method

✔ Two methods

✔ All three methods

depending on your requirement.


==============================================================================================
                        ⚙ STEP 3 : WRITE YOUR LOGIC
==============================================================================================

Example

@Override
public boolean preHandle(...) {

    System.out.println("Request Received");

    return true;
}


Execution

Client

        │

        ▼

Request Received

        │

        ▼

Controller


Very Important

Always return

true

unless you intentionally want to stop the request.


==============================================================================================
                           🌍 REAL WORLD EXAMPLE
==============================================================================================

Suppose every request should be logged.

Instead of writing

System.out.println(...)

inside every Controller,

we create one Logging Interceptor.


Client

        │

        ▼

Logging Interceptor

        │

        ▼

Controller


Every request is automatically logged.


==============================================================================================
                             💎 INTERVIEW POINT
==============================================================================================

Question

Can an Interceptor contain only preHandle()?

Answer

Yes.

You only override the methods
that your application needs.


==============================================================================================
                            ⚠ COMMON MISTAKES
==============================================================================================

❌ Forgetting to return true
from preHandle().

Result

Controller is never executed.


------------------------------------------------------------

❌ Writing business logic
inside an Interceptor.

Business logic always belongs
inside the Service Layer.


==============================================================================================
                                   📝 SUMMARY
==============================================================================================

✔ Create a Java class.

✔ Implement HandlerInterceptor.

✔ Override required methods.

✔ Write your logic.

✔ Return true from preHandle().


==============================================================================================
                                 📚 QUICK REVISION
==============================================================================================

Create Class

        │

        ▼

Implement

HandlerInterceptor

        │

        ▼

Override Methods

        │

        ▼

Write Logic

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Create

↓

Implement

↓

Override

↓

Return true

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Registering an Interceptor

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class CreatingYourFirstInterceptor {

}