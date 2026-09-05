package EngineeringDigest.multithreading.memorymodel;

class SharedFlag {

    // volatile variable
    volatile boolean running = true;
}

public class VolatileKeywordExample {

    public static void main(String[] args) {

        SharedFlag flag =
                new SharedFlag();

        // worker thread
        Thread worker = new Thread(() -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " started working..."
            );

            // continuously checking flag
            while (flag.running) {

                // doing some work
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " stopped because flag became false"
            );
        });

        worker.setName("Worker-Thread");

        worker.start();

        // main thread sleeps
        try {

            Thread.sleep(3000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(
                Thread.currentThread().getName()
                        + " changing running flag to false"
        );

        // updating shared variable
        flag.running = false;

        System.out.println(
                "Main thread finished"
        );
    }
}

/*

========================================================================
                VOLATILE KEYWORD EXAMPLE
========================================================================

This is MOST COMMON volatile example.

========================================================================
MAIN IDEA
========================================================================

One thread updates variable.

========================================================================

Another thread continuously reads variable.

========================================================================

Need:
updated value visible immediately.

========================================================================
SHARED RESOURCE
========================================================================

SharedFlag object shared between:
- Main Thread
- Worker Thread

========================================================================
IMPORTANT VARIABLE
========================================================================

volatile boolean running = true;

========================================================================

volatile guarantees:
latest value visibility.

========================================================================
FLOW OF WORKER THREAD
========================================================================

Worker thread starts:
========================================================================

while(flag.running)

========================================================================

Continuously checking:
        running flag

========================================================================

Initially:
        running = true

========================================================================

So loop continues forever.

========================================================================
FLOW OF MAIN THREAD
========================================================================

Main thread sleeps:
        3 seconds

========================================================================

After sleep:
========================================================================

flag.running = false;

========================================================================

IMPORTANT
========================================================================

Because variable is volatile,
updated value immediately visible
to worker thread.

========================================================================
WHAT HAPPENS NEXT?
========================================================================

Worker thread reads latest value:
        false

========================================================================

Loop stops.

========================================================================

Worker thread exits safely.

========================================================================
WITHOUT VOLATILE
========================================================================

Worker thread may cache:
        running = true

========================================================================

Even after main thread changes:
        false

========================================================================

Worker may continue infinite loop forever.

========================================================================

THIS IS:
        Visibility Problem

========================================================================
WITH VOLATILE
========================================================================

Every read happens from:
        main memory

========================================================================

So worker immediately sees:
        false

========================================================================
OUTPUT CAN BE
========================================================================

Worker-Thread started working...

main changing running flag to false

Main thread finished

Worker-Thread stopped because flag became false

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

volatile solved:
        visibility issue

========================================================================

NOT:
        synchronization problem

========================================================================
WHY THIS EXAMPLE IMPORTANT?
========================================================================

Because:
- simplest visibility problem
- real-world shutdown signal example
- common interview question

========================================================================
REAL LIFE ANALOGY
========================================================================

Main thread updates:
central notice board.

========================================================================

Worker thread continuously checks:
latest notice.

========================================================================

volatile ensures:
worker always reads latest notice.

========================================================================
IMPORTANT INTERVIEW QUESTION
========================================================================

Q. Why worker thread stops correctly here?

========================================================================

ANSWER
========================================================================

Because volatile guarantees
latest value visibility across threads.

========================================================================
IMPORTANT INTERVIEW POINT
========================================================================

volatile suitable for:
- status flags
- shutdown signals
- simple shared states

========================================================================

NOT suitable for:
- counter increment
- compound operations

========================================================================
MOST IMPORTANT INTERVIEW LINE

volatile ensures that changes made
by one thread become immediately visible
to other threads.

========================================================================

*/