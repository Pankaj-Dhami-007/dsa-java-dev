package springboot_deep_drive.transaction_deep;

public class _08_CustomizingRollbackRules {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - TRANSACTION SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │          **Chapter 08 : rollbackFor & noRollbackFor Explained**                            │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                   CUSTOMIZING TRANSACTION ROLLBACK RULES                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In the previous chapter

we learned



Default Rule



RuntimeException

────────► Rollback



Checked Exception

────────► Commit



Question



Can we

change

this behaviour?



Answer



YES.



Spring provides



✔ rollbackFor



✔ noRollbackFor



using

@Transactional.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                        WHY DO WE NEED THIS?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Imagine



Employee joins

your company.



Application performs



┌─────────────────────────────┐
│ Save Employee               │
└─────────────┬───────────────┘
              │
              ▼
┌─────────────────────────────┐
│ Create Login Account        │
└─────────────┬───────────────┘
              │
              ▼
┌─────────────────────────────┐
│ Send Welcome Email          │
└─────────────────────────────┘



Suppose



Email Server

is down.



Should



Employee

be removed

from database?



Usually



NO.



Employee

has already joined.



Only

email failed.



So

sometimes

we DON'T want

Rollback.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                    DEFAULT SPRING BEHAVIOUR
══════════════════════════════════════════════════════════════════════════════════════════════════════



┌──────────────────────────────┬─────────────────────────────┐
│ Exception                    │ Default Behaviour           │
├──────────────────────────────┼─────────────────────────────┤
│ RuntimeException             │ Rollback                    │
├──────────────────────────────┼─────────────────────────────┤
│ Error                        │ Rollback                    │
├──────────────────────────────┼─────────────────────────────┤
│ Checked Exception            │ Commit                      │
└──────────────────────────────┴─────────────────────────────┘



Sometimes

this default

is not enough.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           rollbackFor                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose



Rollback

even for

Checked Exceptions.



Example



@Transactional(

rollbackFor = Exception.class

)



Now



ALL Exceptions



will rollback.



Flow



Transaction Starts

        │

        ▼

Business Logic

        │

        ▼

IOException

(Checked Exception)

        │

        ▼

ROLLBACK



Normally

IOException

would Commit.



Because of

rollbackFor,



it now

Rolls Back.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                      REAL PROJECT EXAMPLE
══════════════════════════════════════════════════════════════════════════════════════════════════════



@Transactional(

rollbackFor = IOException.class

)

public void importEmployees(){



    saveEmployees();



    throw new IOException();



}



Result



ROLLBACK



Even though

IOException

is a

Checked Exception.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           noRollbackFor                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose



Commit

even if

a RuntimeException

occurs.



Example



@Transactional(

noRollbackFor = EmailException.class

)



Question



Why?



Suppose



Employee Created



Salary Created



Leave Balance Created



Welcome Email

fails.



Should

everything

rollback?



No.



Only

Email

failed.



Database

should remain

unchanged.



Flow



Transaction Starts

        │

        ▼

Save Employee

        │

        ▼

EmailException

(RuntimeException)

        │

        ▼

COMMIT



Normally

RuntimeException

causes Rollback.



Because of

noRollbackFor,



Spring

Commits.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE COMPARISON                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬────────────────────────────────────────────┐
│ Attribute                    │ Purpose                                    │
├──────────────────────────────┼────────────────────────────────────────────┤
│ rollbackFor                  │ Force Rollback                            │
├──────────────────────────────┼────────────────────────────────────────────┤
│ noRollbackFor                │ Prevent Rollback                          │
└──────────────────────────────┴────────────────────────────────────────────┘



Example



@Transactional(

rollbackFor = Exception.class

)



Meaning



Rollback

for

every Exception.



------------------------------------------------------------



@Transactional(

noRollbackFor = EmailException.class

)



Meaning



Commit

even if

EmailException

occurs.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        INTERNAL SPRING FLOW                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 Exception Thrown

                        │

                        ▼

┌─────────────────────────────────────────┐
│ Is Exception in rollbackFor ?           │
└──────────────┬──────────────────────────┘
               │
         Yes   │   No
               │
               ▼
         ROLLBACK

               │
               ▼

Otherwise

check



┌─────────────────────────────────────────┐
│ Is Exception in noRollbackFor ?         │
└──────────────┬──────────────────────────┘
               │
         Yes   │   No
               │
               ▼
          COMMIT

               │
               ▼

Otherwise

follow

Spring's

default rules.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL HRMS EXAMPLE                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Employee Registration



Step 1

Save Employee



↓

Step 2

Generate Employee ID



↓

Step 3

Send Welcome Email



Email Server

is down.



Decision



Employee

should still

exist.



Therefore



@Transactional(

noRollbackFor = EmailException.class

)



is a good choice.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           BEST PRACTICES                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



✔ Use

Spring's default

behaviour

whenever possible.



------------------------------------------------------------



✔ Use

rollbackFor

only when

business requirements

demand rollback

for checked exceptions.



------------------------------------------------------------



✔ Use

noRollbackFor

only when

failure

should not

cancel

the entire transaction.



------------------------------------------------------------



✔ Don't change

rollback rules

without

understanding

the business requirement.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



**Q1. What is rollbackFor?**



Answer



It tells Spring

to rollback

for specified exceptions,

even if they are

Checked Exceptions.



------------------------------------------------------------



**Q2. What is noRollbackFor?**



Answer



It tells Spring

NOT to rollback

for specified exceptions,

even if they normally

trigger rollback.



------------------------------------------------------------



**Q3. Why do we need rollbackFor?**



Answer



Because by default,

Spring does not rollback

for Checked Exceptions.



rollbackFor

changes

that behaviour.



------------------------------------------------------------



**Q4. When would you use noRollbackFor?**



Answer



When an exception

does not make

the business operation

invalid.



Example



Failure to send

a notification email

after successfully

creating an employee.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                               **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Default Spring



RuntimeException

────────► Rollback



Checked Exception

────────► Commit



rollbackFor

────────► Force Rollback



noRollbackFor

────────► Force Commit



Business Requirement

should always

decide

whether data

should be

Committed

or

Rolled Back.

*/