# Gitlet Design Document

**Name**: Joe Mo

## Classes and Data Structures

### Blob
Represents a file's contents.

#### Fields
1. `byte[] _contents`: Content of blob represented as a byte array.
2. `String _hash`: SHA-1 hash of the blob for use as identifier.

### Commit
Represents a commit and all of its metadata.

#### Fields
1. `String _branch`: Name of the commit branch.
3. `Date _date`: Commit date.
4. `String _message`: Stores message to be displayed in log.
5. `String _parent`: SHA-1 hash of parent commit.
6. `String _hash`: SHA-1 hash of commit.
7. `HashMap<String, String> _blobHashes`: Mapping from filename to SHA-1
 hashes of blobs.

### Gitlet
Contains static methods corresponding to each Gitlet command.

### Main
The main driver class for the Gitlet project.

### Storage
Contains static methods for handling file persistence.

#### Fields
1. `File CWD`: Current Working Directory.
2. `FIle GITLET_DIR`: Main metadata folder (.gitlet).
3. `File OBJECTS_DIR`: Objects metadata folder (.gitlet/objects).
4. `File REFS_DIR`: References metadata folder (.gitlet/refs).
5. `File HEADS_DIR`: Heads metadata folder (.gitlet/refs/heads).
6. `File REMOTES_DIR`: Remotes metadata folder (.gitlet/refs/remotes).
7. `File STAGING_DIR`: Staging metadata folder (.gitlet/staging).
8. `File HEAD`: HEAD file (.gitlet/HEAD).

## Algorithms

### Main Class
1. `exit(String message, int status)`: Exits the program with given message
 and status code.
2. `main(String[] args)`: Runs a command from the Gitlet class based on user
 input from args.

### Gitlet Class
1. `validateArgs(String cmd, String[] args, int n)`: Verifies that the args
array contains the correct number of arguments and that the .gitlet
directory is initialize for all commands other than "init".
2. `init(String[] args)`: Initialized the .gitlet subdirectory and saves the
initial commit.
3. `add(String[] args)`: Adds files to staging area. Omits file from staging
area if no changes are made. Removes rm operation from staging if the file
had been staged for deletion.
4. `commit(String[] args)`: Creates a new commit and saves to and updates the
.gitlet folder.
5. `rm(String[] args)`: Stage a file for removal.
6. `log(String[] args)`: Display a log of the current branch's commit history.
7. `globalLog(String[] args)`: Displays information about all commits ever made.
8. `find(String[] args)`: Displays what branches currently exist, and marks
the current branch with a *. Also displays what files have been staged for
addition or removal. 
9. `status(String[] args)`: Prints out the ids of all commits that have the
given commit message, one per line. If there are multiple such commits, it
prints the ids out on separate lines. 
10. `checkout(String[] args)`: Checkout is a kind of general command that can do
a few different things depending on what its arguments are.
11. `branch(String[] args)`: Creates a new branch with the given name, and
points it at the current head node. A branch is nothing more than a name for a
reference (a SHA-1 identifier) to a commit node.
12. `rmBranch(String[] args)`:  Deletes the branch with the given name. 
13. `reset(String[] args)`: Checks out all the files tracked by the given
commit. Removes tracked files that are not present in that commit.
14. `merge(String[] args)`: Merges files from the given branch into the current
branch. 
15. `addRemote(String[] args)`: Saves the given login information under the
given remote name. Attempts to push or pull from the given remote name will then
attempt to use this .gitlet directory.
16. `rmRemote(String[] args)`: Remove information associated with the given
remote name.
17. `push(String[] args)`: Attempts to append the current branch's commits to
the end of the given branch at the given remote.
18. `fetch(String[] args)`: Brings down commits from the remote Gitlet
repository into the local Gitlet repository. 
19. `pull(String[] args)`: Fetches branch [remote name]/[remote branch name] as
for the fetch command, and then merges that fetch into the current branch.

### Blob Class
1. `Blob(byte[] contents)`: Constructs a blob object with contents given by
 byte array.
2. `String genHash()`: Generates the SHA-1 identifier hash of the blob based
 its contents.
3. `byte[] contents()`: Returns a byte array of the blob's contents.
4. `String hash()`: Returns the blob's SHA-1 identifier hash.
5. `String objectType()`: Returns "blob".
6. `String toString()`: Returns a string representation of the blob.

### Commit Class
1. `Commit()`: Constructs initial commit.
2. `Commit(String branch, String message, String parent,
Collection<String> filenames)`: Constructs a commit with name of the commit
branch, commit message, SHA-1 hash of parent commit, and a mapping of
 filenames of commit blobs to their SHA-1 identifiers.
3. `String initialHash()`: Generates SHA-1 hash of commit based on its initial
 values.
4. `boolean contains(String filename)`: Returns true if the commit contains a
 file with the specified name.
5. `String blobHash(String filename)`: Returns the hash of a blob given its
 filename.
6. `String branch()`: Returns name of the commit branch.
7. `String author()`: Returns name of the commit author.
8. `Date date()`: Returns commit date.
9. `String message()`: Returns commit message.
10. `String parent()`: Returns SHA-1 hash of parent commit.
11. `String hash()`: Returns SHA-1 hash of commit.
12. `String objectType()`: Always returns "commit".
13. `String toString()`: Returns string representation of the commit.

### Storage Class
1. `boolean checkInitialized()`: Checks that the .gitlet folder is initialized.
2. `File hashToFile(String hash)`: Takes an object hash and returns the file
associated with the hash (the file points to either a blob or a commit).
3. `writeObject(GitletObject object)`: Writes a GitletObject to the objects
folder in the .gitlet directory (.gitlet/object/[first 2 chars of hash]/[last
38 chars of hash]).
4. `<T extends GitletObject> T readObject(String hash, Class<T> expectedClass
)`: Reads GitletObject from file given the hash of the object and the
expected class.
5. `writeBranch(String name, String hash)`: Writes branch reference
(with given name, containing the hash to the latest commit on that branch
) to the refs folder (.gitlet/refs/heads/[branch name]).
6. `String readReference(File ref)`: Given a File object that points to a branch
 file, returns the SHA-1 hash stored in the branch file.
7. `writeHead(String branch)`: Writes a File object pointing to the current
 branch into the main HEAD file (.gitlet/HEAD).
8. `File readHead()`: Reads the HEAD file into a File object pointing to the
 current branch file.
9. `Commit currentCommit()`: Returns a commit object representing the current
 commit that the head is pointing to.

## Persistence
The file structure for the `.gitlet` folder is as follows:

```
HEAD
objects/
  |-31/
  |  |-902cfe42bc787e0fc94dd4eb6bf8921b2c6008
refs/
  |-heads/
  |  |-master
staging/
  |-staged_file
```

* `.gitlet`: Main Gitlet folder.
* `.gitlet/HEAD`: Serialized File object pointing to current branch reference
 file in `refs/heads`.
* `.gitlet/objects`: Objects folder containing gitlet objects (commits and
 blobs) grouped in folders by first 2 characters of hash.
* `.gitlet/refs`: Contains folders for reference files.
* `.gitlet/refs/heads`: Contains reference files named by branch name and
 containing hashes of the latest commits in the branches.
* `.gitlet/refs/remotes`: Contains the reference files for remote repositories.
* `.gitlet/refs/staging`: Contains files added to staging area.

