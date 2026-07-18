package basicQuestions;

public class ReverseANumber {

    private static int reverse(int n){
        int rev = 0;
        while(n != 0){
            int lastDig = n % 10;
            rev = rev * 10 + lastDig;
            n /=10;
        }
        return rev;
    }
    public static void main(String[] args) {
        int n = 123;
        System.out.println(reverse(n));
    }
}
