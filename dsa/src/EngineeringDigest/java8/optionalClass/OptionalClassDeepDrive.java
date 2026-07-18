package EngineeringDigest.java8.optionalClass;

import java.util.Optional;

public class OptionalClassDeepDrive {

    // Optional is a container object introduced in Java 8
    //to handle null values safely and avoid NullPointerException.
    public static void main(String[] args) {

        /*
         * =========================================================
         * WHAT IS OPTIONAL?
         * =========================================================
         *
         * Optional is a container object introduced in Java 8.
         *
         * It is used to avoid:
         * -> NullPointerException (NPE)
         * -> Too many null checks
         * -> Unsafe coding
         *
         * Optional may contain:
         * -> A value
         * -> Empty value
         */




        // =========================================================
        // 1. Creating Optional Objects
        // =========================================================

        /*
         * Optional.of()
         * -------------------
         * Used when value is definitely NOT NULL.
         *
         * If null comes -> NullPointerException
         */

        Optional<String> name1 = Optional.of("Rahul");

        System.out.println(name1.get());




        /*
         * Optional.ofNullable()
         * ----------------------
         * Used when value MAY BE NULL.
         */

        String value = null;

        Optional<String> name2 =
                Optional.ofNullable(value);

        System.out.println(name2);




        /*
         * Optional.empty()
         * --------------------
         * Creates empty Optional
         */

        Optional<String> empty =
                Optional.empty();

        System.out.println(empty);




        // =========================================================
        // 2. Checking Value Present or Not
        // =========================================================

        /*
         * isPresent()
         * -----------------
         * Returns true if value exists
         */

        Optional<String> user =
                Optional.of("Priya");

        if (user.isPresent()) {
            System.out.println(user.get());
        }




        /*
         * isEmpty()
         * ----------------
         * Opposite of isPresent()
         *
         * Added in Java 11
         */

        Optional<String> user2 =
                Optional.empty();

        if (user2.isEmpty()) {
            System.out.println("No Value Present");
        }




        // =========================================================
        // 3. Getting Value from Optional
        // =========================================================

        /*
         * get()
         * ----------------
         * Returns value if present
         *
         * If value absent:
         * -> NoSuchElementException
         *
         * Avoid using get() directly in real projects.
         */

        Optional<String> city =
                Optional.of("Delhi");

        System.out.println(city.get());




        /*
         * orElse()
         * ----------------
         * Returns default value if Optional empty
         */

        Optional<String> country =
                Optional.empty();

        String result =
                country.orElse("India");

        System.out.println(result);




        /*
         * orElseGet()
         * --------------------
         * Takes Supplier
         * Executes lazily
         *
         * Better performance than orElse()
         */

        String result2 =
                country.orElseGet(() -> "Default Country");

        System.out.println(result2);




        /*
         * orElseThrow()
         * ----------------------
         * Throws custom exception if value absent
         */

        Optional<String> email =
                Optional.empty();

        // String emailResult =
        //         email.orElseThrow(
        //                 () -> new RuntimeException("Email Not Found")
        //         );




        // =========================================================
        // 4. Performing Action
        // =========================================================

        /*
         * ifPresent()
         * -------------------
         * Executes Consumer only if value exists
         */

        Optional<String> company =
                Optional.of("Google");

        company.ifPresent(
                value1 -> System.out.println(value1)
        );




        /*
         * ifPresentOrElse()
         * ----------------------
         * Java 9 feature
         */

        Optional<String> data =
                Optional.empty();

        data.ifPresentOrElse(
                val -> System.out.println(val),
                () -> System.out.println("No Data Found")
        );




        // =========================================================
        // 5. Transforming Optional Values
        // =========================================================

        /*
         * map()
         * ----------------
         * Transforms value
         */

        Optional<String> username =
                Optional.of("rahul");

        Optional<String> upper =
                username.map(String::toUpperCase);

        System.out.println(upper.get());




        /*
         * flatMap()
         * ----------------
         * Used when mapper already returns Optional
         */

        Optional<String> userName =
                Optional.of("Rahul");

        Optional<String> resultFlatMap =
                userName.flatMap(
                        val1 -> Optional.of(val1.toUpperCase())
                );

        System.out.println(resultFlatMap.get());




        /*
         * filter()
         * ----------------
         * Filters Optional value using Predicate
         */

        Optional<String> employee =
                Optional.of("Rahul");

        Optional<String> filtered =
                employee.filter(
                        val2 -> val2.startsWith("R")
                );

        System.out.println(filtered);




        // =========================================================
        // 6. Stream API with Optional
        // =========================================================

        Optional<String> product =
                Optional.of("Laptop");

        product.stream()
                .forEach(System.out::println);




        // =========================================================
        // 7. REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * Repository Layer
         */

        /*
         * Spring Boot Example:
         *
         * Optional<User> findById(Long id);
         *
         * Why?
         * Because data may or may not exist.
         */




        /*
         * USE CASE 2
         * API Response Handling
         */

        /*
         * Example:
         *
         * Optional<Address> address =
         *      user.getAddress();
         *
         * Prevents NullPointerException
         */




        /*
         * USE CASE 3
         * Configuration Values
         */

        /*
         * Optional<String> env =
         *      Optional.ofNullable(
         *          System.getenv("DB_URL")
         *      );
         */




        /*
         * USE CASE 4
         * Nested Object Handling
         */

        /*
         * OLD WAY:
         *
         * if(user != null &&
         *    user.getAddress() != null &&
         *    user.getAddress().getCity() != null)
         *
         * NEW WAY:
         *
         * Optional.ofNullable(user)
         *      .map(User::getAddress)
         *      .map(Address::getCity)
         *      .orElse("Unknown");
         */




        // =========================================================
        // 8. COMMON INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Difference between orElse() and orElseGet()
         *
         * orElse():
         * -> Default value created ALWAYS
         *
         * orElseGet():
         * -> Default value created ONLY if needed
         */

        Optional<String> test =
                Optional.of("Java");

        System.out.println(
                test.orElse(getDefaultValue())
        );

        System.out.println(
                test.orElseGet(() -> getDefaultValue())
        );




        /*
         * Q2 Difference between map() and flatMap()
         *
         * map():
         * -> Wraps result into Optional
         *
         * flatMap():
         * -> Avoids nested Optional
         */




        /*
         * map():
         * Optional<Optional<String>>
         */

        Optional<Optional<String>> mapExample =
                Optional.of("Java")
                        .map(val3 -> Optional.of(val3.toUpperCase()));




        /*
         * flatMap():
         * Optional<String>
         */

        Optional<String> flatMapExample =
                Optional.of("Java")
                        .flatMap(val4 ->
                                Optional.of(val4.toUpperCase()));




        // =========================================================
        // 9. BEST PRACTICES
        // =========================================================

        /*
         * GOOD PRACTICES
         * -------------------
         *
         * ✔ Use Optional as return type
         * ✔ Use orElseThrow()
         * ✔ Use map/filter/flatMap
         * ✔ Avoid manual null checks
         */




        /*
         * BAD PRACTICES
         * -------------------
         *
         * ❌ Do NOT use Optional in fields
         * ❌ Do NOT use Optional in constructor params
         * ❌ Do NOT use Optional.get() blindly
         * ❌ Do NOT serialize Optional
         */




        // =========================================================
        // 10. WHY OPTIONAL IS IMPORTANT?
        // =========================================================

        /*
         * BEFORE JAVA 8
         *
         * Too many null checks
         * More NullPointerException
         * Ugly code
         */




        /*
         * AFTER OPTIONAL
         *
         * Cleaner code
         * Safer code
         * Functional style programming
         * Better readability
         */
    }




    // =============================================================
    // Helper Method
    // =============================================================

    public static String getDefaultValue() {

        System.out.println("Default method called");

        return "Default";
    }
}