package EngineeringDigest.java8.stream;

import java.util.*;
import java.util.stream.Collectors;

public class CollectorsDemo {
    // Collectors = bridge between Stream and result
    public static void main(String[] args) {
        // Collectors is a utility class in java.util.stream
        // It provides predefined implementations of Collector
        // provides a set of methods to create common collectors
        // Used with collect() terminal operation
        // Stream → collect() → Collectors → Result (List, Set, Map, etc.)

        // why
        // Convert stream into collection (List, Set, Map)
        // Perform aggregation (sum, avg, count)
        // Enable grouping, partitioning
        // Transform data while collecting
        // syntax -  stream.collect(Collectors.someOperation());

        // 1. Collecting to a List
        // Keeps order, allow duplicates
        List<String> names = Arrays.asList("Alice", "Bob", "Charlie");
        List<String> res = names.stream()
                .filter(name -> name.startsWith("A"))
                .collect(Collectors.toList());
        System.out.println(res);

        // 2. Collecting to a Set
        // Removes duplicates , No guaranteed order
        List<Integer> nums = Arrays.asList(1, 2, 2, 3, 4, 4, 5);
        Set<Integer> set = nums.stream().collect(Collectors.toSet());
        System.out.println(set);

        // 3. Collecting to a Specific Collection
        // Full control over collection type
        // convert any like ll, arrayList....
        ArrayDeque<String> collect = names.stream().collect(Collectors.toCollection(() -> new ArrayDeque<>()));

        // 4. Joining Strings
        // Concatenates stream elements into a single String
        // Supports delimiter, prefix, suffix
        String concatenatedNames = names.stream().map(String::toUpperCase).collect(Collectors.joining(", "));
        System.out.println(concatenatedNames);

        // 5. Summarizing Data
        // Generates statistical summary (count, sum, min, average, max)

        List<Integer> numbers = Arrays.asList(2, 3, 5, 7, 11);
        IntSummaryStatistics stats = numbers.stream().collect(Collectors.summarizingInt(x -> x));
        System.out.println("Count: " + stats.getCount());
        System.out.println("Sum: " + stats.getSum());
        System.out.println("Min: " + stats.getMin());
        System.out.println("Average: " + stats.getAverage());
        System.out.println("Max: " + stats.getMax());

        // 6. Calculating Averages
        Double average = numbers.stream().collect(Collectors.averagingInt(x -> x));
        System.out.println("Average: " + average);

        // 7. Counting Elements
        Long count = numbers.stream().collect(Collectors.counting());
        System.out.println("Count: " + count);

        // 8. Grouping Elements    Important
        // basic , Grouping with Downstream Collector, Custom Map Type
        List<String> words = Arrays.asList("hello", "world", "java", "streams", "collecting");
        System.out.println(words.stream().collect(Collectors.groupingBy(String::length)));
        System.out.println(words.stream().collect(Collectors.groupingBy(String::length, Collectors.joining(", "))));
        System.out.println(words.stream().collect(Collectors.groupingBy(String::length, Collectors.counting())));
        TreeMap<Integer, Long> treeMap = words.stream().collect(Collectors.groupingBy(String::length, TreeMap::new, Collectors.counting()));
        System.out.println(treeMap);

        // 9. Partitioning Elements
        //  Partitions elements into two groups (true and false) based on a predicate
        // splitting data into ONLY 2 groups Based on a boolean condition (predicate)
        // Basic Syntax -> Collectors.partitioningBy(predicate)
        // with downstream -> Collectors.partitioningBy(predicate, downstream)
        List<Integer> nums1 = Arrays.asList(1,2,3,4,5,6);
        Map<Boolean, List<Integer>> map =
                nums1.stream()
                        .collect(Collectors.partitioningBy(x -> x % 2 == 0));

        // o/p
        // {
        //  true = [2,4,6],
        //  false = [1,3,5]
        // }
        //  true → condition satisfied
        // false → condition not satisfied
        // Default downstream = toList()
        System.out.println(words.stream().collect(Collectors.partitioningBy(x -> x.length() > 5)));

        // 10. Mapping and Collecting
        // Applies a mapping function before collecting
        // Mapping (Downstream Transformation)
        // Used inside grouping/collect
        System.out.println(words.stream().collect(Collectors.mapping(x -> x.toUpperCase(), Collectors.toList())));

        // 11. toMap

//        Map<String, Integer> map =
//                fruits.stream()
//                        .collect(Collectors.toMap(
//                                x -> x,
//                                x -> x.length()
//                        )); // This will throw exception: Duplicate Key Problem

        // Fix using Merge Function
//        Map<String, Integer> freq =
//                words.stream()
//                        .collect(Collectors.toMap(
//                                k -> k,
//                                v -> 1,
//                                (x, y) -> x + y
//                        )); // Used for frequency counting

        // Example 1: Collecting Names by Length
        List<String> l1 = Arrays.asList("Anna", "Bob", "Alexander", "Brian", "Alice");
        System.out.println(l1.stream().collect(Collectors.groupingBy(String::length)));

        // Example 2: Counting Word Occurrences
        String sentence = "hello world hello java world";
        System.out.println(Arrays.stream(sentence.split(" ")).collect(Collectors.groupingBy(x -> x, Collectors.counting())));

        // Example 3: Partitioning Even and Odd Numbers
        List<Integer> l2 = Arrays.asList(1, 2, 3, 4, 5, 6);
        System.out.println(l2.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0)));

        // Example 4: Summing Values in a Map
        Map<String, Integer> items = new HashMap<>();
        items.put("Apple", 10);
        items.put("Banana", 20);
        items.put("Orange", 15);
        System.out.println(items.values().stream().reduce(Integer::sum));
        System.out.println(items.values().stream().collect(Collectors.summingInt(x -> x)));

        // Example 5: Creating a Map from Stream Elements
        List<String> fruits = Arrays.asList("Apple", "Banana", "Cherry");
        System.out.println(fruits.stream().collect(Collectors.toMap(x -> x.toUpperCase(), x -> x.length())));

        // Example 6:
        List<String> words2 = Arrays.asList("apple", "banana", "apple", "orange", "banana", "apple");
        System.out.println(words2.stream().collect(Collectors.toMap(k -> k, v -> 1, (x, y) -> x + y)));


        // Without Downstream (Default Behavior)
        Map<Integer, List<String>> map2 =
                words.stream()
                        .collect(Collectors.groupingBy(String::length)); // Default downstream = Collectors.toList()

        // ex o/p - >
        // {
        //  5 = [hello, world],
        //  4 = [java]
        //  }

        // With Downstream (Custom Behavior)
        Map<Integer, Long> map1 =
                words.stream()
                        .collect(Collectors.groupingBy(
                                String::length,
                                Collectors.counting()   // downstream
                        ));
        // {5=2, 4=1}
        // Instead of list → you get count

        // groupingBy(key, downstream)
        // key → how to group
        // downstream → what to do inside group
        // Works with Partitioning Too
        // Use it when: You don’t want List
        // You want: count, sum , avg , transform data, custom collection
        // Downstream collector = operation applied to each group AFTER grouping/partitioning

    }
}

/*

What is a Downstream Collector?
Downstream = “what to do AFTER grouping/partitioning”
When you use: Collectors.groupingBy(...)
First → elements are grouped
Then → you can decide what to do with each group
That “what to do next” part is called downstream collector


 */
