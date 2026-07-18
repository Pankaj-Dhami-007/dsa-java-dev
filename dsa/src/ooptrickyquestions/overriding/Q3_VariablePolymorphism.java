package ooptrickyquestions.overriding;

/*
    ==========================================================
    QUESTION-3
    ==========================================================

    WHAT WILL BE OUTPUT?
 */

public class Q3_VariablePolymorphism {

    public static void main(String[] args) {

        Parent1 parent =
                new Child1();

        System.out.println(
                parent.x
        );
    }

}



class Parent1 {

    int x = 10;
}



class Child1 extends Parent1 {

    int x = 20;
}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    10

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Variables are NOT polymorphic.

    Variables resolved using:
        REFERENCE TYPE

    Reference Type:
        Parent

    So:
        parent.x -> Parent variable

 */