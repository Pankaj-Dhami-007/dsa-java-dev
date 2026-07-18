package patternPrinting.starPattern;

import java.util.Scanner;

/*

 * * * * *
 * * * *
 * * *
 * *
 *

 */
public class InvertedRightTriangle {

    public static void invertedRightTriangle(int n){
       int row = 1;
       int star = n;
       while(row <= n){
           int i = 1;
           while (i <= star){
               System.out.print("* ");
               i++;
           }
           row++;
           star--;
           System.out.println();
       }

    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        invertedRightTriangle(n);
    }
}
