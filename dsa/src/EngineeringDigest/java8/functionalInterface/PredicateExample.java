package EngineeringDigest.java8.functionalInterface;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

// Predicate<T> — Takes input, returns boolean
public class PredicateExample {
    public static void main(String[] args) {

        // Check if number is even
        Predicate<Integer> isEven = n -> n % 2 == 0;

        System.out.println(isEven.test(4));  // true
        System.out.println(isEven.test(7));  // false

        // Combining predicates
        Predicate<Integer> isPositive = n -> n > 0;
        Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);

        System.out.println(isEvenAndPositive.test(4));   // true
        System.out.println(isEvenAndPositive.test(-4));  // false

        // Real use case - filter users
        List<String> names = Arrays.asList("Rahul", "Ram", "Priya", "Rohit");
        Predicate<String> startsWithR = name -> name.startsWith("R");

        names.stream()
                .filter(startsWithR)
                .forEach(System.out::println); // Rahul, Ram, Rohit
    }
}
