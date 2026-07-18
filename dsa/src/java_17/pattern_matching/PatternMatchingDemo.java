package java_17.pattern_matching;

//Pattern Matching simplifies type checking
//and casting by combining them into a single expression.

/*
 * ===============================================================
 * JAVA 17 PATTERN MATCHING FOR SWITCH
 * ===============================================================
 *
 * Introduced to simplify:
 *
 * -> instanceof checks
 * -> type casting
 * -> switch logic
 *
 *
 * BEFORE JAVA 17
 * -----------------------
 * Too much boilerplate:
 *
 * instanceof
 * manual casting
 * multiple if-else blocks
 *
 *
 * JAVA 17 SOLUTION
 * -----------------------
 * Pattern Matching
 *
 * Cleaner and safer code.
 */

public class PatternMatchingDemo {

    public static void main(String[] args) {

        // =========================================================
        // BEFORE JAVA 17
        // =========================================================

        Object obj1 = "Rahul";



        /*
         * Old style:
         *
         * instanceof
         * + manual casting
         */

        if (obj1 instanceof String) {

            String str = (String) obj1;

            System.out.println(
                    str.toUpperCase()
            );
        }




        // =========================================================
        // JAVA 17 PATTERN MATCHING
        // =========================================================

        Object obj2 = "Java 17";



        /*
         * No manual casting required.
         */

        if (obj2 instanceof String str) {

            System.out.println(
                    str.toUpperCase()
            );
        }




        /*
         * Compiler automatically:
         *
         * -> checks type
         * -> casts object
         * -> creates variable
         */




        // =========================================================
        // PATTERN MATCHING FOR SWITCH
        // =========================================================

        Object value = 100;



        String result = switch (value) {

            case Integer num ->

                    "Integer Value : " + num;

            case String str ->

                    "String Value : " + str;

            case Double d ->

                    "Double Value : " + d;

            default ->

                    "Unknown Type";
        };

        System.out.println(result);




        // =========================================================
        // REAL PROJECT USE CASE
        // =========================================================

        Payment payment = new UPIPayment();
        String paymentResult = switch (payment) {

            case CreditCardPayment c ->

                    "Credit Card Processing";

            case UPIPayment u ->

                    "UPI Processing";
        };

        System.out.println(paymentResult);




        // =========================================================
        // WHY IMPORTANT?
        // =========================================================

        /*
         * Cleaner code
         * Less casting
         * Safer type handling
         * Better readability
         */




        // =========================================================
        // GUARDED PATTERNS
        // =========================================================

        Object obj3 = "Rahul";



//        String response = switch (obj3) {
//
//            case String s && s.length() > 3 ->
//
//                "Long String";
//
//                case String s ->
//
//                        "Small String";
//
//                default ->
//
//                        "Unknown";
//        };
//
//        System.out.println(response);




        // =========================================================
        // NULL HANDLING
        // =========================================================

        Object obj4 = null;



        String nullCheck = switch (obj4) {

            case null ->

                    "Value is null";

            default ->

                    "Not Null";
        };

        System.out.println(nullCheck);




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why Pattern Matching introduced?
         *
         * To reduce boilerplate
         * instanceof + casting code.
         */




        /*
         * Q2 Main benefit?
         *
         * Cleaner and safer code.
         */




        /*
         * Q3 What happens internally?
         *
         * Compiler performs:
         * -> type check
         * -> cast
         * automatically.
         */




        /*
         * Q4 Why useful with Sealed Classes?
         *
         * Compiler knows all subclasses.
         */




        /*
         * Q5 Difference between old switch
         * and pattern matching switch?
         *
         * Pattern matching switch
         * supports type patterns.
         */




        /*
         * Q6 Can switch handle null?
         *
         * YES
         *
         * case null supported.
         */




        /*
         * Q7 What are guarded patterns?
         *
         * Extra conditions in pattern matching.
         */
    }
}




// ===============================================================
// SEALED INTERFACE FOR REAL USE CASE
// ===============================================================

sealed interface Payment permits CreditCardPayment, UPIPayment {

}




final class CreditCardPayment implements Payment {

}




final class UPIPayment implements Payment {

}