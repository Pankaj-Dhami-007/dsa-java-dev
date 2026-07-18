package java_8_CodingQ.primitiveStream;

import java.util.Arrays;

public class ArrayMax {
    public static void main(String[] args) {
        int[] arr = {5,1,9,3};

        // Java 8
        int max = Arrays.stream(arr).max().getAsInt();
        System.out.println("Java 8: " + max);

        // Without Java 8
        System.out.println("Without Java 8: " + findMax(arr));
    }

    private static int findMax(int[] arr) {
        int max = Integer.MIN_VALUE;
        for (int x : arr) {
            if (x > max) max = x;
        }
        return max;
    }
}
