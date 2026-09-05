package EngineeringDigest.java8.functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/*
 * Function<T, R>
 * -----------------------------------
 * -> Takes one input of type T
 * -> Returns output of type R
 *
 * Method:
 * R apply(T t)
 */

public class FunctionExample {

    public static void main(String[] args) {

        // =====================================================
        // Example 1 : Convert String to Length
        // =====================================================

        /*
         * Input  -> String
         * Output -> Integer
         */

        Function<String, Integer> getLength =
                str -> str.length();

        System.out.println(getLength.apply("Rahul"));

        /*
         * Output:
         * 5
         */


        // =====================================================
        // Example 2 : Convert String to Uppercase
        // =====================================================

        /*
         * Input  -> String
         * Output -> String
         */

        Function<String, String> toUpper =
                str -> str.toUpperCase();

        System.out.println(toUpper.apply("rahul"));

        /*
         * Output:
         * RAHUL
         */


        // =====================================================
        // Example 3 : Chaining Functions using andThen()
        // =====================================================

        /*
         * andThen()
         * -----------------
         * First function executes,
         * then second function executes.
         */

        Function<String, String> trimAndUpper =

                ((Function<String, String>) String::trim)

                        .andThen(String::toUpperCase);

        System.out.println(trimAndUpper.apply("   rahul   "));

        /*
         * Flow:
         *
         * "   rahul   "
         *       ↓ trim()
         * "rahul"
         *       ↓ toUpperCase()
         * "RAHUL"
         *
         * Output:
         * RAHUL
         */


        // =====================================================
        // Example 4 : Real Use Case - Stream map()
        // =====================================================

        /*
         * map() internally uses Function
         *
         * Each element is transformed
         * using the provided function.
         */

        List<String> names =
                Arrays.asList("rahul", "priya", "rohit");

        names.stream()

                // convert each name to uppercase
                .map(toUpper)

                // print values
                .forEach(System.out::println);

        /*
         * Output:
         * RAHUL
         * PRIYA
         * ROHIT
         */
    }
}