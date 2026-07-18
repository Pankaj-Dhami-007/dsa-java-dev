package springboot_deep_drive.transaction_deep;

public class _06_WhereToUseTransactional {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │             **Chapter 06 : Where Should We Use @Transactional ?**                          │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHERE SHOULD WE USE @Transactional ?                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Now we know



✔ Why Transactions exist

✔ What is a Transaction

✔ How Spring manages Transactions

✔ Internal Working of @Transactional



Question



Where should we

actually write



@Transactional ?



On



Controller ?



Service ?



Repository ?



Entity ?



Let's understand.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                          LAYERED ARCHITECTURE
══════════════════════════════════════════════════════════════════════════════════════════════════════



                 HTTP Request

                       │

                       ▼

┌───────────────────────────────┐
│        Controller             │
└──────────────┬────────────────┘
               │
               ▼
┌───────────────────────────────┐
│         Service               │
└──────────────┬────────────────┘
               │
               ▼
┌───────────────────────────────┐
│       Repository              │
└──────────────┬────────────────┘
               │
               ▼
┌───────────────────────────────┐
│         Database              │
└───────────────────────────────┘



Question



Which layer

contains

Business Logic?



Answer



Service Layer.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                     WHY SERVICE LAYER IS THE BEST PLACE
══════════════════════════════════════════════════════════════════════════════════════════════════════


Business Logic

usually requires

multiple

database operations.



Example



Generate Salary



↓

Update Salary Table



↓

Insert Salary History



↓

Update Payroll Summary



↓

Generate Audit Log



All of these

should belong

to ONE

transaction.



Therefore



@Transactional

belongs

on

the Service.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           CORRECT DESIGN                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Service

public class SalaryService{


    @Transactional

    public void generateSalary(){


        salaryRepository.updateSalary();

        historyRepository.saveHistory();

        payrollRepository.updateSummary();

        auditRepository.saveAudit();

    }

}



One Method

One Transaction.



Exactly

what we want.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                      WHY NOT CONTROLLER ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Wrong



@RestController

public class SalaryController{


    @Transactional

    public void generateSalary(){


        ...

    }

}



Question



Why is this

not recommended?



Because



Controllers

should only



✔ Receive Request

✔ Validate Request

✔ Return Response



They should NOT

manage

database transactions.



Business Logic

belongs

inside

Service.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                      WHY NOT REPOSITORY ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Suppose



SalaryService



calls



SalaryRepository



HistoryRepository



PayrollRepository



AuditRepository



Question



Should each Repository

start

its own

transaction?



NO.



We need



ONE

transaction



covering

ALL repositories.



If every Repository

starts

its own

transaction,



partial updates

can happen.



Therefore,

Repositories

should focus

on



Executing SQL.



Service

should manage

the transaction.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       REAL HRMS EXAMPLE                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Employee Resignation



┌──────────────────────────────┐
│ Update Employee Status       │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ Calculate Settlement         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ Update Leave Balance         │
└──────────────┬───────────────┘
               │
               ▼
┌──────────────────────────────┐
│ Notify Finance               │
└──────────────────────────────┘



Question



How many

transactions

should exist?



Answer



Exactly



ONE.



Because

all operations

represent

one business action.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    METHOD LEVEL vs CLASS LEVEL                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Method Level



@Service

public class EmployeeService{


    @Transactional

    public void saveEmployee(){

    }


    public void findEmployee(){

    }

}



Only



saveEmployee()



runs

inside

a transaction.



══════════════════════════════════════════════════════════════════════════════════════════════════════



Class Level



@Service

@Transactional

public class EmployeeService{


    public void saveEmployee(){

    }


    public void updateEmployee(){

    }


    public void deleteEmployee(){

    }

}



Now



Every

public method

runs

inside

a transaction.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  WHICH ONE SHOULD WE USE ?                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬────────────────────────────────────────────┐
│ Method Level                 │ Class Level                                │
├──────────────────────────────┼────────────────────────────────────────────┤
│ More Explicit                │ Less Code                                 │
├──────────────────────────────┼────────────────────────────────────────────┤
│ Better Control               │ All Methods Become Transactional           │
├──────────────────────────────┼────────────────────────────────────────────┤
│ Easy To Understand           │ May Create Unnecessary Transactions        │
└──────────────────────────────┴────────────────────────────────────────────┘



For most

real projects,



Method Level

is preferred.



Because

not every method

needs

a transaction.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        BEST PRACTICES                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



✔ Put

@Transactional

on

Service Layer.



------------------------------------------------------------



✔ Keep Controllers

free

from

transaction logic.



------------------------------------------------------------



✔ Repository

should focus

on

database access.



------------------------------------------------------------



✔ Use

Method Level

unless

most methods

need

transactions.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



**Q1. Which layer is the best place for @Transactional?**



Answer



Service Layer.



Because

the Service

contains

business logic

that usually

involves

multiple repository calls.



------------------------------------------------------------



**Q2. Why not use @Transactional in the Controller?**



Answer



Controllers

should handle

HTTP requests

and responses,

not business logic

or transaction management.



------------------------------------------------------------



**Q3. Why is @Transactional generally not placed on Repository methods?**



Answer



A single business operation

often calls

multiple repositories.

The transaction should

cover the entire

business operation,

not each repository

individually.



------------------------------------------------------------



**Q4. Method-level or Class-level @Transactional?**



Answer



Method-level

is usually preferred

because it provides

better control

and avoids

creating unnecessary

transactions.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                             **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Controller

      │

      ▼

Service

(@Transactional)

      │

      ▼

Repository

      │

      ▼

Database



The transaction

should surround

the complete

business operation,

which is why

the Service layer

is the recommended place

for

@Transactional.

*/