package com.dhami.java_dev.async;

/**
 * =====================================================================
 *                    THREAD EVOLUTION IN JAVA
 *              (Read this BEFORE learning @Async)
 * =====================================================================
 *
 * The story of threading in Java has 4 distinct eras.
 * Understanding WHY each era was replaced is the key to understanding
 * WHY @Async exists and what problem it actually solves.
 *
 * ---------------------------------------------------------------------
 *  ERA 1 (1995)  : Raw Thread class + Runnable
 *  ERA 2 (2004)  : ExecutorService + Thread Pools (Java 5)
 *  ERA 3 (2004)  : Future + Callable (Java 5)
 *  ERA 4 (2014)  : CompletableFuture (Java 8)
 *  ERA 5 (2023)  : Virtual Threads + Structured Concurrency (Java 21)
 * ---------------------------------------------------------------------
 *
 * Each era was invented because the previous one had a PAIN POINT.
 * We'll write a small demo of each, and annotate the pain points
 * inline so the evolution becomes obvious.
 */
public class ThreadRevisit {

    /*
     * =================================================================
     * ERA 1: Raw Thread + Runnable  (Java 1.0, 1995)
     * =================================================================
     *
     * The only way to run code in parallel:
     *
     *     Thread t = new Thread(() -> doWork());
     *     t.start();
     *
     * TWO WAYS TO DEFINE A TASK:
     *   1. Extend Thread     -> bad, wastes your single inheritance slot
     *   2. Implement Runnable -> good, keeps your class hierarchy free
     *
     * Runnable:
     *   - void run(), no return value
     *   - cannot throw checked exceptions
     *   - "fire and forget" — you can never get a result back
     *
     * PAIN POINTS (why this era died):
     *   ✗ One thread = one OS thread = ~1 MB stack. Expensive.
     *   ✗ No pooling. 10,000 tasks = 10,000 threads = OOM.
     *   ✗ No way to get a result back.
     *   ✗ No way to propagate exceptions to the caller.
     *   ✗ No way to cancel a running task.
     *   ✗ No lifecycle management — you babysit every thread.
     *
     * Real-world failure:
     *   A web server doing `new Thread(...)` per request would die
     *   under any real load. This is the problem that motivated Era 2.
     * =================================================================
     */
    static void era1_rawThread() throws InterruptedException {
        // Example: spawn a thread and wait for it
        Thread t = new Thread(() -> {
            System.out.println("Era 1: running on " + Thread.currentThread().getName());
        }, "raw-worker");
        t.start();
        t.join(); // manually wait — no future, no result, no exception handling
    }

    /*
     * =================================================================
     * ERA 2: ExecutorService + Thread Pools  (Java 5, 2004)
     * =================================================================
     *
     * KEY IDEA: separate TASK SUBMISSION from THREAD MANAGEMENT.
     *
     * You no longer create threads. You create an EXECUTOR and hand it
     * tasks. The executor decides how many threads to create, when to
     * reuse them, and when to reject work.
     *
     * This is the same conceptual leap that @Async makes at the Spring
     * level. @Async is literally "Spring-managed ExecutorService".
     *
     * COMMON FACTORY METHODS (Executors utility class):
     *
     *   newFixedThreadPool(n)
     *      - exactly n threads. Tasks beyond n wait in a queue.
     *      - good for CPU-bound work where n = number of cores.
     *
     *   newCachedThreadPool()
     *      - creates threads on demand, reuses idle ones (60s TTL).
     *      - DANGEROUS: unbounded — can spawn thousands under load.
     *
     *   newSingleThreadExecutor()
     *      - one thread, sequential execution, ordered.
     *      - good for serializing access to a shared resource.
     *
     *   newScheduledThreadPool(n)
     *      - supports delayed / periodic tasks (like @Scheduled).
     *
     *   newWorkStealingPool()
     *      - fork-join style, good for divide-and-conquer CPU work.
     *
     * UNDER THE HOOD: ThreadPoolExecutor
     *   Every one of these wraps a ThreadPoolExecutor with:
     *     - corePoolSize      (min threads kept alive)
     *     - maximumPoolSize   (max threads allowed)
     *     - keepAliveTime     (idle thread TTL)
     *     - workQueue         (holds tasks when all core threads busy)
     *     - threadFactory     (names, priorities, daemon flag)
     *     - rejectionHandler  (what to do when queue + max pool full)
     *
     * TASK SUBMISSION FLOW (CRITICAL TO UNDERSTAND):
     *   1. If fewer than corePoolSize threads → create a new thread.
     *   2. Else if queue is not full         → enqueue the task.
     *   3. Else if fewer than maxPoolSize    → create a new thread.
     *   4. Else                              → call rejection handler.
     *
     *   NOTE: the queue fills BEFORE max threads are created.
     *   This trips up almost everyone. Tune queueCapacity carefully.
     *
     * PAIN POINTS (why Era 3/4 came):
     *   ✗ submit(Runnable) still gives you no result.
     *   ✗ You still can't easily compose tasks ("do A, then B, then C").
     *   ✗ Exception handling is still awkward.
     *   ✗ Blocking still wastes a platform thread.
     * =================================================================
     */
    static void era2_executorService() {
        var pool = java.util.concurrent.Executors.newFixedThreadPool(2);
        try {
            for (int i = 0; i < 5; i++) {
                int id = i;
                pool.submit(() ->
                        System.out.println("Era 2: task " + id + " on " + Thread.currentThread().getName()));
            }
        } finally {
            pool.shutdown(); // YOU must shut it down, or the JVM hangs
        }
    }

    /*
     * =================================================================
     * ERA 3: Callable + Future  (Java 5, 2004)
     * =================================================================
     *
     * Callable<V>:
     *   - V call() throws Exception
     *   - RETURNS a value
     *   - CAN throw checked exceptions
     *   - submitted via executor.submit(...) → returns Future<V>
     *
     * Future<V>:
     *   - get()           → BLOCKS until result is ready
     *   - get(timeout,unit)→ blocks with timeout
     *   - isDone()        → non-blocking check
     *   - cancel(true)    → attempt to interrupt the running task
     *
     * PAIN POINTS (why Era 4 came):
     *   ✗ get() blocks. If you have 3 futures, you call get() 3 times,
     *     and each blocks. Total wait = sum of latencies, not max.
     *   ✗ No composition. No "then", "combine", "either".
     *   ✗ No callbacks. You can't say "when done, run this".
     *   ✗ Cancellation is cooperative — you must check Thread.interrupted().
     *   ✗ Chaining futures by hand is ugly and error-prone.
     *
     * This is where @Async's default return type (Future) comes from.
     * But @Async users are ENCOURAGED to return CompletableFuture
     * (Era 4) instead, because Future is too weak.
     * =================================================================
     */
    static void era3_callableFuture() throws Exception {
        var pool = java.util.concurrent.Executors.newFixedThreadPool(2);
        try {
            var future = pool.submit(() -> {
                Thread.sleep(200);
                return "result-42";
            });

            // This line BLOCKS the calling thread until the task completes.
            // In Era 4 we'll learn to avoid this blocking entirely.
            String result = future.get();
            System.out.println("Era 3: got " + result);
        } finally {
            pool.shutdown();
        }
    }

    /*
     * =================================================================
     * ERA 4: CompletableFuture  (Java 8, 2014)
     * =================================================================
     *
     * A Future you can COMPOSE. Think of it as a Promise (JS) or
     * a Task (C#). You attach callbacks and chain operations, and
     * the whole pipeline runs asynchronously without blocking.
     *
     * CORE OPERATIONS:
     *
     *   CREATION
     *     supplyAsync(() -> value)     // runs on ForkJoinPool.commonPool
     *     runAsync(() -> { ... })      // no return value
     *     completedFuture(value)       // already-completed future
     *
     *   TRANSFORMATION
     *     thenApply(fn)     // map: T -> U
     *     thenAccept(fn)    // consume: T -> void
     *     thenRun(fn)       // ignore result, run a Runnable
     *
     *   CHAINING (async versions)
     *     thenCompose(fn)   // flatMap: T -> CompletableFuture<U>
     *
     *   COMBINING
     *     thenCombine(other, fn)          // combine two results
     *     allOf(f1, f2, f3)               // wait for ALL, returns CF<Void>
     *     anyOf(f1, f2, f3)               // wait for ANY, returns CF<Object>
     *
     *   ERROR HANDLING
     *     exceptionally(fn)               // recover from throwable
     *     handle((result, ex) -> ...)     // always runs, can transform
     *     whenComplete((r, ex) -> ...)    // side-effect, doesn't change result
     *
     * WHY THIS MATTERS FOR @Async:
     *   @Async methods can return CompletableFuture<T>. Spring then
     *   runs the method on a pool thread and returns a future that
     *   completes when the method finishes. Caller chains .thenApply()
     *   etc. to compose — no blocking get() needed.
     *
     * BEWARE (the classic gotcha):
     *   If you don't pass an Executor, CF uses ForkJoinPool.commonPool,
     *   which is sized to (cores - 1). For I/O-bound work that's WAY
     *   too small. Always pass your own Executor for I/O tasks.
     *
     * REMAINING PAIN POINT (why Era 5 came):
     *   ✗ Verbose and hard to read for complex flows.
     *   ✗ Error handling is spread across the chain, easy to miss.
     *   ✗ Debugging is hard — logical flow jumps across threads.
     *   ✗ Still tied to platform threads under the hood, so blocking
     *     inside a CF step still wastes an OS thread.
     * =================================================================
     */
    static void era4_completableFuture() {
        var pool = java.util.concurrent.Executors.newFixedThreadPool(3);

        var userF = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            sleep(200); return "user-1";
        }, pool);

        var ordersF = java.util.concurrent.CompletableFuture.supplyAsync(() -> {
            sleep(300); return 3;
        }, pool);

        // Combine both — this runs at MAX(200,300)=300ms, not SUM=500ms.
        var summaryF = userF.thenCombine(ordersF,
                (user, orders) -> user + " has " + orders + " orders");

        // Attach a callback — no blocking get() here!
        summaryF.thenAccept(s -> System.out.println("Era 4: " + s))
                .whenComplete((v, ex) -> pool.shutdown());
    }

    /*
     * =================================================================
     * ERA 5: Virtual Threads + Structured Concurrency  (Java 21, 2023)
     * =================================================================
     *
     * VIRTUAL THREADS (a.k.a. Project Loom):
     *   - JVM-managed threads, NOT 1:1 with OS threads.
     *   - Thousands/millions of them multiplexed on a few "carrier"
     *     platform threads.
     *   - Blocking a virtual thread is CHEAP: the JVM "unmounts" it
     *     from its carrier, freeing the carrier for other work.
     *   - Stack lives on the HEAP, grows dynamically, few KB.
     *
     *   Consequence: you can write SIMPLE, BLOCKING, SEQUENTIAL code
     *   and still get the scalability that used to require futures,
     *   callbacks, or reactive programming.
     *
     *   Executors.newVirtualThreadPerTaskExecutor() → one v-thread
     *   per submitted task. No pool sizing needed.
     *
     * STRUCTURED CONCURRENCY (still preview in 21, stabilizing later):
     *   StructuredTaskScope lets you fork child tasks that are bound
     *   to the parent's lifetime. If one fails, siblings are cancelled.
     *   This is the "goto considered harmful" moment for concurrency:
     *   task lifetimes become structured, like a try-with-resources.
     *
     *   try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
     *       var a = scope.fork(() -> callA());
     *       var b = scope.fork(() -> callB());
     *       scope.join().throwIfFailed();
     *       return combine(a.get(), b.get());
     *   }
     *
     * WHY THIS CHANGES THE @Async CONVERSATION:
     *   The ORIGINAL motivation for @Async was "don't block the scarce
     *   request thread". With virtual threads, blocking is cheap, so
     *   you often don't need to offload at all.
     *
     *   BUT @Async still adds value for:
     *     - fire-and-forget (truly detached from caller)
     *     - bounded concurrency (rate-limit downstream services)
     *     - declarative Spring-managed execution
     *
     *   Modern best practice: configure @Async to run ON virtual
     *   threads. You get delegation + cheap blocking together.
     * =================================================================
     */
    static void era5_virtualThreads() {
        try (var exec = java.util.concurrent.Executors.newVirtualThreadPerTaskExecutor()) {
            // Submit 10,000 tasks — all run concurrently, no pool tuning.
            for (int i = 0; i < 10_000; i++) {
                int id = i;
                exec.submit(() -> {
                    sleep(50); // blocking is CHEAP on a virtual thread
                    if (id == 9999) System.out.println("Era 5: v-thread done: " + id);
                });
            }
        } // auto-close waits for all tasks
    }

    /*
     * =================================================================
     * THE EVOLUTION IN ONE SENTENCE PER ERA
     * =================================================================
     *
     *  Era 1: "I can create threads manually."        (raw power)
     *  Era 2: "I should reuse threads via a pool."     (management)
     *  Era 3: "I want a result back from a task."      (Future)
     *  Era 4: "I want to chain and compose tasks."     (CompletableFuture)
     *  Era 5: "Threads are so cheap, why bother
     *          with all that machinery?"                (virtual threads)
     *
     * =================================================================
     * WHERE @Async FITS
     * =================================================================
     *
     * @Async is a SPRING-LEVEL abstraction over Era 2 (ExecutorService).
     * It says:
     *
     *     "When someone calls this method, don't run it on the
     *      caller's thread — submit it to a Spring-managed executor
     *      and return control immediately."
     *
     * So the mental model is:
     *
     *     @Async method  ≈  executor.submit(() -> { method body })
     *
     * The reason @Async exists is the SAME reason Era 2 exists:
     * to decouple task submission from thread management, AND to
     * free the caller (usually an HTTP request thread) to do other work.
     *
     * If you return CompletableFuture from an @Async method, you
     * bridge into Era 4 as well.
     *
     * If you configure @Async to use virtual threads, you bridge
     * into Era 5.
     *
     * =================================================================
     * CHECKLIST BEFORE LEARNING @Async
     * =================================================================
     *
     *  [ ] Can explain why `new Thread(...)` per request is bad.
     *  [ ] Know the 4 parameters of ThreadPoolExecutor.
     *  [ ] Can describe the submit() flow (core → queue → max → reject).
     *  [ ] Know the difference between Runnable and Callable.
     *  [ ] Can explain what Future.get() blocks on.
     *  [ ] Can chain a CompletableFuture with thenApply/thenCombine.
     *  [ ] Know that blocking a virtual thread is cheap, and why.
     *  [ ] Can articulate WHY @Async exists (delegation + pooling).
     *
     * If you can tick all of the above, you are ready for @Async.
     * =================================================================
     */

    // ---------- helpers ----------
    private static void sleep(long ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

//    // ---------- entry point (uncomment one era at a time to observe) ----------
//    public static void main(String[] args) throws Exception {
//        // era1_rawThread();
//        // era2_executorService();
//        // era3_callableFuture();
//        // era4_completableFuture();
//        // era5_virtualThreads();
//    }
}