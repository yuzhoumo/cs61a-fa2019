import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * Note that every sorting algorithm takes in an argument k. The sorting 
 * algorithm should sort the array from index 0 to k. This argument could
 * be useful for some of your sorts.
 *
 * Class containing all the sorting algorithms from 61B to date.
 *
 * You may add any number instance variables and instance methods
 * to your Sorting Algorithm classes.
 *
 * You may also override the empty no-argument constructor, but please
 * only use the no-argument constructor for each of the Sorting
 * Algorithms, as that is what will be used for testing.
 *
 * Feel free to use any resources out there to write each sort,
 * including existing implementations on the web or from DSIJ.
 *
 * All implementations except Counting Sort adopted from Algorithms,
 * a textbook by Kevin Wayne and Bob Sedgewick. Their code does not
 * obey our style conventions.
 */
public class MySortingAlgorithms {

    /**
     * Java's Sorting Algorithm. Java uses Quicksort for ints.
     */
    public static class JavaSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            Arrays.sort(array, 0, k);
        }

        @Override
        public String toString() {
            return "Built-In Sort (uses quicksort for ints)";
        }
    }

    /** Insertion sorts the provided data. */
    public static class InsertionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 1; i < k; i++) {
                int j = i - 1, key = array[i];
                while (j >= 0 && array[j] > key) {
                    array[j + 1] = array[j];
                    j--;
                }
                array[j + 1] = key;
            }
        }

        @Override
        public String toString() {
            return "Insertion Sort";
        }
    }

    /**
     * Selection Sort for small K should be more efficient
     * than for larger K. You do not need to use a heap,
     * though if you want an extra challenge, feel free to
     * implement a heap based selection sort (i.e. heapsort).
     */
    public static class SelectionSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = 0; i < k; i++) {
                int min = array[i];
                for (int j = k - 1; j > i; j--)
                    if (array[j] < min) {
                        min = array[j];
                        array[j] = array[i];
                        array[i] = min;
                    }
            }
        }

        @Override
        public String toString() {
            return "Selection Sort";
        }
    }

    /** Your mergesort implementation. An iterative merge
      * method is easier to write than a recursive merge method.
      * Note: I'm only talking about the merge operation here,
      * not the entire algorithm, which is easier to do recursively.
      */
    public static class MergeSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            sort(array, 0, k - 1);
        }

        private void sort(int[] array, int lo, int hi) {
            if (lo < hi) {
                int mid = (lo + hi) / 2;
                sort(array, lo, mid);
                sort(array, mid + 1, hi);
                merge(array, lo, mid, hi);
            }
        }

        private void merge(int[] array, int lo, int mid, int hi) {
            int leftSize = mid - lo + 1, rightSize = hi - mid;
            int[] left = new int [leftSize], right = new int [rightSize];
            System.arraycopy(array, lo, left, 0, leftSize);
            System.arraycopy(array, mid + 1, right, 0, rightSize);

            int i = 0, j = 0, k = lo;
            while (i < leftSize && j < rightSize) {
                if (left[i] <= right[j]) {
                    array[k] = left[i];
                    i++;
                } else {
                    array[k] = right[j];
                    j++;
                }
                k++;
            }

            System.arraycopy(left, i, array, k, leftSize - i);
            System.arraycopy(right, j, array, k + leftSize - i, rightSize - j);
        }

        @Override
        public String toString() {
            return "Merge Sort";
        }
    }

    /**
     * Your Counting Sort implementation.
     * You should create a count array that is the
     * same size as the value of the max digit in the array.
     */
    public static class CountingSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            int countLen = max(array, k) + 1;
            int[] count = new int[countLen];
            int[] output = new int[k];

            for (int i = 0; i < k; i++)
                count[array[i]]++;

            for (int i = 1; i < countLen; i++)
                count[i] += count[i - 1];

            for (int i = k - 1; i > -1; i--) {
                output[count[array[i]] - 1] = array[i];
                count[array[i]]--;
            }

            System.arraycopy(output, 0, array, 0, k);
        }

        private int max(int[] array, int k) {
            int max = array[0];
            for (int i = 0; i < k; i++)
                if (array[i] > max)
                    max = array[i];
            return max;
        }

        @Override
        public String toString() {
            return "Counting Sort";
        }
    }

    /** Your Heapsort implementation.
     */
    public static class HeapSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            for (int i = k / 2 - 1; i > -1; i--)
                heapify(array, k, i);

            for (int i = k - 1; i > 0; i--) {
                swap(array, 0, i);
                heapify(array, i, 0);
            }
        }

        private void heapify(int[] array, int size, int nodeIndex) {
            int largest = nodeIndex;
            int left = 2 * nodeIndex + 1;
            int right = left + 1;

            if (left < size && array[left] > array[largest])
                largest = left;

            if (right < size && array[right] > array[largest])
                largest = right;

            if (largest != nodeIndex) {
                swap(array, nodeIndex, largest);
                heapify(array, size, largest);
            }
        }

        @Override
        public String toString() {
            return "Heap Sort";
        }
    }

    /** Your Quicksort implementation.
     */
    public static class QuickSort implements SortingAlgorithm {
        @Override
        public void sort(int[] array, int k) {
            sort(array, 0, k - 1);
        }

        private void sort(int[] array, int lo, int hi) {
            if (lo < hi) {
                int partitionIndex = partition(array, lo, hi);
                sort(array, lo, partitionIndex - 1);
                sort(array, partitionIndex + 1, hi);
            }
        }

        private int partition(int[] array, int lo, int hi) {
            int pivot = array[hi];
            int partitionIndex = lo - 1;

            for (int j = lo; j < hi; j++)
                if (array[j] < pivot) {
                    partitionIndex++;
                    swap(array, partitionIndex, j);
                }

            swap(array, partitionIndex + 1, hi);
            return partitionIndex + 1;
        }

        @Override
        public String toString() {
            return "Quicksort";
        }
    }

    /* For radix sorts, treat the integers as strings of x-bit numbers.  For
     * example, if you take x to be 2, then the least significant digit of
     * 25 (= 11001 in binary) would be 1 (01), the next least would be 2 (10)
     * and the third least would be 1.  The rest would be 0.  You can even take
     * x to be 1 and sort one bit at a time.  It might be interesting to see
     * how the times compare for various values of x. */

    /**
     * LSD Sort implementation.
     */
    public static class LSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            int[] output = new int[k];

            for (int d = 0; d < 4; d++) {
                int[] count = new int[257];
                for (int i = 0; i < k; i++) {
                    int c = (a[i] >> 8 * d) & 255;
                    count[c + 1]++;
                }

                for (int r = 0; r < 256; r++)
                    count[r + 1] += count[r];

                if (d == 3) {
                    int shift1 = count[256] - count[128];
                    int shift2 = count[128];
                    for (int r = 0; r < 128; r++)
                        count[r] += shift1;
                    for (int r = 128; r < 256; r++)
                        count[r] -= shift2;
                }

                for (int i = 0; i < k; i++) {
                    int c = (a[i] >> 8 * d) & 255;
                    output[count[c]++] = a[i];
                }

                System.arraycopy(output, 0, a, 0, k);
            }
        }

        @Override
        public String toString() {
            return "LSD Sort";
        }
    }

    /**
     * MSD Sort implementation.
     */
    public static class MSDSort implements SortingAlgorithm {
        @Override
        public void sort(int[] a, int k) {
            // FIXME
        }

        @Override
        public String toString() {
            return "MSD Sort";
        }
    }

    /** Exchange A[I] and A[J]. */
    private static void swap(int[] a, int i, int j) {
        int swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

}
