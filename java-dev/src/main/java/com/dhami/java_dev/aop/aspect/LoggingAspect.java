package com.dhami.java_dev.aop.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LoggingAspect {

    @Before("execution(String com.dhami.java_dev.aop.service.StudentServiceImpl.createStudent())")
    public void logBeforeMethod(){
        System.out.println("student is going to be saved ");
    }
}

// Aspect    → Class containing cross-cutting logic
//Advice    → What to execute
//Pointcut  → Where to execute
//@Before   → When to execute (before)
//execution → Matches method execution
//Join Point→ A method execution where advice can be applied
//Proxy      → Executes advice before calling the target method

// Aspect
//│
//├── Advice (What)
//│
//├── Pointcut (Where)
//│
//└── Advice Type (When)

/**

 @Aspect
 Meaning : Marks this class as an Aspect.
 Purpose : Contains cross-cutting logic (logging, transaction, security, auditing, etc.).

 @Component
 Registers the aspect as a Spring Bean.
 Without it, Spring won't detect the aspect.

 Advice
 Definition
 Advice = Actual code to execute when a join point matches.

 @Before
 Meaning : Execute the advice before the target method.


 -> Pointcut Expression
 execution(
 String
 com.dhami.java_dev.aop.service.StudentServiceImpl.createStudent()
 )

 This tells Spring where to apply the advice.


  -> execution()

 Means : Intercept method execution.

 General syntax
 execution(
 returnType
 package.class.method(parameters)
 )
 */

/*

In this example
execution(
String
com.dhami.java_dev.aop.service.StudentServiceImpl.createStudent()
)

means
Intercept
public String createStudent() inside StudentServiceImpl


WHAT  → Advice

WHERE → Pointcut

WHEN  → Advice Type

advice ->
A method containing cross-cutting logic that executes at a matched join point.


advice types

| Annotation      | Runs                            |
| --------------- | ------------------------------- |
| @Before         | Before method                   |
| @After          | After method (always)           |
| @AfterReturning | After successful execution      |
| @AfterThrowing  | After exception                 |
| @Around         | Before and After (full control) |



What is Pointcut?

Definition -> A Pointcut defines where an advice should execute.

Example -> execution(String com.dhami.java_dev.aop.service.StudentServiceImpl.createStudent())

Meaning : Apply advice only for createStudent()
 */
