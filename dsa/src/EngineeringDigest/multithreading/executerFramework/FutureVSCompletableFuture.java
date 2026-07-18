package EngineeringDigest.multithreading.executerFramework;

import java.util.concurrent.*;

/*

========================================================================
                    FUTURE VS COMPLETABLE FUTURE
========================================================================

Future
------
-> Introduced in Java 5
-> Represents result of asynchronous computation.

Main Purpose
------------
Run task in another thread and get result later.

Problems with Future
--------------------
1. Cannot chain tasks
2. Cannot combine multiple async tasks
3. No callback support
4. Mostly blocking using get()
5. Manual thread coordination needed

========================================================================

CompletableFuture
-----------------
-> Introduced in Java 8
-> Implements Future + CompletionStage

Main Purpose
------------
Supports asynchronous programming pipeline.

Advantages
-----------
1. Non-blocking callbacks
2. Task chaining
3. Combine multiple tasks
4. Exception handling
5. Functional programming style
6. Parallel async workflows

========================================================================

*/

public class FutureVSCompletableFuture {

    public static void main(String[] args) throws Exception {

        /*
        ========================================================================
                                FUTURE EXAMPLE
        ========================================================================
         */

        System.out.println("============== FUTURE EXAMPLE ==============");

        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Future<Integer> future = executorService.submit(() -> {

            System.out.println("Future Task Started : " + Thread.currentThread().getName());

            Thread.sleep(3000);

            return 10;
        });

        System.out.println("Main Thread Doing Other Work...");

        /*
        =========================================================================
        get()
        -----
        -> Blocking call
        -> Main thread waits until result available
        =========================================================================
         */

        Integer futureResult = future.get();

        System.out.println("Future Result : " + futureResult);

        executorService.shutdown();

        /*
        ========================================================================
                        COMPLETABLE FUTURE EXAMPLE
        ========================================================================
         */

        System.out.println("\n============== COMPLETABLE FUTURE EXAMPLE ==============");

        CompletableFuture<Integer> completableFuture =
                CompletableFuture.supplyAsync(() -> {

                            System.out.println("CompletableFuture Task Started : "
                                    + Thread.currentThread().getName());

                            sleep(2);

                            return 10;
                        })

                        /*
                        =========================================================
                        thenApply()
                        -----------
                        -> Used to transform result
                        -> Runs after previous stage completes
                        =========================================================
                         */

                        .thenApply(number -> {

                            System.out.println("Multiply By 2");

                            return number * 2;
                        })

                        /*
                        =========================================================
                        Another transformation stage
                        =========================================================
                         */

                        .thenApply(number -> {

                            System.out.println("Add 5");

                            return number + 5;
                        });

        /*
        =========================================================================
        Final Result
        =========================================================================
         */

        Integer completableResult = completableFuture.get();

        System.out.println("CompletableFuture Result : " + completableResult);

        /*
        ========================================================================
                    CALLBACK USING thenAccept()
        ========================================================================
         */

        System.out.println("\n============== CALLBACK EXAMPLE ==============");

        CompletableFuture<Void> callbackFuture =
                CompletableFuture.supplyAsync(() -> {

                            sleep(2);

                            return "Java";
                        })

                        .thenAccept(result -> {

                            System.out.println("Received Result : " + result);
                        });

        callbackFuture.get();

        /*
        ========================================================================
                    COMBINE TWO COMPLETABLE FUTURES
        ========================================================================
         */

        System.out.println("\n============== COMBINE FUTURES ==============");

        CompletableFuture<Integer> future1 =
                CompletableFuture.supplyAsync(() -> {

                    sleep(2);

                    return 10;
                });

        CompletableFuture<Integer> future2 =
                CompletableFuture.supplyAsync(() -> {

                    sleep(2);

                    return 20;
                });

        /*
        =========================================================================
        thenCombine()
        -------------
        -> Combines results of two async tasks
        =========================================================================
         */

        CompletableFuture<Integer> combinedFuture =
                future1.thenCombine(future2, (a, b) -> a + b);

        System.out.println("Combined Result : " + combinedFuture.get());

        /*
        ========================================================================
                        EXCEPTION HANDLING
        ========================================================================
         */

        System.out.println("\n============== EXCEPTION HANDLING ==============");

        CompletableFuture<Integer> exceptionFuture =
                CompletableFuture.supplyAsync(() -> {

                            int x = 10 / 0;

                            return x;
                        })

                        /*
                        =========================================================
                        exceptionally()
                        ----------------
                        -> Handles exception
                        -> Provides fallback value
                        =========================================================
                         */

                        .exceptionally(ex -> {

                            System.out.println("Exception Occurred : " + ex);

                            return 0;
                        });

        System.out.println("Exception Result : " + exceptionFuture.get());

    }

    static void sleep(int seconds) {

        try {

            Thread.sleep(seconds * 1000L);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }
}

/*

========================================================================
                            OUTPUT
========================================================================

============== FUTURE EXAMPLE ==============

Main Thread Doing Other Work...
Future Task Started : pool-1-thread-1
Future Result : 10


============== COMPLETABLE FUTURE EXAMPLE ==============

CompletableFuture Task Started : ForkJoinPool.commonPool-worker-1
Multiply By 2
Add 5
CompletableFuture Result : 25


============== CALLBACK EXAMPLE ==============

Received Result : Java


============== COMBINE FUTURES ==============

Combined Result : 30


============== EXCEPTION HANDLING ==============

Exception Occurred : java.util.concurrent.CompletionException:
java.lang.ArithmeticException: / by zero

Exception Result : 0

========================================================================
                            DIFFERENCE
========================================================================

1. Future
----------
-> Represents async result only
-> Blocking style
-> Cannot chain tasks
-> Cannot combine tasks
-> No callback support
-> Limited exception handling

Example Flow
------------
Task -> Wait -> Result


2. CompletableFuture
--------------------
-> Represents async result
-> Supports async processing pipeline
-> Non-blocking style
-> Supports chaining
-> Supports callbacks
-> Supports combining tasks
-> Powerful exception handling

Example Flow
------------
Task
 ↓
Transform
 ↓
Combine
 ↓
Callback
 ↓
Final Result

========================================================================
                        IMPORTANT METHODS
========================================================================

1. supplyAsync()
----------------
Used when task returns value.

2. runAsync()
-------------
Used when task does not return value.

3. thenApply()
---------------
Transforms result.

4. thenAccept()
----------------
Consumes result.

5. thenRun()
-------------
Runs task after completion without using result.

6. thenCompose()
----------------
Chains dependent async tasks.

7. thenCombine()
----------------
Combines two independent futures.

8. exceptionally()
-------------------
Handles exception.

9. get()
---------
Blocking call to get final result.

========================================================================
                            INTERVIEW POINTS
========================================================================

Q1. Is CompletableFuture thread-safe?
-------------------------------------
Yes.

Q2. Does CompletableFuture use daemon threads?
----------------------------------------------
Usually uses ForkJoinPool.commonPool()

Q3. Can Future be non-blocking?
-------------------------------
Not easily.

Q4. Which is preferred in modern Java?
--------------------------------------
CompletableFuture

Q5. Why CompletableFuture powerful?
-----------------------------------
Because it supports reactive async pipelines.

========================================================================

*/