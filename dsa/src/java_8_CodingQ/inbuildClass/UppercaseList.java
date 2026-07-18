package java_8_CodingQ.inbuildClass;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UppercaseList {
    public static void main(String[] args) {
       List<String> list = Arrays.asList("java", "stream");
        System.out.println(" UppercaseList  "+getUpperCaseList(list));

       List<String> upperCaseList =  list.stream()
               .map(String :: toUpperCase).toList();
//                .map(x-> x.toUpperCase()).toList();
        System.out.println(upperCaseList);
    }
    private static List<String> getUpperCaseList(List<String> list){
        List<String> res = new ArrayList<>();
        for(String s : list){
            res.add(s.toUpperCase());
        }
        return res;
    }
}
