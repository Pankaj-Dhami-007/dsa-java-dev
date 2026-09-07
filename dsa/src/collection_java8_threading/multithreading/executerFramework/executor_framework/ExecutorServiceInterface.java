package multithreading.executor_framework;

import java.util.concurrent.*;

public class ExecutorServiceInterface {
    static void main() throws ExecutionException, InterruptedException {

        // ThreadPoolExecutor impl
        // Creating ThreadPoolExecutor manually is: complex
        // Simplify creation of:
        //- thread pools
        //- executors
        //- scheduled executors
        // Executors utility class simplify thread pool creation.

        //1. newFixedThreadPool()
        //2. newCachedThreadPool()
        //3. newSingleThreadExecutor()
        //4. newScheduledThreadPool()

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.execute(// no return any result
                ()-> System.out.println("Task 1")
        );

        executorService.execute(
                () -> System.out.println("Task 2")
        );

        executorService.execute(
                () -> System.out.println("Task 3")
        );

        // submit task
        executorService.submit(()-> System.out.println("1-0000"));
        Future<Integer> future = executorService.submit(() -> 10 + 20);
        Integer result = future.get();
        System.out.println(result);

        Callable<Integer> callable = ()-> 10+70;
        Future<Integer> future1 = executorService.submit(callable);
        System.out.println(future1.get());

        // future methods
        // future.get();        // result lo
        //future.isDone();     // task complete hua? true / false
        //future.isCancelled();// cancel hua?
        //future.cancel(true); // task cancel karo


        Future<Integer> future2 = executorService.submit(() -> {
            Thread.sleep(5000);
            return 10 + 20;
        });

        System.out.println("Task submitted");
        Integer result2 = future2.get(); // main thread result ke liye wait karega
        System.out.println(result2);


        Future<Integer> future3 = executorService.submit(() -> {
            Thread.sleep(5000);
            return 10 + 20;
        });
        System.out.println(future3.isDone());
        Thread.sleep(6000);
        System.out.println(future3.isDone());

        // combined them get and isDone
        Future<Integer> future4 = executorService.submit(() -> {
            Thread.sleep(3000);
            return 30;
        });

        while (!future4.isDone()) {
            System.out.println("Task still running...");
            Thread.sleep(500);
        }
        System.out.println("Task completed");
        System.out.println("Result = " + future4.get());


        Future<Integer> cancellableTaskFuture =
        executorService.submit(() -> {
            System.out.println("Long running task started");
            Thread.sleep(10000);
            return 100;
        });
        boolean cancelled = cancellableTaskFuture.cancel(true);
        // true : Agar task already running hai, uske thread ko interrupt karne ki permission/request do.
        System.out.println("Task cancelled? " + cancelled);
        System.out.println("Task cancelled status? " + cancellableTaskFuture.isCancelled());

        executorService.shutdown();
    }
}
