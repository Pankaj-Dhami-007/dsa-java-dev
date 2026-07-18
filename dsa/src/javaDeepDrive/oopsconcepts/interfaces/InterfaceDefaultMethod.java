package javaDeepDrive.oopsconcepts.interfaces;

/*

=========================================================
            DEFAULT METHODS IN INTERFACE
=========================================================

IMPORTANT INTERVIEW QUESTION:
-----------------------------
Why default methods are allowed in interfaces?
OR
In which situations do we use default methods?

=========================================================
                BEFORE JAVA 8
=========================================================

Before Java 8:
---------------
Interfaces could contain ONLY:
- abstract methods
- public static final variables

Example:
---------
interface Animal {

    void sound();
}

=========================================================
                PROBLEM BEFORE JAVA 8
=========================================================

Suppose 100 classes implement an interface.

Example:
---------
class Dog implements Animal
class Cat implements Animal
class Lion implements Animal

Now later if we add new method:
void eat();
Then ALL 100 classes must implement it.
Otherwise compilation error occurs.

=========================================================
                SOLUTION
=========================================================

Java 8 introduced:
-------------------
DEFAULT METHODS
Default methods allow method implementation
inside interface.

=========================================================
                WHY DEFAULT METHODS?
=========================================================

1. Backward Compatibility
--------------------------
Old classes should not break.

2. Add New Features
--------------------
Interfaces can evolve safely.

3. Common Logic Reuse
----------------------
Provide common implementation to all classes.

4. Reduce Boilerplate Code
---------------------------
No need to implement same code everywhere.

=========================================================
*/


public class InterfaceDefaultMethod {

    public static void main(String[] args) {

        Dog dog = new Dog();

        dog.sound();

        dog.sleep();



        Cat cat = new Cat();

        cat.sound();

        cat.sleep();
    }
}



/*
=========================================================
                INTERFACE
=========================================================
*/

interface Animal {

    // ABSTRACT METHOD
    void sound();



    /*
    DEFAULT METHOD
    ----------------
    Has implementation inside interface
    */

    default void sleep() {

        System.out.println("Animal is sleeping");
    }
}



/*
=========================================================
                IMPLEMENTATION CLASS
=========================================================
*/

class Dog implements Animal {

    @Override
    public void sound() {

        System.out.println("Dog barks");
    }
}



class Cat implements Animal {

    @Override
    public void sound() {

        System.out.println("Cat meows");
    }



    /*
    Child class may override default method
    */

    @Override
    public void sleep() {

        System.out.println("Cat sleeps on sofa");
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

Dog barks
Animal is sleeping

Cat meows
Cat sleeps on sofa

=========================================================
        REAL SITUATION WHERE USED
=========================================================

Suppose Java developers added new feature
in Collection interface.

If default methods did not exist:
----------------------------------
ALL existing collection classes would break.

Example:
---------
ArrayList
LinkedList
HashSet
Vector

So Java added:
---------------
default methods

Example:
---------
forEach()
stream()

=========================================================
                REAL LIFE ANALOGY
=========================================================

Interface:
-----------
Vehicle

Default Method:
---------------
startEngine()

Most vehicles use same default behavior.

But specific vehicles may override it.

=========================================================
            IMPORTANT INTERVIEW POINTS
=========================================================

1. Default methods introduced in Java 8

2. Used for backward compatibility

3. Interface can have implementation now

4. Implementing class can override default method

5. Multiple inheritance conflict possible

=========================================================
        MULTIPLE INTERFACE CONFLICT
=========================================================

If two interfaces have same default method,
implementing class MUST override it.

Example:
---------

interface A {

    default void show() {

    }
}

interface B {

    default void show() {

    }
}

class Test implements A, B {

    @Override
    public void show() {

        System.out.println("Resolved Conflict");
    }
}

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Why default methods introduced?
Q2. What problem do default methods solve?
Q3. Can interface have implemented methods?
Q4. Can default methods be overridden?
Q5. Difference between abstract and default methods?
Q6. What happens if two interfaces have same default method?

=========================================================
                INTERVIEW ANSWER
=========================================================

Default methods were introduced in Java 8
to provide backward compatibility.

They allow interfaces to contain method
implementation without breaking existing
implementation classes when new methods
are added to interfaces.

=========================================================

*/