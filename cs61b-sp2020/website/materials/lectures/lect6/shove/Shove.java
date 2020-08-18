
/* On the command line, compile with

      javac -g Shove.java

   and run with, e.g.

      java Shove 2 1 9 6 3 -2 7 0 19 -1

   to print

      2 1 9 6 3 -2 -1 7 0 19

*/

/** Example of the inner loop of insertion sorting. */
public class Shove {

    /** Print ARGS, the command line arguments (all integer numerals), with
     *  the last one moved left so that no smaller elements follow it. */
    public static void main(String[] args) {
        /* Command-line arguments are strings (sequences of characters),
         * but moveOver expects ints.  So, we use Integer.parseInt (a library
         * function) to convert the strings in ARGS (each of which should be
         * a valid decimal numeral, possibly negated) into ints. */
        int[] A = new int[args.length];
        for (int i = 0; i < args.length; i += 1) {
            A[i] = Integer.parseInt(args[i]);
        }
        moveOver(A);
        for (int s : A) {
            System.out.print(s + " ");
        }
        System.out.println();
    }


    /** Rotate elements A[k] to A[A.length-1] one element to the
     *  right, where k is the smallest index such that elements k
     *  through A.length-2 are all larger than A[A.length-1]. */
    static void moveOver(int[] A) {
        moveOver(A, A.length - 1);
    }

    /** Rotate elements A[k] to A[U] one element to the right,
     *  where k is the smallest index such that elements k
     *  through U-1 are all larger than A[U]. */
    static void moveOver(int[] A, int U) {
        if (U > 0) {
            if (A[U - 1] > A[U]) {
                int tmp = A[U - 1]; A[U - 1] = A[U]; A[U] = tmp;
                moveOver(A, U - 1);
            }
        }
    }

}
