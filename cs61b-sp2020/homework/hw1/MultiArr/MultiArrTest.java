import static org.junit.Assert.*;

import org.junit.Test;

public class MultiArrTest {

    @Test
    public void testMaxValue() {
        assertEquals(15, MultiArr.maxValue(new int[][]
            {{1, 2, 3, 4, 5}, {6, 7, 8, 9, 10}, {11, 12, 13, 14, 15}}));
        assertEquals(0, MultiArr.maxValue(new int[][]
            {{-1, -2, -3, -4, 0}, {-6, -7, -8, -9, -10}}));
        assertEquals(0, MultiArr.maxValue(new int[][]{{0}}));
        assertEquals(0, MultiArr.maxValue(new int[][]{{}}));
        assertEquals(0, MultiArr.maxValue(new int[][]{}));
    }

    @Test
    public void testAllRowSums() {
        assertArrayEquals(new int[] {-10, 0}, MultiArr.allRowSums(new int[][]
            {{10, -20, 0}, {0, 5, -5}}));
        assertArrayEquals(new int[] {0, 0}, MultiArr.allRowSums(new int[][]
            {{0, 0, 0}, {0, 0, 0}}));
        assertArrayEquals(new int[] {1, -1}, MultiArr.allRowSums(new int[][]
            {{1, 1, -1}, {-1, 1, -1}}));
        assertArrayEquals(new int[] {6, 15}, MultiArr.allRowSums(new int[][]
            {{1, 2, 3}, {4, 5, 6}}));
        assertArrayEquals(new int[] {0}, MultiArr.allRowSums(new int[][]{{}}));
        assertArrayEquals(new int[] {}, MultiArr.allRowSums(new int[][]{}));
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(MultiArrTest.class));
    }
}
