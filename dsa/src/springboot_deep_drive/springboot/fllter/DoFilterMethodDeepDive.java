package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                         📖 CHAPTER 04 : doFilter() METHOD                                 ║
║                                                                                            ║
║                          ⏱ Reading Time : 10 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is the doFilter() method?
✔ Why is doFilter() the heart of every Filter?
✔ What are the three parameters of doFilter()?
✔ What does chain.doFilter() actually do?
✔ What happens before and after chain.doFilter()?


==============================================================================================
                               📖 WHAT IS doFilter()?
==============================================================================================

The doFilter() method is the most important lifecycle method of a Filter.

Every incoming HTTP request passes through this method.

Whenever a client sends a request, the Servlet Container invokes doFilter().

Almost all Filter logic is written inside this method.

Typical responsibilities include:

✔ Logging
✔ Authentication
✔ Authorization
✔ JWT Validation
✔ Header Validation
✔ Response Modification


==============================================================================================
                               ⚙ METHOD SIGNATURE
==============================================================================================

public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)
        throws IOException, ServletException

Let's understand each parameter.


==============================================================================================
                               1️⃣ ServletRequest request
==============================================================================================

Represents the incoming HTTP Request.

Using this object, we can read:

✔ Headers

✔ Parameters

✔ Cookies

✔ Client IP

✔ Request Body (when applicable)

Examples

request.getParameter("id")

request.getHeader("Authorization")

request.getRemoteAddr()


Think of it as

📦 Everything sent by the Client.


==============================================================================================
                             2️⃣ ServletResponse response
==============================================================================================

Represents the HTTP Response.

Using this object, we can

✔ Add Headers

✔ Change Status Code

✔ Write Response

✔ Modify Response

Examples

response.setContentType(...)

response.setCharacterEncoding(...)


Think of it as

📦 Everything returned to the Client.


==============================================================================================
                              3️⃣ FilterChain chain
==============================================================================================

This object represents the remaining Filters in the chain.

Its job is to forward the request.

The most important line is

chain.doFilter(request, response);

Meaning

"I have completed my work.

Now pass the request to the next Filter."

Without this line,

the request never reaches the Controller.


==============================================================================================
                           ⚙ INTERNAL EXECUTION FLOW
==============================================================================================

                 Client

                   │

                   ▼

          doFilter() Starts

                   │

        Before Logic Executes

                   │

                   ▼

 chain.doFilter(request,response)

                   │

                   ▼

         Next Filter (if available)

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

                   │

                   ▼

             Database

===================== RESPONSE =====================

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

       DispatcherServlet

                   │

                   ▼

Returns to doFilter()

                   │

        After Logic Executes

                   │

                   ▼

                Client


==============================================================================================
                            💻 SIMPLE EXAMPLE
==============================================================================================

@Override
public void doFilter(
        ServletRequest request,
        ServletResponse response,
        FilterChain chain)
        throws IOException, ServletException {

    System.out.println("Before Controller");

    chain.doFilter(request, response);

    System.out.println("After Controller");
}


Output Flow

Before Controller

↓

Controller Executes

↓

After Controller


Notice

Everything before chain.doFilter()

↓

Executes BEFORE Controller


Everything after chain.doFilter()

↓

Executes AFTER Controller


==============================================================================================
                        ❌ WHAT IF chain.doFilter() IS REMOVED?
==============================================================================================

Suppose we write

@Override
public void doFilter(...) {

    System.out.println("Checking Request");

    // chain.doFilter() removed

}

Execution

Client

↓

Filter

↓

❌ STOP

Controller is never reached.

No Response is generated.

The request ends here.


==============================================================================================
                             💎 INTERVIEW POINT
==============================================================================================

Question

Why do we call

chain.doFilter(request, response) ?

Answer

It forwards the request to the next Filter.

If no Filter is left,

the request reaches the DispatcherServlet.


==============================================================================================
                              ⚠ COMMON MISTAKES
==============================================================================================

❌ Forgetting chain.doFilter()

Result

The request never reaches the Controller.


------------------------------------------------------------

❌ Writing Business Logic inside Filter.

Business Logic belongs inside the Service Layer.


------------------------------------------------------------

❌ Assuming doFilter() executes only once.

Wrong.

It executes for EVERY request.


==============================================================================================
                              🧠 MEMORY TRICK
==============================================================================================

Remember

Before Logic

        ↓

chain.doFilter()

        ↓

Controller

        ↓

After Logic


Whatever is written

ABOVE chain.doFilter()

Runs BEFORE Controller.


Whatever is written

BELOW chain.doFilter()

Runs AFTER Controller.


==============================================================================================
                                  📝 SUMMARY
==============================================================================================

✔ doFilter() is the heart of every Filter.

✔ Every HTTP Request enters this method.

✔ ServletRequest contains incoming Request data.

✔ ServletResponse represents the outgoing Response.

✔ FilterChain forwards the request.

✔ chain.doFilter() is mandatory to continue processing.

✔ Code before chain.doFilter() executes before the Controller.

✔ Code after chain.doFilter() executes after the Controller.


==============================================================================================
                                📚 QUICK REVISION
==============================================================================================

✔ doFilter() runs for every request.

✔ Request → Filter → Controller.

✔ Response → Controller → Filter.

✔ chain.doFilter() forwards the request.

✔ Without chain.doFilter(), processing stops.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Custom Filter (Creating Your First Filter)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class DoFilterMethodDeepDive {

}