package com.dhami.java_dev.async.taskexecutor;

/**
 * =====================================================================
 *                TaskExecutor HIERARCHY — DEEP NOTES
 * =====================================================================
 *
 * This file is a pure reference document. No logic, no beans, no
 * runtime behavior. Just the full class hierarchy, what each type
 * means, and when to use it.
 *
 * ─────────────────────────────────────────────────────────────────────
 * THE BIG PICTURE
 * ─────────────────────────────────────────────────────────────────────
 *
 * Two root interfaces matter:
 *
 *   1. java.util.concurrent.Executor          (JDK, Java 5)
 *   2. org.springframework.core.task.TaskExecutor  (Spring, extends JDK Executor)
 *
 * Spring does NOT replace JDK's Executor. It EXTENDS it. This is
 * intentional — Spring wants to be compatible with any JDK executor
 * (ThreadPoolExecutor, ForkJoinPool, custom implementations).
 *
 * The single method that defines the whole tree:
 *
 *     void execute(Runnable task)
 *
 * Everything else in the hierarchy is either:
 *   - A refinement of that method (adds Future, timeout, hints).
 *   - A concrete implementation (SimpleAsyncTaskExecutor, etc.).
 *   - An adapter (TaskExecutorAdapter, ConcurrentTaskExecutor).
 *
 * ─────────────────────────────────────────────────────────────────────
 * FULL HIERARCHY (TOP TO BOTTOM)
 * ─────────────────────────────────────────────────────────────────────
 *
 *   java.util.concurrent.Executor
 *   │
 *   │   void execute(Runnable command)
 *   │
 *   └── org.springframework.core.task.TaskExecutor
 *       │
 *       │   void execute(Runnable task)
 *       │   (Same signature; Spring re-declares it for documentation
 *       │    and to mark the interface as Spring's abstraction.)
 *       │
 *       ├── org.springframework.core.task.AsyncTaskExecutor
 *       │   │
 *       │   │   Future<?> submit(Runnable task)
 *       │   │   <T> Future<T> submit(Callable<T> task)
 *       │   │   void execute(Runnable task, long startTimeout)
 *       │   │
 *       │   │   Adds: result-bearing submission, timeout hints.
 *       │   │
 *       │   ├── SimpleAsyncTaskExecutor
 *       │   │     - New thread per task. No pooling. Default @Async pool.
 *       │   │     - Only OK for demos/dev.
 *       │   │
 *       │   ├── ThreadPoolTaskExecutor
 *       │   │     - Wraps JDK ThreadPoolExecutor. Full pool control.
 *       │   │     - Production choice for @Async.
 *       │   │
 *       │   ├── TaskExecutorAdapter
 *       │   │     - Adapts any JDK Executor to Spring's TaskExecutor.
 *       │   │
 *       │   └── ConcurrentTaskExecutor
 *       │         - Adapts JDK ExecutorService to Spring's AsyncTaskExecutor.
 *       │
 *       └── org.springframework.scheduling.SchedulingTaskExecutor
 *           │
 *           │   boolean prefersShortLivedTasks()
 *           │
 *           │   Adds: hints for the scheduler (short vs long tasks).
 *           │
 *           └── (implemented by ThreadPoolTaskExecutor and others)
 *
 * ─────────────────────────────────────────────────────────────────────
 * INTERFACE-BY-INTERFACE BREAKDOWN
 * ─────────────────────────────────────────────────────────────────────
 *
 * ① java.util.concurrent.Executor  (JDK)
 *
 *     @FunctionalInterface
 *     public interface Executor {
 *         void execute(Runnable command);
 *     }
 *
 *     Purpose: minimal abstraction for "run this task somewhere."
 *     No return value. No timeout. No hint. Just execute.
 *
 * ② org.springframework.core.task.TaskExecutor  (Spring)
 *
 *     @FunctionalInterface
 *     public interface TaskExecutor extends Executor {
 *         @Override
 *         void execute(Runnable task);
 *     }
 *
 *     Purpose: Spring's re-declaration of the same method, so Spring
 *     code can depend on TaskExecutor instead of JDK Executor.
 *     Enables container injection, lifecycle, and standardized lookup
 *     for @Async resolution.
 *
 *     Key point: ANY JDK Executor can be a TaskExecutor via adapter,
 *     but not vice versa — Spring's type is richer.
 *
 * ③ org.springframework.core.task.AsyncTaskExecutor  (Spring)
 *
 *     public interface AsyncTaskExecutor extends TaskExecutor {
 *         long TIMEOUT_IMMEDIATE = 0;
 *         long TIMEOUT_INDEFINITE = Long.MAX_VALUE;
 *
 *         Future<?> submit(Runnable task);
 *         <T> Future<T> submit(Callable<T> task);
 *         void execute(Runnable task, long startTimeout);
 *     }
 *
 *     Purpose: adds result-bearing submission and timeout hints.
 *     This is what @Async uses when the method returns Future or
 *     CompletableFuture — because the framework needs to hand back
 *     a Future object to the caller.
 *
 *     ThreadPoolTaskExecutor implements this.
 *     SimpleAsyncTaskExecutor also implements this.
 *
 * ④ org.springframework.scheduling.SchedulingTaskExecutor  (Spring)
 *
 *     public interface SchedulingTaskExecutor extends AsyncTaskExecutor {
 *         boolean prefersShortLivedTasks();
 *     }
 *
 *     Purpose: hint to the scheduler about task characteristics.
 *     Used internally by Spring's scheduling subsystem.
 *
 *     Rarely used directly in application code.
 *
 * ─────────────────────────────────────────────────────────────────────
 * CONCRETE IMPLEMENTATIONS — WHEN TO USE WHICH
 * ─────────────────────────────────────────────────────────────────────
 *
 * ┌────────────────────────────┬──────────────────────────────────────┐
 * │ Class                      │ Use Case                             │
 * ├────────────────────────────┼──────────────────────────────────────┤
 * │ SimpleAsyncTaskExecutor    │ Dev / demo only. NEVER production.   │
 * │                            │ New thread per call. No reuse.       │
 * │                            │ Default @Async pool if you don't     │
 * │                            │ configure anything.                  │
 * ├────────────────────────────┼──────────────────────────────────────┤
 * │ ThreadPoolTaskExecutor     │ Production @Async pool.              │
 * │                            │ Wraps JDK ThreadPoolExecutor.        │
 * │                            │ Full control: core/max/queue/naming. │
 * ├────────────────────────────┼──────────────────────────────────────┤
 * │ TaskExecutorAdapter        │ Bridge JDK Executor → Spring Task-   │
 * │                            │ Executor. Use when you already have  │
 * │                            │ a JDK Executor and want Spring to    │
 * │                            │ manage it.                           │
 * ├────────────────────────────┼──────────────────────────────────────┤
 * │ ConcurrentTaskExecutor     │ Bridge JDK ExecutorService → Spring  │
 * │                            │ AsyncTaskExecutor. Similar to above  │
 * │                            │ but preserves async contract.        │
 * ├────────────────────────────┼──────────────────────────────────────┤
 * │ VirtualThreadTaskExecutor  │ (Java 21+, Spring 6.1+)              │
 * │                            │ Wraps JDK virtual-thread-per-task    │
 * │                            │ executor. Modern alternative to      │
 * │                            │ ThreadPoolTaskExecutor for I/O work. │
 * └────────────────────────────┴──────────────────────────────────────┘
 *
 * ─────────────────────────────────────────────────────────────────────
 * HOW SPRING RESOLVES THE TaskExecutor FOR @Async
 * ─────────────────────────────────────────────────────────────────────
 *
 * When @EnableAsync is present, Spring searches for a TaskExecutor in
 * this EXACT order:
 *
 *   Step 1: Is there a unique TaskExecutor bean?     → use it.
 *   Step 2: Is there an Executor bean named          → use it.
 *           "taskExecutor"?
 *   Step 3: Neither found?                           → create a default.
 *
 * The "default" in Step 3 has changed over Spring versions:
 *
 *   Spring < 2.1.0:
 *     SimpleAsyncTaskExecutor (new thread per call)
 *
 *   Spring Boot ≥ 2.1.0:
 *     ThreadPoolTaskExecutor (core=8, max=Integer.MAX_VALUE,
 *     queue=unbounded, threadNamePrefix="task-")
 *
 *   Even the newer default is dangerous: unbounded queue + unbounded
 *   max threads = OOM risk under sustained load.
 *
 * ALWAYS configure your own bean named "taskExecutor" if you care
 * about production behavior.
 *
 * ─────────────────────────────────────────────────────────────────────
 * HOW THIS MAPS TO CORE JAVA
 * ─────────────────────────────────────────────────────────────────────
 *
 *   CORE JAVA                              SPRING
 *   ─────────────────────────────────────  ──────────────────────────
 *   java.util.concurrent.Executor          TaskExecutor (extends it)
 *   ExecutorService                        AsyncTaskExecutor
 *   Executors.newFixedThreadPool(n)        ThreadPoolTaskExecutor
 *   Executors.newCachedThreadPool()        SimpleAsyncTaskExecutor*
 *   Executors.newVirtualThreadPerTask...   VirtualThreadTaskExecutor
 *   submit(Callable) → Future<T>           AsyncTaskExecutor.submit
 *   execute(Runnable) → void               TaskExecutor.execute
 *
 *   * SimpleAsyncTaskExecutor is conceptually similar to
 *     newCachedThreadPool, but WORSE: no upper bound at all.
 *
 * ─────────────────────────────────────────────────────────────────────
 * KEY INSIGHTS — REMEMBER THESE
 * ─────────────────────────────────────────────────────────────────────
 *
 * 1. TaskExecutor is a MARKER around JDK's Executor. Same method.
 *    Different intent: Spring container integration.
 *
 * 2. AsyncTaskExecutor is where Future & timeout come in. This is
 *    what @Async uses when the method returns CompletableFuture.
 *
 * 3. SimpleAsyncTaskExecutor is the DEFAULT — and the DEFAULT is
 *    a footgun. New thread per call. OOM under load.
 *
 * 4. ThreadPoolTaskExecutor is what you configure in production.
 *    It's a thin wrapper over JDK's ThreadPoolExecutor.
 *
 * 5. Adapters exist (TaskExecutorAdapter, ConcurrentTaskExecutor)
 *    so you can plug in any JDK executor and get Spring integration.
 *
 * 6. @Async is literally "call taskExecutor.execute(runnable)" wrapped
 *    in proxy interception. Nothing more magical than that.
 *
 * ─────────────────────────────────────────────────────────────────────
 * DIAGRAM — WHERE @ASYNC PLUGS IN
 * ─────────────────────────────────────────────────────────────────────
 *
 *   ┌──────────────────┐
 *   │  Caller Bean     │
 *   │  calls @Async    │
 *   │  method          │
 *   └────────┬─────────┘
 *            │
 *            ▼
 *   ┌──────────────────┐
 *   │  Spring AOP      │   intercepts the call,
 *   │  Proxy           │   wraps body in Runnable
 *   └────────┬─────────┘
 *            │
 *            │ taskExecutor.execute(runnable)
 *            ▼
 *   ┌──────────────────┐
 *   │  TaskExecutor    │   ← the thing you configure
 *   │  (SimpleAsync    │
 *   │   or ThreadPool  │
 *   │   or Virtual)    │
 *   └────────┬─────────┘
 *            │
 *            ▼
 *   ┌──────────────────┐
 *   │  Worker Thread   │   method body actually runs here
 *   └──────────────────┘
 *
 * ─────────────────────────────────────────────────────────────────────
 * QUICK REVISION TABLE
 * ─────────────────────────────────────────────────────────────────────
 *
 *   Interface / Class             │ Adds what?
 *   ──────────────────────────────┼────────────────────────────────
 *   Executor (JDK)                │ execute(Runnable)
 *   TaskExecutor                  │ (same, Spring-typed)
 *   AsyncTaskExecutor             │ submit() → Future, timeouts
 *   SchedulingTaskExecutor        │ prefersShortLivedTasks() hint
 *   SimpleAsyncTaskExecutor       │ new thread per call
 *   ThreadPoolTaskExecutor        │ pooled, configurable
 *   TaskExecutorAdapter           │ bridge JDK Executor
 *   ConcurrentTaskExecutor        │ bridge JDK ExecutorService
 *   VirtualThreadTaskExecutor     │ virtual threads (Java 21+)
 *
 * ─────────────────────────────────────────────────────────────────────
 * MENTAL MODEL IN ONE SENTENCE
 * ─────────────────────────────────────────────────────────────────────
 *
 * TaskExecutor is Spring's thin extension of JDK's Executor, with
 * AsyncTaskExecutor adding Future support, and ThreadPoolTaskExecutor
 * being the production-grade implementation that wraps JDK's
 * ThreadPoolExecutor. @Async uses it internally — it's just
 * taskExecutor.execute(runnable) behind a proxy.
 *
 * =====================================================================
 */
public final class TaskExecutorHierarchyNotes {
    private TaskExecutorHierarchyNotes() { }
}