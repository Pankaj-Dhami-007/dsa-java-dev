package arrays.basic_questions;

import java.util.Arrays;

/*
running sum--> 1, 2, 3, 4, 5
--> o/p-> 1, 3, 6, 10, 15
*/
public class RunningSum {

    // Approach 1: In-place (modifies original array)
    public int[] runningSum_2(int[] nums) {
        for (int i = 1; i < nums.length; i++) {
            nums[i] += nums[i - 1];
        }
        return nums;
    }

    // Approach 2: Using new array (does not modify original)
    public int[] runningSum(int[] nums) {
        int[] res = new int[nums.length];
        res[0] = nums[0];

        for (int i = 1; i < nums.length; i++) {
            res[i] = res[i - 1] + nums[i];
        }
        return res;
    }

    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5};
        RunningSum rs = new RunningSum();

        // Call method 1 (new array)
        int[] result1 = rs.runningSum(arr);
        System.out.println("Result (new array): " + Arrays.toString(result1));

        // Call method 2 (in-place)
        int[] arr2 = {1, 2, 3, 4, 5};  // new array to keep original intact
        int[] result2 = rs.runningSum_2(arr2);
        System.out.println("Result (in-place): " + Arrays.toString(result2));
    }
}
