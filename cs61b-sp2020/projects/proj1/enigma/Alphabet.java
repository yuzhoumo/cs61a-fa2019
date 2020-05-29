package enigma;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static enigma.EnigmaException.*;

/**
 * An alphabet of encodable characters. Provides a mapping from characters
 * to and from indices into the alphabet.
 *
 * @author Joe Mo
 */
class Alphabet {

    /**
     * A new alphabet containing CHARS. Character number #k has index
     * K (numbering from 0). No character may be duplicated.
     */
    Alphabet(String chars) {
        Pattern validChars = Pattern.compile("[^()*\\s]+");
        Matcher valid = validChars.matcher(chars);

        if (chars.equals("")) {
            throw error("Cannot create an empty alphabet");
        } else if (!valid.matches()) {
            throw error("Cannot create alphabet containing whitespace or any of"
                    + " the following characters: ( ) *");
        }

        _chars = chars;
        _charToIndex = new HashMap<>();

        for (int i = 0; i < chars.length(); i++) {
            if (_charToIndex.containsKey(_chars.charAt(i))) {
                throw error("Alphabet cannot contain repeated characters");
            }
            _charToIndex.put(_chars.charAt(i), i);
        }
    }

    /**
     * A default alphabet of all upper-case characters.
     */
    Alphabet() {
        this("ABCDEFGHIJKLMNOPQRSTUVWXYZ");
    }

    /**
     * Returns the size of the alphabet.
     */
    int size() {
        return _chars.length();
    }

    /**
     * Returns true if CH is in this alphabet.
     */
    boolean contains(char ch) {
        return _charToIndex.containsKey(ch);
    }

    /**
     * Returns character number INDEX in the alphabet, where
     * 0 <= INDEX < size().
     */
    char toChar(int index) {
        if (0 <= index && index < size()) {
            return _chars.charAt(index);
        } else {
            throw error("Requested character index %s is out of range", index);
        }
    }

    /**
     * Returns the index of character CH which must be in
     * the alphabet. This is the inverse of toChar().
     */
    int toInt(char ch) {
        if (_charToIndex.containsKey(ch)) {
            return _charToIndex.get(ch);
        } else {
            throw error("Cannot get index of a character %s (not in "
                    + "alphabet)", ch);
        }
    }

    /**
     * An String of characters in the alphabet.
     */
    private String _chars;

    /**
     * A dictionary that saves character to index mappings.
     */
    private HashMap<Character, Integer> _charToIndex;

}
