package arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


// Find unique triplets whose sum = 0
// nums = [-1,0,1,2,-1,-4]
// [[-1,-1,2],[-1,0,1]]
public class ThreeSumProblem {


    // with sorting
    // fix one element
    // find remaining two no using two pointer
    public static List<List<Integer>> threeSum(int[] nums) {

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length - 2; i++) {
            // Skip duplicate fixed elements
            if (i > 0 && nums[i] == nums[i - 1]) continue;

            int current = nums[i];
            int target = -current;  // we need two numbers that sum to -current

            // Call helper method for twoSum
            List<List<Integer>> twoSumPairs = twoSum(nums, i + 1, target);

            // Add fixed element to each pair
            for (List<Integer> pair : twoSumPairs) {
                pair.add(0, current);
                result.add(pair);
            }
        }
        return result;
    }


    // ---- twoSum() (Two-pointer approach, no HashMap) ----
    private static List<List<Integer>> twoSum(int[] nums, int start, int target) {
        List<List<Integer>> pairs = new ArrayList<>();
        int left = start;
        int right = nums.length - 1;

        while (left < right) {
            int sum = nums[left] + nums[right];

            if (sum == target) {
                pairs.add(new ArrayList<>(Arrays.asList(nums[left], nums[right])));
                left++;
                right--;

                // Skip duplicates for left
                while (left < right && nums[left] == nums[left - 1]) left++;

                // Skip duplicates for right
                while (left < right && nums[right] == nums[right + 1]) right--;

            } else if (sum < target) {
                left++;
            } else {
                right--;
            }
        }
        return pairs;
    }


    public List<List<Integer>> three_sum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }

            int j = i + 1;
            int k = nums.length - 1;

            while (j < k) {
                int total = nums[i] + nums[j] + nums[k];

                if (total > 0) {
                    k--;
                } else if (total < 0) {
                    j++;
                } else {
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;

                    while (nums[j] == nums[j-1] && j < k) {
                        j++;
                    }
                }
            }
        }
        return res;
    }

    static List<List<Integer>> threeSumWithSkip(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        Arrays.sort(nums);

        for (int i = 0; i < nums.length - 2; i++) {
            if (i > 0 && nums[i] == nums[i - 1]) continue;
            int j = i + 1, k = nums.length - 1;

            while (j < k) {
                int sum = nums[i] + nums[j] + nums[k];
                if (sum == 0) {
                    res.add(Arrays.asList(nums[i], nums[j], nums[k]));
                    j++;
                    k--;
                    // skip duplicates
                    while (j < k && nums[j] == nums[j - 1]) j++;
                    while (j < k && nums[k] == nums[k + 1]) k--;
                } else if (sum < 0) j++;
                else k--;
            }
        }
        return res;
    }
    public static void main(String[] args) {
        int[] nums = {-1, 0, 1, 2, -1, -4};
        List<List<Integer>> triplets = threeSum(nums);
        List<List<Integer>> triplets2 = threeSumWithSkip(nums);

        System.out.println("Unique triplets with sum 0: " + triplets);
        System.out.println("Unique triplets with sum 0: " + triplets2);
    }
}
