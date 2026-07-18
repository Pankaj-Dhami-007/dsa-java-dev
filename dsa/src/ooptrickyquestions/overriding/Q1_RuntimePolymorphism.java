package ooptrickyquestions.overriding;

/*
    ==========================================================
    QUESTION-1
    ==========================================================

    WHAT WILL BE OUTPUT?
 */

public class Q1_RuntimePolymorphism {

    public static void main(String[] args) {

        Animal animal =
                new Dog();

        animal.sound();
    }

}



class Animal {

    public void sound() {

        System.out.println(
                "Animal Sound"
        );
    }

}



class Dog extends Animal {

    @Override
    public void sound() {

        System.out.println(
                "Dog Barking"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Dog Barking

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Reference Type:
        Animal

    Actual Object:
        Dog

    Overridden methods resolved at runtime
    using ACTUAL OBJECT.

    This is:
        Runtime Polymorphism

 */