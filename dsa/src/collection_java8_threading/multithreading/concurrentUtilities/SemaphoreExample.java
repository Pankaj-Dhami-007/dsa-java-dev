package EngineeringDigest.multithreading.concurrentUtilities;

import java.util.concurrent.Semaphore;

public class SemaphoreExample {

    public static void main(String[] args) {

        // only 2 permits available
        Semaphore semaphore =
                new Semaphore(2);

        // common task
        Runnable task = () -> {

            try {

                System.out.println(
                        Thread.currentThread().getName()
                                + " waiting for permit..."
                );

                // acquire permit
                semaphore.acquire();

                System.out.println(
                        Thread.currentThread().getName()
                                + " acquired permit"
                );

                // accessing shared resource
                Thread.sleep(4000);

                System.out.println(
                        Thread.currentThread().getName()
                                + " completed work"
                );

            } catch (InterruptedException e) {

                e.printStackTrace();

            } finally {

                // release permit
                semaphore.release();

                System.out.println(
                        Thread.currentThread().getName()
                                + " released permit"
                );
            }
        };

        // creating threads
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);
        Thread t4 = new Thread(task);

        t1.setName("User-1");
        t2.setName("User-2");
        t3.setName("User-3");
        t4.setName("User-4");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
    }
}

/*

========================================================================
                        SEMAPHORE EXAMPLE
========================================================================

This example demonstrates:
        limited concurrent access.

========================================================================
MAIN IDEA
========================================================================

Only limited number of threads
can access resource simultaneously.

========================================================================
SEMAPHORE CONFIGURATION
========================================================================

Semaphore semaphore =
        new Semaphore(2);

========================================================================

Meaning:
only 2 permits available.

========================================================================

So:
maximum 2 threads can work simultaneously.

========================================================================
THREAD FLOW
========================================================================

Each thread:
========================================================================

1. waits for permit
2. acquire permit
3. access resource
4. release permit

========================================================================
STEP-BY-STEP EXECUTION
========================================================================

Suppose:
User-1 and User-2 acquire permits first.

========================================================================

Now:
available permits = 0

========================================================================

User-3 and User-4:
must WAIT.

========================================================================

After User-1 completes:
========================================================================

release()

========================================================================

Permit returns back.

========================================================================

Now waiting thread may continue.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Semaphore controls:
        concurrency level

========================================================================

NOT complete mutual exclusion.

========================================================================
OUTPUT CAN BE
========================================================================

User-1 waiting for permit...

User-2 waiting for permit...

User-1 acquired permit

User-2 acquired permit

========================================================================

User-3 waiting for permit...

User-4 waiting for permit...

========================================================================

(after 4 seconds)

User-1 completed work

User-1 released permit

========================================================================

User-3 acquired permit

========================================================================
IMPORTANT OBSERVATION
========================================================================

Only 2 threads work simultaneously.

========================================================================

Others wait automatically.

========================================================================
WHY finally BLOCK IMPORTANT?
========================================================================

Ensures:
========================================================================

release()

========================================================================

always executes.

========================================================================

Even if exception occurs.

========================================================================

Otherwise:
permit leakage possible.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

If release() forgotten:
threads may wait forever.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
2 ATM machines available.

========================================================================

Only 2 people use ATM simultaneously.

========================================================================

Others wait in queue.

========================================================================

When one person leaves:
next person enters.

========================================================================

Exactly same happens here.

========================================================================
WHY SEMAPHORE IMPORTANT?
========================================================================

Prevents:
resource overloading.

========================================================================

Useful when resources limited.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Database connection pool
2. API request limiting
3. Printer sharing
4. Thread throttling
5. Resource pool management

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Controlled concurrency
2. Prevents overload
3. Supports multiple concurrent threads
4. Better resource utilization

========================================================================
IMPORTANT LIMITATION
========================================================================

Improper release handling
may cause deadlock-like waiting.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What does Semaphore(2) mean?

→ Maximum 2 threads can access resource simultaneously.

========================================================================

2. Which method acquires permit?

→ acquire()

========================================================================

3. Which method releases permit?

→ release()

========================================================================

4. What happens if no permit available?

→ Thread waits.

========================================================================

5. Why release() written in finally block?

→ To avoid permit leakage.

========================================================================

6. Difference between Semaphore and synchronized?

Semaphore
-----------
Allows limited multiple threads

synchronized
--------------
Only one thread

========================================================================

MOST IMPORTANT INTERVIEW LINE

Semaphore controls concurrent access
by allowing only limited number of threads
to acquire permits simultaneously.

========================================================================

*/