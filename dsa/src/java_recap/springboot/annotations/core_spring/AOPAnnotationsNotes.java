package java_recap.springboot.annotations.core_spring;

/*

=================================================================
                SPRING AOP ANNOTATIONS
=================================================================

Annotations:
-------------
@Aspect
@Before
@After
@AfterReturning
@AfterThrowing
@Around
@Pointcut
@EnableAspectJAutoProxy

Core Concepts:
---------------
1. Aspect Oriented Programming
2. Cross Cutting Concerns
3. Proxy Pattern
4. Method Interception
5. JoinPoint
6. Advice
7. Weaving
8. Runtime Proxies

=================================================================
                WHY AOP EXISTS?
=================================================================

Imagine large application:

Every service method needs:
----------------------------

1. Logging
2. Security
3. Transactions
4. Performance Monitoring
5. Auditing

=================================================================
                PROBLEM
=================================================================

Same code repeated everywhere.

=================================================================
                EXAMPLE
=================================================================

public void transferMoney() {

    log.info("started");

    checkSecurity();

    // business logic

    log.info("completed");

}

=================================================================
                THIS CAUSES
=================================================================

1. Boilerplate code
2. Code duplication
3. Difficult maintenance
4. Mixed responsibilities

=================================================================
                SOLUTION
=================================================================

AOP

=================================================================
                WHAT IS AOP?
=================================================================

AOP =
------
Aspect Oriented Programming

=================================================================
                SIMPLE MEANING
=================================================================

Separates:
-----------

Cross-cutting concerns
from
business logic.

=================================================================
                CROSS CUTTING CONCERNS
=================================================================

Features affecting multiple modules.

=================================================================
                EXAMPLES
=================================================================

1. Logging
2. Transactions
3. Security
4. Monitoring
5. Caching

=================================================================
                MOST IMPORTANT
=================================================================

Spring internally uses AOP for:
--------------------------------

1. @Transactional
2. Spring Security
3. Async
4. Caching

=================================================================
                HOW AOP WORKS?
=================================================================

Spring creates:
----------------

Proxy Objects

=================================================================
                FLOW
=================================================================

Client
   ↓
Proxy Object
   ↓
Aspect Logic Executes
   ↓
Target Method Executes

=================================================================
                VERY IMPORTANT
=================================================================

Original object usually NOT called directly.

Proxy intercepts method calls.

=================================================================
                TERMINOLOGIES
=================================================================

Aspect
Advice
JoinPoint
Pointcut
Weaving

=================================================================
                ASPECT
=================================================================

Module containing cross-cutting logic.

=================================================================
                EXAMPLE
=================================================================

LoggingAspect
SecurityAspect

=================================================================
                ADVICE
=================================================================

Actual code executed.

=================================================================
                JOINPOINT
=================================================================

Point where method intercepted.

=================================================================
                POINTCUT
=================================================================

Rule deciding which methods intercepted.

=================================================================
                WEAVING
=================================================================

Process of applying aspect to target object.

=================================================================
                1. @Aspect
=================================================================

Most important AOP annotation.

=================================================================
                DEFINITION
=================================================================

@Aspect marks class as:
------------------------

Aspect class containing cross-cutting logic.

=================================================================
                EXAMPLE
=================================================================

@Aspect
@Component
public class LoggingAspect {

}

=================================================================
                IMPORTANT
=================================================================

@Aspect alone NOT enough.

Need:
------

@Component

=================================================================
                WHY?
=================================================================

Aspect itself must become Spring Bean.

=================================================================
                2. @Before
=================================================================

Executes BEFORE target method.

=================================================================
                EXAMPLE
=================================================================

@Before(
 "execution(* com.app.service.*.*(..))"
)

public void logBefore() {

    System.out.println("Before Method");

}

=================================================================
                WHAT HAPPENS?
=================================================================

Before actual service method:
------------------------------

Aspect logic executes.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Logging
2. Authentication
3. Validation
4. Audit tracking

=================================================================
                3. @After
=================================================================

Executes AFTER method completes.

=================================================================
                IMPORTANT
=================================================================

Runs whether:
--------------

1. Success
2. Exception

=================================================================
                REAL PROJECT USE CASE
=================================================================

Cleanup operations.

=================================================================
                4. @AfterReturning
=================================================================

Runs ONLY if method succeeds.

=================================================================
                EXAMPLE
=================================================================

@AfterReturning(
   pointcut = "...",
   returning = "result"
)

=================================================================
                USE CASE
=================================================================

1. Success logging
2. Metrics
3. Analytics

=================================================================
                5. @AfterThrowing
=================================================================

Runs ONLY when exception occurs.

=================================================================
                USE CASE
=================================================================

1. Error logging
2. Alert systems
3. Monitoring

=================================================================
                6. @Around
=================================================================

Most powerful advice.

=================================================================
                DEFINITION
=================================================================

Controls entire method execution.

=================================================================
                CAN:
=================================================================

1. Execute before
2. Execute after
3. Skip method
4. Modify result
5. Handle exceptions

=================================================================
                EXAMPLE
=================================================================

@Around("execution(* service.*.*(..))")

public Object monitor(
        ProceedingJoinPoint jp
) throws Throwable {

    long start = System.currentTimeMillis();

    Object result = jp.proceed();

    long end = System.currentTimeMillis();

    return result;

}

=================================================================
                MOST IMPORTANT
=================================================================

jp.proceed() executes actual method.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Performance monitoring
2. Distributed tracing
3. Retry mechanism
4. Security wrappers

=================================================================
                7. @Pointcut
=================================================================

Reusable method interception rule.

=================================================================
                EXAMPLE
=================================================================

@Pointcut(
 "execution(* com.app.service.*.*(..))"
)

public void serviceLayer() {

}

=================================================================
                WHY IMPORTANT?
=================================================================

Avoid repeating expressions everywhere.

=================================================================
                REUSING
=================================================================

@Before("serviceLayer()")

=================================================================
                EXECUTION EXPRESSION
=================================================================

Most important AOP syntax.

=================================================================
                EXAMPLE
=================================================================

execution(* com.app.service.*.*(..))

=================================================================
                BREAKDOWN
=================================================================

*       → Any return type
service → package
*       → any class
*       → any method
(..)    → any arguments

=================================================================
                8. @EnableAspectJAutoProxy
=================================================================

Enables Spring AOP support.

=================================================================
                EXAMPLE
=================================================================

@Configuration
@EnableAspectJAutoProxy
public class AppConfig {

}

=================================================================
                WHAT HAPPENS?
=================================================================

Spring enables proxy generation
for aspect handling.

=================================================================
                JDK PROXY VS CGLIB
=================================================================

Very important interview topic.

=================================================================
                JDK DYNAMIC PROXY
=================================================================

Works using interfaces.

=================================================================
                CGLIB PROXY
=================================================================

Creates subclass dynamically.

=================================================================
                SPRING DECISION
=================================================================

If interface exists:
---------------------

JDK Proxy

Otherwise:
------------

CGLIB

=================================================================
                IMPORTANT LIMITATION
=================================================================

AOP works mainly on:
---------------------

PUBLIC methods.

=================================================================
                WHY?
=================================================================

Proxy interception limitation.

=================================================================
                SELF INVOCATION PROBLEM
=================================================================

Most famous AOP interview question.

=================================================================
                EXAMPLE
=================================================================

public void methodA() {

    methodB();

}

@Transactional
public void methodB() {

}

=================================================================
                PROBLEM
=================================================================

Proxy bypassed.

AOP not applied.

=================================================================
                WHY?
=================================================================

Internal method call does NOT go through proxy.

=================================================================
                REAL PROJECT USE CASES
=================================================================

1. Logging Frameworks
2. Security
3. Transactions
4. Performance Monitoring
5. Retry Mechanisms
6. Distributed Tracing

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
How Spring AOP works internally?

=================================================================
                ANSWER
=================================================================

Spring creates proxy objects.
Proxy intercepts method calls,
executes aspect logic,
then invokes target method.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Difference between @Before
and @Around?

=================================================================
                ANSWER
=================================================================

@Before:
----------
Only executes before method.

@Around:
----------
Controls entire method execution.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why self-invocation breaks AOP?

=================================================================
                ANSWER
=================================================================

Internal method calls bypass proxy,
so aspect interception never happens.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Can private methods be intercepted?

NO usually.

Proxy cannot intercept them properly.

=================================================================
                COMMON MISTAKES
=================================================================

1. Overusing AOP

2. Heavy business logic inside aspects

3. Ignoring proxy limitations

4. Debugging difficulty

=================================================================
                BEST PRACTICE
=================================================================

Use AOP only for:
------------------

1. Logging
2. Monitoring
3. Security
4. Transactions
5. Technical concerns

Avoid business logic inside aspects.

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain internal working of Spring AOP.

Answer:
--------
Spring creates runtime proxies around beans.
Proxy intercepts method calls,
applies aspect logic before/after execution,
then delegates call to actual target object.

=================================================================
                SUMMARY
=================================================================

@Aspect
---------
Defines aspect class.

@Before
---------
Runs before method.

@After
--------
Runs after method.

@Around
---------
Controls entire execution.

@Pointcut
-----------
Reusable interception rule.

@EnableAspectJAutoProxy
------------------------
Enables Spring AOP support.

Spring AOP internally uses:
----------------------------

1. Runtime proxies
2. Method interception
3. Proxy pattern

=================================================================

*/

public class AOPAnnotationsNotes {
}