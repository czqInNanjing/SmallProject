package exer2;

/**
 * #44. Wildcard Matching
 * '?' Matches any single character.
 * '*' Matches any sequence of characters (including the empty sequence).
 * <p>
 * The matching should cover the entire input string (not partial).
 * <p>
 * The function prototype should be:
 * bool isMatch(const char *s, const char *p)
 * <p>
 * Some examples:
 * isMatch("aa","a") → false
 * isMatch("aa","aa") → true
 * isMatch("aaa","aa") → false
 * isMatch("aa", "*") → true
 * isMatch("aa", "a*") → true
 * isMatch("ab", "?*") → true
 * isMatch("aab", "c*a*b") → false
 *
 * @author Qiang
 * @since 15/05/2017
 */
public class PatternMatch {

    public boolean isMatch(String s, String p) {

        int startIndex = -1;
        int match = 0;
        int pPosition = 0;
        int sPosition = 0;

        while (sPosition != s.length()) {

            if (pPosition < p.length() &&  (s.charAt(sPosition) == p.charAt(pPosition) || p.charAt(pPosition) == '?') ) {
                sPosition++;
                pPosition++;
            } else if (pPosition < p.length() && p.charAt(pPosition) == '*') {
                // save the position for backtrack
                startIndex = pPosition;
                match = sPosition;

                pPosition++;

            }  else if (startIndex != -1 ) { //check if * has appeared
                pPosition = startIndex + 1;

                match++;
                sPosition = match;



            } else return false;
        }


        while (pPosition < p.length() && p.charAt(pPosition) == '*')
            pPosition ++;

        return pPosition == p.length();

    }

    public static void main(String[] args) {
        PatternMatch patternMatch = new PatternMatch();
        System.out.println(patternMatch.isMatch("aa", "a"));
        System.out.println(patternMatch.isMatch("aa", "aa"));
        System.out.println(patternMatch.isMatch("ab", "?*"));
    }

}
