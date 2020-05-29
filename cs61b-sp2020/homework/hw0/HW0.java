public class HW0 {
    public static int max(int[] a) {
        int maxVal = a[0];
        for (int n : a)
            if (maxVal < n)
                maxVal = n;
        return maxVal;
    }

    public static boolean threeSum(int[] a) {
        for (int i : a)
            for (int j : a)
                for (int k : a)
                    if (i + j + k == 0)
                        return true;
        return false;
    }

    public static boolean threeSumDistinct(int[] a) {
        for (int i = 0; i < a.length; i++)
            for (int j = 0; j < a.length; j++)
                for (int k = 0; k < a.length; k++)
                    if (a[i] + a[j] + a[k] == 0 && i != j && i != k && j != k)
                        return true;
        return false;
    }
}