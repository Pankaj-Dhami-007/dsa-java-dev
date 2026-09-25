package com.dhami.java_dev.async;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * =====================================================================
 *        THE CLASSIC ThreadPoolExecutor(2, 4, 10s, Queue(2)) DEMO
 * =====================================================================
 *
 * This configuration is famous because it's SMALL ENOUGH to trace
 * every single task submission by hand. It exposes the exact
 * submit flow of ThreadPoolExecutor:
 *
 *     core → queue → max → reject
 *
 * CONFIG BEING USED:
 *
 *   corePoolSize     = 2
 *   maximumPoolSize  = 4
 *   keepAliveTime    = 10 seconds
 *   workQueue        = ArrayBlockingQueue(2)   ← BOUNDED, capacity = 2
 *
 * TOTAL CAPACITY OF THIS POOL (before rejection):
 *
 *   = maximumPoolSize + queueCapacity
 *   = 4 + 2
 *   = 6 tasks can be "in flight" before rejection kicks in
 *
 * =====================================================================
 */

/**

1. There Are 4 Overloaded Constructors
// Constructor 1 — bare minimum
ThreadPoolExecutor(int corePoolSize,
                   int maximumPoolSize,
                   long keepAliveTime,
                   TimeUnit unit,
                   BlockingQueue<Runnable> workQueue)

// Constructor 2 — + ThreadFactory
ThreadPoolExecutor(int corePoolSize,
                   int maximumPoolSize,
                   long keepAliveTime,
                   TimeUnit unit,
                   BlockingQueue<Runnable> workQueue,
                   ThreadFactory threadFactory)

// Constructor 3 — + RejectionHandler
ThreadPoolExecutor(int corePoolSize,
                   int maximumPoolSize,
                   long keepAliveTime,
                   TimeUnit unit,
                   BlockingQueue<Runnable> workQueue,
                   RejectedExecutionHandler handler)

// Constructor 4 — full control
ThreadPoolExecutor(int corePoolSize,
                   int maximumPoolSize,
                   long keepAliveTime,
                   TimeUnit unit,
                   BlockingQueue<Runnable> workQueue,
                   ThreadFactory threadFactory,
                   RejectedExecutionHandler handler)
 */
public class ThreadPoolExecutorDemo {

    /**
     * A task that prints its thread and id, and sleeps for a while.
     * We make it sleep LONG (2 seconds) so we can observe the pool
     * filling up before tasks complete.
     */
    static final class Task implements Runnable {
        private final int id;
        Task(int id) { this.id = id; }

        @Override
        public void run() {
            System.out.println(
                    "  [RUN]  Task-" + id +
                    " on " + Thread.currentThread().getName() +
                    " | active=" + activeCountSnapshot()
            );
            try { Thread.sleep(2000); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            System.out.println("  [DONE] Task-" + id);
        }

        // helper — will be replaced with real snapshot below
        private static int activeCountSnapshot() { return -1; }
    }

    /**
     * =================================================================
     * THE DEMO — Submitting 10 tasks to a pool that can hold 6
     * =================================================================
     *
     * EXPECTED TRACE (memorize this):
     *
     *   Submit Task-1   → core slots free       → create core-1, run
     *   Submit Task-2   → core slots free       → create core-2, run
     *   Submit Task-3   → core full, queue free → enqueue (queue: [3])
     *   Submit Task-4   → core full, queue free → enqueue (queue: [3,4])
     *   Submit Task-5   → queue FULL, below max → create worker-3, run
     *   Submit Task-6   → queue FULL, below max → create worker-4, run
     *   Submit Task-7   → queue FULL, at max    → REJECT (AbortPolicy)
     *   Submit Task-8   → REJECT
     *   Submit Task-9   → REJECT
     *   Submit Task-10  → REJECT
     *
     * So we'll see:
     *   - 4 tasks running immediately (2 core + 2 extra)
     *   - 2 tasks waiting in queue
     *   - 4 tasks REJECTED
     *
     * That is the clearest possible demonstration of the submit flow.
     * =================================================================
     */
    static void runClassicDemo() {
        System.out.println("\n=== ThreadPoolExecutor(2, 4, 10s, ArrayBlockingQueue(2)) ===");

        // Custom thread factory so we can see the pool's behavior clearly
        AtomicInteger counter = new AtomicInteger(1);
        ThreadFactory factory = r -> {
            Thread t = new Thread(r, "demo-worker-" + counter.getAndIncrement());
            t.setDaemon(true);
            return t;
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,                              // corePoolSize
                4,                              // maximumPoolSize
                10, TimeUnit.SECONDS,           // keepAliveTime
                new ArrayBlockingQueue<>(2),    // BOUNDED queue (capacity = 2)
                factory,                        // custom thread names
                new ThreadPoolExecutor.AbortPolicy() // REJECT with exception
        );

        try {
            for (int i = 1; i <= 10; i++) {
                final int taskId = i;
                try {
                    executor.submit(() -> {
                        System.out.println(
                                "  [RUN]  Task-" + taskId +
                                " on " + Thread.currentThread().getName()
                        );
                        try { Thread.sleep(2000); } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                        System.out.println("  [DONE] Task-" + taskId);
                    });

                    // Print pool state AFTER each successful submission
                    System.out.printf(
                            "  submitted task-%d  |  poolSize=%d  active=%d  queue=%d%n",
                            taskId,
                            executor.getPoolSize(),
                            executor.getActiveCount(),
                            executor.getQueue().size()
                    );

                } catch (RejectedExecutionException ex) {
                    System.out.printf(
                            "  [REJECT] Task-%d  |  poolSize=%d  active=%d  queue=%d%n",
                            taskId,
                            executor.getPoolSize(),
                            executor.getActiveCount(),
                            executor.getQueue().size()
                    );
                }

                // Small pause so tasks can actually start running
                // between submissions — otherwise the trace is too fast.
                try { Thread.sleep(100); } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            // Wait for all accepted tasks to finish
            Thread.sleep(3000);

            System.out.println("\n  --- Final pool state ---");
            System.out.println("  poolSize        = " + executor.getPoolSize());
            System.out.println("  largestPoolSize = " + executor.getLargestPoolSize());
            System.out.println("  completedTasks  = " + executor.getCompletedTaskCount());
            System.out.println("  taskCount       = " + executor.getTaskCount());

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            executor.shutdown();
            try {
                if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                    executor.shutdownNow();
                }
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * =================================================================
     * SAME CONFIG, DIFFERENT REJECTION POLICY
     * =================================================================
     *
     * We just saw AbortPolicy — task rejected, exception thrown.
     *
     * Now let's see CallerRunsPolicy — task runs on the CALLER thread.
     *
     * Key behavior: when the caller thread runs a task, it BLOCKS on
     * that task. It cannot submit the next one until it finishes.
     * This creates NATURAL back-pressure — the submission rate slows
     * down automatically, letting the pool drain its queue.
     *
     * Watch the output: you'll see "main" appearing in thread names.
     * =================================================================
     */
    static void runCallerRunsPolicyDemo() {
        System.out.println("\n=== Same config, CallerRunsPolicy ===");

        AtomicInteger counter = new AtomicInteger(1);
        ThreadFactory factory = r -> {
            Thread t = new Thread(r, "crp-worker-" + counter.getAndIncrement());
            t.setDaemon(true);
            return t;
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                2,
                4,
                10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(2),
                factory,
                new ThreadPoolExecutor.CallerRunsPolicy()   // ← back-pressure
        );

        try {
            for (int i = 1; i <= 10; i++) {
                final int taskId = i;

                // NO try/catch for RejectedExecutionException here —
                // CallerRunsPolicy never throws, it just runs the task
                // on the caller thread instead.
                executor.submit(() -> {
                    System.out.println(
                            "  [RUN]  Task-" + taskId +
                            " on " + Thread.currentThread().getName()
                    );
                    try { Thread.sleep(1000); } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println("  [DONE] Task-" + taskId);
                });

                System.out.printf(
                        "  submitted task-%d  |  poolSize=%d  active=%d  queue=%d%n",
                        taskId,
                        executor.getPoolSize(),
                        executor.getActiveCount(),
                        executor.getQueue().size()
                );
            }
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(15, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // Observation: no rejection. The caller thread (main) takes
        // over when the pool is saturated. Notice how the main thread
        // "pauses" for 1 second at a time — that's the back-pressure.
    }

    /**
     * =================================================================
     * SAME CONFIG, DIFFERENT REJECTION POLICIES — QUICK REFERENCE
     * =================================================================
     *
     *   AbortPolicy (default)
     *     → throws RejectedExecutionException.
     *     → caller must handle it.
     *     → BEST when you want to know about saturation.
     *
     *   CallerRunsPolicy
     *     → runs task on caller thread.
     *     → natural back-pressure.
     *     → BEST for bounded producers that can slow down.
     *     → NOTE: blocks the caller; never use with a fast producer
     *             that cannot afford to block.
     *
     *   DiscardPolicy
     *     → silently drops the task.
     *     → callers never know.
     *     → ONLY for non-critical fire-and-forget work (metrics, logging).
     *
     *   DiscardOldestPolicy
     *     → drops the OLDEST queued task, tries again with new one.
     *     → risky in most cases.
     *     → occasionally used for "keep latest state" scenarios.
     *
     * =================================================================
     */

    /**
     * =================================================================
     * WHAT EACH PARAMETER MEANS IN THIS SPECIFIC CONFIG
     * =================================================================
     *
     * corePoolSize = 2
     *   Two threads are always alive. They handle normal load.
     *   Even if idle, they don't die (until pool is shut down).
     *
     * maximumPoolSize = 4
     *   Under burst, pool can grow to 4 threads.
     *   Threads 3 and 4 are "extra" — they die after 10s idle.
     *
     * keepAliveTime = 10 seconds
     *   How long threads 3 and 4 survive with no work.
     *   Core threads (1, 2) ignore this by default.
     *
     * ArrayBlockingQueue(2)
     *   Bounded queue. Max 2 tasks waiting.
     *   This is what makes rejection POSSIBLE at all.
     *   With an unbounded queue, tasks pile up forever — no rejection.
     *
     * AbortPolicy
     *   When 4 threads busy + 2 in queue → throw exception.
     *
     * TOTAL SYSTEM CAPACITY = 4 + 2 = 6 concurrent tasks.
     * Task 7 onwards → rejected.
     *
     * =================================================================
     * WHY THIS CONFIG IS THE "GOLDILOCKS" DEMO
     * =================================================================
     *
     *   Small enough: you can count tasks by hand.
     *   Bounded queue: rejection actually happens.
     *   Above core and below max: you SEE threads grow.
     *   Above max: you SEE rejection.
     *
     * Every other "pool" tutorial uses a big pool (50 threads,
     * 1000 queue) — you never see the boundaries. This config
     * forces you to watch all four stages of submit flow happen.
     *
     * =================================================================
     * STEP-BY-STEP TRACE (with 10 tasks submitted)
     * =================================================================
     *
     *   t=0    submit(1)  core free     → create worker-1     [pool=1, queue=0]
     *   t=100  submit(2)  core free     → create worker-2     [pool=2, queue=0]
     *   t=200  submit(3)  core full,   queue free → enqueue    [pool=2, queue=1]
     *   t=300  submit(4)  core full,   queue free → enqueue    [pool=2, queue=2]
     *   t=400  submit(5)  queue FULL,  below max  → worker-3   [pool=3, queue=2]
     *   t=500  submit(6)  queue FULL,  below max  → worker-4   [pool=4, queue=2]
     *   t=600  submit(7)  queue FULL,  AT max     → REJECT
     *   t=700  submit(8)  → REJECT
     *   t=800  submit(9)  → REJECT
     *   t=900  submit(10) → REJECT
     *
     *   At t≈2000 worker-1 finishes Task-1.
     *   At t≈2100 worker-2 finishes Task-2.
     *   worker-1 and worker-2 pick up Tasks 3 and 4 from the queue.
     *   At t≈4000 Tasks 3,4 finish, workers go idle.
     *   After 10s idle, worker-3 and worker-4 die (keepAliveTime).
     *   Final: poolSize drops back to core=2.
     *
     * =================================================================
     * SUBMIT FLOW — MEMORIZE THIS
     * =================================================================
     *
     *   1. core slots available       → new thread
     *   2. core full, queue has room  → enqueue
     *   3. queue full, below max      → new thread
     *   4. queue full, at max         → REJECT
     *
     * This is the ONE thing to walk away with from this demo.
     * Everything else (Spring @Async config, reactive pools, virtual
     * threads) builds on this mental model.
     *
     * =================================================================
     * HOW THIS MAPS TO SPRING @Async
     * =================================================================
     *
     * If you configure Spring like this:
     *
     *     ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
     *     ex.setCorePoolSize(2);
     *     ex.setMaxPoolSize(4);
     *     ex.setQueueCapacity(2);
     *     ex.setKeepAliveSeconds(10);
     *     ex.setThreadNamePrefix("async-");
     *     ex.initialize();
     *
     * ...you are building EXACTLY this ThreadPoolExecutor under the
     * hood. Every behavior you just observed (core→queue→max→reject)
     * applies identically to your @Async methods.
     *
     * When you later see "@Async pool is stuck" or "tasks rejected in
     * production", the mental model you built here will tell you
     * exactly why.
     *
     * =================================================================
     */

    // ---------- runnable entry points (call from a @Component or test) ----------

    public static void runDemo() {
        runClassicDemo();
    }

    public static void runCallerRuns() {
        runCallerRunsPolicyDemo();
    }
}

/*

output


submitted task-1  |  poolSize=1  active=1  queue=0
submitted task-2  |  poolSize=2  active=2  queue=0
submitted task-3  |  poolSize=2  active=2  queue=1
submitted task-4  |  poolSize=2  active=2  queue=2
submitted task-5  |  poolSize=3  active=3  queue=2
submitted task-6  |  poolSize=4  active=4  queue=2
[REJECT] Task-7   |  poolSize=4  active=4  queue=2
[REJECT] Task-8   |  poolSize=4  active=4  queue=2
[REJECT] Task-9   |  poolSize=4  active=4  queue=2
[REJECT] Task-10  |  poolSize=4  active=4  queue=2
 */