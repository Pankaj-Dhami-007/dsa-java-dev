package patternPrinting.starPattern;

import java.util.Scanner;

/*

         *
       * *
     * * *
   * * * *
 * * * * *

 */
public class RightAlignedTriangle {

    public static void rightAlignedTriangle(int n){
        int row = 1;
        int star = 1;
        int space = n-1;
        while (row <= n){
            int i =1;
            while(i <= space){
                System.out.print("  ");
                i++;
            }
            int j = 1;
            while(j <= star){
                System.out.print("* ");
                j++;
            }
            row++;
            space--;
            star++;
            System.out.println();
        }

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        rightAlignedTriangle(n);
    }
}
