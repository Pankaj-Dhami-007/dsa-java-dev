package arrays.array2D;

import java.util.Scanner;

public class Basics {


    public static void colWiseTraversal(int[][] arr){
        int r = arr.length;
        int c = arr[0].length;

        for (int i = 0; i < c; i++) {
            for (int j = 0; j < r; j++) {
                System.out.print(arr[j][i]+" ");
            }
            System.out.println();
        }
    }
    public static void rowWiseTraversal(int[][] arr){
        int r = arr.length;
        int c = arr[0].length;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        int[][] arr = {
                {1, 2, 3, 4},
                {5, 6, 7, 8},
                {9, 10, 11, 12}
        };
//        rowWiseTraversal(arr);
//        System.out.println();
//        colWiseTraversal(arr);

        Scanner sc = new Scanner(System.in);
        System.out.print("enter rows : ");
        int r = sc.nextInt();
        System.out.print("enter cols : ");
        int c = sc.nextInt();

        int[][] inputArray = new int[r][c];
        System.out.println("Enter metrix elements:");

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++){
                inputArray[i][j] = sc.nextInt();
            }
        }
        rowWiseTraversal(inputArray);
    }
}
