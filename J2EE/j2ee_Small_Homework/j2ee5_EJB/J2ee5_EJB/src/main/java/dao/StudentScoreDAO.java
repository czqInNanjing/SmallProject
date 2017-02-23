package dao;

import entity.Score;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author Qiang
 * @since 21/12/2016
 */
@Remote
public interface StudentScoreDAO {

    List<Score> getStudentsScores(int studentId);
}
