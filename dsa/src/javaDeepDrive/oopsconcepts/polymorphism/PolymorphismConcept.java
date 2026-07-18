package javaDeepDrive.oopsconcepts.polymorphism;

/*

=========================================================
                    POLYMORPHISM
=========================================================

Definition:
Polymorphism means:
"One thing, many forms"

In Java, polymorphism allows one object
to behave differently in different situations.

=========================================================
                REAL LIFE EXAMPLE
=========================================================

Person:
--------
- Son at home
- Employee at office
- Friend outside

Same person -> Different behavior

=========================================================
                TYPES OF POLYMORPHISM
=========================================================

1. Compile-Time Polymorphism
   -> Method Overloading
   -> Static Polymorphism

2. Runtime Polymorphism
   -> Method Overriding
   -> Dynamic Polymorphism

=========================================================
*/


public class PolymorphismConcept {

    public static void main(String[] args) {

        System.out.println("======== METHOD OVERLOADING ========");

        Calculator calculator = new Calculator();

        System.out.println(calculator.add(10, 20));

        System.out.println(calculator.add(10, 20, 30));

        System.out.println(calculator.add(10.5, 20.5));



        System.out.println("\n======== METHOD OVERRIDING ========");

        Animal animal = new Dog();

        animal.sound();



        Animal cat = new Cat();

        cat.sound();



        System.out.println("\n======== POLYMORPHIC BEHAVIOR ========");

        Animal[] animals = {
                new Dog(),
                new Cat(),
                new Lion()
        };

        for (Animal a : animals) {

            a.sound();
        }
    }
}



/*
=========================================================
        1. COMPILE-TIME POLYMORPHISM
=========================================================

Method Overloading:
-------------------
Same method name
Different parameters

Decision made at compile time.

=========================================================
*/

class Calculator {

    int add(int a, int b) {

        return a + b;
    }



    int add(int a, int b, int c) {

        return a + b + c;
    }



    double add(double a, double b) {

        return a + b;
    }
}



/*
=========================================================
        2. RUNTIME POLYMORPHISM
=========================================================

Method Overriding:
------------------
Child class provides its own implementation
of parent method.

Decision made at runtime.

=========================================================
*/

class Animal {

    void sound() {

        System.out.println("Animal makes sound");
    }
}



class Dog extends Animal {

    @Override
    void sound() {

        System.out.println("Dog barks");
    }
}



class Cat extends Animal {

    @Override
    void sound() {

        System.out.println("Cat meows");
    }
}



class Lion extends Animal {

    @Override
    void sound() {

        System.out.println("Lion roars");
    }
}



/*

=========================================================
            METHOD OVERLOADING RULES
=========================================================

Must change:
--------------
1. Number of parameters
OR
2. Type of parameters
OR
3. Order of parameters

---------------------------------------------------------
INVALID OVERLOADING
---------------------------------------------------------

Changing only return type is NOT enough.

INVALID:
---------
int add(int a, int b)

double add(int a, int b)

=========================================================
            METHOD OVERRIDING RULES
=========================================================

1. Same method name
2. Same parameters
3. IS-A relationship required
4. Cannot reduce visibility
5. Static methods cannot be overridden
6. Final methods cannot be overridden

=========================================================
            COMPILE TIME VS RUNTIME
=========================================================

COMPILE-TIME POLYMORPHISM
-------------------------
- Method Overloading
- Faster
- Early Binding

RUNTIME POLYMORPHISM
-------------------------
- Method Overriding
- Dynamic Binding
- More flexible

=========================================================
                UPCASTING
=========================================================

Animal animal = new Dog();

Reference Type:
---------------
Animal

Object Type:
------------
Dog

Compile Time:
-------------
Checks Animal methods

Runtime:
---------
Executes Dog overridden methods

=========================================================
                DYNAMIC METHOD DISPATCH
=========================================================

Java decides overridden method at runtime
based on actual object type.

Example:
---------
Animal a = new Dog();

a.sound();

Dog sound() executes.

=========================================================
                INTERVIEW QUESTIONS
=========================================================

Q1. What is polymorphism?
Q2. Types of polymorphism?
Q3. Difference between overloading and overriding?
Q4. What is compile-time polymorphism?
Q5. What is runtime polymorphism?
Q6. Can static methods be overridden?
Q7. Can private methods be overridden?
Q8. What is dynamic method dispatch?
Q9. Why runtime polymorphism important?
Q10. What is upcasting?

=========================================================
                INTERVIEW ANSWER
=========================================================

Polymorphism means one object behaving in
multiple forms.

In Java, it is achieved using:
1. Method Overloading
2. Method Overriding

Compile-time polymorphism is achieved using
method overloading, while runtime polymorphism
is achieved using method overriding.

=========================================================

*/