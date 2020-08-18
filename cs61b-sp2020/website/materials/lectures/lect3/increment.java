class increment {

    /** Treat ARGS (the command-line arguments) as an array of numerals.
     *  Call ARGS[0] N and let L denote the remaining elements (1 to the end).
     *  Print L after each of several manipulations:
     *    1. First, print L
     *    2. Print L incremented non-destructively by N.
     *    3. Print L again (to see that it has not changed).
     *    4. Print L incremented destructively by N.
     *    5. Print L again (to see that it HAS changed).
     */ 
    public static void main(String[] args) {
        IntList data = IntList.list(args);
        int N = data.head;
        IntList L = data.tail;

        IntList.printList(System.out, L);
        IntList.printList(System.out, incrList(L, N));
        IntList.printList(System.out, L);
        IntList.printList(System.out, dincrList(L, N));
        IntList.printList(System.out, L);
    }

    /* Lecture 4. */
	/** List of all items in P incremented by n. */
    static IntList incrList(IntList P, int n) {
        if (P == null)
            return null;
        else return new IntList(P.head+n, incrList(P.tail, n));
    }

    /* Lecture 5. */
    /** List of all items in P incremented by n. May destroy original. */
    static IntList dincrList(IntList P, int n) {
        if (P == null)
            return null;
        else {
            P.head += n;
            P.tail = dincrList(P.tail, n);  
            return P;
        }
    }

  /* Alternative, iterative, implementation of dincrList:
    static IntList dincrList(IntList L, int n) {
        for (IntList p = L; p != null; p = p.tail)
            p.head += n;
        return L;
    }
  */

}
