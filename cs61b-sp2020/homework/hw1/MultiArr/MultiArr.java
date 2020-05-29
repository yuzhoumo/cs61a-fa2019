/**
 * Multidimensional array
 *
 * @author Zoe Plaxco
 */

public class MultiArr {

    /**
     * {{“hello”,"you",”world”} ,{“how”,”are”,”you”}} prints:
     * Rows: 2
     * Columns: 3
     * <p>
     * {{1,3,4},{1},{5,6,7,8},{7,9}} prints:
     * Rows: 4
     * Columns: 4
     */
    public static void printRowAndCol(int[][] arr) {
        System.out.printf("Rows: " + arr.length + "\nColumns: " + arr[0].length);
    }

    /**
     * @param arr: 2d array
     * @return maximal value present anywhere in the 2d array
     */
    public static int maxValue(int[][] arr) {
        if (arr.length == 0 || arr[0].length == 0) {
            return 0;
        }

        int maxVal = arr[0][0];
        for (int[] a : arr) {
            for (int val : a) {
                if (val > maxVal) {
                    maxVal = val;
                }
            }
        }
        return maxVal;
    }

    /**
     * Return an array where each element is the sum of the
     * corresponding row of the 2d array
     */
    public static int[] allRowSums(int[][] arr) {
        int[] sums = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            int tot = 0;
            for (int val : arr[i]) {
                tot += val;
            }
            sums[i] = tot;
        }
        return sums;
    }
}
