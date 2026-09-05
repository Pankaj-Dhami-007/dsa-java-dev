package EngineeringDigest.multithreading.threads;

public class DaemonThreadConcept extends Thread {

    @Override
    public void run() {

        while (true) {
            System.out.println(
                    Thread.currentThread().getName()
                            + " is running"
            );
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        DaemonThreadConcept t1 = new DaemonThreadConcept();

        // setting daemon thread
        t1.setDaemon(true);
        t1.start();
        System.out.println("Main Thread Completed");
    }
}

/*

========================================================================
                        DAEMON THREAD
========================================================================

In Java, threads are of 2 types:

1. User Thread
2. Daemon Thread

========================================================================
1. USER THREAD
========================================================================

Normal threads created by programmer.

Example:
- main thread
- worker thread

JVM waits for user threads to finish.

========================================================================
2. DAEMON THREAD
========================================================================

Background service thread.

Provides support/services to user threads.

========================================================================

EXAMPLES IN JVM
========================================================================

- Garbage Collector
- Finalizer
- Signal Dispatcher

========================================================================

IMPORTANT DEFINITION
========================================================================

Daemon thread is a low-priority background thread
that runs to support user threads.

========================================================================

IMPORTANT RULE
========================================================================

JVM exits when:
        all user threads finish

Even if daemon threads are still running,
JVM terminates them automatically.

========================================================================

FLOW OF YOUR PROGRAM
========================================================================

main thread starts
        ↓
t1 created
        ↓
t1 becomes daemon
        ↓
t1.start()
        ↓
daemon thread runs
        ↓
main thread finishes
        ↓
no user thread left
        ↓
JVM terminates daemon thread automatically

========================================================================

OUTPUT CAN BE
========================================================================

Main Thread Completed

========================================================================

OR

Main Thread Completed
Thread-0 is running

========================================================================

OR

Main Thread Completed
Thread-0 is running
Thread-0 is running

========================================================================

WHY?
========================================================================

Because daemon thread may terminate anytime
after main thread completion.

========================================================================

IMPORTANT METHOD
========================================================================

        t1.setDaemon(true);

PURPOSE
--------
Converts normal thread into daemon thread.

========================================================================

IMPORTANT RULE
========================================================================

setDaemon(true) must be called BEFORE start()

========================================================================

INVALID
========================================================================

        t1.start();
        t1.setDaemon(true);

Throws:
        IllegalThreadStateException

========================================================================

CHECKING DAEMON THREAD
========================================================================

        t1.isDaemon()

Returns:
        true / false

========================================================================

EXAMPLE
========================================================================

System.out.println(t1.isDaemon());

========================================================================

DEFAULT THREAD TYPES
========================================================================

main thread
--------
User thread

Threads created by main thread
--------
inherit parent thread type

========================================================================

IMPORTANT UNDERSTANDING
========================================================================

If parent thread is daemon,
child thread also becomes daemon by default.

========================================================================

WHY DAEMON THREADS EXIST?
========================================================================

For background tasks like:
- garbage collection
- monitoring
- auto-saving
- cleanup services

========================================================================

DAEMON THREAD CHARACTERISTICS
========================================================================

1. Background thread
2. Low priority service thread
3. JVM does not wait for daemon thread
4. Automatically terminates with JVM

========================================================================

REAL LIFE ANALOGY
========================================================================

User Thread
------------
Customer in restaurant

Daemon Thread
--------------
Cleaner/helper staff

If all customers leave,
restaurant closes even if cleaners working.

========================================================================

IMPORTANT DIFFERENCE
========================================================================

USER THREAD
------------
Keeps JVM alive

DAEMON THREAD
--------------
Does not keep JVM alive

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. What is daemon thread?

→ Background support thread.

========================================================================

2. Does JVM wait for daemon threads?

→ No

========================================================================

3. Which method makes thread daemon?

→ setDaemon(true)

========================================================================

4. When should setDaemon(true) be called?

→ Before start()

========================================================================

5. What happens if setDaemon() called after start()?

→ IllegalThreadStateException

========================================================================

6. Example of daemon thread in JVM?

→ Garbage Collector

========================================================================

7. Difference between user thread and daemon thread?

User Thread
------------
Keeps JVM alive

Daemon Thread
--------------
JVM does not wait for it

========================================================================

8. What is default type of main thread?

→ User thread

========================================================================

9. Do child threads inherit daemon property?

→ Yes

========================================================================

MOST IMPORTANT INTERVIEW LINE

Daemon thread is a background service thread
that automatically terminates when all user threads finish.

========================================================================

*/