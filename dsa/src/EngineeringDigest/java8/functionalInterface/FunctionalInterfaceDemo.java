package EngineeringDigest.java8.functionalInterface;

public class FunctionalInterfaceDemo {
    // An interface with exactly one abstract method is called a Functional Interface.
    // It can have multiple default/static methods but only one abstract method.
    public static void main(String[] args) {

    }
}

/*
Evolution — 4 Ways to Do the Same Thing
1️⃣ Implementation Class (Old School)

// Step 1 - Define interface
@FunctionalInterface
interface Greeter {
    void greet(String name);
}

// Step 2 - Create a separate class
class GreeterImpl implements Greeter {
    @Override
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }
}

// Step 3 - Use it
public class Main {
    public static void main(String[] args) {
        Greeter greeter = new GreeterImpl(); // create object
        greeter.greet("Rahul");
    }
}
Too much code — separate class just for one method

2️⃣ Anonymous Class (Slightly Better)

public class Main {
    public static void main(String[] args) {

        // No separate class needed, but still verbose
        Greeter greeter = new Greeter() {
            @Override
            public void greet(String name) {
                System.out.println("Hello, " + name);
            }
        };

        greeter.greet("Rahul");
    }
}
 Better — no separate file, but still too much boilerplate

 3️⃣ Lambda Expression (Clean & Modern)

 public class Main {
    public static void main(String[] args) {

        // Just one line!
        Greeter greeter = name -> System.out.println("Hello, " + name);

        greeter.greet("Rahul");
    }
}
 Clean, concise, readable — same result!

 4️⃣ Method Reference (Even Cleaner)

 public class Main {
    public static void main(String[] args) {

        // When lambda just calls an existing method
        Greeter greeter = System.out::println;

        greeter.greet("Hello Rahul");
    }
}


Lambda Syntax Breakdown

// Full syntax
(parameters) -> { body }

// Examples:
() -> System.out.println("No params")
(x) -> x * 2                          // single param, expression body
x -> x * 2                            // parentheses optional for single param
(x, y) -> x + y                       // two params
(x, y) -> {
    int sum = x + y;
    return sum;                        // block body needs return
}

Built-in Functional Interfaces (Java 8)
Java provides ready-made functional interfaces in java.util.function package.
You don't need to create your own every time!

1️⃣ Predicate<T> — Takes input, returns boolean
import java.util.function.Predicate;

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
 */