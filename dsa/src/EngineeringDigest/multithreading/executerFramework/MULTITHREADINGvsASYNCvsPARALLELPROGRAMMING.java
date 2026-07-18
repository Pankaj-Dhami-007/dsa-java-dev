package EngineeringDigest.multithreading.executerFramework;

public class MULTITHREADINGvsASYNCvsPARALLELPROGRAMMING {
}

/*

===============================================================================
                MULTITHREADING vs ASYNC vs PARALLEL PROGRAMMING
===============================================================================

These concepts are related but NOT same.

Most developers confuse:
1. Multithreading
2. Concurrency
3. Parallelism
4. Asynchronous Programming

===============================================================================
1. MULTITHREADING
===============================================================================

Definition
----------
Using multiple threads inside a program.

Main Goal
---------
Perform multiple tasks using multiple execution units (threads).

Important
---------
-> Multithreading DOES NOT guarantee parallel execution.
-> Threads may run one by one very fast on single CPU core.

Example
-------
Thread t1 = new Thread();
Thread t2 = new Thread();

Now application has multiple threads.

So it is multithreading.

===============================================================================
REAL LIFE EXAMPLE
===============================================================================

Restaurant Workers

Cook      -> Thread 1
Cashier   -> Thread 2
Waiter    -> Thread 3

Multiple workers exist.

===============================================================================
KEY POINT
===============================================================================

Multithreading only means:
        "Multiple threads exist"

It does NOT mean:
        "All run simultaneously"

===============================================================================
2. CONCURRENCY
===============================================================================

Definition
----------
Handling multiple tasks together.

Main Goal
---------
Efficient task management.

Important
---------
Tasks may or may not run simultaneously.

Example
-------
Single CPU rapidly switching between tasks.

Task1 -> Small execution
Task2 -> Small execution
Task1 -> Small execution
Task2 -> Small execution

This creates illusion of simultaneous execution.

===============================================================================
REAL LIFE EXAMPLE
===============================================================================

Chef cooking:
-> Rice
-> Curry
-> Roti

Chef switches between tasks.

Not truly same time.

===============================================================================
KEY POINT
===============================================================================

Concurrency means:
        "Multiple tasks progressing together"

NOT necessarily same time.

===============================================================================
3. PARALLEL PROGRAMMING
===============================================================================

Definition
----------
Executing multiple tasks literally at SAME TIME.

Main Goal
---------
Increase speed and performance.

Requirement
------------
Multiple CPU cores.

===============================================================================
EXAMPLE
===============================================================================

Core 1 -> Image Processing
Core 2 -> Video Encoding
Core 3 -> AI Training
Core 4 -> File Compression

All tasks running simultaneously.

===============================================================================
REAL LIFE EXAMPLE
===============================================================================

4 workers washing 4 cars simultaneously.

Worker1 -> Car1
Worker2 -> Car2
Worker3 -> Car3
Worker4 -> Car4

===============================================================================
KEY POINT
===============================================================================

Parallelism means:
        "Actual simultaneous execution"

===============================================================================
4. ASYNCHRONOUS PROGRAMMING
===============================================================================

Definition
----------
Do not wait for long-running task completion.

Main Goal
---------
Non-blocking execution.

Important
---------
-> Caller continues other work.
-> Result comes later.

===============================================================================
EXAMPLE
===============================================================================

CompletableFuture.supplyAsync(() -> {

    return getDataFromDatabase();
});

Main thread continues execution.

===============================================================================
REAL LIFE EXAMPLE
===============================================================================

Ordering Pizza

1. Order pizza
2. Continue watching movie
3. Pizza arrives later

You did NOT stand waiting at shop.

===============================================================================
KEY POINT
===============================================================================

Async means:
        "Don't wait"

===============================================================================
VERY IMPORTANT UNDERSTANDING
===============================================================================

1. Async WITHOUT Multithreading
--------------------------------

Possible.

Example:
JavaScript Event Loop
Node.js

Single-threaded but asynchronous.

===============================================================================

2. Multithreading WITHOUT Parallelism
-------------------------------------

Possible.

Single CPU core:

Thread1 -> Runs
Thread2 -> Runs
Thread1 -> Runs

CPU rapidly switches threads.

===============================================================================

3. Parallelism USING Multithreading
-----------------------------------

Most common.

Java:
Multiple threads running on multiple cores.

===============================================================================
VISUAL UNDERSTANDING
===============================================================================

1. MULTITHREADING
-----------------

Multiple workers exist.

===============================================================================

2. CONCURRENCY
---------------

One worker handling multiple tasks efficiently.

===============================================================================

3. PARALLELISM
---------------

Multiple workers working literally same time.

===============================================================================

4. ASYNC
---------

Task started.
Do not wait.
Continue other work.

===============================================================================
RESTAURANT ANALOGY
===============================================================================

MULTITHREADING
--------------
Cook
Cashier
Waiter

Multiple workers.

===============================================================================

CONCURRENCY
------------
Cook:
-> making rice
-> checking curry
-> making roti

Switching between tasks.

===============================================================================

PARALLELISM
------------
Cook making burger.
Cashier billing customer.

Both simultaneously.

===============================================================================

ASYNC
------
Waiter places online order
and serves other customers.

Does not wait near kitchen.

===============================================================================
JAVA MAPPING
===============================================================================

Multithreading
--------------
Thread
Runnable

===============================================================================

Concurrency
------------
ExecutorService
ThreadPool

===============================================================================

Parallelism
------------
Parallel Streams
ForkJoinPool

===============================================================================

Async Programming
------------------
CompletableFuture
Reactive Programming
WebFlux

===============================================================================
IMPORTANT DIFFERENCE TABLE
===============================================================================

| Concept         | Main Goal             | Needs Multiple Threads | Needs Multiple Cores |
|-----------------|----------------------|-------------------------|----------------------|
| Multithreading  | Multiple threads     | YES                     | NO                   |
| Concurrency     | Handle many tasks    | Not always              | NO                   |
| Parallelism     | Speed                | Usually YES             | YES                  |
| Async           | Non-blocking         | Not always              | NO                   |

===============================================================================
MOST IMPORTANT INTERVIEW QUESTION
===============================================================================

Q1. Is multithreading same as parallelism?
------------------------------------------
NO

Multithreading:
        Multiple threads exist.

Parallelism:
        Multiple tasks execute literally same time.

===============================================================================

Q2. Is async programming same as multithreading?
------------------------------------------------
NO

Async focuses on:
        Non-blocking execution.

Multithreading focuses on:
        Multiple execution threads.

===============================================================================

Q3. Can async exist without threads?
------------------------------------
YES

Example:
JavaScript Event Loop.

===============================================================================

Q4. Can multithreading exist without parallelism?
-------------------------------------------------
YES

Single CPU core can switch between threads.

===============================================================================
BEST EXAMPLE TO UNDERSTAND EVERYTHING
===============================================================================

CompletableFuture.supplyAsync(() -> {

    return heavyTask();
});

What happens here?

1. ASYNC
---------
Main thread does not wait.

2. MULTITHREADING
------------------
Task runs in another thread.

3. PARALLELISM
---------------
If multiple cores available,
task may run truly parallel.

===============================================================================
MEMORY TRICK
===============================================================================

Multithreading
--------------
Many threads exist.

Parallelism
-------------
Many tasks execute SAME TIME.

Async
------
Don't wait.

Concurrency
------------
Handle multiple tasks together.

===============================================================================

*/
