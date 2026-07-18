package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                          📖 CHAPTER 02 : FILTER LIFECYCLE                                 ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is the lifecycle of a Filter?
✔ What lifecycle methods are available in the Filter interface?
✔ When each method is executed.
✔ Who invokes these methods.
✔ What should be written inside each lifecycle method.
✔ Important interview questions related to Filter Lifecycle.


==============================================================================================
                                  📖 WHAT IS FILTER LIFECYCLE?
==============================================================================================

Every Filter follows a fixed lifecycle managed by the Servlet Container (Tomcat).

From the moment a Filter is created until the application shuts down,
the Servlet Container controls the complete lifecycle.

A Filter has only three lifecycle methods:

    ✔ init()
    ✔ doFilter()
    ✔ destroy()

These methods are defined in the Servlet API.


==============================================================================================
                                   ⚙ FILTER LIFECYCLE FLOW
==============================================================================================

                          Application Starts
                                  │
                                  ▼
                     Embedded Tomcat Starts
                                  │
                                  ▼
                    Creates Filter Object
                                  │
                                  ▼
                              init()
                       (Called Only Once)
                                  │
                                  ▼
                      Application Running
                                  │
                    Every Incoming Request
                                  │
                                  ▼
                           doFilter()
                    (Called For Every Request)
                                  │
                                  ▼
                    Application Shuts Down
                                  │
                                  ▼
                             destroy()
                       (Called Only Once)


==============================================================================================
                                   1️⃣ init()
==============================================================================================

Purpose

The init() method is executed only once when the application starts.

After creating the Filter object, the Servlet Container immediately calls init()
to perform initialization work.

Typical use cases:

✔ Load configuration files.

✔ Read application properties.

✔ Initialize expensive resources.

✔ Prepare objects required by the Filter.

Execution Flow

Application Starts

        │

        ▼

Filter Created

        │

        ▼

init()

        │

        ▼

Ready To Process Requests


Important

init() is NOT called for every request.

It executes only once during application startup.


==============================================================================================
                                  2️⃣ doFilter()
==============================================================================================

This is the most important lifecycle method.

Every incoming HTTP Request passes through this method.

Almost all Filter logic is written here.

Typical use cases:

✔ Logging

✔ Authentication

✔ Authorization

✔ JWT Validation

✔ Header Validation

✔ Request Timing

Request Flow

Client
   │
   ▼
doFilter()
   │
   ▼
DispatcherServlet
   │
   ▼
Controller
   │
   ▼
Service
   │
   ▼
Repository

Notice

Unlike init(), the doFilter() method executes for EVERY request.


==============================================================================================
                                 3️⃣ destroy()
==============================================================================================

The destroy() method is executed only once before the application shuts down.

Its purpose is to release resources used by the Filter.

Typical use cases:

✔ Close open resources.

✔ Release memory.

✔ Cleanup objects.

Execution Flow

Application Stopping

        │

        ▼

destroy()

        │

        ▼

Resources Released


==============================================================================================
                            👨‍💻 WHO CALLS THESE METHODS?
==============================================================================================

Many beginners think Spring Boot calls these methods.

Actually...

The Servlet Container (Embedded Tomcat) manages the complete lifecycle.

Flow

Spring Boot Starts

        │

        ▼

Embedded Tomcat Starts

        │

        ▼

Tomcat Detects Filter

        │

        ▼

Creates Filter Object

        │

        ▼

Calls init()

        │

        ▼

Calls doFilter() For Every Request

        │

        ▼

Calls destroy() Before Shutdown


Summary Table

┌──────────────┬──────────────────────────────┐
│ Method       │ Called By                    │
├──────────────┼──────────────────────────────┤
│ init()       │ Servlet Container (Tomcat)  │
│ doFilter()   │ Servlet Container (Tomcat)  │
│ destroy()    │ Servlet Container (Tomcat)  │
└──────────────┴──────────────────────────────┘


==============================================================================================
                               📅 LIFECYCLE TIMELINE
==============================================================================================

Application Starts

        │

        ▼

init()

        │

        ▼

Request 1

        │

        ▼

doFilter()

        │

        ▼

Request 2

        │

        ▼

doFilter()

        │

        ▼

Request 3

        │

        ▼

doFilter()

        │

        ▼

Application Stops

        │

        ▼

destroy()


==============================================================================================
                               🎯 INTERVIEW QUESTIONS
==============================================================================================

Q1. How many lifecycle methods does the Filter interface have?

Answer:
Three.

✔ init()

✔ doFilter()

✔ destroy()


------------------------------------------------------------

Q2. Which method executes only once?

Answer:

init()


------------------------------------------------------------

Q3. Which method executes for every request?

Answer:

doFilter()


------------------------------------------------------------

Q4. Which method executes before application shutdown?

Answer:

destroy()


------------------------------------------------------------

Q5. Who invokes these lifecycle methods?

Answer:

The Servlet Container (Embedded Tomcat).

Spring Boot does not invoke them directly.


==============================================================================================
                               ⚠ COMMON MISTAKES
==============================================================================================

❌ Writing request processing code inside init().

Reason:

init() executes only once.

Request processing belongs inside doFilter().


------------------------------------------------------------

❌ Opening expensive resources inside doFilter().

Reason:

doFilter() executes for every request.

Initialize expensive resources inside init() whenever possible.


------------------------------------------------------------

❌ Forgetting resource cleanup.

Always release resources inside destroy() if they were manually created.


==============================================================================================
                               🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine a Shopping Mall.

Morning

        │

        ▼

Security Guard Arrives

        │

        ▼

Opens Security System

        │

        ▼

Ready


Customer Arrives

        │

        ▼

Security Check

        │

        ▼

Entry


Mall Closes

        │

        ▼

Security System Shutdown

        │

        ▼

Guard Leaves


Mapping

Morning Setup        → init()

Customer Checking    → doFilter()

Mall Closing         → destroy()


==============================================================================================
                                     📝 SUMMARY
==============================================================================================

┌──────────────┬──────────────────────────────┬──────────────────────┐
│ Method       │ Purpose                      │ Frequency            │
├──────────────┼──────────────────────────────┼──────────────────────┤
│ init()       │ Initialization               │ Once                 │
│ doFilter()   │ Process Every Request        │ Every Request        │
│ destroy()    │ Cleanup Resources            │ Once                 │
└──────────────┴──────────────────────────────┴──────────────────────┘


Golden Interview Definition

"A Filter has three lifecycle methods: init(), doFilter(), and destroy().
The Servlet Container creates the Filter object, invokes init() once during
application startup, calls doFilter() for every incoming request, and finally
invokes destroy() once before shutting down the application."


==============================================================================================
                                  📚 QUICK REVISION
==============================================================================================

✔ A Filter has three lifecycle methods.

✔ init() executes only once.

✔ doFilter() executes for every request.

✔ destroy() executes only once.

✔ The Servlet Container manages the complete lifecycle.

✔ doFilter() contains the main Filter logic.


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

⏱ Reading Time : 8 Minutes

📈 Difficulty : 🟢 Beginner

➡ Next Chapter : Filter Chain

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class FilterLifecycleDeepDive {

}