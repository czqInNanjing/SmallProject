// This is a SUGGESTED skeleton file.  Throw it away if you don't use it.
package enigma;

import java.awt.geom.RoundRectangle2D;

/**
 * Class that represents a complete enigma machine.
 *
 * @author
 */
class Machine {

    // This needs other methods or constructors.
    private Rotor[] rotors;

    /**
     * Set my rotors to (from left to right) ROTORS.  Initially, the rotor
     * settings are all 'A'.
     */
    void replaceRotors(Rotor[] rotors) {
        this.rotors = rotors;
    }

    /**
     * Set my rotors according to SETTING, which must be a string of four
     * upper-case letters. The first letter refers to the leftmost
     * rotor setting.
     */
    void setRotors(String setting) {
        rotors[0].set(Rotor.toIndex(setting.charAt(0)));
        for (int i = 0; i < 3; i++) {
            rotors[i + 2].set(Rotor.toIndex(setting.charAt(i + 1)));
        }

    }

    /**
     * Returns the encoding/decoding of MSG, updating the state of
     * the rotors accordingly.
     */
    String convert(String msg) {
        StringBuilder result = new StringBuilder(msg.length());
        System.out.println(msg);
        for (char c : msg.toCharArray()) {

            boolean allowToAdvance = true;

            for (int i = 4; i > 2; i--) {

                if (allowToAdvance) {
                    rotors[i].advance();
                }
                allowToAdvance = rotors[i].atNotch();

            }
            if (allowToAdvance) {
                rotors[3].advance();
            }

            for (int i = 4; i >= 0; i--) {
//                System.out.println("before convert " + c);
                c = rotors[i].convertForward(c);
//                System.out.println("after convert " + c);


            }

            for (int i = 1; i < 5; i++) {
                Rotor rotor = rotors[i];
                if (rotor.hasInverse()) { // so the rotor is not a reflector
//                    System.out.println("before backword convert " + c);
                    c = rotor.convertBackward(c);

//                    System.out.println("after backword convert " + c);
                }
            }

            result.append(c);


        }
        return result.toString();

    }
}
