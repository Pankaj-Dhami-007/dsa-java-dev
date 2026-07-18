package interviewQ;

import java.util.Arrays;
import java.util.List;

public class FinalKeyword {

    public static void main(String[] args) {
        final int a = 9;
         //a = 20;// Compile error
        final int[] arr= {1, 2};
        arr[0] = 100;

        String s = "abc";
        s.concat("def");

        String s1 = s.concat("dhami");
        System.out.println(s); // abc
        System.out.println(s1);

        List<Integer> list = Arrays.asList(1, 2, 3);
        list.add(4); // UnsupportedOperationException

    }
}
/*
Key: final on primitive vs reference types.

 */
