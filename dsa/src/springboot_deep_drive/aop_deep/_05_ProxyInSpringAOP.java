package springboot_deep_drive.aop_deep;

public class _05_ProxyInSpringAOP {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │                     **SPRING BOOT DEEP DIVE - AOP SERIES**                                │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                      **Chapter 05 : Proxy In Spring AOP**                                 │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                                    ║
║                     SPRING BOOT DEEP DIVE ── PROXY IN SPRING AOP                                   ║
║                                                                                                    ║
║                           Chapter 05 : PROXY IN SPRING AOP                                         ║
║                                                                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               1. WHY DO WE NEED THIS CHAPTER ?                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In the previous chapter we learned


Application Starts

        │

        ▼

Spring Creates Bean

        │

        ▼

Spring Creates Proxy

        │

        ▼

Stores Proxy inside IOC Container



Immediately one question comes into mind.


                    **What exactly is this Proxy?**


Is it

• A Java Class?

• A Spring Class?

• A Design Pattern?

• A Copy of our Object?

• Something generated automatically?



This chapter answers all these questions.



┌──────────────────────────────────────────────────────────────────────────────────────────────────┐
│                               **After completing this chapter**                                  │
├──────────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                                  │
│  ✔ What is a Proxy?                                                                              │
│  ✔ Why does Spring use Proxies?                                                                  │
│  ✔ Static Proxy                                                                                  │
│  ✔ Dynamic Proxy (Concept)                                                                       │
│  ✔ How Proxy intercepts method calls                                                             │
│  ✔ Real Project Examples                                                                         │
│                                                                                                  │
└──────────────────────────────────────────────────────────────────────────────────────────────────┘




╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                  2. WHAT IS A PROXY ?                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Official Definition


**A Proxy is an object that represents another object
and controls access to that object.**



Simple Definition


Think of a Proxy as


                    **A Middleman**


Instead of talking directly

to the original object,

you first communicate

with another object.


That object

may

✔ Check Security

✔ Perform Logging

✔ Start Transaction

✔ Validate Request

✔ Cache Data

✔ Measure Execution Time


Finally,

it forwards the request

to the original object.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Simple Diagram

══════════════════════════════════════════════════════════════════════════════════════════════════════


                    Without Proxy


             Client

               │

               ▼

      EmployeeService

               │

               ▼

            Database




                    With Proxy



             Client

               │

               ▼

     EmployeeServiceProxy

               │

      ┌────────┼────────┐

      │        │        │

      ▼        ▼        ▼

 Logging  Security  Transaction

               │

               ▼

      EmployeeService

               │

               ▼

            Database



Notice carefully.


Client

never talks

directly

to EmployeeService.



Everything passes through

the Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              3. WHY DO WE NEED A PROXY ?                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

EmployeeService


contains


saveEmployee()



Without Proxy


public void saveEmployee(){

      // Business Logic

}



Now manager says


"We need logging."



Developer modifies



public void saveEmployee(){

     log.info(...);

     // Business Logic

}



After one week


"We also need execution time."



Developer modifies again.



public void saveEmployee(){

     log.info(...);

     long start=...

     // Business Logic

}



Next week


"We need Security."



Again modified.



Question


How many times

will this method

keep changing?



Every new requirement

forces us

to modify

business logic.



This violates


                    **Open Closed Principle**


Business classes

should remain focused

only on

business logic.



Proxy solves

this problem.




╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            4. REAL LIFE ANALOGY                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

you want to meet

the CEO

of a company.



Can you directly

walk into

the CEO's cabin?



NO.



You first meet


                    Receptionist



The receptionist


✔ Checks your appointment

✔ Verifies your identity

✔ Confirms meeting schedule

✔ Notifies CEO



Only after

all these checks,

you are allowed

to meet the CEO.



ASCII Diagram



                   Visitor

                      │

                      ▼

      ┌────────────────────────────────┐
      │         Receptionist           │
      ├────────────────────────────────┤
      │                                │
      │ ✔ Appointment Check            │
      │ ✔ Identity Verification        │
      │ ✔ Inform CEO                   │
      │                                │
      └──────────────┬─────────────────┘
                     │
                     ▼
           ┌───────────────────┐
           │        CEO        │
           └───────────────────┘



CEO

=

Target Object



Receptionist

=

Proxy



This is exactly

how Spring Proxy works.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        5. HRMS PROJECT EXAMPLE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose your HRMS project contains



AttendanceService



public void markAttendance(){


      // Save Attendance


}



Manager says


Every attendance API

should


✔ Log execution

✔ Check Security

✔ Measure Time

✔ Handle Transaction



Question


Should we modify

markAttendance()

every time?



NO.



Instead,


Spring creates



AttendanceServiceProxy



ASCII Flow



Controller

      │

      ▼

┌────────────────────────────────────┐
│ AttendanceServiceProxy             │
├────────────────────────────────────┤
│                                    │
│ Log Request                        │
│                                    │
│ Security Check                     │
│                                    │
│ Start Transaction                  │
│                                    │
└──────────────┬─────────────────────┘
               │
               ▼
     AttendanceService
               │
               ▼
         Save Attendance
               │
               ▼
┌────────────────────────────────────┐
│ Commit Transaction                 │
│ Log Execution Time                 │
└────────────────────────────────────┘



Notice


AttendanceService

contains

ONLY

business logic.



Everything else

is handled

by the Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                  **Remember**                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


**Proxy is NOT your original object.**

It is another object

created to

control access

to the original object.



Spring returns

the Proxy

instead of

the original Bean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 1 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Static Proxy

✔ Coding Static Proxy

✔ Problems with Static Proxy

✔ Why Java introduced Dynamic Proxy

✔ Why Spring never uses Static Proxy

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               6. STATIC PROXY                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Now let's implement

a Proxy

without using

Spring Boot.



Question


Can we create

our own Proxy?



Answer


YES.



Long before

Spring AOP existed,

developers manually

wrote Proxy classes.



This is called

                **Static Proxy**



══════════════════════════════════════════════════════════════════════════════════════════════════════

Real Life Understanding

══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose

EmployeeService

already exists.



Instead of calling

EmployeeService directly,

we create



EmployeeServiceProxy



EmployeeServiceProxy

internally calls

EmployeeService.



Exactly

like Spring.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               STEP 1 : ORIGINAL SERVICE                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


EmployeeService.java


public class EmployeeService {

    public void saveEmployee() {

        System.out.println("Employee Saved Successfully.");

    }

}



This is

our original

business class.



Notice

No Logging

No Security

No Transaction



Only

Business Logic.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Execution Flow

══════════════════════════════════════════════════════════════════════════════════════════════════════



              Client

                 │

                 ▼

      EmployeeService

                 │

                 ▼

        saveEmployee()



Very simple.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              STEP 2 : CREATE PROXY CLASS                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



EmployeeServiceProxy.java



public class EmployeeServiceProxy {

    private EmployeeService employeeService = new EmployeeService();

    public void saveEmployee() {

        System.out.println("Logging Started...");

        employeeService.saveEmployee();

        System.out.println("Logging Completed...");

    }

}



Let's understand

every line.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Line 1

══════════════════════════════════════════════════════════════════════════════════════════════════════


private EmployeeService employeeService = new EmployeeService();


Proxy

contains

the original object.



Proxy

does NOT replace

EmployeeService.



Proxy

simply delegates

the call

to EmployeeService.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Line 2

══════════════════════════════════════════════════════════════════════════════════════════════════════


System.out.println("Logging Started...");


Before

calling

EmployeeService,

Proxy

executes Logging.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Line 3

══════════════════════════════════════════════════════════════════════════════════════════════════════


employeeService.saveEmployee();


Now

Proxy

forwards

the request

to the original object.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Line 4

══════════════════════════════════════════════════════════════════════════════════════════════════════


System.out.println("Logging Completed...");


After

business logic,

Proxy again

executes

extra functionality.



Notice


Business Logic

never changed.



Only

Proxy changed.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           COMPLETE EXECUTION FLOW                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                Client

                  │

                  ▼

      ┌──────────────────────────────┐
      │ EmployeeServiceProxy         │
      ├──────────────────────────────┤
      │                              │
      │ Logging Started              │
      │                              │
      └──────────────┬───────────────┘
                     │
                     ▼

          EmployeeService

                     │

                     ▼

      Employee Saved Successfully

                     │

                     ▼

      ┌──────────────────────────────┐
      │ Logging Completed            │
      └──────────────────────────────┘



Output



Logging Started...

Employee Saved Successfully.

Logging Completed...



Exactly

what Spring Proxy does.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                STEP 3 : MAIN METHOD                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



public class Main {

    public static void main(String[] args) {

        EmployeeServiceProxy proxy = new EmployeeServiceProxy();

        proxy.saveEmployee();

    }

}



Notice carefully.



Client

never creates

EmployeeService.



Client creates



EmployeeServiceProxy.



Proxy internally

creates

EmployeeService.



This is

the entire idea

behind Proxy Pattern.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            VISUALIZE THE COMPLETE FLOW                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                    Client

                      │

                      ▼

       ┌─────────────────────────────────────┐
       │ EmployeeServiceProxy                │
       ├─────────────────────────────────────┤
       │                                     │
       │  Logging Started                    │
       │                                     │
       │  employeeService.saveEmployee()     │
       │                                     │
       │  Logging Completed                  │
       │                                     │
       └────────────────┬────────────────────┘
                        │
                        ▼
          ┌───────────────────────────────┐
          │ EmployeeService               │
          ├───────────────────────────────┤
          │ saveEmployee()                │
          └───────────────────────────────┘



This

is called

Static Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             WHY IS IT CALLED STATIC PROXY ?                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Question


Why "Static"?



Answer


Because

we manually wrote

EmployeeServiceProxy.



The class exists

at compile time.



Nothing is generated

automatically.



We wrote



EmployeeService.java



and



EmployeeServiceProxy.java



manually.



Therefore,

it is called

Static Proxy.



══════════════════════════════════════════════════════════════════════════════════════════════════════

**Remember**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Static Proxy

=

Developer writes

Proxy class manually.



Spring AOP

does NOT use

Static Proxy.



Spring generates

Proxy Objects

automatically.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                 PART 2 COMPLETE                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Problems with Static Proxy

✔ Why Static Proxy is not scalable

✔ Dynamic Proxy Concept

✔ Why Spring generates Proxy automatically

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         7. PROBLEMS WITH STATIC PROXY                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


So far,

Static Proxy looks amazing.


Question

If Static Proxy works,

then why did Java introduce

Dynamic Proxy?

Why does Spring AOP

generate Proxy automatically?

Why doesn't Spring simply create

EmployeeServiceProxy.java

for every service?



Let's understand the limitations.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Problem 1 : Too Many Proxy Classes

══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose your project contains



EmployeeService

AttendanceService

PayrollService

LeaveService

HolidayService

DepartmentService

NotificationService

CustomerService

OrderService

PaymentService



Question


How many Proxy classes

must we write?



Answer


Exactly one Proxy

for every Service.



Example



EmployeeService
        │
        ▼
EmployeeServiceProxy



AttendanceService
        │
        ▼
AttendanceServiceProxy



PayrollService
        │
        ▼
PayrollServiceProxy



LeaveService
        │
        ▼
LeaveServiceProxy



...



Imagine

your company has



250 Services.



You will have to write



250 Proxy classes.



Obviously,

this is not practical.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         VISUAL REPRESENTATION                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



        Original Classes                    Proxy Classes


 EmployeeService                EmployeeServiceProxy

 AttendanceService              AttendanceServiceProxy

 LeaveService                   LeaveServiceProxy

 PayrollService                 PayrollServiceProxy

 HolidayService                 HolidayServiceProxy

 CustomerService                CustomerServiceProxy

 OrderService                   OrderServiceProxy

 PaymentService                 PaymentServiceProxy



Project Size ↑

↓

Proxy Classes ↑

↓

Maintenance ↑



══════════════════════════════════════════════════════════════════════════════════════════════════════

Problem 2 : Duplicate Code

══════════════════════════════════════════════════════════════════════════════════════════════════════


Look at

EmployeeServiceProxy



public void saveEmployee(){

    log();

    employeeService.saveEmployee();

}



Now

AttendanceServiceProxy



public void markAttendance(){

    log();

    attendanceService.markAttendance();

}



PayrollServiceProxy



public void generateSalary(){

    log();

    payrollService.generateSalary();

}



Question


What is different?



Only

one line.



Everything else

is duplicated.



Logging

exists

inside every Proxy.



This violates


                **DRY Principle**



(Don't Repeat Yourself)



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          PROBLEM 3 : MAINTENANCE                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose manager says


"Logging format has changed."



Old



Logging Started...



New



API Started...

User Id

Execution Time

Correlation Id

IP Address



Question


How many Proxy classes

must be modified?



EmployeeServiceProxy

AttendanceServiceProxy

PayrollServiceProxy

LeaveServiceProxy

CustomerServiceProxy

OrderServiceProxy

PaymentServiceProxy

...



Potentially

hundreds.



This becomes

very difficult

to maintain.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Problem 4 : Interface Changes

══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose

EmployeeService

contains



saveEmployee()

updateEmployee()



Tomorrow

developer adds



deleteEmployee()



Question


Who will update

EmployeeServiceProxy?



Developer

must manually

add



deleteEmployee()



Otherwise,

Proxy

becomes incomplete.



Every new method

requires

Proxy modification.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL PROJECT EXAMPLE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

your HRMS project.


It contains



35 Controllers

↓

48 Services

↓

22 Repositories



Suppose

every Service

needs



Logging

Transaction

Execution Time

Audit



Using Static Proxy,

you must create



48 Proxy classes.



Tomorrow,

another

15 Services

are added.



Again,

15 new Proxy classes.



Imagine maintaining this

for

5 years.



Not practical.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      SUMMARY OF STATIC PROXY PROBLEMS                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌────────────────────────────┬─────────────────────────────────────────────┐
│ Problem                    │ Result                                      │
├────────────────────────────┼─────────────────────────────────────────────┤
│ One Proxy per Class        │ Huge number of classes                      │
├────────────────────────────┼─────────────────────────────────────────────┤
│ Duplicate Logic            │ Violates DRY                               │
├────────────────────────────┼─────────────────────────────────────────────┤
│ Manual Coding              │ Time consuming                             │
├────────────────────────────┼─────────────────────────────────────────────┤
│ Interface Changes          │ Proxy must also change                     │
├────────────────────────────┼─────────────────────────────────────────────┤
│ Maintenance                │ Difficult                                  │
└────────────────────────────┴─────────────────────────────────────────────┘



══════════════════════════════════════════════════════════════════════════════════════════════════════

Question

══════════════════════════════════════════════════════════════════════════════════════════════════════


How can we solve

all these problems?



Answer



Instead of

developers writing

Proxy classes,



Java

will generate

Proxy classes

automatically

at runtime.



This is called



                **Dynamic Proxy**



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         STATIC PROXY vs DYNAMIC PROXY                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬───────────────────────────────────────────────┐
│ Static Proxy                 │ Dynamic Proxy                                 │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Written by Developer         │ Generated Automatically                       │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ One Proxy per Class          │ One Mechanism for Many Classes                │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Manual Maintenance           │ Automatic                                     │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ High Code Duplication        │ Minimal Duplication                           │
├──────────────────────────────┼───────────────────────────────────────────────┤
│ Difficult to Scale           │ Highly Scalable                               │
└──────────────────────────────┴───────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             **Remember**                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Static Proxy

is excellent

for understanding

the Proxy Pattern.



But

for enterprise applications,

it is

NOT practical.



This is exactly why

Java introduced

Dynamic Proxy,

and why

Spring AOP

builds on

Dynamic Proxy technologies.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                 PART 3 COMPLETE                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ What is Dynamic Proxy?

✔ How Java Generates Proxy at Runtime

✔ How Spring Uses Dynamic Proxy

✔ JDK Dynamic Proxy vs CGLIB (Introduction)

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           8. WHAT IS A DYNAMIC PROXY ?                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


We have already seen

                Static Proxy


Now the biggest question is


If developers

don't write

Proxy classes,



then


Who creates them?



Answer


Java Runtime

or

Spring Framework



automatically creates

Proxy classes

while the application

is running.



This is called


                    **Dynamic Proxy**



══════════════════════════════════════════════════════════════════════════════════════════════════════

Official Definition

══════════════════════════════════════════════════════════════════════════════════════════════════════


**A Dynamic Proxy is a Proxy Object generated automatically
at runtime instead of being written manually by the developer.**



Don't worry about

how Java creates it.

First,

understand

the concept.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              STATIC vs DYNAMIC THINKING                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Static Proxy


Developer writes


EmployeeService

EmployeeServiceProxy

AttendanceService

AttendanceServiceProxy

PayrollService

PayrollServiceProxy



Developer is responsible

for maintaining

every Proxy.



------------------------------------------------------------



Dynamic Proxy


Developer writes


EmployeeService

AttendanceService

PayrollService



That's all.



Java Runtime

creates



EmployeeServiceProxy

AttendanceServiceProxy

PayrollServiceProxy



automatically.



Developer

never writes

these classes.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Visual Comparison

══════════════════════════════════════════════════════════════════════════════════════════════════════



                    STATIC PROXY


Developer

      │

      ▼

Writes

EmployeeService

      │

      ▼

Writes

EmployeeServiceProxy

      │

      ▼

Application Runs




                    DYNAMIC PROXY



Developer

      │

      ▼

Writes

EmployeeService

      │

      ▼

Application Starts

      │

      ▼

Java Generates

EmployeeServiceProxy

Automatically

      │

      ▼

Application Runs



Notice

Developer never writes

Proxy classes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHY IS DYNAMIC PROXY BETTER ?                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

your HRMS project

contains



100 Services.



Question


How many Proxy classes

will you write?



Answer


ZERO.



Spring

will create

100 Proxy Objects

automatically.



Imagine tomorrow

another

50 Services

are added.



Still,

you write


ZERO

Proxy classes.



Spring

handles

everything.



This is why

enterprise applications

always use

Dynamic Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             REAL LIFE ANALOGY                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

Amazon.



Suppose

every customer

requires

a Customer Care Executive.



Using Static Proxy


Company hires

one executive

for every customer.



Impossible.



Instead,


Customer Care Software

automatically assigns

an available executive.



Customer

doesn't know

who handled

the request.



Similarly,

Spring

automatically creates

Proxy Objects

whenever required.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         HOW SPRING USES DYNAMIC PROXY                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose


@Service

public class AttendanceService{


    public void markAttendance(){


    }

}



Developer writes

only this.



Question


Where is

AttendanceServiceProxy?



Answer


There is

NO

AttendanceServiceProxy.java

inside your project.



Spring creates it

at runtime.



ASCII Diagram



                  Developer Code



        AttendanceService.java

                   │

                   ▼

         Spring Application Starts

                   │

                   ▼

        Spring Generates Proxy

                   │

                   ▼

     AttendanceServiceProxy

          (Runtime Generated)

                   │

                   ▼

          Stored inside IOC



This Proxy

exists only

while the application

is running.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Important Observation

══════════════════════════════════════════════════════════════════════════════════════════════════════


Open your project.



Can you find


AttendanceServiceProxy.java ?



NO.



Because

Spring generates it

in memory.



Not

inside

your source code.



This is why

it is called

Dynamic Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           COMPLETE EXECUTION FLOW                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                     Developer

                         │

                         ▼

             Writes Service Class

                         │

                         ▼

         Spring Application Starts

                         │

                         ▼

         Bean Created By Spring

                         │

                         ▼

      Is AOP Required For This Bean?

                 ┌────────┴────────┐
                 │                 │
                YES               NO
                 │                 │
                 ▼                 ▼

      Generate Proxy         Return Original Bean

                 │

                 ▼

       Store Proxy In IOC Container

                 │

                 ▼

          Inject Proxy Everywhere



Notice carefully.



Spring

does NOT

generate Proxy

for every Bean.



Only

eligible Beans

receive

Dynamic Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       HOW DOES THE CLIENT SEE IT ?                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

Controller


calls



attendanceService.markAttendance();



Question


Does Controller know

whether

AttendanceService

is a Proxy?



NO.



Controller simply writes



attendanceService.markAttendance();



Internally,

Spring has already

injected

the Proxy.



Controller

never knows

whether

it received


Original Bean

or

Proxy Bean.



This is one of

the biggest strengths

of Spring AOP.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           STATIC vs DYNAMIC PROXY                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬─────────────────────────────────────────────────────┐
│ Static Proxy                 │ Dynamic Proxy                                       │
├──────────────────────────────┼─────────────────────────────────────────────────────┤
│ Written manually             │ Generated automatically                             │
├──────────────────────────────┼─────────────────────────────────────────────────────┤
│ Exists in source code        │ Exists only at runtime                              │
├──────────────────────────────┼─────────────────────────────────────────────────────┤
│ One Proxy per class          │ One framework can generate many proxies             │
├──────────────────────────────┼─────────────────────────────────────────────────────┤
│ High maintenance             │ Very low maintenance                                │
├──────────────────────────────┼─────────────────────────────────────────────────────┤
│ Used for learning            │ Used by Spring Framework                            │
└──────────────────────────────┴─────────────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              **Remember**                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


**Dynamic Proxy does NOT mean
that your business class changes.**


EmployeeService

remains

EmployeeService.



Spring simply creates

another object

that sits

between

Client

and

EmployeeService.



That object

is the Dynamic Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               INTERVIEW QUESTIONS                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What is a Dynamic Proxy?


Answer

A Dynamic Proxy is a Proxy Object
generated automatically at runtime
instead of being written manually.



-----------------------------------------------------------


Q2.

Do we write Dynamic Proxy classes?


No.

Java or Spring

generates them

automatically.



-----------------------------------------------------------


Q3.

Can you find

EmployeeServiceProxy.java

inside a Spring Boot project?


No.

It exists

only at runtime.



-----------------------------------------------------------


Q4.

Why does Spring use Dynamic Proxy?


To avoid

manual Proxy creation,

reduce duplicate code,

and automatically apply

cross-cutting concerns.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                  PART 4 COMPLETE                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ JDK Dynamic Proxy

✔ CGLIB Proxy

✔ Which one does Spring choose?

✔ Real Interview Questions

✔ Complete Chapter Summary

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     9. HOW DOES SPRING CREATE DYNAMIC PROXIES ?                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Now we know

✔ What is Proxy

✔ Static Proxy

✔ Dynamic Proxy



Question


How does Spring actually create

Dynamic Proxies?



The answer is

Spring has two techniques.



                    Spring Proxy

                          │

          ┌───────────────┴───────────────┐

          │                               │

          ▼                               ▼

   JDK Dynamic Proxy                 CGLIB Proxy



Don't worry.

Both solve the same problem.

They only differ

in how they create the Proxy.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Technique 1 : JDK Dynamic Proxy

══════════════════════════════════════════════════════════════════════════════════════════════════════


JDK Dynamic Proxy

works only

when your class

implements

an interface.



Example



public interface EmployeeService {

    void saveEmployee();

}



@Service
public class EmployeeServiceImpl
        implements EmployeeService{

}



Since

EmployeeServiceImpl

implements

EmployeeService,

Spring can create

a JDK Dynamic Proxy.



ASCII Diagram



             EmployeeService (Interface)

                       ▲

                       │ implements

                       │

        EmployeeServiceImpl

                       ▲

                       │

                JDK Proxy



Think of it like this.



Proxy

also implements

the same interface.



So whenever

someone calls



employeeService.saveEmployee()



they are actually calling

the Proxy first.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Technique 2 : CGLIB Proxy

══════════════════════════════════════════════════════════════════════════════════════════════════════


Question


What if

there is

NO Interface?



Example



@Service

public class AttendanceService{

    public void markAttendance(){

    }

}



There is

no interface.



Can JDK Proxy work?



NO.



Spring uses

CGLIB Proxy.



ASCII Diagram



      AttendanceService

             ▲

             │ extends

             │

AttendanceService$$SpringProxy



Instead of

implementing an interface,

CGLIB creates

a subclass

of your class.



You don't write

this subclass.

Spring generates it

automatically.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     HOW DOES SPRING DECIDE ?                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Spring follows

a very simple decision.



                    Bean

                     │

                     ▼

        Does it implement Interface?

             ┌────────┴─────────┐

             │                  │

            YES                NO

             │                  │

             ▼                  ▼

      JDK Dynamic Proxy      CGLIB Proxy



That's it.



No complicated logic.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



EmployeeServiceImpl

implements EmployeeService


↓

JDK Dynamic Proxy



--------------------------------------------------



AttendanceService

(No Interface)


↓

CGLIB Proxy



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHICH ONE SHOULD WE USE ?                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


As developers,

we usually

don't decide.



Spring decides

automatically.



If needed,

Spring can also

be configured

to always use

CGLIB.



But in most projects,

the default behavior

works perfectly.



**Interview Point**


Don't say


"I always use JDK Proxy."


or


"I always use CGLIB."


Correct answer is


**Spring automatically chooses the appropriate proxy mechanism based on the target class configuration.**



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    COMPLETE REQUEST FLOW                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                    HTTP Request

                         │

                         ▼

                  Controller

                         │

                         ▼

               IOC Container

                         │

                         ▼

          Returns Proxy Bean

                         │

                         ▼

        Logging / Security / Transaction

                         │

                         ▼

             Original Service

                         │

                         ▼

                   Database

                         │

                         ▼

                 Return Result

                         │

                         ▼

                    Controller

                         │

                         ▼

                  HTTP Response



Notice



Controller

never knows

whether

it received

JDK Proxy

or

CGLIB Proxy.



It simply calls



employeeService.saveEmployee();



Spring handles

everything.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    JDK PROXY vs CGLIB (Interview Table)                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌─────────────────────────────┬──────────────────────────────────────────────┐
│ JDK Dynamic Proxy           │ CGLIB Proxy                                 │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Requires Interface          │ No Interface Required                       │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Implements Interface        │ Extends Target Class                        │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Built into Java             │ Library used by Spring                      │
├─────────────────────────────┼──────────────────────────────────────────────┤
│ Cannot proxy class only     │ Can proxy normal classes                    │
└─────────────────────────────┴──────────────────────────────────────────────┘



**Remember**

You don't need to memorize
every difference.

Just remember

Interface

↓

JDK Proxy


No Interface

↓

CGLIB



This is enough

for most interviews.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       COMMON INTERVIEW QUESTIONS                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

How many proxy mechanisms
does Spring AOP use?


Answer

Two.

JDK Dynamic Proxy

and

CGLIB Proxy.



----------------------------------------------------------


Q2.

When does Spring use
JDK Dynamic Proxy?


Answer

When the target class
implements an interface.



----------------------------------------------------------


Q3.

When does Spring use
CGLIB?


Answer

When the target class
does not implement
an interface.



----------------------------------------------------------


Q4.

Should developers
manually create proxies?


No.

Spring automatically
creates them.



----------------------------------------------------------


Q5.

Does the Controller know
whether it received
a proxy?


No.

The Controller simply
uses the injected bean.

Spring manages
everything behind the scenes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           CHAPTER 05 SUMMARY                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In this chapter we learned


✔ What is Proxy?

✔ Why Proxy is needed?

✔ Static Proxy

✔ Problems with Static Proxy

✔ Dynamic Proxy

✔ JDK Dynamic Proxy

✔ CGLIB Proxy

✔ How Spring chooses a Proxy

✔ Complete Request Flow



**Most Important Takeaway**


As Spring Boot developers,

we don't write
Proxy classes.

We simply use

@Transactional

@Async

@Cacheable

@Aspect

and Spring creates
the appropriate Proxy
automatically.

This is the foundation
of Spring AOP.

*/

