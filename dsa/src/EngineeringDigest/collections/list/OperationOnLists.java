package EngineeringDigest.collections.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

class NumSortComparator implements Comparator<Integer>{

    @Override
    public int compare(Integer o1, Integer o2) {
        //return o1 -o2; // asc
        return o2 -o1;
        // return Integer.compare(o1, o2); // for overflow issue
    }
    // i have 7, 4
    // i want sort this in asc oder so this method ko kya return krna h (should return positive num)
    // 3, 5 (-ve)
    //compare(o1, o2) ko:
    //negative (<0) --- o1 chhota hai o2 se → o1 pehle aayega
    // zero (0) ----- dono equal
    //positive (>0)  ----  o1 bada hai o2 se → o2 pehle aayega
}

class StringSortComparator implements Comparator<String>{

    @Override
    public int compare(String o1, String o2) { // int - tell relative order means how to sort
        return o2.length() - o1.length();
    }
    //  o1   o2
    // "ok" "bye"
}

public class OperationOnLists {

    public static void main(String[] args) {
        List<Integer> numList = new ArrayList<>();
        numList.add(7);
        numList.add(1);
        numList.add(4);

        //numList.sort(null); // natural order
        numList.sort(new NumSortComparator());
        //System.out.println(numList);

        List<String>  words = Arrays.asList("banana", "apple", "date");
        //words.sort(new StringSortComparator());
        words.sort((a, b)-> a.length() - b.length());
        System.out.println(words);
    }
}
