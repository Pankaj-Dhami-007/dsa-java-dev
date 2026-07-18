package EngineeringDigest.multithreading.threads;

class MyThread_1 extends Thread {

    @Override
    public void run() {

        try {
            for (int i = 1; i <= 5; i++) {

                System.out.println(i);

                // thread goes into sleep
                Thread.sleep(1000);
            }

        } catch (InterruptedException e) {

            System.out.println("Thread interrupted");
            System.out.println(Thread.currentThread().getName());
        }
    }
}

public class ThreadInterruptMethod {

    public static void main(String[] args) {

        MyThread_1 t1 = new MyThread_1();
        t1.start();

        // interrupting thread
        t1.interrupt();
        System.out.println("Main thread completed");
    }
}

/*

========================================================================
                        interrupt() METHOD
========================================================================

        t1.interrupt();

PURPOSE
--------
Interrupts a thread.

Interrupt means:
        sending interruption signal to thread.

========================================================================

IMPORTANT UNDERSTANDING

interrupt() does NOT directly stop thread.

It only tells thread:

        "Please stop if possible"

========================================================================

THREAD INTERRUPTION IS COOPERATIVE

Meaning:
thread itself handles interruption.

========================================================================

HOW interrupt() WORKS INTERNALLY
========================================================================

When interrupt() is called:

        t1.interrupt();

JVM sets:
        interrupt flag = true

========================================================================

IF THREAD IS:
------------------------------------------------------------------------

1. sleeping
2. waiting
3. joining

then JVM throws:

        InterruptedException

========================================================================

FLOW OF YOUR PROGRAM
========================================================================

main thread
    ↓
t1.start()
    ↓
new thread starts
    ↓
thread sleeps
    ↓
main calls interrupt()
    ↓
JVM throws InterruptedException
    ↓
catch block executes

========================================================================

OUTPUT CAN BE
========================================================================

1
Main thread completed
Thread interrupted
Thread-0

========================================================================

OR

Main thread completed
Thread interrupted
Thread-0

========================================================================

OUTPUT MAY CHANGE

Because thread scheduling depends on:
        JVM + OS Scheduler

========================================================================
IMPORTANT POINT
========================================================================

interrupt() does NOT kill thread forcefully.

Java provides SAFE interruption mechanism.

========================================================================

EXAMPLE OF INTERRUPTING sleep()
========================================================================

try {

    Thread.sleep(5000);

}
catch (InterruptedException e) {

    System.out.println("Interrupted");

}

========================================================================

INTERRUPT FLAG
========================================================================

Every thread internally has:
        interrupt flag

========================================================================

When interrupt() called:
        flag becomes true

========================================================================

METHODS RELATED TO INTERRUPT
========================================================================

1. interrupt()
2. isInterrupted()
3. interrupted()

========================================================================
1. interrupt()
========================================================================

        t1.interrupt();

Sets interrupt flag.

========================================================================
2. isInterrupted()
========================================================================

        t1.isInterrupted()

Checks interrupt flag.

DOES NOT clear flag.

========================================================================

Example

System.out.println(t1.isInterrupted());

========================================================================
3. interrupted()
========================================================================

        Thread.interrupted()

Static method.

Checks interrupt status of:
        current thread

AND clears flag.

========================================================================

IMPORTANT DIFFERENCE
========================================================================

isInterrupted()
-----------------
Does NOT clear flag.

interrupted()
--------------
Clears flag.

========================================================================

EXAMPLE
========================================================================

Thread.currentThread().interrupt();

System.out.println(Thread.interrupted());

OUTPUT:
        true

Now flag becomes false.

========================================================================

WHEN INTERRUPT IS USED?
========================================================================

Used for:
- stopping long-running threads
- cancelling tasks
- shutdown mechanisms
- thread pool termination

========================================================================

REAL INDUSTRY EXAMPLE
========================================================================

Server shutdown
        ↓
interrupt worker threads
        ↓
threads stop gracefully

========================================================================

BAD PRACTICE
========================================================================

NEVER stop thread forcefully using:
        stop()

Deprecated and unsafe.

========================================================================

CORRECT WAY
========================================================================

Use:
        interrupt()

========================================================================

THREAD STATES AFFECTED
========================================================================

interrupt() mainly affects threads in:

1. WAITING
2. TIMED_WAITING

========================================================================

Example methods

sleep()
wait()
join()

========================================================================

IF THREAD IS RUNNING NORMALLY?
========================================================================

interrupt() only sets interrupt flag.

No exception automatically.

Thread must manually check interruption.

========================================================================

Example
========================================================================

while(!Thread.currentThread().isInterrupted()) {

    System.out.println("Working");

}

========================================================================

INTERVIEW QUESTIONS
========================================================================

1. What does interrupt() do?

→ Sends interruption signal to thread.

========================================================================

2. Does interrupt() stop thread immediately?

→ No

========================================================================

3. Which exception is thrown during interruption?

→ InterruptedException

========================================================================

4. Which methods throw InterruptedException?

→ sleep()
→ wait()
→ join()

========================================================================

5. Difference between isInterrupted() and interrupted()?

isInterrupted()
----------------
Does not clear flag

interrupted()
--------------
Clears flag

========================================================================

6. What happens internally when interrupt() is called?

→ JVM sets interrupt flag.

========================================================================

7. Why stop() method deprecated?

→ Unsafe thread termination.

========================================================================

8. Best way to stop thread?

→ interrupt()

========================================================================

MOST IMPORTANT INTERVIEW LINE

interrupt() is cooperative mechanism
used to request thread termination safely.

========================================================================

*/