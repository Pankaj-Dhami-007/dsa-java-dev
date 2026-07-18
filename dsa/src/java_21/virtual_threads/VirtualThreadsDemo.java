package java_21.virtual_threads;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


//Virtual Threads are lightweight JVM-managed threads
//introduced to support massive concurrency with minimal resource usage.

//Traditional Spring Boot uses platform threads from Tomcat thread pools.
//With Java 21 and Spring Boot 3.2+, Virtual Threads can be enabled
//using spring.threads.virtual.enabled=true.

// Blocking I/O means:
//
//A thread waits (gets blocked) until some input/output operation finishes.
//
//I/O = Input / Output operations like:
//
//Database call
//File reading
//API request
//Network call
//Socket communication

// During waiting time, the thread cannot do other work.
// String data = database.getData();
//System.out.println(data);
//
//Flow:
//
//Thread calls database
//Database takes maybe 2 seconds
//Thread waits those 2 seconds doing nothing
//After response comes, next line runs
//
//This waiting is called blocking.

/*


In Java traditional threads:

Thread -> Request -> Wait -> Response

If 1000 requests come and all are waiting for DB/API:

1000 OS threads stay occupied
Memory usage increases
Context switching increases
Performance decreases


Non-blocking means:

Instead of waiting, thread becomes free and can do other work.

When result comes, system notifies back.

Example idea:

sendRequest();
continueOtherWork();


Spring Boot old model (Tomcat):

Usually uses blocking I/O + platform(OS) threads

One request = one thread.

If DB call waits:
that thread also waits.


Why virtual threads became important in Java 21:

Virtual threads make blocking code cheaper.

So even if thread waits for DB/API:

it does not heavily consume OS thread resources
JVM parks virtual thread internally

That is why Java 21 says:

"Write simple blocking style code, but efficiently."




Blocking I/O means a thread waits until an input/output operation completes,
during which it cannot perform other tasks.

And:

Traditional threads make blocking expensive, while virtual threads
reduce the cost of blocking operations.
 */

/*
 * ===============================================================
 * JAVA 21 VIRTUAL THREADS
 * ===============================================================
 *
 * MOST IMPORTANT JAVA 21 FEATURE
 *
 *
 * WHY INTRODUCED?
 * ------------------------------------------------
 *
 * Traditional threads are expensive.
 *
 * Problems:
 *
 * -> high memory usage
 * -> OS thread limitation
 * -> expensive context switching
 * -> poor scalability
 *
 *
 * JAVA 21 SOLUTION
 * ------------------------------------------------
 *
 * Virtual Threads
 *
 * Lightweight threads managed by JVM.
 */

public class VirtualThreadsDemo {

    public static void main(String[] args) throws Exception {

        // =========================================================
        // BEFORE JAVA 21
        // PLATFORM THREAD
        // =========================================================

        Thread platformThread =

                new Thread(() -> {

                    System.out.println(
                            "Platform Thread Running"
                    );
                });




        platformThread.start();

        platformThread.join();




        /*
         * Problem:
         *
         * One Java thread
         * = One OS thread
         *
         * Expensive.
         */




        // =========================================================
        // JAVA 21
        // VIRTUAL THREAD
        // =========================================================

        Thread virtualThread =

                Thread.ofVirtual()

                        .start(() -> {

                            System.out.println(
                                    "Virtual Thread Running"
                            );
                        });




        virtualThread.join();




        /*
         * Virtual Thread:
         *
         * -> lightweight
         * -> JVM managed
         * -> scalable
         */




        // =========================================================
        // CREATE MULTIPLE VIRTUAL THREADS
        // =========================================================

        List<Thread> threads =
                new ArrayList<>();



        for (int i = 1; i <= 5; i++) {

            int task = i;



            Thread thread =

                    Thread.ofVirtual()

                            .start(() -> {

                                System.out.println(
                                        "Task : " + task
                                );
                            });




            threads.add(thread);
        }




        for (Thread thread : threads) {

            thread.join();
        }




        // =========================================================
        // EXECUTOR SERVICE WITH VIRTUAL THREADS
        // =========================================================

        ExecutorService executor =

                Executors.newVirtualThreadPerTaskExecutor();




        for (int i = 1; i <= 5; i++) {

            int task = i;



            executor.submit(() -> {

                System.out.println(
                        "Executor Task : " + task
                );

                return null;
            });
        }




        executor.close();




        // =========================================================
        // SIMULATE API CALL
        // =========================================================

        /*
         * Real backend scenario:
         *
         * -> DB call
         * -> API call
         * -> file I/O
         *
         * Virtual Threads are excellent
         * for blocking I/O tasks.
         */

        Thread apiThread =

                Thread.ofVirtual()

                        .start(() -> {

                            try {

                                Thread.sleep(2000);

                                System.out.println(
                                        "API Response Received"
                                );

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        });




        apiThread.join();




        // =========================================================
        // WHY VIRTUAL THREADS IMPORTANT?
        // =========================================================

        /*
         * Traditional threads:
         *
         * 10,000 threads
         * -> huge memory
         * -> system crash possible
         */




        /*
         * Virtual Threads:
         *
         * millions possible
         *
         * lightweight scheduling.
         */




        // =========================================================
        // REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * REST APIs
         */




        /*
         * Each request can use
         * virtual thread.
         */




        /*
         * USE CASE 2
         * Microservices
         */




        /*
         * Multiple blocking API calls.
         */




        /*
         * USE CASE 3
         * Database Queries
         */




        /*
         * DB operations are blocking.
         *
         * Virtual threads handle efficiently.
         */




        /*
         * USE CASE 4
         * Chat Applications
         */




        /*
         * Thousands of users simultaneously.
         */




        /*
         * USE CASE 5
         * File Processing
         */




        /*
         * I/O heavy systems.
         */




        // =========================================================
        // IMPORTANT INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 What are Virtual Threads?
         *
         * Lightweight JVM-managed threads.
         */




        /*
         * Q2 Why introduced?
         *
         * Solve thread scalability problem.
         */




        /*
         * Q3 Difference between Platform Thread
         * and Virtual Thread?
         *
         * Platform:
         * -> OS thread
         * -> expensive
         *
         * Virtual:
         * -> lightweight
         * -> JVM managed
         */




        /*
         * Q4 Are Virtual Threads faster?
         *
         * Not CPU faster.
         *
         * Better scalability.
         */




        /*
         * Q5 Best use case?
         *
         * Blocking I/O operations.
         */




        /*
         * Q6 Can millions of Virtual Threads exist?
         *
         * YES
         */




        /*
         * Q7 Do Virtual Threads replace async programming?
         *
         * In many cases yes.
         */




        /*
         * Q8 Are Virtual Threads daemon threads?
         *
         * YES by default.
         */




        /*
         * Q9 Which scheduler used?
         *
         * ForkJoinPool scheduler.
         */




        /*
         * Q10 Are Virtual Threads good
         * for CPU intensive tasks?
         *
         * Not main benefit.
         *
         * Best for I/O blocking tasks.
         */
    }
}