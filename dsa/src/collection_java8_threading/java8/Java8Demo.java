package EngineeringDigest.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.*;
import java.util.stream.Collectors;

public class Java8Demo {
    // Goal:
// - Write less code
// - More readable
// - Functional programming support
    public static void main(String[] args) {

        // Streams

        // Java 8 --> minimal code, functional programing
        // Java 8 --> lambda expression,Functional Interfaces, Streams, Date & Time API

        // lambda expression
        // lambda expression is an anonymous function ( no name, no return type, no access modifier )


//        new Runnable() {
//            public void run() {
//                System.out.println("Hello");
//            }
//        }

        Thread t1 = new Thread(() -> {
            System.out.println("Hello");
        });

        MathOperation sumOperation = (a, b) -> a + b;
        MathOperation subtractOperation = (a, b) -> a - b;
        int res = sumOperation.operate(1, 2);
        System.out.println(res);

        // Predicate --> Functional interface ( Boolean valued function )
        // A Predicate is a functional interface in Java (from java.util.function) that
        // takes one input and returns a boolean (true/false).
        // t is mainly used for filtering or condition checking.
        Predicate<Integer> isEven = x -> x % 2 == 0;
        System.out.println(isEven.test(4));
        Predicate<String> isWordStartingWithA = x -> x.toLowerCase().startsWith("a");
        Predicate<String> isWordEndingWithT = x -> x.toLowerCase().endsWith("t");
        Predicate<String> and = isWordStartingWithA.and(isWordEndingWithT);
        System.out.println(and.test("Akshay"));


        // Function --> work for you
        // A Function is a functional interface in Java (java.util.function) that
        // takes one input and returns one output.
        // It is used for transforming data.
        // syntax -- Function<T, R>  where T = input type , R = return type
        // Commonly used in map() in streams
        // Function is a functional interface that takes one input and returns a transformed output
        Function<Integer, Integer> doubleIt = x -> 2 * x;
        Function<Integer, Integer> tripleIt = x -> 3 * x;
        System.out.println(doubleIt.andThen(tripleIt).apply(20));
        System.out.println(tripleIt.andThen(doubleIt).apply(20)); // same
        System.out.println(doubleIt.compose(tripleIt).apply(20)); // same
        System.out.println(doubleIt.apply(100));
        Function<Integer, Integer> identity = Function.identity();
        Integer res2 = identity.apply(5);
        System.out.println(res2);

        // Consumer
        // A Consumer is a functional interface in Java (java.util.function) that
        // takes one input but does not return anything.
        // syntax --  Consumer<T>
        // Commonly used in forEach()
        // Consumer is a functional interface that takes input and performs an action without returning a result.

        Consumer<Integer> print = x -> System.out.println(x);
        print.accept(51);
        List<Integer> list = Arrays.asList(1, 2, 3);
        Consumer<List<Integer>> printList = x -> {
            for (int i : x) {
                System.out.println(i);
            }
        };
        printList.accept(list);


        // Supplier
        // A Supplier is a functional interface in Java (java.util.function) that
        // does not take any input but returns a value.
        // It is used for generating or supplying data.
        // syntax -   Supplier<T>
        // Supplier is a functional interface that provides data without taking any input.

        Supplier<String> giveHelloWorld = () -> "Hello World";
        System.out.println(giveHelloWorld.get());

        // combined example
        Predicate<Integer> predicate = x -> x % 2 == 0;
        Function<Integer, Integer> function = x -> x * x;
        Consumer<Integer> consumer = x -> System.out.println(x);
        Supplier<Integer> supplier = () -> 100;

        if (predicate.test(supplier.get())) {
            consumer.accept(function.apply(supplier.get()));
        }

        // BiPredicate, BiConsumer, BiFunction

        // A BiPredicate takes two inputs and returns a boolean (true/false).
        // Used for checking condition on two values

        // A BiConsumer takes two inputs and returns nothing.
        // Used for performing operations
        BiConsumer<String, Integer> print1 = (name, age) ->
                System.out.println(name + " " + age);
        print1.accept("Pankaj", 25);

        BiPredicate<Integer, Integer> isSumEven = (x, y) -> (x + y) % 2 == 0;
        System.out.println(isSumEven.test(5, 5));
        BiConsumer<Integer, String> biConsumer = (x, y) -> {
            System.out.println(x);
            System.out.println(y);
        };

        // BiFunction takes two inputs and returns a result.
        // Used for transforming/computing values
        BiFunction<String, String, Integer> biFunction = (x, y) -> (x + y).length();
        System.out.println(biFunction.apply("a", "bc"));

        // UnaryOperator, BinaryOperator
        UnaryOperator<Integer> a = x -> 2 * x;
        BinaryOperator<Integer> b = (x, y) -> x + y;

        // Method reference --> use method without invoking & in place of lambda expression
        List<String> students = Arrays.asList("Ram", "Shyam", "Ghanshyam");
        students.forEach(x -> System.out.println(x));
        students.forEach(System.out::println);

        // Constructor reference
        List<String> names = Arrays.asList("A", "B", "C");
        List<MobilePhone> mobilePhoneList = names.stream().map(MobilePhone::new).collect(Collectors.toList());
    }
}

class MobilePhone{
    String name;

    public MobilePhone(String name) {
        this.name = name;
    }
}

@FunctionalInterface
interface MathOperation {
    int operate(int a, int b);
}
