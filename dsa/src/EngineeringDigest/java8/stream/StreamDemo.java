package EngineeringDigest.java8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class StreamDemo {

    public static void main(String[] args) {

        // why
        // Simplifies data processing
        // Improves readability & maintainability
        // Supports functional programming (lambda, method reference)
        // Enables easy parallel processing

        // feature introduced in Java 8
        // process collections of data in a functional and declarative manner
        // // Stream = declarative data processing pipeline (what, not how)
        // Simplify Data Processing
        // Embrace Functional Programming
        // Enable Easy Parallelism

        //// What is stream ?
        // a sequence of elements supporting functional and declarative programing
        // Streams provide a declarative way to process data

        //// How to Use Streams ?
        // Source, intermediate operations & terminal operation
        // SOURCE → INTERMEDIATE OPERATIONS → TERMINAL OPERATION

        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        // find even count
        // traditional way
        int count = 0;
        for(int i : numbers){
            if(i %2 == 0){
                count++;
            }
        }
        System.out.println(count);

        // stream way
        System.out.println(numbers.stream().filter(x -> x % 2 == 0).count());

        //// Creating Streams
        // 1. From collections
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        Stream<Integer> stream = list.stream();
        // 2. From Arrays
        String[] array = {"a", "b", "c"};
        Stream<String> stream1 = Arrays.stream(array);
        // 3. Using Stream.of()
        Stream<String> stream2 = Stream.of("a", "b");
        // 4. Infinite streams
        Stream.generate(() -> 1); // use limit
        Stream.iterate(1, x -> x + 1);
    }
}

// STREAM (Java 8)

// Definition:
// Stream = sequence of elements used for processing data (NOT storing)
// A Stream is a sequence of elements used to process data, not store it.
// It supports functional and declarative programming.
// Focus: what to do, not how to do it.

// Key Points:
// - Does not store data
// - Does not modify original collection
// - Lazy execution
// - Can be used only once

/*

STREAM PIPELINE
// 1. Source
// 2. Intermediate Operations
// 3. Terminal Operation

list.stream()          // Source
    .filter(x -> x%2==0) // Intermediate
    .map(x -> x*2)       // Intermediate
    .count();            // Terminal


    TYPES OF OPERATIONS
    Intermediate (Lazy):

    Return a new Stream , Do NOT execute immediately
    filter() , map() ,sorted(), distinct(), limit() ,skip()
    Returns Stream and Returns Stream

    Terminal (Triggers execution)

    Trigger execution , Produce result
    forEach(), collect(), count(), reduce(), findFirst(), anyMatch()
    Produces result

 // 1. Stream is lazy (no terminal → no execution)
// 2. Stream can be used only once
// 3. Does NOT modify original collection
// 4. Avoid side effects (no external mutation)

// Collection → stores data
// Stream     → processes data

 */
