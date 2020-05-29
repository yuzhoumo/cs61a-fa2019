import org.junit.Test;

import static org.junit.Assert.*;

import ucb.junit.textui;

/**
 * Tests for hw0.
 *
 * @author Joe Mo
 */
public class Tester {

    /* Feel free to add your own tests.  For now, you can just follow
     * the pattern you see here.  We'll look into the details of JUnit
     * testing later.
     *
     * To actually run the tests, just use
     *      java Tester
     * (after first compiling your files).
     *
     * DON'T put your HW0 solutions here!  Put them in a separate
     * class and figure out how to call them from here.  You'll have
     * to modify the calls to max, threeSum, and threeSumDistinct to
     * get them to work, but it's all good practice! */

    @Test
    public void maxTest() {
        assertEquals(14, HW0.max(new int[]{0, -5, 2, 14, 10}));
        assertEquals(0, HW0.max(new int[]{0, 0, 0, 0, 0, 0}));
        assertEquals(0, HW0.max(new int[]{-5, -1, 0, -14, -10}));
        assertEquals(-2, HW0.max(new int[]{-5, -2, -14, -10}));
    }

    @Test
    public void threeSumTest() {
        assertTrue(HW0.threeSum(new int[]{-6, 3, 10, 200}));
        assertTrue(HW0.threeSum(new int[]{-1, 1, 0}));
        assertTrue(HW0.threeSum(new int[]{0, 0, 0}));

        assertFalse(HW0.threeSum(new int[]{1, 1, 1}));
        assertFalse(HW0.threeSum(new int[]{-1, -3, 1}));
    }

    @Test
    public void threeSumDistinctTest() {
        assertFalse(HW0.threeSumDistinct(new int[]{-6, 3, 10, 200}));
        assertFalse(HW0.threeSumDistinct(new int[]{-1, 2, 0}));
        assertFalse(HW0.threeSum(new int[]{1, 1, 1}));
        assertFalse(HW0.threeSum(new int[]{-1, -3, 1}));
    }

    public static void main(String[] unused) {
        textui.runClasses(Tester.class);
    }

}