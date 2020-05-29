import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * Implementation of a BST based String Set.
 *
 * @author Joe Mo
 */
public class BSTStringSet implements SortedStringSet, Iterable<String> {
    /**
     * Creates a new empty set.
     */
    public BSTStringSet() {
        _root = null;
    }

    @Override
    public void put(String s) {
        _root = putHelper(_root, s);
    }

    private Node putHelper(Node n, String s) {
        if (n == null) return new Node(s);

        if (s.compareTo(n.s) == 0) {
            n.s = s;
        } else if (s.compareTo(n.s) < 0) {
            n.left = putHelper(n.left, s);
        } else {
            n.right = putHelper(n.right, s);
        }
        return n;
    }


    @Override
    public boolean contains(String s) {
        return containsHelper(_root, s);
    }

    private boolean containsHelper(Node n, String s) {
        if (n == null) {
            return false;
        } else if (s.equals(n.s)) {
            return true;
        } else if (s.compareTo(n.s) < 0) {
            return containsHelper(n.left, s);
        } else {
            return containsHelper(n.right, s);
        }
    }

    @Override
    public List<String> asList() {
        ArrayList<String> result = new ArrayList<>();
        BSTIterator iter = new BSTIterator(_root);
        while (iter.hasNext()) {
            result.add(iter.next());
        }
        return result;
    }

    /**
     * Represents a single Node of the tree.
     */
    private static class Node {
        /**
         * String stored in this Node.
         */
        private String s;
        /**
         * Left child of this Node.
         */
        private Node left;
        /**
         * Right child of this Node.
         */
        private Node right;

        /**
         * Creates a Node containing SP.
         */
        Node(String sp) {
            s = sp;
        }
    }

    /**
     * An iterator over BSTs.
     */
    private static class BSTIterator implements Iterator<String> {
        /**
         * Stack of nodes to be delivered.  The values to be delivered
         * are (a) the label of the top of the stack, then (b)
         * the labels of the right child of the top of the stack inorder,
         * then (c) the nodes in the rest of the stack (i.e., the result
         * of recursively applying this rule to the result of popping
         * the stack.
         */
        private Stack<Node> _toDo = new Stack<>();

        /**
         * A new iterator over the labels in NODE.
         */
        BSTIterator(Node node) {
            addTree(node);
        }

        @Override
        public boolean hasNext() {
            return !_toDo.empty();
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Node node = _toDo.pop();
            addTree(node.right);
            return node.s;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        /**
         * Add the relevant subtrees of the tree rooted at NODE.
         */
        private void addTree(Node node) {
            while (node != null) {
                _toDo.push(node);
                node = node.left;
            }
        }
    }

    private static class BSTView implements Iterator<String> {
        private BSTIterator _iter;
        private String _high;
        private String buffer = null;

        BSTView(Node node, String low, String high) {
            _iter = new BSTIterator(node);
            _high = high;

            if (node != null) {
                buffer = _iter.next();
                while (_iter.hasNext() && low.compareTo(buffer) > 0) {
                    buffer = _iter.next();
                }
            }
        }

        @Override
        public boolean hasNext() {
            if (buffer != null) {
                return true;
            } else if (!_iter.hasNext()) {
                return false;
            }

            buffer = _iter.next();
            if (buffer.compareTo(_high) > 0) {
                buffer = null;
                return false;
            }

            return true;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            } else if (buffer != null) {
                String temp = buffer;
                buffer = null;
                return temp;
            } else {
                return _iter.next();
            }
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public Iterator<String> iterator() {
        return new BSTIterator(_root);
    }

    @Override
    public Iterator<String> iterator(String low, String high) {
        return new BSTView(_root, low, high);
    }

    /**
     * Root node of the tree.
     */
    private Node _root;
}
