/**
 * TableFilter to filter for entries whose two columns match.
 *
 * @author Matthew Owen
 */
public class ColumnMatchFilter extends TableFilter {

    public ColumnMatchFilter(Table input, String colName1, String colName2) {
        super(input);
        _index1 = input.colNameToIndex(colName1);
        _index2 = input.colNameToIndex(colName2);
    }

    @Override
    protected boolean keep() {
        Table.TableRow curr = candidateNext();
        return curr.getValue(_index1).equals(curr.getValue(_index2));
    }

    int _index1;
    int _index2;
}
