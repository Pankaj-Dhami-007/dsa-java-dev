package interviewQ.java8;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class PrimitiveStream {

    /*
    Problem: Every number is boxed (int → Integer) and unboxed back (Integer → int) during calculation.
    This adds overhead.
     */
    public static int findSum(List<Integer> list){
        return list.stream().mapToInt(Integer::intValue).sum();
    }

    static int findSumWithoutBoxing(){
       int sum = IntStream.range(1, 11).sum();
       return sum;
    }

    static void arraysStreamMethod(){
        String[] names = {"pankaj", "dhami", "java"};
        Stream<String> stream = Arrays.stream(names);
        stream.forEach(System.out::println);

       // for primitive arr
        int[] nums = {1, 2, 3, 4};
        IntStream intStream = Arrays.stream(nums);
        intStream.forEach(System.out::print);
    }
    public static void main(String[] args) {

      List<Integer> list = IntStream.range(1, 11)
                .boxed()
                .toList();
        System.out.println(findSum(list));

        arraysStreamMethod();

    }
}
