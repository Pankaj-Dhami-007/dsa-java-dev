package xyz;


import java.util.*;

public class practice {

    // nums -> [ 5, 2, 7, 1, 8], t-> 9
    public static List<Integer> twoSumV1(int[] nums, int t){
        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int diff = t - nums[i];
            if(map.containsKey(diff)){
                return new ArrayList<>(Arrays.asList(map.get(diff), i));
            }
            else{
                map.put(nums[i], i);
            }
        }
        return new ArrayList<>();
    }

    public static List<Integer> twoSumV2(int[] nums, int t){
        int n = nums.length;
        Arrays.sort(nums);
        int i =0;
        int j = n-1;
        while(i < j){
            int sum = nums[i]+nums[j];
            if(sum == t){
                return new ArrayList<>(Arrays.asList(nums[i], nums[j]));
            } else if (sum < t) {
                i++;
            }
            else {
                j--;
            }
        }
        return new ArrayList<>();
    }

    public static void main(String[] args) {
        int arr[] = {5, 2, 7, 1, 6};
        System.out.println(twoSumV1(arr,7));
        System.out.println(twoSumV2(arr,7));
    }
}
