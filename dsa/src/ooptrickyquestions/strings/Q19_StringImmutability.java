package ooptrickyquestions.strings;

/*
    ==========================================================
    QUESTION-2
    ==========================================================

    STRING IMMUTABILITY
 */

public class Q19_StringImmutability {

    public static void main(String[] args) {

        String s1 = "java";



        s1.concat(" backend");



        System.out.println(s1);
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    java

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    String is:
        IMMUTABLE

    concat() creates NEW object.

    Original object unchanged.

    Since returned object not stored:
        result lost

 */