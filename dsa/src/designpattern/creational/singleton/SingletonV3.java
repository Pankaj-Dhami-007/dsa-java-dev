package designpattern.creational.singleton;

public class SingletonV3 {

    /*
        =================================================
        SINGLETON V3 -> SYNCHRONIZED METHOD SINGLETON
        =================================================

        Previous Versions:

        V1:
        Lazy Initialization
        NOT thread safe

        V2:
        Eager Initialization
        Thread safe
        But object created early.

        -------------------------------------------------

        GOAL OF V3:

        We want BOTH:

        1. Lazy Loading
        2. Thread Safety

        -------------------------------------------------

        SOLUTION:

        synchronize the getInstance() method.

        So only one thread can enter method at a time.

     */



    /*
        static:
        one shared reference for entire JVM
     */
    private static SingletonV3 instance;



    /*
        private constructor

        Prevents external object creation.
     */
    private SingletonV3() {

        System.out.println("Singleton Object Created");
    }



    /*
        synchronized method

        IMPORTANT:

        At one time,
        only ONE thread can enter this method.

        Other threads must wait.
     */
    public static synchronized SingletonV3 getInstance() {

        /*
            Lazy Initialization

            Object created only when needed.
         */
        if(instance == null) {

            instance = new SingletonV3();
        }

        return instance;
    }



    public static void main(String[] args) {

        /*
            First call:

            instance == null
            -> object created
         */
        SingletonV3 obj1 = SingletonV3.getInstance();



        /*
            Second call:

            existing object returned
         */
        SingletonV3 obj2 = SingletonV3.getInstance();



        System.out.println(obj1);

        System.out.println(obj2);

        System.out.println(obj1 == obj2);



        /*
            OUTPUT:

            Singleton Object Created

            designpattern.creational.singleton.SingletonV3@6b884d57
            designpattern.creational.singleton.SingletonV3@6b884d57

            true
         */



        /*
            =================================================
            INTERNAL THREAD FLOW
            =================================================

            Suppose 2 threads come simultaneously.

            Thread-1 enters getInstance()

            Since method is synchronized:
            JVM locks method.

            Thread-2 must wait outside.

            Thread-1 creates object.

            Thread-1 exits method.

            Lock released.

            Thread-2 enters.

            instance already exists,
            so same object returned.

         */



        /*
            =================================================
            WHY THREAD SAFE?
            =================================================

            synchronized keyword ensures:

            only one thread executes method
            at a time.

            So multiple object creation
            becomes impossible.

         */



        /*
            =================================================
            MAJOR ADVANTAGE
            =================================================

            - Thread safe
            - Lazy initialization supported
            - Easy to understand

         */



        /*
            =================================================
            MAJOR PROBLEM
            =================================================

            PERFORMANCE ISSUE

            WHY?

            Every call acquires lock.

            Even after singleton object
            already created.

         */



        /*
            Example:

            Object already exists.

            Still every thread must wait
            for lock.

            This creates unnecessary overhead.

         */



        /*
            =================================================
            REAL LIFE ANALOGY
            =================================================

            Imagine a shop.

            Only one customer allowed inside,
            even for asking small questions.

            Customers wait unnecessarily.

            System becomes slow.

         */



        /*
            =================================================
            PERFORMANCE PROBLEM VISUALIZATION
            =================================================

            Object already created.

            Still:

            Thread-1 -> wait for lock
            Thread-2 -> wait for lock
            Thread-3 -> wait for lock

            Unnecessary synchronization.

         */



        /*
            =================================================
            WHEN THIS VERSION IS USED?
            =================================================

            Small applications

            Low concurrency systems

            Learning purposes

            NOT preferred for
            high-performance systems.

         */



        /*
            =================================================
            IMPORTANT CONCEPT
            =================================================

            synchronized method locks on:

                SingletonV3.class

            Since method is static.

         */



        /*
            =================================================
            INTERVIEW QUESTIONS
            =================================================

            Q1:
            Why use synchronized?

            Answer:
            To make singleton thread safe.



            Q2:
            Is this version thread safe?

            Answer:
            Yes.



            Q3:
            Main problem of this version?

            Answer:
            Performance overhead due to
            unnecessary locking.



            Q4:
            Does every call need synchronization?

            Answer:
            No.

            But synchronized method forces it.



            Q5:
            Is lazy loading supported?

            Answer:
            Yes.



            Q6:
            Better than V1?

            Answer:
            Yes because thread safe.



            Q7:
            Better than V2?

            Answer:
            Depends.

            V2:
            faster access

            V3:
            lazy loading supported



            Q8:
            Why not preferred in high concurrency systems?

            Answer:
            Because every thread waits
            for method lock.



            Q9:
            What lock is used here?

            Answer:
            Class-level lock
            (SingletonV3.class)



            Q10:
            What optimization came after this version?

            Answer:
            Double Checked Locking
            (Singleton V4)

         */

    }

}