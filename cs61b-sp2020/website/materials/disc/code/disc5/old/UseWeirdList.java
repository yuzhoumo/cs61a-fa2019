/** Functions to sum and increment the elements of a WeirdList. */
class UseWeirdList {
    /** Returns the sum of the elements in L */
    static int maxPos(WeirdList L) { 
        Maximizer maximizer = new Maximizer();
        L.map(maximizer); // REPLACE THIS LINE WITH THE RIGHT ANSWER.
        return maximizer.max;
    }    

    public static void main(String[] args) {
        WeirdList wl1 = new WeirdList(15, WeirdList.EMPTY);
        WeirdList wl2 = new WeirdList(6, wl1);
        WeirdList wl3 = new WeirdList(10, wl2);
        System.out.println(maxPos(wl3));
        System.out.println(maxPos(WeirdList.EMPTY));

    }
}
