package EngineeringDigest.multithreading.executerFramework;

public class JavaConcurrencyHierarchy {
}

/*

========================================================================
                JAVA CONCURRENCY HIERARCHY
========================================================================

Java Concurrency Framework contains
multiple interfaces and classes
working in hierarchy.

========================================================================

VERY IMPORTANT UNDERSTANDING
========================================================================

Hierarchy shows:
who extends whom
and
who implements whom.

========================================================================

This is VERY IMPORTANT
for interviews and deep understanding.

========================================================================
MAIN HIERARCHY
========================================================================

Executor
    ↓
ExecutorService
    ↓
ScheduledExecutorService

========================================================================

IMPLEMENTATION CLASSES
========================================================================

ThreadPoolExecutor
ScheduledThreadPoolExecutor

========================================================================

UTILITY CLASS
========================================================================

Executors

========================================================================
COMPLETE SIMPLE HIERARCHY
========================================================================

Executor (Interface)
        ↓
ExecutorService (Interface)
        ↓
ScheduledExecutorService (Interface)

========================================================================

ExecutorService implemented by:
========================================================================

ThreadPoolExecutor
        ↓
ScheduledThreadPoolExecutor

========================================================================

Executors class provides:
factory methods for creating thread pools.

========================================================================
1. EXECUTOR
========================================================================

Base interface.

========================================================================

Contains only:
        execute()

========================================================================

MAIN PURPOSE
========================================================================

Task execution abstraction.

========================================================================

VERY BASIC INTERFACE.

========================================================================

Example method:
========================================================================

execute(Runnable task)

========================================================================
2. EXECUTORSERVICE
========================================================================

Extends:
        Executor

========================================================================

Adds advanced features:
- submit()
- shutdown()
- Future support
- task management

========================================================================

MOST IMPORTANT INTERFACE
========================================================================

Used heavily in real projects.

========================================================================
MAIN METHODS
========================================================================

1. submit()
2. shutdown()
3. shutdownNow()
4. invokeAll()
5. invokeAny()

========================================================================
3. SCHEDULEDEXECUTORSERVICE
========================================================================

Extends:
        ExecutorService

========================================================================

Used for:
- delayed tasks
- periodic tasks
- scheduling

========================================================================

MAIN METHODS
========================================================================

1. schedule()
2. scheduleAtFixedRate()
3. scheduleWithFixedDelay()

========================================================================
4. THREADPOOLEXECUTOR
========================================================================

Concrete implementation class.

========================================================================

Actually manages:
- worker threads
- task queue
- thread pool

========================================================================

Most thread pool logic exists here.

========================================================================

Implements:
        ExecutorService

========================================================================
5. SCHEDULEDTHREADPOOLEXECUTOR
========================================================================

Extends:
        ThreadPoolExecutor

========================================================================

Adds:
task scheduling capability.

========================================================================
6. EXECUTORS UTILITY CLASS
========================================================================

Very important utility/helper class.

========================================================================

Provides:
factory methods

========================================================================

Examples:
========================================================================

newFixedThreadPool()

newCachedThreadPool()

newSingleThreadExecutor()

newScheduledThreadPool()

========================================================================

IMPORTANT
========================================================================

Executors class creates objects internally.

========================================================================

Usually returns:
        ExecutorService

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

You mostly work with:
        ExecutorService

========================================================================

But internally objects may be:
- ThreadPoolExecutor
- ScheduledThreadPoolExecutor

========================================================================
REAL LIFE ANALOGY
========================================================================

Executor
---------
Basic Manager

========================================================================

ExecutorService
----------------
Advanced Manager

========================================================================

ScheduledExecutorService
-------------------------
Time-based Manager

========================================================================

ThreadPoolExecutor
-------------------
Actual worker management system

========================================================================

Executors
----------
Factory creating managers

========================================================================
IMPORTANT FLOW
========================================================================

Executors utility class
        ↓
creates ExecutorService object
        ↓
internally ThreadPoolExecutor used
        ↓
tasks executed by worker threads

========================================================================
MOST IMPORTANT UNDERSTANDING
========================================================================

Executor
--------
basic execution contract

========================================================================

ExecutorService
----------------
full thread/task management

========================================================================

ThreadPoolExecutor
-------------------
actual implementation engine

========================================================================
WHY HIERARCHY IMPORTANT?
========================================================================

Provides:
- abstraction
- loose coupling
- flexibility
- scalability

========================================================================

Developer programs to:
        interfaces

========================================================================

NOT concrete classes.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Which interface is base of Executor Framework?

→ Executor

========================================================================

2. Which interface extends Executor?

→ ExecutorService

========================================================================

3. Which interface supports scheduling?

→ ScheduledExecutorService

========================================================================

4. Which class implements ExecutorService?

→ ThreadPoolExecutor

========================================================================

5. Purpose of Executors utility class?

→ Creates thread pool objects.

========================================================================

6. Which interface mostly used in real projects?

→ ExecutorService

========================================================================

7. Difference between Executor and ExecutorService?

Executor
---------
Only execute()

ExecutorService
----------------
Advanced lifecycle and task management

========================================================================

8. Which class handles actual thread pool internally?

→ ThreadPoolExecutor

========================================================================

MOST IMPORTANT INTERVIEW LINE

Executor Framework hierarchy provides
abstraction layers for task execution,
thread management,
and thread pool implementation.

========================================================================

*/