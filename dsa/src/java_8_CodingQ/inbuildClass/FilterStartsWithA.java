package java_8_CodingQ.inbuildClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FilterStartsWithA {
    public static void main(String[] args) {
        List<String> list = Arrays.asList("Apple", "Banana", "Avocado");
        // Without Java 8
        List<String> res1 = new ArrayList<>();
        for (String s : list) {
            if (s.startsWith("A")) res1.add(s);
        }
        System.out.println("Without Java 8: " + res1);

        // Java 8
        List<String> res2 = list.stream()
                .filter(s -> s.startsWith("A"))
                .toList();
        System.out.println("Java 8: " + res2);
    }
}
