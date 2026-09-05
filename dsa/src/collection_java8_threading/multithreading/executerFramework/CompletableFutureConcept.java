package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.CompletableFuture;

public class CompletableFutureConcept {

    public static void main(String[] args) {

        System.out.println("Main thread started");

        // asynchronous task
        CompletableFuture<Integer> future =
                CompletableFuture.supplyAsync(() -> {

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " calculating..."
                    );

                    try {

                        Thread.sleep(3000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    return 100;
                });

        System.out.println(
                "Main thread doing other work..."
        );

        // processing result
        future.thenAccept(result -> {

            System.out.println(
                    "Received Result : "
                            + result
            );
        });

        // prevent main thread from ending early
        try {

            Thread.sleep(5000);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

/*

========================================================================
                    COMPLETABLE FUTURE
========================================================================

CompletableFuture is MODERN asynchronous API
introduced in Java 8.

========================================================================
WHY COMPLETABLEFUTURE EXISTS?
========================================================================

Future had limitations.

========================================================================
LIMITATIONS OF FUTURE
========================================================================

1. get() is blocking
2. Cannot chain tasks easily
3. Difficult async composition
4. Manual callback handling
5. Difficult exception handling

========================================================================

Future was good,
but not powerful enough
for modern async programming.

========================================================================

This is why:
        CompletableFuture introduced.

========================================================================
WHAT IS COMPLETABLEFUTURE?
========================================================================

CompletableFuture is advanced asynchronous API
used for:
- non-blocking programming
- async task chaining
- combining multiple async tasks
- callback handling

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Future
--------
Represents async result.

========================================================================

CompletableFuture
------------------
Represents async result
PLUS async processing pipeline.

========================================================================
MAIN IDEA
========================================================================

Run task asynchronously
        ↓
Process result automatically later
without blocking main thread.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

CompletableFuture implements:
        Future interface

========================================================================

BUT adds many advanced async features.

========================================================================
CODE FLOW
========================================================================

supplyAsync()
        ↓
starts background task asynchronously

========================================================================

Main thread continues immediately.

========================================================================

Background thread calculates result.

========================================================================

thenAccept()
        ↓
automatically processes result
when computation completes.

========================================================================
IMPORTANT METHOD
========================================================================

1. supplyAsync()
========================================================================

Used when:
task RETURNS value.

========================================================================

Works similar to:
        Callable

========================================================================
2. runAsync()
========================================================================

Used when:
task does NOT return value.

========================================================================

Works similar to:
        Runnable

========================================================================
3. thenAccept()
========================================================================

Consumes/processes result asynchronously.

========================================================================

Acts like callback.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

No need for:
        future.get()

========================================================================

No blocking required.

========================================================================
OUTPUT CAN BE
========================================================================

Main thread started

Main thread doing other work...

ForkJoinPool.commonPool-worker-1 calculating...

Received Result : 100

========================================================================
MOST IMPORTANT UNDERSTANDING
========================================================================

Main thread never blocked.

========================================================================

Result automatically handled later.

========================================================================

THIS IS:
        Non-blocking asynchronous programming

========================================================================
WHY COMPLETABLEFUTURE VERY POWERFUL?
========================================================================

Supports:
- async chaining
- async pipelines
- combining tasks
- callbacks
- exception handling

========================================================================
EXAMPLE REAL FLOW
========================================================================

Call API
        ↓
Process response
        ↓
Save database
        ↓
Send notification

========================================================================

All asynchronously chained.

========================================================================
VERY IMPORTANT ADVANTAGE
========================================================================

Avoids:
        callback hell
and
manual thread waiting.

========================================================================
REAL LIFE ANALOGY
========================================================================

Future
--------
Order food and stand waiting.

========================================================================

CompletableFuture
------------------
Order food,
continue shopping,
restaurant calls automatically when ready.

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Non-blocking
2. Async chaining
3. Better readability
4. Better scalability
5. Automatic callback handling
6. Modern async programming

========================================================================
IMPORTANT LIMITATION
========================================================================

Complex async chains may become difficult
if poorly designed.

========================================================================

Requires good understanding.

========================================================================
COMPLETABLEFUTURE vs FUTURE
========================================================================

FUTURE
--------
Blocking get()

========================================================================

COMPLETABLEFUTURE
------------------
Non-blocking callbacks

========================================================================

FUTURE
--------
Limited async support

========================================================================

COMPLETABLEFUTURE
------------------
Advanced async pipelines

========================================================================

FUTURE
--------
Difficult chaining

========================================================================

COMPLETABLEFUTURE
------------------
Easy task chaining

========================================================================
IMPORTANT REAL-WORLD USE CASES
========================================================================

1. REST API aggregation
2. Microservices communication
3. Async database processing
4. Notification systems
5. Parallel computations
6. Reactive systems

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. Why CompletableFuture introduced?

→ To overcome Future limitations.

========================================================================

2. Main limitation of Future?

→ Blocking get().

========================================================================

3. Which Java version introduced CompletableFuture?

→ Java 8

========================================================================

4. Which method starts async task returning value?

→ supplyAsync()

========================================================================

5. Which method processes async result?

→ thenAccept()

========================================================================

6. Is CompletableFuture blocking?

→ No, mainly non-blocking.

========================================================================

7. Does CompletableFuture implement Future?

→ Yes

========================================================================

8. Difference between Future and CompletableFuture?

Future
--------
Basic async result holder

CompletableFuture
------------------
Advanced async processing framework

========================================================================

9. Which method similar to Runnable async task?

→ runAsync()

========================================================================

MOST IMPORTANT INTERVIEW LINE

CompletableFuture enables
non-blocking asynchronous programming
with task chaining and callback-based result handling.

========================================================================

*/