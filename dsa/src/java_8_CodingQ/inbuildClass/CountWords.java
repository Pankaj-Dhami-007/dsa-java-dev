package java_8_CodingQ.inbuildClass;


import java.util.*;
import java.util.stream.Collectors;

public class CountWords {
    public static void main(String[] args) {
        String str = "hello world hello";
        // Without Java 8
        Map<String, Integer> map = new HashMap<>();
        for (String word : str.split(" ")) {
            map.put(word, map.getOrDefault(word, 0) + 1);
        }
        System.out.println("Without Java 8: " + map);

        // Java 8
        Map<String, Long> res = Arrays.stream(str.split(" "))
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));
        System.out.println("Java 8: " + res);
    }
}
