package dao;

import entity.Score;

import java.util.List;

/**
 * @author Qiang
 * @since 21/12/2016
 */
public interface StudentScoreDAO {



    List<Score> getStudentsScores(int studentId);



}
