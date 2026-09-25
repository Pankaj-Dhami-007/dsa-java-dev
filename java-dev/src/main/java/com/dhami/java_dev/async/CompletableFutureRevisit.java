package com.dhami.java_dev.async;

/**
 * =====================================================================
 *              COMPLETABLE FUTURE — COMPLETE REVISION NOTES
 *         (Read this BEFORE learning Spring's @Async)
 * =====================================================================
 *
 * PREREQUISITE:
 *   You already know Era 1-5 of thread evolution (see ThreadRevisit).
 *   This file is Era 4 deep dive — the bridge between raw Future
 *   and Spring @Async.
 *
 * WHY THIS FILE EXISTS:
 *   @Async methods in Spring almost always return CompletableFuture<T>.
 *   If you don't understand CF composition, you'll write @Async code
 *   that blocks, deadlocks, or performs worse than synchronous code.
 *
 * =====================================================================
 */

import java.util.concurrent.*;

public class CompletableFutureRevisit {

    /*
     * =================================================================
     * SECTION 1: THE PROBLEM — WHY Future WASN'T ENOUGH
     * =================================================================
     *
     * Recall Era 3 (Future):
     *
     *     Future<Integer> f = executor.submit(() -> 10 + 30);
     *     Integer result = f.get();   // BLOCKS the calling thread
     *
     * PROBLEMS WITH Future:
     *
     *   ✗ get() blocks. If you have 3 futures, you call get() 3 times,
     *     and each blocks. Total wait = SUM of latencies, not MAX.
     *
     *   ✗ No composition. Cannot say "do A, then B, then C".
     *
     *   ✗ No callbacks. Cannot say "when done, run this".
     *
     *   ✗ Cannot combine two futures.
     *
     *   ✗ Cancellation is cooperative — you must check
     *     Thread.interrupted() inside your task.
     *
     *   ✗ No exception handling chain — you get ExecutionException
     *     wrapping the real cause, awkward to unwrap.
     *
     * CompletableFuture (Java 8) solves ALL of the above.
     *
     * =================================================================
     */
    static void section1_problemWithFuture() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(3);

        Future<Integer> f1 = pool.submit(() -> { sleep(200); return 10; });
        Future<Integer> f2 = pool.submit(() -> { sleep(300); return 20; });
        Future<Integer> f3 = pool.submit(() -> { sleep(400); return 30; });

        // Total time = 200 + 300 + 400 = 900ms (sequential blocking)
        // Though the tasks RAN in parallel, we WAITED sequentially.
        int total = f1.get() + f2.get() + f3.get();
        System.out.println("Section 1 (Future) total = " + total);

        pool.shutdown();
    }

    /*
     * =================================================================
     * SECTION 2: WHAT IS CompletableFuture<T>?
     * =================================================================
     *
     * A Future<T> that you can COMPOSE.
     * Think of it as a Promise (JavaScript) or Task (C#).
     *
     * KEY IDEAS:
     *
     *   1. It represents a VALUE THAT WILL EXIST IN THE FUTURE.
     *
     *   2. You attach CALLBACKS to it. When the value arrives,
     *      callbacks fire automatically. No blocking.
     *
     *   3. You can CHAIN operations. Each step returns a new
     *      CompletableFuture, forming a pipeline.
     *
     *   4. It has built-in EXCEPTION HANDLING in the chain.
     *
     *   5. It can be COMBINED with other CompletableFutures.
     *
     * THREE WAYS TO CREATE:
     *
     *   supplyAsync(Supplier<T>)      → task returns a value
     *   supplyAsync(Supplier<T>, Exec)→ same, but on YOUR executor
     *   runAsync(Runnable)            → task returns nothing (void)
     *   runAsync(Runnable, Exec)      → same, but on YOUR executor
     *   completedFuture(value)        → already-completed future
     *
     * =================================================================
     */
    static void section2_creation() {
        // Task that returns a value
        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> 10 + 90);

        // Task that returns nothing
        CompletableFuture<Void> f2 = CompletableFuture.runAsync(
                () -> System.out.println("running"));

        // Already-completed future (useful for testing, caching)
        CompletableFuture<String> f3 = CompletableFuture.completedFuture("cached-value");

        f1.thenAccept(r -> System.out.println("Section 2: " + r));
    }

    /*
     * =================================================================
     * SECTION 3: THE DEFAULT EXECUTOR — THE #1 GOTCHA
     * =================================================================
     *
     * When you DON'T pass an Executor:
     *
     *     CompletableFuture.supplyAsync(() -> doWork());
     *
     * Java silently uses ForkJoinPool.commonPool().
     *
     * Common Pool Size = Number of CPU cores - 1
     *
     *   - 4-core laptop  → 3 threads
     *   - 8-core server  → 7 threads
     *
     * THIS POOL IS SHARED BY THE ENTIRE JVM:
     *   - Your code
     *   - Spring
     *   - Hibernate
     *   - Kafka client
     *   - Every library that uses parallel streams or CF
     *
     * WHY THIS IS DANGEROUS FOR I/O TASKS:
     *
     *   I/O tasks BLOCK the thread (DB query, HTTP call, file read).
     *   Blocked threads cannot serve other tasks.
     *   The pool becomes the bottleneck.
     *
     *   Real example — 100 concurrent DB calls on 4-core laptop:
     *     Common pool = 3 threads
     *     100 tasks × 200ms each = 20 seconds of work
     *     20s / 3 threads = ~6.7 seconds total wall-clock time
     *     Instead of ~200ms if we had enough threads.
     *
     * THE SILENT DEADLOCK — Common Pool Starvation:
     *
     *     CompletableFuture.supplyAsync(() -> {
     *         return anotherFuture.get();  // BLOCKS this common pool thread
     *     });
     *
     *   If all common pool threads block waiting for other CFs that
     *   need the SAME pool to complete → deadlock. Nothing progresses.
     *
     * THE RULE:
     *
     *   ┌─────────────────────────────────────────────────────┐
     *   │ CPU-bound task  → default common pool is fine.       │
     *   │ I/O-bound task  → ALWAYS pass your own Executor.     │
     *   │ Blocking task   → ALWAYS pass your own Executor.     │
     *   └─────────────────────────────────────────────────────┘
     *
     * =================================================================
     */
    static void section3_defaultExecutorDanger() {
        // ❌ BAD — I/O task on common pool (invisible bottleneck)
        CompletableFuture<String> bad = CompletableFuture.supplyAsync(() -> {
            sleep(200); // simulate DB call
            return "user";
        });

        // ✅ GOOD — I/O task on dedicated pool
        ExecutorService ioPool = Executors.newFixedThreadPool(50);
        CompletableFuture<String> good = CompletableFuture.supplyAsync(() -> {
            sleep(200);
            return "user";
        }, ioPool);

        good.whenComplete((r, ex) -> ioPool.shutdown());
    }

    /*
     * =================================================================
     * SECTION 4: TRANSFORMATION — thenApply / thenAccept / thenRun
     * =================================================================
     *
     * All three run AFTER the previous stage completes successfully.
     * The difference is the SHAPE of the function.
     *
     *   thenApply(Function<T, U>)   → T -> U          (returns new value)
     *   thenAccept(Consumer<T>)     → T -> void       (consumes, no return)
     *   thenRun(Runnable)           → void -> void    (ignores result)
     *
     * MENTAL MODEL:
     *
     *   thenApply  = map        (transform the value)
     *   thenAccept = forEach    (do something with the value)
     *   thenRun    = onComplete (just run, ignore value)
     *
     * All three have ASYNC variants (thenApplyAsync, etc.) which run
     * the next stage on a DIFFERENT thread (typically common pool).
     * The non-async variants run on whichever thread completed the
     * previous stage — often the same thread. This matters for
     * performance but is subtle.
     *
     * =================================================================
     */
    static void section4_transformations() {
        // thenApply — transform the result
        CompletableFuture
                .supplyAsync(() -> 10 + 90)
                .thenApply(res -> res * 2)               // 100 → 200
                .thenApply(res -> "Result: " + res)      // 200 → "Result: 200"
                .thenAccept(System.out::println);        // prints "Result: 200"

        // thenAccept — consume the result, return nothing
        CompletableFuture
                .supplyAsync(() -> "pankaj")
                .thenApply(String::toUpperCase)          // "PANKAJ"
                .thenAccept(name ->
                        System.out.println("Hello " + name));

        // thenRun — ignore the result, just run next
        CompletableFuture
                .supplyAsync(() -> "Pankaj")
                .thenRun(() ->
                        System.out.println("Next task running"));
    }

    /*
     * =================================================================
     * SECTION 5: CHAINING ASYNC — thenCompose vs thenApply
     * =================================================================
     *
     * THE PROBLEM:
     *
     *   Suppose you have a function that ITSELF returns a
     *   CompletableFuture:
     *
     *       CompletableFuture<Integer> doubleIt(int n) {
     *           return CompletableFuture.supplyAsync(() -> n * 2);
     *       }
     *
     *   If you use thenApply:
     *
     *       supplyAsync(() -> 10)
     *           .thenApply(n -> doubleIt(n))
     *       // Result: CompletableFuture<CompletableFuture<Integer>>
     *       //                    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
     *       //                    NESTED! Ugly. Nightmare to unwrap.
     *
     *   If you use thenCompose:
     *
     *       supplyAsync(() -> 10)
     *           .thenCompose(n -> doubleIt(n))
     *       // Result: CompletableFuture<Integer>
     *       //         FLAT! Clean. This is what you want.
     *
     * MENTAL MODEL:
     *
     *   thenApply   = map       (T -> U)
     *   thenCompose = flatMap   (T -> CompletableFuture<U>)
     *
     * WHEN TO USE WHICH:
     *
     *   thenApply   → when the next step is a SYNCHRONOUS transformation
     *                 (T -> U, plain value in, plain value out)
     *
     *   thenCompose → when the next step is ANOTHER ASYNC task
     *                 (T -> CompletableFuture<U>)
     *
     * =================================================================
     */
    static void section5_thenApplyVsThenCompose() {
        // ❌ WRONG — nested future
        CompletableFuture<CompletableFuture<Integer>> nested =
                CompletableFuture.supplyAsync(() -> 10)
                        .thenApply(n -> CompletableFuture.supplyAsync(() -> n * 2));

        // ✅ RIGHT — flat future
        CompletableFuture<Integer> flat =
                CompletableFuture.supplyAsync(() -> 10)
                        .thenCompose(n -> CompletableFuture.supplyAsync(() -> n * 2));

        flat.thenAccept(r -> System.out.println("Section 5: " + r)); // 20
    }

    /*
     * =================================================================
     * SECTION 6: COMBINING TWO INDEPENDENT FUTURES — thenCombine
     * =================================================================
     *
     * THE PROBLEM:
     *
     *   You have 2 independent async tasks and want to combine
     *   their results.
     *
     *       Future<User>   userF   = fetchUser(id);
     *       Future<Orders> ordersF = fetchOrders(id);
     *       // Want: "user + orders" combined
     *
     * WRONG WAY (blocking, sequential):
     *
     *       User u = userF.get();      // blocks, ~200ms
     *       Orders o = ordersF.get();  // blocks, ~300ms
     *       String result = u + " " + o;
     *       // Total: 500ms (SUM)
     *
     * RIGHT WAY (thenCombine, non-blocking, parallel):
     *
     *       userF.thenCombine(ordersF, (u, o) -> u + " " + o);
     *       // Total: 300ms (MAX)
     *
     *   Because both futures were already running in parallel,
     *   the combined result arrives when BOTH complete.
     *
     * MENTAL MODEL:
     *
     *   thenCombine   = zipWith (combine two independent results)
     *   thenCompose   = flatMap (chain dependent async tasks)
     *
     * FORMULA:
     *
     *   thenCompose(fn)              → T -> CF<U>          (dependency)
     *   thenCombine(otherCF, fn)     → (T, U) -> V         (combine)
     *
     * =================================================================
     */
    static void section6_thenCombine() {
        ExecutorService pool = Executors.newFixedThreadPool(2);

        CompletableFuture<Integer> a = CompletableFuture.supplyAsync(() -> {
            sleep(200); return 10;
        }, pool);
        CompletableFuture<Integer> b = CompletableFuture.supplyAsync(() -> {
            sleep(300); return 20;
        }, pool);

        CompletableFuture<Integer> total = a.thenCombine(b, Integer::sum);

        total.thenAccept(r -> System.out.println("Section 6: " + r)) // 30
                .whenComplete((v, ex) -> pool.shutdown());
    }

    /*
     * =================================================================
     * SECTION 7: WAITING FOR MANY — allOf and anyOf
     * =================================================================
     *
     * allOf(cf1, cf2, cf3, ...) → CF<Void>
     *   - Completes when ALL input futures complete.
     *   - Return type is CF<Void> because generic array of heterogeneous
     *     futures loses type info. You call .join() on each input
     *     future to get its value.
     *
     * anyOf(cf1, cf2, cf3, ...) → CF<Object>
     *   - Completes when ANY ONE completes.
     *   - Also loses type info → CF<Object>.
     *   - Useful for timeouts, fastest-mirror-wins scenarios.
     *
     * COMMON PATTERN (parallel fan-out, fan-in):
     *
     *   List<CompletableFuture<Result>> futures = ids.stream()
     *       .map(id -> CompletableFuture.supplyAsync(() -> fetch(id), pool))
     *       .toList();
     *
     *   CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]))
     *       .thenRun(() -> {
     *           List<Result> results = futures.stream()
     *               .map(CompletableFuture::join) // safe, all done
     *               .toList();
     *           // process results
     *       });
     *
     * NOTE: join() is like get() but throws UNCHECKED exception.
     *   get() → checked (throws ExecutionException)
     *   join() → unchecked (throws CompletionException)
     *
     * =================================================================
     */
    static void section7_allOfAnyOf() {
        ExecutorService pool = Executors.newFixedThreadPool(5);

        CompletableFuture<Integer> f1 = CompletableFuture.supplyAsync(() -> { sleep(100); return 1; }, pool);
        CompletableFuture<Integer> f2 = CompletableFuture.supplyAsync(() -> { sleep(200); return 2; }, pool);
        CompletableFuture<Integer> f3 = CompletableFuture.supplyAsync(() -> { sleep(300); return 3; }, pool);

        // Wait for ALL
        CompletableFuture.allOf(f1, f2, f3)
                .thenRun(() -> {
                    int sum = f1.join() + f2.join() + f3.join(); // safe — all done
                    System.out.println("Section 7 allOf sum = " + sum); // 6
                })
                .whenComplete((v, ex) -> pool.shutdown());
    }

    /*
     * =================================================================
     * SECTION 8: EXCEPTION HANDLING IN THE CHAIN
     * =================================================================
     *
     * In synchronous code, you write try/catch around blocks.
     * In CF chains, exceptions are handled by attaching callbacks.
     *
     * THREE TOOLS:
     *
     *   exceptionally(Function<Throwable, T>)
     *     - Runs ONLY on exception.
     *     - Returns a fallback value (same type T).
     *     - Chain continues normally from that point.
     *
     *   handle(BiFunction<T, Throwable, U>)
     *     - Runs ALWAYS (success or failure).
     *     - You decide what to return.
     *     - Useful for transforming both success and failure into one shape.
     *
     *   whenComplete(BiConsumer<T, Throwable>)
     *     - Runs ALWAYS.
     *     - Does NOT change the result (side-effect only).
     *     - Useful for logging, metrics, cleanup.
     *
     * KEY RULE:
     *
     *   If a stage throws, the exception PROPAGATES through the rest
     *   of the chain, SKIPPING all subsequent thenApply/thenAccept/etc.,
     *   until it hits exceptionally/handle/whenComplete.
     *
     *   This is like how an exception unwinds the stack — but async.
     *
     * =================================================================
     */
    static void section8_exceptionHandling() {
        // exceptionally — fallback on error
        CompletableFuture
                .supplyAsync(() -> {
                    if (Math.random() < 0.5) throw new RuntimeException("boom");
                    return 42;
                })
                .thenApply(r -> r * 2)
                .exceptionally(ex -> {
                    System.out.println("Caught: " + ex.getMessage());
                    return -1; // fallback value
                })
                .thenAccept(r -> System.out.println("Section 8 result = " + r));

        // handle — always runs, transforms both success and failure
        CompletableFuture
                .supplyAsync(() -> "hello")
                .thenApply(s -> s.toUpperCase())
                .handle((result, ex) -> {
                    if (ex != null) return "ERROR: " + ex.getMessage();
                    return "OK: " + result;
                })
                .thenAccept(System.out::println);

        // whenComplete — side effect, doesn't change result
        CompletableFuture
                .supplyAsync(() -> 100)
                .whenComplete((r, ex) -> {
                    if (ex != null) System.out.println("Failed: " + ex);
                    else System.out.println("Succeeded with " + r);
                });
    }

    /*
     * =================================================================
     * SECTION 9: POLLING — THE ANTI-PATTERN (from your practice code)
     * =================================================================
     *
     * You wrote in your practice:
     *
     *     while (!completableFuture.isDone()) {
     *         Thread.sleep(1000);
     *         System.out.println("task is Running ....");
     *     }
     *     System.out.println(completableFuture.get());
     *
     * This WORKS but it's an ANTI-PATTERN. Why:
     *
     *   ✗ Busy-waiting wastes CPU cycles.
     *   ✗ Polling interval is arbitrary (1 second here) — you either
     *     wait too long or check too often.
     *   ✗ It's conceptually a BLOCKING operation dressed up as async.
     *   ✗ The whole point of CF is CALLBACKS, not polling.
     *
     * THE CORRECT WAY:
     *
     *     completableFuture.thenAccept(result ->
     *         System.out.println(result)
     *     );
     *
     *   No polling. No sleeping. The callback fires automatically
     *   when the result is ready.
     *
     * NOTE: isDone() is still useful for:
     *   - Health checks / monitoring dashboards
     *   - Non-blocking "is it ready yet?" in a game loop
     *   - NOT for waiting in normal business logic
     *
     * =================================================================
     */
    static void section9_pollingAntiPattern() throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(1);
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> {
            sleep(2000); return 100;
        }, pool);

        // ❌ ANTI-PATTERN — polling
        while (!cf.isDone()) {
            Thread.sleep(1000);
            System.out.println("still running...");
        }
        System.out.println("Polling result: " + cf.get());

        // ✅ CORRECT — callback
        CompletableFuture<Integer> cf2 = CompletableFuture.supplyAsync(() -> {
            sleep(2000); return 200;
        }, pool);

        cf2.thenAccept(r -> System.out.println("Callback result: " + r))
           .whenComplete((v, ex) -> pool.shutdown());

        // Give the callback time to fire in this demo
        Thread.sleep(3000);
    }

    /*
     * =================================================================
     * SECTION 10: get() vs join()
     * =================================================================
     *
     * Both block the calling thread until the result is available.
     *
     *   get()  → throws CHECKED ExecutionException
     *             (and InterruptedException)
     *             Use when you can handle checked exceptions.
     *
     *   join() → throws UNCHECKED CompletionException
     *             Use when you don't want try/catch clutter,
     *             especially inside lambdas/streams.
     *
     * RECOMMENDATION:
     *   In modern code, prefer join() inside chains and streams.
     *   Use get() only when you need fine-grained timeout control
     *   (get(timeout, TimeUnit)).
     *
     * IMPORTANT: join()/get() should be called ONLY when you're
     * sure the future is complete, or when you're at the "edge"
     * of your async boundary (e.g., returning from a controller).
     * NEVER call join() inside another async task on the same pool
     * → deadlock risk.
     *
     * =================================================================
     */

    /*
     * =================================================================
     * SECTION 11: CONNECTION TO SPRING @Async
     * =================================================================
     *
     * Everything you learned above directly applies to @Async.
     *
     * MAPPING:
     *
     *   Core Java CF                          Spring @Async
     *   ─────────────────────────────────────────────────────────────
     *   CompletableFuture.supplyAsync(fn)  →  @Async CompletableFuture<T> m()
     *   Executor pool passed as 2nd arg    →  @Async("taskExecutor") or
     *                                          default TaskExecutor bean
     *   thenApply / thenCompose / thenCombine →  SAME — @Async returns CF,
     *                                             you chain it identically
     *   exceptionally / handle             →  SAME
     *   allOf / anyOf                      →  SAME
     *
     * KEY INSIGHT:
     *
     *   @Async is just syntax sugar for:
     *
     *       executor.submit(() -> methodBody())
     *       wrapped so it returns CompletableFuture automatically.
     *
     *   The RETURN TYPE decides the behavior:
     *
     *       void                    → fire-and-forget
     *       CompletableFuture<T>    → composable async result
     *       Future<T>               → legacy, avoid
     *
     * DANGER — DEFAULT @Async EXECUTOR:
     *
     *   If you don't configure a TaskExecutor bean, Spring uses
     *   SimpleAsyncTaskExecutor → creates a NEW THREAD PER CALL.
     *   This is the Spring equivalent of the common pool problem,
     *   but WORSE — unbounded thread creation → OOM.
     *
     *   ALWAYS configure a ThreadPoolTaskExecutor bean:
     *
     *       @Bean(name = "taskExecutor")
     *       public Executor taskExecutor() {
     *           ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
     *           ex.setCorePoolSize(10);
     *           ex.setMaxPoolSize(50);
     *           ex.setQueueCapacity(200);
     *           ex.setThreadNamePrefix("Async-");
     *           ex.initialize();
     *           return ex;
     *       }
     *
     * =================================================================
     * SECTION 12: CHEAT SHEET — WHICH METHOD WHEN
     * =================================================================
     *
     * CREATION
     *   supplyAsync(fn)              → async task returning value
     *   runAsync(fn)                 → async task returning void
     *   completedFuture(v)           → already-done future
     *
     * TRANSFORM
     *   thenApply(fn)                → T -> U (sync map)
     *   thenAccept(consumer)         → T -> void (consume)
     *   thenRun(runnable)            → void -> void (ignore result)
     *
     * CHAIN ASYNC
     *   thenCompose(fn)              → T -> CF<U> (flatMap, dependent)
     *
     * COMBINE
     *   thenCombine(other, fn)       → (T,U) -> V (zipWith)
     *   allOf(cfs...)                → CF<Void> (wait for all)
     *   anyOf(cfs...)                → CF<Object> (wait for any)
     *
     * ERROR
     *   exceptionally(fn)            → fallback value on error
     *   handle(fn)                   → always, transform both paths
     *   whenComplete(action)         → always, side-effect only
     *
     * BLOCK (avoid in async chains)
     *   get()                        → blocking, checked exception
     *   join()                       → blocking, unchecked exception
     *
     * =================================================================
     * FINAL MENTAL MODEL
     * =================================================================
     *
     * CompletableFuture = a value that will exist in the future,
     *                     plus a CHAIN of transformations,
     *                     plus error handling,
     *                     plus composition with other futures.
     *
     * You don't WAIT for it. You ATTACH to it.
     *
     * The whole point: no blocking, no polling, no get() on hot paths.
     * Just pipelines that fire when data is ready.
     *
     * Once this is second nature, @Async is a trivial Spring wrapper
     * over the same concepts.
     *
     * =================================================================
     */

    // ---------- helper ----------
    private static void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }

//    // ---------- entry point ----------
//    public static void main(String[] args) throws Exception {
//        // section1_problemWithFuture();
//        // section2_creation();
//        // section3_defaultExecutorDanger();
//        // section4_transformations();
//        // section5_thenApplyVsThenCompose();
//        // section6_thenCombine();
//        // section7_allOfAnyOf();
//        // section8_exceptionHandling();
//        // section9_pollingAntiPattern();
//    }
}