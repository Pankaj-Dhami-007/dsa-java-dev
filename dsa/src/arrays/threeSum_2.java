package arrays;

import java.util.Arrays;

public class threeSum_2 {

    public static int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums); // Step 1: sort the array
        int closestSum = Integer.MAX_VALUE; // To store closest sum found so far
        int minDiff = Integer.MAX_VALUE;    // To store the minimum difference so far

        // Step 2: Fix one element
        for (int i = 0; i < nums.length - 2; i++) {
            int j = i + 1;                  // Left pointer
            int k = nums.length - 1;        // Right pointer

            // Step 3: Two pointer search
            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                int diff = Math.abs(sum - target);  // Distance from target

                // Step 4: Update closest sum if we found a smaller difference
                if (diff < minDiff) {
                    minDiff = diff;
                    closestSum = sum;
                }

                // Step 5: Move pointers based on comparison with target
                if (sum < target) {
                    j++;  // Need bigger sum
                } else if (sum > target) {
                    k--;  // Need smaller sum
                } else {
                    // Perfect match found
                    return sum;
                }
            }
        }
        return closestSum;
    }
    public static void main(String[] args) {

        int[] nums1 = {-1, 2, 1, -4};
        int target1 = 1;
        System.out.println("Closest sum: " + threeSumClosest(nums1, target1));
        // Expected Output: 2

        int[] nums2 = {0, 0, 0};
        int target2 = 1;
        System.out.println("Closest sum: " + threeSumClosest(nums2, target2));
        // Expected Output: 0

        int[] nums3 = {1, 1, -1, -1, 3};
        int target3 = -1;
        System.out.println("Closest sum: " + threeSumClosest(nums3, target3));
        // Expected Output: -1
    }
}
