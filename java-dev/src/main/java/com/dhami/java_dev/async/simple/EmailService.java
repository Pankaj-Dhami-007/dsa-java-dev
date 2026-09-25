package com.dhami.java_dev.async.simple;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * =====================================================================
 *                           EmailService
 * =====================================================================
 *
 * This service exposes two methods — identical bodies, but one has
 * @Async and one does not. The whole point is to compare their
 * runtime behavior side by side.
 *
 *   sendEmailSync  → runs on the CALLER thread, blocks the caller.
 *   sendEmailAsync → runs on a DIFFERENT thread, caller returns instantly.
 *
 * PREREQUISITE:
 *   @EnableAsync must be present somewhere in the Spring context.
 *   Without it, @Async is just metadata — Spring ignores it and the
 *   method runs synchronously (silent misbehavior).
 *
 * WHY CALLER MUST BE OUTSIDE THIS BEAN:
 *   @Async works through Spring's AOP proxy. If you call
 *   this.sendEmailAsync(...) from another method INSIDE this same
 *   bean, the proxy is bypassed and the method runs synchronously.
 *   That's why we call it from AsyncDemoRunner (a different bean).
 */
@Service
public class EmailService {

    /**
     * WITHOUT @Async.
     *
     * Behavior:
     *   - Runs on the caller's thread.
     *   - Blocks the caller for 3000ms.
     *   - Caller gets control back only after 3 seconds.
     */
    public void sendEmailSync(String to) {
        System.out.println("[SYNC]  Running on thread: "
                + Thread.currentThread().getName());
        sleep(3000);
        System.out.println("[SYNC]  Email sent to " + to);
    }

    /**
     * WITH @Async.
     *
     * Behavior:
     *   - Caller does NOT run the method body.
     *   - Spring proxy intercepts the call.
     *   - Method body is submitted to a TaskExecutor as a task.
     *   - Caller returns immediately.
     *   - Actual work happens on a background pool thread.
     *
     * REQUIREMENTS:
     *   - @EnableAsync active in the context.
     *   - Method must be public.
     *   - Caller must be outside this bean (proxy limitation).
     *
     * NOTE:
     *   Return type is void — fire-and-forget. Caller gets no result,
     *   and exceptions do NOT propagate to the caller.
     */
    @Async
    public void sendEmailAsync(String to) {
        System.out.println("[ASYNC] Running on thread: "
                + Thread.currentThread().getName());
        sleep(3000);
        System.out.println("[ASYNC] Email sent to " + to);
    }

    private static void sleep(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}