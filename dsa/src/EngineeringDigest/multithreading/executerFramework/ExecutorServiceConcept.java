package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorServiceConcept {

    public static void main(String[] args) {

        // creating ExecutorService with fixed thread pool
        ExecutorService service =
                Executors.newFixedThreadPool(2);

        // task 1
        Runnable task1 = () -> {
            System.out.println(
                    Thread.currentThread().getName()
                            + " executing Task-1"
            );
        };

        // task 2
        Runnable task2 = () -> {
            System.out.println(
                    Thread.currentThread().getName()
                            + " executing Task-2"
            );
        };

        // submitting tasks
        service.execute(task1);
        service.execute(task2);

        // shutdown executor service
        service.shutdown();
    }
}

/*

========================================================================
                    EXECUTOR SERVICE
========================================================================

ExecutorService is MOST IMPORTANT interface
in Executor Framework.

========================================================================
WHY EXECUTORSERVICE EXISTS?
========================================================================

Executor interface had limitation.

========================================================================

Executor only provides:
        execute()

========================================================================

Problems:
- no shutdown mechanism
- no task result handling
- no lifecycle management
- no advanced task control

========================================================================

So Java introduced:
        ExecutorService

========================================================================
WHAT IS EXECUTORSERVICE?
========================================================================

ExecutorService is advanced interface
for managing:
- task execution
- thread pools
- thread lifecycle

========================================================================

It extends:
        Executor

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Executor
---------
basic task execution

========================================================================

ExecutorService
----------------
complete thread/task management

========================================================================
MAIN PURPOSE
========================================================================

Manage thread pools and asynchronous task execution efficiently.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
VERY IMPORTANT METHODS
========================================================================

1. execute()
2. submit()
3. shutdown()
4. shutdownNow()

========================================================================
1. execute()
========================================================================

Used for:
        Runnable task execution

========================================================================

No return value.

========================================================================

Example:
========================================================================

service.execute(task);

========================================================================
2. submit()
========================================================================

Submits:
- Runnable
- Callable

========================================================================

Returns:
        Future object

========================================================================

Used when:
task result required.

========================================================================
3. shutdown()
========================================================================

Gracefully shuts down ExecutorService.

========================================================================

IMPORTANT
========================================================================

No new tasks accepted.

========================================================================

Already submitted tasks complete normally.

========================================================================
4. shutdownNow()
========================================================================

Attempts immediate shutdown.

========================================================================

May interrupt running tasks.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

ExecutorService manages:
- worker threads
- task queue
- task scheduling
- lifecycle

========================================================================
CODE FLOW
========================================================================

Executors.newFixedThreadPool(2)
        ↓
creates pool with 2 worker threads

========================================================================

Tasks submitted using:
        execute()

========================================================================

Worker threads execute tasks.

========================================================================

After completion:
threads remain alive for reuse.

========================================================================

Finally:
        shutdown()

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Threads are NOT created per task.

========================================================================

Pool threads reused repeatedly.

========================================================================
WHY shutdown() IMPORTANT?
========================================================================

Without shutdown():
application may continue running.

========================================================================

Because worker threads remain alive.

========================================================================

VERY COMMON BEGINNER MISTAKE.

========================================================================
WHY EXECUTORSERVICE BETTER THAN THREAD?
========================================================================

1. Thread Reuse
2. Better Resource Management
3. Task Queue Support
4. Easier Lifecycle Management
5. Better Scalability

========================================================================
REAL LIFE ANALOGY
========================================================================

Traditional Thread
-------------------
Hire new worker for every customer.

========================================================================

ExecutorService
----------------
Fixed workers already available.

========================================================================

Tasks assigned to available workers.

========================================================================
VERY IMPORTANT CONCEPT
========================================================================

You submit:
        TASKS

========================================================================

ExecutorService handles:
        THREADS

========================================================================
WHAT Executors.newFixedThreadPool(2) MEANS?
========================================================================

Create pool with:
        2 worker threads

========================================================================

At most:
2 tasks execute simultaneously.

========================================================================

Remaining tasks wait in queue.

========================================================================
OUTPUT CAN BE
========================================================================

pool-1-thread-1 executing Task-1
pool-1-thread-2 executing Task-2

========================================================================

Thread names automatically generated by pool.

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Improved Performance
2. Controlled Concurrency
3. Better Scalability
4. Reduced Memory Usage
5. Simplified Multithreading

========================================================================
IMPORTANT LIMITATIONS
========================================================================

1. Improper shutdown may leak resources
2. Wrong pool size affects performance
3. Too many tasks may overload queue

========================================================================
REAL INDUSTRY USE CASES
========================================================================

1. Web servers
2. API processing
3. Banking systems
4. Background jobs
5. Email sending
6. Microservices

========================================================================
EXECUTOR vs EXECUTORSERVICE
========================================================================

Executor
---------
Basic execution interface

========================================================================

ExecutorService
----------------
Advanced task and lifecycle management

========================================================================

Executor
---------
Only execute()

========================================================================

ExecutorService
----------------
submit(), shutdown(), Future support

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is ExecutorService?

→ Advanced interface for managing asynchronous task execution.

========================================================================

2. Which interface does ExecutorService extend?

→ Executor

========================================================================

3. Why ExecutorService introduced?

→ To provide advanced thread lifecycle and task management.

========================================================================

4. Which method gracefully stops ExecutorService?

→ shutdown()

========================================================================

5. Difference between execute() and submit()?

execute()
-----------
No return value

submit()
----------
Returns Future

========================================================================

6. Why shutdown() important?

→ Releases thread pool resources properly.

========================================================================

7. What does newFixedThreadPool(2) mean?

→ Creates pool with 2 worker threads.

========================================================================

8. Does ExecutorService reuse threads?

→ Yes

========================================================================

9. What happens if all pool threads busy?

→ Tasks wait in queue.

========================================================================

10. Which utility class creates ExecutorService?

→ Executors

========================================================================

MOST IMPORTANT INTERVIEW LINE

ExecutorService provides advanced thread pool
and task lifecycle management
beyond basic task execution offered by Executor.

========================================================================

*/