package ooptrickyquestions.constructorflow;

public class ConstructorAndInitializationFlow {

    /*
        ==========================================================
        CONSTRUCTOR + OBJECT INITIALIZATION FLOW
        ==========================================================

        MOST IMPORTANT OOP TRICKY TOPIC.

        EXTREMELY heavily asked in:
        - Java interviews
        - backend interviews
        - Spring interviews
        - experienced developer interviews

     */



    /*
        ==========================================================
        MOST IMPORTANT UNDERSTANDING
        ==========================================================

        Object creation is NOT simple.

        When object created,
        many things happen internally.

     */



    /*
        ==========================================================
        MAIN FLOW
        ==========================================================

        Whenever object created:

        1. Static variables
        2. Static blocks
        3. Parent static
        4. Child static

        Object creation starts:

        5. Parent instance variables
        6. Parent instance blocks
        7. Parent constructor

        8. Child instance variables
        9. Child instance blocks
        10. Child constructor

     */



    /*
        ==========================================================
        MOST ASKED INTERVIEW QUESTION
        ==========================================================

        Q:
        What executes first:
            constructor or instance block?

        Answer:
            instance block first.

     */



    public static void main(String[] args) {

        /*
            ======================================================
            OBJECT CREATION
            ======================================================
         */

        Child child =
                new Child();



        /*
            ======================================================
            EXPECTED OUTPUT
            ======================================================

            Parent Static Variable

            Parent Static Block

            Child Static Variable

            Child Static Block

            Main Method Started

            Parent Instance Variable

            Parent Instance Block

            Parent Constructor

            Child Instance Variable

            Child Instance Block

            Child Constructor

         */

    }

}



/*
    ==========================================================
    PARENT CLASS
    ==========================================================
 */
class Parent {

    /*
        ======================================================
        STATIC VARIABLE
        ======================================================
     */
    static int parentStaticVariable =
            print(
                    "Parent Static Variable"
            );



    /*
        ======================================================
        STATIC BLOCK
        ======================================================
     */
    static {

        System.out.println(
                "Parent Static Block"
        );
    }



    /*
        ======================================================
        INSTANCE VARIABLE
        ======================================================
     */
    int parentInstanceVariable =
            print(
                    "Parent Instance Variable"
            );



    /*
        ======================================================
        INSTANCE BLOCK
        ======================================================
     */
    {

        System.out.println(
                "Parent Instance Block"
        );
    }



    /*
        ======================================================
        CONSTRUCTOR
        ======================================================
     */
    public Parent() {

        System.out.println(
                "Parent Constructor"
        );
    }



    /*
        Helper method.
     */
    static int print(String message) {

        System.out.println(message);

        return 10;
    }

}



/*
    ==========================================================
    CHILD CLASS
    ==========================================================
 */
class Child extends Parent {

    /*
        ======================================================
        STATIC VARIABLE
        ======================================================
     */
    static int childStaticVariable =
            print(
                    "Child Static Variable"
            );



    /*
        ======================================================
        STATIC BLOCK
        ======================================================
     */
    static {

        System.out.println(
                "Child Static Block"
        );
    }



    /*
        ======================================================
        INSTANCE VARIABLE
        ======================================================
     */
    int childInstanceVariable =
            print(
                    "Child Instance Variable"
            );



    /*
        ======================================================
        INSTANCE BLOCK
        ======================================================
     */
    {

        System.out.println(
                "Child Instance Block"
        );
    }



    /*
        ======================================================
        CONSTRUCTOR
        ======================================================
     */
    public Child() {

        System.out.println(
                "Child Constructor"
        );
    }

}

/*

CLASS LOADING TIME:
-------------------

1. Parent static variables
2. Parent static blocks

3. Child static variables
4. Child static blocks

OBJECT CREATION TIME:
---------------------

5. Parent instance variables
6. Parent instance blocks
7. Parent constructor

8. Child instance variables
9. Child instance blocks
10. Child constructor

 */