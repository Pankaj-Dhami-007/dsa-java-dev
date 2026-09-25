package com.dhami.java_dev.async;

/**
 * =====================================================================
 *                    @Async DEEP DIVE — INTERNAL MECHANICS
 * =====================================================================
 *
 * This file is a pure reference document. No logic, no beans.
 * Just the complete internal flow of @Async — what happens at
 * startup, what happens at call time, and why things break.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 1: THE TWO PHASES OF @ASYNC
 * ─────────────────────────────────────────────────────────────────────
 *
 * @Async works in TWO DISTINCT PHASES:
 *
 *   PHASE 1 — STARTUP (Application Context Initialization)
 *     - @EnableAsync registers a special bean post-processor.
 *     - That post-processor scans all beans for @Async methods.
 *     - Beans with @Async get wrapped in AOP proxies.
 *     - The TaskExecutor is resolved once and stored.
 *     - NO worker thread has run anything yet.
 *
 *   PHASE 2 — RUNTIME (Method Call Time)
 *     - Caller calls the @Async method.
 *     - The proxy intercepts the call.
 *     - Proxy wraps method body in a Runnable.
 *     - Proxy submits Runnable to TaskExecutor.
 *     - Proxy returns control to caller immediately.
 *     - TaskExecutor runs method body on a worker thread.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 2: PHASE 1 — WHAT @EnableAsync DOES AT STARTUP
 * ─────────────────────────────────────────────────────────────────────
 *
 * @EnableAsync is NOT just a marker. It imports a configuration
 * that registers a bean post-processor: AsyncAnnotationBeanPostProcessor.
 *
 * Per Spring documentation: "Bean post-processor that automatically
 * applies asynchronous invocation behavior to any bean that carries
 * the @Async annotation at class or method-level by adding a
 * corresponding AsyncAnnotationAdvisor to the exposed proxy (either
 * an existing AOP proxy or a newly generated proxy that implements
 * all the target's interfaces)."[citation:2][citation:14]
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 3: THE BEAN POST-PROCESSOR — AsyncAnnotationBeanPostProcessor
 * ─────────────────────────────────────────────────────────────────────
 *
 * Spring's bean lifecycle has a phase where bean post-processors run
 * on every bean BEFORE it's ready for use. AsyncAnnotationBeanPostProcessor
 * hooks into this phase.[citation:7][citation:10]
 *
 * ITS JOB:
 *   1. Scan every bean for @Async annotations (class-level or method-level).
 *   2. If found, create an AOP proxy for that bean.
 *   3. The proxy wraps the original bean.
 *   4. All calls to the bean go through the proxy.
 *   5. The proxy can then intercept @Async method calls.
 *
 * IMPORTANT: This is why @Async only works when called from OUTSIDE
 * the bean. The proxy needs to intercept the call. If you call
 * this.asyncMethod() from within the same bean, you bypass the
 * proxy entirely and the method runs synchronously.[citation:5][citation:15]
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 4: THE PROXY — HOW IT INTERCEPTS
 * ─────────────────────────────────────────────────────────────────────
 *
 * Spring creates either:
 *   - JDK dynamic proxy (if bean implements interfaces), or
 *   - CGLIB proxy (subclass-based, for concrete classes).
 *
 * The proxy adds an "advisor" — AsyncAnnotationAdvisor — which
 * contains the interceptor logic for @Async methods.[citation:2]
 *
 * Per Spring docs: "The underlying async advisor applies before
 * existing advisors by default, in order to switch to async execution
 * as early as possible in the invocation chain."[citation:2]
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 5: TASK EXECUTOR RESOLUTION — WHICH POOL?
 * ─────────────────────────────────────────────────────────────────────
 *
 * The AsyncAnnotationBeanPostProcessor needs an Executor to submit
 * tasks to. Resolution happens in this order:[citation:2][citation:3]
 *
 *   Step 1: Is there a UNIQUE TaskExecutor bean in the context?
 *           → Use it.
 *
 *   Step 2: Is there an Executor bean NAMED "taskExecutor"?
 *           → Use it.
 *
 *   Step 3: Neither found?
 *           → Create a default executor.
 *
 * WHAT IS THE DEFAULT?
 *   Spring Framework's default is SimpleAsyncTaskExecutor.[citation:3][citation:8]
 *
 *   BUT — Spring Boot overrides this. Per Spring Boot docs:
 *   "In the absence of an Executor bean in the context, Spring Boot
 *   auto-configures a ThreadPoolTaskExecutor with sensible defaults."[citation:4]
 *
 *   So in a Spring Boot application, you actually get a
 *   ThreadPoolTaskExecutor by default (core=8, max=Integer.MAX_VALUE,
 *   unbounded queue, threadNamePrefix="task-").
 *
 *   BOTH DEFAULTS ARE DANGEROUS:
 *     - SimpleAsyncTaskExecutor: new thread per call → OOM risk.
 *     - ThreadPoolTaskExecutor default: unbounded queue + unbounded
 *       max → tasks pile up forever → OOM risk.
 *
 *   ALWAYS configure your own bean named "taskExecutor" for production.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 6: PHASE 2 — WHAT HAPPENS AT METHOD CALL TIME
 * ─────────────────────────────────────────────────────────────────────
 *
 * When caller invokes an @Async method:[citation:16]
 *
 *   1. CALLER makes the call.
 *      → e.g., emailService.sendEmail("user@x.com")
 *
 *   2. PROXY INTERCEPTS.
 *      → The caller is talking to the proxy, not the real object.
 *
 *   3. PROXY WRAPS METHOD BODY IN A RUNNABLE/CALLABLE.
 *      → Internally: Runnable task = () -> realObject.sendEmail("user@x.com");
 *
 *   4. PROXY SUBMITS TASK TO TASKEXECUTOR.
 *      → taskExecutor.execute(task)
 *
 *   5. PROXY RETURNS IMMEDIATELY.
 *      → Caller gets control back in ~microseconds.
 *      → If method returns CompletableFuture, an incomplete future
 *        is returned.
 *
 *   6. TASKEXECUTOR RUNS TASK ON A WORKER THREAD.
 *      → Worker thread picks up the task.
 *      → Method body executes on that thread.
 *
 *   7. RESULT / EXCEPTION HANDLING.
 *      → For void: exception is logged by AsyncUncaughtExceptionHandler.
 *      → For CompletableFuture: future completes (normally or exceptionally).
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 7: THE FULL PICTURE — DIAGRAM
 * ─────────────────────────────────────────────────────────────────────
 *
 *   STARTUP PHASE:
 *
 *   @EnableAsync
 *        │
 *        ▼
 *   Registers AsyncAnnotationBeanPostProcessor
 *        │
 *        ▼
 *   Scans every bean for @Async
 *        │
 *        ▼
 *   Wraps matching beans in AOP proxies
 *        │
 *        ▼
 *   Resolves TaskExecutor (config bean OR default)
 *        │
 *        ▼
 *   Ready. No worker threads running yet.
 *
 *   RUNTIME PHASE:
 *
 *   Caller ──► Proxy ──► wraps in Runnable ──► TaskExecutor.execute()
 *                                                     │
 *   Caller returns immediately ◄───────────────────────┘
 *                                                     │
 *                                                     ▼
 *                                              Worker Thread runs
 *                                              method body
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 8: WHY SELF-INVOCATION FAILS
 * ─────────────────────────────────────────────────────────────────────
 *
 * Because @Async relies on the PROXY, it only works when the call
 * goes through the proxy.[citation:5][citation:15]
 *
 *   ❌ SELF-INVOCATION (bypasses proxy):
 *
 *      @Service
 *      public class MyService {
 *          public void caller() {
 *              this.asyncMethod();  // ← direct call, no proxy
 *          }
 *
 *          @Async
 *          public void asyncMethod() { ... }  // ← runs SYNCHRONOUSLY!
 *      }
 *
 *   ✅ EXTERNAL CALL (goes through proxy):
 *
 *      @Service
 *      public class CallerService {
 *          @Autowired MyService myService;
 *
 *          public void caller() {
 *              myService.asyncMethod();  // ← goes through proxy ✅
 *          }
 *      }
 *
 * RULE: The caller and the @Async method must be in DIFFERENT beans.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 9: THE TASKEXECUTOR RESOLUTION ORDER (DETAILED)
 * ─────────────────────────────────────────────────────────────────────
 *
 * From Spring's AsyncAnnotationBeanPostProcessor Javadoc:
 *
 * "If not specified, default executor resolution will apply:
 * searching for a unique TaskExecutor bean in the context, or for an
 * Executor bean named 'taskExecutor' otherwise. If neither of the two
 * is resolvable, a local default executor will be created within the
 * interceptor."[citation:2]
 *
 * The default executor name constant is: DEFAULT_TASK_EXECUTOR_BEAN_NAME = "taskExecutor"[citation:10]
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 10: EXCEPTION HANDLING FOR void METHODS
 * ─────────────────────────────────────────────────────────────────────
 *
 * For methods returning void, exceptions thrown during async execution
 * CANNOT reach the caller.[citation:2][citation:13]
 *
 * WHY? The caller already returned. There's no one to catch it.
 *
 * DEFAULT BEHAVIOR: The exception is logged.
 *
 * CUSTOM HANDLING: Implement AsyncUncaughtExceptionHandler and
 * register it via AsyncConfigurer.[citation:3][citation:13]
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 11: @Async WITH @PostConstruct — DOESN'T WORK
 * ─────────────────────────────────────────────────────────────────────
 *
 * Per Spring documentation: "You can not use @Async in conjunction
 * with lifecycle callbacks such as @PostConstruct. To asynchronously
 * initialize Spring beans, you currently have to use a separate
 * initializing Spring bean that then invokes the @Async annotated
 * method on the target."[citation:13]
 *
 * WHY? @PostConstruct runs during bean initialization, BEFORE the
 * proxy is fully set up. So the call bypasses the proxy.
 *
 * ─────────────────────────────────────────────────────────────────────
 * SECTION 12: SPRING BOOT 3.5 BEAN NAME CHANGE
 * ─────────────────────────────────────────────────────────────────────
 *
 * IMPORTANT for Spring Boot 3.5+:
 *
 * As of Spring Boot 3.5, the auto-configured TaskExecutor bean is
 * named "applicationTaskExecutor" — the name "taskExecutor" is NO
 * LONGER provided by default.[citation:12]
 *
 * This causes issues when you have MULTIPLE executor beans in the
 * context (e.g., using @EnableAsync and @EnableScheduling together).
 *
 * The framework's default lookup still looks for a bean named
 * "taskExecutor". If it doesn't find one, it falls back to an
 * unconfigured SimpleAsyncTaskExecutor.[citation:11]
 *
 * WORKAROUND: Register an alias or explicitly reference
 * "applicationTaskExecutor" in your AsyncConfigurer.
 *
 * ─────────────────────────────────────────────────────────────────────
 * MENTAL MODEL — ONE SENTENCE
 * ─────────────────────────────────────────────────────────────────────
 *
 * @Async works in two phases: at STARTUP, a bean post-processor
 * wraps @Async beans in AOP proxies and resolves a TaskExecutor;
 * at RUNTIME, the proxy intercepts calls, wraps the method body
 * in a Runnable, submits it to the TaskExecutor, and returns
 * immediately to the caller. Self-invocation fails because it
 * bypasses the proxy.
 *
 * =====================================================================
 */
public final class AsyncDeepDrive {
    private AsyncDeepDrive() { }
}