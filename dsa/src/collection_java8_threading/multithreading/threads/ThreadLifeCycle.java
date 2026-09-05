package EngineeringDigest.multithreading.threads;

class MyThreadExample extends Thread {

    @Override
    public void run() {

        try {

            // RUNNING STATE
            System.out.println("Thread is running");

            // TIMED_WAITING STATE
            Thread.sleep(3000);

            System.out.println("Thread resumed");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

public class ThreadLifeCycle {

    public static void main(String[] args) throws InterruptedException {

        MyThreadExample t1 = new MyThreadExample();

        // ================= NEW STATE =================
        System.out.println("After Thread Creation : " + t1.getState());

        // ================= RUNNABLE STATE =================
        t1.start();

        System.out.println("After start() : " + t1.getState());

        // small delay so thread enters sleep()
        Thread.sleep(500);

        // ================= TIMED_WAITING STATE =================
        System.out.println("During sleep() : " + t1.getState());

        // wait for thread completion
        t1.join();

        // ================= TERMINATED STATE =================
        System.out.println("After completion : " + t1.getState());
    }
}

/*

========================================================================
                        THREAD LIFE CYCLE
========================================================================

A thread does not remain in one state forever.

From creation till completion,
thread moves through different states.

These states together are called:

                Thread Life Cycle

========================================================================

JAVA THREAD STATES

1. NEW
2. RUNNABLE
3. BLOCKED
4. WAITING
5. TIMED_WAITING
6. TERMINATED

========================================================================

FLOW

NEW
 ↓
RUNNABLE
 ↓
RUNNING
 ↓
WAITING / BLOCKED / TIMED_WAITING
 ↓
RUNNABLE
 ↓
TERMINATED

========================================================================
1. NEW STATE
========================================================================

Thread object is created
but start() is NOT called yet.

Example:

        Thread t1 = new Thread();

At this stage:
- thread exists in memory
- JVM has NOT started execution

========================================================================

Example:

        MyThreadExample t1 = new MyThreadExample();

        System.out.println(t1.getState());

OUTPUT:
        NEW

========================================================================
2. RUNNABLE STATE
========================================================================

After calling:

        t1.start();

thread enters RUNNABLE state.

IMPORTANT:
RUNNABLE does NOT mean thread is executing immediately.

It means:
        thread is ready to run
        and waiting for CPU scheduling.

OS Scheduler decides when thread gets CPU.

========================================================================

Example:

        t1.start();

========================================================================
3. RUNNING STATE
========================================================================

When CPU starts executing run() method,
thread becomes RUNNING.

Example:

        public void run() {
            System.out.println("Running");
        }

IMPORTANT:
Java does not provide separate RUNNING state in getState().

RUNNING is internally part of RUNNABLE.

========================================================================

VERY IMPORTANT UNDERSTANDING

RUNNABLE in Java means:

1. Ready to run
2. Currently running

Both combined.

========================================================================
4. BLOCKED STATE
========================================================================

Thread enters BLOCKED state when waiting for monitor lock.

Meaning:
another thread already owns synchronized lock.

========================================================================

Example:

Thread 1 enters synchronized block.

Thread 2 tries same synchronized block.

Thread 2 becomes:
        BLOCKED

========================================================================

Example:

synchronized(obj) {

}

========================================================================
5. WAITING STATE
========================================================================

Thread waits indefinitely
until another thread wakes it up.

Methods causing WAITING:

1. wait()
2. join()

========================================================================

Example:

        t1.join();

Main thread waits until t1 finishes.

========================================================================

Example:

object.wait();

Thread waits until notify()/notifyAll().

========================================================================
6. TIMED_WAITING STATE
========================================================================

Thread waits for specific amount of time.

Methods:

1. sleep(time)
2. wait(time)
3. join(time)

========================================================================

Example:

        Thread.sleep(3000);

Thread pauses for 3 seconds.

========================================================================

IMPORTANT:
sleep() does NOT release lock.

========================================================================
7. TERMINATED STATE
========================================================================

After run() method finishes,
thread dies.

Thread cannot restart again.

========================================================================

Example:

        System.out.println(t1.getState());

OUTPUT:
        TERMINATED

========================================================================

IMPORTANT RULE

Once thread is terminated,
calling start() again causes:

        IllegalThreadStateException

========================================================================

EXAMPLE

        t1.start();
        t1.start();

INVALID

========================================================================
REAL FLOW OF THREAD
========================================================================

Create Object
        ↓
NEW

start()
        ↓
RUNNABLE

CPU Executes
        ↓
RUNNING

sleep()/wait()/lock
        ↓
WAITING/BLOCKED/TIMED_WAITING

Resume
        ↓
RUNNABLE

run() finished
        ↓
TERMINATED

========================================================================
IMPORTANT DIFFERENCE
========================================================================

BLOCKED
--------
Waiting for lock.

WAITING
--------
Waiting indefinitely for another thread signal.

TIMED_WAITING
-------------
Waiting for specified time.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What are thread life cycle states in Java?

→ NEW
→ RUNNABLE
→ BLOCKED
→ WAITING
→ TIMED_WAITING
→ TERMINATED

========================================================================

2. Difference between RUNNABLE and RUNNING?

→ Java combines both into RUNNABLE state.

========================================================================

3. Which method moves thread to RUNNABLE state?

→ start()

========================================================================

4. Which method causes TIMED_WAITING?

→ sleep(time)

========================================================================

5. Difference between BLOCKED and WAITING?

BLOCKED
→ waiting for lock

WAITING
→ waiting for signal/notification

========================================================================

6. Can we restart thread after termination?

→ No

Otherwise:
        IllegalThreadStateException

========================================================================

7. Which thread state occurs before start()?

→ NEW

========================================================================

8. Which method causes WAITING state?

→ wait()
→ join()

========================================================================

9. Does sleep() release lock?

→ No

========================================================================

10. Does wait() release lock?

→ Yes

========================================================================

11. Who controls thread scheduling?

→ OS Scheduler + JVM

========================================================================

MOST IMPORTANT INTERVIEW LINE

Thread lifecycle represents different states
through which thread passes from creation till termination.

========================================================================

*/