class remove {

    /** Treat ARGS (the command-line arguments) as an array of numerals.
     *  Call ARGS[0] N and let L denote the remaining elements (1 to the end).
     *  Print L after each of several manipulations:
     *    1. First, print L
     *    2. Print L with all N's removed non-destructively.
     *    3. Print L again (to see that it has not changed).
     *    4. Print L with all N's removed destructively.
     *    5. Print L again (to see that it HAS changed).
     */ 
    public static void main(String[] args) {
        IntList data = IntList.list(args);
        int N = data.head;
        IntList L = data.tail;

        IntList.printList(System.out, L);
        IntList.printList(System.out, removeAll(L, N));
        IntList.printList(System.out, L);
        IntList.printList(System.out, dremoveAll(L, N));
        IntList.printList(System.out, L);
    }
        

    /** The list resulting from removing all instances of X from L
     *  non-destructively. */
    static IntList removeAll(IntList L, int x) {
        IntList result, last;  
        result = last = null;
        for ( ; L != null; L = L.tail) {
            /* L != null 
               and result points to a list containing all values 
                   the original list up to but not including L, incremented
                   by x
               and last points to the last item in result, if any. */
            if (x == L.head)
                continue;
            else if (last == null)
                result = last = new IntList(L.head, null);
            else
                last = last.tail = new IntList(L.head, null);
        }
        return result;
    }

 /* Here is a recursive implementation of removeAll.  In this case, it
  * is much simpler.

    static IntList removeAll(IntList L, int x) {
        if (L == null)
            return null;
        else if (L.head == x)
            return removeAll(L.tail, x);
        else
            return new IntList(L.head, removeAll(L.tail, x));
    }

  */

    /** The list resulting from removing all instances of X from L.
     *  Original contents of L may be destroyed. */
    static IntList dremoveAll(IntList L, int x) {
        IntList result, last;  
        result = last = null;
        while (L != null) {
            IntList next = L.tail;
            if (x != L.head) {
                if (last == null)
                    result = last = L;
                else
                    last = last.tail = L;
                L.tail = null;
            }
            L = next;
        }
        return result;
    }


 /* Equivalent recursive implementation of dremoveAll.  Again, you can 
  * see it is considerably simpler.

    static IntList dremoveAll(IntList L, int x) {
        if (L == null)
            return null;
        else if (L.head == x)
            return dremoveAll(L.tail, x);
        else {
            L.tail = dremoveAll(L.tail, x);
            return L;
        }
    }
 */

}
