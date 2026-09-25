package com.dhami.java_dev.async.custom_pool;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Custom ThreadFactory that names threads meaningfully.
 *
 * WHY THIS EXISTS:
 *   The default ThreadFactory names threads like "pool-1-thread-1",
 *   which tells you nothing about what the thread does. In a
 *   production thread dump, you can't tell which pool a thread
 *   belongs to. Naming threads meaningfully makes debugging and
 *   monitoring 10x easier.
 *
 * WHAT IT DOES:
 *   - Sets a custom name prefix for every thread.
 *   - Sets threads as daemon (JVM can exit cleanly without shutdown).
 *   - Uses an AtomicInteger to give each thread a unique number.
 *
 * USAGE:
 *   This factory will be assigned to a ThreadPoolTaskExecutor bean,
 *   which will then be used by @Async methods.
 */
public class CustomThreadFactory implements ThreadFactory {

    // Prefix for thread names, e.g., "async-worker-"
    private final String namePrefix;

    // Atomic counter for unique thread numbers, e.g., 1, 2, 3...
    private final AtomicInteger counter = new AtomicInteger(1);

    public CustomThreadFactory(String namePrefix) {
        this.namePrefix = namePrefix;
    }

    @Override
    public Thread newThread(Runnable r) {
        // Create a thread with a unique name: "async-worker-1", "async-worker-2", ...
        Thread t = new Thread(r, namePrefix + counter.getAndIncrement());

        // Mark as daemon so JVM can exit without waiting for these threads
        t.setDaemon(true);

        // Optional: attach an uncaught exception handler for visibility
        t.setUncaughtExceptionHandler((thread, ex) ->
                System.err.println("[UNCAUGHT] in " + thread.getName()
                        + ": " + ex.getMessage())
        );

        return t;
    }
}