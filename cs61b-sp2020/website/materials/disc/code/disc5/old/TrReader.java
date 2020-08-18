import java.io.Reader;
import java.io.IOException; /*probably add this?*/

/** Translating Reader: a stream that is a translation of an
 *  existing reader. */
public class TrReader extends Reader {
    private final Reader subReader;
    private final String from;
    private  final String to;

    /** A new TrReader that produces the stream of characters produced
     *  by STR, converting all characters that occur in FROM to the
     *  corresponding characters in TO.  That is, change occurrences of
     *  FROM.charAt(0) to TO.charAt(0), etc., leaving other characters
     *  unchanged.  FROM and TO must have the same length. */
    public TrReader(Reader str, String f, String t) {
        subReader = str;
        from = f;
        to = t;
    }

    // FILL IN
    // NOTE: Until you fill in the right methods, the compiler will
    //       reject this file, saying that you must declare TrReader
    //     abstract.  Don't do that; define the right methods instead!

    public void close() throws IOException {
        subReader.close();
    }

    public int read(char[] cbuf, int off, int len) {
        return 6;
    }

    private int translate(char in) {    
        return 5;
    }

    public int read() {
        return 7;
        
    }    
}


