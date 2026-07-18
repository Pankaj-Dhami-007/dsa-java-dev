package java_8_CodingQ.inbuildClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SortList {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>(Arrays.asList(5,2,8,1));
        Collections.sort(list);
        System.out.println("Without Java 8: " + list);

        List<Integer> res = list.stream().sorted().toList();
        System.out.println("Java 8: " + res);
    }
}
