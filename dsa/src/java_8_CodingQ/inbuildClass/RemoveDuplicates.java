package java_8_CodingQ.inbuildClass;

import java.util.*;

public class RemoveDuplicates {
    public static void main(String[] args) {
       List<Integer> list = Arrays.asList(1, 2, 2, 3, 4, 4, 5, 6);
        System.out.println(removeDuplicates(list));

        // java 8
        System.out.println(list.stream()
                .distinct().toList());
    }
    private static List<Integer> removeDuplicates(List<Integer> list){
        Set<Integer> set = new HashSet<>(list);
        return new ArrayList<>(set);
    }
}
