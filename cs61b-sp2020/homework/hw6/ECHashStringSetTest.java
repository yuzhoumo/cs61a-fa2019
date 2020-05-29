import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a Hash-based String Set.
 * @author Joe Mo
 */
public class ECHashStringSetTest  {
    @Test
    public void testSet() {
        ECHashStringSet ec = new ECHashStringSet();
        ArrayList<String> all = new ArrayList<>();

        for (int i = 0; i < 500000; i++) {
            ec.put("" + i);
            all.add("" + i);
        }

        for (int i = 0; i < 500000; i++) {
            assertTrue(ec.contains("" + i));
        }

        for (int i = 500000; i < 500100; i++) {
            assertFalse(ec.contains("" + i));
        }

        assertEquals(all, ec.asList());
    }
}
