package cn.edu.nju.util;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class AndCommand implements Command {

    private String[] words;
    private int wordsTimes;
    public AndCommand(String[] words) {
        this.words = words;

    }


    public boolean isResult(Line line) {
        boolean isResult = false;
        int count = 0;
        for (String word : line.getWords()) {
            if (SystemConstants.stringEquals(word, words[count]))
                count++;
            else
                count = 0;

            if (count == words.length) {
                wordsTimes++;
                isResult = true;
                count = 0;
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

    public int getWordsTimes() {
        return wordsTimes;
    }
}
