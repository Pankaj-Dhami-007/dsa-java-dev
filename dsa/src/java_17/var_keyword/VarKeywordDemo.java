package java_17.var_keyword;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * ===============================================================
 * JAVA var KEYWORD
 * ===============================================================
 *
 * var provides:
 *
 * -> local variable type inference
 *
 *
 * BEFORE var
 * -------------------------
 *
 * Repeated long type declarations.
 *
 *
 * JAVA SOLUTION
 * -------------------------
 *
 * Compiler automatically infers type.
 */

public class VarKeywordDemo {

    public static void main(String[] args) {

        // =========================================================
        // BEFORE var
        // =========================================================

        String name = "Rahul";

        List<String> names =
                new ArrayList<>();

        Map<String, List<Integer>> data =
                new HashMap<>();



        /*
         * Problems:
         *
         * -> repetitive types
         * -> verbose code
         * -> difficult readability
         */




        // =========================================================
        // USING var
        // =========================================================

        var city = "Delhi";

        var numbers = new ArrayList<Integer>();

        var studentMap =
                new HashMap<String, String>();



        System.out.println(city);

        System.out.println(numbers);

        System.out.println(studentMap);




        /*
         * Compiler automatically infers:
         *
         * city       -> String
         * numbers    -> ArrayList<Integer>
         * studentMap -> HashMap<String,String>
         */




        // =========================================================
        // IMPORTANT UNDERSTANDING
        // =========================================================

        /*
         * var is NOT dynamic typing.
         *
         * Java remains statically typed.
         */




        /*
         * Once inferred:
         *
         * type cannot change.
         */

        var age = 25;

        // age = "Rahul"; // ERROR




        // =========================================================
        // REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * Complex Generic Types
         */

        var employeeMap =
                new HashMap<String,
                        List<Map<Integer, String>>>();




        /*
         * Without var:
         *
         * very large declaration.
         */




        /*
         * USE CASE 2
         * Stream API
         */

        var result = List.of(1, 2, 3)

                .stream()

                .map(num -> num * 2)

                .toList();



        System.out.println(result);




        /*
         * USE CASE 3
         * Loop variables
         */

        for (var value : result) {
            System.out.println(value);
        }


        /*
         * USE CASE 4
         * try-with-resources
         */

        /*
         * try(var reader = new BufferedReader(...))
         */




        // =========================================================
        // INVALID var USAGE
        // =========================================================

        /*
         * var must initialize immediately.
         */

        // var x; // ERROR




        /*
         * Compiler cannot infer type.
         */




        /*
         * var cannot be used:
         *
         * -> method parameters
         * -> class fields
         * -> return types
         */




        // =========================================================
        // GOOD PRACTICES
        // =========================================================

        /*
         * GOOD:
         */

        var list =
                new ArrayList<String>();




        /*
         * BAD:
         */

        var x = getData();



        /*
         * Problem:
         *
         * unclear type
         */




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 What is var?
         *
         * Local variable type inference.
         */


        /*
         * Q2 Is Java dynamically typed with var?
         *
         * NO
         *
         * Java still statically typed.
         */




        /*
         * Q3 Does var mean Object type?
         *
         * NO
         *
         * Actual type inferred at compile time.
         */




        /*
         * Q4 Can var be used for fields?
         *
         * NO
         */




        /*
         * Q5 Can var be used without initialization?
         *
         * NO
         */




        /*
         * Q6 Main benefit of var?
         *
         * Cleaner code and readability.
         */




        /*
         * Q7 Why var introduced?
         *
         * Reduce repetitive type declarations.
         */




        /*
         * Q8 Is var keyword?
         *
         * NO
         *
         * reserved type name.
         */
    }




    public static String getData() {

        return "Java";
    }
}