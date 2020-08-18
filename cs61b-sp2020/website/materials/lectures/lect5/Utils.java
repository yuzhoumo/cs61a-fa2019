/** Assorted utilities.
 *  @author P. N. Hilfinger
 */
class Utils {

    /** Assuming A is an array of integer numerals, return A[L..U], converted
     *  to ints. */
    public static int[] toInts(String[] A, int L, int U) {
        int[] result = new int[U - L + 1];
        for (int i = L; i <= U; i += 1) {
            result[i - L] = Integer.parseInt(A[i]);
        }
        return result;
    }

    /** Return A (an array of numerals) as ints. */
    public static int[] toInts(String[] A) {
        return toInts(A, 0, A.length - 1);
    }

    /** Print a representation of A on OUT. */
    public static void print(java.io.PrintStream out, int[] A) {
        String sep;
        out.printf("{");
        sep = " ";
        for (int x : A) {
            out.printf("%s%d", sep, x);
            sep = ", ";
        }
        out.print(" }");
    }

    /** Print a representation of A on OUT. */
    public static void print(java.io.PrintStream out, String[] A) {
        String sep;
        out.printf("{");
        sep = " ";
        for (String x : A) {
            out.printf("%s%s", sep, x);
            sep = ", ";
        }
        out.print(" }");
    }

}
