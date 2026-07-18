package java_recap.springboot.annotations.core_spring;

/*

=========================================================
        @Value + @Scope + @Lazy
=========================================================

Core Concepts:
---------------
1. Property Injection
2. Bean Scope
3. Singleton vs Prototype
4. Lazy Loading
5. Bean Lifecycle
6. Memory Optimization

=========================================================
                1. @Value
=========================================================

Definition:
------------
@Value injects values into Spring Beans.

Values can come from:
----------------------
1. application.properties
2. application.yml
3. Environment variables
4. Default values
5. Expressions

=========================================================
                EXAMPLE
=========================================================

application.properties
-----------------------

server.port=8080
app.name=GameService

=========================================================
                JAVA
=========================================================

@Component
public class AppInfo {

    @Value("${app.name}")
    private String appName;

}

=========================================================
                INTERNAL WORKING
=========================================================

Application Starts
        ↓
Spring loads properties
        ↓
Spring resolves placeholder
        ↓
Injects value using reflection

=========================================================
                DEFAULT VALUE
=========================================================

@Value("${app.version:1.0}")

Meaning:
---------
If property missing,
use default value 1.0

=========================================================
                EXPRESSION SUPPORT
=========================================================

@Value("#{10 + 20}")

Result:
--------
30

=========================================================
                SPEL
=========================================================

Spring Expression Language

Example:
---------

@Value("#{T(java.lang.Math).random()}")

=========================================================
                REAL PROJECT USAGE
=========================================================

1. API Keys
2. JWT Secret
3. Database Config
4. Timeout Values
5. Feature Flags
6. External URLs

=========================================================
                IMPORTANT
=========================================================

@Value works AFTER bean creation.

=========================================================
                LIMITATION
=========================================================

Too many @Value fields become messy.

Better Solution:
----------------

@ConfigurationProperties

=========================================================
                INTERVIEW QUESTION
=========================================================

Q. Difference between:
-----------------------

@Value

and

@ConfigurationProperties

=========================================================
                ANSWER
=========================================================

@Value
--------
Single property injection.

@ConfigurationProperties
-------------------------
Bulk structured property binding.

=========================================================
                2. @Scope
=========================================================

Definition:
------------
Defines bean lifecycle and visibility.

=========================================================
                DEFAULT SCOPE
=========================================================

Singleton

Meaning:
---------
Only ONE bean object per IOC Container.

=========================================================
                SINGLETON EXAMPLE
=========================================================

@Component
@Scope("singleton")
class UserService {

}

=========================================================
                IMPORTANT
=========================================================

Singleton is default.
No need to write explicitly.

=========================================================
                PROTOTYPE SCOPE
=========================================================

@Component
@Scope("prototype")
class Payment {

}

=========================================================
                MEANING
=========================================================

Every request gets NEW object.

=========================================================
                DIFFERENCE
=========================================================

Singleton
-----------
Single shared object.

Prototype
-----------
New object every injection/request.

=========================================================
                INTERNAL FLOW
=========================================================

Singleton:
-----------
Bean created once during startup.

Prototype:
-----------
Bean created every time requested.

=========================================================
                WEB SCOPES
=========================================================

1. request
2. session
3. application

=========================================================
                REQUEST SCOPE
=========================================================

@Scope(value = WebApplicationContext.SCOPE_REQUEST)

One bean per HTTP request.

=========================================================
                SESSION SCOPE
=========================================================

One bean per user session.

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q. Which scope is default in Spring?

Answer:
--------
Singleton

=========================================================
                VERY IMPORTANT
=========================================================

Spring Singleton != Java Singleton

=========================================================
                WHY?
=========================================================

Java Singleton:
----------------
One object per JVM.

Spring Singleton:
------------------
One object per IOC Container.

=========================================================
                PROTOTYPE PROBLEM
=========================================================

If Prototype injected into Singleton:

Prototype bean created only once.

=========================================================
                WHY?
=========================================================

Injection happens once during singleton creation.

=========================================================
                INTERVIEW TRICK QUESTION
=========================================================

Q:
---
Does prototype bean always create new object?

Answer:
--------
Not always.

If injected into singleton,
same object may be reused.

=========================================================
                SOLUTION
=========================================================

Use:
-----
1. ObjectFactory
2. Provider
3. ApplicationContext

=========================================================
                3. @Lazy
=========================================================

Definition:
------------
Delays bean creation until actually needed.

=========================================================
                DEFAULT BEHAVIOR
=========================================================

Spring creates singleton beans eagerly.

Meaning:
---------
During application startup.

=========================================================
                EXAMPLE
=========================================================

@Component
@Lazy
class HeavyService {

}

=========================================================
                INTERNAL FLOW
=========================================================

Without @Lazy:
---------------
Startup
  ↓
Bean created immediately

With @Lazy:
-------------
Startup
  ↓
No bean creation
  ↓
Bean created first time used

=========================================================
                WHY IMPORTANT?
=========================================================

Useful for:
-------------
1. Heavy object creation
2. Memory optimization
3. Faster startup
4. Circular dependency workaround

=========================================================
                REAL PROJECT EXAMPLES
=========================================================

1. Report generators
2. ML models
3. Large cache loaders
4. Expensive SDK initialization

=========================================================
                @Lazy WITH DEPENDENCY
=========================================================

@Service
class A {

    @Autowired
    @Lazy
    private B b;

}

=========================================================
                WHAT HAPPENS?
=========================================================

Spring injects proxy object.

Actual object created when used.

=========================================================
                CIRCULAR DEPENDENCY FIX
=========================================================

@Service
class A {

    @Autowired
    @Lazy
    private B b;

}

@Service
class B {

    @Autowired
    private A a;

}

=========================================================
                IMPORTANT
=========================================================

@Lazy is workaround,
NOT proper architectural solution.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is @Value?

Q2. Difference between @Value and
@ConfigurationProperties?

Q3. What are Spring bean scopes?

Q4. Difference between Singleton and Prototype?

Q5. Spring Singleton vs Java Singleton?

Q6. What is @Lazy?

Q7. How @Lazy works internally?

Q8. Can @Lazy solve circular dependency?

Q9. When bean actually created in @Lazy?

=========================================================
                COMMON MISTAKES
=========================================================

1. Overusing @Value

2. Using prototype unnecessarily

3. Thinking Spring Singleton equals JVM singleton

4. Using @Lazy everywhere

=========================================================
                BEST PRACTICE
=========================================================

Use:
-----

@Value
→ Small property injection

@ConfigurationProperties
→ Large structured configs

Singleton
→ Default services

Prototype
→ Stateful temporary objects

@Lazy
→ Heavy or optional beans only

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Why Spring creates singleton beans eagerly?

Answer:
--------
1. Fail-fast approach
2. Detect configuration issues early
3. Validate dependencies during startup
4. Improve runtime stability

=========================================================
                SUMMARY
=========================================================

@Value
--------
Injects configuration values.

@Scope
--------
Defines bean lifecycle scope.

@Lazy
-------
Delays bean creation until required.

Singleton
-----------
Default Spring bean scope.

Prototype
-----------
Creates new bean multiple times.

=========================================================

*/

public class BeanBehaviorNotes {
}