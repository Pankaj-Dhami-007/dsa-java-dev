package javaDeepDrive.oopsconcepts.inheritance;

public class DifferenceBetweenInheritanceandComposition {

    public static void main(String[] args) {

        System.out.println("======== INHERITANCE ========");

        Dog1 dog = new Dog1();

        dog.eat();
        dog.bark();



        System.out.println("\n======== COMPOSITION ========");

        Car car = new Car();

        car.drive();
    }
}



/*
=========================================================
                    INHERITANCE
=========================================================

Inheritance represents IS-A relationship.

Dog IS-A Animal

Child class acquires properties and behavior
from parent class using extends keyword.

Inheritance creates:
- Tight Coupling
- Hierarchical relationship
- Method overriding support

=========================================================
*/

class Animal1 {

    void eat() {
        System.out.println("Animal is eating");
    }
}

class Dog1 extends Animal1 {

    void bark() {
        System.out.println("Dog is barking");
    }
}



/*
=========================================================
                    COMPOSITION
=========================================================

Composition represents HAS-A relationship.

Car HAS-A Engine

One class contains another class object.

Composition creates:
- Loose Coupling
- Better flexibility
- Better maintainability

Modern Java prefers:
Composition over Inheritance

=========================================================
*/

class Engine {

    void start() {
        System.out.println("Engine started");
    }
}

class Car {

    // HAS-A Relationship
    Engine engine = new Engine();

    void drive() {

        engine.start();

        System.out.println("Car is driving");
    }
}



/*
=========================================================
                KEY DIFFERENCES
=========================================================

Inheritance:
-------------
- IS-A relationship
- Uses extends
- Tight coupling
- Reuse via hierarchy

Composition:
-------------
- HAS-A relationship
- Uses object reference
- Loose coupling
- Reuse via objects

=========================================================
                INTERVIEW ANSWER
=========================================================

Inheritance represents an IS-A relationship where
a child class acquires properties and behavior from
a parent class using extends.

Composition represents a HAS-A relationship where
one class contains another class object.

Inheritance creates tight coupling, while composition
provides loose coupling and better flexibility.

=========================================================
*/