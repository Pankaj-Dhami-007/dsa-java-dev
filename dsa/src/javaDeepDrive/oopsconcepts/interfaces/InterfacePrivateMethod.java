package javaDeepDrive.oopsconcepts.interfaces;

/*

=========================================================
            PRIVATE METHODS IN INTERFACE
=========================================================

IMPORTANT INTERVIEW QUESTION:
-----------------------------
Can interfaces have private methods?

ANSWER:
--------
YES

From Java 9 onwards,
interfaces can contain private methods.

=========================================================
                WHY INTRODUCED?
=========================================================

To avoid duplicate code inside:
- default methods
- static methods

=========================================================
                BEFORE JAVA 9
=========================================================

If multiple default methods had same logic,
code duplication happened.

Example:
---------
default void method1() {

    common code
}

default void method2() {

    same common code
}

=========================================================
                SOLUTION
=========================================================

Java 9 introduced:
-------------------
PRIVATE METHODS IN INTERFACES

Now common logic can be reused internally.

=========================================================
*/


public class InterfacePrivateMethod {

    public static void main(String[] args) {

        Payment payment = new CreditCardPayment();

        payment.pay();

        payment.refund();
    }
}



/*
=========================================================
                    INTERFACE
=========================================================
*/

interface Payment {



    /*
    DEFAULT METHOD
    */

    default void pay() {

        commonValidation();

        System.out.println("Payment Successful");
    }



    /*
    ANOTHER DEFAULT METHOD
    */

    default void refund() {

        commonValidation();

        System.out.println("Refund Successful");
    }



    /*
    PRIVATE METHOD
    ----------------
    Used only inside interface

    Cannot be accessed outside
    */

    private void commonValidation() {

        System.out.println("Validating Transaction");
    }



    /*
    PRIVATE STATIC METHOD
    */

    private static void log() {

        System.out.println("Logging Transaction");
    }



    /*
    STATIC METHOD
    */

    static void printMessage() {

        log();

        System.out.println("Payment Interface");
    }
}



/*
=========================================================
            IMPLEMENTATION CLASS
=========================================================
*/

class CreditCardPayment implements Payment {

}



/*

=========================================================
                    OUTPUT
=========================================================

Validating Transaction
Payment Successful

Validating Transaction
Refund Successful

=========================================================
            IMPORTANT UNDERSTANDING
=========================================================

PRIVATE METHODS:
----------------
- Accessible ONLY inside interface
- Cannot be inherited
- Cannot be overridden

Used for:
----------
- code reusability
- reducing duplication

=========================================================
            PRIVATE STATIC METHOD
=========================================================

Allowed from Java 9.

Used only inside:
- static methods
- other private static methods

=========================================================
                IMPORTANT RULES
=========================================================

1. Private methods are allowed from Java 9

2. Private methods must have body

3. Private methods cannot be abstract

4. Cannot access private methods outside interface

5. Used internally by:
   - default methods
   - static methods

=========================================================
                REAL LIFE USE CASE
=========================================================

Suppose interface has:
- validation logic
- logging logic
- formatting logic

Instead of repeating everywhere,
put it in private method.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Can interfaces have private methods?
Q2. When were private methods introduced?
Q3. Why private methods added in interfaces?
Q4. Can private methods be overridden?
Q5. Difference between default and private methods?
Q6. Can private methods be abstract?

=========================================================
                INTERVIEW ANSWER
=========================================================

Private methods were introduced in interfaces
in Java 9 to reduce code duplication between
default and static methods.

They are accessible only inside the interface
and help in internal code reuse.

=========================================================

*/