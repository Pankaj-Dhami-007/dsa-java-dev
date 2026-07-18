package javaDeepDrive.oopsconcepts.polymorphism.methodhiding;

/*

=========================================================
                    METHOD HIDING
=========================================================

Definition:
Method hiding occurs when a child class defines
a static method with the SAME signature as
a static method in the parent class.

=========================================================
                IMPORTANT POINT
=========================================================

STATIC methods are NOT overridden.

They are hidden.

Because static methods belong to:
- Class
NOT
- Object

=========================================================
                METHOD HIDING
                VS
                METHOD OVERRIDING
=========================================================

Method Hiding:
--------------
- Static methods
- Compile-time binding
- Class reference decides method call

Method Overriding:
------------------
- Non-static methods
- Runtime binding
- Object decides method call

=========================================================
*/


public class MethodHidingConcept {

    public static void main(String[] args) {

        System.out.println("======== NORMAL CALL ========");

        SuperClass.display();

        SubClass.display();



        System.out.println("\n======== REFERENCE CALL ========");

        SuperClass superClass = new SubClass();

        superClass.display();



        System.out.println("\n======== OBJECT CALL ========");

        SubClass subClass = new SubClass();

        subClass.display();
    }
}



/*
=========================================================
                    PARENT CLASS
=========================================================
*/

class SuperClass {

    static void display() {

        System.out.println("Display method in SuperClass");
    }
}



/*
=========================================================
                    CHILD CLASS
=========================================================

This does NOT override display().

This hides parent's static method.

=========================================================
*/

class SubClass extends SuperClass {

    static void display() {

        System.out.println("Display method in SubClass");
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

======== NORMAL CALL ========

Display method in SuperClass
Display method in SubClass


======== REFERENCE CALL ========

Display method in SuperClass


======== OBJECT CALL ========

Display method in SubClass

=========================================================
                MOST IMPORTANT UNDERSTANDING
=========================================================

SuperClass superClass = new SubClass();

superClass.display();

OUTPUT:
--------
Display method in SuperClass

WHY?
-----
Because static methods are resolved using
REFERENCE TYPE, not object type.

Reference Type:
----------------
SuperClass

So SuperClass display() executes.

=========================================================
                STATIC METHOD MEMORY
=========================================================

Static methods belong to class memory.

They are loaded once in Method Area.

No polymorphism happens for static methods.

=========================================================
                REAL OVERRIDING
=========================================================

If method is NON-STATIC:

class Animal {

    void sound() {

    }
}

class Dog extends Animal {

    @Override
    void sound() {

    }
}

Now runtime polymorphism happens.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is method hiding?
Q2. Difference between method hiding and overriding?
Q3. Can static methods be overridden?
Q4. Why static methods cannot be overridden?
Q5. Which binding happens in method hiding?
Q6. Does runtime polymorphism work with static methods?

=========================================================
                IMPORTANT RULES
=========================================================

1. Static methods are inherited
2. Static methods are NOT overridden
3. Child static method hides parent static method
4. Compile-time binding happens
5. Reference type decides method execution

=========================================================
                INTERVIEW ANSWER
=========================================================

Method hiding occurs when a subclass defines
a static method with the same signature as
a static method in the superclass.

The child method hides the parent method.

Static methods are resolved using reference type,
so method hiding uses compile-time binding.

=========================================================

*/