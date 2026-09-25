package com.dhami.java_dev.async.simple;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * =====================================================================
 *                         AsyncDemoRunner
 * =====================================================================
 *
 * Runs automatically after Spring Boot starts (because it implements
 * CommandLineRunner).
 *
 * WHAT IT DOES:
 *   1. Injects EmailService via constructor.
 *   2. Calls sendEmailSync — prints caller thread name and time taken.
 *   3. Calls sendEmailAsync — prints caller thread name and time taken.
 *   4. Prints observations so the difference is visible.
 *
 * WHY CommandLineRunner?
 *   We don't want to touch the Spring Boot main class. Spring Boot
 *   automatically runs all CommandLineRunner beans after startup,
 *   which is a clean, idiomatic way to script startup behavior.
 *
 * WHY CONSTRUCTOR INJECTION?
 *   Spring injects the PROXY of EmailService (not the raw object).
 *   That proxy is what makes @Async work. If we did `new EmailService()`,
 *   we'd bypass the proxy and @Async would be ignored.
 */
@Component
public class AsyncDemoRunner implements CommandLineRunner {

    private final EmailService emailService;

    public AsyncDemoRunner(EmailService emailService) {
        this.emailService = emailService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=== RUNNER THREAD: "
                + Thread.currentThread().getName() + " ===");

        // ---- SYNC CALL ----
        System.out.println("\n--- Calling sendEmailSync (no @Async) ---");
        long t1 = System.currentTimeMillis();
        emailService.sendEmailSync("alice@x.com");
        long t2 = System.currentTimeMillis();
        System.out.println("sendEmailSync returned after "
                + (t2 - t1) + "ms");

        // ---- ASYNC CALL ----
        System.out.println("\n--- Calling sendEmailAsync (@Async) ---");
        long t3 = System.currentTimeMillis();
        emailService.sendEmailAsync("bob@x.com");
        long t4 = System.currentTimeMillis();
        System.out.println("sendEmailAsync returned after "
                + (t4 - t3) + "ms");

        System.out.println("\n--- Caller thread continues immediately ---");
        System.out.println("Runner thread is NOT blocked by sendEmailAsync.");

        // Let the async task finish before JVM exits (demo only)
        Thread.sleep(4000);

        System.out.println("\n=== RUNNER THREAD ENDS ===");
    }
}