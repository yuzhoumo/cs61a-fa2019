/** A partial implementation of Reader. Concrete
 *  implementations MUST override close and read(,,).
 *  They MAY override the other read methods for speed. */
public abstract class AbstractReader implements Reader {

    @Override
    public abstract void close();

    @Override
    public abstract int read(char[] buf, int off, int len);

    @Override
    public int read(char[] buf) {
        return read(buf, 0, buf.length);
    }

    @Override
    public int read() {
        return (read(buf1) == -1) ? -1 : buf1[0];
    }

    /** Private buffer used by read(). */
    private char[] buf1 = new char[1];
}
