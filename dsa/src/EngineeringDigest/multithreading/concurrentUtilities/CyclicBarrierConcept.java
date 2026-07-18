package EngineeringDigest.multithreading.concurrentUtilities;

public class CyclicBarrierConcept {
}

/*

========================================================================
                        CYCLICBARRIER
========================================================================

CyclicBarrier is VERY IMPORTANT
thread coordination utility in Java.

========================================================================
WHY CYCLICBARRIER EXISTS?
========================================================================

Sometimes multiple threads must:
- complete current phase
- wait for each other
- continue together

========================================================================

Need:
all threads should reach common point
before proceeding.

========================================================================

This is why CyclicBarrier exists.

========================================================================
WHAT IS CYCLICBARRIER?
========================================================================

CyclicBarrier allows multiple threads
to wait at common barrier point
until all participating threads arrive.

========================================================================
SIMPLE DEFINITION
========================================================================

CyclicBarrier synchronizes group of threads
at specific execution point.

========================================================================
MAIN IDEA
========================================================================

All threads wait for each other.

========================================================================

When all threads arrive:
barrier opens
        ↓
all continue together.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
IMPORTANT METHOD
========================================================================

1. await()

========================================================================
HOW CYCLICBARRIER WORKS?
========================================================================

Suppose:
========================================================================

CyclicBarrier(3)

========================================================================

Meaning:
3 threads required at barrier.

========================================================================
STEP-BY-STEP FLOW
========================================================================

Thread-1 reaches barrier
        ↓
waits

========================================================================

Thread-2 reaches barrier
        ↓
waits

========================================================================

Thread-3 reaches barrier
        ↓
all threads released together

========================================================================

Barrier resets automatically.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Unlike CountDownLatch:
all participating threads wait here.

========================================================================

NOT only one waiting thread.

========================================================================
WHY CALLED CYCLIC?
========================================================================

Because barrier:
        resets automatically

========================================================================

Can be reused again and again.

========================================================================
VERY IMPORTANT DIFFERENCE
========================================================================

CountDownLatch
----------------
One-time use

========================================================================

CyclicBarrier
--------------
Reusable

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

CyclicBarrier useful for:
multi-phase parallel processing.

========================================================================
EXAMPLE SCENARIO
========================================================================

Suppose:
3 players in racing game.

========================================================================

Game starts ONLY when:
all players ready.

========================================================================

Each player reaches starting line
        ↓
waits

========================================================================

All players ready
        ↓
race starts together.

========================================================================

Exactly same concept.

========================================================================
IMPORTANT METHOD
========================================================================

await()

========================================================================

Thread waits at barrier point.

========================================================================

When required threads arrive:
all threads released together.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Barrier automatically resets
after releasing threads.

========================================================================

Can reuse for next cycle.

========================================================================
CYCLICBARRIER vs COUNTDOWNLATCH
========================================================================

CountDownLatch
----------------
One/more threads wait
for worker completion.

========================================================================

CyclicBarrier
--------------
All threads wait for each other.

========================================================================

CountDownLatch
----------------
One-time use

========================================================================

CyclicBarrier
--------------
Reusable

========================================================================

CountDownLatch
----------------
Counter decreases to zero

========================================================================

CyclicBarrier
--------------
Barrier point synchronization

========================================================================
IMPORTANT OPTIONAL FEATURE
========================================================================

Barrier Action.

========================================================================

Special task automatically executes
when all threads reach barrier.

========================================================================

Advanced topic later.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Multiplayer games
2. Parallel simulations
3. Scientific computations
4. Multi-phase processing
5. Distributed processing

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Reusable synchronization
2. Thread coordination
3. Multi-phase execution support
4. Parallel workflow synchronization

========================================================================
IMPORTANT LIMITATION
========================================================================

If one thread never reaches barrier:
other threads may wait forever.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
group trip bus leaves only when:
all passengers arrive.

========================================================================

Passengers wait for each other.

========================================================================

When everyone arrives:
bus starts.

========================================================================

Exactly same concept.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is CyclicBarrier?

→ Synchronization utility where threads wait for each other.

========================================================================

2. Why called cyclic?

→ Because barrier automatically resets.

========================================================================

3. Which method used in CyclicBarrier?

→ await()

========================================================================

4. Difference between CountDownLatch and CyclicBarrier?

CountDownLatch
----------------
One-time waiting

CyclicBarrier
--------------
Reusable mutual waiting

========================================================================

5. Do all threads wait in CyclicBarrier?

→ Yes

========================================================================

6. Can CyclicBarrier reuse automatically?

→ Yes

========================================================================

7. Main use case of CyclicBarrier?

→ Multi-phase parallel processing.

========================================================================

MOST IMPORTANT INTERVIEW LINE

CyclicBarrier synchronizes multiple threads
by making them wait at common barrier point
until all participating threads arrive.

========================================================================

*/