package lists;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Perform tests for the Lists class
 *
 * @author Joe Mo
 */

public class ListsTest {
    @Test
    public void testIntListList() {
        assertEquals(
                IntListList.list(
                        new int[][]{{1, 2, 3}, {1}, {1, 2, 3}, {1}}),
                Lists.naturalRuns(
                        IntList.list(new int[]{1, 2, 3, 1, 1, 2, 3, 1})));

        assertEquals(
                IntListList.list(
                        new int[][]{{5}, {3, 7}, {5}, {4, 6, 9, 10}, {10, 11}}),
                Lists.naturalRuns(
                        IntList.list(
                                new int[]{5, 3, 7, 5, 4, 6, 9, 10, 10, 11})));

        assertEquals(
                IntListList.list(
                        new int[][]{{1, 3, 7}, {5}, {4, 6, 9, 10}, {10, 11}}),
                Lists.naturalRuns(
                        IntList.list(
                                new int[]{1, 3, 7, 5, 4, 6, 9, 10, 10, 11})));

        assertEquals(
                IntListList.list(
                        new int[][]{{1}, {1}, {1}, {1}, {1}, {1}, {1}, {1}}),
                Lists.naturalRuns(
                        IntList.list(new int[]{1, 1, 1, 1, 1, 1, 1, 1})));

        assertEquals(
                IntListList.list(new int[][]{{5}, {4}, {3}, {2}, {1}}),
                Lists.naturalRuns(IntList.list(new int[]{5, 4, 3, 2, 1})));

        assertEquals(
                IntListList.list(new int[][]{{5}, {4}}),
                Lists.naturalRuns(IntList.list(new int[]{5, 4})));

        assertEquals(
                IntListList.list(new int[][]{{1, 2}}),
                Lists.naturalRuns(IntList.list(new int[]{1, 2})));

        assertEquals(
                IntListList.list(new int[][]{{10}}),
                Lists.naturalRuns(IntList.list(new int[]{10})));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }
}
