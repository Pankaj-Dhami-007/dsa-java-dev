package designpattern.creational.singleton;

public class SingletonV5 {

    /*
        =========================================================
        SINGLETON V5 -> BILL PUGH SINGLETON
        =========================================================

        This is one of BEST practical singleton implementations.

        VERY IMPORTANT for interviews + real projects.

        ---------------------------------------------------------

        Previous Versions:

        V1:
        Lazy loading
        NOT thread safe

        V2:
        Thread safe
        But eager loading

        V3:
        Thread safe + lazy
        But slow

        V4:
        Fast + thread safe + lazy
        But complex

        ---------------------------------------------------------

        GOAL OF V5:

        We want:

        - Lazy Loading
        - Thread Safety
        - High Performance
        - Clean Code
        - No synchronization overhead

        ---------------------------------------------------------

        SOLUTION:

        Use static inner helper class.

        This approach uses:
        JVM class loading mechanism.

     */



    /*
        private constructor

        Outside object creation blocked.
     */
    private SingletonV5() {

        System.out.println("Singleton Object Created");
    }



    /*
        =========================================================
        INNER STATIC HELPER CLASS
        =========================================================

        MOST IMPORTANT CONCEPT.

        This inner class is NOT loaded immediately.

        JVM loads it ONLY when needed.

        Meaning:

        Singleton object gets created only
        when getInstance() is called.

        So:
        Lazy Loading achieved.

     */
    private static class Helper {

        /*
            JVM guarantees:

            static class initialization
            is thread safe.

            So object creation becomes safe automatically.
         */
        private static final SingletonV5 INSTANCE =
                new SingletonV5();

    }



    /*
        Global access point
     */
    public static SingletonV5 getInstance() {

        /*
            First time this method executes:

            Helper class loads into memory.

            During loading:
            static variable initializes.

            Singleton object created.

         */
        return Helper.INSTANCE;
    }



    public static void main(String[] args) {

        /*
            IMPORTANT:

            At this point:

            Outer class loaded
            BUT Helper class NOT loaded yet.

         */



        /*
            First call:

            Helper class loads.

            Object created.
         */
        SingletonV5 obj1 = SingletonV5.getInstance();



        /*
            Existing object returned.
         */
        SingletonV5 obj2 = SingletonV5.getInstance();



        System.out.println(obj1);

        System.out.println(obj2);

        System.out.println(obj1 == obj2);



        /*
            OUTPUT:

            Singleton Object Created

            designpattern.creational.singleton.SingletonV5@6b884d57
            designpattern.creational.singleton.SingletonV5@6b884d57

            true
         */



        /*
            =========================================================
            INTERNAL JVM FLOW
            =========================================================

            STEP-1:
            SingletonV5 class loads

            STEP-2:
            Helper class NOT loaded yet

            STEP-3:
            getInstance() called

            STEP-4:
            JVM loads Helper class

            STEP-5:
            static INSTANCE initializes

            STEP-6:
            object created

         */



        /*
            =========================================================
            WHY THREAD SAFE?
            =========================================================

            JVM guarantees:

            Class initialization happens only once.

            And class loading is internally synchronized.

            Therefore:
            only one singleton object created.

         */



        /*
            =========================================================
            WHY BETTER THAN V4?
            =========================================================

            V4:
            - complex
            - uses volatile
            - synchronization logic

            V5:
            - simple
            - cleaner
            - JVM handles thread safety
            - no explicit synchronization

         */



        /*
            =========================================================
            PERFORMANCE
            =========================================================

            VERY FAST

            Why?

            No synchronized method.

            No synchronized block.

            No repeated locking.

         */



        /*
            =========================================================
            REAL WORLD USAGE
            =========================================================

            Highly preferred in production systems.

            Used where:
            - lazy loading needed
            - multithreading exists
            - high performance needed

         */



        /*
            =========================================================
            REAL LIFE ANALOGY
            =========================================================

            Imagine:

            Emergency backup room.

            Room stays closed.

            Only when emergency occurs:
            room opens and resources initialize.

            Same idea:
            Helper class loads only when needed.

         */



        /*
            =========================================================
            MAJOR ADVANTAGES
            =========================================================

            - thread safe
            - lazy loading
            - high performance
            - clean implementation
            - no volatile needed
            - no synchronization overhead

         */



        /*
            =========================================================
            LIMITATIONS
            =========================================================

            Reflection can still break singleton.

            Serialization can also break it.

            Those problems solved later
            using Enum Singleton.

         */



        /*
            =========================================================
            INTERVIEW QUESTIONS
            =========================================================

            Q1:
            Why called Bill Pugh Singleton?

            Answer:
            Proposed by Bill Pugh using
            static inner helper class.



            Q2:
            Is this thread safe?

            Answer:
            Yes.



            Q3:
            Why thread safe?

            Answer:
            JVM class loading mechanism
            is thread safe.



            Q4:
            Is this lazy loaded?

            Answer:
            Yes.



            Q5:
            Why no synchronization needed?

            Answer:
            JVM internally handles
            class initialization safely.



            Q6:
            Better than Double Checked Locking?

            Answer:
            Usually yes.

            Simpler and cleaner.



            Q7:
            When Helper class loads?

            Answer:
            Only when getInstance() is called.



            Q8:
            Is object created during outer class loading?

            Answer:
            No.



            Q9:
            Main benefit over V4?

            Answer:
            Cleaner implementation without
            volatile and synchronization complexity.



            Q10:
            Is this production ready?

            Answer:
            Yes mostly.

         */



        /*
            =========================================================
            IS SINGLETON DESIGN PATTERN COMPLETE?
            =========================================================

            PARTIALLY.

            Still remaining:

            - Reflection attack
            - Serialization issue
            - Cloning issue
            - Enum Singleton (final safest version)
            - Spring Singleton scope
            - Real enterprise usage

         */

    }

}