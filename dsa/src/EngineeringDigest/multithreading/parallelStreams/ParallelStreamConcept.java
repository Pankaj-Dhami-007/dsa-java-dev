package EngineeringDigest.multithreading.parallelStreams;

public class ParallelStreamConcept {
}

/*

========================================================================
                    PARALLEL STREAM
========================================================================

Parallel Stream is modern Java feature
used for:
        parallel data processing.

========================================================================
WHY PARALLEL STREAM EXISTS?
========================================================================

Suppose:
large collection data exists.

========================================================================

Need:
faster processing using multiple CPU cores.

========================================================================

Traditional loop:
processes data sequentially.

========================================================================

Large data processing becomes slow.

========================================================================

This is why Parallel Stream introduced.

========================================================================
WHAT IS PARALLEL STREAM?
========================================================================

Parallel Stream processes collection elements
concurrently using multiple threads.

========================================================================
SIMPLE DEFINITION
========================================================================

Parallel Stream automatically divides data
and processes elements in parallel.

========================================================================
IMPORTANT PACKAGE
========================================================================

        java.util.stream

========================================================================
MAIN IDEA
========================================================================

Large collection
        ↓
Split into smaller chunks
        ↓
Process chunks in parallel
        ↓
Combine results

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Parallel Stream internally uses:
========================================================================

ForkJoinPool.commonPool()

========================================================================

Same pool used by:
- CompletableFuture
- ForkJoinPool

========================================================================
HOW TO CREATE PARALLEL STREAM?
========================================================================

1. parallelStream()
2. stream().parallel()

========================================================================
EXAMPLE
========================================================================

list.parallelStream()

========================================================================

OR

list.stream().parallel()

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Developer does NOT manually create threads.

========================================================================

Java automatically handles:
- thread management
- task splitting
- parallel execution

========================================================================
SEQUENTIAL STREAM
========================================================================

Processes:
one element at a time.

========================================================================

Single-thread execution.

========================================================================
PARALLEL STREAM
========================================================================

Processes:
multiple elements simultaneously.

========================================================================

Multi-thread execution.

========================================================================
STEP-BY-STEP FLOW
========================================================================

Collection data
        ↓
ForkJoinPool divides work
        ↓
Multiple worker threads process chunks
        ↓
Results combined automatically

========================================================================
WHY PARALLEL STREAM FAST?
========================================================================

Because:
multiple CPU cores work simultaneously.

========================================================================

Especially useful for:
CPU-intensive processing.

========================================================================
VERY IMPORTANT INTERVIEW POINT
========================================================================

Parallel Stream uses:
        divide-and-conquer strategy

========================================================================

through:
        ForkJoinPool

========================================================================
WHEN TO USE PARALLEL STREAM?
========================================================================

Use when:
- huge datasets
- CPU-intensive tasks
- independent operations

========================================================================

Examples:
- calculations
- filtering
- aggregation
- transformations

========================================================================
WHEN NOT TO USE?
========================================================================

Avoid for:
- small collections
- blocking I/O tasks
- database calls
- API calls

========================================================================

Because overhead may increase.

========================================================================
VERY IMPORTANT UNDERSTANDING
========================================================================

Parallel Stream works best when:
tasks are:
- independent
- stateless
- CPU-heavy

========================================================================
SEQUENTIAL vs PARALLEL STREAM
========================================================================

SEQUENTIAL STREAM
------------------
Single thread

========================================================================

PARALLEL STREAM
----------------
Multiple threads

========================================================================

SEQUENTIAL STREAM
------------------
Predictable order

========================================================================

PARALLEL STREAM
----------------
Order may vary

========================================================================

SEQUENTIAL STREAM
------------------
Less overhead

========================================================================

PARALLEL STREAM
----------------
Better for large workloads

========================================================================
IMPORTANT LIMITATION
========================================================================

Parallel processing may cause:
- thread contention
- synchronization overhead
- unpredictable ordering

========================================================================
REAL LIFE ANALOGY
========================================================================

Sequential processing:
one worker cleaning whole mall.

========================================================================

Parallel processing:
multiple workers cleaning different floors simultaneously.

========================================================================

Much faster.

========================================================================
COMMON REAL-WORLD USE CASES
========================================================================

1. Big data processing
2. Analytics
3. Financial calculations
4. Scientific computation
5. Data transformation

========================================================================
IMPORTANT ADVANTAGES
========================================================================

1. Easy parallelism
2. Better CPU utilization
3. Automatic thread management
4. Cleaner code
5. Faster large-data processing

========================================================================
IMPORTANT LIMITATION
========================================================================

Not always faster.

========================================================================

Small tasks may become slower
due to parallel overhead.

========================================================================
INTERVIEW QUESTIONS
========================================================================

1. What is Parallel Stream?

→ Stream processing using multiple threads.

========================================================================

2. Which pool used internally by Parallel Stream?

→ ForkJoinPool.commonPool()

========================================================================

3. Difference between sequential and parallel stream?

Sequential
------------
Single-thread processing

Parallel
----------
Multi-thread processing

========================================================================

4. Which tasks suitable for Parallel Stream?

→ CPU-intensive independent tasks.

========================================================================

5. Is Parallel Stream always faster?

→ No

========================================================================

6. Which method creates Parallel Stream?

→ parallelStream()

========================================================================

7. Why Parallel Stream may become slow sometimes?

→ Parallel overhead for small tasks.

========================================================================

MOST IMPORTANT INTERVIEW LINE

Parallel Stream automatically performs
parallel data processing
using ForkJoinPool worker threads internally.

========================================================================

*/