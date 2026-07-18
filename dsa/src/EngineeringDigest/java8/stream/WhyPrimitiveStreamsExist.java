package EngineeringDigest.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


//Primitive Streams were introduced to improve
//performance and reduce memory usage by avoiding
//boxing and unboxing overhead during numeric operations.


// Collections cannot store primitive types because Java generics
//work only with objects. So primitives are automatically boxed
//into wrapper objects like Integer.
//
//When using Stream<Integer>, boxing and unboxing overhead occurs.
//Primitive streams like IntStream were introduced to avoid this
//overhead and improve performance for numeric operations.
/*
 * ===============================================================
 * WHY PRIMITIVE STREAMS EXIST IN JAVA?
 * ===============================================================
 *
 * Before understanding Primitive Streams,
 * first understand the problem with normal streams.
 *
 * Java Streams:
 *
 * Stream<T>
 *
 * Example:
 * Stream<Integer>
 *
 * Problem:
 * Integer is NOT primitive.
 * It is a Wrapper Object.
 *
 * That means:
 *
 * int -> Integer
 *
 * This conversion is called BOXING.
 *
 * Every Integer object:
 * -> takes memory
 * -> object creation happens
 * -> garbage collection increases
 * -> slower performance
 *
 * Java introduced:
 *
 * IntStream
 * LongStream
 * DoubleStream
 *
 * to solve this problem.
 */

public class WhyPrimitiveStreamsExist {

    public static void main(String[] args) {

        // =========================================================
        // EXAMPLE 1
        // NORMAL COLLECTION STREAM
        // =========================================================

        /*
         * List<Integer>
         *
         * Internally stores Integer objects.
         */

        List<Integer> numbers =
                Arrays.asList(1, 2, 3, 4, 5);



        /*
         * Stream<Integer>
         *
         * Internally works with Integer objects.
         */

        int total1 = numbers.stream()

                // Integer -> int
                // UNBOXING happens here
                .mapToInt(Integer::intValue)

                .sum();

        System.out.println(total1);




        // =========================================================
        // WHAT PROBLEM HAPPENS HERE?
        // =========================================================

        /*
         * Behind the scenes:
         *
         * int -> Integer (BOXING)
         *
         * while storing in List
         *
         * Integer -> int (UNBOXING)
         *
         * while calculations
         *
         * This creates overhead.
         */




        // =========================================================
        // EXAMPLE 2
        // PRIMITIVE STREAM
        // =========================================================

        /*
         * IntStream directly works with primitive int
         *
         * NO Integer objects
         * NO boxing
         * NO unboxing
         */

        int total2 =

                IntStream.rangeClosed(1, 5)

                        .sum();

        System.out.println(total2);




        // =========================================================
        // INTERNAL WORKING DIFFERENCE
        // =========================================================

        /*
         * NORMAL STREAM
         * ---------------------
         *
         * int
         *   ↓
         * Integer object created
         *   ↓
         * Stream processing
         *   ↓
         * Integer converted back to int
         *
         * Extra object creation happens.
         */




        /*
         * PRIMITIVE STREAM
         * ---------------------
         *
         * int
         *   ↓
         * Direct processing
         *
         * No wrapper object.
         * Faster execution.
         */




        // =========================================================
        // PERFORMANCE EXAMPLE
        // =========================================================

        /*
         * Suppose:
         * 10 million salary records
         *
         * Stream<Integer>
         * creates millions of Integer objects.
         *
         * Huge memory usage.
         */




        /*
         * IntStream:
         *
         * directly processes primitive int.
         *
         * Better for:
         * -> analytics
         * -> calculations
         * -> reports
         * -> finance systems
         * -> large datasets
         */




        // =========================================================
        // EXAMPLE 3
        // STREAM<Integer>
        // =========================================================

        List<Integer> salaries =

                Arrays.asList(
                        10000,
                        20000,
                        30000,
                        40000
                );



        int totalSalary =

                salaries.stream()

                        // Convert object stream
                        // to primitive stream
                        .mapToInt(Integer::intValue)

                        .sum();

        System.out.println(totalSalary);




        // =========================================================
        // WHY mapToInt() IS NEEDED?
        // =========================================================

        /*
         * salaries.stream()
         *
         * returns:
         *
         * Stream<Integer>
         *
         * sum() method does NOT exist
         * directly on Stream<Integer>
         */




        /*
         * So Java converts:
         *
         * Stream<Integer>
         *          ↓
         * IntStream
         *
         * using:
         *
         * mapToInt()
         */




        // =========================================================
        // EXAMPLE 4
        // INTSTREAM SPECIAL METHODS
        // =========================================================

        /*
         * Primitive streams provide
         * special numeric operations.
         */

        IntStream.rangeClosed(1, 5)

                .average()

                .ifPresent(System.out::println);



        IntStream.rangeClosed(1, 5)

                .max()

                .ifPresent(System.out::println);



        IntStream.rangeClosed(1, 5)

                .min()

                .ifPresent(System.out::println);




        /*
         * These methods are optimized
         * for primitive calculations.
         */




        // =========================================================
        // EXAMPLE 5
        // BOXING BACK TO OBJECT STREAM
        // =========================================================

        /*
         * Sometimes APIs require:
         *
         * Stream<Integer>
         *
         * not IntStream
         */

        IntStream.rangeClosed(1, 5)

                // int -> Integer
                .boxed()

                .forEach(System.out::println);




        // =========================================================
        // REAL PROJECT SCENARIOS
        // =========================================================

        /*
         * USE CASE 1
         * Banking System
         */

        /*
         * Calculate total transactions
         *
         * transactions.stream()
         *      .mapToDouble(Transaction::getAmount)
         *      .sum();
         */




        /*
         * USE CASE 2
         * Analytics Dashboard
         */

        /*
         * millions of records
         *
         * Primitive streams improve performance.
         */




        /*
         * USE CASE 3
         * E-commerce Reports
         */

        /*
         * Calculate:
         * -> total sales
         * -> average sales
         * -> max order amount
         */




        /*
         * USE CASE 4
         * Game Development
         */

        /*
         * score calculations
         * frame calculations
         * physics calculations
         */




        // =========================================================
        // IMPORTANT INTERVIEW QUESTIONS
        // =========================================================

        /*
         * Q1 Why Primitive Streams introduced?
         *
         * To avoid boxing/unboxing overhead.
         */




        /*
         * Q2 Difference between Stream<Integer>
         * and IntStream?
         *
         * Stream<Integer>
         * -> Wrapper objects
         * -> More memory
         * -> Slower
         *
         * IntStream
         * -> Primitive int
         * -> Faster
         * -> Optimized
         */




        /*
         * Q3 Why sum() not directly available
         * on Stream<Integer>?
         *
         * Because Stream<T>
         * is generic object stream.
         *
         * Numeric operations belong to
         * primitive specialized streams.
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


/*


INTERNAL MEMORY VIEW
What you write
List<Integer> list =
        Arrays.asList(1, 2, 3);
What compiler does internally
List<Integer> list =
        Arrays.asList(
                Integer.valueOf(1),
                Integer.valueOf(2),
                Integer.valueOf(3)
        );

This is:

AUTOBOXING


1,2,3 literals
      ↓
Autoboxing
      ↓
Integer objects stored in List
      ↓
Stream<Integer>
      ↓
mapToInt()
      ↓
Unboxing
      ↓
primitive int
      ↓
sum()



WHY INTSTREAM IS FASTER?

primitive int
      ↓
direct processing
      ↓
sum/average/max

NO:

boxing
unboxing
wrapper objects






 */