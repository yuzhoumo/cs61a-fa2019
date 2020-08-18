/* (A slightly modified version of) code developed during class. */

/* On the command line, compile with
     javac -g Sort.java
   and run with
     java Sort WORD1 WORD2 ...
   so that
     java Sort the quick brown fox jumps over the lazy dog.
   prints
     brown dog. fox jumps lazy over quick the the

*/

public class Sort {
    /** Sort and print WORDS lexicographically. */
    public static void main(String[] words) {
        sort(words, 0, words.length-1);
        print(words);
    }

    /** Sort items A[L..U], with all others unchanged. */
    static void sort(String[] A, int L, int U) { 
        if (L >= U) 
            return;
        swap(A, L, smallest(A, L, U));
        sort(A, L + 1, U);
    }
        
    private static int smallest(String[] A, int L, int U) {
        if (L == U)
            return L;
        int k = smallest(A, L + 1, U);
        if (A[L].compareTo(A[k]) < 0)
            return L;
        else
            return k;
    }

    /** Swap elements I0 and I1 of A. */
    private static void swap(String[] A, int i0, int i1) {
        String tmp = A[i0];
        A[i0] = A[i1];
        A[i1] = tmp;
    }

    /** Print A on one line, separated by blanks. */
    static void print(String[] A) { /* "TOMORROW" */ }

}

