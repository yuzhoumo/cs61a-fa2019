/** Uses of Readers. */
class ReaderClients {

    /** Return The total number of words in R, where a "word" is
     *  a maximal sequence of non-whitespace characters. */
    int wc(Reader r) {
        int c0, count;
        c0 = ' '; count = 0;
        while (true) {
            int c = r.read();
            if (c == -1) {
                return count;
            }
            if (Character.isWhitespace((char) c0)
                && !Character.isWhitespace((char) c)) {
                count += 1;
            }
            c0 = c;
        }
    }

    /** Return the number of words in S. */
    int wordsInString(String s) {
        return wc(new StringReader(s));
    }

}

