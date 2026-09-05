package EngineeringDigest.multithreading.concurrentUtilities;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierExample {

    public static void main(String[] args) {

        // barrier for 3 threads
        CyclicBarrier barrier =
                new CyclicBarrier(3, () -> {

                    System.out.println();
                    System.out.println(
                            "All players are ready."
                    );

                    System.out.println(
                            "Game Started!"
                    );

                    System.out.println();
                });

        // common task
        Runnable playerTask = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " reached waiting area"
            );

            try {

                Thread.sleep(3000);

                System.out.println(
                        Thread.currentThread().getName()
                                + " waiting at barrier"
                );

                // waiting at barrier
                barrier.await();

                System.out.println(
                        Thread.currentThread().getName()
                                + " started playing"
                );

            } catch (InterruptedException |
                     BrokenBarrierException e) {

                e.printStackTrace();
            }
        };

        // player threads
        Thread t1 = new Thread(playerTask);
        Thread t2 = new Thread(playerTask);
        Thread t3 = new Thread(playerTask);

        t1.setName("Player-1");
        t2.setName("Player-2");
        t3.setName("Player-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

/*

========================================================================
                    CYCLICBARRIER EXAMPLE
========================================================================

This example demonstrates:
        group thread synchronization.

========================================================================
MAIN IDEA
========================================================================

All player threads must wait
until everyone becomes ready.

========================================================================

Then:
all continue together.

========================================================================
BARRIER CONFIGURATION
========================================================================

CyclicBarrier barrier =
        new CyclicBarrier(3)

========================================================================

Meaning:
3 threads required at barrier.

========================================================================

Only after:
3 threads reach barrier
        ↓
all released together.

========================================================================
IMPORTANT FEATURE
========================================================================

Barrier Action.

========================================================================

This code:
========================================================================

() -> {

    System.out.println("Game Started!");

}

========================================================================

executes automatically
when all threads reach barrier.

========================================================================
THREAD FLOW
========================================================================

Each player thread:
========================================================================

1. reaches waiting area
2. sleeps 3 seconds
3. reaches barrier
4. waits at barrier
5. continues together

========================================================================
STEP-BY-STEP EXECUTION
========================================================================

Player-1 reaches barrier
        ↓
waits

========================================================================

Player-2 reaches barrier
        ↓
waits

========================================================================

Player-3 reaches barrier
        ↓
waits

========================================================================

Now:
required thread count completed.

========================================================================

Barrier opens.

========================================================================

Barrier action executes:
========================================================================

Game Started!

========================================================================

All player threads continue together.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

All threads wait for:
        each other

========================================================================

NOT just one main thread.

========================================================================
OUTPUT CAN BE
========================================================================

Player-1 reached waiting area

Player-2 reached waiting area

Player-3 reached waiting area

========================================================================

(after 3 seconds)

Player-1 waiting at barrier

Player-2 waiting at barrier

Player-3 waiting at barrier

========================================================================

All players are ready.

Game Started!

========================================================================

Player-1 started playing

Player-2 started playing

Player-3 started playing

========================================================================
IMPORTANT OBSERVATION
========================================================================

No thread starts playing early.

========================================================================

All start together after:
barrier completion.

========================================================================
WHY BARRIER ACTION IMPORTANT?
========================================================================

Useful for:
- starting next phase
- initialization
- coordination message

========================================================================

Automatically runs
when barrier fully reached.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Barrier resets automatically
after releasing threads.

========================================================================

Can reuse again.

========================================================================
WHY EXCEPTION HANDLING REQUIRED?
========================================================================

await() may throw:
========================================================================

1. InterruptedException
2. BrokenBarrierException

========================================================================

BrokenBarrierException occurs if:
- barrier breaks
- waiting thread interrupted

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
relay race starts only when:
all runners reach starting line.

========================================================================

Everyone waits.

========================================================================

All ready
        ↓
race begins together.

========================================================================

Exactly same concept.

========================================================================
WHY CYCLICBARRIER IMPORTANT?
========================================================================

Useful for:
parallel phase coordination.

========================================================================

Especially when:
multiple threads must synchronize repeatedly.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Multiplayer games
2. Scientific simulations
3. Parallel computations
4. Batch phase processing
5. Distributed systems

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Reusable synchronization
2. Group coordination
3. Phase-based execution
4. Automatic barrier reset

========================================================================
IMPORTANT LIMITATION
========================================================================

If one thread fails to reach barrier:
other threads may remain waiting.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What does CyclicBarrier(3) mean?

→ 3 threads required to open barrier.

========================================================================

2. Which method waits at barrier?

→ await()

========================================================================

3. What is barrier action?

→ Task executed automatically when barrier completes.

========================================================================

4. Why called cyclic?

→ Barrier resets automatically.

========================================================================

5. Do all threads wait here?

→ Yes

========================================================================

6. Difference between CountDownLatch and CyclicBarrier?

CountDownLatch
----------------
One-way waiting

CyclicBarrier
--------------
Mutual waiting

========================================================================

7. Can CyclicBarrier reuse automatically?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

CyclicBarrier coordinates multiple threads
by making them wait at common barrier point
until all threads are ready to continue together.

========================================================================

*/