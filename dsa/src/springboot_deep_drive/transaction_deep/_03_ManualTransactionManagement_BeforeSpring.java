package springboot_deep_drive.transaction_deep;

public class _03_ManualTransactionManagement_BeforeSpring {
}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │          **Chapter 03 : Manual Transaction Management (Before Spring)**                    │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    HOW WERE TRANSACTIONS MANAGED BEFORE SPRING ?                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Today

developers simply write



@Transactional



and everything works.



Question



Did Java always have

@Transactional ?



NO.



Before Spring,

developers had to manage

transactions

manually.



Let's see

what that looked like.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                              THE REAL PROBLEM
══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose

an employee

is receiving

his monthly salary.



Our application performs



┌────────────────────────────────────────┐
│ Update Employee Salary                 │
└───────────────────┬────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────┐
│ Insert Salary History                  │
└───────────────────┬────────────────────┘
                    │
                    ▼
┌────────────────────────────────────────┐
│ Update Payroll Summary                 │
└────────────────────────────────────────┘



Question



If

step 3

fails,



who should

rollback

the first

two updates?



Somebody

must handle it.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                         JDBC MANUAL TRANSACTION
══════════════════════════════════════════════════════════════════════════════════════════════════════



Connection connection = null;

try{

    connection = dataSource.getConnection();

    connection.setAutoCommit(false);

    salaryRepository.updateSalary(connection);

    salaryHistoryRepository.insertHistory(connection);

    payrollRepository.updateSummary(connection);

    connection.commit();

}
catch(Exception ex){

    connection.rollback();

}
finally{

    connection.close();

}



Don't worry

about every line.

Let's understand

the flow.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERNAL EXECUTION FLOW                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                Get Database Connection

                          │

                          ▼

┌──────────────────────────────────────────┐
│ Auto Commit = FALSE                      │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│ Execute SQL 1                            │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│ Execute SQL 2                            │
└──────────────────┬───────────────────────┘
                   │
                   ▼
┌──────────────────────────────────────────┐
│ Execute SQL 3                            │
└──────────────────┬───────────────────────┘
                   │
         ┌─────────┴──────────┐
         │                    │
        Success              Exception
         │                    │
         ▼                    ▼
┌────────────────┐    ┌──────────────────┐
│ Commit         │    │ Rollback         │
└────────────────┘    └──────────────────┘



Everything

had to be written

manually.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHAT DOES setAutoCommit(false) DO ?                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Normally

databases work like this.



SQL

↓

Immediately Saved



Example



UPDATE employee...



Immediately

saved

to database.



This is called



Auto Commit.



ASCII Flow



SQL

     │

     ▼

Database

     │

     ▼

Immediately Commit



══════════════════════════════════════════════════════════════════════════════════════════════════════


Now suppose

we write



connection.setAutoCommit(false);



Meaning



"Dear Database,

don't save

every SQL immediately.



Wait

until

I explicitly

say

COMMIT."



Now the flow becomes



SQL 1

↓

Temporary



SQL 2

↓

Temporary



SQL 3

↓

Temporary



COMMIT

↓

Everything Saved



If

something fails



ROLLBACK

↓

Everything Removed



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       WHY WAS THIS A PROBLEM ?                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Imagine



500 Service Methods.



Every Service

contains



try

catch

rollback

commit

close



again

and again

and again.



Example



EmployeeService



try{

...

commit();

}
catch{

rollback();

}



------------------------------------------------------------



LeaveService



try{

...

commit();

}
catch{

rollback();

}



------------------------------------------------------------



PayrollService



try{

...

commit();

}
catch{

rollback();

}



Same code

everywhere.



Huge duplication.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                           REAL HRMS EXAMPLE
══════════════════════════════════════════════════════════════════════════════════════════════════════



Employee Resigns.



Application performs



Update Employee



↓

Generate Settlement



↓

Update Leave Balance



↓

Notify Finance



If

Generate Settlement

fails



Developer

must remember



rollback()



If he forgets



Database

becomes

inconsistent.



This was

a very common

problem

before Spring.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       HOW DID SPRING SOLVE THIS ?                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Spring asked



"Why should

developers

write

the same

transaction code

hundreds of times?"



Instead,

Spring said



"You write

business logic.



I'll manage

transactions."



That is exactly

why

@Transactional

was introduced.



Later,

we'll see

how one annotation

replaces

all this code.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERVIEW QUESTIONS                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

How were transactions managed

before Spring?



Answer


Using JDBC,

developers manually controlled

begin,

commit,

rollback,

and connection management.



------------------------------------------------------------



Q2.

Why was manual transaction management

difficult?



Answer


Because every service method

needed repetitive

try-catch,

commit,

rollback,

and resource cleanup,

making the code difficult

to maintain.



------------------------------------------------------------



Q3.

What is the purpose of

setAutoCommit(false) ?



Answer


It disables automatic commits,

allowing multiple SQL operations

to execute as one transaction

until commit()

or rollback()

is called.



------------------------------------------------------------



Q4.

What problem did Spring solve

using @Transactional?



Answer


Spring automated

transaction management,

allowing developers

to focus only

on business logic.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                               **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Before Spring



Developer managed



Connection

↓

Begin Transaction

↓

Commit

↓

Rollback

↓

Close Connection



After Spring



Developer writes



@Transactional



Spring manages

everything else.



This is why

@Transactional

became one of

the most important

features of

Spring Framework.

*/

