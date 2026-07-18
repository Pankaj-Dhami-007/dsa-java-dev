package springboot_deep_drive.aop_deep;

public class _13_Enable_Annotations_That_Activate_AOP {
}

/*
╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                    HOW DOES SPRING KNOW TO ENABLE AOP FEATURES?                                  ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


So far

we learned



@Transactional

@Async

@Cacheable

@Retryable



Question


If I simply write



@Transactional



will it work?



Always?



Answer



NO.



Spring must first

enable

that feature.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                            BIG PICTURE
══════════════════════════════════════════════════════════════════════════════════════════════════════



               Developer Adds Annotation

                          │

                          ▼

                 Feature Enabled ?

               ┌──────────┴──────────┐
               │                     │
              Yes                    No
               │                     │
               ▼                     ▼

      Spring Creates Proxy      Annotation Ignored



Simply writing

an annotation

is not enough.



Spring must know

that

this feature

should be activated.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                         COMMON ENABLE ANNOTATIONS                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌────────────────────────────┬──────────────────────────────────────────────┐
│ Annotation                 │ Enables                                      │
├────────────────────────────┼──────────────────────────────────────────────┤
│ @EnableAspectJAutoProxy    │ Spring AOP                                  │
├────────────────────────────┼──────────────────────────────────────────────┤
│ @EnableTransactionManagement│ @Transactional                             │
├────────────────────────────┼──────────────────────────────────────────────┤
│ @EnableAsync               │ @Async                                      │
├────────────────────────────┼──────────────────────────────────────────────┤
│ @EnableCaching             │ @Cacheable                                  │
├────────────────────────────┼──────────────────────────────────────────────┤
│ @EnableRetry               │ @Retryable                                  │
└────────────────────────────┴──────────────────────────────────────────────┘



Think of these

like switches.



Until the switch

is ON,

Spring

doesn't activate

that functionality.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        1. @EnableAspectJAutoProxy                                                ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Purpose



Enable

Spring AOP.



Internal Flow



┌──────────────┐
│Application   │
│Starts        │
└──────┬───────┘
       │
       ▼
┌──────────────────────┐
│Enable AOP Processing │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│Create Proxy Objects  │
└──────┬───────────────┘
       │
       ▼
┌──────────────────────┐
│Execute Advice        │
└──────────────────────┘



Without this,

Spring AOP

cannot create

Proxy objects.



══════════════════════════════════════════════════════════════════════════════════════════════════════

Note

══════════════════════════════════════════════════════════════════════════════════════════════════════



In Spring Boot,

you usually

do NOT write



@EnableAspectJAutoProxy



because

Spring Boot

auto-configures it

when

spring-boot-starter-aop

is present.



This is a common

interview question.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                      2. @EnableTransactionManagement                                              ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Purpose



Enable

@Transactional



Internal Flow



Application

      │

      ▼

Enable Transaction

Management

      │

      ▼

Create Transaction

Interceptor

      │

      ▼

@Transactional Works



Spring Boot

normally

enables this

automatically

through auto-configuration.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               3. @EnableAsync                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Purpose



Enable

@Async



Flow



Application

      │

      ▼

Enable Async

      │

      ▼

Create Async

Interceptor

      │

      ▼

@Async Works



Unlike AOP,

this annotation

is commonly required

when using

@Async.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                             4. @EnableCaching                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Purpose



Enable

Spring Cache.



Flow



Application

      │

      ▼

Enable Cache

      │

      ▼

Create Cache

Interceptor

      │

      ▼

@Cacheable Works



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                               5. @EnableRetry                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Purpose



Enable

Retry Support.



Flow



Application

      │

      ▼

Enable Retry

      │

      ▼

Retry Interceptor

      │

      ▼

@Retryable Works



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           INTERVIEW QUESTIONS                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



Q1.

Why do we need

@EnableAsync?



Answer


Because

it enables

Spring's Async

infrastructure,

allowing

@Async

to work.



------------------------------------------------------------



Q2.

Do we need

@EnableAspectJAutoProxy

in Spring Boot?



Answer


Usually,

No.



Spring Boot

automatically

configures

AOP

when

spring-boot-starter-aop

is included.



------------------------------------------------------------



Q3.

What is common

between



@EnableAsync

@EnableCaching

@EnableRetry

@EnableTransactionManagement



Answer


All of them

enable

framework features

that internally

use

Spring AOP

or proxy-based

interceptors.



══════════════════════════════════════════════════════════════════════════════════════════════════════

**REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════



Annotation

        │

        ▼

Enable Feature

        │

        ▼

Spring Creates

Infrastructure

        │

        ▼

Proxy / Interceptor

        │

        ▼

Business Method



This is how

Spring activates

most of its

annotation-driven

features.

*/