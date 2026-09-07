package multithreading.executor_framework;

import java.util.concurrent.CompletableFuture;

public class ExceptionallyExample {

    static void main() {

        // exceptionally() handles an exception that occurs during a
        // CompletableFuture pipeline and provides an alternative result.

        // Agar async task fail ho jaye, to error handle karke fallback value de sakte ho.

        // without exception handle
        CompletableFuture<Integer> calculationFuture =
        CompletableFuture.supplyAsync(() -> {
            return 10 / 0;
        });

        // with fallback

        CompletableFuture<Integer> calculationFutureWithFallback =
        CompletableFuture
                .supplyAsync(() -> 10 / 0)
                .exceptionally(exception -> {
                    System.out.println(exception.getMessage());
                    return 0; // return type should be same as future
                });

        Integer result = calculationFutureWithFallback.join();
        System.out.println(result);
    }
}
