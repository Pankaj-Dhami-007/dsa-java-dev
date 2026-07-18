package interviewQ.java8;


/*
map()-->
The map() operation transforms each element of a stream into another object using a function.

flatMap()--->

What is Flattening?
Flattening is the process of converting a nested structure (like a stream of streams)
into a single, flat structure.

The flatMap() operation transforms each element into a stream and
then flattens all the streams into a single stream.(Single flattened stream)(Flattened stream of elements)
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MapVsFlatMap {

    public static void main(String[] args) {

        //map()>>>>>>>>>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<
        // Using map (returns stream of arrays....,)
        // Result: List of String arrays...,
        List<String> names = Arrays.asList("pankaj", "singh", "dhami");
        // Transform each string to uppercase
      List<String> upperCaseNames =  names.stream()
                //.map(str-> str.toUpperCase()).collect(Collectors.toList());
                .map(String :: toUpperCase).collect(Collectors.toList());// Result: ["JOHN", "MARY", "PETER"]

        // Transform strings to their lengths

       List<Integer> namesLength =  names.stream()
                //.map(s-> s.length()).collect(Collectors.toUnmodifiableList());
                .map(String :: length).collect(Collectors.toList()); // Result: [4, 4, 5]


//flatmap()>>>>>>>>>>>>>>>>>>>>>><<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
// Using flatMap (returns flattened stream)
        List<List<String>> nestedLists = Arrays.asList(
                Arrays.asList("a", "b", "c"),
                Arrays.asList("d", "e"),
                Arrays.asList("f", "g", "h")
        );
        // Flatten nested lists into a single list

        List<String> flatList =  nestedLists.stream()
                .flatMap(List :: stream)
                //.flatMap(list-> list.stream())
                .collect(Collectors.toList());

        // Using map - returns Stream<List<String>>
        Stream<List<String>> streamOfLists = nestedLists.stream().map(list -> list); // Still nested!

        List<List<String>> result = streamOfLists.collect(Collectors.toList());
// Result: [["a", "b", "c"], ["d", "e"], ["f", "g", "h"]] - STILL NESTED!


    }
}
