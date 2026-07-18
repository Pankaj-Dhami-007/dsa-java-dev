package javaDeepDrive.oopsconcepts.abstraction.abstractclass;

/*

=========================================================
            ABSTRACT CLASS CONSTRUCTOR
=========================================================

IMPORTANT INTERVIEW QUESTION:
-----------------------------
Can abstract class have constructor?

ANSWER:
--------
YES

Abstract classes CAN have constructors.

=========================================================
                WHY?
=========================================================

Even though abstract class object cannot be created,
its constructor is used to initialize parent part
of child object.

=========================================================
                FLOW
=========================================================

When child object is created:

new Dog();

Execution Flow:
----------------
1. Animal constructor
2. Dog constructor

=========================================================
*/


public class AbstractClassConstructor {

    public static void main(String[] args) {

        Dog dog = new Dog();

        dog.sound();
    }
}



/*
=========================================================
                ABSTRACT CLASS
=========================================================
*/

abstract class Animal {

    String type;



    // CONSTRUCTOR
    Animal() {

        type = "Animal";

        System.out.println("Abstract Class Constructor Called");
    }



    // PARAMETERIZED CONSTRUCTOR
    Animal(String type) {

        this.type = type;

        System.out.println("Parameterized Abstract Constructor");
    }



    abstract void sound();



    void displayType() {

        System.out.println("Type : " + type);
    }
}



/*
=========================================================
                    CHILD CLASS
=========================================================
*/

class Dog extends Animal {



    Dog() {

        // super() automatically called

        System.out.println("Dog Constructor Called");
    }



    @Override
    void sound() {

        System.out.println("Dog Barks");

        displayType();
    }
}



/*

=========================================================
                    OUTPUT
=========================================================

Abstract Class Constructor Called
Dog Constructor Called
Dog Barks
Type : Animal

=========================================================
                IMPORTANT UNDERSTANDING
=========================================================

Even though we cannot do:

Animal a = new Animal(); // ERROR

Its constructor still executes when child object
is created.

Reason:
--------
Child object contains parent part also.

=========================================================
                MEMORY VIEW
=========================================================

Dog Object:
--------------------------
| Animal Variables       |
| Dog Variables          |
--------------------------

So Animal constructor initializes Animal part.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. Can abstract class have constructor?
   YES

Q2. Why constructor needed in abstract class?
   To initialize parent state.

Q3. Can we create object of abstract class?
   NO

Q4. Can abstract class have parameterized constructor?
   YES

=========================================================
                IMPORTANT RULE
=========================================================

Constructor of abstract class executes ONLY
through child object creation.

=========================================================
*/