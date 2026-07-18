package ooptrickyquestions.strings;

/*
    ==========================================================
    QUESTION-1
    ==========================================================

    == vs equals()
 */

public class Q18_DoubleEqualsVsEquals {

    public static void main(String[] args) {

        String s1 = "java";

        String s2 = "java";



        String s3 =
                new String("java");



        System.out.println(
                s1 == s2
        );



        System.out.println(
                s1 == s3
        );



        System.out.println(
                s1.equals(s3)
        );
    }

}



/*
    ==========================================================
    OUTPUT
    ==========================================================

    true

    false

    true

 */



/*
    ==========================================================
    EXPLANATION
    ==========================================================

    == checks:
        memory reference/address

    equals() checks:
        actual content

    ----------------------------------------------------------

    String literals stored inside:
        String Pool

    So:
        s1 and s2 point to same object

    But:
        new String("java")

    creates NEW object in heap memory

 */