/** Various insertions into an array (Lecture 6).
 *  @author P. N. Hilfinger */
class Inserting {
    /** Insert X at location K in ARR, moving items
     *  K, K+1, ... to locations K+1, K+2, ....
     *  The last item in ARR is lost. */
    static void insert(String[] arr, int k, String x) {
        for (int i = arr.length - 1; i > k; i -= 1) { // Why backwards?
            arr[i] = arr[i - 1];
        }
        /* Or use the array-copying function from the library:
           System.arraycopy (arr, k,          // from
                             arr, k+1,        // to
                             arr.length-k-1); // number to copy
        */

        arr[k] = x;
    }

    /** Return array, r, where r.length = ARR.length+1; r[0..K-1]
     *  the same as ARR[0..K-1], r[k] = X, r[K+1..] same as ARR[K..]. */
    static String[] insert2(String[] arr, int k, String x) {
        String[] result = new String[arr.length + 1];
        System.arraycopy(arr, 0, result, 0, k);
        System.arraycopy(arr, k, result, k + 1, arr.length - k);
        result[k] = x;
        return result;
    }

    /** Demostrate on array ARGS. */
    public static void main(String[] args) {
        System.out.printf("Original: ");
        Utils.print(System.out, args);
        System.out.printf("%nAfter rotating right by one: ");
        insert(args, 0, args[args.length - 1]);
        Utils.print(System.out, args);

        String[] args2 = insert2(args, args.length / 2, "MIDDLE");
        System.out.printf("%nValue returned by args2(args, %d, \"MIDDLE\"): ",
                           args.length / 2);
        Utils.print(System.out, args2);
        System.out.printf("%nargs array after applying args2: ");
        Utils.print(System.out, args);
        System.out.println();
    }

}


