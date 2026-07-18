package java_recap.springboot.annotations.springboot_core;

/*

=========================================================
        @ConfigurationProperties
=========================================================

Very Important Enterprise Annotation.

=========================================================
                WHY THIS EXISTS?
=========================================================

Problem with @Value:
--------------------

@Value("${db.url}")
private String url;

@Value("${db.username}")
private String username;

@Value("${db.password}")
private String password;

=========================================================
                PROBLEMS
=========================================================

1. Too much boilerplate
2. Hard maintenance
3. No grouping
4. No validation
5. Poor scalability
6. Difficult testing

=========================================================
                SOLUTION
=========================================================

@ConfigurationProperties

=========================================================
                DEFINITION
=========================================================

@ConfigurationProperties binds multiple
related properties into Java object.

=========================================================
                EXAMPLE
=========================================================

application.yml
----------------

app:
  name: GameService
  version: 1.0
  timeout: 5000

=========================================================
                JAVA CLASS
=========================================================

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private String name;
    private String version;
    private int timeout;

    getters/setters

}

=========================================================
                INTERNAL WORKING
=========================================================

Application Starts
        ↓
Spring loads properties
        ↓
@ConfigurationProperties detected
        ↓
Prefix matched
        ↓
Properties mapped to fields
        ↓
Bean registered in IOC Container

=========================================================
                PREFIX
=========================================================

prefix = "app"

Means:
------
All properties starting with "app"
mapped automatically.

=========================================================
                MAPPING
=========================================================

app.name
        ↓
name field

app.version
        ↓
version field

=========================================================
                RELAXED BINDING
=========================================================

Spring supports multiple formats.

Example:
---------

app-name
app_name
app.name
APP_NAME

All can map to:
---------------

private String appName;

=========================================================
                IMPORTANT
=========================================================

Spring Boot uses Binder API internally.

=========================================================
                VALIDATION SUPPORT
=========================================================

@ConfigurationProperties
supports validation.

=========================================================
                EXAMPLE
=========================================================

@Component
@ConfigurationProperties(prefix = "app")
@Validated
public class AppProperties {

    @NotBlank
    private String name;

    @Min(1000)
    private int timeout;

}

=========================================================
                BENEFIT
=========================================================

Application fails fast if config invalid.

=========================================================
                IMMUTABLE CONFIGURATION
=========================================================

Best Practice for enterprise systems.

=========================================================
                CONSTRUCTOR BINDING
=========================================================

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final String name;

    public AppProperties(String name) {

        this.name = name;

    }

}

=========================================================
                IMPORTANT
=========================================================

Immutable configs are:
-----------------------

1. Thread-safe
2. Cleaner
3. Safer
4. Better for production systems

=========================================================
                ENABLE CONFIGURATION PROPERTIES
=========================================================

Modern Spring Boot:
--------------------

@Component usually enough.

Older style:
-------------

@EnableConfigurationProperties

=========================================================
                NESTED PROPERTIES
=========================================================

application.yml
----------------

app:
  security:
    jwt-secret: abc123
    expiration: 3600

=========================================================
                JAVA
=========================================================

@Component
@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private Security security;

    public static class Security {

        private String jwtSecret;
        private int expiration;

    }

}

=========================================================
                REAL PROJECT EXAMPLES
=========================================================

1. JWT Config
2. Kafka Config
3. Redis Config
4. API URLs
5. Feature Flags
6. Payment Gateway Config
7. Rate Limiting Config

=========================================================
                VS @Value
=========================================================

@Value
--------
Single value injection.

@ConfigurationProperties
-------------------------
Bulk structured binding.

=========================================================
                PERFORMANCE
=========================================================

@ConfigurationProperties more efficient
than repeated @Value usage.

=========================================================
                WHY?
=========================================================

Spring binds properties in bulk.

=========================================================
                TYPE SAFETY
=========================================================

Automatic conversion supported.

Example:
---------

timeout: 5s

Can map to:
------------

Duration timeout;

=========================================================
                SUPPORTED TYPES
=========================================================

1. int
2. long
3. boolean
4. List
5. Map
6. Enum
7. Duration
8. Custom Objects

=========================================================
                INTERVIEW IMPORTANT
=========================================================

Q:
---
Why @ConfigurationProperties preferred
over @Value in enterprise applications?

=========================================================
                ANSWER
=========================================================

1. Better structure
2. Better scalability
3. Validation support
4. Type safety
5. Bulk binding
6. Cleaner maintenance
7. Immutable config support

=========================================================
                METADATA SUPPORT
=========================================================

Spring Boot can generate metadata for IDE support.

Dependency:
------------

spring-boot-configuration-processor

=========================================================
                BENEFIT
=========================================================

IDE auto-completion for custom properties.

=========================================================
                COMMON INTERVIEW QUESTIONS
=========================================================

Q1. What is @ConfigurationProperties?

Q2. Difference between @Value and
@ConfigurationProperties?

Q3. What is relaxed binding?

Q4. How validation works?

Q5. What is constructor binding?

Q6. Why immutable config preferred?

Q7. How nested properties work?

Q8. How Spring binds properties internally?

=========================================================
                TRICKY INTERVIEW QUESTION
=========================================================

Q:
---
Does @ConfigurationProperties inject values
using reflection like @Value?

Answer:
--------
Not exactly.

Spring Boot uses Binder API for structured
property binding.

=========================================================
                ANOTHER IMPORTANT QUESTION
=========================================================

Q:
---
Can @ConfigurationProperties work
without @Component?

YES.

If registered manually using:
------------------------------

@EnableConfigurationProperties

=========================================================
                REAL INTERVIEW ANSWER
=========================================================

Q. Why enterprise projects prefer
@ConfigurationProperties?

Answer:
--------
Because it provides:
1. Structured configuration
2. Validation support
3. Type-safe binding
4. Better maintainability
5. Cleaner code
6. Immutable configuration support

=========================================================
                COMMON MISTAKES
=========================================================

1. Forgetting getters/setters

2. Wrong prefix

3. Using too many scattered @Value fields

4. Not validating critical configs

=========================================================
                BEST PRACTICE
=========================================================

Use:
-----

@Value
→ Small/simple values

@ConfigurationProperties
→ Large grouped configurations

Use immutable config classes whenever possible.

=========================================================
                SUMMARY
=========================================================

@ConfigurationProperties
-------------------------
Binds grouped properties into Java object.

Best for:
----------
Enterprise-level configuration management.

Provides:
-----------
1. Type safety
2. Validation
3. Better structure
4. Cleaner maintenance

=========================================================

*/

public class ConfigurationPropertiesNotes {
}