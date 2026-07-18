package EngineeringDigest.multithreading.executerFramework;

public class ThreadPoolConcept {
}

/*

========================================================================
                THREAD POOL REAL LIFE EXAMPLE
========================================================================

Suppose:
you have a small Neebu Paani shop.

========================================================================
CASE 1 : FEW CUSTOMERS
========================================================================

Only 1 or 2 customers come.

========================================================================

You alone can handle:
- taking order
- making neebu paani
- serving customer

========================================================================

No problem.

========================================================================
CASE 2 : MANY CUSTOMERS COME
========================================================================

Now suddenly:
50 customers arrive together.

========================================================================

PROBLEM
========================================================================

You alone cannot handle:
- all orders
- all serving
- all preparation

========================================================================

Customers start waiting.

Shop becomes slow.

========================================================================
YOUR FIRST IDEA
========================================================================

Whenever customer comes,
you call friend for help.

========================================================================

Example:
========================================================================

Customer comes
        ↓
call Friend-1
        ↓
friend prepares drink
        ↓
friend leaves

========================================================================

Next customer:
========================================================================

call Friend-2
        ↓
work done
        ↓
friend leaves

========================================================================
PROBLEM WITH THIS APPROACH
========================================================================

Every time:
- calling friend
- waiting for friend
- explaining work
- friend may be busy

========================================================================

This becomes:
- slow
- expensive
- inefficient

========================================================================

THIS IS LIKE
========================================================================

Creating new thread for every task.

========================================================================

Thread creation is expensive.

========================================================================
YOUR BETTER IDEA
========================================================================

You decide:

        "I will keep fixed friends
         always ready at shop."

========================================================================

Suppose:
3 fixed friends always available.

========================================================================

Now flow becomes:
========================================================================

Customer comes
        ↓
free friend handles order
        ↓
after work friend becomes free again
        ↓
next customer assigned

========================================================================

Friends are REUSED.

========================================================================

No need to:
- call again
- create again
- wait again

========================================================================

THIS IS EXACTLY
========================================================================

Thread Pool

========================================================================
MAPPING WITH JAVA
========================================================================

YOU
----
Application Owner

========================================================================

FRIENDS
--------
Threads

========================================================================

CUSTOMERS
-----------
Tasks / Requests

========================================================================

FIXED FRIENDS
--------------
Thread Pool

========================================================================

CUSTOMER WAITING LINE
----------------------
Task Queue

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

If all friends busy:
new customers wait in queue.

========================================================================

As soon as one friend becomes free:
he handles next customer.

========================================================================

Exactly same happens in:
        ExecutorService

========================================================================
WHY THREAD POOL BETTER?
========================================================================

Because:
threads already available.

========================================================================

No repeated:
- thread creation
- thread destruction

========================================================================

This improves:
- speed
- performance
- scalability

========================================================================
VERY IMPORTANT CONCEPT
========================================================================

Without Thread Pool
========================================================================

Every customer:
        new friend called

========================================================================

WITH Thread Pool
========================================================================

Same friends reused again and again.

========================================================================
REAL SERVER EXAMPLE
========================================================================

Suppose:
Amazon receives millions requests.

========================================================================

Creating new thread for every request:
        impossible.

========================================================================

So servers use:
        thread pools

========================================================================

Fixed worker threads handle requests efficiently.

========================================================================
MOST IMPORTANT UNDERSTANDING
========================================================================

Thread Pool means:

        fixed reusable workers
        continuously handling tasks.

========================================================================
INTERVIEW STYLE ONE-LINE ANSWER
========================================================================

Thread pool is like keeping fixed workers ready
to handle customer requests efficiently
instead of hiring new worker for every customer.

========================================================================

*/

/*
========================================================================
                        THREAD POOL
========================================================================

Thread Pool is one of the MOST IMPORTANT concepts
in modern multithreading.

========================================================================
PROBLEM BEFORE THREAD POOL
========================================================================

Suppose:
1000 tasks arrive.

========================================================================

Without thread pool:
========================================================================

For every task:

        create new thread
        execute task
        destroy thread

========================================================================

PROBLEM
========================================================================

Thread creation is:
- expensive
- slow
- memory consuming

========================================================================

Too many threads may cause:
- CPU overhead
- memory issues
- context switching overhead
- application slowdown

========================================================================
THIS IS WHY THREAD POOL EXISTS
========================================================================

Instead of creating threads repeatedly,
Java creates:
        fixed group of reusable threads.

========================================================================

This group is called:
        Thread Pool

========================================================================
SIMPLE DEFINITION
========================================================================

Thread pool is collection of pre-created reusable threads
used to execute multiple tasks efficiently.

========================================================================
VERY SIMPLE UNDERSTANDING
========================================================================

Instead of:
        creating new worker every time

Company keeps:
        permanent workers ready.

========================================================================

New task comes
        ↓
assign task to free worker
        ↓
worker completes task
        ↓
worker reused again

========================================================================

Same concept in thread pool.

========================================================================
MAIN IDEA
========================================================================

Reuse threads instead of creating repeatedly.

========================================================================
HOW THREAD POOL WORKS?
========================================================================

Thread pool creates:
        limited number of threads.

========================================================================

Tasks submitted to:
        task queue

========================================================================

Free thread picks task
and executes it.

========================================================================

After task completion:
thread returns to pool.

========================================================================

Thread NOT destroyed.

========================================================================
FLOW
========================================================================

Task submitted
        ↓
Stored in queue
        ↓
Free thread picks task
        ↓
Executes task
        ↓
Returns to pool

========================================================================
WHY THREAD POOL IMPORTANT?
========================================================================

1. Better performance
2. Thread reuse
3. Less memory usage
4. Reduced thread creation cost
5. Better concurrency management

========================================================================
THREAD CREATION IS EXPENSIVE
========================================================================

Creating thread requires:
- stack memory
- OS resources
- scheduling setup

========================================================================

Thread pool avoids repeated creation cost.

========================================================================
IMPORTANT BENEFITS
========================================================================

1. Improved Performance
2. Better Resource Management
3. Controlled Concurrency
4. Reduced CPU Overhead
5. Better Scalability

========================================================================
CONTROLLED CONCURRENCY
========================================================================

Suppose server receives:
        10,000 requests

========================================================================

Without thread pool:
10,000 threads created.

========================================================================

System may crash.

========================================================================

With thread pool:
only limited threads exist.

Example:
        50 threads

========================================================================

Remaining tasks wait in queue safely.

========================================================================
USE CASES OF THREAD POOL
========================================================================

1. Web servers
2. API request handling
3. Database processing
4. File processing
5. Email sending systems
6. Banking systems
7. Background task processing
8. Microservices

========================================================================
REAL LIFE ANALOGY
========================================================================

Restaurant Example

========================================================================

WITHOUT THREAD POOL
========================================================================

New waiter hired for every customer.

Very expensive and slow.

========================================================================

WITH THREAD POOL
========================================================================

Fixed waiters already available.

Customers assigned to available waiter.

Much more efficient.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
IMPORTANT INTERFACES
========================================================================

1. Executor
2. ExecutorService

========================================================================
IMPORTANT CLASSES
========================================================================

1. ThreadPoolExecutor
2. Executors

========================================================================
COMMON THREAD POOL TYPES
========================================================================

1. Fixed Thread Pool
2. Cached Thread Pool
3. Single Thread Executor
4. Scheduled Thread Pool

========================================================================
1. FIXED THREAD POOL
========================================================================

Fixed number of threads.

Example:
        5 threads only

========================================================================
2. CACHED THREAD POOL
========================================================================

Creates threads dynamically.

Reusable idle threads.

========================================================================
3. SINGLE THREAD EXECUTOR
========================================================================

Only one worker thread.

Tasks execute sequentially.

========================================================================
4. SCHEDULED THREAD POOL
========================================================================

Executes tasks after delay
or periodically.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Thread pool executes:
        tasks

NOT directly threads.

========================================================================

Usually tasks implemented using:
        Runnable or Callable

========================================================================
THREAD POOL vs NORMAL THREAD
========================================================================

NORMAL THREAD
---------------
Create every time.

========================================================================

THREAD POOL
-------------
Reuse existing threads.

========================================================================
WHY MODERN JAVA USES THREAD POOL?
========================================================================

Because manual thread management:
- difficult
- inefficient
- not scalable

========================================================================

Executor framework solves this.

========================================================================
IMPORTANT CONCEPT
========================================================================

Task Queue

========================================================================

If all pool threads busy,
new tasks wait in queue.

========================================================================

Thread becomes free
        ↓
takes next task from queue

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is thread pool?

→ Collection of reusable worker threads.

========================================================================

2. Why thread pool needed?

→ To avoid repeated thread creation overhead.

========================================================================

3. Main advantage of thread pool?

→ Better performance and resource management.

========================================================================

4. What problem does thread pool solve?

→ Excessive thread creation and resource wastage.

========================================================================

5. Which package provides thread pool?

→ java.util.concurrent

========================================================================

6. Which interface commonly used?

→ ExecutorService

========================================================================

7. What happens if all pool threads busy?

→ Tasks wait in queue.

========================================================================

8. Difference between normal thread and thread pool?

Normal Thread
--------------
Created every time

Thread Pool
-------------
Threads reused

========================================================================

9. Real-world use cases of thread pool?

→ Servers, APIs, databases, background processing.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Thread pool improves performance
by reusing pre-created threads
instead of creating new threads for every task.

========================================================================

*/