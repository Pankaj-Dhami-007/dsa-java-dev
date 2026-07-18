package arrays;

import java.util.Arrays;

/*
Input: nums = [2, 7, 11, 15], target = 9
Output: [2, 7]

 */
public class TwoSum_2 {
    public static int[] twoSumElements(int[] nums, int target){
        Arrays.sort(nums);
        int n = nums.length-1;
        int l = 0;
        int r =n-1;
       while(l< r){
             int sum = nums[l]+nums[r];
             if(sum == target){
                 return new int[] {nums[l], nums[r]};
             } else if (sum < target) {
                 l++;
             } else{
                 r--;
             }
       }

       return new int[]{};
    }
    public static void main(String[] args) {

        int[] nums = {15, 2, 11, 7};
        int target = 9;
        int[] result = twoSumElements(nums, target);

        if (result.length > 0) {
            System.out.println("Elements: [" + result[0] + ", " + result[1] + "]");
        } else {
            System.out.println("No pair found");
        }
    }
}
