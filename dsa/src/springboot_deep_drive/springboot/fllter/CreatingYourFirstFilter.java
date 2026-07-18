package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                      📖 CHAPTER 05 : CREATING YOUR FIRST FILTER                           ║
║                                                                                            ║
║                          ⏱ Reading Time : 12 Minutes                                      ║
║                          📈 Difficulty : 🟢 Beginner                                      ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ How to create a custom Filter.

✔ Which interface should be implemented.

✔ Why we override doFilter().

✔ How a Filter becomes part of a Spring Boot application.


==============================================================================================
                               📖 CREATING A FILTER
==============================================================================================

Creating a Filter is very simple.

A Filter is just a Java class that implements the Filter interface.

Example

public class LoggingFilter implements Filter {

}

By implementing the Filter interface,

our class promises to provide the lifecycle methods defined by the Servlet API.


==============================================================================================
                           📦 THE FILTER INTERFACE
==============================================================================================

public interface Filter {

    void init(FilterConfig filterConfig);

    void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain);

    void destroy();

}

These are the same lifecycle methods we studied in the previous chapter.


==============================================================================================
                       ⚙ STEP 1 : CREATE A FILTER CLASS
==============================================================================================

Example

public class LoggingFilter implements Filter {

}

Nothing special.

It is just a normal Java class.

The only difference is

it implements the Filter interface.


==============================================================================================
                     ⚙ STEP 2 : IMPLEMENT THE METHODS
==============================================================================================

@Override
public void init(FilterConfig filterConfig) {

}

@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)
        throws IOException, ServletException {

}

@Override
public void destroy() {

}


Most real-world Filters mainly use

✔ doFilter()

The other two methods are used less frequently.


==============================================================================================
                       ⚙ STEP 3 : WRITE FILTER LOGIC
==============================================================================================

Example

@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)
        throws IOException, ServletException {

    System.out.println("Request Received");

    chain.doFilter(request, response);

    System.out.println("Response Returned");
}


Execution

Request

↓

Request Received

↓

Controller Executes

↓

Response Returned


==============================================================================================
                         🌍 REAL WORLD EXAMPLE
==============================================================================================

Suppose every API request should be logged.

Instead of writing

System.out.println(...)

inside every Controller,

we create one Logging Filter.

Client

↓

Logging Filter

↓

Controller

↓

Response


Now every request is logged automatically.


==============================================================================================
                       💡 WHY CREATE A CUSTOM FILTER?
==============================================================================================

Custom Filters help us perform common work for every request.

Examples

✔ Logging

✔ JWT Validation

✔ Header Validation

✔ CORS

✔ Request Timing

✔ Response Headers

✔ Audit Logs

Notice

These are common tasks,

not business logic.


==============================================================================================
                             💎 INTERVIEW POINT
==============================================================================================

Question

Can we create multiple Filters?

Answer

Yes.

Large applications usually have multiple Filters,

each responsible for only one task.


==============================================================================================
                             ⚠ COMMON MISTAKES
==============================================================================================

❌ Forgetting

chain.doFilter(request, response);

Result

The request never reaches the Controller.


------------------------------------------------------------

❌ Writing business logic inside Filters.

Business logic always belongs inside Services.


------------------------------------------------------------

❌ Creating one huge Filter.

Instead,

create multiple small Filters,

each having a single responsibility.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

✔ A Filter is just a Java class.

✔ It implements the Filter interface.

✔ doFilter() contains the main logic.

✔ Every request automatically passes through the Filter.

✔ Multiple Filters can exist in one application.


==============================================================================================
                                 📚 QUICK REVISION
==============================================================================================

✔ Create a Java class.

✔ Implement Filter.

✔ Override lifecycle methods.

✔ Write logic inside doFilter().

✔ Call chain.doFilter().

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Create Class

↓

Implement Filter

↓

Override Methods

↓

Write Logic

↓

Call chain.doFilter()

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Registering a Filter in Spring Boot

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class CreatingYourFirstFilter {

}