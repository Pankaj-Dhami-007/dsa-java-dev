package arrays.basic_questions;

import java.util.HashMap;
import java.util.Map;

public class MaxFrequencyElement {

    private static int optimizedWithContains(int[] arr) {
        Map<Integer, Integer> freqMap = new HashMap<>();
        int maxFreq = 0;

        for (int num : arr) {
            if (freqMap.containsKey(num)) {
                int freq = freqMap.get(num) + 1;
                freqMap.put(num, freq);
                if (freq > maxFreq) {
                    maxFreq = freq;
                }
            } else {
                freqMap.put(num, 1);
                if (1 > maxFreq) {
                    maxFreq = 1;
                }
            }
        }

        return maxFreq; // Time Complexity: O(n)
    }


    private static int optimizedCode(int[] arr){
        Map<Integer, Integer> freqMap = new HashMap<>();
        int maxFreq = 0;

        for (int num : arr) {
            int freq = freqMap.getOrDefault(num, 0) + 1;
            freqMap.put(num, freq);
            if (freq > maxFreq) {
                maxFreq = freq;
            }
        }

        return maxFreq;// tc-> o(n)
    }
    private static Map<String, Integer> findMaxFreqElement(int[] arr){
        Map<String, Integer> infoMaxElement = new HashMap<>();
        int n = arr.length;
        int maxCount = 0;
        int maxFreqElement = arr[0];
        int maxFreqIndex = 0;
        for (int i = 0; i < n; i++) {
            int count =0;
            for (int j = 0; j < n; j++) {
                if(arr[i] == arr[j]){
                    count++;
                }
            }
            if(count > maxCount){
                maxCount= count;
                maxFreqElement = arr[i];
                maxFreqIndex=i;
            }
        }
        infoMaxElement.put("maxFreqElement",maxFreqElement);
        infoMaxElement.put("maxCount",maxCount);
        infoMaxElement.put("maxFreqIndex",maxFreqIndex);
        return infoMaxElement;// tc- o(n2)
    }
    public static void main(String[] args) {
        int arr[] = {2, 3, 4, 4, 4, 4, 2, 3, 3, 3, 3, 3};
        System.out.println(findMaxFreqElement(arr));
        System.out.println(optimizedCode(arr));
        System.out.println(optimizedWithContains(arr));
    }
}

/*
Difference:
getOrDefault(num, 0) directly gives you the count or 0 if not found.
containsKey() is a bit more manual—you check if the key exists, and handle the put logic accordingly.
 */
