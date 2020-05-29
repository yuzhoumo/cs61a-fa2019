package enigma;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * The suite of all JUnit tests for the Reflector class.
 *
 * @author Joe Mo
 */
public class ReflectorTest {
    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Test(expected = EnigmaException.class)
    public void testSet() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", a);
        Reflector r = new Reflector("I", p);

        r.set(5);
    }

    @Test
    public void validReflector() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", a);
        Reflector r = new Reflector("I", p);

        r.set(0);
    }

    @Test
    public void testRotate() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", a);
        Reflector r = new Reflector("I", p);

        assertFalse(r.rotates());
    }

    @Test
    public void testReflecting() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", a);
        Reflector r = new Reflector("I", p);

        assertTrue(r.reflecting());
    }

    @Test(expected = EnigmaException.class)
    public void invalidDerangement1() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        Reflector r = new Reflector("I", p);
    }

    @Test(expected = EnigmaException.class)
    public void invalidDerangement2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABC) (DEF)", a);
        Reflector r = new Reflector("I", p);
    }
}
