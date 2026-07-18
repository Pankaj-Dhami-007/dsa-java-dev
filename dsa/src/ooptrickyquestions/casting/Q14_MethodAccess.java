package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-6
    ==========================================================

    METHOD ACCESS
 */

public class Q14_MethodAccess {

    public static void main(String[] args) {

        ParentQ14 parent =
                new ChildQ14();



        parent.parentMethod();



        /*
            COMPILE TIME ERROR

            parent.childMethod();

         */
    }

}



class ParentQ14 {

    public void parentMethod() {

        System.out.println(
                "Parent Method"
        );
    }

}



class ChildQ14 extends ParentQ14 {

    public void childMethod() {

        System.out.println(
                "Child Method"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Parent Method

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Compiler checks:
        REFERENCE TYPE

    Reference type:
        ParentQ14

    Parent reference cannot access:
        child-specific methods

 */