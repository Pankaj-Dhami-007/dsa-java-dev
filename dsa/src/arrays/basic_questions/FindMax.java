package arrays.basic_questions;

public class FindMax {
    private static int findMaxElement(int[] arr){
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] > max){
                max = arr[i];
            }
        }
        return max;
    }
    public static void main(String[] args) {
        int arr[] = {1, 2, 33, 4, 5, 6};
        System.out.println(findMaxElement(arr));
    }
}
