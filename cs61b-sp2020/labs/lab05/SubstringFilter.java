/**
 * TableFilter to filter for containing substrings.
 *
 * @author Matthew Owen
 */
public class SubstringFilter extends TableFilter {

    public SubstringFilter(Table input, String colName, String subStr) {
        super(input);
        _index = input.colNameToIndex(colName);
        _sub = subStr;
    }

    @Override
    protected boolean keep() {
        Table.TableRow curr = candidateNext();
        return curr.getValue(_index).contains(_sub);
    }

    int _index;
    String _sub;
}
