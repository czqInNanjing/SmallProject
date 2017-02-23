package entity;

/**
 * @author Qiang
 * @date 07/12/2016
 */
public class Score {

    public int studentID;
    public int courseID;
    public int score;

    public Score(int studentID, int courseID, int score) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.score = score;
    }
}
