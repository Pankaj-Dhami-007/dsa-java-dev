package ooptrickyquestions.polymorphism;

public class OverridingOverloadingAndPolymorphism {

    /*
        ==========================================================
        METHOD OVERRIDING vs METHOD OVERLOADING
        + RUNTIME POLYMORPHISM
        ==========================================================

        MOST IMPORTANT OOP interview topic.

        EXTREMELY heavily asked in:
        - Java interviews
        - Spring interviews
        - experienced backend interviews

     */



    /*
        ==========================================================
        MAIN IDEA
        ==========================================================

        MOST beginners confuse:

        - overriding
        - overloading
        - polymorphism
        - static binding
        - dynamic binding

     */



    /*
        ==========================================================
        SIMPLE UNDERSTANDING
        ==========================================================

        OVERLOADING:
            same method name
            different parameters

        OVERRIDING:
            child changes parent behavior

     */



    /*
        ==========================================================
        MOST IMPORTANT UNDERSTANDING
        ==========================================================

        OVERLOADING:
            compile-time polymorphism

        OVERRIDING:
            runtime polymorphism

     */



    public static void main(String[] args) {

        /*
            ======================================================
            METHOD OVERLOADING
            ======================================================
         */

        Calculator calculator =
                new Calculator();



        calculator.add(10, 20);

        calculator.add(10, 20, 30);



        /*
            OUTPUT:

            Two Integer Method

            Three Integer Method

         */



        /*
            ======================================================
            METHOD OVERRIDING
            ======================================================
         */

        Animal animal =
                new Dog();



        animal.sound();



        /*
            OUTPUT:

            Dog Barking

         */



        /*
            ======================================================
            MAIN UNDERSTANDING
            ======================================================

            Reference type:
                Animal

            Actual object:
                Dog

            JVM decides method at runtime.

         */

    }

}



/*
    ==========================================================
    METHOD OVERLOADING
    ==========================================================
 */
class Calculator {

    /*
        Same method name.

        Different parameters.
     */

    public void add(int a, int b) {

        System.out.println(
                "Two Integer Method"
        );
    }



    public void add(
            int a,
            int b,
            int c
    ) {

        System.out.println(
                "Three Integer Method"
        );
    }

}



/*
    ==========================================================
    PARENT CLASS
    ==========================================================
 */
class Animal {

    public void sound() {

        System.out.println(
                "Animal Sound"
        );
    }

}



/*
    ==========================================================
    CHILD CLASS
    ==========================================================
 */
class Dog extends Animal {

    /*
        Method overriding.
     */
    @Override
    public void sound() {

        System.out.println(
                "Dog Barking"
        );
    }

}