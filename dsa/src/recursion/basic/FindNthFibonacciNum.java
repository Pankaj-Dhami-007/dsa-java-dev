package recursion.basic;

/*
fibo series -> 0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55.......
 */
public class FindNthFibonacciNum {

    private static int nthFiboNum(int n){

        if (n == 0 || n == 1) return n;
       return nthFiboNum(n-1)+ nthFiboNum(n-2);
    }
    public static void main(String[] args) {
        System.out.println(nthFiboNum(7));
        System.out.println(nthFiboNum(0));
        System.out.println(nthFiboNum(1));
    }
}
