package gitlet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/** Represents Gitlet commit and all of its metadata.
 *  @author Joe Mo */
public class Commit implements GitletObject {

    /** Parent hash of initial commit. */
    public static final String GENESIS =
            "0000000000000000000000000000000000000000";

    /** SHA-1 hash of the initial commit. */
    public static final String GENESIS_HASH =
            "1d597a35602ffcbcffeafe6f89d4c0f30e3fb624";

    /** Constructs initial commit. */
    public Commit() {
        _date = new Date(0);
        _message = "initial commit";
        _parent = GENESIS;
        _hash = genHash();
        _blobHashes = new HashMap<>();
    }

    /** Constructs a new commit given the PREVIOUS commit and a MESSAGE. */
    public Commit(Commit previous, String message) {
        _date = new Date();
        _message = message;
        _parent = previous.hash();
        _hash = genHash();
        _blobHashes = new HashMap<>(previous._blobHashes);
    }

    /** Constructs a new commit given the PREVIOUS commit, a MESSAGE and
     *  MERGEHASH. */
    public Commit(Commit previous, String message, String mergeHash) {
        _date = new Date();
        _isMerge = true;
        _mergeHash = mergeHash;
        _message = message;
        _parent = previous.hash();
        _hash = genHash();
        _blobHashes = new HashMap<>(previous._blobHashes);
    }

    /** Return SHA-1 hash of commit derived from initial values. */
    private String genHash() {
        String dateString = "" + _date.getTime();
        return Utils.sha1(dateString, _message, _parent);
    }

    /** Return true if file with name FILENAME is contained in the commit. */
    public boolean contains(String filename) {
        return _blobHashes.containsKey(filename);
    }

    /** Adds a blob to the commit with the given FILENAME and blob HASH. */
    public void addBlob(String filename, String hash) {
        _blobHashes.put(filename, hash);
    }

    /** Remove reference to blob with FILENAME from the commit. */
    public void removeBlob(String filename) {
        _blobHashes.remove(filename);
    }

    /** Return hash of blob with the name FILENAME. */
    public String blobHash(String filename) {
        return _blobHashes.get(filename);
    }

    /** Return blob object with given FILENAME. */
    public Blob blob(String filename) {
        return Storage.readBlob(blobHash(filename));
    }

    /** Return list of filenames associated with the commit. */
    public List<String> files() {
        List<String> names = new ArrayList<>(_blobHashes.keySet());
        Collections.sort(names);
        return names;
    }

    /** Return commit date. */
    public Date date() {
        return _date;
    }

    /** Return commit message. */
    public String message() {
        return _message;
    }

    /** Return SHA-1 hash of parent commit. */
    public String parent() {
        return _parent;
    }

    /** Return SHA-1 hash of commit. */
    public String hash() {
        return _hash;
    }

    /** Return true if commit is a merge commit (two parents). */
    public boolean isMerge() {
        return _isMerge;
    }

    /** Return the SHA-1 hash of the merged commit (second parent). */
    public String mergeHash() {
        return _mergeHash;
    }

    /** Return the type of the object. */
    public String objectType() {
        return "commit";
    }

    /** Return a string representation of the commit. */
    public String toString() {
        return objectType() + " " + _hash;
    }

    /** Commit date. */
    private Date _date;

    /** Stores message displayed in log. */
    private String _message;

    /** Hash of parent commit. */
    private String _parent;

    /** SHA-1 hash of commit. */
    private String _hash;

    /** If the commit is a merge commit (two parents). */
    private boolean _isMerge;

    /** SHA-1 hash of merged commit (second parent). */
    private String _mergeHash;

    /** Stores mapping from filename to blob hash. */
    private HashMap<String, String> _blobHashes;

}
