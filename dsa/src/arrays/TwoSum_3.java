package arrays;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
Input: nums = [1, 1, 2, 2, 3, 4, 4, 5], target = 6
Output: [[1, 5], [2, 4]]

 */
public class TwoSum_3 {

    public static List<List<Integer>> twoSumUnique(int[] nums, int target){

        List<List<Integer>> result = new ArrayList<>();
        Arrays.sort(nums);
        int n = nums.length;
        int l = 0;
        int r = n-1;
        while(l<r){
            int sum = nums[l]+nums[r];
            if(sum == target){
                result.add(Arrays.asList(nums[l], nums[r]));
                l++;
                r--;
                while (l< r && nums[l] == nums[l-1]){
                  l++;
                }
                while (l<r && nums[r] == nums[r+1]){
                    r--;
                }
            } else if (sum < target) {
                l++;
            }else{
                r--;
            }
        }
        return result; //tc - nlog(n)
    }
    public static void main(String[] args) {

        int[] nums = {1, 1, 2, 2, 3, 4, 4, 5};
        int target = 6;

        List<List<Integer>> pairs = twoSumUnique(nums, target);
        System.out.println("Unique pairs with sum " + target + ": " + pairs);
    }
}
