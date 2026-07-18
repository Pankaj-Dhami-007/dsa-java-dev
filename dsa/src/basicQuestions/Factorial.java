package basicQuestions;

public class Factorial {
    private static int fact(int n){
        int fact = 1;
        for (int i = 1; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
    public static void main(String[] args) {
        int n =5;
        System.out.println(fact(n));
    }
}
