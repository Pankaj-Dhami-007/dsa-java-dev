package patternPrinting.starPattern;

import java.util.Scanner;

/*

5
        *
      * ! *
    * ! * ! *
  * ! * ! * ! *
* ! * ! * ! * ! *

 */
public class Pattern_7 {

    public static void printPattern(int n){

        int row = 1;
        int star = 1;
        int space = n-1;
        while(row <= n){
            int i =1;
            while(i <= space){
                System.out.print("  ");
                i++;
            }
            int j =1;
            while (j <= star){
                if(j % 2 != 0) System.out.print("* "); // odd
                else System.out.print("! ");
                j++;
            }
            System.out.println();
            row++;
            star += 2;
            space--;
        }
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        printPattern(n);
    }
}
