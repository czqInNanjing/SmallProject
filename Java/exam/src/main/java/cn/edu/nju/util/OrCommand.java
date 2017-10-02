package cn.edu.nju.util;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class OrCommand implements Command {

    private String[] words;
    private int[] times;
    public OrCommand(String[] words) {

        this.words = words;
        times = new int[words.length];
    }

    public boolean isResult(Line line) {
        boolean isResult = false;
        for (String word: line.getWords()) {
            for (int i = 0; i < words.length; i++) {
                if (SystemConstants.stringEquals(word, words[i])) {
                    isResult =  true;
                    times[i]++;
                }
            }

        }
        return isResult;
    }

    public String getWords() {
        StringBuilder result = new StringBuilder();
        for (String word: words) {
            result.append(" ").append(word);
        }

        return result.toString();
    }
}
