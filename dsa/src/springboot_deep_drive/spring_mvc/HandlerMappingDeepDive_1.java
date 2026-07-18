package springboot_deep_drive.spring_mvc;

/*
╔════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                            ║
║                           🌟 SPRING BOOT DEEP DRIVE SERIES 🌟                             ║
║                                                                                            ║
║                      📖 CHAPTER 07 : HANDLER MAPPING (PART 1/2)                           ║
║                                                                                            ║
║                          ⏱ Reading Time : 15 Minutes                                      ║
║                          📈 Difficulty : 🟡 Intermediate                                  ║
║                                                                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════╝


==============================================================================================
                                   LEARNING OBJECTIVES
==============================================================================================

By the end of this chapter you will understand

✔ What is HandlerMapping?

✔ Why Spring created HandlerMapping?

✔ Why DispatcherServlet cannot directly find Controllers?

✔ How Spring finds the correct Controller?

✔ What happens during application startup?

✔ Internal architecture of HandlerMapping.


==============================================================================================
                           THE BIG QUESTION
==============================================================================================

Suppose your application contains

EmployeeController

UserController

DepartmentController

LeaveController

AttendanceController

Now the client sends

GET /employees

Question

How does Spring know

that

GET /employees

should execute

EmployeeController#getEmployees()

Who finds this Controller?

Answer

HandlerMapping.


==============================================================================================
                           WHAT IS HANDLERMAPPING?
==============================================================================================

HandlerMapping is a Spring MVC strategy interface.

Its responsibility is

        "Find the correct handler
         for the incoming request."

Notice one word carefully

Find.

It DOES NOT

❌ Execute Controller

❌ Validate Request

❌ Generate Response

Its only job is

Locate the correct Handler.


==============================================================================================
                       WHY WAS HANDLERMAPPING CREATED?
==============================================================================================

Imagine DispatcherServlet without HandlerMapping.

Request

↓

DispatcherServlet

↓

Search Every Controller

↓

Search Every Method

↓

Compare URLs

↓

Execute Matching Method

Now imagine

100 Controllers

Each Controller

20 APIs

Total

2000 methods

Would DispatcherServlet scan

2000 methods

for every request?

Absolutely NOT.

That would be extremely slow.

So Spring solves this problem
using HandlerMapping.


==============================================================================================
                        REAL LIFE ANALOGY
==============================================================================================

Imagine a huge hospital.

You enter the reception.

Receptionist doesn't know medicine.

Receptionist only asks

"What is your problem?"

Then sends you to

✔ Dentist

✔ Cardiologist

✔ Orthopedic Doctor

Exactly the same

DispatcherServlet

↓

HandlerMapping

↓

Correct Controller


HandlerMapping is the Receptionist.


==============================================================================================
                       COMPLETE REQUEST FLOW
==============================================================================================


                     HTTP REQUEST


              Client

                 │

                 ▼

        DispatcherServlet

                 │

                 ▼

        HandlerMapping

                 │

     Finds Correct Handler

                 │

                 ▼

      HandlerExecutionChain

                 │

                 ▼

        HandlerAdapter

                 │

                 ▼

         Controller


Notice

DispatcherServlet never searches
Controllers itself.

It delegates that responsibility.


==============================================================================================
                     WHAT IS A HANDLER?
==============================================================================================

Many beginners think

Handler

=

Controller

Not exactly.

A Handler simply means

"The object that will process the request."

In Spring Boot,

the Handler is usually

Controller + Controller Method

Example

@RestController
@RequestMapping("/employees")

public class EmployeeController {

    @GetMapping

    public List<Employee> getEmployees(){}

}

Here

Handler

=

EmployeeController#getEmployees()


==============================================================================================
                  INTERNAL THINKING OF SPRING
==============================================================================================

Suppose request is

GET /employees

DispatcherServlet thinks

"I don't know
which Controller should execute."

↓

Ask HandlerMapping

↓

HandlerMapping replies

"I found it."

↓

EmployeeController#getEmployees()

↓

DispatcherServlet

↓

HandlerAdapter

↓

Controller


DispatcherServlet never searches manually.


==============================================================================================
                 WHAT HAPPENS DURING APPLICATION STARTUP?
==============================================================================================

This is where Spring becomes powerful.

Spring does NOT wait
until the first request arrives.

Instead,

during application startup

Spring scans

@Controller

@RestController

@RequestMapping

@GetMapping

@PostMapping

etc.

and creates an internal lookup table.


This process happens

ONLY ONCE

during startup.


==============================================================================================
                 INTERNAL LOOKUP TABLE (SIMPLIFIED)
==============================================================================================

┌──────────────────────────┬──────────────────────────────────────────────┐
│ URL                      │ Controller Method                            │
├──────────────────────────┼──────────────────────────────────────────────┤
│ /employees               │ EmployeeController#getEmployees()            │
│ /users                   │ UserController#getUsers()                    │
│ /attendance              │ AttendanceController#getAttendance()         │
│ /departments             │ DepartmentController#getDepartments()        │
└──────────────────────────┴──────────────────────────────────────────────┘


Now when

GET /employees

arrives,

Spring performs

Map Lookup

instead of

Scanning all Controllers.


This is one of the biggest reasons
Spring MVC is fast.


==============================================================================================
                          INTERVIEW QUESTIONS
==============================================================================================

Q.

Who finds the Controller?

Answer

HandlerMapping.


------------------------------------------------------------

Q.

Does DispatcherServlet search Controllers?

Answer

No.

DispatcherServlet delegates that work
to HandlerMapping.


------------------------------------------------------------

Q.

When does Spring scan Controllers?

Answer

During application startup.

Not for every request.


==============================================================================================
                           PART 1 SUMMARY
==============================================================================================

✔ HandlerMapping finds the correct Handler.

✔ DispatcherServlet delegates Controller lookup.

✔ Spring scans Controllers only once during startup.

✔ Spring creates an internal URL mapping table.

✔ Runtime lookup is extremely fast.


═══════════════════════════════════════════════════════════════════════════════════════════════

➡ PART 2

Topics

✔ RequestMappingHandlerMapping

✔ URL Matching Algorithm

✔ How @GetMapping Works

✔ Internal Flow

✔ Production Insights

═══════════════════════════════════════════════════════════════════════════════════════════════


*/

public class HandlerMappingDeepDive_1 {

}