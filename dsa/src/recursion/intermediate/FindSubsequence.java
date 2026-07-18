package recursion.intermediate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FindSubsequence {

    public static void printSubSeq(String str, String ans){

//        if(str.length() == 0) {
//            System.out.println(ans);
//            return;
//        }

        if(str.isEmpty()) {
            System.out.print(ans+" ");
            return;
        }
        char ch = str.charAt(0);
        printSubSeq(str.substring(1), ans);
        printSubSeq(str.substring(1), ans+ch);
    }

    public static void printSubSeq(String str, String ans, List<String> list){
        if(str.isEmpty()) {
            list.add(ans);
            return;
        }

        char ch = str.charAt(0);
        printSubSeq(str.substring(1), ans, list);
        printSubSeq(str.substring(1), ans + ch, list);
    }

    public static void main(String[] args) {
        String s= "abc";
        printSubSeq(s, "");
        System.out.println();

        List<String> list = new ArrayList<>();
        printSubSeq(s, "", list);
        Collections.sort(list);
        System.out.println(list);
    }
}
