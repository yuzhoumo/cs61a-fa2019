import org.junit.Test;
import static org.junit.Assert.*;

public class SortTesting {

    /** Copy INPUT into data and run Sort.sort on it. Returns the result. */
    private String[] runSort(String[] input, int L, int U) {
        String[] data = new String[input.length];
        System.arraycopy(input, 0, data, 0, input.length);
        Sort.sort(data, L, U);
        return data;
    }

    @Test
    public void emptyTests() {
        assertArrayEquals("Empty array failed", 
                          new String[] {}, 
                          runSort(new String[] { }, 0, -1));
        assertArrayEquals("Empty array segment failed",
                          new String[] { "red", "green", "blue" },
                          runSort(new String[] { "red", "green", "blue" },
                                  1, 0));
    }

    @Test
    public void singletonTests() {
        assertArrayEquals("Single-element test failed", 
                          new String[] { "red" }, 
                          runSort(new String[] { "red" }, 0, 0));
        assertArrayEquals("Single-element segment failed",
                          new String[] { "red", "green", "blue" },
                          runSort(new String[] { "red", "green", "blue" },
                                  1, 1));
    }

    @Test
    public void reverseTest() {
        assertArrayEquals("Reverse test failed",
                          new String[] { "blue", "green", "red" },
                          runSort(new String[] { "red", "green", "blue" },
                                  0, 2));
    }


}
