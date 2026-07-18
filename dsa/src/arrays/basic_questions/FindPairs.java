package arrays.basic_questions;

public class FindPairs {

    private static void printPairs(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            int start = arr[i];
            for (int j = i+1; j < arr.length; j++) {
                int end = arr[j];
                System.out.print("(" + start + ", " + end + ") ");
                //System.out.println(arr[start]);
            }
            System.out.println();
        }
    }
    public static void main(String[] args) {
        int[] arr = {2, 4, 6, 8, 10, 12};
        printPairs(arr);
    }
}
