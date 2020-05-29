/**
 * Scheme-like pairs that can be used to form a list of integers.
 *
 * @author P. N. Hilfinger; updated by Vivant Sakore (1/29/2020)
 */
public class IntDList {

    /**
     * First and last nodes of list.
     */
    protected DNode _front, _back;

    /**
     * An empty list.
     */
    public IntDList() {
        _front = _back = null;
    }

    /**
     * @param values the ints to be placed in the IntDList.
     */
    public IntDList(Integer... values) {
        _front = _back = null;
        for (int val : values) {
            insertBack(val);
        }
    }

    /**
     * @return The first value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getFront() {
        return _front._val;
    }

    /**
     * @return The last value in this list.
     * Throws a NullPointerException if the list is empty.
     */
    public int getBack() {
        return _back._val;
    }

    /**
     * @return The number of elements in this list.
     */
    public int size() {
        int count = 0;
        DNode pointer = _front;
        while (pointer != null) {
            pointer = pointer._next;
            count++;
        }

        return count;
    }

    /**
     * @param i index of element to return,
     *          where i = 0 returns the first element,
     *          i = 1 returns the second element,
     *          i = -1 returns the last element,
     *          i = -2 returns the second to last element, and so on.
     *          You can assume i will always be a valid index, i.e 0 <= i < size
     *              for positive indices
     *          and -size <= i <= -1 for negative indices.
     * @return The integer value at index i
     */
    public int get(int i) {
        DNode pointer;
        if (i >= 0) {
            pointer = _front;
            while (i != 0) {
                pointer = pointer._next;
                i--;
            }
        } else {
            pointer = _back;
            while (i != -1) {
                pointer = pointer._prev;
                i++;
            }
        }

        return pointer._val;
    }

    /**
     * @param d value to be inserted in the front
     */
    public void insertFront(int d) {
        if (_front == null) {
            _front = _back = new DNode(null, d, null);
        } else {
            DNode insertion = new DNode(null, d, _front);
            _front._prev = insertion;
            _front = insertion;
        }
    }

    /**
     * @param d value to be inserted in the back
     */
    public void insertBack(int d) {
        if (_front == null) {
            _front = _back = new DNode(null, d, null);
        } else {
            DNode insertion = new DNode(_back, d, null);
            _back._next = insertion;
            _back = insertion;
        }
    }

    /**
     * @param d     value to be inserted
     * @param index index at which the value should be inserted
     *              where index = 0 inserts at the front,
     *              index = 1 inserts at the second position,
     *              index = -1 inserts at the back,
     *              index = -2 inserts at the second to last position,
     *                  and so on.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index <= size for positive indices (including
     *                  insertions at front and back)
     *              and -(size+1) <= index <= -1 for negative indices
     *                  (including insertions at front and back).
     */
    public void insertAtIndex(int d, int index) {
        DNode pointer;
        DNode insertion = new DNode(null, d, null);
        int listSize = size();

        if (index == 0 || listSize + 1 == -index) {
            insertFront(d);
        } else if (index == -1 || index == listSize) {
            insertBack(d);
        } else if (index > 0) {
            pointer = _front;
            while (index != 0) {
                pointer = pointer._next;
                index--;
            }
            if (pointer._prev != null) {
                pointer._prev._next = insertion;
                insertion._prev = pointer._prev;
            }
            pointer._prev = insertion;
            insertion._next = pointer;
        } else {
            pointer = _back;
            while (index != -1) {
                pointer = pointer._prev;
                index++;
            }
            if (pointer._next != null) {
                pointer._next._prev = insertion;
                insertion._next = pointer._next;
            }
            pointer._next = insertion;
            insertion._prev = pointer;
        }
    }

    /**
     * Removes the first item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteFront() {
        DNode deleted = _front;
        if (_front == _back) {
            _front = _back = null;
        } else {
            _front._next._prev = null;
            _front = _front._next;
        }
        return deleted._val;
    }

    /**
     * Removes the last item in the IntDList and returns it.
     *
     * @return the item that was deleted
     */
    public int deleteBack() {
        DNode deleted = _back;
        if (_front == _back) {
            _front = _back = null;
        } else {
            _back._prev._next = null;
            _back = _back._prev;
        }
        return deleted._val;
    }

    /**
     * @param index index of element to be deleted,
     *              where index = 0 returns the first element,
     *              index = 1 will delete the second element,
     *              index = -1 will delete the last element,
     *              index = -2 will delete the second to last element,
     *                  and so on.
     *              You can assume index will always be a valid index,
     *              i.e 0 <= index < size for positive indices (including
     *                  deletions at front and back)
     *              and -size <= index <= -1 for negative indices (including
     *                  deletions at front and back).
     * @return the item that was deleted
     */
    public int deleteAtIndex(int index) {
        int value;
        int listSize = size();
        DNode pointer;

        if (index == 0 || listSize == -index) {
            value = deleteFront();
        } else if (index == -1 || index == listSize - 1) {
            value = deleteBack();
        } else if (index > 0) {
            pointer = _front;
            while (index != 0) {
                pointer = pointer._next;
                index--;
            }
            if (pointer._prev != null) {
                pointer._prev._next = pointer._next;
            }
            pointer._next._prev = pointer._prev;
            value = pointer._val;
        } else {
            pointer = _back;
            while (index != -1) {
                pointer = pointer._prev;
                index++;
            }
            if (pointer._next != null) {
                pointer._next._prev = pointer._prev;
            }
            pointer._prev._next = pointer._next;
            value = pointer._val;
        }
        return value;
    }

    /**
     * @return a string representation of the IntDList in the form
     * [] (empty list) or [1, 2], etc.
     * Hint:
     * String a = "a";
     * a += "b";
     * System.out.println(a); //prints ab
     */
    public String toString() {
        if (_front == null) {
            return "[]";
        }

        StringBuilder res = new StringBuilder("[");
        DNode pointer = _front;
        while (pointer._next != null) {
            res.append(pointer._val).append(", ");
            pointer = pointer._next;
        }
        return res.toString() + pointer._val + "]";
    }

    /**
     * DNode is a "static nested class", because we're only using it inside
     * IntDList, so there's no need to put it outside (and "pollute the
     * namespace" with it. This is also referred to as encapsulation.
     * Look it up for more information!
     */
    static class DNode {
        /**
         * Previous DNode.
         */
        protected DNode _prev;
        /**
         * Next DNode.
         */
        protected DNode _next;
        /**
         * Value contained in DNode.
         */
        protected int _val;

        /**
         * @param val the int to be placed in DNode.
         */
        protected DNode(int val) {
            this(null, val, null);
        }

        /**
         * @param prev previous DNode.
         * @param val  value to be stored in DNode.
         * @param next next DNode.
         */
        protected DNode(DNode prev, int val, DNode next) {
            _prev = prev;
            _val = val;
            _next = next;
        }
    }
}
