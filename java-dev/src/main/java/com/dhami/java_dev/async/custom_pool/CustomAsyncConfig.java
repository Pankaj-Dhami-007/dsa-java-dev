package com.dhami.java_dev.async.custom_pool;

import com.dhami.java_dev.async.custom_pool.CustomThreadFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Spring configuration that defines a custom TaskExecutor bean.
 *
 * WHY THIS EXISTS:
 *   By default, Spring (via Spring Boot auto-config) uses a
 *   ThreadPoolTaskExecutor with unbounded queue and maxPoolSize =
 *   Integer.MAX_VALUE. This is dangerous in production — tasks pile
 *   up indefinitely, leading to memory exhaustion.
 *
 *   This config replaces the default with an EXPLICITLY configured
 *   ThreadPoolTaskExecutor with bounded queue and a custom
 *   ThreadFactory. It gives us full control over concurrency.
 *
 * BEAN NAME MATTERS:
 *   The bean MUST be named "taskExecutor". Spring's @Async
 *   resolution logic looks for a bean with exactly this name
 *   (Spring's DEFAULT_TASK_EXECUTOR_BEAN_NAME constant).
 *   If you name it anything else, Spring won't find it and will
 *   fall back to its own default.
 *
 * NOTE:
 *   @EnableAsync is present on the main application class
 *   (JavaDevApplication). We do NOT repeat it here — one
 *   @EnableAsync per application is enough.
 */
@Configuration
public class CustomAsyncConfig {

    /**
     * The custom TaskExecutor bean.
     *
     * CONFIGURATION CHOICES:
     *   corePoolSize    = 2   → always 2 threads alive.
     *   maxPoolSize     = 4   → up to 4 threads under burst.
     *   queueCapacity   = 10  → up to 10 tasks can wait.
     *   keepAliveSeconds= 30  → extra threads die after 30s idle.
     *   threadNamePrefix= "async-worker-" → readable thread names.
     *   rejectionHandler= CallerRunsPolicy → back-pressure, no drops.
     *
     * TOTAL CAPACITY BEFORE BACK-PRESSURE:
     *   maxPoolSize + queueCapacity = 4 + 10 = 14 tasks.
     *   Task 15 onwards → CallerRunsPolicy runs it on caller thread.
     */
    @Bean(name = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();

        // Minimum threads always alive
        executor.setCorePoolSize(2);

        // Maximum threads allowed under load
        executor.setMaxPoolSize(4);

        // Bounded queue — prevents unbounded task pile-up
        executor.setQueueCapacity(10);

        // Idle threads above corePoolSize die after this many seconds
        executor.setKeepAliveSeconds(30);

        // Custom ThreadFactory → readable thread names in logs/dumps
        executor.setThreadFactory(new CustomThreadFactory("async-worker-"));

        // When pool + queue are full → caller runs the task itself
        // (natural back-pressure, no task loss)
        executor.setRejectedExecutionHandler(
                new ThreadPoolExecutor.CallerRunsPolicy()
        );

        // Initialize the executor (creates the underlying pool)
        executor.initialize();

        return executor;
    }
}