package EngineeringDigest.multithreading.threads;

// Step 1:
// Implement Runnable interface

class MyRunnable implements Runnable {

    // Step 2:
    // Override run() method

    @Override
    public void run() {

        System.out.println("Thread is running");

        System.out.println(Thread.currentThread().getName());

        for (int i = 1; i <= 5; i++) {
            System.out.println(i);
        }
    }
}

public class RunnableThreadDemo {

    public static void main(String[] args) {

        System.out.println("Main Thread Started");

        // Step 3:
        // Create Runnable object

        MyRunnable task = new MyRunnable();

        // Step 4:
        // Pass Runnable object to Thread class

        Thread t1 = new Thread(task);

        // Step 5:
        // Start thread

        t1.start();

        System.out.println("Main Thread Ended");
    }
}

/*

==================== CREATING THREAD USING RUNNABLE ====================

STEP 1:
Implement Runnable interface

        class MyRunnable implements Runnable

========================================================================

STEP 2:
Override run() method

        public void run()

run() contains actual task of thread.

========================================================================

STEP 3:
Create Runnable object

        MyRunnable task = new MyRunnable();

Runnable itself is NOT a thread.

It only represents:
        task/work to perform

========================================================================

STEP 4:
Pass Runnable object into Thread class

        Thread t1 = new Thread(task);

Now Thread object gets task to execute.

========================================================================

STEP 5:
Call start()

        t1.start();

JVM creates new thread internally
then calls run() method.

========================================================================

FLOW

Runnable Object
      ↓
Passed to Thread
      ↓
start()
      ↓
JVM creates new thread
      ↓
run() executes

========================================================================

IMPORTANT UNDERSTANDING

Runnable
---------
Represents task/work

Thread
-------
Represents actual thread

========================================================================

OUTPUT CAN CHANGE

Because thread scheduling is handled by:
        JVM + OS Scheduler

========================================================================

WHY RUNNABLE IS BETTER THAN THREAD CLASS?

VERY IMPORTANT INTERVIEW QUESTION

1. Java does not support multiple inheritance.

If we extend Thread class:
        class A extends Thread

then class A cannot extend another class.

========================================================================

2. Runnable separates:
        Task and Thread

Better design.

========================================================================

3. Multiple threads can share same Runnable object.

Example:

        Runnable task = new MyRunnable();

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

========================================================================

IMPORTANT DIFFERENCE

EXTENDING THREAD
----------------
Thread itself contains task.

IMPLEMENTING RUNNABLE
---------------------
Task is separate from thread.

========================================================================

INTERVIEW QUESTIONS

1. How to create thread using Runnable?
→ Implement Runnable interface and override run().

2. Is Runnable a thread?
→ No.

3. Which method starts thread?
→ start()

4. Which method contains task?
→ run()

5. Why Runnable preferred over Thread class?
→ Better design and supports inheritance.

6. Can multiple threads share same Runnable object?
→ Yes.

========================================================================

MOST IMPORTANT LINE

Runnable = task
Thread = worker executing task

========================================================================

*/