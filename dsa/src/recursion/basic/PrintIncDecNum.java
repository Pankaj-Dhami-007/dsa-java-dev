package recursion.basic;

/*
     print 1 to n => 1, 2, 3, 4, .....n
 */
public class PrintIncDecNum {

    private static void printIncNum(int n){

        if(n==1){
            System.out.print(n+" ");
            return;
        }
        //sub problem ->
        printIncNum(n-1);
        System.out.print(n+" ");
    }
    public static void main(String[] args) {
        printIncNum(5);
    }
}
