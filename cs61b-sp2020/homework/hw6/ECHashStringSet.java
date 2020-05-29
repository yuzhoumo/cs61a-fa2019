import java.util.*;

/**
 * A set of String values.
 *
 * @author Joe Mo
 */
class ECHashStringSet implements StringSet {

    @Override
    public void put(String s) {
        putHelper(s, true);
    }

    @Override
    public boolean contains(String s) {
        if (_set[toIndex(s)] == null)
            return false;
        return _set[toIndex(s)].contains(s);
    }

    @Override
    public List<String> asList() {
        return _allItems;
    }

    private void putHelper(String s, boolean addToList) {
        if (_allItems.size() * 2 > _set.length)
            resize();
        if (addToList)
            _allItems.add(s);
        if (!contains(s))
            push(toIndex(s), s);
    }

    private int toIndex(String s) {
        int hash = s.hashCode();
        hash = hash > 0 ? hash : -hash;
        return hash % _set.length;
    }

    private void push(int index, String s) {
        if (_set[index] == null) {
            _set[index] = new Bucket(s, null);
        } else {
            _set[index] = new Bucket(s, _set[index]);
        }
    }

    private void resize() {
        int newSize = nextPrime(_set.length * 2);
        _set = new Bucket[newSize];

        for (String s : _allItems)
            putHelper(s, false);
    }

    private int nextPrime(int n) {
        if (n <= 2)
            return 3;

        while (true) {
            n++;
            if (isPrime(n))
                return n;
        }
    }

    private boolean isPrime(int n) {
        if (n % 2 == 0 || n % 3 == 0)
            return false;

        for (int i = 5; i * i <= n; i += 6)
            if (n % i == 0 || n % (i + 2) == 0)
                return false;

        return true;
    }

    private List<String> _allItems = new ArrayList<>();
    private Bucket[] _set = new Bucket[17];

    private class Bucket {
        public Bucket(String val, Bucket next) {
            _val = val;
            _next = next;
        }

        public boolean contains(String s) {
            Bucket ptr = this;
            while (ptr != null) {
                if (s.equals(ptr._val))
                    return true;
                ptr = ptr._next;
            }
            return false;
        }

        private String _val;
        private Bucket _next;
    }
}
