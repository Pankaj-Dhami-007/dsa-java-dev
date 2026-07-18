package EngineeringDigest.multithreading.executerFramework;

public class RunnableVsCallable {
}

/*

========================================================================
                RUNNABLE vs CALLABLE
========================================================================

VERY IMPORTANT INTERVIEW TOPIC

========================================================================
WHY CALLABLE INTRODUCED?
========================================================================

Runnable had limitations:
- no return value
- no checked exception support

========================================================================

To solve this:
        Callable introduced

========================================================================
WHAT IS RUNNABLE?
========================================================================

Functional interface representing task
that:
- does not return result
- cannot throw checked exception

========================================================================

METHOD
========================================================================

        run()

========================================================================

Signature:
========================================================================

void run()

========================================================================
WHAT IS CALLABLE?
========================================================================

Functional interface representing task
that:
- returns result
- can throw checked exception

========================================================================

METHOD
========================================================================

        call()

========================================================================

Signature:
========================================================================

V call() throws Exception

========================================================================
MAIN DIFFERENCE
========================================================================

Runnable
----------
No return value

========================================================================

Callable
----------
Returns value

========================================================================
IMPORTANT DIFFERENCE
========================================================================

Runnable
----------
Cannot throw checked exception

========================================================================

Callable
----------
Can throw checked exception

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Runnable mainly designed for:
        Thread class

========================================================================

Callable mainly designed for:
        Executor Framework

========================================================================
RETURN VALUE DIFFERENCE
========================================================================

Runnable
----------
Used for:
fire-and-forget tasks

========================================================================

Callable
----------
Used for:
result-oriented async tasks

========================================================================
EXAMPLE SCENARIO
========================================================================

Runnable
----------
Print log message

========================================================================

Callable
----------
Calculate total salary
and return result

========================================================================
EXCEPTION HANDLING DIFFERENCE
========================================================================

Runnable
----------
Cannot directly throw checked exception

========================================================================

Callable
----------
Supports checked exceptions naturally

========================================================================
IMPORTANT UNDERSTANDING
========================================================================

Runnable task:
        no result tracking

========================================================================

Callable task:
        works with Future

========================================================================
CALLABLE + FUTURE
========================================================================

Callable produces result
        ↓
Future tracks/retrieves result

========================================================================
THREAD COMPATIBILITY
========================================================================

Runnable
----------
Can directly run using:
        Thread class

========================================================================

Callable
----------
Cannot directly use:
        Thread.start()

========================================================================

Must use:
        ExecutorService

========================================================================
LAMBDA SUPPORT
========================================================================

Both are:
        Functional Interfaces

========================================================================

Both support:
        lambda expressions

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Runnable introduced in:
        Java 1.0

========================================================================

Callable introduced in:
        Java 5

========================================================================

As part of:
        Executor Framework

========================================================================
REAL LIFE ANALOGY
========================================================================

Runnable
----------
Worker does task silently.

========================================================================

Callable
----------
Worker does task
and returns report/result.

========================================================================
WHEN TO USE RUNNABLE?
========================================================================

Use when:
- no result needed
- lightweight background task
- simple async operation

========================================================================
WHEN TO USE CALLABLE?
========================================================================

Use when:
- result needed
- calculation required
- async response needed
- checked exceptions possible

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

RUNNABLE
----------
- logging
- background cleanup
- notifications

========================================================================

CALLABLE
----------
- API response
- DB query result
- calculations
- report generation

========================================================================
IMPORTANT ADVANTAGES
========================================================================

RUNNABLE
----------
simple and lightweight

========================================================================

CALLABLE
----------
powerful and flexible

========================================================================
IMPORTANT LIMITATIONS
========================================================================

RUNNABLE
----------
No result support

========================================================================

CALLABLE
----------
Requires ExecutorService/Future

========================================================================
SUMMARY TABLE
========================================================================

RUNNABLE
----------
Method:
        run()

Return Type:
        void

Checked Exception:
        No

Thread Compatible:
        Yes

Future Support:
        No direct support

========================================================================

CALLABLE
----------
Method:
        call()

Return Type:
        Generic Value

Checked Exception:
        Yes

Thread Compatible:
        No direct support

Future Support:
        Yes

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Difference between Runnable and Callable?

Runnable
----------
No result

Callable
----------
Returns result

========================================================================

2. Which method belongs to Runnable?

→ run()

========================================================================

3. Which method belongs to Callable?

→ call()

========================================================================

4. Which supports checked exception?

→ Callable

========================================================================

5. Which works directly with Thread class?

→ Runnable

========================================================================

6. Which works with Future?

→ Callable

========================================================================

7. Is Callable functional interface?

→ Yes

========================================================================

8. Which introduced later?

→ Callable

========================================================================

9. Why Callable introduced?

→ To support result returning and checked exceptions.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Runnable is used for tasks
that do not return result,
whereas Callable supports asynchronous tasks
that return values and throw checked exceptions.

========================================================================

*/