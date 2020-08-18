import java.io.PrintStream;

public class IntList {

    /** List cell containing (HEAD, TAIL). */
    public IntList(int head, IntList tail) {
        this.head = head; this.tail = tail;
    }

    /** Interpret the elements of ARGS as numerals, returning an IntList
     *  containing the designated integers. */
    public static IntList list(String[] args) {
        IntList L;
        L = null;
        for (int i = args.length - 1; i >= 0; i -= 1)
            L = new IntList(Integer.parseInt(args[i]), L);
        return L;
    }

    /** A new IntList containing the ints in ARGS. */
    /* From HW #1.  The ... syntax here is Java's way of allowing arbitrary
     *  numbers of arguments. */ 
    public static IntList list(int ... args) {
        IntList result, p;

        if (args.length > 0)
            result = new IntList(args[0], null);
        else 
            return null;

        int k;
        for (k = 1, p = result; k < args.length; k += 1, p = p.tail)
            p.tail = new IntList(args[k], null);
        return result;
    }

    /** Print L on OUT. */
    public static void printList(PrintStream out, IntList L) {
        String sep;
        out.print("(");
        sep = "";
        while (L != null) {
            out.print(sep + L.head);
            sep = ", ";
            L = L.tail;
        }
        out.println(")");
    }

    public int head;     
    public IntList tail;
}
