package springboot_deep_drive.aop_deep;

public class _06_AdviceAnnotations {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │                    **SPRING BOOT DEEP DIVE - AOP SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                     **Chapter 06 : Advice Annotations**                                   │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                                    ║
║                      SPRING BOOT DEEP DIVE ── ADVICE ANNOTATIONS                                  ║
║                                                                                                    ║
║                         Chapter 06 : ADVICE ANNOTATIONS                                           ║
║                                                                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             1. WHAT IS AN ADVICE ?                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In Chapter 03

we learned

Aspect

contains

Advice.



Question


What exactly

is an Advice?



Simple Answer


**Advice is the code that Spring executes
before, after or around your business method.**



Think like this



Aspect

↓

Container



Advice

↓

Actual Logic



Example



Logging

Execution Time

Security

Validation

Audit

Notification

Exception Logging



All these

can be written

inside Advice methods.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Real Example

══════════════════════════════════════════════════════════════════════════════════════════════════════


AttendanceService



public void markAttendance(){

      // Business Logic

}



Before executing

markAttendance(),

Spring can


Log Request

↓

Validate User

↓

Check Permission



These are

Advices.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        TYPES OF ADVICE IN SPRING AOP                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Spring provides

five Advice types.



                    Advice

                       │

      ┌────────────────┼───────────────────┐

      │                │                   │

      ▼                ▼                   ▼

   @Before         @After              @Around

                       │

             ┌─────────┴──────────┐

             ▼                    ▼

     @AfterReturning      @AfterThrowing



Every Advice

has a different purpose.



Never use them

interchangeably.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    COMPLETE METHOD EXECUTION TIMELINE                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 Client

                   │

                   ▼

            @Before Advice

                   │

                   ▼

            Business Method

                   │

         ┌─────────┴─────────┐

         │                   │

         ▼                   ▼

Success                Exception

   │                        │

   ▼                        ▼

@AfterReturning      @AfterThrowing

         │                   │

         └─────────┬─────────┘

                   ▼

             @After Advice



Notice carefully.



@After

always executes.



@AfterReturning

executes only

when method succeeds.



@AfterThrowing

executes only

when exception occurs.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHY DO WE NEED DIFFERENT ADVICES ?                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

an ATM Machine.



Before transaction

↓

Check PIN



During transaction

↓

Withdraw Money



If success

↓

Print Receipt



If failure

↓

Show Error



Always

↓

Close Session



Question


Can one method

handle all these?



Possible.



But

it becomes

difficult

to maintain.



Spring separates

each responsibility

into

different Advice types.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Real HRMS Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



Attendance API



Before

↓

Check Login



Business Logic

↓

Save Attendance



Success

↓

Write Audit Log



Failure

↓

Log Exception



Always

↓

Calculate Execution Time



Each responsibility

matches

a different Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        WHICH ADVICE SHOULD I USE ?                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬──────────────────────────────────────────────────────────────┐
│ Requirement                  │ Advice                                                       │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ Log before execution         │ @Before                                                      │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ Log after completion         │ @After                                                       │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ Need returned value          │ @AfterReturning                                              │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ Log only exceptions          │ @AfterThrowing                                               │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ Full control over execution  │ @Around                                                      │
└──────────────────────────────┴──────────────────────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           REAL PROJECT STRUCTURE                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



src/main/java

│

├── service

│      ├── EmployeeService

│      ├── AttendanceService

│      └── LeaveService

│

├── aspect

│      └── LoggingAspect

│

└── controller

       └── AttendanceController



LoggingAspect

contains

all Advice methods.



Business classes

contain

only

business logic.



══════════════════════════════════════════════════════════════════════════════════════════════════════

**Remember**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Advice

is NOT

an annotation.



Advice

is

the method.



@Before

@After

@Around

are annotations

used to define

the type of Advice.



Many developers

say

"@Before is Advice."



Technically,

that's incorrect.



Correct statement


@Before

marks

a method

as

a Before Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

How many Advice types
does Spring AOP provide?


Answer

Five.


@Before

@After

@AfterReturning

@AfterThrowing

@Around



------------------------------------------------------------


Q2.

What is Advice?


Answer

Advice is the actual code
executed by Spring
at a selected Join Point.



------------------------------------------------------------


Q3.

Is @Before itself an Advice?


No.


@Before

is an annotation.


The method

annotated with

@Before

is the Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 1 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ @Aspect

✔ Creating our first Aspect

✔ @EnableAspectJAutoProxy

✔ Project Setup

✔ First @Before Advice

✔ Complete Running Example

*/

//
//        ---
//
//        ## 📌 One recommendation for this chapter
//
//Before teaching `@Before`, I want to add a **complete runnable Spring Boot project structure**
// (controller → service → aspect → main class). That way, every annotation we learn
// (`@Before`, `@After`, `@Around`, etc.) will be added to the **same project**,
// making it feel like a real application instead of isolated code snippets.
// This approach is much closer to how you'll use AOP in actual Spring Boot projects.


/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         2. CREATING OUR FIRST AOP PROJECT                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Now enough theory.

Let's build our first Spring AOP application.

We will create

        Employee Management System

using Spring Boot.

In this project we will

✔ Create Service

✔ Create Controller

✔ Create Aspect

✔ Add @Before Advice

✔ Observe how Spring intercepts method calls



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            PROJECT STRUCTURE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


src
│
├── controller
│      └── EmployeeController
│
├── service
│      └── EmployeeService
│
├── aspect
│      └── LoggingAspect
│
└── SpringBootApplication



This is the typical structure
used in almost every Spring Boot project.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         STEP 1 : ADD REQUIRED DEPENDENCY                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Spring AOP is not included automatically.

We need to add its dependency.



For Maven


<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>



Once this dependency is added,

Spring downloads

all required AOP libraries.



**Interview Point**

Without

spring-boot-starter-aop

none of the annotations like

@Aspect

@Before

@After

@Around

will work.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          STEP 2 : CREATE SERVICE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Service
public class EmployeeService {

    public void saveEmployee() {

        System.out.println("Employee Saved Successfully.");

    }

}



Nothing special here.

This is a normal

Spring Service Bean.



Notice

There is

NO

logging,

NO

transaction,

NO

AOP code.



Only

business logic.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         STEP 3 : CREATE CONTROLLER                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@RestController

@RequestMapping("/employee")

public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/save")
    public String saveEmployee() {

        employeeService.saveEmployee();

        return "Employee Saved";

    }

}



Flow


Browser

↓

Controller

↓

Service



No Aspect

yet.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      STEP 4 : CREATE OUR FIRST ASPECT                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect

@Component

public class LoggingAspect {



}



Let's understand

both annotations.



@Aspect

tells Spring


"This class contains AOP logic."



@Component

registers this class

as a Spring Bean.



Question


Why do we need

@Component?



Because

only Spring Beans

can participate

in Spring AOP.



Without

@Component

Spring won't create

LoggingAspect Bean.



No Bean

↓

No Aspect

↓

No Advice Execution.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      INTERNAL SPRING FLOW                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 Spring Boot Starts

                         │

                         ▼

              Component Scanning

                         │

                         ▼

      Finds EmployeeService Bean

                         │

                         ▼

      Finds LoggingAspect Bean

                         │

                         ▼

       Sees @Aspect Annotation

                         │

                         ▼

     Registers Aspect Internally

                         │

                         ▼

        Application Ready



Notice

At this point

our Advice

still doesn't exist.

We have only

created an Aspect class.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        WHY @Aspect ALONE IS NOT ENOUGH ?                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Many beginners think

adding

@Aspect

is enough.



Wrong.



@Aspect

only tells Spring


"This class contains AOP logic."



It does NOT tell

Spring


Which method

to intercept.



That is the job of

@Before

@After

@Around

etc.



Think like this


@Aspect

↓

Create AOP Class



@Before

↓

When should code execute?



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             COMMON MISTAKES                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Forgetting

@Component



Result


Aspect never executes.



----------------------------------------------------



❌ Forgetting

spring-boot-starter-aop



Result


Spring ignores

@Aspect.



----------------------------------------------------



❌ Writing business logic

inside Aspect.



Aspect should contain

cross-cutting concerns

only.



Business logic

belongs in

Service classes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why do we annotate
LoggingAspect
with @Component?


Answer

Because an Aspect must be a Spring Bean.
Only Spring-managed beans participate in AOP.



------------------------------------------------------------


Q2.

What is the purpose
of @Aspect?


Answer

@Aspect marks a class as an Aspect,
indicating that it contains AOP advice.



------------------------------------------------------------


Q3.

Is @Aspect enough
to execute Advice?


No.

We also need

@Before,

@After,

@Around,

etc.,

to specify

when the Advice should execute.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 2 COMPLETE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Writing our first @Before Advice

✔ Understanding execution(* ..)

✔ Running the application

✔ Console Output

✔ Internal Execution Flow

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           3. OUR FIRST @Before ADVICE                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now

our LoggingAspect

looks like this



@Aspect

@Component

public class LoggingAspect {

}



Question


How does Spring know

which method

should execute?



Answer


Using

                **@Before**



@Before tells Spring


"Execute this Advice

BEFORE

the target method executes."



Think like this



                Client

                   │

                   ▼

            @Before Advice

                   │

                   ▼

          Business Method



So

@Before

always executes

before

the actual business logic.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            OUR FIRST BEFORE ADVICE                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.example.service.EmployeeService.saveEmployee(..))")
    public void logBeforeSaveEmployee() {

        System.out.println("Logging Started...");

    }

}



Don't worry about

execution(...)

right now.



We'll study

Pointcut Expressions

in a separate chapter.



For now,

remember only


@Before

↓

Execute before method.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          UNDERSTANDING THE CODE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Let's understand

line by line.



------------------------------------------------------------


@Aspect


This class

contains AOP logic.



------------------------------------------------------------


@Component


Spring creates

LoggingAspect Bean.



------------------------------------------------------------


@Before(...)


Execute this method

before

the target method.



------------------------------------------------------------


public void logBeforeSaveEmployee()


This is

our Advice method.



Inside this method,

we can write


✔ Logging

✔ Validation

✔ Authentication Check

✔ Audit

✔ Performance Tracking



Any common logic

that should execute

before

business logic.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        WHAT DOES execution(...) MEAN ?                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Let's look at it.



execution(

    *                           // Return Type

    com.example.service.EmployeeService

    .saveEmployee

    (..)

)



Don't memorize it now.



For this chapter,

just understand



execution(...)

means


"Which method

should this Advice

apply to?"



We will study

every part

of execution()

in detail

in the Pointcut chapter.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          COMPLETE EXECUTION FLOW                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 Browser

                    │

                    ▼

             EmployeeController

                    │

                    ▼

          EmployeeService Proxy

                    │

                    ▼

      @Before Advice Executes

                    │

                    ▼

      EmployeeService.saveEmployee()

                    │

                    ▼

            Return Response



Notice


Controller

does NOT call

LoggingAspect.



Spring

automatically

calls it

through

the Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          WHAT HAPPENS INTERNALLY ?                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Application Starts

        │

        ▼

Spring Finds

LoggingAspect

        │

        ▼

Reads @Before

        │

        ▼

Creates Advisor

        │

        ▼

Creates EmployeeService Proxy

        │

        ▼

Stores Proxy

inside IOC Container



HTTP Request

        │

        ▼

Controller

        │

        ▼

Proxy

        │

        ▼

Execute @Before

        │

        ▼

Call saveEmployee()



Everything happens

automatically.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             RUNNING THE APPLICATION                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

we call



GET

/employee/save



Controller



@GetMapping("/save")
public String saveEmployee(){

    employeeService.saveEmployee();

    return "Employee Saved";

}



Service



public void saveEmployee(){

    System.out.println("Employee Saved Successfully.");

}



Aspect



@Before(...)
public void logBeforeSaveEmployee(){

    System.out.println("Logging Started...");
}



Console Output



Logging Started...

Employee Saved Successfully.



Notice


Logging executes

FIRST.



Business Logic

executes

SECOND.



Exactly because

we used

@Before.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      REAL HRMS PROJECT EXAMPLE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

Attendance API



@PostMapping("/mark")



calls



attendanceService.markAttendance();



Instead of



logging

inside every Service,



we write



@Before

public void logRequest(){



}



Now

every attendance request

is automatically logged.



Business methods

remain clean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           WHEN SHOULD WE USE @Before ?                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Use @Before when

you need to execute

logic

before

the business method.



Examples


✔ Logging

✔ Input Validation

✔ Permission Check

✔ Authentication Check

✔ Request Tracking

✔ API Monitoring

✔ Start Timer



Avoid

writing

business logic

inside

@Before.



Keep it

for

cross-cutting concerns only.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              COMMON MISTAKES                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake


Calling

EmployeeService

directly

from the Aspect.



Wrong.



Aspect

should only contain

cross-cutting logic.



--------------------------------------------------------


❌ Mistake


Writing

database operations

inside

@Before.



Avoid

heavy business logic

inside Advice.



--------------------------------------------------------


❌ Mistake


Thinking

@Before

can stop

method execution.



Wrong.



@Before

only executes

before the method.



If you need

full control

(use, skip, or modify execution),

use

@Around.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            INTERVIEW QUESTIONS                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What is @Before?


Answer

@Before is an Advice annotation
that executes the annotated method
before the target business method.



------------------------------------------------------------


Q2.

Does @Before execute
after the method?


No.

It always executes

before

the target method.



------------------------------------------------------------


Q3.

Can @Before
prevent method execution?


No.

For complete control,

use

@Around.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 3 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ @After

✔ @AfterReturning

✔ @AfterThrowing

✔ Difference between all three

✔ Complete execution timeline

*/

/**
 *
 * 📌 One improvement suggestion
 *
 * I noticed one thing that will make these notes even more interview-friendly.
 *
 * Instead of writing:
 *
 * @Before("execution(* com.example.service.EmployeeService.saveEmployee(..))")
 *
 * in the first example, we should introduce pointcuts gradually.
 *
 * For example:
 *
 * @Before("execution(* *.*(..))")
 *
 * or explain:
 *
 * execution(
 *    returnType
 *    package.class.method(arguments)
 * )

 */

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                4. @After ADVICE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


So far we have learned


                @Before


which executes

BEFORE

the business method.



Question


Can Spring execute

some code

AFTER

the business method?



Answer


YES.



Spring provides


                    **@After**



@After

executes

AFTER

the business method finishes.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Simple Execution Flow

══════════════════════════════════════════════════════════════════════════════════════════════════════


                Client

                   │

                   ▼

              @Before

                   │

                   ▼

           Business Method

                   │

                   ▼

               @After



Very easy to remember.


@Before

↓

Before Method



@After

↓

After Method



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         OUR FIRST @After ADVICE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect {

    @After("execution(* com.example.service.EmployeeService.saveEmployee(..))")
    public void logAfterSaveEmployee() {

        System.out.println("Method Execution Completed.");

    }

}



Whenever

saveEmployee()

finishes,

Spring automatically

calls

logAfterSaveEmployee().



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         COMPLETE EXECUTION FLOW                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                  Browser

                     │

                     ▼

             EmployeeController

                     │

                     ▼

          EmployeeService Proxy

                     │

                     ▼

          EmployeeService

                     │

                     ▼

      saveEmployee() Executes

                     │

                     ▼

             @After Advice

                     │

                     ▼

               Return Response



Notice

Business Method

always executes

before

@After.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        RUNNING THE APPLICATION                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


EmployeeService


public void saveEmployee(){

    System.out.println("Saving Employee...");

}



Aspect


@After(...)
public void logAfterSaveEmployee(){

    System.out.println("Method Completed.");

}



Console Output



Saving Employee...

Method Completed.



Very simple.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      MOST IMPORTANT INTERVIEW QUESTION                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Question


Does @After execute

only when

method succeeds?



Answer


NO.



This is the biggest confusion.



@After

executes

whether

the method


Succeeds

OR

Throws Exception.



Think of

@After

like

Java's

finally block.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



try{

    businessMethod();

}

finally{

    afterAdvice();

}



Exactly

how

@After

behaves.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    SUCCESS vs EXCEPTION FLOW                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                    Business Method

                           │

              ┌────────────┴────────────┐

              │                         │

              ▼                         ▼

         Success                   Exception

              │                         │

              ▼                         ▼

                  @After Executes



Notice


Both paths

finally execute

@After.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      REAL HRMS PROJECT EXAMPLE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Attendance API



markAttendance()



Whether

attendance is saved

or

database throws exception,


we always want


Execution Finished



This is a perfect

use case

for

@After.



Examples


✔ Close Resources

✔ Stop Timer

✔ Clear ThreadLocal

✔ Remove Temporary Data

✔ Cleanup



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           @After vs @Before                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬─────────────────────────────────────────────┐
│ @Before                      │ @After                                      │
├──────────────────────────────┼─────────────────────────────────────────────┤
│ Executes before method       │ Executes after method                       │
├──────────────────────────────┼─────────────────────────────────────────────┤
│ Used for validation          │ Used for cleanup                            │
├──────────────────────────────┼─────────────────────────────────────────────┤
│ Executes first               │ Executes last                               │
└──────────────────────────────┴─────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              COMMON MISTAKES                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake


Thinking

@After

runs only

on success.



Wrong.



It always executes.



------------------------------------------------------



❌ Mistake


Using

@After

to read

returned value.



Wrong.



Use

@AfterReturning

instead.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            INTERVIEW QUESTIONS                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

When does @After execute?


Answer

@After executes after the target method
finishes, regardless of whether it
completes successfully or throws an exception.



----------------------------------------------------------


Q2.

Which Java block is
@After similar to?


Answer

It behaves like a

finally

block.



----------------------------------------------------------


Q3.

Can @After access
the returned value?


No.

Use

@AfterReturning

if you need

the returned object.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                PART 4 COMPLETE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ @AfterReturning

✔ @AfterThrowing

✔ Difference Between

   @After
   @AfterReturning
   @AfterThrowing

✔ One Complete Timeline Diagram

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    5. @AfterReturning vs @AfterThrowing                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now we know

@Before

↓

Before Method



@After

↓

Always Executes
(Success + Exception)



Now suppose

our business method

returns successfully.



Question


Can Spring execute

some code

ONLY

when

the method succeeds?



Answer


YES.



Spring provides

                **@AfterReturning**



Similarly,

if the method

throws an Exception,

Spring provides

                **@AfterThrowing**



══════════════════════════════════════════════════════════════════════════════════════════════════════
                     COMPLETE EXECUTION TIMELINE
══════════════════════════════════════════════════════════════════════════════════════════════════════



                            Client

                              │

                              ▼

                         @Before

                              │

                              ▼

                      Business Method

                    ┌─────────┴─────────┐
                    │                   │
                    ▼                   ▼

              Method Success      Exception Occurs

                    │                   │

                    ▼                   ▼

           @AfterReturning      @AfterThrowing

                    │                   │

                    └─────────┬─────────┘
                              │
                              ▼

                           @After



This single diagram

is enough

to remember

all four Advice types.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           WHAT IS @AfterReturning ?                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Definition


**@AfterReturning executes only when the target method
completes successfully.**



Suppose



public String saveEmployee(){

    return "Employee Saved";

}



After

the method

returns successfully,

Spring executes

@AfterReturning.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



@AfterReturning(
        pointcut = "execution(* com.example.service.EmployeeService.saveEmployee(..))",
        returning = "result"
)
public void afterSuccess(Object result){

    System.out.println("Method executed successfully.");

    System.out.println("Returned Value : " + result);

}



Notice



returning = "result"



This allows

Spring

to pass

the returned value

to the Advice.



Output



Employee Saved Successfully

Method executed successfully.

Returned Value : Employee Saved



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      WHEN SHOULD WE USE @AfterReturning ?                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Real Project Examples


✔ Audit Log

✔ Success Notification

✔ Send Email

✔ Cache Result

✔ Analytics

✔ Success Metrics



HRMS Example



Leave Approved Successfully

↓

Write Audit Entry

↓

Send Notification Email

↓

Return Response



These actions

should happen

ONLY

if

the operation succeeds.



Perfect use case

for

@AfterReturning.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           WHAT IS @AfterThrowing ?                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Definition


**@AfterThrowing executes only when
the target method throws an Exception.**



Suppose



public void saveEmployee(){

    throw new RuntimeException("Database Error");

}



Spring detects

the Exception

and executes

@AfterThrowing.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



@AfterThrowing(
        pointcut = "execution(* com.example.service.EmployeeService.saveEmployee(..))",
        throwing = "ex"
)
public void afterException(Exception ex){

    System.out.println("Exception Occurred.");

    System.out.println(ex.getMessage());

}



Output



Exception Occurred.

Database Error



Notice



throwing = "ex"



Spring passes

the Exception object

to the Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHEN SHOULD WE USE @AfterThrowing ?                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Real Project Examples


✔ Exception Logging

✔ Alert Support Team

✔ Send Slack Notification

✔ Create Incident Ticket

✔ Audit Failure

✔ Rollback Monitoring



HRMS Example



Salary Generation Failed

↓

Log Exception

↓

Notify Admin

↓

Store Error Details



These actions

should execute

ONLY

when

an Exception occurs.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         COMPLETE COMPARISON                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────┬────────────────────────────────────────────────────────────┐
│ Advice               │ Executes When                                              │
├──────────────────────┼────────────────────────────────────────────────────────────┤
│ @Before              │ Before method execution                                    │
├──────────────────────┼────────────────────────────────────────────────────────────┤
│ @After               │ Always (Success + Exception)                               │
├──────────────────────┼────────────────────────────────────────────────────────────┤
│ @AfterReturning      │ Only if method completes successfully                      │
├──────────────────────┼────────────────────────────────────────────────────────────┤
│ @AfterThrowing       │ Only if method throws an Exception                         │
└──────────────────────┴────────────────────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           MEMORY TRICK                                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


@Before

↓

Before Method



@AfterReturning

↓

Success Only



@AfterThrowing

↓

Exception Only



@After

↓

Always



Think of

@After

as

finally

block.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          COMMON INTERVIEW MISTAKES                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake 1

Thinking

@After

and

@AfterReturning

are the same.


Wrong.


@After

always executes.


@AfterReturning

executes

only

after successful completion.



----------------------------------------------------------


❌ Mistake 2

Using

@AfterReturning

for exception logging.


Wrong.


Use

@AfterThrowing.



----------------------------------------------------------


❌ Mistake 3

Trying to access

return value

inside

@After.


Wrong.


Use

@AfterReturning

with

returning = "result".



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             INTERVIEW QUESTIONS                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What is the difference between

@After

and

@AfterReturning?



Answer


@After executes regardless of whether the method
completes successfully or throws an exception.

@AfterReturning executes only when the method
completes successfully.



----------------------------------------------------------


Q2.

Which Advice is used

to log Exceptions?



Answer


@AfterThrowing



----------------------------------------------------------


Q3.

Can

@AfterReturning

access

the returned value?



Yes.

Using

returning = "result"

Spring passes

the returned object

to the Advice.



----------------------------------------------------------


Q4.

Which Advice behaves

like Java's

finally block?



Answer


@After



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 5 COMPLETE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ @Around Advice

✔ ProceedingJoinPoint

✔ proceed()

✔ Modify Request

✔ Modify Response

✔ Why @Around is the most powerful Advice

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                6. @Around ADVICE                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now we have learned



@Before

↓

Execute Before Method



@After

↓

Always Execute



@AfterReturning

↓

Success Only



@AfterThrowing

↓

Exception Only



Question


Can we execute code


Before

↓

Business Method

↓

After


using

ONE

Advice?



Answer


YES.



Spring provides


                **@Around**



It is the

most powerful

Advice

provided by Spring AOP.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                          WHY IS @Around SPECIAL ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


All previous Advices

only execute

at one point.



Example


@Before

↓

Only Before



@After

↓

Only After



But

@Around

can execute



Before Method

↓

Call Method

↓

After Method



inside

one Advice.



Think of it as


"Wrapping"

the business method.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           COMPLETE EXECUTION FLOW                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                     Client

                        │

                        ▼

                 @Around Starts

                        │

                        ▼

              Before Business Logic

                        │

                        ▼

          proceed()  ─────────────► Business Method

                        │

                        ▼

               After Business Logic

                        │

                        ▼

                    Return Result



Notice carefully.



The business method

does NOT execute

automatically.



It executes

ONLY

when

proceed()

is called.



This is the biggest difference

between

@Around

and every other Advice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     OUR FIRST @Around ADVICE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Around("execution(* com.example.service.EmployeeService.saveEmployee(..))")
public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable {

    System.out.println("Before Method");

    Object result = joinPoint.proceed();

    System.out.println("After Method");

    return result;

}



Don't worry about

ProceedingJoinPoint

right now.



First understand

the flow.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         UNDERSTANDING THE CODE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Line 1



@Around(...)



Execute this Advice

around

the business method.



--------------------------------------------------------



Line 2



ProceedingJoinPoint



Represents

the target method.



Using this object,

Spring allows us

to execute

the original method.



--------------------------------------------------------



Line 3



joinPoint.proceed();



This line

calls

EmployeeService.saveEmployee()



Without this line,

the business method

never executes.



This is one of the most
important interview questions.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      WHAT HAPPENS IF proceed() IS REMOVED ?                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Suppose


@Around(...)
public Object logExecution(ProceedingJoinPoint joinPoint){

    System.out.println("Before");

    // proceed() removed

    System.out.println("After");

    return null;

}



Output



Before

After



Question


Will

saveEmployee()

execute?



NO.



Because

joinPoint.proceed()

was never called.



Business Method

is skipped completely.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Execution Flow

══════════════════════════════════════════════════════════════════════════════════════════════════════



              Client

                 │

                 ▼

            @Around

                 │

                 ▼

      proceed() called ?

          ┌────────────┐
          │            │
         YES          NO
          │            │
          ▼            ▼

Business Method     Skip Method

          │
          ▼

Return Result



Remember this.

It is one of the
most frequently asked
Spring AOP interview questions.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      RUNNING OUR APPLICATION                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



EmployeeService



public String saveEmployee(){

    System.out.println("Saving Employee...");

    return "Employee Saved";

}



Aspect



@Around(...)
public Object logExecution(ProceedingJoinPoint joinPoint) throws Throwable{

    System.out.println("Before Method");

    Object result = joinPoint.proceed();

    System.out.println("After Method");

    return result;

}



Console Output



Before Method

Saving Employee...

After Method



Notice


@Before

could only execute

before.



@After

could only execute

after.



@Around

does

both.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        WHY DO COMPANIES USE @Around ?                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Because

it provides

complete control.



Real Project Examples



✔ Execution Time

✔ Logging

✔ Retry Logic

✔ Transactions

✔ Cache

✔ Performance Monitoring

✔ Security

✔ Custom Validation



Almost every

enterprise project

uses

@Around

for these features.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          REAL HRMS EXAMPLE                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Attendance API



Before Method


↓

Start Timer



↓

Call proceed()



↓

Attendance Saved



↓

Stop Timer



↓

Print Execution Time



Without changing

AttendanceService,

we measured

its execution time.



This is exactly

why

AOP exists.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              COMMON MISTAKES                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake 1


Forgetting

joinPoint.proceed()



Result


Business Method

never executes.



----------------------------------------------------------



❌ Mistake 2


Calling

proceed()

twice.



Result


Business Method

executes twice.



----------------------------------------------------------



❌ Mistake 3


Returning

null

instead of

result.



Result


Controller

receives

null

even though

business method

returned a value.



Always

return

the result

unless you intentionally
want to modify it.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why is @Around
considered the most powerful Advice?


Answer


Because it can execute code

before,

after,

and even control

whether the target method

executes at all.



------------------------------------------------------------


Q2.

What is the purpose of

ProceedingJoinPoint?


Answer


It represents the target method

and allows the Advice

to invoke it

using

proceed().



------------------------------------------------------------


Q3.

What happens if

proceed()

is not called?


Answer


The target business method

will never execute.



------------------------------------------------------------


Q4.

Can @Around
modify the returned value?


Yes.

It can receive,

modify,

or replace

the returned object

before sending it

back to the caller.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 6 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ ProceedingJoinPoint Deep Dive

✔ Access Method Name

✔ Access Method Arguments

✔ Modify Arguments

✔ Modify Return Value

✔ Measure API Execution Time (Real Project)

*/


/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     7. PROCEEDINGJOINPOINT DEEP DIVE                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In the previous section

we wrote



@Around(...)
public Object logExecution(ProceedingJoinPoint joinPoint)
        throws Throwable{

    Object result = joinPoint.proceed();

    return result;

}



Question


What exactly is

ProceedingJoinPoint?



Simple Answer


**ProceedingJoinPoint represents the method
that Spring is about to execute.**



It provides information about


✔ Method Name

✔ Method Parameters

✔ Target Object

✔ Return Value

✔ Execute Original Method



Think of it as

a Remote Control

for the target method.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                         WHAT CAN WE DO WITH IT ?
══════════════════════════════════════════════════════════════════════════════════════════════════════



ProceedingJoinPoint

        │

        ├────────► Get Method Name

        │

        ├────────► Get Parameters

        │

        ├────────► Get Target Object

        │

        ├────────► Execute Method

        │

        └────────► Return Result



These are the features

developers use

in almost every project.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          1. GET METHOD NAME                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Around("execution(* com.example.service.*.*(..))")
public Object logMethod(ProceedingJoinPoint joinPoint)
        throws Throwable{

    String methodName = joinPoint.getSignature().getName();

    System.out.println(methodName);

    return joinPoint.proceed();

}



Output



saveEmployee



OR



markAttendance



OR



approveLeave



depending upon

which method

was called.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Real Project Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



Instead of writing



System.out.println("saveEmployee called");



we write



System.out.println(
        joinPoint.getSignature().getName()
);



Now

one Aspect

works

for every Service.



Much better.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           2. GET METHOD ARGUMENTS                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose



public void saveEmployee(String name,Integer age){



}



Question


Can we access

these parameters

inside Aspect?



YES.



Example



Object[] args = joinPoint.getArgs();



System.out.println(Arrays.toString(args));



Output



[Pankaj, 25]



Now

the Aspect

knows

which data

was passed

to the method.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Real HRMS Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



markAttendance(

employeeId,

date

)



Aspect logs



Employee Id : 105

Date : 10-Jul-2026



Without writing

logging code

inside Service.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          3. EXECUTE ORIGINAL METHOD                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


The most important method



joinPoint.proceed();



Question


What does it do?



Answer


It executes

the original

business method.



ASCII Flow



                @Around

                    │

                    ▼

      Before Logging

                    │

                    ▼

        proceed()

                    │

                    ▼

     EmployeeService

                    │

                    ▼

      After Logging



Without

proceed(),

EmployeeService

never executes.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Interview Point

══════════════════════════════════════════════════════════════════════════════════════════════════════


**proceed() executes the original business method.**



This sentence

is enough

for interviews.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         4. GET RETURN VALUE                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Object result = joinPoint.proceed();



Question


What is

result?



Answer


It is

the value

returned by

the business method.



Example



public String saveEmployee(){

    return "Employee Saved";

}



Aspect



Object result = joinPoint.proceed();



System.out.println(result);



Output



Employee Saved



Now

we can log

the response

before

returning it.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     REAL PROJECT : EXECUTION TIME LOGGER                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



This is one of

the most common

AOP examples.



@Around("execution(* com.example.service.*.*(..))")
public Object executionTimeLogger(
        ProceedingJoinPoint joinPoint)
        throws Throwable {

    long start = System.currentTimeMillis();

    Object result = joinPoint.proceed();

    long end = System.currentTimeMillis();

    System.out.println(
            joinPoint.getSignature().getName()
            + " took "
            + (end - start)
            + " ms");

    return result;

}



Console Output



saveEmployee took 15 ms



markAttendance took 32 ms



approveLeave took 8 ms



Notice



One Aspect

works

for every Service.



No duplicate code.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    COMPLETE EXECUTION FLOW                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 HTTP Request

                       │

                       ▼

                Controller

                       │

                       ▼

                Service Proxy

                       │

                       ▼

          @Around Advice Starts

                       │

                       ▼

         Get Method Name

                       │

                       ▼

        Get Parameters

                       │

                       ▼

         Start Timer

                       │

                       ▼

      joinPoint.proceed()

                       │

                       ▼

        Original Service

                       │

                       ▼

         Get Return Value

                       │

                       ▼

        Stop Timer

                       │

                       ▼

        Log Execution Time

                       │

                       ▼

          Return Response



This is

how

Execution Time Logging

is implemented

in many

Spring Boot projects.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           COMMON MISTAKES                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Logging sensitive data

Don't log

Passwords

OTP

Tokens

Credit Card Numbers



------------------------------------------------------


❌ Forgetting

return result;



Controller

receives

null.



------------------------------------------------------


❌ Calling

proceed()

multiple times.



Business Method

executes

multiple times.



Only call

proceed()

once

unless you intentionally
want repeated execution.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What information can you get from
ProceedingJoinPoint?


Answer


Method Name

Method Arguments

Target Object

Return Value

and it allows execution
of the original method.



----------------------------------------------------------


Q2.

Which method executes
the original business method?


Answer


joinPoint.proceed()



----------------------------------------------------------


Q3.

Can you measure
API execution time
using AOP?


Yes.

Using

System.currentTimeMillis()

before

and after

joinPoint.proceed().



----------------------------------------------------------


Q4.

Which Advice is generally used
for execution time logging?


Answer


@Around



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 7 COMPLETE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Pointcut Expressions

✔ execution()

✔ *

✔ ..

✔ Package Matching

✔ Method Matching

✔ Real Project Pointcuts

✔ Reusable @Pointcut

*/





