package sliding_window_technique;

public class longestSubstring {

    public static int longestSubstring_1(String s){
        int n = s.length();
        int max = 0;
        for (int i = 0; i <n ; i++) {
            int count =1;
            for (int j = i; j < n-1; j++) {
                if(s.charAt(j) != s.charAt(j+1)){
                    count++;
                    j++;
                }
                j++;
                max = Math.max(max, count);
            }
        }
        return max;

    }
    public static void main(String[] args) {
         String s1 = "abcabcbb";
         String s2 = "bbbb";
        System.out.println(longestSubstring_1(s1));
        System.out.println(longestSubstring_1(s2));
    }
}
