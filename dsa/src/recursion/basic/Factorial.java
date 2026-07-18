package recursion.basic;
/*
find factorial ->
ex-> 5! = 5*4*3*2*1=120
 */
public class Factorial {

    private static int findFact(int n){

        if (n == 1){
            return n;
        }
        return  n * findFact(n-1);
    }
    public static void main(String[] args) {
        System.out.println(findFact(5));
    }
}
