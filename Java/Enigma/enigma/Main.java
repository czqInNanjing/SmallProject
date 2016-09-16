// This is a SUGGESTED skeleton file.  Throw it away if you want.
package enigma;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Enigma simulator.
 *
 * @author
 */
public final class Main {


    /**
     * all the rotors inside
     */
    private static Map<String, Rotor> rotorMap;

    /**
     * Process a sequence of encryptions and decryptions, as
     * specified in the input from the standard input.  Print the
     * results on the standard output. Exits normally if there are
     * no errors in the input; otherwise with code 1.
     */
    public static void main(String[] args) {
        Machine M;
        BufferedReader input = null;
        try {
            input = new BufferedReader(
                    new InputStreamReader(new FileInputStream(args[0])));
        } catch (FileNotFoundException e) {
            throw new RuntimeException("No such file found.");
        }

        String outputFilename;
        if (args.length >= 2) {
            outputFilename = args[1];
        } else {
            outputFilename = "output.txt";
        }

        buildRotors();

        M = null;

        try {
            String line = input.readLine();
            if (isConfigurationLine(line)) {
                M = new Machine();
                configure(M, line);
            } else {
                throw new EnigmaException("The input not start with a configuration.");
            }

            while (true) {
                line = input.readLine();
                if (line == null) {
                    break;
                }
                if (isConfigurationLine(line)) {
                    M = new Machine();
                    configure(M, line);
                } else {
                    writeMessageLine(M.convert(standardize(line)),
                            outputFilename);
                }


            }
//            writeMessageLine("\n",outputFilename);
        } catch (IOException excp) {
            System.err.printf("Input error: %s%n", excp.getMessage());
            System.exit(1);
        }
    }

    /**
     * Return true iff LINE is an Enigma configuration line.
     */
    private static boolean isConfigurationLine(String line) throws EnigmaException {
        return line.startsWith("*");
    }

    /**
     * Configure M according to the specification given on CONFIG,
     * which must have the format specified in the assignment.
     */
    private static void configure(Machine M, String config) {
        String[] result = config.split(" ");
        if (result.length != 7) {
            throw new EnigmaException("The configuration line contain the wrong number of arguments.");
        }
        Rotor[] rotors = new Rotor[5];
        List<String> rotorsName = new ArrayList<>();
        for (int i = 0; i < 5; i++) {

            String rotorName = result[i + 1];
            if (rotorsName.contains(rotorName)) {
                throw new EnigmaException("A rotor might be repeated in the configuration.");
            }

            rotorsName.add(rotorName);
            rotors[i] = rotorMap.get(rotorName);

            if (rotors[i] == null) {
                throw new EnigmaException("The rotors might be misnamed");
            }


        }

        if (rotors[0].hasInverse()) {
            throw new EnigmaException("The first rotor might not be a reflector.");
        }

        M.replaceRotors(rotors);
        //config settings of the router

        if (result[6].length() != 4) {
            throw new EnigmaException("The initial positions string might be the wrong length");
        }

        for (char c : result[6].toCharArray()) {
            if (c < 'A' || c > 'Z') {
                throw new EnigmaException("The initial positions string might contain non-alphabetic characters.");
            }
        }

        M.setRotors(result[6]);
    }

    /**
     * Return the result of converting LINE to all upper case,
     * removing all blanks and tabs.  It is an error if LINE contains
     * characters other than letters and blanks.
     */
    private static String standardize(String line) {
        System.out.println(line);
        StringBuilder result = new StringBuilder(line.length());

        for (char c : line.toCharArray()) {
            if (c == ' ') {
                continue;
            } else if (((c >= 'A') && (c <= 'Z')) || ((c >= 'a') && (c <= 'z'))) {
                result.append(c);
            } else {
                throw new EnigmaException("There is illegal character in the message: " + c);
            }


        }
        return result.toString().toUpperCase();
    }

    /**
     * Write MSG in groups of five to out file (except that the last
     * group may have fewer letters).
     */
    private static void writeMessageLine(String msg, String filename) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            String outputString = "";
            for (int i = 0; i < msg.length(); i += 5) {
                outputString += msg.substring(i, Math.min(i + 5, msg.length()));
                if (i + 5 < msg.length()) {
                    outputString += " ";
                }
            }
//            if (!outputString.isEmpty()) {
                writer.write(outputString + "\n");
//            }

            writer.close();
        } catch (IOException e) {
            System.out.println("IOException when writing: " + e);
        }
    }

    /**
     * Create all the necessary rotors.
     */
    private static void buildRotors() {
        rotorMap = new HashMap<>(30);

        for (String[] strings : PermutationData.ROTOR_SPECS) {
            if (strings.length == 2) {
                //fixed rotor
                rotorMap.put(strings[0], new Reflector(strings[0], null));
            } else if (strings.length == 3) {
                //reflector
                rotorMap.put(strings[0], new FixedRotor(strings[0], null));
            } else {
                // normal rotor
                rotorMap.put(strings[0], new Rotor(strings[0], makeNotchs(strings[3])));
            }
        }


    }

    private static int[] makeNotchs(String string) {
        int[] result = new int[string.length()];
        for (int i = 0; i < string.length(); i++) {
            result[i] = Rotor.toIndex(string.charAt(i));
        }

        return result;
    }

}

