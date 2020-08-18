/** Summing an array.
 *  @author P. N. Hilfinger
 */
class Summing {

    /** Return the sum of the elements of A. */
    static int sum(int[] A) {
        int N;
        N = 0;

        for (int i = 0; i < A.length; i += 1) {
            N += A[i];
        }

        /* Or use the new, improved syntax:
           for (int x : A)
               N += x;
        */

        return N;
    }

    /** Test on ARGS. */
    public static void main(String[] args) {
        int[] B = Utils.toInts(args);
        System.out.print("The sum of ");
        Utils.print(System.out, B);
        System.out.printf(" is %d.%n", sum(B));
    }

}

