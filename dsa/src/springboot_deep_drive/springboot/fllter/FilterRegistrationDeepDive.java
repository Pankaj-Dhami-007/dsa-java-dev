package springboot_deep_drive.springboot.fllter;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                      📖 CHAPTER 06 : FILTER REGISTRATION                                  ║
║                                                                                            ║
║                          ⏱ Reading Time : 12 Minutes                                      ║
║                          📈 Difficulty : 🟢 Beginner                                      ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ What is Filter Registration?
✔ Why is Filter Registration required?
✔ Different ways to register a Filter.
✔ Which registration method is preferred in real projects.


==============================================================================================
                               📖 WHY FILTER REGISTRATION?
==============================================================================================

Creating a Filter class is NOT enough.

Example

public class LoggingFilter implements Filter {

}

This only creates a Java class.
Tomcat still does NOT know

✔ When should this Filter execute?
✔ Which URLs should it intercept?
✔ In what order should it run?

To answer these questions,
the Filter must be registered.


==============================================================================================
                             🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine a security guard is hired by a company.

Employee Created

        │

        ▼

Still Sitting At Home 😄

Why?

Because nobody assigned him to the office.

Exactly the same happens with Filters.

Creating a Filter

≠

Registering a Filter

Only after registration does Tomcat know where and when
the Filter should execute.


==============================================================================================
                               ⚙ HOW REGISTRATION WORKS
==============================================================================================

Client

   │

   ▼

Embedded Tomcat

   │

   ▼

Checks Registered Filters

   │

   ▼

Logging Filter

   │

   ▼

Authentication Filter

   │

   ▼

DispatcherServlet

Only registered Filters become part of the Filter Chain.


==============================================================================================
                      📦 WAYS TO REGISTER A FILTER
==============================================================================================

Spring Boot provides multiple ways.

✔ Method 1

@Component

--------------------------------------------

✔ Method 2

FilterRegistrationBean

--------------------------------------------

✔ Method 3

@WebFilter

(Requires @ServletComponentScan)

We'll study each method separately.


==============================================================================================
                           ✅ METHOD 1 : @Component
==============================================================================================

The easiest way.

@Component

public class LoggingFilter implements Filter {

}

What happens?

Spring creates the Filter Bean.

Spring Boot automatically registers it with Embedded Tomcat.

Advantages

✔ Very Easy

✔ Less Configuration

✔ Suitable for small applications


Disadvantages

❌ Limited customization.


==============================================================================================
                    ✅ METHOD 2 : FilterRegistrationBean
==============================================================================================

This is the most commonly used approach
in production applications.

Why?

Because it gives complete control.

We can configure

✔ URL Patterns

✔ Filter Order

✔ Filter Name

✔ Enable / Disable Filter

✔ Multiple Configurations

This is the recommended approach for enterprise projects.


==============================================================================================
                          ✅ METHOD 3 : @WebFilter
==============================================================================================

Another Servlet API approach.

Example

@WebFilter

public class LoggingFilter implements Filter {

}

To use it,

Spring Boot requires

@ServletComponentScan

Without it,

@WebFilter will not be detected.


==============================================================================================
                         💎 WHICH METHOD SHOULD WE USE?
==============================================================================================

Small Project

↓

@Component


Enterprise Project

↓

FilterRegistrationBean


Legacy Servlet Project

↓

@WebFilter


==============================================================================================
                            ⚠ COMMON MISTAKES
==============================================================================================

❌ Creating a Filter but never registering it.

Result

The Filter never executes.


------------------------------------------------------------

❌ Thinking that implementing Filter is enough.

Wrong.

Registration is mandatory.


------------------------------------------------------------

❌ Using @WebFilter without

@ServletComponentScan

Result

Filter is never detected.


==============================================================================================
                           💼 PRODUCTION INSIGHT
==============================================================================================

Most enterprise Spring Boot applications prefer

FilterRegistrationBean

because it gives complete control over

✔ Execution Order

✔ URL Mapping

✔ Configuration


==============================================================================================
                                   📝 SUMMARY
==============================================================================================

✔ Creating a Filter is only the first step.

✔ Registration tells Tomcat about the Filter.

✔ Only registered Filters execute.

✔ Spring Boot supports three registration techniques.

✔ FilterRegistrationBean is the preferred production approach.


==============================================================================================
                                 📚 QUICK REVISION
==============================================================================================

Filter Created

        │

        ▼

Register Filter

        │

        ▼

Tomcat Detects Filter

        │

        ▼

Filter Becomes Part Of Filter Chain


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Create

↓

Register

↓

Execute

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

💎 INTERVIEW QUESTION

Q) Does implementing Filter automatically register it?

Answer

❌ No.

The Filter must be registered before Tomcat can execute it.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : @Component vs FilterRegistrationBean vs @WebFilter

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class FilterRegistrationDeepDive {

}