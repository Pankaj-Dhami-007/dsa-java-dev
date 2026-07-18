package interviewQ.streamApiCodingQ;

import java.util.Arrays;
import java.util.List;

public class RemovingDuplicates {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(12, 67, 86,53, 11, 90, 82, 86, 25);
        int[] arr = new int[]{10, 24, 25, 21, 86, 95, 100};

        list.stream()
                .distinct().forEach(System.out::println);
        Arrays.stream(arr).distinct().forEach(System.out::println);
    }
}
