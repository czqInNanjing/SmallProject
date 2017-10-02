package cn.edu.nju.util;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public interface Command {


    boolean isResult(Line line);

    String getWords();
}
