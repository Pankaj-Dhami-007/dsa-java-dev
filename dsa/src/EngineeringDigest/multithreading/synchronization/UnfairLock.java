package EngineeringDigest.multithreading.synchronization;

import java.util.concurrent.locks.ReentrantLock;

class UnfairLockExample {

    // default ReentrantLock is UNFAIR
    private final ReentrantLock unfairLock =
            new ReentrantLock(); // pass true if want fairness or ordering  jis order me req

    public void accessResource() {

        unfairLock.lock();

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " acquired lock"
            );

            try {

                Thread.sleep(2000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " completed work"
            );

        } finally {

            System.out.println(
                    Thread.currentThread().getName()
                            + " released lock"
            );

            unfairLock.unlock();
        }
    }
}

public class UnfairLock {

    public static void main(String[] args) {

        UnfairLockExample obj =
                new UnfairLockExample();

        Runnable task = new Runnable() {

            @Override
            public void run() {

                obj.accessResource();
            }
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.setName("Thread-1");
        t2.setName("Thread-2");
        t3.setName("Thread-3");

        t1.start();
        t2.start();
        t3.start();
    }
}

/*

========================================================================
                        UNFAIR LOCK
========================================================================

By default:

        ReentrantLock

uses:
        UNFAIR LOCKING

========================================================================

IMPORTANT DEFINITION
========================================================================

Unfair lock does NOT guarantee
First Come First Serve order.

========================================================================

Meaning:
thread waiting longest may NOT get lock first.

========================================================================

Suppose:
========================================================================

Thread-1 waiting
Thread-2 waiting
Thread-3 comes later

========================================================================

Lock may directly give chance to:
        Thread-3

even though:
Thread-1 waiting longer.

========================================================================

THIS IS CALLED
========================================================================

Thread Barging

========================================================================

WHY "UNFAIR"?
========================================================================

Because waiting order is not respected.

========================================================================

IMPORTANT UNDERSTANDING
========================================================================

When lock becomes free,
ANY thread may acquire it.

Scheduler decides.

========================================================================

OUTPUT CAN BE
========================================================================

Thread-1 acquired lock
Thread-1 completed work
Thread-1 released lock

Thread-3 acquired lock
Thread-3 completed work
Thread-3 released lock

Thread-2 acquired lock
Thread-2 completed work
Thread-2 released lock

========================================================================

IMPORTANT
========================================================================

Even if Thread-2 waiting before Thread-3,
Thread-3 may still get lock first.

========================================================================

WHY JAVA USES UNFAIR LOCK BY DEFAULT?
========================================================================

Because unfair locks provide:
- better throughput
- better performance
- less overhead

========================================================================

FAIR LOCK vs UNFAIR LOCK
========================================================================

FAIR LOCK
----------
First Come First Serve.

========================================================================

UNFAIR LOCK
------------
No order guarantee.

========================================================================

FAIR LOCK
----------
Slower

UNFAIR LOCK
------------
Faster

========================================================================

WHY UNFAIR LOCK FASTER?
========================================================================

Because JVM does not maintain strict queue ordering.

Less scheduling overhead.

========================================================================

IMPORTANT INTERNAL CONCEPT
========================================================================

When lock released:

fair lock
----------
checks waiting queue first

unfair lock
------------
any thread can grab lock immediately

========================================================================

THREAD STARVATION
========================================================================

Problem with unfair lock:
some thread may wait too long.

This is called:
        Starvation

========================================================================

HOW TO CREATE FAIR LOCK?
========================================================================

        new ReentrantLock(true);

========================================================================

DEFAULT
========================================================================

        new ReentrantLock();

means:
        unfair lock

========================================================================

USE CASE OF UNFAIR LOCK
========================================================================

Used when:
- performance more important than ordering
- high throughput systems
- thread starvation unlikely

========================================================================

REAL INDUSTRY USE CASES
========================================================================

1. Web servers
2. API servers
3. High concurrency systems
4. Thread pools

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. Is ReentrantLock fair by default?

→ No

Default is unfair.

========================================================================

2. What is unfair lock?

→ Lock that does not guarantee thread ordering.

========================================================================

3. What is advantage of unfair lock?

→ Better performance and throughput.

========================================================================

4. What problem may occur in unfair lock?

→ Thread starvation.

========================================================================

5. How to create fair lock?

→ new ReentrantLock(true)

========================================================================

6. Which is faster:
fair lock or unfair lock?

→ Unfair lock

========================================================================

7. What is thread barging?

→ New thread getting lock before waiting thread.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Unfair lock improves performance
by allowing threads to acquire lock
without strict waiting order.

========================================================================

*/