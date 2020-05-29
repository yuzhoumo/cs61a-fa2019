package gitlet;

import java.io.IOException;

/** Driver class for Gitlet, the tiny stupid version-control system.
 *  @author Joe Mo */
public class Main {

    /** Prints MESSAGE and exits with exit code STATUS. */
    public static void exit(String message, int status) {
        System.out.println(message);
        System.exit(status);
    }

    /** Usage: java gitlet.Main ARGS, where ARGS contains
     *  <COMMAND> <OPERAND> .... */
    public static void main(String... args) {
        if (args.length == 0) {
            exit("Please enter a command.", 0);
        }

        try {
            switch (args[0]) {
            case "init": Gitlet.init(args); break;
            case "add": Gitlet.add(args); break;
            case "commit": Gitlet.commit(args); break;
            case "rm": Gitlet.rm(args); break;
            case "log": Gitlet.log(args); break;
            case "global-log": Gitlet.globalLog(args); break;
            case "find": Gitlet.find(args); break;
            case "status": Gitlet.status(args); break;
            case "checkout": Gitlet.checkout(args); break;
            case "branch": Gitlet.branch(args); break;
            case "rm-branch": Gitlet.rmBranch(args); break;
            case "reset": Gitlet.reset(args); break;
            case "merge": Gitlet.merge(args); break;
            case "add-remote": Gitlet.addRemote(args); break;
            case "rm-remote": Gitlet.rmRemote(args); break;
            case "push": Gitlet.push(args); break;
            case "fetch": Gitlet.fetch(args); break;
            case "pull": Gitlet.pull(args); break;
            default: exit("No command with that name exists.", 0);
            }
        } catch (GitletException | IOException e) {
            exit(e.getMessage(), 1);
        }
    }

}
