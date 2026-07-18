package java_17.switch_expression;
//Switch Expressions provide cleaner and safer switch handling
//by eliminating break statements and supporting direct value returns.
//Accidental fall-through caused by missing break.
//yield is used to return value from a switch block.


/*
 * ===============================================================
 * JAVA 17 SWITCH EXPRESSIONS
 * ===============================================================
 *
 * Introduced to make switch:
 *
 * -> cleaner
 * -> shorter
 * -> safer
 * -> expression-based
 *
 *
 * BEFORE JAVA 17
 * -------------------------
 * Problems:
 *
 * -> break keyword required
 * -> fall-through bugs
 * -> verbose syntax
 * -> cannot directly return value
 *
 *
 * JAVA 17 SOLUTION
 * -------------------------
 *
 * Enhanced Switch Expression
 */

public class SwitchExpressionDemo {

    public static void main(String[] args) {

        // =========================================================
        // BEFORE JAVA 17
        // OLD SWITCH STATEMENT
        // =========================================================

        int day = 2;

        String resultOld;

        switch (day) {

            case 1:
                resultOld = "Monday";
                break;

            case 2:
                resultOld = "Tuesday";
                break;

            case 3:
                resultOld = "Wednesday";
                break;

            default:
                resultOld = "Invalid";
        }



        System.out.println(resultOld);




        /*
         * Problems:
         *
         * -> repetitive break
         * -> large code
         * -> fall-through risk
         */




        // =========================================================
        // JAVA 17 SWITCH EXPRESSION
        // =========================================================

        int day2 = 3;

        String resultNew = switch (day2) {

            case 1 -> "Monday";

            case 2 -> "Tuesday";

            case 3 -> "Wednesday";

            default -> "Invalid";
        };

        System.out.println(resultNew);




        /*
         * Advantages:
         *
         * -> cleaner
         * -> shorter
         * -> no break needed
         * -> directly returns value
         */


        // =========================================================
        // MULTIPLE CASES
        // =========================================================

        int month = 1;

        String season = switch (month) {

            case 12, 1, 2 -> "Winter";

            case 3, 4, 5 -> "Summer";

            case 6, 7, 8 -> "Rainy";

            default -> "Unknown";
        };

        System.out.println(season);




        // =========================================================
        // yield KEYWORD
        // =========================================================

        /*
         * Used when block contains
         * multiple statements.
         */

        int marks = 85;

        String grade = switch (marks / 10) {
            case 10, 9 -> {
                System.out.println("Excellent");
                yield "A";
            }

            case 8 -> {

                System.out.println("Very Good");
                yield "B";
            }

            default -> {
                yield "C";
            }
        };
        System.out.println(grade);


        // =========================================================
        // REAL PROJECT USE CASE
        // =========================================================

        String paymentType = "UPI";
        String paymentResult = switch (paymentType) {

            case "UPI" ->

                    "UPI Payment Processing";

            case "CARD" ->

                    "Card Payment Processing";

            case "NET_BANKING" ->

                    "Net Banking Processing";

            default ->

                    "Unknown Payment";
        };

        System.out.println(paymentResult);




        // =========================================================
        // ENUM SWITCH
        // =========================================================

        OrderStatus status =
                OrderStatus.SHIPPED;

        String orderMessage = switch (status) {

            case PLACED ->

                    "Order Placed";

            case SHIPPED ->

                    "Order Shipped";

            case DELIVERED ->

                    "Order Delivered";
        };

        System.out.println(orderMessage);

        // =========================================================
        // WHY IMPORTANT?
        // =========================================================

        /*
         * Cleaner syntax
         * No fall-through bugs
         * Better readability
         * Expression support
         */




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why Switch Expressions introduced?
         *
         * To make switch cleaner and safer.
         */




        /*
         * Q2 Difference between old switch
         * and switch expression?
         *
         * Switch expression:
         * -> returns value
         * -> no break needed
         * -> arrow syntax
         */




        /*
         * Q3 What problem solved?
         *
         * Fall-through bug.
         */




        /*
         * Q4 What is yield keyword?
         *
         * Used to return value
         * from switch block.
         */




        /*
         * Q5 Why safer than old switch?
         *
         * No accidental fall-through.
         */




        /*
         * Q6 Can multiple cases be combined?
         *
         * YES
         *
         * case 1,2,3 ->
         */




        /*
         * Q7 Can switch directly assign value?
         *
         * YES
         *
         * String result = switch(){}
         */
    }
}




// ===============================================================
// ENUM FOR REAL PROJECT USE CASE
// ===============================================================

enum OrderStatus {

    PLACED,
    SHIPPED,
    DELIVERED
}