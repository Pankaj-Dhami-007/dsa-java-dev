package java_21.structured_concurrency;

import java.util.concurrent.StructuredTaskScope;

/*
 * ===============================================================
 * JAVA 21 STRUCTURED CONCURRENCY
 * ===============================================================
 *
 * PURPOSE:
 * ------------------------------------------------
 *
 * Manage multiple related concurrent tasks
 * as a single unit.
 *
 *
 * WHY INTRODUCED?
 * ------------------------------------------------
 *
 * Traditional multithreading problems:
 *
 * -> difficult cancellation
 * -> difficult error handling
 * -> thread leaks
 * -> hard readability
 * -> child tasks unmanaged
 *
 *
 * JAVA 21 SOLUTION
 * ------------------------------------------------
 *
 * Structured Concurrency
 */

public class StructuredConcurrencyDemo {

    public static void main(String[] args) throws Exception {

        // =========================================================
        // REAL PROJECT SCENARIO
        // =========================================================

        /*
         * Example:
         *
         * API request needs:
         *
         * -> user data
         * -> order data
         * -> payment data
         *
         * All can run concurrently.
         */




        // =========================================================
        // STRUCTURED TASK SCOPE
        // =========================================================

        try (var scope =

                     new StructuredTaskScope.ShutdownOnFailure()) {




            // =====================================================
            // TASK 1
            // =====================================================

            StructuredTaskScope.Subtask<String> userTask =

                    scope.fork(() -> {

                        Thread.sleep(1000);

                        return "User Data";
                    });




            // =====================================================
            // TASK 2
            // =====================================================

            StructuredTaskScope.Subtask<String> orderTask =

                    scope.fork(() -> {

                        Thread.sleep(1500);

                        return "Order Data";
                    });




            // =====================================================
            // TASK 3
            // =====================================================

            StructuredTaskScope.Subtask<String> paymentTask =

                    scope.fork(() -> {

                        Thread.sleep(2000);

                        return "Payment Data";
                    });




            // =====================================================
            // WAIT FOR ALL TASKS
            // =====================================================

            scope.join();




            /*
             * If any task fails:
             *
             * remaining tasks cancelled.
             */




            // =====================================================
            // THROW FAILURE IF EXISTS
            // =====================================================

            scope.throwIfFailed();




            // =====================================================
            // GET RESULTS
            // =====================================================

            System.out.println(userTask.get());

            System.out.println(orderTask.get());

            System.out.println(paymentTask.get());
        }




        // =========================================================
        // WHY IMPORTANT?
        // =========================================================

        /*
         * Parent task controls child tasks.
         */




        /*
         * Easier:
         *
         * -> cancellation
         * -> cleanup
         * -> exception handling
         * -> readability
         */




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 What is Structured Concurrency?
         *
         * Managing related concurrent tasks
         * as single unit.
         */




        /*
         * Q2 Why introduced?
         *
         * Better thread management.
         */




        /*
         * Q3 Main benefit?
         *
         * Better cancellation and error handling.
         */




        /*
         * Q4 Real use case?
         *
         * Multiple API calls.
         */




        /*
         * Q5 What happens if one task fails?
         *
         * Remaining tasks can be cancelled.
         */




        /*
         * Q6 Why useful with Virtual Threads?
         *
         * Simplifies concurrent task management.
         */
    }
}