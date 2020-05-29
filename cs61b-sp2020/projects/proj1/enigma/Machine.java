package enigma;

import java.util.HashMap;
import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/**
 * Class that represents a complete enigma machine.
 *
 * @author Joe Mo
 */
class Machine {

    /**
     * A new Enigma machine with alphabet ALPHA, 1 < NUMROTORS rotor slots,
     * and 0 <= PAWLS < NUMROTORS pawls. ALLROTORS contains all the
     * available rotors.
     */
    Machine(Alphabet alpha, int numRotors, int pawls,
            Collection<Rotor> allRotors) {

        if (numRotors < 1) {
            throw error("Machine must have more than 1 rotor slot");
        } else if (pawls < 0 || pawls >= numRotors) {
            throw error("Number of pawls must be 0 <= pawls < numRotors");
        } else if (allRotors.size() < numRotors) {
            throw error("Number of rotors provided is less than number of slots"
                    + "in the machine");
        }

        _alphabet = alpha;
        _numPawls = pawls;
        _numRotors = numRotors;
        _rotors = new HashMap<>();

        int moving = 0;
        for (Rotor r : allRotors) {
            String name = r.name();
            if (!_rotors.containsKey(name)) {
                _rotors.put(name, r);
                moving += r.rotates() ? 1 : 0;
            } else {
                throw error("Cannot have multiple rotors of the same name %s",
                        name);
            }
        }

        if (moving < pawls) {
            throw error("Number of moving rotors is less than the number of"
                    + " pawls");
        }
    }

    /**
     * Return the number of rotor slots I have.
     */
    int numRotors() {
        return _numRotors;
    }

    /**
     * Return the number pawls (and thus rotating rotors) I have.
     */
    int numPawls() {
        return _numPawls;
    }

    /**
     * Set my rotor slots to the rotors named ROTORS from my set of
     * available rotors (ROTORS[0] names the reflector).
     * Initially, all rotors are set at their 0 setting.
     */
    void insertRotors(String[] rotors) {
        _slots = new Rotor[_numRotors];

        if (rotors.length != _numRotors) {
            throw error("Number of inserted rotors does not match number of "
                    + "slots");
        }

        int moving = 0;
        for (int i = 0; i < _numRotors; i++) {
            String name = rotors[i];
            Rotor rotor;

            if (_rotors.containsKey(name)) {
                rotor = _rotors.get(name);
                rotor.set(0);
                _slots[i] = rotor;
                moving += rotor.rotates() ? 1 : 0;
            } else {
                throw error("Rotor with name %s does not exist", name);
            }
        }

        if (moving != _numPawls) {
            throw error("Number of moving rotors does not match number of"
                    + " pawls");
        }
    }

    /**
     * Set my rotors according to SETTING, which must be a string of
     * numRotors()-1 characters in my alphabet. The first letter refers
     * to the leftmost rotor setting (not counting the reflector). The optional
     * RINGSETTINGS String denotes the alphabet ring (ringstellung) settings if
     * it is non-empty.
     */
    void setRotors(String setting) {
        if (setting.length() != _numRotors - 1) {
            throw error("Malformed rotor setting (incorrect length %s)",
                    setting.length());
        }

        if (!_slots[0].reflecting()) {
            throw error("First rotor must be a reflector");
        }

        char[] chars = setting.toCharArray();
        for (int i = 0; i < _numRotors - 1; i++) {
            if (_alphabet.contains(chars[i])) {
                _slots[i + 1].set(chars[i]);
            } else {
                throw error("Malformed rotor setting (character %s not found)",
                        chars[i]);
            }
        }
    }

    /**
     * Sets machine rotors' alphabet rings (ringstellungs) so that the 0 setting
     * for characters are set based on the characters of SETTING.
     */
    void setRings(String setting) {
        if (setting.length() != _numRotors - 1) {
            throw error("Malformed ring setting (incorrect length %s)",
                    setting.length());
        }

        char[] chars = setting.toCharArray();
        for (int i = 0; i < _numRotors - 1; i++) {
            if (_alphabet.contains(chars[i])) {
                int index = _alphabet.toInt(chars[i]);
                _slots[i + 1].setOffset(index);
            } else {
                throw error("Malformed ring setting (character %s not found)",
                        chars[i]);
            }
        }
    }

    /**
     * Resets all alphabet rings (ringstellung) in machine.
     */
    void resetRings() {
        for (Rotor r : _slots) {
            r.setOffset(0);
        }
    }

    /**
     * Set the plugboard to PLUGBOARD.
     */
    void setPlugboard(Permutation plugboard) {
        String cycles = plugboard.toString();
        Pattern validBoard = Pattern.compile("(\\((.|..)\\))*");
        Matcher valid = validBoard.matcher(cycles);

        if (valid.matches()) {
            _plugboard = plugboard;
        } else {
            throw error("Plugboard cannot have permutation cycles longer"
                    + " than 2");
        }
    }

    /**
     * Advances the machine forward by one step.
     */
    void advance() {
        boolean[] rotate = new boolean[_numRotors];
        rotate[_numRotors - 1] = true;

        for (int i = _numRotors - 1; i > 1; i--) {
            if (_slots[i].atNotch() && _slots[i - 1].rotates()) {
                rotate[i] = true;
                rotate[i - 1] = true;
            }
        }

        for (int i = 0; i < rotate.length; i++) {
            if (rotate[i]) {
                _slots[i].advance();
            }
        }
    }

    /**
     * Returns the result of converting the input character C (as an
     * index in the range 0..alphabet size - 1), after first advancing
     * <p>
     * the machine.
     */
    int convert(int c) {
        advance();
        c = _plugboard.permute(c);

        for (int i = _numRotors - 1; i >= 0; i--) {
            c = _slots[i].convertForward(c);
        }

        for (int i = 1; i < _numRotors; i++) {
            c = _slots[i].convertBackward(c);
        }

        return _plugboard.invert(c);
    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        StringBuilder result = new StringBuilder();
        for (char c : msg.toCharArray()) {
            int index = _alphabet.toInt(c);
            int converted = convert(index);
            result.append(_alphabet.toChar(converted));
        }
        return result.toString();
    }

    /**
     * Common alphabet of my rotors.
     */
    private final Alphabet _alphabet;

    /**
     * Number of rotors in machine.
     */
    private int _numRotors;

    /**
     * Number of pawls (or moving rotors) in machine.
     */
    private int _numPawls;

    /**
     * HashMap of all of the machine's rotors.
     */
    private HashMap<String, Rotor> _rotors;

    /**
     * The machine's plugboard settings.
     */
    private Permutation _plugboard;

    /**
     * Array of rotors currently set in the machine's slots.
     */
    private Rotor[] _slots;
}
