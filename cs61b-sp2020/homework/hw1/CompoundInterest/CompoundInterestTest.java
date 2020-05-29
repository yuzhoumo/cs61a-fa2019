import static org.junit.Assert.*;

import org.junit.Test;

public class CompoundInterestTest {

    @Test
    public void testNumYears() {
        assertEquals(0, CompoundInterest.numYears(2020));
        assertEquals(1, CompoundInterest.numYears(2021));
    }

    @Test
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(12.544, CompoundInterest.futureValue(10, 12, 2022), tolerance);
        assertEquals(7.744, CompoundInterest.futureValue(10, -12, 2022), tolerance);
        assertEquals(0, CompoundInterest.futureValue(0, 12, 2022), tolerance);
        assertEquals(0, CompoundInterest.futureValue(0, -12, 2022), tolerance);
        assertEquals(10, CompoundInterest.futureValue(10, 0, 2022), tolerance);
    }

    @Test
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(11.8026496, CompoundInterest.futureValueReal(10, 12, 2022, 3), tolerance);
        assertEquals(13.3079296, CompoundInterest.futureValueReal(10, 12, 2022, -3), tolerance);
        assertEquals(0, CompoundInterest.futureValueReal(0, 12, 2022, 3), tolerance);
        assertEquals(0, CompoundInterest.futureValueReal(0, 12, 2022, -3), tolerance);
        assertEquals(10, CompoundInterest.futureValueReal(10, 0, 2022, 0), tolerance);
    }


    @Test
    public void testTotalSavings() {
        double tolerance = 0.01;
        assertEquals(16550, CompoundInterest.totalSavings(5000, 2022, 10), tolerance);
        assertEquals(13550, CompoundInterest.totalSavings(5000, 2022, -10), tolerance);
        assertEquals(0, CompoundInterest.totalSavings(0, 2022, 10), tolerance);
        assertEquals(0, CompoundInterest.totalSavings(0, 2022, -10), tolerance);
        assertEquals(30, CompoundInterest.totalSavings(10, 2022, 0), tolerance);
    }

    @Test
    public void testTotalSavingsReal() {
        double tolerance = 0.01;
        assertEquals(15571.895, CompoundInterest.totalSavingsReal(5000, 2022, 10, 3), tolerance);
        assertEquals(17557.895, CompoundInterest.totalSavingsReal(5000, 2022, 10, -3), tolerance);
        assertEquals(0, CompoundInterest.totalSavingsReal(0, 2022, 10, 3), tolerance);
        assertEquals(0, CompoundInterest.totalSavingsReal(0, 2022, 10, -3), tolerance);
        assertEquals(30, CompoundInterest.totalSavingsReal(10, 2022, 0, 0), tolerance);
    }

    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }
}
