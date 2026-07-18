package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ScheduledThreadPoolConcept {

    public static void main(String[] args) {

        // creating scheduled thread pool
        ExecutorService service =
                Executors.newScheduledThreadPool(2);

        System.out.println("Main Thread Started");

        // type casting because scheduling methods exist
        // in ScheduledExecutorService
        java.util.concurrent.ScheduledExecutorService scheduler =
                (java.util.concurrent.ScheduledExecutorService) service;

        // task
        Runnable task = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " executing scheduled task"
            );
        };

        // execute task after 3 seconds delay
        scheduler.schedule(task, 3, TimeUnit.SECONDS);

        System.out.println("Task Scheduled");

        scheduler.shutdown();
    }
}

/*

========================================================================
                SCHEDULED THREAD POOL
========================================================================

Scheduled Thread Pool used for:
- delayed tasks
- repeated tasks
- periodic scheduling

========================================================================
METHOD USED
========================================================================

Executors.newScheduledThreadPool()

========================================================================
WHAT IS SCHEDULED THREAD POOL?
========================================================================

Thread pool that can execute tasks:
- after specific delay
- repeatedly after interval

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Normal thread pools execute:
        immediately

========================================================================

Scheduled thread pool can execute:
        later or periodically

========================================================================
WHY SCHEDULED THREAD POOL EXISTS?
========================================================================

Many real systems need:
- delayed execution
- repeated background jobs
- automatic scheduling

========================================================================

Examples:
- OTP expiration
- notification sending
- health checks
- cron jobs
- cache cleanup

========================================================================
IMPORTANT INTERFACE
========================================================================

        ScheduledExecutorService

========================================================================

Extends:
        ExecutorService

========================================================================
WHY TYPE CASTING USED?
========================================================================

newScheduledThreadPool()
returns:
        ScheduledExecutorService

========================================================================

But reference stored as:
        ExecutorService

========================================================================

Scheduling methods exist only in:
        ScheduledExecutorService

========================================================================

So type casting done.

========================================================================
IMPORTANT METHODS
========================================================================

1. schedule()
2. scheduleAtFixedRate()
3. scheduleWithFixedDelay()

========================================================================
1. schedule()
========================================================================

Executes task:
        after delay

========================================================================

Example:
========================================================================

schedule(task, 3, TimeUnit.SECONDS)

========================================================================

Meaning:
execute task after 3 seconds.

========================================================================
2. scheduleAtFixedRate()
========================================================================

Executes task repeatedly
at fixed interval.

========================================================================

Example:
========================================================================

every 5 seconds

========================================================================
3. scheduleWithFixedDelay()
========================================================================

Executes task repeatedly
with delay between completion and next start.

========================================================================
CODE FLOW
========================================================================

newScheduledThreadPool(2)
        ↓
creates pool with 2 worker threads

========================================================================

Task submitted using:
        schedule()

========================================================================

Task waits 3 seconds
        ↓
worker thread executes task

========================================================================
OUTPUT CAN BE
========================================================================

Main Thread Started
Task Scheduled

(after 3 seconds)

pool-1-thread-1 executing scheduled task

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Main thread does NOT wait.

========================================================================

Task executes asynchronously later.

========================================================================
WHY SCHEDULED THREAD POOL IMPORTANT?
========================================================================

Without this:
developer manually handles:
- timers
- delays
- repeated execution

========================================================================

Very difficult and error-prone.

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Delayed execution
2. Periodic execution
3. Background scheduling
4. Thread reuse
5. Automatic task management

========================================================================
IMPORTANT LIMITATION
========================================================================

Improper scheduling may:
- overload system
- create memory issues
- accumulate pending tasks

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
you tell worker:

========================================================================

"After 10 minutes,
serve cold water."

========================================================================

OR

"Every 1 hour,
clean tables."

========================================================================

Exactly same idea.

========================================================================
SCHEDULED THREAD POOL vs NORMAL THREAD POOL
========================================================================

NORMAL THREAD POOL
-------------------
Immediate execution

========================================================================

SCHEDULED THREAD POOL
----------------------
Delayed/periodic execution

========================================================================
REAL-WORLD USE CASES
========================================================================

1. OTP expiration
2. Notification systems
3. Health monitoring
4. Cache cleanup
5. Cron jobs
6. Scheduled backups
7. Session timeout

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

scheduleAtFixedRate()
--------------------------------
fixed start interval

========================================================================

scheduleWithFixedDelay()
--------------------------------
delay after previous completion

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Scheduled Thread Pool?

→ Thread pool for delayed and periodic task execution.

========================================================================

2. Which method creates Scheduled Thread Pool?

→ Executors.newScheduledThreadPool()

========================================================================

3. Which interface supports scheduling methods?

→ ScheduledExecutorService

========================================================================

4. Which method executes task after delay?

→ schedule()

========================================================================

5. Which method executes task repeatedly at fixed interval?

→ scheduleAtFixedRate()

========================================================================

6. Main advantage of scheduled thread pool?

→ Automatic delayed and periodic execution.

========================================================================

7. Real-world use case?

→ OTP expiration, notifications, cron jobs.

========================================================================

8. Does scheduled thread pool reuse threads?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

Scheduled Thread Pool enables
delayed and periodic asynchronous task execution
using reusable worker threads.

========================================================================

*/