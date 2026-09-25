package com.dhami.java_dev.async;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * =====================================================================
 *           THREADPOOLEXECUTOR — DEEP DIVE
 *      (Executors utility vs direct ThreadPoolExecutor)
 * =====================================================================
 *
 * PREREQUISITE:
 *   You already know Era 2 (ExecutorService) and Era 4 (CompletableFuture).
 *   This file zooms into HOW ExecutorService actually works internally.
 *
 * THE BIG QUESTION THIS FILE ANSWERS:
 *
 *   "When I write Executors.newFixedThreadPool(5), what actually
 *    happens? And why do experienced devs say 'never use Executors
 *    factory methods in production'?"
 *
 * The answer requires understanding ThreadPoolExecutor's internals.
 * Let's build both and compare.
 *
 * =====================================================================
 */

/**
 * =====================================================================
 * SECTION 1: THE INTERNAL ARCHITECTURE OF ThreadPoolExecutor
 * =====================================================================
 *
 * ThreadPoolExecutor is THE class behind every pool you've ever used.
 * All Executors.newXxx() factory methods are just convenient wrappers
 * around "new ThreadPoolExecutor(...)" with different parameters.
 *
 * ITS STATE CONSISTS OF:
 *
 *   1. WORKER THREADS
 *      - Actual Thread objects, each looping forever:
 *            while (task = getTask()) { task.run(); }
 *      - They are NOT killed after each task. They pick up the next.
 *      - This is the "reuse" that makes pools efficient.
 *
 *   2. WORK QUEUE (BlockingQueue<Runnable>)
 *      - Holds submitted tasks when all core threads are busy.
 *      - Workers poll from this queue when idle.
 *      - Different implementations: LinkedBlockingQueue,
 *        ArrayBlockingQueue, SynchronousQueue, PriorityBlockingQueue.
 *
 *   3. CONTROL STATE (a single AtomicInteger, ctl)
 *      - Encodes BOTH worker count AND run state in one int.
 *      - Upper 3 bits   → runState (RUNNING, SHUTDOWN, STOP, TIDYING, TERMINATED)
 *      - Lower 29 bits  → workerCount
 *      - Clever bit-packing to avoid synchronization overhead.
 *
 * =====================================================================
 */

/**
 * =====================================================================
 * SECTION 2: THE 4 CRITICAL PARAMETERS
 * =====================================================================
 *
 * These 4 parameters define EVERY pool's behavior:
 *
 *   corePoolSize     → minimum threads kept alive (even if idle)
 *   maximumPoolSize  → absolute max threads allowed
 *   keepAliveTime    → how long an idle thread above core lives
 *   workQueue        → holds tasks when core threads are busy
 *
 * PLUS TWO OPTIONAL:
 *
 *   threadFactory    → customizes thread creation (names, priority, daemon)
 *   rejectionHandler → what to do when queue + maxPool full
 *
 * =====================================================================
 */

/**
 * =====================================================================
 * SECTION 3: THE SUBMIT FLOW — MOST IMPORTANT PART
 * =====================================================================
 *
 * When you call executor.submit(task), ThreadPoolExecutor does this
 * IN THIS EXACT ORDER:
 *
 *   ┌───────────────────────────────────────────────────────────┐
 *   │ STEP 1: Are running threads < corePoolSize?               │
 *   │         YES → spawn new thread, run task immediately      │
 *   │         NO  → go to Step 2                                │
 *   ├───────────────────────────────────────────────────────────┤
 *   │ STEP 2: Can the task be added to workQueue?               │
 *   │         YES → enqueue task, return (a worker will pick it)│
 *   │         NO  → go to Step 3                                │
 *   ├───────────────────────────────────────────────────────────┤
 *   │ STEP 3: Are running threads < maximumPoolSize?            │
 *   │         YES → spawn new thread, run task                  │
 *   │         NO  → go to Step 4                                │
 *   ├───────────────────────────────────────────────────────────┤
 *   │ STEP 4: REJECTION — call rejectionHandler                 │
 *   └───────────────────────────────────────────────────────────┘
 *
 * THE COUNTERINTUITIVE PART:
 *
 *   Threads grow to MAXIMUMSIZE only AFTER the queue is FULL.
 *   The pool does NOT grow to max first, then queue.
 *
 *   This is the #1 misunderstanding about ThreadPoolExecutor.
 *
 *   Example: core=2, max=10, queue=100
 *     100 tasks arrive.
 *     - Task 1, 2 → create 2 core threads.
 *     - Task 3..102 → all go into queue (queue now full).
 *     - Tasks 103..110 → create 8 more threads (up to max=10).
 *     - Task 111 → REJECTED.
 *
 *   The pool spent a long time at 2 threads even though max=10.
 *   Queue filled before extra threads were spawned.
 *
 * =====================================================================
 */

/**
 * =====================================================================
 * SECTION 4: THE Executors UTILITY CLASS
 * =====================================================================
 *
 * Executors is a FACTORY class (not a builder). It provides static
 * methods that return preconfigured ExecutorService instances.
 *
 * INTERNALLY, every method just calls "new ThreadPoolExecutor(...)"
 * with specific parameters. So there's no magic — just defaults.
 *
 * THE COMMON ONES AND THEIR INTERNAL CONFIG:
 *
 * ─────────────────────────────────────────────────────────────────
 * newFixedThreadPool(n)
 * ─────────────────────────────────────────────────────────────────
 *   core = n
 *   max  = n
 *   queue = LinkedBlockingQueue (UNBOUNDED, capacity = Integer.MAX_VALUE)
 *   keepAlive = 0
 *
 *   Behavior:
 *     - Exactly n threads, no more.
 *     - Queue grows unbounded — an OOM waiting to happen.
 *     - If 1 million tasks arrive, they all sit in memory.
 *
 *   Good for: CPU-bound work with predictable load.
 *   Bad for:  Unbounded submission scenarios.
 *
 * ─────────────────────────────────────────────────────────────────
 * newCachedThreadPool()
 * ─────────────────────────────────────────────────────────────────
 *   core = 0
 *   max  = Integer.MAX_VALUE
 *   queue = SynchronousQueue (capacity 0 — handoff only)
 *   keepAlive = 60 seconds
 *
 *   Behavior:
 *     - Creates a thread per task on demand.
 *     - Reuses idle threads if available.
 *     - Can spawn UNLIMITED threads → OOM under load.
 *
 *   Good for: Bursty, short-lived tasks in low traffic.
 *   Bad for:  Production — unbounded thread growth.
 *
 * ─────────────────────────────────────────────────────────────────
 * newSingleThreadExecutor()
 * ─────────────────────────────────────────────────────────────────
 *   core = 1, max = 1, queue = LinkedBlockingQueue (unbounded)
 *
 *   Behavior:
 *     - Sequential execution, FIFO order guaranteed.
 *     - Serializes access to a shared resource.
 *     - Same unbounded queue problem.
 *
 *   Good for: Serializing file writes, event processing.
 *
 * ─────────────────────────────────────────────────────────────────
 * newScheduledThreadPool(n)
 * ─────────────────────────────────────────────────────────────────
 *   Internally uses ScheduledThreadPoolExecutor (subclass).
 *   Supports delayed and periodic tasks.
 *
 * ─────────────────────────────────────────────────────────────────
 * newWorkStealingPool()
 * ─────────────────────────────────────────────────────────────────
 *   Returns ForkJoinPool, NOT ThreadPoolExecutor.
 *   Different beast — work-stealing algorithm, divide-and-conquer.
 *
 * WHY SENIOR DEVS SAY "AVOID Executors FACTORY METHODS":
 *
 *   1. Fixed pool → unbounded queue → OOM risk.
 *   2. Cached pool → unbounded threads → OOM risk.
 *   3. Single thread → unbounded queue → OOM risk.
 *   4. You can't control thread names → hard debugging.
 *   5. You can't control rejection policy → silent task loss.
 *
 * THE PRODUCTION WAY: use "new ThreadPoolExecutor(...)" directly
 * with explicit parameters. That's what the rest of this file shows.
 *
 * =====================================================================
 */
public class ThreadPoolExecutorDeepDrive {

    /**
     * =================================================================
     * TASK DEFINITION — shared between both approaches
     * =================================================================
     * A simple task that simulates work (sleep) and prints thread name.
     * Same task will be submitted to both pools for fair comparison.
     */
    static final class SampleTask implements Runnable {
        private final int id;
        SampleTask(int id) { this.id = id; }

        @Override
        public void run() {
            System.out.println(
                    "  Task-" + id + " running on " +
                    Thread.currentThread().getName()
            );
            try { Thread.sleep(500); } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * =================================================================
     * SECTION 5: APPROACH 1 — Executors UTILITY CLASS
     * =================================================================
     *
     * The "easy" way. One line, sensible defaults, but hidden traps.
     *
     * INTERNALLY COMPILES TO:
     *
     *     return new ThreadPoolExecutor(
     *         n, n,                                    // core, max
     *         0L, TimeUnit.MILLISECONDS,               // keepAlive
     *         new LinkedBlockingQueue<Runnable>()      // UNBOUNDED
     *     );
     *
     * Note: no threadFactory, no rejection handler passed.
     * Defaults kick in:
     *   - ThreadFactory: default → "pool-N-thread-M" names.
     *   - Rejection:     AbortPolicy → throws RejectedExecutionException.
     *
     * Let's submit 5 tasks to a 3-thread pool and observe.
     */
    static void approach1_executorsUtility() {
        System.out.println("\n=== Approach 1: Executors.newFixedThreadPool(3) ===");

        ExecutorService pool = Executors.newFixedThreadPool(3);

        try {
            for (int i = 1; i <= 5; i++) {
                pool.submit(new SampleTask(i));
            }
        } finally {
            pool.shutdown();
            try {
                // Wait up to 5 seconds for all tasks to finish
                if (!pool.awaitTermination(5, TimeUnit.SECONDS)) {
                    pool.shutdownNow();
                }
            } catch (InterruptedException e) {
                pool.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }
        // Observation: thread names will be "pool-1-thread-1", etc.
        // Only 3 threads, so 3 tasks start immediately, 2 wait in queue.
    }

    /**
     * =================================================================
     * SECTION 6: APPROACH 2 — DIRECT ThreadPoolExecutor
     * =================================================================
     *
     * The "production" way. Verbose, but every knob is explicit.
     *
     * WE CONTROL:
     *
     *   1. corePoolSize     = 3   (min threads)
     *   2. maximumPoolSize  = 6   (max threads, if queue fills)
     *   3. keepAliveTime    = 30s (idle threads above core die after this)
     *   4. workQueue        = ArrayBlockingQueue(10)  (BOUNDED! no OOM)
     *   5. threadFactory    = custom naming + daemon
     *   6. rejectionHandler = CallerRunsPolicy (back-pressure!)
     *
     * WHY EACH CHOICE MATTERS:
     *
     *   corePoolSize = 3
     *     Three threads always ready, handling steady-state load.
     *
     *   maximumPoolSize = 6
     *     Under burst, we allow up to 6 threads. Beyond that, back-pressure.
     *
     *   keepAliveTime = 30s
     *     Threads 4, 5, 6 (above core) die after 30s idle — save resources.
     *
     *   ArrayBlockingQueue(10)
     *     CRITICAL. Bounded queue means tasks can't pile up indefinitely.
     *     When 6 threads busy + 10 in queue, next task triggers rejection.
     *     This is our safety valve against OOM.
     *
     *   Custom ThreadFactory
     *     Thread names become "async-worker-1", "async-worker-2"...
     *     Massively easier debugging in thread dumps.
     *     Daemon threads → JVM can exit even if pool is running.
     *
     *   CallerRunsPolicy
     *     When pool is saturated, the CALLER thread runs the task itself.
     *     This creates natural BACK-PRESSURE — the caller slows down,
     *     which slows submission rate, which lets pool catch up.
     *     Alternatives:
     *       AbortPolicy       → throws RejectedExecutionException (default)
     *       DiscardPolicy     → silently drops task (dangerous)
     *       DiscardOldestPolicy → drops oldest queued task (also risky)
     *
     * Let's build it and submit the same 5 tasks plus a burst test.
     */
    static void approach2_directThreadPoolExecutor() {
        System.out.println("\n=== Approach 2: Direct ThreadPoolExecutor ===");

        // Custom ThreadFactory — names threads meaningfully
        AtomicInteger threadCounter = new AtomicInteger(1);
        ThreadFactory threadFactory = runnable -> {
            Thread t = new Thread(runnable,
                    "async-worker-" + threadCounter.getAndIncrement());
            t.setDaemon(true);
            return t;
        };

        // Bounded work queue — key to preventing OOM
        BlockingQueue<Runnable> workQueue = new ArrayBlockingQueue<>(10);

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3,                                          // corePoolSize
                6,                                          // maximumPoolSize
                30L, TimeUnit.SECONDS,                      // keepAliveTime
                workQueue,                                  // bounded queue
                threadFactory,                              // custom naming
                new ThreadPoolExecutor.CallerRunsPolicy()   // back-pressure
        );

        try {
            // Submit 5 normal tasks — up to 3 run immediately, 2 queue
            for (int i = 1; i <= 5; i++) {
                executor.submit(new SampleTask(i));
            }

            // Observability — you can inspect pool metrics anytime
            System.out.println("  Pool size         : " + executor.getPoolSize());
            System.out.println("  Active threads    : " + executor.getActiveCount());
            System.out.println("  Queued tasks      : " + executor.getQueue().size());
            System.out.println("  Completed tasks   : " + executor.getCompletedTaskCount());

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
     * SECTION 7: BURST TEST — SEEING BACK-PRESSURE IN ACTION
     * =================================================================
     *
     * Same executor config, but now we slam it with 25 tasks.
     *
     * Trace:
     *   Tasks 1-3   → create 3 core threads
     *   Tasks 4-13  → fill the ArrayBlockingQueue(10)
     *   Tasks 14-16 → create 3 extra threads (up to max=6)
     *   Tasks 17-25 → CallerRunsPolicy: MAIN thread runs them!
     *
     * Watch the output — you'll see "main" thread running some tasks.
     * That's the back-pressure doing its job.
     */
    static void section7_burstTest() {
        System.out.println("\n=== Section 7: Burst Test — 25 tasks ===");

        AtomicInteger counter = new AtomicInteger(1);
        ThreadFactory factory = r -> {
            Thread t = new Thread(r, "burst-worker-" + counter.getAndIncrement());
            t.setDaemon(true);
            return t;
        };

        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                3, 6, 30L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(10),
                factory,
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        try {
            for (int i = 1; i <= 25; i++) {
                executor.submit(new SampleTask(i));
            }
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(15, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * =================================================================
     * SECTION 8: LIFECYCLE — shutdown() vs shutdownNow()
     * =================================================================
     *
     * Two ways to stop a pool:
     *
     *   shutdown()
     *     - Stops accepting NEW tasks.
     *     - Lets QUEUED and RUNNING tasks finish.
     *     - Graceful. Use this normally.
     *
     *   shutdownNow()
     *     - Stops accepting new tasks.
     *     - Interrupts running tasks (cooperative).
     *     - DRAINS the queue and returns it as List<Runnable>.
     *     - Forceful. Use only on shutdown / fatal conditions.
     *
     *   awaitTermination(timeout, unit)
     *     - Blocks the calling thread until either:
     *         (a) all tasks finish, or
     *         (b) timeout elapses.
     *     - Returns true if terminated, false otherwise.
     *
     * TYPICAL SHUTDOWN PATTERN:
     *
     *     executor.shutdown();
     *     try {
     *         if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
     *             executor.shutdownNow();
     *             if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
     *                 // log fatal — pool won't die
     *             }
     *         }
     *     } catch (InterruptedException e) {
     *         executor.shutdownNow();
     *         Thread.currentThread().interrupt();
     *     }
     *
     * In Spring, you don't write this — Spring manages the lifecycle.
     * But understanding it prevents leaks when you use raw pools.
     *
     * =================================================================
     */

    /**
     * =================================================================
     * SECTION 9: OBSERVABILITY — READING POOL STATE
     * =================================================================
     *
     * ThreadPoolExecutor exposes live metrics:
     *
     *   getPoolSize()             → current number of threads
     *   getActiveCount()          → threads currently executing tasks
     *   getQueue().size()         → tasks waiting
     *   getCompletedTaskCount()   → total tasks finished
     *   getLargestPoolSize()      → peak thread count ever
     *   getTaskCount()            → total tasks ever submitted
     *
     * Useful for:
     *   - Actuator / Micrometer metrics
     *   - Health checks
     *   - Detecting pool exhaustion in production
     *   - Alerting when queue.size() > threshold
     *
     * NOTE: Sizes are approximate (concurrent state). Good enough
     * for monitoring, not for correctness decisions.
     *
     * =================================================================
     */

    /**
     * =================================================================
     * SECTION 10: EXECUTORS FACTORY vs DIRECT — SIDE-BY-SIDE
     * =================================================================
     *
     * ┌──────────────────┬──────────────────────┬────────────────────────┐
     * │ Aspect           │ Executors.newXxx()   │ new ThreadPoolExecutor │
     * ├──────────────────┼──────────────────────┼────────────────────────┤
     * │ Verbosity        │ 1 line               │ 7-10 lines             │
     * │ Queue            │ Unbounded (OOM risk) │ Explicit (bounded)     │
     * │ Max threads      │ Often = core         │ Fully tunable          │
     * │ Thread naming    │ Default pool-N-...   │ Custom prefix          │
     * │ Rejection policy │ AbortPolicy          │ Fully customizable     │
     * │ Debuggability    │ Poor                 │ Excellent              │
     * │ Observability    │ Only via cast        │ Native, first-class    │
     * │ Production ready │ ❌ Usually not       │ ✅ Yes                 │
     * └──────────────────┴──────────────────────┴────────────────────────┘
     *
     * RULE OF THUMB:
     *   Executors factory → prototypes, tests, quick scripts.
     *   Direct ThreadPoolExecutor → production services.
     *
     * Or, in Java 21+: Executors.newVirtualThreadPerTaskExecutor()
     * sidesteps the whole pool-size problem for I/O-bound work.
     *
     * =================================================================
     */

    /**
     * =================================================================
     * SECTION 11: HOW THIS MAPS TO SPRING @Async
     * =================================================================
     *
     * ThreadPoolTaskExecutor (Spring) is a WRAPPER around
     * java.util.concurrent.ThreadPoolExecutor. It exposes the same
     * parameters as setters:
     *
     *   Spring setter              → Underlying ThreadPoolExecutor param
     *   ─────────────────────────────────────────────────────────────
     *   setCorePoolSize(n)         → corePoolSize
     *   setMaxPoolSize(n)          → maximumPoolSize
     *   setQueueCapacity(n)        → ArrayBlockingQueue capacity
     *   setKeepAliveSeconds(n)     → keepAliveTime
     *   setThreadNamePrefix(s)     → ThreadFactory naming
     *   setRejectedExecutionHandler(handler) → rejection policy
     *
     * When you configure a @Async TaskExecutor bean in Spring, you
     * are literally configuring ThreadPoolExecutor via these setters.
     *
     * The reason we're doing this deep dive NOW:
     *   Every decision you make for @Async pool config
     *   (core/max/queue/rejection) is a decision you just learned
     *   to make with raw ThreadPoolExecutor.
     *
     * Spring just hides the boilerplate. The physics is identical.
     *
     * =================================================================
     */

    /**
     * =================================================================
     * SECTION 12: SIZING GUIDE — HOW MANY THREADS?
     * =================================================================
     *
     * CPU-BOUND tasks (math, sorting, parsing):
     *
     *     threads = number of CPU cores
     *
     *   Why: beyond core count, CPU context switching hurts more
     *   than helps. Each core can run exactly one thread efficiently.
     *
     * I/O-BOUND tasks (DB, REST, file, network):
     *
     *     threads = cores × (1 + waitTime / computeTime)
     *
     *   Why: while one thread blocks on I/O, another can use the CPU.
     *   If wait:compute = 100:1 (typical DB call), threads ≈ 100 × cores.
     *
     *   Example: 4 cores, wait=100ms, compute=1ms
     *     threads = 4 × (1 + 100/1) = 404 threads. Huge!
     *     This is why async matters — you can't create 400 platform
     *     threads cheaply... but 400 VIRTUAL threads? Yes, easily.
     *
     * MIXED workloads:
     *     Use TWO pools — one CPU-sized, one I/O-sized. Never mix.
     *
     * =================================================================
     */

    /**
     * =================================================================
     * SECTION 13: THE COMPLETE PICTURE — POOL STATE MACHINE
     * =================================================================
     *
     * ThreadPoolExecutor's runState (packed in upper 3 bits of ctl):
     *
     *   RUNNING     → accept new tasks + process queued
     *   SHUTDOWN    → reject new tasks, process queued
     *                 (entered via shutdown())
     *   STOP        → reject new tasks, drop queue, interrupt running
     *                 (entered via shutdownNow())
     *   TIDYING     → all tasks done, worker count = 0
     *   TERMINATED  → terminated() hook has run
     *
     * TRANSITIONS:
     *
     *   RUNNING ──shutdown()──→ SHUTDOWN ──queue empty──→ TIDYING → TERMINATED
     *   RUNNING ──shutdownNow()──→ STOP ──workers done──→ TIDYING → TERMINATED
     *
     * Knowing this state machine is what separates "I use pools"
     * from "I can diagnose pool bugs at 3 AM in production."
     *
     * =================================================================
     * FINAL MENTAL MODEL
     * =================================================================
     *
     * ThreadPoolExecutor = bounded threads + bounded (or unbounded) queue
     *                    + rejection policy when both are full.
     *
     * Executors.newXxx() = convenient factory but with hidden defaults
     *                    (often unbounded queue → OOM risk).
     *
     * new ThreadPoolExecutor(...) = explicit, controllable, production-ready.
     *
     * SUBMIT FLOW: core → queue → max → reject. MEMORIZE THIS.
     *
     * Everything Spring @Async does with pools is a wrapper over
     * exactly these mechanics.
     *
     * =================================================================
     */

    // ---------- runnable entry points (no main method, as requested) ----------
    // Call these from a Spring @Component, a test, or another runner class.

    public static void runApproach1() {
        approach1_executorsUtility();
    }

    public static void runApproach2() {
        approach2_directThreadPoolExecutor();
    }

    public static void runBurstTest() {
        section7_burstTest();
    }
}