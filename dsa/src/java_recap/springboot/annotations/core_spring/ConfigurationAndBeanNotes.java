package java_recap.springboot.annotations.core_spring;

/*

=========================================================
            @Configuration AND @Bean
=========================================================

Core Concepts:
---------------
1. Manual Bean Creation
2. IOC Container
3. Bean Management
4. Spring Proxy Mechanism
5. Singleton Management

=========================================================
                WHY THESE EXIST?
=========================================================

Problem:
---------
Some classes cannot use @Component.

Example:
---------
1. Third-party library classes
2. External SDK classes
3. Complex object creation
4. Conditional object creation

We cannot modify external source code.

Solution:
----------
@Bean + @Configuration

=========================================================
                @Bean
=========================================================

Definition:
------------
@Bean tells Spring:

"Execute this method and store returned object
as Bean inside IOC Container."

=========================================================
                EXAMPLE
=========================================================

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();

    }

}

=========================================================
                INTERNAL WORKING
=========================================================

Application Starts
        ↓
Spring scans @Configuration
        ↓
Spring executes @Bean method
        ↓
Returned object stored as Bean
        ↓
Bean available for Dependency Injection

=========================================================
                VERY IMPORTANT
=========================================================

@Bean is METHOD LEVEL annotation.

@Component is CLASS LEVEL annotation.

=========================================================
                DIFFERENCE
=========================================================

@Component
------------
Automatic Bean Creation

@Bean
-------
Manual Bean Creation

=========================================================
                WHEN TO USE @Bean
=========================================================

1. Third-party classes

Example:
---------

@Bean
public ObjectMapper objectMapper() {

    return new ObjectMapper();

}

2. Custom configuration required

3. Complex object creation logic

4. Conditional creation

=========================================================
                @Configuration
=========================================================

Definition:
------------
@Configuration tells Spring:

"This class contains Bean definitions."

=========================================================
                INTERNAL SECRET
=========================================================

@Configuration internally uses:
--------------------------------

@Component

So config class itself becomes Bean.

=========================================================
                MOST IMPORTANT INTERVIEW QUESTION
=========================================================

Q. Difference between:
----------------------

@Configuration

and

@Component

on config class?

=========================================================
                ANSWER
=========================================================

@Configuration creates CGLIB Proxy.

@Component does NOT.

=========================================================
                WHY PROXY NEEDED?
=========================================================

To maintain Singleton Bean behavior.

=========================================================
                VERY IMPORTANT SCENARIO
=========================================================

@Configuration
public class AppConfig {

    @Bean
    public Engine engine() {

        return new Engine();

    }

    @Bean
    public Car car() {

        return new Car(engine());

    }

}

=========================================================
                QUESTION
=========================================================

Will engine() create new object every time?

NO.

Why?
-----
@Configuration creates proxy class.

Proxy intercepts method call.

Spring returns SAME singleton bean.

=========================================================
                WITHOUT @Configuration
=========================================================

@Component
public class AppConfig {

    @Bean
    public Engine engine() {

        return new Engine();

    }

    @Bean
    public Car car() {

        return new Car(engine());

    }

}

=========================================================
                PROBLEM
=========================================================

engine() method directly executes.

New object created.

Singleton breaks.

=========================================================
                INTERNAL FLOW
=========================================================

@Configuration
        ↓
CGLIB Proxy Created
        ↓
@Bean method intercepted
        ↓
Check bean already exists?
        ↓
YES → Return existing bean
NO  → Create new bean

=========================================================
                DEFAULT BEAN NAME
=========================================================

Method name becomes bean name.

Example:
---------

@Bean
public RestTemplate restTemplate() {

}

Bean Name:
-----------
restTemplate

=========================================================
                CUSTOM BEAN NAME
=========================================================

@Bean("customRestTemplate")

public RestTemplate restTemplate() {

}

=========================================================
                REAL PROJECT EXAMPLES
=========================================================

1. RestTemplate Bean
2. ObjectMapper Bean
3. Kafka Producer Bean
4. SecurityFilterChain Bean
5. ExecutorService Bean
6. BCryptPasswordEncoder Bean

=========================================================
                DEPENDENCY INJECTION
=========================================================

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

}

Spring injects Bean automatically.

=========================================================
                BEAN LIFECYCLE
=========================================================

@Bean method executes
        ↓
Bean Created
        ↓
Bean Initialized
        ↓
Bean Stored in IOC Container
        ↓
Application Uses Bean
        ↓
Bean Destroyed on shutdown

=========================================================
                SINGLETON BEHAVIOR
=========================================================

Default Scope:
---------------

Singleton

Meaning:
---------
Only ONE object per IOC Container.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Difference between @Bean and @Component?

Q2. Why use @Configuration?

Q3. What happens internally in @Configuration?

Q4. Why CGLIB proxy required?

Q5. Can @Bean work without @Configuration?

Q6. Difference between manual and automatic bean creation?

Q7. Why Spring intercepts @Bean methods?

Q8. What is singleton bean?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Can @Bean work inside @Component class?

YES.

But:
-----
Singleton behavior may break due to
absence of proxy mechanism.

Best Practice:
---------------
Use @Bean inside @Configuration only.

=========================================================
                COMMON MISTAKES
=========================================================

1. Using new keyword manually

Wrong:
-------

RestTemplate rt = new RestTemplate();

Inside business logic.

2. Using @Component instead of @Configuration

Can break singleton behavior.

3. Creating heavy objects repeatedly

Bad for performance.

=========================================================
                BEST PRACTICE
=========================================================

Use:
-----

@Component
→ Your own classes

@Bean
→ Third-party/external classes

@Configuration
→ Centralized bean configuration

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Why use @Bean when we already have @Component?

Answer:
--------
@Component works only on classes we control.

@Bean is used when:
1. Source code unavailable
2. Complex creation logic needed
3. External library integration required
4. Dynamic configuration required

=========================================================
                SUMMARY
=========================================================

@Bean
-------
Creates bean manually.

@Configuration
----------------
Defines bean configuration class.

@Configuration uses proxy mechanism
to maintain singleton behavior.

@Bean methods executed by Spring
and returned objects stored inside IOC Container.

=========================================================

*/

public class ConfigurationAndBeanNotes {
}