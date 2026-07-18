package java_recap.springboot.annotations.core_spring;

/*

=========================================================
        SPRING INTERNALS IMPORTANT NOTES
=========================================================

Topics:
--------
1. IOC Container
2. Dependency Injection
3. Bean Lifecycle
4. Component Scanning
5. ApplicationContext
6. BeanFactory
7. Spring Startup Flow

=========================================================
                1. IOC CONTAINER
=========================================================

IOC =
------
Inversion Of Control

=========================================================
                WHAT DOES IT MEAN?
=========================================================

Object creation responsibility transferred
from developer to Spring.

=========================================================
                BEFORE SPRING
=========================================================

UserService service =
        new UserService();

Developer manually creates objects.

=========================================================
                WITH SPRING
=========================================================

@Service
class UserService {

}

Spring creates object automatically.

=========================================================
                WHY "INVERSION"?
=========================================================

Because control inverted:
--------------------------

FROM:
------
Developer

TO:
---
Spring Framework

=========================================================
                IOC CONTAINER RESPONSIBILITIES
=========================================================

1. Bean Creation
2. Bean Initialization
3. Dependency Injection
4. Bean Lifecycle Management
5. Bean Destruction
6. Configuration Management

=========================================================
                TYPES OF IOC CONTAINER
=========================================================

1. BeanFactory
2. ApplicationContext

=========================================================
                1. BeanFactory
=========================================================

Basic IOC Container.

Features:
----------
1. Lazy loading
2. Minimal features
3. Old approach

=========================================================
                2. ApplicationContext
=========================================================

Advanced IOC Container.

Most used in Spring Boot.

=========================================================
                EXTRA FEATURES
=========================================================

1. Event Handling
2. AOP Support
3. Internationalization
4. Environment Support
5. BeanPostProcessor
6. Auto Scanning

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q. Difference between BeanFactory
and ApplicationContext?

=========================================================
                ANSWER
=========================================================

BeanFactory:
-------------
Basic container with lazy initialization.

ApplicationContext:
--------------------
Advanced container with enterprise features
and eager singleton initialization.

=========================================================
                2. COMPONENT SCANNING
=========================================================

Spring scans packages for annotations.

=========================================================
                SCANNED ANNOTATIONS
=========================================================

@Component
@Service
@Repository
@Controller
@Configuration

=========================================================
                HOW SCANNING WORKS?
=========================================================

Application Starts
        ↓
@ComponentScan executes
        ↓
Spring scans packages
        ↓
Finds stereotype annotations
        ↓
Creates Bean Definitions
        ↓
Beans stored in IOC Container

=========================================================
                IMPORTANT
=========================================================

@SpringBootApplication internally contains:
--------------------------------------------

@ComponentScan

=========================================================
                PACKAGE RULE
=========================================================

Spring scans:
--------------
Current package + sub-packages

=========================================================
                COMMON MISTAKE
=========================================================

Main class:

com.app

Service class:

com.service

=========================================================
                PROBLEM
=========================================================

Service package outside scanning hierarchy.

Bean not detected.

=========================================================
                SOLUTION
=========================================================

1. Move package correctly

OR

2. Use explicit scanning

@ComponentScan("com.service")

=========================================================
                3. BEAN LIFECYCLE
=========================================================

Most important interview topic.

=========================================================
                COMPLETE LIFECYCLE
=========================================================

1. Container Starts
2. Bean Definition Loaded
3. Bean Created
4. Dependencies Injected
5. BeanPostProcessor Before
6. Init Method Called
7. Bean Ready For Use
8. BeanPostProcessor After
9. Bean Destroyed on shutdown

=========================================================
                VISUAL FLOW
=========================================================

Application Start
        ↓
Component Scan
        ↓
Bean Definition Created
        ↓
Bean Instantiation
        ↓
Dependency Injection
        ↓
@PostConstruct
        ↓
Bean Ready
        ↓
Application Running
        ↓
@PreDestroy
        ↓
Container Shutdown

=========================================================
                BEAN INSTANTIATION
=========================================================

Spring creates object using:
----------------------------

Reflection API

=========================================================
                DEPENDENCY INJECTION
=========================================================

Spring resolves dependencies from IOC Container.

=========================================================
                INIT METHODS
=========================================================

1. @PostConstruct
2. afterPropertiesSet()
3. custom init-method

=========================================================
                DESTROY METHODS
=========================================================

1. @PreDestroy
2. destroy()
3. custom destroy-method

=========================================================
                IMPORTANT
=========================================================

Prototype beans usually NOT managed fully
after creation.

Destroy methods may not execute automatically.

=========================================================
                BEANPOSTPROCESSOR
=========================================================

Allows modifying bean before/after initialization.

Used internally in:
-------------------

1. AOP
2. Transactions
3. Security
4. Proxies

=========================================================
                VERY IMPORTANT
=========================================================

Many Spring features internally use:
------------------------------------

Dynamic Proxy + BeanPostProcessor

=========================================================
                4. SPRING STARTUP FLOW
=========================================================

Main Method
    ↓
SpringApplication.run()
    ↓
IOC Container Created
    ↓
Component Scanning
    ↓
Bean Definitions Loaded
    ↓
Beans Instantiated
    ↓
Dependencies Injected
    ↓
Embedded Server Starts
    ↓
Application Ready

=========================================================
                BEAN DEFINITION
=========================================================

Bean Definition stores metadata:
--------------------------------

1. Class Name
2. Scope
3. Lazy/Eager
4. Dependencies
5. Init Methods
6. Destroy Methods

=========================================================
                EAGER VS LAZY
=========================================================

Singleton:
-----------
Created during startup.

Lazy:
------
Created on first use.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is IOC?

Q2. Why called Inversion Of Control?

Q3. Difference between IOC and DI?

Q4. Difference between BeanFactory
and ApplicationContext?

Q5. Explain Bean Lifecycle.

Q6. What happens during Spring startup?

Q7. How component scanning works?

Q8. What is Bean Definition?

Q9. How Spring creates objects internally?

Q10. What is BeanPostProcessor?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Does Spring create all beans at startup?

Answer:
--------
By default:
-------------
Singleton beans → YES

Prototype beans → NO

@Lazy beans → NO

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
How Spring creates private field injection?

Answer:
--------
Using Reflection API.

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Explain complete Spring bean lifecycle.

Answer:
--------
1. Bean Definition loaded
2. Bean instantiated
3. Dependencies injected
4. BeanPostProcessor before init
5. Init methods execute
6. Bean ready for use
7. BeanPostProcessor after init
8. Destroy methods execute during shutdown

=========================================================
                COMMON MISTAKES
=========================================================

1. Wrong package structure

2. Circular dependencies

3. Manual object creation using new

4. Confusing IOC with DI

=========================================================
                BEST PRACTICE
=========================================================

1. Use constructor injection
2. Keep package hierarchy clean
3. Avoid circular dependency
4. Use singleton for stateless services
5. Understand bean lifecycle deeply

=========================================================
                SUMMARY
=========================================================

IOC:
-----
Spring controls object creation.

DI:
----
Spring injects dependencies automatically.

ApplicationContext:
--------------------
Advanced IOC Container.

Bean Lifecycle:
----------------
Creation → Injection → Init → Use → Destroy

Component Scanning:
-------------------
Spring automatically finds beans.

=========================================================

*/

public class SpringInternalsNotes {
}