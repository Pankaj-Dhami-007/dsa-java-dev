package string;

public class ReverseWords {

    /*
    Space (" "): Regular character in regex
     Dot ("."): Special character in regex that means "any character"
     In regular expressions, \ is also an escape character. So \. in regex means "literal dot".
     */

    public static String reverseWordsIncludingSpaces(String s){
        if (s == null || s.length() == 0) return s;

        String[] words = s.split(" ");  // Split by space instead of dot
        StringBuilder sb = new StringBuilder();

        for (int i = words.length - 1; i >= 0; i--) {
            if (!words[i].isEmpty()) { // Skip empty strings
                if (sb.length() > 0) {
                    sb.append(" ");    // Append space instead of dot
                }
                sb.append(words[i]);
            }
        }

        return sb.toString();
    }
    public static String reverseWords(String s){
        if (s == null || s.length() == 0) return s;
        //StringBuilder sb = new StringBuilder();
//        String[] words = s.split(".");
        String[] words = s.split("\\.");
        //String[] words = s.split(Pattern.quote("."));
        //String[] words = s.split("[.]");// char class
        for(String w : words){
            System.out.print(w+" ");
        }
        System.out.println();

        StringBuilder sb = new StringBuilder();

//        for (int i = words.length - 1; i >= 0; i--) {
//            if(words[i].length() == 0){
//                continue;
//            }
//            else if(sb.isEmpty()){
//                sb.append(words[i]);
//            } else {
//                sb.append(".").append(words[i]);
//            }
//        }

        for (int i = words.length - 1; i >= 0; i--) {
            if (!words[i].isEmpty()) { // Skip empty strings
                if (sb.length() > 0) {
                    sb.append(".");
                }
                sb.append(words[i]);
            }
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        String s = "..geeks..for.geeks.";
        System.out.println(reverseWords(s));
    }
}
