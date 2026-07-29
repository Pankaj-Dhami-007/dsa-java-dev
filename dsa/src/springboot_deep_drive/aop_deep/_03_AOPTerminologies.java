package springboot_deep_drive.aop_deep;


/*
Decorator and  Proxy design pattern

They look almost identical because both wrap another object.
But their intent (purpose) is different. In design patterns, intent matters more than structure.

Let's compare.

                                     Structurally (Code)

-------- Decorator

Client
   │
   ▼
LoggingDecorator
   │
   ▼
EmployeeService

---------- Proxy

Client
   │
   ▼
EmployeeServiceProxy
   │
   ▼
EmployeeService

Looks almost identical.
Both implement the same interface.
Both hold a reference to the target.
Both delegate the call.
So structure is almost the same.

Difference is WHY they exist.

Decorator

Purpose:  Add new behavior/features to an object.
Each decorator adds new functionality.
The object's behavior is enhanced.

Proxy

Purpose: Control access to an object.


They have a very similar structure because both wrap another object and implement the same interface.
The difference lies in their intent. A Decorator is used to add or extend an object's behavior dynamically,
 while a Proxy is used to control access to the target object by performing tasks such as security checks,
  transaction management, lazy loading, or remote communication before delegating the call.
  Spring AOP uses the Proxy Pattern because its primary goal is to intercept and control method invocations.
 */
public class _03_AOPTerminologies {

}
// Can logging be implemented using the Decorator Pattern instead of AOP?
// ans
// Yes. The Decorator Pattern can add logging without modifying the target class by wrapping
// it with another object. In fact, Spring AOP proxies work on a similar idea—they wrap
// the target object and intercept method calls. The key difference is that
// decorators are created manually, whereas Spring automatically generates proxies at runtime,
// making the solution more scalable and easier to maintain for large applications.

// Concern = An area of interest or responsibility in an application.
/*
Real-Life Example

Imagine a hospital. The hospital has different concerns (areas of responsibility):

Patient Treatment
Billing
Security
Cleaning
Reception
Each is a concern because it is responsible for a specific part of the hospital.


Software Example

An application has different concerns:
Application
│
├── User Registration
├── Order Processing
├── Payment
├── Logging
├── Security
├── Transaction Management
└── Exception Handling

Each of these is a concern (an area of responsibility).

Types of Concerns
1. Core Concern (Business Concern) , vertical cencern
These are the main purpose of the application.
belongs to a specific module or feature of the application.
The code that solves the business problem.
Each module has its own business logic.

2. Cross-Cutting Concern  , infra logic
These support many core concerns.
These are called Cross-Cutting Concerns because they are needed across multiple modules.
These are not business features.
They provide infrastructure for the application.

Why are they called Infrastructure Concerns?

Because they support the application just like infrastructure supports a city.

Example:

A city has:

Schools
Hospitals
Banks
Offices

All of them need:

Electricity ⚡
Roads 🛣️
Water 💧
Internet 🌐

Electricity is not the business of a hospital.
Roads are not the business of a bank.
They are infrastructure.

Business logic stays inside one module (vertical).
Infrastructure logic is shared across all modules (cross-cutting).

                    APPLICATION

         Vertical Concerns (Business Logic)

      User Module      Order Module      Payment Module
      ------------     ------------      --------------
      Register         Place Order       Pay Bill
      Login            Cancel Order      Refund


      Infrastructure (Cross-Cutting) Concerns

           Logging
           Security
           Transactions
           Caching
           Exception Handling
 */

/**
A concern is a specific area of responsibility or functionality in a software application.
It can be either a core business concern or a cross-cutting concern.
 */

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────┐
 * │                  **SPRING BOOT DEEP DIVE - AOP SERIES**                           │
 * ├────────────────────────────────────────────────────────────────────────────────────┤
 * │                    **Chapter 03 : AOP TERMINOLOGIES**                             │
 * └────────────────────────────────────────────────────────────────────────────────────┘
 */

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                      ║
║                     SPRING BOOT DEEP DIVE ── AOP                                     ║
║                                                                                      ║
║                     Chapter 03 : AOP TERMINOLOGIES                                   ║
║                                                                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                      WHY DO WE NEED TO LEARN TERMINOLOGIES ?                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Imagine you are giving an interview.

Interviewer asks
        "What is an Aspect?"
You answer
        "Logging."

Interviewer asks
        "What is Advice?"

You answer
        "Logging."

Interviewer asks
        "What is Pointcut?"

You answer
        "Logging."

Everything becomes Logging.

This is one of the biggest mistakes beginners make.
Every terminology has a completely different meaning.
If these terminologies are not clear,
understanding Spring AOP internals becomes almost impossible.

┌────────────────────────────────────────────────────────────────────────────────────┐
│                         Relationship Between Terminologies                          │
└────────────────────────────────────────────────────────────────────────────────────┘

                        ┌──────────────────────────────┐
                        │           ASPECT             │
                        │ (e.g., LoggingAspect)        │
                        └──────────────┬───────────────┘
                                       │
                    Contains one or more Advices
                                       │
              ┌────────────────────────┼────────────────────────┐
              │                        │                        │
              ▼                        ▼                        ▼
      @Before Advice           @After Advice           @Around Advice
              │                        │                        │
              └────────────────────────┼────────────────────────┘
                                       │
                        Executes at a Join Point
                                       │
                                       ▼
                        ┌───────────────────────────┐
                        │        JOIN POINT         │
                        │ A method execution point  │
                        └──────────────┬────────────┘
                                       ▲
                                       │
                          Selected by Pointcut
                                       │
                        ┌──────────────┴────────────┐
                        │         POINTCUT          │
                        │ execution(* service.*.*) │
                        └──────────────┬────────────┘
                                       │
                                       ▼
                        ┌───────────────────────────┐
                        │       TARGET METHOD       │
                        │     saveEmployee()        │
                        └──────────────┬────────────┘
                                       │
                                Belongs to
                                       │
                                       ▼
                        ┌───────────────────────────┐
                        │       TARGET OBJECT       │
                        │     EmployeeService       │
                        └───────────────────────────┘



**Remember**

Don't try to memorize this diagram.
By the end of this chapter,
it will become completely natural.


                   @Aspect
             (LoggingAspect)
                     │
                     ▼
        +-------------------------+
        |        Pointcut         |
        | execution(* service.*)  |
        +-----------+-------------+
                    │
        Selects Matching Join Points
                    │
                    ▼
        +-------------------------+
        |      Join Point         |
        | saveEmployee() Method   |
        +-----------+-------------+
                    │
          Advice executes here
                    │
      ┌─────────────┼─────────────┐
      │             │             │
      ▼             ▼             ▼
   @Before       @Around       @After
      │             │             │
      └─────────────┼─────────────┘
                    │
                    ▼
        +-------------------------+
        |      Target Method      |
        |     saveEmployee()      |
        +-----------+-------------+
                    │
                    ▼
        +-------------------------+
        |      Target Object      |
        |    EmployeeService      |
        +-------------------------+




simple -

                 ASPECT
                    │
     ┌──────────────┴──────────────┐
     │                             │
 Contains Pointcuts         Contains Advices
     │                             │
     └──────────────┬──────────────┘
                    │
          Pointcut selects
                    │
               JOIN POINT
          (Method Execution)
                    │
          Advice executes here
                    │
                    ▼
             TARGET METHOD
                    │
                    ▼
             TARGET OBJECT



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                             1. WHAT IS AN ASPECT ?                                  ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

This is probably the MOST IMPORTANT AOP terminology.
Official Definition


 */
/**
An Aspect is a class that contains cross-cutting concerns.
 One Aspect can contain multiple Advices.

Simple Definition
An Aspect is simply a normal Java class whose responsibility is NOT business logic.
Instead, it contains common functionality that should execute for multiple business methods.

 */
/*
Examples


Logging
Security
Performance Monitoring
Transactions
Caching
Auditing
Notification
Retry Logic


These functionalities are called Cross-Cutting Concerns.
The class that contains them is called Aspect.


╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL LIFE ANALOGY                                           ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine a Hospital.

Doctors belong to different departments.


Cardiology
Neurology
Orthopedics
Emergency
ENT
Pediatrics

But every patient first goes through

Registration

        │

        ▼

Token Generation

        │

        ▼

Insurance Verification

        │

        ▼

Doctor Consultation



Notice something?

Registration is required for every department.
Instead of every doctor creating patient registration,

the hospital has one Registration Department.

Registration Department is like an

Aspect.


Every Doctor is like a Business Service.


╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         HRMS PROJECT EXAMPLE                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose your HRMS project contains


EmployeeService
AttendanceService
LeaveService
PayrollService
DepartmentService
HolidayService

Every service requires

Logging
Execution Time
Exception Logging
Audit

Should every service
implement logging separately?

NO.

Instead create LoggingAspect
which automatically logs every service method.

ASCII Diagram

                  LoggingAspect

                       │

       ┌───────────────┼────────────────┐

       │               │                │

       ▼               ▼                ▼

EmployeeService  LeaveService   AttendanceService


One Aspect works for hundreds of methods.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                   WHAT DOES AN ASPECT CONTAIN ?                                     ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

An Aspect does NOT directly execute.


Instead, it contains

                Advice
Think like this
Aspect is a Container.
Advice is the actual logic.

Diagram

                 LoggingAspect

           ┌──────────────────────┐

           │                      │

           │ Before Advice        │

           │                      │

           │ After Advice         │

           │                      │

           │ Around Advice        │

           │                      │

           └──────────────────────┘



So Aspect contains Advice.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        2. WHAT IS ADVICE ?                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

 */
 /**
 Definition
Advice is the action performed by an Aspect at a particular Join Point.

Simple Definition
Advice is the actual code that Spring executes before, after, or around a target method.

Think like this
Aspect is the class. Advice is the method.
or Advice is the method inside an Aspect that contains the cross-cutting logic.

Advice is the action or code executed by an Aspect at a specific Join Point. In Spring AOP,
an advice is a method inside an Aspect that contains cross-cutting logic,
such as logging, transaction handling, or security checks, and it executes before, after, or around
the target method.
 */
/*
For example

LoggingAspect contains
beforeLogging()
afterLogging()
measureExecutionTime()

These methods are Advices.

ASCII Representation



          LoggingAspect

      ┌──────────────────────┐

      │ beforeLogging()      │

      │                      │

      │ afterLogging()       │

      │                      │

      │ executionTime()      │

      └──────────────────────┘


Every method inside Aspect is an Advice.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    REAL SPRING BOOT EXAMPLE                                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

@Aspect
public class LoggingAspect{

        @Before(...)
        public void beforeLog(){

        }


        @After(...)
        public void afterLog(){

        }

}


Here LoggingAspect is the Aspect.
beforeLog() is an Advice.
afterLog() is another Advice.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    DIFFERENT TYPES OF ADVICE                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝
Spring supports
five major Advice types.
 */
/**

                              ADVICE
                 (What code should execute?)

                                   │
         ┌─────────────────────────┼─────────────────────────┐
         │                         │                         │
         ▼                         ▼                         ▼
     @Before                  @After                  @Around
  Before Method            After Method        Before + After +
      Executes               Executes          Controls Execution
                                                           │
                                                           │
                                      ┌────────────────────┴────────────────────┐
                                      │                                         │
                                      ▼                                         ▼
                             @AfterReturning                          @AfterThrowing
                           After Successful Return                  After Exception


                             Before
                             Runs BEFORE method execution.

                             After
                             Runs AFTER method execution.

                             Around
                             Runs before and after the method.

                             After Returning
                             Runs only if method completes successfully.

                             After Throwing
                             Runs only when exception occurs.



                             METHOD EXECUTION

 @Before
 │
 ▼
 Target Method
 /      \
 /        \
 Success      Exception
 │             │
 ▼             ▼
 @AfterReturning  @AfterThrowing
 \        /
 \      /
 ▼    ▼
 @After

 ═══════════════════════════════════════
 @Around wraps EVERYTHING above.
 ═══════════════════════════════════════

 */

/*
We will study every Advice in detail later.
For now, remember only their purpose.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                       ASPECT vs ADVICE                                              ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────┬──────────────────────────────────────────────┐
│ Aspect                      │ Advice                                       │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ A Java Class                │ A Method inside Aspect                       │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Container                   │ Actual Logic                                │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Can contain many Advices    │ Belongs to exactly one Aspect               │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Example : LoggingAspect     │ beforeLog(), afterLog()                     │
└─────────────────────────────┴──────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     COMMON INTERVIEW MISTAKES                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake 1

Aspect and Advice are the same.
Wrong. Aspect is a Class. Advice is a Method.
--------------------------------------------------------

❌ Mistake 2

Aspect executes.
Wrong.
Advice executes.
Aspect simply contains Advices.
--------------------------------------------------------

❌ Mistake 3

One Aspect contains only one Advice. Wrong.
One Aspect can contain multiple Advices.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝
 */
/**

Q1

What is an Aspect?
Answer
An Aspect is a Java class that encapsulates
cross-cutting concerns such as logging,
security, transactions, auditing, caching etc.
------------------------------------------------------

Q2

What is Advice?

Answer
Advice is the actual action
executed by an Aspect
at a specific Join Point.
------------------------------------------------------

Q3

Can one Aspect contain multiple Advices?
Yes.
This is very common in Spring Boot.
Example
LoggingAspect

        beforeLog()
        afterLog()
        executionTime()
 */
/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                            PART 1 COMPLETE                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Join Point
✔ Pointcut
✔ Difference Between Join Point and Pointcut
✔ Target Object
✔ Proxy Object
*/

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          3. WHAT IS A JOIN POINT ?                                  ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


This is one of the **MOST CONFUSING** AOP terminologies.

Many developers confuse
        Join Point
with
        Pointcut.
Let's understand it slowly.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           OFFICIAL DEFINITION                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


 */

/**
A Join Point is any point during program execution where an Aspect can potentially execute an Advice.
In Spring AOP, every method execution is a Join Point because Spring's proxy mechanism can
intercept method executions and apply cross-cutting logic before, after, or around them.

 A Join Point is a point during the execution of a program where
 additional behaviour can be applied.

 A Join Point is any place in your program where Spring AOP has the opportunity to execute an Advice.

 A Join Point is a possible execution point where an Aspect can run.

 Join Point = Opportunity
 Spring sees an opportunity and asks:
 "Do I need to execute any Advice here?"
 If yes, it executes it.

 Real Life Example
 Imagine a shopping mall.

 Every location is a place where a security guard could stand.
 Those possible locations are like Join Points.
 The guard doesn't have to stand everywhere.
 He can stand there.
 That's why they're called possible execution points.

 note -

 Join Point = Every possible place.
 Pointcut    = The places we actually choose.
 Advice      = The code executed at those chosen places.
 */
        /*

Sounds difficult? Let's simplify it.
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           SIMPLE DEFINITION                                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Imagine a Java class.

EmployeeService contains

                 EmployeeService

        +-----------------------------+
        | saveEmployee()              |  ◄── Join Point
        +-----------------------------+

        +-----------------------------+
        | updateEmployee()            |  ◄── Join Point
        +-----------------------------+

        +-----------------------------+
        | deleteEmployee()            |  ◄── Join Point
        +-----------------------------+

        +-----------------------------+
        | findEmployee()              |  ◄── Join Point
        +-----------------------------+

        +-----------------------------+
        | calculateSalary()           |  ◄── Join Point
        +-----------------------------+

Every method is a potential place where Spring can execute extra logic.
Each such place is called
                **Join Point**

Simply remember
                Join Point = Possible Execution Point

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        VISUAL REPRESENTATION                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

                EmployeeService

        ┌────────────────────────────┐

        │ saveEmployee()       ●     │

        │                            │

        │ updateEmployee()     ●     │

        │                            │

        │ deleteEmployee()     ●     │

        │                            │

        │ findEmployee()       ●     │

        │                            │

        │ calculateSalary()    ●     │

        └────────────────────────────┘


Every ● represents a Join Point.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         HRMS PROJECT EXAMPLE                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


AttendanceService

markAttendance()
approveAttendance()
rejectAttendance()
generateAttendanceReport()

Each method is a Join Point.

Similarly LeaveService

applyLeave()
approveLeave()
cancelLeave()
rejectLeave()
Again,

every method is a Join Point.

Think like this.

Method exists

↓

Spring CAN intercept it

↓
Therefore it is a Join Point.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         IMPORTANT NOTE                                              ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


**Remember**

In Spring AOP,

Join Point means
                **Method Execution**


Spring AOP does NOT support
Constructor Execution
Field Access
Object Initialization
Static Block Execution

Those are supported by AspectJ.

So during interviews, if someone asks
"What is a Join Point in Spring AOP?"

Answer
**Every method execution of a Spring-managed bean
is a Join Point.**

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                            4. WHAT IS A POINTCUT ?                                  ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


         */

/**
 *        ------- POINTCUT
A Pointcut selects Join Points.
 A Pointcut is an expression that identifies one or more Join Points where an Advice should be applied.

 or A Pointcut is a rule or filter that tells Spring at which Join Points an Advice should execute.
 Pointcut = Selection Rule

 All Methods
 │
 ▼
 Join Points
 │
 ▼
 Pointcut Filters
 │
 ▼
 Selected Join Points
 │
 ▼
 Advice Executes

 A Pointcut is an expression or rule that selects specific Join Points where an Advice should execute.
 While every method execution is a Join Point in Spring AOP, a Pointcut filters
 those Join Points so that the Advice runs only for the methods that match the defined expression.

 or
 A Join Point is every possible interception point (method execution in Spring AOP),
 while a Pointcut is the expression that selects which of those Join Points should execute an Advice.


 EmployeeService

 save()        ◄── Join Point
 update()      ◄── Join Point
 delete()      ◄── Join Point
 find()        ◄── Join Point
 login()       ◄── Join Point

 │
 ▼
 Pointcut Expression
 (execution(* *Service.save*(..)))

 │
 ▼
 save() Selected

 │
 ▼
 Advice Executes

         */
        /*
Now comes the second terminology.

A Pointcut is an expression that selects one or more Join Points.

Still sounds difficult?
Let's simplify.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                            SIMPLE DEFINITION                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose
EmployeeService contains

saveEmployee()
updateEmployee()
deleteEmployee()
findEmployee()
generateSalary()

All of them are Join Points.

But suppose you only want logging for saveEmployee()
and updateEmployee()

Question

How will Spring know which methods should execute logging?

Answer

Using
                Pointcut

Pointcut filters Join Points.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          VISUAL REPRESENTATION                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

All Join Points


saveEmployee()          ●
updateEmployee()        ●
deleteEmployee()        ●
findEmployee()          ●
generateSalary()        ●

Pointcut selects



saveEmployee()          ✔
updateEmployee()        ✔
Remaining methods

deleteEmployee()        ✘
findEmployee()          ✘
generateSalary()        ✘


Only selected methods will execute Advice.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        REAL SPRING EXAMPLE                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

@Before(
"execution(* com.company.service.EmployeeService.saveEmployee(..))"

)

This expression is a Pointcut.
It selects saveEmployee() from all available Join Points.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         ANOTHER REAL EXAMPLE                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



Suppose

AttendanceService contains

markAttendance()
approveAttendance()
rejectAttendance()
downloadReport()
deleteAttendance()

Manager says
Log only approval methods.
Pointcut becomes
approveAttendance()
Only that Join Point
will execute Advice.

ASCII Diagram

All Join Points

markAttendance()          ●
approveAttendance()       ●
rejectAttendance()        ●
downloadReport()          ●
deleteAttendance()        ●

Pointcut

approveAttendance()       ✔
Only one method selected.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                   JOIN POINT vs POINTCUT                                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



┌────────────────────────────┬─────────────────────────────────────────────────────┐
│ Join Point                 │ Pointcut                                            │
├────────────────────────────┼─────────────────────────────────────────────────────┤
│ Possible execution point   │ Selects execution points                            │
├────────────────────────────┼─────────────────────────────────────────────────────┤
│ Exists automatically       │ Created by developer                                │
├────────────────────────────┼─────────────────────────────────────────────────────┤
│ Every method               │ Selected methods only                               │
├────────────────────────────┼─────────────────────────────────────────────────────┤
│ Cannot filter              │ Filters Join Points                                 │
├────────────────────────────┼─────────────────────────────────────────────────────┤
│ Spring provides            │ Developer defines                                   │
└────────────────────────────┴─────────────────────────────────────────────────────┘



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        SIMPLE MEMORY TRICK                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine

100 students

standing in a classroom.



Every student

represents

a Join Point.



Teacher says


"I only want students

whose roll number

is less than 10."



Teacher's condition

is a

Pointcut.



Selected students



1

2

3

4

5

6

7

8

9

10



Remaining students

are ignored.



Exactly

Pointcut works like this.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     COMMON INTERVIEW MISTAKES                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake


Join Point

and

Pointcut

are the same.



Wrong.



Join Point

means

possible execution point.



Pointcut

means

selected execution point.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Q1

What is a Join Point?


Answer


A Join Point is a point during program execution
where an Advice can potentially execute.
In Spring AOP,
every method execution
is a Join Point.

------------------------------------------------------


Q2

What is Pointcut?
Answer

A Pointcut is an expression
used to select one or more Join Points.

------------------------------------------------------


Q3

Which comes first?

Join Point
or
Pointcut?

Answer
Join Points already exist.
Pointcut selects
some of them.
------------------------------------------------------


Q4

Can every Join Point
execute Advice?

No.

Only the Join Points selected
by Pointcut
will execute Advice.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                            PART 2 COMPLETE                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Target Object

✔ Proxy Object

✔ Weaving

✔ Advisor

✔ Complete Relationship Diagram

✔ Summary

*/

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           5. WHAT IS A TARGET OBJECT ?                              ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Now we know

        ✔ Aspect
        ✔ Advice
        ✔ Join Point
        ✔ Pointcut
Now another question arises.


Question

Who actually executes the business logic?

Answer
The
                **Target Object**

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         OFFICIAL DEFINITION                                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Target Object is the actual object whose methods are intercepted by Spring AOP.

Target Object is simply your original class.

Examples

EmployeeService
AttendanceService
LeaveService
PayrollService
CustomerService

Spring never changes
their business logic.
Instead,
Spring surrounds them
with
Proxy Objects.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         VISUAL REPRESENTATION                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

                 EmployeeService
      saveEmployee()
      updateEmployee()
      deleteEmployee()
This original object
is called
                Target Object.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        IMPORTANT NOTE                                               ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


 */

/**
**Remember**

AOP does NOT replace your service class.
It simply adds another object before it.

Target Object always contains the original business logic.
 */
/*

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           6. WHAT IS A PROXY OBJECT ?                               ║
╚══════════════════════════════════════════════════════════════════════════════════════╝
This is one of the MOST IMPORTANT concepts in Spring AOP.
Almost everything inside Spring AOP depends upon Proxy Objects.
If you understand Proxy, you understand Spring AOP.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          OFFICIAL DEFINITION                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


 */
/**
********************** PROXY OBJECT **************
A Proxy Object is an object created by Spring that stands between
the client and the Target Object to apply additional behaviour.

 Think of a Proxy as  a middleman. Instead of Client calling EmployeeService directly,
 Client calls EmployeeServiceProxy.
 The Proxy decides
 Should Logging execute?
 Should Transaction begin?
 Should Security execute?
 Should Cache be checked?
 After completing them,
 the Proxy finally calls
 EmployeeService.

 Client
 │
 │ 1. Calls saveEmployee()
 ▼
 Spring AOP Proxy
 │
 ├──► Before Logging Advice
 │
 ├──► Before Security Advice
 │
 ├──► Begin Transaction
 │
 ├──► Execute EmployeeService.saveEmployee()
 │
 ├──► Commit / Rollback Transaction
 │
 ├──► After Logging Advice
 │
 ▼
 Return Result to Client


 Notice
 Client
 never communicates directly with EmployeeService. Everything passes through the Proxy.

 */

/*

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    REAL LIFE ANALOGY                                                ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine
CEO of a company.
Can every employee
directly meet
the CEO?

NO.

Employees first meet
Secretary.
Secretary checks

Appointment

↓

Permission

↓

Schedule

↓

Availability



Only then
CEO is called.

ASCII Diagram



Employee

    │

    ▼

Secretary

    │

Permission Check

    │

Appointment Check

    │

Meeting Schedule

    │

    ▼

CEO



CEO = Target Object

Secretary = Proxy Object.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    HRMS PROJECT EXAMPLE                                             ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose


AttendanceService contains markAttendance()
Now

@Transactional is added.

Question

Will Spring modify AttendanceService?   NO.

Spring creates
AttendanceServiceProxy
Execution Flow


Controller

      │

      ▼

AttendanceServiceProxy

      │

Begin Transaction

      │

      ▼

AttendanceService

      │

Save Attendance

      │

Return

      │

Commit

      │

Return Response


This is exactly how Spring implements @Transactional.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     WHY DOES SPRING NEED A PROXY ?                                  ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Without Proxy
Client calls EmployeeService directly.

No place exists to execute
Logging
Security
Transactions.

Proxy solves this problem.
It inserts itself between Client and Target Object.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        WITHOUT PROXY                                                ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Client

   │

   ▼

EmployeeService

   │

   ▼

Database

No interception.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         WITH PROXY                                                  ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Client

   │

   ▼

EmployeeServiceProxy

   │

Logging

   │

Security

   │

Transaction

   │

   ▼

EmployeeService

   │

   ▼

Database



Now Spring can execute additional behaviour.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                   TARGET OBJECT vs PROXY OBJECT                                     ║
╚══════════════════════════════════════════════════════════════════════════════════════╝
┌────────────────────────────┬────────────────────────────────────────────────┐
│ Target Object              │ Proxy Object                                   │
├────────────────────────────┼────────────────────────────────────────────────┤
│ Original Bean              │ Spring Generated Bean                          │
├────────────────────────────┼────────────────────────────────────────────────┤
│ Contains Business Logic    │ Contains Extra Behaviour                       │
├────────────────────────────┼────────────────────────────────────────────────┤
│ Created By Developer       │ Created By Spring                              │
├────────────────────────────┼────────────────────────────────────────────────┤
│ Doesn't Know AOP           │ Executes AOP                                   │
├────────────────────────────┼────────────────────────────────────────────────┤
│ Calls Database             │ Calls Target Object                            │
└────────────────────────────┴────────────────────────────────────────────────┘


╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



 */
/**
Q1

What is Target Object?
Answer

Target Object is the original Spring bean
whose methods are intercepted by Spring AOP.

----------------------------------------------------------


Q2

What is Proxy Object?

Answer

A Proxy Object is a Spring-generated object
that sits between the client and the target object
to execute additional behaviour like logging,
transactions, caching, security etc.

----------------------------------------------------------


Q3

Who creates Proxy Objects?

Answer

Spring Framework creates Proxy Objects
during bean creation.

----------------------------------------------------------


Q4

Can Client directly call Target Object?
Practically,
No.
Client usually receives
the Proxy Bean
from the Spring IOC Container.
 */
/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 3 COMPLETE                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Next Part
✔ Weaving
✔ Advisor
✔ Complete Relationship Diagram
✔ Chapter Summary
✔ Interview Cheat Sheet

*/

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                              7. WHAT IS WEAVING ?                                   ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


After understanding

        ✔ Aspec
        ✔ Advice
        ✔ Join Point
        ✔ Pointcut
        ✔ Target Object
        ✔ Proxy Object
one question naturally comes into our mind.

Question
How does Spring connect Aspect with Target Object?
The answer is
                **Weaving**


╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           OFFICIAL DEFINITION                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Weaving is the process of connecting an Aspect with a Target Object
to create an advised object.

Sounds complicated?
Let's simplify.

╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           SIMPLE DEFINITION                                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose you have


LoggingAspect



and


EmployeeService



Initially

they don't know anything

about each other.



EmployeeService

contains

business logic.



LoggingAspect

contains

logging logic.



Spring combines them

using

                Weaving.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        BEFORE WEAVING                                               ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



LoggingAspect



        beforeLog()



EmployeeService



        saveEmployee()



Both are

completely independent.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         AFTER WEAVING                                               ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



Client

   │

   ▼

EmployeeServiceProxy

   │

beforeLog()

   │

saveEmployee()

   │

afterLog()



Now

Logging

and

Business Logic

work together.



This connection process

is called

                Weaving.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        REAL LIFE ANALOGY                                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine

a shirt.



Initially


Shirt

and

Company Logo

are separate.



Shirt

      +

Logo



Factory combines them.



After stitching


Company Shirt



The stitching process

is similar to

Weaving.



Aspect

+

Business Class

↓

Spring combines them

↓

Proxy Object



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        TYPES OF WEAVING                                             ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


There are

three types

of Weaving.



                Weaving

                    │

      ┌─────────────┼──────────────────┐

      │             │                  │

      ▼             ▼                  ▼

 Compile Time   Load Time      Runtime



Let's understand

each one.



══════════════════════════════════════════════════════════════════════════════════════════

1. Compile Time Weaving

══════════════════════════════════════════════════════════════════════════════════════════


Aspect

and

Business Class

are combined

during compilation.



Used by

AspectJ.



══════════════════════════════════════════════════════════════════════════════════════════

2. Load Time Weaving

══════════════════════════════════════════════════════════════════════════════════════════


Combination happens

while

class is loaded

into JVM.



Mostly used

by AspectJ.



══════════════════════════════════════════════════════════════════════════════════════════

3. Runtime Weaving

══════════════════════════════════════════════════════════════════════════════════════════


This is what

Spring AOP

uses.



Application Starts

↓

Bean Created

↓

Proxy Created

↓

Aspect Attached

↓

Bean Ready



This entire process

happens

during runtime.



**Remember**

Spring AOP supports

only

Runtime Weaving.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    SPRING RUNTIME WEAVING FLOW                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



Application Starts

        │

        ▼

IOC Container Starts

        │

        ▼

EmployeeService Bean Created

        │

        ▼

Spring Detects Aspect

        │

        ▼

Creates Proxy

        │

        ▼

Attaches Advice

        │

        ▼

Stores Proxy In IOC Container

        │

        ▼

Application Ready



Notice

Original Bean

is NOT returned.

Proxy Bean

is stored

inside Spring Container.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                            8. WHAT IS AN ADVISOR ?                                  ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Advisor

is another interview term.



Many developers

never use it directly,

but Spring internally

uses Advisors extensively.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        OFFICIAL DEFINITION                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


**Advisor is an object that combines an Advice with a Pointcut.**



Simple Definition


Think like this.


Advice

answers

"What should execute?"


Pointcut

answers

"Where should it execute?"


Advisor

combines both.



ASCII Diagram



              Advisor

                  │

        ┌─────────┴─────────┐

        ▼                   ▼

     Advice             Pointcut



Spring internally

creates Advisors

while processing Aspects.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     COMPLETE RELATIONSHIP OF ALL TERMINOLOGIES                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



                               Aspect

                                  │

                  Contains Multiple Advices

                                  │

                                  ▼

                               Advice

                                  │

                      Executes At Join Point

                                  │

                                  ▼

                              Join Point

                                  │

                      Selected By Pointcut

                                  │

                                  ▼

                              Pointcut

                                  │

                        Combined With Advice

                                  │

                                  ▼

                               Advisor

                                  │

                     Applied To Target Object

                                  │

                                  ▼

                            Proxy Created

                                  │

                                  ▼

                           Client Executes



This is

the complete picture

of Spring AOP.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     COMPLETE EXECUTION FLOW                                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



Client

   │

   ▼

Proxy

   │

Advice Executes

   │

Pointcut Matches ?

   │

 ┌─Yes──────────────┐

 │                  │

 ▼                  │

Target Method       │

 │                  │

 ▼                  │

Return              │

 │                  │

 └──────────────────┘



If Pointcut

doesn't match,

Advice never executes.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          QUICK MEMORY TRICK                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Aspect

=

Container



Advice

=

What to Execute



Join Point

=

Possible Place



Pointcut

=

Selected Place



Target Object

=

Original Bean



Proxy

=

Spring Generated Bean



Weaving

=

Connection Process



Advisor

=

Advice + Pointcut



If you remember

just these seven lines,

you can explain

AOP terminology

confidently in interviews.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        FREQUENTLY ASKED INTERVIEW QUESTIONS                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What is Weaving?


Answer

Weaving is the process of applying an Aspect
to a Target Object to create an advised object.



--------------------------------------------------------


Q2.

Which type of Weaving
does Spring AOP use?


Answer

Runtime Weaving.



--------------------------------------------------------


Q3.

What is Advisor?


Answer

Advisor is a combination of

Pointcut

and

Advice.



--------------------------------------------------------


Q4.

Does Spring AOP modify
the original class?


Answer

No.

Spring creates

Proxy Objects.



--------------------------------------------------------


Q5.

Does Spring AOP support
Compile Time Weaving?


Answer

No.

Only Runtime Weaving.

Compile Time and Load Time
Weaving are provided by AspectJ.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           CHAPTER 03 SUMMARY                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


By now you should clearly understand

✔ Aspect

✔ Advice

✔ Join Point

✔ Pointcut

✔ Target Object

✔ Proxy Object

✔ Weaving

✔ Advisor

More importantly,

you should understand

how these concepts

are related to each other.



**Remember**

Don't memorize these terms independently.

Always visualize the flow.


        Aspect

          │

          ▼

       Advice

          │

          ▼

     Join Point

          │

          ▼

      Pointcut

          │

          ▼

      Target Bean

          │

          ▼

      Proxy Object

          │

          ▼

     Method Execution



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         NEXT CHAPTER PREVIEW                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


                Chapter 04

        **How Spring AOP Works Internally**


This is the most important chapter
of the complete AOP series.


We will answer

✔ What happens when Spring starts?

✔ How does Spring detect @Aspect?

✔ Who creates the Proxy?

✔ What is BeanPostProcessor?

✔ What is AnnotationAwareAspectJAutoProxyCreator?

✔ JDK Proxy vs CGLIB (Internal View)

✔ Why does @Transactional work?

✔ Why does self-invocation fail?

✔ Complete IOC + Proxy creation flow.

*/