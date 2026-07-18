package springboot_deep_drive.aop_deep;

public class _10_AOP_InternalWorking_And_InterviewQuestions {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - AOP SERIES**                                          │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │        **Chapter 10 : Internal Working & Interview Questions**                             │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    HOW SPRING AOP WORKS INTERNALLY                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now

we have used



@Before

@After

@Around

@Aspect



Question


What actually happens

inside Spring

when we call



employeeService.saveEmployee();



Let's understand

the complete journey.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                    COMPLETE INTERNAL EXECUTION FLOW
══════════════════════════════════════════════════════════════════════════════════════════════════════



Application Starts

        │

        ▼

Spring scans classes

(@Component,
 @Service,
 @Repository)

        │

        ▼

Spring finds

@Aspect

        │

        ▼

Spring creates

Pointcuts

and Advice metadata

        │

        ▼

Spring creates

Proxy Object

        │

        ▼

Proxy stored inside

IOC Container

        │

        ▼

@Autowired injects

Proxy

NOT Original Object

        │

        ▼

Client calls Method

        │

        ▼

Proxy intercepts call

        │

        ▼

Matching Advice executes

        │

        ▼

Original Method executes

        │

        ▼

Result returned



This is

the complete lifecycle

of Spring AOP.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    STEP 1 : SPRING SCANS CLASSES                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


During application startup


Spring scans



@Service

@Component

@Repository

@Controller

@Aspect



Every discovered class

becomes

a Bean Candidate.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Internal Flow

══════════════════════════════════════════════════════════════════════════════════════════════════════



Application Start

        │

        ▼

Component Scan

        │

        ▼

EmployeeService Found

        │

        ▼

LoggingAspect Found



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                STEP 2 : SPRING ANALYZES THE ASPECT                                               ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Spring reads



@Aspect

public class LoggingAspect



Then checks



@Before

@After

@Around

@Pointcut



Spring stores


✔ Pointcut Expression

✔ Advice Type

✔ Target Methods



Think of it like



Rule Book



"If this method matches,

execute this Advice."



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    STEP 3 : SPRING CREATES PROXY                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Original Object



EmployeeService



        │

        ▼

Spring wraps it

inside



Proxy



ASCII Diagram



                EmployeeService

                      ▲

                      │

              Wrapped By

                      │

                      ▼

             EmployeeService Proxy



This Proxy

is responsible

for running

AOP.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                 STEP 4 : IOC CONTAINER STORES PROXY                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Important


IOC Container

does NOT usually store

the original object.



It stores



Proxy



Diagram



IOC Container

        │

        ▼

EmployeeService Proxy



When someone writes



@Autowired

EmployeeService service;



they receive



EmployeeService Proxy.



Not

the original object.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    STEP 5 : METHOD INVOCATION                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Suppose



service.saveEmployee();



Execution



Client

      │

      ▼

Proxy

      │

      ▼

Check Pointcut

      │

      ▼

Execute Advice

      │

      ▼

Original Method

      │

      ▼

Return Result



Everything

passes

through

the Proxy.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                     STEP 6 : RETURN RESPONSE                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Business Method

finishes

        │

        ▼

After Advice

executes

        │

        ▼

Proxy returns

result

to caller.



Complete.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      INTERNAL OBJECT STRUCTURE                                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



                Client

                  │

                  ▼

        EmployeeService Proxy

                  │

      ┌───────────┼────────────┐

      ▼           ▼            ▼

@Before      @Around       @After

                  │

                  ▼

          EmployeeService

                  │

                  ▼

           Database Layer



This is

the actual architecture

used by Spring AOP.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                       MOST IMPORTANT INTERVIEW QUESTIONS                                          ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Does Spring modify

our original class?



Answer


No.

Spring creates

a Proxy Object

that wraps

the original object.



------------------------------------------------------------


Q2.

Does IOC Container

store

the original object

or Proxy?



Answer


When AOP is applied,

IOC Container

typically stores

the Proxy object,

which internally delegates

to the original bean.



------------------------------------------------------------


Q3.

Who executes

@Before

and

@Around?



Answer


The Spring Proxy

executes

all matching Advice

before invoking

the original method.



------------------------------------------------------------


Q4.

When does Spring

create Proxy Objects?



Answer


During

Application Startup,

while creating

Spring Beans.



------------------------------------------------------------


Q5.

Why is Proxy required?



Answer


Without a Proxy,

Spring has no place

to intercept

method calls

and execute Advice.



------------------------------------------------------------


Q6.

Who decides

whether an Advice

should execute?



Answer


The Proxy

checks the Pointcut.

If the method matches,

the Advice executes.



------------------------------------------------------------


Q7.

What is the sequence

of execution?



Answer



Client

↓

Proxy

↓

Pointcut Match

↓

Advice

↓

Original Method

↓

Return Result



------------------------------------------------------------


Q8.

Does AOP change

business logic?



Answer


No.

Business logic

remains unchanged.

AOP only adds

additional behavior

before,

after,

or around

the method.



------------------------------------------------------------


Q9.

Why is Spring AOP

called Proxy-based AOP?



Answer


Because

Spring creates

Proxy Objects

around Spring Beans

to intercept

method calls.



------------------------------------------------------------


Q10.

What is the most important

rule of Spring AOP?



Answer


If a method call

does not pass

through

the Spring Proxy,

AOP

will never execute.



══════════════════════════════════════════════════════════════════════════════════════════════════════

                    **FINAL MEMORY DIAGRAM**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Application Start

        │

        ▼

Bean Created

        │

        ▼

Aspect Found

        │

        ▼

Proxy Created

        │

        ▼

Proxy Stored

in IOC

        │

        ▼

@Autowired

gets Proxy

        │

        ▼

Method Called

        │

        ▼

Advice Executes

        │

        ▼

Original Method

        │

        ▼

Response Returned



Remember

this one diagram.

It explains

almost every

Spring AOP interview question.

*/