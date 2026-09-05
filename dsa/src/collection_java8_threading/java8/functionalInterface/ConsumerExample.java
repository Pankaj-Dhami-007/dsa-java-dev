package EngineeringDigest.java8.functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class ConsumerExample {

    public static void main(String[] args) {

        /*
         * Consumer<T>
         * -------------------------
         * -> Takes input
         * -> Returns nothing (void)
         *
         * Method:
         * void accept(T t)
         */

        // =====================================================
        // Example 1 : Print Name
        // =====================================================

        // Consumer<String>
        // Takes String input and prints it
        Consumer<String> printName =
                name -> System.out.println("Name: " + name);

        // accept() is used to pass value
        printName.accept("Rahul");

        /*
         * Output:
         * Name: Rahul
         */


        // =====================================================
        // Example 2 : Save Data
        // =====================================================

        // Simulating database save operation
        Consumer<String> saveToDb =
                name -> System.out.println("Saving " + name + " to DB");

        saveToDb.accept("Aman");

        /*
         * Output:
         * Saving Aman to DB
         */


        // =====================================================
        // Example 3 : Chaining Consumers using andThen()
        // =====================================================

        /*
         * andThen()
         * ----------
         * Used to combine multiple consumers.
         *
         * First consumer executes,
         * then second consumer executes.
         */

        Consumer<String> printAndSave =
                printName.andThen(saveToDb);

        printAndSave.accept("Priya");

        /*
         * Output:
         * Name: Priya
         * Saving Priya to DB
         */


        // =====================================================
        // Example 4 : Real Use Case - forEach()
        // =====================================================

        /*
         * forEach() internally uses Consumer
         */

        List<String> names =
                Arrays.asList("Rahul", "Priya", "Rohit");

        names.forEach(printName);

        /*
         * Output:
         * Name: Rahul
         * Name: Priya
         * Name: Rohit
         */
    }
}