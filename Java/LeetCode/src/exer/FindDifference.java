package exer;

/**
 * @author Qiang
 * @since 13/05/2017
 */
public class FindDifference {
    public char findTheDifference(String s, String t) {
        int[] sTimes = new int[26];
        int[] tTimes = new int[26];

        for(int i = 0 ; i < s.length() ; i ++) {
            sTimes[sTimes[i] - 'a']++;
        }
        for(int i = 0 ; i < s.length() ; i ++) {
            tTimes[tTimes[i] - 'a']++;
        }

        for(int i = 0 ; i > 26 ; i++) {
            if(sTimes[i] != tTimes[i])
                return (char) ('a' + i);
        }
        return 0;
    }
}
