package gitlet;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/** Contains methods for all Gitlet commands.
 *  @author Joe Mo */
public class Gitlet {

    /** Validates that argument array ARGS contains the valid number of
     *  arguments N and exits otherwise. Also checks that the user is in an
     *  initialized Gitlet directory if the command name CMD is not "init". */
    private static void validateArgs(String cmd, String[] args, int n) {
        if (!cmd.equals("init") && !Storage.checkInitialized()) {
            Main.exit("Not in an initialized Gitlet directory.", 0);
        } else if (args.length != n) {
            Main.exit("Incorrect operands.", 0);
        }
    }

    /** Takes ARGS and initializes .gitlet folder with initial commit.
     *  Usage: java gitlet.Main init. */
    public static void init(String... args) throws IOException {
        validateArgs("init", args, 1);
        if (Storage.checkInitialized()) {
            System.out.println("A Gitlet version-control system already"
                    + " exists in the current directory.");
        } else {
            Storage.setup();
        }
    }

    /** Takes ARGS and adds file to staging area. Omits the file from staging
     *  area if the previous commit contains the exact same file. The file
     *  will no longer be staged for removal.
     *  Usage: java gitlet.Main add [file name] */
    public static void add(String... args) throws IOException {
        validateArgs("add", args, 2);

        String filename = args[1];
        File addition = new File(Storage.cwd(), filename);
        File stagedAdd = new File(Storage.stagingAddDir(), filename);
        File stagedRm = new File(Storage.stagingRmDir(), filename);

        if (!addition.isFile()) {
            Main.exit("File does not exist.", 0);
        }

        Commit curr = Storage.currentCommit();
        byte[] fileContents = Utils.readContents(addition);
        Storage.delete(stagedRm);

        if (curr.contains(filename)) {
            String blobHash = curr.blobHash(filename);
            Blob blob = Storage.readBlob(blobHash);

            if (Arrays.equals(fileContents, blob.contents())) {
                Storage.delete(stagedAdd);
            } else {
                Storage.addStageFile(filename, fileContents);
            }
        } else {
            Storage.addStageFile(filename, fileContents);
        }
    }


    /** Helper method for commit with a different response for merge commits.
     *  Takes in the commit MESSAGE and MERGEHASH. If the merges hash is
     *  empty, then the commit precedes as usual. Otherwise, it writes a new
     *  containing both the hash of its parent and the merged parent. */
    private static void commitHelper(String message, String mergeHash)
            throws IOException {
        if (message.length() == 0) {
            Main.exit("Please enter a commit message.", 0);
        }

        List<String> add = Storage.stagedAdds();
        List<String> remove = Storage.stagedRms();

        if (add.size() == 0 && remove.size() == 0) {
            Main.exit("No changes added to the commit.", 0);
        }

        Commit curr = Storage.currentCommit();
        Commit next;
        if (mergeHash.isEmpty()) {
            next = new Commit(curr, message);
        } else {
            next = new Commit(curr, message, mergeHash);
        }

        for (String filename : remove) {
            File stagedRm = new File(Storage.stagingRmDir(), filename);
            next.removeBlob(filename);
            Storage.delete(stagedRm);
        }

        for (String filename : add) {
            File stagedAdd = new File(Storage.stagingAddDir(), filename);
            Blob blob = new Blob(Utils.readContents(stagedAdd));
            Storage.writeBlob(blob);
            next.addBlob(filename, blob.hash());
            Storage.delete(stagedAdd);
        }

        Storage.writeCommit(next);
    }

    /** Takes ARGS and creates a new commit with staged changes. Sets the
     *  head of the current branch to this new commit and clears the staging
     *  area.
     *  Usage: java gitlet.Main commit [message] */
    public static void commit(String... args) throws IOException {
        validateArgs("commit", args, 2);
        String message = args[1].strip();
        commitHelper(message, "");
    }

    /** Takes ARGS and stages files for removal. Deletes the file from the
     *  working directory if it is tracked by the current commit. Removes
     *  file from commit if it is tracked, and removes file from staging
     *  area is it is staged for addition.
     *  Usage: java gitlet.Main rm [file name] */
    public static void rm(String... args) throws IOException {
        validateArgs("rm", args, 2);
        String filename = args[1];
        File workingFile = new File(Storage.cwd(), filename);
        File stagedAdd = new File(Storage.stagingAddDir(), filename);

        Commit curr = Storage.currentCommit();
        boolean deletedStagedAdd = stagedAdd.delete();
        if (!deletedStagedAdd && !curr.contains(filename)) {
            Main.exit("No reason to remove the file.", 0);
        } else if (!deletedStagedAdd) {
            Storage.rmStageFile(filename);
            if (curr.contains(filename)) {
                Storage.delete(workingFile);
            }
        }
    }

    /** Takes COMMIT and prints a single log message with information from
     *  the commit. */
    private static void logCommit(Commit commit) {
        String format = "EEE MMM dd HH:mm:ss yyyy ZZZZ";
        String dateString = new SimpleDateFormat(format).format(commit.date());

        System.out.println("===\ncommit " + commit.hash());

        if (commit.isMerge()) {
            String orig = commit.hash().substring(0, 7);
            String merge = commit.mergeHash().substring(0, 7);
            System.out.println("Merge: " + orig + " " + merge);
        }

        System.out.println("Date: " + dateString);
        System.out.println(commit.message() + "\n");
    }

    /** Takes ARGS and prints a chronological log of the commits in the
     *  current branch.
     *  Usage: java gitlet.Main log*/
    public static void log(String... args) {
        validateArgs("log", args, 1);
        for (Commit commit : Storage.commits(Storage.currentCommit())) {
            logCommit(commit);
        }
    }

    /** Takes ARGS and prints a non-chronological log of all of the commits
     *  stored in the repository.
     *  Usage: java gitlet.Main global-log */
    public static void globalLog(String... args) {
        validateArgs("global-log", args, 1);
        for (Commit commit: Storage.commits()) {
            logCommit(commit);
        }
    }

    /** Takes ARGS and prints a list of commit hashes whose messages contain
     *  the given string.
     *  Usage: java gitlet.Main find [commit message] */
    public static void find(String... args) {
        validateArgs("find", args, 2);
        String key = args[1];

        int count = 0;
        for (Commit commit : Storage.commits()) {
            if (commit.message().contains(key)) {
                System.out.println(commit.hash());
                count++;
            }
        }

        if (count == 0) {
            System.out.println("Found no commit with that message.");
        }
    }

    /** Prints out the contents of the given LIST line by line. */
    private static void printList(List<? extends Object> list) {
        for (Object item : list) {
            System.out.println(item);
        }
    }

    /** Takes ARGS and prints out the current status of the Gitlet repository.
     *  Displays what branches currently exist, and marks the current branch
     *  with a *. Also displays what files have been staged for addition or
     *  removal. Untracked files and modifications are also listed.
     *  Usage: java gitlet.Main status*/
    public static void status(String... args) {
        validateArgs("status", args, 1);
        List<String> untrackedChanges = new ArrayList<>();
        List<String> untrackedFiles = new ArrayList<>();
        Storage.untracked(untrackedFiles, untrackedChanges);
        String currentBranch = Storage.currentBranch();

        System.out.println("=== Branches ===");
        boolean printedCurrent = false;
        for (String name : Storage.branches()) {
            if (!printedCurrent && name.equals(currentBranch)) {
                System.out.println("*" + currentBranch);
                printedCurrent = true;
            } else {
                System.out.println(name);
            }
        }

        System.out.println("\n=== Staged Files ===");
        printList(Storage.stagedAdds());
        System.out.println("\n=== Removed Files ===");
        printList(Storage.stagedRms());
        System.out.println("\n=== Modifications Not Staged For Commit ===");
        printList(untrackedChanges);
        System.out.println("\n=== Untracked Files ===");
        printList(untrackedFiles);
        System.out.println();
    }

    /** Checks out the file with FILENAME in the given COMMIT. */
    private static void checkoutSingle(String filename, Commit commit) {

        if (!commit.contains(filename)) {
            Main.exit("File does not exist in that commit.", 0);
        }

        File checkedFile = new File(Storage.cwd(), filename);
        Storage.createNewFile(checkedFile);
        Utils.writeContents(checkedFile, commit.blob(filename).contents());
    }

    /** Takes in a commit OTHER and checks out all of the commit's files. */
    private static void checkoutAll(Commit other) {
        Commit curr = Storage.currentCommit();

        for (String filename : Storage.untracked()) {
            if (other.contains(filename)) {
                Main.exit("There is an untracked file in the way; delete"
                        + " it, or add and commit it first.", 0);
            }
        }

        for (String filename : other.files()) {
            checkoutSingle(filename, other);
        }

        for (String filename : Storage.workingFiles()) {
            if (curr.contains(filename) && !other.contains(filename)) {
                File workingFile = new File(Storage.cwd(), filename);
                Storage.delete(workingFile);
            }
        }

        Storage.clearStaging();
    }

    /** Takes ARGS and performs operations based on commands with one of three
     *  possible formats:
     *
     *  1. Takes the version of the file as it exists in the head commit, the
     *  front of the current branch, and puts it in the working directory,
     *  overwriting the version of the file that's already there if there is
     *  one. The new version of the file is not staged.
     *
     *  2. Takes the version of the file as it exists in the commit with the
     *  given id, and puts it in the working directory, overwriting the version
     *  of the file that's already there if there is one. The new version of the
     *  file is not staged.
     *
     *  3. Takes all files in the commit at the head of the given branch, and
     *  puts them in the working directory, overwriting the versions of the
     *  files that are already there if they exist. Also, at the end of this
     *  command, the given branch will now be considered the current branch
     *  (HEAD). Any files that are tracked in the current branch but are not
     *  present in the checked-out branch are deleted. The staging area is
     *  cleared, unless the checked-out branch is the current branch.
     *
     *  Usages:
     *  1. java gitlet.Main checkout -- [file name]
     *  2. java gitlet.Main checkout [commit id] -- [file name]
     *  3. java gitlet.Main checkout [branch name]
     */
    public static void checkout(String... args) {
        Storage.checkInitialized();

        if (args.length == 3 && args[1].equals("--")) {
            String filename = args[2];
            Commit curr = Storage.currentCommit();
            checkoutSingle(filename, curr);
        } else if (args.length == 4 && args[2].equals("--")) {
            String filename = args[3];
            String commitHash = args[1];
            Commit commit = null;

            try {
                commit = Storage.readCommit(commitHash);
            } catch (NullPointerException | IllegalArgumentException e) {
                Main.exit("No commit with that id exists.", 0);
            }

            checkoutSingle(filename, commit);
        } else if (args.length == 2) {
            String branchName = args[1];
            File branch = Storage.readBranch(branchName);

            if (branchName.equals(Storage.currentBranch())) {
                Main.exit("No need to checkout the current branch.", 0);
            } else if (!branch.isFile()) {
                Main.exit("No such branch exists.", 0);
            }

            String commitHash = Utils.readContentsAsString(branch);
            Commit commit = Storage.readCommit(commitHash);
            checkoutAll(commit);
            Storage.writeHead(branchName);
        } else {
            Main.exit("Incorrect operands.", 0);
        }
    }

    /** Takes ARGS and creates a new branch with the given name, and points
     *  it at the current head node. A branch is nothing more than a name for
     *  a reference (a SHA-1 identifier) to a commit node. This command does NOT
     *  immediately switch to the newly created branch (just as in real Git).
     *  Before you ever call branch, your code should be running with a default
     *  branch called "master".
     *  Usage: java gitlet.Main branch [branch name] */
    public static void branch(String... args) {
        validateArgs("branch", args, 2);

        String branchName = args[1];
        File branchFile = new File(Storage.headsDir(), branchName);
        if (branchFile.isFile()) {
            Main.exit("A branch with that name already exists.", 0);
        }

        Storage.writeBranch(branchName, Storage.currentCommit().hash());
    }

    /** Takes ARGS and deletes the branch with the given name. This only
     *  means to delete the pointer associated with the branch; it does not
     *  mean to delete all commits that were created under the branch.
     *  Usage: java gitlet.Main rm-branch [branch name] */
    public static void rmBranch(String... args) {
        validateArgs("rm-branch", args, 2);

        String branchName = args[1];
        File branchFile = new File(Storage.headsDir(), branchName);
        if (!branchFile.isFile()) {
            Main.exit("A branch with that name does not exist.", 0);
        }

        if (branchName.equals(Storage.currentBranch())) {
            Main.exit("Cannot remove the current branch.", 0);
        }

        Storage.delete(branchFile);
    }

    /** Takes ARGS and checks out all the files tracked by the given commit.
     *  Removes tracked files that are not present in that commit. Also moves
     *  the current branch's head to that commit node. The [commit id] may be
     *  abbreviated as for checkout. The staging area is cleared. The command
     *  is essentially checkout of an arbitrary commit that also changes the
     *  current branch head.
     *  Usage: java gitlet.Main reset [commit id] */
    public static void reset(String... args) {
        validateArgs("reset", args, 2);
        String commitHash = args[1];
        Commit commit = null;
        try {
            commit = Storage.readCommit(commitHash);
        } catch (IllegalArgumentException e) {
            Main.exit("No commit with that id exists.", 0);
        }

        checkoutAll(commit);
        Storage.writeBranch(Storage.currentBranch(), commit.hash());
    }

    /** Takes blob CURR from current branch, blob GIVEN from merged branch,
     *  and FILENAME. Merges conflicting files into a single file and
     *  adds and stages the file. */
    private static void mergeConflict(Blob curr, Blob given, String filename) {
        _encounteredMergeConflict = true;
        String main = curr != null ? new String(curr.contents(),
                StandardCharsets.UTF_8) : "";
        String other = given != null ? new String(given.contents(),
                StandardCharsets.UTF_8) : "";

        String merged = "<<<<<<< HEAD\n";
        merged += main + "=======\n";
        merged += other + ">>>>>>>\n";
        File workingFile = new File(Storage.cwd(), filename);

        Storage.createNewFile(workingFile);
        Utils.writeContents(workingFile, merged);
        Storage.addStageFile(filename, merged.getBytes(StandardCharsets.UTF_8));
    }

    /** Takes in the commit SPLITPOINT, the commit CURRCOMMIT from the main
     * branch, the commit GIVENCOMMIT from the merged in branch, and the file
     * name FILENAME. Handles the cases where the file in question is tracked
     * in all three commits. */
    private static void mergeCaseFileInAll(
            Commit splitPoint, Commit currCommit, Commit givenCommit,
            String filename) {

        Blob origBlob = splitPoint.blob(filename);
        Blob currBlob = currCommit.blob(filename);
        Blob givenBlob = givenCommit.blob(filename);
        boolean currIsModified = !origBlob.equals(currBlob);
        boolean givenIsModified = !origBlob.equals(givenBlob);
        boolean sameChanges = currBlob.equals(givenBlob);

        if (givenIsModified && !currIsModified) {
            checkout("checkout", givenCommit.hash(), "--", filename);
            Storage.addStageFile(filename, givenBlob.contents());
        } else if (currIsModified && givenIsModified && !sameChanges) {
            mergeConflict(currBlob, givenBlob, filename);
        }
    }

    /** Takes in the commit SPLITPOINT, the commit CURRCOMMIT from the main
     * branch, the commit GIVENCOMMIT from the merged in branch, and the file
     * name FILENAME. Handles the cases where the file in question is tracked
     * in the split point. */
    private static void mergeCaseFileInSplit(
            Commit splitPoint, Commit currCommit, Commit givenCommit,
            String filename) {

        boolean fileInCurr = currCommit.contains(filename);
        boolean fileInGiven = givenCommit.contains(filename);

        if (fileInCurr && !fileInGiven) {
            Blob origBlob = splitPoint.blob(filename);
            Blob currBlob = currCommit.blob(filename);
            boolean currUnmodified = origBlob.equals(currBlob);

            if (currUnmodified) {
                Storage.delete(new File(Storage.cwd(), filename));
                Storage.rmStageFile(filename);
            }
        }

        if (fileInCurr && !fileInGiven) {
            Blob origBlob = splitPoint.blob(filename);
            Blob currBlob = currCommit.blob(filename);
            boolean currModified = !origBlob.equals(currBlob);

            if (currModified) {
                mergeConflict(currBlob, null, filename);
            }
        }

        if (fileInGiven && !fileInCurr) {
            Blob origBlob = splitPoint.blob(filename);
            Blob givenBlob = givenCommit.blob(filename);
            boolean givenModified = !origBlob.equals(givenBlob);

            if (givenModified) {
                mergeConflict(null, givenBlob, filename);
            }
        }
    }

    /** Takes in the commit SPLITPOINT, the commit CURRCOMMIT from the main
     * branch, the commit GIVENCOMMIT from the merged in branch, and the file
     * name FILENAME. Handles the cases where the file in question is
     * not tracked in the split point. */
    private static void mergeCaseFileNotInSplit(
            Commit currCommit, Commit givenCommit, String filename) {

        boolean fileInCurr = currCommit.contains(filename);
        boolean fileInGiven = givenCommit.contains(filename);
        if (!fileInCurr && fileInGiven) {
            Blob givenBlob = givenCommit.blob(filename);
            checkout("checkout", givenCommit.hash(), "--", filename);
            Storage.addStageFile(filename, givenBlob.contents());
        }

        if (fileInCurr && fileInGiven) {
            Blob currBlob = currCommit.blob(filename);
            Blob givenBlob = givenCommit.blob(filename);
            boolean differentContents = !currBlob.equals(givenBlob);

            if (differentContents) {
                mergeConflict(currBlob, givenBlob, filename);
            }
        }
    }

    /** Takes ARGS and merges files from the given branch into the current
     *  branch.
     *  Usage: java gitlet.Main merge [branch name] */
    public static void merge(String... args) throws IOException {
        validateArgs("merge", args, 2);
        _encounteredMergeConflict = false;
        String givenBranchName = args[1];
        File givenBranchFile = Storage.readBranch(givenBranchName);
        String currBranchName = Storage.currentBranch();

        if (Storage.stagedAdds().size() > 0 || Storage.stagedRms().size() > 0) {
            Main.exit("You have uncommitted changes.", 0);
        } else if (!givenBranchFile.isFile()) {
            Main.exit("A branch with that name does not exist.", 0);
        } else if (currBranchName.equals(givenBranchName)) {
            Main.exit("Cannot merge a branch with itself.", 0);
        }

        String givenBranchHash = Utils.readContentsAsString(givenBranchFile);
        Commit splitPoint = Storage.splitPoint(currBranchName, givenBranchName);
        Commit currCommit = Storage.currentCommit();
        Commit givenCommit = Storage.readCommit(givenBranchHash);

        if (splitPoint.hash().equals(givenBranchHash)) {
            Main.exit("Given branch is an ancestor of the current branch.", 0);
        } else if (splitPoint.hash().equals(currCommit.hash())) {
            checkout("checkout", givenBranchName);
            Main.exit("Current branch fast-forwarded.", 0);
        }

        HashSet<String> filesAtHeadCommits = new HashSet<>();
        filesAtHeadCommits.addAll(givenCommit.files());
        filesAtHeadCommits.addAll(currCommit.files());
        for (String filename : Storage.untracked()) {
            if (filesAtHeadCommits.contains(filename)) {
                Main.exit("There is an untracked file in the way; delete"
                        + " it, or add and commit it first.", 0);
            }
        }

        for (String file : filesAtHeadCommits) {
            boolean fileInSplit = splitPoint.contains(file);
            boolean fileInCurr = currCommit.contains(file);
            boolean fileInGiven = givenCommit.contains(file);

            if (fileInSplit && fileInCurr && fileInGiven) {
                mergeCaseFileInAll(splitPoint, currCommit, givenCommit, file);
            } else if (fileInSplit) {
                mergeCaseFileInSplit(splitPoint, currCommit, givenCommit, file);
            } else {
                mergeCaseFileNotInSplit(currCommit, givenCommit, file);
            }
        }

        commitHelper("Merged " + givenBranchName + " into "
                + currBranchName + ".", givenBranchHash);

        if (_encounteredMergeConflict) {
            System.out.println("Encountered a merge conflict.");
            _encounteredMergeConflict = false;
        }
    }

    /** Takes ARGS and saves the given login information under the given
     *  remote name. Attempts to push or pull from the given remote name will
     *  then attempt to use this .gitlet directory.
     *  Usage: java gitlet.Main add-remote [remote name] [name of remote
     *  directory] */
    public static void addRemote(String... args) {
        validateArgs("add-remote", args, 3);
        String remoteName = args[1];
        File refLocation = new File(Storage.remotesDir(), remoteName);
        if (refLocation.exists()) {
            Main.exit("A remote with that name already exists.", 0);
        }

        String remotePath = args[2].replaceAll("/", File.separator);
        remotePath = remotePath.replace(".gitlet", "");
        Utils.writeContents(refLocation, remotePath);
    }

    /** Takes ARGS and remove information associated with the given remote
     *  name.
     *  Usage: java gitlet.Main rm-remote [remote name] */
    public static void rmRemote(String... args) {
        validateArgs("rm-remote", args, 2);
        String remoteName = args[1];
        File refLocation = new File(Storage.remotesDir(), remoteName);
        if (!refLocation.exists()) {
            Main.exit("A remote with that name does not exist.", 0);
        } else {
            Storage.delete(refLocation);
        }
    }

    /** Takes ARGS and attempts to append the current branch's commits to
     *  the end of the given branch at the given remote.
     *  Details: This command only works if the remote branch's head is in
     *  the history of the current local head, which means that the local branch
     *  contains some commits in the future of the remote branch. In this case,
     *  append the future commits to the remote branch. Then, the remote should
     *  reset to the front of the appended commits (so its head will be the same
     *  as the local head). This is called fast-forwarding. If the Gitlet
     *  system on the remote machine exists but does not have the input branch,
     *  then simply add the branch to the remote Gitlet.
     *  Usage: java gitlet.Main push [remote name] [remote branch name] */
    public static void push(String... args) {
        validateArgs("push", args, 3);
        String remoteName = args[1];
        String branchName = args[2];
        Commit currCommit = Storage.currentCommit();
        File referenceFile = new File(Storage.remotesDir(), remoteName);

        if (!referenceFile.exists()) {
            Main.exit("Remote directory not found.", 0);
        }

        File remoteLocation = Storage.readRemote(remoteName);

        try {
            Storage.changeCWD(remoteLocation);
        } catch (IllegalArgumentException e) {
            Main.exit("Remote directory not found.", 0);
        }

        if (!Storage.gitletDir().isDirectory()) {
            Main.exit("Remote directory not found.", 0);
        }

        File remoteBranch = Storage.readBranch(branchName);
        String branchHead = Utils.readContentsAsString(remoteBranch).strip();

        Storage.resetCWD();
        List<Commit> commitHistory = Storage.commits(currCommit);
        Storage.changeCWD(remoteLocation);

        boolean inHistory = false;
        for (Commit commit : commitHistory) {
            if (branchHead.equals(commit.hash())) {
                inHistory = true;
                break;
            }
        }

        if (!inHistory) {
            Main.exit("Please pull down remote changes before pushing.", 0);
        }

        Storage.resetCWD();
        for (String hash : Storage.readIndex(Storage.objectIndex())) {
            Storage.changeCWD(remoteLocation);
            if (!Storage.hasObject(hash)) {
                Storage.resetCWD();
                GitletObject obj = Storage.readObject(hash, GitletObject.class);
                Storage.changeCWD(remoteLocation);
                Storage.writeObject(obj);
            }
        }

        Storage.changeCWD(remoteLocation);
        reset("reset", currCommit.hash());
        Storage.resetCWD();
    }

    /** Takes ARGS and brings down commits from the remote Gitlet repository
     *  into the local Gitlet repository. Basically, this copies all commits
     *  and blobs from the given branch in the remote repository (that are not
     *  already in the current repository) into a branch named
     *  [remote name]/[remote branch name] in the local .gitlet, changing
     *  [remote name]/[remote branch name] to point to the head commit
     *  (thus copying the contents of the branch from the remote repository
     *  to the current one). This branch is created in the local repository if
     *  it did not previously exist.
     *  Usage: java gitlet.Main fetch [remote name] [remote branch name] */
    public static void fetch(String... args) {
        validateArgs("fetch", args, 3);
        String remoteName = args[1];
        String branchName = args[2];
        File referenceFile = new File(Storage.remotesDir(), remoteName);

        if (!referenceFile.exists()) {
            Main.exit("Remote directory not found.", 0);
        }

        File remoteLocation = Storage.readRemote(args[1]);

        try {
            Storage.changeCWD(remoteLocation);
        } catch (IllegalArgumentException e) {
            Main.exit("Remote directory not found.", 0);
        }

        File remoteBranch = Storage.readBranch(branchName);
        if (!Storage.gitletDir().isDirectory()) {
            Storage.resetCWD();
            Main.exit("Remote directory not found.", 0);
        } else if (!remoteBranch.isFile()) {
            Storage.resetCWD();
            Main.exit("That remote does not have that branch.", 0);
        }

        String branchHead = Utils.readContentsAsString(remoteBranch).strip();
        for (String hash : Storage.readIndex(Storage.objectIndex())) {
            Storage.resetCWD();
            if (!Storage.hasObject(hash)) {
                Storage.changeCWD(remoteLocation);
                GitletObject obj = Storage.readObject(hash, GitletObject.class);
                Storage.resetCWD();
                Storage.writeObject(obj);
            }
        }

        Storage.resetCWD();
        File branchDir = new File(Storage.headsDir(), remoteName);
        File branchFile = new File(branchDir, branchName);
        Storage.mkdir(branchDir);
        Storage.createNewFile(branchFile);
        Utils.writeContents(branchFile, branchHead);
    }

    /** Takes ARGS and fetches branch [remote name]/[remote branch name] as
     *  for the fetch command, and then merges that fetch into the current
     *  branch.
     *  Usage: java gitlet.Main pull [remote name] [remote branch name] */
    public static void pull(String... args) throws IOException {
        validateArgs("pull", args, 3);
        fetch("fetch", args[1], args[2]);
        merge("merge", args[1] + "/" + args[2]);
    }

    /** Flips to true if a merge conflict is encountered. */
    private static boolean _encounteredMergeConflict = false;

}
