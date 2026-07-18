package recursion.basic;

import java.util.Scanner;

public class FindPower {

    public static int pow(int x, int n){
        if(n == 0) return 1;
       return x * pow(x, n-1);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();
        int ans = pow(a, b);
        System.out.println(ans);
    }
}
