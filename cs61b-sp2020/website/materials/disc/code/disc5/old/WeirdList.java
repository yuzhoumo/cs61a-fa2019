/** A WeirdList holds a sequence of integers. */
public class WeirdList {
    private int head;
    private WeirdList tail;

    /** The empty sequence of integers. */
    public static final WeirdListEmpty EMPTY =
        new WeirdListEmpty();  // REPLACE THIS LINE WITH THE RIGHT ANSWER.

    /** A new WeirdList whose head is HEAD and tail is TAIL. */
    public WeirdList(int head, WeirdList tail) { 
        this.head = head;
        this.tail = tail;
    }

    /** Returns the number of elements in the sequence that
     *  starts with THIS. */
    public int length() {
        return 1 + tail.length();  // REPLACE THIS LINE WITH THE RIGHT ANSWER.
    }

    /** Apply FUNC.apply to every element of THIS WeirdList in
     *  sequence, and return a WeirdList of the resulting values. */
    public WeirdList map(IntUnaryFunction func) {
        return new WeirdList(func.apply(head), tail.map(func));  // REPLACE THIS LINE WITH THE RIGHT ANSWER.
    }

    /** Print the contents of THIS WeirdList on the standard output
     *  (on one line, each followed by a blank).  Does not print
     *  an end-of-line. */
    public void print() { System.out.print(head + " ");
                          tail.print(); }

    // FILL IN WITH *PRIVATE* FIELDS ONLY.
    // You should NOT need any more methods here.
    // You MAY want to add some private nested classes (and methods) to
    // be used by WeirdList (start them off with 'private static class...'),
    // OR you may want to add more class files to those provided in the
    // skeleton.  In the latter case, be sure you commit them as well.
}
