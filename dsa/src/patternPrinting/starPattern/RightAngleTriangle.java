package patternPrinting.starPattern;

import java.util.Scanner;

/*

 *
 * *
 * * *
 * * * *
 * * * * *

 */
public class RightAngleTriangle {

    public static void rightTriangle(int n){

        int row = 1;
        int star = 1;
        while(row <= n){
            int i = 1;
            while (i <= star){
                System.out.print("* ");
                i++;
            }
            // next row preparation
            row++;
            star++;
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        rightTriangle(n);
    }
}
