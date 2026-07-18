package ooptrickyquestions.casting;

/*
    ==========================================================
    QUESTION-7
    ==========================================================

    ACCESS CHILD METHOD USING DOWNCASTING
 */

public class Q15_DowncastingMethodAccess {

    public static void main(String[] args) {

        ParentQ15 parent =
                new ChildQ15();



        ChildQ15 child =
                (ChildQ15) parent;



        child.childMethod();
    }

}



class ParentQ15 {

}



class ChildQ15 extends ParentQ15 {

    public void childMethod() {

        System.out.println(
                "Child Method Called"
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    Child Method Called

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    After downcasting:

        ChildQ15 child

    compiler now allows:
        child-specific methods

 */