package springboot_deep_drive.aop_deep;

public class _08_LoggingAspectRealProject {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │                **SPRING BOOT DEEP DIVE - AOP SERIES**                                      │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │                  **Chapter 08 : Logging Aspect (Real Project)**                            │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                                    ║
║                  SPRING BOOT DEEP DIVE ── LOGGING ASPECT (REAL PROJECT)                           ║
║                                                                                                    ║
║                      Chapter 08 : LOGGING ASPECT                                                  ║
║                                                                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           1. WHY DO WE NEED LOGGING ?                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

your HRMS application
is running in Production.



An employee says



"I clicked Approve Leave,

but it failed."



Question


How will you know


✔ Which API was called?

✔ Which method executed?

✔ Which user made the request?

✔ What parameters were passed?

✔ Did the method complete successfully?

✔ How long did it take?



Without logs,

finding the issue

becomes very difficult.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Without AOP

══════════════════════════════════════════════════════════════════════════════════════════════════════



@Service
public class EmployeeService{

    public void saveEmployee(){

        log.info("Method Started");

        // Business Logic

        log.info("Method Completed");

    }

}



AttendanceService



public void markAttendance(){

    log.info("Method Started");

    // Business Logic

    log.info("Method Completed");

}



PayrollService



public void generateSalary(){

    log.info("Method Started");

    // Business Logic

    log.info("Method Completed");

}



Question


What is the problem?



Answer


Duplicate code

everywhere.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          SOLUTION USING AOP                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Move

all logging

into

one Aspect.



                   LoggingAspect

                          │

        ┌─────────────────┼─────────────────┐

        ▼                 ▼                 ▼

 EmployeeService   AttendanceService   PayrollService



One Aspect

handles

every Service.



No duplicate code.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         COMPLETE PROJECT STRUCTURE                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



src

│

├── controller

│      └── EmployeeController

│

├── service

│      └── EmployeeService

│

├── aspect

│      └── LoggingAspect

│

└── SpringBootApplication



Simple

and clean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           STEP 1 : SERVICE                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Service
public class EmployeeService{

    public String saveEmployee(String name){

        System.out.println("Saving Employee...");

        return "Employee Saved";

    }

}



Notice



No logging.



Business logic only.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        STEP 2 : LOGGING ASPECT                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect{


    @Around("execution(* com.hrms.service.*.*(..))")
    public Object logMethod(
            ProceedingJoinPoint joinPoint)
            throws Throwable{


        String methodName =
                joinPoint.getSignature().getName();


        System.out.println(
                "Method Started : "
                + methodName);


        Object result =
                joinPoint.proceed();


        System.out.println(
                "Method Completed : "
                + methodName);


        return result;

    }

}



Very little code.

Works for

every Service.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE EXECUTION FLOW                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 HTTP Request

                       │

                       ▼

                 Controller

                       │

                       ▼

                Service Proxy

                       │

                       ▼

       LoggingAspect Starts

                       │

                       ▼

        Print "Method Started"

                       │

                       ▼

      joinPoint.proceed()

                       │

                       ▼

          EmployeeService

                       │

                       ▼

       Print "Method Completed"

                       │

                       ▼

                 HTTP Response



Notice carefully.



LoggingAspect

never calls

EmployeeService

directly.



Spring

handles everything.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              CONSOLE OUTPUT                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Method Started : saveEmployee

Saving Employee...

Method Completed : saveEmployee



Now suppose



markAttendance()



is called.



Output



Method Started : markAttendance

Attendance Saved

Method Completed : markAttendance



Same Aspect.



Different Service.



Zero duplicate code.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL HRMS EXAMPLE                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Employee Login

        │

        ▼

Employee clicks

Approve Leave

        │

        ▼

approveLeave()

        │

        ▼

LoggingAspect


prints



Method Started : approveLeave



↓

Business Logic



↓

Method Completed : approveLeave



Later,

if an issue occurs,

developers

simply check logs.



No need

to modify

business classes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHAT SHOULD WE LOG ?                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Good things to log


✔ Method Name

✔ Execution Time

✔ User Id

✔ Request Id

✔ Exception Details

✔ API Name



Avoid logging


❌ Password

❌ OTP

❌ Access Token

❌ Refresh Token

❌ Credit Card Number

❌ Aadhaar Number

❌ PAN Number



Never log

sensitive information

in production.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            BEST PRACTICES                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Keep logging

inside Aspect.



✔ Keep business logic

inside Service.



✔ Log meaningful messages.



✔ Don't log

sensitive data.



✔ Use

SLF4J Logger

instead of

System.out.println()

in real projects.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why is AOP commonly used
for logging?



Answer


Because logging is a cross-cutting concern.

Using AOP removes duplicate logging code
from business classes.



------------------------------------------------------------


Q2.

Should logging be written
inside every Service?



No.

A LoggingAspect should handle
logging for all matching methods.



------------------------------------------------------------


Q3.

What information is commonly logged?



Method Name

Execution Time

User Id

Request Id

Exception Details



Avoid logging

sensitive information.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             PART 1 COMPLETE                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Production Logger (SLF4J)

✔ Logging Request Parameters

✔ Logging Return Values

✔ Exception Logging

✔ Execution Time Logging

✔ Final Production LoggingAspect

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    2. PRODUCTION READY LOGGING ASPECT                                             ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now

our LoggingAspect

was


@Before

↓

Print Method Name



This works

for learning,

but not

for Production.



Question


What information

does a company

usually log?



Typical Production Logs


✔ API Name

✔ Class Name

✔ Method Name

✔ Method Parameters

✔ Execution Time

✔ Success Status

✔ Exception Details

✔ Request Id (Optional)

✔ User Id (Optional)



Let's build

a production-style

Logging Aspect.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     STEP 1 : USE SLF4J LOGGER                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Never use


System.out.println()



inside

Spring Boot applications.



Instead use



private static final Logger log =
        LoggerFactory.getLogger(LoggingAspect.class);



Why?



Because Logger provides


✔ Log Levels

✔ Log Files

✔ Log Formatting

✔ Log Rotation

✔ External Configuration



Example



log.info("Employee Saved");



log.error("Database Error");



log.warn("Low Memory");



log.debug("Method Started");



This is

the standard

used in

almost every

Spring Boot project.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                    STEP 2 : COMPLETE LOGGING ASPECT
══════════════════════════════════════════════════════════════════════════════════════════════════════



@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);


    @Around("execution(* com.hrms.service.*.*(..))")
    public Object logMethod(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        String methodName =
                joinPoint.getSignature().getName();

        Object[] arguments =
                joinPoint.getArgs();

        log.info("Method Started : {}", methodName);

        log.info("Arguments : {}",
                Arrays.toString(arguments));

        Object result =
                joinPoint.proceed();

        log.info("Method Completed : {}", methodName);

        return result;
    }

}



Notice

how little code

is required

to log

every Service.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         COMPLETE EXECUTION FLOW                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                  HTTP Request

                        │

                        ▼

                 Controller

                        │

                        ▼

                Service Proxy

                        │

                        ▼

       Read Method Name

                        │

                        ▼

       Read Parameters

                        │

                        ▼

      Log "Method Started"

                        │

                        ▼

        joinPoint.proceed()

                        │

                        ▼

       Original Service Method

                        │

                        ▼

      Log "Method Completed"

                        │

                        ▼

                 HTTP Response



Everything happens

automatically.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     SAMPLE CONSOLE OUTPUT                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose


saveEmployee(
        "Pankaj",
        25
)



Console



INFO

Method Started : saveEmployee



INFO

Arguments : [Pankaj, 25]



INFO

Method Completed : saveEmployee



Very clean.

Very readable.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       SHOULD WE LOG EVERYTHING ?                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


No.



This is a very common

production mistake.



Never log


❌ Password

❌ OTP

❌ JWT Token

❌ Refresh Token

❌ Credit Card Number

❌ Aadhaar Number

❌ PAN Number

❌ CVV



Example



LoginRequest



{

   username : "admin",

   password : "123456"

}



Bad Logging



Arguments :

[admin,123456]



Good Logging



Arguments :

[admin, ******]



Sensitive information

should always

be masked

or omitted.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     REAL HRMS PROJECT EXAMPLE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Employee submits

Leave Request.



Aspect logs



Method Started :
applyLeave



Arguments :

[EmployeeId=101,
 LeaveType=CASUAL]



Business Method Executes



Method Completed :
applyLeave



If

manager reports

a problem,

developers can quickly

identify


✔ Which API executed

✔ Which employee

✔ Which method

✔ Which parameters



Without adding

logging code

inside

LeaveService.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           BEST PRACTICES                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Use Logger

instead of

System.out.println()



------------------------------------------------------



✔ Log

Method Name.



------------------------------------------------------



✔ Log

only safe parameters.



------------------------------------------------------



✔ Never log

passwords,

tokens,

or sensitive information.



------------------------------------------------------



✔ Keep logging

lightweight.



Heavy processing

inside an Aspect

can slow down

every request.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERVIEW QUESTIONS                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why is Logger preferred over
System.out.println()?


Answer


Logger provides

log levels,

file output,

formatting,

filtering,

and better production support.



---------------------------------------------------------


Q2.

Should we log

all method parameters?


Answer


No.

Sensitive information

such as passwords,

tokens,

and personal data

should never be logged.



---------------------------------------------------------


Q3.

Why is AOP suitable
for logging?


Answer


Because logging is a
cross-cutting concern.

AOP centralizes logging,
removes duplicate code,
and keeps business logic clean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 2 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Exception Logging

✔ Execution Time Logging

✔ Logging Return Values

✔ Final Production LoggingAspect

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        3. EXECUTION TIME LOGGING                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

your HRMS application

contains



Employee APIs

Attendance APIs

Payroll APIs

Leave APIs



One day

users complain


"The application is slow."



Question


Which API

is slow?



Which Service

takes more time?



Which Database call

is expensive?



Without measuring

execution time,

developers

are simply

guessing.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                         WHY DO WE MEASURE EXECUTION TIME?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Execution Time helps us


✔ Identify slow APIs

✔ Find slow database queries

✔ Detect performance bottlenecks

✔ Compare before & after optimization

✔ Monitor production performance



Almost every

large company

logs execution time.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          REAL LIFE EXAMPLE                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

you order food

using Zomato.



You place the order

at



10:00:00



Food arrives

at



10:32:15



Question


How much time

did delivery take?



Answer


End Time

-

Start Time



Exactly the same

concept

is used

inside AOP.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     COMPLETE EXECUTION FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                  HTTP Request

                        │

                        ▼

                 Logging Aspect

                        │

                        ▼

          Record Start Time

                        │

                        ▼

          joinPoint.proceed()

                        │

                        ▼

           Original Service

                        │

                        ▼

           Record End Time

                        │

                        ▼

      Calculate Execution Time

                        │

                        ▼

                Print Log

                        │

                        ▼

                HTTP Response



Notice carefully.



The timer

starts

before

the business method.



The timer

stops

after

the business method.



Difference

=

Execution Time.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      COMPLETE IMPLEMENTATION                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);


    @Around("execution(* com.hrms.service.*.*(..))")
    public Object logExecutionTime(
            ProceedingJoinPoint joinPoint)
            throws Throwable {


        String methodName =
                joinPoint.getSignature().getName();


        long startTime =
                System.currentTimeMillis();


        Object result =
                joinPoint.proceed();


        long endTime =
                System.currentTimeMillis();


        long executionTime =
                endTime - startTime;


        log.info(
                "{} executed in {} ms",
                methodName,
                executionTime
        );


        return result;
    }

}



The logic is very simple.



Start Time

↓

Execute Method

↓

End Time

↓

Subtract



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        UNDERSTANDING THE CODE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Step 1



long startTime =
        System.currentTimeMillis();



Stores

the current time

before

method execution.



------------------------------------------------------------



Step 2



joinPoint.proceed();



Executes

the original

business method.



------------------------------------------------------------



Step 3



long endTime =
        System.currentTimeMillis();



Stores

the current time

after

method execution.



------------------------------------------------------------



Step 4



executionTime

=

endTime

-

startTime



This gives

the total execution time

in milliseconds.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          SAMPLE OUTPUT                                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

saveEmployee()

takes

35 milliseconds.



Console



INFO

saveEmployee executed in 35 ms



------------------------------------------------------------



Suppose

markAttendance()

takes

8 milliseconds.



Console



INFO

markAttendance executed in 8 ms



Now

developers know

which methods

are slow.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     REAL HRMS PROJECT EXAMPLE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Payroll Generation



generateMonthlySalary()



Console



INFO

generateMonthlySalary executed in 892 ms



Immediately

developers know



Payroll module

needs optimization.



------------------------------------------------------------



Leave Approval



approveLeave()



Console



INFO

approveLeave executed in 14 ms



No problem.



Performance

is good.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERNAL FLOW                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



             Controller

                  │

                  ▼

           Service Proxy

                  │

                  ▼

         Start Timer

                  │

                  ▼

        Original Service

                  │

                  ▼

          Stop Timer

                  │

                  ▼

      Calculate Duration

                  │

                  ▼

         Write Log Entry

                  │

                  ▼

         Return Response



This entire process

is automatic.



No changes

are required

inside

EmployeeService.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             BEST PRACTICES                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Log execution time

only for

important methods.



------------------------------------------------------------



✔ Investigate

methods

that consistently

take longer

than expected.



------------------------------------------------------------



✔ Combine

execution time

with

method name

for meaningful logs.



Good



INFO

approveLeave executed in 18 ms



Bad



INFO

18 ms



No one knows

which method

took

18 ms.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             COMMON MISTAKES                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Logging only

execution time.



Always include

method name

or class name.



------------------------------------------------------------



❌ Forgetting

return result;



The caller

receives

null.



------------------------------------------------------------



❌ Calling

proceed()

more than once.



The business method

executes

multiple times.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why is

@Around

used for measuring

execution time?



Answer


Because

@Around

executes both

before

and after

the business method,

allowing us to record

start time

and end time.



------------------------------------------------------------



Q2.

How do you calculate

execution time?



Answer


Execution Time

=

End Time

-

Start Time



------------------------------------------------------------



Q3.

Can

@Before

measure execution time?



No.



@Before

runs only

before the method.



To measure execution time,

we need both

the start

and end

of execution,

which is why

@Around

is used.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 3 COMPLETE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Exception Logging

✔ Using try-catch-finally inside @Around

✔ Logging Success & Failure

✔ Building the Final Production-Ready LoggingAspect

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           4. EXCEPTION LOGGING USING AOP                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

your HRMS application

is running

in Production.



One day

HR reports


"Salary Generation Failed."



Question


How will developers know



✔ Which method failed?

✔ Which Exception occurred?

✔ What was the error message?

✔ Which API caused it?

✔ How long did it run before failing?



Without exception logs,

developers

start debugging

from scratch.



Exception Logging

helps identify

production problems

within minutes.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                         WHY DO WE LOG EXCEPTIONS ?
══════════════════════════════════════════════════════════════════════════════════════════════════════


Exception logs help us


✔ Find production bugs

✔ Identify failing APIs

✔ Debug database problems

✔ Detect invalid user input

✔ Monitor application health

✔ Reduce debugging time



Almost every

enterprise application

logs exceptions.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           REAL LIFE EXAMPLE                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Imagine

you visit

a hospital.



Doctor asks


What happened?



Patient says


"I have pain."



Doctor immediately asks


Where?

Since when?

How severe?



Without these details,

the doctor

cannot diagnose

the problem.



Exception logging

works

exactly the same.



The more useful

the log,

the faster

the debugging.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        EXCEPTION FLOW INSIDE @Around                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                    HTTP Request

                          │

                          ▼

                   Logging Aspect

                          │

                          ▼

                    Start Timer

                          │

                          ▼

                 joinPoint.proceed()

                          │

              ┌───────────┴───────────┐

              │                       │

              ▼                       ▼

         Success                 Exception

              │                       │

              ▼                       ▼

        Return Result         Log Exception

              │                       │

              └───────────┬───────────┘

                          ▼

                    Stop Timer

                          │

                          ▼

                   Return Response



Notice



Even when

an Exception occurs,

our Aspect

can still log

everything.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     PRODUCTION READY EXCEPTION HANDLING                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Around("execution(* com.hrms.service.*.*(..))")
public Object logExecution(
        ProceedingJoinPoint joinPoint)
        throws Throwable {

    String methodName =
            joinPoint.getSignature().getName();

    long startTime =
            System.currentTimeMillis();

    try {

        log.info("Method Started : {}", methodName);

        Object result = joinPoint.proceed();

        log.info("Method Completed : {}", methodName);

        return result;

    } catch (Exception ex) {

        log.error(
                "Exception in {} : {}",
                methodName,
                ex.getMessage(),
                ex
        );

        throw ex;

    } finally {

        long executionTime =
                System.currentTimeMillis() - startTime;

        log.info(
                "{} executed in {} ms",
                methodName,
                executionTime
        );
    }

}



Notice

the structure.



try

↓

Execute Business Logic



catch

↓

Log Exception



finally

↓

Log Execution Time



This pattern

is used

in many

real Spring Boot projects.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHY DO WE RE-THROW THE EXCEPTION ?                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


This line



throw ex;



is extremely important.



Question


Why don't we

simply log

the Exception

and continue?



Answer


Because

our Aspect

should NOT

hide

business failures.



Suppose

Database is down.



If we remove



throw ex;



then



Controller

thinks

everything

worked successfully.



This is wrong.



Correct Flow



Database Error

↓

Aspect Logs Error

↓

Exception Re-thrown

↓

Global Exception Handler

↓

HTTP 500 Response



Exactly

what happens

in production.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE EXECUTION EXAMPLE                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


EmployeeService



public void saveEmployee(){

    throw new RuntimeException(
            "Database Connection Failed"
    );

}



Console Output



INFO

Method Started : saveEmployee



ERROR

Exception in saveEmployee :

Database Connection Failed



INFO

saveEmployee executed in 21 ms



Notice



Execution Time

is still logged.



Because

finally

always executes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       REAL HRMS PROJECT EXAMPLE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Monthly Salary Processing



generateSalary()



↓

Database Connection Lost



↓

LoggingAspect



prints



Method Started : generateSalary



↓

ERROR

Database Connection Failed



↓

generateSalary executed in 684 ms



Developers

immediately know


✔ Which Service failed

✔ Which Exception occurred

✔ How long it executed



Without opening

the debugger.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            BEST PRACTICES                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Log

Method Name.



------------------------------------------------------------



✔ Log

Exception Message.



------------------------------------------------------------



✔ Log

Complete Stack Trace.



Use



log.error("...", ex);



instead of



System.out.println(ex);



------------------------------------------------------------



✔ Always

re-throw

the Exception

unless

you intentionally

want to handle it.



------------------------------------------------------------



✔ Keep

business logic

out of

catch blocks

inside Aspects.



Aspect

should log,

not recover

business failures.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             COMMON MISTAKES                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


❌ Mistake


Catching

the Exception

without

throwing it again.



Result



Controller

believes

the request

was successful.



------------------------------------------------------------



❌ Mistake


Logging only

ex.getMessage()



You lose

the stack trace.



Always prefer



log.error(
    "...",
    ex
);



------------------------------------------------------------



❌ Mistake


Writing

recovery logic

inside

LoggingAspect.



Recovery belongs

to business logic

or

Global Exception Handler.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why do we use

try-catch-finally

inside

@Around?



Answer


To log

successful execution,

exceptions,

and execution time

in one place.



------------------------------------------------------------



Q2.

Why should we

re-throw

the Exception?



Answer


So that the normal

exception handling flow

continues.

The Aspect should log

the exception,

not swallow it.



------------------------------------------------------------



Q3.

Will

finally

execute

even when

an Exception occurs?



Yes.



Just like normal Java,

the finally block

always executes,

making it a good place

to log execution time.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 4 COMPLETE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Final Production LoggingAspect

✔ Combining

   • Entry Logging

   • Exit Logging

   • Parameters

   • Return Value

   • Execution Time

   • Exception Logging

into one clean Aspect.

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      5. FINAL PRODUCTION LOGGING ASPECT                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


So far,

we have learned


✔ Entry Logging

✔ Exit Logging

✔ Method Name

✔ Parameters

✔ Execution Time

✔ Exception Logging



Question


Do companies create

five different Aspects?



Usually,

NO.



Most companies

create

one reusable

LoggingAspect

that performs

all these tasks.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                         COMPLETE REQUEST LIFECYCLE
══════════════════════════════════════════════════════════════════════════════════════════════════════



                      HTTP Request

                           │

                           ▼

                  Logging Aspect Starts

                           │

                           ▼

                 Read Method Name

                           │

                           ▼

               Read Method Arguments

                           │

                           ▼

                  Start Timer

                           │

                           ▼

              Execute Business Method

                           │

              ┌────────────┴────────────┐

              │                         │

              ▼                         ▼

         Success                  Exception

              │                         │

              ▼                         ▼

      Log Return Value          Log Exception

              │                         │

              └────────────┬────────────┘

                           ▼

                  Stop Timer

                           │

                           ▼

               Log Execution Time

                           │

                           ▼

                     Return Response



Everything happens

inside

one Aspect.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     COMPLETE PRODUCTION LOGGING ASPECT                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



@Aspect
@Component
public class LoggingAspect {

    private static final Logger log =
            LoggerFactory.getLogger(LoggingAspect.class);


    @Around("execution(* com.hrms.service.*.*(..))")
    public Object logMethod(
            ProceedingJoinPoint joinPoint)
            throws Throwable {

        String className =
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName();

        String methodName =
                joinPoint.getSignature()
                        .getName();

        Object[] arguments =
                joinPoint.getArgs();

        long startTime =
                System.currentTimeMillis();

        try {

            log.info(
                    "Started : {}.{}()",
                    className,
                    methodName
            );

            log.debug(
                    "Arguments : {}",
                    Arrays.toString(arguments)
            );

            Object result =
                    joinPoint.proceed();

            log.debug(
                    "Returned : {}",
                    result
            );

            return result;

        } catch (Exception ex) {

            log.error(
                    "Exception in {}.{}()",
                    className,
                    methodName,
                    ex
            );

            throw ex;

        } finally {

            long executionTime =
                    System.currentTimeMillis()
                    - startTime;

            log.info(
                    "Completed : {}.{}() in {} ms",
                    className,
                    methodName,
                    executionTime
            );

        }

    }

}



Notice

how organized

the code is.



No business logic.



Only logging.



Exactly

what AOP

is meant for.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     UNDERSTANDING THE EXECUTION STEP BY STEP                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Step 1

Read

Class Name



EmployeeService



-------------------------------------------------------



Step 2

Read

Method Name



saveEmployee()



-------------------------------------------------------



Step 3

Read

Method Arguments



["Pankaj",25]



-------------------------------------------------------



Step 4

Start Timer



Current Time

↓

Store



-------------------------------------------------------



Step 5

Execute



joinPoint.proceed()



-------------------------------------------------------



Step 6

If Success


Log Return Value



-------------------------------------------------------



Step 7

If Failure


Log Exception



-------------------------------------------------------



Step 8

Always


Log Execution Time



Exactly

one clean flow.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       COMPLETE CONSOLE OUTPUT                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Case 1

Success



INFO

Started :
EmployeeService.saveEmployee()



DEBUG

Arguments :
[Pankaj,25]



DEBUG

Returned :
Employee Saved Successfully



INFO

Completed :
EmployeeService.saveEmployee()
in 14 ms



══════════════════════════════════════════════════════════════════════════════════════════════════════

Case 2

Exception

══════════════════════════════════════════════════════════════════════════════════════════════════════



INFO

Started :
PayrollService.generateSalary()



ERROR

Exception in
PayrollService.generateSalary()

Database Connection Failed



INFO

Completed :
PayrollService.generateSalary()
in 128 ms



Notice



Execution Time

is still logged

because

finally

always executes.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        REAL HRMS PROJECT FLOW                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Employee

Clicks

Approve Leave

          │

          ▼

LeaveController

          │

          ▼

LeaveService Proxy

          │

          ▼

LoggingAspect

          │

          ├────────► Log Method Name

          │

          ├────────► Log Parameters

          │

          ├────────► Start Timer

          │

          ├────────► Execute Service

          │

          ├────────► Log Result

          │

          ├────────► Log Exception (if any)

          │

          └────────► Log Execution Time

          │

          ▼

HTTP Response



Notice



LeaveService

contains

ZERO

logging code.



Business logic

remains

clean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                   WHY NOT WRITE LOGGING INSIDE SERVICES ?                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Without AOP



saveEmployee(){

   log.info(...)

   ...

   log.info(...)

}



approveLeave(){

   log.info(...)

   ...

   log.info(...)

}



generateSalary(){

   log.info(...)

   ...

   log.info(...)

}



Hundreds

of duplicate

logging statements.



----------------------------------------------------------



With AOP



saveEmployee(){

    Business Logic Only
}



approveLeave(){

    Business Logic Only
}



generateSalary(){

    Business Logic Only
}



Logging

exists

only once.



Easy to maintain.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          PRODUCTION BEST PRACTICES                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


✔ Keep Aspects

focused on

cross-cutting concerns.



----------------------------------------------------------



✔ Never put

business rules

inside an Aspect.



----------------------------------------------------------



✔ Log only

useful information.



----------------------------------------------------------



✔ Never log

Passwords

JWT Tokens

OTP

Bank Details

Sensitive Personal Data



----------------------------------------------------------



✔ Use

appropriate log levels.



INFO

↓

Business Events



DEBUG

↓

Development Details



WARN

↓

Unexpected but Recoverable



ERROR

↓

Failures



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          COMMON INTERVIEW QUESTIONS                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why is Logging
a cross-cutting concern?



Answer


Because almost every module
(Employee, Attendance, Payroll, Leave, etc.)
requires logging, but logging is not part of
their core business logic.



------------------------------------------------------------


Q2.

Why is @Around
commonly used for logging?



Answer


Because it allows us to

• execute code before the method,
• execute the original method,
• capture exceptions,
• measure execution time,
• inspect the returned value,

all in a single Advice.



------------------------------------------------------------


Q3.

Should business logic
be written inside an Aspect?



No.


Business logic belongs in

Service classes.


Aspects should contain only
cross-cutting concerns such as

logging,

auditing,

security,

caching,

or performance monitoring.



------------------------------------------------------------


Q4.

What information
should never be logged?



Passwords,

OTP,

Access Tokens,

Refresh Tokens,

Credit Card Numbers,

or any sensitive personal information.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             CHAPTER 08 SUMMARY                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Now you can build

a production-style

Logging Aspect.


You understand


✔ Method Entry Logging

✔ Method Exit Logging

✔ Method Name

✔ Class Name

✔ Parameters

✔ Return Value

✔ Exception Logging

✔ Execution Time

✔ Logger Best Practices

✔ Real Project Usage



This is enough

to build

logging aspects

used in

most Spring Boot applications.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                 NEXT CHAPTER                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Chapter 09

**Spring AOP Limitations & Hidden Interview Questions**


We will answer questions like


✔ Why doesn't AOP work on private methods?

✔ Why doesn't self-invocation work?

✔ Why don't final methods get intercepted?

✔ Why don't constructors execute through AOP?

✔ Why does calling this.method() bypass the Aspect?

✔ How to solve self-invocation problems?

✔ Real debugging scenarios from production.


This is one of the most frequently asked Spring AOP interview topics and something every Spring Boot developer should understand.

*/