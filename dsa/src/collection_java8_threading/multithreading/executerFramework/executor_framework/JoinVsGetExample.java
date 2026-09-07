package multithreading.executor_framework;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class JoinVsGetExample {
    static void main() throws ExecutionException, InterruptedException {
        // CompletableFuture ka result lena.

        CompletableFuture<Integer> calculationFuture =
                CompletableFuture.supplyAsync(() -> 10 + 20);
        Integer result = calculationFuture.get();// get() checked exceptions throw
        System.out.println(result);

        CompletableFuture<Integer> calculationFuture1 =
                CompletableFuture.supplyAsync(() -> 10 + 20);
        Integer result1 = calculationFuture.join(); // if fail then CompletionException
        System.out.println(result);
    }

    // get()
    //→ checked exception
    //→ try/catch ya throws required

    // join()
    //→ unchecked CompletionException
    //→ try/catch compulsory nahi
}
