package interviewQ.streamApiCodingQ;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/*

Optional<T> max(Comparator<? super T> comparator)
the max() method is mainly used with Streams to find the maximum element according to a given Comparator.
 */

public class ListOfStringQ {

    static void convertToUpperCase(){
        List<String> fruits = Arrays.asList(
                "Watermelon",
                "Banana",
                "Mango",
                "Strawberry",
                "Grapes",
                "Pineapple",
                "Papaya",
                "Orange",
                "Apple",
                "Guava",
                "Apple"
        );
     fruits.stream()
                .map(x-> x.toUpperCase())//map(String :: toUpperCase)
                .sorted()
                .forEach(x-> System.out.println(x));

        System.out.println("String > 5");
     fruits.stream()
             .filter(x-> x.length() > 5)
             .forEach(System.out :: println);

        System.out.println("remove duplicates");

        fruits.stream()
                .distinct()
                .forEach(s-> System.out.println(s));

        System.out.println("contain str");

        fruits.stream()
                .filter(s-> s.contains("berry"))
                .forEach(System.out::println);

        System.out.println("remove with start a particular word or chr");

        fruits.stream()
                .filter(s-> s.startsWith("B"))
                .forEach(System.out::println);

        System.out.println();
        System.out.println();
        System.out.println("find the 1st element start with letter-------------------------------");

       Optional<String> opt =  fruits.stream()
                .filter(s-> s.startsWith("A"))
                .findFirst();// .get()= string
        System.out.println(opt);

        System.out.println();

        fruits.stream()
                .map(s-> s.length())
                .forEach(System.out::println);


        System.out.println();
        System.out.println("sort based on the length.............");

//        fruits.stream()
//                .sorted((x, y)-> Integer.compare(x.length(), y.length()))
//                .forEach(System.out::println);

        fruits.stream()
                .sorted(Comparator.comparingInt(String::length))
                .forEach(System.out::println);

        System.out.println(".............................................");
        System.out.println("max length string");

        fruits.stream()
                .max(Comparator.comparing(String::length))   // use max with comparator
                .ifPresent(System.out::println);

    }
    public static void main(String[] args) {

        convertToUpperCase();
    }
}

