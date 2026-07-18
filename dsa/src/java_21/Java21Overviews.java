package java_21;


//Java 21 is an LTS release focused mainly on
//modern concurrency and scalability improvements,
//especially Virtual Threads.


/*
 * ===============================================================
 * JAVA 21 COMPLETE OVERVIEW
 * ===============================================================
 *
 * Java 21 is:
 *
 * -> Long Term Support (LTS)
 *
 * Released:
 * -> September 2023
 *
 *
 * WHY IMPORTANT?
 * ------------------------------------------------
 *
 * Java 21 focuses heavily on:
 *
 * -> concurrency
 * -> scalability
 * -> modern backend development
 * -> cloud-native applications
 *
 *
 * MOST IMPORTANT FEATURE:
 * ------------------------------------------------
 *
 * Virtual Threads
 *
 *
 * WHY COMPANIES MOVING TO JAVA 21?
 * ------------------------------------------------
 *
 * -> better concurrency
 * -> lightweight threads
 * -> better scalability
 * -> modern APIs
 * -> cleaner coding
 * -> improved performance
 *
 *
 * ===============================================================
 * MAIN JAVA 21 FEATURES
 * ===============================================================
 *
 * 1. Virtual Threads
 * 2. Structured Concurrency
 * 3. Scoped Values
 * 4. Record Patterns
 * 5. Pattern Matching for switch
 * 6. Sequenced Collections
 * 7. String Templates (Preview)
 * 8. Foreign Function & Memory API
 * 9. Unnamed Patterns & Variables
 * 10. Performance Improvements
 *
 *
 * ===============================================================
 * MOST IMPORTANT FOR INTERVIEWS
 * ===============================================================
 *
 * HIGH PRIORITY:
 *
 * -> Virtual Threads
 * -> Platform Thread vs Virtual Thread
 * -> Structured Concurrency
 * -> Record Patterns
 *
 *
 * LOW PRIORITY:
 *
 * -> String Templates
 * -> Scoped Values
 * -> FFM API
 */

public class Java21Overviews {

    public static void main(String[] args) throws Exception {

        // =========================================================
        // 1. VIRTUAL THREADS
        // =========================================================

        /*
         * MOST IMPORTANT JAVA 21 FEATURE
         */




        /*
         * BEFORE JAVA 21
         * -------------------------
         *
         * One OS thread per Java thread.
         *
         * Expensive:
         * -> memory
         * -> context switching
         * -> scalability problem
         */




        /*
         * JAVA 21
         * -------------------------
         *
         * Lightweight threads.
         *
         * Millions of threads possible.
         */

        Thread virtualThread =

                Thread.ofVirtual()

                        .start(() -> {

                            System.out.println(
                                    "Virtual Thread Running"
                            );
                        });




        virtualThread.join();




        /*
         * REAL USE CASE
         * -------------------------
         *
         * APIs
         * microservices
         * backend servers
         * high concurrent systems
         */




        // =========================================================
        // 2. PLATFORM THREAD vs VIRTUAL THREAD
        // =========================================================

        /*
         * Platform Thread:
         * -> heavy
         * -> OS thread
         * -> expensive
         */




        /*
         * Virtual Thread:
         * -> lightweight
         * -> managed by JVM
         * -> scalable
         */




        // =========================================================
        // 3. STRUCTURED CONCURRENCY
        // =========================================================

        /*
         * Manage multiple related tasks
         * as single unit.
         */




        /*
         * Improves:
         * -> cancellation
         * -> error handling
         * -> readability
         */




        // =========================================================
        // 4. RECORD PATTERNS
        // =========================================================

        /*
         * Improved record destructuring.
         */

        Person person =
                new Person("Rahul", 25);

        if (person instanceof Person(String name, int age)) {

            System.out.println(name);
            System.out.println(age);
        }




        /*
         * Cleaner extraction from records.
         */


        // =========================================================
        // 5. PATTERN MATCHING FOR SWITCH
        // =========================================================

        Object obj = "Java 21";
        String result = switch (obj) {

            case String s ->

                    "String : " + s;

            case Integer i ->

                    "Integer : " + i;

            default ->

                    "Unknown";
        };



        System.out.println(result);




        // =========================================================
        // 6. SEQUENCED COLLECTIONS
        // =========================================================

        /*
         * Better collection ordering APIs.
         */




        /*
         * New methods:
         * -> getFirst()
         * -> getLast()
         * -> reversed()
         */




        // =========================================================
        // 7. STRING TEMPLATES
        // =========================================================

        /*
         * Cleaner string interpolation.
         *
         * Preview feature.
         */




        /*
         * Similar to:
         * Kotlin
         * JavaScript template strings
         */




        // =========================================================
        // 8. SCOPED VALUES
        // =========================================================

        /*
         * Alternative to ThreadLocal.
         *
         * Better for Virtual Threads.
         */




        // =========================================================
        // 9. FOREIGN FUNCTION & MEMORY API
        // =========================================================

        /*
         * Direct native memory access.
         *
         * Better than JNI.
         */




        // =========================================================
        // 10. PERFORMANCE IMPROVEMENTS
        // =========================================================

        /*
         * Better:
         * -> GC
         * -> concurrency
         * -> startup performance
         * -> scalability
         */




        // =========================================================
        // IMPORTANT INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Most important Java 21 feature?
         *
         * Virtual Threads.
         */




        /*
         * Q2 Why Virtual Threads introduced?
         *
         * Solve scalability problem.
         */




        /*
         * Q3 Difference between Platform Thread
         * and Virtual Thread?
         *
         * Platform:
         * -> OS managed
         * -> heavy
         *
         * Virtual:
         * -> JVM managed
         * -> lightweight
         */




        /*
         * Q4 What problem Virtual Threads solve?
         *
         * Thread scalability.
         */




        /*
         * Q5 Real use cases?
         *
         * APIs
         * microservices
         * backend systems
         */




        /*
         * Q6 What are Record Patterns?
         *
         * Easier record data extraction.
         */




        /*
         * Q7 Why Structured Concurrency useful?
         *
         * Better task management.
         */




        /*
         * Q8 Why Scoped Values introduced?
         *
         * Better alternative to ThreadLocal
         * for Virtual Threads.
         */




        /*
         * Q9 Why Java 21 important?
         *
         * Modern concurrency improvements.
         */




        /*
         * Q10 Java 17 vs Java 21?
         *
         * Java 17:
         * -> language improvements
         *
         * Java 21:
         * -> concurrency revolution
         */
    }
}




// ===============================================================
// RECORD FOR RECORD PATTERN EXAMPLE
// ===============================================================

record Person(
        String name,
        int age
) {

}






/*

// I have explored important Java 21 features like
//Virtual Threads, Structured Concurrency,
//and modern concurrency improvements.
//I understand how Virtual Threads improve scalability
//compared to traditional platform threads.

 */