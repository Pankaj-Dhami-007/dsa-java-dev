package designpattern.creational.singleton;

public class SingletonV1 {

    /*
        ================================
        SINGLETON DESIGN PATTERN
        ================================

        Definition:
        A class that allows only ONE object
        in the entire application.

        Also provides a global access point
        to access that object.

        --------------------------------
        WHY DO WE NEED IT?
        --------------------------------

        Some resources should have only one controller.

        Examples:
        - Database Connection Manager
        - Logger
        - Cache Manager
        - Configuration Manager
        - Thread Pool

        Imagine:

        Every service creates its own DB manager:
            new DBManager()

        Problems:
        - Too many DB connections
        - Memory waste
        - Resource exhaustion
        - Inconsistent state

        Instead:
        One central object controls everything.

        --------------------------------
        REAL LIFE EXAMPLE
        --------------------------------

        President of a country

        Only one president exists at a time.
        Everyone refers to same person.

        Another Example:
        ATM Machine Server

        All ATMs should talk to one central bank server manager.

        --------------------------------
        RULES OF SINGLETON
        --------------------------------

        1. Constructor must be private
           -> Outside world cannot create object

        2. One static object reference
           -> shared globally

        3. Public static method
           -> gives access to object

     */


    /*
        static:
        Belongs to class not object

        Why static here?

        Because we want ONLY ONE copy
        of instance variable for entire JVM.
     */
    private static SingletonV1 instance;



    /*
        private constructor

        IMPORTANT:
        Outside class cannot do:

            new SingletonV1();

        So object creation becomes controlled.
     */
    private SingletonV1() {

        System.out.println("Singleton Object Created");
    }



    /*
        Global access point

        Why static?

        Because initially object does not exist.

        So we need class-level access:

            SingletonV1.getInstance()

        without creating object first.
     */
    public static SingletonV1 getInstance() {

        /*
            LAZY INITIALIZATION

            Object created only when needed.

            Memory efficient.
         */

        if(instance == null) {

            instance = new SingletonV1();
        }

        return instance;
    }



    public static void main(String[] args) {

        /*
            First call:
            instance == null

            So object gets created.
         */
        SingletonV1 obj1 = SingletonV1.getInstance();



        /*
            Second call:
            object already exists.

            Existing object returned.
         */
        SingletonV1 obj2 = SingletonV1.getInstance();



        /*
            Both references point
            to SAME object.
         */
        System.out.println(obj1);

        System.out.println(obj2);



        /*
            == compares memory address
         */
        System.out.println(obj1 == obj2);



        /*
            OUTPUT:

            Singleton Object Created

            designpattern.creational.singleton.SingletonV1@6b884d57
            designpattern.creational.singleton.SingletonV1@6b884d57

            true
         */



        /*
            =====================================
            INTERNAL FLOW
            =====================================

            STEP 1:
            obj1 calls getInstance()

            instance == null
            -> object created

            STEP 2:
            obj2 calls getInstance()

            instance already exists
            -> same object returned

         */



        /*
            =====================================
            MAJOR PROBLEM IN THIS VERSION
            =====================================

            NOT THREAD SAFE

            In multithreading:

            Thread-1 enters:
                if(instance == null)

            Before object creation finishes...

            Thread-2 also enters:
                if(instance == null)

            Both create objects.

            Singleton breaks.

            So this version is unsafe
            in multithreaded applications.
         */



        /*
            =====================================
            INTERVIEW QUESTIONS
            =====================================

            Q1:
            Why constructor is private?

            Answer:
            To stop outside object creation.



            Q2:
            Why instance variable is static?

            Answer:
            Because singleton object belongs
            to class level not object level.



            Q3:
            Why getInstance() is static?

            Answer:
            Because object may not exist initially,
            so access must happen using class name.



            Q4:
            Is this version thread safe?

            Answer:
            No.



            Q5:
            Why called Lazy Initialization?

            Answer:
            Because object is created only when needed.



            Q6:
            Main disadvantage?

            Answer:
            Multiple objects can be created
            in multithreading.
         */

    }

}