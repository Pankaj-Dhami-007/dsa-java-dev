package ooptrickyquestions.overriding;

/*
    ==========================================================
    QUESTION-5
    ==========================================================

    CAN PRIVATE METHODS BE OVERRIDDEN?
 */

public class Q5_PrivateMethod {

    public static void main(String[] args) {

        Parent2 parent =
                new Child2();

        parent.test();
    }

}



class Parent2 {

    private void message() {

        System.out.println(
                "Parent Private Method"
        );
    }



    public void test() {

        message();
    }

}



class Child2 extends Parent2 {

    /*
        This is NOT overriding.

        Parent private methods
        are not visible to child.
     */
    public void message() {

        System.out.println(
                "Child Method"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Parent Private Method

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Private methods are NOT inherited.

    So:
        overriding impossible

    Parent test() method internally calls:
        Parent private message()

 */