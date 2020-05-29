package enigma;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

import static org.junit.Assert.*;

import static enigma.TestUtils.*;

/**
 * The suite of all JUnit tests for the Permutation class.
 *
 * @author Joe Mo
 */
public class PermutationTest {

    /**
     * Testing time limit.
     */
    @Rule
    public Timeout globalTimeout = Timeout.seconds(5);

    /* ***** TESTING UTILITIES ***** */

    private Permutation perm;
    private String alpha = UPPER_STRING;

    /**
     * Check that perm has an alphabet whose size is that of
     * FROMALPHA and TOALPHA and that maps each character of
     * FROMALPHA to the corresponding character of FROMALPHA, and
     * vice-versa. TESTID is used in error messages.
     */
    private void checkPerm(String testId,
                           String fromAlpha, String toAlpha) {
        int N = fromAlpha.length();
        assertEquals(testId + " (wrong length)", N, perm.size());
        for (int i = 0; i < N; i += 1) {
            char c = fromAlpha.charAt(i), e = toAlpha.charAt(i);
            assertEquals(msg(testId, "wrong translation of '%c'", c),
                    e, perm.permute(c));
            assertEquals(msg(testId, "wrong inverse of '%c'", e),
                    c, perm.invert(e));
            int ci = alpha.indexOf(c), ei = alpha.indexOf(e);
            assertEquals(msg(testId, "wrong translation of %d", ci),
                    ei, perm.permute(ci));
            assertEquals(msg(testId, "wrong inverse of %d", ei),
                    ci, perm.invert(ei));
        }
    }

    /* ***** TESTS ***** */

    @Test
    public void checkIdTransform() {
        perm = new Permutation("", UPPER);
        checkPerm("identity", UPPER_STRING, UPPER_STRING);
    }

    @Test
    public void testPermute() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(NIUAD) (OKL)  (P)", a);
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

        p = new Permutation("", a);
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
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(NIUAD) (OKL)  (P)", a);
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

        p = new Permutation("", a);
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

        a = new Alphabet("ABCDE");
        p = new Permutation("", a);
        assertFalse(p.derangement());

        p = new Permutation("(A) (B) (C) (D) (E)", a);
        assertFalse(p.derangement());

        p = new Permutation("(A) (B) (C)", a);
        assertFalse(p.derangement());

        p = new Permutation("(AB) (CD)", a);
        assertFalse(p.derangement());

        p = new Permutation("(ABCDE)", a);
        assertTrue(p.derangement());

        p = new Permutation("(AB) (CDE)", a);
        assertTrue(p.derangement());
    }

    @Test
    public void testSize() {
        Alphabet a;
        Permutation p;

        a = new Alphabet("ABCDEFG");
        p = new Permutation("", a);
        assertEquals(7, p.size());

        a = new Alphabet("1234567890");
        p = new Permutation("", a);
        assertEquals(10, p.size());

        a = new Alphabet("!%#&$");
        p = new Permutation("", a);
        assertEquals(5, p.size());
    }

    @Test
    public void customAlphabetPermute() {
        Alphabet a = new Alphabet("AaBbCcDdEe._");
        Permutation p = new Permutation("(Aa)(Bb)(Cd)(De)(E)(._)", a);
        assertSame(p.alphabet(), a);

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

        p = new Permutation("(AaBbCcDdEe._)", a);
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
    }

    @Test
    public void customAlphabetInvert() {
        Alphabet a = new Alphabet("AaBbCcDdEe._");
        Permutation p = new Permutation("(AaBbCcDdEe._)", a);
        assertSame(p.alphabet(), a);

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
    public void emptyCycles() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("", a);
        assertSame(p.alphabet(), a);
    }

    @Test
    public void validCycles() {
        Alphabet a = new Alphabet();
        Permutation p;

        p = new Permutation("", a);
        p = new Permutation("(NIUAD)(OKL)(P)", a);
        p = new Permutation("(NIUAD)  (OKL) (P)", a);
        p = new Permutation("(NIUAD) \n\n\t (OKL) \t\t(P)", a);
        p = new Permutation("(ABCD)  (EFG) (H)  (IJK)  (LMNOP)", a);
        p = new Permutation("(ABCD)(EFG)(H)(IJK)(LMNOP)", a);
        p = new Permutation("(ABCDEFGHIJKLMNOPQRSTUVWXYZ)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidMissingParentheses() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("ABCDEFGHIJKLMNOPQRSTUVWXYZ", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidMissingContents() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("()", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidMissingContents2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(    )", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidParentheses1() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(NIUAD)OKL) (P)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidParentheses2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(NIUAD) (OKL)) (P)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidParentheses3() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(NIUAD) (OKL)  ((P)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidParentheses4() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABC)()", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidRepeats() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABBC)(ABC)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidAsterisk() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABC)(DEF*)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidNotInAlphabet1() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABC)(@us)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidNotInAlphabet2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABC)(DeFG)", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidWhitespace1() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("( AB C D E) ( F G   )", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidWhitespace2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("  ( AB C D E  ( F G)  ", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidOutsideText1() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABCD) garbage (EFGH) garb", a);
    }

    @Test(expected = EnigmaException.class)
    public void invalidOutsideText2() {
        Alphabet a = new Alphabet();
        Permutation p = new Permutation("(ABCD) , (EFGH)afd", a);
    }
}
