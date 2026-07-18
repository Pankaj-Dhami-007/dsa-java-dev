package java_8_CodingQ.inbuildClass;


import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class FindMaxElement {
    public static void main(String[] args) {
      List<Integer> list = Arrays.asList(11, 33, 12, -900, 444, 213);
        System.out.println("max from getMax method  "+getMax(list));
        // using java 8

        // comparingInt
      int max = list.stream()
                .max(Comparator.comparingInt(x->x)).get();
        System.out.println("Max: " + max);

        // comparing()
        list.stream().max(Comparator.comparing(x -> x)).get();

        // method reference
        // Each element is an Integer object
        // JVM has to do boxing/unboxing, slower
        // .max((a, b) -> a.compareTo(b))
        list.stream().max(Integer::compareTo).get();

        // best Primitive Stream
        // Stream<Integer>  →  IntStream (int primitives)
        // No boxing, faster
        list.stream().mapToInt(x -> x).max().getAsInt();

        // reduce
        list.stream().reduce((a, b) -> a > b ? a : b).get();

    }

    private static int getMax(List<Integer> list){
        int max = Integer.MIN_VALUE;
        for(int i : list){
            max = Math.max(max, i);
        }
        return max;
    }
}
