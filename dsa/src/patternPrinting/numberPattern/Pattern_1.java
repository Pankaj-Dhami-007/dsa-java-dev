package patternPrinting.numberPattern;

import java.util.Scanner;


/*
n = 5
				1
			2	3	4
		5	6	7	8	9
	10	11	12	13	14	15	16
17	18	19	20	21	22	23	24	25

 */
public class Pattern_1 {

    public static void printPattern(int n){

        int row = 1;
        int nums = 1;
        int val = 1;
        int spaces = n-1;
        while(row <= n){
            int j =1;
            while (j <= spaces){
                System.out.print("\t"); // space alignment breaks (\t)
                j++;
            }
            int i =1;
            while (i <= nums){
                System.out.print(val+"\t");
                val++;
                i++;
            }
            row++;
            nums+=2;
            spaces--;
            System.out.println();
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        printPattern(n);
    }
}
