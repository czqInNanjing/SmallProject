package service;

import entity.ScoreEntity;

import javax.ejb.Remote;
import java.util.List;

/**
 * @author Qiang
 * @since 06/01/2017
 */
@Remote
public interface StudentScoreService {

    List<ScoreEntity> getStudentsScores(int studentId);

}
