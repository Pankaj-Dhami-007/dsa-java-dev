package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-8
    ==========================================================

    MULTILEVEL POLYMORPHISM
 */

public class Q16_MultilevelPolymorphism {

    public static void main(String[] args) {

        AnimalQ16 animal =
                new PuppyQ16();

        animal.sound();
    }

}



class AnimalQ16 {

    public void sound() {

        System.out.println(
                "Animal Sound"
        );
    }

}



class DogQ16 extends AnimalQ16 {

    @Override
    public void sound() {

        System.out.println(
                "Dog Bark"
        );
    }

}



class PuppyQ16 extends DogQ16 {

    @Override
    public void sound() {

        System.out.println(
                "Puppy Sound"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Puppy Sound

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    JVM checks:
        ACTUAL OBJECT

    Actual object:
        PuppyQ16

    So:
        Puppy version executes

 */