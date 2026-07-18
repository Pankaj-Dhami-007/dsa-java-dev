package EngineeringDigest.multithreading.executerFramework;

public class ExecutorsUtilityClass {
}

/*

========================================================================
                    EXECUTORS UTILITY CLASS
========================================================================

Executors is utility/helper class
used to create:
- thread pools
- ExecutorService objects
- ScheduledExecutorService objects

========================================================================
WHY EXECUTORS CLASS EXISTS?
========================================================================

Creating ThreadPoolExecutor manually is:
- complex
- lengthy
- difficult for beginners

========================================================================

Java introduced:
        Executors utility class

========================================================================

Purpose:
simplify thread pool creation.

========================================================================
WHAT IS EXECUTORS CLASS?
========================================================================

Executors is utility class containing
static factory methods
for creating executor services easily.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Executors class does NOT execute tasks itself.

========================================================================

It only:
        creates executor objects

========================================================================

VERY IMPORTANT
========================================================================

Executors is:
        utility class

NOT interface.

NOT thread pool itself.

========================================================================
MAIN PURPOSE
========================================================================

Simplify creation of:
- thread pools
- executors
- scheduled executors

========================================================================
MOST IMPORTANT METHODS
========================================================================

1. newFixedThreadPool()
2. newCachedThreadPool()
3. newSingleThreadExecutor()
4. newScheduledThreadPool()

========================================================================
1. newFixedThreadPool(n)
========================================================================

Creates:
fixed number of worker threads.

========================================================================

Example:
========================================================================

Executors.newFixedThreadPool(5)

========================================================================

Means:
maximum 5 threads execute simultaneously.

========================================================================

Extra tasks wait in queue.

========================================================================

MOST COMMONLY USED.

========================================================================
2. newCachedThreadPool()
========================================================================

Creates threads dynamically.

========================================================================

Can increase threads as needed.

========================================================================

Idle threads reused.

========================================================================

Good for:
short-lived async tasks.

========================================================================

RISK
========================================================================

Too many tasks
→ too many threads

========================================================================
3. newSingleThreadExecutor()
========================================================================

Creates:
only ONE worker thread.

========================================================================

Tasks execute:
sequentially one by one.

========================================================================

Useful when order important.

========================================================================
4. newScheduledThreadPool()
========================================================================

Used for:
- delayed tasks
- periodic tasks
- scheduling

========================================================================

Example:
========================================================================

run task every 5 seconds

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Executors methods usually return:
        ExecutorService

========================================================================

Internally objects may be:
- ThreadPoolExecutor
- ScheduledThreadPoolExecutor

========================================================================
EXAMPLE FLOW
========================================================================

Executors.newFixedThreadPool(3)
        ↓
creates ThreadPoolExecutor internally
        ↓
returns ExecutorService reference

========================================================================
VERY IMPORTANT DESIGN CONCEPT
========================================================================

Program to:
        Interface

NOT implementation.

========================================================================

That is why:
========================================================================

ExecutorService service =
        Executors.newFixedThreadPool(3);

========================================================================

NOT:
========================================================================

ThreadPoolExecutor executor = ...

========================================================================
WHY EXECUTORS CLASS IMPORTANT?
========================================================================

Without Executors:
manual ThreadPoolExecutor creation difficult.

========================================================================

Executors provides:
simple factory methods.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
you need workers.

========================================================================

Without Executors
------------------
Manually interview and configure workers.

========================================================================

With Executors
---------------
Agency quickly provides ready-made worker team.

========================================================================

Executors acts like:
        factory/agency

========================================================================
IMPORTANT LIMITATION
========================================================================

Executors utility methods may create:
unbounded queues or excessive threads.

========================================================================

In large production systems:
sometimes ThreadPoolExecutor manually preferred.

========================================================================

Advanced topic later.

========================================================================
EXECUTORS vs EXECUTORSERVICE
========================================================================

Executors
----------
Utility/factory class

========================================================================

ExecutorService
----------------
Actual task execution interface

========================================================================

Executors
----------
Creates executors

========================================================================

ExecutorService
----------------
Executes/manages tasks

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Web servers
2. Background jobs
3. Async processing
4. Scheduled notifications
5. Email services
6. Batch processing

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Executors class?

→ Utility class for creating executor services.

========================================================================

2. Which package contains Executors?

→ java.util.concurrent

========================================================================

3. Is Executors interface or class?

→ Utility class

========================================================================

4. Main purpose of Executors?

→ Simplify thread pool creation.

========================================================================

5. Most common method of Executors?

→ newFixedThreadPool()

========================================================================

6. Which method creates single worker thread?

→ newSingleThreadExecutor()

========================================================================

7. Which method supports scheduled tasks?

→ newScheduledThreadPool()

========================================================================

8. Difference between Executors and ExecutorService?

Executors
----------
Factory utility class

ExecutorService
----------------
Task execution interface

========================================================================

9. Does Executors execute tasks directly?

→ No

========================================================================

10. What does newFixedThreadPool(5) mean?

→ Pool with 5 worker threads.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Executors is a utility factory class
used to create different types
of ExecutorService and thread pools easily.

========================================================================

*/