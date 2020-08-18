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
    static void sort(String[] A, int L, int U) { /* "TOMORROW" */ }

    /** Print A on one line, separated by blanks. */
    static void print(String[] A) { /* "TOMORROW" */ }

}

