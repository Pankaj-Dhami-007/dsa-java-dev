package EngineeringDigest.multithreading.concurrentUtilities;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class SumTask extends RecursiveTask<Integer> {

    private int start;
    private int end;

    public SumTask(int start, int end) {

        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {

        // small task directly solve
        if ((end - start) <= 5) {

            int sum = 0;

            for (int i = start; i <= end; i++) {

                sum = sum + i;
            }

            System.out.println(
                    Thread.currentThread().getName()
                            + " calculated sum : "
                            + sum
                            + " for range "
                            + start
                            + " to "
                            + end
            );

            return sum;
        }

        // divide task
        int mid = (start + end) / 2;

        System.out.println(
                Thread.currentThread().getName()
                        + " dividing task : "
                        + start
                        + " to "
                        + end
        );

        // left subtask
        SumTask leftTask =
                new SumTask(start, mid);

        // right subtask
        SumTask rightTask =
                new SumTask(mid + 1, end);

        // fork subtasks
        leftTask.fork();
        rightTask.fork();

        // join results
        int leftResult = leftTask.join();
        int rightResult = rightTask.join();

        return leftResult + rightResult;
    }
}

public class ForkJoinPoolExample {

    public static void main(String[] args) {

        // creating fork join pool
        ForkJoinPool pool =
                new ForkJoinPool();

        // main task
        SumTask task =
                new SumTask(1, 20);

        // execute task
        int result = pool.invoke(task);

        System.out.println();
        System.out.println(
                "Final Sum : "
                        + result
        );
    }
}

/*

========================================================================
                    FORKJOINPOOL EXAMPLE
========================================================================

This is MOST IMPORTANT example
for understanding ForkJoinPool.

========================================================================
MAIN IDEA
========================================================================

Large task divided into:
smaller subtasks recursively.

========================================================================

Subtasks execute in parallel.

========================================================================

Results combined later.

========================================================================
TASK GOAL
========================================================================

Calculate:
========================================================================

sum from 1 to 20

========================================================================

using divide-and-conquer parallelism.

========================================================================
IMPORTANT CLASS
========================================================================

RecursiveTask<Integer>

========================================================================

Used because:
task RETURNS result.

========================================================================
WHY RecursiveTask?
========================================================================

ForkJoinPool works using:
- RecursiveTask
- RecursiveAction

========================================================================

RecursiveTask:
returns value.

========================================================================

RecursiveAction:
no return value.

========================================================================
COMPUTE METHOD
========================================================================

compute() contains:
main recursive logic.

========================================================================
STEP 1
========================================================================

If task small enough:
solve directly.

========================================================================

Condition:
========================================================================

(end - start) <= 5

========================================================================

Meaning:
small task.

========================================================================

Directly calculate sum.

========================================================================
STEP 2
========================================================================

If task large:
divide into smaller subtasks.

========================================================================

Example:
========================================================================

1 to 20

========================================================================

divided into:
========================================================================

1 to 10
11 to 20

========================================================================
STEP 3
========================================================================

fork()

========================================================================

Subtasks submitted asynchronously
to ForkJoinPool worker threads.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

fork()
--------
Starts subtask asynchronously.

========================================================================

Task may execute in another worker thread.

========================================================================
STEP 4
========================================================================

join()

========================================================================

Waits for subtask completion
and collects result.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

fork()
--------
Task splitting

========================================================================

join()
--------
Result combining

========================================================================
STEP-BY-STEP EXECUTION
========================================================================

Task:
========================================================================

1 to 20

========================================================================

divides into:
========================================================================

1 to 10
11 to 20

========================================================================

Again divided recursively:
========================================================================

1 to 5
6 to 10
11 to 15
16 to 20

========================================================================

Now tasks small enough.

========================================================================

Worker threads calculate sums in parallel.

========================================================================

Results joined together.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Multiple worker threads execute
different subtasks simultaneously.

========================================================================

This gives:
parallel computation.

========================================================================
OUTPUT CAN BE
========================================================================

ForkJoinPool-1-worker-1 dividing task : 1 to 20

ForkJoinPool-1-worker-2 dividing task : 1 to 10

========================================================================

ForkJoinPool-1-worker-3 calculated sum : 15 for range 1 to 5

========================================================================

Final Sum : 210

========================================================================
IMPORTANT OBSERVATION
========================================================================

Different worker threads
handle different subtasks.

========================================================================
WHY FORKJOINPOOL FAST?
========================================================================

Large task divided into:
small parallel subtasks.

========================================================================

Multiple CPU cores utilized efficiently.

========================================================================
VERY IMPORTANT INTERNAL CONCEPT
========================================================================

Idle worker threads may:
        steal tasks

========================================================================

using:
        work-stealing algorithm.

========================================================================

Improves performance.

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
one huge book counting project.

========================================================================

Leader divides pages among workers.

========================================================================

Workers count pages simultaneously.

========================================================================

Results combined later.

========================================================================

Exactly same concept.

========================================================================
WHY THRESHOLD CONDITION IMPORTANT?
========================================================================

If tasks become too tiny:
overhead increases.

========================================================================

So:
small tasks solved directly.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Parallel sorting
2. Big data processing
3. Scientific computation
4. Image processing
5. Parallel searching

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Parallel processing
2. Better CPU utilization
3. Recursive task handling
4. Work-stealing optimization

========================================================================
IMPORTANT LIMITATION
========================================================================

Not suitable for:
blocking operations like:
- database calls
- API requests
- network operations

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Why RecursiveTask used here?

→ Because task returns result.

========================================================================

2. Difference between fork() and join()?

fork()
--------
Starts async subtask

join()
--------
Waits and collects result

========================================================================

3. Why task divided recursively?

→ To achieve parallel computation.

========================================================================

4. What happens when task small enough?

→ Solved directly without further division.

========================================================================

5. Which algorithm used internally?

→ Work-stealing algorithm.

========================================================================

6. Main advantage of ForkJoinPool?

→ Efficient divide-and-conquer parallelism.

========================================================================

MOST IMPORTANT INTERVIEW LINE

ForkJoinPool improves parallel computation
by recursively dividing large tasks
into smaller subtasks
and combining results efficiently.

========================================================================

*/