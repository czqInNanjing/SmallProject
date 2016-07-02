// This is a SUGGESTED skeleton file.  Throw it away if you don't use it.
package enigma;

import a.k.P;

/**
 * Class that represents a rotor in the enigma machine.
 *
 * @author
 */
class Rotor {
    // This needs other methods, fields, and constructors.

    /**
     * Size of alphabet used for plaintext and ciphertext.
     */
    static final int ALPHABET_SIZE = 26;

    /**
     * Assuming that P is an integer in the range 0..25, returns the
     * corresponding upper-case letter in the range A..Z.
     */
    static char toLetter(int p) {
        return (char) ('A' + p);
    }

    /**
     * Assuming that C is an upper-case letter in the range A-Z, return the
     * corresponding index in the range 0..25. Inverse of toLetter.
     */
    static int toIndex(char c) {
        return c - 'A';
    }

    /**
     * Find the convert map string based on the rotor name
     *
     * @param forwardOrBackward true means forward, forward means right to left
     * @return map string
     */
    private String findConvertString(boolean forwardOrBackward) {
        for (String[] strings : PermutationData.ROTOR_SPECS) {
            if (strings[0].equals(name)) {
                if (forwardOrBackward) {
                    return strings[1];
                } else {
                    return strings[2];
                }
            }
        }
        return null;
    }

    /**
     * Returns true iff this rotor has a ratchet and can advance.
     */
    boolean advances() {
        return true;
    }

    /**
     * Returns true iff this rotor has a left-to-right inverse.
     */
    boolean hasInverse() {
        return true;
    }

    /**
     * Return my current rotational setting as an integer between 0
     * and 25 (corresponding to letters 'A' to 'Z').
     */
    int getSetting() {
        return _setting;
    }

    /**
     * Set getSetting() to POSN.
     */
    void set(int posn) {
        assert 0 <= posn && posn < ALPHABET_SIZE;
        _setting = posn;
    }

    /**
     * Return the conversion of P (an integer in the range 0..25)
     * according to my permutation.
     */
    int convertForward(int p) {
        int in = (p + _setting) % ALPHABET_SIZE;
//        System.out.println(p + " " + _setting + " " + in);
//        System.out.println(name);

//        System.out.println(findConvertString(true));
        int out = toIndex(findConvertString(true).charAt(in));
//        System.out.println(p + " " + _setting + " " + out);
//        System.out.println((out - _setting) % ALPHABET_SIZE);
        int result = (out - _setting) % ALPHABET_SIZE;
        if (result < 0) {
            result += ALPHABET_SIZE;
        }
        return result;
    }

    /**
     * Return the conversion of E (an integer in the range 0..25)
     * according to the inverse of my permutation.
     */
    int convertBackward(int e) {
        int in = (e + _setting) % ALPHABET_SIZE;
//        System.out.println(e + " " + _setting + " " + in);
//        System.out.println(name);

//        System.out.println(findConvertString(true));
        int out = toIndex(findConvertString(false).charAt(in));
//        System.out.println(e + " " + _setting + " " + out);
//        System.out.println((out - _setting) % ALPHABET_SIZE);
        int result = (out - _setting) % ALPHABET_SIZE;
        if (result < 0) {
            result += ALPHABET_SIZE;
        }
        return result;
    }

    char convertForward(char p) {
        return toLetter(convertForward(toIndex(p)));
    }

    char convertBackward(char e) {
        return toLetter(convertBackward(toIndex(e)));
    }

    /**
     * Returns true iff I am positioned to allow the rotor to my left
     * to advance.
     */
    boolean atNotch() {
        int lastSetting = (_setting - 1) % 26;
        if (lastSetting < 0){
            lastSetting += ALPHABET_SIZE;
        }
        for (int n : notchs) {
            if (n == lastSetting) {
                return true;
            }
        }
        return false;
    }

    /**
     * Advance me one position.
     */
    void advance() {
        _setting = (_setting + 1) % ALPHABET_SIZE;
    }

    /**
     * My current setting (index 0..25, with 0 indicating that 'A'
     * is showing).
     */
    private int _setting;

    /**
     * name of the rotor
     */
    private String name;

    /**
     * notchs of the rotor
     */
    private int[] notchs;


    Rotor(String name, int[] notchs) {
        this.name = name;
        this.notchs = notchs;
    }

}
