package multithreading.executor_framework;

import java.util.concurrent.*;

public class CompletableFutureExample {

    static void main() throws ExecutionException, InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(3);
        Future<Integer> future = executorService.submit(() -> 10 + 30);
        // Integer result = future.get(); here wait main thread


                CompletableFuture<Integer> completableFuture =
                CompletableFuture
                .supplyAsync(() ->{
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return 10 + 90;
                })
                .thenApply(res -> res *2);

               while (!completableFuture.isDone()){
                   Thread.sleep(1000);
                   System.out.println(" task is Running ....");
               }
               System.out.println(completableFuture.get());

//        CompletableFuture<Integer> completableFuture =
                CompletableFuture
                .supplyAsync(() -> 10 + 90)
                .thenApply(res -> res *2)
                .thenAccept(res -> System.out.println(res));
        // System.out.println(completableFuture.get());

        CompletableFuture
        .supplyAsync(() -> "pankaj")
        .thenApply(name -> name.toUpperCase())// thenApply() result ko transform karke naya result deta hai:
        .thenAccept(name ->
                System.out.println("Hello " + name)
        );// thenAccept() result consume karta hai, kuch return nahi karta:


        CompletableFuture
        .supplyAsync(() -> "Pankaj")
        .thenRun(() -> {
            System.out.println("Next task running");
        });// Previous task complete hone ke baad koi next kaam run karna hai


        // thenCompose() ka main kaam hai ek async task ke baad doosra async task chalana.
        CompletableFuture<Integer> firstTask =
                CompletableFuture.supplyAsync(() -> 10);

        CompletableFuture<Integer> finalResult =
                firstTask.thenCompose(number ->
                        CompletableFuture.supplyAsync(() -> number * 2)
                );
        // first task -> 10 -> thenCompose()-> 2nd async task -> 20
        // thenApply() -> return normal value , value → value
        // thenCompose() -> return CompletableFuture , value → CompletableFuture<value>


        CompletableFuture<Integer> result =
        CompletableFuture
                .supplyAsync(() -> 10)
                .thenCompose(number ->
                        CompletableFuture.supplyAsync(
                                () -> number * 2
                        )
                );

        // thenCombine()
        // Jab tumhare paas 2 independent CompletableFuture hain aur dono ke result ko
        // mila kar ek result banana hai.

        CompletableFuture<Integer> firstNumber =
                CompletableFuture.supplyAsync(() -> 10);

        CompletableFuture<Integer> secondNumber =
                CompletableFuture.supplyAsync(() -> 20);

        CompletableFuture<Integer> total =
                firstNumber.thenCombine(
                        secondNumber,
                        (firstResult, secondResult) ->
                                firstResult + secondResult
                );
        // thenCompose(): Sequential dependency, ek ke baad doosra async task
        // thenCombine(): both independent, do async tasks ke results combine

    }
}
