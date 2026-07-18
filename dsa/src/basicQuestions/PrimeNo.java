package basicQuestions;

public class PrimeNo {
    private static boolean isPrime(int n){

        if (n <= 1) return false;
        if (n == 2) return true;
        if (n % 2 == 0) return false;

        for (int i = 3; i*i<=n; i+=2){
            if(n % i == 0){
                return false;
            }
        }
        return true; // tc-> O(√n)
    }
    public static void main(String[] args) {
        int n = 11;
        System.out.println(isPrime(n));
    }
}
/*

i * i <= n
This is the same as: i <= √n

Why?
Because if n has a factor larger than √n, it must also have a smaller factor less than √n.
So, if no factors are found up to √n, the number is prime.
 */
