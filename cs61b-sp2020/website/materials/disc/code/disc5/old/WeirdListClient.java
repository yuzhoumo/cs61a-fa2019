/** Functions to sum and increment the elements of a WeirdList. */
class WeirdListClient {
    /** Returns the result of adding N to each element of L. */
    static WeirdList add(WeirdList L, int n) {
        Adder adder = new Adder(n);
        return L.map(adder); // REPLACE THIS LINE WITH THE RIGHT ANSWER.
    }

    /** Returns the sum of the elements in L */
    static int sum(WeirdList L) { 
        Summer summer = new Summer();
        L.map(summer); // REPLACE THIS LINE WITH THE RIGHT ANSWER.
        return summer.getS();
    }

    // FILL IN OTHER CLASSES USED BY HERE (HINT, HINT).
    // You MAY want to add some private nested classes (and methods) to
    // be used by User (start them off with 'private static class...'),
    // OR you may want to add more class files to those provided in the
    // skeleton.  In the latter case, be sure you commit them as well.

    
}
