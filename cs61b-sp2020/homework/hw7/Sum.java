import java.util.HashSet;

/** HW #7, Two-sum problem.
 * @author Joe Mo
 */
public class Sum {

    /** Returns true iff A[i]+B[j] = M for some i and j. */
    public static boolean sumsTo(int[] A, int[] B, int m) {
        HashSet<Integer> b = new HashSet<>();
        for (int n : B) b.add(n);

        for (int n : A)
            if (b.contains(m - n))
                return true;
        return false;
    }

}
