package EngineeringDigest.multithreading.threads;

public class YieldMethod extends Thread {

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(
                    Thread.currentThread().getName()
                            + " : " + i
            );
            // giving chance to other thread
            Thread.yield();
        }
    }

    public static void main(String[] args) {

        YieldMethod t1 = new YieldMethod();
        YieldMethod t2 = new YieldMethod();

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();
    }
}

/*

========================================================================
                            yield() METHOD
========================================================================

        Thread.yield();

PURPOSE
--------
yield() tells scheduler:

        "I am ready to pause,
         give chance to another thread."

========================================================================

IMPORTANT UNDERSTANDING

yield() is a hint/request to:
        OS Scheduler

It does NOT guarantee anything.

========================================================================

INTERNAL WORKING
========================================================================

When current thread executes:

        Thread.yield();

Thread moves from:
        RUNNING → RUNNABLE

Now scheduler may:
- continue same thread
OR
- give CPU to another thread

========================================================================

VERY IMPORTANT

yield() does NOT:
-----------------
1. stop thread
2. block thread
3. sleep thread

It only suggests:
        "let other threads execute"

========================================================================

FLOW
========================================================================

Thread-1 running
        ↓
yield()
        ↓
Scheduler decision
        ↓
Either:
    continue Thread-1
OR
    switch to Thread-2

========================================================================

OUTPUT CAN BE
========================================================================

Thread-1 : 1
Thread-2 : 1
Thread-1 : 2
Thread-2 : 2
Thread-1 : 3
Thread-2 : 3

========================================================================

OR

Thread-1 : 1
Thread-1 : 2
Thread-1 : 3
Thread-2 : 1

========================================================================

IMPORTANT

yield() does NOT guarantee context switching.

Scheduler may ignore yield() completely.

========================================================================

WHY OUTPUT CHANGES?
========================================================================

Because thread scheduling depends on:
        JVM + OS Scheduler

========================================================================

yield() METHOD TYPE
========================================================================

Static method.

========================================================================

CALLED AS
========================================================================

        Thread.yield();

========================================================================

THREAD STATE AFTER yield()
========================================================================

RUNNING
    ↓
RUNNABLE

========================================================================

DIFFERENCE BETWEEN sleep() and yield()
========================================================================

sleep()
--------
Pauses thread for fixed time.

yield()
--------
Only gives chance to other threads.

========================================================================

sleep()
--------
Moves thread to TIMED_WAITING.

yield()
--------
Moves thread to RUNNABLE.

========================================================================

sleep()
--------
Guarantees pause.

yield()
--------
No guarantee.

========================================================================

REAL USE CASE
========================================================================

Used rarely.

Can be used when:
- current thread doing too much work
- wants fair CPU sharing

========================================================================

IMPORTANT NOTE
========================================================================

Modern schedulers often ignore yield().

So practical usage is very limited.

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. What is purpose of yield()?

→ Gives chance to other threads for execution.

========================================================================

2. Does yield() stop thread?

→ No

========================================================================

3. Does yield() guarantee context switching?

→ No

========================================================================

4. Which class contains yield() method?

→ Thread class

========================================================================

5. Is yield() static or non-static?

→ Static

========================================================================

6. Which state thread enters after yield()?

→ RUNNABLE

========================================================================

7. Difference between sleep() and yield()?

sleep()
--------
Pauses thread for fixed time

yield()
--------
Only hints scheduler

========================================================================

8. Does yield() release lock?

→ No

========================================================================

MOST IMPORTANT INTERVIEW LINE

yield() is a scheduler hint
used to suggest that current thread
is willing to give CPU to another thread.

========================================================================

*/