package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class FixedThreadPoolExample {

    public static void main(String[] args) {

        // creating fixed thread pool with 3 worker threads
        ExecutorService service =
                Executors.newFixedThreadPool(3);

        // submitting 10 tasks
        for (int i = 1; i <= 10; i++) {

            int taskId = i;

            service.execute(() -> {

                System.out.println(
                        Thread.currentThread().getName()
                                + " executing Task-"
                                + taskId
                );

                try {

                    Thread.sleep(2000);

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

        // shutdown pool
        service.shutdown();
    }
}

/*

========================================================================
                    FIXED THREAD POOL
========================================================================

Fixed Thread Pool is MOST COMMONLY USED
thread pool type in Java.

========================================================================
WHAT IS FIXED THREAD POOL?
========================================================================

Fixed Thread Pool creates:
        fixed number of worker threads.

========================================================================

Example:
========================================================================

Executors.newFixedThreadPool(3)

========================================================================

Means:
only 3 worker threads available.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

No matter how many tasks submitted,
maximum only fixed number of threads
execute simultaneously.

========================================================================

Remaining tasks wait in:
        task queue

========================================================================
WHY FIXED THREAD POOL EXISTS?
========================================================================

Without thread pool:
new thread created for every task.

========================================================================

Problems:
- memory overhead
- excessive thread creation
- context switching
- poor scalability

========================================================================

Fixed thread pool solves this using:
        reusable worker threads

========================================================================
MAIN IDEA
========================================================================

Limited workers continuously reused.

========================================================================
CODE FLOW
========================================================================

newFixedThreadPool(3)
        ↓
creates 3 worker threads

========================================================================

10 tasks submitted.

========================================================================

First 3 tasks:
executed immediately.

========================================================================

Remaining 7 tasks:
wait in queue.

========================================================================

When thread becomes free:
next task picked automatically.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Threads are NOT created per task.

========================================================================

Same threads reused repeatedly.

========================================================================
EXAMPLE EXECUTION
========================================================================

pool-1-thread-1 → Task-1
pool-1-thread-2 → Task-2
pool-1-thread-3 → Task-3

========================================================================

After completion:
same threads execute:
- Task-4
- Task-5
- Task-6

========================================================================

This continues until all tasks finish.

========================================================================
OUTPUT CAN BE
========================================================================

pool-1-thread-1 executing Task-1
pool-1-thread-2 executing Task-2
pool-1-thread-3 executing Task-3

========================================================================

pool-1-thread-1 completed Task-1
pool-1-thread-1 executing Task-4

========================================================================

IMPORTANT
========================================================================

Same thread reused for multiple tasks.

========================================================================
WHY THREAD REUSE IMPORTANT?
========================================================================

Thread creation expensive.

========================================================================

Reuse improves:
- performance
- memory usage
- scalability

========================================================================
TASK QUEUE CONCEPT
========================================================================

If all threads busy:
new tasks wait in queue.

========================================================================

Free worker thread picks next task automatically.

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Better Performance
2. Controlled Concurrency
3. Thread Reuse
4. Lower Memory Usage
5. Better Scalability

========================================================================
CONTROLLED CONCURRENCY
========================================================================

Suppose:
1000 tasks submitted.

========================================================================

With fixed pool size 5:
only 5 tasks execute simultaneously.

========================================================================

System remains stable.

========================================================================
VERY IMPORTANT CONCEPT
========================================================================

Fixed Thread Pool prevents:
        thread explosion

========================================================================

Meaning:
too many thread creation avoided.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
Neebu Paani shop has:
        3 fixed workers

========================================================================

10 customers arrive.

========================================================================

First 3 customers served immediately.

========================================================================

Remaining customers wait in line.

========================================================================

When worker becomes free:
next customer handled.

========================================================================

Exactly same happens here.

========================================================================
WHY FIXED THREAD POOL MOST POPULAR?
========================================================================

Because:
- predictable behavior
- stable resource usage
- easy management

========================================================================
IMPORTANT LIMITATION
========================================================================

If pool size too small:
tasks wait too much.

========================================================================

If pool size too large:
memory and CPU wasted.

========================================================================

Choosing correct pool size important.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Web servers
2. REST APIs
3. Database requests
4. Background processing
5. File upload systems
6. Banking systems

========================================================================
IMPORTANT INTERVIEW QUESTION
========================================================================

Q. What happens if all pool threads busy?

========================================================================

ANSWER
========================================================================

New tasks wait in internal task queue
until worker thread becomes free.

========================================================================
FIXED THREAD POOL vs NORMAL THREAD
========================================================================

NORMAL THREAD
---------------
New thread per task

========================================================================

FIXED THREAD POOL
------------------
Reusable fixed worker threads

========================================================================

NORMAL THREAD
---------------
Poor scalability

========================================================================

FIXED THREAD POOL
------------------
Better scalability

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Fixed Thread Pool?

→ Thread pool with fixed number of worker threads.

========================================================================

2. Which method creates fixed thread pool?

→ Executors.newFixedThreadPool()

========================================================================

3. What happens when all threads busy?

→ Tasks wait in queue.

========================================================================

4. Major advantage of fixed thread pool?

→ Thread reuse and controlled concurrency.

========================================================================

5. Why fixed thread pool better than manual threads?

→ Avoids repeated thread creation overhead.

========================================================================

6. Does fixed thread pool create new thread for every task?

→ No

========================================================================

7. Which interface usually stores fixed thread pool?

→ ExecutorService

========================================================================

8. What happens after task completion?

→ Thread reused for next task.

========================================================================

9. Main problem solved by fixed thread pool?

→ Excessive thread creation.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Fixed Thread Pool improves performance
by reusing a fixed number of worker threads
to execute multiple tasks efficiently.

========================================================================

*/