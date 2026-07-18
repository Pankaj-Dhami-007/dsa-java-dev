package springboot_deep_drive.aop_deep;

public class _12_WhichSpringAnnotationsUseAOP {

}

/**
 * ┌────────────────────────────────────────────────────────────────────────────────────────────┐
 * │             **SPRING BOOT DEEP DIVE - AOP SERIES**                                          │
 * ├────────────────────────────────────────────────────────────────────────────────────────────┤
 * │            **Chapter 12 : Spring Annotations Built Using AOP**                             │
 * └────────────────────────────────────────────────────────────────────────────────────────────┘
 */

/*

╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                  WHICH SPRING ANNOTATIONS INTERNALLY USE AOP ?                                   ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Until now

we created

our own Aspect.



Example



@Aspect

@Before

@Around



Question


Do developers

write their own

Aspects

every day?



NO.



In real projects,

developers mostly use

Spring's built-in annotations.



These annotations

internally

use

Spring AOP.



══════════════════════════════════════════════════════════════════════════════════════════════════════
                             BIG PICTURE
══════════════════════════════════════════════════════════════════════════════════════════════════════



Developer

      │

      ▼

Uses Annotation

      │

      ▼

Spring AOP Proxy

      │

      ▼

Extra Work

(Transaction,
Cache,
Security,
etc.)

      │

      ▼

Business Method



Notice



Developer

never creates

the Proxy.



Spring

creates it

automatically.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            1. @Transactional                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose


Manage

Database Transactions.



Internal Flow



Client

     │

     ▼

Spring Proxy

     │

     ▼

Start Transaction

     │

     ▼

Business Method

     │

     ▼

Exception?

   ┌─────────────┐
   │             │
 Yes            No
   │             │
   ▼             ▼

Rollback      Commit



Without AOP,

Spring

cannot start

or commit

transactions

automatically.



We will study

@Transactional

completely

inside

Transaction Module.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                                2. @Async                                                        ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose


Execute

a method

asynchronously.



Internal Flow



Client

     │

     ▼

Spring Proxy

     │

     ▼

Create Worker Thread

     │

     ▼

Execute Method

     │

     ▼

Return Immediately



Without AOP,

Spring

cannot intercept

the method

to execute it

in another thread.



We'll study

@Async

later

in Async Module.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              3. @Cacheable                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose


Avoid

repeated

database calls.



Internal Flow



Client

     │

     ▼

Spring Proxy

     │

     ▼

Cache Exists?

   ┌─────────────┐
   │             │
 Yes            No
   │             │
   ▼             ▼

Return Cache   Execute Method
                   │
                   ▼
              Store Result
              in Cache



Without AOP,

Spring

cannot check

the cache

before

executing

the method.



We'll study

Caching

later.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              4. @Retryable                                                      ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose


Retry

temporary failures.



Example


Database temporarily unavailable

Network timeout

External API failure



Internal Flow



Client

     │

     ▼

Spring Proxy

     │

     ▼

Execute Method

     │

     ▼

Exception?

   ┌─────────────┐
   │             │
 Yes            No
   │             │
   ▼             ▼

Retry Again   Return Result
   │
   ▼

Retry Again

   │

Success



Without AOP,

Spring

cannot intercept

the exception

and retry

the method.



We'll study

Retry

later.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                            5. @PreAuthorize                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose


Authorization.



Question


Does the user

have permission

to execute

this method?



Internal Flow



Client

     │

     ▼

Spring Proxy

     │

     ▼

Check Permission

   ┌─────────────┐
   │             │
 Yes            No
   │             │
   ▼             ▼

Execute      Access Denied



Without AOP,

Spring

cannot perform

the permission check

before

calling the method.



We'll study

Spring Security

later.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                           6. @PostAuthorize                                                     ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Purpose


Authorization

after

method execution.



Internal Flow



Client

     │

     ▼

Spring Proxy

     │

     ▼

Execute Method

     │

     ▼

Check Returned Object

     │

     ▼

Allowed?

   ┌─────────────┐
   │             │
 Yes            No
   │             │
   ▼             ▼

Return Data  Access Denied



Unlike

@PreAuthorize,

this check

happens

after

the business method.



We'll study

this

inside

Spring Security.



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                        COMPLETE SUMMARY TABLE                                                    ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝



┌──────────────────────┬──────────────────────────────┬──────────────────────┐
│ Annotation           │ Purpose                      │ Uses Spring AOP ?     │
├──────────────────────┼──────────────────────────────┼──────────────────────┤
│ @Transactional       │ Transaction Management       │ ✅ Yes               │
├──────────────────────┼──────────────────────────────┼──────────────────────┤
│ @Async               │ Async Execution              │ ✅ Yes               │
├──────────────────────┼──────────────────────────────┼──────────────────────┤
│ @Cacheable           │ Method Caching               │ ✅ Yes               │
├──────────────────────┼──────────────────────────────┼──────────────────────┤
│ @Retryable           │ Retry Failed Operations      │ ✅ Yes               │
├──────────────────────┼──────────────────────────────┼──────────────────────┤
│ @PreAuthorize        │ Authorization Before Method  │ ✅ Yes               │
├──────────────────────┼──────────────────────────────┼──────────────────────┤
│ @PostAuthorize       │ Authorization After Method   │ ✅ Yes               │
└──────────────────────┴──────────────────────────────┴──────────────────────┘



╔════════════════════════════════════════════════════════════════════════════════════════════════════╗
║                              INTERVIEW QUESTIONS                                                 ║
╚════════════════════════════════════════════════════════════════════════════════════════════════════╝


Q1.

Name some Spring annotations
that internally use AOP.



Answer


@Transactional

@Async

@Cacheable

@Retryable

@PreAuthorize

@PostAuthorize



------------------------------------------------------------


Q2.

What is common between

@Transactional,

@Async,

@Cacheable

and

@Retryable?



Answer


All of them

work by

intercepting

method calls

using

Spring AOP Proxies.



------------------------------------------------------------


Q3.

Do we need to

write our own

Aspect

to use

@Transactional?



Answer


No.


Spring Framework

already provides

the Aspect

internally.



We only

use

the annotation.



------------------------------------------------------------


Q4.

Does

AOP only work

for logging?



Answer


No.



Logging

is just one use case.



Spring also uses AOP

for


✔ Transactions

✔ Async Execution

✔ Caching

✔ Retry

✔ Authorization



══════════════════════════════════════════════════════════════════════════════════════════════════════

                             **REMEMBER**

══════════════════════════════════════════════════════════════════════════════════════════════════════


Whenever you see


@Transactional

@Async

@Cacheable

@Retryable

@PreAuthorize



Always think



Annotation

        │

        ▼

Spring Proxy

        │

        ▼

Extra Framework Logic

        │

        ▼

Business Method



That is exactly

how Spring AOP

powers

many of the framework's

most useful features.

*/