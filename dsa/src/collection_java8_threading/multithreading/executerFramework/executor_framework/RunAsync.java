package multithreading.executor_framework;

import java.util.concurrent.CompletableFuture;

public class RunAsync {
    static void main() {
        // runAsync() runs an asynchronous task that does not return a result.
        // like runnable

        CompletableFuture<Void> taskFuture =
        CompletableFuture.runAsync(() -> {
            System.out.println("Task is running");
        });

        // supplyAsync() → Supplier<T>
        // runAsync()    → Runnable
    }
}
