package arrays.basic_questions;

public class FindMin {
    private static int findMinElement(int[] arr){
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < arr.length; i++) {
            if(arr[i] < min){
                min = arr[i];
            }
        }
        return min;
    }
    public static void main(String[] args) {
        int arr[] = {11, 2, 33, 4, 12, 1};
        System.out.println(findMinElement(arr));
//        System.out.println(bruteforce(arr));
    }
}
