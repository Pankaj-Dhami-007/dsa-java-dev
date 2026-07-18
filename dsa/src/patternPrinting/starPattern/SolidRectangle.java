package patternPrinting.starPattern;

import java.util.Scanner;


/*

 * * * * *
 * * * * *
 * * * * *
 * * * * *
 * * * * *

 */
public class SolidRectangle {

    public static void printSolidRec(int n){

        int row = 1;
        int star = n;
        while(row <= n){
            int i =1;
            while(i <= star){ // repeat this loop for n times
                System.out.print("* ");
                i++;
            }
            row++;
            System.out.println();
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        printSolidRec(n);
    }

    // steps
    // 1. row variable , find no of rows , 1 to n  and set star var acc to first row star
    // 2. total diff work (print star, spaces etc)
    // loop in each row (what task in each row)
    // first loop in row
    // second loop in each row for perform task
    // next row preparation

}
