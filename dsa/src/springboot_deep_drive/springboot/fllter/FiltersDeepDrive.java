package springboot_deep_drive.springboot.fllter;

/**
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                             📖 CHAPTER 01 : FILTER OVERVIEW                               ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                         🎯 LEARNING GOALS
==============================================================================================

By the end of this chapter, you should be able to answer:

✔ What is a Filter?
✔ Why do we need Filters?
✔ Where does a Filter execute?
✔ How does a request travel through Filters?
✔ How do Filters differ from Interceptors?
✔ How do Spring Security Filters work?
✔ When should we create a custom Filter?
✔ What are GenericFilterBean and OncePerRequestFilter?

This chapter provides only the overview.
Each topic will be covered in detail in the upcoming chapters.


==============================================================================================
                                      📖 WHAT IS A FILTER?
==============================================================================================

A Filter is a component of the Servlet API that sits between the Client and the
Spring Boot application.

Its primary responsibility is to inspect, modify, allow, or reject every
incoming HTTP Request and every outgoing HTTP Response.

In simple words:

        "A Filter acts like a checkpoint before a request enters
         the Spring MVC application."


==============================================================================================
                                 📚 WHAT IS A SERVLET COMPONENT?
==============================================================================================

A Servlet Component is any Java class that participates in the Servlet lifecycle
and is managed by the Servlet Container (Tomcat).

The Servlet API mainly provides three important components.

┌──────────────────────┬─────────────────────────────────────────────────────┐
│ Component            │ Responsibility                                      │
├──────────────────────┼─────────────────────────────────────────────────────┤
│ Servlet              │ Handles the request and generates the response      │
│ Filter               │ Intercepts every request and response               │
│ Listener             │ Listens to application lifecycle events             │
└──────────────────────┴─────────────────────────────────────────────────────┘


Filter is one of these Servlet Components.

It is created and managed by the Servlet Container (Embedded Tomcat).


==============================================================================================
                              📊 FILTER VS SERVLET
==============================================================================================

┌──────────────────────┬─────────────────────────────────────────────────────┐
│ FILTER               │ SERVLET                                             │
├──────────────────────┼─────────────────────────────────────────────────────┤
│ Intercepts Requests  │ Processes Requests                                  │
│ Intercepts Responses │ Generates Responses                                 │
│ Performs Common Work │ Performs Business Logic                             │
│ Runs Before Servlet  │ Executes After Filter                               │
└──────────────────────┴─────────────────────────────────────────────────────┘


==============================================================================================
                               🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine a company office.

Before anyone enters the building, the Security Guard checks:

✔ Who are you?
✔ Do you have permission?
✔ Should you be allowed inside?
✔ Should your entry be logged?

Only after verification is the person allowed inside.

Spring Boot Filters work in exactly the same way.


==============================================================================================
                                   ⚙ REQUEST FLOW
==============================================================================================

                               Incoming HTTP Request

                                   Client
                                      │
                                      ▼
                           +---------------------+
                           |       FILTER        |
                           +---------------------+
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



                               Outgoing HTTP Response

                                 Repository
                                      │
                                      ▼
                                   Service
                                      │
                                      ▼
                                 Controller
                                      │
                                      ▼
                           DispatcherServlet
                                      │
                                      ▼
                           +---------------------+
                           |       FILTER        |
                           +---------------------+
                                      │
                                      ▼
                                    Client


Notice:

✔ Filter executes before the request reaches Spring MVC.

✔ Filter also executes before the response is returned to the client.


==============================================================================================
                                  ❓ WHY DO WE NEED FILTERS?
==============================================================================================

Imagine an application without Filters.

Every Controller would need to write common code such as:

✔ Authentication
✔ Authorization
✔ Logging
✔ Header Validation
✔ Token Validation
✔ Request Timing
✔ CORS Handling

Example:

@GetMapping("/employees")
public List<Employee> getEmployees() {

    // Validate JWT Token

    // Log Request

    // Validate Headers

    // Start Timer

    // Business Logic
}

Now imagine having 200 Controllers.

The same code would be repeated everywhere.

This violates the DRY Principle
(Don't Repeat Yourself).


Instead...

                Client
                   │
                   ▼
              Common Filter
                   │
                   ▼
               Controller

The Filter handles common tasks, while the Controller focuses only on
Business Logic.


==============================================================================================
                                  💼 COMMON USE CASES
==============================================================================================

Filters are commonly used for:

✔ JWT Authentication

✔ Request Logging

✔ API Response Time Measurement

✔ CORS Handling

✔ Header Validation

✔ Rate Limiting

✔ Response Header Modification

✔ Request Compression

✔ Audit Logging

✔ Multi-Tenant Identification


Notice one important thing...

These are Cross-Cutting Concerns.

They are NOT Business Logic.


==============================================================================================
                                🔄 COMPLETE REQUEST FLOW
==============================================================================================

                             HTTP REQUEST

Client
   │
   ▼
Embedded Tomcat
   │
   ▼
Filter 1
   │
Filter 2
   │
Filter 3
   │
DispatcherServlet
   │
Handler Mapping
   │
Controller
   │
Service
   │
Repository
   │
Database


=========================== RESPONSE ===========================

Database
   │
Repository
   │
Service
   │
Controller
   │
DispatcherServlet
   │
Filter 3
   │
Filter 2
   │
Filter 1
   │
Client


Important Observation:

✔ Request travels downward.

✔ Response travels upward.

✔ Every Filter gets a chance to process both.


==============================================================================================
                                🎯 IMPORTANT OBSERVATIONS
==============================================================================================

Filters execute BEFORE Spring MVC.

Therefore, they execute before:

✔ Controller

✔ ControllerAdvice

✔ Interceptor

✔ Service

Remember:

Filters belong to the Servlet Layer.

They do NOT belong to the Spring MVC Layer.


==============================================================================================
                              ✅ BEST USE CASES OF FILTERS
==============================================================================================

Filters are best suited for:

✔ Authentication

✔ Logging

✔ Security

✔ Header Validation

✔ CORS

✔ Request Modification

✔ Response Modification

✔ Performance Monitoring


==============================================================================================
                              ❌ WHEN NOT TO USE FILTERS
==============================================================================================

Never place Business Logic inside a Filter.

Examples:

❌ Calculate Salary

❌ Create Employee

❌ Update Attendance

❌ Generate Payroll

Business Logic should always remain inside the Service Layer.


==============================================================================================
                                   📚 NEXT CHAPTERS
==============================================================================================

In the upcoming chapters we will cover:

01. Filter Lifecycle

02. Filter Chain

03. doFilter() Internal Working

04. Custom Filter

05. Filter Registration

06. GenericFilterBean

07. OncePerRequestFilter

08. Filter Ordering

09. Filter vs Interceptor

10. Spring Security Filter Chain

11. Production Best Practices

12. Interview Questions


==============================================================================================
                                        📝 SUMMARY
==============================================================================================

Definition

A Filter is a Servlet API component that intercepts every incoming HTTP Request
and outgoing HTTP Response before they enter or leave the Spring MVC pipeline.


Purpose

✔ Common Processing

✔ Security

✔ Logging

✔ Validation

✔ Performance Monitoring

✔ Request/Response Modification


🎯 Interview Definition

"A Filter is a Servlet API component managed by the Servlet Container that
intercepts every HTTP request and response before they enter or leave the
Spring MVC pipeline."


==============================================================================================
                                    📚 QUICK REVISION
==============================================================================================

✔ Filter belongs to the Servlet API.

✔ Filter is managed by the Servlet Container (Tomcat).

✔ Filter executes before Spring MVC.

✔ Filter can process both Request and Response.

✔ Filters are mainly used for Cross-Cutting Concerns.

✔ Business Logic should never be written inside a Filter.

==============================================================================================
*/

public class FiltersDeepDrive {

}