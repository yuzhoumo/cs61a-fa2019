/**
 * TableFilter to filter for entries equal to a given string.
 *
 * @author Matthew Owen
 */
public class EqualityFilter extends TableFilter {

    public EqualityFilter(Table input, String colName, String match) {
        super(input);
        _match = match;
        _index = input.colNameToIndex(colName);
    }

    @Override
    protected boolean keep() {
        Table.TableRow curr = candidateNext();
        return curr.getValue(_index).equals(_match);
    }

    int _index;
    String _match;
}
