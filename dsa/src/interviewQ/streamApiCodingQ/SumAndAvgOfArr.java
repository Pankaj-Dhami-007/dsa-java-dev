package interviewQ.streamApiCodingQ;

import java.util.*;


/*
reduce is used for performing aggregation operation
 */
public class SumAndAvgOfArr {
    public static void main(String[] args) {

        List<Integer> list = Arrays.asList(12, 67, 86,53, 11, 90, 82, 86, 25);
       int sum =   list.stream()
//                 .mapToInt(num-> num.intValue())
                 .mapToInt(Integer::byteValue)
                 .sum();
        System.out.println(sum);
        System.out.println();
       Optional<Integer> optNum = list.stream()
                .reduce((x, y)-> x+y);
       optNum.ifPresent(System.out::println);

        System.out.println();
        int[] arr = new int[]{10, 24, 25, 21, 86, 95, 100};
         int totalSum =  Arrays.stream(arr).sum();
        System.out.println(totalSum);

        System.out.println("avg..........");

   OptionalDouble optAvg = list.stream()
//             .mapToDouble(Double::doubleValue)
             .mapToDouble(num-> num.doubleValue())
             .average();
   if(optAvg.isPresent()){
            System.out.println(optAvg);
        }

        System.out.println("max no in arr");

        System.out.println(Arrays.stream(arr).max());
        System.out.println();

        System.out.println("max num in the list");
        list.stream()
                .max(Comparator.comparing(Integer:: valueOf)).get();
        list.stream()
                .mapToInt(Integer::intValue).max();
        list.stream()
                .sorted((x, y)-> y-x).findFirst().get();

    }
}
