package basicQuestions;

public class Fibonacci {
/*
fibonacci series--> 0, 1, 1, 2, 3, 5, 8, 13, 21.........
 */
    private static int nthFibonacciNo(int n){

        if (n < 0) return -1;
        if(n == 0) return 0;
        if(n == 1) return 1;

        int firstNum = 0;
        int secondNum = 1;

        for (int i = 2; i <= n; i++) {
            int nextNum = firstNum+secondNum;
            firstNum = secondNum;
            secondNum = nextNum;
        }
        return secondNum;// tc --> o(n)
    }
    public static void main(String[] args) {

        int n =5;
        System.out.println(nthFibonacciNo(3));
    }
}

