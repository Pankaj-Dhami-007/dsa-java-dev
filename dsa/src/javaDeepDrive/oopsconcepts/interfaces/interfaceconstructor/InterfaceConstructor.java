package javaDeepDrive.oopsconcepts.interfaces.interfaceconstructor;

/*

=========================================================
            WHY INTERFACE CANNOT HAVE CONSTRUCTOR?
=========================================================

IMPORTANT INTERVIEW QUESTION
=========================================================

QUESTION:
---------
Why can't an interface have a constructor?

=========================================================
                SHORT ANSWER
=========================================================

Interfaces cannot have constructors because:

1. Interfaces cannot create objects
2. Constructors are used for object initialization
3. Interfaces contain contract, not object state

=========================================================
                VERY IMPORTANT
=========================================================

CONSTRUCTOR PURPOSE:
---------------------
Constructors initialize object state.

Example:
---------
- initialize variables
- allocate object data

But interface:
---------------
- cannot create object
- has no instance state
- has no instance variables

So constructor makes NO sense.

=========================================================
                BEFORE UNDERSTANDING
=========================================================

Constructor belongs to:
------------------------
OBJECT CREATION

Interface belongs to:
----------------------
CONTRACT / RULES

=========================================================
*/


public class InterfaceConstructor {

    public static void main(String[] args) {



        /*
        =================================================
                    VALID
        =================================================
        */

        Shape shape = new Circle();

        shape.draw();



        /*
        =================================================
                    INVALID
        =================================================

        Shape s = new Shape();

        ERROR:
        ------
        Cannot instantiate interface

        =================================================
        */
    }
}



/*
=========================================================
                    INTERFACE
=========================================================

Interface only defines:
-------------------------
- behavior contract
- method declarations

No object creation logic.

=========================================================
*/

interface Shape {

    void draw();
}



/*
=========================================================
                IMPLEMENTATION CLASS
=========================================================
*/

class Circle implements Shape {

    /*
    Constructor belongs to CLASS
    because class objects can be created.
    */

    Circle() {

        System.out.println("Circle Constructor Called");
    }



    @Override
    public void draw() {

        System.out.println("Drawing Circle");
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

Circle Constructor Called
Drawing Circle

=========================================================
                IMPORTANT UNDERSTANDING
=========================================================

This object creation:

new Circle();

calls:
---------
Circle constructor

NOT:
-----
Shape constructor

Because interfaces do not have constructors.

=========================================================
            WHY ABSTRACT CLASS CAN HAVE
            CONSTRUCTOR BUT INTERFACE CANNOT?
=========================================================

ABSTRACT CLASS:
----------------
- Can contain instance variables
- Has object state
- Parent part exists inside child object

So constructor useful.

---------------------------------------------------------

INTERFACE:
------------
- No instance variables
- No object state
- No object creation

So constructor useless.

=========================================================
                INTERNAL JVM THINKING
=========================================================

JVM creates:
-------------
OBJECTS OF CLASSES

NOT:
-----
OBJECTS OF INTERFACES

So JVM never needs interface constructor.

=========================================================
                REAL LIFE ANALOGY
=========================================================

Interface:
------------
Blueprint / Rules

Constructor:
--------------
Building initialization process

Rules themselves cannot be initialized.

Only actual building can be initialized.

=========================================================
                IMPORTANT RULES
=========================================================

1. Interface cannot have constructor

2. Interface cannot create objects

3. Interface variables are:
   public static final

4. Interface methods define behavior contract

5. Constructors belong only to classes

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Why interface cannot have constructor?
Q2. Can interface create object?
Q3. Difference between abstract class and interface?
Q4. Why abstract class can have constructor?
Q5. What is purpose of constructor?
Q6. Does interface contain object state?

=========================================================
                INTERVIEW ANSWER
=========================================================

Interfaces cannot have constructors because
constructors are used for object initialization,
and interfaces cannot be instantiated.

Interfaces define behavior contracts,
not object state or object creation logic.

=========================================================

*/