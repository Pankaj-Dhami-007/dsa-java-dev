package designpattern.creational.singleton;

public class SingletonV2 {

    /*
        ==========================================
        SINGLETON V2 -> EAGER INITIALIZATION
        ==========================================

        In V1:
        Object was created only when needed.

        That is called:
        LAZY INITIALIZATION

        ------------------------------------------

        In this version:
        Object is created IMMEDIATELY
        when class loads into JVM.

        This is called:
        EAGER INITIALIZATION

        ------------------------------------------
        WHY THIS VERSION?
        ------------------------------------------

        Problem in V1:

        It was NOT thread safe.

        Multiple threads could create
        multiple objects simultaneously.

        ------------------------------------------

        In this version:

        JVM itself handles object creation
        during class loading.

        JVM class loading mechanism is thread safe.

        So singleton becomes automatically safe.

     */



    /*
        IMPORTANT:

        Object created immediately
        when class loads.

        No waiting for getInstance() call.

        static:
        -> class-level memory

        final:
        -> reference cannot change
     */
    private static final SingletonV2 instance =
            new SingletonV2();



    /*
        private constructor

        Outside world cannot create object.

        This prevents:

            new SingletonV2();

     */
    private SingletonV2() {

        System.out.println("Singleton Object Created");
    }



    /*
        Global access point

        Simply returns already created object.
     */
    public static SingletonV2 getInstance() {

        return instance;
    }



    public static void main(String[] args) {

        /*
            IMPORTANT FLOW

            BEFORE main() starts:

            JVM loads SingletonV2 class.

            During class loading:
            static variables initialize.

            So object gets created immediately.

         */



        /*
            No new object created here.

            Object already exists.
         */
        SingletonV2 obj1 = SingletonV2.getInstance();

        SingletonV2 obj2 = SingletonV2.getInstance();



        /*
            Both point to same object.
         */
        System.out.println(obj1);

        System.out.println(obj2);

        System.out.println(obj1 == obj2);



        /*
            OUTPUT:

            Singleton Object Created

            designpattern.creational.singleton.SingletonV2@6b884d57
            designpattern.creational.singleton.SingletonV2@6b884d57

            true
         */



        /*
            ==========================================
            INTERNAL JVM FLOW
            ==========================================

            STEP 1:
            JVM loads class.

            STEP 2:
            static variables initialize.

            STEP 3:
            Singleton object created.

            STEP 4:
            getInstance() simply returns object.

         */



        /*
            ==========================================
            WHY THREAD SAFE?
            ==========================================

            JVM class loading process
            is internally synchronized.

            JVM guarantees:
            class loads only once.

            Therefore:
            object also created only once.

         */



        /*
            ==========================================
            MAJOR ADVANTAGE
            ==========================================

            Very simple implementation.

            Automatically thread safe.

         */



        /*
            ==========================================
            MAJOR DISADVANTAGE
            ==========================================

            Memory waste possible.

            Why?

            Object created even if application
            never uses it.

            Example:

            Suppose DBManager object is huge.

            App never calls it.

            Still memory consumed.

         */



        /*
            ==========================================
            REAL WORLD USAGE
            ==========================================

            Good when:

            - object lightweight
            - object always needed
            - startup initialization acceptable

            Example:
            Configuration Manager

            App always needs config,
            so early creation is okay.

         */



        /*
            ==========================================
            V1 vs V2
            ==========================================

            V1:
            Lazy Initialization
            -> object created when needed

            V2:
            Eager Initialization
            -> object created immediately

         */



        /*
            ==========================================
            INTERVIEW QUESTIONS
            ==========================================

            Q1:
            Why called Eager Initialization?

            Answer:
            Because object is created eagerly
            during class loading.



            Q2:
            Is this version thread safe?

            Answer:
            Yes.



            Q3:
            Why thread safe?

            Answer:
            JVM class loading mechanism
            is thread safe.



            Q4:
            Main disadvantage?

            Answer:
            Object created even if never used.



            Q5:
            Difference between V1 and V2?

            Answer:

            V1:
            Lazy loading
            Not thread safe

            V2:
            Eager loading
            Thread safe



            Q6:
            Which consumes more memory?

            Answer:
            V2 can waste memory because
            object created early.



            Q7:
            Which performs faster during access?

            Answer:
            V2

            Because object already exists,
            no null check needed.
         */

    }

}