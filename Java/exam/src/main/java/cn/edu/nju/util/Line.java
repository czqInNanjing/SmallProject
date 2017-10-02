package cn.edu.nju.util;

import java.util.Arrays;

/**
 * @author Qiang
 * @since 10/07/2017
 */
public class Line {

    private int lineNum;
    private String[] words;


    public Line(int lineNum, String[] words) {
        this.lineNum = lineNum;
        this.words = words;
    }

    public int getLineNum() {
        return lineNum;
    }

    public String[] getWords() {
        return words;
    }

    @Override
    public String toString() {
        return "Line{" +
                "lineNum=" + lineNum +
                ", words=" + Arrays.toString(words) +
                '}';
    }
}
