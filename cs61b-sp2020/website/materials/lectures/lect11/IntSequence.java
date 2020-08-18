import java.util.Comparable;
import java.util.Arrays;

/** A sequence of 0 or more ints. */
public class IntSequence implements Comparable<IntSequence> {
    /** Current sequence in myValues[0 .. myCount-1]. */
    private int[] myValues;
    /** Size of current sequence. */
    private int myCount;

    /** An initially empty sequence. */
    public IntSequence() {
        myCount = 0;
        myValues = new int[8];
    }

    /** Return my current size. */
    public int size() {
        return myCount;
    }

    /** Return item #K in this sequence, 0 <= K < size(). */
    public int get(int k) {
        boundsCheck(k);
        return myValues[k];
    }

    /** Add VAL to the end of my sequence. */
    public void add(int val) {
        if (myCount + 1 >= myValues.length) {
            myValues = Arrays.copyOf(myValues, 2 * myValues.length);
        }
        myValues[myCount] = val;
        myCount += 1;
    }

    /** Set item K to VAL, 0 <= K < size(). */
    public void set(int k, int val) {
        boundsCheck(k);
        myValues[k] = val;
    }

    @Override
    public int compareTo(IntSequence x) {
        for (int i = 0; i < myCount && i < x.myCount; i += 1) {
            if (myValues[i] < x.myValues[i]) {
                return -1;
            } else if (myValues[i] > x.myValues[i]) {
                return 1;
            }
        }
        return myCount - x.myCount;
    }

    /** Check that index K is in bounds. */
    private void boundsCheck(int k) {
        if (k < 0 || k >= myCount) {
            throw new IndexOutOfBoundsException("" + k);
        }
    }
}
