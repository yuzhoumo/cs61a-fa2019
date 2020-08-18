import java.util.ArrayList;
import java.util.List;
public class ListUtils {
    /** Apply a function of two arguments cumulatively to the 
     * elements of list and return a single accumulated value. */
    static int reduce(? func, List<Integer> list) {

    }

    public static void main(String[] args) {
        ArrayList<Integer> integers = new ArrayList<>();
        integers.add(2); integers.add(3); integers.add(4);
        ? add = ?;
        ? mult = ?;
        reduce(add, integers); //Should evaluate to 9
        reduce(mult, integers); //Should evaluate to 24
    }
}
