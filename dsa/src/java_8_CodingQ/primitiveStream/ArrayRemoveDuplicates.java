package java_8_CodingQ.primitiveStream;

import java.util.*;
import java.util.stream.Collectors;

public class ArrayRemoveDuplicates {
    /*
    "boxed() converts primitive stream (IntStream) into object stream (Stream<Integer>)
    so that we can use operations like collect which require objects."
     */
    public static void main(String[] args) {
        int[] arr = {1,2,2,3,4,4,5};

        // Java 8
        List<Integer> res = Arrays.stream(arr)
                .boxed() // IntStream → Stream<Integer>
                // or .mapToObj(x -> Integer.valueOf(x))
                .distinct()
                .collect(Collectors.toList());
        System.out.println("Java 8: " + res);

        // Without Java 8
        System.out.println("Without Java 8: " + removeDuplicates(arr));
    }

    private static List<Integer> removeDuplicates(int[] arr) {
        Set<Integer> set = new HashSet<>();
        for (int x : arr) {
            set.add(x);
        }
        return new ArrayList<>(set);
    }
}
