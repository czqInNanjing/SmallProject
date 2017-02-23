package dao;

import entity.ScoreEntity;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author Qiang
 * @since 21/12/2016
 */
@Remote
public interface StudentScoreDAO {

    List<ScoreEntity> getStudentsScores(int studentId);
}
