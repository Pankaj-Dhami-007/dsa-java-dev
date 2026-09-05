package EngineeringDigest.multithreading.concurrentUtilities;

public class ForkJoinPoolConcept {
}

/*

========================================================================
                        FORKJOINPOOL
========================================================================

ForkJoinPool is advanced thread pool
designed for:
        parallel recursive tasks.

========================================================================
WHY FORKJOINPOOL EXISTS?
========================================================================

Normal thread pools work well for:
- independent tasks
- simple async operations

========================================================================

BUT:
large recursive computations
need better parallel processing.

========================================================================

Example:
- merge sort
- quick sort
- large mathematical computation
- file searching
- divide-and-conquer algorithms

========================================================================

This is why ForkJoinPool introduced.

========================================================================
WHAT IS FORKJOINPOOL?
========================================================================

ForkJoinPool is specialized thread pool
for dividing large tasks
into smaller subtasks
and processing them in parallel.

========================================================================
SIMPLE DEFINITION
========================================================================

ForkJoinPool implements:
        divide-and-conquer parallelism.

========================================================================
MAIN IDEA
========================================================================

Large task
        ↓
Divide into smaller tasks
        ↓
Execute subtasks in parallel
        ↓
Combine results

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.concurrent

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

ForkJoinPool internally uses:
        worker threads

========================================================================

But optimized for:
recursive parallel execution.

========================================================================
WHY CALLED FORK-JOIN?
========================================================================

FORK
------
Split task into smaller subtasks.

========================================================================

JOIN
------
Combine results of subtasks.

========================================================================

This is core concept.

========================================================================
STEP-BY-STEP FLOW
========================================================================

Suppose:
huge task arrives.

========================================================================

Task divided recursively:
========================================================================

Big Task
    ↓
Small Task-1
Small Task-2

========================================================================

Each task may divide further.

========================================================================

All subtasks execute in parallel.

========================================================================

After completion:
results combined together.

========================================================================
VERY IMPORTANT INTERNAL CONCEPT
========================================================================

ForkJoinPool uses:
        Work Stealing Algorithm

========================================================================
WHAT IS WORK STEALING?
========================================================================

Suppose:
one worker thread becomes idle.

========================================================================

Instead of waiting:
it steals tasks
from busy worker thread queue.

========================================================================

Improves:
- CPU utilization
- performance
- parallelism

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Each worker thread has:
own deque/task queue.

========================================================================

Idle workers steal tasks
from others.

========================================================================
WHY WORK STEALING IMPORTANT?
========================================================================

Prevents:
some threads idle
while others overloaded.

========================================================================

Better workload balancing.

========================================================================
IMPORTANT CLASSES
========================================================================

1. RecursiveTask
2. RecursiveAction

========================================================================
1. RECURSIVETASK
========================================================================

Used when:
task RETURNS result.

========================================================================
2. RECURSIVEACTION
========================================================================

Used when:
task does NOT return result.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

ForkJoinPool mostly used for:
CPU-intensive parallel tasks.

========================================================================

NOT ideal for:
blocking I/O tasks.

========================================================================
FORKJOINPOOL vs NORMAL THREAD POOL
========================================================================

NORMAL THREAD POOL
-------------------
Independent task execution

========================================================================

FORKJOINPOOL
--------------
Recursive divide-and-conquer execution

========================================================================

NORMAL THREAD POOL
-------------------
Simple queue sharing

========================================================================

FORKJOINPOOL
--------------
Work-stealing queues

========================================================================

NORMAL THREAD POOL
-------------------
General-purpose tasks

========================================================================

FORKJOINPOOL
--------------
CPU-intensive recursive tasks

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

CompletableFuture by default uses:
========================================================================

ForkJoinPool.commonPool()

========================================================================

for async task execution.

========================================================================
COMMONPOOL
========================================================================

Shared global ForkJoinPool
provided by JVM.

========================================================================

Used by:
- CompletableFuture
- parallel streams

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
huge cleaning work.

========================================================================

One person divides building floors
among many workers.

========================================================================

Idle worker helps busy worker
by taking remaining rooms.

========================================================================

Exactly same concept.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Parallel sorting
2. Parallel searching
3. Big data processing
4. Scientific computation
5. Parallel streams
6. Image processing

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. High parallelism
2. Better CPU utilization
3. Work-stealing optimization
4. Efficient recursive processing

========================================================================
IMPORTANT LIMITATION
========================================================================

Not ideal for:
blocking tasks like:
- database calls
- network I/O
- API requests

========================================================================

Because worker threads may block.

========================================================================
IMPORTANT INTERVIEW QUESTIONS
========================================================================

1. What is ForkJoinPool?

→ Specialized thread pool for recursive parallel tasks.

========================================================================

2. Why called ForkJoinPool?

→ Tasks are forked into subtasks and joined later.

========================================================================

3. Which algorithm used internally?

→ Work-stealing algorithm.

========================================================================

4. What is work stealing?

→ Idle thread steals task from busy thread queue.

========================================================================

5. Which classes used with ForkJoinPool?

→ RecursiveTask and RecursiveAction.

========================================================================

6. Which pool does CompletableFuture use by default?

→ ForkJoinPool.commonPool()

========================================================================

7. Best use case of ForkJoinPool?

→ CPU-intensive divide-and-conquer tasks.

========================================================================

8. Is ForkJoinPool good for blocking I/O?

→ No

========================================================================

MOST IMPORTANT INTERVIEW LINE

ForkJoinPool is specialized thread pool
optimized for recursive divide-and-conquer parallelism
using work-stealing algorithm.

========================================================================

*/