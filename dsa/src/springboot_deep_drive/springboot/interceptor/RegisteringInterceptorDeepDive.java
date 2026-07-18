package springboot_deep_drive.springboot.interceptor;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                     📖 CHAPTER 04 : REGISTERING AN INTERCEPTOR                            ║
║                                                                                            ║
║                          ⏱ Reading Time : 10 Minutes                                      ║
║                          📈 Difficulty : 🟢 Beginner                                      ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                    🎯 LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter, you will understand:

✔ Why an Interceptor must be registered.

✔ How Spring MVC registers an Interceptor.

✔ What is WebMvcConfigurer?

✔ What is addInterceptors()?

✔ What are Path Patterns?


==============================================================================================
                             📖 WHY DO WE NEED REGISTRATION?
==============================================================================================

Creating an Interceptor is NOT enough.

Example

public class LoggingInterceptor implements HandlerInterceptor {

}

This is only a Java class.

Spring MVC still doesn't know

✔ When should this Interceptor execute?

✔ Which URLs should it intercept?

✔ Which Controllers should use it?

Therefore,

the Interceptor must be registered.


==============================================================================================
                           🌍 REAL WORLD ANALOGY
==============================================================================================

Imagine a new receptionist joins a company.

The receptionist is hired.

                │

                ▼

But nobody assigns a reception desk.

                │

                ▼

Visitors never meet the receptionist.

Exactly the same happens with an Interceptor.

Creating an Interceptor

≠

Registering an Interceptor.


==============================================================================================
                           ⚙ HOW REGISTRATION WORKS
==============================================================================================

                        HTTP REQUEST

               ┌───────────────┐
               │    Client     │
               └──────┬────────┘
                      │
                      ▼
           ┌─────────────────────┐
           │       Filter        │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │ DispatcherServlet   │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │ Registered          │
           │ Interceptor         │
           └─────────┬───────────┘
                     │
                     ▼
           ┌─────────────────────┐
           │ Controller          │
           └─────────────────────┘

Only registered Interceptors become part of the request flow.


==============================================================================================
                      🏗 STEP 1 : CREATE A CONFIGURATION CLASS
==============================================================================================

@Configuration
public class WebConfig {

}

This class is responsible for MVC configuration.


==============================================================================================
                 🏗 STEP 2 : IMPLEMENT WebMvcConfigurer
==============================================================================================

@Configuration
public class WebConfig implements WebMvcConfigurer {

}

WebMvcConfigurer allows us to customize
Spring MVC.


==============================================================================================
                🏗 STEP 3 : OVERRIDE addInterceptors()
==============================================================================================

@Override
public void addInterceptors(InterceptorRegistry registry) {

}

Spring MVC automatically calls this method
during application startup.


==============================================================================================
                     🏗 STEP 4 : REGISTER THE INTERCEPTOR
==============================================================================================

@Override
public void addInterceptors(InterceptorRegistry registry) {

    registry.addInterceptor(new LoggingInterceptor());

}

Now Spring MVC knows

✔ Which Interceptor to execute.

✔ When to execute it.


==============================================================================================
                         📌 APPLYING TO SPECIFIC URLS
==============================================================================================

We can decide which URLs should use
the Interceptor.

Example

registry.addInterceptor(new LoggingInterceptor())

        .addPathPatterns("/api/**");


Meaning

Every request starting with

/api/

will execute this Interceptor.


==============================================================================================
                          🚫 EXCLUDING URLS
==============================================================================================

Example

registry.addInterceptor(new LoggingInterceptor())

        .addPathPatterns("/api/**")

        .excludePathPatterns("/login",
                             "/register");


Now

✔ /api/employees

        ✔ Interceptor Executes


✔ /login

        ❌ Interceptor Skipped


==============================================================================================
                             💎 INTERVIEW POINT
==============================================================================================

Question

Who registers an Interceptor?

Answer

Spring MVC

using

WebMvcConfigurer

and

addInterceptors().


==============================================================================================
                            ⚠ COMMON MISTAKES
==============================================================================================

❌ Creating an Interceptor but never registering it.

Result

Interceptor never executes.


------------------------------------------------------------

❌ Forgetting

implements WebMvcConfigurer

Result

addInterceptors() is never available.


------------------------------------------------------------

❌ Registering every URL unnecessarily.

Always use

addPathPatterns()

and

excludePathPatterns()

when required.


==============================================================================================
                                    📝 SUMMARY
==============================================================================================

✔ Creating an Interceptor is not enough.

✔ It must be registered.

✔ Registration happens using

WebMvcConfigurer.

✔ addInterceptors() registers Interceptors.

✔ Path Patterns decide where an Interceptor executes.


==============================================================================================
                                  📚 QUICK REVISION
==============================================================================================

Create Interceptor

        │

        ▼

Create WebConfig

        │

        ▼

Implement WebMvcConfigurer

        │

        ▼

Override addInterceptors()

        │

        ▼

Register Interceptor


━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

🧠 MEMORY TRICK

Create

↓

Configure

↓

Register

↓

Intercept

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

💡 DID YOU KNOW?

Unlike Filters, which are managed by the Servlet Container (Tomcat),
Interceptors are managed entirely by Spring MVC.

This is one of the most frequently asked Spring Boot interview questions.

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━

➡ Next Chapter : Interceptor Execution Flow (Complete Internal Working)

━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
*/

public class RegisteringInterceptorDeepDive {

}