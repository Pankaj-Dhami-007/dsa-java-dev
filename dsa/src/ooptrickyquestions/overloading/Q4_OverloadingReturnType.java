package ooptrickyquestions.overloading;

/*
    ==========================================================
    QUESTION-4
    ==========================================================

    CAN METHODS BE OVERLOADED
    ONLY BY RETURN TYPE?
 */

public class Q4_OverloadingReturnType {

    public static void main(String[] args) {

    }

}



class Calculator {

    int add() {

        return 10;
    }



    /*
        COMPILE TIME ERROR
     */

    /*
    double add() {

        return 20;
    }
    */

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    COMPILE TIME ERROR

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    Method overloading depends on:
        PARAMETERS

    NOT:
        return type

    Compiler becomes confused:
        which add() to call

 */