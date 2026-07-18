package designpattern.creational.singleton;

public class SingletonV6Demo {

    /*
        ==========================================================
        SINGLETON V6 -> ENUM SINGLETON
        ==========================================================

        FINAL and SAFEST singleton implementation.

        Recommended by:
        Joshua Bloch
        (Effective Java Book)

        ----------------------------------------------------------

        Previous Versions Problems:

        V1:
        not thread safe

        V2:
        eager loading

        V3:
        synchronization overhead

        V4:
        complex code

        V5:
        reflection + serialization issues still possible

        ----------------------------------------------------------

        GOAL OF V6:

        Create COMPLETE SAFE singleton.

        Protection against:
        - multithreading
        - reflection
        - serialization

        ----------------------------------------------------------

        SOLUTION:

        Use ENUM.

     */



    public static void main(String[] args) {

        /*
            Access singleton object.

            Enum constants are globally accessible.
         */
        SingletonV6 obj1 = SingletonV6.INSTANCE;

        SingletonV6 obj2 = SingletonV6.INSTANCE;



        obj1.showMessage();



        System.out.println(obj1);

        System.out.println(obj2);

        System.out.println(obj1 == obj2);



        /*
            OUTPUT:

            Singleton Object Created

            Hello From Singleton

            INSTANCE
            INSTANCE

            true
         */



        /*
            ==========================================================
            INTERNAL WORKING
            ==========================================================

            JVM internally creates enum objects.

            Developer cannot manually create them.

            JVM guarantees:

            - only one enum instance
            - thread safety
            - serialization safety

         */



        /*
            ==========================================================
            WHY ENUM SINGLETON IS SPECIAL?
            ==========================================================

            Normal class singleton depends on:

            private constructor

            BUT reflection can bypass private constructor.

            ----------------------------------------------------------

            Enum constructors are internally protected by JVM.

            Reflection CANNOT create enum objects.

         */



        /*
            ==========================================================
            REFLECTION ATTACK PREVENTION
            ==========================================================

            Suppose reflection tries:

            Constructor<SingletonV6> c =
                    SingletonV6.class.getDeclaredConstructor();

            JVM throws exception.

            Because:
            Enum objects cannot be reflectively created.

         */



        /*
            ==========================================================
            SERIALIZATION SAFETY
            ==========================================================

            Normal singleton problem:

            During deserialization,
            JVM may create new object.

            ----------------------------------------------------------

            Enum handles serialization internally.

            JVM always returns same enum instance.

         */



        /*
            ==========================================================
            THREAD SAFETY
            ==========================================================

            Enum initialization happens during
            class loading.

            JVM guarantees thread safety.

         */



        /*
            ==========================================================
            REAL LIFE ANALOGY
            ==========================================================

            Think of:

            Earth

            JVM says:
            only one Earth object exists.

            Nobody can manually create another Earth.

         */



        /*
            ==========================================================
            PERFORMANCE
            ==========================================================

            Very fast.

            No synchronization.

            No locking.

            JVM optimized.

         */



        /*
            ==========================================================
            WHEN TO USE ENUM SINGLETON?
            ==========================================================

            Use when:
            - absolute singleton safety needed
            - serialization involved
            - reflection protection needed

         */



        /*
            ==========================================================
            LIMITATION
            ==========================================================

            Enum cannot extend another class.

            Because:
            Java enums already extend java.lang.Enum

         */



        /*
            ==========================================================
            IMPORTANT DIFFERENCE
            ==========================================================

            Normal Singleton:
            developer controls singleton logic

            Enum Singleton:
            JVM controls singleton behavior

         */



        /*
            ==========================================================
            INTERVIEW QUESTIONS
            ==========================================================

            Q1:
            Which singleton is safest?

            Answer:
            Enum Singleton.



            Q2:
            Who recommended Enum Singleton?

            Answer:
            Joshua Bloch.



            Q3:
            Why reflection cannot break Enum Singleton?

            Answer:
            JVM prevents reflective enum object creation.



            Q4:
            Is Enum Singleton thread safe?

            Answer:
            Yes.



            Q5:
            Is Enum Singleton serialization safe?

            Answer:
            Yes.



            Q6:
            Does Enum Singleton need synchronized?

            Answer:
            No.



            Q7:
            Main advantage over Bill Pugh Singleton?

            Answer:
            Reflection + Serialization safety.



            Q8:
            Can enum extend another class?

            Answer:
            No.



            Q9:
            Is Enum Singleton production ready?

            Answer:
            Yes.



            Q10:
            Which singleton is most recommended today?

            Answer:
            Enum Singleton.

         */



        /*
            ==========================================================
            IS SINGLETON DESIGN PATTERN COMPLETE?
            ==========================================================

            YES — CORE SINGLETON MASTERY COMPLETE.

            You now know:

            - Lazy Singleton
            - Eager Singleton
            - Synchronized Singleton
            - Double Checked Locking
            - Bill Pugh Singleton
            - Enum Singleton

            Advanced concepts covered:
            - multithreading
            - synchronization
            - volatile
            - JVM class loading
            - reflection basics
            - serialization problem

            ----------------------------------------------------------

            STILL POSSIBLE ADVANCED TOPICS:

            - serialization breaking demo
            - reflection attack demo
            - cloning issue
            - singleton in Spring
            - singleton in distributed systems
            - singleton vs static class
            - real enterprise architecture usage

         */

    }

}



/*
    ==========================================================
    ENUM SINGLETON CLASS
    ==========================================================

    Enum constants are implicitly:
    public static final

    JVM creates enum object internally.
 */
enum SingletonV6 {

    INSTANCE;



    /*
        Enum constructor

        Automatically private internally.

        Cannot create enum object manually.
     */
    SingletonV6() {

        System.out.println("Singleton Object Created");
    }



    public void showMessage() {

        System.out.println("Hello From Singleton");
    }

}