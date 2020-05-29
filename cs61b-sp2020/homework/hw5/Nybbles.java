/** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author Joe Mo
 */
public class Nybbles {

    /** Maximum positive value of a Nybble. */
    public static final int MAX_VALUE = 7;

    /** Return an array of size N. 
    * DON'T CHANGE THIS.*/
    public Nybbles(int N) {
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int i = k / 8;
            int shift = (7 - k % 8) * 4;
            return(_data[i] << shift) >> 28;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < (-MAX_VALUE - 1) || val > MAX_VALUE) {
            throw new IllegalArgumentException();
        } else {
            int i = k / 8;
            int rshift = (k % 8 + 1) * 4;
            int lshift = (8 - k % 8) * 4;
            int first = (((_data[i] >>> rshift) << 4) + val) << (rshift - 4);
            int rest = (_data[i] << lshift) >>> lshift;
            _data[i] = first + rest;
        }
    }

    /** DON'T CHANGE OR ADD TO THESE.*/
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    public int[] _data; //fix

    public static void main(String[] args) {
        int[] x = new int[] {39, 42290814, -35};
        Nybbles n = new Nybbles(24);
        n._data = x;

        for (int i = 0; i< 24; i++) {
            System.out.println(n.get(i));
            //System.out.println(Integer.toBinaryString(n.get(i)));
        }
    }
}
