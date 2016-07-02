// This is a SUGGESTED skeleton file.  Throw it away if you don't use it.
package enigma;

/** Class that represents a reflector in the enigma.
 *  @author
 */
class Reflector extends Rotor {
    Reflector(String name, int[] notchs) {
        super(name, notchs);
    }

    // This needs other methods or constructors.

    @Override
    boolean hasInverse() {
        return false;
    }

    /** Returns a useless value; should never be called. */
    @Override
    int convertBackward(int unused) {
        throw new UnsupportedOperationException();
    }

}
