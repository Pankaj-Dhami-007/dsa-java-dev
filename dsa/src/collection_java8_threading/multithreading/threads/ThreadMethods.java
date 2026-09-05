package EngineeringDigest.multithreading.threads;

public class ThreadMethods extends Thread {

    public ThreadMethods(String name){
        super(name);
    }
    @Override
    public void run() {

        // current thread info
        System.out.println("Current Thread : "
                + Thread.currentThread().getName());

        for (int i = 1; i <= 5; i++) {

            try {
                // ================= sleep() =================
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(i);
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        // ================= currentThread() =================
        System.out.println(Thread.currentThread().getName());

        ThreadMethods t1 = new ThreadMethods("Dhami");

        // ================= setName() =================
        //t1.setName("Custom-Thread");

        // ================= getName() =================
        System.out.println(t1.getName());

        // ================= getState() =================
        System.out.println(t1.getState());

        // ================= isAlive() =================
        System.out.println(t1.isAlive());

        // ================= start() =================
        t1.start();

        // ================= getState() =================
        System.out.println(t1.getState());

        // ================= isAlive() =================
        System.out.println(t1.isAlive());

        // ================= join() =================
        t1.join();

        System.out.println("Main thread resumed");

        // ================= isAlive() =================
        System.out.println(t1.isAlive());

        // ================= getState() =================
        System.out.println(t1.getState());
    }
}

/*

========================================================================
                        IMPORTANT THREAD METHODS
========================================================================

1. start()
2. run()
3. sleep()
4. join()
5. currentThread()
6. getName()
7. setName()
8. getPriority()
9. setPriority()
10. isAlive()
11. getState()
12. interrupt()

========================================================================
1. start()
========================================================================

        t1.start();

PURPOSE
--------
Starts new thread.

INTERNALLY
-----------
1. JVM creates new thread
2. Calls run() method automatically

========================================================================

IMPORTANT

start() != run()

========================================================================

run()
------
Normal method call.
No new thread created.

start()
--------
Creates separate thread.

========================================================================

2. run()
========================================================================

        public void run()

PURPOSE
--------
Contains actual task/work of thread.

JVM executes run() after start().

========================================================================

3. sleep()
========================================================================

        Thread.sleep(1000);

PURPOSE
--------
Pauses current thread for specified time.

UNIT
----
milliseconds

1000 ms = 1 second

========================================================================

IMPORTANT

1. sleep() is static method.
2. Causes TIMED_WAITING state.
3. sleep() does NOT release lock.

========================================================================

Example

        Thread.sleep(3000);

Pause thread for 3 seconds.

========================================================================

4. join()
========================================================================

        t1.join();

PURPOSE
--------
Makes current thread wait until target thread completes.

========================================================================

Example

main thread
    ↓
t1.join()
    ↓
main waits
    ↓
t1 completes
    ↓
main resumes

========================================================================

IMPORTANT

join() causes:
        WAITING state

========================================================================

Example

Without join():
        main and t1 execute together

With join():
        main waits for t1 completion

========================================================================

5. currentThread()
========================================================================

        Thread.currentThread()

PURPOSE
--------
Returns currently executing thread object.

========================================================================

Example

        System.out.println(
            Thread.currentThread().getName()
        );

========================================================================

OUTPUT

main

OR

Thread-0

========================================================================

6. getName()
========================================================================

        t1.getName()

PURPOSE
--------
Returns thread name.

========================================================================

DEFAULT NAMES

Thread-0
Thread-1
Thread-2

========================================================================

7. setName()
========================================================================

        t1.setName("Worker-Thread");

PURPOSE
--------
Changes thread name.

Useful for:
- debugging
- logging
- monitoring

========================================================================

8. getPriority()
========================================================================

        t1.getPriority()

PURPOSE
--------
Returns thread priority.

========================================================================

DEFAULT PRIORITY

5

========================================================================

RANGE

MIN_PRIORITY = 1
NORM_PRIORITY = 5
MAX_PRIORITY = 10

========================================================================

9. setPriority()
========================================================================

        t1.setPriority(10);

PURPOSE
--------
Changes thread priority.

========================================================================

IMPORTANT

Higher priority does NOT guarantee execution.

OS Scheduler decides actual execution.

========================================================================

10. isAlive()
========================================================================

        t1.isAlive()

PURPOSE
--------
Checks whether thread is alive.

========================================================================

RETURNS

true
------
if thread started and not terminated

false
-------
if thread not started OR completed

========================================================================

11. getState()
========================================================================

        t1.getState()

PURPOSE
--------
Returns current thread state.

========================================================================

POSSIBLE STATES

NEW
RUNNABLE
BLOCKED
WAITING
TIMED_WAITING
TERMINATED

========================================================================

12. interrupt()
========================================================================

        t1.interrupt()

PURPOSE
--------
Interrupts thread.

Mostly used to stop:
- sleep()
- wait()
- join()

========================================================================

Example

try {
    Thread.sleep(5000);
}
catch (InterruptedException e) {
    System.out.println("Interrupted");
}

========================================================================

IMPORTANT FLOW OF YOUR PROGRAM
========================================================================

main thread starts
        ↓
t1 object created
        ↓
NEW state
        ↓
t1.start()
        ↓
RUNNABLE state
        ↓
run() executes
        ↓
sleep(1000)
        ↓
TIMED_WAITING
        ↓
resume
        ↓
loop finishes
        ↓
TERMINATED
        ↓
main resumes after join()

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. Difference between start() and run()?

start()
--------
Creates new thread

run()
------
Normal method call

========================================================================

2. Which method pauses thread?

→ sleep()

========================================================================

3. Does sleep() release lock?

→ No

========================================================================

4. Which method waits for another thread completion?

→ join()

========================================================================

5. Which method returns currently executing thread?

→ currentThread()

========================================================================

6. What is default thread priority?

→ 5

========================================================================

7. Does higher priority guarantee execution first?

→ No

========================================================================

8. Which method checks thread state?

→ getState()

========================================================================

9. Which method checks thread alive or not?

→ isAlive()

========================================================================

10. Which method interrupts thread?

→ interrupt()

========================================================================

11. Which methods throw InterruptedException?

→ sleep()
→ join()
→ wait()

========================================================================

12. Which method causes WAITING state?

→ join()

========================================================================

13. Which method causes TIMED_WAITING state?

→ sleep()

========================================================================

MOST IMPORTANT INTERVIEW LINE

Thread methods help control
thread execution, coordination,
state management, and scheduling.

========================================================================

*/