package designpattern.creational.singleton;

public class SingletonV4 {

    /*
        ======================================================
        SINGLETON V4 -> DOUBLE CHECKED LOCKING
        ======================================================

        Previous Versions:

        V1:
        Lazy Loading
        NOT thread safe

        V2:
        Eager Loading
        Thread safe
        But object created early

        V3:
        Lazy + Thread safe
        But slow because every call synchronized

        ------------------------------------------------------

        GOAL OF V4:

        We want:

        1. Lazy Loading
        2. Thread Safety
        3. High Performance

        ------------------------------------------------------

        SOLUTION:

        Double Checked Locking

        This is one of MOST IMPORTANT
        singleton implementations.

        VERY COMMON interview question.

     */



    /*
        volatile is VERY IMPORTANT here.

        Why?

        Prevents instruction reordering.

        More details below.
     */
    private static volatile SingletonV4 instance;



    /*
        private constructor

        Prevents external object creation.
     */
    private SingletonV4() {

        System.out.println("Singleton Object Created");
    }



    /*
        Double Checked Locking
     */
    public static SingletonV4 getInstance() {

        /*
            FIRST CHECK

            No locking initially.

            Performance optimization.

            If object already exists,
            threads directly return object.

            No synchronization overhead.
         */
        if(instance == null) {

            /*
                Lock only when object
                does not exist.

                synchronized block uses:

                    SingletonV4.class

                as lock object.
             */
            synchronized (SingletonV4.class) {

                /*
                    SECOND CHECK

                    VERY IMPORTANT.

                    Why second check?

                    Suppose:

                    Thread-1 entered first check.
                    Before creating object...

                    Thread-2 also entered first check.

                    Now both waiting for synchronized block.

                    Thread-1 creates object.

                    Thread-2 enters synchronized block later.

                    Without second check:
                    Thread-2 would create another object.

                    So second check prevents that.
                 */
                if(instance == null) {

                    instance = new SingletonV4();
                }
            }
        }

        return instance;
    }



    public static void main(String[] args) {

        SingletonV4 obj1 = SingletonV4.getInstance();

        SingletonV4 obj2 = SingletonV4.getInstance();



        System.out.println(obj1);

        System.out.println(obj2);

        System.out.println(obj1 == obj2);



        /*
            OUTPUT:

            Singleton Object Created

            designpattern.creational.singleton.SingletonV4@6b884d57
            designpattern.creational.singleton.SingletonV4@6b884d57

            true
         */



        /*
            ======================================================
            WHY CALLED DOUBLE CHECKED LOCKING?
            ======================================================

            Because null check happens TWICE.

            FIRST CHECK:
            before synchronization

            SECOND CHECK:
            inside synchronization

         */



        /*
            ======================================================
            INTERNAL THREAD FLOW
            ======================================================

            Suppose 2 threads come simultaneously.

            --------------------------------------------------
            THREAD-1
            --------------------------------------------------

            instance == null -> true

            enters synchronized block

            again checks:
            instance == null -> true

            creates object

            exits block

            --------------------------------------------------
            THREAD-2
            --------------------------------------------------

            waited outside synchronized block

            enters later

            second check:
            instance == null -> false

            no object created

            existing object returned

         */



        /*
            ======================================================
            MAJOR PERFORMANCE BENEFIT
            ======================================================

            After object creation:

            first check becomes false.

            So synchronized block skipped completely.

            This makes it MUCH faster than V3.

         */



        /*
            ======================================================
            WHY volatile IS IMPORTANT?
            ======================================================

            MOST IMPORTANT CONCEPT.

            VERY COMMON INTERVIEW QUESTION.

            ------------------------------------------------------

            Object creation internally happens in 3 steps:

            STEP-1:
            allocate memory

            STEP-2:
            initialize object

            STEP-3:
            assign reference

            ------------------------------------------------------

            JVM optimization may reorder instructions:

            STEP-1
            STEP-3
            STEP-2

            This is called:
            INSTRUCTION REORDERING

         */



        /*
            ======================================================
            DANGEROUS SCENARIO
            ======================================================

            Thread-1:

            Memory allocated

            Reference assigned

            BUT object initialization not completed yet.

            ------------------------------------------------------

            Thread-2 checks:

                instance != null

            So Thread-2 gets partially created object.

            VERY dangerous problem.

         */



        /*
            volatile prevents:

            instruction reordering

            and guarantees visibility
            across threads.

         */



        /*
            ======================================================
            PERFORMANCE COMPARISON
            ======================================================

            V3:
            synchronized on every call

            V4:
            synchronized only during
            first object creation

         */



        /*
            ======================================================
            REAL WORLD USAGE
            ======================================================

            Used in:
            - high concurrency systems
            - frameworks
            - enterprise applications
            - multithreaded servers

            Because:
            thread safe + fast

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Why called Double Checked Locking?

            Answer:
            Because null check happens twice.



            Q2:
            Why second check needed?

            Answer:
            To prevent multiple object creation
            when multiple threads enter simultaneously.



            Q3:
            Why volatile used?

            Answer:
            To prevent instruction reordering
            and ensure thread visibility.



            Q4:
            What problem occurs without volatile?

            Answer:
            Another thread may get
            partially initialized object.



            Q5:
            Is this version thread safe?

            Answer:
            Yes.



            Q6:
            Is this version lazy loaded?

            Answer:
            Yes.



            Q7:
            Better than synchronized singleton?

            Answer:
            Yes.

            Much better performance.



            Q8:
            Why faster than V3?

            Answer:
            Synchronization happens only once
            during first object creation.



            Q9:
            Is this used in production?

            Answer:
            Yes.



            Q10:
            Which singleton version is most asked in interviews?

            Answer:
            Double Checked Locking Singleton.



            Q11:
            Main concepts involved here?

            Answer:
            - synchronization
            - multithreading
            - volatile
            - instruction reordering
            - JVM memory model

         */

    }

}