package java_21.platform_vs_virtual;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/*
 * ===============================================================
 * PLATFORM THREAD vs VIRTUAL THREAD
 * ===============================================================
 *
 * MOST IMPORTANT JAVA 21 INTERVIEW TOPIC
 *
 *
 * WHY IMPORTANT?
 * ------------------------------------------------
 *
 * Interviewers VERY commonly ask:
 *
 * -> Difference between Platform and Virtual Thread
 * -> Internal working
 * -> Scalability
 * -> Memory usage
 * -> Real backend impact
 */

public class PlatformVsVirtualDemo {

    public static void main(String[] args) throws Exception {

        // =========================================================
        // PLATFORM THREAD
        // =========================================================

        Thread platformThread =

                new Thread(() -> {

                    System.out.println(
                            "Platform Thread : "
                                    + Thread.currentThread()
                    );
                });




        platformThread.start();

        platformThread.join();




        /*
         * Traditional Java thread.
         *
         * Backed by:
         * -> real OS thread
         */




        // =========================================================
        // VIRTUAL THREAD
        // =========================================================

        Thread virtualThread =

                Thread.ofVirtual()

                        .start(() -> {

                            System.out.println(
                                    "Virtual Thread : "
                                            + Thread.currentThread()
                            );
                        });




        virtualThread.join();




        /*
         * Lightweight thread.
         *
         * Managed by JVM scheduler.
         */




        // =========================================================
        // EXECUTOR COMPARISON
        // =========================================================

        /*
         * OLD STYLE
         * Platform thread pool
         */

        ExecutorService platformExecutor =

                Executors.newFixedThreadPool(5);




        /*
         * JAVA 21
         * Virtual thread executor
         */

        ExecutorService virtualExecutor =

                Executors.newVirtualThreadPerTaskExecutor();




        virtualExecutor.submit(() -> {

            System.out.println(
                    "Running Virtual Task"
            );

            return null;
        });




        virtualExecutor.close();




        // =========================================================
        // BLOCKING EXAMPLE
        // =========================================================

        /*
         * Simulate API/DB call.
         */

        Thread blockingThread =

                Thread.ofVirtual()

                        .start(() -> {

                            try {

                                System.out.println(
                                        "Before Sleep"
                                );

                                Thread.sleep(3000);

                                System.out.println(
                                        "After Sleep"
                                );

                            } catch (Exception e) {

                                e.printStackTrace();
                            }
                        });




        blockingThread.join();




        /*
         * JVM efficiently parks
         * Virtual Thread during blocking.
         */




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Platform Thread meaning?
         *
         * OS-level thread.
         */




        /*
         * Q2 Virtual Thread meaning?
         *
         * Lightweight JVM-managed thread.
         */




        /*
         * Q3 Main problem with Platform Threads?
         *
         * Expensive scalability.
         */




        /*
         * Q4 Main advantage of Virtual Threads?
         *
         * Massive concurrency.
         */




        /*
         * Q5 Best use case of Virtual Threads?
         *
         * Blocking I/O tasks.
         */




        /*
         * Q6 Do Virtual Threads replace Platform Threads?
         *
         * No.
         *
         * Platform Threads still exist internally.
         */




        /*
         * Q7 Are Virtual Threads suitable
         * for CPU-heavy tasks?
         *
         * Not main benefit.
         */




        /*
         * Q8 Which scheduler handles Virtual Threads?
         *
         * ForkJoinPool scheduler.
         */
    }
}