package com.dhami.java_dev.async.completablefuture;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

/**
 * Service with @Async methods that return CompletableFuture<T>.
 *
 * KEY PATTERN TO OBSERVE:
 *   Every @Async method returns CompletableFuture.completedFuture(result).
 *   We do NOT call CompletableFuture.supplyAsync — because the method
 *   body is already running on a pool thread (Spring's TaskExecutor).
 *   Wrapping in supplyAsync would submit a SECOND task to a different
 *   pool — wasteful.
 *
 * The caller receives a CompletableFuture that is typically already
 * complete (since the body finished before returning the future).
 */
@Service
public class AsyncUserService {

    /**
     * Fetches a user by id — asynchronously.
     *
     * Return type: CompletableFuture<User>.
     *   - Caller can chain, combine, or block.
     *   - The future is completed by the time the method returns.
     *
     * NOTE: We use CompletableFuture.completedFuture(...) because
     * the method is already running on a pool thread. No need to
     * spawn another async task.
     */

    // CompletableFuture<Void>
    //→ represent completion/failure of an operation with no result
    @Async
    public CompletableFuture<User> fetchUser(Long id) {
        System.out.println("[fetchUser] running on "
                + Thread.currentThread().getName());
        simulateLatency(500);
        return CompletableFuture.completedFuture(
                new User(id, "User-" + id)
        );
    }

    /**
     * Fetches orders for a user — asynchronously.
     *
     * Same pattern: method body is already async; return a
     * completed future with the result.
     */
    @Async
    public CompletableFuture<Integer> fetchOrderCount(Long userId) {
        System.out.println("[fetchOrderCount] running on "
                + Thread.currentThread().getName());
        simulateLatency(700);
        return CompletableFuture.completedFuture(3 + (int) (userId % 5));
    }

    /**
     * Fetches loyalty points for a user — asynchronously.
     *
     * Same pattern again.
     */
    @Async
    public CompletableFuture<Long> fetchPoints(Long userId) {
        System.out.println("[fetchPoints] running on "
                + Thread.currentThread().getName());
        simulateLatency(300);
        return CompletableFuture.completedFuture(100 *  userId);
    }

    /**
     * Demonstrates exception handling in @Async + CompletableFuture.
     *
     * If the user id is invalid, we return a FAILED future using
     * CompletableFuture.failedFuture(ex). The caller can observe
     * this via .exceptionally() or .handle().
     *
     * In Java 8, use a helper to construct failed futures since
     * CompletableFuture.failedFuture was added in Java 9.
     */
    @Async
    public CompletableFuture<User> fetchUserOrFail(Long id) {
        System.out.println("[fetchUserOrFail] running on "
                + Thread.currentThread().getName());
        simulateLatency(300);
        if (id == null || id <= 0) {
            return CompletableFuture.failedFuture(
                    new IllegalArgumentException("Invalid id: " + id)
            );
        }
        return CompletableFuture.completedFuture(new User(id, "User-" + id));
    }

    // ---------- helpers ----------

    private static void simulateLatency(long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Simple value object for the demo
    public record User(Long id, String name) { }
}