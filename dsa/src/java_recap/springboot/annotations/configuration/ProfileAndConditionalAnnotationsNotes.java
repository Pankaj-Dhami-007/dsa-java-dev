package java_recap.springboot.annotations.configuration;

/*

=================================================================
    PROFILE & CONDITIONAL ANNOTATIONS
=================================================================

Annotations:
-------------
@Profile
@Conditional
@ConditionalOnProperty
@ConditionalOnMissingBean

Core Concepts:
---------------
1. Environment-based Configuration
2. Conditional Bean Loading
3. Auto Configuration
4. Feature Flags
5. Spring Boot Internals
6. Dynamic Bean Registration
7. Deployment Profiles

=================================================================
                WHY THESE ANNOTATIONS EXIST?
=================================================================

Applications behave differently
in different environments.

=================================================================
                EXAMPLES
=================================================================

Development:
--------------

1. H2 database
2. Debug logs
3. Mock APIs

Production:
-------------

1. MySQL/PostgreSQL
2. Optimized logging
3. Real APIs

=================================================================
                PROBLEM
=================================================================

Without profiles/conditions:
-----------------------------

Huge manual configuration management.

=================================================================
                RESULT
=================================================================

1. Deployment mistakes
2. Wrong beans loaded
3. Security issues
4. Environment conflicts

=================================================================
                SOLUTION
=================================================================

Conditional bean loading.

=================================================================
                WHAT IS PROFILE?
=================================================================

Profile =
----------
Named environment configuration.

=================================================================
                COMMON PROFILES
=================================================================

dev
test
staging
prod

=================================================================
                1. @Profile
=================================================================

Most important environment annotation.

=================================================================
                DEFINITION
=================================================================

@Profile tells Spring:

"Load this bean only for specific profile."

=================================================================
                EXAMPLE
=================================================================

@Profile("dev")
@Component
public class DevPaymentService {

}

=================================================================
                WHAT HAPPENS?
=================================================================

Bean created ONLY if:
----------------------

Active profile = dev

=================================================================
                INTERNAL WORKING
=================================================================

Application Starts
        ↓
Spring checks active profiles
        ↓
@Profile conditions evaluated
        ↓
Matching beans registered
        ↓
Non-matching beans skipped

=================================================================
                ACTIVE PROFILE
=================================================================

Defined using:
---------------

application.properties

=================================================================
                EXAMPLE
=================================================================

spring.profiles.active=dev

=================================================================
                REAL PROJECT USE CASE
=================================================================

Development:
--------------

Fake email service

Production:
-------------

Real SMTP service

=================================================================
                MULTIPLE PROFILES
=================================================================

@Profile({"dev","test"})

=================================================================
                MEANING
=================================================================

Bean loads in:
---------------

dev OR test

=================================================================
                NEGATIVE PROFILE
=================================================================

@Profile("!prod")

=================================================================
                MEANING
=================================================================

Load everywhere except production.

=================================================================
                VERY IMPORTANT
=================================================================

Spring Boot auto-configurations
also heavily use profiles internally.

=================================================================
                PROFILE-SPECIFIC FILES
=================================================================

application-dev.properties

application-prod.properties

=================================================================
                WHY IMPORTANT?
=================================================================

Different environment settings.

=================================================================
                EXAMPLES
=================================================================

Dev DB:
--------

H2

Prod DB:
---------

PostgreSQL

=================================================================
                2. @Conditional
=================================================================

Most powerful conditional annotation.

=================================================================
                DEFINITION
=================================================================

@Conditional loads bean
only if custom condition matches.

=================================================================
                EXAMPLE
=================================================================

@Conditional(MyCondition.class)

=================================================================
                WHAT HAPPENS?
=================================================================

Spring executes custom condition logic.

=================================================================
                CUSTOM CONDITION
=================================================================

public class MyCondition
implements Condition {

    public boolean matches(
        ConditionContext context,
        AnnotatedTypeMetadata metadata
    ) {

        return true;

    }

}

=================================================================
                INTERNAL WORKING
=================================================================

Bean registration starts
        ↓
Condition evaluated
        ↓
true  → bean registered
false → bean skipped

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. OS-specific beans
2. Cloud provider configs
3. Feature toggles
4. Dynamic integrations

=================================================================
                3. @ConditionalOnProperty
=================================================================

Most used Spring Boot conditional annotation.

=================================================================
                DEFINITION
=================================================================

Loads bean only if
specific property exists/matches.

=================================================================
                EXAMPLE
=================================================================

@ConditionalOnProperty(
    name = "payment.enabled",
    havingValue = "true"
)

=================================================================
                WHAT HAPPENS?
=================================================================

Bean created ONLY if:
----------------------

payment.enabled=true

=================================================================
                WHY IMPORTANT?
=================================================================

Feature flag style configuration.

=================================================================
                REAL PROJECT USE CASE
=================================================================

1. Enable Redis conditionally
2. Enable Kafka consumers
3. Payment gateway toggles
4. Experimental features

=================================================================
                matchIfMissing
=================================================================

@ConditionalOnProperty(
    name = "cache.enabled",
    matchIfMissing = true
)

=================================================================
                MEANING
=================================================================

If property absent,
condition still passes.

=================================================================
                4. @ConditionalOnMissingBean
=================================================================

Most important Spring Boot internal annotation.

=================================================================
                DEFINITION
=================================================================

Create bean ONLY IF
no matching bean already exists.

=================================================================
                EXAMPLE
=================================================================

@Bean

@ConditionalOnMissingBean
public ObjectMapper objectMapper() {

}

=================================================================
                WHY IMPORTANT?
=================================================================

Avoid duplicate bean conflicts.

=================================================================
                MOST IMPORTANT USE
=================================================================

Spring Boot Auto Configuration.

=================================================================
                INTERNAL WORKING
=================================================================

Spring checks IOC container
        ↓
Matching bean exists?
        ↓
YES → skip bean creation
NO  → create bean

=================================================================
                REAL PROJECT USE CASE
=================================================================

Library developers provide:
----------------------------

Default bean

Application developers can:
----------------------------

Override custom bean

=================================================================
                SPRING BOOT MAGIC
=================================================================

Most Spring Boot auto-configuration
works using conditional annotations.

=================================================================
                EXAMPLE
=================================================================

Redis dependency present?
    ↓
RedisAutoConfiguration activates

=================================================================
                AUTO CONFIGURATION FLOW
=================================================================

Application Starts
    ↓
Classpath scanned
    ↓
Conditional checks evaluated
    ↓
Matching auto-configurations enabled
    ↓
Beans registered dynamically

=================================================================
                FEATURE TOGGLE
=================================================================

Very important enterprise concept.

=================================================================
                WHAT IS IT?
=================================================================

Enable/disable features
without code changes.

=================================================================
                EXAMPLE
=================================================================

feature.new-ui.enabled=true

=================================================================
                BENEFITS
=================================================================

1. Safe rollout
2. A/B testing
3. Gradual deployment

=================================================================
                CLOUD ENVIRONMENT USE CASE
=================================================================

AWS:
-----

S3 Storage Bean

Local:
--------

LocalStorage Bean

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
How does @Profile work internally?

=================================================================
                ANSWER
=================================================================

Spring checks currently active profiles
during bean registration phase
and loads/skips beans accordingly.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why @ConditionalOnMissingBean important?

=================================================================
                ANSWER
=================================================================

Allows default bean creation
without overriding user-defined beans.

Core foundation of Spring Boot
auto-configuration.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
How Spring Boot auto-configuration works?

=================================================================
                ANSWER
=================================================================

Spring Boot scans auto-configurations,
evaluates conditional annotations,
and dynamically registers beans
based on classpath/properties/environment.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Can multiple profiles be active?

YES.

=================================================================
                EXAMPLE
=================================================================

spring.profiles.active=dev,cloud

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
What happens if two profile beans conflict?

=================================================================
                ANSWER
=================================================================

Spring may throw:
------------------

NoUniqueBeanDefinitionException

unless resolved using:
-----------------------

@Primary or @Qualifier

=================================================================
                COMMON MISTAKES
=================================================================

1. Hardcoding environment logic

2. Huge profile complexity

3. Conflicting conditional beans

4. Misusing feature toggles

=================================================================
                BEST PRACTICE
=================================================================

1. Separate environment configs clearly
2. Use feature toggles carefully
3. Prefer property-based conditions
4. Keep auto-configurations modular

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain internal working of Spring Boot
auto-configuration.

Answer:
--------
Spring Boot scans auto-configuration classes,
evaluates conditional annotations like
@ConditionalOnProperty and
@ConditionalOnMissingBean,
then dynamically registers beans
based on environment, properties,
and classpath conditions.

=================================================================
                SUMMARY
=================================================================

@Profile
----------
Environment-specific bean loading.

@Conditional
--------------
Custom condition-based bean loading.

@ConditionalOnProperty
-----------------------
Property-based bean loading.

@ConditionalOnMissingBean
--------------------------
Create bean only if missing.

These annotations power:
-------------------------

1. Spring Boot auto-configuration
2. Feature toggles
3. Environment configs
4. Cloud-native deployments

=================================================================

*/

public class ProfileAndConditionalAnnotationsNotes {
}