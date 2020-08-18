import java.util.ArrayList;

class FlexHistogram implements Histogram {
    private ArrayList<Double> values = new ArrayList<>();
    int size;
    double low, high;
    private int[] count;

    public FlexHistogram(int size) {
        this.size = size;
        this.count = null;
    }

    public int size() {
        return size;
    }
    
    public double low(int k) {
        makeCount();
        return low + k * (high-low)/size;
    }

    public void add(double x) {
        count = null;
        values.add(x);
    }

    public int count(int k) {
        makeCount();
        return count[k];
    }

    private void makeCount() {
        if (count == null) { 
            count = new int[size];
            if (values.size() == 0) {
                low = high = 0.0;
                return;
            }
            low = Double.MAX_VALUE;
            high = Double.MIN_VALUE;
            for (double val : values) {
                low = Math.min(low, val);
                high = Math.max(high, val);
            }
            for (double val : values) {
                if (val >= high) {
                    count[count.length - 1] += 1;
                } else {
                    count[(int) ((val-low)/(high-low) * count.length)] += 1;
                }
            }
        }
    }
}
