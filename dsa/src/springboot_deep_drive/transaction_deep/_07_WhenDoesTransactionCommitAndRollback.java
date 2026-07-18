package springboot_deep_drive.transaction_deep;

public class _07_WhenDoesTransactionCommitAndRollback {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │        **Chapter 07 : When Does Spring Commit & Rollback Transactions?**                  │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHEN DOES SPRING COMMIT OR ROLLBACK ?                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

we have



@Transactional

public void generateSalary(){



    updateSalary();

    saveSalaryHistory();

    updatePayroll();

}



Question



Who decides



Commit



or



Rollback ?



Answer



Spring

decides

based on

how the method

finishes.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                              COMPLETE FLOW
══════════════════════════════════════════════════════════════════════════════════════════════════════



                       Client

                         │

                         ▼

┌──────────────────────────────────────┐
│ Spring Transaction Proxy             │
└────────────────┬─────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────┐
│ Begin Transaction                    │
└────────────────┬─────────────────────┘
                 │
                 ▼
┌──────────────────────────────────────┐
│ Execute Business Method              │
└────────────────┬─────────────────────┘
                 │
       ┌─────────┴─────────┐
       │                   │
       ▼                   ▼
 Method Returns      Exception Thrown
 Normally
       │                   │
       ▼                   ▼
 Commit Decision     Rollback Decision
       │                   │
       └─────────┬─────────┘
                 ▼
         Return Response



The entire decision

depends on

how

the method exits.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         CASE 1 : SUCCESS                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Transactional

public void saveEmployee(){



    repository.save(employee);

    repository.saveHistory();



}



No Exception.



Method finishes

normally.



Flow



Begin Transaction

        │

        ▼

Business Logic

        │

        ▼

Method Ends Normally

        │

        ▼

COMMIT



Database

permanently saves

all changes.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                    CASE 2 : EXCEPTION OCCURS
══════════════════════════════════════════════════════════════════════════════════════════════════════



@Transactional

public void saveEmployee(){



    repository.save(employee);



    throw new RuntimeException("Database Error");



}



Flow



Begin Transaction

        │

        ▼

Save Employee

        │

        ▼

RuntimeException

        │

        ▼

ROLLBACK



Database

removes

every change

made

during

the transaction.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    DOES EVERY EXCEPTION CAUSE ROLLBACK ?                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


This is one of

the biggest

interview questions.



Answer



                **NO**



By default,

Spring behaves

like this.



┌────────────────────────────┬───────────────────────────────┐
│ Exception Type             │ Default Behaviour             │
├────────────────────────────┼───────────────────────────────┤
│ RuntimeException           │ ✅ Rollback                   │
├────────────────────────────┼───────────────────────────────┤
│ Error                      │ ✅ Rollback                   │
├────────────────────────────┼───────────────────────────────┤
│ Checked Exception          │ ❌ Commit (Default)           │
└────────────────────────────┴───────────────────────────────┘



This surprises

many developers.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                     WHY THIS DIFFERENCE ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Java divides

exceptions into



┌─────────────────────┐
│ Checked Exception   │
└──────────┬──────────┘
           │
Examples

IOException

SQLException

FileNotFoundException



------------------------------------------------------



┌─────────────────────┐
│ Unchecked Exception │
└──────────┬──────────┘
           │
Examples

RuntimeException

NullPointerException

IllegalArgumentException



Spring's default rule



RuntimeException

↓

Rollback



Checked Exception

↓

Commit



Later

we'll learn

how to change

this behaviour.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL HRMS EXAMPLE                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Employee applies

for Leave.



Application performs



┌──────────────────────────────┐
│ Save Leave Request           │
└─────────────┬────────────────┘
              │
              ▼
┌──────────────────────────────┐
│ Update Leave Balance         │
└─────────────┬────────────────┘
              │
              ▼
┌──────────────────────────────┐
│ Send Notification            │
└──────────────────────────────┘



Suppose



Update Leave Balance

throws



NullPointerException



Flow



Transaction Starts

        │

        ▼

Leave Saved

        │

        ▼

RuntimeException

        │

        ▼

Rollback



Database

returns

to

its previous state.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       COMMON DEVELOPER MISTAKE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Transactional

public void saveEmployee(){



    try{


        repository.save(employee);



    }catch(Exception ex){

        log.error(ex.getMessage());

    }

}



Question



Will Spring

Rollback?



Answer



                **NO**



Why?



Because

the exception

never leaves

the method.



Spring thinks



Everything

completed

successfully.



Therefore



COMMIT



This is a very

common

production bug.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



**Q1. When does Spring Commit a transaction?**



Answer



When

the transactional method

finishes normally

without an exception.



------------------------------------------------------------



**Q2. When does Spring Rollback a transaction?**



Answer



By default,

when an unchecked exception

(RuntimeException)

or Error

is thrown

out of

the transactional method.



------------------------------------------------------------



**Q3. Does every Exception cause Rollback?**



Answer



No.



Checked Exceptions

do not trigger

rollback

by default.



------------------------------------------------------------



**Q4. Why doesn't Spring Rollback if I catch the Exception?**



Answer



Because

Spring never sees

the exception.

From Spring's perspective,

the method

completed successfully,

so it commits

the transaction.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                                **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Method Ends Normally

        │

        ▼

COMMIT



RuntimeException Escapes

        │

        ▼

ROLLBACK



Exception Caught

Inside Method

        │

        ▼

COMMIT



This single rule

explains

most transaction

bugs in

real Spring Boot projects.

*/