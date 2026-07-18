package java_recap.springboot.annotations.springboot_core;

/*

=========================================================
            @SpringBootApplication
=========================================================

Most Important Spring Boot Annotation.

=========================================================
                DEFINITION
=========================================================

@SpringBootApplication is the main entry point
of Spring Boot application.

It tells Spring Boot:

1. Start Spring Application
2. Perform Component Scanning
3. Enable Auto Configuration
4. Register Beans
5. Start Embedded Server

=========================================================
                EXAMPLE
=========================================================

@SpringBootApplication
public class GameServiceApplication {

    public static void main(String[] args) {

        SpringApplication.run(
            GameServiceApplication.class,
            args
        );

    }

}

=========================================================
                INTERNAL COMPOSITION
=========================================================

@SpringBootApplication internally contains:
--------------------------------------------

@Configuration
@EnableAutoConfiguration
@ComponentScan

=========================================================
                VERY IMPORTANT
=========================================================

@SpringBootApplication is NOT magic.

It is combination of multiple annotations.

=========================================================
                INTERNAL HIERARCHY
=========================================================

@SpringBootApplication
        ↓
--------------------------------
↓               ↓             ↓

@Configuration
@EnableAutoConfiguration
@ComponentScan

=========================================================
                1. @Configuration
=========================================================

Marks class as configuration class.

Allows:
--------
@Bean definitions.

=========================================================
                2. @ComponentScan
=========================================================

Scans current package and sub-packages
for Spring Beans.

=========================================================
                3. @EnableAutoConfiguration
=========================================================

Enables Spring Boot Auto Configuration.

This is core power of Spring Boot.

=========================================================
                WHAT IS AUTO CONFIGURATION?
=========================================================

Spring Boot automatically configures beans
based on:

1. Dependencies in classpath
2. Existing beans
3. application.properties
4. Environment

=========================================================
                EXAMPLE
=========================================================

If dependency exists:
---------------------

spring-boot-starter-web

Spring Boot automatically configures:
--------------------------------------

1. DispatcherServlet
2. Tomcat Server
3. Jackson
4. REST Configuration
5. Error Handling

Without manual configuration.

=========================================================
                INTERNAL WORKING
=========================================================

Application Starts
        ↓
SpringApplication.run()
        ↓
ApplicationContext Created
        ↓
@ComponentScan starts
        ↓
Beans discovered
        ↓
@EnableAutoConfiguration executes
        ↓
Auto-config classes loaded
        ↓
Beans created automatically
        ↓
Embedded server starts
        ↓
Application Ready

=========================================================
                SPRINGAPPLICATION.RUN()
=========================================================

Very important internal API.

=========================================================
                RESPONSIBILITIES
=========================================================

1. Create IOC Container
2. Start ApplicationContext
3. Perform component scan
4. Apply auto configuration
5. Start embedded server
6. Trigger lifecycle events

=========================================================
                EMBEDDED SERVER
=========================================================

Spring Boot automatically starts:
---------------------------------

Tomcat (default)

Can also use:
--------------
Jetty
Undertow

=========================================================
                PACKAGE SCANNING RULE
=========================================================

@SpringBootApplication scans:
------------------------------

Current package + sub-packages

=========================================================
                IMPORTANT INTERVIEW POINT
=========================================================

Always place main class at ROOT package.

=========================================================
                CORRECT STRUCTURE
=========================================================

com.game

    GameApplication.java

    controller/
    service/
    repository/

=========================================================
                WRONG STRUCTURE
=========================================================

com.game.main

Main Class

com.service

Service

=========================================================
                PROBLEM
=========================================================

Service package not scanned.

Bean creation fails.

=========================================================
                AUTO CONFIGURATION INTERNALS
=========================================================

Spring Boot reads:

META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

=========================================================
                WHAT DOES IT CONTAIN?
=========================================================

List of auto configuration classes.

Example:
---------

DataSourceAutoConfiguration
WebMvcAutoConfiguration
SecurityAutoConfiguration

=========================================================
                CONDITIONAL CONFIGURATION
=========================================================

Auto configurations use:
-------------------------

@Conditional annotations

Example:
---------

@ConditionalOnClass
@ConditionalOnMissingBean
@ConditionalOnProperty

=========================================================
                EXAMPLE
=========================================================

If:
----
Jackson library exists

Then:
------
JacksonAutoConfiguration activates.

=========================================================
                BIGGEST ADVANTAGE
=========================================================

Reduces XML/manual configuration massively.

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q:
---
Why Spring Boot became popular?

Answer:
--------
Because of:
-------------
1. Auto Configuration
2. Embedded Servers
3. Minimal setup
4. Production-ready features

=========================================================
                COMMON INTERVIEW QUESTIONS
=========================================================

Q1. What is @SpringBootApplication?

Q2. Which annotations exist internally?

Q3. What is auto configuration?

Q4. How auto configuration works internally?

Q5. What does SpringApplication.run() do?

Q6. Why place main class at root package?

Q7. How Spring Boot starts embedded Tomcat?

Q8. What happens during Spring Boot startup?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Can we use Spring Boot without
@SpringBootApplication?

YES.

Equivalent:
------------

@Configuration
@EnableAutoConfiguration
@ComponentScan

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
Can we disable auto configuration?

YES.

Example:
---------

@SpringBootApplication(
    exclude = DataSourceAutoConfiguration.class
)

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Explain complete startup flow
of Spring Boot.

Answer:
--------
1. main() method executes
2. SpringApplication.run() called
3. IOC Container created
4. Component scanning starts
5. Beans discovered
6. Auto configuration applied
7. Dependencies injected
8. Embedded server starts
9. Application becomes ready

=========================================================
                COMMON MISTAKES
=========================================================

1. Wrong package structure

2. Multiple conflicting beans

3. Not understanding auto configuration

4. Overriding auto-config accidentally

=========================================================
                BEST PRACTICE
=========================================================

1. Keep main class at root package
2. Understand auto configuration
3. Avoid unnecessary manual config
4. Use starter dependencies properly

=========================================================
                SUMMARY
=========================================================

@SpringBootApplication =
--------------------------------

@Configuration
        +
@EnableAutoConfiguration
        +
@ComponentScan

It bootstraps complete Spring Boot application.

=========================================================

*/

public class SpringBootApplicationNotes {
}