package java_8_CodingQ.primitiveStream;

import java.util.Arrays;

public class ArraySum {
    public static void main(String[] args) {
        int[] arr = {1,2,3,4,5};

        // Java 8
        int sum = Arrays.stream(arr).sum();
        System.out.println("Java 8: " + sum);

        // Without Java 8
        System.out.println("Without Java 8: " + sumArray(arr));
    }

    private static int sumArray(int[] arr) {
        int sum = 0;
        for (int x : arr) sum += x;
        return sum;
    }
}
