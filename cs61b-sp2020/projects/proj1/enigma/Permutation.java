package enigma;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/**
 * Represents a permutation of a range of integers starting at 0 corresponding
 * to the characters of an alphabet.
 *
 * @author Joe Mo
 */
class Permutation {

    /**
     * Set this Permutation to that specified by CYCLES, a string in the
     * form "(cccc) (cc) ..." where the c's are characters in ALPHABET, which
     * is interpreted as a permutation in cycle notation.  Characters in the
     * alphabet that are not included in any cycle map to themselves.
     * Whitespace is ignored.
     */
    Permutation(String cycles, Alphabet alphabet) {
        _alphabet = alphabet;
        _inverse = new HashMap<>();
        _permutation = new HashMap<>();
        _cycleString = cleanCycles(cycles);

        String cs = _cycleString.replaceAll("\\(", "");
        String[] splitCycles = cs.split("\\)");

        for (String cycle : splitCycles) {
            addCycle(cycle);
        }
    }

    /**
     * Checks if a cycles String is valid (no repeat letters, contains only
     * characters from the given alphabet, correct parentheses).
     *
     * @param cycles A String of cycles in cycle notation with white space
     *               removed such as, "(ABC)(S)"
     * @return {boolean} Returns true if valid cycle, false otherwise
     */
    private String cleanCycles(String cycles) {
        Scanner input = new Scanner(cycles);
        StringBuilder cleaned = new StringBuilder();
        while (input.hasNext("(\\([^*()]+\\))+")) {
            cleaned.append(input.next());
        }

        if (input.hasNext()) {
            throw error("Invalid cycle notation.");
        }

        BitSet used = new BitSet();
        for (char c : cleaned.toString().toCharArray()) {
            if (c == '(' || c == ')') {
                assert true;
            } else if (_alphabet.contains(c) && !used.get(c)) {
                used.set(c);
            } else if (used.get(c) || !_alphabet.contains(c)) {
                throw error("Cycles cannot contain repeated characters.");
            }
        }

        return cleaned.toString();
    }

    /**
     * Add the cycle c0->c1->...->cm->c0 to the permutation, where CYCLE is
     * c0c1...cm.
     */
    private void addCycle(String cycle) {
        for (int i = 0; i < cycle.length() - 1; i++) {
            _permutation.put(cycle.charAt(i), cycle.charAt(i + 1));
            _inverse.put(cycle.charAt(i + 1), cycle.charAt(i));
        }

        if (cycle.length() > 1) {
            _permutation.put(cycle.charAt(cycle.length() - 1), cycle.charAt(0));
            _inverse.put(cycle.charAt(0), cycle.charAt(cycle.length() - 1));
        }
    }

    /**
     * Return the value of P modulo the size of this permutation.
     */
    final int wrap(int p) {
        int r = p % size();
        if (r < 0) {
            r += size();
        }
        return r;
    }

    /**
     * Returns the size of the alphabet I permute.
     */
    int size() {
        return _alphabet.size();
    }

    /**
     * Return the result of applying this permutation to P modulo the
     * alphabet size.
     */
    int permute(int p) {
        char orig = _alphabet.toChar(wrap(p));
        char result = permute(orig);
        return _alphabet.toInt(result);
    }

    /**
     * Return the result of applying the inverse of this permutation
     * to C modulo the alphabet size.
     */
    int invert(int c) {
        char orig = _alphabet.toChar(wrap(c));
        char result = invert(orig);
        return _alphabet.toInt(result);
    }

    /**
     * Return the result of applying this permutation to the index of P
     * in ALPHABET, and converting the result to a character of ALPHABET.
     */
    char permute(char p) {
        if (!_alphabet.contains(p)) {
            String err = "Character is not in the alphabet.";
            throw new EnigmaException(err);
        }

        if (_permutation.containsKey(p)) {
            return _permutation.get(p);
        }
        return p;
    }

    /**
     * Return the result of applying the inverse of this permutation to C.
     */
    char invert(char c) {
        if (!_alphabet.contains(c)) {
            String err = "Character is not in the alphabet.";
            throw new EnigmaException(err);
        }

        if (_inverse.containsKey(c)) {
            return _inverse.get(c);
        }
        return c;
    }

    /**
     * Return the alphabet used to initialize this Permutation.
     */
    Alphabet alphabet() {
        return _alphabet;
    }

    /**
     * Return true iff this permutation is a derangement (i.e., a
     * permutation for which no value maps to itself).
     */
    boolean derangement() {
        for (int i = 0; i < _alphabet.size(); i++) {
            char c = _alphabet.toChar(i);
            if (!_permutation.containsKey(c) || _permutation.get(c) == c) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return _cycleString;
    }

    /**
     * Alphabet of this permutation.
     */
    private Alphabet _alphabet;

    /**
     * String representation of permutation cycles.
     */
    private String _cycleString;

    /**
     * Mappings for a characters to their inverse permutations.
     */
    private HashMap<Character, Character> _inverse;

    /**
     * Mappings for a characters to their permutations.
     */
    private HashMap<Character, Character> _permutation;

}
