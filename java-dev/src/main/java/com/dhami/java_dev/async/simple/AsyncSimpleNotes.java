package com.dhami.java_dev.async.simple;

/**
 * =====================================================================
 *              @Async SIMPLE BEHAVIOUR — THEORY NOTES
 * =====================================================================
 *
 * ─────────────────────────────────────────────────────────────────
 * ONE-LINE DEFINITION
 * ─────────────────────────────────────────────────────────────────
 *
 *   @Async = "run this method on a different thread from a pool,
 *             and return immediately to the caller."
 *
 * ─────────────────────────────────────────────────────────────────
 * WHAT IT ACTUALLY DOES (5 STEPS AT RUNTIME)
 * ─────────────────────────────────────────────────────────────────
 *
 *   1. Spring proxy intercepts the method call.
 *   2. The method body is wrapped in a Runnable/Callable.
 *   3. The task is submitted to a TaskExecutor.
 *   4. The caller thread returns immediately.
 *   5. The method body runs on a pool thread in the background.
 *
 * ─────────────────────────────────────────────────────────────────
 * TIMELINE — WHEN IS THE POOL ASSIGNED?
 * ─────────────────────────────────────────────────────────────────
 *
 *   COMPILE TIME
 *     → Nothing. @Async is just bytecode metadata.
 *
 *   APPLICATION STARTUP
 *     → @EnableAsync registers AsyncAnnotationBeanPostProcessor.
 *     → BPP scans every bean, finds @Async methods.
 *     → Those beans are wrapped in PROXIES.
 *     → TaskExecutor is RESOLVED here (at startup).
 *         - If a bean named "taskExecutor" exists → use it.
 *         - Else if a unique TaskExecutor bean exists → use it.
 *         - Else → create default SimpleAsyncTaskExecutor.
 *     → No worker thread has run anything yet.
 *
 *   FIRST CALL TO AN @ASYNC METHOD (runtime)
 *     → Proxy intercepts the call.
 *     → Task is submitted to the TaskExecutor.
 *     → The first worker thread is created LAZILY here.
 *     → Method body runs on that worker thread.
 *
 *   SUBSEQUENT CALLS
 *     → Idle worker is reused if available.
 *     → Otherwise a new worker is created (up to pool max).
 *     → Otherwise the task is queued or rejected (per policy).
 *
 *   INTERVIEW ONE-LINER:
 *     Pool TYPE is decided at STARTUP.
 *     Worker THREADS are created LAZILY on first task submission.
 *
 * ─────────────────────────────────────────────────────────────────
 * DEFAULT POOL (WHEN YOU DON'T CONFIGURE ONE)
 * ─────────────────────────────────────────────────────────────────
 *
 *   SimpleAsyncTaskExecutor
 *     - Creates a NEW THREAD for EVERY @Async method call.
 *     - No pooling. No reuse. No queue. No max limit.
 *     - 1000 calls → 1000 threads → ~1GB stack memory → OOM risk.
 *     - Thread names: SimpleAsyncTaskExecutor-1, -2, -3, ...
 *
 *   OK for: demos, dev/test, very low traffic.
 *   BAD for: production.
 *
 *   FIX: define a bean named "taskExecutor" of type
 *        ThreadPoolTaskExecutor. Spring will use it instead.
 *
 * ─────────────────────────────────────────────────────────────────
 * REQUIREMENTS TO MAKE @Async WORK
 * ─────────────────────────────────────────────────────────────────
 *
 *   1. @EnableAsync present somewhere in the context.
 *   2. Method must be public.
 *   3. Call must come from OUTSIDE the bean (proxy limitation).
 *   4. Return type must be void or CompletableFuture<T>.
 *      Any other return type → silent misbehavior.
 *
 * ─────────────────────────────────────────────────────────────────
 * PROXY LIMITATION — SELF-INVOCATION
 * ─────────────────────────────────────────────────────────────────
 *
 *   ❌  this.asyncMethod()        → bypasses proxy, runs synchronously.
 *   ✅  otherBean.asyncMethod()   → goes through proxy, runs async.
 *
 *   Rule: caller and @Async method must be in DIFFERENT beans.
 *
 * ─────────────────────────────────────────────────────────────────
 * WHAT @ASYNC DOES *NOT* DO
 * ─────────────────────────────────────────────────────────────────
 *
 *   ✗ Does NOT make the method faster. Work still takes the same time.
 *   ✗ Does NOT reduce total work.
 *   ✗ Does NOT auto-propagate exceptions to caller (for void).
 *   ✗ Does NOT propagate Transaction, SecurityContext, or MDC.
 *   ✗ Does NOT work for self-invocation (same-bean calls).
 *
 * ─────────────────────────────────────────────────────────────────
 * TOP 5 INTERVIEW QUESTIONS
 * ─────────────────────────────────────────────────────────────────
 *
 *   Q1: What does @Async do?
 *       Runs the method on a different thread from a TaskExecutor;
 *       the caller returns immediately.
 *
 *   Q2: When is the thread pool assigned to @Async methods?
 *       At APPLICATION STARTUP (bean post-processing).
 *       Worker threads inside the pool are created LAZILY on the
 *       first task submission.
 *
 *   Q3: What is the default executor if you don't configure one?
 *       SimpleAsyncTaskExecutor — creates a new thread per call.
 *       Not suitable for production (OOM risk under load).
 *
 *   Q4: Why must @Async be called from outside the bean?
 *       Spring uses AOP proxies. Self-invocation (`this.method()`)
 *       bypasses the proxy, so @Async is not applied.
 *
 *   Q5: Does @Async make the code faster?
 *       No. It moves work off the caller thread. The work itself
 *       still takes the same amount of time.
 *
 * ─────────────────────────────────────────────────────────────────
 * MENTAL MODEL IN ONE SENTENCE
 * ─────────────────────────────────────────────────────────────────
 *
 *   @Async moves the method body to a different thread and returns
 *   to the caller immediately. Pool TYPE is decided at startup;
 *   worker THREADS are created lazily. Default pool is
 *   SimpleAsyncTaskExecutor (new thread per call) — avoid in prod.
 *
 * =====================================================================
 */
public final class AsyncSimpleNotes {
    private AsyncSimpleNotes() { }
}