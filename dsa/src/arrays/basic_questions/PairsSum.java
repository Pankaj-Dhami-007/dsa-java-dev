package arrays.basic_questions;

// sorted arr, find pairs sum equal to k
public class PairsSum {

    private static boolean bruteForce(int[] arr, int k){
        int n = arr.length;

        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                int sum = arr[i] + arr[j];
                if (sum == k){
                    return true;
                }
            }
        }
        return false;// tc-> o(n2)
    }

    private static  boolean optimize(int arr[], int k){
        // two pointer approach
        int start = 0;
        int end = arr[arr.length-1];

        while (start <= end){
            if (start+end == k){
                return  true;
            } else if (start + end > k) {
                end--;
            }
            else {
                start++;
            }
        }
        return false; // tc => o(n)
    }
    public static void main(String[] args) {
        int arr[] = {1, 2, 3, 4, 5, 6};
        int k1 = 5;
        System.out.println(bruteForce(arr, k1));
        System.out.println(optimize(arr, k1));
    }
}
