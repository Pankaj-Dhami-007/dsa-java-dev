package EngineeringDigest.multithreading.concurrentUtilities;

public class CountDownLatchConcept {
}

/*

========================================================================
                    COUNTDOWNLATCH
========================================================================

CountDownLatch is VERY IMPORTANT
thread coordination utility in Java.

========================================================================
WHY COUNTDOWNLATCH EXISTS?
========================================================================

Sometimes one thread must WAIT
until other threads complete work.

========================================================================

Examples:
- application startup
- microservices initialization
- parallel task completion
- game loading
- report generation

========================================================================

Need:
main thread should continue
ONLY AFTER some tasks finish.

========================================================================

This is why CountDownLatch exists.

========================================================================
WHAT IS COUNTDOWNLATCH?
========================================================================

CountDownLatch is synchronization utility
that allows threads to wait
until counter reaches zero.

========================================================================
SIMPLE DEFINITION
========================================================================

CountDownLatch blocks thread
until specified operations complete.

========================================================================
MAIN IDEA
========================================================================

Counter starts with some value.

========================================================================

Worker threads decrease counter.

========================================================================

Waiting thread continues
when counter becomes:
        ZERO

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
IMPORTANT METHODS
========================================================================

1. await()
2. countDown()

========================================================================
HOW COUNTDOWNLATCH WORKS?
========================================================================

Suppose:
========================================================================

CountDownLatch(3)

========================================================================

Meaning:
counter starts with:
        3

========================================================================

Need:
3 tasks completion.

========================================================================
STEP-BY-STEP FLOW
========================================================================

Main thread calls:
========================================================================

await()

========================================================================

Main thread waits.

========================================================================

Worker-1 finishes task
        ↓
countDown()

counter:
3 → 2

========================================================================

Worker-2 finishes task
        ↓
countDown()

counter:
2 → 1

========================================================================

Worker-3 finishes task
        ↓
countDown()

counter:
1 → 0

========================================================================

Now:
await() thread continues.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

countDown() decreases counter.

========================================================================

await() waits for:
counter reaching zero.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

CountDownLatch is:
        one-time use utility

========================================================================

Once counter reaches zero:
cannot reset.

========================================================================

Need new latch object.

========================================================================
WHY IMPORTANT?
========================================================================

Helps coordinate:
multiple thread completion.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
rocket launch requires:
- fuel check
- engine check
- weather check

========================================================================

Rocket launches ONLY when:
all 3 checks complete.

========================================================================

Each check decreases counter.

========================================================================

Counter becomes zero
        ↓
rocket launches.

========================================================================

Exactly same concept.

========================================================================
COUNTDOWNLATCH vs JOIN()
========================================================================

join()
--------
Waits for ONE thread.

========================================================================

CountDownLatch
----------------
Waits for MULTIPLE threads/tasks.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

CountDownLatch does NOT block worker threads.

========================================================================

Only waiting thread blocks.

========================================================================
IMPORTANT METHODS
========================================================================

1. await()
========================================================================

Thread waits until:
counter becomes zero.

========================================================================
2. countDown()
========================================================================

Decreases counter by 1.

========================================================================
VERY IMPORTANT LIMITATION
========================================================================

CountDownLatch cannot reset.

========================================================================

Reusable coordination requires:
        CyclicBarrier

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Microservice startup
2. Parallel task synchronization
3. Application initialization
4. Testing multithreaded code
5. Batch processing

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Easy thread coordination
2. Wait for multiple tasks
3. Better synchronization
4. Simplifies parallel workflows

========================================================================
IMPORTANT LIMITATION
========================================================================

One-time use only.

========================================================================
COUNTDOWNLATCH vs SEMAPHORE
========================================================================

Semaphore
-----------
Controls concurrent access.

========================================================================

CountDownLatch
----------------
Coordinates task completion.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is CountDownLatch?

→ Thread coordination utility using counter mechanism.

========================================================================

2. Why CountDownLatch used?

→ To wait for multiple tasks completion.

========================================================================

3. Which method waits?

→ await()

========================================================================

4. Which method decreases counter?

→ countDown()

========================================================================

5. Can CountDownLatch reset?

→ No

========================================================================

6. Difference between join() and CountDownLatch?

join()
--------
Waits for one thread

CountDownLatch
----------------
Waits for multiple tasks

========================================================================

7. Main limitation of CountDownLatch?

→ One-time use only.

========================================================================

MOST IMPORTANT INTERVIEW LINE

CountDownLatch allows one or more threads
to wait until a set of operations
performed by other threads completes.

========================================================================

*/