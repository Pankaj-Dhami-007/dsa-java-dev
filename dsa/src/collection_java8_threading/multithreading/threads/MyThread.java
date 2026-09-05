package EngineeringDigest.multithreading.threads;

// Step 1:
// Create custom thread by extending Thread class

class MyCustomThread extends Thread {

    // Step 2:
    // Override run() method
    // run() contains task that thread will execute

    @Override
    public void run() {

        // current executing thread
        System.out.println("Thread is running");

        System.out.println(Thread.currentThread().getName());

        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
    }
}

public class MyThread {

    public static void main(String[] args) {

        System.out.println("Main Thread Started");

        // Step 3:
        // Create object of custom thread

        MyCustomThread t1 = new MyCustomThread();

        // Step 4:
        // start() creates new thread internally
        // then JVM calls run() method automatically

        t1.start();

        System.out.println("Main Thread Ended");
    }
}

/*

==================== CREATING THREAD USING THREAD CLASS ====================

STEP 1:
Extend Thread class

        class MyCustomThread extends Thread

========================================================================

STEP 2:
Override run() method

        public void run()

run() contains actual work/task of thread.

========================================================================

STEP 3:
Create object

        MyCustomThread t1 = new MyCustomThread();

At this point:
Thread is created but NOT started.

State:
        NEW

========================================================================

STEP 4:
Call start()

        t1.start();

IMPORTANT:
Never call run() directly for multithreading.

start() does 2 things internally:

1. Creates new thread
2. JVM automatically calls run()

========================================================================

FLOW

main thread
    ↓
t1.start()
    ↓
JVM creates new thread
    ↓
new thread executes run()

========================================================================

OUTPUT CAN BE:

Main Thread Started
Main Thread Ended
Thread is running
Thread-0
1
2
3
4
5

OR

Main Thread Started
Thread is running
Thread-0
1
2
3
4
5
Main Thread Ended

========================================================================

WHY OUTPUT CHANGES?

Because thread scheduling is controlled by:
        OS Scheduler + JVM

Execution order is NOT guaranteed.

This is called:
        Concurrent Execution

========================================================================

IMPORTANT DIFFERENCE

run()
------
Normal method call.
No new thread created.

start()
--------
Creates separate thread.
Then JVM calls run() internally.

========================================================================

INTERVIEW QUESTIONS

1. What is thread?
→ Smallest unit of execution.

2. How to create thread using Thread class?
→ Extend Thread class and override run() method.

3. Which method contains task of thread?
→ run()

4. Which method actually starts new thread?
→ start()

5. Can we call run() directly?
→ Yes, but no new thread will be created.

6. Who calls run() method internally?
→ JVM after start().

7. What is default name of thread?
→ Thread-0, Thread-1 etc.

========================================================================

VERY IMPORTANT MEMORY POINT

start()  !=  run()

start()
    → creates new thread

run()
    → task executed by thread

========================================================================

*/