package gitlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/** Collection of static methods that handle storage/persistence for Gitlet.
 *  @author Joe Mo */
class Storage {

    /** Original current working directory. */
    private static final File ORIG_CWD = new File(".");

    /** Length of a SHA-1 hash. */
    private static final int SHA1_LENGTH = 40;

    /** Resets Gitlet CWD to the actually current working directory. */
    static void resetCWD() {
        changeCWD(ORIG_CWD);
    }

    /** Changes CWD (current working directory) to a different location
     *  denoted by the File NEWLOCATION. */
    static void changeCWD(File newLocation) {
        if (newLocation.isDirectory()) {
            _cwd = newLocation;
            _gitletDir = new File(_cwd, ".gitlet");
            _objectsDir = new File(_gitletDir, "objects");
            _refsDir = new File(_gitletDir, "refs");
            _headsDir = new File(_refsDir, "heads");
            _remotesDir = new File(_refsDir, "remotes");
            _stagingDir = new File(_gitletDir, "staging");
            _stagingAddDir = new File(_stagingDir, "add");
            _stagingRmDir = new File(_stagingDir, "rm");
            _head = new File(_gitletDir, "HEAD");
            _commitIndex = new File(_gitletDir, "COMMIT_INDEX");
            _objectIndex = new File(_gitletDir, "OBJECT_INDEX");
        } else {
            throw new IllegalArgumentException("Attempted to change Gitlet"
                    + " current working directory to an invalid directory.");
        }
    }

    /** Return true if CHILD file is contained inside the repository. */
    static boolean isInside(File child) throws IOException {
        String parentPath = _cwd.getCanonicalPath();
        String childPath = child.getCanonicalPath();
        return childPath.startsWith(parentPath + File.separator);
    }

    /** Creates file with name FILE if it does not exist. Otherwise, does
     *  nothing. Errors if file location is not inside the .gitlet directory
     *  or if the file does not exist but cannot be created. Make a directory
     *  if MAKEDIRECTORY is true, otherwise makes a file. */
    private static void create(File file, boolean makeDirectory) {
        try {
            assert isInside(file);
            boolean exists = file.exists();
            if (!exists && makeDirectory) {
                boolean success = file.mkdir();
                assert success;
            } else if (!exists) {
                boolean success = file.createNewFile();
                assert success;
            }
        } catch (AssertionError | IOException e) {
            e.printStackTrace();
            throw new GitletException("Failed to write file.");
        }
    }

    /** Creates a new file. Does nothing if FILE already exists. */
    static void createNewFile(File file) {
        create(file, false);
    }

    /** Creates a new directory. Does nothing if FILE already exists. */
    static void mkdir(File file) {
        create(file, true);
    }

    /** Deletes the given file FILE if it exists. Otherwise, does nothing.
     *  Error if file exists but cannot be deleted. */
    static void delete(File file) {
        try {
            assert isInside(file);
            if (file.exists()) {
                boolean success = file.delete();
                assert success;
            }
        } catch (AssertionError | IOException e) {
            throw new GitletException("Failed to delete file.");
        }
    }

    /** Sets up all of the directories needed for persistence. Also writes
     *  initial commit to storage and sets current branch to master. */
    static void setup() throws IOException {
        mkdir(_gitletDir);
        mkdir(_objectsDir);
        mkdir(_refsDir);
        mkdir(_headsDir);
        mkdir(_remotesDir);
        mkdir(_stagingDir);
        mkdir(_stagingAddDir);
        mkdir(_stagingRmDir);
        createNewFile(_commitIndex);
        createNewFile(_objectIndex);
        createNewFile(_head);
        writeHead("master");
        writeCommit(new Commit());
    }

    /** Return true if GITLET_FOLDER is initialized. */
    static boolean checkInitialized() {
        return _gitletDir.isDirectory();
    }

    /** Return file object pointing to the GitletObject corresponding to the
     *  given SHA-1 HASH string. The given hash may be abbreviated, but an
     *  IllegalArgumentException is thrown if the abbreviation is less than 3
     *  characters or if no matches are found or multiple are found. */
    static File hashToFile(String hash) {

        if (hash.length() == SHA1_LENGTH) {
            File dir = new File(_objectsDir, hash.substring(0, 2));
            mkdir(dir);
            return new File(dir, hash.substring(2));
        } else if (hash.length() < 3) {
            throw new IllegalArgumentException();
        }

        String dirname = hash.substring(0, 2);
        String rest = hash.substring(2);
        File dir = new File(_objectsDir, dirname);

        if (!dir.isDirectory()) {
            throw new IllegalArgumentException();
        }

        String match = null;
        for (String filename : Utils.plainFilenamesIn(dir)) {
            if (match != null && filename.startsWith(rest)) {
                throw new IllegalArgumentException();
            } else if (match == null && filename.startsWith(rest)) {
                match = filename;
            }
        }

        return Utils.join(_objectsDir, dirname, match);
    }

    /** Return true if the objects folder contains an object with the hash
     *  OBJECTHASH. */
    static boolean hasObject(String objectHash) {
        File objectFile = hashToFile(objectHash);
        return objectFile.isFile();
    }

    /** Writes a given GitletObject OBJECT to the objects directory. */
    static void writeObject(GitletObject object) {
        File file = hashToFile(object.hash());
        createNewFile(file);
        writeIndex(object.hash(), _objectIndex);
        Utils.writeObject(file, object);
    }

    /** Return either a commit or blob T from the objects directory given its
     *  HASH and EXPECTEDCLASS. This can also take abbreviations of hashes but
     *  will throw an error if the abbreviation is not valid. */
    static <T extends GitletObject> T readObject(
            String hash, Class<T> expectedClass) {

        File objectFile = hashToFile(hash);
        return Utils.readObject(objectFile, expectedClass);
    }

    /** Updates the HEAD file to point to the reference file corresponding
     *  to the branch name given by BRANCH. */
    static void writeHead(String branch) {
        File location = new File(_headsDir, branch);
        Utils.writeObject(_head, location);
    }

    /** Return the HEAD file as a File object pointing to the reference file of
     *  the current branch. */
    static File readHead() {
        String path = Utils.readObject(_head, File.class).getPath();

        if (path.startsWith("." + File.separator)) {
            path = path.replace("." + File.separator,
                    _cwd.getPath() + File.separator);
        }

        return new File(path);
    }

    /** Return list of hashes stored in INDEXFILE. */
    static List<String> readIndex(File indexFile) {
        String indexContents = Utils.readContentsAsString(indexFile).strip();
        String[] hashes = indexContents.split(",");
        return Arrays.asList(hashes);
    }

     /** Appends a HASH to front of the INDEXFILE (comma separated). */
    static void writeIndex(String hash, File indexFile) {
        String indexContents = Utils.readContentsAsString(indexFile).strip();

        if (indexContents.isEmpty()) {
            indexContents = hash;
        } else {
            indexContents = hash + "," + indexContents;
        }

        Utils.writeContents(indexFile, indexContents);
    }

    /** Writes a branch file given the branch's NAME and the SHA-1 HASH of the
     *  commit that the branch points to. Overwrites any existing files. */
    static void writeBranch(String name, String hash) {
        File branch = new File(_headsDir, name);
        createNewFile(branch);
        Utils.writeContents(branch, hash);
    }

    /** Return file object pointing to the branch reference file given the
     *  BRANCHNAME. */
    static File readBranch(String branchName) {
        return new File(_headsDir, branchName);
    }

    /** Return list of all branch names. */
    static List<String> branches() {
        return Utils.plainFilenamesIn(_headsDir);
    }

    /** Return the name of the current branch. */
    static String currentBranch() {
        File path = readHead();
        File parent = path.getParentFile();

        if (parent.equals(_headsDir)) {
            return path.getName();
        }

        return parent.getName() + "/" + path.getName();
    }

    /** Writes BLOB to objects directory. */
    static void writeBlob(Blob blob) {
        writeObject(blob);
    }

    /** Return blob from storage with given SHA-1 hash BLOBHASH. */
    static Blob readBlob(String blobHash) {
        return readObject(blobHash, Blob.class);
    }

    /** Write COMMIT to the objects directory and set the current branch
     *  reference to point to it. Also adds the commit hash to INDEX file. */
    static void writeCommit(Commit commit) {
        writeObject(commit);
        writeBranch(currentBranch(), commit.hash());
        writeIndex(commit.hash(), _commitIndex);
    }

    /** Return commit object with the hash COMMITHASH from storage. */
    static Commit readCommit(String commitHash) {
        return readObject(commitHash, Commit.class);
    }

    /** Return the current commit. */
    static Commit currentCommit() {
        File currentBranch = readHead();
        String commitHash = Utils.readContentsAsString(currentBranch).strip();
        File commitFile = hashToFile(commitHash);
        return Utils.readObject(commitFile, Commit.class);
    }

    /** Fills the given COMMITS collection (of generic type T) with all
     *  commits ever created. */
    private static <T extends Collection<Commit>> void getCommits(T commits) {
        List<String> commitHashes = readIndex(_commitIndex);

        for (String hash : commitHashes) {
            commits.add(readCommit(hash));
        }
    }

    /** Fills the given COMMITS collection (of generic type T) with all
     *  commits preceding the commit CURR. */
    private static <T extends Collection<Commit>> void getCommits(
            T commits, Commit curr) {

        while (!curr.parent().equals(Commit.GENESIS)) {
            commits.add(curr);
            curr = readCommit(curr.parent());
        }

        commits.add(readCommit(Commit.GENESIS_HASH));
    }

    /** Return a list of all commits ever created. */
    static List<Commit> commits() {
        ArrayList<Commit> commits = new ArrayList<>();
        getCommits(commits);
        return commits;
    }

    /** Return a list of commits that precede the given commit CURR. */
    static List<Commit> commits(Commit curr) {
        ArrayList<Commit> commits = new ArrayList<>();
        getCommits(commits, curr);
        return commits;
    }

    /** Fills the DISTANCES hash map with mappings of the hashes of commits
     *  that precede the commit to the integer distance from the current
     *  commit identified by COMMITHASH. Also includes merged in parents.
     *  CURRDISTANCE should be 0 for the initial call can then increments in
     *  recursive calls to track the distance from the commit with the given
     *  hash COMMITHASH. */
    static void commitDistances(String commitHash, int currDistance,
                                  HashMap<String, Integer> distances) {

        if (commitHash.equals(Commit.GENESIS)) {
            return;
        }

        Commit curr = readCommit(commitHash);
        String parentHash = curr.parent();

        if (curr.isMerge()) {
            distances.putIfAbsent(curr.hash(), currDistance);
            commitDistances(curr.mergeHash(), currDistance + 1, distances);
        }

        distances.putIfAbsent(curr.hash(), currDistance);
        commitDistances(parentHash, currDistance + 1, distances);
    }

    /** Return the latest common ancestor, given the current branch with the
     *  name MAINBRANCH and the given branch with name GIVENBRANCH. */
    static Commit splitPoint(String mainBranch, String givenBranch) {
        String mainHash = Utils.readContentsAsString(readBranch(mainBranch));
        String givenHash = Utils.readContentsAsString(readBranch(givenBranch));
        HashMap<String, Integer> mainDists = new HashMap<>();
        HashMap<String, Integer> givenDists = new HashMap<>();
        commitDistances(mainHash, 0, mainDists);
        commitDistances(givenHash, 0, givenDists);

        String splitPoint = Commit.GENESIS_HASH;
        int minDistance = Integer.MAX_VALUE;
        for (String hash : givenDists.keySet()) {
            boolean overlap = mainDists.containsKey(hash);
            if (overlap && mainDists.get(hash) < minDistance) {
                minDistance = mainDists.get(hash);
                splitPoint = hash;
            }
        }

        return readCommit(splitPoint);
    }

    /** Return a list of names of untracked files in the working directory. */
    static List<String> untracked() {
        ArrayList<String> untrackedFiles = new ArrayList<>();
        ArrayList<String> dummy = new ArrayList<>();
        untracked(untrackedFiles, dummy);
        return untrackedFiles;
    }

    /** Looks through all of the files in the staging area, working
     *  directory, and current commit. Adds names of all untracked files to
     *  FILES and names of all tracked files with untracked changes to
     *  CHANGES. */
    static void untracked(List<String> files, List<String> changes) {
        Commit curr = currentCommit();
        HashSet<String> filenames = new HashSet<>(curr.files());
        filenames.addAll(workingFiles());
        filenames.addAll(stagedAdds());
        filenames.addAll(stagedRms());

        for (String filename : filenames) {
            File working = new File(_cwd, filename);
            File stagedAdd = new File(_stagingAddDir, filename);
            File stagedRm = new File(_stagingRmDir, filename);
            boolean tracked = curr.contains(filename);

            if (!tracked && working.isFile()
                    && !stagedAdd.isFile() && !stagedRm.isFile()) {
                files.add(filename);
            } else if (tracked && !stagedAdd.isFile() && working.isFile()
                    && !Arrays.equals(curr.blob(filename).contents(),
                    Utils.readContents(working))) {
                changes.add(filename + " (modified)");
            } else if (tracked && !working.isFile() && !stagedRm.isFile()) {
                changes.add(filename + " (deleted)");
            } else if (stagedAdd.isFile() && !working.isFile()) {
                changes.add(filename + " (deleted)");
            } else if (tracked && !working.isFile() && !stagedRm.isFile()) {
                changes.add(filename + " (deleted)");
            }
        }

        Collections.sort(files);
        Collections.sort(changes);
    }

    /** Return a list of names of all files in the working directory. */
    static List<String> workingFiles() {
        return Utils.plainFilenamesIn(_cwd);
    }

    /** Stage a file with FILENAME and CONTENTS for addition. */
    static void addStageFile(String filename, byte[] contents) {
        File stagedAdd = new File(_stagingAddDir, filename);
        Storage.createNewFile(stagedAdd);
        Utils.writeContents(stagedAdd, contents);
    }

    /** Stage a file with FILENAME for removal. */
    static void rmStageFile(String filename) {
        File stagedRm = new File(_stagingRmDir, filename);
        Storage.createNewFile(stagedRm);
    }

    /** Return a list of names of all files staged for addition. */
    static List<String> stagedAdds() {
        return Utils.plainFilenamesIn(_stagingAddDir);
    }

    /** Return a list of names of all files staged for removal. */
    static List<String> stagedRms() {
        return Utils.plainFilenamesIn(_stagingRmDir);
    }

    /** Deletes all of the files in the staging area. */
    static void clearStaging() {
        for (String filename : stagedAdds()) {
            delete(new File(_stagingAddDir, filename));
        }

        for (String filename : stagedRms()) {
            delete(new File(_stagingRmDir, filename));
        }
    }

    /** Return File object after reading remote path with name REMOTENAME
     *  into the File object. */
    static File readRemote(String remoteName) {
        File referenceFile = new File(_remotesDir, remoteName);
        String remotePath = Utils.readContentsAsString(referenceFile);

        if (remotePath.startsWith("." + File.separator)) {
            remotePath = remotePath.replace("." + File.separator,
                    _cwd.getPath() + File.separator);
        }

        return new File(remotePath);
    }

    /** Return _cwd (getter method). */
    public static File cwd() {
        return _cwd;
    }

    /** Return _gitletDir (getter method). */
    public static File gitletDir() {
        return _gitletDir;
    }

    /** Return _headsDir (getter method). */
    public static File headsDir() {
        return _headsDir;
    }

    /** Return _remotesDir (getter method). */
    public static File remotesDir() {
        return _remotesDir;
    }

    /** Return _stagingAddDir (getter method). */
    public static File stagingAddDir() {
        return _stagingAddDir;
    }

    /** Return _stagingRmDir (getter method). */
    public static File stagingRmDir() {
        return _stagingRmDir;
    }

    /** Return _objectIndex (getter method). */
    public static File objectIndex() {
        return _objectIndex;
    }

    /** Current Working Directory (should be the directory of the repo). This
     *  can be changed via method calls. */
    private static File _cwd = ORIG_CWD;

    /** Main metadata folder for Gitlet files (.gitlet). */
    private static File _gitletDir = new File(_cwd, ".gitlet");

    /** Objects folder containing Commit and Blob files (.gitlet/objects). */
    private static File _objectsDir = new File(_gitletDir, "objects");

    /** References metadata folder (.gitlet/refs). */
    private static File _refsDir = new File(_gitletDir, "refs");

    /** Contains files that contain branch heads hashes (.gitlet/refs/heads). */
    private static File _headsDir = new File(_refsDir, "heads");

    /** Remotes metadata folder (.gitlet/refs/remotes). */
    private static File _remotesDir = new File(_refsDir, "remotes");

    /** Staging metadata folder (.gitlet/staging). */
    private static File _stagingDir = new File(_gitletDir, "staging");

    /** Staging metadata folder for additions (.gitlet/staging/add). */
    private static File _stagingAddDir = new File(_stagingDir, "add");

    /** Staging metadata folder for removals (.gitlet/staging/rm). */
    private static File _stagingRmDir = new File(_stagingDir, "rm");

    /** HEAD file containing serialized File object pointing to current
     *  branch reference file (.gitlet/HEAD). */
    private static File _head = new File(_gitletDir, "HEAD");

    /** COMMIT_INDEX file containing comma separated list of all commit hashes
     *  (.gitlet/COMMIT_INDEX). */
    private static File _commitIndex = new File(_gitletDir, "COMMIT_INDEX");

    /** OBJECT_INDEX file containing comma separated list of all object hashes
     *  (.gitlet/OBJECT_INDEX). */
    private static File _objectIndex = new File(_gitletDir, "OBJECT_INDEX");

}
