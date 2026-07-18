package springboot_deep_drive.aop_deep;

public class _04_HowSpringAOPWorksInternally {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │                    **SPRING BOOT DEEP DIVE - AOP SERIES**                                 │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │               **Chapter 04 : How Spring AOP Works Internally**                            │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                                                                                ║
║                  SPRING BOOT DEEP DIVE ── AOP INTERNALS                                        ║
║                                                                                                ║
║                  Chapter 04 : HOW SPRING AOP WORKS INTERNALLY                                  ║
║                                                                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               1. WHY SHOULD WE LEARN THIS ?                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose an interviewer asks you


        "How does @Transactional work internally?"


Most developers answer


        Spring creates a proxy.


Question


Who creates the proxy?

When is it created?

Why isn't your original object returned?

Where is the proxy stored?

How does Spring even know
that your class needs a proxy?


Most developers cannot answer these questions.


This chapter answers ALL of them.



┌──────────────────────────────────────────────────────────────────────────────────────────────┐
│                         **By the end of this chapter you will know**                         │
├──────────────────────────────────────────────────────────────────────────────────────────────┤
│                                                                                              │
│  ✔ Spring Application Startup                                                                │
│  ✔ IOC Container Lifecycle                                                                   │
│  ✔ Bean Creation                                                                             │
│  ✔ BeanPostProcessor                                                                         │
│  ✔ Proxy Creation                                                                            │
│  ✔ Why Original Bean Gets Replaced                                                           │
│  ✔ Complete Internal Flow                                                                    │
│                                                                                              │
└──────────────────────────────────────────────────────────────────────────────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              2. BIG PICTURE FIRST                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Before learning internals,

let's first see

the complete flow.



┌──────────────────────────────────────────────────────────────────────────────────────────────┐
│                          COMPLETE SPRING AOP STARTUP FLOW                                    │
└──────────────────────────────────────────────────────────────────────────────────────────────┘


                 ┌─────────────────────────────┐
                 │ SpringApplication.run()     │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ Create ApplicationContext   │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ Scan Components             │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ Create Bean Definitions      │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ Instantiate Beans           │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ Dependency Injection         │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ BeanPostProcessor           │
                 └──────────────┬──────────────┘
                                │
                                ▼
                 ┌─────────────────────────────┐
                 │ Should Proxy Be Created ?   │
                 └───────┬───────────┬─────────┘
                         │           │
                       YES           NO
                         │           │
                         ▼           ▼
          ┌────────────────────┐   Return Original Bean
          │ Create Proxy       │
          └──────────┬─────────┘
                     │
                     ▼
          ┌────────────────────┐
          │ Store Proxy In IOC │
          └──────────┬─────────┘
                     │
                     ▼
          ┌────────────────────┐
          │ Application Ready  │
          └────────────────────┘



Don't worry.

We will explain

EVERY BOX

one by one.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     3. STEP 1 : SpringApplication.run()                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Every Spring Boot application starts here.



                public static void main(String[] args){

                        SpringApplication.run(App.class,args);

                }



Question


What actually happens

after calling

SpringApplication.run() ?



Most developers think

their application starts immediately.


Actually,

Spring performs

dozens of operations

before your first API

can even execute.



══════════════════════════════════════════════════════════════════════════════════════════════════

Theory

══════════════════════════════════════════════════════════════════════════════════════════════════


SpringApplication.run()

is responsible for


✔ Bootstrapping Spring

✔ Creating IOC Container

✔ Reading Configuration

✔ Loading Beans

✔ Creating Beans

✔ Injecting Dependencies

✔ Running BeanPostProcessors

✔ Starting Embedded Tomcat

✔ Finally accepting HTTP requests



So

this single line


        SpringApplication.run()


actually performs

hundreds of framework operations.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         INTERNAL WORKING                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



                  Developer

                        │

                        ▼

         ┌───────────────────────────────┐
         │ SpringApplication.run()       │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │ Create ApplicationContext     │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │ Initialize BeanFactory        │
         └───────────────┬───────────────┘
                         │
                         ▼
         ┌───────────────────────────────┐
         │ Load Bean Definitions         │
         └───────────────────────────────┘



Notice

No Bean has been created yet.

Spring is only

preparing the container.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       4. WHAT IS APPLICATION CONTEXT ?                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


This is another

very important interview topic.



Most developers say


IOC Container.


But

ApplicationContext

is much more than

just an IOC Container.



══════════════════════════════════════════════════════════════════════════════════════════════════

Official Definition

══════════════════════════════════════════════════════════════════════════════════════════════════


ApplicationContext

is the central container

of the Spring Framework.



It is responsible for


✔ Creating Beans

✔ Managing Bean Lifecycle

✔ Dependency Injection

✔ Event Publishing

✔ Resource Loading

✔ Internationalization

✔ AOP Support

✔ Transaction Support



Think of it as

the brain

of Spring.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          VISUAL REPRESENTATION                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



                     Spring Boot Application



                                │

                                ▼



          ┌───────────────────────────────────────────────┐
          │             ApplicationContext                │
          ├───────────────────────────────────────────────┤
          │                                               │
          │   EmployeeService Bean                        │
          │                                               │
          │   AttendanceService Bean                      │
          │                                               │
          │   LeaveService Bean                           │
          │                                               │
          │   PayrollService Bean                         │
          │                                               │
          │   Controller Beans                            │
          │                                               │
          │   Repository Beans                            │
          │                                               │
          │   Aspect Beans                                │
          │                                               │
          └───────────────────────────────────────────────┘



Everything

inside Spring

lives

inside

ApplicationContext.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        REAL PROJECT EXAMPLE                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose your HRMS project contains



        EmployeeService

        AttendanceService

        LeaveService

        PayrollService

        LoggingAspect

        SecurityAspect

        AttendanceController



Question


Who creates all these objects?



Answer


ApplicationContext.



Question


Who stores them?



ApplicationContext.



Question


Who injects them?



ApplicationContext.



This is why

ApplicationContext

is called

the heart

of Spring Framework.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               **Remember**                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Many developers think


SpringApplication.run()

creates beans.


Not exactly.


It creates

ApplicationContext.


ApplicationContext

creates

BeanFactory.


BeanFactory

creates

Beans.



This distinction

is very important

for interviews.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            PART 1 COMPLETE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ BeanDefinition

✔ BeanFactory

✔ Component Scanning

✔ Bean Creation

✔ Dependency Injection

✔ Why Beans are NOT immediately returned

✔ Internal Lifecycle

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         5. WHAT IS A BEAN DEFINITION ?                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Before Spring creates any object,

it first creates something called

                **BeanDefinition**


This is another very common interview question.



Question

Why doesn't Spring directly create the object?



Suppose your application contains



        @Service

        EmployeeService



Can Spring immediately create

EmployeeService?



Answer

NO.



First,

Spring needs information about the class.



For example


✔ Class Name

✔ Package Name

✔ Scope

✔ Lazy Initialization

✔ Constructor Information

✔ Dependencies

✔ Bean Name

✔ Initialization Method

✔ Destroy Method



Spring stores all this information

inside

                BeanDefinition.



══════════════════════════════════════════════════════════════════════════════════════════════════

Think Like This

══════════════════════════════════════════════════════════════════════════════════════════════════


Suppose

you are constructing a building.



Do workers

start construction immediately?



NO.



First,

they prepare



Blueprint



The blueprint contains



Floor Plan

Room Size

Electrical Plan

Water Pipeline

Door Position

Window Position



After the blueprint is ready,

construction begins.



Exactly similarly



BeanDefinition

is the blueprint

of a Spring Bean.



It is NOT

the actual object.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERNAL WORKING                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



              Spring Starts

                    │

                    ▼

      ┌──────────────────────────────┐
      │ Scan @Component Classes      │
      └──────────────┬───────────────┘
                     │
                     ▼
      ┌──────────────────────────────┐
      │ Read Class Metadata          │
      └──────────────┬───────────────┘
                     │
                     ▼
      ┌──────────────────────────────┐
      │ Create BeanDefinition        │
      └──────────────┬───────────────┘
                     │
                     ▼
      ┌──────────────────────────────┐
      │ Store BeanDefinition         │
      └──────────────────────────────┘


Notice

Still

NO OBJECT

has been created.



Only metadata exists.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         REAL PROJECT EXAMPLE                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose



@Service

public class AttendanceService{

}



Spring creates



AttendanceService BeanDefinition



NOT

AttendanceService object.



The object

will be created later.



══════════════════════════════════════════════════════════════════════════════════════════════════

**Remember**

══════════════════════════════════════════════════════════════════════════════════════════════════


BeanDefinition

≠

Bean



BeanDefinition

contains information

about the Bean.



Bean

is the actual object.






╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          6. WHAT IS BEAN FACTORY ?                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Now another question.


Question


Who creates the Bean?



Answer


                BeanFactory.



BeanFactory

is the actual factory

responsible for creating

Spring Beans.



Think of BeanFactory as



                Factory Manager.



It receives

BeanDefinition.



It produces

Bean Objects.



══════════════════════════════════════════════════════════════════════════════════════════════════

REAL LIFE ANALOGY

══════════════════════════════════════════════════════════════════════════════════════════════════


Imagine

a Car Factory.



Blueprint

↓

Factory

↓

Car



Similarly



BeanDefinition

↓

BeanFactory

↓

Bean



Blueprint

doesn't manufacture

the car.



Factory

does.



Exactly same concept.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERNAL FLOW                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



            BeanDefinition

                    │

                    ▼

      ┌──────────────────────────────┐
      │        BeanFactory           │
      ├──────────────────────────────┤
      │                              │
      │ Read Metadata                │
      │                              │
      │ Create Object                │
      │                              │
      │ Call Constructor             │
      │                              │
      │ Return Bean                  │
      │                              │
      └──────────────┬───────────────┘
                     │
                     ▼
           EmployeeService Bean



Notice

BeanFactory

never scans packages.



It only creates objects.



Scanning

is somebody else's job.



We'll learn that next.






╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         7. COMPONENT SCANNING                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Question


How does Spring know

EmployeeService exists?



Nobody manually tells Spring.



Spring automatically

searches your project.



This process is called



        **Component Scanning**



══════════════════════════════════════════════════════════════════════════════════════════════════

What does Component Scan search?

══════════════════════════════════════════════════════════════════════════════════════════════════


@Component

@Service

@Repository

@Controller

@RestController

@Configuration

@Aspect



Every class

having one of these annotations

becomes a candidate

for Spring Bean creation.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE COMPONENT SCAN FLOW                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



             Spring Boot Starts

                      │

                      ▼

      ┌────────────────────────────────┐
      │ Component Scanner              │
      └──────────────┬─────────────────┘
                     │
                     ▼

      Searches Entire Package Structure

                     │

                     ▼

┌────────────────────────────────────────────────────────────┐
│                                                            │
│ EmployeeService     ✔                                      │
│ AttendanceService   ✔                                      │
│ PayrollService      ✔                                      │
│ LeaveService        ✔                                      │
│ LoggingAspect       ✔                                      │
│ EmployeeController  ✔                                      │
│ UserRepository      ✔                                      │
│                                                            │
└────────────────────────────────────────────────────────────┘

                     │

                     ▼

        Create BeanDefinition

                     │

                     ▼

             BeanFactory



Notice

Component Scanner

does NOT create Beans.



It only

finds classes.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHO DOES WHAT ? (VERY IMPORTANT)                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────────────┬──────────────────────────────────────────────────────────────┐
│ Spring Component             │ Responsibility                                               │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ Component Scanner            │ Finds annotated classes                                      │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ BeanDefinition               │ Stores bean metadata                                         │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ BeanFactory                  │ Creates bean objects                                         │
├──────────────────────────────┼──────────────────────────────────────────────────────────────┤
│ ApplicationContext           │ Manages everything                                           │
└──────────────────────────────┴──────────────────────────────────────────────────────────────┘




╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              INTERVIEW QUESTIONS                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1

What is BeanDefinition?



Answer


BeanDefinition is a metadata object

that stores all configuration

required to create a Spring Bean.



------------------------------------------------------


Q2

What is BeanFactory?



Answer


BeanFactory is responsible

for creating

and managing Spring Beans.



------------------------------------------------------


Q3

Does Component Scanner create Beans?



NO.



It only discovers

candidate classes.



BeanFactory

creates the Bean.



------------------------------------------------------


Q4

Difference between BeanDefinition and Bean?



BeanDefinition

contains metadata.



Bean

is the actual object.



╔════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              PART 2 COMPLETE                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Dependency Injection

✔ Bean Lifecycle

✔ BeanPostProcessor

✔ Why BeanPostProcessor is called "Post"

✔ Where AOP enters the lifecycle

✔ Proxy Creation Starts Here

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          8. WHAT IS DEPENDENCY INJECTION (DI) ?                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Before Spring can apply AOP,

it must first create complete Bean objects.

That includes injecting all required dependencies.



Example



@Service
public class EmployeeService{

        @Autowired
        private EmployeeRepository repository;

}



Question

Can Spring immediately return EmployeeService?



NO.



EmployeeRepository

must first be created,

then injected into

EmployeeService.



Only then

EmployeeService

becomes a complete Bean.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Complete Internal Flow

══════════════════════════════════════════════════════════════════════════════════════════════════════



┌───────────────────────────────────────────────────────────────────────────────────────────────┐
│                        SPRING DEPENDENCY INJECTION FLOW                                       │
└───────────────────────────────────────────────────────────────────────────────────────────────┘


          EmployeeRepository Bean
                     │
                     ▼
      ┌────────────────────────────┐
      │ EmployeeService Bean       │
      │                            │
      │ repository = NULL          │
      └──────────────┬─────────────┘
                     │
             Dependency Injection
                     │
                     ▼
      ┌────────────────────────────┐
      │ EmployeeService Bean       │
      │                            │
      │ repository = Bean          │
      └──────────────┬─────────────┘
                     │
                     ▼
             Bean Ready



**Remember**

AOP NEVER works on an incomplete Bean.

Spring first creates

a fully initialized Bean,

then decides

whether a Proxy is required.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              9. SPRING BEAN LIFECYCLE                                            ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


This lifecycle is extremely important.

Every Spring Bean

passes through

these stages.



┌──────────────────────────────────────────────────────────────────────────────────────────────────┐
│                             COMPLETE SPRING BEAN LIFECYCLE                                      │
└──────────────────────────────────────────────────────────────────────────────────────────────────┘



                  Component Scan
                         │
                         ▼
              BeanDefinition Created
                         │
                         ▼
                 Bean Instantiated
                         │
                         ▼
              Dependency Injection
                         │
                         ▼
             Aware Interfaces Called
                         │
                         ▼
               BeanPostProcessor
                         │
                         ▼
               Initialization Methods
                         │
                         ▼
               Bean Ready To Use
                         │
                         ▼
                 Destroy Method



Notice carefully.


AOP

does NOT happen

during Component Scan.


AOP

does NOT happen

during BeanDefinition creation.


AOP starts around

BeanPostProcessor.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Interview Question

══════════════════════════════════════════════════════════════════════════════════════════════════════


Question


At which stage

does Spring decide

whether a Bean

should become a Proxy?



Answer


During BeanPostProcessor execution.



This is an extremely common interview question.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             10. WHAT IS BeanPostProcessor ?                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Finally...

we have reached

the most important concept.



Almost every Spring feature

internally depends on

BeanPostProcessor.



Examples


✔ AOP

✔ @Transactional

✔ @Async

✔ @Cacheable

✔ Validation

✔ Security

✔ Custom Bean Processing



══════════════════════════════════════════════════════════════════════════════════════════════════════

Official Definition

══════════════════════════════════════════════════════════════════════════════════════════════════════


**BeanPostProcessor is a Spring extension point that allows custom
processing of Beans before and after initialization.**



Still sounds difficult?



Let's simplify.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Simple Definition

══════════════════════════════════════════════════════════════════════════════════════════════════════


Think of BeanPostProcessor

as

Spring's Inspection Department.



Whenever Spring creates

any Bean,

it sends it

to BeanPostProcessor.



BeanPostProcessor checks



Should this Bean be modified?

Should this Bean become a Proxy?

Should extra behaviour be added?

Should annotations be processed?



If yes,

it modifies

the Bean.



Otherwise,

it simply returns it.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             REAL LIFE ANALOGY                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Imagine

a Car Manufacturing Company.



Every car

comes out

from the assembly line.



But before

it reaches customers,

it goes through

Quality Inspection.



                    Car Manufactured
                            │
                            ▼
              ┌──────────────────────────┐
              │ Quality Inspection Team  │
              └─────────────┬────────────┘
                            │
                Is everything OK ?
                    ┌───────┴────────┐
                    │                │
                   YES              NO
                    │                │
                    ▼                ▼
             Deliver Car        Fix Problems



BeanPostProcessor

works exactly

like

Quality Inspection.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             INTERNAL SPRING FLOW                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                  Bean Created
                        │
                        ▼
          ┌──────────────────────────────┐
          │ BeanPostProcessor            │
          ├──────────────────────────────┤
          │                              │
          │ Inspect Bean                 │
          │                              │
          │ Check Annotations            │
          │                              │
          │ Modify Bean If Needed        │
          │                              │
          │ Return Bean                  │
          │                              │
          └──────────────┬───────────────┘
                         │
                         ▼
                  Bean Stored In IOC



This is

where AOP

enters

the Bean Lifecycle.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          WHY IS IT CALLED "POST" PROCESSOR ?                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Many interviewers ask this.



Question


Why isn't it called

BeanProcessor?



Why

BeanPostProcessor?



Answer


Because

it executes

AFTER

the Bean has been instantiated.



Flow



Bean Instantiated

        │

        ▼

Dependency Injection

        │

        ▼

BeanPostProcessor

        │

        ▼

Initialization Complete



The Bean

already exists.



BeanPostProcessor

is not responsible

for creating

the Bean.



It is responsible

for processing

the Bean.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              **Remember**                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


BeanFactory

creates Beans.



BeanPostProcessor

modifies Beans.



Never confuse

these two.



══════════════════════════════════════════════════════════════════════════════════════════════════════

BeanFactory

↓

Creates Object



BeanPostProcessor

↓

Processes Object



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               PART 3 COMPLETE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ AnnotationAwareAspectJAutoProxyCreator

✔ How Spring Detects @Aspect

✔ Advisor Creation

✔ Proxy Creation Starts

✔ Why Original Bean Gets Replaced

*/


/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                 11. AnnotationAwareAspectJAutoProxyCreator (AAAPC)                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Don't panic after seeing this long class name.

Let's divide it.



          Annotation

                 +

            Aware

                 +

           AspectJ

                 +

         Auto Proxy Creator



Meaning


It is a Spring class which

✔ Understands AspectJ annotations

✔ Detects @Aspect classes

✔ Automatically creates Proxy Objects



**Remember**

This is the heart of Spring AOP.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Question

══════════════════════════════════════════════════════════════════════════════════════════════════════


Until now we know

BeanPostProcessor

checks every Bean.



But

who inside Spring

checks

whether

EmployeeService

needs a Proxy?



Answer


AnnotationAwareAspectJAutoProxyCreator



BeanPostProcessor

is just an interface.



This class

implements

the AOP logic.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     WHERE DOES THIS CLASS FIT ?                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                    Spring Startup

                           │

                           ▼

             BeanFactory Creates Bean

                           │

                           ▼

               BeanPostProcessor Phase

                           │

                           ▼

┌────────────────────────────────────────────────────────────┐
│ AnnotationAwareAspectJAutoProxyCreator                     │
├────────────────────────────────────────────────────────────┤
│                                                            │
│   Is this Bean eligible for AOP ?                          │
│                                                            │
│   Does any Pointcut match ?                                │
│                                                            │
│   Should Proxy be created ?                                │
│                                                            │
└──────────────────────────────┬─────────────────────────────┘
                               │
                     Yes        │       No
                               │
             ┌─────────────────┘
             ▼
      Create Proxy

                               │

                               ▼

                     Store Proxy In IOC



This class

is responsible

for deciding

whether

Proxy creation

is required.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       INTERNAL THINKING OF SPRING                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Imagine

Spring

thinking internally.



EmployeeService Bean Arrives



                │

                ▼



"Is this Bean

an Aspect?"



        │

   Yes  │  No

        │

        ▼



"Does any Aspect

target

this Bean?"



        │

   Yes  │  No

        │

        ▼



"Create Proxy"



Otherwise



Return Original Bean.



Spring performs

similar checks

for every Bean

during startup.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    REAL PROJECT EXAMPLE                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Suppose



@Service

AttendanceService



contains



markAttendance()



approveAttendance()



and



@Aspect

LoggingAspect



contains



@Before(...)



Spring internally

asks



┌─────────────────────────────────────────────────────────────────────────────┐
│ Does LoggingAspect apply to AttendanceService ?                             │
└─────────────────────────────────────────────────────────────────────────────┘



If


YES



AttendanceServiceProxy

is created.



Otherwise



AttendanceService

is returned

without proxy.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                   COMPLETE INTERNAL FLOW                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                 Bean Created

                      │

                      ▼

┌──────────────────────────────────────┐
│ AnnotationAwareAspectJAutoProxyCreator│
└───────────────────┬──────────────────┘
                    │
                    ▼

        Is Bean Infrastructure Bean?

                    │
             Yes    │     No
                    │
                    ▼

          Skip Proxy Creation

                    │

                    ▼

        Find Matching Advisors

                    │

                    ▼

       Any Advisor Found ?

             │
       ┌─────┴─────┐
       │           │
      NO          YES
       │           │
       ▼           ▼

Return Original   Create Proxy
Bean              Object

                    │
                    ▼

          Return Proxy Bean



Notice carefully.


Spring

does NOT

create Proxy

for every Bean.



Only

eligible Beans

receive Proxy Objects.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Question

══════════════════════════════════════════════════════════════════════════════════════════════════════


How does Spring know

which Bean

requires Proxy?



Answer


By checking

whether

any Advisor

matches

that Bean.



We'll study Advisors

next.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    12. HOW DOES SPRING FIND @Aspect ?                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose


@Aspect

@Component

public class LoggingAspect{


}



Question


How does Spring know

that this class

is an Aspect?



Answer



During Component Scan.



@Component

registers it

as a Bean.



Later,

AnnotationAwareAspectJAutoProxyCreator

checks


"Does this Bean

contain

@Aspect?"



If yes,

Spring stores it

as an Aspect Bean.



ASCII Flow



Component Scan

       │

       ▼

LoggingAspect Bean

       │

       ▼

Check @Aspect ?

       │

       ▼

YES

       │

       ▼

Register As Aspect



Now

Spring knows

this Bean

contains

Advices.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  WHAT HAPPENS AFTER FINDING @Aspect ?                                           ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



After finding

Aspect Beans,

Spring starts reading


@Before

@After

@Around

@Pointcut



annotations.



It converts

them into

internal Spring objects.



Example



@Before("execution(...)")



becomes



Advice Object



Pointcut Object



Advisor Object



These internal objects

are later used

during Proxy Creation.



══════════════════════════════════════════════════════════════════════════════════════════════════════

**Remember**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Spring never executes

annotations directly.



It first converts

annotations

into internal objects.



Those objects

drive

the AOP engine.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Q1.

Who creates AOP Proxy?



Answer


AnnotationAwareAspectJAutoProxyCreator.



--------------------------------------------------------


Q2.

Is BeanPostProcessor

responsible for Proxy creation?



Answer


Indirectly.


BeanPostProcessor

provides

the lifecycle hook.


AnnotationAwareAspectJAutoProxyCreator

implements

the AOP logic.



--------------------------------------------------------


Q3.

Does Spring create

Proxy for every Bean?



Answer


No.


Only eligible Beans

whose methods match

an Advisor

receive Proxy Objects.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                PART 4 COMPLETE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ Advisor Creation

✔ ProxyFactory

✔ JDK Dynamic Proxy

✔ CGLIB Proxy

✔ Why Original Bean Is Replaced

✔ Complete End-to-End Startup Flow

*/

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         13. HOW DOES SPRING CREATE AN ADVISOR ?                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


In previous section we learned

        @Aspect
              │
              ▼
 AnnotationAwareAspectJAutoProxyCreator
              │
              ▼
 Detect Aspect Beans


Question

After Spring finds an @Aspect,

what happens next?



The answer is

                **Advisor Creation**



══════════════════════════════════════════════════════════════════════════════════════════════════════

First Let's Recall

══════════════════════════════════════════════════════════════════════════════════════════════════════


Advisor = Advice + Pointcut



Example



@Before("execution(* *.service.*.*(..))")

public void beforeLog(){}



Spring does NOT store

this annotation

as it is.



Instead,

it converts it into



┌───────────────────────────────────────────────────────────────┐
│                       Advisor Object                          │
├───────────────────────────────────────────────────────────────┤
│                                                               │
│ Advice   → beforeLog()                                        │
│                                                               │
│ Pointcut → execution(* *.service.*.*(..))                     │
│                                                               │
└───────────────────────────────────────────────────────────────┘



Every Advice

becomes

one Advisor.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          INTERNAL CONVERSION                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Developer writes



@Aspect

public class LoggingAspect{


    @Before("execution(* *.service.*.*(..))")
    public void beforeLog(){}

}



Spring internally converts it



                LoggingAspect

                       │

                       ▼

        Read @Before Annotation

                       │

                       ▼

      Read Pointcut Expression

                       │

                       ▼

        Create Advice Object

                       │

                       ▼

       Create Pointcut Object

                       │

                       ▼

          Combine Both

                       │

                       ▼

              Advisor Created



Notice



Spring never executes

@Before

directly.



Everything becomes

internal objects.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         WHY DOES SPRING CREATE ADVISORS ?                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose

you have



LoggingAspect



PerformanceAspect



SecurityAspect



TransactionAspect



Question


How will Spring know

which Advice

belongs to

which Pointcut?



Answer


Advisor.



Each Advisor

contains



"What to Execute"

+

"Where to Execute"



Without Advisor,

Spring would have to

search every annotation

every time

a method executes.



That would be

very slow.



Advisor solves

this problem.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE INTERNAL FLOW                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                @Aspect Bean

                      │

                      ▼

      ┌─────────────────────────────────┐
      │ Scan Advice Methods             │
      └───────────────┬─────────────────┘
                      │
                      ▼
      ┌─────────────────────────────────┐
      │ Read Pointcut Expression        │
      └───────────────┬─────────────────┘
                      │
                      ▼
      ┌─────────────────────────────────┐
      │ Create Advice                   │
      └───────────────┬─────────────────┘
                      │
                      ▼
      ┌─────────────────────────────────┐
      │ Create Pointcut                 │
      └───────────────┬─────────────────┘
                      │
                      ▼
      ┌─────────────────────────────────┐
      │ Combine Both                    │
      └───────────────┬─────────────────┘
                      │
                      ▼
      ┌─────────────────────────────────┐
      │ Advisor Ready                   │
      └─────────────────────────────────┘



Spring creates

one Advisor

for every Advice.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       14. HOW DOES SPRING DECIDE                                                ║
║                          WHETHER A PROXY IS REQUIRED ?                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Now Spring has


✔ Bean

✔ Advisors


Question


Should every Bean

receive a Proxy?



NO.



Only matching Beans

receive Proxy.






┌──────────────────────────────────────────────────────────────────────────────────────────────────┐
│                         PROXY ELIGIBILITY CHECK                                                  │
└──────────────────────────────────────────────────────────────────────────────────────────────────┘



                  EmployeeService Bean

                           │

                           ▼

          ┌───────────────────────────────────┐
          │ Find All Advisors                 │
          └────────────────┬──────────────────┘
                           │
                           ▼
          ┌───────────────────────────────────┐
          │ Does Pointcut Match ?             │
          └───────────────┬───────────────────┘
                          │
                YES       │        NO
                          │
                          ▼

               Create Proxy

                          │

                          ▼

              Store Proxy Bean



If

no Advisor matches,

Spring simply returns

the original Bean.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Example

══════════════════════════════════════════════════════════════════════════════════════════════════════



@Before(

execution(* *.service.*.*(..))

)



EmployeeService

↓

Matches

↓

Proxy Created



----------------------------------------------------



EmployeeController

↓

Does Not Match

↓

No Proxy



----------------------------------------------------



UtilityClass

↓

Does Not Match

↓

No Proxy



This improves performance.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                          15. ProxyFactory                                                       ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Question


Who actually creates

the Proxy Object?



Answer



                ProxyFactory



Think of it as



Spring's Proxy Machine.



It receives


✔ Target Bean

✔ Advisors



It returns


Proxy Bean.



══════════════════════════════════════════════════════════════════════════════════════════════════════

INTERNAL FLOW

══════════════════════════════════════════════════════════════════════════════════════════════════════



                  Target Bean

                        │

                        ▼

                 ProxyFactory

                        │

      ┌─────────────────┼──────────────────┐
      │                 │                  │

      ▼                 ▼                  ▼

 Read Advisors    Choose Proxy Type    Generate Proxy

      │                 │                  │
      └─────────────────┼──────────────────┘
                        │
                        ▼

                 Proxy Bean Ready



ProxyFactory

does NOT decide

whether AOP

is required.



It simply

creates

the Proxy.



Decision

was already taken

by

AnnotationAwareAspectJAutoProxyCreator.






╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    COMPLETE FLOW SO FAR                                                         ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



SpringApplication.run()

        │

        ▼

ApplicationContext

        │

        ▼

Component Scan

        │

        ▼

BeanDefinition Created

        │

        ▼

BeanFactory Creates Bean

        │

        ▼

Dependency Injection

        │

        ▼

BeanPostProcessor

        │

        ▼

AnnotationAwareAspectJAutoProxyCreator

        │

        ▼

Detect @Aspect Beans

        │

        ▼

Create Advisors

        │

        ▼

Match Pointcuts

        │

        ▼

ProxyFactory

        │

        ▼

Create Proxy

        │

        ▼

Store Proxy Inside IOC

        │

        ▼

Application Ready



**Remember**

Up to this point,

Spring has NOT yet decided

whether to use

JDK Dynamic Proxy

or

CGLIB Proxy.

That decision happens next.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              INTERVIEW QUESTIONS                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Why does Spring create an Advisor?


Answer

To combine

Advice

and

Pointcut

into one reusable object that can efficiently determine what to execute and where.



------------------------------------------------------------


Q2.

Who creates Proxy Objects?


Answer

ProxyFactory.



------------------------------------------------------------


Q3.

Does ProxyFactory decide whether AOP should be applied?


Answer

No.

AnnotationAwareAspectJAutoProxyCreator decides.

ProxyFactory only creates the Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                PART 5 COMPLETE                                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Next Part

✔ JDK Dynamic Proxy

✔ CGLIB Proxy

✔ Why Spring chooses one over another

✔ Original Bean Replacement

✔ Complete End-to-End Execution Flow

*/

