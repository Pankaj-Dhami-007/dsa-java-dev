package designpattern.creational.factory;

public class FactoryDefinitionUsingParentChild {

    /*
        ==========================================================
        FACTORY DESIGN PATTERN
        USING PARENT + CHILD RELATION
        ==========================================================

        This is MOST IMPORTANT understanding.

        Factory Pattern mainly works using:

        - Parent type reference
        - Child class objects
        - Runtime Polymorphism

     */



    /*
        ==========================================================
        SIMPLE DEFINITION
        ==========================================================

        Factory Pattern is a design pattern
        where parent type reference decides
        which child object to create at runtime.

     */



    /*
        ==========================================================
        MORE SIMPLE
        ==========================================================

        Instead of client creating child objects directly:

            new Dog()
            new Cat()

        Factory creates appropriate child object
        and returns it as parent type.

     */



    /*
        ==========================================================
        MAIN CONCEPT
        ==========================================================

        Parent reference can hold
        child object.

            Animal a = new Dog();

        Factory uses this concept heavily.

     */



    /*
        ==========================================================
        WHY IMPORTANT?
        ==========================================================

        Client should depend on:

            Parent abstraction

        NOT concrete child classes.

        This creates:
        - loose coupling
        - flexible system
        - scalable architecture

     */



    /*
        ==========================================================
        WITHOUT FACTORY
        ==========================================================

        Client directly creates child objects:

            Dog d = new Dog();
            Cat c = new Cat();

        Problem:

        Client tightly coupled with child classes.

     */



    /*
        ==========================================================
        WITH FACTORY
        ==========================================================

        Client only knows parent:

            Animal a =
                    AnimalFactory.getAnimal("DOG");

        Factory decides:
        which child object to create.

     */



    /*
        ==========================================================
        REAL LIFE ANALOGY
        ==========================================================

        Think:

        You order vehicle.

        You only know:
            "I need vehicle"

        Factory decides:
        - Car
        - Bike
        - Truck

        You interact through:
            Vehicle

        not specific internal implementation.

     */



    public static void main(String[] args) {

        /*
            Parent reference
            holding child object.
         */
        Animal animal1 =
                AnimalFactory.getAnimal("DOG");



        Animal animal2 =
                AnimalFactory.getAnimal("CAT");



        animal1.sound();

        animal2.sound();



        /*
            OUTPUT:

            Dog Barking

            Cat Meowing
         */



        /*
            ======================================================
            INTERNAL FLOW
            ======================================================

            STEP-1:
            Client asks factory for object.

            STEP-2:
            Factory checks type.

            STEP-3:
            Factory creates child object.

            STEP-4:
            Factory returns object
            as parent type.

         */



        /*
            ======================================================
            IMPORTANT UNDERSTANDING
            ======================================================

            Client does NOT know:

            - object creation logic
            - child implementation
            - constructor details

            Client only knows:

                Animal

         */



        /*
            ======================================================
            MAIN POWER OF FACTORY
            ======================================================

            Tomorrow new child comes:

                Lion

            Client code remains same.

            Only factory changes.

         */



        /*
            ======================================================
            INTERVIEW QUESTIONS
            ======================================================

            Q1:
            Factory Pattern mainly depends on which OOP concept?

            Answer:
            Polymorphism.



            Q2:
            Why parent reference used?

            Answer:
            To achieve loose coupling.



            Q3:
            Who decides child object creation?

            Answer:
            Factory class.



            Q4:
            Client depends on?

            Answer:
            Parent abstraction.



            Q5:
            Biggest advantage?

            Answer:
            Client independent from child classes.



            Q6:
            Which principle used heavily?

            Answer:
            Program to interface
            not implementation.

         */



        /*
            ======================================================
            IS FACTORY PATTERN COMPLETE?
            ======================================================

            NO.

            Still remaining:

            - proper notification example
            - payment example
            - runtime factory selection
            - factory method pattern
            - abstract factory
            - Spring internal usage
            - advanced interview questions

         */

    }

}



/*
    ==========================================================
    PARENT
    ==========================================================

    Common abstraction.
 */
interface Animal {

    void sound();
}



/*
    ==========================================================
    CHILD-1
    ==========================================================
 */
class Dog implements Animal {

    @Override
    public void sound() {

        System.out.println("Dog Barking");
    }
}



/*
    ==========================================================
    CHILD-2
    ==========================================================
 */
class Cat implements Animal {

    @Override
    public void sound() {

        System.out.println("Cat Meowing");
    }
}



/*
    ==========================================================
    FACTORY CLASS
    ==========================================================

    Responsible for object creation.
 */
class AnimalFactory {

    public static Animal getAnimal(String type) {

        if(type.equals("DOG")) {

            return new Dog();
        }

        else if(type.equals("CAT")) {

            return new Cat();
        }

        return null;
    }

}