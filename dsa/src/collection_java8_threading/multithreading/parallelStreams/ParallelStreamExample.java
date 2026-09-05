package EngineeringDigest.multithreading.parallelStreams;

import java.util.Arrays;
import java.util.List;

public class ParallelStreamExample {

    public static void main(String[] args) {

        List<Integer> numbers =
                Arrays.asList(
                        1,2,3,4,5,6,7,8,9,10
                );

        System.out.println(
                "Main Thread : "
                        + Thread.currentThread().getName()
        );

        System.out.println();

        // parallel stream processing
        numbers.parallelStream()
                .forEach(number -> {

                    System.out.println(
                            Thread.currentThread().getName()
                                    + " processing number : "
                                    + number
                    );

                    try {

                        Thread.sleep(1000);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });

        System.out.println();
        System.out.println("Processing Completed");
    }
}

/*

========================================================================
                PARALLEL STREAM EXAMPLE
========================================================================

This example demonstrates:
automatic parallel data processing.

========================================================================
MAIN IDEA
========================================================================

Collection elements processed
simultaneously using multiple threads.

========================================================================
IMPORTANT CODE
========================================================================

numbers.parallelStream()

========================================================================

This converts normal collection
into:
        parallel stream

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Java automatically:
- splits data
- creates parallel tasks
- assigns worker threads
- combines execution

========================================================================

Developer does NOT manage threads manually.

========================================================================
STEP-BY-STEP FLOW
========================================================================

List contains:
========================================================================

1 to 10

========================================================================

parallelStream()
        ↓
splits data internally

========================================================================

ForkJoinPool worker threads
process different numbers simultaneously.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Different numbers processed
by different worker threads.

========================================================================

Example:
========================================================================

worker-1 → 1,2

worker-2 → 3,4

worker-3 → 5,6

========================================================================

Order may vary.

========================================================================
OUTPUT CAN BE
========================================================================

ForkJoinPool.commonPool-worker-1
processing number : 3

========================================================================

ForkJoinPool.commonPool-worker-2
processing number : 7

========================================================================

main
processing number : 6

========================================================================

Different threads process simultaneously.

========================================================================
IMPORTANT OBSERVATION
========================================================================

Output order NOT guaranteed.

========================================================================

Because tasks execute in parallel.

========================================================================
WHY THREAD NAMES DIFFERENT?
========================================================================

parallelStream() internally uses:
========================================================================

ForkJoinPool.commonPool()

========================================================================

Worker threads from pool
execute stream tasks.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

parallelStream() uses:
        ForkJoinPool.commonPool()

========================================================================

by default.

========================================================================
WHY Thread.sleep() USED?
========================================================================

To visualize:
parallel execution clearly.

========================================================================

Otherwise execution may finish too quickly.

========================================================================
WHY PARALLEL STREAM POWERFUL?
========================================================================

Without manual threading:
Java automatically achieves:
- parallelism
- better CPU utilization
- concurrent execution

========================================================================
SEQUENTIAL STREAM vs PARALLEL STREAM
========================================================================

SEQUENTIAL
------------
One element at a time

========================================================================

PARALLEL
----------
Multiple elements simultaneously

========================================================================

SEQUENTIAL
------------
Single thread

========================================================================

PARALLEL
----------
Multiple worker threads

========================================================================
IMPORTANT PERFORMANCE UNDERSTANDING
========================================================================

Parallel Stream best for:
- large datasets
- CPU-intensive operations

========================================================================

NOT always faster for:
- small collections
- lightweight operations

========================================================================

Because:
parallel overhead exists.

========================================================================
VERY IMPORTANT LIMITATION
========================================================================

Avoid shared mutable state.

========================================================================

Example:
========================================================================

int sum = 0;

parallelStream().forEach(x -> sum += x);

========================================================================

NOT thread-safe.

========================================================================

Race condition possible.

========================================================================
BEST PRACTICE
========================================================================

Use:
- stateless operations
- independent processing
- immutable data

========================================================================
REAL LIFE ANALOGY
========================================================================

Suppose:
10 boxes need checking.

========================================================================

Sequential:
one worker checks all boxes.

========================================================================

Parallel:
multiple workers check different boxes simultaneously.

========================================================================

Much faster.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Big data analytics
2. Parallel filtering
3. Aggregation
4. Mathematical computations
5. Batch transformations

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Automatic parallelism
2. Cleaner code
3. Better CPU usage
4. Less manual threading
5. Faster large-data processing

========================================================================
IMPORTANT LIMITATION
========================================================================

Parallel processing overhead
may reduce performance
for small/simple tasks.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. Which method creates parallel stream?

→ parallelStream()

========================================================================

2. Which pool used internally?

→ ForkJoinPool.commonPool()

========================================================================

3. Is output order guaranteed?

→ No

========================================================================

4. Why Parallel Stream useful?

→ Automatic parallel processing.

========================================================================

5. Is Parallel Stream always faster?

→ No

========================================================================

6. Which operations best for Parallel Stream?

→ CPU-intensive independent operations.

========================================================================

7. Why shared mutable state dangerous?

→ Race condition may occur.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Parallel Stream automatically processes collection elements
using ForkJoinPool worker threads
for parallel execution.

========================================================================

*/