package leetcode.intervalPattern;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class MergeInterval {

    /*
    // Method 1: Lambda
list.sort((a, b) -> a.get(0).compareTo(b.get(0)));

// Method 2: Comparator.comparing (best)
list.sort(Comparator.comparing(l -> l.get(0)));

// Method 3: Descending
list.sort(Comparator.comparing(l -> l.get(0)).reversed());
     */

    public static void demo(){
        int[][] arr = {
                {5, 9},
                {1,3},
                {2, 7},
                {8, 10},
        };


       // Arrays.sort(arr,(a, b)-> Integer.compare(a[0], b[0]));
        Arrays.sort(arr, Comparator.comparingInt(a -> a[0]));//primitive int

        List<int[]> list = new ArrayList<>();
        for(int[] i : arr){
            list.add(i);
        }
        System.out.println("Method 1:");
        System.out.println(Arrays.deepToString(list.toArray()));

        System.out.println("\nMethod 2:");
        for (int[] row : list) {
            System.out.println(Arrays.toString(row));
        }

        List<List<Integer>> list1 = new ArrayList<>();
        list1.add(Arrays.asList(5, 9));
        list1.add(Arrays.asList(1, 3));
        list1.add(Arrays.asList(2, 7));
        list1.add(Arrays.asList(8, 10));

        System.out.println("Before sorting:");
        System.out.println(list1);

        list1.sort(Comparator.comparing(innerList -> innerList.get(0)));

        System.out.println("\nAfter sorting (by first element):");
        System.out.println(list);

    }
    public static int[][] mergeInterval(int[][] intervals){

        if (intervals.length <= 1) return intervals;
        Arrays.sort(intervals, Comparator.comparingInt(a->a[0]));
        List<int[]> list = new ArrayList<>();
        list.add(intervals[0]);
        for (int i = 1; i < intervals.length; i++) {
            int[] prev = list.get(list.size()-1);
            int[] curr = intervals[i];

            // Overlap condition: current start <= previous end
            if(curr[0] <= prev[1]){
                //prev[0] = Math.min(prev[0], curr[0]); no need already sorted
                // Merge intervals step
                prev[1] = Math.max(prev[1], curr[1]);
            }
            else{
                // No overlap, add previous interval to result
                //list.add(intervals[i]);
                list.add(curr);
            }
        }

        int[][] res = new int[list.size()][2];
        for (int i = 0; i < list.size(); i++) {
            res[i] = list.get(i);
        }
      return res;
        //return list.toArray(new int[list.size()][]);
    }

    public int[][] merge(int[][] intervals) {

        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

        List<int[]> list = new ArrayList<>();
        int[] prev = intervals[0];

        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] <= prev[1]) {
                prev[1] = Math.max(prev[1], intervals[i][1]);
            } else {
                list.add(prev);
                prev = intervals[i];
            }
        }

        list.add(prev);

        return list.toArray(new int[list.size()][]);
    }

    public List<List<Integer>> merge(List<List<Integer>> intervals) {
        if (intervals.size() <= 1) return intervals;

        intervals.sort(Comparator.comparing(l -> l.get(0)));

        List<List<Integer>> result = new ArrayList<>();
        result.add(intervals.get(0));

        for (List<Integer> curr : intervals) {
            List<Integer> last = result.get(result.size() - 1);
            if (curr.get(0) <= last.get(1)) {
                last.set(1, Math.max(last.get(1), curr.get(1)));
            } else {
                result.add(curr);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        demo();
    }
}
