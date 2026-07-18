package patternPrinting.mirrorPattern;

import java.util.Scanner;


/*

n = 5
*
* *
* * *
* * * *
* * * * *
* * * *
* * *
* *
*

 */
public class Pattern_1 {

    public static void printPattern(int n){
        // no of rows
        // task only print star

        int row = 1;
        int star = 1;
        while (row <= 2*n - 1){
            // star
            int i =1;
            while (i<= star){
                System.out.print("* ");
                i++;
            }

            // for next row
            // mirror concept
            if(row < n){
                star++;
            }
            else{
                star--;
            }
            System.out.println();
            row++;
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        // n = 5
        // no of rows = 9
        // row 1 to 2 * (n-1)
        // identify mirror (horizontal mirror), not vertical

        printPattern(n);
    }
}
