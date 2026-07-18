package java_recap.springboot.annotations.springboot_core;

/*

=========================================================
    @ComponentScan + @EnableAutoConfiguration
=========================================================

Core Concepts:
---------------
1. Package Scanning
2. Bean Discovery
3. Auto Configuration
4. Conditional Beans
5. Spring Boot Internals

=========================================================
                1. @ComponentScan
=========================================================

Definition:
------------
@ComponentScan tells Spring:

"Scan packages and find Spring Beans."

=========================================================
                WHAT DOES IT SCAN?
=========================================================

Annotations:
-------------

@Component
@Service
@Repository
@Controller
@Configuration

=========================================================
                INTERNAL WORKING
=========================================================

Application Starts
        ↓
@ComponentScan executes
        ↓
Spring scans package hierarchy
        ↓
Finds stereotype annotations
        ↓
Creates Bean Definitions
        ↓
Registers beans inside IOC Container

=========================================================
                DEFAULT BEHAVIOR
=========================================================

Spring scans:
--------------
Current package + sub-packages

=========================================================
                EXAMPLE
=========================================================

package com.game;

@SpringBootApplication
public class GameApplication {

}

=========================================================
                SPRING SCANS
=========================================================

com.game.controller
com.game.service
com.game.repository

=========================================================
                IMPORTANT
=========================================================

Spring will NOT scan:
----------------------

com.other.service

=========================================================
                SOLUTION
=========================================================

@ComponentScan(basePackages = {
    "com.game",
    "com.other"
})

=========================================================
                MULTIPLE PACKAGES
=========================================================

@ComponentScan({
    "com.user",
    "com.payment",
    "com.notification"
})

=========================================================
                INTERNAL DETAIL
=========================================================

Spring uses:
--------------
Reflection + ClassPath Scanning

=========================================================
                PERFORMANCE NOTE
=========================================================

Very large scanning scope can slow startup.

=========================================================
                BEST PRACTICE
=========================================================

Keep scanning scope controlled.

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q:
---
Why main class should stay at root package?

Answer:
--------
Because @ComponentScan scans current package
and all sub-packages only.

=========================================================
                FILTERING
=========================================================

@ComponentScan supports filters.

Example:
---------

@ComponentScan(
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE,
            classes = TestService.class
        )
    }
)

=========================================================
                USE CASE
=========================================================

Exclude:
---------
1. Test beans
2. Experimental beans
3. Temporary modules

=========================================================
                2. @EnableAutoConfiguration
=========================================================

Most powerful Spring Boot feature.

=========================================================
                DEFINITION
=========================================================

Automatically configures Spring application
based on:

1. Dependencies
2. Classpath
3. Existing Beans
4. Properties

=========================================================
                MAIN GOAL
=========================================================

Reduce manual configuration.

=========================================================
                EXAMPLE
=========================================================

Dependency:
------------

spring-boot-starter-data-jpa

Spring Boot automatically configures:
--------------------------------------

1. Hibernate
2. JPA
3. Transaction Manager
4. EntityManager
5. DataSource

=========================================================
                INTERNAL WORKING
=========================================================

@EnableAutoConfiguration
        ↓
Spring Boot reads metadata file
        ↓
Loads auto configuration classes
        ↓
Checks conditions
        ↓
Creates beans automatically

=========================================================
                VERY IMPORTANT
=========================================================

Spring Boot does NOT blindly create beans.

Uses conditions first.

=========================================================
                AUTO CONFIGURATION FILE
=========================================================

Spring Boot reads:
-------------------

META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports

=========================================================
                FILE CONTAINS
=========================================================

List of configuration classes.

Example:
---------

WebMvcAutoConfiguration
DataSourceAutoConfiguration
SecurityAutoConfiguration

=========================================================
                CONDITIONAL ANNOTATIONS
=========================================================

Most important internal mechanism.

=========================================================
                COMMON CONDITIONS
=========================================================

@ConditionalOnClass
@ConditionalOnBean
@ConditionalOnMissingBean
@ConditionalOnProperty

=========================================================
                EXAMPLE
=========================================================

@ConditionalOnClass(DataSource.class)

Meaning:
---------
Only configure if DataSource exists.

=========================================================
                ANOTHER EXAMPLE
=========================================================

@ConditionalOnMissingBean

Meaning:
---------
Create bean only if user has not created one.

=========================================================
                VERY IMPORTANT
=========================================================

User-defined bean gets higher priority
than auto-configured bean.

=========================================================
                REAL EXAMPLE
=========================================================

Spring Boot auto-creates:
--------------------------

ObjectMapper Bean

But if user creates custom ObjectMapper:
-----------------------------------------

Spring Boot backs off.

=========================================================
                WHY IMPORTANT?
=========================================================

Allows customization without modifying framework.

=========================================================
                AUTO CONFIGURATION FLOW
=========================================================

Dependency detected
        ↓
Auto-config class loaded
        ↓
Conditions checked
        ↓
Bean definitions created
        ↓
Beans registered

=========================================================
                DEBUGGING AUTO CONFIG
=========================================================

Use:
-----

debug=true

in application.properties

=========================================================
                WHAT YOU SEE?
=========================================================

Condition Evaluation Report

Shows:
------
1. Which auto configs activated
2. Which failed
3. Why conditions matched/not matched

=========================================================
                DISABLING AUTO CONFIG
=========================================================

@SpringBootApplication(
    exclude = SecurityAutoConfiguration.class
)

=========================================================
                USE CASE
=========================================================

Disable unwanted default configuration.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. How @ComponentScan works internally?

Q2. What packages are scanned?

Q3. What is auto configuration?

Q4. How auto configuration works internally?

Q5. What is AutoConfiguration.imports?

Q6. What are conditional annotations?

Q7. What is @ConditionalOnMissingBean?

Q8. How Spring Boot decides which bean to create?

Q9. How to disable auto configuration?

Q10. Why user bean overrides auto-config bean?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Does @ComponentScan create beans?

Answer:
--------
No directly.

It discovers classes and creates
Bean Definitions.

IOC container later creates beans.

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
Can Spring Boot work without
@EnableAutoConfiguration?

YES.

But:
-----
You must manually configure everything.

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Explain auto configuration internally.

Answer:
--------
1. Spring Boot reads auto-config metadata
2. Loads configuration classes
3. Checks conditions
4. Creates required beans automatically
5. Skips beans already defined by user

=========================================================
                COMMON MISTAKES
=========================================================

1. Wrong package structure

2. Large uncontrolled component scanning

3. Creating duplicate beans

4. Not understanding auto-config conflicts

=========================================================
                BEST PRACTICE
=========================================================

1. Keep root package clean
2. Use starter dependencies properly
3. Avoid unnecessary manual config
4. Understand condition-based loading

=========================================================
                SUMMARY
=========================================================

@ComponentScan
----------------
Finds Spring-managed classes.

@EnableAutoConfiguration
-------------------------
Automatically configures application
based on dependencies and conditions.

Together they form core engine
of Spring Boot.

=========================================================

*/

public class BootInternalAnnotationsNotes {
}