package arrays;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * HW #2, Problem #2.
 *
 * @author Joe Mo
 */

public class ArraysTest {
    /**
     * Performs tests for the Arrays class
     */
    @Test
    public void testCatenate() {
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6},
                Arrays.catenate(new int[]{1, 2, 3}, new int[]{4, 5, 6}));

        assertArrayEquals(new int[]{1, 2, 3, 1, 2, 3},
                Arrays.catenate(new int[]{1, 2, 3}, new int[]{1, 2, 3}));

        assertArrayEquals(new int[]{1, 2, 3},
                Arrays.catenate(new int[]{1, 2, 3}, new int[]{}));

        assertArrayEquals(new int[]{1, 2, 3},
                Arrays.catenate(new int[]{}, new int[]{1, 2, 3}));

        assertArrayEquals(new int[]{},
                Arrays.catenate(new int[]{}, new int[]{}));
    }

    @Test
    public void testRemove() {
        assertArrayEquals(new int[]{},
                Arrays.remove(new int[]{1, 2, 3}, 0, 3));

        assertArrayEquals(new int[]{1, 2, 3},
                Arrays.remove(new int[]{1, 2, 3}, 0, 0));

        assertArrayEquals(new int[]{1, 3},
                Arrays.remove(new int[]{1, 2, 3}, 1, 1));

        assertArrayEquals(new int[]{1, 7},
                Arrays.remove(new int[]{1, 2, 3, 4, 5, 6, 7}, 1, 5));
    }

    @Test
    public void testNaturalRuns() {
        assertArrayEquals(
                new int[][]{{1, 2, 3}, {1}, {1, 2, 3}, {1}},
                Arrays.naturalRuns(new int[]{1, 2, 3, 1, 1, 2, 3, 1}));

        assertArrayEquals(
                new int[][]{{5}, {3, 7}, {5}, {4, 6, 9, 10}, {10, 11}},
                Arrays.naturalRuns(new int[]{5, 3, 7, 5, 4, 6, 9, 10, 10, 11}));

        assertArrayEquals(
                new int[][]{{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}},
                Arrays.naturalRuns(new int[]{1, 3, 7, 5, 4, 6, 9, 10, 10, 11}));

        assertArrayEquals(
                new int[][]{{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}},
                Arrays.naturalRuns(new int[]{1, 1, 1, 1, 1, 1, 1, 1}));

        assertArrayEquals(
                new int[][]{{5}, {4}, {3}, {2}, {1}},
                Arrays.naturalRuns(new int[]{5, 4, 3, 2, 1}));

        assertArrayEquals(
                new int[][]{{5}, {4}},
                Arrays.naturalRuns(new int[]{5, 4}));

        assertArrayEquals(
                new int[][]{{1, 2}},
                Arrays.naturalRuns(new int[]{1, 2}));

        assertArrayEquals(
                new int[][]{{10}},
                Arrays.naturalRuns(new int[]{10}));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }
}
