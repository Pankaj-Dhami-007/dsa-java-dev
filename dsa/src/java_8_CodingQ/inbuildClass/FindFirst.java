package java_8_CodingQ.inbuildClass;

import java.util.Arrays;
import java.util.List;

public class FindFirst {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(10,20,30);

        // Without Java 8
        if (!list.isEmpty()) {
            System.out.println("Without Java 8: " + list.get(0));
        }
        // Java 8
        System.out.println("Java 8: " + list.stream().findFirst().get());
    }
}
