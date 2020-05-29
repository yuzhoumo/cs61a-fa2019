package lists;

/**
 * HW #2, Problem #1.
 */

/**
 * List problem.
 *
 * @author Joe Mo
 */
class Lists {

    /* B. */

    /**
     * Return the list of lists formed by breaking up L into "natural runs":
     * that is, maximal strictly ascending sublists, in the same order as
     * the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     * then result is the four-item list
     * ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     * Destructive: creates no new IntList items, and may modify the
     * original list pointed to by L.
     */
    static IntListList naturalRuns(IntList L) {
        return makeRuns(L, L);
    }

    /**
     * Recursive destructive helper method for naturalRuns.
     *
     * @param L Input IntList to make natural runs for
     * @param curr Pointer to keep track of position in L
     * @return {IntListList} IntListList of strictly ascending natural runs
     * from L
     */
    static IntListList makeRuns(IntList L, IntList curr) {
        if (curr.tail == null) {
            return new IntListList(L, null);
        } else if (curr.head >= curr.tail.head) {
            IntList next = curr.tail;
            curr.tail = null;
            return new IntListList(L, makeRuns(next, next));
        }
        return makeRuns(L, curr.tail);
    }
}
