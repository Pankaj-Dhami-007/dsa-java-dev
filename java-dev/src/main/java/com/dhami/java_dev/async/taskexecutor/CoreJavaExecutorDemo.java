package com.dhami.java_dev.async.taskexecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * =====================================================================
 *           CORE JAVA APPROACH — Raw ExecutorService
 * =====================================================================
 *
 * This class demonstrates how you'd do async work in pure Core Java,
 * using JDK's ExecutorService directly.
 *
 * KEY POINTS:
 *   - You create the ExecutorService yourself.
 *   - You manage its lifecycle (shutdown).
 *   - You call execute()/submit() explicitly.
 *   - Nothing is managed by a container.
 *
 * COMPARE WITH: SpringTaskExecutorDemo.java
 */
public class CoreJavaExecutorDemo {

    /**
     * Core Java: you own the executor.
     * You must shut it down, or the JVM hangs.
     */
    public static void runDemo() {
        System.out.println("\n=== CORE JAVA: ExecutorService ===");

        // 1. YOU create the pool
        ExecutorService executor = Executors.newFixedThreadPool(2);

        try {
            // 2. YOU submit tasks
            for (int i = 1; i <= 4; i++) {
                final int taskId = i;
                executor.execute(() -> {
                    System.out.println(
                            "[CoreJava] Task-" + taskId +
                            " on " + Thread.currentThread().getName()
                    );
                    sleep(500);
                });
            }
        } finally {
            // 3. YOU shut it down
            executor.shutdown();
            try {
                executor.awaitTermination(5, java.util.concurrent.TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                executor.shutdownNow();
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("=== CORE JAVA DONE ===");
    }

    private static void sleep(long ms) {
        try { Thread.sleep(ms); }
        catch (InterruptedException e) { Thread.currentThread().interrupt(); }
    }
}