package entity;

import java.io.Serializable;

/**
 * @author Qiang
 * @date 07/12/2016
 */
public class Score implements Serializable{

    public int studentID;
    public int courseID;
    public int score;

    public Score(int studentID, int courseID, int score) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.score = score;
    }
}
