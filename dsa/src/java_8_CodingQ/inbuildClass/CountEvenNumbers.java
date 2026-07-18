package java_8_CodingQ.inbuildClass;

import java.util.Arrays;
import java.util.List;

public class CountEvenNumbers {
    public static void main(String[] args) {
       List<Integer> list =  Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
        System.out.println("even count from gatEvenCount "+getEvenCount(list));

        long evenCount = list.stream()
                .filter(x-> x % 2 == 0)
                .count();
        System.out.println("using java-8 even count = "+evenCount);
    }

    static int getEvenCount(List<Integer> list){
        int count = 0;
        for(int i : list){
            if(i % 2 == 0){
                count++;
            }
        }
        return count;
    }
}
