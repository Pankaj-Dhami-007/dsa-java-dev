package EngineeringDigest.java8.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;


// Primitive Streams are specialized streams
//introduced for primitive data types to improve
//performance by avoiding boxing and unboxing.
/*
 * ===========================================================
 * PRIMITIVE STREAMS IN JAVA 8
 * ===========================================================
 *
 * Java provides specialized streams for primitive types:
 *
 * 1. IntStream
 * 2. LongStream
 * 3. DoubleStream
 *
 * WHY?
 * -------------------
 * To avoid boxing/unboxing overhead.
 *
 * Faster and memory efficient than normal Stream<Integer>.
 */

public class PrimitiveStreams {

    public static void main(String[] args) {

        // =====================================================
        // 1. Creating Primitive Streams
        // =====================================================

        /*
         * IntStream from array
         */

        int[] numbers = {1, 2, 3, 4, 5};

        IntStream stream =
                Arrays.stream(numbers);

        System.out.println(
                stream.boxed().toList()
        );




        /*
         * IntStream.of()
         * -------------------
         * Creates stream directly
         */

        IntStream.of(10, 20, 30)
                .forEach(System.out::println);




        // =====================================================
        // 2. range() vs rangeClosed()
        // =====================================================

        /*
         * range(start, end)
         * -----------------------
         * End value EXCLUDED
         */

        System.out.println(

                IntStream.range(1, 5)

                        .boxed()

                        .collect(Collectors.toList())
        );

        /*
         * Output:
         * [1, 2, 3, 4]
         */




        /*
         * rangeClosed(start, end)
         * ----------------------------
         * End value INCLUDED
         */

        System.out.println(

                IntStream.rangeClosed(1, 5)

                        .boxed()

                        .collect(Collectors.toList())
        );

        /*
         * Output:
         * [1, 2, 3, 4, 5]
         */




        // =====================================================
        // 3. boxed()
        // =====================================================

        /*
         * Primitive Stream
         * ---------------------
         * IntStream contains primitive int
         *
         * Sometimes APIs require:
         * Stream<Integer>
         *
         * boxed() converts:
         *
         * int -> Integer
         */

        IntStream.rangeClosed(1, 5)

                .boxed()

                .forEach(System.out::println);




        /*
         * WITHOUT boxed():
         *
         * IntStream
         *
         * WITH boxed():
         *
         * Stream<Integer>
         */




        // =====================================================
        // 4. map() Operations
        // =====================================================

        /*
         * Primitive stream transformations
         */

        IntStream.rangeClosed(1, 5)

                .map(num -> num * 2)

                .forEach(System.out::println);

        /*
         * Output:
         * 2 4 6 8 10
         */




        /*
         * mapToObj()
         * -----------------
         * Convert primitive to object
         */

        IntStream.rangeClosed(1, 3)

                .mapToObj(num -> "Number : " + num)

                .forEach(System.out::println);




        /*
         * mapToDouble()
         */

        IntStream.rangeClosed(1, 5)

                .mapToDouble(num -> num * 1.5)

                .forEach(System.out::println);




        // =====================================================
        // 5. Random Primitive Streams
        // =====================================================

        /*
         * Random IntStream
         */

        IntStream intStream =
                new Random().ints(5);

        System.out.println(
                intStream.boxed().toList()
        );




        /*
         * Random DoubleStream
         */

        DoubleStream doubles =
                new Random().doubles(5);

        System.out.println(
                doubles.boxed().toList()
        );




        // =====================================================
        // 6. Important Terminal Operations
        // =====================================================

        /*
         * sum()
         */

        int sum =

                IntStream.rangeClosed(1, 5)

                        .sum();

        System.out.println(sum);




        /*
         * average()
         */

        System.out.println(

                IntStream.rangeClosed(1, 5)

                        .average()
        );




        /*
         * max()
         */

        System.out.println(

                IntStream.rangeClosed(1, 5)

                        .max()
        );




        /*
         * min()
         */

        System.out.println(

                IntStream.rangeClosed(1, 5)

                        .min()
        );




        /*
         * count()
         */

        System.out.println(

                IntStream.rangeClosed(1, 10)

                        .count()
        );




        // =====================================================
        // 7. summaryStatistics()
        // =====================================================

        /*
         * Gives:
         * -> sum
         * -> average
         * -> max
         * -> min
         * -> count
         */

        IntSummaryStatistics stats =

                IntStream.rangeClosed(1, 5)

                        .summaryStatistics();

        System.out.println(stats);




        // =====================================================
        // 8. filter()
        // =====================================================

        IntStream.rangeClosed(1, 10)

                .filter(num -> num % 2 == 0)

                .forEach(System.out::println);

        /*
         * Output:
         * 2 4 6 8 10
         */




        // =====================================================
        // 9. reduce()
        // =====================================================

        /*
         * Used for aggregation
         */

        int result =

                IntStream.rangeClosed(1, 5)

                        .reduce(0, Integer::sum);

        System.out.println(result);




        // =====================================================
        // 10. LongStream Example
        // =====================================================

        LongStream.rangeClosed(1, 5)

                .forEach(System.out::println);




        // =====================================================
        // 11. Parallel Streams
        // =====================================================

        /*
         * Parallel processing
         */

        IntStream.rangeClosed(1, 10)

                .parallel()

                .forEach(System.out::println);




        // =====================================================
        // 12. Convert Collection Stream to Primitive Stream
        // =====================================================

        /*
         * Collection Stream:
         * Stream<Integer>
         *
         * Primitive Stream:
         * IntStream
         */

        Arrays.asList(1, 2, 3, 4, 5)

                .stream()

                .mapToInt(Integer::intValue)

                .forEach(System.out::println);




        // =====================================================
        // 13. Convert Primitive Stream to Collection Stream
        // =====================================================

        IntStream.rangeClosed(1, 5)

                .boxed()

                .collect(Collectors.toList())

                .forEach(System.out::println);




        // =====================================================
        // 14. Real Project Use Cases
        // =====================================================

        /*
         * USE CASE 1
         * Calculate total salary
         */

        int totalSalary =

                Arrays.asList(10000, 20000, 30000)

                        .stream()

                        .mapToInt(Integer::intValue)

                        .sum();

        System.out.println(totalSalary);




        /*
         * USE CASE 2
         * Analytics System
         */

        DoubleStream analytics =
                new Random().doubles(100);

        System.out.println(
                analytics.average()
        );




        /*
         * USE CASE 3
         * Report Generation
         */

        IntStream.rangeClosed(1, 12)

                .mapToObj(month -> "Month : " + month)

                .forEach(System.out::println);




        // =====================================================
        // 15. IMPORTANT INTERVIEW QUESTIONS
        // =====================================================

        /*
         * Q1 Why Primitive Streams?
         *
         * To avoid:
         * -> Boxing
         * -> Unboxing
         *
         * Better performance.
         */




        /*
         * Q2 Difference between Stream<Integer>
         * and IntStream
         *
         * Stream<Integer>
         * -> Uses Integer objects
         * -> Slower
         * -> More memory
         *
         * IntStream
         * -> Uses primitive int
         * -> Faster
         * -> Memory efficient
         */




        /*
         * Q3 Difference between range()
         * and rangeClosed()
         *
         * range():
         * end excluded
         *
         * rangeClosed():
         * end included
         */




        /*
         * Q4 What is boxing?
         *
         * Primitive -> Wrapper object
         *
         * int -> Integer
         */




        /*
         * Q5 What is unboxing?
         *
         * Wrapper -> Primitive
         *
         * Integer -> int
         */
    }
}