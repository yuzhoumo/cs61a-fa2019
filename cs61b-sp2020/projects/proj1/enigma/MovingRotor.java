package enigma;

import java.util.BitSet;

import static enigma.EnigmaException.*;

/**
 * Class that represents a rotating rotor in the enigma machine.
 *
 * @author Joe Mo
 */
class MovingRotor extends Rotor {

    /**
     * A rotor named NAME whose permutation in its default setting is
     * PERM, and whose notches are at the positions indicated in NOTCHES.
     * The Rotor is initally in its 0 setting (first character of its
     * alphabet).
     */
    MovingRotor(String name, Permutation perm, String notches) {
        super(name, perm);
        _notches = new BitSet();
        for (char c : notches.toCharArray()) {
            if (alphabet().contains(c)) {
                _notches.set(alphabet().toInt(c));
            } else {
                throw error("Rotor does not have character %s", c);
            }
        }
    }

    @Override
    boolean atNotch() {
        int setting = permutation().wrap(setting() + offset());
        return _notches.get(setting);
    }

    @Override
    boolean rotates() {
        return true;
    }

    @Override
    void advance() {
        int current = setting();
        set(permutation().wrap(current + 1));
    }

    /**
     * Set of alphabet indices where notches exists.
     */
    private BitSet _notches;

}
