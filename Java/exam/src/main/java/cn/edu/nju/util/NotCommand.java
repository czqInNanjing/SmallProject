package cn.edu.nju.util;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class NotCommand implements Command {

    private String notAllowWord;

    public NotCommand(String notAllowWord) {
        this.notAllowWord = notAllowWord;
    }

    public boolean isResult(Line line) {
        for (String word: line.getWords()) {
            if (SystemConstants.stringEquals(word, notAllowWord))
                return false;
        }
        return true;
    }
    public String getWords() {
        return "!" +notAllowWord;
    }
}
