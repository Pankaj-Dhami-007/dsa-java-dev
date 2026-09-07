package multithreading.executor_framework;

import java.util.concurrent.CompletableFuture;

public class HandleExample {

    static void main() {

        // handle() processes both the successful result and the exception of a CompletableFuture.
        // Task successful ho ya fail — dono cases ko ek hi jagah handle karo.

        CompletableFuture<Integer> calculationFuture =
        CompletableFuture
                .supplyAsync(() -> 10 / 0)
                .handle((result, exception) -> {
                    if (exception != null) {
                        return 0;
                    }
                    return result;
                });

        // (result, exception)
        // result → task successful hua to result
        // exception → task fail hua to exception


        CompletableFuture<Integer> calculationFuture2 =
        CompletableFuture
                .supplyAsync(() -> 10 + 20)
                .handle((result, exception) -> {
                    if (exception != null) {
                        return 0;
                    }
                    return result * 2;
                });
    }
}
