package arrays.array2D;

public class Addition {

    public static void add(int[][] a, int[][] b){
        int r1 = a.length;
        int c1 = a[0].length;
        int r2 = b.length;
        int c2 = b[0].length;

        if(r1 != r2 || c1 != c2){
            System.out.println("Addition not possible");
            return;
        }
        int[][] ans = new int[r1][c1];

        for (int i = 0; i < r1; i++) {
            for (int j = 0; j <c1 ; j++) {
                ans[i][j] = a[i][j] + b[i][j];
            }
        }
        print(ans);
    }

    public static void print(int[][] arr){
        int r = arr.length;
        int c = arr[0].length;

        for (int i = 0; i < r; i++) {
            for (int j = 0; j < c; j++) {
                System.out.print(arr[i][j]+" ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] a = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        int[][] b = {
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };

        add(a, b);
    }
}
