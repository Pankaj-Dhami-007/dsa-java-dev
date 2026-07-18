package arrays;

import java.util.HashMap;
import java.util.Map;

/*
two sum problem -
i/0 -> [7, 4, 2, 1, 9] and k = 3
o/p -> [2, 3]
 */
public class TwoSumProblem {

    private static int[] findTwoSumIndices(int arr[], int k){
        int n = arr.length;
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < n; i++) {
            int num = arr[i];
            if (map.containsKey(k - num)){
                return new int[] {map.get(k-num), i};
            }
            else {
                map.put(num, i);
            }
        }
        System.out.println(map);

        return new int[] {-1, -1};
    }

    public static int[] twoSumBruteForceSolution(int arr[], int k){
        int n = arr.length;
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {

                if(arr[i] + arr[j] == k){
                    return new int[]{i, j};
                }
            }
        }
        return new int[]{};
    }
    public static void main(String[] args) {

        int arr[] = {7, 4, 2, 1, 9};
        int[] ans = findTwoSumIndices(arr, 3);
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i]+" ");
        }
    }
}
