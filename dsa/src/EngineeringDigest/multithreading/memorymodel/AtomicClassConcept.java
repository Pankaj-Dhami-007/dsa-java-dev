package EngineeringDigest.multithreading.memorymodel;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicClassConcept {

    public static void main(String[] args)
            throws InterruptedException {

        // atomic shared variable
        AtomicInteger counter =
                new AtomicInteger(0);

        // thread 1
        Thread t1 = new Thread(() -> {

            for (int i = 1; i <= 1000; i++) {

                counter.incrementAndGet();
            }
        });

        // thread 2
        Thread t2 = new Thread(() -> {

            for (int i = 1; i <= 1000; i++) {

                counter.incrementAndGet();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(
                "Final Counter Value : "
                        + counter.get()
        );
    }
}

/*

========================================================================
                        ATOMIC CLASSES
========================================================================

Atomic classes are VERY IMPORTANT
modern concurrency classes in Java.

========================================================================
WHY ATOMIC CLASSES EXIST?
========================================================================

Problem with normal variable:
========================================================================

count++

========================================================================

NOT thread-safe.

========================================================================

Because internally:
count++ performs:
1. read
2. modify
3. write

========================================================================

Multiple threads may interfere.

========================================================================

This causes:
        Race Condition

========================================================================
OLD SOLUTION
========================================================================

Use:
        synchronized

========================================================================

BUT synchronized uses:
- locking
- blocking
- context switching

========================================================================

Can reduce performance.

========================================================================
THIS IS WHY ATOMIC CLASSES EXIST
========================================================================

Atomic classes provide:
        thread-safe operations
WITHOUT heavy locking.

========================================================================
WHAT IS ATOMIC?
========================================================================

Atomic means:
operation happens completely
as single indivisible unit.

========================================================================

No thread interruption possible
during operation.

========================================================================
WHAT ARE ATOMIC CLASSES?
========================================================================

Classes providing:
lock-free thread-safe operations.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent.atomic

========================================================================
MOST COMMON CLASS
========================================================================

        AtomicInteger

========================================================================
OTHER IMPORTANT CLASSES
========================================================================

1. AtomicInteger
2. AtomicLong
3. AtomicBoolean
4. AtomicReference

========================================================================
MAIN IDEA
========================================================================

Provide thread-safe updates
without synchronized blocks.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Atomic classes internally use:
        CAS

========================================================================

CAS means:
        Compare And Swap

========================================================================

Very important low-level CPU operation.

========================================================================
HOW CAS WORKS?
========================================================================

1. Read current value
2. Compare expected value
3. If same → update value
4. Else retry

========================================================================

All happens atomically.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Atomic classes mostly:
        lock-free

========================================================================

Meaning:
threads usually don't block each other.

========================================================================
CODE FLOW
========================================================================

counter.incrementAndGet()

========================================================================

Thread-safe increment operation.

========================================================================

Even multiple threads update safely.

========================================================================
WHY THIS SAFE?
========================================================================

AtomicInteger internally uses:
        CAS operation

========================================================================

No race condition occurs.

========================================================================
OUTPUT
========================================================================

Final Counter Value : 2000

========================================================================

Always correct.

========================================================================
WITHOUT ATOMICINTEGER
========================================================================

Using:
========================================================================

int counter = 0;

counter++;

========================================================================

Result may become:
- 1800
- 1900
- inconsistent values

========================================================================

Due to race condition.

========================================================================
WITH ATOMICINTEGER
========================================================================

All updates happen safely.

========================================================================

Correct result guaranteed.

========================================================================
IMPORTANT METHODS
========================================================================

1. incrementAndGet()
2. decrementAndGet()
3. get()
4. set()
5. compareAndSet()

========================================================================
1. incrementAndGet()
========================================================================

Atomically increments value.

========================================================================
2. get()
========================================================================

Returns current value.

========================================================================
3. compareAndSet()
========================================================================

Core CAS method.

========================================================================

Updates value only if expected value matches.

========================================================================
ATOMICINTEGER vs VOLATILE
========================================================================

volatile
----------
Visibility only

========================================================================

AtomicInteger
--------------
Visibility + atomic operations

========================================================================
ATOMICINTEGER vs SYNCHRONIZED
========================================================================

synchronized
--------------
Uses locking

========================================================================

AtomicInteger
--------------
Mostly lock-free

========================================================================

synchronized
--------------
Blocking possible

========================================================================

AtomicInteger
--------------
Better performance in many cases

========================================================================
VERY IMPORTANT LIMITATION
========================================================================

Atomic classes good for:
        single variable atomic operations.

========================================================================

Complex multiple-variable operations
may still require:
        synchronized or locks.

========================================================================
REAL LIFE ANALOGY
========================================================================

Only one worker allowed
to update counter machine at exact instant.

========================================================================

Updates happen safely automatically.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Request counters
2. Visitor counters
3. Inventory count
4. Metrics systems
5. Thread-safe statistics

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Thread-safe
2. Lock-free
3. Better performance
4. No explicit synchronization
5. Easy concurrent updates

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. Why Atomic classes introduced?

→ To provide thread-safe operations without locking.

========================================================================

2. Which package contains AtomicInteger?

→ java.util.concurrent.atomic

========================================================================

3. Is count++ thread-safe?

→ No

========================================================================

4. Which AtomicInteger method increments value safely?

→ incrementAndGet()

========================================================================

5. What does CAS stand for?

→ Compare And Swap

========================================================================

6. Do Atomic classes use synchronized internally?

→ Mostly no

========================================================================

7. Difference between volatile and AtomicInteger?

volatile
----------
Visibility only

AtomicInteger
--------------
Atomic operations + visibility

========================================================================

8. Main advantage of Atomic classes?

→ Lock-free thread-safe operations.

========================================================================

9. Can AtomicInteger completely replace synchronized?

→ No

========================================================================

MOST IMPORTANT INTERVIEW LINE

Atomic classes provide lock-free,
thread-safe operations
using CAS (Compare And Swap) mechanism.

========================================================================

*/