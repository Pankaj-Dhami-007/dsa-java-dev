package EngineeringDigest.multithreading.virtualthreads;

public class VirtualThreadExample {

    public static void main(String[] args)
            throws InterruptedException {

        System.out.println(
                "Main Thread : "
                        + Thread.currentThread()
        );

        // creating virtual thread
        Thread virtualThread1 =
                Thread.startVirtualThread(() -> {

                    System.out.println(
                            Thread.currentThread()
                                    + " started work"
                    );

                    try {

                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(
                            Thread.currentThread()
                                    + " completed work"
                    );
                });

        // another virtual thread
        Thread virtualThread2 =
                Thread.startVirtualThread(() -> {

                    System.out.println(
                            Thread.currentThread()
                                    + " processing request"
                    );

                    try {

                        Thread.sleep(2000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    System.out.println(
                            Thread.currentThread()
                                    + " response sent"
                    );
                });

        virtualThread1.join();
        virtualThread2.join();

        System.out.println();
        System.out.println(
                "Main thread completed"
        );
    }
}

/*

========================================================================
                    VIRTUAL THREAD EXAMPLE
========================================================================

This example demonstrates:
modern lightweight thread execution.

========================================================================
MAIN IDEA
========================================================================

Create lightweight threads
without expensive OS-thread cost.

========================================================================
IMPORTANT CODE
========================================================================

Thread.startVirtualThread(() -> {

});

========================================================================

Creates:
        Virtual Thread

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Unlike traditional thread:
========================================================================

new Thread()

========================================================================

Virtual Thread is:
JVM-managed lightweight thread.

========================================================================
STEP-BY-STEP FLOW
========================================================================

Main thread starts.

========================================================================

Two virtual threads created:
========================================================================

virtualThread1
virtualThread2

========================================================================

Both tasks execute concurrently.

========================================================================

JVM schedules them
on carrier OS threads internally.

========================================================================
IMPORTANT OBSERVATION
========================================================================

Inside lambda:
========================================================================

Thread.currentThread()

========================================================================

shows:
========================================================================

VirtualThread[#...]

========================================================================

Meaning:
currently executing virtual thread.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

When virtual thread calls:
========================================================================

Thread.sleep()

========================================================================

virtual thread blocks logically.

========================================================================

BUT:
carrier OS thread released.

========================================================================

Another virtual thread may use it.

========================================================================
THIS IS BIG ADVANTAGE
========================================================================

Traditional platform thread:
OS thread blocked directly.

========================================================================

Virtual thread:
carrier thread reusable.

========================================================================

Massive scalability improvement.

========================================================================
OUTPUT CAN BE
========================================================================

Main Thread : Thread[#1,main,5,main]

========================================================================

VirtualThread[#21]/runnable@ForkJoinPool...

started work

========================================================================

VirtualThread[#22]/runnable@ForkJoinPool...

processing request

========================================================================

VirtualThread[#22] response sent

========================================================================

VirtualThread[#21] completed work

========================================================================

Main thread completed

========================================================================
IMPORTANT OBSERVATION
========================================================================

Both virtual threads execute concurrently
using very lightweight resources.

========================================================================
WHY join() USED?
========================================================================

Main thread waits until:
virtual threads complete.

========================================================================

Otherwise:
program may terminate early.

========================================================================
WHY VIRTUAL THREAD POWERFUL?
========================================================================

Can create:
- thousands
- millions

of lightweight concurrent tasks.

========================================================================

Without massive OS-thread overhead.

========================================================================
VERY IMPORTANT ENTERPRISE UNDERSTANDING
========================================================================

Virtual threads make:
thread-per-request model
scalable again.

========================================================================

Huge impact on:
backend server architecture.

========================================================================
WHY BACKEND SERVERS BENEFIT?
========================================================================

API requests mostly:
========================================================================

I/O waiting tasks

========================================================================

Examples:
- database calls
- HTTP requests
- file access

========================================================================

Virtual threads handle waiting efficiently.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Virtual Threads are BEST for:
========================================================================

I/O-bound workloads

========================================================================

NOT necessarily:
heavy CPU-bound parallelism.

========================================================================
TRADITIONAL THREAD vs VIRTUAL THREAD
========================================================================

TRADITIONAL THREAD
-------------------
Heavy OS thread

========================================================================

VIRTUAL THREAD
----------------
Lightweight JVM-managed thread

========================================================================

TRADITIONAL THREAD
-------------------
Expensive blocking

========================================================================

VIRTUAL THREAD
----------------
Cheap blocking

========================================================================

TRADITIONAL THREAD
-------------------
Limited scalability

========================================================================

VIRTUAL THREAD
----------------
Massive scalability

========================================================================
REAL LIFE ANALOGY
========================================================================

Traditional threads:
every customer gets dedicated worker.

========================================================================

Virtual threads:
few workers efficiently handle
huge number of customers.

========================================================================

Much more scalable.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. REST APIs
2. Microservices
3. Database applications
4. Network servers
5. High-concurrency backend systems

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Lightweight threads
2. Better scalability
3. Simpler concurrent programming
4. Better resource utilization
5. Efficient blocking operations

========================================================================
IMPORTANT LIMITATION
========================================================================

Not best for:
heavy CPU-intensive computations.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Which method creates Virtual Thread?

→ Thread.startVirtualThread()

========================================================================

2. Are Virtual Threads OS threads?

→ No

========================================================================

3. Why Virtual Threads scalable?

→ Many virtual threads share fewer OS threads.

========================================================================

4. Best use case of Virtual Threads?

→ I/O-bound applications.

========================================================================

5. What happens when virtual thread blocks?

→ Carrier thread released for reuse.

========================================================================

6. Why Virtual Threads revolutionary?

→ Massive concurrency with simple programming style.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Virtual Threads provide lightweight scalable concurrency
by allowing millions of JVM-managed threads
to efficiently share a smaller number of OS threads.

========================================================================

*/