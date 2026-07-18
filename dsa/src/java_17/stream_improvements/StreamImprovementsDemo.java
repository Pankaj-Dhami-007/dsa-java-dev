package java_17.stream_improvements;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * ===============================================================
 * JAVA STREAM API IMPROVEMENTS
 * ===============================================================
 *
 * Java 9 → 17 added several important
 * Stream API improvements.
 *
 * Main features:
 *
 * -> toList()
 * -> takeWhile()
 * -> dropWhile()
 * -> ofNullable()
 * -> iterate() improvements
 *
 *
 * WHY IMPORTANT?
 * -------------------------
 *
 * Cleaner functional programming
 * and less boilerplate code.
 */

public class StreamImprovementsDemo {

    public static void main(String[] args) {

        // =========================================================
        // BEFORE JAVA 16
        // collect(Collectors.toList())
        // =========================================================

        List<String> oldList =

                Stream.of("A", "B", "C")

                        .collect(Collectors.toList());



        System.out.println(oldList);




        // =========================================================
        // JAVA 16/17
        // toList()
        // =========================================================

        List<String> newList =

                Stream.of("A", "B", "C")

                        .toList();



        System.out.println(newList);




        /*
         * Cleaner and shorter.
         */




        // =========================================================
        // takeWhile()
        // =========================================================

        /*
         * Takes elements
         * until condition becomes false.
         */

        List<Integer> takeWhileResult =

                Stream.of(1, 2, 3, 4, 1, 2)

                        .takeWhile(num -> num < 4)

                        .toList();



        System.out.println(takeWhileResult);




        /*
         * Output:
         * [1, 2, 3]
         *
         * Stops when 4 comes.
         */




        // =========================================================
        // dropWhile()
        // =========================================================

        /*
         * Drops elements
         * until condition becomes false.
         */

        List<Integer> dropWhileResult =

                Stream.of(1, 2, 3, 4, 1, 2)

                        .dropWhile(num -> num < 4)

                        .toList();



        System.out.println(dropWhileResult);




        /*
         * Output:
         * [4, 1, 2]
         */




        // =========================================================
        // ofNullable()
        // =========================================================

        /*
         * Creates empty stream
         * if value null.
         */

        String name = null;



        Stream<String> stream =

                Stream.ofNullable(name);



        System.out.println(
                stream.count()
        );




        /*
         * Output:
         * 0
         */




        // =========================================================
        // iterate() IMPROVEMENT
        // =========================================================

        /*
         * Before:
         * infinite stream
         */

        Stream.iterate(1, num -> num + 1)

                .limit(5)

                .forEach(System.out::println);




        /*
         * Java 9+
         * iterate with condition
         */

        Stream.iterate(

                        1,

                        num -> num <= 5,

                        num -> num + 1
                )

                .forEach(System.out::println);




        // =========================================================
        // OPTIONAL STREAM
        // =========================================================

        Optional<String> optional =
                Optional.of("Java");



        optional.stream()

                .forEach(System.out::println);




        /*
         * Useful in functional pipelines.
         */




        // =========================================================
        // REAL PROJECT USE CASES
        // =========================================================

        /*
         * USE CASE 1
         * Pagination
         */




        /*
         * takeWhile()
         * useful for stopping condition.
         */




        /*
         * USE CASE 2
         * Data Cleaning
         */




        /*
         * dropWhile()
         * skip invalid entries.
         */




        /*
         * USE CASE 3
         * Null-safe pipelines
         */




        /*
         * Stream.ofNullable()
         * avoids null checks.
         */




        /*
         * USE CASE 4
         * API Response Processing
         */




        /*
         * Optional.stream()
         * simplifies chaining.
         */




        // =========================================================
        // INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Difference between
         * collect(toList()) and toList()?
         *
         * toList():
         * -> cleaner
         * -> simpler
         */




        /*
         * Q2 What is takeWhile()?
         *
         * Takes elements
         * until condition false.
         */




        /*
         * Q3 What is dropWhile()?
         *
         * Drops elements
         * until condition false.
         */




        /*
         * Q4 Why ofNullable() useful?
         *
         * Null-safe stream creation.
         */




        /*
         * Q5 iterate() improvement?
         *
         * Supports condition directly.
         */
    }
}