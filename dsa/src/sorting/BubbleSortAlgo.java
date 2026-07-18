package sorting;

public class BubbleSortAlgo {

    public static void bubbleSort(int[] arr){
        // [8, 1, 3, 5, 2, 8, 4]
        int n = arr.length;
        for(int i = 0; i< n; i++){
            boolean isSwap = false;
            for(int j = 0; j < n-i-1; j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                    isSwap = true;
                }
            }
            if(!isSwap) break;;
        }
        // print
        for(int i =0; i<n; i++){
            System.out.print(arr[i]+" ");
        }
    }
    public static void main(String[] args) {
        int[] arr = {9, 8, 7, 4, 6, 2, 1};
       bubbleSort(arr);
    }
}
