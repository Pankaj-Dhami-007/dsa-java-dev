package springboot_deep_drive.transaction_deep;

public class _05_InternalWorkingOfTransactional {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │              **Chapter 05 : Internal Working of @Transactional**                           │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  HOW DOES @Transactional WORK INTERNALLY ?                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now

we know



@Transactional



automatically manages

transactions.



Question



How?



Who starts

the transaction?



Who commits it?



Who rolls it back?



Let's understand

the complete

internal flow.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                     COMPLETE INTERNAL EXECUTION FLOW
══════════════════════════════════════════════════════════════════════════════════════════════════════



                     Client Request

                           │

                           ▼

┌──────────────────────────────────────────────┐
│        Spring AOP Proxy                      │
└───────────────────┬──────────────────────────┘
                    │
                    ▼
┌──────────────────────────────────────────────┐
│ Transaction Interceptor                      │
└───────────────────┬──────────────────────────┘
                    │
                    ▼
┌──────────────────────────────────────────────┐
│ PlatformTransactionManager                   │
│ begin()                                      │
└───────────────────┬──────────────────────────┘
                    │
                    ▼
┌──────────────────────────────────────────────┐
│ Your Service Method                          │
└───────────────────┬──────────────────────────┘
                    │
         ┌──────────┴──────────┐
         │                     │
      Success              Exception
         │                     │
         ▼                     ▼
┌─────────────────┐    ┌────────────────────┐
│ commit()        │    │ rollback()         │
└─────────────────┘    └────────────────────┘
         │                     │
         └──────────┬──────────┘
                    ▼
             Return Response



This is

what actually happens

inside Spring.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      STEP 1 : CLIENT CALLS SERVICE                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Controller



calls



salaryService.generateSalary();



Question



Does Controller

call

SalaryService

directly?



No.



Because

SalaryService

has



@Transactional.



Spring injects

a Proxy.



Flow



Controller

      │

      ▼

SalaryService Proxy



Not

Original Service.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  STEP 2 : TRANSACTION INTERCEPTOR                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



The Proxy

detects



@Transactional



Instead of

calling

the method

immediately,



it first

calls



Transaction Interceptor.



Think of it

like



A Security Guard.



Before entering

the building,



everyone

must pass

through

security.



Similarly,



before entering

your Service,



every request

passes through

Transaction Interceptor.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  STEP 3 : TRANSACTION MANAGER                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Transaction Interceptor

asks



PlatformTransactionManager



to start

a transaction.



ASCII Flow



Proxy

     │

     ▼

Transaction Interceptor

     │

     ▼

PlatformTransactionManager

     │

     ▼

BEGIN Transaction



Notice



Spring itself

doesn't execute SQL.



It coordinates

the transaction.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    STEP 4 : BUSINESS METHOD EXECUTES                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Now

Spring calls



generateSalary()



Inside it



Update Salary



↓

Insert History



↓

Update Payroll



Business logic

executes

normally.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  STEP 5 : SUCCESS PATH                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Suppose

everything succeeds.



Flow



Business Method

        │

        ▼

Transaction Manager

        │

        ▼

commit()



Database

makes

all changes

permanent.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    STEP 6 : EXCEPTION PATH                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Suppose



Insert Salary History



throws

an Exception.



Flow



Business Method

        │

        ▼

Exception

        │

        ▼

Transaction Manager

        │

        ▼

rollback()



Database

undoes

every change.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHO DOES WHAT ?                                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────┬───────────────────────────────────────────────────┐
│ Component                │ Responsibility                                   │
├──────────────────────────┼───────────────────────────────────────────────────┤
│ Controller               │ Calls Service                                    │
├──────────────────────────┼───────────────────────────────────────────────────┤
│ Spring Proxy             │ Intercepts Method Call                           │
├──────────────────────────┼───────────────────────────────────────────────────┤
│ TransactionInterceptor   │ Detects @Transactional                           │
├──────────────────────────┼───────────────────────────────────────────────────┤
│ PlatformTransactionManager│ Starts / Commits / Rolls Back Transaction       │
├──────────────────────────┼───────────────────────────────────────────────────┤
│ Repository               │ Executes SQL                                     │
├──────────────────────────┼───────────────────────────────────────────────────┤
│ Database                 │ Performs Actual Commit / Rollback                │
└──────────────────────────┴───────────────────────────────────────────────────┘



Each component

has

one responsibility.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      REAL HRMS PROJECT FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Employee clicks

Generate Salary

          │
          ▼
┌────────────────────────────┐
│ SalaryController           │
└─────────────┬──────────────┘
              │
              ▼
┌────────────────────────────┐
│ SalaryService Proxy        │
└─────────────┬──────────────┘
              │
              ▼
┌────────────────────────────┐
│ Transaction Interceptor    │
└─────────────┬──────────────┘
              │
              ▼
┌────────────────────────────┐
│ Transaction Manager        │
│ BEGIN                      │
└─────────────┬──────────────┘
              │
              ▼
┌────────────────────────────┐
│ SalaryService              │
└─────────────┬──────────────┘
              │
      Success │ Exception
              │
      ┌───────┴─────────┐
      ▼                 ▼
┌──────────────┐  ┌──────────────┐
│ COMMIT       │  │ ROLLBACK     │
└──────────────┘  └──────────────┘



Everything

is automatic.



Developer

only writes



@Transactional



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



**Q1. How does @Transactional work internally?**



Answer



Spring AOP creates

a Proxy.

The Proxy

uses

TransactionInterceptor,

which asks

PlatformTransactionManager

to begin,

commit,

or rollback

the transaction.



------------------------------------------------------------



**Q2. Who actually starts the transaction?**



Answer



PlatformTransactionManager

starts

the transaction.



------------------------------------------------------------



**Q3. Does @Transactional execute SQL?**



Answer



No.



Repositories

execute SQL.



@Transactional

only manages

the transaction

around

those operations.



------------------------------------------------------------



**Q4. Is @Transactional implemented using AOP?**



Answer



Yes.



Spring uses

Proxy-based AOP

to intercept

method calls

and apply

transaction management.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                             **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Client

        │

        ▼

Spring Proxy

        │

        ▼

Transaction Interceptor

        │

        ▼

PlatformTransactionManager

        │

        ▼

Business Method

        │

        ▼

Commit / Rollback



This is the

most important

internal flow

to remember

for Spring Transaction

interview questions.

*/