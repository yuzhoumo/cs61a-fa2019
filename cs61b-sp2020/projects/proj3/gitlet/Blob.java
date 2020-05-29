package gitlet;

/** Represents a file's contents.
 *  @author Joe Mo */
public class Blob implements GitletObject {

    /** Constructs a blob with given byte[] CONTENTS. */
    Blob(byte[] contents) {
        _contents = contents;
        _hash = genHash();
    }

    /** Return SHA-1 hash of blob derived from initial values. */
    private String genHash() {
        StringBuilder content = new StringBuilder();
        for (byte b : _contents) {
            content.append(b);
        }
        return Utils.sha1(content.toString());
    }

    /** Return true if hash of this blob is the same as the hash of OTHER. */
    public boolean equals(Blob other) {
        return hash().equals(other.hash());
    }

    /** Return contents of current blob. */
    public byte[] contents() {
        return _contents;
    }

    /** Return SHA-1 hash of current blob. */
    public String hash() {
        return _hash;
    }

    /** Return the type of the object. */
    public String objectType() {
        return "blob";
    }

    /** Return a string representation of the blob. */
    public String toString() {
        return objectType() + " " + _hash;
    }

    /** Stores blob contents. */
    private byte[] _contents;

    /** Stores SHA-1 hash of blob. */
    private String _hash;

}
