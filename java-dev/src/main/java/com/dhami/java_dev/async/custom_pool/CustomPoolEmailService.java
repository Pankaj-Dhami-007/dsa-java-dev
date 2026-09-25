package com.dhami.java_dev.async.custom_pool;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service with @Async methods that will run on our custom pool.
 *
 * WHEN YOU CALL sendEmailAsync():
 *   1. Spring proxy intercepts.
 *   2. Method body is submitted to the "taskExecutor" bean
 *      (our custom ThreadPoolTaskExecutor with CustomThreadFactory).
 *   3. Method runs on a thread named "async-worker-N".
 *   4. Caller returns immediately.
 *
 * WHAT YOU'LL SEE IN THE OUTPUT:
 *   [ASYNC] Running on thread: async-worker-1
 *   [ASYNC] Running on thread: async-worker-2
 *   ...
 *
 * NOT "pool-1-thread-1" or "SimpleAsyncTaskExecutor-1".
 * That proves our custom factory is being used.
 */
@Service
public class CustomPoolEmailService {

    /**
     * Plain @Async — no qualifier.
     *
     * Since we have a unique bean named "taskExecutor", Spring's
     * resolution logic will pick it up automatically.
     *
     * If we had MULTIPLE TaskExecutor beans, we would need to
     * specify which one via @Async("beanName").
     */
    @Async
    public void sendEmailAsync(String to) {
        System.out.println("[ASYNC] Running on thread: "
                + Thread.currentThread().getName()
                + " | sending email to " + to);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("[ASYNC] Email sent to " + to
                + " (thread: " + Thread.currentThread().getName() + ")");
    }
}

/*

Key Takeaways
Bean name "taskExecutor" is mandatory — Spring's @Async resolution looks for exactly this name.

Custom ThreadFactory makes debugging easier — thread names like async-worker-1 are far better than pool-1-thread-1.

@EnableAsync on the main class is enough — don't repeat it in the config.
 */

/**

The Two Forms of @Async

 @Async                 // form 1 — no argument
@Async("beanName")     // form 2 — with a bean name qualifier
@Async("beanName")     // form 3 — with a bean name qualifier (same as above)

 The annotation has a single optional attribute value, which is the name of the
TaskExecutor bean you want to use.

 Form 1: @Async (No Argument)  >>>>
java
@Async
public void sendEmail() { ... }
What happens:

Spring uses its default resolution order to pick an executor:

Unique TaskExecutor bean in context → use it.
Bean named "taskExecutor" → use it.
Neither → fall back to a default.
Our demo worked because:

We defined a bean named "taskExecutor" in CustomAsyncConfig.

Spring's step 2 found it.

So @Async used OUR pool without us having to specify anything.

This is the cleanest form when:

You have exactly ONE pool for all async work.

That pool is named "taskExecutor".



 Form 2: @Async("beanName") (With a Qualifier)
java
@Async("emailExecutor")
public void sendEmail() { ... }

@Async("reportExecutor")
public CompletableFuture<Report> generateReport() { ... }
What happens:

Spring does NOT use the default resolution.

It looks for a bean with the EXACT name "emailExecutor".

If not found → throws NoSuchBeanDefinitionException at startup.

This is required when:

You have multiple TaskExecutor beans and want to route different @Async methods to different pools.


 */

/**
 *
 * Scenario 1: Different Workloads, Different Sizes
 * Imagine you have two kinds of async tasks:
 *
 * java
 * // Fast, frequent tasks — many small email sends
 * @Async
 * public void sendEmail(String to) { ... }     // 100ms each, 1000s per minute
 *
 * // Slow, rare tasks — report generation
 * @Async
 * public CompletableFuture<Report> generateReport(Long id) { ... }  // 30s each
 * If both share ONE pool:
 *
 * Reports take 30 seconds each.
 *
 * With only 4 threads, four reports can occupy the entire pool.
 *
 * 1000 email sends are stuck in the queue behind them.
 *
 * Head-of-line blocking — the fast tasks wait behind the slow ones.
 *
 * Solution: Give each type its own pool.
 *
 * java
 * @Configuration
 * public class AsyncConfig {
 *
 *     // Small pool for quick email tasks
 *     @Bean(name = "emailExecutor")
 *     public Executor emailExecutor() {
 *         ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
 *         ex.setCorePoolSize(4);
 *         ex.setMaxPoolSize(8);
 *         ex.setQueueCapacity(500);
 *         ex.setThreadNamePrefix("email-");
 *         ex.initialize();
 *         return ex;
 *     }
 *
 *     // Separate pool for slow report generation
 *     @Bean(name = "reportExecutor")
 *     public Executor reportExecutor() {
 *         ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
 *         ex.setCorePoolSize(2);
 *         ex.setMaxPoolSize(4);
 *         ex.setQueueCapacity(10);
 *         ex.setThreadNamePrefix("report-");
 *         ex.initialize();
 *         return ex;
 *     }
 * }
 * And then in the service:
 *
 * java
 * @Async("emailExecutor")
 * public void sendEmail(String to) { ... }
 *
 * @Async("reportExecutor")
 * public CompletableFuture<Report> generateReport(Long id) { ... }
 * Now slow reports can't block fast emails. Each pool operates independently.
 *
 * Scenario 2: Different Concurrency Limits
 * Some downstream systems can only handle limited concurrency:
 *
 * java
 * // Payment gateway allows at most 5 concurrent requests
 * @Bean(name = "paymentExecutor")
 * public Executor paymentExecutor() {
 *     ThreadPoolTaskExecutor ex = new ThreadPoolTaskExecutor();
 *     ex.setCorePoolSize(5);
 *     ex.setMaxPoolSize(5);      // ← HARD LIMIT
 *     ex.setQueueCapacity(100);
 *     ex.setThreadNamePrefix("payment-");
 *     ex.initialize();
 *     return ex;
 * }
 * java
 * @Async("paymentExecutor")
 * public CompletableFuture<PaymentResult> processPayment(Payment p) { ... }
 * This enforces a hard cap on concurrent calls to the payment gateway — protecting both the downstream service and yourself.
 *
 * Scenario 3: CPU-Bound vs I/O-Bound Separation
 * CPU-heavy tasks (image processing, PDF generation) → small pool sized to CPU cores.
 *
 * I/O-heavy tasks (DB, network) → larger pool.
 *
 * If they share a pool, CPU-bound tasks starve the I/O tasks of threads.
 */