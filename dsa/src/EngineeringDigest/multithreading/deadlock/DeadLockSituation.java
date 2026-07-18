package EngineeringDigest.multithreading.deadlock;

// shared resource 1
class Pen {

    // synchronized method
    public synchronized void usePenAndPaper(Paper paper) {

        System.out.println(
                Thread.currentThread().getName()
                        + " acquired PEN lock"
        );

        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " trying to acquire PAPER lock"
        );

        // trying to acquire paper lock
        paper.finishWriting();
    }

    public synchronized void finishWriting() {

        System.out.println(
                Thread.currentThread().getName()
                        + " using PEN"
        );
    }
}

// shared resource 2
class Paper {

    // synchronized method
    public synchronized void usePaperAndPen(Pen pen) {

        System.out.println(
                Thread.currentThread().getName()
                        + " acquired PAPER lock"
        );

        try {

            Thread.sleep(1000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " trying to acquire PEN lock"
        );

        // trying to acquire pen lock
        pen.finishWriting();
    }

    public synchronized void finishWriting() {

        System.out.println(
                Thread.currentThread().getName()
                        + " using PAPER"
        );
    }
}

public class DeadLockSituation {

    public static void main(String[] args) {

        Pen pen = new Pen();
        Paper paper = new Paper();

        // Thread-1
        Thread t1 = new Thread(new Runnable() {

            @Override
            public void run() {
                pen.usePenAndPaper(paper);
            }
        });

        // Thread-2
        Thread t2 = new Thread(new Runnable() {

            @Override
            public void run() {

                paper.usePaperAndPen(pen);

                // FIX:
                // same lock order
               // pen.usePenAndPaper(paper);
            }
        });

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}

/*

========================================================================
                    DEADLOCK PRACTICAL EXAMPLE
========================================================================

We have:
1. Pen object
2. Paper object

========================================================================

Both have:
        synchronized methods

========================================================================

IMPORTANT
========================================================================

synchronized method means:
thread acquires object lock.

========================================================================

LOCKS HERE
========================================================================

Pen object
-----------
Pen lock

Paper object
-------------
Paper lock

========================================================================
FLOW OF THREAD-1
========================================================================

Thread-1 enters:

        pen.usePenAndPaper()

========================================================================

Thread-1 acquires:
        PEN lock

========================================================================

Then Thread-1 sleeps for 1 second.

========================================================================

After sleep:
Thread-1 tries to acquire:
        PAPER lock

========================================================================
FLOW OF THREAD-2
========================================================================

Thread-2 enters:

        paper.usePaperAndPen()

========================================================================

Thread-2 acquires:
        PAPER lock

========================================================================

Then Thread-2 sleeps for 1 second.

========================================================================

After sleep:
Thread-2 tries to acquire:
        PEN lock

========================================================================
NOW THE PROBLEM
========================================================================

Thread-1 holds:
        PEN lock

and waiting for:
        PAPER lock

========================================================================

Thread-2 holds:
        PAPER lock

and waiting for:
        PEN lock

========================================================================

BOTH WAIT FOREVER
========================================================================

Neither thread can proceed.

========================================================================

THIS IS:
        DEADLOCK

========================================================================
VISUAL FLOW
========================================================================

Thread-1
---------
holds PEN
waiting for PAPER

========================================================================

Thread-2
---------
holds PAPER
waiting for PEN

========================================================================

RESULT
========================================================================

Infinite waiting.

Program hangs forever.

========================================================================
OUTPUT CAN BE
========================================================================

Thread-1 acquired PEN lock
Thread-2 acquired PAPER lock

Thread-1 trying to acquire PAPER lock
Thread-2 trying to acquire PEN lock

========================================================================

After this:
        program stuck forever.

========================================================================
WHY sleep() USED?
========================================================================

To intentionally create deadlock situation.

========================================================================

Without sleep():
deadlock may or may not happen
depending on scheduler timing.

========================================================================
MOST IMPORTANT UNDERSTANDING
========================================================================

Deadlock happens because:
threads acquire locks in different order.

========================================================================

Thread-1:
        PEN → PAPER

========================================================================

Thread-2:
        PAPER → PEN

========================================================================

Different lock order
creates circular waiting.

========================================================================
HOW TO FIX THIS?
========================================================================

IMPORTANT INTERVIEW TOPIC

========================================================================
1. FIXED LOCK ORDER
========================================================================

Both threads should acquire locks
in SAME order.

========================================================================

GOOD
========================================================================

Thread-1:
        PEN → PAPER

Thread-2:
        PEN → PAPER

========================================================================
2. USE tryLock()
========================================================================

Avoid infinite waiting.

========================================================================
3. AVOID NESTED LOCKING
========================================================================

Reduce multiple lock dependency.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why deadlock happening here?

→ Threads acquiring locks in opposite order.

========================================================================

2. Which locks involved here?

→ Pen object lock and Paper object lock.

========================================================================

3. Why synchronized causing deadlock?

→ Because synchronized acquires intrinsic locks.

========================================================================

4. Why sleep() used?

→ To increase deadlock probability.

========================================================================

5. Main cause of deadlock?

→ Circular waiting.

========================================================================

6. How to prevent deadlock?

→ Consistent lock ordering.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Deadlock occurs when threads acquire
multiple locks in different order
and wait forever for each other.

========================================================================

*/

/*
========================================================================
                    HOW DEADLOCK RESOLVED?
========================================================================

MAIN PROBLEM BEFORE
========================================================================

Thread-1:
        PEN → PAPER

========================================================================

Thread-2:
        PAPER → PEN

========================================================================

Different lock order caused:
        Circular Waiting

========================================================================

Thread-1 waiting for PAPER
Thread-2 waiting for PEN

========================================================================

RESULT:
        DEADLOCK

========================================================================
FIX
========================================================================

Now BOTH threads follow SAME order.

========================================================================

Thread-1:
        PEN → PAPER

========================================================================

Thread-2:
        PEN → PAPER

========================================================================

NO CIRCULAR WAIT POSSIBLE
========================================================================

========================================================================
FLOW NOW
========================================================================

Thread-1 acquires:
        PEN lock

========================================================================

Thread-2 tries PEN lock.

========================================================================

Thread-2 waits.

========================================================================

Thread-1 acquires:
        PAPER lock

========================================================================

Thread-1 completes work
and releases both locks.

========================================================================

NOW:
Thread-2 gets PEN lock
and continues safely.

========================================================================

IMPORTANT UNDERSTANDING
========================================================================

Thread-2 is waiting,
BUT NOT deadlocked.

========================================================================

WHY?
========================================================================

Because Thread-1 is NOT waiting for Thread-2.

========================================================================

Deadlock requires:
        circular waiting

========================================================================

HERE:
only one-direction waiting exists.

========================================================================

OUTPUT CAN BE
========================================================================

Thread-1 acquired PEN lock
Thread-1 trying to acquire PAPER lock
Thread-1 using PAPER

Thread-2 acquired PEN lock
Thread-2 trying to acquire PAPER lock
Thread-2 using PAPER

========================================================================

Program completes successfully.

========================================================================
MOST IMPORTANT DEADLOCK PREVENTION RULE
========================================================================

Always acquire locks in:
        CONSISTENT ORDER

========================================================================

This is most common real-world deadlock prevention technique.

========================================================================
IMPORTANT INTERVIEW QUESTION
========================================================================

Q. Best way to prevent deadlock?

========================================================================

ANSWER
========================================================================

Use fixed/consistent lock ordering.

========================================================================
WHY THIS WORKS?
========================================================================

Because circular waiting condition breaks.

========================================================================

Without circular waiting:
        deadlock impossible.

========================================================================
DEADLOCK CONDITION REMOVED
========================================================================

Before:
        Circular Wait existed

Now:
        Circular Wait removed

========================================================================

Since one necessary condition removed,
deadlock cannot happen.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. How deadlock resolved here?

→ By maintaining same lock order.

========================================================================

2. Which deadlock condition removed?

→ Circular Wait

========================================================================

3. Why Thread-2 waits but no deadlock?

→ Because Thread-1 not waiting for Thread-2.

========================================================================

4. Most common deadlock prevention technique?

→ Consistent lock ordering.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Deadlock can be prevented by ensuring
all threads acquire shared locks
in the same consistent order.

========================================================================


 */