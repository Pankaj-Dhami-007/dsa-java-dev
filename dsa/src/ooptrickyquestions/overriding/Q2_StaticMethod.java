package ooptrickyquestions.overriding;

/*
    ==========================================================
    QUESTION-2
    ==========================================================

    WHAT WILL BE OUTPUT?
 */

public class Q2_StaticMethod {

    public static void main(String[] args) {

        Parent parent =
                new Child();

        parent.test();
    }

}



class Parent {

    static void test() {

        System.out.println(
                "Parent Static Method"
        );
    }

}



class Child extends Parent {

    static void test() {

        System.out.println(
                "Child Static Method"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Parent Static Method

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Static methods are NOT overridden.

    They are:
        METHOD HIDDEN

    Static methods resolved using:
        REFERENCE TYPE

    Reference Type:
        Parent

    So:
        Parent.test() executes

 */