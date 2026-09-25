package com.dhami.java_dev.async.completablefuture;

/**
 * =====================================================================
 *      @Async + CompletableFuture — THEORY NOTES
 * =====================================================================
 *
 * This file is a pure reference document. No logic, no beans.
 * Just the complete theory of combining @Async with CompletableFuture.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 1: WHY COMPLETABLEFUTURE WITH @ASYNC?
 * ─────────────────────────────────────────────────────────────────────
 *
 * Recall: @Async with void return type is fire-and-forget.
 *   - Caller gets nothing back.
 *   - Exceptions don't propagate.
 *   - No way to know when the task finishes.
 *
 * @Async with CompletableFuture<T> return type solves all three:
 *   - Caller gets a handle (the future) to the result.
 *   - Exceptions propagate through the future.
 *   - Caller can chain, combine, and compose futures.
 *
 * In short: void @Async is "do this in background."
 *           CompletableFuture @Async is "do this in background,
 *           give me a handle to the result."
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 2: THE RETURN TYPE CONTRACT
 * ─────────────────────────────────────────────────────────────────────
 *
 * When an @Async method declares CompletableFuture<T>, Spring's
 * proxy does something clever:
 *
 *   1. It creates a new CompletableFuture<T> internally.
 *   2. It submits the method body to the TaskExecutor.
 *   3. The method body executes and RETURNS a CompletableFuture.
 *   4. Spring's proxy chains that returned future to the one it
 *      created, so the caller sees the result.
 *
 * KEY IMPLICATION FOR YOUR CODE:
 *   Your @Async method MUST return a CompletableFuture object.
 *   The method body itself runs on a worker thread and simply
 *   returns a future. Since the body is already on a worker thread,
 *   the returned future is typically ALREADY COMPLETE.
 *
 * In other words, you should return:
 *
 *     return CompletableFuture.completedFuture(result);
 *
 * ...NOT:
 *
 *     return CompletableFuture.supplyAsync(() -> result);
 *
 * WHY? Because the method body is ALREADY running asynchronously
 * (on a pool thread). Wrapping it in another supplyAsync would
 * submit a NEW task to a DIFFERENT pool — wasteful and confusing.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 3: THE MENTAL MODEL
 * ─────────────────────────────────────────────────────────────────────
 *
 * WRONG MENTAL MODEL (common mistake):
 *
 *   "I need to make my method async, so I'll use supplyAsync inside."
 *
 *     @Async
 *     public CompletableFuture<User> getUser() {
 *         return CompletableFuture.supplyAsync(() -> fetchUser());
 *         //     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *         //     WRONG: double async — method already runs on a pool.
 *         //     This submits to a SECOND pool (ForkJoinPool.commonPool
 *         //     or whatever the default is).
 *     }
 *
 * RIGHT MENTAL MODEL:
 *
 *   "The method body is already async. I just need to give the
 *    caller a CompletableFuture with the result."
 *
 *     @Async
 *     public CompletableFuture<User> getUser() {
 *         User u = fetchUser();      // plain synchronous call
 *         return CompletableFuture.completedFuture(u);
 *         //     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 *         //     CORRECT: the method runs on a pool thread already.
 *         //     The returned future is already complete.
 *     }
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 4: WHAT THE CALLER DOES
 * ─────────────────────────────────────────────────────────────────────
 *
 * The caller gets a CompletableFuture<T> immediately — possibly
 * already complete, possibly not (depending on timing).
 *
 * The caller can:
 *
 *   1. BLOCK on it:      future.get()  or  future.join()
 *
 *   2. CHAIN on it:      future.thenApply(...)
 *                        future.thenCompose(...)
 *                        future.thenAccept(...)
 *
 *   3. COMBINE with other futures:
 *                        future1.thenCombine(future2, ...)
 *                        CompletableFuture.allOf(f1, f2, f3)
 *
 *   4. HANDLE errors:    future.exceptionally(...)
 *                        future.handle(...)
 *
 * IMPORTANT:
 *   The CALLER should NOT block if it can avoid it. The whole
 *   point of CompletableFuture is non-blocking composition.
 *   Use .thenAccept() for side effects, .thenApply() for
 *   transformations, .thenCombine() for joining.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 5: EXCEPTION PROPAGATION
 * ─────────────────────────────────────────────────────────────────────
 *
 * With void @Async:
 *   - Exceptions never reach the caller.
 *   - Logged by AsyncUncaughtExceptionHandler (default: just logs).
 *
 * With CompletableFuture @Async:
 *   - If the method body throws, the returned future completes
 *     EXCEPTIONALLY.
 *   - The caller can observe via:
 *
 *         future.exceptionally(ex -> fallbackValue);
 *         future.handle((value, ex) -> valueOrFallback);
 *         future.whenComplete((value, ex) -> logSomething);
 *
 *   - If the caller calls get()/join(), the exception surfaces
 *     as ExecutionException (get) or CompletionException (join).
 *
 * KEY PATTERN:
 *   When you return a CompletableFuture and the method body fails,
 *   use CompletableFuture.failedFuture(ex) to build an already-
 *   failed future:
 *
 *     @Async
 *     public CompletableFuture<User> getUser(Long id) {
 *         try {
 *             return CompletableFuture.completedFuture(fetchUser(id));
 *         } catch (Exception e) {
 *             return CompletableFuture.failedFuture(e);
 *         }
 *     }
 *
 * (In Java 8, use a helper to construct failed futures since
 *  CompletableFuture.failedFuture was added in Java 9.)
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 6: TIMEOUTS
 * ─────────────────────────────────────────────────────────────────────
 *
 * CompletableFuture has no built-in timeout mechanism on its own.
 * To time out an async operation, use:
 *
 *     future.orTimeout(5, TimeUnit.SECONDS)     // Java 9+
 *     future.completeOnTimeout(defaultValue, 5, SECONDS)  // Java 9+
 *
 * Or, use future.get(5, TimeUnit.SECONDS) at the boundary — but
 * this blocks and is not the CompletableFuture-idiomatic approach.
 *
 * OrTimeout throws TimeoutException when the timeout elapses.
 * completeOnTimeout returns a default value instead.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 7: COMPOSITION ACROSS @ASYNC METHODS
 * ─────────────────────────────────────────────────────────────────────
 *
 * The real power: chaining multiple @Async methods together.
 *
 *     @Async public CompletableFuture<User> fetchUser(Long id) { ... }
 *     @Async public CompletableFuture<Orders> fetchOrders(Long userId) { ... }
 *     @Async public CompletableFuture<Points> fetchPoints(Long userId) { ... }
 *
 * Now, in a caller (which could itself be @Async or a plain method):
 *
 *     CompletableFuture<User> userF = service.fetchUser(1L);
 *     CompletableFuture<Orders> ordersF = service.fetchOrders(1L);
 *     CompletableFuture<Points> pointsF = service.fetchPoints(1L);
 *
 *     CompletableFuture<Profile> profileF =
 *         userF.thenCombine(ordersF, (u, o) -> new PartialProfile(u, o))
 *              .thenCombine(pointsF, (p, pt) -> new Profile(p.user, p.orders, pt));
 *
 *     profileF.thenAccept(profile -> log.info("Profile ready: {}", profile));
 *
 * Note: no blocking anywhere. Every step is a composition.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 8: COMMON PITFALLS
 * ─────────────────────────────────────────────────────────────────────
 *
 * PITFALL 1: Returning supplyAsync() inside an @Async method
 *   - Double async.
 *   - Wastes two pools.
 *   - Wrong mental model.
 *   Fix: use CompletableFuture.completedFuture(...).
 *
 * PITFALL 2: Using @Async with a non-Future return type
 *   - e.g., @Async returning String, User, List, etc.
 *   - Spring ignores the return value; method still runs async.
 *   - Caller gets NULL.
 *   - Silent bug.
 *   Fix: return void or CompletableFuture<T>. Nothing else.
 *
 * PITFALL 3: Blocking on the future inside an @Async method
 *   - e.g., someOtherFuture.get() inside an @Async method.
 *   - Blocks a worker thread, wasting the pool.
 *   - Can cause deadlock if the pool is small.
 *   Fix: use thenCompose / thenCombine to compose, don't block.
 *
 * PITFALL 4: Not configuring the pool
 *   - Default pool is dangerous (unbounded queue in Spring Boot).
 *   - Under sustained load → OOM.
 *   Fix: define a ThreadPoolTaskExecutor bean named "taskExecutor".
 *
 * PITFALL 5: Self-invocation
 *   - @Async called from within the same bean → synchronous.
 *   - Same proxy limitation as void @Async.
 *   Fix: call from outside the bean, or self-inject the proxy.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 9: @ASYNC vs CompletableFuture.supplyAsync — WHEN TO USE WHICH
 * ─────────────────────────────────────────────────────────────────────
 *
 * Both submit work to a pool and return a future. So which one?
 *
 * @Async:
 *   + Declarative. Annotation-only.
 *   + Pool is resolved by Spring (name-based).
 *   + Integrates with Spring AOP, transactions, etc.
 *   + Easier to test with mocks.
 *   - Needs @EnableAsync.
 *   - Proxy limitations (self-invocation).
 *   - Must be public, on a Spring bean.
 *
 * CompletableFuture.supplyAsync(fn, pool):
 *   + No Spring dependency. Pure Java.
 *   + No proxy. No self-invocation issue.
 *   + Works anywhere, anytime.
 *   - Must pass pool explicitly (or use common pool — dangerous).
 *   - Doesn't participate in Spring's @Async pool resolution.
 *   - Doesn't participate in Spring transaction/scheduling plumbing.
 *
 * RULE OF THUMB:
 *   In Spring Boot apps, prefer @Async for service-level methods.
 *   Use CompletableFuture.supplyAsync only when you need to
 *   dynamically choose a pool, or when you're outside Spring's
 *   context.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 10: INTERVIEW QUESTIONS
 * ─────────────────────────────────────────────────────────────────────
 *
 * Q1: What does @Async return?
 *     Nothing meaningful unless the return type is void or
 *     CompletableFuture<T> (or Future<T>). Any other type is a bug.
 *
 * Q2: How do you get a result back from an @Async method?
 *     Declare the return type as CompletableFuture<T>. The method
 *     body should return CompletableFuture.completedFuture(result).
 *
 * Q3: Why should you NOT return supplyAsync() from an @Async method?
 *     The method body already runs on a pool thread. Wrapping it
 *     in supplyAsync submits a second task to a different pool —
 *     wasteful and confusing.
 *
 * Q4: How do exceptions propagate?
 *     If the method body throws, the returned future completes
 *     exceptionally. Callers observe via .exceptionally(), .handle(),
 *     or by catching ExecutionException/CompletionException at the
 *     boundary.
 *
 * Q5: How do you compose multiple @Async methods?
 *     Use thenCombine, thenCompose, allOf, anyOf — same as plain
 *     CompletableFuture.
 *
 * Q6: What's the difference between @Async and supplyAsync?
 *     @Async is Spring's declarative abstraction; supplyAsync is
 *     JDK's programmatic one. Both submit work to a pool. @Async
 *     integrates with Spring context, proxies, and configuration.
 *
 * Q7: Can an @Async method call another @Async method?
 *     Yes, if the caller is a different bean. Self-invocation
 *     bypasses the proxy and runs synchronously.
 *
 * Q8: How do you enforce a timeout on an @Async call?
 *     Use future.orTimeout(...) or future.completeOnTimeout(...)
 *     (Java 9+). Or future.get(timeout, unit) at the boundary.
 *
 * ─────────────────────────────────────────────────────────────────────
 * MENTAL MODEL IN ONE SENTENCE
 * ─────────────────────────────────────────────────────────────────────
 *
 * @Async + CompletableFuture = declarative async execution with a
 * result handle. The method body already runs on a pool thread, so
 * return CompletableFuture.completedFuture(result) — not another
 * supplyAsync. Callers compose with thenApply/thenCompose/thenCombine
 * and handle errors with exceptionally/handle.
 *
 * =====================================================================
 */
public final class AsyncCompletableFutureNotes {
    private AsyncCompletableFutureNotes() { }
}