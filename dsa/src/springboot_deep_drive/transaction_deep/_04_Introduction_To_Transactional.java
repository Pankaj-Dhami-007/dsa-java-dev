package springboot_deep_drive.transaction_deep;

public class _04_Introduction_To_Transactional {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                 **Chapter 04 : Introduction to @Transactional**                            │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        INTRODUCTION TO @Transactional                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In the previous chapter

we learned

how transactions

were managed

before Spring.



Developers wrote



Connection

↓

setAutoCommit(false)

↓

commit()

↓

rollback()

↓

close()



inside

every Service.



Question


Can Spring

do this

automatically?



Answer



YES.



Spring provides



                **@Transactional**



══════════════════════════════════════════════════════════════════════════════════════════════════════
                           WHAT IS @Transactional ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Simple Definition



**@Transactional tells Spring that the method
should execute inside a database transaction.**



Instead of writing



commit()



rollback()



connection management



manually,



Spring

handles

everything.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        BEFORE SPRING                                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Developer



Get Connection

      │

      ▼

Disable Auto Commit

      │

      ▼

Execute SQL

      │

      ▼

Commit

      │

      ▼

Rollback

      │

      ▼

Close Connection



Developer

writes everything.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                           WITH SPRING
══════════════════════════════════════════════════════════════════════════════════════════════════════



Developer



@Transactional

public void processSalary(){



}



That's it.



Spring handles

everything else.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      COMPLETE INTERNAL FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                    Client

                      │

                      ▼

              Spring Proxy

                      │

                      ▼

        Transaction Starts

                      │

                      ▼

            Business Method

                      │

          ┌───────────┴────────────┐
          │                        │
         Success               Exception
          │                        │
          ▼                        ▼

     Commit Transaction      Rollback Transaction

          │                        │
          └───────────┬────────────┘
                      ▼

               Return Response



Notice carefully.



The developer

never calls



commit()



or



rollback()



Spring does it

automatically.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         SIMPLE EXAMPLE                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Service

public class SalaryService{



    @Transactional

    public void generateSalary(){



        salaryRepository.updateSalary();



        historyRepository.insertHistory();



        payrollRepository.updateSummary();



    }

}



Question


Where is



commit()



?



Nowhere.



Question


Where is



rollback()



?



Nowhere.



Spring

handles both.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL HRMS EXAMPLE                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Employee Salary Processing



┌────────────────────────────────────────┐
│ Update Salary                          │
└──────────────────┬─────────────────────┘
                   │
                   ▼
┌────────────────────────────────────────┐
│ Insert Salary History                  │
└──────────────────┬─────────────────────┘
                   │
                   ▼
┌────────────────────────────────────────┐
│ Update Payroll Summary                 │
└──────────────────┬─────────────────────┘
                   │
         ┌─────────┴──────────┐
         │                    │
        Success            Exception
         │                    │
         ▼                    ▼

┌────────────────┐     ┌────────────────┐
│ COMMIT         │     │ ROLLBACK       │
└────────────────┘     └────────────────┘



All of this

happens

automatically

because

of



@Transactional



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  IMPORTANT NOTE : DOES @Transactional                                            ║
║                     EXECUTE THE SQL ITSELF ?                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Many beginners think



@Transactional

executes SQL.



Wrong.



@Transactional

does NOT

execute SQL.



Repositories

execute SQL.



@Transactional

only manages

the transaction.



Think like this.



Repository

↓

"What SQL should execute?"



@Transactional

↓

"When should it Commit?"

"When should it Rollback?"



Both have

different responsibilities.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     DOES @Transactional CREATE SQL ?                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



NO.



@Transactional



does not create



INSERT



UPDATE



DELETE



SELECT



SQL statements.



It only

controls

the transaction

around them.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╗



Q1.

What is

@Transactional?



Answer


@Transactional tells Spring

to execute

the annotated method

inside

a database transaction.



------------------------------------------------------------



Q2.

Who performs

commit()

and

rollback()?



Answer


Spring's

Transaction Manager

coordinates them,

while the underlying

database performs

the actual commit

or rollback.



------------------------------------------------------------



Q3.

Does

@Transactional

execute SQL queries?



Answer


No.



Repositories

(or JDBC/JPA)

execute SQL.



@Transactional

only manages

the transaction

around those operations.



------------------------------------------------------------



Q4.

What is the biggest advantage

of

@Transactional?



Answer


It removes

manual transaction code

and automatically

handles

commit,

rollback,

and transaction boundaries.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                               **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



@Transactional

        │

        ▼

Spring Starts Transaction

        │

        ▼

Business Logic Executes

        │

        ▼

Success ?

     ┌──────┴──────┐
     │             │
    Yes            No
     │             │
     ▼             ▼

 Commit      Rollback



The annotation

does not execute SQL.

It manages

the transaction

around

your business logic.

*/