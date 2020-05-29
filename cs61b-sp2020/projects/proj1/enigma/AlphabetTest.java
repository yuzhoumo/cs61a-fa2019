package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

/**
 * The suite of all JUnit tests for the Alphabet class.
 *
 * @author Joe Mo
 */
public class AlphabetTest {

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Test
    public void testSize() {
        Alphabet a;

        a = new Alphabet("ABCDEFG");
        assertEquals(7, a.size());

        a = new Alphabet("1234567890");
        assertEquals(10, a.size());

        a = new Alphabet("!%#&$");
        assertEquals(5, a.size());
    }

    @Test
    public void validAlphabets() {
        Alphabet a;

        a = new Alphabet();
        a = new Alphabet("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
        a = new Alphabet("ABCDEF1234567890@!#$%\\.,/");
    }

    @Test(expected = EnigmaException.class)
    public void invalidEmpty() {
        Alphabet a = new Alphabet("");
    }

    @Test(expected = EnigmaException.class)
    public void invalidRepeats() {
        Alphabet a = new Alphabet("ABBC");
    }

    @Test(expected = EnigmaException.class)
    public void invalidWhitespace1() {
        Alphabet a = new Alphabet("ABCD  EFG");
    }

    @Test(expected = EnigmaException.class)
    public void invalidWhitespace2() {
        Alphabet a = new Alphabet("ABCD\nEFG");
    }

    @Test(expected = EnigmaException.class)
    public void invalidWhitespace3() {
        Alphabet a = new Alphabet("ABCD\tEFG");
    }

    @Test(expected = EnigmaException.class)
    public void invalidAsterisk() {
        Alphabet a = new Alphabet("AB*CDEFG");
    }

    @Test(expected = EnigmaException.class)
    public void invalidParenthesis1() {
        Alphabet a = new Alphabet("AB(CDEFG");
    }

    @Test(expected = EnigmaException.class)
    public void invalidParenthesis2() {
        Alphabet a = new Alphabet("AB)CDEFG");
    }
}
