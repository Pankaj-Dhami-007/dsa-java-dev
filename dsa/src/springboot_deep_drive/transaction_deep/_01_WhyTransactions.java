package springboot_deep_drive.transaction_deep;

public class _01_WhyTransactions {
}

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                                    ║
║                    SPRING BOOT DEEP DIVE ── TRANSACTION MANAGEMENT                                ║
║                                                                                                    ║
║                         Chapter 01 : WHY DO WE NEED TRANSACTIONS ?                                ║
║                                                                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Before learning

@Transactional

we need to understand

one important question.



Question



Why do Transactions

exist?



Why didn't

Java

or

Database

simply execute

our SQL queries

one by one?



Let's understand

using

a real project.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                                REAL HRMS EXAMPLE
══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose

Employee Salary

is generated.



To complete

one request,

our application performs



Step 1

Update Salary Table



↓

Step 2

Insert Salary History



↓

Step 3

Update Employee Balance



↓

Step 4

Generate Audit Record



Question


What happens

if

Step 3 fails?



Should

Step 1

and

Step 2

remain

inside

the database?



NO.



That creates

inconsistent data.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                              WITHOUT TRANSACTION
══════════════════════════════════════════════════════════════════════════════════════════════════════



┌──────────────────────────┐
│ Generate Salary Request  │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Update Salary Table      │
│          ✅ Success       │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Insert Salary History    │
│          ✅ Success       │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Update Employee Balance  │
│          ❌ Failed        │
└──────────────┬───────────┘
               │
               ▼
        Application Stops



Database State



┌───────────────────────────────────────────────┐
│ Salary Updated                 ✅             │
│ Salary History Inserted        ✅             │
│ Employee Balance Updated       ❌             │
│ Audit Record                   ❌             │
└───────────────────────────────────────────────┘



Question


Is this data

correct?



NO.



Database

is now

partially updated.



This situation

is called



                **Data Inconsistency**



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          WHAT IS DATA INCONSISTENCY ?                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Data Inconsistency means



Some operations

completed successfully,

while others

failed.



As a result,

different tables

contain

different versions

of the truth.



Example



Salary Table

shows



₹50,000



Employee Balance

still shows



₹45,000



Question


Which value

is correct?



Nobody knows.



The database

has become

inconsistent.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                               SOLUTION
══════════════════════════════════════════════════════════════════════════════════════════════════════


Instead of saying



Execute

each SQL

independently,



we tell

the database



Treat

all operations

as

ONE UNIT.



That unit

is called



                **Transaction**



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WITH TRANSACTION                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────┐
│ Transaction Starts       │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Update Salary Table      │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Insert Salary History    │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Update Employee Balance  │
│        ❌ Failed          │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Rollback Everything      │
└──────────────┬───────────┘
               │
               ▼
┌──────────────────────────┐
│ Database Restored        │
└──────────────────────────┘



Notice



Even though

Step 1

and

Step 2

were successful,



the database

undoes

everything.



Why?



Because



Either

everything succeeds



OR



nothing succeeds.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                            REAL LIFE EXAMPLE

══════════════════════════════════════════════════════════════════════════════════════════════════════


Think about

ATM Withdrawal.



You request



Withdraw

₹10,000



ATM performs



┌────────────────────────┐
│ Deduct Bank Balance    │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│ Dispense Cash          │
└───────────┬────────────┘
            │
            ▼
┌────────────────────────┐
│ Print Receipt          │
└────────────────────────┘



Imagine



Money deducted



but



Cash

never comes out.



Would you accept that?



Never.



ATM

uses

Transactions

for exactly

this reason.



Either



Money deducted

+

Cash Dispensed



OR



Nothing happens.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Q1.

Why do we need

Transactions?



Answer


Transactions ensure

multiple database operations

behave

as one logical unit,

preventing

partial updates

and maintaining

data consistency.



------------------------------------------------------------



Q2.

What problem

does a Transaction solve?



Answer


Data Inconsistency.



------------------------------------------------------------



Q3.

What happens

when one operation

fails

inside

a Transaction?



Answer


All previous

database changes

are rolled back,

returning

the database

to its previous

consistent state.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                            **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Without Transaction



Operation 1 ✔

Operation 2 ✔

Operation 3 ❌



Database

becomes

inconsistent.



With Transaction



Operation 1 ✔

Operation 2 ✔

Operation 3 ❌



Rollback



Database

returns

to

its original state.



That is

the fundamental reason

why

Transaction Management

exists.

*/
