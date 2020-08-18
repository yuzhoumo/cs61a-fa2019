import static java.lang.System.*;

/** Merging arrays.
 *  @author P. N. Hilfinger
 */
class Merge {

    /* Simple, recursive merge. */

    /** Assuming A and B are sorted, returns their merge. */
    public static int[] merge1(int[] A, int[] B) {
        return merge1(A, 0, B, 0);
    }

    /** Return the merge of A[L0..] and B[L1..] assuming A and B sorted. */
    static int[] merge1(int[] A, int L0, int[] B, int L1) {
        int N = A.length - L0 + B.length - L1;
        int[] C = new int[N];
        if (A.length <= L0) arraycopy(B, L1, C, 0, N);
        else if (B.length <= L1) arraycopy(A, L0, C, 0, N);
        else if (A[L0] <= B[L1]) {
            C[0] = A[L0]; arraycopy(merge1(A, L0 + 1, B, L1), 0,
                                    C, 1, N - 1);
        } else {
            C[0] = B[L1]; arraycopy(merge1(A, L0, B, L1 + 1), 0,
                                     C, 1, N - 1);
        }
        return C;
    }

    /* Tail-recursive solution */

    /** Assuming A and B are sorted, returns their merge. */
    public static int[] merge2(int[] A, int[] B) {
        return merge2(A, 0, B, 0, new int[A.length + B.length], 0);
    }

    /** Return the merge A[L0..] and B[L1..] into C[K...], assuming
     *  A and B sorted. */
    static int[] merge2(int[] A, int L0, int[] B, int L1, int[] C, int k) {
        if (L0 >= A.length) {
            arraycopy(B, L1, C, k, B.length - L1);
        } else if (L1 >= B.length) {
            arraycopy(A, L0, C, k, A.length - L0);
        } else if (A[L0] <= B[L1]) {
            C[k] = A[L0];
            merge2(A, L0 + 1, B, L1, C, k + 1);
        } else {
            C[k] = B[L1];
            merge2(A, L0, B, L1 + 1, C, k + 1);
        }
        return C;
    }

    /* Iterative solution */

    /** Assuming A and B are sorted, returns their merge. */
    public static int[] merge3(int[] A, int[] B) {
        int[] C = new int[A.length + B.length];
        int L0, L1;
        L0 = L1 = 0;
        for (int k = 0; k < C.length; k += 1) {
            if (L0 >= A.length) {
                C[k] = B[L1]; L1 += 1;
            } else if (L1 >= B.length) {
                C[k] = A[L0]; L0 += 1;
            } else if (A[L0] <= B[L1]) {
                C[k] = A[L0]; L0 += 1;
            } else {
                C[k] = B[L1]; L1 += 1;
            }
        }
        return C;
    }


    /* Testing */

    /** Print the array A on the standard output. */
    private static void print(int[] A) {
        String s;
        s = " ";
        for (int x : A) {
            out.printf("%s%d", s, x);
            s = ", ";
        }
        out.print(" ]");
    }

    /** Test merging on ARGS, divided into two segments at "/". */
    public static void main(String... args) {
        int sep;
        sep = -1;
        for (int k = 0; k < args.length; k += 1) {
            if (args[k].equals("/")) {
                sep = k;
            }
        }
        if (sep == -1) {
            err.println("Usage: java merge a0 ... am / b0 ... bn");
            exit(1);
        }
        int[] A = new int[sep];
        int[] B = new int[args.length - sep - 1];
        for (int k = 0; k < sep; k += 1) {
            A[k] = Integer.parseInt(args[k]);
        }
        for (int k = sep + 1; k < args.length; k += 1) {
            B[k - sep - 1] = Integer.parseInt(args[k]);
        }
        out.print("Merging A = ["); print(A);
        out.print(" and B = ["); print(B);
        out.println(":");
        out.print("   merge1: ["); print(merge1(A, B)); out.println();
        out.print("   merge2: ["); print(merge2(A, B)); out.println();
        out.print("   merge3: ["); print(merge3(A, B)); out.println();
    }

}
