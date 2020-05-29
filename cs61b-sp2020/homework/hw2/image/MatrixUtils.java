package image;

/**
 * Provides a variety of utilities for operating on matrices.
 * All methods assume that the double[][] arrays provided are rectangular.
 *
 * @author Josh Hug and YOU
 */

public class MatrixUtils {
    /**
     * enum for specifying vertical vs. horizontal orientation
     * You can use this, for example, by writing Orientation x = VERTICAL
     * <p>
     * If you're using this from another class, you'll need to use the class
     * name as well, e.g.
     * MatrixUtils.Orientation x = MatrixUtils.Orientation.VERTICAL
     * <p>
     * See http://goo.gl/73xqAu for more.
     */
    enum Orientation { VERTICAL, HORIZONTAL };

    /**
     * Non-destructively accumulates an energy matrix in the vertical
     * direction.
     * <p>
     * Given an energy matrix M, returns a new matrix am[][] such that
     * for each index i, j, the value in am[i][j] is the minimum total
     * energy required to reach position i, j from any spot in the top
     * row. See the bottom of this comment for an example.
     * <p>
     * Potentially useful methods: MatrixUtils.copy
     * <p>
     * A helper method you might consider writing:
     * get(double[][] e, int r, int c): Returns the e[r][c] if
     * r and c are valid. Double.POSITIVE_INFINITY otherwise
     * <p>
     * An example is shown below. See the assignment spec for a
     * detailed explanation of this example.
     * <p>
     * Sample input:
     * 1000000   1000000   1000000   1000000
     * 1000000     75990     30003   1000000
     * 1000000     30002    103046   1000000
     * 1000000     29515     38273   1000000
     * 1000000     73403     35399   1000000
     * 1000000   1000000   1000000   1000000
     * <p>
     * Output for sample input:
     * 1000000   1000000   1000000   1000000
     * 2000000   1075990   1030003   2000000
     * 2075990   1060005   1133049   2030003
     * 2060005   1089520   1098278   2133049
     * 2089520   1162923   1124919   2098278
     * 2162923   2124919   2124919   2124919
     */
    public static double[][] accumulateVertical(double[][] m) {
        double[][] result = copy(m);

        for (int i = 1; i < result.length; i++) {
            for (int j = 0; j < result[0].length; j++) {
                double[] contiguous = new double[]{
                        get(result, i - 1, j - 1),
                        get(result, i - 1, j),
                        get(result, i - 1, j + 1)};
                result[i][j] = result[i][j]
                        + contiguous[minIndex(contiguous, 0, 3)];
            }
        }
        return result;
    }

    /**
     * Takes in an array of doubles and returns the minimum index of value in
     * the array between the given bounds. Assigns positive infinity to number
     * value if index is out of bounds.
     *
     * @param arr Array of doubles
     * @param lo Lower bound index (inclusive)
     * @param hi Upper bound index (non-inclusive)
     * @return {double} Minimum value in input array
     */
    public static int minIndex(double[] arr, int lo, int hi) {
        double min = arr[0];
        int index = 0;
        for (int i = lo; i < hi; i++) {
            if (get1D(arr, i) < min) {
                min = arr[i];
                index = i;
            }
        }

        return index;
    }

    /**
     * Returns the arr[i] if i is valid. Double.POSITIVE_INFINITY otherwise.
     * @param arr Array of doubles
     * @param i Index of array
     * @return {double} value at X, Y if the value is not positive infinity
     */
    public static double get1D(double[] arr, int i) {
        try {
            return arr[i];
        } catch (IndexOutOfBoundsException ex) {
            return Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Returns the arr[x][y] if x and y are valid. Double.POSITIVE_INFINITY
     * otherwise.
     * @param arr Array of doubles
     * @param x Index of column
     * @param y Index of row
     * @return {double} value at X, Y if the value is not positive infinity
     */
    public static double get(double[][] arr, int x, int y) {
        try {
            return arr[x][y];
        } catch (IndexOutOfBoundsException ex) {
            return Double.POSITIVE_INFINITY;
        }
    }

    /**
     * Non-destructively accumulates a matrix M along the specified
     * ORIENTATION.
     * <p>
     * If the orientation is Orientation.VERTICAL, function is identical to
     * accumulateVertical. If Orientation.HORIZONTAL, function is
     * the same, but with roles of r and c reversed.
     * <p>
     * Do NOT copy and paste a bunch of code! Instead, you should write
     * a helper function that creates a new matrix mT that contains all
     * the information from m, but with the property that
     * accumulateVertical(mT) returns the correct result.
     * <p>
     * accumulate should be very short (only a few lines). Most of the
     * work should be done in creating the helper function (and even
     * that function should be pretty short and straightforward).
     * <p>
     * The important lesson here is that you should never have big
     * copy and pastes of any code. Instead, find the right
     * abstraction that lets you avoid this mess. You'll need to do this
     * for project 1, but in a more complex way.
     */
    public static double[][] accumulate(double[][] m, Orientation orientation) {
        if (orientation == Orientation.VERTICAL) {
            return accumulateVertical(m);
        } else if (orientation == Orientation.HORIZONTAL) {
            return transpose(accumulateVertical(transpose(m)));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Takes in matrix of form double[][] and transposes it.
     *
     * @return {double[][]} Transpose of M
     */
    public static double[][] transpose(double[][] m) {
        double[][] mT = new double[m[0].length][m.length];
        for (int i = 0; i < mT.length; i++) {
            for (int j = 0; j < mT[0].length; j++) {
                mT[i][j] = m[j][i];
            }
        }
        return mT;
    }

    /**
     * Finds the vertical seam VERTSEAM of the given matrix M.
     * <p>
     * Potentially useful helper function: Something that takes
     * an array, a lo index, and a hi index, and returns the
     * index of the smallest thing in that array between those
     * indices (inclusive).
     * <p>
     * Such a helper function will keep your code simple.
     * <p>
     * To keep the HW from getting too long, this method (and the
     * next) is optional. however, completing it will allow you to
     * see the resizing algorithm (that you wrote!) in action.
     * <p>
     * Sample input:
     * 1000000   1000000   1000000   1000000
     * 2000000   1075990   1030003   2000000
     * 2075990   1060005   1133049   2030003
     * 2060005   1089520   1098278   2133049
     * 2089520   1162923   1124919   2098278
     * 2162923   2124919   2124919   2124919
     * <p>
     * Output for sample input: {1, 2, 1, 1, 2, 1}.
     * See 4x6.png.verticalSeam.correct for a visual picture.
     * <p>
     * This answer is NOT unique. There are other correct seams.
     * One way to test seam correctness is to check that the
     * total energy is approximately equal.
     */
    public static int[] findVerticalSeam(double[][] m) {
        int w = m[0].length; int h = m.length; int[] result = new int[h];
        int min = result[0] = result[1] = minIndex(m[1], 0, w);

        for (int i = 2; i < h; i++) {
            min = minIndex(m[i], min - 1, min + 1);
            result[i] = min;
        }

        return result;
    }

    /**
     * Returns the SEAM of M with the given ORIENTATION.
     * As with accumulate, this should be really short and use
     * a helper method.
     */
    public static int[] findSeam(double[][] m, Orientation orientation) {
        if (orientation == Orientation.VERTICAL) {
            return findVerticalSeam(m);
        } else if (orientation == Orientation.HORIZONTAL) {
            return findVerticalSeam(transpose(m));
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * does nothing. ARGS not used. use for whatever purposes you'd like
     */
    public static void main(String[] args) {
        /*
        Rescaler sc = new Rescaler("4x6.png");

        double[][] em = Rescaler.energyMatrix(sc);

        // double[][] m = sc.cumulativeEnergyMatrix(true);
        double[][] m = MatrixUtils.accumulateVertical(em);
        System.out.println(MatrixUtils.matrixToString(em));
        System.out.println();

        double[][] ms = MatrixUtils.accumulate(em, Orientation.HORIZONTAL);

        System.out.println(MatrixUtils.matrixToString(m));
        System.out.println();
        System.out.println(MatrixUtils.matrixToString(ms));


        int[] lep = MatrixUtils.findVerticalSeam(m);

        System.out.println(seamToString(m, lep, Orientation.VERTICAL));

        int[] leps = MatrixUtils.findSeam(ms, Orientation.HORIZONTAL);

        System.out.println(seamToString(ms, leps, Orientation.HORIZONTAL));
        */
    }


    /** Below follow some utility functions. Also see Utils.java. */

    /**
     * Returns a string representaiton of the given matrix M.
     */

    public static String matrixToString(double[][] m) {
        int height = m.length;
        int width = m[0].length;
        StringBuilder sb = new StringBuilder();

        for (int r = 0; r < height; r += 1) {
            for (int c = 0; c < width; c += 1) {
                sb.append(String.format("%9.0f ", m[r][c]));
            }

            sb.append("\n");
        }
        return sb.toString();
    }

    /**
     * Returns a copy of the matrix M. Note that it is probably a better idea
     * to use System.arraycopy for general 2D arrays since
     */

    public static double[][] copy(double[][] m) {
        int height = m.length;
        int width = m[0].length;

        double[][] copy = new double[height][];
        for (int r = 0; r < height; r += 1) {
            copy[r] = new double[width];
            double[] thisRow = m[r];
            System.arraycopy(thisRow, 0, copy[r], 0, width);
        }
        return copy;
    }

    /**
     * Checks to see if a given SEAM is valid given a particular
     * image HEIGHT, WIDTH, and a seam ORIENTATION. Throws an illegal
     * argument exception if invalid.
     */

    public static void validateSeam(int height, int width,
                                    int[] seam, Orientation orientation) {

        if ((orientation == Orientation.VERTICAL)
                && (seam.length != height)) {
            throw new IllegalArgumentException("Bad vertical seam length.");
        }

        if ((orientation == Orientation.HORIZONTAL)
                && (seam.length != width)) {
            throw new IllegalArgumentException("Bad horizontal seam length.");
        }

        int maxValue;
        if (orientation == Orientation.VERTICAL) {
            maxValue = width - 1;
        } else {
            maxValue = height - 1;
        }


        for (int i = 0; i < seam.length; i += 1) {
            if (seam[i] < 0 || seam[i] >= maxValue) {
                String msg = "Seam value out of bounds.";
                throw new IllegalArgumentException(msg);
            }
        }

        for (int i = 0; i < seam.length - 1; i += 1) {
            int difference = Math.abs(seam[i] - seam[i + 1]);


            if (difference > 1) {
                String msg = "Seam not contiguous.";
                throw new IllegalArgumentException(msg);
            }
        }
    }


    /**
     * Returns a string representation of  the matrix M annotated with the
     * provided SEAM along the ORIENTATION specified.
     */

    public static String seamToString(double[][] m, int[] seam,
                                      Orientation orientation) {

        StringBuilder sb = new StringBuilder();
        int height = m.length;
        int width = m[0].length;

        validateSeam(height, width, seam, orientation);

        for (int r = 0; r < height; r += 1) {
            for (int c = 0; c < width; c += 1) {
                char lMarker = ' ';
                char rMarker = ' ';
                if (orientation == Orientation.VERTICAL) {
                    if (c == seam[r]) {
                        lMarker = '[';
                        rMarker = ']';
                    }
                } else {
                    if (r == seam[c]) {
                        lMarker = '[';
                        rMarker = ']';
                    }
                }
                sb.append(String.format("%c%6.0f%c ",
                        lMarker, m[r][c], rMarker));
            }
            sb.append("\n");
        }

        return sb.toString();
    }

}
