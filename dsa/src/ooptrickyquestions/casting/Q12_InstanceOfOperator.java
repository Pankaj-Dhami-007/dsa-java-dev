package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-4
    ==========================================================

    instanceof OPERATOR
 */

public class Q12_InstanceOfOperator {

    public static void main(String[] args) {

        AnimalQ12 animal =
                new DogQ12();



        System.out.println(
                animal instanceof DogQ12
        );



        System.out.println(
                animal instanceof AnimalQ12
        );
    }

}



class AnimalQ12 {

}



class DogQ12 extends AnimalQ12 {

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    true

    true

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Actual object:
        DogQ12

    Dog IS-A Animal

    So object belongs to:
        both DogQ12 and AnimalQ12

 */