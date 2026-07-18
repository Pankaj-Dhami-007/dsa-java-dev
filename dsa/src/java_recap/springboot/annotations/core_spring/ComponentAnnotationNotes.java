package java_recap.springboot.annotations.core_spring;

/*

=========================================================
                @Component Annotation
=========================================================

Definition:
------------
@Component is used to tell Spring:

"Create object of this class automatically
 and manage it inside IOC Container."

Spring creates bean automatically.

=========================================================
                WHY WE USE IT
=========================================================

Without Spring:
----------------

UserService service = new UserService();

Manual object creation.

Problems:
---------
1. Tight Coupling
2. Hard to manage
3. Difficult testing
4. No lifecycle management

With Spring:
------------

@Component
class UserService {
}

Spring creates object automatically.

=========================================================
                INTERNAL WORKING
=========================================================

Step 1:
--------
@ComponentScan scans package.

Step 2:
--------
Spring finds classes having:
@Component

Step 3:
--------
Spring creates object (Bean).

Step 4:
--------
Stores bean inside IOC Container.

=========================================================
                WHAT IS BEAN?
=========================================================

Bean =
------
Object managed by Spring IOC Container.

Example:
--------

UserService userService = new UserService();

If Spring creates and manages this object,
then it becomes Bean.

=========================================================
                WHAT IS IOC CONTAINER?
=========================================================

IOC = Inversion Of Control

Meaning:
--------
Object creation responsibility transferred
from programmer to Spring.

Before Spring:
---------------
Developer creates objects.

After Spring:
--------------
Spring creates objects.

=========================================================
                REAL PROJECT EXAMPLE
=========================================================

@Component
public class EmailService {

    public void sendEmail() {

        System.out.println("Email Sent");

    }

}

=========================================================
                HOW SPRING FINDS COMPONENT
=========================================================

Using:
-------

@ComponentScan

@SpringBootApplication already contains:
----------------------------------------

@ComponentScan

So Spring scans all sub-packages.

=========================================================
                IMPORTANT POINT
=========================================================

@Component is generic stereotype annotation.

Other annotations internally use it:
------------------------------------

@Service
@Repository
@Controller

All are special types of @Component.

=========================================================
                EXAMPLE
=========================================================

@Component
public class PaymentService {

    public void pay() {

        System.out.println("Payment Done");

    }

}

=========================================================
                ACCESSING COMPONENT
=========================================================

@Service
public class OrderService {

    @Autowired
    private PaymentService paymentService;

}

Spring injects dependency automatically.

=========================================================
                BEAN NAME
=========================================================

Default Bean Name:
------------------

Class name with small first letter.

Example:
--------

PaymentService

Bean name:
-----------

paymentService

=========================================================
                CUSTOM BEAN NAME
=========================================================

@Component("myPaymentService")

public class PaymentService {

}

=========================================================
                COMMON MISTAKES
=========================================================

1. Class outside package scanning

Problem:
--------
Spring cannot detect bean.

2. Forgetting @Component

Problem:
--------
No bean created.

3. Manual object creation with new keyword

Wrong:
------

PaymentService service = new PaymentService();

This bypasses Spring container.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is @Component?
Q2. Difference between Bean and Object?
Q3. What is IOC Container?
Q4. How does Spring create beans?
Q5. How component scanning works?
Q6. Difference between @Component and @Bean?

=========================================================
                RUNTIME FLOW
=========================================================

Application Start
        ↓
@ComponentScan runs
        ↓
Spring finds @Component
        ↓
Bean Created
        ↓
Stored inside IOC Container
        ↓
Dependency Injected
        ↓
Application Ready

=========================================================
                VERY IMPORTANT
=========================================================

@Component itself does NOT inject dependency.

Dependency Injection happens using:
-----------------------------------

@Autowired

=========================================================
                SUMMARY
=========================================================

@Component tells Spring:

1. Create object automatically
2. Manage object lifecycle
3. Store object inside IOC container
4. Make object available for Dependency Injection

=========================================================

*/

public class ComponentAnnotationNotes {
}