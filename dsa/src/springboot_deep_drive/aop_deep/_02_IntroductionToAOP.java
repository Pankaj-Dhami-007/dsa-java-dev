package springboot_deep_drive.aop_deep;

public class _02_IntroductionToAOP {

}

/**
 * ┌──────────────────────────────────────────────────────────────────────────────┐
 * │              **SPRING BOOT DEEP DIVE - AOP SERIES**                         │
 * ├──────────────────────────────────────────────────────────────────────────────┤
 * │                     **Chapter 02 : Introduction To AOP**                    │
 * └──────────────────────────────────────────────────────────────────────────────┘
 */

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                      ║
║                     SPRING BOOT DEEP DIVE ── AOP                                     ║
║                                                                                      ║
║                    Chapter 02 : INTRODUCTION TO AOP                                  ║
║                                                                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          1. WHAT ARE WE GOING TO LEARN ?                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

In Chapter 1, we answered

        **WHY DO WE NEED AOP?**

We learned that business classes become cluttered because of
Logging, Security, Transactions, Validation, Auditing etc.

Now another question arises...

        **What exactly is AOP?**

Is it

        • A Java Feature?

        • A Spring Feature?

        • A Framework?

        • A Design Pattern?

        • A Programming Paradigm?

This chapter answers all these questions.


┌────────────────────────────────────────────────────────────────────────────────────┐
│                                                                                    │
│                     **By the end of this chapter you will know**                  │
│                                                                                    │
├────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                    │
│  ✔ What is Aspect-Oriented Programming?                                           │
│                                                                                    │
│  ✔ Why OOP alone was not enough?                                                  │
│                                                                                    │
│  ✔ Why AspectJ was introduced?                                                    │
│                                                                                    │
│  ✔ How Spring uses AOP?                                                           │
│                                                                                    │
│  ✔ Difference between OOP and AOP                                                 │
│                                                                                    │
│  ✔ Where AOP is actually used in Spring Boot                                      │
│                                                                                    │
└────────────────────────────────────────────────────────────────────────────────────┘



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     2. EVOLUTION OF PROGRAMMING PARADIGMS                           ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

Programming did not start with AOP.

It evolved gradually.

Every new paradigm tried to solve problems that
the previous paradigm could not solve.


                             Evolution Timeline


         Procedural Programming
                  (1970s)
                      │
                      │
      Solved simple sequential programs
                      │
                      ▼
        Object-Oriented Programming
                  (1980s)
                      │
                      │
      Solved code organization using
      Classes and Objects
                      │
                      ▼
      Aspect-Oriented Programming
                  (1990s)
                      │
                      │
      Solved Cross-Cutting Concerns
                      │
                      ▼
            Modern Spring Boot



**Important**

AOP did NOT replace OOP.

Instead,

        **AOP complements OOP.**

Both work together.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    3. WHY WAS OOP NOT ENOUGH ?                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

When Object-Oriented Programming was introduced,
it solved many problems.

For example

✔ Encapsulation

✔ Inheritance

✔ Polymorphism

✔ Abstraction

✔ Reusability

✔ Better Code Organization


Example

Without OOP

------------------------------------

saveEmployee()

saveCustomer()

saveProduct()

Everything written independently.

------------------------------------

With OOP

                Person

                  ▲

      ┌───────────┴───────────┐

 Employee                 Customer


Code reuse became easy.

This was a revolutionary improvement.


But...

One problem still remained.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                  4. THE PROBLEM THAT OOP COULD NOT SOLVE                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose we have


                EmployeeService

                CustomerService

                ProductService

                PaymentService

                AttendanceService

                LeaveService



Every service performs

        Business Logic

But every service ALSO contains

        Logging

        Security

        Transactions

        Validation

        Auditing

        Exception Handling



ASCII Representation


             EmployeeService

     ┌──────────────────────────────┐

     │ Logging                      │

     │ Security                     │

     │ Transaction                  │

     │ Validation                   │

     │ Audit                        │

     │ Save Employee                │

     └──────────────────────────────┘



             CustomerService

     ┌──────────────────────────────┐

     │ Logging                      │

     │ Security                     │

     │ Transaction                  │

     │ Validation                   │

     │ Audit                        │

     │ Save Customer                │

     └──────────────────────────────┘



Notice something?

The business logic is different.

But

Logging

Security

Transactions

are exactly the same.


**This is the limitation of OOP.**

OOP organizes BUSINESS OBJECTS very well,

but

it does not provide a good mechanism
to separate cross-cutting concerns.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                   5. WHAT IS ASPECT ORIENTED PROGRAMMING ?                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


There are two ways to understand AOP.


────────────────────────────────────────────────────────────────────

Definition (Interview)

────────────────────────────────────────────────────────────────────


**Aspect-Oriented Programming (AOP)**

is a programming paradigm that separates

        **Cross-Cutting Concerns**

from

        **Business Logic**

to improve

        ✔ Maintainability

        ✔ Reusability

        ✔ Readability

        ✔ Separation of Concerns



────────────────────────────────────────────────────────────────────

Simple Definition

────────────────────────────────────────────────────────────────────


Imagine you write logging code

inside

500 methods.


AOP says

        "Don't write it 500 times."

Write it

ONLY ONCE

and let the framework execute it automatically
wherever required.



This single sentence explains
the entire purpose of AOP.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                      6. REAL LIFE ANALOGY                                           ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine an Airport.



Every passenger wants only one thing.


                    Catch Flight



But before boarding,


everyone goes through


            Identity Verification

                    │

                    ▼

             Security Checking

                    │

                    ▼

               Baggage Scan

                    │

                    ▼

            Immigration Check

                    │

                    ▼

                 Boarding

                    │

                    ▼

                 Flight



Question


Does every airline implement
security separately?


NO.


The Airport performs those common tasks.


Exactly similarly,


EmployeeService

CustomerService

OrderService

PaymentService


should not implement Logging,
Transactions and Security separately.


AOP performs them centrally.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    7. AOP IN ONE SENTENCE                                           ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


If someone asks in an interview


"What is AOP?"


You can answer


┌────────────────────────────────────────────────────────────────────────────────────┐
│                                                                                    │
│ **Aspect-Oriented Programming is a programming paradigm that allows us to**        │
│ **separate cross-cutting concerns like logging, security, transaction**            │
│ **management, caching and auditing from the core business logic.**                 │
│                                                                                    │
└────────────────────────────────────────────────────────────────────────────────────┘


This is a perfect interview answer.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          PART 1 COMPLETE                                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Next Part Covers

✔ OOP vs AOP

✔ Is AOP a Java feature?

✔ Is Spring AOP different from AspectJ?

✔ Advantages

✔ Disadvantages

✔ Real Project Usage

✔ Interview Questions

*/

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                          8. OOP vs AOP                                              ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

This is one of the most common interview questions.

Many developers think AOP replaces OOP.

**This is completely incorrect.**

AOP is NOT a replacement for Object-Oriented Programming.

Instead,

            OOP + AOP = Complete Enterprise Application



┌────────────────────────────────────────────────────────────────────────────────────┐
│                           OBJECT ORIENTED PROGRAMMING                              │
└────────────────────────────────────────────────────────────────────────────────────┘

OOP focuses on

        **Objects**

It tries to answer

        "How should business entities be organized?"

Example

Employee

Customer

Order

Product

Invoice

Department

Each entity becomes a separate class.



Example



                Employee

        +----------------------+

        id

        name

        salary

        save()

        update()

        delete()

        +----------------------+



Every object has

✔ State

✔ Behaviour



OOP Principles


        Encapsulation

        Inheritance

        Polymorphism

        Abstraction



These principles help organize business code.



But...



They DO NOT solve


Logging

Transactions

Caching

Security

Auditing

Exception Handling



because these are not properties of any object.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                       WHAT DOES AOP FOCUS ON ?                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


AOP focuses on

        **Aspects**

instead of Objects.


An Aspect represents

a common functionality

that should execute

before

after

or around

many business methods.



Example


Logging Aspect

Security Aspect

Audit Aspect

Transaction Aspect

Caching Aspect

Monitoring Aspect



Instead of adding logging

inside Employee,

Customer,

Order,

Product,

AOP keeps logging

inside one place.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                            OOP vs AOP DIAGRAM                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



                     WITHOUT AOP



             EmployeeService

      ┌──────────────────────────────┐

      │ Logging                      │

      │ Security                     │

      │ Transaction                  │

      │ Save Employee                │

      └──────────────────────────────┘



             CustomerService

      ┌──────────────────────────────┐

      │ Logging                      │

      │ Security                     │

      │ Transaction                  │

      │ Save Customer                │

      └──────────────────────────────┘



Every class contains

duplicate code.



------------------------------------------------------------



                      WITH AOP



              Logging Aspect

                     │

                     ▼



              Security Aspect

                     │

                     ▼



            Transaction Aspect

                     │

                     ▼



             EmployeeService

      ┌──────────────────────────────┐

      │ Save Employee                │

      └──────────────────────────────┘



             CustomerService

      ┌──────────────────────────────┐

      │ Save Customer                │

      └──────────────────────────────┘



Business classes become

clean

small

easy to understand.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           OOP vs AOP COMPARISON                                    ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



┌────────────────────────────┬────────────────────────────────────────────┐
│ Object Oriented Programming│ Aspect Oriented Programming                │
├────────────────────────────┼────────────────────────────────────────────┤
│ Focuses on Objects         │ Focuses on Aspects                         │
├────────────────────────────┼────────────────────────────────────────────┤
│ Organizes business logic   │ Organizes cross-cutting concerns           │
├────────────────────────────┼────────────────────────────────────────────┤
│ Uses Classes               │ Uses Aspects                              │
├────────────────────────────┼────────────────────────────────────────────┤
│ Uses Inheritance           │ Uses Advice                               │
├────────────────────────────┼────────────────────────────────────────────┤
│ Uses Polymorphism          │ Uses Pointcuts                            │
├────────────────────────────┼────────────────────────────────────────────┤
│ Object Interaction         │ Method Interception                       │
├────────────────────────────┼────────────────────────────────────────────┤
│ Business Features          │ Logging / Security / Transactions          │
├────────────────────────────┼────────────────────────────────────────────┤
│ Mandatory                  │ Optional                                  │
└────────────────────────────┴────────────────────────────────────────────┘



**Remember**

Every Spring Boot application uses OOP.

Some Spring Boot applications additionally use AOP.

AOP cannot exist without OOP.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     9. IS AOP A JAVA FEATURE ?                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Another famous interview question.


Question


Is AOP part of Java?


Answer


NO.


Java itself

does NOT provide AOP.


When Java was designed,

it included


Classes

Objects

Inheritance

Interfaces

Exception Handling

Collections

Threads

Generics


but


AOP was NOT included.



Instead,

AOP is implemented

using frameworks

and libraries.



Examples


AspectJ

Spring AOP

JBoss AOP

Guice AOP



Therefore


**AOP is NOT a Java language feature.**

It is implemented

using frameworks.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     10. THEN HOW DOES SPRING PROVIDE AOP ?                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Spring provides AOP

using


                 PROXIES


Don't worry if Proxy is new.


The next chapters will explain

everything about

Dynamic Proxy

JDK Proxy

CGLIB Proxy

Proxy Chain

BeanPostProcessor

Internal Workflow



For now,

remember only one thing.



┌────────────────────────────────────────────────────────────────────────────────────┐
│                                                                                    │
│      Spring NEVER modifies your original class directly.                           │
│                                                                                    │
│      Instead, Spring creates another object called a **Proxy Object**             │
│      and places additional behaviour around your business methods.                 │
│                                                                                    │
└────────────────────────────────────────────────────────────────────────────────────┘



Example



Your Class



                EmployeeService



                     │



Spring Creates



                EmployeeServiceProxy



The proxy decides


Should Logging execute?

Should Transaction start?

Should Security execute?

Then

calls

EmployeeService.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                    PROXY VISUALIZATION                                              ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



Client

   │

   ▼

EmployeeServiceProxy

   │

   ├────────► Logging

   │

   ├────────► Security

   │

   ├────────► Transaction

   │

   ▼

EmployeeService

   │

   ▼

Database



Notice

Client never talks directly

to EmployeeService.

It communicates

with the Proxy.



This single concept explains almost

70% of Spring AOP.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        11. REAL PROJECT EXAMPLE                                    ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine your HRMS project.


EmployeeService


contains


saveEmployee()

updateEmployee()

deleteEmployee()



AttendanceService


contains


markAttendance()

approveAttendance()



LeaveService


contains


applyLeave()

cancelLeave()



Manager says


"We need execution time
for EVERY service method."


Question


Will you modify


saveEmployee()

updateEmployee()

deleteEmployee()

applyLeave()

cancelLeave()

approveAttendance()

markAttendance()


individually?



Absolutely NOT.



Instead,

create one

Performance Aspect.



It automatically executes

for all service methods.



One class.



Hundreds of methods covered.



That is the real power of AOP.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 2 COMPLETE                                      ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



Next Part

✔ Spring AOP vs AspectJ

✔ Advantages

✔ Disadvantages

✔ Where NOT to use AOP

✔ Common Misconceptions

✔ Interview Questions

✔ Summary

*/

/*
╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     12. SPRING AOP vs ASPECTJ                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝

This is one of the **MOST FREQUENTLY ASKED INTERVIEW QUESTIONS**.

Many developers think

        Spring AOP == AspectJ

**This is WRONG.**

Spring AOP and AspectJ are related,

but they are NOT the same.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                           WHAT IS ASPECTJ ?                                        ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


AspectJ is

        **A complete AOP framework**

created by

                Xerox PARC

long before Spring Boot became popular.


It provides

✔ Complete AOP

✔ Compile Time Weaving

✔ Load Time Weaving

✔ Runtime Weaving

✔ Bytecode Modification

✔ Constructor Interception

✔ Field Access Interception

✔ Private Method Interception

✔ Static Method Interception



AspectJ is considered

the **reference implementation**

of Aspect-Oriented Programming.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                        WHAT IS SPRING AOP ?                                         ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Spring AOP is

NOT

a complete implementation of AOP.


Instead,

Spring provides

a lightweight AOP framework

built on top of

                **Proxy Objects**



Instead of modifying bytecode,

Spring simply creates

another object

called

        Proxy

and executes extra logic

before

after

or around

your methods.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     HOW BOTH APPROACHES WORK                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



AspectJ


        Source Code

              │

              ▼

      Bytecode Modified

              │

              ▼

      Final Compiled Class



-----------------------------------------------------------



Spring AOP



        Source Code

              │

              ▼

      Original Bean

              │

              ▼

      Spring Creates Proxy

              │

              ▼

      Client Uses Proxy



**Important**

AspectJ changes the class.

Spring creates another class.



This is the biggest architectural difference.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                       FEATURE COMPARISON                                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝



┌─────────────────────────────┬──────────────────────────┬────────────────────────────┐
│ Feature                     │ Spring AOP              │ AspectJ                    │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Implementation              │ Proxy Based             │ Bytecode Weaving           │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Performance                 │ Good                    │ Faster                     │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Complexity                  │ Easy                    │ Complex                    │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Spring Bean Required        │ Yes                     │ No                         │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Private Method              │ No                      │ Yes                        │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Static Method               │ No                      │ Yes                        │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Constructor                 │ No                      │ Yes                        │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Field Access                │ No                      │ Yes                        │
├─────────────────────────────┼──────────────────────────┼────────────────────────────┤
│ Method Execution            │ Yes                     │ Yes                        │
└─────────────────────────────┴──────────────────────────┴────────────────────────────┘



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                   WHY DOES SPRING USE PROXY INSTEAD OF ASPECTJ ?                    ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Spring Framework was designed with

Simplicity

as one of its major goals.


Using Proxy Objects means

✔ No bytecode modification

✔ No special compiler

✔ No JVM configuration

✔ Easy debugging

✔ Easy integration

✔ Lightweight



For 95% of enterprise applications,

Proxy-based AOP is more than enough.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     REAL LIFE ANALOGY                                               ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Imagine

You hire a Personal Assistant.


Whenever someone wants to meet you,

they first meet

your assistant.


                 Client

                    │

                    ▼

          Personal Assistant

                    │

     Checks Appointment

                    │

     Checks Permission

                    │

     Records Meeting

                    │

                    ▼

                  You



Did anyone modify YOU?

NO.

They simply added

another layer

before reaching you.


Spring Proxy works exactly like this.



AspectJ is different.


Instead of hiring an assistant,

AspectJ changes

YOUR OWN BEHAVIOUR.

It modifies the class itself.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                13. WHERE IS SPRING AOP USED INTERNALLY ?                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Most developers use Spring AOP

without even realizing it.


The following Spring annotations internally use

                **AOP Proxies**



                @Transactional

                        │

                        ▼

                Transaction Aspect



                @Cacheable

                        │

                        ▼

                 Cache Aspect



                @Retryable

                        │

                        ▼

                Retry Aspect



                @Async

                        │

                        ▼

                Async Aspect



Method Security

        @PreAuthorize()

        @PostAuthorize()

also rely on proxy-based interception.



**Remember**

Every time you use

@Transactional

Spring creates a proxy.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                 EXAMPLE : @Transactional                                            ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose


@Service

EmployeeService

{

    saveEmployee()

}



You write


@Transactional

public void saveEmployee(){}



Question

Did Spring change your method?


NO.


Spring created


EmployeeServiceProxy



Flow


Client

   │

   ▼

EmployeeServiceProxy

   │

Begin Transaction

   │

   ▼

saveEmployee()

   │

Commit

   │

Return



This is AOP.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                     14. ADVANTAGES OF AOP                                           ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Using AOP provides many benefits.


✔ Cleaner Code

Business logic becomes very small.


-------------------------------------------------------


✔ Less Code Duplication

Write once.

Execute everywhere.


-------------------------------------------------------


✔ Better Maintainability

Need to change logging?

Modify only one Aspect.


-------------------------------------------------------


✔ Better Reusability

One aspect

can work

for hundreds of classes.


-------------------------------------------------------


✔ Centralized Security

Security logic

stays in one place.


-------------------------------------------------------


✔ Better Monitoring

Execution Time

Memory Usage

API Count

can be monitored centrally.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                      REAL PROJECT BENEFITS                                          ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Suppose

your company has


420 REST APIs.


Manager says


"We need Request Id logging."


Without AOP


Modify

420 APIs.


With AOP


Modify

ONE Aspect.


Done.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                         **Remember**                                                ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


A good Aspect

usually replaces

hundreds

or even thousands

of duplicate code blocks.



╔══════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 3 COMPLETE                                       ║
╚══════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Disadvantages

✔ When NOT to use AOP

✔ Common Misconceptions

✔ Interview Questions

✔ Chapter Summary

✔ Next Chapter Preview

*/