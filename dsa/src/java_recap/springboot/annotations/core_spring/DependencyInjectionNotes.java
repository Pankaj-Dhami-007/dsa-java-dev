package java_recap.springboot.annotations.core_spring;

/*

=========================================================
        DEPENDENCY INJECTION ANNOTATIONS
=========================================================

Annotations:
-------------
@Autowired
@Qualifier
@Primary

Core Concepts:
---------------
1. Dependency Injection
2. IOC Container
3. Bean Resolution
4. Loose Coupling
5. Constructor Injection
6. Ambiguous Beans

=========================================================
                WHAT IS DEPENDENCY?
=========================================================

If one class needs another class object,
then second class is dependency.

Example:
---------

UserService needs UserRepository.

@Repository
class UserRepository {

}

@Service
class UserService {

    private UserRepository userRepository;

}

UserRepository is dependency of UserService.

=========================================================
            WHAT IS DEPENDENCY INJECTION?
=========================================================

Spring automatically provides required dependency.

Instead of:
------------

new UserRepository()

Spring injects object automatically.

=========================================================
                WHY IMPORTANT?
=========================================================

Without DI:
-------------
1. Tight coupling
2. Difficult testing
3. Hard maintenance
4. Manual object management

With DI:
---------
1. Loose coupling
2. Better testing
3. Easy maintenance
4. Scalable architecture

=========================================================
                @Autowired
=========================================================

Definition:
------------
@Autowired tells Spring:

"Find required bean from IOC container
and inject it automatically."

=========================================================
                FIELD INJECTION
=========================================================

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

}

=========================================================
                INTERNAL WORKING
=========================================================

Spring:
--------
1. Finds UserRepository bean
2. Creates UserService bean
3. Injects dependency automatically

=========================================================
                HOW SPRING RESOLVES?
=========================================================

Default:
---------
By Type

Meaning:
---------
Spring searches matching class type.

=========================================================
                IMPORTANT
=========================================================

@Autowired internally uses:
----------------------------

Reflection

Spring injects private fields using reflection.

=========================================================
                TYPES OF INJECTION
=========================================================

1. Field Injection
2. Setter Injection
3. Constructor Injection

=========================================================
                1. FIELD INJECTION
=========================================================

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

}

=========================================================
                PROBLEMS
=========================================================

1. Difficult unit testing
2. Reflection based
3. Hidden dependencies
4. Immutability impossible

=========================================================
                2. SETTER INJECTION
=========================================================

@Service
public class UserService {

    private UserRepository repository;

    @Autowired
    public void setRepository(UserRepository repository) {

        this.repository = repository;

    }

}

=========================================================
                USE CASE
=========================================================

Optional dependencies.

=========================================================
                3. CONSTRUCTOR INJECTION
=========================================================

BEST PRACTICE

=========================================================
                EXAMPLE
=========================================================

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;

    }

}

=========================================================
                IMPORTANT
=========================================================

If class has SINGLE constructor,
@Autowired optional from Spring 4.3+

=========================================================
                WHY CONSTRUCTOR INJECTION BEST?
=========================================================

1. Immutable dependencies
2. Easier testing
3. Mandatory dependencies guaranteed
4. Cleaner design
5. Better for interview standards

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q. Which injection is best?

Answer:
--------
Constructor Injection

=========================================================
                MULTIPLE BEANS PROBLEM
=========================================================

Suppose:

interface PaymentService

Two implementations:
---------------------

@Component
class RazorpayService implements PaymentService

@Component
class StripeService implements PaymentService

=========================================================
                PROBLEM
=========================================================

@Autowired
private PaymentService paymentService;

Spring gets confused.

Error:
------
NoUniqueBeanDefinitionException

=========================================================
                SOLUTION 1
                @Qualifier
=========================================================

@Autowired
@Qualifier("stripeService")
private PaymentService paymentService;

=========================================================
                INTERNAL WORKING
=========================================================

1. Spring finds all PaymentService beans
2. Checks qualifier name
3. Injects matching bean

=========================================================
                IMPORTANT
=========================================================

Qualifier uses:
---------------
Bean Name

Default bean names:
-------------------

stripeService
razorpayService

=========================================================
                CUSTOM QUALIFIER
=========================================================

@Component("stripe")
class StripeService implements PaymentService

@Autowired
@Qualifier("stripe")
private PaymentService paymentService;

=========================================================
                SOLUTION 2
                @Primary
=========================================================

@Component
@Primary
class StripeService implements PaymentService

=========================================================
                MEANING
=========================================================

Default preferred bean.

Whenever ambiguity occurs:
---------------------------

Spring injects @Primary bean.

=========================================================
                DIFFERENCE
=========================================================

@Qualifier
------------
Specific bean selection.

@Primary
----------
Default bean selection.

=========================================================
                PRIORITY RULE
=========================================================

@Qualifier > @Primary

Meaning:
---------
Qualifier overrides Primary.

=========================================================
                REAL PROJECT EXAMPLE
=========================================================

Interface:
-----------

NotificationService

Implementations:
----------------

EmailNotificationService
SMSNotificationService
PushNotificationService

Use:
-----
@Qualifier for selecting required implementation.

=========================================================
                OPTIONAL DEPENDENCY
=========================================================

@Autowired(required = false)

Avoids exception if bean missing.

=========================================================
                BUT BEST PRACTICE
=========================================================

Avoid optional dependencies unless necessary.

=========================================================
                CIRCULAR DEPENDENCY
=========================================================

@Service
class A {

    @Autowired
    private B b;

}

@Service
class B {

    @Autowired
    private A a;

}

=========================================================
                PROBLEM
=========================================================

Circular Dependency.

Spring may fail during startup.

=========================================================
                SOLUTION
=========================================================

1. Redesign architecture
2. Use @Lazy (temporary fix)

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is Dependency Injection?

Q2. Difference between IOC and DI?

Q3. How @Autowired works internally?

Q4. Why Constructor Injection preferred?

Q5. Difference between @Qualifier and @Primary?

Q6. What happens if multiple beans exist?

Q7. What is circular dependency?

Q8. Can Spring inject private fields?

Q9. Does @Autowired use reflection?

Q10. Is @Autowired mandatory on constructor?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Can Spring inject dependency without @Autowired?

YES.

If single constructor exists.

Example:
---------

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {

        this.repository = repository;

    }

}

Spring automatically injects dependency.

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Why Constructor Injection over Field Injection?

Answer:
--------
1. Better testability
2. Supports immutability
3. Avoids reflection injection
4. Explicit dependencies
5. Cleaner architecture
6. Recommended by Spring team

=========================================================
                COMMON MISTAKES
=========================================================

1. Using Field Injection everywhere

2. Too many dependencies in one class

3. Circular dependency design

4. Manual object creation using new keyword

Wrong:
-------

UserRepository repo = new UserRepository();

=========================================================
                BEST PRACTICE
=========================================================

Use:
-----

Constructor Injection
        +
final fields

Avoid:
-------
Field Injection in production code.

=========================================================
                SUMMARY
=========================================================

@Autowired
------------
Inject dependency automatically.

@Qualifier
------------
Select specific bean.

@Primary
----------
Default preferred bean.

Constructor Injection
----------------------
Best approach for enterprise applications.

=========================================================

*/

public class DependencyInjectionNotes {
}