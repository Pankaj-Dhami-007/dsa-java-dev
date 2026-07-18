package EngineeringDigest.multithreading.executerFramework;

public class ExecutorConcept {
}

/*

========================================================================
                        EXECUTOR CONCEPT
========================================================================

Executor is first step toward:
        Executor Framework

========================================================================
WHY EXECUTOR EXISTS?
========================================================================

Traditional threading had major problems:
- manual thread creation
- poor scalability
- resource wastage
- difficult management

========================================================================

Example:
========================================================================

Thread t1 = new Thread(task);
t1.start();

========================================================================

Developer manually handled:
- thread creation
- thread execution
- lifecycle management

========================================================================

This became difficult for large systems.

========================================================================
SOLUTION
========================================================================

Java introduced:
        Executor

========================================================================
WHAT IS EXECUTOR?
========================================================================

Executor is interface that separates:
        task submission
        from
        task execution

========================================================================

VERY IMPORTANT UNDERSTANDING
========================================================================

Before Executor:
developer created threads manually.

========================================================================

With Executor:
developer submits task only.

========================================================================

Executor decides:
- how task executes
- which thread executes
- when task executes

========================================================================
SIMPLE DEFINITION
========================================================================

Executor is high-level abstraction
for executing tasks asynchronously.

========================================================================
MAIN IDEA
========================================================================

Focus on:
        TASK

========================================================================

Executor handles:
        THREAD EXECUTION

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
IMPORTANT INTERFACE
========================================================================

        Executor

========================================================================
MAIN METHOD
========================================================================

        execute(Runnable task)

========================================================================

Executor accepts:
        Runnable task

========================================================================

Then executes task internally.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Executor works with:
        TASKS

NOT directly threads.

========================================================================

This is BIG design improvement.

========================================================================
TRADITIONAL APPROACH
========================================================================

Task + Thread tightly coupled.

========================================================================

EXECUTOR APPROACH
========================================================================

Task separated from thread execution.

========================================================================
FLOW OF EXECUTOR
========================================================================

Task submitted
        ↓
Executor receives task
        ↓
Executor decides execution strategy
        ↓
Task executes

========================================================================
WHY THIS DESIGN IMPORTANT?
========================================================================

Provides:
- loose coupling
- better scalability
- flexible execution
- reusable thread management

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
you own company.

========================================================================

WITHOUT EXECUTOR
========================================================================

You personally assign every worker manually.

========================================================================

WITH EXECUTOR
========================================================================

You only submit work to manager.

========================================================================

Manager handles:
- worker assignment
- execution
- management

========================================================================

Executor acts like:
        manager

========================================================================
VERY IMPORTANT CONCEPT
========================================================================

Executor does NOT necessarily create new thread.

========================================================================

It only defines:
        task execution mechanism

========================================================================

Different Executors may:
- use thread pool
- use single thread
- use current thread

========================================================================
WHY EXECUTOR BETTER THAN DIRECT THREAD?
========================================================================

1. Decouples task from execution
2. Better resource management
3. Flexible execution strategies
4. Easier scalability
5. Foundation for thread pools

========================================================================
IMPORTANT EVOLUTION
========================================================================

Thread
   ↓
Executor
   ↓
ExecutorService
   ↓
ThreadPoolExecutor

========================================================================

Executor is foundation layer.

========================================================================
LIMITATION OF EXECUTOR
========================================================================

Executor interface very basic.

========================================================================

Only provides:
        execute()

========================================================================

No:
- shutdown
- future result
- task status
- lifecycle management

========================================================================

These added later in:
        ExecutorService

========================================================================
MAIN PURPOSE OF EXECUTOR
========================================================================

Abstract away:
        thread execution details

========================================================================

Developer should think:
        "What task?"

========================================================================

NOT:
        "How thread created?"

========================================================================
IMPORTANT DIFFERENCE
========================================================================

THREAD APPROACH
----------------
Create and manage thread manually.

========================================================================

EXECUTOR APPROACH
------------------
Submit task to executor.

========================================================================
USE CASES
========================================================================

1. Background tasks
2. Server request handling
3. Async processing
4. Task scheduling
5. Thread pooling foundation

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Executor in Java?

→ Interface for executing asynchronous tasks.

========================================================================

2. Which package contains Executor?

→ java.util.concurrent

========================================================================

3. Main purpose of Executor?

→ Separate task submission from execution.

========================================================================

4. Main method of Executor interface?

→ execute()

========================================================================

5. What does Executor accept?

→ Runnable task

========================================================================

6. Difference between Thread and Executor?

Thread
--------
Manual thread handling

Executor
---------
Task submission abstraction

========================================================================

7. Does Executor manage thread creation internally?

→ Yes, depending on implementation.

========================================================================

8. Major limitation of Executor interface?

→ No lifecycle and result management.

========================================================================

9. Which interface extends Executor?

→ ExecutorService

========================================================================

MOST IMPORTANT INTERVIEW LINE

Executor separates task submission
from thread execution,
providing better abstraction and scalability.

========================================================================

*/