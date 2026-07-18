package javaDeepDrive.oopsconcepts.interfaces;

/*

=========================================================
            STATIC METHODS IN INTERFACES
=========================================================

IMPORTANT INTERVIEW QUESTION:
-----------------------------
Can interfaces have static methods?

ANSWER:
--------
YES

From Java 8 onwards,
interfaces can contain static methods.

=========================================================
                WHY INTRODUCED?
=========================================================

To provide utility/helper methods directly
inside interfaces.

Before Java 8:
---------------
Utility methods were usually placed in
separate utility classes.

=========================================================
                EXAMPLE
=========================================================

Collections.sort()

Now interfaces themselves can provide
related utility methods.

=========================================================
*/


public class InterfaceStaticMethod {

    public static void main(String[] args) {



        /*
        STATIC METHOD CALL
        -------------------
        Called using interface name
        */

        Payment1.showRules();



        CreditCardPayment1 Payment1 = new CreditCardPayment1();

        Payment1.pay();



        /*
        INVALID:
        --------
        Payment1.showRules();

        Static methods are NOT inherited
        */
    }
}



/*
=========================================================
                    INTERFACE
=========================================================
*/

interface Payment1 {



    /*
    ABSTRACT METHOD
    */

    void pay();



    /*
    STATIC METHOD
    ----------------
    Belongs to interface itself
    */

    static void showRules() {

        System.out.println("Payment1 Rules:");
        System.out.println("1. Amount must be positive");
        System.out.println("2. Account must be active");
    }
}



/*
=========================================================
            IMPLEMENTATION CLASS
=========================================================
*/

class CreditCardPayment1 implements Payment1 {

    @Override
    public void pay() {

        System.out.println("Payment1 done using Credit Card");
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

Payment1 Rules:
1. Amount must be positive
2. Account must be active

Payment1 done using Credit Card

=========================================================
            IMPORTANT UNDERSTANDING
=========================================================

Interface static methods:
--------------------------
- Belong to interface
- NOT inherited by implementing classes
- Called using interface name

=========================================================
                METHOD CALL
=========================================================

VALID:
-------
Payment1.showRules();

INVALID:
---------
Payment1.showRules();

=========================================================
            STATIC METHOD VS DEFAULT METHOD
=========================================================

STATIC METHOD
--------------
- Belongs to interface
- NOT inherited
- Called using interface name

DEFAULT METHOD
---------------
- Inherited by implementing class
- Can be overridden
- Called using object

=========================================================
                REAL LIFE USE CASE
=========================================================

Suppose interface has:
- validation helpers
- common utility methods
- factory methods

Example:
---------
List.of()
Map.of()

These are static methods in interfaces.

=========================================================
                IMPORTANT RULES
=========================================================

1. Static methods allowed from Java 8

2. Static methods must have body

3. Cannot be abstract

4. Cannot be overridden

5. Called using interface name

=========================================================
                MEMORY UNDERSTANDING
=========================================================

Static methods belong to:
--------------------------
Interface/Class memory

Loaded once when interface loads.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Can interfaces have static methods?
Q2. When were static methods introduced in interfaces?
Q3. Why static methods added in interfaces?
Q4. Are static methods inherited?
Q5. Difference between static and default methods?
Q6. Can static methods be overridden?

=========================================================
                INTERVIEW ANSWER
=========================================================

Static methods were introduced in interfaces
in Java 8 to provide utility/helper methods
inside interfaces.

They belong to the interface itself,
are not inherited, and are called using
the interface name.

=========================================================

*/