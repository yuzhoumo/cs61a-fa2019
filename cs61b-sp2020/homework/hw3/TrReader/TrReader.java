import java.io.Reader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Translating Reader: a stream that is a translation of an
 * <p>
 * existing reader.
 *
 * @author Joe Mo
 */

public class TrReader extends Reader {

    /**
     * A new TrReader that produces the stream of characters produced
     * <p>
     * by STR, converting all characters that occur in FROM to the
     * <p>
     * corresponding characters in TO.  That is, change occurrences of
     * <p>
     * FROM.charAt(i) to TO.charAt(i), for all i, leaving other characters
     * <p>
     * in STR unchanged.  FROM and TO must have the same length.
     */

    private HashMap<Integer, Character> trans = new HashMap<>();
    private Reader str;
    private boolean closed = false;

    public TrReader(Reader str, String from, String to) {
        char[] f = from.toCharArray();
        char[] t = to.toCharArray();
        this.str = str;

        for (int i = 0; i < from.length(); i++) {
            trans.put((int) f[i], t[i]);
        }
    }

    public int read(char[] buf, int offset, int count) throws IOException {
        if (closed) {
            throw new IOException();
        } else {
            int tally = 0;
            for (int i = offset; i < offset + count; i++) {
                int orig = str.read();
                if (orig != -1) {
                    Character val = trans.get(orig);
                    buf[i] = val != null ? val : (char) orig;
                    tally++;
                } else if (tally == 0) {
                    return -1;
                } else {
                    return tally;
                }
            }
        }
        return count;
    }

    public void close() {
        closed = true;
    }
}
