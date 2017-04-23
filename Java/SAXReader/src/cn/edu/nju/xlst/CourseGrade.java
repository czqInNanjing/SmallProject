package cn.edu.nju.xlst;

import cn.edu.nju.enumType.ScoreType;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class CourseGrade implements ElementService {
    private String courseNumber;
    private ScoreType scoreType;
    private String studentNumber;
    private int score;

    public CourseGrade() {
    }

    public CourseGrade(String courseNumber, ScoreType scoreType, String studentNumber, int score) {
        this.courseNumber = courseNumber;
        this.scoreType = scoreType;
        this.studentNumber = studentNumber;
        this.score = score;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

    public ScoreType getGradeType() {
        return scoreType;
    }

    public void setGradeType(ScoreType gradeType) {
        this.scoreType = gradeType;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public Element createElement(Document doc) {
        Element cGradeElement = doc.createElement("课程成绩");
        cGradeElement.setAttribute("课程编号", courseNumber);
        cGradeElement.setAttribute("成绩性质", scoreType.toString());
        Element gradeElement = doc.createElement("成绩");
        Element sNumberElement = doc.createElement("学号");
        sNumberElement.appendChild(doc.createTextNode(studentNumber));
        Element scoreElement = doc.createElement("得分");
        scoreElement.appendChild(doc.createTextNode(score + ""));
        gradeElement.appendChild(sNumberElement);
        gradeElement.appendChild(scoreElement);
        cGradeElement.appendChild(gradeElement);

        return cGradeElement;
    }


}
