package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureConcept {

    public static void main(String[] args)
            throws ExecutionException, InterruptedException {

        // creating thread pool
        ExecutorService service =
                Executors.newFixedThreadPool(2);

        // callable task
        Callable<Integer> task = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " calculating result..."
            );

            Thread.sleep(3000);

            return 100;
        };

        // submitting callable task
        Future<Integer> future =
                service.submit(task);

        System.out.println("Main thread doing other work...");

        // getting result
        Integer result = future.get();

        System.out.println("Result : " + result);

        service.shutdown();
    }
}

/*

========================================================================
                            FUTURE
========================================================================

Future is one of MOST IMPORTANT concepts
in modern asynchronous programming.

========================================================================
WHY FUTURE EXISTS?
========================================================================

Callable can return result.

========================================================================

BUT:
how to get result from background thread?

========================================================================

Because task executes:
        asynchronously

========================================================================

Result may not be ready immediately.

========================================================================

This problem solved by:
        Future

========================================================================
WHAT IS FUTURE?
========================================================================

Future represents:
        future/pending result
of asynchronous computation.

========================================================================

SIMPLE DEFINITION
========================================================================

Future is object used to:
- track async task
- retrieve result later
- check completion status
- cancel task

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Future does NOT contain immediate result.

========================================================================

It represents:
        result that may arrive later.

========================================================================
MAIN IDEA
========================================================================

Submit task now
        ↓
Get result later

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
HOW FUTURE CREATED?
========================================================================

Using:
        ExecutorService.submit()

========================================================================

Example:
========================================================================

Future<Integer> future =
        service.submit(task);

========================================================================

submit() immediately returns:
        Future object

========================================================================

Task continues in background.

========================================================================
VERY IMPORTANT FLOW
========================================================================

Callable task submitted
        ↓
Task executes asynchronously
        ↓
Future returned immediately
        ↓
Result available later

========================================================================
IMPORTANT METHODS
========================================================================

1. get()
2. isDone()
3. cancel()
4. isCancelled()

========================================================================
1. get()
========================================================================

Returns task result.

========================================================================

IMPORTANT
========================================================================

If result not ready:
        get() waits

========================================================================

This is BLOCKING call.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

future.get()
--------
Main thread pauses until result ready.

========================================================================
CODE FLOW
========================================================================

Task submitted
        ↓
Background thread starts calculation
        ↓
Main thread continues independently

========================================================================

future.get()
        ↓
waits if task incomplete
        ↓
returns result when ready

========================================================================
OUTPUT CAN BE
========================================================================

Main thread doing other work...

pool-1-thread-1 calculating result...

(after 3 seconds)

Result : 100

========================================================================

IMPORTANT
========================================================================

Main thread did NOT execute calculation.

========================================================================

Worker thread handled task asynchronously.

========================================================================
2. isDone()
========================================================================

Checks:
task completed or not.

========================================================================

Returns:
- true
- false

========================================================================

Useful for non-blocking checks.

========================================================================
3. cancel()
========================================================================

Attempts to cancel task.

========================================================================

Example:
========================================================================

future.cancel(true);

========================================================================
4. isCancelled()
========================================================================

Checks whether task cancelled.

========================================================================
VERY IMPORTANT CONCEPT
========================================================================

Future enables:
        asynchronous programming

========================================================================

Meaning:
main thread need not wait immediately.

========================================================================

Can continue other work.

========================================================================
WHY FUTURE IMPORTANT?
========================================================================

Without Future:
no easy way to:
- retrieve async result
- track task status
- cancel task

========================================================================

Future solves all these.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
you order pizza online.

========================================================================

Restaurant gives:
        order token

========================================================================

Pizza preparing asynchronously.

========================================================================

Later:
using token,
you collect pizza.

========================================================================

Future acts like:
        async result token

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Future itself does NOT execute task.

========================================================================

ExecutorService executes task.

========================================================================

Future only:
tracks task/result.

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Async result retrieval
2. Task tracking
3. Cancellation support
4. Non-blocking workflow
5. Better concurrency

========================================================================
IMPORTANT LIMITATION
========================================================================

future.get() is BLOCKING.

========================================================================

If task takes long:
calling thread waits.

========================================================================

This limitation later improved by:
        CompletableFuture

========================================================================
CALLABLE + FUTURE RELATIONSHIP
========================================================================

Callable
----------
Produces result

========================================================================

Future
--------
Holds/tracks result

========================================================================

ExecutorService
----------------
Executes task

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

submit() returns:
        Future

========================================================================

execute() does NOT return Future.

========================================================================
REAL-WORLD USE CASES
========================================================================

1. API aggregation
2. Async database calls
3. Parallel computations
4. Background processing
5. Microservices communication

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Future?

→ Represents result of asynchronous computation.

========================================================================

2. Which method returns Future?

→ submit()

========================================================================

3. Which method retrieves result?

→ get()

========================================================================

4. Is future.get() blocking?

→ Yes

========================================================================

5. Which interface commonly works with Future?

→ Callable

========================================================================

6. Can Future cancel task?

→ Yes

========================================================================

7. Which method checks task completion?

→ isDone()

========================================================================

8. Does Future execute task itself?

→ No

========================================================================

9. Main limitation of Future?

→ get() blocks thread.

========================================================================

10. Which modern API improves Future limitations?

→ CompletableFuture

========================================================================

MOST IMPORTANT INTERVIEW LINE

Future represents the pending result
of asynchronous computation
and allows task tracking and result retrieval later.

========================================================================

*/