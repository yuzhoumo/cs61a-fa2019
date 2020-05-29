package arrays;

/**
 * HW #2
 */

/**
 * Array utilities.
 *
 * @author Joe Mo
 */
class Arrays {

    /* C1. */

    /**
     * Returns a new array consisting of the elements of A followed by the
     * elements of B.
     */
    static int[] catenate(int[] A, int[] B) {
        int[] result = new int[A.length + B.length];
        System.arraycopy(A, 0, result, 0, A.length);
        System.arraycopy(B, 0, result, A.length, result.length - A.length);
        return result;
    }

    /**
     * Returns a new 2D int array consisting of the elements of A followed by
     * the elements of B.
     *
     * @param A First 2D int array
     * @param B Second 2D int array
     * @result {int[][]} Returns the result of concatenating A and B
     */
    static int[][] catenate2D(int[][] A, int[][] B) {
        int[][] result = new int[A.length + B.length][];
        System.arraycopy(A, 0, result, 0, A.length);
        System.arraycopy(B, 0, result, A.length, result.length - A.length);
        return result;
    }

    /* C2. */

    /**
     * Returns the array formed by removing LEN items from A,
     * beginning with item #START.
     */
    static int[] remove(int[] A, int start, int len) {
        int[] a = java.util.Arrays.copyOfRange(A, 0, start);
        int[] b = java.util.Arrays.copyOfRange(A, start + len, A.length);
        return catenate(a, b);
    }

    /* C3. */

    /**
     * Returns the array of arrays formed by breaking up A into
     * maximal ascending lists, without reordering.
     * For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     * returns the three-element array
     * {{1, 3, 7}, {5}, {4, 6, 9, 10}}.
     */
    static int[][] naturalRuns(int[] A) {
        return makeRuns(A, 0);
    }

    /**
     * Recursive helper method for naturalRuns.
     *
     * @param A Input array to make natural runs for
     * @param i Index to keep track of position in A
     * @return {int[][]} 2D array of strictly ascending natural runs from A
     */
    static int[][] makeRuns(int[] A, int i) {
        if (A.length == 2) {
            if (A[0] < A[1]) {
                return new int[][]{{A[0], A[1]}};
            }
            return new int[][]{{A[0]}, {A[1]}};
        } else if (i + 1 == A.length) {
            return new int[][]{{A[0]}};
        } else if (A[i] >= A[i + 1]) {
            int[] next = remove(A, 0, i + 1);
            int[] curr = remove(A, i + 1, A.length - i - 1);
            return catenate2D(new int[][]{curr}, makeRuns(next, 0));
        }
        return makeRuns(A, i + 1);
    }
}
