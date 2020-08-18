/** A revisionist version of the Java java.io.Reader class as an interface. */
public interface Reader {
    /** Release this stream: further reads are illegal. */
    void close();

    /** Read as many characters as possible, up to LEN,
     *  into BUF[OFF], BUF[OFF+1],..., and return the
     *  number read, or -1 if at end-of-stream. */
    int read(char[] buf, int off, int len);

    /** Returns read(BUF, 0, BUF.length). */
    int read(char[] buf);

    /** Read and return single character, or -1 at end-of-stream. */
    int read();
}
