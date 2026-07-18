package springboot_deep_drive.aop_deep;

public class _07_PointcutExpressions {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │                   **SPRING BOOT DEEP DIVE - AOP SERIES**                                  │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                    **Chapter 07 : Pointcut Expressions**                                  │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                                    ║
║                     SPRING BOOT DEEP DIVE ── POINTCUT EXPRESSIONS                                 ║
║                                                                                                    ║
║                         Chapter 07 : POINTCUT EXPRESSIONS                                         ║
║                                                                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             1. WHAT IS A POINTCUT ?                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In the previous chapter

we wrote


@Before("execution(...)")



Question


How does Spring know

which method

should execute

our Advice?



Suppose

our project contains



EmployeeService

AttendanceService

PayrollService

LeaveService

NotificationService



Should Spring

execute the Advice

for every method?



NO.



Spring needs

a rule.



That rule

is called



                        **Pointcut**



══════════════════════════════════════════════════════════════════════════════════════════════════════

Simple Definition

══════════════════════════════════════════════════════════════════════════════════════════════════════


A Pointcut tells Spring


                "WHERE"

the Advice

should execute.



Think like this.



Advice

↓

What should execute?



Pointcut

↓

Where should it execute?



Without a Pointcut,

Spring has no idea

which methods

should be intercepted.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          REAL LIFE EXAMPLE                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

a security guard

has instructions


"Check everyone

entering

Room 101."


Notice


The guard

does NOT

check

every room.



He only checks

Room 101.



Similarly,

Pointcut tells Spring


Intercept only

these methods.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Spring AOP works exactly the same.

══════════════════════════════════════════════════════════════════════════════════════════════════════



                   Advice

                     │

                     ▼

              Pointcut Rule

                     │

                     ▼

         Matching Method Only



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     COMPLETE EXECUTION FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



              HTTP Request

                     │

                     ▼

             EmployeeController

                     │

                     ▼

             EmployeeService

                     │

                     ▼

       Does Pointcut Match?

             ┌─────────────┐
             │             │
            YES           NO
             │             │
             ▼             ▼

     Execute Advice   Skip Advice

             │
             ▼

      Execute Method



Notice


Advice

does NOT execute

for every method.



Only

matching methods

are intercepted.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      2. THE MOST COMMON POINTCUT                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


The most commonly used

Pointcut

is



execution(...)



Almost every

Spring Boot project

uses

execution().



Example



@Before(

"execution(* com.example.service.EmployeeService.saveEmployee(..))"

)



Don't panic.

It looks complicated,

but it is actually

very simple.



We'll break it

into small pieces.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    BREAKING execution() INTO PARTS                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



execution(

      *

      com.example.service.EmployeeService

      .

      saveEmployee

      (

      ..

      )

)



Every part

has a meaning.



┌───────────────────────────┬──────────────────────────────────────────────┐
│ Part                      │ Meaning                                      │
├───────────────────────────┼──────────────────────────────────────────────┤
│ execution                 │ Execute this method                          │
├───────────────────────────┼──────────────────────────────────────────────┤
│ *                         │ Any return type                              │
├───────────────────────────┼──────────────────────────────────────────────┤
│ EmployeeService           │ Class Name                                   │
├───────────────────────────┼──────────────────────────────────────────────┤
│ saveEmployee              │ Method Name                                  │
├───────────────────────────┼──────────────────────────────────────────────┤
│ (..)                      │ Any number of parameters                     │
└───────────────────────────┴──────────────────────────────────────────────┘



Don't try to memorize.

We'll learn

each symbol

one by one.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     EXAMPLE USING EMPLOYEE SERVICE                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Service
public class EmployeeService{

    public void saveEmployee(){}

    public void updateEmployee(){}

    public void deleteEmployee(){}

}



Suppose

our Pointcut is



execution(
    * EmployeeService.saveEmployee(..)
)



Only



saveEmployee()



matches.



updateEmployee()

↓

No Match



deleteEmployee()

↓

No Match



Only one method

will execute

the Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What is a Pointcut?


Answer


A Pointcut defines

where

an Advice should execute.



----------------------------------------------------------


Q2.

Does Advice execute

for every method?


No.

Only for methods

matching

the Pointcut.



----------------------------------------------------------


Q3.

Which Pointcut expression

is most commonly used

in Spring Boot?


Answer


execution()



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             PART 1 COMPLETE                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Meaning of *

✔ Meaning of ..

✔ Matching All Methods

✔ Matching Packages

✔ Matching Multiple Services

✔ Real Project Examples

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     3. UNDERSTANDING * (ASTERISK)                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


If you search any Spring project,

you'll see

Pointcuts like



execution(* com.company.service.*.*(..))



Question


What does

*

mean?



Simple Answer


**'*' means ANY (one thing).**



Depending on

where it is used,

its meaning changes.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                 Think of * as a Wildcard
══════════════════════════════════════════════════════════════════════════════════════════════════════


Whenever Spring sees

*

it simply thinks


"I don't care.

Match anything."



Example


Your teacher says


"I'll select

any

one student."



She doesn't care

whether it's

Rahul

Pankaj

Amit

Rohit



Exactly the same

idea.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       1. * AS RETURN TYPE                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Example



execution(
    *
    com.example.service.EmployeeService.saveEmployee(..)
)



Question


What does

the first

*

mean?



Answer


Any Return Type.



That means



public void saveEmployee()



matches.



------------------------------



public String saveEmployee()



also matches.



------------------------------



public Employee saveEmployee()



also matches.



Because



*



means



"I don't care

what the return type is."



ASCII Diagram



             Return Type

                  │

        ┌─────────┼─────────┐

        ▼         ▼         ▼

      void      String    Employee

        │         │          │

        └─────────┴──────────┘

                  │

                  ▼

               MATCH



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         2. * AS METHOD NAME                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose



execution(
    *
    com.example.service.EmployeeService.*(..)
)



Notice



method name



is replaced by



*



Now Spring says


Match

every method.



Example



public void saveEmployee()



✔ Match



----------------------------



public void updateEmployee()



✔ Match



----------------------------



public void deleteEmployee()



✔ Match



All methods

match.



ASCII Flow



EmployeeService

      │

      ├────────► saveEmployee() ✔

      │

      ├────────► updateEmployee() ✔

      │

      ├────────► deleteEmployee() ✔

      │

      └────────► getEmployee() ✔



Everything matches.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         3. * AS CLASS NAME                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose



execution(
    *
    com.example.service.*
    .saveEmployee(..)
)



Now

Spring says


Any class

inside

service package.



Example



EmployeeService

✔



AttendanceService

✔



PayrollService

✔



LeaveService

✔



But



Controller



❌ No Match



Because

it is

not inside

service package.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Real HRMS Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



Suppose


service package

contains



AttendanceService

EmployeeService

LeaveService

PayrollService



Expression



execution(
    * com.hrms.service.*.*(..)
)



Meaning



Any Return Type

↓

Any Service Class

↓

Any Method

↓

Any Parameters



Every Service method

will execute

our Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      COMPLETE BREAKDOWN                                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



execution(

*

com.hrms.service.*

.*

(..)

)



Let's translate it



*

↓

Any Return Type



service.*

↓

Any Class



.*

↓

Any Method



(..)

↓

Any Parameters



Final Meaning


Intercept

every method

of every class

inside

service package.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              COMMON MISTAKE                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Many beginners think



execution(* *.*(..))



means



every method

in the project.



Wrong.



It depends on

the package

you specify.



Always read

the entire expression.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        4. UNDERSTANDING .. (DOUBLE DOT)                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Another confusing symbol is



..



Question


What does

..

mean?



Simple Answer


**'..' means zero or more.**



The meaning depends

on where

it is used.



There are

two common usages.



✔ Parameters

✔ Packages



These are the only two

you'll use

in most Spring Boot projects.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                   .. IN METHOD PARAMETERS
══════════════════════════════════════════════════════════════════════════════════════════════════════



Example



execution(
    * EmployeeService.saveEmployee(..)
)



Meaning


Match



saveEmployee()



saveEmployee(String)



saveEmployee(Integer)



saveEmployee(String,Integer)



saveEmployee(EmployeeDTO,Long,String)



Any number

of parameters.



ASCII Diagram



saveEmployee()                  ✔

saveEmployee(String)            ✔

saveEmployee(Long)              ✔

saveEmployee(String,Long)       ✔

saveEmployee(EmployeeDTO...)    ✔



Everything matches.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                     .. IN PACKAGE NAME
══════════════════════════════════════════════════════════════════════════════════════════════════════



Suppose



com.hrms



contains



service

service.impl

service.admin

service.internal



Expression



execution(
    * com.hrms.service..*.*(..)
)



Notice


service..



Now Spring says


Include

all

sub-packages

also.



ASCII Diagram



com.hrms.service

        │

        ├────────► EmployeeService ✔

        │

        ├────────► impl

        │          └──── PayrollService ✔

        │

        ├────────► admin

        │          └──── LeaveService ✔

        │

        └────────► internal

                   └──── AuditService ✔



Everything

inside

service

and

its sub-packages

matches.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     INTERVIEW MEMORY TABLE                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌─────────────────────────────┬────────────────────────────────────────────┐
│ Symbol                      │ Meaning                                    │
├─────────────────────────────┼────────────────────────────────────────────┤
│ *                           │ Any one thing                              │
├─────────────────────────────┼────────────────────────────────────────────┤
│ .. (Parameters)             │ Any number of parameters                   │
├─────────────────────────────┼────────────────────────────────────────────┤
│ .. (Packages)               │ Current package + all sub-packages         │
└─────────────────────────────┴────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              **Remember**                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


For Spring Boot developers,

remember only these three rules.


*

↓

Any one thing



(..)

↓

Any number of parameters



package..

↓

Current package

+

all sub-packages



These three cover

almost every

Spring Boot project.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             PART 2 COMPLETE                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Most Used Pointcut Expressions

✔ @Pointcut Annotation

✔ Reusing Pointcuts

✔ Real Project Best Practices

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             5. @Pointcut ANNOTATION                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

we have



@Before("execution(* com.hrms.service.*.*(..))")



@After("execution(* com.hrms.service.*.*(..))")



@Around("execution(* com.hrms.service.*.*(..))")



Question


What is repeated?



Answer


The Pointcut Expression.



Imagine

your project contains



10 Aspects.



Each Aspect contains



5 Advice methods.



Now

the same Pointcut

is written

50 times.



Is that a good practice?



NO.



Spring provides


                    **@Pointcut**



══════════════════════════════════════════════════════════════════════════════════════════════════════
                        WHAT IS @Pointcut ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Simple Definition


**@Pointcut allows us to define a Pointcut
once and reuse it multiple times.**



Think of it like

a Java method.



Instead of writing

the same code

again and again,

we create

one method

and reuse it.



Pointcut

works exactly

the same way.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      WITHOUT @Pointcut                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect{


    @Before("execution(* com.hrms.service.*.*(..))")
    public void beforeLog(){


    }


    @After("execution(* com.hrms.service.*.*(..))")
    public void afterLog(){


    }


    @Around("execution(* com.hrms.service.*.*(..))")
    public Object executionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable{

        return joinPoint.proceed();

    }

}



Question


How many times

did we write

the same expression?



Three.



Imagine

writing it

20 times.



Very difficult

to maintain.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        WITH @Pointcut                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect{


    @Pointcut("execution(* com.hrms.service.*.*(..))")
    public void serviceMethods(){


    }


    @Before("serviceMethods()")
    public void beforeLog(){


    }


    @After("serviceMethods()")
    public void afterLog(){


    }


    @Around("serviceMethods()")
    public Object executionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable{

        return joinPoint.proceed();

    }

}



Notice carefully.



The long

execution(...)

expression

is written

only once.



Every Advice

simply calls



serviceMethods()



Exactly

like calling

a Java method.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      DOES serviceMethods() EXECUTE ?                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Many beginners ask


Question


Will

serviceMethods()

execute?



Answer


NO.



This method

is NEVER called

like a normal method.



Its only purpose

is to store

the Pointcut Expression.



Think of it as



                A Label



or



                A Name



for the Pointcut.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Think Like This

══════════════════════════════════════════════════════════════════════════════════════════════════════



Java Method


calculateSalary()



↓

Contains

Business Logic



------------------------------------------------------



@Pointcut Method


serviceMethods()



↓

Contains

ONLY

Pointcut Definition



No business logic.

No execution.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       REAL HRMS PROJECT EXAMPLE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

you want

Logging,

Execution Time,

and

Audit

for

every Service.



Instead of



@Before("execution(...)")



@Around("execution(...)")



@AfterReturning("execution(...)")



Create



@Pointcut("execution(* com.hrms.service.*.*(..))")
public void serviceMethods(){}



Now use



@Before("serviceMethods()")



@Around("serviceMethods()")



@AfterReturning("serviceMethods()")



Very clean.

Very readable.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      COMPLETE INTERNAL FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



               HTTP Request

                     │

                     ▼

               Controller

                     │

                     ▼

          EmployeeService Proxy

                     │

                     ▼

        Does Pointcut Match?

                     │

                     ▼

       serviceMethods()

                     │

                     ▼

        Execute Advice Chain

                     │

                     ▼

         Original Method



Notice



serviceMethods()

is NOT

the business method.



It simply

represents

the Pointcut.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      BEST PRACTICES                                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Give meaningful names


Good


serviceMethods()



controllerMethods()



publicApiMethods()



------------------------------------------------------



Avoid


test()



abc()



pointcut1()



------------------------------------------------------



✔ Reuse Pointcuts

instead of

copy-pasting

execution(...)

everywhere.



------------------------------------------------------



✔ Keep Pointcuts

small

and readable.



Don't create

very complicated

expressions.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMMON MISTAKES                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake


Writing business logic

inside

@Pointcut method.



Wrong.



Example



@Pointcut(...)
public void serviceMethods(){

    System.out.println("Hello");

}



This code

never executes.



The method

exists only

to define

the Pointcut.



------------------------------------------------------



❌ Mistake


Giving meaningless names.


Bad


pointcut1()



Good


allServiceMethods()



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        INTERVIEW QUESTIONS                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why do we use

@Pointcut?



Answer


To define a Pointcut once

and reuse it

across multiple Advice methods.



----------------------------------------------------------


Q2.

Does

@Pointcut method

execute?



No.



It only stores

the Pointcut expression.



----------------------------------------------------------


Q3.

Can one Pointcut

be used by

multiple Advice methods?



Yes.



That is the main purpose

of

@Pointcut.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 3 COMPLETE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ @annotation()

✔ Creating Custom Annotation

✔ Logging Only Selected Methods

✔ Real Project Audit Example

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          6. @annotation() POINTCUT                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


So far,

our Pointcuts look like



execution(* com.hrms.service.*.*(..))



Question


What is the problem?



Suppose

EmployeeService

contains



saveEmployee()

updateEmployee()

deleteEmployee()

calculateSalary()

generateReport()

sendEmail()



But

we only want

Logging

for



saveEmployee()



and



deleteEmployee()



Should we write

a complicated

Pointcut?



NO.



Spring provides

                **@annotation()**



══════════════════════════════════════════════════════════════════════════════════════════════════════
                        WHAT IS @annotation() ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Simple Definition


**@annotation() intercepts only those methods
that have a specific annotation.**



Instead of selecting

methods

using

package names,



we select

methods

using

annotations.



Think of it as



Method

        +

Custom Annotation

        =

Execute Advice



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     STEP 1 : CREATE CUSTOM ANNOTATION                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Retention(RetentionPolicy.RUNTIME)

@Target(ElementType.METHOD)

public @interface LogExecution {



}



Don't worry

about

@Retention

or

@Target

right now.



Just remember


We created

our own annotation.



Exactly like

Spring created



@Transactional

@Override

@GetMapping



Now

we also have



@LogExecution



══════════════════════════════════════════════════════════════════════════════════════════════════════
                     STEP 2 : USE THE ANNOTATION
══════════════════════════════════════════════════════════════════════════════════════════════════════



@Service

public class EmployeeService{



    @LogExecution
    public void saveEmployee(){

    }



    public void updateEmployee(){

    }



    @LogExecution
    public void deleteEmployee(){

    }



}



Notice carefully.



Only

two methods

have

@LogExecution.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       STEP 3 : CREATE THE ASPECT                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect{



    @Before("@annotation(LogExecution)")
    public void beforeLog(){

        System.out.println("Logging Started...");

    }

}



Meaning



Whenever

Spring finds

@LogExecution



execute

beforeLog()



No package matching.

No class matching.

No long

execution(...)

expression.



Very clean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     COMPLETE EXECUTION FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                  HTTP Request

                        │

                        ▼

                EmployeeService

                        │

                        ▼

         Does Method Have

            @LogExecution ?

             ┌───────────────┐
             │               │
            YES             NO
             │               │
             ▼               ▼

      Execute Advice      Skip Advice
             │
             ▼

       Execute Method



Simple.

Easy to understand.

Easy to maintain.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL HRMS PROJECT EXAMPLE                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

your HRMS project

contains



markAttendance()



approveLeave()



downloadSalary()



generatePayslip()



But

only



approveLeave()



should be audited.



Simply write



@AuditLog

public void approveLeave(){

}



Aspect



@Before("@annotation(AuditLog)")

public void audit(){



}



Only

approveLeave()

is intercepted.



Everything else

runs normally.



This is

much cleaner

than writing

complex

execution(...)

expressions.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      WHY DO COMPANIES USE THIS ?                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Large projects

contain



Hundreds

of Services.



Matching

package names

becomes difficult.



Instead,

developers simply mark

important methods.



Example



@AuditLog



@TrackExecution



@NotifyManager



@LogExecution



Then

Aspect

becomes

very simple.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                    REAL EXAMPLES FROM INDUSTRY
══════════════════════════════════════════════════════════════════════════════════════════════════════



Banking


@AuditTransaction



-------------------------------------------------------



E-Commerce


@TrackOrder



-------------------------------------------------------



Hospital


@PatientAudit



-------------------------------------------------------



HRMS


@SalaryApprovalAudit



-------------------------------------------------------



Security


@RequiresAdmin



This pattern

is very common

in enterprise applications.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     execution() vs @annotation()                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌─────────────────────────────┬──────────────────────────────────────────────┐
│ execution()                 │ @annotation()                                │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Matches by method signature │ Matches by annotation                        │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Uses package/class names    │ Uses custom annotation                       │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Good for broad matching     │ Good for selected methods                    │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Most common                 │ Very common in enterprise projects           │
└─────────────────────────────┴──────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              BEST PRACTICES                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Use

execution()

when

many methods

follow the same rule.



-------------------------------------------------------



✔ Use

@annotation()

when

only selected methods

should execute

the Advice.



-------------------------------------------------------



✔ Give

meaningful names

to custom annotations.



Good


@AuditLog

@TrackExecution

@LogExecution



Avoid


@Test1

@MyAnnotation

@Demo



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             COMMON MISTAKES                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Forgetting to place

@LogExecution

on the method.



Result



Advice never executes.



-------------------------------------------------------



❌ Thinking

@annotation()

works

on classes.



No.



It matches

annotations

placed on

methods.



(For class-level annotations,
other pointcut expressions are used.)



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

When should we use

@annotation()?



Answer


Use @annotation()

when you want

the Advice

to execute

only for methods

marked with

a specific annotation.



----------------------------------------------------------


Q2.

Which is better?


execution()

or

@annotation()?



Answer


Neither is universally better.


Use

execution()

for package or method pattern matching.


Use

@annotation()

when developers explicitly mark methods
that require special behavior.



----------------------------------------------------------


Q3.

Can we create

our own annotation

for AOP?



Yes.


This is a common practice

in enterprise applications.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           CHAPTER 07 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Congratulations!

Now you understand

✔ What is a Pointcut

✔ execution()

✔ *

✔ ..

✔ @Pointcut

✔ @annotation()



These concepts cover

almost everything

you'll use

in day-to-day

Spring Boot development.



Next Chapter

✔ Real Project AOP Examples

✔ Logging Aspect

✔ Execution Time Aspect

✔ Exception Logging Aspect

✔ Audit Logging

✔ API Request Logging

✔ Production Best Practices

*/


