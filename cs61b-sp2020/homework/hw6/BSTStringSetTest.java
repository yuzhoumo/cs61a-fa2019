import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Test of a BST-based String Set.
 * @author Joe Mo
 */
public class BSTStringSetTest  {

    @Test
    public void testSet() {
        BSTStringSet bst = new BSTStringSet();
        ArrayList<String> all = new ArrayList<>();

        for (int i = 0; i < 500000; i++) {
            bst.put("" + i);
            all.add("" + i);
        }

        for (int i = 0; i < 500000; i++) {
            assertTrue(bst.contains("" + i));
        }

        for (int i = 500000; i < 500100; i++) {
            assertFalse(bst.contains("" + i));
        }

        java.util.Collections.sort(all);
        assertEquals(all, bst.asList());
    }
}
