package EngineeringDigest.multithreading.executerFramework;

public class ExecutorFrameworkConcept {
}

/*

========================================================================
                    EXECUTOR FRAMEWORK
========================================================================

Executor Framework is one of the MOST IMPORTANT
concepts in modern Java multithreading.

========================================================================
PROBLEM BEFORE EXECUTOR FRAMEWORK
========================================================================

Earlier we manually created threads.

Example:
========================================================================

Thread t1 = new Thread(task);
t1.start();

========================================================================

For many tasks:
========================================================================

- too many threads created
- difficult management
- memory overhead
- poor scalability

========================================================================

Suppose:
10,000 tasks arrive.

========================================================================

Creating 10,000 threads manually:
        VERY BAD

========================================================================

Problems:
- high memory usage
- excessive context switching
- slow performance
- difficult lifecycle management

========================================================================
THIS IS WHY EXECUTOR FRAMEWORK EXISTS
========================================================================

Executor Framework provides:
- thread management
- task scheduling
- thread pooling
- concurrency control

========================================================================
SIMPLE DEFINITION
========================================================================

Executor Framework is high-level API
for managing and executing asynchronous tasks efficiently.

========================================================================
VERY SIMPLE UNDERSTANDING
========================================================================

Instead of manually creating workers,
manager handles workers automatically.

========================================================================

You only submit:
        TASKS

========================================================================

Framework handles:
- thread creation
- thread reuse
- task execution
- thread lifecycle

========================================================================
MAIN IDEA
========================================================================

Developer focuses on:
        TASK

========================================================================

Executor Framework handles:
        THREAD MANAGEMENT

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
IMPORTANT INTERFACES
========================================================================

1. Executor
2. ExecutorService
3. ScheduledExecutorService

========================================================================
IMPORTANT CLASSES
========================================================================

1. ThreadPoolExecutor
2. Executors

========================================================================
MOST IMPORTANT INTERFACE
========================================================================

        ExecutorService

========================================================================

Used heavily in real-world applications.

========================================================================
HOW EXECUTOR FRAMEWORK WORKS?
========================================================================

Task submitted
        ↓
Executor receives task
        ↓
Thread pool assigns worker thread
        ↓
Task executes
        ↓
Thread reused again

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Executor Framework works mainly with:
- Runnable
- Callable

========================================================================

Tasks are submitted,
NOT threads.

========================================================================
WHY EXECUTOR FRAMEWORK BETTER?
========================================================================

1. Thread Reuse
2. Better Performance
3. Better Resource Management
4. Easier Multithreading
5. Controlled Concurrency
6. Task Queue Management

========================================================================
THREAD REUSE
========================================================================

Same threads reused again and again.

========================================================================

No repeated:
- thread creation
- thread destruction

========================================================================

Improves performance significantly.

========================================================================
CONTROLLED CONCURRENCY
========================================================================

Suppose:
1000 tasks submitted.

========================================================================

Executor may use:
        only 10 worker threads.

========================================================================

Remaining tasks wait in queue.

========================================================================

Prevents system overload.

========================================================================
IMPORTANT METHODS
========================================================================

1. execute()
2. submit()
3. shutdown()

========================================================================
1. execute()
========================================================================

Used to execute Runnable task.

========================================================================

No return value.

========================================================================
2. submit()
========================================================================

Submits Runnable or Callable task.

========================================================================

Returns:
        Future object

========================================================================

Can retrieve result later.

========================================================================
3. shutdown()
========================================================================

Gracefully stops ExecutorService.

========================================================================

No new tasks accepted.

========================================================================
COMMON THREAD POOLS
========================================================================

1. Fixed Thread Pool
2. Cached Thread Pool
3. Single Thread Executor
4. Scheduled Thread Pool

========================================================================
1. FIXED THREAD POOL
========================================================================

Fixed number of worker threads.

========================================================================

Most commonly used.

========================================================================
2. CACHED THREAD POOL
========================================================================

Creates threads dynamically.

========================================================================
3. SINGLE THREAD EXECUTOR
========================================================================

Only one worker thread.

========================================================================

Tasks execute sequentially.

========================================================================
4. SCHEDULED THREAD POOL
========================================================================

Executes tasks:
- periodically
- after delay

========================================================================
REAL LIFE ANALOGY
========================================================================

Restaurant Example

========================================================================

You = Task creator

========================================================================

Waiters = Worker Threads

========================================================================

Manager = Executor Framework

========================================================================

You only give:
        customer orders

========================================================================

Manager handles:
- assigning waiter
- reusing waiter
- managing workers

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Executor Framework separates:
        TASK from THREAD

========================================================================

You submit work,
framework manages execution.

========================================================================
WHY MODERN JAVA USES EXECUTOR FRAMEWORK?
========================================================================

Because manual thread handling:
- difficult
- unsafe
- inefficient
- not scalable

========================================================================

Executor Framework solves these problems.

========================================================================
REAL INDUSTRY USE CASES
========================================================================

1. Web servers
2. REST APIs
3. Database processing
4. File upload systems
5. Banking systems
6. Microservices
7. Background jobs
8. Email processing

========================================================================
THREAD vs EXECUTOR FRAMEWORK
========================================================================

THREAD
--------
Manual management

========================================================================

EXECUTOR FRAMEWORK
-------------------
Automatic management

========================================================================

THREAD
--------
Create every time

========================================================================

EXECUTOR FRAMEWORK
-------------------
Thread reuse

========================================================================

THREAD
--------
Hard scalability

========================================================================

EXECUTOR FRAMEWORK
-------------------
Easy scalability

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is Executor Framework?

→ High-level API for managing asynchronous task execution.

========================================================================

2. Why Executor Framework needed?

→ To avoid manual thread management and improve performance.

========================================================================

3. Which package contains Executor Framework?

→ java.util.concurrent

========================================================================

4. Most important interface?

→ ExecutorService

========================================================================

5. Main advantage of Executor Framework?

→ Thread reuse and better resource management.

========================================================================

6. What does Executor Framework manage?

→ Thread lifecycle and task execution.

========================================================================

7. Which tasks commonly submitted?

→ Runnable and Callable

========================================================================

8. Difference between thread and Executor Framework?

Thread
--------
Manual thread handling

Executor Framework
-------------------
Automatic thread management

========================================================================

9. Which method shuts down ExecutorService?

→ shutdown()

========================================================================

10. Which method returns Future object?

→ submit()

========================================================================

MOST IMPORTANT INTERVIEW LINE

Executor Framework simplifies multithreading
by managing thread creation,
thread reuse,
and task execution efficiently.

========================================================================

*/