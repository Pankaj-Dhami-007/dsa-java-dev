package EngineeringDigest.java8.functionalInterface;

import java.util.Optional;
import java.util.function.Supplier;

public class SupplierExample {

    public static void main(String[] args) {

        /*
         * Supplier<T>
         * -------------------------
         * -> Takes NO input
         * -> Returns a value
         *
         * Method:
         * T get()
         */


        // =====================================================
        // Example 1 : Supply Default Name
        // =====================================================

        // Returns a String value
        Supplier<String> defaultName =
                () -> "Guest";

        System.out.println(defaultName.get());

        /*
         * Output:
         * Guest
         */


        // =====================================================
        // Example 2 : Supply Current Timestamp
        // =====================================================

        /*
         * Every time get() is called,
         * new timestamp is generated.
         */

        Supplier<Long> currentTime =
                () -> System.currentTimeMillis();

        System.out.println(currentTime.get());


        // =====================================================
        // Example 3 : Real Use Case - Optional + Lazy Loading
        // =====================================================

        String name = null;

        // OLD WAY
        String result1 =
                name != null ? name : "Default";

        System.out.println(result1);


        /*
         * orElseGet() uses Supplier
         *
         * Supplier runs ONLY if value is null.
         * This is called lazy execution.
         */

        Supplier<String> fallback =
                () -> "Default";

        String result2 =
                Optional.ofNullable(name)
                        .orElseGet(fallback);

        System.out.println(result2);

        /*
         * Output:
         * Default
         */
    }
}