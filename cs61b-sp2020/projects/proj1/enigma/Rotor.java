package enigma;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/**
 * Superclass that represents a rotor in the enigma machine.
 *
 * @author Joe Mo
 */
class Rotor {

    /**
     * A rotor named NAME whose permutation is given by PERM.
     */
    Rotor(String name, Permutation perm) {
        Pattern validName = Pattern.compile("[^()*\\s]+");
        Matcher valid = validName.matcher(name);

        if (!valid.matches()) {
            throw error("Rotor name cannot contain whitespace or any of"
                    + " the following characters: ( ) *");
        }

        _name = name;
        _permutation = perm;

        _offset = 0;
        _setting = 0;
    }

    /**
     * Return my name.
     */
    String name() {
        return _name;
    }

    /**
     * Return my alphabet.
     */
    Alphabet alphabet() {
        return permutation().alphabet();
    }

    /**
     * Return my permutation.
     */
    Permutation permutation() {
        return _permutation;
    }

    /**
     * Return the size of my alphabet.
     */
    int size() {
        return permutation().size();
    }

    /**
     * Return true iff I have a ratchet and can move.
     */
    boolean rotates() {
        return false;
    }

    /**
     * Return true iff I reflect.
     */
    boolean reflecting() {
        return false;
    }

    /**
     * Return my current setting.
     */
    int setting() {
        return permutation().wrap(_setting - offset());
    }

    /**
     * Set setting() to POSN.
     */
    void set(int posn) {
        if (0 <= posn && posn < size()) {
            _setting = permutation().wrap(posn + offset());
        } else {
            throw error("Rotor position %s is out of bounds", posn);
        }
    }

    /**
     * Set setting() to character CPOSN.
     */
    void set(char cposn) {
        if (_permutation.alphabet().contains(cposn)) {
            _setting = permutation().wrap(alphabet().toInt(cposn) + offset());
        } else {
            throw error("Rotor does not have character %s", cposn);
        }
    }

    /**
     * Sets alphabet ring (ringstellung) OFFSET.
     */
    void setOffset(int offset) {
        _offset = offset;
    }

    /**
     * Returns alphabet ring (ringstellung) OFFSET.
     */
    int offset() {
        return _offset;
    }

    /**
     * Return the conversion of P (an integer in the range 0..size()-1)
     * according to my permutation.
     */
    int convertForward(int p) {
        int result = permutation().permute(setting() + p);
        return permutation().wrap(result - setting());
    }

    /**
     * Return the conversion of E (an integer in the range 0..size()-1)
     * according to the inverse of my permutation.
     */
    int convertBackward(int e) {
        int result = permutation().invert(setting() + e);
        return permutation().wrap(result - setting());
    }

    /**
     * Returns true iff I am positioned to allow the rotor to my left
     * to advance.
     */
    boolean atNotch() {
        return false;
    }

    /**
     * Advance me one position, if possible. By default, does nothing.
     */
    void advance() {
    }

    @Override
    public String toString() {
        return "Rotor " + _name;
    }

    /**
     * This rotor's name.
     */
    private final String _name;

    /**
     * The permutation implemented by this rotor in its 0 position.
     */
    private Permutation _permutation;

    /**
     * The setting for the position of the rotor.
     */
    private int _setting;

    /**
     * The offset for the position of the rotor.
     */
    private int _offset;
}
