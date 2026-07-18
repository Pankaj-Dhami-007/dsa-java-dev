package string;

public class longestCommonPrefix {


    public static String longestCommonPrefix_1(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        for (int i = 0; i < strs[0].length(); i++) {
            char c = strs[0].charAt(i);

            for (int j = 1; j < strs.length; j++) {
                if (i == strs[j].length() || strs[j].charAt(i) != c) {
                    return strs[0].substring(0, i);
                }
            }
        }

        return strs[0];
    }


    public static String longestCommonPrefix_2(String[] strs) {
        if (strs == null || strs.length == 0) return "";

        StringBuilder result = new StringBuilder();

        // Use first string as reference
        for (int i = 0; i < strs[0].length(); i++) {
            char currentChar = strs[0].charAt(i);

            // Check if all other strings have the same character at position i
            for (int j = 1; j < strs.length; j++) {
                if (i >= strs[j].length() || strs[j].charAt(i) != currentChar) {
                    return result.toString();
                }
            }

            result.append(currentChar);
        }

        return result.toString();
    }


    public static void main(String[] args) {

        // Test cases
        String[] test1 = {"flower", "flow", "flight"};
        System.out.println(longestCommonPrefix_1(test1)); // "fl"

        String[] test2 = {"dog", "racecar", "car"};
        System.out.println(longestCommonPrefix_2(test2)); // ""
    }
}
