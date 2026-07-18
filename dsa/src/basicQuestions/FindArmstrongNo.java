package basicQuestions;

public class FindArmstrongNo {

    public static boolean isArmStrongNum(int num){
        int nof=0,n1=num;
        while(n1> 0){
            nof++;
            n1/=10;
        }
        int sum = 0, n2=num;
        while(n2> 0){
            int ld = n2 % 10;
            sum = sum + (int)Math.pow(ld, nof);
            n2/=10;
        }
        System.out.println(num+" , "+sum);
       return sum == num;
    }
    public static void main(String[] args) {
        System.out.println(isArmStrongNum(153));
    }
}
