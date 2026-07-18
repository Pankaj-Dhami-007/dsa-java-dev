package javaDeepDrive.oopsconcepts.inheritance.tricky;

/*

=========================================================
                    TRICKY OOP QUESTIONS
=========================================================

MOST IMPORTANT INTERVIEW CONCEPT:
----------------------------------
1. Reference Type
2. Object Type
3. Compile Time
4. Runtime
5. Accessibility
6. Method Overriding
7. Variable Hiding
8. Static Method Hiding

=========================================================
                GOLDEN RULE
=========================================================

ACCESSIBILITY:
---------------
Reference Type decides.

EXECUTION:
------------
Object Type decides ONLY for overridden methods.

VARIABLES:
------------
Reference Type decides.

STATIC METHODS:
----------------
Reference Type decides.

=========================================================
*/


public class TrickyQ {

    public static void main(String[] args) {



        /*
        =================================================
                    CASE 1
                Parent Object
        =================================================
        */

        System.out.println("======== CASE 1 ========");

        Parent parent = new Parent();

        System.out.println(parent.name);

        parent.show();

        parent.staticMethod();



        /*
        =================================================
                    CASE 2
                Child Object
        =================================================
        */

        System.out.println("\n======== CASE 2 ========");

        Child child = new Child();

        System.out.println(child.name);

        System.out.println(child.childName);

        child.show();

        child.staticMethod();

        child.childMethod();



        /*
        =================================================
                    CASE 3
                UPCASTING
        =================================================
        */

        System.out.println("\n======== CASE 3 ========");

        Parent p = new Child();



        /*
        =================================================
                    ACCESSIBILITY
        =================================================
        */

        // ACCESSIBLE
        System.out.println(p.name);

        p.show();

        p.staticMethod();



        /*
        NOT ACCESSIBLE
        ----------------

        System.out.println(p.childName);

        p.childMethod();

        COMPILE TIME ERROR

        Because reference type is Parent
        */



        /*
        =================================================
                    DOWNCASTING
        =================================================
        */

        System.out.println("\n======== CASE 4 ========");

        Child c = (Child) p;

        System.out.println(c.name);

        System.out.println(c.childName);

        c.show();

        c.staticMethod();

        c.childMethod();
    }
}



/*
=========================================================
                    PARENT CLASS
=========================================================
*/

class Parent {

    String name = "Parent Variable";



    static void staticMethod() {

        System.out.println("Parent Static Method");
    }



    void show() {

        System.out.println("Parent Show Method");
    }
}



/*
=========================================================
                    CHILD CLASS
=========================================================
*/

class Child extends Parent {

    String name = "Child Variable";

    String childName = "Child Special Variable";



    static void staticMethod() {

        System.out.println("Child Static Method");
    }



    @Override
    void show() {

        System.out.println("Child Show Method");
    }



    void childMethod() {

        System.out.println("Child Method");
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

======== CASE 1 ========

Parent Variable
Parent Show Method
Parent Static Method


======== CASE 2 ========

Child Variable
Child Special Variable
Child Show Method
Child Static Method
Child Method


======== CASE 3 ========

Parent Variable
Child Show Method
Parent Static Method


======== CASE 4 ========

Child Variable
Child Special Variable
Child Show Method
Child Static Method
Child Method

=========================================================
            MOST IMPORTANT UNDERSTANDING
=========================================================

CASE 3:
--------
Parent p = new Child();

---------------------------------------------------------
REFERENCE TYPE
---------------------------------------------------------
Parent

---------------------------------------------------------
OBJECT TYPE
---------------------------------------------------------
Child

=========================================================
                VARIABLE ACCESS
=========================================================

System.out.println(p.name);

OUTPUT:
--------
Parent Variable

WHY?
-----
Variables use REFERENCE TYPE.

No runtime polymorphism for variables.

=========================================================
                METHOD OVERRIDING
=========================================================

p.show();

OUTPUT:
--------
Child Show Method

WHY?
-----
Overridden methods use OBJECT TYPE.

Runtime polymorphism happens.

=========================================================
                STATIC METHOD
=========================================================

p.staticMethod();

OUTPUT:
--------
Parent Static Method

WHY?
-----
Static methods use REFERENCE TYPE.

No runtime polymorphism.

=========================================================
                ACCESSIBILITY
=========================================================

p.childMethod();

ERROR

WHY?
-----
Compiler checks REFERENCE TYPE.

Parent class does not contain childMethod().

=========================================================
                DOWNCASTING
=========================================================

Child c = (Child) p;

Now Child-specific members accessible.

=========================================================
                MASTER TABLE
=========================================================

| Member Type       | Decided By        |
|-------------------|-------------------|
| Variable          | Reference Type    |
| Static Method     | Reference Type    |
| Overridden Method | Object Type       |

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is upcasting?
Q2. What is downcasting?
Q3. Difference between reference type and object type?
Q4. Why variables don't support polymorphism?
Q5. Why static methods don't override?
Q6. Why overridden methods use runtime binding?
Q7. What determines accessibility?
Q8. What determines execution?

=========================================================
                FINAL INTERVIEW ANSWER
=========================================================

In Java:

1. Reference type determines accessibility.
2. Variables and static methods use compile-time binding.
3. Overridden instance methods use runtime polymorphism.
4. Object type decides overridden method execution.

=========================================================

*/