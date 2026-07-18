package basicQuestions;

public class SumLastDigit {

    public static int findSum(int num){
        while(num > 9) {
            int sum = 0;
            while (num > 0) {
                int lastD = num % 10;
                sum += lastD;
                num /= 10;
            }
            return sum;
        }
        return num;
    }
    public static void main(String[] args) {
        System.out.println(findSum(998));
    }
}
