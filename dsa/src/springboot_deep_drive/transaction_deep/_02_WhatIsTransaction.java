package springboot_deep_drive.transaction_deep;

public class _02_WhatIsTransaction {
}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                    **Chapter 02 : What is a Transaction?**                                 │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           WHAT EXACTLY IS A TRANSACTION ?                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In the previous chapter

we learned


Transactions

prevent

data inconsistency.



Question


But what exactly

is

a Transaction?



Simple Definition



                **A Transaction is a group of database operations
                treated as a single logical unit of work.**



Instead of saying



Execute

these SQL queries

individually,



we tell

the database



Treat all of them

as

ONE UNIT.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                              SIMPLE EXAMPLE
══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose

we transfer

₹10,000

from

Account A

to

Account B.



Database Operations



1.

Deduct

₹10,000

from

Account A



2.

Add

₹10,000

to

Account B



Question


Are these

two different operations?



YES.



Should they

be treated

independently?



NO.



They should behave

like

ONE operation.



That

is called

a Transaction.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERNAL DATABASE FLOW                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                  Transfer Money

                         │

                         ▼

               Transaction Starts

                         │

                         ▼

┌────────────────────────────────────────┐
│ Deduct ₹10,000 From Account A          │
└───────────────────┬────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────┐
│ Add ₹10,000 To Account B               │
└───────────────────┬────────────────────┘
                    │
                    ▼
          Everything Successful ?

             ┌──────────┴──────────┐
             │                     │
            Yes                    No
             │                     │
             ▼                     ▼

┌──────────────────────┐   ┌──────────────────────┐
│ COMMIT Changes       │   │ ROLLBACK Everything  │
└──────────────────────┘   └──────────────────────┘



Notice



Database

does NOT

save changes

immediately.



It waits

until

the transaction

finishes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          WHAT IS COMMIT ?                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Commit means



                **Make all changes permanent.**



Example



Update Salary



Insert Salary History



Update Leave Balance



Everything succeeds.



Database executes



COMMIT



Now

all changes

are permanently

stored.



ASCII Flow



Transaction

      │

      ▼

Operation 1 ✔

      │

      ▼

Operation 2 ✔

      │

      ▼

Operation 3 ✔

      │

      ▼

┌──────────────────────┐
│       COMMIT         │
└──────────────────────┘

      │

      ▼

Database Updated



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHAT IS ROLLBACK ?                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Rollback means



                **Undo every change
                made during the transaction.**



Suppose



Operation 1

Success



Operation 2

Success



Operation 3

Fails



Database executes



ROLLBACK



Now



Operation 1

is also undone.



Operation 2

is also undone.



Database returns

to

its previous state.



ASCII Flow



Transaction

      │

      ▼

Operation 1 ✔

      │

      ▼

Operation 2 ✔

      │

      ▼

Operation 3 ❌

      │

      ▼

┌──────────────────────┐
│      ROLLBACK        │
└──────────────────────┘

      │

      ▼

Database Restored



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     REAL HRMS PROJECT EXAMPLE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Employee Resigns.



Application performs



┌────────────────────────────────────────┐
│ Update Employee Status                 │
└───────────────────┬────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────┐
│ Calculate Final Salary                 │
└───────────────────┬────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────┐
│ Generate Settlement Record             │
└───────────────────┬────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────┐
│ Send Data To Finance                   │
└────────────────────────────────────────┘



Suppose

Salary Calculation

fails.



Question


Should

Employee Status

remain updated?



NO.



Entire transaction

must rollback.



Otherwise

HR

and Finance

will have

different data.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     DOES JAVA HANDLE TRANSACTIONS ?                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Question


Does Java

automatically

manage

database transactions?



Answer


NO.



Java

can execute SQL.



But



the Database

controls



BEGIN

COMMIT

ROLLBACK



Spring

simply provides

an easier way

to manage

these operations.



We will see

how

Spring does this

using

@Transactional

in the next chapters.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERVIEW QUESTIONS                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

What is a Transaction?



Answer


A Transaction is

a group of database operations

treated as

one logical unit of work.



------------------------------------------------------------



Q2.

What is Commit?



Answer


Commit makes

all database changes

permanent.



------------------------------------------------------------



Q3.

What is Rollback?



Answer


Rollback

undoes

all changes

made during

the current transaction.



------------------------------------------------------------



Q4.

Who actually performs

Commit

and

Rollback?



Answer


The Database Engine

performs

Commit

and

Rollback.



Spring

coordinates

when they should happen.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                               **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Transaction

        │

        ▼

Multiple Database Operations

        │

        ▼

Everything Successful?

      ┌──────────┴──────────┐
      │                     │
     Yes                    No
      │                     │
      ▼                     ▼

COMMIT               ROLLBACK

      │                     │
      ▼                     ▼

Permanent Data      Undo Everything



One sentence

for interviews



**A Transaction ensures that multiple database operations
behave as one atomic unit, where either all succeed or all fail.**

*/