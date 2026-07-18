package springboot_deep_drive.aop_deep;

public class _01_WhyWeNeedAOP {

}

/**
 * ┌─────────────────────────────────────────────────────────────────────────┐
 * │ "When we already have Filters and Interceptors, why do we need AOP?"   │
 * ├─────────────────────────────────────────────────────────────────────────┤
 * │ Answer ──▶ See detailed explanation below                               │
 * └─────────────────────────────────────────────────────────────────────────┘
 */
/*
╔══════════════════════════════════════════════════════════════════════════════╗
║                     FILTERS vs INTERCEPTORS vs AOP                          ║
╚══════════════════════════════════════════════════════════════════════════════╝

The answer is that they solve different problems, although there is some overlap.

┌─────────────────────────────────────────────────────────────────────────────┐
│                           HTTP REQUEST FLOW                                 │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Client                                                                     │
│      │                                                                       │
│      ▼                                                                       │
│  ┌──────────────────────┐                                                   │
│  │   Servlet Filter     │   ← Servlet container (Tomcat, Jetty)             │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │  DispatcherServlet   │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │ Spring Interceptor   │   ← Inside Spring MVC                             │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │     Controller       │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │      Service         │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │    Repository        │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │      Database        │                                                   │
│  └──────────────────────┘                                                   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

Now let's add AOP.

┌─────────────────────────────────────────────────────────────────────────────┐
│                        HTTP REQUEST FLOW WITH AOP                           │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│   Client                                                                     │
│      │                                                                       │
│      ▼                                                                       │
│  ┌──────────────────────┐                                                   │
│  │   Servlet Filter     │   ← Unaware of Service/Repository                 │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │  DispatcherServlet   │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │ Spring Interceptor   │   ← Knows Controller/Handler/ModelAndView         │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────┐                                                   │
│  │     Controller       │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                        ★  AOP  ★                                    │   │
│  │  ┌────────────────────────────────────────────────────────────────┐  │   │
│  │  │  Intercepts Spring Beans (Service, Repository methods)        │  │   │
│  │  └────────────────────────────────────────────────────────────────┘  │   │
│  └───────────────────────────┬──────────────────────────────────────────┘   │
│                              │                                              │
│                              ▼                                              │
│  ┌──────────────────────┐                                                   │
│  │   Service Method     │                                                   │
│  └─────────┬────────────┘                                                   │
│            │                                                                 │
│            ▼                                                                 │
│  ┌──────────────────────────────────────────────────────────────────────┐   │
│  │                        ★  AOP  ★                                    │   │
│  └───────────────────────────┬──────────────────────────────────────────┘   │
│                              │                                              │
│                              ▼                                              │
│  ┌──────────────────────┐                                                   │
│  │    Repository        │                                                   │
│  └──────────────────────┘                                                   │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

Notice something?
AOP works around Spring beans (Service, Repository, etc.), not around the entire HTTP request.

╔══════════════════════════════════════════════════════════════════════════════╗
║                          1. SERVLET FILTER                                  ║
╚══════════════════════════════════════════════════════════════════════════════╝

Filter belongs to the Servlet container (Tomcat, Jetty, etc.).
It executes before Spring MVC even starts.

    +-------------------------------+
    │   Filter DOES NOT know about  │
    ├───────────────────────────────┤
    │   ✘ Service                   │
    │   ✘ Repository                │
    │   ✘ Business methods          │
    ├───────────────────────────────┤
    │   Filter ONLY knows about     │
    ├───────────────────────────────┤
    │   ✔ HTTP Request              │
    │   ✔ HTTP Response             │
    +-------------------------------+

╔══════════════════════════════════════════════════════════════════════════════╗
║                        2. SPRING INTERCEPTOR                                ║
╚══════════════════════════════════════════════════════════════════════════════╝

Interceptor works inside Spring MVC.

    +-------------------------------+
    │ Interceptor KNOWS about       │
    ├───────────────────────────────┤
    │   ✔ Controller                │
    │   ✔ Handler                   │
    │   ✔ ModelAndView              │
    ├───────────────────────────────┤
    │ Interceptor DOES NOT know     │
    ├───────────────────────────────┤
    │   ✘ EmployeeService           │
    │   ✘ SalaryService             │
    │   ✘ PaymentService            │
    +-------------------------------+

╔══════════════════════════════════════════════════════════════════════════════╗
║                                3. AOP                                       ║
╚══════════════════════════════════════════════════════════════════════════════╝

AOP doesn't care about HTTP. It cares about Methods.

    +-------------------------------+
    │   AOP KNOWS about             │
    ├───────────────────────────────┤
    │   ✔ saveEmployee()            │
    │   ✔ updateEmployee()          │
    │   ✔ Any method on any bean    │
    +-------------------------------+

Suppose you have:

    @Service
    public class EmployeeService {
        public void saveEmployee() {}
        public void updateEmployee() {}
    }

AOP can intercept:

    saveEmployee()
    updateEmployee()

Whether they are called from:

    ┌──────────────┐
    │  REST API    │────┐
    └──────────────┘    │
    ┌──────────────┐    ├──▶ saveEmployee()
    │  Scheduler   │────┤
    └──────────────┘    │
    ┌──────────────┐    │
    │ Kafka Con    │────┘
    └──────────────┘

    ┌──────────────┐
    │  RabbitMQ    │────┐
    └──────────────┘    ├──▶ updateEmployee()
    ┌──────────────┐    │
    │ Another Svc  │────┤
    └──────────────┘    │
    ┌──────────────┐    │
    │  Unit Test   │────┘
    └──────────────┘

It doesn't matter.

╔══════════════════════════════════════════════════════════════════════════════╗
║                           REAL EXAMPLE                                      ║
╚══════════════════════════════════════════════════════════════════════════════╝

Suppose:

    salaryService.generateSalary();

called by @Scheduled ── No HTTP request.

    ┌────────────────────────────────────┐
    │           @Scheduled               │
    │           ⏰ 2 AM daily            │
    └──────────────┬─────────────────────┘
                   │
                   ▼
    ┌────────────────────────────────────┐
    │   salaryService.generateSalary()   │
    └────────────────────────────────────┘

    So:
      ✘ No Filter
      ✘ No Interceptor
      ✔ AOP still works

Another example ── Kafka Consumer:

    ┌────────────────────────────────────┐
    │         Kafka Consumer             │
    │      (Message Listener)            │
    └──────────────┬─────────────────────┘
                   │
                   ▼
    ┌────────────────────────────────────┐
    │   salaryService.generateSalary()   │
    └────────────────────────────────────┘

    Again:
      ✘ No Filter
      ✘ No Interceptor
      ✔ AOP Works

┌─────────────────────────────────────────────────────────────────────────────┐
│  WHY?                                                                       │
│                                                                             │
│  Because AOP is method-level.                                               │
│  Filter/Interceptor are request-level.                                      │
└─────────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║                         QUESTION: LOGGING                                   ║
╚══════════════════════════════════════════════════════════════════════════════╝

Should logging happen only for REST API?
    Obviously, NO.
    Every call should be logged.
    So AOP is perfect.

╔══════════════════════════════════════════════════════════════════════════════╗
║                      QUESTION: AUTHENTICATION                               ║
╚══════════════════════════════════════════════════════════════════════════════╝

Now your question becomes interesting.
You asked: "Authentication is already done using Filters. Why AOP?"

Answer:
Because authentication and authorization happen at different layers.

    ┌─────────────────────────────────────────────────────────────────────┐
    │                    AUTHENTICATION FLOW                              │
    ├─────────────────────────────────────────────────────────────────────┤
    │                                                                     │
    │   Filter                                                             │
    │      │                                                               │
    │      ▼                                                               │
    │   Verify Token                                                       │
    │      │                                                               │
    │      ▼                                                               │
    │   Set User in SecurityContext  ← This is correct for identity       │
    │                                                                     │
    └─────────────────────────────────────────────────────────────────────┘

But suppose:

    approveSalary() should only be executed by HR_ADMIN

Where do you check?

    Option 1:  Inside every service (repeated everywhere) ❌
    ┌─────────────────────────────────────────────┐
    │   if (user.isHRAdmin()) { ... }              │
    │   ── same code in every method               │
    └─────────────────────────────────────────────┘

    Option 2:  Spring Security Method Security     ✔
    ┌─────────────────────────────────────────────┐
    │   @PreAuthorize("hasRole('HR_ADMIN')")       │
    │   Internally implemented using AOP proxies   │
    └─────────────────────────────────────────────┘

So:

    Authentication        ──▶  usually Filter
    Method-level auth     ──▶  often AOP (via Spring Security)

╔══════════════════════════════════════════════════════════════════════════════╗
║                     QUESTION: PERFORMANCE MONITORING                        ║
╚══════════════════════════════════════════════════════════════════════════════╝

Suppose manager asks: "Find methods taking more than 3 seconds."

Should you modify 400 methods?   NO

Just use @Around to measure execution time.

╔══════════════════════════════════════════════════════════════════════════════╗
║                  SPRING FEATURES BUILT ON AOP                               ║
╚══════════════════════════════════════════════════════════════════════════════╝

    ┌─────────────────────────┐
    │  @Transactional         │  ← AOP
    ├─────────────────────────┤
    │  @Cacheable             │  ← AOP
    ├─────────────────────────┤
    │  @Retryable             │  ← AOP
    ├─────────────────────────┤
    │  @Async                 │  ← AOP Proxy
    └─────────────────────────┘

Event Listener ── Many Spring features internally rely on proxying and
interception concepts, and several annotation-driven features such as
@Transactional, @Cacheable, @Retryable, and @Async are implemented using
AOP-style proxies.
*/

/*
╔══════════════════════════════════════════════════════════════════════════════╗
║                                                                              ║
║                     SPRING BOOT DEEP DIVE ── AOP                             ║
║                                                                              ║
║                  Chapter 1 : Why We Need AOP                                 ║
║                                                                              ║
╚══════════════════════════════════════════════════════════════════════════════╝

╔══════════════════════════════════════════════════════════════════════════════╗
║  1. WHAT ARE WE GOING TO LEARN?                                             ║
╚══════════════════════════════════════════════════════════════════════════════╝

Before learning AOP annotations like:

    @Aspect  │  @Before  │  @After  │  @Around

we first need to answer one important interview question:

    ┌─────────────────────────────────────────────────────────────────────┐
    │             "Why was AOP introduced?"                               │
    └─────────────────────────────────────────────────────────────────────┘

Most developers directly start learning annotations.

Interviewers usually don't ask:

    ❌ What is @Before?

Instead they ask:

    ✔ Why do we even need AOP?
    ✔ Which problem does AOP solve?
    ✔ Why can't we simply write normal Java code?
    ✔ What was happening before AOP existed?

If you understand this chapter properly,
the remaining AOP concepts become much easier.

╔══════════════════════════════════════════════════════════════════════════════╗
║  2. SOFTWARE DEVELOPMENT BEFORE AOP                                        ║
╚══════════════════════════════════════════════════════════════════════════════╝

Imagine you are working in a company.

Your project has:

    ┌─────────────────────┐
    │ • Employee Module   │
    │ • Customer Module   │
    │ • Order Module      │
    │ • Payment Module    │
    │ • Leave Management  │
    │ • Attendance Module │
    │ • Salary Module     │
    └─────────────────────┘

Every module has hundreds of service methods.

Example:

    createEmployee()     │  updateEmployee()     │  deleteEmployee()
    approveLeave()       │  rejectLeave()        │  calculateSalary()
    processPayment()

Every method performs its own business logic.

Example ── createEmployee():

    1. Validate input
    2. Save employee
    3. Return response

Looks simple. But in real projects something else also happens.

╔══════════════════════════════════════════════════════════════════════════════╗
║  3. WHAT ACTUALLY HAPPENS IN REAL PROJECTS?                                ║
╚══════════════════════════════════════════════════════════════════════════════╝

Before executing business logic, companies usually perform many additional tasks.

┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│  LOGGING                                                                    │
│      Log.info("Employee creation started");                                 │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  AUTHENTICATION                                                             │
│      Is user logged in?                                                     │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  AUTHORIZATION                                                              │
│      Does user have ADMIN role?                                             │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  PERFORMANCE MONITORING                                                     │
│      How much time did this API take?                                       │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  AUDIT                                                                      │
│      Who modified employee?                                                 │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  EXCEPTION LOGGING                                                          │
│      If exception occurs, store it in ELK/Splunk.                           │
│                                                                             │
├─────────────────────────────────────────────────────────────────────────────┤
│                                                                             │
│  TRANSACTION MANAGEMENT                                                     │
│      Begin Transaction  │  Commit  │  Rollback                              │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║  4. EXAMPLE WITHOUT AOP                                                    ║
╚══════════════════════════════════════════════════════════════════════════════╝

Suppose your service looks like this.

┌─────────────────────────────────────────────────────────────────────────────┐
│  createEmployee()                                                           │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │  logging                                                             │  │
│  │  authentication                                                      │  │
│  │  authorization                                                       │  │
│  │  validation                                                          │  │
│  │  start timer                                                         │  │
│  │  begin transaction                                                   │  │
│  │  save employee         ◀── ONE LINE of actual business logic         │  │
│  │  commit                                                              │  │
│  │  stop timer                                                          │  │
│  │  audit                                                               │  │
│  │  logging                                                             │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘

Now imagine another method.

┌─────────────────────────────────────────────────────────────────────────────┐
│  updateEmployee()                                                           │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │  logging                                                             │  │
│  │  authentication                                                      │  │
│  │  authorization                                                       │  │
│  │  validation                                                          │  │
│  │  start timer                                                         │  │
│  │  begin transaction                                                   │  │
│  │  update employee      ◀── ONE LINE of actual business logic          │  │
│  │  commit                                                              │  │
│  │  stop timer                                                          │  │
│  │  audit                                                               │  │
│  │  logging                                                             │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘

Another method ── deleteEmployee().

┌─────────────────────────────────────────────────────────────────────────────┐
│  deleteEmployee()                                                           │
│  ┌───────────────────────────────────────────────────────────────────────┐  │
│  │  logging                                                             │  │
│  │  authentication                                                      │  │
│  │  authorization                                                       │  │
│  │  validation                                                          │  │
│  │  start timer                                                         │  │
│  │  begin transaction                                                   │  │
│  │  delete employee      ◀── ONE LINE of actual business logic          │  │
│  │  commit                                                              │  │
│  │  stop timer                                                          │  │
│  │  audit                                                               │  │
│  │  logging                                                             │  │
│  └───────────────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────────────────┘

Notice something?

    Business logic is only ONE LINE.
    Everything else is repeated.

╔══════════════════════════════════════════════════════════════════════════════╗
║  5. CODE DUPLICATION                                                        ║
╚══════════════════════════════════════════════════════════════════════════════╝

This repeated code exists in:

    Employee Service    │  Customer Service   │  Product Service
    Order Service       │  Payment Service    │  Attendance Service
    Leave Service       │  Salary Service

Everywhere. This is called:

    ┌─────────────────────────────────────────────┐
    │           CODE DUPLICATION                   │
    └─────────────────────────────────────────────┘

Problems:

    ✔ Difficult to maintain
    ✔ Difficult to debug
    ✔ Difficult to modify
    ✔ Easy to forget

╔══════════════════════════════════════════════════════════════════════════════╗
║  6. REAL LIFE PROJECT EXAMPLE                                              ║
╚══════════════════════════════════════════════════════════════════════════════╝

Imagine your HRMS project.

You have:

    ┌─────────────────────────────────────────────────────────────────────┐
    │                                                                     │
    │  AttendanceService          markAttendance()                        │
    │                                                                     │
    │  RegularizationService      approveRegularization()                 │
    │                                                                     │
    │  LeaveService               applyLeave()                            │
    │                                                                     │
    │  PayrollService             generateSalary()                        │
    │                                                                     │
    └─────────────────────────────────────────────────────────────────────┘

Every method starts with:

    log.info("API Started");

Every method ends with:

    log.info("API Completed");

One day manager says:

    "We now need to log:
        User IP
        Browser
        Execution Time
        Request Id"

Question: How many places will you modify?

Suppose there are 350 Service Methods.
You'll have to modify 350 methods.

    Huge effort.  Huge risk.

╔══════════════════════════════════════════════════════════════════════════════╗
║  7. CROSS-CUTTING CONCERNS                                                  ║
╚══════════════════════════════════════════════════════════════════════════════╝

This is one of the MOST IMPORTANT interview terms.

Definition:
┌─────────────────────────────────────────────────────────────────────────────┐
│                                                                             │
│  A Cross-Cutting Concern is functionality that is needed                    │
│  across multiple modules of an application,                                 │
│  instead of belonging to one specific business feature.                     │
│                                                                             │
└─────────────────────────────────────────────────────────────────────────────┘

Examples:

    ┌─────────────────────────┐
    │  Logging                │
    │  Security               │
    │  Caching                │
    │  Performance Monitoring │
    │  Transactions           │
    │  Auditing               │
    │  Exception Logging      │
    │  Notification           │
    │  Metrics                │
    └─────────────────────────┘

These concerns "cut across" many classes.

Hence the name ── Cross-Cutting Concern.

╔══════════════════════════════════════════════════════════════════════════════╗
║  8. BUSINESS CONCERN vs CROSS-CUTTING CONCERN                               ║
╚══════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────┐
│  BUSINESS CONCERN                    CROSS-CUTTING CONCERN                  │
├─────────────────────────────────────┬───────────────────────────────────────┤
│  ✔ Create Employee                  │  ✔ Logging                            │
│  ✔ Delete Employee                  │  ✔ Authentication                     │
│  ✔ Generate Salary                  │  ✔ Authorization                      │
│  ✔ Apply Leave                      │  ✔ Performance Monitoring             │
│  ✔ Approve Attendance               │  ✔ Auditing                           │
│                                     │  ✔ Transactions                       │
│                                     │  ✔ Caching                            │
├─────────────────────────────────────┴───────────────────────────────────────┤
│  Business concerns are unique.     These are common to many modules.        │
└─────────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║  9. VISUALIZING THE PROBLEM                                                 ║
╚══════════════════════════════════════════════════════════════════════════════╝

Without AOP:

    ┌────────────────────────────────┐
    │       Employee Service         │
    ├────────────────────────────────┤
    │  Logging                       │
    │  Authentication                │
    │  Authorization                 │
    │  Validation                    │
    │  Transaction                   │
    │  Business Logic                │
    │  Logging                       │
    │  Audit                         │
    └────────────────────────────────┘

    ┌────────────────────────────────┐
    │       Customer Service         │
    ├────────────────────────────────┤
    │  Logging                       │
    │  Authentication                │
    │  Authorization                 │
    │  Validation                    │
    │  Transaction                   │
    │  Business Logic                │
    │  Logging                       │
    │  Audit                         │
    └────────────────────────────────┘

Same code... Again... Again... Again...

╔══════════════════════════════════════════════════════════════════════════════╗
║  10. PROBLEMS CAUSED                                                        ║
╚══════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────┐
│  Problem 1   Huge Code Duplication                                         │
├─────────────────────────────────────────────────────────────────────────────┤
│  Problem 2   Maintenance becomes difficult                                  │
├─────────────────────────────────────────────────────────────────────────────┤
│  Problem 3   Business logic becomes difficult to read                       │
├─────────────────────────────────────────────────────────────────────────────┤
│  Problem 4   Higher probability of bugs                                     │
├─────────────────────────────────────────────────────────────────────────────┤
│  Problem 5   Violation of DRY Principle (Don't Repeat Yourself)            │
├─────────────────────────────────────────────────────────────────────────────┤
│  Problem 6   Violation of Single Responsibility Principle (SRP)            │
│              A service should only perform business logic.                  │
│              Instead, it also performs: Logging, Security,                  │
│              Transactions, Caching, Monitoring                              │
└─────────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║  11. REAL LIFE ANALOGY ── SHOPPING MALL                                    ║
╚══════════════════════════════════════════════════════════════════════════════╝

Imagine a shopping mall.

Every customer entering the mall goes through:

    ┌─────────────────────────────────────────────────────────────────────────┐
    │                                                                         │
    │    Security Check  ──▶  Bag Scanning  ──▶  Metal Detector  ──▶  Entry  │
    │                                                                         │
    │                                    │                                     │
    │                                    ▼                                     │
    │                               Shopping                                   │
    │                                                                         │
    └─────────────────────────────────────────────────────────────────────────┘

The customer's purpose is SHOPPING. Not Security Checking.

    Security is a common concern.
    Instead of every shop doing security separately,
    the mall performs security once at the entrance.

AOP works in the same way.

    Instead of every service implementing logging,
    Spring automatically performs logging before your business method executes.

╔══════════════════════════════════════════════════════════════════════════════╗
║  12. ANOTHER REAL LIFE EXAMPLE ── BANK                                     ║
╚══════════════════════════════════════════════════════════════════════════════╝

Imagine a bank. Every transaction goes through:

    ┌─────────────────────────────────────────────┐
    │  Identity Verification                       │
    │         │                                     │
    │         ▼                                     │
    │  Fraud Detection                              │
    │         │                                     │
    │         ▼                                     │
    │  Permission Check                             │
    │         │                                     │
    │         ▼                                     │
    │  Actual Money Transfer                        │
    │         │                                     │
    │         ▼                                     │
    │  Audit Log                                    │
    │         │                                     │
    │         ▼                                     │
    │  SMS Notification                             │
    └─────────────────────────────────────────────┘

The customer only sees: Money Transfer.
The bank internally executes many common tasks.

This is exactly how AOP works.

╔══════════════════════════════════════════════════════════════════════════════╗
║  13. WHAT DEVELOPERS WANTED                                                 ║
╚══════════════════════════════════════════════════════════════════════════════╝

Developers wanted something like this:

    ┌─────────────────────────────────────────────────────────────────────────┐
    │  EmployeeService                                                        │
    │  ─────────────────────────────────────────────────────────────────────  │
    │  createEmployee() {                                                     │
    │      saveEmployee();     ◀── Only business logic                        │
    │  }                                                                      │
    │                                                                         │
    │  No logging.  No transaction code.  No audit.                           │
    │  No timer.  No security code.                                           │
    │  Only business logic.                                                   │
    │                                                                         │
    │  Everything else should happen AUTOMATICALLY.                           │
    └─────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║  14. BIRTH OF AOP                                                           ║
╚══════════════════════════════════════════════════════════════════════════════╝

Aspect-Oriented Programming (AOP) was introduced to separate:

    ┌─────────────────────────────┐
    │     Business Logic          │
    └──────────┬──────────────────┘
               │  from
               ▼
    ┌─────────────────────────────┐
    │  Cross-Cutting Concerns     │
    └─────────────────────────────┘

Instead of writing logging code inside every method,
we write it once. Spring automatically executes it wherever needed.

╔══════════════════════════════════════════════════════════════════════════════╗
║  15. AFTER INTRODUCING AOP                                                  ║
╚══════════════════════════════════════════════════════════════════════════════╝

                    Client Request
                         │
                         ▼
               ┌────────────────────┐
               │  Logging Aspect    │
               └────────┬───────────┘
                         │
                         ▼
               ┌────────────────────┐
               │ Security Aspect    │
               └────────┬───────────┘
                         │
                         ▼
               ┌────────────────────┐
               │Transaction Aspect  │
               └────────┬───────────┘
                         │
                         ▼
               ┌──────────────────────────────┐
               │      Employee Service         │
               │      saveEmployee()           │
               │      ▲  Clean Business Logic  │
               └──────────────────────────────┘

    Business logic stays clean.
    Technical concerns stay outside.

╔══════════════════════════════════════════════════════════════════════════════╗
║  16. KEY BENEFITS                                                           ║
╚══════════════════════════════════════════════════════════════════════════════╝

    ✔ Cleaner Code
    ✔ Better Readability
    ✔ Better Maintainability
    ✔ Reusable Logic
    ✔ Centralized Logging
    ✔ Centralized Security
    ✔ Centralized Exception Handling
    ✔ Better Testing
    ✔ Follows SOLID Principles

╔══════════════════════════════════════════════════════════════════════════════╗
║  17. INTERVIEW QUESTIONS                                                    ║
╚══════════════════════════════════════════════════════════════════════════════╝

┌─────────────────────────────────────────────────────────────────────────────┐
│  Q1. Why was AOP introduced?                                                │
├─────────────────────────────────────────────────────────────────────────────┤
│  Answer: To separate cross-cutting concerns such as logging,                │
│  security, transactions, caching, and auditing                              │
│  from business logic, reducing code duplication                             │
│  and improving maintainability.                                             │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│  Q2. What is a Cross-Cutting Concern?                                       │
├─────────────────────────────────────────────────────────────────────────────┤
│  Answer: A concern that is required across multiple modules                 │
│  of an application rather than belonging to a single                        │
│  business feature.                                                          │
│                                                                             │
│  Examples: Logging, Security, Transactions, Caching, Auditing               │
└─────────────────────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────────────────────┐
│  Q3. Why shouldn't logging be written inside every service?                 │
├─────────────────────────────────────────────────────────────────────────────┤
│  Answer: Because it causes code duplication,                                │
│  violates the DRY principle,                                                │
│  makes maintenance difficult,                                               │
│  and mixes technical concerns with business logic.                          │
└─────────────────────────────────────────────────────────────────────────────┘

╔══════════════════════════════════════════════════════════════════════════════╗
║  18. SUMMARY                                                                ║
╚══════════════════════════════════════════════════════════════════════════════╝

    ✔ AOP exists to solve code duplication.
    ✔ Cross-cutting concerns should not be mixed with business logic.
    ✔ Logging, Security, Transactions, Caching, and Auditing are classic
      cross-cutting concerns.
    ✔ AOP keeps business classes focused only on business logic.
    ✔ Understanding "WHY" AOP exists is the foundation for learning
      everything else in Spring AOP.

╔══════════════════════════════════════════════════════════════════════════════╗
║  NEXT CHAPTER                                                               ║
╚══════════════════════════════════════════════════════════════════════════════╝

    _02_IntroductionToAOP

    In the next chapter we will answer:
        ✔ What exactly is Aspect-Oriented Programming?
        ✔ Is AOP a Spring feature or a Java concept?
        ✔ How does AOP differ from Object-Oriented Programming (OOP)?
        ✔ What are the advantages and limitations of AOP?

╔══════════════════════════════════════════════════════════════════════════════╗
║                          END OF CHAPTER 1                                   ║
╚══════════════════════════════════════════════════════════════════════════════╝
*/
