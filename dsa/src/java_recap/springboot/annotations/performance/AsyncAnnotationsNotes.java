package java_recap.springboot.annotations.performance;

/*

=================================================================
            SPRING ASYNC ANNOTATIONS
=================================================================

Annotations:
-------------
@EnableAsync
@Async

Core Concepts:
---------------
1. Asynchronous Programming
2. Multithreading
3. Thread Pools
4. Non-Blocking Execution
5. Background Processing
6. CompletableFuture
7. Task Executors
8. Proxy-Based Interception

=================================================================
                WHY ASYNC EXISTS?
=================================================================

Imagine API:

POST /register

=================================================================
                FLOW
=================================================================

1. Save user
2. Send email
3. Send SMS
4. Generate reports

=================================================================
                PROBLEM
=================================================================

If email takes 5 seconds:
--------------------------

Entire API response delayed.

=================================================================
                RESULT
=================================================================

1. Slow APIs
2. Bad user experience
3. Reduced scalability
4. Thread blocking

=================================================================
                SOLUTION
=================================================================

Asynchronous execution.

=================================================================
                WHAT IS SYNCHRONOUS?
=================================================================

Tasks execute one after another.

=================================================================
                EXAMPLE
=================================================================

Task1
wait
Task2
wait
Task3

=================================================================
                WHAT IS ASYNCHRONOUS?
=================================================================

Tasks execute independently
without blocking main flow.

=================================================================
                EXAMPLE
=================================================================

Main API response returned immediately
while email sending happens separately.

=================================================================
                SIMPLE MEANING
=================================================================

"Do work in background."

=================================================================
                WHAT IS @Async?
=================================================================

Most important Spring async annotation.

=================================================================
                DEFINITION
=================================================================

@Async tells Spring:

"Execute this method in separate thread."

=================================================================
                EXAMPLE
=================================================================

@Async
public void sendEmail() {

}

=================================================================
                WHAT HAPPENS?
=================================================================

Method runs in background thread.

Caller thread does NOT wait.

=================================================================
                INTERNAL WORKING
=================================================================

Client Thread
      ↓
Spring Proxy Intercepts
      ↓
Task submitted to ThreadPool
      ↓
Worker Thread Executes Method

=================================================================
                VERY IMPORTANT
=================================================================

@Async internally uses:
------------------------

1. AOP Proxy
2. ThreadPoolTaskExecutor

=================================================================
                FLOW
=================================================================

Request Thread
      ↓
Proxy Interception
      ↓
Executor Service
      ↓
Background Thread
      ↓
Method Execution

=================================================================
                1. @EnableAsync
=================================================================

Main annotation enabling async support.

=================================================================
                EXAMPLE
=================================================================

@Configuration
@EnableAsync
public class AsyncConfig {

}

=================================================================
                WHAT HAPPENS?
=================================================================

Spring enables:
----------------

1. Async proxies
2. Task executors
3. Background execution support

=================================================================
                DEFAULT EXECUTION
=================================================================

Spring uses SimpleAsyncTaskExecutor.

=================================================================
                PROBLEM
=================================================================

Creates too many threads.

=================================================================
                ENTERPRISE BEST PRACTICE
=================================================================

Use custom thread pool.

=================================================================
                CUSTOM EXECUTOR
=================================================================

@Bean
public Executor taskExecutor() {

    ThreadPoolTaskExecutor executor =
            new ThreadPoolTaskExecutor();

    executor.setCorePoolSize(5);
    executor.setMaxPoolSize(10);

    executor.initialize();

    return executor;

}

=================================================================
                WHY THREAD POOL?
=================================================================

Creating threads expensive.

=================================================================
                THREAD POOL BENEFITS
=================================================================

1. Thread reuse
2. Better performance
3. Controlled concurrency
4. Resource optimization

=================================================================
                REAL PROJECT USE CASES
=================================================================

1. Email sending
2. SMS notifications
3. Audit logging
4. Report generation
5. File processing
6. Image compression

=================================================================
                RETURN TYPES
=================================================================

@Async supports:
-----------------

1. void
2. Future
3. CompletableFuture

=================================================================
                VOID EXAMPLE
=================================================================

@Async
public void sendEmail() {

}

=================================================================
                IMPORTANT
=================================================================

Fire-and-forget style.

=================================================================
                COMPLETABLEFUTURE
=================================================================

Modern async programming style.

=================================================================
                EXAMPLE
=================================================================

@Async
public CompletableFuture<String> process() {

    return CompletableFuture.completedFuture(
        "DONE"
    );

}

=================================================================
                BENEFITS
=================================================================

1. Async chaining
2. Parallel execution
3. Better error handling

=================================================================
                PARALLEL EXECUTION
=================================================================

Most important scalability concept.

=================================================================
                EXAMPLE
=================================================================

Fetch:
-------

1. User details
2. Orders
3. Recommendations

All simultaneously.

=================================================================
                RESULT
=================================================================

Huge performance improvement.

=================================================================
                THREAD POOL
=================================================================

Collection of reusable worker threads.

=================================================================
                WHY IMPORTANT?
=================================================================

Without thread pool:
---------------------

Too many threads created.

=================================================================
                PROBLEMS
=================================================================

1. Memory issues
2. CPU overhead
3. Context switching cost

=================================================================
                THREAD POOL PARAMETERS
=================================================================

corePoolSize
maxPoolSize
queueCapacity

=================================================================
                CORE POOL SIZE
=================================================================

Minimum active threads.

=================================================================
                MAX POOL SIZE
=================================================================

Maximum threads allowed.

=================================================================
                QUEUE CAPACITY
=================================================================

Waiting task queue size.

=================================================================
                IMPORTANT INTERVIEW TOPIC
=================================================================

THREAD STARVATION

=================================================================
                WHAT IS IT?
=================================================================

All threads busy,
new tasks cannot execute.

=================================================================
                SOLUTION
=================================================================

Proper thread pool sizing.

=================================================================
                EXCEPTION HANDLING
=================================================================

Very important real-world issue.

=================================================================
                PROBLEM
=================================================================

Exceptions inside async methods
may not propagate normally.

=================================================================
                SOLUTION
=================================================================

AsyncUncaughtExceptionHandler

=================================================================
                SELF INVOCATION PROBLEM
=================================================================

Very important interview question.

=================================================================
                EXAMPLE
=================================================================

public void methodA() {

    methodB();

}

@Async
public void methodB() {

}

=================================================================
                PROBLEM
=================================================================

@Async NOT applied.

=================================================================
                WHY?
=================================================================

Internal call bypasses proxy.

=================================================================
                IMPORTANT
=================================================================

Same issue exists in:
----------------------

1. @Transactional
2. @Cacheable
3. @Async
4. Security AOP

=================================================================
                ASYNC VS MULTITHREADING
=================================================================

Most asked confusion topic.

=================================================================
                ASYNC
=================================================================

Programming style.

=================================================================
                MULTITHREADING
=================================================================

Execution mechanism.

=================================================================
                IMPORTANT
=================================================================

Spring @Async internally uses
multithreading.

=================================================================
                NON-BLOCKING BENEFIT
=================================================================

Main request thread free quickly.

=================================================================
                RESULT
=================================================================

Better scalability.

=================================================================
                REAL MICROSERVICE USE CASE
=================================================================

Order Service:
---------------

1. Save order synchronously
2. Send Kafka event asynchronously
3. Send email asynchronously

=================================================================
                PERFORMANCE BENEFIT
=================================================================

User receives response immediately.

=================================================================
                INTERVIEW IMPORTANT
=================================================================

Q:
---
How @Async works internally?

=================================================================
                ANSWER
=================================================================

Spring creates AOP proxy.
Proxy intercepts method call,
submits task to thread pool executor,
and background thread executes method.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why thread pool needed?

=================================================================
                ANSWER
=================================================================

Creating threads repeatedly is expensive.
Thread pools reuse threads
and improve performance/resource management.

=================================================================
                ANOTHER IMPORTANT QUESTION
=================================================================

Q:
---
Why self-invocation breaks @Async?

=================================================================
                ANSWER
=================================================================

Internal method calls bypass Spring proxy,
so async interception never happens.

=================================================================
                TRICKY INTERVIEW QUESTION
=================================================================

Q:
---
Does @Async make code non-blocking?

PARTIALLY.

=================================================================
                ANSWER
=================================================================

Method executes in separate thread,
but thread still blocks internally
during IO/sleep operations.

=================================================================
                ANOTHER TRICKY QUESTION
=================================================================

Q:
---
Can private methods use @Async?

NO usually.

Proxy cannot intercept properly.

=================================================================
                COMMON MISTAKES
=================================================================

1. Using default executor in production

2. Too many async tasks

3. Ignoring thread pool sizing

4. Long-running blocking operations

5. Self-invocation problems

=================================================================
                BEST PRACTICE
=================================================================

1. Use custom thread pools
2. Keep async tasks independent
3. Monitor thread pool metrics
4. Handle async exceptions properly
5. Use CompletableFuture for complex flows

=================================================================
                REAL INTERVIEW ANSWER
=================================================================

Q. Explain complete flow of @Async.

Answer:
--------
1. Client calls async method
2. Spring proxy intercepts call
3. Task submitted to thread pool
4. Caller thread returns immediately
5. Worker thread executes method
6. Result handled asynchronously

=================================================================
                SUMMARY
=================================================================

@EnableAsync
--------------
Enables async infrastructure.

@Async
--------
Executes method in background thread.

Spring internally uses:
------------------------

1. AOP proxies
2. Thread pools
3. Executor framework

Used heavily for:
------------------

1. Notifications
2. Background jobs
3. Parallel processing
4. Scalability optimization

=================================================================

*/

public class AsyncAnnotationsNotes {
}