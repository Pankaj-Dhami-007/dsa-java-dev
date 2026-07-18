package EngineeringDigest.multithreading.synchronization;

public class DisadvantageOfSynchronization {

}

/*

========================================================================
                DISADVANTAGES OF SYNCHRONIZATION
========================================================================

Although synchronization provides:
- thread safety
- mutual exclusion
- data consistency

it also has some important disadvantages.

========================================================================
1. NO FAIRNESS GUARANTEE
========================================================================

synchronized does NOT guarantee:
        First Come First Serve order.

========================================================================

Meaning:
thread waiting longest may NOT get lock first.

========================================================================

PROBLEM
========================================================================

Some threads may wait too long.

This can cause:
        Starvation

========================================================================

WHY?
========================================================================

Intrinsic lock does not maintain waiting queue order.

========================================================================

SOLUTION
========================================================================

Use:
        ReentrantLock(true)

Fair lock provides FIFO ordering.

========================================================================
2. BLOCKING PROBLEM
========================================================================

If thread cannot acquire lock,
it waits indefinitely.

========================================================================

Suppose:
Thread-1 acquired lock
and doing heavy task for 10 sec.

========================================================================

Thread-2 tries same synchronized method.

========================================================================

RESULT
========================================================================

Thread-2 becomes BLOCKED
until Thread-1 releases lock.

========================================================================

PROBLEM
========================================================================

Thread cannot:
- skip task
- retry later
- do other work

========================================================================

WHY?
========================================================================

synchronized does not support:
        tryLock()

========================================================================

SOLUTION
========================================================================

Use:
        ReentrantLock + tryLock()

========================================================================
3. NO INTERRUPTIBLE LOCK WAITING
========================================================================

Suppose:
Thread waiting for synchronized lock.

========================================================================

Even if interrupted,
thread continues waiting for lock.

========================================================================

PROBLEM
========================================================================

Poor responsiveness in:
- shutdown systems
- cancellation systems
- servers

========================================================================

WHY?
========================================================================

synchronized does not support:
        interruptible waiting

========================================================================

SOLUTION
========================================================================

Use:
        lockInterruptibly()

========================================================================
4. NO READ-WRITE LOCK SUPPORT
========================================================================

synchronized allows:
        only one thread at a time.

========================================================================

PROBLEM
========================================================================

Even multiple READ operations
cannot execute simultaneously.

========================================================================

Suppose:
100 threads only reading data.

Still:
        one thread executes at a time.

========================================================================

THIS REDUCES PERFORMANCE
========================================================================

Because reads are usually safe together.

========================================================================

synchronized cannot differentiate:
- READ operation
- WRITE operation

========================================================================

SOLUTION
========================================================================

Use:
        ReadWriteLock

========================================================================

READ LOCK
========================================================================

Multiple threads can READ simultaneously.

========================================================================

WRITE LOCK
========================================================================

Only one thread can WRITE at a time.

========================================================================
5. PERFORMANCE OVERHEAD
========================================================================

Synchronization increases:
- context switching
- waiting
- lock management

========================================================================

PROBLEM
========================================================================

Too much synchronization reduces performance.

========================================================================

Especially in:
- high concurrency systems
- large scale applications

========================================================================
6. DEADLOCK POSSIBILITY
========================================================================

Improper synchronization may cause:
        Deadlock

========================================================================

EXAMPLE
========================================================================

Thread-1 waiting for Thread-2 lock
and Thread-2 waiting for Thread-1 lock.

========================================================================

RESULT
========================================================================

Both wait forever.

========================================================================
7. REDUCED CONCURRENCY
========================================================================

Only one thread accesses synchronized section
at one time.

========================================================================

PROBLEM
========================================================================

Other threads remain idle even if CPU available.

========================================================================

This reduces:
        Parallelism

========================================================================
8. MANUAL DESIGN COMPLEXITY
========================================================================

Improper synchronization may cause:
- race conditions
- deadlocks
- starvation

========================================================================

Multithreaded debugging becomes difficult.

========================================================================
SUMMARY
========================================================================

DISADVANTAGES OF synchronized
--------------------------------

1. No fairness guarantee
2. Blocking behavior
3. No interruptible waiting
4. No read-write separation
5. Performance overhead
6. Deadlock risk
7. Reduced concurrency
8. Difficult debugging

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What are disadvantages of synchronization?

→
- fairness issue
- blocking
- deadlock
- performance overhead
- reduced concurrency

========================================================================

2. Does synchronized support fairness?

→ No

========================================================================

3. Does synchronized support tryLock()?

→ No

========================================================================

4. Can synchronized waiting thread be interrupted?

→ Not easily

========================================================================

5. Does synchronized support ReadWriteLock?

→ No

========================================================================

6. Major performance issue with synchronized?

→ Excessive blocking and reduced concurrency.

========================================================================

7. Main problem caused by improper synchronization?

→ Deadlock

========================================================================

MOST IMPORTANT INTERVIEW LINE

Synchronization ensures thread safety,
but excessive synchronization may reduce performance,
cause blocking, and increase deadlock risk.

========================================================================

*/