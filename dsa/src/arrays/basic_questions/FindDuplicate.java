package arrays.basic_questions;

import java.util.ArrayList;
import java.util.List;

public class FindDuplicate {

    private static List<Integer> findAllDuplicates(int arr[]){
        int n = arr.length;
        List<Integer> duplicateElements = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            for (int j = i+1; j < n; j++) {
                if (arr[i] == arr[j]){
                    if (!duplicateElements.contains(arr[i])) {
                        duplicateElements.add(arr[i]);
                    }
                    break;
                }
            }
        }
        return duplicateElements;// tc--> o(n2)
    }

    private static boolean isDuplicate(int arr[]){

        for (int i = 0; i < arr.length; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if (arr[i] == arr[j]){
                    return true;
                }
            }
        }
        return false;// tc--> o(n2)
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 2, 2, 3, 5};
        System.out.println(isDuplicate(arr));
        System.out.println(findAllDuplicates(arr));
    }
}
