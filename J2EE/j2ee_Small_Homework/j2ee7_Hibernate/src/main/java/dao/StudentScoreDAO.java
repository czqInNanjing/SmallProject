package dao;

import entity.ScoreEntity;

import java.util.List;

/**
 * @author Qiang
 * @since 21/12/2016
 */
public interface StudentScoreDAO {



    List<ScoreEntity> getStudentsScores(int studentId);



}
