public class FixedHistogram implements Histogram {
    private double low, high; /* From constructor*/
    private int[] count; /* Value counts */

    /** A new histogram with SIZE buckets of values >= LOW and < HIGH. */
    public FixedHistogram(int size, double low, double high)
    {
        if (low >= high || size <= 0) {
            throw new IllegalArgumentException();
        }
        this.low = low; this.high = high;
        this.count = new int[size];
    }

    public int size() {
        return count.length;
    }
    
    public double low(int k) {
        return low + k * (high-low)/count.length;
    }

    public int count(int k) {
        return count[k];
    }

    public void add(double val) {
        if (val >= low && val < high) {
            count[(int) ((val-low)/(high-low) * count.length)] += 1;
        }
    }
}
