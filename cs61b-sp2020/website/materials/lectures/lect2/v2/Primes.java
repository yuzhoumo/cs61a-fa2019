/* A complete version of Primes, with test cases. */

import org.junit.Test;
import static org.junit.Assert.*;

/** Program for printing prime numbers.
 *  @author P. N. Hilfinger
 */
public class Primes {
    /** Print all primes up to ARGS[0] (interpreted as an
     *  integer), 10 to a line. */
    public static void main(String[] args) {
        printPrimes(Integer.parseInt(args[0]));
    }

    /** Return true iff X is prime. */
    public static boolean isPrime(int x) {
        if (x <= 1) {
            return false;
        } else {
            return !isDivisible(x, 2);
        }
    }

    /** Return true iff X is divisible by
     *  some number >=K and < X,
     *  given K > 1. */
    private static boolean isDivisible(int x, int k) {
        if (k >= x) {
            return false;
        } else if (x % k == 0) {
            return true;
        } else {
            return isDivisible(x, k + 1);
        }
    }

    /** Print all primes up to and including LIMIT, 10 to
     *  a line. */
    public static void printPrimes(int limit) {
        int np;
        np = 0;
        for (int p = 2; p <= limit; p += 1) {
            if (isPrime(p)) {
                System.out.print(p + " ");
                np += 1;
                if (np % 10 == 0) {
                    System.out.println();
                }
            }
        }
        if (np % 10 != 0) {
            System.out.println();
        }
    }

    /** Sample data for testIsPrime. */
    private static final int[] SAMPLE_PRIMES = {
        2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 1021
    };

    /** Sample data for testIsComposite. */
    private static final int[] SAMPLE_COMPOSITES = {
        4, 6, 8, 9, 10, 12, 14, 15, 16, 18, 20, 21, 22, 24, 25, 26, 27, 28,
        30, 32, 33, 34, 35, 36, 38, 39, 40, 42, 44, 45, 46, 48, 49, 50
    };

    /** Check that all numbers in SAMPLE_PRIMES test as prime. */
    @Test
    public void testIsPrime() {
        for (int p : SAMPLE_PRIMES) {
            assertTrue(p + " should be prime.", isPrime(p));
        }
    }

    /** Check that all numbers in SAMPLE_COMPOSITES test as not prime. */
    @Test
    public void testIsComposite() {
        for (int p : SAMPLE_COMPOSITES) {
            assertFalse(p + " should be composite", isPrime(p));
        }
    }

}
