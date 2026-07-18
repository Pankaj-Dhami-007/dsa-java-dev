package patternPrinting.starPattern;

import java.util.Scanner;

/*

 * * * * *
   * * * *
     * * *
       * *
         *


 */
public class InvertedRightAlignedTriangle {

    public static void invertedRightAlignedTriangle(int n){

        int row = 1;
        int star = n;
        int space = 0;
        while(row <= n){
            int j = 0;
            while (j < space){
                System.out.print("  ");
                j++;
            }

            int i =1;
            while (i<= star){
                System.out.print("* ");
                i++;
            }
            System.out.println();
            row++;
            star--;
            space++;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        invertedRightAlignedTriangle(n);
    }
}
