/** A Reader that takes characters from a String. */
public class StringReader extends AbstractReader {
    /** My source string. */
    private String str;
    /** Position in str. */
    private int k;

    /** A Reader that delivers the characters in S. */
    public StringReader(String s) {
        str = s; k = 0;
    }

    @Override
    public void close() {
        str = null;
    }

    @Override
    public int read(char[] buf, int off, int len) {
        if (k == str.length()) {
            return -1;
        }
        len = Math.min(len, str.length() - k);
        str.getChars(k, k + len, buf, off);
        k += len;
        return len;
    }
}
