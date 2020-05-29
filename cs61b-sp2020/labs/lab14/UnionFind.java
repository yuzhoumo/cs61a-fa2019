/** Disjoint sets of contiguous integers that allows (a) finding whether
 *  two integers are in the same set and (b) unioning two sets together.  
 *  At any given time, for a structure partitioning the integers 1 to N, 
 *  into sets, each set is represented by a unique member of that
 *  set, called its representative.
 *  @author Joe Mo
 */
public class UnionFind {

    /** A union-find structure consisting of the sets { 1 }, { 2 }, ... { N }.
     */
    public UnionFind(int N) {
        _parents = new int[N + 1];
        _sizes = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            _parents[i] = i;
            _sizes[i] = 1;
        }
    }

    /** Return the representative of the set currently containing V.
     *  Assumes V is contained in one of the sets.  */
    public int find(int v) {
        if (v == _parents[v])
            return v;

        int parent = find(_parents[v]);
        _parents[v] = parent;

        return parent;
    }

    /** Return true iff U and V are in the same set. */
    public boolean samePartition(int u, int v) {
        return find(u) == find(v);
    }

    /** Union U and V into a single set, returning its representative. */
    public int union(int u, int v) {
        if (!samePartition(u, v)) {
            u = find(u); v = find(v);
            int small = _sizes[u] < _sizes[v] ? u : v;
            int large = small == v ? u : v;
            _parents[small] = large;
            _sizes[large] += _sizes[small];
        }

        return find(u);
    }

    int[] _parents;
    int[] _sizes;
}
