package enigma;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import static org.junit.Assert.assertFalse;

/**
 * The suite of all JUnit tests for the Rotor class.
 *
 * @author Joe Mo
 */
public class FixedRotorTest {
    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    @Test
    public void testSet() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        FixedRotor r = new FixedRotor("I", p);

        r.set(5);
    }

    @Test
    public void testRotate() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        FixedRotor r = new FixedRotor("I", p);

        assertFalse(r.rotates());
    }

    @Test
    public void testReflecting() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        FixedRotor r = new FixedRotor("I", p);

        assertFalse(r.reflecting());
    }

    @Test(expected = EnigmaException.class)
    public void invalidSet1() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        FixedRotor r = new FixedRotor("I", p);

        r.set(500);
    }

    @Test(expected = EnigmaException.class)
    public void invalidSet2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        FixedRotor r = new FixedRotor("I", p);

        r.set(-500);
    }
}
