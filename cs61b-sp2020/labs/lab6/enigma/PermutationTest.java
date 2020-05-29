package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class. For the purposes of
 * this lab (in order to test) this is an abstract class, but in proj1, it will
 * be a concrete class. If you want to copy your tests for proj1, you can make
 * this class concrete by removing the 4 abstract keywords and implementing the
 * 3 abstract methods.
 *
 * @author Joe Mo
 */
public abstract class PermutationTest {

    /**
     * For this lab, you must use this to get a new Permutation,
     * the equivalent to:
     * new Permutation(cycles, alphabet)
     *
     * @return a Permutation with cycles as its cycles and alphabet as
     * its alphabet
     * @see Permutation for description of the Permutation conctructor
     */
    abstract Permutation getNewPermutation(String cycles, Alphabet alphabet);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet(chars)
     *
     * @return an Alphabet with chars as its characters
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet(String chars);

    /**
     * For this lab, you must use this to get a new Alphabet,
     * the equivalent to:
     * new Alphabet()
     *
     * @return a default Alphabet with characters ABCD...Z
     * @see Alphabet for description of the Alphabet constructor
     */
    abstract Alphabet getNewAlphabet();

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /**
     * Check that PERM has an ALPHABET whose size is that of
     * FROMALPHA and TOALPHA and that maps each character of
     * FROMALPHA to the corresponding character of FROMALPHA, and
     * vice-versa. TESTID is used in error messages.
     */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha,
                           Permutation perm, Alphabet alpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.toInt(c), ei = alpha.toInt(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        Alphabet alpha = getNewAlphabet();
        Permutation p = getNewPermutation("", alpha);
        checkPerm("identity", UPPER_STRING, UPPER_STRING, p, alpha);
    }

    @Test
    public void testValidCycles() {
        Alphabet a = getNewAlphabet();
        Permutation p;

        p = getNewPermutation("", a);
        p = getNewPermutation("(NIUAD)(OKL)(P)", a);
        p = getNewPermutation("(NIUAD)  (OKL) (P)", a);
        p = getNewPermutation("(NIUAD) \n\n\t (OKL) \t\t(P)", a);
        p = getNewPermutation("(ABCD)  (EFG) (H)  (IJK)  (LMNOP)", a);
        p = getNewPermutation("(ABCD)(EFG)(H)(IJK)(LMNOP)", a);
        p = getNewPermutation("ABCDEFGHIJKLMNOPQRSTUVWXYZ", a);

        boolean thrown = false;
        try {
            p = getNewPermutation("()", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(  )", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(NIUAD)OKL) (P)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(NIUAD) (OKL)) (P)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(NIUAD) (OKL)  ((P)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(ABC)()", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(ABBC)(ABC)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(ABC)(DEF*)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(ABC)(@us)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("(ABC)(DeFG)", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("( AB C D E) ( F G   )", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
        thrown = false;

        try {
            p = getNewPermutation("  ( AB C D E  ( F G)  ", a);
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testCustomAlphabet() {
        Alphabet a;
        Permutation p;

        a = getNewAlphabet();
        p = getNewPermutation("", a);
        assertSame(p.alphabet(), a);

        a = getNewAlphabet("AaBbCcDdEe._");
        p = getNewPermutation("(Aa)(Bb)(Cd)(De)(E)(._)", a);
        assertEquals('a', p.permute('A'));
        assertEquals('A', p.permute('a'));
        assertEquals('b', p.permute('B'));
        assertEquals('B', p.permute('b'));
        assertEquals('d', p.permute('C'));
        assertEquals('C', p.permute('d'));
        assertEquals('e', p.permute('D'));
        assertEquals('D', p.permute('e'));
        assertEquals('E', p.permute('E'));
        assertEquals('_', p.permute('.'));
        assertEquals('.', p.permute('_'));

        p = getNewPermutation("(AaBbCcDdEe._)", a);
        assertEquals(1, p.permute(0));
        assertEquals(2, p.permute(1));
        assertEquals(3, p.permute(2));
        assertEquals(4, p.permute(3));
        assertEquals(5, p.permute(4));
        assertEquals(6, p.permute(5));
        assertEquals(7, p.permute(6));
        assertEquals(8, p.permute(7));
        assertEquals(9, p.permute(8));
        assertEquals(10, p.permute(9));
        assertEquals(11, p.permute(10));
        assertEquals(0, p.permute(11));

        assertEquals('A', p.invert('a'));
        assertEquals('a', p.invert('B'));
        assertEquals('B', p.invert('b'));
        assertEquals('b', p.invert('C'));
        assertEquals('C', p.invert('c'));
        assertEquals('c', p.invert('D'));
        assertEquals('D', p.invert('d'));
        assertEquals('d', p.invert('E'));
        assertEquals('E', p.invert('e'));
        assertEquals('e', p.invert('.'));
        assertEquals('_', p.invert('A'));

        assertEquals(0, p.invert(1));
        assertEquals(1, p.invert(2));
        assertEquals(2, p.invert(3));
        assertEquals(3, p.invert(4));
        assertEquals(4, p.invert(5));
        assertEquals(5, p.invert(6));
        assertEquals(6, p.invert(7));
        assertEquals(7, p.invert(8));
        assertEquals(8, p.invert(9));
        assertEquals(9, p.invert(10));
        assertEquals(10, p.invert(11));
        assertEquals(11, p.invert(0));
    }

    @Test
    public void testPermute() {
        Alphabet a = getNewAlphabet();
        Permutation p = getNewPermutation("(NIUAD) (OKL)  (P)", a);
        assertEquals('I', p.permute('N'));
        assertEquals('U', p.permute('I'));
        assertEquals('A', p.permute('U'));
        assertEquals('D', p.permute('A'));
        assertEquals('N', p.permute('D'));

        assertEquals(8, p.permute(13));
        assertEquals(20, p.permute(8));
        assertEquals(0, p.permute(20));
        assertEquals(3, p.permute(0));
        assertEquals(13, p.permute(3));

        assertEquals('K', p.permute('O'));
        assertEquals('L', p.permute('K'));
        assertEquals('O', p.permute('L'));
        assertEquals('P', p.permute('P'));

        assertEquals(3, p.permute(26));
        assertEquals(3, p.permute(-26));
        assertEquals(1, p.permute(27));
        assertEquals(25, p.permute(-27));

        assertEquals('B', p.permute('B'));
        assertEquals('C', p.permute('C'));

        assertEquals(26, p.size());
        assertFalse(p.derangement());

        p = getNewPermutation("", a);
        for (int i = 0; i < a.size(); i++) {
            assertEquals(a.toChar(i), p.permute(a.toChar(i)));
            assertEquals(i, p.permute(i));
        }

        boolean thrown = false;
        try {
            p.permute('!');
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testInvert() {
        Alphabet a = getNewAlphabet();
        Permutation p = getNewPermutation("(NIUAD) (OKL)  (P)", a);
        assertEquals('N', p.invert('I'));
        assertEquals('I', p.invert('U'));
        assertEquals('U', p.invert('A'));
        assertEquals('A', p.invert('D'));
        assertEquals('D', p.invert('N'));

        assertEquals(13, p.invert(8));
        assertEquals(8, p.invert(20));
        assertEquals(20, p.invert(0));
        assertEquals(0, p.invert(3));
        assertEquals(3, p.invert(13));

        assertEquals('O', p.invert('K'));
        assertEquals('K', p.invert('L'));
        assertEquals('L', p.invert('O'));
        assertEquals('P', p.invert('P'));

        assertEquals('B', p.invert('B'));
        assertEquals('C', p.invert('C'));

        assertEquals(20, p.invert(26));
        assertEquals(20, p.invert(-26));
        assertEquals(1, p.invert(27));
        assertEquals(25, p.invert(-27));

        assertEquals(26, p.size());
        assertFalse(p.derangement());

        p = getNewPermutation("", a);
        for (int i = 0; i < a.size(); i++) {
            assertEquals(a.toChar(i), p.invert(a.toChar(i)));
            assertEquals(i, p.invert(i));
        }

        boolean thrown = false;
        try {
            p.invert('!');
        } catch (EnigmaException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    @Test
    public void testDerangement() {
        Alphabet a;
        Permutation p;

        a = getNewAlphabet("ABCDE");
        p = getNewPermutation("", a);
        assertFalse(p.derangement());

        p = getNewPermutation("(A) (B) (C) (D) (E)", a);
        assertFalse(p.derangement());

        p = getNewPermutation("(A) (B) (C)", a);
        assertFalse(p.derangement());

        p = getNewPermutation("(AB) (CD)", a);
        assertFalse(p.derangement());

        p = getNewPermutation("(ABCDE)", a);
        assertTrue(p.derangement());

        p = getNewPermutation("(AB) (CDE)", a);
        assertTrue(p.derangement());
    }

    @Test
    public void testSize() {
        Alphabet a;
        Permutation p;

        a = getNewAlphabet("ABCDEFG");
        p = getNewPermutation("", a);
        assertEquals(7, p.size());

        a = getNewAlphabet("1234567890");
        p = getNewPermutation("", a);
        assertEquals(10, p.size());

        a = getNewAlphabet("!%#&$");
        p = getNewPermutation("", a);
        assertEquals(5, p.size());
    }
}
