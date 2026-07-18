package basicQuestions;

/*
let n = 5;
 -> 1, 2, 3, 4, 5=> 1-2+3-4+5= 3 = output
 */
public class FindAlternativeSum {

    private static int secondApproach(int n){

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            sum += (i % 2 == 0) ? -i : i;
        }
        return sum;
    }

    private static int firstApproach(int n){

        int sum = 0;
        for (int i = 1; i <= n; i++) {
            if (i % 2 == 0){
               sum -= i;
            }
            else{
                sum += i;
            }
        }
        return sum;
    }
    public static void main(String[] args) {
        int n1 =5;
        int n2 = 10;
        System.out.println(firstApproach(n2));
        System.out.println(secondApproach(n1));
    }
}
