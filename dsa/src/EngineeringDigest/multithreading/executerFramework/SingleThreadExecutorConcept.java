package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SingleThreadExecutorConcept {

    public static void main(String[] args) {

        // creating single thread executor
        ExecutorService service =
                Executors.newSingleThreadExecutor();

        // submitting tasks
        for (int i = 1; i <= 5; i++) {

            int taskId = i;

            service.execute(() -> {

                System.out.println(
                        Thread.currentThread().getName()
                                + " executing Task-"
                                + taskId
                );

                try {

                    Thread.sleep(2000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(
                        Thread.currentThread().getName()
                                + " completed Task-"
                                + taskId
                );
            });
        }

        // shutdown executor
        service.shutdown();
    }
}

/*

========================================================================
                SINGLE THREAD EXECUTOR
========================================================================

SingleThreadExecutor is thread pool
containing ONLY ONE worker thread.

========================================================================
METHOD USED
========================================================================

Executors.newSingleThreadExecutor()

========================================================================
WHAT IS SINGLE THREAD EXECUTOR?
========================================================================

ExecutorService that executes:
        only one task at a time
using single worker thread.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Even if many tasks submitted:
ONLY ONE task executes at a time.

========================================================================

Remaining tasks wait in queue.

========================================================================
MAIN IDEA
========================================================================

Sequential task execution
with background thread management.

========================================================================
WHY SINGLE THREAD EXECUTOR EXISTS?
========================================================================

Sometimes task order is VERY IMPORTANT.

========================================================================

Example:
- bank transaction logs
- file writing
- report generation
- message processing

========================================================================

Tasks must execute:
        one by one in order.

========================================================================

SingleThreadExecutor guarantees this.

========================================================================
CODE FLOW
========================================================================

newSingleThreadExecutor()
        ↓
creates ONE worker thread

========================================================================

5 tasks submitted.

========================================================================

Task-1 executes first
        ↓
Task-2 waits
        ↓
Task-3 waits
        ↓
Task-4 waits

========================================================================

After Task-1 completes:
Task-2 starts.

========================================================================

This continues sequentially.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

NO parallel execution happens.

========================================================================

Only one worker thread processes all tasks.

========================================================================
OUTPUT CAN BE
========================================================================

pool-1-thread-1 executing Task-1
pool-1-thread-1 completed Task-1

pool-1-thread-1 executing Task-2
pool-1-thread-1 completed Task-2

========================================================================

IMPORTANT
========================================================================

Same thread reused for every task.

========================================================================
WHY NOT NORMAL THREAD?
========================================================================

Normal thread:
manual management required.

========================================================================

SingleThreadExecutor:
- automatic management
- task queue handling
- lifecycle support

========================================================================
VERY IMPORTANT BENEFIT
========================================================================

Guarantees:
        task execution order

========================================================================

Tasks execute in:
        FIFO order

========================================================================

First submitted
        ↓
first executed

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Sequential execution
2. Predictable ordering
3. Thread reuse
4. Simpler synchronization
5. Automatic lifecycle management

========================================================================
IMPORTANT LIMITATION
========================================================================

Only one task at a time.

========================================================================

Long-running task may delay all others.

========================================================================

Poor for:
heavy parallel workloads.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
only one cashier at shop.

========================================================================

Customers stand in queue.

========================================================================

Cashier handles:
one customer at a time.

========================================================================

Next customer waits until previous finishes.

========================================================================

Exactly same happens here.

========================================================================
SINGLE THREAD EXECUTOR vs FIXED THREAD POOL
========================================================================

SINGLE THREAD EXECUTOR
-----------------------
Only ONE worker thread

========================================================================

FIXED THREAD POOL
------------------
Multiple worker threads

========================================================================

SINGLE THREAD EXECUTOR
-----------------------
Sequential execution

========================================================================

FIXED THREAD POOL
------------------
Parallel execution

========================================================================

SINGLE THREAD EXECUTOR
-----------------------
Guaranteed order

========================================================================

FIXED THREAD POOL
------------------
Order not guaranteed

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

If worker thread dies due to exception,
SingleThreadExecutor creates new worker automatically.

========================================================================

This is better than manual thread.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. File writing systems
2. Logging systems
3. Sequential transaction processing
4. Message queues
5. Event ordering systems

========================================================================
WHEN TO USE?
========================================================================

Use when:
- task order important
- shared resource sensitive
- sequential execution required

========================================================================
WHEN NOT TO USE?
========================================================================

Avoid when:
parallel processing needed.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is SingleThreadExecutor?

→ ExecutorService with one worker thread.

========================================================================

2. Which method creates SingleThreadExecutor?

→ Executors.newSingleThreadExecutor()

========================================================================

3. Does SingleThreadExecutor execute tasks in parallel?

→ No

========================================================================

4. Main advantage of SingleThreadExecutor?

→ Sequential ordered execution.

========================================================================

5. What happens if many tasks submitted?

→ Tasks wait in queue.

========================================================================

6. Is same thread reused?

→ Yes

========================================================================

7. Why better than manual single thread?

→ Automatic lifecycle and queue management.

========================================================================

8. Does SingleThreadExecutor guarantee order?

→ Yes

========================================================================

9. What happens if worker thread crashes?

→ Executor creates new thread automatically.

========================================================================

MOST IMPORTANT INTERVIEW LINE

SingleThreadExecutor guarantees
sequential task execution
using a single reusable worker thread.

========================================================================

*/