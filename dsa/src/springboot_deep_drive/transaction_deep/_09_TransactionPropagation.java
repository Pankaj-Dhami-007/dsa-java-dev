package springboot_deep_drive.transaction_deep;

public class _09_TransactionPropagation {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                  **Chapter 09 : Transaction Propagation**                                  │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              WHAT IS PROPAGATION ?                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now

we have seen



@Transactional



working

on a

single method.



Question



What happens

when



one

@Transactional

method



calls



another

@Transactional

method?



Should



Both methods

share

the same

transaction?



OR



Should

Spring

create

a new

transaction?



This decision

is called



                    **Transaction Propagation**



══════════════════════════════════════════════════════════════════════════════════════════════════════
                              SIMPLE DEFINITION
══════════════════════════════════════════════════════════════════════════════════════════════════════


**Transaction Propagation defines what should happen
when a transactional method calls another transactional method.**



It answers

questions like



Should we



✔ Join existing transaction?



✔ Create new transaction?



✔ Execute without transaction?



Spring

provides

Propagation Types

to decide this.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHY DO WE NEED PROPAGATION ?                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose



EmployeeService



calls



PayrollService



ASCII Flow



┌────────────────────┐
│ EmployeeService    │
└─────────┬──────────┘
          │
          ▼
┌────────────────────┐
│ PayrollService     │
└────────────────────┘



Question



EmployeeService

already has

a Transaction.



Should

PayrollService



join it?



Or create

another one?



Propagation

answers

this question.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE INTERNAL FLOW                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



EmployeeService()

        │

        ▼

Spring Proxy

        │

        ▼

Transaction Exists?

        │

   ┌────┴─────┐
   │          │
  Yes         No
   │          │
   ▼          ▼

Propagation Rule Decides

   │

   ▼

Join Existing

OR

Create New

OR

Throw Exception

OR

Run Without Transaction



Notice



Spring

checks



whether

a transaction

already exists.



Then

Propagation

decides

the next step.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     AVAILABLE PROPAGATION TYPES                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌────────────────────────────┬──────────────────────────────────────────────┐
│ Propagation Type           │ Purpose                                      │
├────────────────────────────┼──────────────────────────────────────────────┤
│ REQUIRED                   │ Join existing or create new                  │
├────────────────────────────┼──────────────────────────────────────────────┤
│ REQUIRES_NEW               │ Always create new transaction                │
├────────────────────────────┼──────────────────────────────────────────────┤
│ SUPPORTS                   │ Join if exists, otherwise run normally       │
├────────────────────────────┼──────────────────────────────────────────────┤
│ NOT_SUPPORTED              │ Execute without transaction                  │
├────────────────────────────┼──────────────────────────────────────────────┤
│ MANDATORY                  │ Existing transaction required                │
├────────────────────────────┼──────────────────────────────────────────────┤
│ NEVER                      │ Fail if transaction exists                   │
├────────────────────────────┼──────────────────────────────────────────────┤
│ NESTED                     │ Nested transaction (Savepoint)               │
└────────────────────────────┴──────────────────────────────────────────────┘



Don't worry.



We will study

each one

individually.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                         REAL HRMS EXAMPLE
══════════════════════════════════════════════════════════════════════════════════════════════════════


Employee Joining



EmployeeService



performs



┌──────────────────────────────┐
│ Save Employee                │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ PayrollService               │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ EmailService                 │
└──────────────────────────────┘



Question



Should



PayrollService



share

EmployeeService's

transaction?



Should



EmailService

have

its own

transaction?



Propagation

decides

these behaviours.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         MOST IMPORTANT FACT                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


**Propagation only matters
when one transactional method
calls another transactional method.**



If there is

only

one method,



Propagation

doesn't change

anything.



This is a very

common

interview question.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERVIEW QUESTIONS                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



**Q1. What is Transaction Propagation?**



Answer



It defines

how a transactional method

behaves

when another

transaction

already exists.



------------------------------------------------------------



**Q2. When does Propagation matter?**



Answer



Only when

one transactional method

calls another

transactional method.



------------------------------------------------------------



**Q3. Which Propagation is the default?**



Answer



Propagation.REQUIRED



This is

Spring's

default behaviour.



------------------------------------------------------------



**Q4. How many propagation types are available?**



Answer



Spring provides

seven

propagation types.



The most commonly used are



✔ REQUIRED



✔ REQUIRES_NEW



══════════════════════════════════════════════════════════════════════════════════════════════════════

                               **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Transaction Exists?

        │

        ▼

Propagation

decides



Join Existing



OR



Create New



OR



Run Without Transaction



That is

the entire purpose

of

Transaction Propagation.

*/