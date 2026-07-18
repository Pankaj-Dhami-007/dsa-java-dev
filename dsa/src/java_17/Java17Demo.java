package java_17;


//Java 17 is a modern LTS release focused on
//performance, security, readability, and developer productivity.

/*
 * ===============================================================
 * JAVA 17 COMPLETE OVERVIEW
 * ===============================================================
 *
 * Java 17 is a:
 *
 * -> Long Term Support (LTS) version
 *
 * Released:
 * -> September 2021
 *
 * WHY IMPORTANT?
 * ------------------------
 * Most companies migrated from:
 *
 * Java 8 -> Java 17
 *
 * because:
 * -> better performance
 * -> modern language features
 * -> cleaner code
 * -> security improvements
 * -> JVM optimizations
 *
 *
 * ===============================================================
 * MAIN FEATURES OF JAVA 17
 * ===============================================================
 *
 * 1. Sealed Classes
 * 2. Pattern Matching for switch
 * 3. Records
 * 4. Text Blocks
 * 5. Enhanced Random Generator
 * 6. Helpful NullPointerException
 * 7. Strong Encapsulation
 * 8. New macOS Rendering Pipeline
 * 9. Foreign Function & Memory API (Incubator)
 * 10. Vector API (Incubator)
 *
 *
 * ===============================================================
 * NOTE
 * ===============================================================
 *
 * This file is ONLY OVERVIEW.
 *
 * Later:
 * -> deep dive individually
 * -> internal working
 * -> JVM behavior
 * -> interview questions
 * -> real project use cases
 */



public class Java17Demo {

    public static void main(String[] args) {

        // =========================================================
        // 1. RECORDS
        // =========================================================

        /*
         * BEFORE JAVA 17
         *
         * For DTO/Model class:
         * -> fields
         * -> constructor
         * -> getters
         * -> toString()
         * -> equals()
         * -> hashCode()
         *
         * Too much boilerplate code.
         */




        /*
         * JAVA 17 RECORD
         *
         * Automatically generates:
         * -> constructor
         * -> getters
         * -> equals()
         * -> hashCode()
         * -> toString()
         */

        User user =
                new User(1, "Rahul");

        System.out.println(user);




        /*
         * REAL PROJECT USE CASE
         * -------------------------
         * DTO classes
         * API responses
         * immutable data transfer
         */




        // =========================================================
        // 2. TEXT BLOCKS
        // =========================================================

        /*
         * BEFORE JAVA 17
         *
         * String json =
         * "{\n" +
         * "\"name\":\"Rahul\"\n" +
         * "}";
         *
         * Hard to read.
         */




        /*
         * JAVA 17 TEXT BLOCKS
         */

        String json = """

                {
                    "name" : "Rahul",
                    "city" : "Delhi"
                }

                """;

        System.out.println(json);




        /*
         * REAL USE CASE
         * -------------------
         * JSON
         * SQL Queries
         * HTML Templates
         * XML
         */




        // =========================================================
        // 3. SEALED CLASSES
        // =========================================================

        /*
         * Controls inheritance.
         *
         * Only specific classes
         * are allowed to extend.
         */

        Shape shape =
                new Circle();

        shape.draw();




        /*
         * WHY INTRODUCED?
         * -------------------
         *
         * Better control over hierarchy.
         *
         * Prevent random inheritance.
         */




        /*
         * REAL USE CASE
         * -------------------
         * Payment systems
         * Response hierarchies
         * Domain modeling
         * Finite class hierarchy
         */




        // =========================================================
        // 4. PATTERN MATCHING FOR SWITCH
        // =========================================================

        /*
         * Cleaner instanceof + casting.
         */

        Object obj = "Java 17";

        String result = switch (obj) {

            case String str ->
                    "String Value : " + str;

            case Integer num ->
                    "Integer Value : " + num;

            default ->
                    "Unknown";
        };

        System.out.println(result);




        /*
         * BEFORE JAVA 17
         *
         * if(obj instanceof String){
         *      String s = (String) obj;
         * }
         */




        /*
         * REAL USE CASE
         * -------------------
         * Event processing
         * API response parsing
         * type-based logic
         */




        // =========================================================
        // 5. HELPFUL NULL POINTER EXCEPTION
        // =========================================================

        /*
         * BEFORE JAVA 17
         *
         * NullPointerException
         *
         * No exact information.
         */




        /*
         * JAVA 17
         *
         * Gives detailed info:
         *
         * Example:
         *
         * Cannot invoke "Address.getCity()"
         * because "user.getAddress()" is null
         */




        // =========================================================
        // 6. ENHANCED RANDOM GENERATOR
        // =========================================================

        /*
         * Better random generators
         * introduced.
         *
         * Improved performance.
         */




        /*
         * REAL USE CASE
         * -------------------
         * Simulations
         * Gaming
         * Security
         * Analytics
         */




        // =========================================================
        // 7. STRONG ENCAPSULATION
        // =========================================================

        /*
         * Internal JDK APIs
         * are strongly encapsulated.
         *
         * Improves:
         * -> security
         * -> maintainability
         */




        // =========================================================
        // 8. VECTOR API
        // =========================================================

        /*
         * Used for high-performance
         * mathematical computations.
         *
         * SIMD operations.
         */




        /*
         * REAL USE CASE
         * -------------------
         * AI
         * Machine Learning
         * Financial calculations
         * Scientific computing
         */




        // =========================================================
        // 9. FOREIGN FUNCTION & MEMORY API
        // =========================================================

        /*
         * Java can directly interact
         * with native memory and C libraries.
         */




        /*
         * WHY IMPORTANT?
         * -------------------
         *
         * Better than JNI.
         *
         * Safer native access.
         */




        // =========================================================
        // 10. PERFORMANCE IMPROVEMENTS
        // =========================================================

        /*
         * Java 17 provides:
         *
         * -> Better JVM optimizations
         * -> Better G1 GC
         * -> Faster startup
         * -> Better memory management
         */




        // =========================================================
        // 11. SECURITY IMPROVEMENTS
        // =========================================================

        /*
         * Improved:
         * -> TLS
         * -> cryptography
         * -> encapsulation
         * -> JVM safety
         */




        // =========================================================
        // 12. INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why companies moving from Java 8 to Java 17?
         *
         * -> LTS support
         * -> modern features
         * -> better performance
         * -> better security
         * -> cleaner code
         */




        /*
         * Q2 Most important Java 17 feature?
         *
         * Usually:
         * -> Records
         * -> Sealed Classes
         * -> Pattern Matching
         */




        /*
         * Q3 What problem do Records solve?
         *
         * Removes boilerplate code.
         */




        /*
         * Q4 What problem do Sealed Classes solve?
         *
         * Controlled inheritance.
         */




        /*
         * Q5 Why Pattern Matching useful?
         *
         * Cleaner type checking and casting.
         */




        /*
         * Q6 Difference between normal class
         * and record?
         *
         * Record:
         * -> immutable
         * -> concise
         * -> auto-generated methods
         */




        /*
         * Q7 Difference between final class
         * and sealed class?
         *
         * final:
         * -> nobody can extend
         *
         * sealed:
         * -> only permitted classes can extend
         */




        /*
         * Q8 Why Text Blocks introduced?
         *
         * Multi-line strings readability.
         */




        /*
         * Q9 Is Java 17 backward compatible?
         *
         * Mostly yes.
         */




        /*
         * Q10 Why Java 17 is called LTS?
         *
         * Long-term support from Oracle.
         */
    }
}




// ===============================================================
// RECORD EXAMPLE
// ===============================================================

record User(int id, String name) {

}




// ===============================================================
// SEALED CLASS EXAMPLE
// ===============================================================

sealed class Shape
        permits Circle, Rectangle {

    public void draw() {
        System.out.println("Drawing Shape");
    }
}




final class Circle extends Shape {

}




final class Rectangle extends Shape {

}