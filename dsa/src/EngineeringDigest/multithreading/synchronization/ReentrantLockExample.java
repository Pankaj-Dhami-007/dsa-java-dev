package EngineeringDigest.multithreading.synchronization;

import java.util.concurrent.locks.ReentrantLock;

class Test {

    private final ReentrantLock lock = new ReentrantLock();

    public void outerMethod() {

        lock.lock();

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " entered outerMethod"
            );

            // calling another method
            innerMethod();

            System.out.println(
                    Thread.currentThread().getName()
                            + " exiting outerMethod"
            );

        } finally {

            lock.unlock();
        }
    }

    public void innerMethod() {

        lock.lock();

        try {

            System.out.println(
                    Thread.currentThread().getName()
                            + " entered innerMethod"
            );

            System.out.println(
                    "Hold Count : "
                            + lock.getHoldCount()
            );

        } finally {

            lock.unlock();
        }
    }
}

public class ReentrantLockExample {

    public static void main(String[] args) {

        Test obj = new Test();

        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {

                obj.outerMethod();
            }
        });

        t1.setName("Thread-1");

        t1.start();
    }
}

// Both methods use the same ReentrantLock object instance, so the same thread
// can re-acquire that lock multiple times because ReentrantLock is reentrant.

/*

========================================================================
                    REENTRANT LOCK CONCEPT
========================================================================

Reentrant means:

        Same thread can acquire same lock
        multiple times.

========================================================================

VERY IMPORTANT
========================================================================

Thread already holding lock
can again acquire same lock.

No deadlock occurs.

========================================================================

FLOW OF THIS PROGRAM
========================================================================

Thread-1 enters outerMethod()
        ↓
acquires lock

========================================================================

Inside outerMethod():

        innerMethod()

called.

========================================================================

Now innerMethod() again calls:

        lock.lock();

========================================================================

IMPORTANT QUESTION
========================================================================

Will deadlock happen?

ANSWER:
        NO

========================================================================

WHY?
========================================================================

Because ReentrantLock is:
        REENTRANT

Same thread allowed to re-acquire same lock.

========================================================================

WITHOUT REENTRANT FEATURE
========================================================================

Thread would wait for its own lock forever.
Deadlock.

========================================================================

OUTPUT
========================================================================

Thread-1 entered outerMethod
Thread-1 entered innerMethod
Hold Count : 2
Thread-1 exiting outerMethod

========================================================================

IMPORTANT UNDERSTANDING
========================================================================

Hold Count : 2

means:
same thread acquired same lock 2 times.

========================================================================

FIRST LOCK
========================================================================

outerMethod()

========================================================================

SECOND LOCK
========================================================================

innerMethod()

========================================================================

VERY IMPORTANT RULE
========================================================================

If lock acquired 2 times,
unlock() also required 2 times.

========================================================================

That is why:
- innerMethod() unlocks once
- outerMethod() unlocks once

========================================================================

OTHERWISE
========================================================================

Lock will not release properly.

========================================================================

REAL LIFE ANALOGY
========================================================================

Suppose:
you entered your own room.

Now you go into attached bathroom.

You do not need permission again.

Same owner can re-enter.

========================================================================

WHY REENTRANT LOCK IMPORTANT?
========================================================================

Methods often call other synchronized/locked methods.

Without reentrant behavior:
        self-deadlock may happen.

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. What is ReentrantLock?

→ Lock where same thread can acquire same lock multiple times.

========================================================================

2. What does reentrant mean?

→ Re-enter same lock by same thread.

========================================================================

3. Will same thread deadlock itself?

→ No

========================================================================

4. What is hold count?

→ Number of times current thread acquired lock.

========================================================================

5. Why multiple unlock() needed?

→ Because lock acquired multiple times.

========================================================================

6. Is synchronized reentrant?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

ReentrantLock allows same thread
to acquire same lock multiple times safely,
preventing self-deadlock.

========================================================================

*/

/*

This is Called
Self Deadlock

or

Reentrant Deadlock
Visual Flow
Thread-1
   ↓
outerMethod()
   ↓
acquire lock
   ↓
innerMethod()
   ↓
tries same lock again
   ↓
wait forever


Why Reentrant Feature Solves This?

ReentrantLock internally checks:

"Is current thread already owner?"

If YES:

allow lock again
increase hold count

instead of waiting.


Internal Working

Without reentrant:

lock busy → wait

With reentrant:

same owner thread → allow entry
 */