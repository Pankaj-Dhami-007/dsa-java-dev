package multithreading.executor_framework;

import java.util.concurrent.CompletableFuture;

import static java.lang.Thread.sleep;

public class allOfAndAnyOfExample {
    static void main() {

        // allOf()
        // allOf() creates a new CompletableFuture that completes when all the given
        // CompletableFuture tasks are completed.
        // Wait for all the given async tasks to complete.
        CompletableFuture<String> firstTask =
                CompletableFuture.supplyAsync(() -> "Task 1");

        CompletableFuture<String> secondTask =
                CompletableFuture.supplyAsync(() -> "Task 2");

        CompletableFuture<String> thirdTask =
                CompletableFuture.supplyAsync(() -> "Task 3");

        CompletableFuture<Void> allTasks =
        CompletableFuture.allOf(
                firstTask,
                secondTask,
                thirdTask
        );

        allTasks.join();
        String firstResult = firstTask.join();
        String secondResult = secondTask.join();
        String thirdResult = thirdTask.join();

        // allOf()
        //→ waits for ALL futures to complete
        //→ returns CompletableFuture<Void>


        // CompletableFuture.anyOf()
        // anyOf() creates a new CompletableFuture that completes when any one of the given
        // CompletableFuture tasks completes.
        // Jo task sabse pehle complete ho, uska result le lo.

        CompletableFuture<String> firstTask1 =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "First Task";
                });

        CompletableFuture<String> secondTask2 =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        sleep(1000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Second Task";
                });

        CompletableFuture<String> thirdTask3 =
                CompletableFuture.supplyAsync(() -> {
                    try {
                        sleep(2000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return "Third Task";
                });

        CompletableFuture<Object> fastestTask =
        CompletableFuture.anyOf(
                firstTask1,
                secondTask2, // return second task , 1 sec
                thirdTask3
        );

        Object result = fastestTask.join();
        System.out.println(result);
        // allOf()
        //→ ALL tasks complete hone ka wait

        // anyOf()
        //→ ANY ONE task complete hote hi complete



    }
}
