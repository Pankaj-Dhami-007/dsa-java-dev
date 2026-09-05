package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecuteVsSubmit {

    public static void main(String[] args) {

        ExecutorService service =
                Executors.newFixedThreadPool(2);

        // ============================================================
        // execute()
        // ============================================================

        Runnable task1 = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " executing task using execute()"
            );
        };

        service.execute(task1);

        // ============================================================
        // submit()
        // ============================================================

        Runnable task2 = () -> {

            System.out.println(
                    Thread.currentThread().getName()
                            + " executing task using submit()"
            );
        };

        service.submit(task2);

        service.shutdown();
    }
}

/*

========================================================================
                    execute() vs submit()
========================================================================

VERY IMPORTANT INTERVIEW TOPIC

========================================================================

Both methods used to:
        submit tasks to ExecutorService

========================================================================

BUT:
important differences exist.

========================================================================
1. execute()
========================================================================

Defined in:
        Executor interface

========================================================================

METHOD
========================================================================

execute(Runnable task)

========================================================================

PURPOSE
========================================================================

Executes Runnable task.

========================================================================

RETURN TYPE
========================================================================

void

========================================================================

Meaning:
no result returned.

========================================================================
2. submit()
========================================================================

Defined in:
        ExecutorService interface

========================================================================

PURPOSE
========================================================================

Submits:
- Runnable
- Callable

========================================================================

RETURN TYPE
========================================================================

Future object

========================================================================

Meaning:
can track task result/status later.

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

execute()
--------
fire-and-forget style

========================================================================

submit()
---------
trackable asynchronous task

========================================================================
CODE FLOW
========================================================================

service.execute(task1)
        ↓
task executes
        ↓
no result returned

========================================================================

service.submit(task2)
        ↓
task executes
        ↓
Future returned

========================================================================
BIGGEST DIFFERENCE
========================================================================

execute()
--------
No Future support

========================================================================

submit()
---------
Supports Future

========================================================================
VERY IMPORTANT CONCEPT
========================================================================

submit() supports:
        Callable

========================================================================

Callable can:
- return value
- throw checked exception

========================================================================

execute() only supports:
        Runnable

========================================================================
EXCEPTION HANDLING DIFFERENCE
========================================================================

execute()
--------
Exception directly visible.

========================================================================

submit()
---------
Exception wrapped inside Future.

========================================================================

This is VERY IMPORTANT interview point.

========================================================================
EXAMPLE
========================================================================

execute()
--------
If exception occurs:
stacktrace visible immediately.

========================================================================

submit()
---------
Need:
        future.get()

to see exception.

========================================================================
WHY submit() MORE COMMON?
========================================================================

Because modern applications often need:
- task result
- async tracking
- cancellation
- exception handling

========================================================================
WHEN TO USE execute()?
========================================================================

Use when:
- no result needed
- simple background task

========================================================================
WHEN TO USE submit()?
========================================================================

Use when:
- result needed
- async tracking needed
- Callable used

========================================================================
IMPORTANT ADVANTAGES
========================================================================

execute()
--------
simple and lightweight

========================================================================

submit()
---------
powerful and flexible

========================================================================
IMPORTANT LIMITATIONS
========================================================================

execute()
--------
No result tracking

========================================================================

submit()
---------
Slightly more overhead

========================================================================
REAL LIFE ANALOGY
========================================================================

execute()
--------
You tell worker:
        "Just do this work."

========================================================================

submit()
---------
You tell worker:
        "Do this work and report result back."

========================================================================
OUTPUT CAN BE
========================================================================

pool-1-thread-1 executing task using execute()

pool-1-thread-2 executing task using submit()

========================================================================
IMPORTANT INTERVIEW DIFFERENCE
========================================================================

execute()
--------
Runnable only

========================================================================

submit()
---------
Runnable + Callable

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. Difference between execute() and submit()?

execute()
-----------
No return value

submit()
----------
Returns Future

========================================================================

2. Which method belongs to Executor interface?

→ execute()

========================================================================

3. Which method belongs to ExecutorService?

→ submit()

========================================================================

4. Which method supports Callable?

→ submit()

========================================================================

5. Which method more commonly used?

→ submit()

========================================================================

6. Which method follows fire-and-forget style?

→ execute()

========================================================================

7. Which method supports async result tracking?

→ submit()

========================================================================

8. What does submit() return?

→ Future object

========================================================================

9. Which method better for simple background task?

→ execute()

========================================================================

MOST IMPORTANT INTERVIEW LINE

execute() simply executes Runnable tasks
without returning result,
whereas submit() supports task tracking
through Future objects.

========================================================================

*/