package ooptrickyquestions.overriding;

/*
    ==========================================================
    QUESTION-6
    ==========================================================

    WHAT WILL BE OUTPUT?
 */

public class Q6_ConstructorPolymorphism {

    public static void main(String[] args) {

        Parent3 parent =
                new Child3();
    }

}



class Parent3 {

    Parent3() {
        test();
    }



    public void test() {

        System.out.println(
                "Parent Method"
        );
    }

}



class Child3 extends Parent3 {

    int x = 10;

    @Override
    public void test() {

        System.out.println(x);
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    0

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    VERY IMPORTANT INTERVIEW QUESTION.

    Parent constructor executes first.

    During parent constructor:
        Child variables NOT initialized yet.

    So:
        x still default value

    default int value:
        0

 */