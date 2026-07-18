package EngineeringDigest.multithreading.concurrentUtilities;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchExample {

    public static void main(String[] args)
            throws InterruptedException {

        // counter starts with 3
        CountDownLatch latch =
                new CountDownLatch(3);

        // common task
        Runnable task = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " started work"
            );

            try {

                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " completed work"
            );

            // decrease counter
            latch.countDown();

            System.out.println(
                    "Remaining Count : "
                            + latch.getCount()
            );
        };

        // worker threads
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.setName("Service-1");
        t2.setName("Service-2");
        t3.setName("Service-3");

        t1.start();
        t2.start();
        t3.start();

        System.out.println();
        System.out.println(
                "Main thread waiting for all services..."
        );

        // main thread waits
        latch.await();

        System.out.println();
        System.out.println(
                "All services completed."
        );

        System.out.println(
                "Main thread resumed execution"
        );
    }
}

/*

========================================================================
                    COUNTDOWNLATCH EXAMPLE
========================================================================

This is MOST IMPORTANT practical example
for understanding CountDownLatch.

========================================================================
MAIN IDEA
========================================================================

Main thread should wait
until multiple worker threads finish work.

========================================================================
LATCH CONFIGURATION
========================================================================

CountDownLatch latch =
        new CountDownLatch(3);

========================================================================

Meaning:
counter starts with:
        3

========================================================================

Need:
3 tasks completion.

========================================================================
THREAD FLOW
========================================================================

3 worker threads:
- Service-1
- Service-2
- Service-3

========================================================================

Each thread:
1. starts work
2. sleeps 3 seconds
3. completes work
4. calls countDown()

========================================================================
MAIN THREAD FLOW
========================================================================

Main thread calls:
========================================================================

latch.await()

========================================================================

Main thread BLOCKS.

========================================================================

It waits until:
counter becomes zero.

========================================================================
STEP-BY-STEP EXECUTION
========================================================================

Initial counter:
========================================================================

3

========================================================================

Service-1 completes
        ↓
countDown()

counter:
3 → 2

========================================================================

Service-2 completes
        ↓
countDown()

counter:
2 → 1

========================================================================

Service-3 completes
        ↓
countDown()

counter:
1 → 0

========================================================================

Now:
await() releases main thread.

========================================================================

Main thread resumes execution.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Worker threads NEVER wait.

========================================================================

Only:
main thread waits.

========================================================================
OUTPUT CAN BE
========================================================================

Service-1 started work

Service-2 started work

Service-3 started work

========================================================================

Main thread waiting for all services...

========================================================================

(after 3 seconds)

Service-1 completed work

Remaining Count : 2

========================================================================

Service-2 completed work

Remaining Count : 1

========================================================================

Service-3 completed work

Remaining Count : 0

========================================================================

All services completed.

Main thread resumed execution

========================================================================
IMPORTANT OBSERVATION
========================================================================

Main thread continues ONLY AFTER:
all worker threads complete.

========================================================================
WHY THIS IMPORTANT?
========================================================================

Many systems require:
- all services ready
- all tasks completed
- all resources initialized

========================================================================

before proceeding.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
movie shooting starts only after:
- camera ready
- lights ready
- actors ready

========================================================================

Director waits.

========================================================================

Each completed setup:
reduces counter.

========================================================================

Counter becomes zero
        ↓
shooting starts.

========================================================================

Exactly same happens here.

========================================================================
WHY await() IMPORTANT?
========================================================================

Ensures:
main thread does NOT continue early.

========================================================================

Prevents:
incomplete initialization problems.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

CountDownLatch used for:
        coordination

========================================================================

NOT:
resource locking.

========================================================================
IMPORTANT METHOD
========================================================================

getCount()

========================================================================

Returns current counter value.

========================================================================
IMPORTANT LIMITATION
========================================================================

Once counter reaches zero:
cannot reuse latch.

========================================================================

Need new CountDownLatch object.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Microservice startup
2. Parallel API completion
3. Batch processing
4. Application initialization
5. Integration testing

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Easy coordination
2. Wait for multiple threads
3. Better startup synchronization
4. Cleaner concurrent workflow

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What does CountDownLatch(3) mean?

→ Counter starts with value 3.

========================================================================

2. Which method blocks thread?

→ await()

========================================================================

3. Which method decreases counter?

→ countDown()

========================================================================

4. When does await() release thread?

→ When counter becomes zero.

========================================================================

5. Do worker threads wait here?

→ No

========================================================================

6. Is CountDownLatch reusable?

→ No

========================================================================

7. Main purpose of CountDownLatch?

→ Thread coordination.

========================================================================

MOST IMPORTANT INTERVIEW LINE

CountDownLatch coordinates multiple threads
by blocking waiting threads
until all required tasks complete.

========================================================================

*/