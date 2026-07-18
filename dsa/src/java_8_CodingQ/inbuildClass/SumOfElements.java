package java_8_CodingQ.inbuildClass;

import java.util.Arrays;
import java.util.List;

public class SumOfElements {
    public static void main(String[] args) {
       List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        System.out.println(getSum(list));

        System.out.println(list.stream()
                .mapToInt(x->x).sum());
    }

    private static int getSum(List<Integer> list){
        int sum = 0;
        for(Integer i : list){
            sum += i;
        }
        return sum;
    }
}
