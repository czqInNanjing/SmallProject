package cn.edu.nju.util;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class NormalCommand implements Command {
    private String mustWord;

    public NormalCommand(String mustWord) {
        this.mustWord = mustWord;
    }

    public boolean isResult(Line line) {
        for (String word: line.getWords()) {
            if (SystemConstants.stringEquals(word, mustWord))
                return true;
        }
        return false;
    }
    public String getWords() {
        return mustWord;
    }
}
