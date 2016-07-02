package enigma;

/** Descriptions of permutations used in Enigma rotors.
 *  @author P. N. Hilfinger
 */
class PermutationData {

    /** The names and definitions of the rotors and reflectors in M4.  The
     *  first string in each entry is the name of a rotor or reflector.  The
     *  second is a 26-character string whose first character is the mapping
     *  (when the rotor is at the 'A' setting), of 'A' in the right-to-left
     *  direction, whose second is that of 'B', etc.
     *
     *  The third entry, if present, is the inverse of the
     *  second---the left-to-right permutation of the rotor.  It is
     *  not present for reflectors.
     *
     *  The fourth entry, if present, gives the positions of the
     *  notches. These are the settings of the rotors just before the
     *  wheels advanced (wheels advance before a character is
     *  translated).  Other written accounts of the Enigma generally
     *  show instead the character settings just after a character is
     *  coded (e.g., 'R', rather than 'Q', or 'A' rather than 'Z').
     *  The entry is absent in rotors that do not advance. */

    static final String[][] ROTOR_SPECS = {
        { "I", "EKMFLGDQVZNTOWYHXUSPAIBRCJ", "UWYGADFPVZBECKMTHXSLRINQOJ",
          "Q" },
        { "II", "AJDKSIRUXBLHWTMCQGZNPYFVOE", "AJPCZWRLFBDKOTYUQGENHXMIVS",
          "E" },
        { "III", "BDFHJLCPRTXVZNYEIWGAKMUSQO", "TAGBPCSDQEUFVNZHYIXJWLRKOM",
          "V" },
        { "IV", "ESOVPZJAYQUIRHXLNFTGKDCMWB", "HZWVARTNLGUPXQCEJMBSKDYOIF",
          "J" },
        { "V", "VZBRGITYUPSDNHLXAWMJQOFECK", "QCYLXWENFTZOSMVJUDKGIARPHB",
          "Z" },
        { "VI", "JPGVOUMFYQBENHZRDKASXLICTW", "SKXQLHCNWARVGMEBJPTYFDZUIO",
          "ZM" },
        { "VII", "NZJHGRCXMYSWBOUFAIVLPEKQDT", "QMGYVPEDRCWTIANUXFKZOSLHJB",
          "ZM" },
        { "VIII", "FKQHTLXOCBJSPDZRAMEWNIUYGV", "QJINSAYDVKBFRUHMCPLEWZTGXO",
          "ZM" },
        { "BETA", "LEYJVCNIXWPBQMDRTAKZGFUHOS", "RLFOBVUXHDSANGYKMPZQWEJICT" },
        { "GAMMA", "FSOKANUERHMBTIYCWLQPZXVGJD", "ELPZHAXJNYDRKFCTSIBMGWQVOU" },
        { "B", "ENKQAUYWJICOPBLMDXZVFTHRGS" },
        { "C", "RDOBJNTKVEHMLFCWZAXGYIPSUQ" }
    };

}
