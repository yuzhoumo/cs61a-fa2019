package enigma;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

import java.util.ArrayList;
import java.util.Scanner;

import static enigma.EnigmaException.*;

/**
 * Enigma simulator.
 *
 * @author Joe Mo
 */
public final class Main {

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified by ARGS, where 1 <= ARGS.length <= 3.
     * ARGS[0] is the name of a configuration file.
     * ARGS[1] is optional; when present, it names an input file
     * containing messages.  Otherwise, input comes from the standard
     * input.  ARGS[2] is optional; when present, it names an output
     * file for processed messages.  Otherwise, output goes to the
     * standard output. Exits normally if there are no errors in the input;
     * otherwise with code 1.
     */
    public static void main(String... args) {
        try {
            new Main(args).process();
            return;
        } catch (EnigmaException excp) {
            System.err.printf("Error: %s%n", excp.getMessage());
        }
        System.exit(1);
    }

    /**
     * Check ARGS and open the necessary files (see comment on main).
     */
    Main(String[] args) {
        if (args.length < 1 || args.length > 3) {
            throw error("Only 1, 2, or 3 command-line arguments allowed");
        }

        _config = getInput(args[0]);

        if (args.length > 1) {
            _input = getInput(args[1]);
        } else {
            _input = new Scanner(System.in);
        }

        if (args.length > 2) {
            _output = getOutput(args[2]);
        } else {
            _output = System.out;
        }
    }

    /**
     * Return a Scanner reading from the file named NAME.
     */
    private Scanner getInput(String name) {
        try {
            return new Scanner(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Return a PrintStream writing to the file named NAME.
     */
    private PrintStream getOutput(String name) {
        try {
            return new PrintStream(new File(name));
        } catch (IOException excp) {
            throw error("could not open %s", name);
        }
    }

    /**
     * Configure an Enigma machine from the contents of configuration
     * file _config and apply it to the messages in _input, sending the
     * results to _output.
     */
    private void process() {
        Machine machine = readConfig();

        while (_input.hasNextLine()) {
            String line = _input.nextLine().strip();
            if (line.equals("")) {
                _output.println();
            } else if (line.charAt(0) == '*') {
                processMessage(machine, line);
            } else {
                throw error("Malformed input file");
            }
        }
    }

    /**
     * Processes and outputs a single message from _input using a MACHINE and
     * its SETTINGS String.
     */
    private void processMessage(Machine machine, String settings) {
        settings = settings.substring(1);
        setUp(machine, settings);

        String input;
        while (_input.hasNextLine() && _input.hasNext("[^*]+")) {
            input = _input.nextLine().replaceAll("\\s", "");
            printMessageLine(machine.convert(input));
        }
    }

    /**
     * Return an Enigma machine configured from the contents of configuration
     * file _config.
     */
    private Machine readConfig() {
        String alpha = _config.next();
        _alphabet = new Alphabet(alpha);

        int numRotors, numPawls;
        try {
            numRotors = Integer.parseInt(_config.next());
            numPawls = Integer.parseInt(_config.next());
        } catch (NumberFormatException e) {
            throw error("Malformed config (expected number of rotors &"
                    + " number of pawls)");
        }

        ArrayList<Rotor> rotors = new ArrayList<>();
        while (_config.hasNext()) {
            rotors.add(readRotor());
        }

        return new Machine(_alphabet, numRotors, numPawls, rotors);
    }

    /**
     * Return a rotor, reading its description from _config.
     */
    private Rotor readRotor() {
        String name = _config.next();
        String rotorSetting = _config.next();
        String notches = rotorSetting.substring(1);

        StringBuilder perm = new StringBuilder();
        while (_config.hasNext("\\(.+\\)")) {
            perm.append(_config.next());
        }

        Permutation rotorPerm = new Permutation(perm.toString(), _alphabet);

        switch (rotorSetting.charAt(0)) {
        case 'M':
            return new MovingRotor(name, rotorPerm, notches);
        case 'N':
            return new FixedRotor(name, rotorPerm);
        case 'R':
            return new Reflector(name, rotorPerm);
        default:
            throw new EnigmaException("Bad rotor description");
        }
    }

    /**
     * Set M according to the specification given on SETTINGS,
     * which must have the format specified in the assignment.
     */
    private void setUp(Machine M, String settings) {
        Scanner input = new Scanner(settings);
        String[] rotors = new String[M.numRotors()];

        for (int i = 0; i < M.numRotors(); i++) {
            rotors[i] = input.next();
        }

        M.insertRotors(rotors);
        M.resetRings();

        String initialPositions = input.next();
        M.setRotors(initialPositions);

        if (input.hasNext("[^()]+")) {
            String ringSettings = input.next();
            M.setRings(ringSettings);
        }

        StringBuilder cycles = new StringBuilder();
        while (input.hasNext()) {
            cycles.append(input.next());
        }

        M.setPlugboard(new Permutation(cycles.toString(), _alphabet));
    }

    /**
     * Print MSG in groups of five (except that the last group may
     * have fewer letters).
     */
    private void printMessageLine(String msg) {
        if (msg.length() > 4) {
            _output.print(msg.substring(0, 5));
            msg = msg.substring(5);
        } else {
            _output.println(msg);
            return;
        }

        while (msg.length() > 4) {
            _output.print(" " + msg.substring(0, 5));
            msg = msg.substring(5);
        }

        if (msg.length() > 0) {
            _output.print(" " + msg);
        }

        _output.println();
    }

    /**
     * Alphabet used in this machine.
     */
    private Alphabet _alphabet;

    /**
     * Source of input messages.
     */
    private Scanner _input;

    /**
     * Source of machine configuration.
     */
    private Scanner _config;

    /**
     * File for encoded/decoded messages.
     */
    private PrintStream _output;

}
