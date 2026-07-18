package java_8_CodingQ.inbuildClass;


import java.util.*;
import java.util.stream.Collectors;

public class PartitionEvenOdd {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6);

        // Without Java 8
        List<Integer> even = new ArrayList<>();
        List<Integer> odd = new ArrayList<>();

        for (int x : list) {
            if (x % 2 == 0) even.add(x);
            else odd.add(x);
        }
        System.out.println("Without Java 8: Even=" + even + ", Odd=" + odd);

        // Java 8
        Map<Boolean, List<Integer>> res =
                list.stream().collect(Collectors.partitioningBy(x -> x % 2 == 0));
        System.out.println("Java 8: " + res);
    }
}
