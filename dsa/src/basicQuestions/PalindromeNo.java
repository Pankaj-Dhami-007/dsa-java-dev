package basicQuestions;

public class PalindromeNo {
    /*
    palindromeNo--> A number is a palindrome if you reverse the digits, and it’s still the same number.
     */
    private static boolean isPalindrome(int n){
           int z = n;
           int revNum =0;
           while (z != 0){
               int lastDigit = z % 10;
               revNum = revNum * 10 + lastDigit;
               z /=10;
           }
        return n == revNum;
    }
    public static void main(String[] args) {
        int a= 121;
        int b = 1331;
        int c = 12321;
        int d = 123;
        System.out.println(isPalindrome(d));
    }
}
