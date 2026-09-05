package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CachedThreadPoolConcept {

    public static void main(String[] args) {

        // creating cached thread pool
        ExecutorService service =
                Executors.newCachedThreadPool();

        // submitting tasks
        for (int i = 1; i <= 10; i++) {

            int taskId = i;

            service.execute(() -> {

                System.out.println(
                        Thread.currentThread().getName()
                                + " executing Task-"
                                + taskId
                );

                try {

                    Thread.sleep(3000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(
                        Thread.currentThread().getName()
                                + " completed Task-"
                                + taskId
                );
            });
        }

        service.shutdown();
    }
}

/*

========================================================================
                    CACHED THREAD POOL
========================================================================

Cached Thread Pool is dynamic thread pool
that creates threads as needed.

========================================================================
WHAT IS CACHED THREAD POOL?
========================================================================

Cached thread pool creates:
        new threads dynamically
when required.

========================================================================

Idle threads are:
        reused later.

========================================================================
METHOD USED
========================================================================

Executors.newCachedThreadPool()

========================================================================
MAIN IDEA
========================================================================

No fixed thread count.

========================================================================

Pool size increases or decreases dynamically
depending on workload.

========================================================================
WHY CACHED THREAD POOL EXISTS?
========================================================================

Fixed thread pool has limitation.

========================================================================

Suppose:
very large number of short tasks arrive suddenly.

========================================================================

Fixed pool may create:
        long waiting queue.

========================================================================

Cached thread pool solves this by:
creating more threads dynamically.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

If free thread available:
        reused

========================================================================

If no free thread available:
        new thread created

========================================================================
THREAD REUSE CONCEPT
========================================================================

Completed idle threads remain cached temporarily.

========================================================================

New tasks may reuse those idle threads.

========================================================================

That is why called:
        Cached Thread Pool

========================================================================
CODE FLOW
========================================================================

10 tasks submitted.

========================================================================

If no idle thread exists:
new threads created dynamically.

========================================================================

Possible:
10 threads created for 10 tasks.

========================================================================

After tasks complete:
threads remain idle for some time.

========================================================================

Future tasks may reuse same threads.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Cached thread pool uses:
        no fixed limit

========================================================================

Thread count may grow very large.

========================================================================
OUTPUT CAN BE
========================================================================

pool-1-thread-1 executing Task-1
pool-1-thread-2 executing Task-2
pool-1-thread-3 executing Task-3
pool-1-thread-4 executing Task-4

========================================================================

Many threads may execute simultaneously.

========================================================================
WHY CACHED THREAD POOL FAST?
========================================================================

Because:
- creates threads immediately
- avoids queue waiting
- handles burst traffic quickly

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Dynamic scaling
2. Fast task handling
3. Good for short-lived tasks
4. Idle thread reuse

========================================================================
VERY IMPORTANT LIMITATION
========================================================================

NO THREAD LIMIT.

========================================================================

Too many tasks
        ↓
Too many threads

========================================================================

This may cause:
- high memory usage
- CPU overhead
- thread explosion
- OutOfMemoryError

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Cached thread pool can become dangerous
if too many tasks submitted.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
Neebu Paani shop.

========================================================================

Customers suddenly increase.

========================================================================

You call more friends dynamically
whenever needed.

========================================================================

If many customers come:
many friends called.

========================================================================

After work:
friends stay nearby for future reuse.

========================================================================

Exactly same happens here.

========================================================================
WHEN TO USE CACHED THREAD POOL?
========================================================================

Best for:
- many short asynchronous tasks
- lightweight operations
- burst traffic systems

========================================================================
WHEN NOT TO USE?
========================================================================

Avoid for:
- heavy long-running tasks
- uncontrolled large workloads

========================================================================

Because thread count may become huge.

========================================================================
FIXED THREAD POOL vs CACHED THREAD POOL
========================================================================

FIXED THREAD POOL
------------------
Fixed number of threads

========================================================================

CACHED THREAD POOL
-------------------
Dynamic thread count

========================================================================

FIXED THREAD POOL
------------------
Better resource control

========================================================================

CACHED THREAD POOL
-------------------
Better burst handling

========================================================================

FIXED THREAD POOL
------------------
Tasks wait in queue

========================================================================

CACHED THREAD POOL
-------------------
Creates more threads instead of queueing

========================================================================
IMPORTANT INTERNAL CONCEPT
========================================================================

Cached thread pool uses:
        SynchronousQueue

========================================================================

Meaning:
tasks directly handed to threads.

========================================================================

If no thread available:
new thread created.

========================================================================

Advanced topic later.

========================================================================
REAL-WORLD USE CASES
========================================================================

1. Lightweight async processing
2. Short API calls
3. Event handling
4. Temporary background tasks

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Cached Thread Pool?

→ Dynamically growing thread pool.

========================================================================

2. Which method creates Cached Thread Pool?

→ Executors.newCachedThreadPool()

========================================================================

3. Does cached thread pool have fixed size?

→ No

========================================================================

4. What happens if no idle thread available?

→ New thread created.

========================================================================

5. Main advantage of cached thread pool?

→ Dynamic scaling and fast response.

========================================================================

6. Main risk of cached thread pool?

→ Too many threads may be created.

========================================================================

7. Best use case of cached thread pool?

→ Short-lived asynchronous tasks.

========================================================================

8. Difference between fixed and cached thread pool?

Fixed
------
Limited threads

Cached
--------
Dynamic threads

========================================================================

9. Are idle threads reused?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

Cached Thread Pool dynamically creates
and reuses threads
to handle short-lived asynchronous tasks efficiently.

========================================================================

*/