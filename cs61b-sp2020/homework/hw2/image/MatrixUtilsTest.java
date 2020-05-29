package image;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * HW #2 Problem #3
 *
 * @author Joe Mo
 */

public class MatrixUtilsTest {
    /**
     * Tests for MatrixUtils class
     */

    @Test
    public void testAccumulateVertical() {
        assertArrayEquals(
            new double[][]{
                    {1000000.0, 1000000.0, 1000000.0, 1000000.0},
                    {2000000.0, 1075990.0, 1030003.0, 2000000.0},
                    {2075990.0, 1060005.0, 1133049.0, 2030003.0},
                    {2060005.0, 1089520.0, 1098278.0, 2133049.0},
                    {2089520.0, 1162923.0, 1124919.0, 2098278.0},
                    {2162923.0, 2124919.0, 2124919.0, 2124919.0}},

                MatrixUtils.accumulateVertical(
                    new double[][]{
                            {1000000.0, 1000000.0, 1000000.0, 1000000.0},
                            {1000000.0, 75990.0, 30003.0, 1000000.0},
                            {1000000.0, 30002.0, 103046.0, 1000000.0},
                            {1000000.0, 29515.0, 38273.0, 1000000.0},
                            {1000000.0, 73403.0, 35399.0, 1000000.0},
                            {1000000.0, 1000000.0, 1000000.0, 1000000.0}}));
    }

    @Test
    public void testAccumulate() {
        assertArrayEquals(
            new double[][]{
                    {1000000.0, 1000000.0, 1000000.0, 1000000.0},
                    {2000000.0, 1075990.0, 1030003.0, 2000000.0},
                    {2075990.0, 1060005.0, 1133049.0, 2030003.0},
                    {2060005.0, 1089520.0, 1098278.0, 2133049.0},
                    {2089520.0, 1162923.0, 1124919.0, 2098278.0},
                    {2162923.0, 2124919.0, 2124919.0, 2124919.0}},

                MatrixUtils.accumulate(
                    new double[][]{
                            {1000000.0, 1000000.0, 1000000.0, 1000000.0},
                            {1000000.0, 75990.0, 30003.0, 1000000.0},
                            {1000000.0, 30002.0, 103046.0, 1000000.0},
                            {1000000.0, 29515.0, 38273.0, 1000000.0},
                            {1000000.0, 73403.0, 35399.0, 1000000.0},
                            {1000000.0, 1000000.0, 1000000.0, 1000000.0}},
                        MatrixUtils.Orientation.VERTICAL));

        assertArrayEquals(
            new double[][]{
                    {1000000.0, 2000000.0, 2075990.0, 2060005.0},
                    {1000000.0, 1075990.0, 1060005.0, 2060005.0},
                    {1000000.0, 1030002.0, 1132561.0, 2060005.0},
                    {1000000.0, 1029515.0, 1067788.0, 2064914.0},
                    {1000000.0, 1073403.0, 1064914.0, 2064914.0},
                    {1000000.0, 2000000.0, 2073403.0, 2064914.0}},

                MatrixUtils.accumulate(
                    new double[][]{
                            {1000000.0, 1000000.0, 1000000.0, 1000000.0},
                            {1000000.0, 75990.0, 30003.0, 1000000.0},
                            {1000000.0, 30002.0, 103046.0, 1000000.0},
                            {1000000.0, 29515.0, 38273.0, 1000000.0},
                            {1000000.0, 73403.0, 35399.0, 1000000.0},
                            {1000000.0, 1000000.0, 1000000.0, 1000000.0}},
                        MatrixUtils.Orientation.HORIZONTAL));
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(MatrixUtilsTest.class));
    }
}
